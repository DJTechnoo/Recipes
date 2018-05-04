package com.example.askel.recipes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RecipeItemsActivity extends Activity implements View.OnClickListener {

    private String key;

    private Button but;
    private EditText et;
    private TextView tv;
    private DatabaseReference db;

    private ArrayList<String> itemList;
    private ArrayAdapter<String> arrAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_recipe_items);
        getWindow().getDecorView().setBackgroundColor(Color.BLACK);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        db = FirebaseDatabase.getInstance().getReference();
        tv = findViewById(R.id.recipeitems_tv);
        et = findViewById(R.id.recipeitems_et);
        but = findViewById(R.id.recipeitems_but);
        listView = findViewById(R.id.recipeitems_lv);
        itemList = new ArrayList<>();
        arrAdapter = new ArrayAdapter<String>(this, R.layout.item_color, R.id.list_content, itemList);
        listView.setAdapter(arrAdapter);

        Intent intent = getIntent();
        key = intent.getStringExtra("KEY");
        tv.setText("Add items for " + key);
        but.setOnClickListener(this);

        db = db.child("RECIPES").child(key);
        db.addChildEventListener(new OnItemListener());
    }

    private class OnItemListener implements ChildEventListener{

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            String item = dataSnapshot.getValue().toString();
            itemList.add(item);
            arrAdapter.notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.recipeitems_but:
                addItem();
                break;
        }
    }

    public void addItem() {
        String item = et.getText().toString();
        et.setText("");
        db.push().setValue(item);
    }

    private static void hideKeyboard(Activity activity) {
        View view = activity.findViewById(android.R.id.content);
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
