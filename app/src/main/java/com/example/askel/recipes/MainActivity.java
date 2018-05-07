package com.example.askel.recipes;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setBackgroundColor(Color.BLACK);

        Button fridgeBut = findViewById(R.id.fridgebut);
        Button shopBut = findViewById(R.id.shopbut);
        Button recipeBut = findViewById(R.id.recipebut);
        fridgeBut.setOnClickListener(this);
        shopBut.setOnClickListener(this);
        recipeBut.setOnClickListener(this);
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
            case R.id.recipebut: {
                Intent intent = new Intent(this, RecipeActivity.class);
                startActivityForResult(intent, 1003);
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
            case 1003: break;
        }


    }
}
