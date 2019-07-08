package com.vggbudge.educhat.network.repository;

import com.vggbudge.educhat.network.model.Question;

import java.util.ArrayList;

public interface QuestionRepository {

    void getQuestionList(String amount, String category, String difficulty, String type, GetQuestionsCallback callback);

    interface GetQuestionsCallback {

        void onQuestionsLoaded(ArrayList<Question> questionList);

        void onFailure(Throwable throwable);
    }

}
