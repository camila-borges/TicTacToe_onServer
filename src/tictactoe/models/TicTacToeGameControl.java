package tictactoe.models;

public class TicTacToeGameControl {
	private boolean isGameEnded = false;
	private String[][] gameBoard = new String[3][3];
	private int countX;
	private int countO;
	private String winner;

	public TicTacToeGameControl() {
		for (int i = 0; i < gameBoard.length; i++) {
			for (int j = 0; j < gameBoard.length; j++) {
				this.gameBoard[i][j] = "";
			}
		}
	}

	public String drawValue(int row, int column, boolean xTurn) {

		String defaultStyle = "-fx-background-position: center center; " + "-fx-background-repeat: stretch;"
				+ "-fx-background-size: 130px 130px;" + "-fx-background-color: transparent;"
				+ "-fx-border-color:  #66cc00";

		if (xTurn && !isGameEnded && gameBoard[row][column].equals("")) {
			gameBoard[row][column] = "X";
			isGameComplete();
			xTurn = false;
		} else if (!xTurn && !isGameEnded && gameBoard[row][column].equals("")) {
			gameBoard[row][column] = "O";
			isGameComplete();
			xTurn = true;
		}

		if (gameBoard[row][column] == "X") {
			return "-fx-background-image: url('/resources/angryBeaversX.png'); " + defaultStyle;
		} else if (gameBoard[row][column] == "O") {
			return "-fx-background-image: url('/resources/angryBeaversO.png'); " + defaultStyle;
		} else {
			return defaultStyle;
		}

	}

	public String[][] getGameBoard() {
		return this.gameBoard;
	}

	public void isGameComplete() {

		// Rows verifier
		for (int i = 0; i < 3; i++) {
			countX = countO = 0;
			for (int j = 0; j < 3; j++) {
				if (gameBoard[i][j] == "X") {
					countX++;
				}
				if (gameBoard[i][j] == "O") {
					countO++;
				}
			}

			if (countX == 3) {
				isGameEnded = true;
				winner = "X";
			} else if (countO == 3) {
				isGameEnded = true;
				winner = "O";
			}
		}

		// Columns verifier
		for (int i = 0; i < 3; i++) {
			countX = countO = 0;
			for (int j = 0; j < 3; j++) {
				if (gameBoard[j][i] == "X") {
					countX++;
				}
				if (gameBoard[j][i] == "O") {
					countO++;
				}
			}

			if (countX == 3) {
				isGameEnded = true;
				winner = "X";
			} else if (countO == 3) {
				isGameEnded = true;
				winner = "O";
			}
		}

		// Diagonals verifier
		if ((gameBoard[0][0] == "X" && gameBoard[1][1] == "X" && gameBoard[2][2] == "X")) {
			isGameEnded = true;
			winner = "X";
		}

		if ((gameBoard[0][0] == "O" && gameBoard[1][1] == "O" && gameBoard[2][2] == "O")) {
			isGameEnded = true;
			winner = "O";
		}

		if ((gameBoard[2][0] == "X" && gameBoard[1][1] == "X" && gameBoard[0][2] == "X")) {
			isGameEnded = true;
			winner = "X";
		}

		if ((gameBoard[2][0] == "O" && gameBoard[1][1] == "O" && gameBoard[0][2] == "O")) {
			isGameEnded = true;
			winner = "O";
		}

		return;
	}

	public boolean getIsGameEnded() {
		return this.isGameEnded;
	}

	public String getWinner() {
		if(winner.isEmpty()){
			return "N";
		}
		return winner;
	}
}
