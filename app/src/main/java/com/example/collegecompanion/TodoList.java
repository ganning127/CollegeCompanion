package com.example.collegecompanion;

import android.app.Activity;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TodoList extends Activity {
    private static final String TAG = "todolist: ";

    private HashMap<String, ArrayList<TodoListItem>> items;

    public ArrayList<TodoListItem> tasks;
    // private ArrayAdapter<ClassItem> itemsAdapter;
    BaseAdapter itemsAdapter;
    private ListView listView;

    private Button button;

    private Button backButton;

    private String filterKey;

    EditText itemName;

    TextView todoTitle;

    Button delButton;

    private int modIndex; // index of modification

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todolist);

        listView = findViewById(R.id.listView);
        button = findViewById(R.id.addButton);
        backButton = findViewById(R.id.backButton);
        itemName = findViewById(R.id.itemName);
        todoTitle = findViewById(R.id.todoTitle);
        delButton = findViewById(R.id.delButton);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            filterKey = extras.getString("filterKey");
            todoTitle.setText(filterKey);
            //The key argument here must match that used in the other activity
        }

        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items.remove(filterKey);
                Intent intent = new Intent(TodoList.this, TodolistCategories.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (button.getText().equals("Save")) {
                    button.setText("Add");

                    hideKeyboard(view);

                    TodoListItem obj = items.get(filterKey).get(modIndex);
                    obj.item = itemName.getText().toString();
                    obj.completed = false;

                    tasks.set(modIndex, obj);

                    itemName.setText("");

                    items.get(filterKey).set(modIndex, obj);
                    itemsAdapter.notifyDataSetChanged(); // refresh the list
                } else {
                    System.out.println("AAAAAAAAA");
                    addItem(view);
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(TodoList.this, TodolistCategories.class);
                startActivity(intent);

            }
        });


        items = TodoData.getInstance();

        tasks = new ArrayList<>(items.get(filterKey));
        itemsAdapter = new TodoListAdapter(TodoList.this, items, filterKey, tasks);


        // itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        // setContentView(R.layout.action_bar_title_item)
        // setContentView(R.layout.activity_todolist);

        listView.setAdapter(itemsAdapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Context context = getApplicationContext();
                modIndex = i;

                itemName.setText(tasks.get(i).item);

                button.setText("Save");

                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                System.out.println("ITEM REOMVE");
                Context context = getApplicationContext();
                Toast.makeText(context, "Item removed", Toast.LENGTH_LONG).show();
                items.get(filterKey).remove(i);
                tasks.remove(i); // index i was touched
                itemsAdapter.notifyDataSetChanged(); // refresh the list
            }
        });


        // setUpListViewListener();
    }

    // string -> arraylist<todoitem>()
    private void setUpListViewListener() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Context context = getApplicationContext();
                modIndex = i;
                System.out.println("ON CLICKED");

                itemName.setText(tasks.get(i).item);

                button.setText("Save");

                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Context context = getApplicationContext();
                Toast.makeText(context, "Item removed", Toast.LENGTH_LONG).show();
                items.remove(i); // index i was touched
                itemsAdapter.notifyDataSetChanged(); // refresh the list
            }
        });
    }

    private void addItem(View view) {
        String itemNameText = itemName.getText().toString();

        System.out.println("classNameText " + itemName.equals(""));

        if (!itemNameText.equals("")) {
            System.out.println("ADDING...");
            TodoListItem obj = new TodoListItem(itemNameText, false);
            items.get(filterKey).add(obj);
            tasks.add(obj);
            itemsAdapter.notifyDataSetChanged();
            itemName.setText("");


            hideKeyboard(view);
        } else {
            Toast.makeText(getApplicationContext(), "Task name cannot be empty", Toast.LENGTH_LONG).show();
        }
    }

    public void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void onStart() {
        super.onStart();
        Log.d(TAG, "in onStart()");
    }

    @Override
    public void onRestart() {
        super.onRestart();
        Log.d(TAG, "in onReStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "in onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "in onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "in onStop()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "in onDestroy()");
    }

}

class TodoListItem {
    public String item;
    public boolean completed;

    public TodoListItem(String item, boolean completed) {
        this.item = item;
        this.completed = completed;
    }

    public String toString() {
        return this.item + "=" + this.completed;
    }
}

class TodoListAdapter extends BaseAdapter {
    Context mContext;
    HashMap<String, ArrayList<TodoListItem>> items;

    ArrayList<TodoListItem> tasks;

    String filterKey;
    public TodoListAdapter(Context context, HashMap<String, ArrayList<TodoListItem>> items, String filterKey, ArrayList<TodoListItem> tasks) {
        mContext = context;
        this.items = items;
        this.tasks = tasks;
        this.filterKey = filterKey;
    }
    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Object getItem(int i) {
        return tasks.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.listview_todolist_item, viewGroup, false);
        }

        TodoListItem tempObj = (TodoListItem) getItem(i);
        TextView tvItemName = (TextView) view.findViewById(R.id.categoryName);
        CheckBox tvCompleted = (CheckBox) view.findViewById(R.id.completed);

        tvItemName.setText(tempObj.item);

        tvCompleted.setOnCheckedChangeListener(null);

        tvCompleted.setChecked(tempObj.completed);

        BaseAdapter ref = this;

        tvCompleted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                System.out.println("hit check");
                items.get(filterKey).get(i).completed = !items.get(filterKey).get(i).completed;
                ref.notifyDataSetChanged();
            }
        });

        return view;
    }
}