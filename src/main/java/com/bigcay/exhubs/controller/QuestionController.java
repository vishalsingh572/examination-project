package com.bigcay.exhubs.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bigcay.exhubs.common.GlobalManager;
import com.bigcay.exhubs.common.ResponseResult;
import com.bigcay.exhubs.common.ResultType;
import com.bigcay.exhubs.common.ValidationResult;
import com.bigcay.exhubs.form.QuestionDetailBean;
import com.bigcay.exhubs.form.QuestionHeaderBean;
import com.bigcay.exhubs.form.QuestionSubjectFormBean;
import com.bigcay.exhubs.model.Image;
import com.bigcay.exhubs.model.QuestionAnswer;
import com.bigcay.exhubs.model.QuestionDetail;
import com.bigcay.exhubs.model.QuestionHeader;
import com.bigcay.exhubs.model.QuestionSubject;
import com.bigcay.exhubs.model.QuestionTag;
import com.bigcay.exhubs.model.QuestionType;
import com.bigcay.exhubs.model.User;
import com.bigcay.exhubs.service.AuthorityService;
import com.bigcay.exhubs.service.ImageService;
import com.bigcay.exhubs.service.QuestionService;
import com.bigcay.exhubs.util.QuestionUtil;

@Controller
public class QuestionController extends BaseController {

	@Autowired
	MessageSource messageSource;

	@Autowired
	private AuthorityService authorityService;

	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private ImageService imageService;

	private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

	@ModelAttribute("questionChoices")
	public String[] getAllQuestionChoices() {
		return QuestionUtil.getQuestionChoices();
	}

	@ModelAttribute("tagNames")
	public String getAllTagNames() {
		// TO-DO
		
		List<QuestionTag> questionTags = questionService.findAllQuestionTags();

		Set<String> tagNameSet = new TreeSet<String>();

		for (QuestionTag questionTag : questionTags) {
			tagNameSet.add(questionTag.getName());
		}

		StringBuilder sb = new StringBuilder(100);
		sb.append("[");

		if (tagNameSet.size() > 0) {
			for (String tagName : tagNameSet) {
				sb.append("\"" + tagName + "\"" + ",");
			}

			sb.deleteCharAt(sb.length() - 1);
		}

		sb.append("]");

		return sb.toString();
	}

	@ModelAttribute("questionTypes")
	public List<QuestionType> getAllQuestionTypes() {
		return questionService.findAllQuestionTypes();
	}

	@RequestMapping("questiontypes")
	public String questionTypeIndexHandler() {

		logger.debug("QuestionController.questionTypeIndexHandler is invoked.");

		return "questiontypes/index";
	}

	@RequestMapping("ajax/questiontypes/show_question_types")
	public String showQuestionTypesAjaxHandler(Model model) {

		logger.debug("QuestionController.showQuestionTypesAjaxHandler is invoked.");

		List<QuestionType> questionTypes = questionService.findAllQuestionTypes();

		model.addAttribute("questionTypes", questionTypes);

		return "ajax/questiontypes/show_question_types";
	}

	@RequestMapping("questionrepos")
	public String questionRepoIndexHandler() {

		logger.debug("QuestionController.questionRepoIndexHandler is invoked.");

		return "questionrepos/index";
	}

	@RequestMapping("ajax/questionrepos/show_question_subjects")
	public String showQuestionSubjectsAjaxHandler(Model model, @RequestParam("pageNumber") Integer pageNumber) {

		logger.debug("QuestionController.showQuestionSubjectsAjaxHandler is invoked.");

		Page<QuestionSubject> questionSubjectPage = questionService.findPageableQuestionSubjects(pageNumber - 1);
		List<QuestionSubject> questionSubjects = questionSubjectPage.getContent();
		
		model.addAttribute("questionSubjects", questionSubjects);
		// add pagination attributes
		model.addAttribute("showRecordsJSFunc", "showQuestionSubjects");
		model.addAttribute("prefixFuncParams", "");
		model.addAllAttributes(GlobalManager.getGlobalPageableMap(questionSubjectPage));

		return "ajax/questionrepos/show_question_subjects";
	}

	@RequestMapping("question_subject/{questionSubjectId}")
	public String selectQuestionSubjectHandler(Model model, @PathVariable Integer questionSubjectId) {

		logger.debug("QuestionController.selectQuestionSubjectHandler is invoked.");

		QuestionSubject questionSubject = questionService.findQuestionSubjectById(questionSubjectId);

		model.addAttribute("questionSubject", questionSubject);

		return "questionrepos/select_question_subject";
	}

	@RequestMapping(value = "questionrepos/create_question", method = RequestMethod.GET)
	public String createQuestionHandler(Model model) {

		logger.info("QuestionController.createQuestionHandler is invoked.");

		List<QuestionHeaderBean> questionHeaderBeans = new ArrayList<QuestionHeaderBean>();
		QuestionSubjectFormBean questionSubjectFormBean = new QuestionSubjectFormBean();
		QuestionHeaderBean questionHeaderBean = new QuestionHeaderBean();
		questionHeaderBean.setQuestionDetailBeans(new ArrayList<QuestionDetailBean>());
		questionHeaderBeans.add(questionHeaderBean);
		questionSubjectFormBean.setQuestionHeaderBeans(questionHeaderBeans);

		model.addAttribute("questionSubjectFormBean", questionSubjectFormBean);

		return "questionrepos/create_question";
	}

	@RequestMapping(value = "questionrepos/create_question", method = RequestMethod.POST)
	public String createQuestionSubmitHandler(Model model, @RequestParam("file") MultipartFile multipartFile, Locale locale,
			@Valid @ModelAttribute("questionSubjectFormBean") QuestionSubjectFormBean questionSubjectFormBean,
			BindingResult result, final RedirectAttributes redirectAttributes, Principal principal) throws IOException {

		logger.info("QuestionController.createQuestionSubmitHandler is invoked.");

		if (result.hasErrors()) {

			logger.debug("ERROR! TO-DO");

			return "questionrepos/create_question";
		} else {
			/* authorityService.findUserByUserId(principal.getName()); */
			User editUser = authorityService.findUserById(1);

			QuestionType questionType = questionService.findQuestionTypeById(questionSubjectFormBean
					.getQuestionTypeId());

			QuestionSubject questionSubject = new QuestionSubject();
			questionSubject.setContent(questionSubjectFormBean.getContent());
			
			// persist Image to database and return imageId back
			if (!multipartFile.isEmpty()) {
				//Blob blob = Hibernate.createBlob(multipartFile.getInputStream());

				Image image = new Image();
				image.setName(multipartFile.getName());
				image.setContentType(multipartFile.getContentType());
				image.setLength((int) multipartFile.getSize());
				image.setContent(multipartFile.getBytes());
				image.setCreateDate(new Date());

				Image savedImage = imageService.persist(image);
				questionSubject.setImageId(savedImage.getId());
			}
			
			questionSubject.setQuestionType(questionType);
			questionSubject.setTotalScore(questionSubjectFormBean.getTotalScore());
			questionSubject.setUser(editUser);
			questionSubject.setQuestionTags(null);

			List<QuestionHeaderBean> questionHeaderBeans = questionSubjectFormBean.getQuestionHeaderBeans();
			Set<QuestionHeader> questionHeaders = new HashSet<QuestionHeader>();

			for (QuestionHeaderBean questionHeaderBean : questionHeaderBeans) {

				QuestionHeader questionHeader = new QuestionHeader();

				QuestionAnswer questionAnswer = new QuestionAnswer();

				List<QuestionDetailBean> questionDetailBeans = questionHeaderBean.getQuestionDetailBeans();

				Set<QuestionDetail> questionDetails = new HashSet<QuestionDetail>();

				int questionDetailIndex = 1;
				int checkboxBinaryNum = 0;
				for (QuestionDetailBean questionDetailBean : questionDetailBeans) {
					QuestionDetail questionDetail = new QuestionDetail();
					questionDetail.setContent(questionDetailBean.getContent());
					questionDetail.setSortOrder(questionDetailIndex);
					questionDetail.setQuestionHeader(questionHeader);
					questionDetails.add(questionDetail);

					if (questionDetailBean.getIsChecked() != null && questionDetailBean.getIsChecked().booleanValue()) {
						checkboxBinaryNum += Math.pow(2, questionDetailIndex - 1);
					}

					questionDetailIndex++;
				}

				if ("SCQ".equalsIgnoreCase(questionType.getName()) || "TFQ".equalsIgnoreCase(questionType.getName())) {
					int binaryValue = (int) Math.pow(2, (double) questionHeaderBean.getRadioSelectedIndex());
					questionAnswer.setBinaryValue(binaryValue);
				} else if ("MCQ".equalsIgnoreCase(questionType.getName())) {
					questionAnswer.setBinaryValue(checkboxBinaryNum);
				} else if ("BFQ".equalsIgnoreCase(questionType.getName())) {
					questionAnswer.setShortTextValue(questionHeaderBean.getTextAnswer());

					QuestionDetail questionDetail = new QuestionDetail();
					questionDetail.setContent("");
					questionDetail.setSortOrder(1);
					questionDetail.setQuestionHeader(questionHeader);
					questionDetails.add(questionDetail);
				} else if ("EQ".equalsIgnoreCase(questionType.getName())) {
					questionAnswer.setLongTextValue(questionHeaderBean.getTextAnswer());

					QuestionDetail questionDetail = new QuestionDetail();
					questionDetail.setContent("");
					questionDetail.setSortOrder(1);
					questionDetail.setQuestionHeader(questionHeader);
					questionDetails.add(questionDetail);
				}

				questionHeader.setQuestionDetails(questionDetails);
				questionHeader.setQuestionAnswer(questionAnswer);
				questionHeader.setQuestionSubject(questionSubject);
				questionHeader.setQuestionType(questionType);
				questionHeader.setDescription(questionHeaderBean.getDescription());
				questionHeader.setScore(questionHeaderBean.getScore());

				questionHeaders.add(questionHeader);

				questionSubject.setQuestionHeaders(questionHeaders);
			}

			// save here
			QuestionSubject savedQuestionSubject = questionService.persist(questionSubject);

			savedQuestionSubject.setQuestionTags(questionService.getAssociatedQuestionTags(questionSubjectFormBean
					.splitTagsToArray()));

			questionService.persist(savedQuestionSubject);

			redirectAttributes.addFlashAttribute("info",
					messageSource.getMessage("questionrepos.info.add_question_success", null, locale));
			return "redirect:/questionrepos";
		}
	}

	@RequestMapping("ajax/questionrepos/show_sub_question_area")
	public String showSubQuestionAreaAjaxHandler(Model model, @RequestParam(required = true) Integer questionTypeId) {

		logger.debug("QuestionController.showGroupRolesAjaxHandler is invoked.");

		QuestionType questionType = questionService.findQuestionTypeById(questionTypeId);

		if ("SCQ".equalsIgnoreCase(questionType.getName())) {
			return "ajax/questionrepos/show_SCQ_area";
		} else if ("MCQ".equalsIgnoreCase(questionType.getName())) {
			return "ajax/questionrepos/show_MCQ_area";
		} else if ("TFQ".equalsIgnoreCase(questionType.getName())) {
			return "ajax/questionrepos/show_TFQ_area";
		} else if ("BFQ".equalsIgnoreCase(questionType.getName())) {
			return "ajax/questionrepos/show_BFQ_area";
		} else if ("EQ".equalsIgnoreCase(questionType.getName())) {
			return "ajax/questionrepos/show_EQ_area";
		} else {
			// should not happen
			return "ajax/questionrepos/show_SCQ_area";
		}
	}

	@RequestMapping(value = "/rest/questionrepos/validate_create_question_subject", method = RequestMethod.POST)
	public @ResponseBody
	ResponseResult validateCreateQuestionSubjectRestHandler(Locale locale,
			QuestionSubjectFormBean questionSubjectFormBean) {

		logger.debug("QuestionController.validateCreateQuestionSubjectRestHandler is invoked.");

		ValidationResult validationResult = questionService.validateBeforeCreateQuestionSubject(
				questionSubjectFormBean, locale);

		ResponseResult responseResult = new ResponseResult(validationResult);
		return responseResult;
	}

	@RequestMapping(value = "/rest/questionrepos/delete_question_subject", method = RequestMethod.POST)
	public @ResponseBody
	ResponseResult deleteQuestionSubjectRestHandler(Locale locale, @RequestParam("deleteId") Integer deleteId) {

		logger.debug("QuestionController.deleteQuestionSubjectRestHandler is invoked.");

		ValidationResult validationResult = questionService.validateBeforeDeleteQuestionSubject(deleteId, locale);

		if (ResultType.SUCCESS == validationResult.getResultType()) {
			questionService.deleteQuestionSubject(deleteId);
		}

		ResponseResult responseResult = new ResponseResult(validationResult);
		return responseResult;
	}

}
