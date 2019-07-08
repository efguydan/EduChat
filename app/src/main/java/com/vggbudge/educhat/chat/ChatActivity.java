package com.vggbudge.educhat.chat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.github.bassaer.chatmessageview.model.ChatUser;
import com.github.bassaer.chatmessageview.model.Message;
import com.github.bassaer.chatmessageview.view.ChatView;
import com.vggbudge.educhat.R;
import com.vggbudge.educhat.base.BaseActivity;
import com.vggbudge.educhat.network.models.Question;
import com.vggbudge.educhat.utils.Provider;
import com.vggbudge.educhat.utils.TextUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ChatActivity extends BaseActivity<ChatContract.Presenter> implements ChatContract.View {

    private ChatContract.Presenter presenter;
    private ChatView mChatView;
    private Question currentQuestion;
    private ChatUser user;
    private ChatUser budge;
    private int answerID;
    private ArrayList<Question> questionList;
    private String[] optionsABCDE = new String[] {"A", "B", "C", "D", "E"};
    int currentTrials = 0, totalGotten = 0;
    private String cat, dif;

    private String amountOfCourses, category, difficulty;

    private static HashMap<String, String> topicCategories;
    public static ArrayList<String> topicsList;
    public static HashMap<String, String> difficultyMatching;
    public static ArrayList<String> difficultyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        setUpDataFiles();

        //initialize views
        mChatView = findViewById(R.id.chat_view);
        mChatView.setRightBubbleColor(ContextCompat.getColor(this, R.color.human_bg));

        int humanId = 0;
        Bitmap humanIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_account);
        String humanName = "You";

        int botId = 1;
        Bitmap botIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.app_logo);
        String botName = "Budge";

        user = new ChatUser(humanId,humanName,humanIcon);
        budge = new ChatUser(botId,botName,botIcon);

        mChatView.setOnClickSendButtonListener(v -> {
            String answer = mChatView.getInputText();
            sendHumanMessage(answer);
            if (answer.equalsIgnoreCase("help")) {
                sendBotMessage("Press Help to get this message anytime. \n Press Reset to reset me :\\ \n Press exit to stop  anytime ");
                return;
            } else if (answer.equalsIgnoreCase("reset") || answer.equalsIgnoreCase("restart")) {
                sendBotMessage("Resetting...");
                answerID = 33;
                startBot();
                return;
            } else if (answer.equalsIgnoreCase("exit") || answer.equalsIgnoreCase("stop")) {
                sendBotMessage("Stopping Processes");
                exitScreen();
                return;
            }
            mChatView.setInputText("");
            switch (answerID) {
                case 1:
                    verifyAnswer(answer);
                    break;
                case 2:
                    int categor;
                    try {
                        categor = Integer.parseInt(answer);
                    } catch (NumberFormatException e) {
                        sendBotMessage("I asked for a number between 1 and 5, thank you ;)");
                        sendBotMessage(getCategoriesMessage());
                        e.printStackTrace();
                        break;
                    }
                    if (categor < 1 || categor > 5) {
                        sendBotMessage("I asked for a number between 1 and 5, thank you ;)");
                        sendBotMessage(getCategoriesMessage());
                        break;
                    }
                    getCategory(categor);
                    break;
                case 3:
                    int amoun;
                    try {
                        amoun = Integer.parseInt(answer);
                    } catch (NumberFormatException e) {
                        retryAmount();
                        e.printStackTrace();
                        break;
                    }
                    if (amoun < 10 || amoun > 50) {
                        retryAmount();
                        break;
                    }
                    amountOfCourses = String.valueOf(amoun);
                    answerID = 4;
                    sendBotMessage("The amount you selected is: " + amountOfCourses);
                    sendBotMessage(getDifficultyMessage());
                    break;
                case 4:
                    int diffic;
                    try {
                        diffic = Integer.parseInt(answer);
                    } catch (NumberFormatException e) {
                        sendBotMessage("I asked for a number between 1 and 4, thank you ;)");
                        sendBotMessage(getDifficultyMessage());
                        e.printStackTrace();
                        break;
                    }
                    if (diffic < 1 || diffic > 4) {
                        sendBotMessage("I asked for a number between 1 and 4, thank you ;)");
                        sendBotMessage(getDifficultyMessage());
                        break;
                    }
                    getDifficulty(diffic);
                    break;
                case 5:
                    if (answer.equalsIgnoreCase("start")) {
                        startLoadingQuestions();
                    } else {
                        sendBotMessage("Reply Start to begin ;)");
                    }
                    break;
                default:
                    sendBotMessage("I didn't get what you said");
                    break;
            }
        });
        presenter = new ChatPresenter(this, Provider.provideQuestionRepository());
        startBot();
    }

    private void exitScreen() {
        new Handler().postDelayed(() -> {
            finish();
        }, 1200);
    }

    private void startLoadingQuestions() {
        sendBotMessage("Loading Questions Online :) ");
        sendBotMessage("Please Wait");
        presenter.loadSampleData(amountOfCourses, category, difficulty, null);
    }

    private void retryAmount() {
        sendBotMessage("I asked for a number between 10 and 50, please try again");
    }

    private void getDifficulty(int diffic) {
        this.difficulty = difficultyMatching.get(difficultyList.get(diffic-1).toLowerCase());
        dif = difficultyList.get(diffic-1);
        sendBotMessage("Your selected difficulty is: " + dif);
        printoutDetails();
    }

    private void printoutDetails() {
        sendBotMessage(String.format("Category: %s \nAmount: %s\n Difficulty: %s", cat, amountOfCourses, dif));
        sendBotMessage("Reply \"Start\" to Continue");
        answerID = 5;
    }

    private String getDifficultyMessage() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Please choose the difficulty you want. Please reply with a number within 1 and 4\n\n");
        for (int i = 1; i <= difficultyList.size(); i++) {
            stringBuilder.append(i + ". " + difficultyList.get(i-1));
            stringBuilder.append("\n");
        }
        answerID = 4;
        return  stringBuilder.toString();
    }

    private void getCategory(int category) {
        this.category = topicCategories.get(topicsList.get(category-1).toLowerCase());
        cat = topicsList.get(category-1);
        sendBotMessage("Your selected Category is: " + cat);
        answerID = 3;
        sendBotMessage("How many Questions do you want to answer? Please give an answer between 10 and 50");
    }

    private void startBot() {
        sendBotMessage("Hello there, buddy. My name is Budge :)");
        sendBotMessage("Send Help to get the help screen anytime");
        sendBotMessage(getCategoriesMessage());
    }

    private String getCategoriesMessage() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("We have a couple of Categories we want you to choose from. Please reply with a number within 1 to 5\n\n");
        for (int i = 1; i <= topicsList.size(); i++) {
            stringBuilder.append(i + ". " + topicsList.get(i-1));
            stringBuilder.append("\n");
        }
        answerID = 2;
        return  stringBuilder.toString();
    }

    private void verifyAnswer(String answer) {
        if (answer.equalsIgnoreCase(currentQuestion.correctAnswer) ||
                answer.equalsIgnoreCase(optionsABCDE[currentQuestion.incorrectAnswers.indexOf(currentQuestion.correctAnswer)])) {
            sendBotMessage("Nice one, That was correct!");
            totalGotten++;
            startQuestionSequence();
        } else if (currentTrials == 1) {
            skipCurrentQuestion();
        } else {
            currentTrials++;
            sendBotMessage("That was wrong,Please try again");
            interactWithUser(currentQuestion);
        }
    }

    private void skipCurrentQuestion() {
        sendBotMessage("You have had 2 trials for the question and the question will be skipped.");
        String message = String.format("The correct answer is option %s: %s",
                optionsABCDE[currentQuestion.incorrectAnswers.indexOf(currentQuestion.correctAnswer)], currentQuestion.correctAnswer);
        sendBotMessage(message);
        startQuestionSequence();
    }

    @Override
    public void onQuestionsLoaded(ArrayList<Question> questionList) {
        this.questionList = questionList;
        if (questionList.isEmpty()) {
            sendBotMessage("Sorry, I was not able to find any question for your request :( Please try some other options");
            startBot();
            return;
        }
        sendBotMessage("Done. Let's start");
        startQuestionSequence();
    }

    private void startQuestionSequence() {
        if (questionList.isEmpty()) {
            startEndSequence();
            return;
        }
        currentQuestion = questionList.remove(0);
        currentQuestion.incorrectAnswers.add(currentQuestion.correctAnswer);
        Collections.shuffle(currentQuestion.incorrectAnswers);
        currentTrials = 0;
        interactWithUser(currentQuestion);
    }

    private void interactWithUser(Question question) {
        answerID = 1;
        currentQuestion = question;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(question.questionStatement);
        stringBuilder.append("\n");
        for (String wrongAnswer : question.incorrectAnswers) {
            stringBuilder.append("\n" + optionsABCDE[question.incorrectAnswers.indexOf(wrongAnswer)] + ". " + wrongAnswer);
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

    private void setUpDataFiles() {
        //Topic Categories
        topicCategories = new HashMap<>();
        topicCategories.put("general knowledge", "9");
        topicCategories.put("science and nature", "17");
        topicCategories.put("computers", "18");
        topicCategories.put("mathematics", "19");
        topicCategories.put("geography", "22");

        //Topic List
        topicsList = new ArrayList<>();
        topicsList.add("General Knowledge");
        topicsList.add("Science and Nature");
        topicsList.add("Computers");
        topicsList.add("Mathematics");
        topicsList.add("Geography");

        //Difficulty Matching
        difficultyMatching = new HashMap<>();
        difficultyMatching.put("any difficulty", null);
        difficultyMatching.put("easy", "easy");
        difficultyMatching.put("medium", "medium");
        difficultyMatching.put("hard", "hard");

        //Difficulty List
        difficultyList = new ArrayList<>();
        difficultyList.add("Any Difficulty");
        difficultyList.add("Easy");
        difficultyList.add("Medium");
        difficultyList.add("Hard");

    }

    private void startEndSequence() {
        //TODO What happens after the question list has finished
        sendBotMessage("You have finished your quiz. Your Score was");
        sendBotMessage(String.format("%d out of %d", totalGotten, amountOfCourses));
        sendBotMessage("Press Restart to start over or exit to quit");
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
