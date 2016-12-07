package tictactoe.network;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

public class TicTacToeThread extends Thread {

	Socket player1;
	Socket player2;
	PrintStream player1Stream, player2Stream;
	ServerSocket match;
	Scanner player1Scanner, player2Scanner;

	static List<Integer> portsList = new ArrayList<Integer>();

	public TicTacToeThread(Socket player1, Socket player2, String player1Name, String player2Name) {
		this.player1 = player1;
		this.player2 = player2;

		try {
			/*
			 * Gera uma porta aleatória no intervalo entre 5001 e 5051 para
			 * gerar a conexão de dois jogadores no servidor de controle da
			 * partida.
			 */
			Random randomPort = new Random();
			int portNumber = randomPort.nextInt(50) + 5001;

			/*
			 * Verifica se a porta não está sendo utilizada.
			 */
			while (portsList.contains(portNumber) && portsList.size() < 50) {
				portNumber = randomPort.nextInt(50) + 5001;
			}

			portsList.add(portNumber);
			match = new ServerSocket(portNumber);
			System.out.println("Servidor match iniciado, porta: " + portNumber);

			/*
			 * Passa as mensagens de conexão bem sucedida para verificar se a
			 * partida pode ser iniciada.
			 */
			player1Stream = new PrintStream(player1.getOutputStream());
			player1Stream.println("CONNECTED PLAYER1 " + portNumber + " " + player2Name);
			player1Stream.flush();

			player2Stream = new PrintStream(player2.getOutputStream());
			player2Stream.println("CONNECTED PLAYER2 " + portNumber + " " + player1Name);
			player2Stream.flush();

			player1 = match.accept();
			System.out.println("Player 1 entrou na partida, porta " + portNumber + player1Name);
			player2 = match.accept();

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

			/*
			 * Thread responsável pela troca de informações durante o jogo.
			 */

			while (true) {

				try {
					// Player 1 turn
					String player1Response = " ";

					player1Response = player1Scanner.nextLine();
					/*
					 * Passa a informação da jogada do player 1 para o player 2
					 */
					player2Stream.println(player1Response);
					player2Stream.flush();

					// Player 2 turn
					String player2Response = " ";
					player2Response = player2Scanner.nextLine();
					/*
					 * Passa a informação da jogada do player 2 para o player 1
					 */
					player1Stream.println(player2Response);
					player1Stream.flush();
					/*
					 * Essa exceção é tratada pois ocorria ao final do jogo,
					 * pois os players aguardarão uma informação mesmo que o
					 * jogo acabe.
					 */
				} catch (NoSuchElementException e) {
					System.out.println("Jogo encerrado!");
					break;
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
