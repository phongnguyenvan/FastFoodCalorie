package com.app.vtn.fastfoodcalorie;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;


public class You extends ActionBarActivity {

    RadioButton female;
    EditText age;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        female=(RadioButton)findViewById(R.id.Female);
        female.setChecked(true);
        age=(EditText)findViewById(R.id.age);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_you, menu);
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
                BMR.men=female.isChecked();
                BMR.age=Integer.parseInt(age.getText().toString());
                Intent height_activity = new Intent(this, Height_Goal.class);
                startActivity(height_activity);
            }
            catch(Exception ae)
            {
                Toast.makeText(getApplicationContext(),"Please enter data",Toast.LENGTH_SHORT).show();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
