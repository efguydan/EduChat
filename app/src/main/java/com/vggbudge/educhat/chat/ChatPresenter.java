package com.vggbudge.educhat.chat;

import com.vggbudge.educhat.network.repository.QuestionRepository;

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

}
