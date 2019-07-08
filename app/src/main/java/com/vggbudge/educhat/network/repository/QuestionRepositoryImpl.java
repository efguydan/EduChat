package com.vggbudge.educhat.network.repository;

import com.vggbudge.educhat.network.service.QuestionService;

public class QuestionRepositoryImpl implements QuestionRepository {

    private QuestionService questionService;

    public QuestionRepositoryImpl(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public void getQuestionList(String amount, String category, String difficulty, String type, GetQuestionsCallback callback) {

    }

}
