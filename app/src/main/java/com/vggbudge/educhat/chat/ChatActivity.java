package com.vggbudge.educhat.chat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.vggbudge.educhat.R;
import com.vggbudge.educhat.base.BaseActivity;
import com.vggbudge.educhat.utils.Provider;

public class ChatActivity extends BaseActivity<ChatContract.Presenter> implements ChatContract.View {

    private ChatContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        presenter = new ChatPresenter(this, Provider.provideQuestionRepository());
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
