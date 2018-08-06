package com.example.test.programmingmama.Model.Challenge2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class CBlanks extends RealmObject {
    @SerializedName("soln")
    @Expose
    private String soln;
    @SerializedName("tf1")
    @Expose
    private String tf1;
    @SerializedName("tt1")
    @Expose
    private String tt1;
    @SerializedName("tt2")
    @Expose
    private String tt2;
    @SerializedName("tf2")
    @Expose
    private String tf2;
    @SerializedName("output")
    @Expose
    private String output;

    public String getSoln() {
        return soln;
    }

    public void setSoln(String soln) {
        this.soln = soln;
    }

    public String getTf1() {
        return tf1;
    }

    public void setTf1(String tf1) {
        this.tf1 = tf1;
    }

    public String getTt1() {
        return tt1;
    }

    public void setTt1(String tt1) {
        this.tt1 = tt1;
    }

    public String getTt2() {
        return tt2;
    }

    public void setTt2(String tt2) {
        this.tt2 = tt2;
    }

    public String getTf2() {
        return tf2;
    }

    public void setTf2(String tf2) {
        this.tf2 = tf2;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public CBlanks() {
    }

    public CBlanks(String soln, String tf1, String tt1, String tt2, String tf2, String output) {
        this.soln = soln;
        this.tf1 = tf1;
        this.tt1 = tt1;
        this.tt2 = tt2;
        this.tf2 = tf2;
        this.output = output;
    }

    @Override
    public String toString() {
        return "CBlanks{" +
                "soln='" + soln + '\'' +
                ", tf1='" + tf1 + '\'' +
                ", tt1='" + tt1 + '\'' +
                ", tt2='" + tt2 + '\'' +
                ", tf2='" + tf2 + '\'' +
                ", output='" + output + '\'' +
                '}';
    }
}
