package com.example.askel.recipes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button fridgeBut, shopBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fridgeBut = findViewById(R.id.fridgebut);
        shopBut = findViewById(R.id.shopbut);
        fridgeBut.setOnClickListener(this);
        shopBut.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.fridgebut: {
                Intent intent = new Intent(this, FridgeActivity.class);
                startActivityForResult(intent, 1001);
            }
                break;
            case R.id.shopbut: {
                Intent intent = new Intent(this, ShopActivity.class);
                startActivityForResult(intent, 1002);
            }
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(resultCode){
            case 1001: break;
            case 1002: break;
        }


    }
}
