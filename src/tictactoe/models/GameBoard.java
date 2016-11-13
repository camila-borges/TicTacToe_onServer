package tictactoe.models;

public class GameBoard {

	private boolean xTurn = true;
	private String[][] gameBoard = new String[3][3];
	private int countX;
	private int countO;

	public GameBoard() {
		for (int i = 0; i < gameBoard.length; i++) {
			for (int j = 0; j < gameBoard.length; j++) {
				this.gameBoard[i][j] = "";
			}
		}
	}
	
	public void insertElement(String row, String column){
		
		int x = Integer.parseInt(row);
		int y = Integer.parseInt(column);
		this.gameBoard[x][y] = xTurn ? "X" : "O"; 
	}

	public String isGameComplete() {

		// Verifica as linhas
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
				return "X";
			}else if (countO == 3){
				return "O";
			}
		}

		// Verifica as colunas
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
				return "X";
			}else if (countO == 3){
				return "O";
			}
		}

		// Verifica as Diagonais
		if (gameBoard[0][0] == "X" && gameBoard[1][1] == "X" && gameBoard[2][2] == "X"){
			return "X";
			
		}else if(gameBoard[0][0] == "O" && gameBoard[1][1] == "O" && gameBoard[2][2] == "O"){
			return "O";
		}

		if (gameBoard[2][0] == "X" && gameBoard[1][1] == "X" && gameBoard[0][2] == "X"){
			return "X";
			
		}else if(gameBoard[2][0] == "O" && gameBoard[1][1] == "O" && gameBoard[0][2] == "O"){
			return "O";
		}

		return "N";
	}
	
	public String[][] getGameBoard(){
		return this.gameBoard;
	}
}
