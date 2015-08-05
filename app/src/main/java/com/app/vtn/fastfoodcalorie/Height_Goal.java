package com.app.vtn.fastfoodcalorie;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;


public class Height_Goal extends ActionBarActivity {

    EditText height,weight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_height__goal);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        height=(EditText)findViewById(R.id.tall);
        weight=(EditText)findViewById(R.id.currentweight);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_height__goal, menu);
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
            try {
                BMR.weight = Double.parseDouble(weight.getText().toString());
                BMR.height = Double.parseDouble(height.getText().toString());
                Intent height_activity = new Intent(this, Detail.class);
                startActivity(height_activity);
            } catch (Exception ae)
            {
                Toast.makeText(getApplicationContext(), "Please enter data", Toast.LENGTH_SHORT).show();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
