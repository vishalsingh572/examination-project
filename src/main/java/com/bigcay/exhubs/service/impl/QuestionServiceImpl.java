package com.bigcay.exhubs.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bigcay.exhubs.common.GlobalManager;
import com.bigcay.exhubs.common.ResultType;
import com.bigcay.exhubs.common.ValidationResult;
import com.bigcay.exhubs.form.QuestionDetailBean;
import com.bigcay.exhubs.form.QuestionHeaderBean;
import com.bigcay.exhubs.form.QuestionSubjectFormBean;
import com.bigcay.exhubs.model.QuestionAnswer;
import com.bigcay.exhubs.model.QuestionDetail;
import com.bigcay.exhubs.model.QuestionHeader;
import com.bigcay.exhubs.model.QuestionSubject;
import com.bigcay.exhubs.model.QuestionTag;
import com.bigcay.exhubs.model.QuestionType;
import com.bigcay.exhubs.repository.QuestionAnswerRepository;
import com.bigcay.exhubs.repository.QuestionDetailRepository;
import com.bigcay.exhubs.repository.QuestionHeaderRepository;
import com.bigcay.exhubs.repository.QuestionSubjectRepository;
import com.bigcay.exhubs.repository.QuestionTagRepository;
import com.bigcay.exhubs.repository.QuestionTypeRepository;
import com.bigcay.exhubs.service.QuestionService;

@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	MessageSource messageSource;

	@Autowired
	private QuestionTypeRepository questionTypeRepository;

	@Autowired
	private QuestionSubjectRepository questionSubjectRepository;

	@Autowired
	private QuestionAnswerRepository questionAnswerRepository;

	@Autowired
	private QuestionHeaderRepository questionHeaderRepository;

	@Autowired
	private QuestionDetailRepository questionDetailRepository;
	
	@Autowired
	private QuestionTagRepository questionTagRepository;

	@Override
	public QuestionType findQuestionTypeById(Integer id) {
		return questionTypeRepository.findOne(id);
	}

	@Override
	public QuestionSubject findQuestionSubjectById(Integer id) {
		return questionSubjectRepository.findOne(id);
	}

	@Override
	public QuestionAnswer findQuestionAnswerById(Integer id) {
		return questionAnswerRepository.findOne(id);
	}

	@Override
	public QuestionHeader findQuestionHeaderById(Integer id) {
		return questionHeaderRepository.findOne(id);
	}

	@Override
	public QuestionDetail findQuestionDetailById(Integer id) {
		return questionDetailRepository.findOne(id);
	}

	@Override
	public List<QuestionType> findAllQuestionTypes() {
		return questionTypeRepository.findAll();
	}

	@Override
	public List<QuestionSubject> findAllQuestionSubjects() {
		return questionSubjectRepository.findAll();
	}

	@Override
	public QuestionSubject persist(QuestionSubject questionSubject) {
		return questionSubjectRepository.save(questionSubject);
	}

	@Override
	public void deleteQuestionSubject(Integer questionSubjectId) {
		questionSubjectRepository.delete(questionSubjectId);
	}

	@Override
	public ValidationResult validateBeforeDeleteQuestionSubject(Integer questionSubjectId, Locale locale) {
		// TO-DO

		ValidationResult result = new ValidationResult(ResultType.SUCCESS);
		return result;
	}

	@Override
	public ValidationResult validateBeforeCreateQuestionSubject(QuestionSubjectFormBean questionSubjectFormBean,
			Locale locale) {

		// let's suppose that there is no error at the beginning
		ValidationResult result = new ValidationResult(ResultType.SUCCESS);

		String questionTypeName = null;

		if (questionSubjectFormBean.getQuestionTypeId() == null || questionSubjectFormBean.getQuestionTypeId() == 0) {
			result.setResultType(ResultType.ERROR);
			result.addErrorMessage(messageSource
					.getMessage("questionrepos.error.questionType.IsRequired", null, locale));
			return result;
		} else {
			questionTypeName = this.findQuestionTypeById(questionSubjectFormBean.getQuestionTypeId()).getName();
		}

		if (questionSubjectFormBean.getContent() == null || questionSubjectFormBean.getContent().isEmpty()) {
			result.setResultType(ResultType.ERROR);
			result.addErrorMessage(messageSource.getMessage("questionrepos.error.content.NotEmpty", null, locale));
			return result;
		}

		if (questionSubjectFormBean.getTotalScore() == null || questionSubjectFormBean.getTotalScore().intValue() == 0) {
			result.setResultType(ResultType.ERROR);
			result.addErrorMessage(messageSource.getMessage("questionrepos.error.totalScore.NotEmpty", null, locale));
			return result;
		}

		List<QuestionHeaderBean> questionHeaderBeans = questionSubjectFormBean.getQuestionHeaderBeans();
		if (questionHeaderBeans == null || questionHeaderBeans.size() == 0) {
			result.setResultType(ResultType.ERROR);
			result.addErrorMessage(messageSource.getMessage("questionrepos.error.questionDescription.NotEmpty", null,
					locale));
			return result;
		} else {
			int questionHeaderScoreSum = 0;

			for (QuestionHeaderBean questionHeaderBean : questionHeaderBeans) {
				if (questionHeaderBean.getDescription() == null || questionHeaderBean.getDescription().isEmpty()) {
					result.setResultType(ResultType.ERROR);
					result.addErrorMessage(messageSource.getMessage("questionrepos.error.detailDescription.NotEmpty",
							null, locale));
					return result;
				}

				if (questionHeaderBean.getScore() == null || questionHeaderBean.getScore() == 0) {
					result.setResultType(ResultType.ERROR);
					result.addErrorMessage(messageSource.getMessage("questionrepos.error.score.NotEmpty", null, locale));
					return result;
				} else {
					questionHeaderScoreSum += questionHeaderBean.getScore();
				}

				List<QuestionDetailBean> questionDetailBeans = questionHeaderBean.getQuestionDetailBeans();

				if ("SCQ".equalsIgnoreCase(questionTypeName) || "MCQ".equalsIgnoreCase(questionTypeName)
						|| "TFQ".equalsIgnoreCase(questionTypeName)) {
					if (questionDetailBeans == null || questionDetailBeans.size() == 0) {
						result.setResultType(ResultType.ERROR);
						result.addErrorMessage(messageSource.getMessage("questionrepos.error.questionDetail.NotEmpty",
								null, locale));
						return result;
					} else {
						boolean chkboxSelectedFlag = false;

						for (QuestionDetailBean questionDetailBean : questionDetailBeans) {
							if (questionDetailBean.getContent() == null || questionDetailBean.getContent().isEmpty()) {
								result.setResultType(ResultType.ERROR);
								result.addErrorMessage(messageSource.getMessage(
										"questionrepos.error.questionDetailContent.NotEmpty", null, locale));
								return result;
							}

							if (questionDetailBean.getIsChecked() != null && questionDetailBean.getIsChecked()) {
								chkboxSelectedFlag = true;
							}
						}

						if ("SCQ".equalsIgnoreCase(questionTypeName)
								&& questionHeaderBean.getRadioSelectedIndex() == null) {
							result.setResultType(ResultType.ERROR);
							result.addErrorMessage(messageSource.getMessage(
									"questionrepos.error.rightAnswer.IsRequired", null, locale));
							return result;
						}

						if ("TFQ".equalsIgnoreCase(questionTypeName)
								&& questionHeaderBean.getRadioSelectedIndex() == null) {
							result.setResultType(ResultType.ERROR);
							result.addErrorMessage(messageSource.getMessage(
									"questionrepos.error.rightAnswer.IsRequired", null, locale));
							return result;
						}

						if ("MCQ".equalsIgnoreCase(questionTypeName) && !chkboxSelectedFlag) {
							result.setResultType(ResultType.ERROR);
							result.addErrorMessage(messageSource.getMessage(
									"questionrepos.error.rightAnswer.IsRequired", null, locale));
							return result;
						}

					}
				} else if ("BFQ".equalsIgnoreCase(questionTypeName) || "EQ".equalsIgnoreCase(questionTypeName)) {
					if (questionHeaderBean.getTextAnswer() == null || questionHeaderBean.getTextAnswer().isEmpty()) {
						result.setResultType(ResultType.ERROR);
						result.addErrorMessage(messageSource.getMessage("questionrepos.error.rightAnswer.IsRequired",
								null, locale));
						return result;
					}
				}
			}

			if (questionSubjectFormBean.getTotalScore().intValue() != questionHeaderScoreSum) {
				result.setResultType(ResultType.ERROR);
				result.addErrorMessage(messageSource.getMessage("questionrepos.error.totalScoreScoreSum.NotMatch",
						null, locale));
				return result;
			}
		}

		return result;
	}

	@Override
	public Set<QuestionTag> getAssociatedQuestionTags(String[] questionTagNameArray) {

		Set<QuestionTag> questionTags = new HashSet<QuestionTag>();

		for (String questionTagName : questionTagNameArray) {
			if (!questionTagName.trim().isEmpty()) {
				QuestionTag questionTag = questionTagRepository.findByName(questionTagName.trim());
				if (questionTag == null) {
					questionTag = new QuestionTag();
					questionTag.setName(questionTagName.trim());
				}
				questionTags.add(questionTag);
			}
		}

		return questionTags;
	}

	@Override
	public List<QuestionTag> findAllQuestionTags() {
		return questionTagRepository.findAll();
	}

	@Override
	public Page<QuestionSubject> findPageableQuestionSubjects(Integer pageNumber) {
		PageRequest pageRequest = new PageRequest(pageNumber, GlobalManager.DEFAULT_PAGE_SIZE, Sort.Direction.ASC, "id");
		return questionSubjectRepository.findAll(pageRequest);
	}

}
