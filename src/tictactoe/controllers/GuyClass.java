package tictactoe.controllers;

import java.util.List;

import javafx.scene.layout.GridPane;

public class GuyClass {

	private final GridPane GRID_PANE;
	private final int GRID_DIMENSION;
	private final int AMOUNT_NEEDED_TO_WIN;
	
	public GuyClass (GridPane gridPane, int gridDimension, int amountToWin){
		this.GRID_PANE = gridPane;
		this.GRID_DIMENSION = gridDimension;
		this.AMOUNT_NEEDED_TO_WIN = amountToWin;
		
		initializeGrid();
	}
	
	public int getDimension(){
		return this.GRID_DIMENSION;
	}
	
	public int getColumnSize(){
		return GRID_PANE.getColumnConstraints().size();
	}
	
	public int getRowSize(){
		return GRID_PANE.getRowConstraints().size();
	}
	
	public int getAmountNeededToWin(){
		return this.AMOUNT_NEEDED_TO_WIN;
	}
	
	public GridPane getGridPane(){
		return this.GRID_PANE;
	}
	
	public Player getPlayer1(){
		for (Coordinate c : getUsedCoordinates()){
			
		}
	}
}
