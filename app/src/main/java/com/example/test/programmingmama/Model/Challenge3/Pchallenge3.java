package com.example.test.programmingmama.Model.Challenge3;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Pchallenge3  extends RealmObject {
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("quiz3")
    @Expose
    private Quiz3 quiz;
    @SerializedName("blanks3")
    @Expose
    private Blanks3 blanks;

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

    public Quiz3 getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz3 quiz) {
        this.quiz = quiz;
    }

    public Blanks3 getBlanks() {
        return blanks;
    }

    public void setBlanks(Blanks3 blanks) {
        this.blanks = blanks;
    }

    public Pchallenge3(String question, String type, Quiz3 quiz, Blanks3 blanks) {
        this.question = question;
        this.type = type;
        this.quiz = quiz;
        this.blanks = blanks;
    }

    public Pchallenge3() {
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

