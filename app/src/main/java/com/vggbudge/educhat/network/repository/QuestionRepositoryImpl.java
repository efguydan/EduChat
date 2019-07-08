package com.vggbudge.educhat.network.repository;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.vggbudge.educhat.network.models.Question;
import com.vggbudge.educhat.network.service.QuestionService;
import com.vggbudge.educhat.utils.JsendResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionRepositoryImpl implements QuestionRepository {

    private QuestionService questionService;

    public QuestionRepositoryImpl(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public void getQuestionList(String amount, String category, String difficulty, String type, GetQuestionsCallback callback) {
        questionService.getQuestions(amount, category, difficulty, type).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                JsendResponse jsendResponse = new JsendResponse(response.body(), response.errorBody());
                ArrayList<Question> questions = new Gson().fromJson(jsendResponse.getResultsAsArray(),
                        new TypeToken<ArrayList<Question>>() { }.getType());
                callback.onQuestionsLoaded(questions);
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

}
