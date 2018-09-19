package com.mkyong.model;

import org.springframework.stereotype.Component;

@Component
public class Image {
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
