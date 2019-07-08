package com.vggbudge.educhat.chat;

import com.vggbudge.educhat.network.models.Question;
import com.vggbudge.educhat.network.repository.QuestionRepository;

import java.util.ArrayList;

public class ChatPresenter implements ChatContract.Presenter {

    private ChatContract.View view;
    private QuestionRepository questionRepository;

    public ChatPresenter(ChatContract.View view, QuestionRepository questionRepository) {
        this.view = view;
        this.questionRepository = questionRepository;
    }

    @Override
    public void start() {

    }

    @Override
    public void loadSampleData(String amount, String category, String difficulty, String questionType) {
        questionRepository.getQuestionList(amount, category, difficulty, questionType,
                new QuestionRepository.GetQuestionsCallback() {
            @Override
            public void onQuestionsLoaded(ArrayList<Question> questionList) {
                view.onQuestionsLoaded(questionList);
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
    }
}
