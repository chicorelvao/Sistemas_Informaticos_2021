package trabalho22;

import java.util.Stack;
//lol
public class Board {
	
	Stack<Tiles> tileOnTable = new Stack<Tiles>();
	Tiles topTile;      		//pe�a de domino na posi��o superior do tabuleiro
	Tiles botTile;				//pe�a de domino na posi��o superior do tabuleiro
	int playerTiles;			//variavel iteradora
	int humanPlayer = 0;        //variavel iteradora
	boolean tilePlaced;			//variavel que verifica se alguma pe�a foi colocada por um jogador
	boolean tileCheck;
	int numberPlayers = 4;       //quantos jogadores est�o nesta partida, escalabilidade do codigo para mais jogadpres
	
	boolean tie;         //
	
	
	
	
	public Board() {
		System.out.println("Welcome!\n");
	}
	
	
	//metodo que coloca as pe�as no tabuleiro
		//se ainda n�o existir nenhuma pe�a no tabuleiro � emitido um aviso que o tabuleiro est� vazio
	public boolean boardPlace (Tiles inTile, Player player, Board table, int indexTile, int nextPlayer) {
		
		tilePlaced = false;
					

		if (tileOnTable.size() != 0) 
		{
			
			//vai buscar a tile colocada no topo do tabuleiro
			topTile = tileOnTable.get(0);
			//vai buscar a tile colocada no fundo do tabuleiro
			botTile = tileOnTable.get(tileOnTable.size() - 1);
			
			//verificar se a pe�a colocada tem algum numero igual ao numero de cima da pe�a no topo do tabuleiro
			if ( inTile.checkIn(topTile.getTopNumber())) {
				
				//caso tenha, � comparado qual dos lados da pe�a tem o n�mero
				//e a pe�a � rotacionada, caso seja necess�rio
				//se a pe�a for uma carro�a, n�o interessa, o primeiro if � ativado
				if(inTile.getBotNumber() == topTile.getTopNumber()) 
					{
						tileOnTable.add(0, inTile);
						tilePlaced = true;
					
					} 
				else          //se a pe�a para ser colocada tem que ser rodada ent�o entra dentro deste else
					{
						tileOnTable.add(0, inTile.rotateTile());
						tilePlaced = true;
					}
				
				
			} 
			else if (inTile.checkIn(botTile.getBotNumber())) 
			
				{
				//verificar se a pe�a colocada tem algum numero igual ao numero de baixo da pe�a no fundo do tabuleiro
				//caso tenha, � comparado qual dos lados da pe�a tem o n�mero
				
					if(inTile.getTopNumber() == botTile.getBotNumber()) 
					{
						tileOnTable.push(inTile);
						tilePlaced = true;
					} 
					else 
					{
					//e a pe�a � rotacionada, caso seja necess�rio
					//se a pe�a for uma carro�a, n�o interessa, o primeiro if � ativado
					
						tileOnTable.push(inTile.rotateTile());
						tilePlaced = true;
				
					}
				
			}  
			else         //se  ape�a selecionada n�o for possivel ser jogada ou for uma entrada n�o aceite 
				{ 
				
				if (nextPlayer == humanPlayer) 
					{
						System.out.println("A pe�a selecionada n�o � permitida.");
					}
				//rechamada do metodo para jogar 
				
				player.playHand(table, player, nextPlayer);
				tilePlaced = false; 
				}
			
			} 
			else 
			{
			//se n�o existe pe�as no tabuleiro, a pe�a � adicionada sem restri��es
			// perguntar ao jogador se quer rodar a pe�a
			tileOnTable.push(inTile);
			tilePlaced = true;
			}
		
		//remove a pe�a da mao do jogador se pe�a for aceite
			if (tilePlaced)
			{
			player.handPlayer.remove(indexTile);
			
			}
		//return booleano a dizer que a joagda doi realizada com sucesso
		return tilePlaced;
	}
//metodo q permite aos bots verificar se podem colocar uma pe�a, sem a colocar no tabuleiro
	public boolean checkBoardPlace (Tiles inTile) {
		
		tileCheck = false;
		
		if (tileOnTable.size() != 0) {
			
			//vai buscar a tile colocada no topo do tabuleiro
			topTile = tileOnTable.get(0);
			//vai buscar a tile colocada no fundo do tabuleiro
			botTile = tileOnTable.get(tileOnTable.size() - 1);
			
			//verificar se a pe�a colocada tem algum numero igual ao numero de cima da pe�a no topo do tabuleiro
			if ( inTile.checkIn(topTile.getTopNumber())) {
				
				//caso tenha, � comparado qual dos lados da pe�a tem o n�mero
				//e a pe�a � rotacionada, caso seja necess�rio
				//se a pe�a for uma carro�a, n�o interessa, o primeiro if � ativado
				if(inTile.getBotNumber() == topTile.getTopNumber()) {
					
					tileCheck = true;
				} else {
					tileCheck = true;
				}
				
				
			} else if (inTile.checkIn(botTile.getBotNumber())) {
				//verificar se a pe�a colocada tem algum numero igual ao numero de baixo da pe�a no fundo do tabuleiro
				//caso tenha, � comparado qual dos lados da pe�a tem o n�mero
				
				if(inTile.getTopNumber() == botTile.getBotNumber()) {
					
					tileCheck = true;
					
				} else {
					//e a pe�a � rotacionada, caso seja necess�rio
					//se a pe�a for uma carro�a, n�o interessa, o primeiro if � ativado
					
					tileCheck = true;
				}
				
			} else {
				//rechamada do metodo para jogar 
				tileCheck = false;
			}
			
		} else {
			//se n�o existe pe�as no tabuleiro, a pe�a � adicionada sem restri��es
			
			
			tileCheck = true;
		}
		

		
		
		return tileCheck;
	}
	
	
//metodo para mostrar a pe�as no tabuleiro
	
 	public void boardShow () {
	
		if(tileOnTable.size() != 0)     //casos em que h� pe�as me cima do tabuleiro
		{
			System.out.println("____________________");
			for (int i = 0; i < tileOnTable.size(); i++)   
			{
				//itera por todas as pe�as do tabuleiro e desenha as mesmas na consola
				System.out.println(tileOnTable.get(i).toString());
				
			}
			System.out.println("____________________");
		} 
		else    //se o tabuleiro estiver vazio
		{
			System.out.println("Not tiles on the board.\n");
		}
	}
	
	//verifica se alguem fica sem pe�as na m�o
	public boolean checkWin(Player[] players) {
		
		boolean win = false;
		
		for(int i = 0; i < numberPlayers; i++) {
			
			if (players[i].handPlayer.size() == 0) {
				win = true;          //set win =true se algum jogador fica sem pe�as
				break;
			}
		}
		//dizer quem ganhou
		
		return win;
	}

	public boolean checkDraw(Player[] players, Board table) {
		//verifica se para cada jogador tem alguma pe�a que possa ser jogada
		
		tie = true;
		
		for(int j = 0; j < numberPlayers; j++) {       
			for(int i = 0; i < players[j].handPlayer.size(); i++) {
				
				tie = !table.checkBoardPlace(players[j].handPlayer.get(i)) && tie;
				
			}
		}
		return  (tie);
	}
	
	
	//depois de cada match o tabuleiro � limpo para ser jogado um novo match
	public void clearBoard() {
		tileOnTable.clear();
	}
}
