package com.fireforest.task;

import java.util.ArrayList;
import java.util.List;

import com.fireforest.listener.IPlayTurn;
import com.fireforest.listener.IPlayTurnListener;
import com.fireforest.model.Cell;
import com.fireforest.model.Grid;
import com.fireforest.model.Parameter;


import android.os.AsyncTask;

public class PlayCycleTask extends AsyncTask<Grid, Integer, Grid> implements IPlayTurn {

	private Grid grid; // Game grid
	private List<IPlayTurnListener> listeners = null;
	
	@Override
	protected Grid doInBackground(Grid... params) {
		if (params==null || params.length==0) {
			return null;
		}
		
		grid = params[0];
		
		// Running until : task is cancelled or all no cells in fire
		while (grid.getFire()>0) {
			try {
				Thread.sleep(Parameter.INITSleep);  // TODO
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			playStepFire();
			playStepUpdateGrid();
			publishProgress();
			if (isCancelled()) break;
		}
			
		return grid;
	}
	
	/**
	 * On task progression
	 * Inform listener 
	 */
	protected void onProgressUpdate(Integer... progress) {
		fireDataChanged();
    }

	
	/**
	 * Play step fire
	 */
	private void playStepFire() {
		grid.copyGridToTemp();
		Cell[][] tempCells = grid.getTempCells();
    	for (int x=0; x<grid.getGridX(); x++) {
			for (int y=0; y<grid.getGridY(); y++) {
				Cell tempCell = tempCells[x][y];
				if (tempCell.getStatus() == Cell.CELL_TREE) {
				
					List<Cell> neighbors = grid.getNeighborByRange(x, y, 1);
					boolean startFire = false;
					for (Cell neighbor:neighbors) {
						if (Cell.CELL_FIRE==neighbor.getStatus()) {
							startFire = true;
							break;
						}
					}
					if (startFire) {
						grid.getCell(x, y).setStatus(Cell.CELL_NEW_FIRE);
					}
				}
			}
		}
	}
	
	/**
	 * Play step update
	 */
	private void playStepUpdateGrid() {
		grid.setFire(0);
		grid.setAsh(0);
		grid.setTree(0);
		for (int x=0; x<grid.getGridX(); x++) {
			for (int y=0; y<grid.getGridY(); y++) {
				Cell cell = grid.getCell(x, y);
				if (cell.getStatus() == Cell.CELL_TREE) {
					grid.setTree(grid.getTree()+1);
				} else if (cell.getStatus() == Cell.CELL_ASH) {
					cell.setStatus(Cell.CELL_EMPTY);
				} else if (cell.getStatus() == Cell.CELL_FIRE) {
					cell.setStatus(Cell.CELL_ASH);
					grid.setAsh(grid.getAsh()+1);
				} else if (cell.getStatus() == Cell.CELL_NEW_FIRE) {
					cell.setStatus(Cell.CELL_FIRE);
					grid.setFire(grid.getFire()+1);
				}
			}
		}
	}

	
	/**
	 * Inform listener
	 */
	private void fireDataChanged(){ 
	    if(listeners != null){ 
	        for(IPlayTurnListener listener: listeners){ 
	            listener.dataChanged(grid); 
	        } 
	    } 
	}
	
	@Override
	public void addListener(IPlayTurnListener listener) {
		if(listeners == null){ 
	        listeners = new ArrayList<IPlayTurnListener>(); 
	    } 
	    listeners.add(listener); 
		
	}

	@Override
	public void removeListener(IPlayTurnListener listener) {
		if(listeners != null){ 
	        listeners.remove(listener); 
	    } 
	}
}
