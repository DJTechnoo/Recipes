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

public class ShopActivity extends Activity implements View.OnClickListener {

    private Button back, addBut;
    private EditText addEt;
    private ListView shopListView;
    private ArrayAdapter<String> arrAdapter;
    private ArrayList<String> shopList;
    private DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_shop);
        getWindow().getDecorView().setBackgroundColor(Color.BLACK);


        shopListView = findViewById(R.id.shoplist);
        shopList = new ArrayList<>();
        addEt = findViewById(R.id.additem_et);
        addBut = findViewById(R.id.additem_but);
        addBut.setOnClickListener(this);
        back = findViewById(R.id.fromshop);
        back.setOnClickListener(this);
        db = FirebaseDatabase.getInstance().getReference();
        arrAdapter = new ArrayAdapter<String>(this, R.layout.item_color, R.id.list_content, shopList);
        shopListView.setAdapter(arrAdapter);



        shopListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                /* TO DO */
                String key = shopList.get(i);
                shopList.remove(i);
                db.child("SHOP").child(key).setValue(null);
                db.child("FRIDGE").child(key).setValue(true);
            }
        });


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

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
            String retrievedItem = dataSnapshot.getKey().toString();
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
            arrAdapter.notifyDataSetChanged();
            shopListView.setSelection(arrAdapter.getCount() - 1);
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
        db.child("SHOP").child(food).setValue(true);
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
