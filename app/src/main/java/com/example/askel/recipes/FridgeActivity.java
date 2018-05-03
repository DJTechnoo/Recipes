package com.example.askel.recipes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;

public class FridgeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button back, addBut;
    private EditText addEt;
    private ListView listView;
    private DatabaseReference db;
    private ArrayList<String> foodList;
    private ArrayAdapter<String> arrAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge);

        addEt = findViewById(R.id.addfood_et);
        listView = findViewById(R.id.foodlist);
        addBut = findViewById(R.id.addfood_but);
        addBut.setOnClickListener(this);
        back = findViewById(R.id.fromfridge);
        back.setOnClickListener(this);

        db = FirebaseDatabase.getInstance().getReference();

        foodList = new ArrayList<>();
        arrAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, foodList);
        listView.setAdapter(arrAdapter);

        db.child("FRIDGE").addChildEventListener(new OnFoodListener());
    }



    private class OnFoodListener implements ChildEventListener{

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            String retrievedFood = dataSnapshot.getValue(String.class);
            foodList.add(retrievedFood);
            Collections.sort(foodList, String.CASE_INSENSITIVE_ORDER);
            arrAdapter.notifyDataSetChanged();
            listView.setSelection(arrAdapter.getCount() - 1);
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
            case R.id.fromfridge:
                Intent intent=new Intent();
                setResult(1001,intent);
                finish();//finishing activity
                break;
            case R.id.addfood_but:
                addFood();
                break;
        }
    }

    public void addFood()
    {

        String food = addEt.getText().toString();
        db.child("FRIDGE").push().setValue(food);
        addEt.setText("");
        hideKeyboard(this);
    }

    private static void hideKeyboard(Activity activity) {
        View view = activity.findViewById(android.R.id.content);
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}

