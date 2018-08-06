package com.example.test.programmingmama.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Explore_Info {
    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("image_url")
    @Expose
    private String imageUrl;

    @SerializedName("caption")
    @Expose
    private String caption;

    @SerializedName("link")
    @Expose
    private String link;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Explore_Info(String title, String imageUrl, String caption, String link) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.caption = caption;
        this.link = link;
    }

    public String getLink() {

        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "Explore_Info{" +
                "title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", caption='" + caption + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
