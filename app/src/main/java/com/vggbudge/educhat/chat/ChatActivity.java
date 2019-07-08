package com.vggbudge.educhat.chat;

import android.os.Bundle;

import com.vggbudge.educhat.R;
import com.vggbudge.educhat.base.BaseActivity;
import com.vggbudge.educhat.network.models.Question;
import com.vggbudge.educhat.utils.Provider;

import java.util.ArrayList;

public class ChatActivity extends BaseActivity<ChatContract.Presenter> implements ChatContract.View {

    private ChatContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        presenter = new ChatPresenter(this, Provider.provideQuestionRepository());
        //sample
        presenter.loadSampleData("10", "9", "easy", null);
    }

    @Override
    public void onQuestionsLoaded(ArrayList<Question> questionList) {
        showToast(questionList.get(0).questionStatement);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showLoading(int progressMessage) {

    }

    @Override
    public void showLoading(String progressMessage) {

    }

    @Override
    public void dismissLoading() {

    }
}
