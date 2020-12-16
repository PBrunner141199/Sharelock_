package com.example.sharelockv2.Helperclasses;

public class ProfileHelper {
    private String imageUrl;
    public ProfileHelper(){ }
    public ProfileHelper(String imageUrl){this.imageUrl = imageUrl;}
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
