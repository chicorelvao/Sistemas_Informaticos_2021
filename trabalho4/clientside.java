package trabalho4;
import java.util.*;
import java.rmi.registry.LocateRegistry; 
import java.rmi.registry.Registry;

import java.util.Scanner;
import java.io.IOException;
//import java.io.IOException;
import java.rmi.*;  


public class clientside {
	
	public static void main(String[] args) {	
		
		int invalidRequest = 0;
		
		//variavel que controla se o cliente se mantem ligado
		boolean end_connection= false;
		

		
		boolean option_menu1 = true;
		
		boolean option_menu2 = false;
		
		Registry registry = null;
		Interface interfaceServer;

		boolean isItValid = false;
		String user_login = "";
		
		boolean connectionFail = false;
		
		Scanner sc = new Scanner(System.in);
	
		//declaraÃƒÂ§ÃƒÂ£o de um objeto cliente que contem pubs
		Client user;
		
		System.out.println("***********");
		System.out.println("**WELCOME**");
		System.out.println("***********\n");
					
			
		

			try
			{	
				
		

				
				while(true) {
					try { // tenta obter as informações do User


						// Returns a reference to the remote object Registry on the specified host and port.
						registry = LocateRegistry.getRegistry(1234);
						interfaceServer = (Interface) registry.lookup("Implement");

						invalidRequest = 0;
						break; // se for bem sucedido sai do while()


					} catch (IOException e) { // se houver uma falha na conexão trata dessa falha

						System.out.println("> Connection lost, trying again in 3 seconds");
						invalidRequest++; // incrementa em 1 as tentativas de conexão
						Thread.sleep(3000); // espera 3s para tentar outra vez

						try {
							interfaceServer = (Interface) registry.lookup("Implement");														// registry

						} catch (Exception d) { // caso não encontre o Registry
							System.out.println("> Failed to establish connection");
						}

					} catch (Exception idk) { // apanha outros possiveis erros
						System.out.print("> An error has occurred: ");
						idk.printStackTrace();
					}

					if (invalidRequest == 10) { // quando tenta conectar-se 10x sem sucesso, desliga o programa
						System.out.println("> Program terminated. Cause: Lost Connection with Server");
						System.exit(0);
					}

				}
				
				
				
				
				
				
				// loop de log in
				while(!end_connection) {
			
					while(option_menu1) {


						System.out.println("1-LOGIN");
						System.out.println("2-EXIT");
						System.out.println("3-Create Account\n");

						
						switch (sc.nextLine()) {
						//acaso utilizador queira fazer conta
						case "1": {

							System.out.println("insert user email");
							user_login = sc.nextLine();
							System.out.println("insert password");
							String pass_login= sc.nextLine();
							//procura por todos os clientes da base de dados
							
							 
							while(true) {
							 try { // tenta obter as informações do User

									//procura por todos os clientes da base de dados
									option_menu1 = interfaceServer.loginVerify(user_login,pass_login);
									//option_menu1 ÃƒÂ© TRUE se o login for invalido 
									//option_menu1 ÃƒÂ© FALSE se o login for valido 
									isItValid = interfaceServer.isThisClientRegistred(user_login);
									invalidRequest = 0;
									break; // se for bem sucedido sai do while()


								} catch (IOException e) { // se houver uma falha na conexão trata dessa falha
									connectionFail = true;
									System.out.println("> Connection lost, trying again in 3 seconds");
									invalidRequest++; // incrementa em 1 as tentativas de conexão
									Thread.sleep(3000); // espera 3s para tentar outra vez

									try {
										interfaceServer = (Interface) registry.lookup("Implement");														// registry

									} catch (Exception d) { // caso não encontre o Registry
										System.out.println("> Failed to establish connection");
									}

								} catch (Exception idk) { // apanha outros possiveis erros
									System.out.print("> An error has occurred: ");
									idk.printStackTrace();
								}

								if (invalidRequest == 10) { // quando tenta conectar-se 10x sem sucesso, desliga o programa
									System.out.println("> Program terminated. Cause: Lost Connection with Server");
									System.exit(0);
								}

							}
							 
							 //option_menu1 ÃƒÂ© TRUE se o login for invalido 
							 //option_menu1 ÃƒÂ© FALSE se o login for valido 
							 if (option_menu1) {
								 
								 
								 if(isItValid) {
									 System.out.println("Your email is valid. Password incorrect");
								 }else {
									 if(connectionFail) {
										 System.out.println("Connection failed during log in. Try again.");
									 } else {
									 System.out.println("Your email is not yet registred in database. Sign Up");
								
									 }
								 }
								 
								 
								 System.out.println("Invalid Log In\n");
								 }else {
									 System.out.println("Valid Log In\n");
									 option_menu2 = true;
								 }
							break;
							
							
							
							
							
						}
						case "2": {
							//cliente nÃƒÂ£o quer fazer registo nem log in
							System.out.println("Disconnected \n");
							end_connection = true;
							option_menu1 = false;
							option_menu2 = false;
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
							
							
							int option_logUp = 3;
							
							while(true) {
								try { // tenta obter as informações do User


									option_logUp = interfaceServer.logUpRoutine(newname, newmail, newpassword, newaff);
									invalidRequest = 0;
									break; // se for bem sucedido sai do while()


								} catch (IOException e) { // se houver uma falha na conexão trata dessa falha

									System.out.println("> Connection lost, trying again in 3 seconds");
									invalidRequest++; // incrementa em 1 as tentativas de conexão
									Thread.sleep(3000); // espera 3s para tentar outra vez

									try {
										interfaceServer = (Interface) registry.lookup("Implement");														// registry

									} catch (Exception d) { // caso não encontre o Registry
										System.out.println("> Failed to establish connection");
									}

								} catch (Exception idk) { // apanha outros possiveis erros
									System.out.print("> An error has occurred: ");
									idk.printStackTrace();
								}

								if (invalidRequest == 10) { // quando tenta conectar-se 10x sem sucesso, desliga o programa
									System.out.println("> Program terminated. Cause: Lost Connection with Server");
									System.exit(0);
								}

							}
						
							//no logUp existem 3 casos que podem acontecer
							//1-o email inserido pelo cliente ÃƒÂ© repetido e nÃƒÂ£o ÃƒÂ© valido
							//2-a conta ÃƒÂ© criada com sucesso  eo cliente ÃƒÂ© adicionado ao ficheiro
							//3-o email inserido pelo ciente nÃƒÂ£o tem @mail no nome, ou seja tem um formato nÃƒÂ£o valido
							switch (option_logUp) {
							
							case 1:
								System.out.println("ERROR - Please insert a valid email");
								System.out.println("Email repeated\n");
								break;
							
							case 2:
								System.out.println("Acc created with success\n");
								break;
							case 3:
								System.out.println("ERROR - Please insert a valid email");
								System.out.println("Insert @mail for valid email\n");
								break;
							
							}
							


						}


						
					}
				

		
		}	
					
					if(option_menu2) {
						System.out.println("You have logged in.");
	
						
						
						while(true) {
							try { // tenta obter as informações do User

								user = interfaceServer.whosClient(user_login);

								System.out.println("Current user: " + user.getName());

								option_menu1 = mainMenu(sc, option_menu2, user,  interfaceServer, registry);
								invalidRequest = 0;
								break; // se for bem sucedido sai do while()


							} catch (IOException e) { // se houver uma falha na conexão trata dessa falha

								System.out.println("> Connection lost, trying again in 3 seconds");
								invalidRequest++; // incrementa em 1 as tentativas de conexão
								Thread.sleep(3000); // espera 3s para tentar outra vez

								try {
									interfaceServer = (Interface) registry.lookup("Implement");														// registry

								} catch (Exception d) { // caso não encontre o Registry
									System.out.println("> Failed to establish connection");
								}

							} catch (Exception idk) { // apanha outros possiveis erros
								System.out.print("> An error has occurred: ");
								idk.printStackTrace();
							} 

							if (invalidRequest == 10) { // quando tenta conectar-se 10x sem sucesso, desliga o programa
								System.out.println("> Program terminated. Cause: Lost Connection with Server");
								System.exit(0);
							}

						}
					}
					
		}
				
				
				System.out.println("Exited with success");
				invalidRequest=0;
				sc.close();
			}
		catch (Exception e) // catching Exception means that we are handling all errors in the same block
		{          // usually it is advisable to use multiple catch blocks and perform different error handling actions


			invalidRequest++;

			System.err.println("An error has occured!");
			e.printStackTrace();

			if(invalidRequest<=10) {
				System.out.println("Trying to reconnect in 3 seconds. Attemp number:"+invalidRequest);
				try{
					Thread.sleep(3000);
				} catch (InterruptedException e2) {
					// Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	}
	public static boolean mainMenu (Scanner sc, boolean optionMenu, Client user, Interface interfaceServer, Registry registry) throws InterruptedException, IOException, RemoteException  {
		//variavel para receber inputs do utilizador
		String input;
		boolean correctInput = false;
		while(optionMenu) {
			
			//menu apresnetado
			System.out.println("\n\nMENU USER:");
			System.out.println("1-Minhas publicações");
			System.out.println("2-Introduzir publicações");
			System.out.println("3-Publicações candidatas");
			System.out.println("4-Remover publicações");
			System.out.println("5-My Performance");
			System.out.println("6-Exit\n");
			
			//Ãƒâ€° pedido ao utilizador o que deseja
			input = sc.nextLine();
			
			//o input ÃƒÂ© recebido e analisado
			 switch(input){
			 

			case "1":
				
				System.out.println("Listar publicações:\n 1 - Por ano\n 2 - por citações");
				
				correctInput = false;
				
				while(!correctInput) {
					
					input = sc.nextLine();
					switch(input) {
						case "1":
							user = interfaceServer.printPubs(user, true);
							printPublications(user.getPubs());
							correctInput = true;
							
							break;
						case"2":
							user = interfaceServer.printPubs(user, false);
							printPublications(user.getPubs());
							correctInput = true;
							break;
						default:
							System.out.println("Please select 1 or 2.");
							
							break;
					}
					
				}
				
				//Print das pubs
				
				
			
				break;
	
			case "2":
				int[] addNewPubNumbers = new int[5];
				
				
				System.out.println("Add a new publication: ");
				System.out.println("Title: ");
				String title = sc.nextLine();
				System.out.println("Authors \n Example: Albert Einstein / John Mayer/JoeBiden\n");
				String[] authors = sc.nextLine().split("/");
				System.out.println("Year:");
				String year = sc.nextLine();
				System.out.println("Pages:");
				String page = sc.nextLine();
				System.out.println("Journal: ");
				String journal = sc.nextLine();
				System.out.println("Volume:");
				String volume = sc.nextLine();
				System.out.println("DOI:");
				String DOi = sc.nextLine();
				System.out.println("Citations:");
				String citationsNumb = sc.nextLine();
				boolean pubAdd = false;
				boolean intText = false;
				
				try {
					addNewPubNumbers[0] = Integer.parseInt(year);
					addNewPubNumbers[1] = Integer.parseInt(page);
					addNewPubNumbers[2] = Integer.parseInt(volume);
					addNewPubNumbers[3] = Integer.parseInt(DOi);
					addNewPubNumbers[4] = Integer.parseInt(citationsNumb);
					intText = true;
				} catch (Exception e) {
					System.out.println("Your input is invalid. Use numbers for year, page, volume, DOi and citationsNumb");
					System.out.println("");
				}
				
				if(intText) {
					//itera por todos os autore da publicaÃ§Ã£o
					for (String i: authors) {
						
							//caso um dos autores da publicaÃ§Ã£o seja o cliente logado no momento pode adicionar  apublicaÃ§Ã£o
					if  (user.getName().equals(i.trim())) {
							//Ã© possivel adicionar uma publicaÃ§Ã£o se:
							//-> caso o DOI nÃ£o exista jÃ¡ na base de dados (publicaÃ§Ã£o repetida 
						pubAdd = interfaceServer.addNewPub(title, journal, authors, addNewPubNumbers,user.getName());
						break; 
						} 
					}
			 
				}
				
				
				if(pubAdd) {
					System.out.println("Publication added.");
				} else {
					System.out.println("Use a different DOI number and your name.");
				}
				
				break;
					 
	
	
			case "3":
				//pedido ao server de pubs candidatas
				
					user = interfaceServer.requestPubs(user);
					
					
					
					//impressÃƒÂ£o de pubs candidatas
					int counter = 1;
					
					//controlo de entradas corretas. Prende o utilizador atÃƒÂ© este 
					//escolher um input adequado
					correctInput = false;
					
					//se existir pubs para adicionar
					if(!user.requestPubs.empty()) {
						//apresenta as pubs
						
						printPublications(user.requestPubs());
						System.out.println("Select publications you want to add.\nExample: 1 2 3 12 45 334");
						System.out.println("Press X to get out.");
						
						correctInput = false;
					
					} else {
						System.out.println("No more publications to add.");
						correctInput = true;
					}
					
					
					
					while(!correctInput) {
						//input de pubs candidatas
						input = sc.nextLine();
						correctInput = false;
						
						if(input.toUpperCase().equals("X")) {
							//sai do loop
							System.out.println("Pressed X");
							correctInput = true;
							
						} else {
							//separaÃƒÂ§ÃƒÂ£o dos nÃƒÂºmeros por espaco
							String[] numberString = input.split(" ");
							//conversÃƒÂ£o de texto para inteiro
							int textToNumber;
							//stack para enviar ao server
							Stack<Integer> numberInt = new Stack<Integer>();
							
							//contador para verificar se todos os numeros sao convertidos
							counter = 0;
							
							//cada texto ÃƒÂ© convertido num inteiro. 
							//Ãƒâ€° detetado o erro de inputs q nao pode ser convertidos
							for( String i : numberString) {
								//esperamos por erros
								
								try {
									//passagem de texto para int, o utilizador comeca por 1 e nao em 0
									textToNumber = Integer.parseInt(i) - 1;
									//o numero e inserido numa stack
									if(textToNumber < 0|| textToNumber > user.requestPubs.size() - 1) {
										System.out.println("Pick a number between 1 and " + user.requestPubs.size() + ".");
										break;
									}
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
							
							if(counter == numberString.length) {
								
								for(int j : numberInt) {
									//entao os objetos pubs sÃƒÂ£o adicionados numa stack auxiliar
									user.requestPubs().get(j).print();
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
				System.out.println("Remove a publication\n You can only remove publications that you added with your name.\n");
				printPublications(user.userPubs);
				
				
				
				int doi = 0;
				boolean pubInUser;
				
			
					while(true) {
						
							System.out.println("Pick a DOI number or Press X to get out: ");
							
							input = sc.nextLine();
							
							if(input.toUpperCase().equals("X")) {
								break;
							}
							pubInUser = false;
							
							try {
								
							doi = Integer.parseInt(input);
							
							for(Pub p : user.getPubs()) {
								if(doi == p.getDOI()) {
									pubInUser = true;
									break;
								}
							}
							
							} catch (Exception e) {
								System.out.println("User numbers for your input.");
							}
							
							
							
							
							
							if(pubInUser) {
								
							user = interfaceServer.removePub(user, doi);
							
				
									if(user.removedPub) {
										System.out.println("Your publication was removed.");
										break;
									} else {
										System.out.println("Pick a correct DOI number. You must be the author of the pub.");
										
									}
							
							} else {
								System.out.println("You can´t remove a publication that is not in your publications.");
							}
				
						
					}
				
				
				
				
				break;
	
	
			case "5":
				//da print das metricas
				System.out.println("Perfomance metric");
				System.out.println("Pick your H metric number: ");
				int H = 0;
				while(true) {
					try {
						H = Integer.parseInt(sc.nextLine());
						break;
					} catch (Exception e) {
						System.out.println("Use integer numbers for the H metric.");
					}
				}
				
				user = interfaceServer.performance(user, H);
					
				System.out.println("Total citations:" + user.citationScore[0]);
				System.out.println("H - metric:" + user.citationScore[1]);
				System.out.println("Hyundai - i10 - metric:" + user.citationScore[2]);

				
				break;	
	
	
			case "6":
				optionMenu = false;
				
				
				
				return true;
			default:
				System.out.println("Invalid option");
				break;
	
			}
			 
		}
		
		return false;

	}
	
	//da print de todas as publicações do cliente que estão nas suas publicações
	public static void printPublications(Stack<Pub> pub) {
		int counter = 1;
		System.out.println("Publications: ");
		
		if(pub.size() > 0) {
			for(Pub i : pub) {
				System.out.println("Publication: " + counter);
				i.print();
				counter++;
			} 
		} else {
			System.out.println("No publications to show. Add a few.");
		}
		
	}
	
}