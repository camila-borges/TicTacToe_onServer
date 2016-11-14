package tictactoe.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
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
	String actualPlayer;
	boolean player1, xTurn = true;

	Socket listenServer;
	Scanner listener;
	PrintWriter sendCordinates;
	int count = 0;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@FXML
	public void handlePauseButton() {

	}
	
	@FXML
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
		if (((actualPlayer.equals("PLAYER1") && xTurn) || (actualPlayer.equals("PLAYER2") && !xTurn))
				&& clickedButton.getStyle().startsWith("-fx-border")) {
			clickedButton.setStyle(game.drawValue(row, column, player1));
			count++;
			sendCordinates.println(row + " " + column);
			sendCordinates.flush();
			xTurn = !xTurn;
		}
	}
	
	@FXML
	public void handleQuitButton(ActionEvent e) {
		quitButton = (Button) e.getSource();

	}

	public void setButtonImage(String row, String column) {

		count++;
		Integer intRow = Integer.parseInt(row);
		Integer intColumn = Integer.parseInt(column);

		String clickedButton = "but" + row + column;
		@SuppressWarnings("unchecked")
		Class<AnchorPaneInGameController> c = (Class<AnchorPaneInGameController>) this.getClass();
		try {
			Field f = c.getDeclaredField(clickedButton);
			f.setAccessible(true);
			Button realClickedButton = (Button) f.get(this);

			realClickedButton.setStyle(game.drawValue(intRow, intColumn, !player1));
			xTurn = !xTurn;
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

	}

	public void setDialogStage(String ip, String playerName) {

		if (playerName.isEmpty()) {
			playerName = "Player 1";
		}

		try {
			listenServer = new Socket(ip, 5000);
			System.out.println(ip);
			listener = new Scanner(listenServer.getInputStream());
			String isConnected = listener.nextLine();
			if (isConnected.startsWith("CONNECTED")) {
				int port = Integer.parseInt(isConnected.split(" ")[2]);
				actualPlayer = isConnected.split(" ")[1];
				if (actualPlayer.equals("PLAYER1")) {
					player1 = true;
					nicknameP1Label.setText(playerName);
				} else {
					player1 = false;
					nicknameP2Label.setText(playerName);
				}

				listenServer = new Socket(ip, port);
				sendCordinates = new PrintWriter(listenServer.getOutputStream());
				Listener listenerThread = new Listener();
				listenerThread.start();
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	class Listener extends Thread {

		@Override
		public void run() {
			String getValue = "";
			if (listener.hasNextLine()) {
				getValue = listener.nextLine();
			}
			while (!getValue.equals("X") && !getValue.equals("O") && count < 9) {
				String[] cordinates = getValue.split(" ");
				setButtonImage(cordinates[0], cordinates[1]);
				if (listener.hasNextLine()) {
					getValue = listener.nextLine();
				}
			}
		}
	}
}
