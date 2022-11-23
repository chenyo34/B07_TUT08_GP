package com.example.courseplanner;

import java.util.Objects;

public class User {
    private String name;
    private String UTORid;
    private String gender;

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getUTORid() {
        return UTORid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUTORid(String UTORid) {
        this.UTORid = UTORid;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public User(String name, String username, String UTORid) {
        this.name = name;
        this.UTORid = username;
        this.gender = UTORid;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", username='" + UTORid + '\'' +
                ", password='" + gender + '\'' +
                '}';
    }
}
