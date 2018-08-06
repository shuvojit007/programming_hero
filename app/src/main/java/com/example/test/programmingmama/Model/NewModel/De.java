
package com.example.test.programmingmama.Model.NewModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class De extends RealmObject implements Parcelable {

    @SerializedName("bid")
    @Expose
    private int bid;

    @SerializedName("btype")
    @Expose
    private String btype;

    @SerializedName("ptxt")
    @Expose
    private String ptxt;


    @SerializedName("popup")
    @Expose
    private String popup;

    @SerializedName("bookmark")
    @Expose
    private String bookmark;


    @PrimaryKey
    private String id = UUID.randomUUID().toString();

    @SerializedName("mark")
    @Expose
    private int mark;
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("finish")
    @Expose
    private String finish;

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("des_01")
    @Expose
    private String des01;
    @SerializedName("link01")
    @Expose
    private String link01;
    @SerializedName("des_02")
    @Expose
    private String des02;
    @SerializedName("link02")
    @Expose
    private String link02;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("output")
    @Expose
    private String output;
    @SerializedName("des_03")
    @Expose
    private String des03;
    @SerializedName("qtype")
    @Expose
    private String qtype;
    @SerializedName("blankstype")
    @Expose
    private String blankstype;
    @SerializedName("blanks")
    @Expose
    private Blanks blanks;
    @SerializedName("quiz")
    @Expose
    private Quiz quiz;

    public De() {
    }

    public De(int bid, String btype, String ptxt, String popup, String bookmark, String id, int mark, String status, String finish, String name, String des01, String link01, String des02, String link02, String code, String output, String des03, String qtype, String blankstype, Blanks blanks, Quiz quiz) {
        this.bid = bid;
        this.btype = btype;
        this.ptxt = ptxt;
        this.popup = popup;
        this.bookmark = bookmark;
        this.id = id;
        this.mark = mark;
        this.status = status;
        this.finish = finish;
        this.name = name;
        this.des01 = des01;
        this.link01 = link01;
        this.des02 = des02;
        this.link02 = link02;
        this.code = code;
        this.output = output;
        this.des03 = des03;
        this.qtype = qtype;
        this.blankstype = blankstype;
        this.blanks = blanks;
        this.quiz = quiz;
    }

    public String getPtxt() {
        return ptxt;
    }

    public void setPtxt(String ptxt) {
        this.ptxt = ptxt;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public String getBtype() {
        return btype;
    }

    public void setBtype(String btype) {
        this.btype = btype;
    }

    public String getPopup() {
        return popup;
    }

    public void setPopup(String popup) {
        this.popup = popup;
    }

    public String getBookmark() {
        return bookmark;
    }

    public void setBookmark(String bookmark) {
        this.bookmark = bookmark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFinish() {
        return finish;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes01() {
        return des01;
    }

    public void setDes01(String des01) {
        this.des01 = des01;
    }

    public String getLink01() {
        return link01;
    }

    public void setLink01(String link01) {
        this.link01 = link01;
    }

    public String getDes02() {
        return des02;
    }

    public void setDes02(String des02) {
        this.des02 = des02;
    }

    public String getLink02() {
        return link02;
    }

    public void setLink02(String link02) {
        this.link02 = link02;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getDes03() {
        return des03;
    }

    public void setDes03(String des03) {
        this.des03 = des03;
    }

    public String getQtype() {
        return qtype;
    }

    public void setQtype(String qtype) {
        this.qtype = qtype;
    }

    public String getBlankstype() {
        return blankstype;
    }

    public void setBlankstype(String blankstype) {
        this.blankstype = blankstype;
    }

    public Blanks getBlanks() {
        return blanks;
    }

    public void setBlanks(Blanks blanks) {
        this.blanks = blanks;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.bid);
        dest.writeString(this.btype);
        dest.writeString(this.ptxt);
        dest.writeString(this.popup);
        dest.writeString(this.bookmark);
        dest.writeString(this.id);
        dest.writeInt(this.mark);
        dest.writeString(this.status);
        dest.writeString(this.finish);
        dest.writeString(this.name);
        dest.writeString(this.des01);
        dest.writeString(this.link01);
        dest.writeString(this.des02);
        dest.writeString(this.link02);
        dest.writeString(this.code);
        dest.writeString(this.output);
        dest.writeString(this.des03);
        dest.writeString(this.qtype);
        dest.writeString(this.blankstype);
        dest.writeParcelable(this.blanks, flags);
        dest.writeParcelable(this.quiz, flags);
    }

    protected De(Parcel in) {
        this.bid = in.readInt();
        this.btype = in.readString();
        this.ptxt = in.readString();
        this.popup = in.readString();
        this.bookmark = in.readString();
        this.id = in.readString();
        this.mark = in.readInt();
        this.status = in.readString();
        this.finish = in.readString();
        this.name = in.readString();
        this.des01 = in.readString();
        this.link01 = in.readString();
        this.des02 = in.readString();
        this.link02 = in.readString();
        this.code = in.readString();
        this.output = in.readString();
        this.des03 = in.readString();
        this.qtype = in.readString();
        this.blankstype = in.readString();
        this.blanks = in.readParcelable(Blanks.class.getClassLoader());
        this.quiz = in.readParcelable(Quiz.class.getClassLoader());
    }

    public static final Creator<De> CREATOR = new Creator<De>() {
        @Override
        public De createFromParcel(Parcel source) {
            return new De(source);
        }

        @Override
        public De[] newArray(int size) {
            return new De[size];
        }
    };
}
