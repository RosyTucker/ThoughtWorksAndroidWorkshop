package com.example.rtucker.myapplication;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.List;

class TodoAdapter extends ArrayAdapter<Todo> {

    private final Storage storage;

    public TodoAdapter(Context context, Storage storage) {
        this(context, storage, storage.getAll());
    }

    private TodoAdapter(Context context, Storage storage, List<Todo> items) {
        super(context, R.layout.todo_list_item, items);
        this.storage = storage;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.todo_list_item, parent, false);
        }

        CheckBox todoCheckbox = (CheckBox) convertView.findViewById(R.id.todoCheckbox);

        final Todo todo = getItem(position);

        Button deleteButton = (Button) convertView.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(todo);
                storage.remove(position);
                notifyDataSetChanged();
            }
        });

        todoCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                buttonView.setPaintFlags(getPaintFlags(buttonView, isChecked));
            }
        });

        todoCheckbox.setText(todo.value);

        return convertView;
    }

    private int getPaintFlags(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            return buttonView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG;
        }
        return buttonView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG);
    }

    @Override
    public void add(Todo item) {
        super.add(item);
        storage.add(item);
    }

    @Override
    public void remove(Todo todo) {
        super.remove(todo);
        storage.remove(getPosition(todo));
    }
}