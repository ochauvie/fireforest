package com.fireforest.activity;

import com.fireforest.R;
import com.fireforest.adapter.GridAdapter;
import com.fireforest.listener.IPlayTurnListener;
import com.fireforest.model.Cell;
import com.fireforest.model.Grid;
import com.fireforest.model.Parameter;
import com.fireforest.task.PlayCycleTask;

import android.os.Bundle;
import android.os.AsyncTask.Status;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity implements IPlayTurnListener {

	private GridView gridView;
	private GridAdapter adapter;
	
	private ImageButton turnButton;
	private TextView turnView;
	
	private Grid grid;
	private Parameter parameter;
	private int turn;
	
	private PlayCycleTask playCycleTask;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forest);
        
        // Init parameter
        parameter = new Parameter();
        
        // Get parameters from settings
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
        	parameter = bundle.getParcelable("parameter");
        }
        
        // Init grid
        grid = new Grid(parameter);
        
	    // Grid view
	    gridView = (GridView) findViewById(R.id.grid_forest);
        gridView.setNumColumns(parameter.getGridY());	// Number of columns
        adapter = new GridAdapter(MainActivity.this, grid);
        gridView.setAdapter(adapter);
        // Cell selection in the grid
        gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				
				// Get cell by position
		        int xPosition =  (int) Math.floor(position/(grid.getGridY()));
		        int yPosition = position - (xPosition*(grid.getGridY()));
		        Cell cell = grid.getCell(xPosition, yPosition);
		        
		        // Start fire
				if (cell.getStatus()==Cell.CELL_TREE) {
					cell.setStatus(Cell.CELL_FIRE);
					grid.setFire(grid.getFire()+1);
				}
		        
			}
	
		});
        
        turnView = (TextView) findViewById(R.id.textViewTurn);
        turn = 0;
        turnView.setText(getString(R.string.turn) + ": " + turn 
        		+ " - " + getString(R.string.tree) + ": " + grid.getTree()
        		+ " - " + getString(R.string.fire) + ": " + grid.getFire());
        
        
        // Next turn button
        turnButton = (ImageButton) findViewById(R.id.nextTurn);
        turnButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		
        		boolean stop = false;
        		if (playCycleTask!=null) {
        			if (playCycleTask.getStatus().equals(Status.RUNNING)) {
        				playCycleTask.cancel(true);
        				stop = true;
        				turnButton.setImageResource(R.drawable.start);
        			} 
        		} 
        		if (!stop) {
	        		playCycleTask = new PlayCycleTask();
	        		playCycleTask.addListener(MainActivity.this);
	        		playCycleTask.execute(grid);
	        		turnButton.setImageResource(R.drawable.stop);
        		}
        		
        	}
        });    
        
    }

    @Override
	public void dataChanged(Grid grid) {
    	this.grid = grid;
		adapter = new GridAdapter(MainActivity.this, this.grid);
		gridView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		turnView.setText(getString(R.string.turn) + ": " + turn++ 
        		+ " - " + getString(R.string.tree) + ": " + grid.getTree()
        		+ " - " + getString(R.string.fire) + ": " + grid.getFire());

		
		if (this.grid.getFire()==0) {
			turnButton.setImageResource(R.drawable.start);
		}
	}
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    /**
     * Call when menu item is selected
     */
    public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId()) {
          case R.id.menu_settings:
             Intent myIntent = new Intent(MainActivity.this, SettingsActivity.class);
             myIntent.putExtra("parameter", parameter);
             startActivity(myIntent);
             finish();
             return true;
       }
       return false;
    }
    
    @Override
    public void onStop() {
    	super.onStop();
    	// End play task and remove listener
    	if (playCycleTask!=null) {
    		playCycleTask.cancel(true);
    		playCycleTask.removeListener(this);
    	} 
    }




	
}
