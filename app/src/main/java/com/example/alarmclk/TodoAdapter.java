package com.example.alarmclk;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

public class TodoAdapter extends ArrayAdapter<TodoItem> {

    public TodoAdapter(Context context, List<TodoItem> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final TodoItem item = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_todo, parent, false);
        }

        CheckBox checkBox = convertView.findViewById(R.id.checkBox);
        final TextView tvTitle = convertView.findViewById(R.id.tvTitle);
        final TextView tvDescription = convertView.findViewById(R.id.tvDescription);
        final TextView tvDueDate = convertView.findViewById(R.id.tvDueDate);
        Button btnDelete = convertView.findViewById(R.id.btnDelete);

        checkBox.setChecked(item.isCompleted());
        tvTitle.setText(item.getTitle());
        tvDescription.setText(item.getDescription());
        tvDueDate.setText(item.getDueDate());

        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> item.setCompleted(isChecked));

        tvTitle.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ItemDetailActivity.class);
            intent.putExtra("item", item);
            intent.putExtra("position", position);
            getContext().startActivity(intent);
        });

        btnDelete.setOnClickListener(v -> {
            remove(item);
            notifyDataSetChanged();
        });

        return convertView;
    }
}





