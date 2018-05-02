package com.example.askel.recipes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FridgeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button back, addBut;
    private EditText addEt;
    private DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge);

        addEt = findViewById(R.id.addfood_et);
        addBut = findViewById(R.id.addfood_but);
        addBut.setOnClickListener(this);
        back = findViewById(R.id.fromfridge);
        back.setOnClickListener(this);

        db = FirebaseDatabase.getInstance().getReference();
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

