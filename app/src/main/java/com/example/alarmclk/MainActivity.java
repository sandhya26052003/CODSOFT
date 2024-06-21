package com.example.alarmclk;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<TodoItem> items;
    private TodoAdapter todoAdapter;
    private ListView lvItems;
    private Button btnAddItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems = findViewById(R.id.lvItems);
        btnAddItem = findViewById(R.id.btnAddItem);

        items = new ArrayList<>();
        todoAdapter = new TodoAdapter(this, items);
        lvItems.setAdapter(todoAdapter);

        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddEditItemDialog(null, -1);
            }
        });
    }

    private void showAddEditItemDialog(final TodoItem item, final int position) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_add_item, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(dialogView);

        final EditText etTitle = dialogView.findViewById(R.id.etTitle);
        final EditText etDescription = dialogView.findViewById(R.id.etDescription);
        final EditText etDueDate = dialogView.findViewById(R.id.etDueDate);

        if (item != null) {
            etTitle.setText(item.getTitle());
            etDescription.setText(item.getDescription());
            etDueDate.setText(item.getDueDate());
        }

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String title = etTitle.getText().toString();
                        String description = etDescription.getText().toString();
                        String dueDate = etDueDate.getText().toString();
                        if (!title.isEmpty()) {
                            if (item != null) {
                                item.setTitle(title);
                                item.setDescription(description);
                                item.setDueDate(dueDate);
                                todoAdapter.notifyDataSetChanged();
                            } else {
                                TodoItem newItem = new TodoItem(title, description, dueDate);
                                items.add(newItem);
                                todoAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
