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
import android.widget.AdapterView;
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

public class FridgeActivity extends Activity implements View.OnClickListener {

    private EditText addEt;
    private ListView listView;
    private DatabaseReference db;
    private ArrayList<String> foodList;
    private ArrayAdapter<String> arrAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge);
        getWindow().getDecorView().setBackgroundColor(Color.BLACK);

        addEt = findViewById(R.id.addfood_et);
        listView = findViewById(R.id.foodlist);
        Button addBut = findViewById(R.id.addfood_but);
        addBut.setOnClickListener(this);
        Button back = findViewById(R.id.fromfridge);
        back.setOnClickListener(this);

        db = FirebaseDatabase.getInstance().getReference();

        foodList = new ArrayList<>();
        arrAdapter = new ArrayAdapter<>(this, R.layout.item_color, R.id.list_content, foodList);
        listView.setAdapter(arrAdapter);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        db.child("FRIDGE").addChildEventListener(new OnFoodListener());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String key = foodList.get(i);
                foodList.remove(i);
                db.child("FRIDGE").child(key).setValue(null);
            }
        });
    }



    private class OnFoodListener implements ChildEventListener{

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            String retrievedFood = dataSnapshot.getKey();
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
            arrAdapter.notifyDataSetChanged();
            listView.setSelection(arrAdapter.getCount() - 1);
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

    private void addFood()
    {

        String food = addEt.getText().toString();
        db.child("FRIDGE").child(food).setValue(true);
        addEt.setText("");
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
}

