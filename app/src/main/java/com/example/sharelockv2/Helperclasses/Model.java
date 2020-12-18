package com.example.sharelockv2.Helperclasses;

public class Model {
    String desc, title, uID,username, imageUrl,date;
    int type;


    public Model(){}
    public Model(String desc, String title, String uID, String username, String imageUrl, int type, String date){
        this.title=title;
        this.desc=desc;
        this.uID=uID;
        this.username=username;
        this.imageUrl = imageUrl;
        this.type = type;
        this.date = date;


    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title=title;
    }
    public String getDesc(){return desc;}
    public void setDesc(String desc){
        this.desc=desc;
    }
    public String getUID(){return  uID;}
    public void setUID(String uID){
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
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public String getDate (){return date;}
    public void setDate (String date){this.date = date;}

}
