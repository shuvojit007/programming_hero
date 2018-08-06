package com.example.test.programmingmama.Model;

public class Reply_Model {
    private String reply;
    private long time;
    private String Uid;

    public Reply_Model(String reply, long time, String uid) {
        this.reply = reply;
        this.time = time;
        Uid = uid;
    }

    public Reply_Model() {
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }
}
