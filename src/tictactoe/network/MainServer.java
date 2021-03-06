package tictactoe.network;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * Classe respons�vel por fazer a conex�o de dois jogadores no servidor principal, de porta fixa.
 */

public class MainServer {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(5000);
			System.out
					.println("Servidor ligado porta: 5000!" + "\nIP: " + Inet4Address.getLocalHost().getHostAddress());
			ArrayList<TicTacToeThread> connectedList = new ArrayList<>();
			Scanner player1Scan, player2Scan;

			for (;;) {
				Socket client1 = server.accept();
				System.out.println("Player 1 conectado.");
				player1Scan = new Scanner(client1.getInputStream());
				String player1Name = player1Scan.nextLine();

				Socket client2 = server.accept();
				System.out.println("Player 2 conectado.");
				player2Scan = new Scanner(client2.getInputStream());
				String player2Name = player2Scan.nextLine();

				/*
				 * Instancia um objeto da classe TicTacToeThread, passando os
				 * jogadores por par�metro.
				 */
				TicTacToeThread game = new TicTacToeThread(client1, client2, player1Name, player2Name);
				game.start();
				/*
				 * Adi��o dos jogadores em uma lista para controle do servidor,
				 * visando implementa��es futuras.
				 */
				connectedList.add(game);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
