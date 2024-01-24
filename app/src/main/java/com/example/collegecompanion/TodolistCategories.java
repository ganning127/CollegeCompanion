package com.example.collegecompanion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collegecompanion.databinding.ActivityMainBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringJoiner;

public class TodolistCategories extends AppCompatActivity {
    private String TAG = "TodolistCategories";
    private HashMap<String, ArrayList<TodoListItem>> items;

    ArrayList<String> categoryNamesList;

    // private ArrayAdapter<ClassItem> itemsAdapter;
    BaseAdapter itemsAdapter;
    private ListView listView;

    private Button addButton;

    private Button backButton;

    private EditText categoryNameInput;

    EditText itemName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todolist_categories);

        listView = findViewById(R.id.listView);
        addButton = findViewById(R.id.addButton);
        categoryNameInput = findViewById(R.id.categoryNameInput);
        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TodolistCategories.this, MainActivity.class);
                startActivity(intent);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // add the category
                addCategory(view);
            }
        });

        items = TodoData.getInstance();

        categoryNamesList = new ArrayList<String>(items.keySet());

        itemsAdapter = new TodoListCategoriesAdapter(TodolistCategories.this, categoryNamesList);

        listView.setAdapter(itemsAdapter);
        setUpListViewListener();

    }

    private void setUpListViewListener() {
//        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Context context = getApplicationContext();
//                modIndex = i;
//
//                button.setText("Save");
//
//                return true;
//            }
//        });
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
//                Context context = getApplicationContext();
//                Toast.makeText(context, "Item removed", Toast.LENGTH_LONG).show();
//                items.remove(i); // index i was touched
//                itemsAdapter.notifyDataSetChanged(); // refresh the list
//            }
//        });
    }

    private void addCategory(View view) {
        String categoryName = categoryNameInput.getText().toString();
        if (!categoryName.equals("")) {
            // check that categoryName is not a key so far
            if (items.containsKey(categoryName)) {
                Log.d(TAG, "key already exists");

                Toast.makeText(getApplicationContext(), "Cannot have duplicate category names", Toast.LENGTH_LONG).show();
            } else {
                Log.d(TAG, "addCategory: " + categoryName );

                items.put(categoryName, new ArrayList<TodoListItem>());
                categoryNamesList.add(categoryName);
                itemsAdapter.notifyDataSetChanged();
                categoryNameInput.setText("");
            }
        } else {
            Log.d(TAG, "category name empty");
        }
        hideKeyboard(view);

    }

    public void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}

class TodoListCategoriesAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<String> categories;

    public TodoListCategoriesAdapter(Context context, ArrayList<String> categories) {
        mContext = context;
        this.categories = categories;
    }
    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int i) {
        return categories.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.listview_todolist_categories, viewGroup, false);
        }

        String text = (String) getItem(i);
        TextView tvCategoryName = (TextView) view.findViewById(R.id.categoryName);

        tvCategoryName.setText(text);

        return view;
    }
}