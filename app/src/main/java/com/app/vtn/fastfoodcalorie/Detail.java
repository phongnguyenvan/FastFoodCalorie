package com.app.vtn.fastfoodcalorie;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class Detail extends ActionBarActivity {

    TextView detail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        detail=(TextView)findViewById(R.id.detail);
        double bmr=BMR.getBMR(BMR.men,BMR.weight,BMR.height,BMR.age);
        double arm=BMR.getAMR(BMR.typeactivity,bmr);
        if(BMR.typegoal==0)
        {
            double calo=BMR.getCalories(BMR.weightlost);
            double weigh=arm-(BMR.getCalories(BMR.weightlost)/7);
            if(weigh<0)weigh=weigh*-1;
            detail.setText("Your Base Metabolic Rate is "+bmr+ ". Based on your daily activity level,you burn "+arm+" each day."+
            " In order to lose "+BMR.weightlost+" lbs, you must eat "+calo+" less calories than you burn, so to lose "+BMR.weightlost+
                    " lbs in a week, you should eat "+weigh+" calories each day.");

        }
        if(BMR.typegoal==1)
        {

            double weigh=arm-BMR.getCalories(BMR.weightlost);
            detail.setText("Your Base Metabolic Rate is "+bmr+ ". Based on your daily activity level,you burn "+arm+" each day.");

        }
        if(BMR.typegoal==2)
        {

            double calo=BMR.getCalories(BMR.weightlost);
            double weigh=arm+(BMR.getCalories(BMR.weightlost)/7);
            String result = String.format("%0.1d", weigh);
            String sbmr = String.format("%0.1f", bmr);
            String sarm = String.format("%0.1f", arm);

            detail.setText("Your Base Metabolic Rate is "+sbmr+ ". Based on your daily activity level,you burn "+sarm+" each day."+
                    " In order to gain "+BMR.weightlost+" lbs, you must eat "+calo+" than calories than you burn, so to gain "+BMR.weightlost+
                    " lbs in a week, you should eat "+result+" calories each day.");

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
