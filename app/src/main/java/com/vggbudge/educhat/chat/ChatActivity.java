package com.vggbudge.educhat.chat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import com.github.bassaer.chatmessageview.model.ChatUser;
import com.github.bassaer.chatmessageview.model.Message;
import com.github.bassaer.chatmessageview.util.ChatBot;
import com.github.bassaer.chatmessageview.view.ChatView;
import com.vggbudge.educhat.R;
import com.vggbudge.educhat.base.BaseActivity;
import com.vggbudge.educhat.network.models.Question;
import com.vggbudge.educhat.utils.Data;
import com.vggbudge.educhat.utils.Provider;

import java.util.ArrayList;

public class ChatActivity extends BaseActivity<ChatContract.Presenter> implements ChatContract.View {

    private ChatContract.Presenter presenter;
    private ChatView mChatView;
    private Question currentQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //initialize views
        mChatView = findViewById(R.id.chat_view);

        int humanId = 0;
        Bitmap humanIcon = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_account);
        String humanName = Data.userName;

        int botId = 1;
        Bitmap botIcon = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_account);
        String botName = Data.botName;



        final ChatUser user = new ChatUser(humanId,humanName,humanIcon);
        final ChatUser chatBot = new ChatUser(botId,botName,botIcon);

        mChatView.setOnClickSendButtonListener(v -> {

            Message message = new Message.Builder()
                    .setUser(user)
                    .setRight(true)
                    .setText(mChatView.getInputText())
                    .hideIcon(true)
                    .build();
            //Set to chat view
            mChatView.send(message);
            //Reset edit text
            mChatView.setInputText("");

            //Receive message
            final Message receivedMessage = new Message.Builder()
                    .setUser(chatBot)
                    .setRight(false)
                    .setText("A minute, I am still learning")
                    .build();
            mChatView.send(receivedMessage);

        });

        presenter = new ChatPresenter(this, Provider.provideQuestionRepository());
        //sample
        presenter.loadSampleData("20", "9", "easy", null);
    }

    @Override
    public void onQuestionsLoaded(ArrayList<Question> questionList) {
        startQuestionSequence(questionList);
    }

    private void startQuestionSequence(ArrayList<Question> questionList) {
        while(!questionList.isEmpty()) {
            interactWithUser(questionList.remove(0));
        }
        startEndSequence();
    }

    private void startEndSequence() {
        //TODO What happens after the question list has finished
    }

    private void interactWithUser(Question question) {

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
