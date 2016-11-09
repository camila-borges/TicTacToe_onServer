package tictactoe.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class AnchorPaneInGameController implements Initializable{

	@FXML
	private Label nicknameP1Label, nicknameP2Label, scoreP1Label, scoreP2Label;
	@FXML
	private Button pauseButton, quitButton;
	@FXML
	private GridPane gridGame;
	@FXML
	private ImageView img00, img01, img02, img10,img11, img12, img20, img21, img22;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	@FXML
	public void handlePauseButton(){
	
	}
	
	@FXML
	public void handleQuitButton(){
	
	}
}
