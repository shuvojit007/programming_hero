
package com.example.test.programmingmama.Model.NewModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Home extends RealmObject implements Parcelable  {

    @SerializedName("total")
    @Expose
    private int total;

    @SerializedName("result")
    @Expose
    private int result;

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("count")
    @Expose
    private Integer count;

    @SerializedName("section_image")
    @Expose
    private String sectionImage;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("des")
    @Expose
    private RealmList<De> des = null;

    @SerializedName("list")
    @Expose
    private RealmList<List> list = null;

    public Home() {
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

    public Home(int total, int result, int id, String title, String type, Integer count, String sectionImage, String status, RealmList<De> des, RealmList<List> list) {
        this.total = total;
        this.result = result;
        this.id = id;
        this.title = title;
        this.type = type;
        this.count = count;
        this.sectionImage = sectionImage;
        this.status = status;
        this.des = des;
        this.list = list;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getSectionImage() {
        return sectionImage;
    }

    public void setSectionImage(String sectionImage) {
        this.sectionImage = sectionImage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public RealmList<De> getDes() {
        return des;
    }

    public void setDes(RealmList<De> des) {
        this.des = des;
    }

    public RealmList<List> getList() {
        return list;
    }

    public void setList(RealmList<List> list) {
        this.list = list;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.total);
        dest.writeInt(this.result);
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.type);
        dest.writeValue(this.count);
        dest.writeString(this.sectionImage);
        dest.writeString(this.status);
        dest.writeTypedList(this.des);
        dest.writeTypedList(this.list);
    }

    protected Home(Parcel in) {
        this.total = in.readInt();
        this.result = in.readInt();
        this.id = in.readInt();
        this.title = in.readString();
        this.type = in.readString();
        this.count = (Integer) in.readValue(Integer.class.getClassLoader());
        this.sectionImage = in.readString();
        this.status = in.readString();
        this.des =new RealmList<>();
        this.des.addAll( in.createTypedArrayList(De.CREATOR));
        this.list =new RealmList<>();
        list.addAll(in.createTypedArrayList(List.CREATOR));
    }

    public static final Creator<Home> CREATOR = new Creator<Home>() {
        @Override
        public Home createFromParcel(Parcel source) {
            return new Home(source);
        }

        @Override
        public Home[] newArray(int size) {
            return new Home[size];
        }
    };
}
