package tictactoe.threads;

import java.util.Scanner;

public class Listener extends Thread {
	
	Scanner listener;
	
	public Listener(Scanner listener){
		this.listener = listener;
	}
	
	@Override
	public void run() {	
		
		String getValue = listener.nextLine();
		while(!getValue.equals("X") && !getValue.equals("O")){
			String[] cordinates = getValue.split(" ");
		}
	}
}
