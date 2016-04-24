package com.moblima.movie;

import java.io.Serializable;

public class SeatLayout implements Serializable{
	private int row;
	private int col;
	
	private Seat[][] seats;
	
	public SeatLayout(){
		row = 10;
		col = 10;
		seats = new Seat[row][col];
		initializeSeat();
	}
	public SeatLayout(int row, int col) {
		this.row = row;
		this.col = col;
		initializeSeat();
	}

	public Seat getSeats(int row, int col) {
		return seats[row][col];
	}
	public Seat[][] getSeats() {
		return seats;
	}
	
	public void setSeats(int row, int col, boolean available) {
		seats[row][col].setAvailable(available);
	}
	private void initializeSeat() {
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				seats[i][j] = new Seat(false);
			}
		}
	}
	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}
	public boolean isFullyBooked(){
		for(int i=0;i<10;i++){
			for(int j =0;j<10;j++){
				if(seats[i][j].getAvailable()==false){
					return false;
				}
			}
		}
		return true;
	}
}
