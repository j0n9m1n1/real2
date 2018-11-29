package com.example.entitys.real.fragment;

public class Notices {

    private String title;
    private String item;

    public Notices(String title, String item){
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
