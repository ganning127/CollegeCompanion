package com.example.collegecompanion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class TodoData {
    // private static ArrayList<TodoListItem> items;
    private static HashMap<String, ArrayList<TodoListItem>> categoryMapper;


    private TodoData() {

    }

    public static HashMap<String, ArrayList<TodoListItem>> getInstance() {
        if (categoryMapper == null) {
            categoryMapper = new LinkedHashMap<>();
        }
        return categoryMapper;
    }
}
