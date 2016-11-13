package tictactoe.threads;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import tictactoe.models.GameBoard;

public class TicTacToe extends Thread {

	Socket player1;
	Socket player2;
	PrintStream player1Stream, player2Stream;
	ServerSocket match;
	GameBoard gameBoard;
	
	static List<Integer> portsList = new ArrayList<Integer>();
	
	public TicTacToe(Socket player1, Socket player2){
		gameBoard = new GameBoard();
		this.player1 = player1;
		this.player2 = player2;
		
		try {
			Random randomPort = new Random();
			int portNumber = randomPort.nextInt(50) + 5001;
			
			while(portsList.contains(portNumber) && portsList.size() < 50 ){
				portNumber = randomPort.nextInt(50) + 5001;
			}
			
			portsList.add(portNumber);
			match = new ServerSocket(portNumber);
			
			player1Stream = new PrintStream(player1.getOutputStream());
			player1Stream.println("CONNECTED " + portNumber);
			player1Stream.flush();
			player1Stream.close();
			player2Stream = new PrintStream(player2.getOutputStream());
			player2Stream.println("CONNECTED " + portNumber);
			player2Stream.flush();
			player2Stream.close();
			
			player1 = match.accept();
			player2 = match.accept();			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		try {
			Scanner player1Scanner = new Scanner(player1.getInputStream());
			Scanner player2Scanner = new Scanner(player2.getInputStream());
			player1Stream = new PrintStream(player1.getOutputStream());
			player2Stream = new PrintStream(player2.getOutputStream());
			
			while(gameBoard.isGameComplete().equals("N")){				
				String player1Response = player1Scanner.nextLine();
				String[] player1Position = player1Response.split(" "); 
				String player2Response = player2Scanner.nextLine();
				String[] player2Position = player2Response.split(" ");
				
				gameBoard.insertElement(player1Position[0], player1Position[1]);
				gameBoard.insertElement(player2Position[0], player2Position[1]);
				
				player1Stream.println(player2Response);
				player1Stream.flush();
				player2Stream.println(player1Response);
				player2Stream.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
