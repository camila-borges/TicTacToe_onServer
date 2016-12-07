package tictactoe.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AboutController implements Initializable{

	@FXML
	private Button buttonEntendi;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
	@FXML
	private void handleButtonEntendi(){
		Stage stage = (Stage) buttonEntendi.getScene().getWindow();
		stage.close();
	}

}
