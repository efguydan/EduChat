package com.vggbudge.educhat.network.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Question implements Parcelable {

    @SerializedName("category")
    @Expose
    public String category;
    @SerializedName("type")
    @Expose
    public String typeOfQuestion;
    @SerializedName("difficulty")
    @Expose
    public String difficulty;
    @SerializedName("question")
    @Expose
    public String questionStatement;
    @SerializedName("correct_answer")
    @Expose
    public String correctAnswer;
    @SerializedName("incorrect_answers")
    @Expose
    public ArrayList<String> incorrectAnswers;

    protected Question(Parcel in) {
        category = in.readString();
        typeOfQuestion = in.readString();
        difficulty = in.readString();
        questionStatement = in.readString();
        correctAnswer = in.readString();
        incorrectAnswers = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(category);
        dest.writeString(typeOfQuestion);
        dest.writeString(difficulty);
        dest.writeString(questionStatement);
        dest.writeString(correctAnswer);
        dest.writeStringList(incorrectAnswers);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };
}
