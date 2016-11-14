package tictactoe.network;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(5000);
			System.out.println("Servidor ligado porta: 5000!" + "IP: " + Inet4Address.getLocalHost().getHostAddress());
			ArrayList<TicTacToeThread> connectedList = new ArrayList<>();
			
			for (;;) {				
				Socket client1 = server.accept();
				System.out.println("Player 1 conectado.");
				Socket client2 = server.accept();
				System.out.println("Player 2 conectado.");
				TicTacToeThread game = new TicTacToeThread(client1, client2);				
				game.start();
				connectedList.add(game);				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
}
