package trabalho3.Trabalho_3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.Stack;
import java.util.HashMap;


public class Client { 																// CLIENTE

	public static void main(String args[]) throws IOException {
		Socket socket = new Socket("localhost", 1234); 					// Abrir conex�o com o servidor, na ponte 7000

		InputStream in = socket.getInputStream();
		DataInputStream dataIn = new DataInputStream(in);
		OutputStream out = socket.getOutputStream();
		DataOutputStream dataOut = new DataOutputStream(out);
		boolean acabarJogo = true;

		
		Stack<Integer> StackOne = new Stack<Integer>();      //torre direita		
		Stack<Integer> StackTwo = new Stack<Integer>();      //stack intermedia
		Stack<Integer> StackThree = new Stack<Integer>();      //stack esquerda	
		
		StackOne.push(1000);
		StackTwo.push(1000);
		StackThree.push(1000);
		
		gameStart();
		
		while (acabarJogo) {

			Scanner sc = new Scanner(System.in);
			

			
			// Ler a resposta do servidor
			String serverResponse = dataIn.readUTF();

							

			switch(serverResponse){
			
			case "INIT":
				
				System.out.println("Connect to server 'Torre de Hanoy' [Y/N]: ");
				dataOut.writeUTF(sc.nextLine());
				break;
				
			case "PROTOCOL_ERROR":

				System.out.println("Connection timed out");
				acabarJogo=false;
				break;


			case "LOGIN":	

				System.out.println("The server replied: " + serverResponse);
				System.out.println("insert username: ");
				dataOut.writeUTF(sc.nextLine());
				System.out.println("insert password: ");
				dataOut.writeUTF(sc.nextLine());
				break;


			case "INVALID_CREDENTIAL":

				System.out.println("Invalid Login - Try again [Y/N]: ");
				dataOut.writeUTF(sc.nextLine().toUpperCase());
				boolean invalid = true;
				while (invalid) 
				{
					String newtry = dataIn.readUTF();
					switch(newtry) {
					
					case "TRY_AGAIN":
						System.out.println("Insert username again ");
						dataOut.writeUTF(sc.nextLine());
						System.out.println("Insert password again ");
						dataOut.writeUTF(sc.nextLine());
						newtry = dataIn.readUTF();
						break;
					case "VALID_CREDENTIAL":
						System.out.println("Valid Login!");
						invalid = false;
						break;
					case "INVALID_CREDENTIAL":
						System.out.println("Invalid Login - Try again [Y/N]: ");
						dataOut.writeUTF(sc.nextLine().toUpperCase());
						break;
					case "NO_TRY":
						System.out.println("Connection timed out");
						//invalid = false;
						break;
					}
					
					break;
				}

				

			case "VALID_CREDENTIAL":

				System.out.println("Valid Login!");
				//invalid = false;
				break;
			
			case "DISK_NUMBER":
				System.out.println("* Insert number of disks between 3 and 10 to continue: ");
				String diskNumber = sc.nextLine();
				dataOut.writeUTF(diskNumber);
				
				switch(dataIn.readUTF()) {
					case "DISKS_ACCEPTED":
						System.out.println("You picked " + diskNumber + " disks");
						break;
						
					case "DISKS_DECLINED":
						System.out.println("Please select a number between 3 and 10.");
						break;
						
					case "DISKS_DECLINED_ERROR":
						System.out.println("Please insert a number.");
						break;
				}
				break;
				
			case "PIN_VERIFIER":

				System.out.println("* Insert initial pin: ");
				String initialpin = sc.nextLine();
				dataOut.writeUTF(initialpin);
				
				System.out.println("* Insert final pin: ");
				String finalpin = sc.nextLine();
				dataOut.writeUTF(finalpin);

				break;
			case "GAME_STARTED":
				clientUtil.welcome();
				
				break;
			case "PLAY":
				clientUtil.playOptions();
				String play = sc.nextLine();
				dataOut.writeUTF(play);
				break;
				
			case "STACK_ONE_ADD":
				
				break;
			
			case "NO_TRY":

				System.out.println("Client doesn't want to connect");
				//invalid = false;
				break;

			case "DRAW":

				System.out.println(dataIn.readUTF());    
				break;

				
				
			case "MENU":
				displayMenu();
				break;
				
				
				
			case "END_GAME":
				dataIn.readUTF();
				System.out.println(dataIn.readUTF());
				displayMenu();
				String menuoption= sc.nextLine();
				
				switch (menuoption){
				case "1":
					dataOut.writeUTF("OPTION1");
					break;
					
				case "2":
					dataOut.writeUTF("OPTION2");
					break;
					
				case"Q":
					dataOut.writeUTF("OPTIONQ");
					break;
					
				
				}
				
				
				
			default:
				break;

			}

		}

	}
	
	
	public static void gameStart() {
		
		//Recep��o ao jogador
		System.out.println("*".repeat(25));
		System.out.println("* Welcome to Hanoi Tower *");
		System.out.println("*".repeat(25)+"\n\n\n");
		
		
		}
	
	public static void displayMenu(){

		System.out.println("---------Select and Option----------\n");
		System.out.println("1-Play");
		System.out.println("2-See stats");
		System.out.println("Q-Stop");
		}
	
}
	
	
	






