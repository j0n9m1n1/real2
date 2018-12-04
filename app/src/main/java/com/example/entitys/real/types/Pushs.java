package com.example.entitys.real.types;

public class Pushs {

    public String subject_title;
    public String report_title;
    public String item;

    public Pushs(String subject_title, String report_title, String item){
        this.subject_title = subject_title;
        this.report_title = report_title;
        this.item = item;
    }


    public String getSubject_title() {
        return subject_title;
    }

    public void setSubject_title(String subject_title) {
        this.subject_title = subject_title;
    }

    public String getReport_title() {
        return report_title;
    }

    public void setReport_title(String report_title) {
        this.report_title = report_title;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
}
