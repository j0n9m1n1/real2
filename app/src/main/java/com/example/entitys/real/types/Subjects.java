package com.example.entitys.real.types;

import java.util.ArrayList;
import java.util.HashMap;

public class Subjects {
    public ArrayList<Reports> reportgroup;
    public String subjectname;// 과목명

    public Subjects(String name){
        subjectname = name;
        reportgroup = new ArrayList<Reports>();
    }
}