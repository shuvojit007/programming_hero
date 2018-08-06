
package com.example.test.programmingmama.Model.NewModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class List extends RealmObject implements Parcelable {
    @SerializedName("position")
    @Expose
    private int position = 0;


    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("status")
    @Expose
    private String status;


    @SerializedName("total")
    @Expose
    private int total;

    @SerializedName("result")
    @Expose
    private int result;

    @SerializedName("fmodule")
    @Expose
    private String fmodule;

    @SerializedName("mcount")
    @Expose
    private Integer mcount;
    @SerializedName("mtitle")
    @Expose
    private String mtitle;
    @SerializedName("mdes")
    @Expose
    private RealmList<Mde> mdes = null;

    public List() {
    }

    public List(int id, String status, int total, int result, String fmodule, Integer mcount, String mtitle, RealmList<Mde> mdes) {
        this.id = id;
        this.status = status;
        this.total = total;
        this.result = result;
        this.fmodule = fmodule;
        this.mcount = mcount;
        this.mtitle = mtitle;
        this.mdes = mdes;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFmodule() {
        return fmodule;
    }

    public void setFmodule(String fmodule) {
        this.fmodule = fmodule;
    }

    public Integer getMcount() {
        return mcount;
    }

    public void setMcount(Integer mcount) {
        this.mcount = mcount;
    }

    public String getMtitle() {
        return mtitle;
    }

    public void setMtitle(String mtitle) {
        this.mtitle = mtitle;
    }

    public RealmList<Mde> getMdes() {
        return mdes;
    }

    public void setMdes(RealmList<Mde> mdes) {
        this.mdes = mdes;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.position);
        dest.writeInt(this.id);
        dest.writeString(this.status);
        dest.writeInt(this.total);
        dest.writeInt(this.result);
        dest.writeString(this.fmodule);
        dest.writeValue(this.mcount);
        dest.writeString(this.mtitle);
        dest.writeTypedList(this.mdes);
    }

    protected List(Parcel in) {
        this.position = in.readInt();
        this.id = in.readInt();
        this.status = in.readString();
        this.total = in.readInt();
        this.result = in.readInt();
        this.fmodule = in.readString();
        this.mcount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.mtitle = in.readString();
        this.mdes =new RealmList<>();
        this.mdes.addAll(in.createTypedArrayList(Mde.CREATOR));
    }

    public static final Creator<List> CREATOR = new Creator<List>() {
        @Override
        public List createFromParcel(Parcel source) {
            return new List(source);
        }

        @Override
        public List[] newArray(int size) {
            return new List[size];
        }
    };
}
