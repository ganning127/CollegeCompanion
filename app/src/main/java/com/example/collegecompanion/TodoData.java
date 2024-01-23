package com.example.collegecompanion;

import java.util.ArrayList;

public class TodoData {
    private static ArrayList<TodoListItem> items;


    private TodoData() {

    }

    public static ArrayList<TodoListItem> getInstance() {
        if (items == null) {
            items = new ArrayList<>();
        }
        return items;
    }
}
