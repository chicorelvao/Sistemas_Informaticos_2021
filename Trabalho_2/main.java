package trabalho23;

import java.util.*;



public class main {
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);

		//n�mero de jogadores (para facilitaro debug)
		int numberPlayers = 4;


		//classe banner d� print do eventos mais importantes
		banner.begin();
		
		//pedido do nome do utilizador
		System.out.println("------ Please, insert your name:");
		System.out.println("\n\n");
		String userName = sc.nextLine();
		
		//um objeto rand � criado para criar numeros aleatorios para o primeiro dealer e o primeiro a jogar
		Random rand = new Random();
		
		//string com 8 nomes para ser mais f�cil de iterar
		String[] names_aux = {userName, "bot_1", "bot_2", "bot_3",userName, "bot_1", "bot_2", "bot_3"};

		//o primeiro jogador � aleat�rio
		int firstPlayer = rand.nextInt(numberPlayers);
		
		//Um variavel para definir que � o dealer a cada ronda, por agora � inicializada com 0
		int nextDealer = 0;
		//contador do n�mero de jogos, no jogo total
		int gameCounter = 1;
		
		
//--------------------------------------------------------------------------------
		//declara��o das 28 tiles
		//A classe Tiles permite definir objetos dominos
		Tiles[] tilesBook = new Tiles[28];

		int k = 0;
		                
		//s�o necess�rias 27 pe�as
		for (int i = 0; i < 7; i++) {

			for (int j = 0; j < 7; j++) {
				/* A pe�as existentes podem ser vistas como metade de uma matriz,
				em que i � o eixo do x  e j � o eixo do y.
				*/
				if (i >= j) {
					//S� queremos metade das pe�as, ou seja se x>y, ent�o as pe�as n�o s�o contadas
					tilesBook[k] = new Tiles(i,j);
					//Quando uma pe�a � criada, � contada, para verificar se foram criadas 28 pe�as
					k++;
					
				}
			}	
		}

//--------------------------------------------------------------------------

		//S�o necess�rios 4 jogadores, 1 humano e 3 bots
		Player Bot_one = new Player();
		Player Bot_two = new Player();
		Player Bot_three = new Player();
		Player Human = new Player();
		
		//Array para iterar sobre os jogadores, para evitar alguns loops extensos
		Player Players_aux[] = new Player[] {Human, Bot_one, Bot_two, Bot_three, Human, Bot_one, Bot_two, Bot_three};
		
		//� necess�rio uma mesa
		Board table = new Board();
		
		//Array com todos os jogadores que necessita de loops complexos para ser iterado, dado que todos os jogadores t�m de ser percorridos
		Player Players_aux2[] = new Player[] {Human, Bot_one, Bot_two, Bot_three};
		
		//boolean para verificar se o jogo total � terminado ou n�o, ou seja quando alguem passa os 50 pontos
		boolean running = true;
		
		while(running) {
			//Contador de jogos
			System.out.println("-----New Game------\n" + "-----Game: "+ gameCounter + "-------\n" + "-----New Game------\n");
				
	
			//o dealer � chamado para baralhar e o pr�ximo dealer � definido
			nextDealer =  dealerRoutine(gameCounter, Players_aux, deckShuffle(), tilesBook, nextDealer, numberPlayers, names_aux);
			
			//Come�a ent�o o jogo. Quando o jogo termina, o jogador que ganha � o primeiro a jogar no proximo jogo
			firstPlayer = gameRoutine(userName, names_aux, numberPlayers, firstPlayer, Players_aux2, table);
			
			//verifica se alguem j�a chegou aos 50 pontos e termina o jogo caso necess�rio
			running = GameOver(Players_aux, userName);
			
			//reset routine dos jogadores: limpas os dominos das maos e os pontos por ronda
			for(int g = 0; g < numberPlayers; g++) {
				Players_aux2[g].resetPlayer();
			}
			//os dominos em cima da mesa s�o limpos
			table.clearBoard();
			
			//counter do jogo � incrementado se o jogo total nao for terminado
			gameCounter++;
		}
		
		


			

			sc.close(); //scan � fechado para evitar erros mesmo ap�s o codigo terminar
	}
	
	
	
	//fun��o que permite encontrar o valor minimo de um array, assim como o seu index
	public static int[] getMin(int[] array){ 
	    int aux = array[0]; 
	  
	    int arrayReturn[] = new int[2];
	    arrayReturn[0] = aux; 
	    arrayReturn[1] = 0;
	    
	    for(int i=1; i<array.length; i++){ 
	      if(array[i] < aux){ 
	    	  
	    	  aux = array[i];
		      arrayReturn[0] = array[i]; 
		      arrayReturn[1] = i;
	      } 
	    } 
	    return arrayReturn; //� retornado o valor minimo e o index
	  } 
	
	//Rotina para um �nico jogo
	public static int gameRoutine(String userName, String[] names_aux, int numberPlayers, int firstPlayer, Player[] Players_aux2, Board table) {
		
		int lastWinner = firstPlayer; //O primeiro jogador a jogar � definido recursivamente
		
		int roundStarter;
		int roundShow = 1;//Todos os jogos come�am na primeira ronda, este valor � usado nos prints
		
		boolean gameOnline = true;//boolean que mantem o jogo em p�
		int round = 1; //contador de rondas interno, uma ronda so � contada quando � todos o jogadores jogam, n�o quando o index de um array � 0
		
		//� apresentada a ordem de jogada
		System.out.println("Game order: \n");

		for (int k=0; k<numberPlayers;k++ ) {

			System.out.println(k + 1 + ": "+ names_aux[firstPlayer + k]);

		}
		System.out.println("\n".repeat(2));
		
		//Ronda inicial
		System.out.println("Round: " + roundShow + " ---------------------\n");
		
			while(gameOnline) {
					
					//Na primeira ronda, quem come�a � definido aleatoriamente
					if(round != 1) {
						roundStarter = 0;
					} else {
						//mas na outras rondas, queremos percorrer todos os jogadores
						roundStarter = lastWinner;
						//por isso � q o roundShow � um contador diferente
						//para o jogo, n�o interessa quando uma ronda acaba ou come�a
					}
				
					//itera sobre todos os jogadores, um a um
					
					for(int j = roundStarter; j < numberPlayers; j++) {
						
						//contagem das rondas e print da ronda atual
						if(j == lastWinner && round != 1) {
							roundShow++;
							System.out.println("Round: " + roundShow + " ---------------------");
							
						}
						//metodo para decidir se deve printar ou nao a mao de um jogador e peder inputs
						humanOrBot(j,Players_aux2, table);
						
						//na primeira ronda ninguem ganha ou perde
						if(round != 1) {
							//o tabuleiro verifica se alguem fica com a mao vazia
							if(table.checkWin(Players_aux2)) {
								System.out.println("It's a win!");
								//o jogo � terminado
								gameOnline = false;
								
								break;
							}
							//o tabuleiro tem omnipotencia e consegue ver se os jogadores s� conseguem passar
							if(table.checkDraw(Players_aux2, table)) {
								System.out.println("All players have passed! None can play!");
								//o jogo � terminado
								gameOnline = false;
								break;
							}
							
							
						}
					
						
						//passamos para a proxima ronda
						round++;
					}
					
					
					
			}
			//no final do jogo, s�o calculados os pontos
			hand_score(Players_aux2, numberPlayers);
			
			int roundWinCheck[] = new int[numberPlayers]; //array para que tem os pontos de todos os jogadores numa ronda
			Stack<Integer> roundTieCheck = new Stack<Integer>();//array para verificare empates
			int totalPoints[] = new int[numberPlayers];//array com os pontos acumulados
			
			for(int h = 0; h < numberPlayers; h++) {//os pontos s�o atualizados a cada ronda
				roundWinCheck[h] = Players_aux2[h].roundPoints;
				totalPoints[h] = Players_aux2[h].totalPoints;
				//System.out.println("Pontos: " + roundWinCheck[h]);
				
			}
			//quem ganha tem menos pontos
			
			lastWinner = getMin(roundWinCheck)[1];//index de quem ganha
			
			
			//verifa��o de empates
		
			for(int m = 0; m < numberPlayers; m++) {
				
				if(roundWinCheck[m] == getMin(roundWinCheck)[0]) {
					//verifica se existem mais jogadores com os mesmo pontos de quem ganha
					roundTieCheck.push(m);
					
				}
			}
			
			
			//se o array dos empates n�o tiver s� um elemento, ent�o � porque houve empate
			if(roundTieCheck.size() != 1) {
				System.out.println("It's a tie between " + roundTieCheck.size() + " players.");
				
				//print dos jogadores q empataram
				for(int b = 0; b < roundTieCheck.size(); b++) {
					System.out.println(names_aux[roundTieCheck.get(b)]);
				}
				//a desforra � feita pelo o lugar.
				System.out.println("But, someone must win. The tie is decided by seat place order.");
				lastWinner = roundTieCheck.get(0);
			}
			
			//� apresentado quem ganhou
			System.out.println("Round Winner: " + names_aux[lastWinner] + " with " + Players_aux2[lastWinner].roundPoints + " points.\n");
			//� apresentado os pontos deste jogo
			banner.matchscore(roundWinCheck, userName);
			//� apresentado os pontos totais acumulados
			banner.acumlatedScore(totalPoints, userName);
			
			// quem ganhou � o pr�ximo a jogar
			return firstPlayer = lastWinner;
		
	}
		

	//verifica se � um bot ou um jogador
	public static void humanOrBot (int nextPlayer, Player[] players, Board table) {
		//o jogador humano est� sempre no index 0 de qualquer array
		int humanPlayer = 0;
		
		if(nextPlayer == humanPlayer) {//para o ser humano, tem de ser apresentada a suas pe�as
			players[humanPlayer].handShow();
			table.boardShow(); //o tabuleiro antes da jogada
			players[humanPlayer].playHand(table, players[humanPlayer], nextPlayer);//� pedido ao pplayer para jogar
			
		} else {
			//players[nextPlayer].handShow();
			players[nextPlayer].playHand(table, players[nextPlayer], nextPlayer);//os bots simplesmente jogam
			
		}
		System.out.println("Board after this play: ");
		table.boardShow(); //� mostrada a mesa depois de uma jogada
	}
	
	
	//soma da m�o de um player no final da ronda
	
	public static void hand_score(Player[] player, int numberPlayers) {     // entram o gamescore anterior{
		int soma;
		
		System.out.println("-----Points in this Round--------");
		//s�o iterados todos os players
		for(int l = 0; l < numberPlayers; l++) {
			soma = 0; //reset da soma
			
			if(l == 0) {
				System.out.println("Tiles in your hand: ");//o jogador � humano
				
			} else {
				System.out.println("Tiles in the hand of Bot" + l +": ");//o jogador � um bot
				
			}
			//S�o apresentadas as pe�as na mao de cada jogador
			for(int k = 0; k < player[l].handPlayer.size(); k++) {
				
				System.out.println(player[l].handPlayer.get(k));
				
				soma += player[l].handPlayer.get(k).sumTile(player[l]);
				
			}		
			
			player[l].totalPoints += soma;//cada jogador tem os seus pontos acumulados
			player[l].roundPoints = soma;// os pontos de rondas s�o atualizados
			
			if(soma == 0) {
				System.out.println("Empty hand");
				//se o jogador n�o tiver pontos, � pq tem a mao vazia. A pe�a 0|0 vale 10
			}
			
			if(l == 0) {
				System.out.println("You - Points: " + soma);//� apresentado os pontos do humano
				
			} else {
				System.out.println("Bot" + l + " - Points: " + soma);//os bots s�o apresentados em 3 pessoa do singular
				
			}
		}
		
		
	}
	
	//Fun�ao que verifica se alguem chega aos 50 pontos e termina o jogo, caso necessario
	public static boolean GameOver( Player Players_aux[], String userName)
		{	
		String[] names = {userName, "bot_1", "bot_2", "bot_3"};
		int maxpoints = 50;
		ArrayList<String> winner = new ArrayList<String>();
		int[] gameScores = {Players_aux[0].totalPoints,Players_aux[1].totalPoints,Players_aux[2].totalPoints,Players_aux[3].totalPoints};
		
		
		//Se alguem chegar aos 50 pontos
		if ( Players_aux[0].totalPoints >= maxpoints || Players_aux[1].totalPoints >= maxpoints 
				|| Players_aux[2].totalPoints >= maxpoints || Players_aux[3].totalPoints >= maxpoints)       
		{		
			//ent�o � gameOver
			banner.gameover();     
	
			//� verificado quem tem menos pontos acumulados																			//array com os pontos de cada gajo
			for (int i = 0; i < 4; i++)						     
			{	
				if (Players_aux[i].totalPoints == getMin(gameScores)[0])			    		 //procura qual o mano vencedor
				{
					winner.add(names[i]);				     
				}
			}
				
			if (winner.size() == 1)	//caso exista s� um jogador, � uma vitoria					            	 					//se so existir um vencedor
			{						    		     	
				System.out.println("The winner is "+ winner.get(0) + "!");	     
			}
	
			if (winner.size() > 1)	
			{			    	//se existirem v�rios jogadores, � empate	     
				System.out.println("Tie between:");
				for (int i = 0; i < winner.size(); i++)
				{	
					System.out.println(winner.get(i)); //da print a todos os empatadores
				}
	
			}						    		     
	
					
					
			
			
			
			return false;//o jogo � terminado
		}
		
		return true;//se ninguem tiver chegado aos 50 pontos, o jogo pode continuar
			     
	}
	
	// baralha os dominos, ap�s serem criados
	public static int[] deckShuffle()
	{	
		Random r = new Random();
		int[] shuffle_aux = new int[28]; //array auxiliar com as 28 pe�as

		for (int i = 0; i < 28; i++) {   // cria os indices de cada Tile
			shuffle_aux[i] = i;	
		}
		
		for (int i = 27; i > 0; i--)      				 //baralha   
		{                             
			
			
			int j = r.nextInt(i+1);

			
			int temp = shuffle_aux[i];
			shuffle_aux[i] = shuffle_aux[j];
			shuffle_aux[j] = temp;
		}
	return shuffle_aux;
	
}
	
	
	//distribui��o das pe�as baralhadas
	public static int dealerRoutine(int round,Player Players_aux[], int[] shuffle_aux, Tiles[] tilesBook, int Dealer, int numberPlayers, String[] names_aux)   
	{
		int Dealer_index = Dealer;
		int t = 0;
		Random rand = new Random();
		
		System.out.println("Dealer order: \n");
	
		
		
		if (round == 1) {
			Dealer_index = rand.nextInt(4);//o dealer � aleatorio na primeira ronda
			
		}
		
		for (int k=0; k< numberPlayers;k++ ) {//print da ordem dos dealers
			
			System.out.println(k + 1 + ": "+ names_aux[Dealer_index + k]);
	
		}
		
		System.out.println("\n".repeat(2));
		
			for (int j=1 ; j<5 ; j++) //as pe�as s�o distribuidas por cada um dos players
				{
				for (int i=0 ; i<7 ; i++) //cada jogador recebe 7 pe�as
					{
						Players_aux[Dealer_index+j].handInit(tilesBook[shuffle_aux[t]]);
						t++;//o dealer � ultimo a ficar com pe�as 
						if (i == 7 && t !=27) //a cada sete pe�as dadas, a entrega passa para outro jogador
						{
							i=0;
							
							break;
							
						}	 
					}
				}
			
			
		
			
		Dealer_index++;//o proximo dealer � quem est� sentado � esquerda
		
		if(Dealer_index > 3){//reset do counter do dealer
			Dealer_index = 0;
		}
		
		
		return Dealer_index;//� retornado um valor para o dealer da proxima ronda
		
	}


}
