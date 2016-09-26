package com.practice.todolistandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.firebase.client.Firebase;

/**
 * Created by vsuryawanshi on 9/26/16.
 */
public class editItem extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edititem);
    }
    public void editItem(View v) {
        Intent intent = getIntent();
        String preEditValue = intent.getStringExtra("value");
        Firebase ref = new Firebase("https://vijayeta.firebaseio.com/");
        EditText edittedValue = (EditText)findViewById(R.id.toDoItem);
        ref.child(preEditValue).setValue(null);
        ref.child(edittedValue.getText().toString()).setValue(edittedValue.getText().toString());
        finish();
    }
}
