package com.example.hi.data;

public class UserFactor {
    private int id;
    private String uname;
    private String upwd;
    private String snumber;

    public UserFactor(int id, String uname, String upwd, String snumber) {
        this.id = id;
        this.uname = uname;
        this.upwd = upwd;
        this.snumber = snumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUpwd() {
        return upwd;
    }

    public void setUpwd(String upwd) {
        this.upwd = upwd;
    }

    public String getSnumber() {
        return snumber;
    }

    public void setSnumber(String snumber) {
        this.snumber = snumber;
    }
}
