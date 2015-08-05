package com.app.vtn.fastfoodcalorie;


import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends ActionBarActivity {
    GridView grid;
    Activity main;
    FoodDatabase dbfood;
    EditText search;
    String[] web = {
            "Flavor of negro",
            "Hus on first",
            "Louisiana Kitchen",
            "Soon fatt"

    } ;
    int[] imageId = {
            R.drawable.flavors_of_negro,
            R.drawable.hus_on_first,
            R.drawable.louisiana_kitchen,
            R.drawable.soon_fatt

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main=this;
        CustomGrid adapter = new CustomGrid(MainActivity.this, web, imageId);
        grid=(GridView)findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(MainActivity.this, "Show all foods in  " +web[+ position], Toast.LENGTH_SHORT).show();
                Intent foodDetail=new Intent(main,FoodDetail.class);
                foodDetail.putExtra("RESTAURANT",web[position]);
                startActivity(foodDetail);

            }
        });
       loadDatabase();
        search=(EditText)findViewById(R.id.search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                ArrayList<String> lst=new ArrayList<String>();
                int[] imageresult=new int[web.length];
                int index=0;
                for(int i=0;i<web.length;i++)
                {
                    if(web[i].toLowerCase().contains(s))
                    {
                        lst.add(web[i]);
                        imageresult[index++]=imageId[i];
                    }
                }
                String [] result=new String[lst.size()];
                int i=0;
                for(i=0;i<lst.size();i++)
                {
                    result[i]=lst.get(i);
                }
                CustomGrid adapter = new CustomGrid(MainActivity.this, result, imageresult);
                grid.setAdapter(adapter);


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
    private void loadDatabase()
    {
        dbfood=new FoodDatabase(getApplicationContext());

        //dbfood.onCreate(dbfood.getReadableDatabase());
        Random rn=new Random();
        // Creating tags
        Food food1=new Food("Bacon Clubhouse Burger");
        food1.food_group="Burgers & Sandwiches";
        food1.Calories=rn.nextInt(200)+500;
        food1.Total_Fat=rn.nextInt(100)+100;
        food1.Protein=rn.nextInt(100)+100;
        food1.Vitamin=rn.nextInt(100)+100;
        food1.Iron=rn.nextInt(100)+100;

        Food food2=new Food("Bacon Clubhouse Grilled Chicken Sandwich");
        food2.food_group="Burgers & Sandwiches";
        food2.Calories=rn.nextInt(200)+500;
        food2.Total_Fat=rn.nextInt(100)+100;
        food2.Protein=rn.nextInt(100)+100;
        food2.Vitamin=rn.nextInt(100)+100;
        food2.Iron=rn.nextInt(100)+100;

        Food food3=new Food("Bacon Clubhouse Crispy Chicken Sandwich");
        food3.food_group="Burgers & Sandwiches";
        food3.Calories=rn.nextInt(200)+500;
        food3.Total_Fat=rn.nextInt(100)+100;
        food3.Protein=rn.nextInt(100)+100;
        food3.Vitamin=rn.nextInt(100)+100;
        food3.Iron=rn.nextInt(100)+100;



        Food food4=new Food("Bacon Clubhouse Grilled Chicken Sandwich");
        food4.food_group="Chicken & Fish";
        food4.Calories=rn.nextInt(200)+500;
        food4.Total_Fat=rn.nextInt(100)+100;
        food4.Protein=rn.nextInt(100)+100;
        food4.Vitamin=rn.nextInt(100)+100;
        food4.Iron=rn.nextInt(100)+100;

        // Inserting Restaurant in db
        final  long food1_id=dbfood.createFood(food1);
        final  long food2_id=dbfood.createFood(food2);
        final  long food3_id=dbfood.createFood(food3);
        final  long food4_id=dbfood.createFood(food4);

        Restaurant res1=new Restaurant("Flavor of negro");
        Restaurant res2=new Restaurant("Hus on first");
        Restaurant res3=new Restaurant("Louisiana Kitchen");
        Restaurant res4=new Restaurant("Soon fatt");

        long res1_id=dbfood.createRestaurant(res1,new long[]{food1_id,food3_id});
        long res2_id=dbfood.createRestaurant(res2,new long[]{food3_id,food2_id});
        long res3_id=dbfood.createRestaurant(res3,new long[]{food4_id,food1_id});
        long res4_id=dbfood.createRestaurant(res4,new long[]{food2_id,food4_id});



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_calculator) {

            Intent calculatorBMR=new Intent(main,Goal.class);
            startActivity(calculatorBMR);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}