package com.example.rtucker.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Storage {

    private static final String FILE_NAME = "TODOs";
    private static final String INDEX_PREFIX = "TODO_";
    private final SharedPreferences prefs;

    public Storage(Context context) {
        prefs = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    public List<Todo> getAll() {
        List<Todo> todos = new ArrayList<>();
        for(String item: (Collection<String>) prefs.getAll().values()) {
            todos.add(new Todo(item));
        }
        return todos;
    }

    public void remove(int position) {
        prefs.edit().remove(INDEX_PREFIX + position).apply();
        update();
    }

    public void add(Todo todo) {
        prefs.edit().putString(INDEX_PREFIX + prefs.getAll().size(), todo.value).apply();
        update();
    }

    private void update() {
        List<Todo> currentTodos = getAll();
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        for(int i = 0; i< currentTodos.size(); i++){
            editor.putString(INDEX_PREFIX + i, currentTodos.get(i).value);
        }
        editor.apply();
    }
}