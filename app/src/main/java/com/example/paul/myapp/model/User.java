package com.example.paul.myapp.model;

import android.content.Intent;

public class User {

    private String username;
    private String name;
    private String nickname;
    private String pass;
    private String year;
    private Integer age;
    private String id;
    private Integer points;

    public User(String name, String nickname, String pass, String year, Integer age, String username, String id, Integer points) {
        this.username = username;
        this.name = name;
        this.nickname = nickname;
        this.pass = pass;
        this.year = year;
        this.age = age;
        this.id = id;
        this.points=points;
    }

    public User(String uusername) {
        username = uusername;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}
