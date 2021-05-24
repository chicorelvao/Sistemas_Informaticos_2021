
package si_2021.trabalho4;
import java.util.*;
import java.rmi.registry.LocateRegistry; 
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Scanner;
import java.rmi.*;  
import java.rmi.server.*;  

public class clientside {
	
	public static void main(String[] args) {	
		int invalidrequest = 0;
		
		//variavel que controla se o cliente se mantem ligado
		boolean end_connection= false;
		
		boolean valid_login = false;
		
		boolean option_menu1 = true;
		
		boolean option_menu2 = false;
		
		boolean valid_acc = true;
		
		String currentUser = "";
		
		
		Scanner sc = new Scanner(System.in);
	
		//declara��o de um objeto cliente que contem pubs
		Client user;
		
		System.out.println("***********");
		System.out.println("**WELCOME**");
		System.out.println("***********\n");
					
			
		

			try
			{	
				// Returns a reference to the remote object Registry on the specified host and port.
				Registry registry = LocateRegistry.getRegistry(1234);
				Interface interfaceServer = (Interface) registry.lookup("Implement");



				while(!end_connection) {
			
					while(option_menu1) {


						System.out.println("1-LOGIN");
						System.out.println("2-EXIT");
						System.out.println("3-Create Account\n");

						
						switch (sc.nextLine()) {
						//acaso utilizador queira fazer conta
						case "1": {

							System.out.println("insert user email");
							String user_login= sc.nextLine();
							System.out.println("insert password");
							String pass_login= sc.nextLine();
							//procura por todos os clientes da base de dados
							
							 option_menu1 = interfaceServer.loginVerify(user_login,pass_login);
							 
							 
							break;
						}
						case "2": {
							//cliente n�o quer fazer registo nem log in
							System.out.println("Disconnected \n");
							end_connection = true;
							option_menu1 = false;
							break;
						}
						//caso utilziadr queira fazer conta
						case "3": {

							//inserir os dados de registo de utilzador
							System.out.println("insert name");
							String newname= sc.nextLine();
							System.out.println("insert mail");
							String newmail= sc.nextLine();
							System.out.println("insert password");
							String newpassword= sc.nextLine();
							System.out.println("insert affiliation");
							String newaff= sc.nextLine();
							
							
							interfaceServer.logUpRoutine(newname, newmail, newpassword, newaff);
							


						}


						
					}
				

		
		}	
					System.out.println("You have loged in.");

					//user = interfaceServer.;
					//mainMenu(sc, option_menu2, user,  interfaceServer);
				
		}
				
				
				System.out.println("Exited with success");
				invalidrequest=0;
				sc.close();
			}
		catch (Exception e) // catching Exception means that we are handling all errors in the same block
		{          // usually it is advisable to use multiple catch blocks and perform different error handling actions


			invalidrequest++;

			System.err.println("An error has occured!");
			e.printStackTrace();

			if(invalidrequest<=10) {
				System.out.println("Trying to reconnect in 3 seconds. Attemp number:"+invalidrequest);
				try{
					Thread.sleep(3000);
				} catch (InterruptedException e2) {
					// Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	}

	
	public static void mainMenu (Scanner sc, boolean optionMenu, Client user, Interface interfaceServer) {
		//variavel para receber inputs do utilizador
		String input;
		while(optionMenu) {
			
			//menu apresnetado
			System.out.println("\n\nMENU USER:");
			System.out.println("1-Minhas publica��es");
			System.out.println("2-Introduzir publica��es");
			System.out.println("3-Publica��es candidatas");
			System.out.println("4-Remover publica��es");
			System.out.println("5-My Performance");
			System.out.println("6-Exit\n");
			
			//� pedido ao utilizador o que deseja
			input = sc.nextLine();
			
			//o input � recebido e analisado
			 switch(input){
			 

			case "1":
				
				System.out.println("Listar publica��es:\n 1 - Por ano\n 2 - por cita��es");
				input = sc.nextLine();
				
				switch(input) {
					case "1":
						interfaceServer.printPubs(user, true);
						printPublications(user.getPubs());
						
						break;
					case"2":
						interfaceServer.printPubs(user, false);
						printPublications(user.getPubs());
						break;
					default:
						System.out.println("Please select 1 or 2.");
						break;
				}
				
				//Print das pubs
				
				
			
				break;
	
			case "2":
				break;
	
	
			case "3":
				//pedido ao server de pubs candidatas
				
					interfaceServer.requestPubs(user);
					
					//impress�o de pubs candidatas
					int counter = 1;
					
					//controlo de entradas corretas. Prende o utilizador at� este 
					//escolher um input adequado
					boolean correctInput = false;
					
					//se existir pubs para adicionar
					if(!user.requestPubs.empty()) {
						//apresenta as pubs
						
						printPublications(user.requestPubs());
						System.out.println("Select pubs. Example: 1 2 3 12 45 334");
						System.out.println("Press X to get out.");
						correctInput = false;
					
					} else {
						System.out.println("No more publications to add.");
						correctInput = true;
					}
					
					
					
					while(!correctInput) {
						//input de pubs candidatas
						input = sc.nextLine();
						
						if(input.toUpperCase().equals("X")) {
							//sai do loop
							System.out.println("Pressed X");
							correctInput = true;
							
						} else {
							//separa��o dos n�meros por espaco
							String[] numberString = input.split(" ");
							//convers�o de texto para inteiro
							int textToNumber;
							//stack para enviar ao server
							Stack<Integer> numberInt = new Stack<Integer>();
							
							//contador para verificar se todos os numeros sao convertidos
							counter = 1;
							
							//cada texto � convertido num inteiro. 
							//� detetado o erro de inputs q nao pode ser convertidos
							for( String i : numberString) {
								//esperamos por erros
								try {
									//passagem de texto para int
									textToNumber = Integer.parseInt(i);
									//o numero e inserido numa stack
									numberInt.push(textToNumber);
								} catch (Exception e) {
										System.out.println("Please select a correct number.");
									break;
								}
								//o counter aumenta
								counter++;
							}
							
							Stack<Pub> auxPubs = new Stack<Pub>();
							
							//se todos os inputs tiverem sido convertidos
							if(counter == numberInt.size()) {
								for(int j : numberInt) {
									//entao os objetos pubs s�o adicionados numa stack auxiliar
									auxPubs.push(user.requestPubs().get(j));
								}
								//a stack do utilizador e atualizada
								user.userPubsUpdate(auxPubs);
								correctInput = true;
							}
							
						}
					}	

				
				break;
	
	
			case "4":
				break;
	
	
			case "5":
				interfaceServer.performance(user);
				System.out.println("Performance: " + user.citationScore);
				break;	
	
	
			case "6":
				
				break;
	
			default:
				System.out.println("Invalid option");
				break;
	
			}
			 
		}

	}
	
	
	public static void printPublications(Stack<Pub> pub) {
		int counter = 0;
		System.out.println("Publications: ");
		for(Pub i : pub) {
			System.out.println("Publication: " + counter);
			i.print();
			counter++;
		}
	}
	
}
