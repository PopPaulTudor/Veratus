package com.example.paul.myapp.model;

import android.media.Image;

/**
 * Created by poppa on 24.07.2017.
 */

public class Reward {

    private Integer image;
    private String text;
    private Integer target;
    public Reward(Integer image, String text,Integer target) {
        this.image = image;
        this.text = text;
        this.target=target;
    }

    public Integer getTarget() {
        return target;
    }

    public void setTarget(Integer target) {
        this.target = target;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
