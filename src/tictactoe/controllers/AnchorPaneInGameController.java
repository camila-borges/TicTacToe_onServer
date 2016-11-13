package tictactoe.controllers;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
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
	@FXML
	private ImageView imgView22;

	private TicTacToe game = new TicTacToe();
	private String[][] gameBoard;
	
	Socket listenServer;
	Scanner listener;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			listenServer = new Socket("127.0.0.1", 5000);
			listener = new Scanner(listenServer.getInputStream());
			String isConnected = listener.nextLine();
			if(isConnected.startsWith("CONNECTED")){
				int port = Integer.parseInt(isConnected.split(" ")[1]);
				listenServer.close();
				listenServer = new Socket("127.0.0.1", port);
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void handlePauseButton() {

	}

	@SuppressWarnings("static-access")
	public void handlePaneClick(Event evt) {

		Button clickedButton = (Button) evt.getTarget();

		Node node = (Node) evt.getSource();
		int column, row;
		if (gridGame.getColumnIndex(node) == null) {
			column = 0;
		} else {
			column = gridGame.getColumnIndex(node);
		}
		if (gridGame.getRowIndex(node) == null) {
			row = 0;
		} else {
			row = gridGame.getRowIndex(node);
		}
		clickedButton.setStyle(game.drawValue(row, column));
		
		try {
			PrintStream sendCordinates = new PrintStream(listenServer.getOutputStream());
			sendCordinates.println(row + " " + column);
			sendCordinates.flush();
			sendCordinates.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void handleGridPane() {

	}

	public void handleQuitButton(ActionEvent e) {
		quitButton = (Button) e.getSource();

	}
}
