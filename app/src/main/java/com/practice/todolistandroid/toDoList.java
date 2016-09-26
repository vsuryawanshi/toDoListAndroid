package com.practice.todolistandroid;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.view.View;
import android.content.Intent;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;


public class toDoList extends Activity {
    ArrayAdapter<String> adapter;
    ListView toDoList;
    ArrayList<String> toDoListItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        Firebase ref = new Firebase("https://vijayeta.firebaseio.com/");
        setContentView(R.layout.activity_to_do_list);
        toDoList = (ListView)findViewById(R.id.toDoListView);
        toDoListItems = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,toDoListItems);
        toDoList.setAdapter(adapter);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, String> map = (Map) dataSnapshot.getValue();
                if(adapter.getCount()>0) {
                    adapter.clear();
                }
                if (dataSnapshot.getChildrenCount()>0) {
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        adapter.add(entry.getValue());
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        setupListener();
        setupEditListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_to_do_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void addToDoListItem(View view) {
        EditText toDoListItem = (EditText)findViewById(R.id.toDoItem);
       // adapter.add(toDoListItem.getText().toString());
        Firebase myFirebaseRef = new Firebase("https://vijayeta.firebaseio.com/");
//        Firebase pushRef=myFirebaseRef.push();
        myFirebaseRef.child(toDoListItem.getText().toString()).setValue(toDoListItem.getText().toString());
        toDoListItem.setText("");
    }
    public void setupListener(){
        toDoList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Firebase ref = new Firebase("https://vijayeta.firebaseio.com/");
                ref.child(toDoListItems.get(position)).setValue(null);
               // toDoListItems.remove(position);
                adapter.notifyDataSetChanged();
                return true;
            }
        });
    }
    public void setupEditListener(){
        final Intent intent = new Intent(this, editItem.class);
        toDoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent.putExtra("value", adapter.getItem(position));
                startActivity(intent);
            }
        });
    }
}
