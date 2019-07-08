package com.vggbudge.educhat.chat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.github.bassaer.chatmessageview.model.ChatUser;
import com.github.bassaer.chatmessageview.model.Message;
import com.github.bassaer.chatmessageview.view.ChatView;
import com.vggbudge.educhat.R;
import com.vggbudge.educhat.base.BaseActivity;
import com.vggbudge.educhat.network.models.Question;
import com.vggbudge.educhat.utils.Provider;

import java.util.ArrayList;
import java.util.Collections;

public class ChatActivity extends BaseActivity<ChatContract.Presenter> implements ChatContract.View {

    private ChatContract.Presenter presenter;
    private ChatView mChatView;
    private Question currentQuestion;
    private ChatUser user;
    private ChatUser budge;
    private boolean expectingAnswerToQuestion;
    private ArrayList<Question> questionList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //initialize views
        mChatView = findViewById(R.id.chat_view);

        int humanId = 0;
        Bitmap humanIcon = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_account);
        String humanName = "You";

        int botId = 1;
        Bitmap botIcon = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_account);
        String botName = "Budge";

        user = new ChatUser(humanId,humanName,humanIcon);
        budge = new ChatUser(botId,botName,botIcon);

        mChatView.setOnClickSendButtonListener(v -> {
            if (expectingAnswerToQuestion) {
                String answer = mChatView.getInputText();
                sendHumanMessage(answer);
                verifyAnswer(answer);
            } else {
                sendHumanMessage(mChatView.getInputText());
                sendBotMessage("I am still learning");
            }
            mChatView.setInputText("");
        });

        presenter = new ChatPresenter(this, Provider.provideQuestionRepository());
        //sample
        presenter.loadSampleData("20", "9", "easy", null);
    }

    private void verifyAnswer(String answer) {
        if (answer.equalsIgnoreCase(currentQuestion.correctAnswer)) {
            sendBotMessage("Nice one, That was correct!");
            startQuestionSequence();
        } else {
            sendBotMessage("That was wrong,Please try again");
            currentQuestion.incorrectAnswers.remove(currentQuestion.correctAnswer);
            interactWithUser(currentQuestion);
        }
    }

    @Override
    public void onQuestionsLoaded(ArrayList<Question> questionList) {
        this.questionList = questionList;
        startQuestionSequence();
    }

    private void startQuestionSequence() {
        interactWithUser(questionList.remove(0));
        if (questionList.isEmpty()) {
            startEndSequence();
        }
    }

    private void interactWithUser(Question question) {
        expectingAnswerToQuestion = true;
        currentQuestion = question;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(question.questionStatement);
        question.incorrectAnswers.add(question.correctAnswer);
        Collections.shuffle(question.incorrectAnswers);
        for (String wrongAnswer : question.incorrectAnswers) {
            stringBuilder.append("\n" + wrongAnswer);
        }
        sendBotMessage(stringBuilder.toString());
    }

    private void sendHumanMessage(String message) {
        final Message receivedMessage = new Message.Builder()
                .setUser(user)
                .setRight(true)
                .setText(message)
                .build();
        mChatView.send(receivedMessage);
    }

    private void sendBotMessage(String message) {
        final Message receivedMessage = new Message.Builder()
                .setUser(budge)
                .setRight(false)
                .setText(message)
                .build();
        mChatView.send(receivedMessage);
    }

    private void startEndSequence() {
        //TODO What happens after the question list has finished
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
