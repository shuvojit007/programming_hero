package com.example.test.programmingmama.Model.Challenge3;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Quiz3 extends RealmObject {

    @SerializedName("solution")
    @Expose
    private String solution;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("set1")
    @Expose
    private String set1;
    @SerializedName("set2")
    @Expose
    private String set2;
    @SerializedName("set3")
    @Expose
    private String set3;

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSet1() {
        return set1;
    }

    public void setSet1(String set1) {
        this.set1 = set1;
    }

    public String getSet2() {
        return set2;
    }

    public void setSet2(String set2) {
        this.set2 = set2;
    }

    public String getSet3() {
        return set3;
    }

    public void setSet3(String set3) {
        this.set3 = set3;
    }

    public Quiz3(String solution, String code, String set1, String set2, String set3) {
        this.solution = solution;
        this.code = code;
        this.set1 = set1;
        this.set2 = set2;
        this.set3 = set3;
    }

    public Quiz3() {
    }

    @Override
    public String toString() {
        return "CQuiz{" +
                "solution='" + solution + '\'' +
                ", code='" + code + '\'' +
                ", set1='" + set1 + '\'' +
                ", set2='" + set2 + '\'' +
                ", set3='" + set3 + '\'' +
                '}';
    }
}
