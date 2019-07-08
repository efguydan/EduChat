package com.vggbudge.educhat.chat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.github.bassaer.chatmessageview.model.ChatUser;
import com.github.bassaer.chatmessageview.model.Message;
import com.github.bassaer.chatmessageview.util.ChatBot;
import com.github.bassaer.chatmessageview.view.ChatView;
import com.vggbudge.educhat.R;

import java.util.Random;

public class ChatActivity extends AppCompatActivity {
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



    }
}
