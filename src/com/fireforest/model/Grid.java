package com.fireforest.model;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Grid implements Parcelable {
	
	private int gridX; // Number of lines
	private int gridY; // Number of columns
	private int initDensity; // Population density
	private Cell[][] cells; // Cells of the grid
	private Cell[][] tempCells; // Temp cells of the grid
	private int tree; // Number of tree
	private int ash; // Number of ash
	private int fire; // Number of fire
	
	
	/**
	 * Constructor
	 * @param parameter : game parameters
	 */
	public Grid(Parameter parameter) {
		super();
		this.gridX = parameter.getGridX();
		this.gridY = parameter.getGridY();
		this.initDensity = parameter.getGridDensity();
		cells = new Cell[gridX][gridY];
		tempCells = new Cell[gridX][gridY];
		
		// Populate the grid
		populateGrid();
		
		// Start fire
		startFire();
	}
	
	public Grid(Parcel parcel) {
		gridX = parcel.readInt();
		gridY = parcel.readInt();
		initDensity = parcel.readInt();
		cells = new Cell[gridX][gridY];
		tempCells = new Cell[gridX][gridY];
		for (int x=0; x<gridX; x++) {
			for (int y=0; y<gridY; y++) {
				Cell cell = parcel.readParcelable(Cell.class.getClassLoader());
				cells[x][y] = cell;
			}
		}
		tree = parcel.readInt();
		ash = parcel.readInt();
		fire = parcel.readInt();
	}
	
	public static final Parcelable.Creator<Grid> CREATOR = new Parcelable.Creator<Grid>()
		{
		    @Override
		    public Grid createFromParcel(Parcel source)
		    { return new Grid(source);}

		    @Override
		    public Grid[] newArray(int size)
		    { return new Grid[size];}
		};
	
	
	
	/**
	 * Getter gridX
	 * @return the gridX
	 */
	public int getGridX() {
		return gridX;
	}
	/**
	 * Setter gridX
	 * @param gridX the gridX to set
	 */
	public void setGridX(int gridX) {
		this.gridX = gridX;
	}
	/**
	 * Getter gridY
	 * @return the gridY
	 */
	public int getGridY() {
		return gridY;
	}
	/**
	 * Setter gridY
	 * @param gridY the gridY to set
	 */
	public void setGridY(int gridY) {
		this.gridY = gridY;
	}
	/**
	 * Getter initDensity
	 * @return the initDensity
	 */
	public int getInitDensity() {
		return initDensity;
	}
	/**
	 * Setter initDensity
	 * @param initDensity the initDensity to set
	 */
	public void setInitDensity(int initDensity) {
		this.initDensity = initDensity;
	}
	/**
	 * Getter cells
	 * @return the cells
	 */
	public Cell[][] getCells() {
		return cells;
	}
	/**
	 * Setter cells
	 * @param cells the cells to set
	 */
	public void setCells(Cell[][] cells) {
		this.cells = cells;
	} 
	
	
	
		
	/**
	 * Getter tree
	 * @return the tree
	 */
	public int getTree() {
		return tree;
	}

	/**
	 * Setter tree
	 * @param tree the tree to set
	 */
	public void setTree(int tree) {
		this.tree = tree;
	}

	/**
	 * Getter ash
	 * @return the ash
	 */
	public int getAsh() {
		return ash;
	}

	/**
	 * Setter ash
	 * @param ash the ash to set
	 */
	public void setAsh(int ash) {
		this.ash = ash;
	}

	/**
	 * Getter fire
	 * @return the fire
	 */
	public int getFire() {
		return fire;
	}

	/**
	 * Setter fire
	 * @param fire the fire to set
	 */
	public void setFire(int fire) {
		this.fire = fire;
	}

	/**
	 * Getter tempCells
	 * @return the tempCells
	 */
	public Cell[][] getTempCells() {
		return tempCells;
	}

	/**
     * Random population using density parameter
     */
    private void populateGrid() {
    	tree = 0;
    	ash = 0;
    	fire = 0;
    	int lower = 0;
		int higher = 10;
		for (int x=0; x<gridX; x++) {
			for (int y=0; y<gridY; y++) {
				int random = (int)(Math.random() * (higher-lower)) + lower;
				Cell cell = new Cell(x, y, Cell.CELL_EMPTY);
				if (random<=initDensity) {
					cell.setStatus(Cell.CELL_TREE);
					tree++;
				} 
				cells[x][y] = cell;
			}
		}
	}

    /**
     * Start fire in random cell
     */
    private void startFire() {
    	int lower = 0;
		int xHigher = gridX ;
		int yHigher = gridY ;
		boolean start = false;
		while (!start) {
			int xRandom = (int)(Math.random() * (xHigher-lower)) + lower;
			int yRandom = (int)(Math.random() * (yHigher-lower)) + lower;
			if (cells[xRandom][yRandom].getStatus()==Cell.CELL_TREE) {
				cells[xRandom][yRandom].setStatus(Cell.CELL_FIRE);
				tree--;
				fire++;
				start = true;
			}
		}
    }
    
    /**
     * Copy current grid in temp grid
     */
    public void copyGridToTemp() {
    	for (int x=0; x<gridX; x++) {
			for (int y=0; y<gridY; y++) {
				Cell tempCell = new Cell(x, y, cells[x][y].getStatus());
				tempCells[x][y] = tempCell;
			}
    	}
    }
	
    /**
     * Return cell in the grid by x and y position
     * @param x
     * @param y
     * @return the cell
     */
    public Cell getCell(int x, int y) {
    	Cell cell = null;
    	if (x >=0 && y>=0 && (x<gridX) && (y<gridY)) {
    		cell = cells[x][y];
    	}
    	return cell;
    }
    
    
    /**
     * Get neighbor cells in temp grid (only 4 neighbors by cell)
     * @param x: x position of current cell
     * @param y: y position of current cell
     * @param range: range of cell to find neighbors
     * @return: list of neighbors
     */
    public List<Cell> getNeighborByRange(int x, int y, int range) {
		List<Cell> neigghborCells = new ArrayList<Cell>();
		for (int r=1; r<=range; r++) {
			// Up line
			if ((x-r)>=0) {
				neigghborCells.add(tempCells[x-r][y]);
			}
			// Current line
			if ((y-r)>=0) {
				neigghborCells.add(tempCells[x][y-r]);
			}
			if ((y+r)<(gridY)) {
				neigghborCells.add(tempCells[x][y+r]);
			}
			
			// Under line
			if ((x+r)<(gridX)) {
				neigghborCells.add(tempCells[x+r][y]);
			}
		}
		return neigghborCells;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeInt(gridX);
		parcel.writeInt(gridY);
		parcel.writeInt(initDensity);
		for (int x=0; x<gridX; x++) {
			for (int y=0; y<gridY; y++) {
				parcel.writeParcelable(getCell(x, y), flags);
			}
		}
		parcel.writeInt(tree);
		parcel.writeInt(ash);
		parcel.writeInt(fire);
	}
    
}
