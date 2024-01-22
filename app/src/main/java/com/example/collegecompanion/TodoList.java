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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TodoList extends Activity {
    private static final String TAG = "todolist: ";

    private ArrayList<ClassItem> items;
    // private ArrayAdapter<ClassItem> itemsAdapter;
    BaseAdapter itemsAdapter;
    private ListView listView;

    private Button button;

    private Button backButton;

    EditText className;
    EditText profName;

    EditText daysTime;
    EditText location;

    private int modIndex; // index of modification

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todolist);

        listView = findViewById(R.id.listView);
        button = findViewById(R.id.addButton);
        backButton = findViewById(R.id.backButton);
        className = findViewById(R.id.classNameId);
        profName = findViewById(R.id.profNameId);
        daysTime = findViewById(R.id.daysTime);
        location = findViewById(R.id.location);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (button.getText().equals("Save")) {
                    button.setText("Add");

                    hideKeyboard(view);

                    ClassItem obj = items.get(modIndex);
                    obj.className = className.getText().toString();
                    obj.profName = profName.getText().toString();
                    obj.location = location.getText().toString();
                    obj.daysTime = daysTime.getText().toString();

                    className.setText("");
                    profName.setText("");
                    location.setText("");
                    daysTime.setText("");

                    items.set(modIndex, obj);
                    itemsAdapter.notifyDataSetChanged(); // refresh the list

                } else {
                    addItem(view);
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(TodoList.this, MainActivity.class);
                startActivity(intent);

            }
        });


        items = Data.getInstance();

        itemsAdapter = new ClassAdapter(TodoList.this, items);


        // itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        // setContentView(R.layout.action_bar_title_item)
        // setContentView(R.layout.activity_todolist);

        listView.setAdapter(itemsAdapter);
        setUpListViewListener();
    }

    private void setUpListViewListener() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Context context = getApplicationContext();
                Log.d("TODOLIST", "long click on " + i);
                className.setText(items.get(i).className);
                profName.setText(items.get(i).profName);
                location.setText(items.get(i).location);
                daysTime.setText(items.get(i).daysTime);

                modIndex = i;

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
        String classNameText = className.getText().toString();
        String profNameText = profName.getText().toString();
        String locationText = location.getText().toString();
        String daysTimeText = daysTime.getText().toString();


        System.out.println("classNameText " + classNameText.equals(""));
        System.out.println("profNameText " + profNameText.equals(""));
        System.out.println("locationText " + locationText.equals(""));
        System.out.println("daysTimeText " + daysTimeText.equals(""));

        if (!classNameText.equals("") && !profNameText.equals("") && !locationText.equals("") && !daysTimeText.equals("")) {
            System.out.println("ADDING...");
            items.add(new ClassItem(classNameText, profNameText, locationText, daysTimeText));
            itemsAdapter.notifyDataSetChanged();
            className.setText("");
            profName.setText("");
            location.setText("");
            daysTime.setText("");


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

class ClassItem {
    public String className;
    public String profName;

    public String daysTime;

    public String location;

    public ClassItem(String className, String profName, String location, String daysTime) {
        this.className = className;
        this.profName = profName;
        this.daysTime = daysTime;
        this.location = location;
    }
}

class ClassAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<ClassItem> items;
    public ClassAdapter(Context context, ArrayList<ClassItem> items) {
        mContext = context;
        this.items = items;
    }
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.listview_item, viewGroup, false);
        }

        ClassItem tempObj = (ClassItem) getItem(i);
        TextView tvClassName = (TextView) view.findViewById(R.id.class_name);
        TextView tvProfName = (TextView) view.findViewById(R.id.prof_name);
        TextView tvDaysTimeName = (TextView) view.findViewById(R.id.days_time_name);
        TextView tvLocationName = (TextView) view.findViewById(R.id.location_name);

        tvClassName.setText(tempObj.className);
        tvProfName.setText(tempObj.profName);
        tvDaysTimeName.setText(tempObj.daysTime);
        tvLocationName.setText(tempObj.location);


        return view;
    }
}