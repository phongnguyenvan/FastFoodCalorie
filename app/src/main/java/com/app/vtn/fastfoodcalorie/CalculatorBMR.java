package com.app.vtn.fastfoodcalorie;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;


public class CalculatorBMR extends Activity {

    EditText age,weight,height;
    Spinner activity;
    RadioButton female;
    Button calculator;
    int itemselected=-1;
    String[] typeActivity={"Sedentary","Lightly active","Moderately active","Very active","Extra active"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_bmr);
        age=(EditText)findViewById(R.id.age);
        weight=(EditText)findViewById(R.id.weight);
        height=(EditText)findViewById(R.id.height);
        activity=(Spinner)findViewById(R.id.activity);
        female=(RadioButton)findViewById(R.id.Female);
        calculator=(Button)findViewById(R.id.show);
        ArrayAdapter<String> aa = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, typeActivity);
// provide a particular design for the drop-down lines
        aa.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
// associate GUI spinner and adapter
        activity.setAdapter(aa);
        activity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemselected=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        calculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double w=Double.parseDouble(weight.getText().toString());
                double h=Double.parseDouble(height.getText().toString());
                boolean men=false;
                if(female.isChecked())men=true;
                int a=Integer.parseInt(age.getText().toString());
                double bmr=BMR.getBMR(men,w,h,a);
                double amr=BMR.getAMR(itemselected,bmr);
                AlertDialog alertDialog = new AlertDialog.Builder(CalculatorBMR.this).create();
                alertDialog.setTitle("Show");
                alertDialog.setMessage("You must eat "+amr+" calories per day!");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calculator_bmr, menu);
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
