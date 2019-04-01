package com.example.himani_k.greeting_card.Database;

public class Favourtites {
    int id;
    String image;

    public Favourtites(){   }

    public Favourtites(int id, String name){
        this.id = id;
        this.image = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}