package com.vggbudge.educhat.chat;

import com.vggbudge.educhat.base.BasePresenter;
import com.vggbudge.educhat.base.BaseView;
import com.vggbudge.educhat.network.models.Question;

import java.util.ArrayList;

public class ChatContract {

    interface View extends BaseView<Presenter> {

        void onQuestionsLoaded(ArrayList<Question> questionList);
    }

    interface Presenter extends BasePresenter {

        void loadSampleData(String amount, String category, String difficulty, String questionType);
    }

}
