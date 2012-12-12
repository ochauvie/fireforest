package com.fireforest.activity;



import com.fireforest.R;
import com.fireforest.filter.InputFilterMinMax;
import com.fireforest.model.Parameter;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.widget.EditText;
import android.widget.ImageButton;
import android.text.InputFilter;
import android.view.View;

public class SettingsActivity extends Activity {
	
	private EditText line, column, density, sleep;
	private ImageButton save;
	private Parameter parameter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        
        line = (EditText) findViewById(R.id.line);
        column = (EditText) findViewById(R.id.column);
        density = (EditText) findViewById(R.id.density);
        sleep = (EditText) findViewById(R.id.sleep);
        
        line.setFilters(new InputFilter[]{ new InputFilterMinMax(Parameter.MINX, Parameter.MAXX)});
        column.setFilters(new InputFilter[]{ new InputFilterMinMax(Parameter.MINY, Parameter.MAXY)});
        density.setFilters(new InputFilter[]{ new InputFilterMinMax(Parameter.MINDensity, Parameter.MAXDensity)});
        sleep.setFilters(new InputFilter[]{ new InputFilterMinMax(Parameter.MINSleep, Parameter.MAXSleep)});
        
        parameter = new Parameter();
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
        	parameter = getIntent().getExtras().getParcelable("parameter");
        }
        
        line.setText(String.valueOf(parameter.getGridX()));
        column.setText(String.valueOf(parameter.getGridY()));
        density.setText(String.valueOf(parameter.getGridDensity()));
        sleep.setText(String.valueOf(parameter.getTurnSleep()));
          
        
        // Save button
        save = (ImageButton) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		if (line.getText().length()>0 && column.getText().length()>0 && density.getText().length()>0) { 
	        		Intent myIntent = new Intent(SettingsActivity.this, MainActivity.class);
	        		parameter.setGridX(Integer.valueOf(line.getText().toString()));
	        		parameter.setGridY(Integer.valueOf(column.getText().toString()));
	        		parameter.setGridDensity(Integer.valueOf(density.getText().toString()));
	        		parameter.setTurnSleep(Integer.valueOf(sleep.getText().toString()));
	        		myIntent.putExtra("parameter", parameter);
	                startActivity(myIntent);
	                finish();
        		}
        	}
        });    
                
    }
    
    @Override
    public void onBackPressed() {
	}
	
}
