package com.example.collegecompanion;

import java.util.ArrayList;

public class ExamData {
    private static ArrayList<ExamItem> items;


    private ExamData() {

    }

    public static ArrayList<ExamItem> getInstance() {
        if (items == null) {
            items = new ArrayList<>();
        }
        return items;
    }
}
