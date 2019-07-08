package com.vggbudge.educhat.network.service;

import com.google.gson.JsonElement;

import org.jetbrains.annotations.Nullable;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface QuestionService {

    @GET("api.php")
    Call<JsonElement> getQuestions(@Query("amount") @Nullable String amount, @Query("category") @Nullable String category,
                                   @Query("difficulty") @Nullable String difficulty, @Query("type") @Nullable String type);
}
