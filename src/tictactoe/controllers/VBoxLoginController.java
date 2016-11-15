package tictactoe.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class VBoxLoginController implements Initializable {

	@FXML
	private Button quitButton, loginButton;
	@FXML
	private TextField nicknameTextField, ipTextField;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@FXML
	public void handleQuitButton() {
		Stage stage = (Stage) quitButton.getScene().getWindow();
		stage.close();
	}

	@FXML
	public void handleLoginButton() throws IOException {

		if (nicknameTextField.getText().isEmpty() || ipTextField.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("EMPTY FIELD!");
			alert.setHeaderText("Both nickname or ip are empty!");
			alert.setContentText("You must fill all the fields before start playing!");
			alert.show();
		} else {
			Stage stage = (Stage) quitButton.getScene().getWindow();
			stage.close();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(AnchorPaneInGameController.class.getResource("/tictactoe/views/AnchorPaneInGame.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			Stage gameStage = new Stage();
			gameStage.setTitle("TicTactoe!");
			Scene scene = new Scene(page);
			gameStage.setScene(scene);

			AnchorPaneInGameController controller = loader.getController();

			controller.setDialogStage(ipTextField.getText(), nicknameTextField.getText());

			gameStage.show();
		}
	}

}
