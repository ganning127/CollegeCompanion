package com.example.collegecompanion;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Exam extends AppCompatActivity {
    private static final String TAG = "exams: ";
    private ArrayList<ExamItem> items;
    BaseAdapter itemsAdapter;

    private ListView listView;

    private Button addButton;

    EditText examNameInput;

    EditText examDateInput;

    EditText LocationInput;

    private int modIndex;

    public Button backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        listView = findViewById(R.id.listView);
        addButton = findViewById(R.id.addButton);
        backButton = findViewById(R.id.backButton);
        examNameInput = findViewById(R.id.examNameInput);
        examDateInput = findViewById(R.id.examDateInput);
        LocationInput = findViewById(R.id.LocationInput);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addButton.getText().equals("Save")) {
                    addButton.setText("Add");
                    hideKeyboard(view);

                    ExamItem obj = items.get(modIndex);
                    obj.examName = examNameInput.getText().toString();
                    obj.location = LocationInput.getText().toString();
                    obj.dueDate = examDateInput.getText().toString();
                } else {
                    addItem(view);
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Exam.this, MainActivity.class);
                startActivity(intent);
            }
        });

        items = ExamData.getInstance();

        itemsAdapter = new ExamAdapter(Exam.this, items);

        listView.setAdapter(itemsAdapter);
        setUpListViewListener();


    }

    private void setUpListViewListener() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Context context = getApplicationContext();
                Log.d(TAG, "long click on " + i);

                examNameInput.setText(items.get(i).examName);
                examDateInput.setText(items.get(i).dueDate);
                LocationInput.setText(items.get(i).location);

                modIndex = i;

                addButton.setText("Save");

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

        String examNameText = examNameInput.getText().toString();
        String dueDateText = examDateInput.getText().toString();
        String LocationText = LocationInput.getText().toString();

        if (!examNameText.equals("") && !dueDateText.equals("") && !LocationText.equals("")) {
            items.add(new ExamItem(examNameText, dueDateText, LocationText));
            itemsAdapter.notifyDataSetChanged();


            hideKeyboard(view);
        } else {
            Toast.makeText(getApplicationContext(), "Task name cannot be empty", Toast.LENGTH_LONG).show();
        }
    }



    public void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}

class ExamItem {
    public String examName;
    public String dueDate;

    public String location;

    public ExamItem(String a, String d, String c) {
        this.examName = a;
        this.dueDate = d;
        this.location = c;
    }
}

class ExamAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<ExamItem> items;
    public ExamAdapter(Context context, ArrayList<ExamItem> items) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.listview_exam_item, viewGroup, false);
        }

        ExamItem tempObj = (ExamItem) getItem(i);
        TextView tvexamName = (TextView) view.findViewById(R.id.examName);
        TextView tvDueDate = (TextView) view.findViewById(R.id.dueDate);
        TextView tvLocation = (TextView) view.findViewById(R.id.location);

        tvexamName.setText(tempObj.examName);
        tvDueDate.setText(tempObj.dueDate);
        tvLocation.setText(tempObj.location);

        return view;
    }
}