package com.example.test.programmingmama.Model;

public class My_Comment_Model {
    String cmntId;
    int listid;
    int moduleid;
    String modulename;

    public My_Comment_Model() {
    }

    public My_Comment_Model(String cmntId, int listid, int moduleid, String modulename) {
        this.cmntId = cmntId;
        this.listid = listid;
        this.moduleid = moduleid;
        this.modulename = modulename;
    }

    public String getCmntId() {
        return cmntId;
    }

    public void setCmntId(String cmntId) {
        this.cmntId = cmntId;
    }

    public int getListid() {
        return listid;
    }

    public void setListid(int listid) {
        this.listid = listid;
    }

    public int getModuleid() {
        return moduleid;
    }

    public void setModuleid(int moduleid) {
        this.moduleid = moduleid;
    }

    public String getModulename() {
        return modulename;
    }

    public void setModulename(String modulename) {
        this.modulename = modulename;
    }
}
