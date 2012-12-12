package com.fireforest.adapter;

import com.fireforest.R;
import com.fireforest.model.Cell;
import com.fireforest.model.Grid;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class GridAdapter extends BaseAdapter {

	private Grid grid;
	private Context mContext;

    public GridAdapter(Context c, Grid grid) {
        mContext = c;
        this.grid = grid;
    }

    public int getCount() {
    	return (grid.getGridX() * grid.getGridY()); 
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(15, 15));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(1, 1, 1, 1);
        } else {
            imageView = (ImageView) convertView;
        }
        
        // Get cell by position
        int xPosition =  (int) Math.floor(position/(grid.getGridY())); // Line
        int yPosition = position - (xPosition*(grid.getGridY())); // Column
        
        Cell cell = grid.getCell(xPosition, yPosition);

        
        // Cell status visual effect
        if (cell.getStatus()==Cell.CELL_EMPTY) {
        	imageView.setImageResource(0);
        }
        if (cell.getStatus()==Cell.CELL_ASH) {
        	imageView.setImageResource(R.drawable.ash);	
        }
        if (cell.getStatus()==Cell.CELL_FIRE) {
       		imageView.setImageResource(R.drawable.fire);
        }
        if (cell.getStatus()==Cell.CELL_TREE) {
        	imageView.setImageResource(R.drawable.tree);	
        }

        return imageView;
    }


}

