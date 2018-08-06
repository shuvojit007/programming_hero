package com.example.test.programmingmama.Model;

public class Comment_Model {
    long time;
    String comment;
    int likes;

    String userId , cmntId ;

    public Comment_Model() {
    }

    public Comment_Model(long time, String comment, int likes, String userId, String cmntId) {
        this.time = time;
        this.comment = comment;
        this.likes = likes;
        this.userId = userId;
        this.cmntId = cmntId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCmntId() {
        return cmntId;
    }

    public void setCmntId(String cmntId) {
        this.cmntId = cmntId;
    }
}
