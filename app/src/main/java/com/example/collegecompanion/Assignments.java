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
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;

public class Assignments extends AppCompatActivity {
    private static final String TAG = "assignments: ";
    private ArrayList<AssignmentItem> items;
    BaseAdapter itemsAdapter;

    private ListView listView;

    private Button addButton;

    EditText assignmentNameInput;

    EditText dueDateInput;

    EditText withClassInput;

    RadioButton sortByClassRadio;
    RadioButton sortByDueDateRadio;

    private int modIndex;

    public Button backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignments);

        listView = findViewById(R.id.listView);
        addButton = findViewById(R.id.addButton);
        backButton = findViewById(R.id.backButton);
        assignmentNameInput = findViewById(R.id.assignmentNameInput);
        dueDateInput = findViewById(R.id.dueDateInput);
        withClassInput = findViewById(R.id.withClassInput);
        sortByClassRadio = findViewById(R.id.sortByClassRadio);
        sortByDueDateRadio = findViewById(R.id.sortByDueDateRadio);

        sortByClassRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(items, new Comparator<AssignmentItem>() {
                    @Override
                    public int compare(AssignmentItem o1, AssignmentItem o2) {
                        return o1.withClass.compareTo(o2.withClass);
                    }
                });

                itemsAdapter.notifyDataSetChanged();

            }
        });

        sortByDueDateRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(items, new Comparator<AssignmentItem>() {
                    @Override
                    public int compare(AssignmentItem o1, AssignmentItem o2) {
                        return o1.dueDate.compareTo(o2.dueDate);
                    }
                });

                itemsAdapter.notifyDataSetChanged();
            }
        });


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addButton.getText().equals("Save")) {
                    addButton.setText("Add");
                    hideKeyboard(view);

                    AssignmentItem obj = items.get(modIndex);
                    obj.assignmentName = assignmentNameInput.getText().toString();
                    obj.withClass = withClassInput.getText().toString();
                    obj.dueDate = Utils.StringToCalendar(dueDateInput.getText().toString());


//                    AssignmentItem obj = items.get(modIndex);
//                    obj.assignmentName = assignmentNameInput.getText().toString();
//
//                    itemName.setText("");
//
//                    items.set(modIndex, obj);
//                    itemsAdapter.notifyDataSetChanged(); // refresh the list

                } else {
                    addItem(view);
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Assignments.this, MainActivity.class);
                startActivity(intent);
            }
        });

        items = AssignmentsData.getInstance();

        itemsAdapter = new AssignmentAdapter(Assignments.this, items);

        listView.setAdapter(itemsAdapter);
        setUpListViewListener();


    }

    private void setUpListViewListener() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Context context = getApplicationContext();
                Log.d(TAG, "long click on " + i);

                assignmentNameInput.setText(items.get(i).assignmentName);
                dueDateInput.setText(Utils.CalendarToString(items.get(i).dueDate));
                withClassInput.setText(items.get(i).withClass);

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

        String assignmentNameText = assignmentNameInput.getText().toString();
        String dueDateText = dueDateInput.getText().toString();
        String withClassText = withClassInput.getText().toString();

        if (!assignmentNameText.equals("") && !dueDateText.equals("") && !withClassText.equals("")) {
            Calendar c;
            try {
                c = Utils.StringToCalendar(dueDateText);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Please enter valid date", Toast.LENGTH_LONG).show();
                return;
            }
            items.add(new AssignmentItem(assignmentNameText, c, withClassText));
            itemsAdapter.notifyDataSetChanged();

            hideKeyboard(view);
        } else {
            Toast.makeText(getApplicationContext(), "Assignment fields cannot be empty", Toast.LENGTH_LONG).show();
        }
    }



    public void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}

class AssignmentItem {
    public String assignmentName;
    public Calendar dueDate;

    public String withClass;

    public AssignmentItem(String a, Calendar d, String c) {
        this.assignmentName = a;
        this.dueDate = d;
        this.withClass = c;
    }
}

class AssignmentAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<AssignmentItem> items;
    public AssignmentAdapter(Context context, ArrayList<AssignmentItem> items) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.listview_assignment_item, viewGroup, false);
        }

        AssignmentItem tempObj = (AssignmentItem) getItem(i);
        TextView tvAssignmentName = (TextView) view.findViewById(R.id.assignmentName);
        TextView tvDueDate = (TextView) view.findViewById(R.id.dueDate);
        TextView tvWithClass = (TextView) view.findViewById(R.id.withClass);

        tvAssignmentName.setText(tempObj.assignmentName);
        tvDueDate.setText(Utils.CalendarToString(tempObj.dueDate));
        tvWithClass.setText(tempObj.withClass);

        return view;
    }
}

class Utils {
    public static String CalendarToString(Calendar c) {
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DATE);

        return month + "/" + day + "/" + year;
    }

    public static Calendar StringToCalendar(String s) {
        String[] parsedItemsInDate = s.split("/");

        Calendar c = GregorianCalendar.getInstance();
        c.set(Integer.parseInt(parsedItemsInDate[2]), Integer.parseInt(parsedItemsInDate[0]) - 1, Integer.parseInt(parsedItemsInDate[1]));

        return c;
    }
}
