package tictactoe.network;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class TicTacToeThread extends Thread {

	Socket player1;
	Socket player2;
	PrintStream player1Stream, player2Stream;
	ServerSocket match;
	Scanner player1Scanner, player2Scanner;
	boolean xTurn = true;
	boolean isGameComplete = false;

	static List<Integer> portsList = new ArrayList<Integer>();

	public TicTacToeThread(Socket player1, Socket player2, String player1Name, String player2Name) {
		this.player1 = player1;
		this.player2 = player2;

		try {
			Random randomPort = new Random();
			int portNumber = randomPort.nextInt(50) + 5001;

			while (portsList.contains(portNumber) && portsList.size() < 50) {
				portNumber = randomPort.nextInt(50) + 5001;
			}

			portsList.add(portNumber);
			match = new ServerSocket(portNumber);
			System.out.println("Servidor match iniciado, porta: " + portNumber);

			player1Stream = new PrintStream(player1.getOutputStream());
			player1Stream.println("CONNECTED PLAYER1 " + portNumber + " " + player2Name);
			player1Stream.flush();

			player2Stream = new PrintStream(player2.getOutputStream());
			player2Stream.println("CONNECTED PLAYER2 " + portNumber + " " + player1Name);
			player2Stream.flush();

			player1 = match.accept();
			System.out.println("Player 1 entrou na partida, porta " + portNumber + player1Name);
			player2 = match.accept();
			//player2Stream.println("READY");
			//player2Stream.flush();
			//player1Stream.println("READY");
			//player1Stream.flush();
			System.out.println("Player 2 entrou na partida, porta " + portNumber);
			player1Scanner = new Scanner(player1.getInputStream());
			player2Scanner = new Scanner(player2.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			player1Stream = new PrintStream(player1.getOutputStream());
			player2Stream = new PrintStream(player2.getOutputStream());

			while (true) {
				// Player 1 turn
				String player1Response = " ";
				
					player1Response = player1Scanner.nextLine();
					player2Stream.println(player1Response);
					player2Stream.flush();
					xTurn = false;
				

				// Player 2 turn
				String player2Response = " ";
				
					player2Response = player2Scanner.nextLine();
					player1Stream.println(player2Response);
					player1Stream.flush();
					xTurn = true;
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
