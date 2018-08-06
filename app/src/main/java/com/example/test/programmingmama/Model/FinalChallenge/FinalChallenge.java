package com.example.test.programmingmama.Model.FinalChallenge;

import com.example.test.programmingmama.Model.Challenge3.Blanks3;
import com.example.test.programmingmama.Model.Challenge3.Quiz3;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class FinalChallenge extends RealmObject {
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("fquiz")
    @Expose
    private FQuiz quiz;
    @SerializedName("fblanks")
    @Expose
    private FBlanks blanks;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public FQuiz getQuiz() {
        return quiz;
    }

    public void setQuiz(FQuiz quiz) {
        this.quiz = quiz;
    }

    public FBlanks getBlanks() {
        return blanks;
    }

    public void setBlanks(FBlanks blanks) {
        this.blanks = blanks;
    }

    public FinalChallenge(String question, String type, FQuiz quiz, FBlanks blanks) {
        this.question = question;
        this.type = type;
        this.quiz = quiz;
        this.blanks = blanks;
    }

    public FinalChallenge() {
    }

    @Override
    public String toString() {
        return "Pchallenge{" +
                "question='" + question + '\'' +
                ", type='" + type + '\'' +
                ", quiz=" + quiz +
                ", blanks=" + blanks +
                '}';
    }
}

