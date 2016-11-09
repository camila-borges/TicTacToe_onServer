package tictactoe.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import tictactoe.models.TicTacToe;

public class AnchorPaneInGameController implements Initializable {

	@FXML
	private Label nicknameP1Label, nicknameP2Label, scoreP1Label, scoreP2Label;
	@FXML
	private Button pauseButton, quitButton;
	@FXML
	private GridPane gridGame;
	@FXML
	private Button but00, but01, but02, but10, but11, but12, but20, but21, but22;

	private TicTacToe game = new TicTacToe();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@FXML
	public void handlePauseButton() {

	}

	@FXML
	public void handleQuitButton() {

	}

	@SuppressWarnings("static-access")
	public void handlePaneClick(Event evt){
		
		Button clickedButton = (Button) evt.getTarget();
		
		Node node = (Node)evt.getSource();
		int column, row;
		if(gridGame.getColumnIndex(node) == null){
			column = 0;
		}else{
			column = gridGame.getColumnIndex(node);
		}
		if(gridGame.getRowIndex(node) == null){
			row = 0;
		}else{
			row = gridGame.getRowIndex(node);
		}
		clickedButton.setText(game.drawValue(row, column));			
	}

	public void handleGridPane() {

	}
}
