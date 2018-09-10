package com.bigcay.exhubs.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.bigcay.exhubs.common.ResponseResult;
import com.bigcay.exhubs.common.ValidationResult;
import com.bigcay.exhubs.form.QuestionDetailBean;
import com.bigcay.exhubs.form.QuestionHeaderBean;
import com.bigcay.exhubs.form.QuestionSubjectFormBean;
import com.bigcay.exhubs.form.UserFormBean;
import com.bigcay.exhubs.form.UserFormBeanValidator;
import com.bigcay.exhubs.model.Group;
import com.bigcay.exhubs.model.Image;
import com.bigcay.exhubs.model.QuestionAnswer;
import com.bigcay.exhubs.model.QuestionDetail;
import com.bigcay.exhubs.model.QuestionHeader;
import com.bigcay.exhubs.model.QuestionSubject;
import com.bigcay.exhubs.model.QuestionType;
import com.bigcay.exhubs.model.Role;
import com.bigcay.exhubs.model.User;
import com.bigcay.exhubs.service.AuthorityService;
import com.bigcay.exhubs.service.ExamService;
import com.bigcay.exhubs.service.ImageService;
import com.bigcay.exhubs.service.QuestionService;

@Controller
public class DemoController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(DemoController.class);

	@Autowired
	MessageSource messageSource;

	@Autowired
	private AuthorityService authorityService;

	@Autowired
	private QuestionService questionService;

	@Autowired
	private ExamService examService;

	@Autowired
	private ImageService imageService;

	@Autowired
	private UserFormBeanValidator userFormBeanValidator;

	@InitBinder("userFormBean")
	protected void initUserFormBeanBinder(WebDataBinder binder) {
		binder.setValidator(userFormBeanValidator);
	}

	@ModelAttribute("groups")
	public List<Group> getAllGroups() {
		return authorityService.findAllGroups();
	}

	@ModelAttribute("questionTypes")
	public List<QuestionType> getAllQuestionTypes() {
		return questionService.findAllQuestionTypes();
	}

	@RequestMapping(value = "demo", method = RequestMethod.GET)
	public String indexHandler(Model model) {

		/*
		ExamCandidate examCandidate = examService.findExamCandidateByExamEventIdAndUserId(1, 1);
		
		if (examCandidate != null) {
			System.out.println("examCandidate.submitDateTime: " + examCandidate.getSubmitDateTime());
		}
		
		examCandidate.setSubmitDateTime(new Date());
		examService.persist(examCandidate);
		*/

		return "demo/index";
	}

	@RequestMapping(value = "demo", method = RequestMethod.POST)
	public String indexSubmitHandler(Model model) {

		return "demo/index";
	}

	@RequestMapping(value = "demo/upload", method = RequestMethod.GET)
	public String uploadIndexHandler(Model model) {
		return "demo/upload";
	}

	@RequestMapping(value = "demo/upload", method = RequestMethod.POST)
	public String uploadSubmitHandler(Model model, @RequestParam("name") String name,
			@RequestParam("file") MultipartFile multipartFile) throws IOException {

		logger.warn("** demo name: " + name);

		if (!multipartFile.isEmpty()) {

			//Blob blob = Hibernate.createBlob(multipartFile.getInputStream());

			Image image = new Image();
			image.setName(multipartFile.getName());
			image.setContentType(multipartFile.getContentType());
			image.setLength((int) multipartFile.getSize());
			image.setContent(multipartFile.getBytes());
			image.setCreateDate(new Date());

			Image savedImage = imageService.persist(image);

			return "redirect:/image/" + savedImage.getId();
		} else {
			return "demo/upload";
		}
	}

	@RequestMapping(value = "demo/save_group", method = RequestMethod.GET)
	public String cascadeSaveGroupHandler(Model model) {

		Group newGroup = new Group();
		newGroup.setName("Group1-name");
		newGroup.setDescription("Group1-desc");

		Set<Role> newRoles = new HashSet<Role>();
		Role newRole = new Role();
		newRole.setName("Role1-name");
		newRole.setDescription("Role1-desc");
		newRoles.add(newRole);

		newGroup.setRoles(newRoles);

		Group savedGroup = authorityService.persist(newGroup);

		if (savedGroup != null) {
			logger.debug("** group was saved successfully!");
		} else {
			logger.debug("** failed to save group ...");
		}

		return "demo/index";
	}

	@RequestMapping(value = "demo/create_user", method = RequestMethod.GET)
	public String createUserHandler(Model model) {

		logger.info("DemoController.createUserHandler is invoked.");

		QuestionType questionType = questionService.findQuestionTypeById(1);
		logger.debug("-----------------------------------------");
		logger.debug(questionType.getDescription());

		QuestionSubject questionSubject = questionService.findQuestionSubjectById(1);
		logger.debug("-----------------------------------------");
		logger.debug("* content:" + questionSubject.getContent());
		logger.debug("** questionType:" + questionSubject.getQuestionType().getDescription());
		logger.debug("*** userId:" + questionSubject.getUser().getUserId());

		QuestionAnswer questionAnswer = questionService.findQuestionAnswerById(1);
		logger.debug("-----------------------------------------");
		logger.debug("* binary_value:" + questionAnswer.getBinaryValue());

		QuestionHeader questionHeader = questionService.findQuestionHeaderById(1);
		logger.debug("-----------------------------------------");
		logger.debug("* header.description:" + questionHeader.getDescription());
		logger.debug("* header.questionAnswer.binary_value:" + questionHeader.getQuestionAnswer().getBinaryValue());

		logger.debug("-----------------------------------------");
		for (QuestionDetail questionDetail : questionHeader.getQuestionDetails()) {
			logger.debug("detail:" + questionDetail.getSortOrder() + "," + questionDetail.getContent());
		}

		logger.debug("-----------------------------------------");
		for (QuestionHeader questionHeaderItem : questionSubject.getQuestionHeaders()) {
			logger.debug("*** questionHeaderItem: " + questionHeaderItem.getId() + ","
					+ questionHeaderItem.getDescription() + "," + questionHeaderItem.getScore());

			for (QuestionDetail questionDetail : questionHeaderItem.getQuestionDetails()) {
				logger.debug("**** questionDetail:" + questionDetail.getContent() + ", sort_order:"
						+ questionDetail.getSortOrder());
			}
		}

		model.addAttribute("userFormBean", new UserFormBean());

		return "demo/create_user";
	}

	@RequestMapping(value = "demo/create_user", method = RequestMethod.POST)
	public String createUserSubmitHandler(Model model, Locale locale,
			@Valid @ModelAttribute("userFormBean") UserFormBean userFormBean, BindingResult result,
			final RedirectAttributes redirectAttributes) {

		logger.debug("DemoController.createUserSubmitHandler is invoked.");

		if (result.hasErrors()) {
			return "demo/create_user";
		} else {
			redirectAttributes.addFlashAttribute("info", "success!");
			return "redirect:/";
		}
	}

	@RequestMapping(value = "demo/create_question", method = RequestMethod.GET)
	public String createQuestionHandler(Model model) {

		logger.info("DemoController.createQuestionHandler is invoked.");

		List<QuestionHeaderBean> questionHeaderBeans = new ArrayList<QuestionHeaderBean>();
		QuestionSubjectFormBean questionSubjectFormBean = new QuestionSubjectFormBean();
		QuestionHeaderBean questionHeaderBean = new QuestionHeaderBean();
		questionHeaderBean.setQuestionDetailBeans(new ArrayList<QuestionDetailBean>());
		questionHeaderBeans.add(questionHeaderBean);
		questionSubjectFormBean.setQuestionHeaderBeans(questionHeaderBeans);

		model.addAttribute("questionSubjectFormBean", questionSubjectFormBean);

		return "demo/create_question";
	}

	@RequestMapping(value = "demo/create_question", method = RequestMethod.POST)
	public String createQuestionSubmitHandler(Model model, Locale locale,
			@Valid @ModelAttribute("questionSubjectFormBean") QuestionSubjectFormBean questionSubjectFormBean,
			BindingResult result, final RedirectAttributes redirectAttributes, Principal principal) {
		if (result.hasErrors()) {

			logger.debug("ERROR! TO-DO");

			return "demo/create_question";
		} else {
			/* authorityService.findUserByUserId(principal.getName()); */
			User editUser = authorityService.findUserById(1);

			QuestionType questionType = questionService.findQuestionTypeById(questionSubjectFormBean
					.getQuestionTypeId());

			QuestionSubject questionSubject = new QuestionSubject();
			questionSubject.setContent(questionSubjectFormBean.getContent());
			questionSubject.setQuestionType(questionType);
			questionSubject.setTotalScore(questionSubjectFormBean.getTotalScore());
			questionSubject.setUser(editUser);

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
			questionService.persist(questionSubject);

			redirectAttributes.addFlashAttribute("info",
					messageSource.getMessage("questionrepos.info.add_question_success", null, locale));
			return "redirect:/questionrepos";
		}
	}

	@RequestMapping(value = "/rest/demo/validate_create_question_subject", method = RequestMethod.POST)
	public @ResponseBody
	ResponseResult validateCreateQuestionSubjectRestHandler(Locale locale,
			QuestionSubjectFormBean questionSubjectFormBean) {

		logger.debug("DemoController.validateCreateQuestionSubjectRestHandler is invoked.");

		ValidationResult validationResult = questionService.validateBeforeCreateQuestionSubject(
				questionSubjectFormBean, locale);

		ResponseResult responseResult = new ResponseResult(validationResult);
		return responseResult;
	}

	@RequestMapping("ajax/demo/show_sub_question_area")
	public String showSubQuestionAreaAjaxHandler(Model model, @RequestParam(required = true) Integer questionTypeId) {

		logger.debug("DemoController.showGroupRolesAjaxHandler is invoked.");

		QuestionType questionType = questionService.findQuestionTypeById(questionTypeId);

		if ("SCQ".equalsIgnoreCase(questionType.getName())) {
			return "ajax/demo/show_SCQ_area";
		} else if ("MCQ".equalsIgnoreCase(questionType.getName())) {
			return "ajax/demo/show_MCQ_area";
		} else if ("TFQ".equalsIgnoreCase(questionType.getName())) {
			return "ajax/demo/show_TFQ_area";
		} else if ("BFQ".equalsIgnoreCase(questionType.getName())) {
			return "ajax/demo/show_BFQ_area";
		} else if ("EQ".equalsIgnoreCase(questionType.getName())) {
			return "ajax/demo/show_EQ_area";
		} else {
			// should not happen
			return "ajax/demo/show_SCQ_area";
		}
	}

	@RequestMapping(value = "demo/export/groups", method = RequestMethod.GET)
	public View exportGroups2ExcelHandler(Model model) {

		View view = new AbstractExcelView() {

			@Override
			protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook,
					HttpServletRequest request, HttpServletResponse response) throws Exception {

				response.setHeader("Content-Disposition", "attachment; filename=\"groups.xls\"");
				HSSFSheet sheet = workbook.createSheet("groups");

				int rowNum = 0;
				int idx = 0;

				HSSFRow header = sheet.createRow(rowNum++);
				header.createCell(idx++).setCellValue("id");
				header.createCell(idx++).setCellValue("name");
				header.createCell(idx++).setCellValue("description");

				List<Group> groups = authorityService.findAllGroups();
				HSSFRow row;

				for (Group group : groups) {
					idx = 0;
					row = sheet.createRow(rowNum++);
					row.createCell(idx++).setCellValue(group.getId());
					row.createCell(idx++).setCellValue(group.getName());
					row.createCell(idx++).setCellValue(group.getDescription());
				}
			}
		};

		return view;
	}
}
