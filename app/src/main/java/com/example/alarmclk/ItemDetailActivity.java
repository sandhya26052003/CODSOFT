package com.example.alarmclk;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ItemDetailActivity extends AppCompatActivity {

    private TodoItem item;
    private int position;
    private EditText etTitle, etDescription, etDueDate;
    private Button btnEdit, btnDelete;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        item = (TodoItem) getIntent().getSerializableExtra("item");
        position = getIntent().getIntExtra("position", -1);

        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        etDueDate = findViewById(R.id.etDueDate);
        checkBox = findViewById(R.id.checkBox);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);

        etTitle.setText(item.getTitle());
        etDescription.setText(item.getDescription());
        etDueDate.setText(item.getDueDate());
        checkBox.setChecked(item.isCompleted());

        btnEdit.setOnClickListener(v -> showEditItemDialog());
        btnDelete.setOnClickListener(v -> showDeleteConfirmationDialog());
    }

    private void showEditItemDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_add_item, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(dialogView);

        final EditText etTitle = dialogView.findViewById(R.id.etTitle);
        final EditText etDescription = dialogView.findViewById(R.id.etDescription);
        final EditText etDueDate = dialogView.findViewById(R.id.etDueDate);

        etTitle.setText(item.getTitle());
        etDescription.setText(item.getDescription());
        etDueDate.setText(item.getDueDate());

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String title = etTitle.getText().toString();
                        String description = etDescription.getText().toString();
                        String dueDate = etDueDate.getText().toString();
                        if (!title.isEmpty()) {
                            item.setTitle(title);
                            item.setDescription(description);
                            item.setDueDate(dueDate);
                            setResult(RESULT_OK, getIntent().putExtra("item", item));
                            finish();
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

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete this item?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        setResult(RESULT_OK, getIntent().putExtra("delete", true));
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.create().show();
    }
}
