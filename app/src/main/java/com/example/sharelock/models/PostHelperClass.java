package com.example.sharelock.models;

public class PostHelperClass {
    String desc, title, uID,username;

    public PostHelperClass(String desc, String title, String uID, String username){
        this.title=title;
        this.desc=desc;
        this.uID=uID;
        this.username=username;

    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title=title;
    }
    public String getDesc(){return desc;}
    public void setdesc(String desc){
        this.desc=desc;
    }
    public String getuID(){return  uID;}
    public void setuID(String uID){
        this.uID=uID;
    }
    public String  getUsername(){return  username;}
    public void setUsername(String key){
        this.username= username;
    }
}
