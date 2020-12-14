package com.example.sharelockv2.Helperclasses;

public class Model {
    String desc, title, uID,username, imageUrl;//,type

    public Model(String desc, String title, String uID, String username, String imageUrl/*, String type*/){
        this.title=title;
        this.desc=desc;
        this.uID=uID;
        this.username=username;
        this.imageUrl = imageUrl;
       // this.type = type;

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
    public void setUsername(String username){
        this.username= username;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
   // public String setType(){return type;}
    //public void setType(String type){this.type= type;}
}
