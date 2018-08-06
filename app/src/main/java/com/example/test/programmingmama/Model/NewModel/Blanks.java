
package com.example.test.programmingmama.Model.NewModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Blanks  extends RealmObject implements Parcelable {

    @SerializedName("v1")
    @Expose
    private Integer v1;
    @SerializedName("v2")
    @Expose
    private Integer v2;
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
    @SerializedName("op")
    @Expose
    private String op;

    public Blanks() {
    }

    public Blanks(Integer v1, Integer v2, String soln, String tf1, String tt1, String tt2, String tf2, String output, String op) {
        this.v1 = v1;
        this.v2 = v2;
        this.soln = soln;
        this.tf1 = tf1;
        this.tt1 = tt1;
        this.tt2 = tt2;
        this.tf2 = tf2;
        this.output = output;
        this.op = op;
    }

    public Integer getV1() {
        return v1;
    }

    public void setV1(Integer v1) {
        this.v1 = v1;
    }

    public Integer getV2() {
        return v2;
    }

    public void setV2(Integer v2) {
        this.v2 = v2;
    }

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

    public String getOp() {
        return op;
    }



    public void setOp(String op) {
        this.op = op;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.v1);
        dest.writeValue(this.v2);
        dest.writeString(this.soln);
        dest.writeString(this.tf1);
        dest.writeString(this.tt1);
        dest.writeString(this.tt2);
        dest.writeString(this.tf2);
        dest.writeString(this.output);
        dest.writeString(this.op);
    }



    protected Blanks(Parcel in) {
        this.v1 = (Integer) in.readValue(Integer.class.getClassLoader());
        this.v2 = (Integer) in.readValue(Integer.class.getClassLoader());
        this.soln = in.readString();
        this.tf1 = in.readString();
        this.tt1 = in.readString();
        this.tt2 = in.readString();
        this.tf2 = in.readString();
        this.output = in.readString();
        this.op = in.readString();
    }

    public static final Parcelable.Creator<Blanks> CREATOR = new Parcelable.Creator<Blanks>() {
        @Override
        public Blanks createFromParcel(Parcel source) {
            return new Blanks(source);
        }

        @Override
        public Blanks[] newArray(int size) {
            return new Blanks[size];
        }
    };
}
