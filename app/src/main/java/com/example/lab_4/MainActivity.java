package com.example.lab_4;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<TodoItem> list = new ArrayList<TodoItem>();
    private EditText editText;
    private Switch switchUrgent;
    private Button buttonAdd;
    private ListView listView;
    private ToDoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.edit_text);
        switchUrgent = findViewById(R.id.urgent_switch);
        buttonAdd = findViewById(R.id.add_button);
        listView = findViewById(R.id.list_view);

        adapter = new ToDoAdapter(this, list);
        listView.setAdapter(adapter);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TodoItem item = new TodoItem(editText.getText().toString(), switchUrgent.isChecked());
                list.add(item);
                editText.setText("");
                adapter.notifyDataSetChanged();
            }
        });


        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            final int itemPos = position;
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Do you want to delete this?");
            builder.setMessage("The selected row is: " + position);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    list.remove(itemPos);
                    adapter.notifyDataSetChanged();
                }
            });
            builder.setNegativeButton("No", null);
            builder.create().show();
            return true;
        });
    }
    public class TodoItem {
        private String text;
        private boolean isUrgent;

        public TodoItem(String text, boolean isUrgent) {
            this.text = text;
            this.isUrgent = isUrgent;
        }

        public String getText() {
            return text;
        }

        public boolean isUrgent() {
            return isUrgent;
        }
    }

    List<TodoItem> todoItems = new ArrayList<>();

    private class ToDoAdapter extends BaseAdapter {

        public ToDoAdapter(MainActivity mainActivity, List<TodoItem> list) {
        }
        

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public TodoItem getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View old, ViewGroup parent) {
            View newView = old;
            LayoutInflater inflater = getLayoutInflater();

            if (newView == null) {
                newView = inflater.inflate(R.layout.activity_main, parent, false);
            }

            TextView tView = newView.findViewById(R.id.list_view);
            tView.setText(getItem(position).getText());

            if (getItem(position).isUrgent()) {
                newView.setBackgroundColor(Color.RED);
                tView.setTextColor(Color.WHITE);
            }
            return newView;
        }

    }

}