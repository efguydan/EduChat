package com.vggbudge.educhat.utils;

import java.util.ArrayList;
import java.util.HashMap;

public class Data {

    private Data() {
    }

    public static String botName = "EduChat";
    public static String userName = "You";

    public static String defaultAmountofQuestions = "20";

    public static HashMap<String, String> topicCategories;
    static {
        topicCategories.put("general knowledge", "9");
        topicCategories.put("science and nature", "17");
        topicCategories.put("computers", "18");
        topicCategories.put("mathematics", "19");
        topicCategories.put("geography", "22");
    }

    public static ArrayList<String> topicsList;
    static {
        topicsList.add("General Knowledge");
        topicsList.add("Science and Nature");
        topicsList.add("Computers");
        topicsList.add("Mathematics");
        topicsList.add("Geography");
    }

    public static HashMap<String, String> difficultyMatching;
    static {
        difficultyMatching.put("any difficulty", null);
        difficultyMatching.put("easy", "easy");
        difficultyMatching.put("medium", "medium");
        difficultyMatching.put("hard", "hard");
    }

    public static ArrayList<String> difficultyList;
    static {
        difficultyList.add("Any Difficulty");
        difficultyList.add("Easy");
        difficultyList.add("Medium");
        difficultyList.add("Hard");
    }

    public static HashMap<String, String> typeMatching;
    static {
        difficultyMatching.put("any type", null);
        difficultyMatching.put("multiple choice", "multiple");
        difficultyMatching.put("true or false", "boolean");
    }

    public static ArrayList<String> typeList;
    static {
        difficultyList.add("Any Type");
        difficultyList.add("Multiple Choice");
        difficultyList.add("True or False");
    }

    //Topics Section
    public static String getCategoryID(String topic) {
        return topicCategories.get(topic);
    }

    public static ArrayList<String> getTopicsList() {
        return topicsList;
    }

    public static String getTopicAtIndexOfList(int i) {
        return topicsList.get(i);
    }

    //Difficulty Section
    public static ArrayList<String> getDifficultyList() {
        return difficultyList;
    }

    public static String getDifficultyID(String difficulty) {
        return difficultyMatching.get(difficulty);
    }

    public static String getDifficultyAtIndexOfList(int index) {
        return difficultyList.get(index);
    }

    //Type Section
    public static ArrayList<String> getTypeList() {
        return typeList;
    }

    public static String getTypeID(String type) {
        return typeMatching.get(type);
    }

    public static String getTypeAtIndexOfList(int index) {
        return typeList.get(index);
    }
}
