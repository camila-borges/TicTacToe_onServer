package tictactoe.models;

public class TicTacToe {
	private boolean isGameEnded = false;
	private boolean xTurn = true;
	private String[][] gameBoard = new String[3][3];
	private int countX;
	private int countO;
	
	public TicTacToe(){
		for (int i = 0; i < gameBoard.length; i++) {
			for (int j = 0; j < gameBoard.length; j++) {
				this.gameBoard[i][j] = "";
			}
		}
	}
	
	/*public String drawValue(int row, int column){
		
		//if ( !this.xTurn == player ) { return null; }
		
		if(xTurn && !isGameEnded && gameBoard[row][column].equals("")){
			gameBoard[row][column] = "X";
			isGameComplete();
			xTurn = false;
			return "X";
		}else if(!xTurn && !isGameEnded && gameBoard[row][column].equals("")){
			gameBoard[row][column] = "O";
			isGameComplete();
			xTurn = true;
			return "O";
		}
		return gameBoard[row][column];
	}*/
	
	public String drawValue(int row, int column, boolean xTurn){
		
		String defaultStyle = "-fx-background-position: center center; " +
								"-fx-background-repeat: stretch;" +
								"-fx-background-size: 130px 130px;"+
								"-fx-background-color: transparent;"+
								"-fx-border-color:  #66cc00";
		
		//if ( !this.xTurn == player ) { return null; }
		
		if (xTurn && !isGameEnded && gameBoard[row][column].equals("")){
			gameBoard[row][column] = "X";
			isGameComplete();
			xTurn = false;
		} else if (!xTurn && !isGameEnded && gameBoard[row][column].equals("")){
			gameBoard[row][column] = "O";
			isGameComplete();
			xTurn = true;
		}
		
		if (gameBoard[row][column] == "X"){
			return "-fx-background-image: url('/resources/angryBeaversX.png'); " + defaultStyle;
		} else if (gameBoard[row][column] == "O"){
			return "-fx-background-image: url('/resources/angryBeaversO.png'); " + defaultStyle;
		} else {
			return defaultStyle;
		}
			
	}
	
	public String[][] getGameBoard(){
		return this.gameBoard;
	}
	
	public void isGameComplete(){
		
		//Verifica as linhas
		for(int i = 0; i < 3; i++){
			countX = countO = 0;
			for (int j = 0; j < 3; j++) {
				if(gameBoard[i][j] == "X"){
					countX++;
				}
				if(gameBoard[i][j] == "O"){
					countO++;
				}
			}
			
			if(countX == 3 || countO == 3){
				isGameEnded = true;
			}
		}
		
		//Verifica as colunas
		for(int i = 0; i < 3; i++){
			countX = countO = 0;
			for (int j = 0; j < 3; j++) {
				if(gameBoard[j][i] == "X"){
					countX++;
				}
				if(gameBoard[j][i] == "O"){
					countO++;
				}
			}
			
			if(countX == 3 || countO == 3){
				isGameEnded = true;
			}
		}
		
		//Verifica as Diagonais
		if((gameBoard[0][0] == "X" && gameBoard[1][1] == "X" && gameBoard[2][2] == "X") || 
				(gameBoard[0][0] == "O" && gameBoard[1][1] == "O" && gameBoard[2][2] == "O")){
			isGameEnded = true;
		}
		
		if((gameBoard[2][0] == "X" && gameBoard[1][1] == "X" && gameBoard[0][2] == "X") || 
				(gameBoard[2][0] == "O" && gameBoard[1][1] == "O" && gameBoard[0][2] == "O")){
			isGameEnded = true;
		}
		
		return;
	}
}
