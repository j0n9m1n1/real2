package com.example.entitys.real.types;

public class Pushs {

    public String title;
    public String item;

    public Pushs(String title, String item){
        this.title = title;
        this.item = item;
    }

    public String getTitle(){
        return title;
    }

    public String getItem(){
        return item;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setItem(String item){
        this.item = item;
    }
}
