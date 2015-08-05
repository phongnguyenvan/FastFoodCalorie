package com.app.vtn.fastfoodcalorie;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.List;


public class FoodDetail extends Activity {

    // more efficient than HashMap for mapping integers to objects
    SparseArray<Group> groups = new SparseArray<Group>();
    EditText find;
    FoodDatabase dbfood;
    Activity main;
    String restaurant;
    TextView showitem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        main=this;
        ExpandableListView listView = (ExpandableListView) findViewById(R.id.listView);
        MyExpandableListAdapter adapter = new MyExpandableListAdapter(this,
                groups);
        showitem=(TextView)findViewById(R.id.showitem);
        //find=(EditText)findViewById(R.id.find);
        restaurant=getIntent().getExtras().getString("RESTAURANT");
         dbfood=new FoodDatabase(getApplicationContext());


        //find.setText(restaurant);
        listView.setAdapter(adapter);
        createData();
        find=(EditText)findViewById(R.id.find);
        find.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                groups = new SparseArray<Group>();
                ExpandableListView listView = (ExpandableListView) findViewById(R.id.listView);
                MyExpandableListAdapter adapter = new MyExpandableListAdapter(main,
                        groups);
                listView.setAdapter(adapter);
                Group group1 = new Group("Burgers & Sandwiches");
                Group group2 = new Group("Chicken & Fish");
                List<Food> ress=dbfood.getAllFoodByRestaurant(restaurant);
                Log.i("get name",restaurant);
                for(Food res:ress)
                {
                    if(res.getFoodName().toLowerCase().contains(s)) {
                        if (res.food_group.equals(group1.string)) {
                            group1.children.add(res.food_name + "\n" +
                                    "Calories:" + res.Calories + ", Protein:" + res.Protein);
                        } else {
                            group2.children.add(res.food_name + "\n" +
                                    "Calories:" + res.Calories + ", Protein:" + res.Protein);
                        }
                    }
                }


                groups.append(0,group1);
                groups.append(1,group2);
                showitem.setText((group1.children.size()+group2.children.size())+" items");

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void createData() {


        Group group1 = new Group("Burgers & Sandwiches");
        Group group2 = new Group("Chicken & Fish");
        List<Food> ress=dbfood.getAllFoodByRestaurant(restaurant);
        Log.i("get name",restaurant);
        for(Food res:ress)
        {
            Log.i("Ket qua hien thi ",res.getFoodName());
            if(res.food_group.equals(group1.string))
            {
                group1.children.add(res.food_name+"\n"+
                "Calories:"+res.Calories+", Protein:"+res.Protein);
            }
            else
            {
                group2.children.add(res.food_name+"\n"+
                        "Calories:"+res.Calories+", Protein:"+res.Protein);
            }
        }


        groups.append(0,group1);
        groups.append(1,group2);
        showitem.setText(ress.size()+" items");
    }

}
