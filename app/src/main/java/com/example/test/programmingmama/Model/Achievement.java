package com.example.test.programmingmama.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Achievement extends RealmObject{
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("active")
    @Expose
    private String active;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("indication")
    @Expose
    private String indication;

    public Achievement() {
    }

    public Achievement(Integer id, String active, String icon, String name, String msg, String indication) {
        this.id = id;
        this.active = active;
        this.icon = icon;
        this.name = name;
        this.msg = msg;
        this.indication = indication;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getActive() {
        return active;
    }

    @Override
    public String toString() {
        return "Achievement{" +
                "id=" + id +
                ", active='" + active + '\'' +
                ", icon='" + icon + '\'' +
                ", name='" + name + '\'' +
                ", msg='" + msg + '\'' +
                ", indication='" + indication + '\'' +
                '}';
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getIndication() {
        return indication;
    }

    public void setIndication(String indication) {
        this.indication = indication;
    }

}
