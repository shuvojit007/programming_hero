package com.example.test.programmingmama.Model;

import java.util.UUID;

import io.realm.RealmObject;

public class pc1Model extends RealmObject {
    String question;
    String op1,op2,op3;
    boolean answer= false ;
    String uid = UUID.randomUUID().toString();

    String status;
    int ans;

    public pc1Model() {
    }

    public boolean getAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }



    public pc1Model(String question, String op1, String op2, String op3, String status, int ans) {
        this.question = question;
        this.op1 = op1;
        this.op2 = op2;
        this.op3 = op3;

        this.status = status;
        this.ans = ans;
    }

    public boolean isAnswer() {
        return answer;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getAns() {
        return ans;
    }

    public void setAns(int ans) {
        this.ans = ans;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOp1() {
        return op1;
    }

    public void setOp1(String op1) {
        this.op1 = op1;
    }

    public String getOp2() {
        return op2;
    }

    public void setOp2(String op2) {
        this.op2 = op2;
    }

    public String getOp3() {
        return op3;
    }

    public void setOp3(String op3) {
        this.op3 = op3;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
