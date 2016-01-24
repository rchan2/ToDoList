package com.example.raymondchan1994.todolist;

import android.app.Activity;
import android.content.Context;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import stanford.androidlib.SimpleActivity;

public class MainActivity extends SimpleActivity {

    private static final String SAVE_STATE_TO_DO_LIST_ITEMS = "SAVE_STATE_TO_DO_LIST_ITEMS";

    private ArrayList<String> toDoItems;
    private ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        ListView toDoList = findListView(R.id.toDoList);
        if (savedInstanceState != null) {
            toDoItems = savedInstanceState.getStringArrayList(SAVE_STATE_TO_DO_LIST_ITEMS);
        } else {
            toDoItems = new ArrayList<>();
        }
        adapter = new ArrayAdapter<String>(this, R.layout.my_to_do_list_item_layout, R.id.the_item_text, toDoItems);
        toDoList.setAdapter(adapter);
        toDoList.setOnItemLongClickListener(this);
    }

    private void hideKeyboard() {
        EditText myEditText = findEditText(R.id.editText);
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(myEditText.getWindowToken(), 0);
    }

    @Override
    public boolean onItemLongClick(ListView list, int index) {
        super.onItemLongClick(list, index);
        toDoItems.remove(index);
        adapter.notifyDataSetChanged();
        return true;
    }


    public void addToDoListItem(View view) {
        EditText enteredText = findEditText(R.id.editText);
        String toDoItem = enteredText.getText().toString();
        toDoItems.add(toDoItem);

        // Clear the text field
        enteredText.setText("");

        adapter.notifyDataSetChanged();
        hideKeyboard();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(SAVE_STATE_TO_DO_LIST_ITEMS, toDoItems);
        log("Saved arraylist");
        log(toDoItems.toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        toDoItems = savedInstanceState.getStringArrayList(SAVE_STATE_TO_DO_LIST_ITEMS);
        adapter.notifyDataSetChanged();
    }
}