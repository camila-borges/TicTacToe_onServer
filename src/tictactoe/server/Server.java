package tictactoe.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import tictactoe.threads.TicTacToe;

public class Server {

	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(5000);
			ArrayList<TicTacToe> connectedList = new ArrayList<>();
			
			for (;;) {				
				Socket client1 = server.accept();
				Socket client2 = server.accept();
				
				TicTacToe game = new TicTacToe(client1, client2);				
				game.start();
				connectedList.add(game);
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
}
