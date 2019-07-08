package com.vggbudge.educhat.utils;

import android.content.Context;

import com.vggbudge.educhat.network.repository.QuestionRepository;
import com.vggbudge.educhat.network.repository.QuestionRepositoryImpl;
import com.vggbudge.educhat.network.service.QuestionService;

import retrofit2.Retrofit;

/**
 * Convenience class to instantiate single instance of repositories to be used
 * throughout the app and to make it easy to swap concrete implementations
 * of repositories in case a local storage will be used
 */

public final class Provider {

    private static PrefsUtils prefsUtils;
    private static Retrofit retrofitInstance;

    private Provider() {
    }

    public static QuestionRepository provideQuestionRepository() {
        return new QuestionRepositoryImpl(provideRetrofitService(provideRetrofitInstance(), QuestionService.class));
    }

    public static PrefsUtils providePrefManager(Context context) {
        if (prefsUtils == null) {
            prefsUtils = new PrefsUtils(context.getApplicationContext());
        }
        return prefsUtils;
    }

    public static Retrofit provideRetrofitInstance() {
        if (retrofitInstance == null) {
            retrofitInstance = RetrofitClient.build();
        }
        return retrofitInstance;
    }

    public static <T> T provideRetrofitService(final Retrofit retrofit, Class<T> clazz) {
        return retrofit.create(clazz);
    }
}
