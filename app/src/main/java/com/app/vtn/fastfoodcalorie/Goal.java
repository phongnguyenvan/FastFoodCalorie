package com.app.vtn.fastfoodcalorie;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class Goal extends ActionBarActivity {

    String[] items={"Lose weight","Maintain weight","Gain weight"};
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);
        list=(ListView)findViewById(R.id.list);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ArrayAdapter<String> aa = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, items);
// provide a particular design for the drop-down lines
        aa.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
// associate GUI spinner and adapter
        list.setAdapter(aa);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BMR.typegoal=position;

                if(position!=1) {
                    Intent weightintent = new Intent(Goal.this, Weight_Goal.class);
                    startActivity(weightintent);
                }
                else
                {
                    Intent active_level=new Intent(Goal.this,Activity_Level.class);
                    startActivity(active_level);
                }

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_goal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent active_level=new Intent(this,Activity_Level.class);
            startActivity(active_level);
            return true;
        }
        if(id==R.id.home)
        {
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }
}
