package tictactoe.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class AnchorPaneWaitingScreenController implements Initializable {

	@FXML
	private Button quitButton, backButton;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	@FXML
	public void handleQuitButton(){
		Stage stage = (Stage) quitButton.getScene().getWindow();
		stage.close();
	}
	
	@FXML
	public void handleBackButton() throws IOException{
		 Stage stage = new Stage();
	     Pane myPane = null;
	     myPane = FXMLLoader.load(getClass().getResource("/tictactoe/views/VBoxLogin.fxml"));
	     Scene scene = new Scene(myPane);
	     stage.setScene(scene);

	     Stage prevStage = (Stage) quitButton.getScene().getWindow();
	     prevStage.close();
	     
	     stage.show();
	}
}
