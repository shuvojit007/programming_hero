
package com.example.test.programmingmama.Model.NewModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Option_ extends RealmObject implements Parcelable {

    @SerializedName("set")
    @Expose
    private String set;

    public Option_() {
    }

    public Option_(String set) {
        this.set = set;
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.set);
    }



    protected Option_(Parcel in) {
        this.set = in.readString();
    }

    public static final Parcelable.Creator<Option_> CREATOR = new Parcelable.Creator<Option_>() {
        @Override
        public Option_ createFromParcel(Parcel source) {
            return new Option_(source);
        }

        @Override
        public Option_[] newArray(int size) {
            return new Option_[size];
        }
    };
}
