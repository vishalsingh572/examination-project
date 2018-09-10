package com.bigcay.exhubs.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bigcay.exhubs.common.GlobalManager;
import com.bigcay.exhubs.common.ResponseResult;
import com.bigcay.exhubs.common.ResultType;
import com.bigcay.exhubs.common.ValidationResult;
import com.bigcay.exhubs.converter.DefaultDateEditor;
import com.bigcay.exhubs.form.ExamEventFormBean;
import com.bigcay.exhubs.form.ExamEventFormBeanValidator;
import com.bigcay.exhubs.form.ExamPaperFormBean;
import com.bigcay.exhubs.form.ExamPaperFormBeanValidator;
import com.bigcay.exhubs.form.ExamTypeFormBean;
import com.bigcay.exhubs.form.ExamTypeFormBeanValidator;
import com.bigcay.exhubs.form.QuestionDetailBean;
import com.bigcay.exhubs.form.SubmitExamPaperFormBean;
import com.bigcay.exhubs.form.SubmitQuestionHeaderBean;
import com.bigcay.exhubs.model.ExamCandidate;
import com.bigcay.exhubs.model.ExamEvent;
import com.bigcay.exhubs.model.ExamPaper;
import com.bigcay.exhubs.model.ExamType;
import com.bigcay.exhubs.model.QuestionDetail;
import com.bigcay.exhubs.model.QuestionHeader;
import com.bigcay.exhubs.model.QuestionSubject;
import com.bigcay.exhubs.model.QuestionType;
import com.bigcay.exhubs.model.SubmitQuestionAnswer;
import com.bigcay.exhubs.model.SubmitQuestionHeader;
import com.bigcay.exhubs.model.User;
import com.bigcay.exhubs.service.AuthorityService;
import com.bigcay.exhubs.service.ExamService;
import com.bigcay.exhubs.service.QuestionService;
import com.bigcay.exhubs.util.QuestionUtil;

@Controller
public class ExamController extends BaseController {

	@Autowired
	MessageSource messageSource;

	private static final Logger logger = LoggerFactory.getLogger(ExamController.class);
	private static final String ACTION_TYPE_SUBMIT = "submit";
	private static final String ACTION_TYPE_SAVE_DRAFT = "save_draft";

	@Autowired
	private ExamService examService;

	@Autowired
	private AuthorityService authorityService;

	@Autowired
	private QuestionService questionService;

	@Autowired
	private ExamTypeFormBeanValidator examTypeFormBeanValidator;

	@Autowired
	private ExamPaperFormBeanValidator examPaperFormBeanValidator;

	@Autowired
	private ExamEventFormBeanValidator examEventFormBeanValidator;

	@InitBinder("examTypeFormBean")
	protected void initExamTypeFormBeanBinder(WebDataBinder binder) {
		binder.setValidator(examTypeFormBeanValidator);
	}

	@InitBinder("examPaperFormBean")
	protected void initExamPaperFormBeanBinder(WebDataBinder binder) {
		binder.setValidator(examPaperFormBeanValidator);
	}

	@InitBinder("examEventFormBean")
	protected void initExamEventFormBeanBinder(WebDataBinder binder) {
		binder.setValidator(examEventFormBeanValidator);
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DefaultDateEditor());
	}

	@ModelAttribute("questionChoices")
	public String[] getAllQuestionChoices() {
		return QuestionUtil.getQuestionChoices();
	}
	
	@ModelAttribute("examTypes")
	public List<ExamType> getAllExamTypes() {
		return examService.findAllExamTypes();
	}

	@ModelAttribute("examPapers")
	public List<ExamPaper> getAllExamPapers() {
		return examService.findAllExamPapers();
	}

	@RequestMapping("examtypes")
	public String examTypesIndexHandler() {

		logger.debug("ExamController.examTypesIndexHandler is invoked.");

		return "examtypes/index";
	}

	@RequestMapping("ajax/examtypes/show_exam_types")
	public String showExamTypesAjaxHandler(Model model, @RequestParam("pageNumber") Integer pageNumber) {

		logger.debug("ExamController.showExamTypesAjaxHandler is invoked.");

		Page<ExamType> examTypePage = examService.findPageableExamTypes(pageNumber - 1);
		List<ExamType> examTypes = examTypePage.getContent();

		model.addAttribute("examTypes", examTypes);
		// add pagination attributes
		model.addAttribute("showRecordsJSFunc", "showExamTypes");
		model.addAllAttributes(GlobalManager.getGlobalPageableMap(examTypePage));

		return "ajax/examtypes/show_exam_types";
	}

	@RequestMapping(value = "examtypes/create", method = RequestMethod.GET)
	public String addExamTypeGetHandler(Model model) {

		logger.debug("ExamController.addExamTypeGetHandler is invoked.");

		model.addAttribute("examTypeFormBean", new ExamTypeFormBean());

		return "examtypes/add_exam_type";
	}

	@RequestMapping(value = "examtypes/create", method = RequestMethod.POST)
	public String addExamTypeSubmitHandler(Model model, Locale locale,
			@Valid @ModelAttribute("examTypeFormBean") ExamTypeFormBean examTypeFormBean, BindingResult result,
			final RedirectAttributes redirectAttributes) {

		logger.debug("ExamController.addExamTypeSubmitHandler is invoked.");

		if (result.hasErrors()) {
			return "examtypes/add_exam_type";
		} else {
			ExamType examType = new ExamType();
			examType.setName(examTypeFormBean.getName());
			examType.setDescription(examTypeFormBean.getDescription());

			examType = examService.persist(examType);

			redirectAttributes.addFlashAttribute("info", messageSource.getMessage(
					"examtypes.info.add_exam_type_success", new String[] { examType.getName() }, locale));
			return "redirect:/examtypes";
		}
	}

	@RequestMapping(value = "examtype/edit/{editId}", method = RequestMethod.GET)
	public String editExamTypeGetHandler(Model model, @PathVariable Integer editId) {

		logger.debug("ExamController.editExamTypeGetHandler is invoked.");

		ExamType editExamType = examService.findExamTypeById(editId);

		ExamTypeFormBean examTypeFormBean = new ExamTypeFormBean();
		examTypeFormBean.setId(editExamType.getId());
		examTypeFormBean.setName(editExamType.getName());
		examTypeFormBean.setDescription(editExamType.getDescription());

		model.addAttribute("examTypeFormBean", examTypeFormBean);

		return "examtypes/edit_exam_type";
	}

	@RequestMapping(value = "examtype/edit/{editId}", method = RequestMethod.POST)
	public String editExamTypeSubmitHandler(Model model, Locale locale, @PathVariable Integer editId,
			@Valid @ModelAttribute("examTypeFormBean") ExamTypeFormBean examTypeFormBean, BindingResult result,
			final RedirectAttributes redirectAttributes) {

		logger.debug("ExamController.editExamTypeSubmitHandler is invoked.");

		if (result.hasErrors()) {
			return "examtypes/edit_exam_type";
		} else {
			ExamType examType = examService.findExamTypeById(editId);
			examType.setName(examTypeFormBean.getName());
			examType.setDescription(examTypeFormBean.getDescription());

			examService.persist(examType);

			redirectAttributes.addFlashAttribute("info", messageSource.getMessage(
					"examtypes.info.edit_exam_type_success", new String[] { examType.getName() }, locale));
			return "redirect:/examtypes";
		}
	}

	@RequestMapping("exampapers")
	public String examPapersIndexHandler() {

		logger.debug("ExamController.examPapersIndexHandler is invoked.");

		return "exampapers/index";
	}

	@RequestMapping("ajax/exampapers/show_exam_papers")
	public String showExamPapersAjaxHandler(Model model, @RequestParam("pageNumber") Integer pageNumber) {

		logger.debug("ExamController.showExamPapersAjaxHandler is invoked.");

		Page<ExamPaper> examPaperPage = examService.findPageableExamPapers(pageNumber - 1);
		List<ExamPaper> examPapers = examPaperPage.getContent();

		model.addAttribute("examPapers", examPapers);
		// add pagination attributes
		model.addAttribute("showRecordsJSFunc", "showExamPapers");
		model.addAllAttributes(GlobalManager.getGlobalPageableMap(examPaperPage));

		return "ajax/exampapers/show_exam_papers";
	}

	@RequestMapping(value = "exampapers/create", method = RequestMethod.GET)
	public String addExamPaperGetHandler(Model model) {

		logger.debug("ExamController.addExamPaperGetHandler is invoked.");

		model.addAttribute("examPaperFormBean", new ExamPaperFormBean());

		return "exampapers/add_exam_paper";
	}

	@RequestMapping(value = "exampapers/create", method = RequestMethod.POST)
	public String addExamPaperSubmitHandler(Model model, Locale locale,
			@Valid @ModelAttribute("examPaperFormBean") ExamPaperFormBean examPaperFormBean, BindingResult result,
			final RedirectAttributes redirectAttributes, Principal principal) {

		logger.debug("ExamController.addExamPaperSubmitHandler is invoked.");

		if (result.hasErrors()) {
			return "exampapers/add_exam_paper";
		} else {
			/* authorityService.findUserByUserId(principal.getName()); */
			User editUser = authorityService.findUserById(1);

			ExamPaper examPaper = new ExamPaper();
			examPaper.setName(examPaperFormBean.getName());
			examPaper.setDescription(examPaperFormBean.getDescription());
			examPaper.setCreateDate(new Date());
			examPaper.setActiveFlag(true);
			examPaper.setUser(editUser);
			examPaper.setExamType(examService.findExamTypeById(examPaperFormBean.getExamTypeId()));

			examPaper = examService.persist(examPaper);

			redirectAttributes.addFlashAttribute(
					"info",
					messageSource.getMessage("exampapers.info.add_exam_paper_success",
							new String[] { examPaper.getName() }, locale));
			return "redirect:/exampapers";
		}
	}

	@RequestMapping(value = "exam_paper/edit/{editId}", method = RequestMethod.GET)
	public String editExamPaperGetHandler(Model model, @PathVariable Integer editId) {

		logger.debug("ExamController.editExamPaperGetHandler is invoked.");

		ExamPaper editExamPaper = examService.findExamPaperById(editId);

		ExamPaperFormBean examPaperFormBean = new ExamPaperFormBean();
		examPaperFormBean.setId(editExamPaper.getId());
		examPaperFormBean.setName(editExamPaper.getName());
		examPaperFormBean.setDescription(editExamPaper.getDescription());
		examPaperFormBean.setExamTypeId(editExamPaper.getExamType().getId());

		model.addAttribute("examPaperFormBean", examPaperFormBean);

		return "exampapers/edit_exam_paper";
	}

	@RequestMapping(value = "exam_paper/edit/{editId}", method = RequestMethod.POST)
	public String editExamPaperSubmitHandler(Model model, Locale locale, @PathVariable Integer editId,
			@Valid @ModelAttribute("examPaperFormBean") ExamPaperFormBean examPaperFormBean, BindingResult result,
			final RedirectAttributes redirectAttributes) {

		logger.debug("ExamController.editExamPageSubmitHandler is invoked.");

		if (result.hasErrors()) {
			return "exampapers/edit_exam_paper";
		} else {
			ExamPaper examPaper = examService.findExamPaperById(editId);
			examPaper.setName(examPaperFormBean.getName());
			examPaper.setDescription(examPaperFormBean.getDescription());
			examPaper.setExamType(examService.findExamTypeById(examPaperFormBean.getExamTypeId()));

			examService.persist(examPaper);

			redirectAttributes.addFlashAttribute(
					"info",
					messageSource.getMessage("exampapers.info.edit_exam_paper_success",
							new String[] { examPaper.getName() }, locale));
			return "redirect:/exampapers";
		}
	}

	@RequestMapping("exam_paper/{examPaperId}")
	public String selectExamPaperHandler(Model model, @PathVariable Integer examPaperId) {

		logger.debug("ExamController.selectExamPaperHandler is invoked.");

		model.addAttribute("examPaperId", examPaperId);

		return "exampapers/select_exam_paper";
	}

	@RequestMapping("ajax/exampapers/show_associated_question_subjects")
	public String showAssociatedQuestionSubjectsAjaxHandler(Model model,
			@RequestParam("examPaperId") Integer examPaperId) {

		logger.debug("ExamController.showAssociatedQuestionSubjectsAjaxHandler is invoked.");

		List<QuestionSubject> associatedQuestionSubjects = examService.findQuestionSubjectsByExamPaperId(examPaperId);

		model.addAttribute("associatedQuestionSubjects", associatedQuestionSubjects);

		return "ajax/exampapers/show_associated_question_subjects";
	}

	@RequestMapping("ajax/exampapers/show_potential_question_subjects")
	public String showPotentialQuestionSubjectsAjaxHandler(Model model, @RequestParam("pageNumber") Integer pageNumber) {

		logger.debug("ExamController.showPotentialQuestionSubjectsAjaxHandler is invoked.");

		// TO-DO, Add filter to get potential question subjects
		Page<QuestionSubject> potentialQuestionSubjectPage = questionService
				.findPageableQuestionSubjects(pageNumber - 1);
		List<QuestionSubject> potentialQuestionSubjects = potentialQuestionSubjectPage.getContent();

		model.addAttribute("potentialQuestionSubjects", potentialQuestionSubjects);
		// add pagination attributes
		model.addAttribute("showRecordsJSFunc", "showPotentialQuestionSubjects");
		model.addAllAttributes(GlobalManager.getGlobalPageableMap(potentialQuestionSubjectPage));

		return "ajax/exampapers/show_potential_question_subjects";
	}

	@RequestMapping("examevents")
	public String examEventsIndexHandler() {

		logger.debug("ExamController.examEventsIndexHandler is invoked.");

		return "examevents/index";
	}

	@RequestMapping("ajax/examevents/show_exam_events")
	public String showExamEventsAjaxHandler(Model model, @RequestParam("pageNumber") Integer pageNumber) {

		logger.debug("ExamController.showExamEventsAjaxHandler is invoked.");

		Page<ExamEvent> examEventPage = examService.findPageableExamEvents(pageNumber - 1);
		List<ExamEvent> examEvents = examEventPage.getContent();

		model.addAttribute("examEvents", examEvents);
		// add pagination attributes
		model.addAttribute("showRecordsJSFunc", "showExamEvents");
		model.addAllAttributes(GlobalManager.getGlobalPageableMap(examEventPage));

		return "ajax/examevents/show_exam_events";
	}

	@RequestMapping(value = "examevents/create", method = RequestMethod.GET)
	public String addExamEventGetHandler(Model model) {

		logger.debug("ExamController.addExamEventGetHandler is invoked.");

		model.addAttribute("examEventFormBean", new ExamEventFormBean());

		return "examevents/add_exam_event";
	}

	@RequestMapping(value = "examevents/create", method = RequestMethod.POST)
	public String addExamEventeSubmitHandler(Model model, Locale locale,
			@Valid @ModelAttribute("examEventFormBean") ExamEventFormBean examEventFormBean, BindingResult result,
			final RedirectAttributes redirectAttributes, Principal principal) {

		logger.debug("ExamController.addExamEventeSubmitHandler is invoked.");

		if (result.hasErrors()) {
			return "examevents/add_exam_event";
		} else {
			/* authorityService.findUserByUserId(principal.getName()); */
			User editUser = authorityService.findUserById(1);

			Calendar cal = Calendar.getInstance();
			cal.setTime(examEventFormBean.getStartDateTime());
			cal.add(Calendar.MINUTE, examEventFormBean.getDuration());
			Date calculatedEndDateTime = cal.getTime();
			
			ExamEvent examEvent = new ExamEvent();
			examEvent.setName(examEventFormBean.getName());
			examEvent.setDescription(examEventFormBean.getDescription());
			examEvent.setExamPaper(examService.findExamPaperById(examEventFormBean.getExamPaperId()));
			examEvent.setUser(editUser);
			examEvent.setStartDateTime(examEventFormBean.getStartDateTime());
			examEvent.setEndDateTime(calculatedEndDateTime);
			examEvent.setDuration(examEventFormBean.getDuration());
			examEvent.setActiveFlag(true);

			examEvent = examService.persist(examEvent);

			redirectAttributes.addFlashAttribute(
					"info",
					messageSource.getMessage("examevents.info.add_exam_event_success",
							new String[] { examEvent.getName() }, locale));
			return "redirect:/examevents";
		}
	}
	
	@RequestMapping(value = "exam_event/edit/{editId}", method = RequestMethod.GET)
	public String editExamEventGetHandler(Model model, @PathVariable Integer editId) {

		logger.debug("ExamController.editExamEventGetHandler is invoked.");

		ExamEvent editExamEvent = examService.findExamEventById(editId);

		ExamEventFormBean examEventFormBean = new ExamEventFormBean();
		examEventFormBean.setId(editExamEvent.getId());
		examEventFormBean.setName(editExamEvent.getName());
		examEventFormBean.setDescription(editExamEvent.getDescription());
		examEventFormBean.setExamPaperId(editExamEvent.getExamPaper().getId());
		examEventFormBean.setStartDateTime(editExamEvent.getStartDateTime());
		examEventFormBean.setDuration(editExamEvent.getDuration());

		model.addAttribute("examEventFormBean", examEventFormBean);

		return "examevents/edit_exam_event";
	}
	
	@RequestMapping(value = "exam_event/edit/{editId}", method = RequestMethod.POST)
	public String editExamEventSubmitHandler(Model model, Locale locale, @PathVariable Integer editId,
			@Valid @ModelAttribute("examEventFormBean") ExamEventFormBean examEventFormBean, BindingResult result,
			final RedirectAttributes redirectAttributes) {

		logger.debug("ExamController.editExamEventSubmitHandler is invoked.");

		if (result.hasErrors()) {
			return "examevents/edit_exam_event";
		} else {
			Calendar cal = Calendar.getInstance();
			cal.setTime(examEventFormBean.getStartDateTime());
			cal.add(Calendar.MINUTE, examEventFormBean.getDuration());
			Date calculatedEndDateTime = cal.getTime();

			ExamEvent examEvent = examService.findExamEventById(editId);
			examEvent.setName(examEventFormBean.getName());
			examEvent.setDescription(examEventFormBean.getDescription());
			examEvent.setExamPaper(examService.findExamPaperById(examEventFormBean.getExamPaperId()));
			examEvent.setStartDateTime(examEventFormBean.getStartDateTime());
			examEvent.setEndDateTime(calculatedEndDateTime);
			examEvent.setDuration(examEventFormBean.getDuration());

			examService.persist(examEvent);

			redirectAttributes.addFlashAttribute(
					"info",
					messageSource.getMessage("examevents.info.edit_exam_event_success",
							new String[] { examEvent.getName() }, locale));
			return "redirect:/examevents";
		}
	}
	
	@RequestMapping("exam_event/{examEventId}")
	public String selectExamEventHandler(Model model, @PathVariable Integer examEventId) {

		logger.debug("ExamController.selectExamEventHandler is invoked.");

		model.addAttribute("examEventId", examEventId);

		return "examevents/select_exam_event";
	}
	
	@RequestMapping("ajax/examevents/show_associated_candidates")
	public String showAssociatedCandidatesAjaxHandler(Model model,
			@RequestParam("examEventId") Integer examEventId) {

		logger.debug("ExamController.showAssociatedCandidatesAjaxHandler is invoked.");

		List<User> associatedCandidates = examService.findCandidatesByExamEventId(examEventId);

		model.addAttribute("associatedCandidates", associatedCandidates);

		return "ajax/examevents/show_associated_candidates";
	}

	@RequestMapping("ajax/examevents/show_potential_candidates")
	public String showPotentialCandidatesAjaxHandler(Model model, @RequestParam("pageNumber") Integer pageNumber) {

		logger.debug("ExamController.showPotentialCandidatesAjaxHandler is invoked.");

		// TO-DO, Add filter to get potential candidates
		Page<User> potentialCandidatePage = authorityService.findPageableUsers(pageNumber - 1);
		List<User> potentialCandidates = potentialCandidatePage.getContent();

		model.addAttribute("potentialCandidates", potentialCandidates);
		// add pagination attributes
		model.addAttribute("showRecordsJSFunc", "showPotentialCandidates");
		model.addAllAttributes(GlobalManager.getGlobalPageableMap(potentialCandidatePage));

		return "ajax/examevents/show_potential_candidates";
	}
	
	@RequestMapping("ajax/examevents/show_associated_reviewers")
	public String showAssociatedReviewersAjaxHandler(Model model,
			@RequestParam("examEventId") Integer examEventId) {

		logger.debug("ExamController.showAssociatedReviewersAjaxHandler is invoked.");

		List<User> associatedReviewers = examService.findReviewersByExamEventId(examEventId);

		model.addAttribute("associatedReviewers", associatedReviewers);

		return "ajax/examevents/show_associated_reviewers";
	}

	@RequestMapping("ajax/examevents/show_potential_reviewers")
	public String showPotentialReviewersAjaxHandler(Model model, @RequestParam("pageNumber") Integer pageNumber) {

		logger.debug("ExamController.showPotentialReviewersAjaxHandler is invoked.");

		// TO-DO, Add filter to get potential reviewers
		Page<User> potentialReviewerPage = authorityService.findPageableUsers(pageNumber - 1);
		List<User> potentialReviewers = potentialReviewerPage.getContent();

		model.addAttribute("potentialReviewers", potentialReviewers);
		// add pagination attributes
		model.addAttribute("showRecordsJSFunc", "showPotentialReviewers");
		model.addAllAttributes(GlobalManager.getGlobalPageableMap(potentialReviewerPage));

		return "ajax/examevents/show_potential_reviewers";
	}
	
	@RequestMapping("joinexams")
	public String joinExamsIndexHandler() {

		logger.debug("ExamController.joinExamsIndexHandler is invoked.");

		return "joinexams/index";
	}
	
	@RequestMapping("ajax/joinexams/show_candidate_exam_events")
	public String showCandidateExamEventsAjaxHandler(Model model, Principal principal) {

		logger.debug("ExamController.showCandidateExamEventsAjaxHandler is invoked.");
		
		User currentUser = authorityService.findUserByUserId(principal.getName());
		
		List<ExamEvent> candidateExamEvents = examService.findCandidateExamEventsByUserId(currentUser.getId());
		
		model.addAttribute("candidateExamEvents", candidateExamEvents);

		return "ajax/joinexams/show_candidate_exam_events";
	}
	
	@RequestMapping("answer_exam/{currExamEventId}")
	public String answerExamIndexHandler(Model model, @PathVariable Integer currExamEventId, Principal principal) {

		logger.debug("ExamController.answerExamIndexHandler is invoked.");

		User currentUser = authorityService.findUserByUserId(principal.getName());
		ExamEvent examEvent = examService.findExamEventById(currExamEventId);

		List<SubmitQuestionHeader> submitQuestionHeaders = examService.findSubmitQuestionHeadersByExamEventIdAndUserId(currExamEventId,
				currentUser.getId());

		if (submitQuestionHeaders != null && submitQuestionHeaders.size() > 0) {
			Map<Integer, SubmitQuestionHeader> submitQuestionHeaderMap = new HashMap<Integer, SubmitQuestionHeader>();

			for (SubmitQuestionHeader submitQuestionHeader : submitQuestionHeaders) {
				submitQuestionHeaderMap.put(submitQuestionHeader.getQuestionHeader().getId(), submitQuestionHeader);
			}

			for (QuestionSubject questionSubject : examEvent.getExamPaper().getQuestionSubjects()) {
				for (QuestionHeader questionHeader : questionSubject.getQuestionHeaders()) {
					if (submitQuestionHeaderMap.containsKey(questionHeader.getId())) {
						SubmitQuestionAnswer submitQuestionAnswer = submitQuestionHeaderMap.get(
								questionHeader.getId()).getSubmitQuestionAnswer();
						questionHeader.setCandidateSubmitQuestionAnswer(submitQuestionAnswer);
						
						if (submitQuestionAnswer.getBinaryValue() != null && submitQuestionAnswer.getBinaryValue() > 0) {

							int sortIndex = 1;
							for (QuestionDetail questionDetail : questionHeader.getQuestionDetails()) {
								int currQuestionDetailBinary = (int) Math.pow(2, sortIndex - 1);

								if ((submitQuestionAnswer.getBinaryValue() & currQuestionDetailBinary) == currQuestionDetailBinary) {
									questionDetail.setIsChecked(true);
								}

								sortIndex++;
							}
						}
					}
				}
			}
		}
		
		SubmitExamPaperFormBean submitExamPaperFormBean = new SubmitExamPaperFormBean();
		List<SubmitQuestionHeaderBean> submitQuestionHeaderBeans = new ArrayList<SubmitQuestionHeaderBean>();
		submitExamPaperFormBean.setSubmitQuestionHeaderBeans(submitQuestionHeaderBeans);

		model.addAttribute("examEvent", examEvent);
		model.addAttribute("userId", currentUser.getId());
		model.addAttribute("submitExamPaperFormBean", submitExamPaperFormBean);

		return "joinexams/answer_exam";
	}
	
	@RequestMapping(value = "answer_exam/{currExamEventId}", method = RequestMethod.POST)
	public String examPaperSubmitHandler(Model model, Locale locale, @PathVariable Integer currExamEventId, 
			@RequestParam("actionType") String actionType,
			@Valid @ModelAttribute("submitExamPaperFormBean") SubmitExamPaperFormBean submitExamPaperFormBean,
			BindingResult result, final RedirectAttributes redirectAttributes, Principal principal) {

		logger.debug("ExamController.examPaperSubmitHandler is invoked.");
		
		if (result.hasErrors()) {
			return "answer_exam/" + currExamEventId;
		} else {
			ExamEvent examEvent = examService.findExamEventById(currExamEventId);
			User currentUser = authorityService.findUserByUserId(principal.getName());

			for (SubmitQuestionHeaderBean submitQuestionHeaderBean : submitExamPaperFormBean
					.getSubmitQuestionHeaderBeans()) {

				SubmitQuestionHeader submitQuestionHeader = null;
				SubmitQuestionAnswer submitQuestionAnswer = null;
				String questionTypeName = submitQuestionHeaderBean.getQuestionTypeName();

				SubmitQuestionHeader existSubmitQuestionHeader = examService.findSubmitQuestionHeader(currExamEventId,
						currentUser.getId(), submitQuestionHeaderBean.getQuestionHeaderId());

				submitQuestionHeader = existSubmitQuestionHeader != null ? existSubmitQuestionHeader
						: new SubmitQuestionHeader();

				submitQuestionAnswer = submitQuestionHeader.getSubmitQuestionAnswer() != null ? submitQuestionHeader
						.getSubmitQuestionAnswer() : new SubmitQuestionAnswer();

				QuestionHeader questionHeader = questionService.findQuestionHeaderById(submitQuestionHeaderBean
						.getQuestionHeaderId());

				int questionDetailIndex = 1;
				int checkboxBinaryNum = 0;
				List<QuestionDetailBean> submitQuestionDetailBeans = submitQuestionHeaderBean.getQuestionDetailBeans();

				for (QuestionDetailBean submitQuestionDetailBean : submitQuestionDetailBeans) {
					if (submitQuestionDetailBean.getIsChecked() != null
							&& submitQuestionDetailBean.getIsChecked().booleanValue()) {
						checkboxBinaryNum += Math.pow(2, questionDetailIndex - 1);
					}
					questionDetailIndex++;
				}

				if ("SCQ".equalsIgnoreCase(questionTypeName) || "TFQ".equalsIgnoreCase(questionTypeName)) {
					if (submitQuestionHeaderBean.getRadioSelectedIndex() != null) {
						int binaryValue = (int) Math.pow(2, (double) submitQuestionHeaderBean.getRadioSelectedIndex());
						submitQuestionAnswer.setBinaryValue(binaryValue);
					}
				} else if ("MCQ".equalsIgnoreCase(questionTypeName)) {
					if (checkboxBinaryNum > 0) {
						submitQuestionAnswer.setBinaryValue(checkboxBinaryNum);
					}
				} else if ("BFQ".equalsIgnoreCase(questionTypeName)) {
					if (submitQuestionHeaderBean.getTextAnswer() != null
							&& !submitQuestionHeaderBean.getTextAnswer().isEmpty()) {
						submitQuestionAnswer.setShortTextValue(submitQuestionHeaderBean.getTextAnswer());
					}
				} else if ("EQ".equalsIgnoreCase(questionTypeName)) {
					if (submitQuestionHeaderBean.getTextAnswer() != null
							&& !submitQuestionHeaderBean.getTextAnswer().isEmpty()) {
						submitQuestionAnswer.setLongTextValue(submitQuestionHeaderBean.getTextAnswer());
					}
				}

				submitQuestionHeader.setExamEvent(examEvent);
				submitQuestionHeader.setCandidate(currentUser);
				submitQuestionHeader.setQuestionHeader(questionHeader);
				submitQuestionHeader.setSubmitQuestionAnswer(submitQuestionAnswer);

				examService.persist(submitQuestionHeader);
			}
			
			if(ACTION_TYPE_SUBMIT.equalsIgnoreCase(actionType)) {
				ExamCandidate currentExamCandidate = examService.findExamCandidateByExamEventIdAndUserId(currExamEventId, currentUser.getId());
				currentExamCandidate.setSubmitDateTime(new Date());
				
				examService.persist(currentExamCandidate);
			}

			if (ACTION_TYPE_SAVE_DRAFT.equalsIgnoreCase(actionType)) {
				redirectAttributes.addFlashAttribute(
						"info",
						messageSource.getMessage("joinexams.info.save_draft_exam_success",
								new String[] { examEvent.getName() }, locale));
			} else if (ACTION_TYPE_SUBMIT.equalsIgnoreCase(actionType)) {
				redirectAttributes.addFlashAttribute(
						"info",
						messageSource.getMessage("joinexams.info.submit_exam_success",
								new String[] { examEvent.getName() }, locale));
			}
			
			return "redirect:/joinexams";
		}
	}
	
	@RequestMapping("reviewexams")
	public String reviewExamsIndexHandler() {

		logger.debug("ExamController.reviewExamsIndexHandler is invoked.");

		return "reviewexams/index";
	}
	
	@RequestMapping("ajax/reviewexams/show_reviewer_exam_events")
	public String showReviewerExamEventsAjaxHandler(Model model, Principal principal) {

		logger.debug("ExamController.showReviewerExamEventsAjaxHandler is invoked.");
		
		User currentUser = authorityService.findUserByUserId(principal.getName());
		
		List<ExamEvent> reviewerExamEvents = examService.findReviewerExamEventsByUserId(currentUser.getId());
		
		model.addAttribute("reviewerExamEvents", reviewerExamEvents);

		return "ajax/reviewexams/show_reviewer_exam_events";
	}
	
	@RequestMapping("review_exam_event/{examEventId}")
	public String reviewExamEventHandler(Model model, @PathVariable Integer examEventId) {

		logger.debug("ExamController.reviewExamEventHandler is invoked.");

		model.addAttribute("examEventId", examEventId);

		return "reviewexams/select_review_exam_event";
	}
	
	@RequestMapping("ajax/reviewexams/show_review_question_subjects")
	public String showReviewQuestionSubjectsAjaxHandler(Model model, @RequestParam("examEventId") Integer examEventId, Principal principal) {

		logger.debug("ExamController.showReviewQuestionSubjectsAjaxHandler is invoked.");
		
		//User currentUser = authorityService.findUserByUserId(principal.getName());
		
		ExamEvent examEvent = examService.findExamEventById(examEventId);
		Set<QuestionSubject> questionSubjects = examEvent.getExamPaper().getQuestionSubjects();
		
		model.addAttribute("questionSubjects", questionSubjects);
		model.addAttribute("examEventId", examEventId);
		
		return "ajax/reviewexams/show_review_question_subjects";
	}
	
	@RequestMapping(value = "/rest/joinexams/validate_submit_exam_paper", method = RequestMethod.POST)
	public @ResponseBody
	ResponseResult validateSubmitExamPaperRestHandler(Locale locale,
			SubmitExamPaperFormBean submitExamPaperFormBean) {

		logger.debug("ExamController.validateSubmitExamPaperRestHandler is invoked.");

		ValidationResult validationResult = examService.validateBeforeSubmitExamPaper(
				submitExamPaperFormBean, locale);

		ResponseResult responseResult = new ResponseResult(validationResult);
		return responseResult;
	}

	@RequestMapping(value = "/rest/examtypes/delete_exam_type", method = RequestMethod.POST)
	public @ResponseBody
	ResponseResult deleteExamTypeRestHandler(Locale locale, @RequestParam("deleteId") Integer deleteId) {

		logger.debug("ExamController.deleteExamTypeRestHandler is invoked.");

		ValidationResult validationResult = examService.validateBeforeDeleteExamType(deleteId, locale);

		if (ResultType.SUCCESS == validationResult.getResultType()) {
			examService.deleteExamType(deleteId);
		}

		ResponseResult responseResult = new ResponseResult(validationResult);
		return responseResult;
	}

	@RequestMapping(value = "/rest/exampapers/delete_exam_paper", method = RequestMethod.POST)
	public @ResponseBody
	ResponseResult deleteExamPaperRestHandler(Locale locale, @RequestParam("deleteId") Integer deleteId) {

		logger.debug("ExamController.deleteExamPaperRestHandler is invoked.");

		ValidationResult validationResult = examService.validateBeforeDeleteExamPaper(deleteId, locale);

		if (ResultType.SUCCESS == validationResult.getResultType()) {
			examService.deleteExamPaper(deleteId);
		}

		ResponseResult responseResult = new ResponseResult(validationResult);
		return responseResult;
	}
	
	@RequestMapping(value = "/rest/exampapers/assign_question_subject", method = RequestMethod.POST)
	public @ResponseBody
	ResponseResult assignQuestionSubjectRestHandler(Locale locale, @RequestParam("examPaperId") Integer examPaperId, 
			@RequestParam("questionSubjectId") Integer questionSubjectId) {

		logger.debug("ExamController.assignQuestionSubjectRestHandler is invoked.");

		ValidationResult validationResult = examService.validateBeforeAssignQuestionSubject(examPaperId, questionSubjectId, locale);

		if (ResultType.SUCCESS == validationResult.getResultType()) {
			examService.assignQuestionSubject(examPaperId, questionSubjectId);
		}

		ResponseResult responseResult = new ResponseResult(validationResult);
		return responseResult;
	}
	
	@RequestMapping(value = "/rest/exampapers/detach_question_subject", method = RequestMethod.POST)
	public @ResponseBody
	ResponseResult detachQuestionSubjectRestHandler(Locale locale, @RequestParam("examPaperId") Integer examPaperId, 
			@RequestParam("questionSubjectId") Integer questionSubjectId) {

		logger.debug("ExamController.detachQuestionSubjectRestHandler is invoked.");

		ValidationResult validationResult = examService.validateBeforeDetachQuestionSubject(examPaperId, questionSubjectId, locale);

		if (ResultType.SUCCESS == validationResult.getResultType()) {
			examService.detachQuestionSubject(examPaperId, questionSubjectId);
		}

		ResponseResult responseResult = new ResponseResult(validationResult);
		return responseResult;
	}
	
	@RequestMapping(value = "/rest/examevents/delete_exam_event", method = RequestMethod.POST)
	public @ResponseBody
	ResponseResult deleteExamEventRestHandler(Locale locale, @RequestParam("deleteId") Integer deleteId) {

		logger.debug("ExamController.deleteExamEventRestHandler is invoked.");

		ValidationResult validationResult = examService.validateBeforeDeleteExamEvent(deleteId, locale);

		if (ResultType.SUCCESS == validationResult.getResultType()) {
			examService.deleteExamEvent(deleteId);
		}

		ResponseResult responseResult = new ResponseResult(validationResult);
		return responseResult;
	}
	
	@RequestMapping(value = "/rest/examevents/assign_candidate", method = RequestMethod.POST)
	public @ResponseBody
	ResponseResult assignCandidateRestHandler(Locale locale, @RequestParam("examEventId") Integer examEventId,
			@RequestParam("candidateId") Integer candidateId) {

		logger.debug("ExamController.assignCandidateRestHandler is invoked.");

		ValidationResult validationResult = examService.validateBeforeAssignCandidate(examEventId, candidateId, locale);

		if (ResultType.SUCCESS == validationResult.getResultType()) {
			examService.assignCandidate(examEventId, candidateId);
		}

		ResponseResult responseResult = new ResponseResult(validationResult);
		return responseResult;
	}
	
	@RequestMapping(value = "/rest/examevents/detach_candidate", method = RequestMethod.POST)
	public @ResponseBody
	ResponseResult detachCandidateRestHandler(Locale locale, @RequestParam("examEventId") Integer examEventId,
			@RequestParam("candidateId") Integer candidateId) {

		logger.debug("ExamController.detachCandidateRestHandler is invoked.");

		ValidationResult validationResult = examService.validateBeforeDetachCandidate(examEventId, candidateId, locale);

		if (ResultType.SUCCESS == validationResult.getResultType()) {
			examService.detachCandidate(examEventId, candidateId);
		}

		ResponseResult responseResult = new ResponseResult(validationResult);
		return responseResult;
	}
	
	@RequestMapping(value = "/rest/examevents/assign_reviewer", method = RequestMethod.POST)
	public @ResponseBody
	ResponseResult assignReviewerRestHandler(Locale locale, @RequestParam("examEventId") Integer examEventId,
			@RequestParam("reviewerId") Integer reviewerId) {

		logger.debug("ExamController.assignReviewerRestHandler is invoked.");

		ValidationResult validationResult = examService.validateBeforeAssignReviewer(examEventId, reviewerId, locale);

		if (ResultType.SUCCESS == validationResult.getResultType()) {
			examService.assignReviewer(examEventId, reviewerId);
		}

		ResponseResult responseResult = new ResponseResult(validationResult);
		return responseResult;
	}
	
	@RequestMapping(value = "/rest/examevents/detach_reviewer", method = RequestMethod.POST)
	public @ResponseBody
	ResponseResult detachReviewerRestHandler(Locale locale, @RequestParam("examEventId") Integer examEventId,
			@RequestParam("reviewerId") Integer reviewerId) {

		logger.debug("ExamController.detachReviewerRestHandler is invoked.");

		ValidationResult validationResult = examService.validateBeforeDetachReviewer(examEventId, reviewerId, locale);

		if (ResultType.SUCCESS == validationResult.getResultType()) {
			examService.detachReviewer(examEventId, reviewerId);
		}

		ResponseResult responseResult = new ResponseResult(validationResult);
		return responseResult;
	}
	
	@RequestMapping(value = "/rest/reviewexams/auto_review_question_subject", method = RequestMethod.POST)
	public @ResponseBody
	ResponseResult autoReviewQuestionSubjectRestHandler(Locale locale,
			@RequestParam("examEventId") Integer examEventId,
			@RequestParam("questionSubjectId") Integer questionSubjectId, Principal principal) {

		logger.debug("ExamController.autoReviewQuestionSubjectRestHandler is invoked.");

		/* authorityService.findUserByUserId(principal.getName()); */
		User reviewer = authorityService.findUserById(1);

		QuestionSubject questionSubject = questionService.findQuestionSubjectById(questionSubjectId);
		QuestionType questionType = questionSubject.getQuestionType();

		ValidationResult validationResult = new ValidationResult(ResultType.SUCCESS);
		validationResult.setSuccessMessage(messageSource.getMessage("reviewexams.info.review_question_subject_success",
				new String[] { questionSubject.getContent().substring(0, 25) + "..." }, locale));

		for (QuestionHeader questionHeader : questionSubject.getQuestionHeaders()) {
			List<SubmitQuestionHeader> submitQuestionHeaders = examService
					.findSubmitQuestionHeadersByExamEventIdAndQuestionHeaderId(examEventId, questionHeader.getId());

			for (SubmitQuestionHeader submitQuestionHeader : submitQuestionHeaders) {
				if (QuestionUtil.reviewSubmittAnswer(questionType.getName(), questionHeader.getQuestionAnswer(),
						submitQuestionHeader.getSubmitQuestionAnswer())) {
					submitQuestionHeader.setObtainScore(questionHeader.getScore());
				} else {
					submitQuestionHeader.setObtainScore(0);
				}

				submitQuestionHeader.setReviewer(reviewer);
				submitQuestionHeader.setReviewDateTime(new Date());

				examService.persist(submitQuestionHeader);
			}
		}

		ResponseResult responseResult = new ResponseResult(validationResult);
		return responseResult;
	}
	
	@RequestMapping(value = "/rest/reviewexams/calculate_exam_event_scores", method = RequestMethod.POST)
	public @ResponseBody
	ResponseResult calculateExamEventScoresRestHandler(Locale locale, @RequestParam("examEventId") Integer examEventId) {

		logger.debug("ExamController.calculateExamEventScoresRestHandler is invoked.");

		ExamEvent examEvent = examService.findExamEventById(examEventId);

		ValidationResult validationResult = new ValidationResult(ResultType.SUCCESS);
		validationResult.setSuccessMessage(messageSource.getMessage(
				"reviewexams.info.calculate_exam_event_scores_success", new String[] { examEvent.getName() }, locale));

		for (ExamCandidate examCandidate : examEvent.getExamCandidates()) {
			int tempFinalScore = 0;

			List<SubmitQuestionHeader> submitQuestionHeaders = examService
					.findSubmitQuestionHeadersByExamEventIdAndUserId(examEventId, examCandidate.getUser().getId());

			for (SubmitQuestionHeader submitQuestionHeader : submitQuestionHeaders) {
				if (submitQuestionHeader.getObtainScore() != null) {
					tempFinalScore += submitQuestionHeader.getObtainScore();
				}
			}

			examCandidate.setFinalScore(tempFinalScore);
			examCandidate.setDoneFlag(true);

			examService.persist(examCandidate);
		}

		ResponseResult responseResult = new ResponseResult(validationResult);
		return responseResult;
	}

}
