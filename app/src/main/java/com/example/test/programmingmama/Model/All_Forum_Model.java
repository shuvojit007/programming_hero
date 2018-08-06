package com.example.test.programmingmama.Model;

public class All_Forum_Model {
    long time;
    String comment;
    int likes,dislikes;

    String userId , frmId ;

    public All_Forum_Model(long time, String comment, int likes, int dislikes, String userId, String frmId) {
        this.time = time;
        this.comment = comment;
        this.likes = likes;
        this.dislikes = dislikes;
        this.userId = userId;
        this.frmId = frmId;
    }

    public All_Forum_Model() {
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFrmId() {
        return frmId;
    }

    public void setFrmId(String frmId) {
        this.frmId = frmId;
    }
}
