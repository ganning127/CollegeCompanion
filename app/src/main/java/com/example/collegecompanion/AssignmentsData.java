package com.example.collegecompanion;

import java.util.ArrayList;

public class AssignmentsData {
    private static ArrayList<AssignmentItem> items;


    private AssignmentsData() {

    }

    public static ArrayList<AssignmentItem> getInstance() {
        if (items == null) {
            items = new ArrayList<>();
        }
        return items;
    }
}
