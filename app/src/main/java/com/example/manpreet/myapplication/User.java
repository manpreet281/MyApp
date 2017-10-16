package com.example.manpreet.myapplication;

/**
 * Created by manpreet on 4/10/17.
 */

public class User {

    String name;



    public User(String name) {
        this.name = name;
    }

    public User() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}
