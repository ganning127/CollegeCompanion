package com.example.collegecompanion;

import java.util.ArrayList;

public class Data {
    private static ArrayList<ClassItem> items;


    private Data() {

    }

    public static ArrayList<ClassItem> getInstance() {
        if (items == null) {
            items = new ArrayList<>();
        }
        return items;
    }
}
