package com.fireforest.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Parameter implements Parcelable {
	
	public static final int INITX = 35; // Default number of lines
	public static final int INITY = 35; // Default number of columns
	public static final int INITDensity = 5; // Default for density
	public static final int INITSleep = 300; // Default sleep time (ms)
	
	public static final int MINX = 1; // Minimum number of lines
	public static final int MINY = 1; // Minimum number of columns
	public static final int MINDensity = 1; // Minimum for density
	public static final int MINSleep = 0; // Minimum for sleep
	public static final int MINPlayer = 1; // Minimum number for player
	public static final int MINNbVirus = 1; // Minimum number for virus
	
	public static final int MAXX = 100; // Maximum number of lines
	public static final int MAXY = 100; // Maximum number of columns
	public static final int MAXDensity = 10; // Maximum for density
	public static final int MAXSleep = 10000; // Maximum for sleep
	
	public static final int MAXTurn = 1000; // Maximum for density
	
	
	
	private int gridX; // Number of lines
	private int gridY; // Number of columns
	private int gridDensity; // Density of cells
	private int turnSleep; // Time to sleep between two cycle in auto mode
	
	public Parameter() {
		this.gridX = INITX;
		this.gridY = INITY;
		this.gridDensity = INITDensity;
		this.turnSleep = INITSleep;
	}
	
	public Parameter(Parcel parcel) {
		this.gridX = parcel.readInt();
		this.gridY = parcel.readInt();
		this.gridDensity = parcel.readInt();
		this.turnSleep = parcel.readInt();
	}
	
	public static final Parcelable.Creator<Parameter> CREATOR = new Parcelable.Creator<Parameter>()
		{
		    @Override
		    public Parameter createFromParcel(Parcel source)
		    { return new Parameter(source);}

		    @Override
		    public Parameter[] newArray(int size)
		    { return new Parameter[size];}
		};
	
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel parcel, int flag) {
		parcel.writeInt(gridX);
		parcel.writeInt(gridY);
		parcel.writeInt(gridDensity);
		parcel.writeInt(turnSleep);
	}

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
	 * Getter gridDensity
	 * @return the gridDensity
	 */
	public int getGridDensity() {
		return gridDensity;
	}

	/**
	 * Setter gridDensity
	 * @param gridDensity the gridDensity to set
	 */
	public void setGridDensity(int gridDensity) {
		this.gridDensity = gridDensity;
	}

		
	/**
	 * Getter turnSleep
	 * @return the turnSleep
	 */
	public int getTurnSleep() {
		return turnSleep;
	}

	/**
	 * Setter turnSleep
	 * @param turnSleep the turnSleep to set
	 */
	public void setTurnSleep(int turnSleep) {
		this.turnSleep = turnSleep;
	}

			
	
}
