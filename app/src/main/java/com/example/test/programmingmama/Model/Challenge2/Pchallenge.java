package com.example.test.programmingmama.Model.Challenge2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Pchallenge  extends RealmObject{
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("cquiz")
    @Expose
    private CQuiz quiz;
    @SerializedName("cblanks")
    @Expose
    private CBlanks blanks;

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

    public CQuiz getQuiz() {
        return quiz;
    }

    public void setQuiz(CQuiz quiz) {
        this.quiz = quiz;
    }

    public CBlanks getBlanks() {
        return blanks;
    }

    public void setBlanks(CBlanks blanks) {
        this.blanks = blanks;
    }

    public Pchallenge(String question, String type, CQuiz quiz, CBlanks blanks) {
        this.question = question;
        this.type = type;
        this.quiz = quiz;
        this.blanks = blanks;
    }

    public Pchallenge() {
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
