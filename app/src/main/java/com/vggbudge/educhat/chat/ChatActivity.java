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
import com.vggbudge.educhat.utils.Provider;

import java.util.ArrayList;

public class ChatActivity extends BaseActivity<ChatContract.Presenter> implements ChatContract.View {

    private ChatContract.Presenter presenter;
    private ChatView mChatView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //initialize views
        mChatView = findViewById(R.id.chat_view);

        int humanId = 0;
        Bitmap humanIcon = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_account);
        String humanName = "Dapo";

        int botId = 1;
        Bitmap botIcon = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_account);
        String botName = "EduChat";



        final ChatUser user = new ChatUser(humanId,humanName,humanIcon);
        final ChatUser chatBot = new ChatUser(botId,botName,botIcon);

        mChatView.setOnClickSendButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                        .setText(ChatBot.INSTANCE.talk(chatBot.getName(),message.getText()))
                        .build();

            }
        });

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
