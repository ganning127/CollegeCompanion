package com.example.collegecompanion;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.collegecompanion.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    public Button todoNavButton;
    public Button classesButton;

    public Button assignmentsButton;

    public Button examsButton;

    TextView classCount;
    TextView assignmentsCount;
    TextView examsCount;
    TextView todosCount;

    ArrayList<ClassItem> classItems;
    ArrayList<AssignmentItem> assignmentItems;
    ArrayList<ExamItem> examItems;
    HashMap<String, ArrayList<TodoListItem>> todosItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        classCount = findViewById(R.id.classCount);
        classItems = Data.getInstance();
        classCount.setText(Integer.toString(classItems.size()));

        examsCount = findViewById(R.id.examCount);
        examItems = ExamData.getInstance();
        examsCount.setText(Integer.toString(examItems.size()));

        assignmentsCount = findViewById(R.id.assignmentCount);
        assignmentItems = AssignmentsData.getInstance();
        assignmentsCount.setText(Integer.toString(assignmentItems.size()));

        todosCount = findViewById(R.id.todoCount);
        todosItems = TodoData.getInstance();
        int counter = 0;

        for (String key: todosItems.keySet()) {
            counter += todosItems.get(key).size();
        }

        todosCount.setText(Integer.toString(counter));



        examsButton = findViewById(R.id.examsNavButton);
        examsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Exam.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);
            }
        });


        assignmentsButton = findViewById(R.id.assignmentsNavButton);
        assignmentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Assignments.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);
            }
        });

        classesButton = findViewById(R.id.button_first);
        classesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Classes.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);
            }
        });



        todoNavButton = findViewById(R.id.todoButton);

        todoNavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TodolistCategories.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }


}