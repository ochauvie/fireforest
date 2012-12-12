package com.fireforest.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Cell implements Parcelable {

	public static final int CELL_EMPTY = 0;
	public static final int CELL_FIRE = 1;
	public static final int CELL_TREE = 2;
	public static final int CELL_ASH = 3;
	public static final int CELL_NEW_FIRE = 4;
	
	private int x; // Line
	private int y; // Column
	private int status; // Cell status
	
	
	
	public Cell(int x, int y, int status) {
		super();
		this.x = x;
		this.y = y;
		this.status = status;
	}
	/**
	 * Getter x
	 * @return the x
	 */
	public int getX() {
		return x;
	}
	/**
	 * Setter x
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}
	/**
	 * Getter y
	 * @return the y
	 */
	public int getY() {
		return y;
	}
	/**
	 * Setter y
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}
	/**
	 * Getter status
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * Setter status
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	
	public Cell(Parcel parcel) {
		x = parcel.readInt();
		y = parcel.readInt();
		status = parcel.readInt();
	}
	
	public static final Parcelable.Creator<Cell> CREATOR = new Parcelable.Creator<Cell>()
		{
		    @Override
		    public Cell createFromParcel(Parcel source)
		    { return new Cell(source);}

		    @Override
		    public Cell[] newArray(int size)
		    { return new Cell[size];}
		};
	
	
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel parcel, int flag) {
		parcel.writeInt(x);
		parcel.writeInt(y);
		parcel.writeInt(status);
	}
	
	
	
	
}
