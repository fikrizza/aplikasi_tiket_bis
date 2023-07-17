package com.example.proyek.object;

public class user {
    String name;
    String email;
    String password;


    public user(String nama, String email, String password) {
        this.name = nama;
        this.email = email;
        this.password = password;
    }


    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }

}
