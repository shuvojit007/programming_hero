
package com.example.test.programmingmama.Model.NewModel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

public class NewHome  extends RealmObject implements Parcelable {
    @SerializedName("position")
    @Expose
    private int position = 0;

    @SerializedName("home")
    @Expose
    private RealmList<Home> home = null;

    public NewHome() {
    }

    public NewHome(RealmList<Home> home) {
        this.home = home;
    }

    public List<Home> getHome() {
        return home;
    }

    public void setHome(RealmList<Home> home) {
        this.home = home;
    }



    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.position);
        dest.writeTypedList(this.home);
    }

    protected NewHome(Parcel in) {
        this.position = in.readInt();
        this.home =new RealmList<>();
        this.home.addAll( in.createTypedArrayList(Home.CREATOR));
    }

    public static final Creator<NewHome> CREATOR = new Creator<NewHome>() {
        @Override
        public NewHome createFromParcel(Parcel source) {
            return new NewHome(source);
        }

        @Override
        public NewHome[] newArray(int size) {
            return new NewHome[size];
        }
    };
}
