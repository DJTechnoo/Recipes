package com.example.askel.recipes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
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
import java.util.Collections;
import java.util.Comparator;

public class RecipeActivity extends Activity implements View.OnClickListener {

    private EditText et;
    private Button but_add, back;
    private DatabaseReference db;
    private ArrayList<String> recipeList;
    private ArrayAdapter<String> arrAdapter;
    private ListView listView;
    private ArrayList<Recipe> objList;
    private ArrayList<String> ownedItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_recipe);
        getWindow().getDecorView().setBackgroundColor(Color.BLACK);


        db = FirebaseDatabase.getInstance().getReference();
        et = findViewById(R.id.recipekey_et);
        but_add = findViewById(R.id.recipekey_but);
        back = findViewById(R.id.fromrecipe_but);
        listView = findViewById(R.id.recipe_lv);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        ownedItems = new ArrayList<>();
        recipeList = new ArrayList<>();
        objList = new ArrayList<>();
        arrAdapter = new ArrayAdapter<String>(this, R.layout.item_color, R.id.list_content, recipeList);
        listView.setAdapter(arrAdapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                String key;
                key = recipeList.get(i);
                db.child("RECIPES").child(key).setValue(null);
                objList.remove(i);
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent;
                String key;
                intent = new Intent(getApplicationContext(), RecipeItemsActivity.class);
                key = recipeList.get(i);
                intent.putExtra("KEY", key);
                startActivity(intent);
            }
        });


        but_add.setOnClickListener(this);
        back.setOnClickListener(this);
        db.child("FRIDGE").addChildEventListener(new OnFridgeListener());
        db.child("RECIPES").addChildEventListener(new OnRecipeListener());
        Log.v("TAG COUNT", "count= " + objList.size());

    }

    private class OnFridgeListener implements ChildEventListener{

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            String fridgeItem = dataSnapshot.getKey().toString();
            ownedItems.add(fridgeItem);
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

    private class OnRecipeListener implements ChildEventListener {

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            String key = dataSnapshot.getKey();
            //recipeList.add(key);


            // Loop through each "RECIPE" Obj to add it to the obj-list
            Log.v("TAG1", dataSnapshot.getKey().toString());
            Recipe tmp = new Recipe(key);
            for(DataSnapshot ds2 : dataSnapshot.getChildren()){
                Log.v("Tag2", ds2.getValue().toString());
                tmp.appendList(ds2.getValue().toString());
            }

            recipeList.clear();
            objList.add(tmp);

            /* ALGORITHM COMES HERE FOR SORTING BY OWNED ITEMS!
             1) update "priority"
             2) sort by "priority"
             3) place the keys back into listview in that order
            * */
            // 1)
            for(Recipe r : objList){
                for(String t : r.itemList){
                    for(String t2 : ownedItems) {
                        if (t.equals(t2)) r.priority++;
                    }
                }
            }

            // 2)
            Collections.sort(objList, new Comparator<Recipe>() {
                @Override
                public int compare(Recipe recipe, Recipe t1) {
                    return (Integer.valueOf(t1.priority).compareTo(recipe.priority));
                }
            });

            // 3)
            for(Recipe r : objList) {

                recipeList.add(r.key);
            }
            arrAdapter.notifyDataSetChanged();
            listView.setSelection(arrAdapter.getCount()-1);

        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            String key = dataSnapshot.getKey();
            //recipeList.add(key);
            recipeList.remove(key);
            arrAdapter.notifyDataSetChanged();
            listView.setSelection(arrAdapter.getCount()-1);


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
        switch(view.getId()){
            case R.id.recipekey_but:
                addRecipe(); break;

            case R.id.fromrecipe_but: {
                Intent intent = new Intent();
                setResult(1003, intent);
                finish();//finishing activity
            } break;
        }
    }

    public void addRecipe()
    {
        String recipeKey = et.getText().toString();
        if(!recipeKey.equals(""))
        db.child("RECIPES").child(recipeKey).setValue(true);
        et.setText("");
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
