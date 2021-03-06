package tictactoe.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import tictactoe.models.TicTacToeGameControl;

public class AnchorPaneInGameController implements Initializable {

	@FXML
	private Label nicknameP1Label, nicknameP2Label, scoreP1Label, scoreP2Label;
	@FXML
	private Button pauseButton, quitButton, aboutButton;
	@FXML
	private GridPane gridGame;
	@FXML
	private Button but00, but01, but02, but10, but11, but12, but20, but21, but22;
	@FXML
	private ImageView imgView22;

	private TicTacToeGameControl game = new TicTacToeGameControl();
	String actualPlayer;
	boolean player1, xTurn = true;

	Socket listenServer;
	PrintWriter writeToMainServer;
	Scanner listener;
	PrintWriter sendCordinates;

	/*
	 * M�todo a ser chamado para realizar a transi��o da tela de conex�o para a
	 * tela de jogo. Respons�vel por preparar todas as informa��es iniciais do
	 * jogo e iniciar a thread do mesmo para cada jogador.
	 */
	public void setDialogStage(String ip, String playerName) {
		try {
			/*
			 * Realiza a conex�o com o servidor principal.
			 */
			listenServer = new Socket(ip, 5000);
			writeToMainServer = new PrintWriter(listenServer.getOutputStream());
			writeToMainServer.println(playerName);
			writeToMainServer.flush();
			listener = new Scanner(listenServer.getInputStream());
			String isConnected = listener.nextLine();
			/*
			 * Verifica se a conex�o foi bem sucedida e altera o nome de cada
			 * jogador na tela de acordo com quem foi conectado
			 */
			if (isConnected.startsWith("CONNECTED")) {
				int port = Integer.parseInt(isConnected.split(" ")[2]);
				actualPlayer = isConnected.split(" ")[1];
				if (actualPlayer.equals("PLAYER1")) {
					player1 = true;
					nicknameP1Label.setText(playerName);
					nicknameP2Label.setText(isConnected.split(" ")[3]);
				} else {
					player1 = false;
					nicknameP2Label.setText(playerName);
					nicknameP1Label.setText(isConnected.split(" ")[3]);
				}

				listenServer = new Socket(ip, port);
				sendCordinates = new PrintWriter(listenServer.getOutputStream());

				/*
				 * Inicia a Thread que trata o jogo
				 */
				Listener listenerThread = new Listener();
				listenerThread.start();
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@FXML
	public void handlePauseButton() {

	}

	/*
	 * M�todo que verifica qual bot�o foi clicado e trata o evento que dever�
	 * ocorrer de acordo com o estado atual do jogo
	 */
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
			sendCordinates.println(row + " " + column);
			sendCordinates.flush();
			xTurn = !xTurn;
			verifyGameState();
		}

	}

	@FXML
	public void handleButtonAbout() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(AboutController.class.getResource("/tictactoe/views/About.fxml"));
		AnchorPane page = (AnchorPane) loader.load();

		Stage gameStage = new Stage();
		Scene scene = new Scene(page);
		gameStage.setScene(scene);
		gameStage.setTitle("Sobre o TicTacToe");
		
		gameStage.show();
	}
	
	@FXML
	public void handleQuitButton() {
		Stage stage = (Stage) quitButton.getScene().getWindow();
		stage.close();
		sendCordinates.println("FINISHED");
	}

	/*
	 * M�todo respons�vel por atualizar as informa��es na tela do jogador que
	 * estava aguardando a sua vez.
	 */
	public void refreshForOpponent(String row, String column) {

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
			verifyGameState();
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

	}

	/*
	 * M�todo que deve ser chamado a cada jogada. Ele verifica o estado atual do
	 * jogo e trata as mensagens de vit�ria, derrota ou empate
	 */
	public void verifyGameState() {
		if (game.getIsGameEnded()) {
			sendCordinates.println("FINISHED");
			sendCordinates.flush();
			but00.setDisable(true);
			but01.setDisable(true);
			but02.setDisable(true);
			but10.setDisable(true);
			but11.setDisable(true);
			but12.setDisable(true);
			but20.setDisable(true);
			but21.setDisable(true);
			but22.setDisable(true);

			Platform.runLater(new Runnable() {
				@Override
				public void run() {

					Alert winnerAlert = new Alert(AlertType.INFORMATION);
					if (game.getWinner().equals("X")) {
						if (actualPlayer.equals("PLAYER1")) {
							winnerAlert.setTitle("VICTORY!");
							winnerAlert.setHeaderText("YOU WIN!");
							winnerAlert.setContentText("CONGRATULATIONS! YOU'RE AWESOME!");
						} else {
							winnerAlert.setTitle("DEFEAT!");
							winnerAlert.setHeaderText("YOU LOSE!");
							winnerAlert.setContentText("DON'T GIVE UP! TRY HARDER!");
						}
					} else if (game.getWinner().equals("O")) {
						if (actualPlayer.equals("PLAYER2")) {
							winnerAlert.setTitle("VICTORY!");
							winnerAlert.setHeaderText("YOU WIN!");
							winnerAlert.setContentText("CONGRATULATIONS! YOU'RE AWESOME!");
						} else {
							winnerAlert.setTitle("DEFEAT!");
							winnerAlert.setHeaderText("YOU LOSE!");
							winnerAlert.setContentText("DON'T GIVE UP! TRY HARDER!");
						}
					} else if (game.getWinner().equals("N")) {
						winnerAlert.setTitle("DRAW");
						winnerAlert.setHeaderText("IT'S A DRAW!");
						winnerAlert.setContentText("DON'T GIVE UP! TRY HARDER!");
					}
					winnerAlert.showAndWait();
					Stage stage = (Stage) quitButton.getScene().getWindow();
					stage.close();
				}
			});
		}
	}

	/*
	 * Thread respons�vel por controlar a tela do jogo. � ela quem passa as
	 * informa��es para se atualizar as informa��es de cada jogada para o
	 * oponente
	 */
	class Listener extends Thread {

		@Override
		public void run() {
			String getValue = "";
			if (listener.hasNextLine()) {
				getValue = listener.nextLine();
			}
			while (!getValue.contains("FINISHED")) {
				String[] cordinates = getValue.split(" ");
				refreshForOpponent(cordinates[0], cordinates[1]);
				if (listener.hasNextLine()) {
					getValue = listener.nextLine();
				}
			}

		}
	}
}
