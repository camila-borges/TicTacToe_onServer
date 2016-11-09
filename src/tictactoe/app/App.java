package tictactoe.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application{

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/tictactoe/views/AnchorPaneInGame.fxml"));

        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.sizeToScene();

        stage.setTitle("TicTacToe");
        stage.show();
        stage.toFront();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
