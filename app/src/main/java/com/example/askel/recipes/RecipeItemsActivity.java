package com.example.askel.recipes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

    private EditText et;
    private DatabaseReference db;

    private ArrayList<String> buttonKeys;
    private ArrayList<String> itemList;
    private ArrayList<String> fridgeList;
    private ArrayAdapter<String> arrAdapter;
    private int b_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_recipe_items);
        getWindow().getDecorView().setBackgroundColor(Color.BLACK);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        b_id = 0;
        DatabaseReference db2;
        db = db2 = FirebaseDatabase.getInstance().getReference();
        TextView tv = findViewById(R.id.recipeitems_tv);
        et = findViewById(R.id.recipeitems_et);
        Button but = findViewById(R.id.recipeitems_but);
        ListView listView = findViewById(R.id.recipeitems_lv);
        itemList = new ArrayList<>();
        fridgeList = new ArrayList<>();
        buttonKeys = new ArrayList<>();
        arrAdapter = new ArrayAdapter<>(this, R.layout.item_color, R.id.list_content, itemList);
        listView.setAdapter(arrAdapter);

        Intent intent = getIntent();
        key = intent.getStringExtra("KEY");
        tv.setText("Add items for " + key);
        but.setOnClickListener(this);

        db2.child("FRIDGE").addChildEventListener(new OnFridgeListener());
       // db = db.child("RECIPES").child(key);
        db.child("RECIPES").child(key).addChildEventListener(new OnItemListener());

    }

    /**
     * This class simply collects data from the fridge.
     */
    private class OnFridgeListener implements ChildEventListener{

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            String fKey = dataSnapshot.getKey();
            fridgeList.add(fKey);
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

    /**
     * This class Implements ChildEventListener
     * It is used to listen to data being added and removed.
     * It also checks if button needs to be made, when the
     * item in the recipe doesn't exist in the fridge
     */
    private class OnItemListener implements ChildEventListener{

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            if(dataSnapshot != null) {
                String item = dataSnapshot.getValue().toString();
                itemList.add(item);
                arrAdapter.notifyDataSetChanged();

                boolean found = false;
                for (String tmp : fridgeList) {

                    if (item.equals(tmp)) {
                        found = true;
                    }

                }
                if (!found) {
                    createButton(item, b_id);
                    b_id++;
                }
            }
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

    /**
     * This Method adds an item to the RECIPE in FireBase
     */
    private void addItem() {
        String item = et.getText().toString();
        et.setText("");
        db.child("RECIPES").child(key).push().setValue(item);
        hideKeyboard(this);
    }

    private static void hideKeyboard(Activity activity) {
        View view = activity.findViewById(android.R.id.content);
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void createButton(String b_id, int idx){
        LinearLayout layout = findViewById(R.id.buttons_linear);

        buttonKeys.add(b_id);
        //set the properties for button
        Button btnTag = new Button(this);
        btnTag.setText(b_id);
        btnTag.setId(idx);
        btnTag.setOnClickListener(new View.OnClickListener()
                                  {
                                      @Override
                                      public void onClick(View v) {
                                          int i = v.getId();
                                          String button_key = buttonKeys.get(i);
                                          DatabaseReference tDb = FirebaseDatabase.getInstance().getReference();
                                          tDb.child("SHOP").child(button_key).setValue(true);
                                      }
                                  });

        //add button to the layout
        layout.addView(btnTag);
    }
}
