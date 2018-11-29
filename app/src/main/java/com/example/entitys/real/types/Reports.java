package com.example.entitys.real.types;

import java.util.ArrayList;

public class Reports {
    public String reportname;
    public ArrayList<String> reportdetail;

    public Reports(String reportname){
        this.reportname = reportname;
        reportdetail = new ArrayList<String>();
    }

}
