package com.example.sharelockv2.Helperclasses;

public class UserHelperClass {
    String username, email, password;



    public UserHelperClass(String email, String username, String password) {
        this.username = username;
        this.email = email;
        this.password = password;

    }

    public String getName() {
        return username;
    }

    public void setName(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
