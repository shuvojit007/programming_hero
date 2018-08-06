
package com.example.test.programmingmama.Model.NewModel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Quiz_ extends RealmObject implements Parcelable {

    @SerializedName("solution")
    @Expose
    private String solution;
    @SerializedName("question")
    @Expose
    private String question;

    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("option")
    @Expose
    private RealmList<Option_> option = null;

    public Quiz_() {
    }

    public Quiz_(String solution, String question, String code, RealmList<Option_> option) {
        this.solution = solution;
        this.question = question;
        this.code = code;
        this.option = option;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public RealmList<Option_> getOption() {
        return option;
    }

    public void setOption(RealmList<Option_> option) {
        this.option = option;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.solution);
        dest.writeString(this.question);
        dest.writeString(this.code);
        dest.writeTypedList(this.option);
    }

    protected Quiz_(Parcel in) {
        this.solution = in.readString();
        this.question = in.readString();
        this.code = in.readString();
        this.option =new RealmList<>();
        this.option.addAll( in.createTypedArrayList(Option_.CREATOR));
    }

    public static final Creator<Quiz_> CREATOR = new Creator<Quiz_>() {
        @Override
        public Quiz_ createFromParcel(Parcel source) {
            return new Quiz_(source);
        }

        @Override
        public Quiz_[] newArray(int size) {
            return new Quiz_[size];
        }
    };
}
