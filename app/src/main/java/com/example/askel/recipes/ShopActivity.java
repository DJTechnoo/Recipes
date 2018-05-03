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

public class ShopActivity extends AppCompatActivity implements View.OnClickListener {

    private Button back, addBut;
    private EditText addEt;
    private ListView shopListView;
    private ArrayAdapter<String> arrAdapter;
    private ArrayList<String> shopList;
    private DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        shopListView = findViewById(R.id.shoplist);
        shopList = new ArrayList<>();
        addEt = findViewById(R.id.additem_et);
        addBut = findViewById(R.id.additem_but);
        addBut.setOnClickListener(this);
        back = findViewById(R.id.fromshop);
        back.setOnClickListener(this);

        arrAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, shopList);
        shopListView.setAdapter(arrAdapter);
        db = FirebaseDatabase.getInstance().getReference();

        db.child("SHOP").addChildEventListener(new OnShopListener());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fromshop:
                Intent intent=new Intent();
                setResult(1002,intent);
                finish();//finishing activity
                break;
            case R.id.additem_but:
                addFood();
                break;
        }
    }


    private class OnShopListener implements ChildEventListener {

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            String retrievedItem = dataSnapshot.getValue(String.class);
            shopList.add(retrievedItem);
            Collections.sort(shopList, String.CASE_INSENSITIVE_ORDER);
            arrAdapter.notifyDataSetChanged();
            shopListView.setSelection(arrAdapter.getCount() - 1);
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

    public void addFood()
    {

        String food = addEt.getText().toString();
        db.child("SHOP").push().setValue(food);
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
