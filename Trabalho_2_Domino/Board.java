package trabalho2;

import java.util.Stack;


public class Board {
	
	Stack<Tiles> tileOnTable = new Stack<Tiles>();
	Tiles topTile;
	Tiles botTile;
	int playerTiles;
	int humanPlayer = 0;
	boolean tilePlaced;
	boolean tileCheck;
	int numberPlayers = 4;
	
	boolean humanTie;
	
	public Board() {
		System.out.println("Welcome!\n");
	}
	
	public boolean boardPlace (Tiles inTile, Player player, Board table, int indexTile, int nextPlayer) {
		
		tilePlaced = false;
		
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
					tileOnTable.add(0, inTile);
					tilePlaced = true;
					
				} else {
					tileOnTable.add(0, inTile.rotateTile());
					tilePlaced = true;
				}
				
				
			} else if (inTile.checkIn(botTile.getBotNumber())) {
				//verificar se a pe�a colocada tem algum numero igual ao numero de baixo da pe�a no fundo do tabuleiro
				//caso tenha, � comparado qual dos lados da pe�a tem o n�mero
				
				if(inTile.getTopNumber() == botTile.getBotNumber()) {
					tileOnTable.push(inTile);
					tilePlaced = true;
					
				} else {
					//e a pe�a � rotacionada, caso seja necess�rio
					//se a pe�a for uma carro�a, n�o interessa, o primeiro if � ativado
					tileOnTable.push(inTile.rotateTile());
					tilePlaced = true;
				}
				
			} else {
				
				if (nextPlayer == humanPlayer) {
					System.out.println("A pe�a selecionada n�o � permitida.");
				}
				//rechamada do metodo para jogar 
				player.playHand(table, player, nextPlayer);
				tilePlaced = false;
			}
			
		} else {
			//se n�o existe pe�as no tabuleiro, a pe�a � adicionada sem restri��es
			// perguntar ao jogador se quer rodar a pe�a
			tileOnTable.push(inTile);
			tilePlaced = true;
		}
		
		//remove a pe�a da mao do jogador se pe�a for aceite
		if (tilePlaced) {
			player.handPlayer.remove(indexTile);
			
		}
		
		return tilePlaced;
	}

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
	
	
	
	
 	public void boardShow () {
	
		if(tileOnTable.size() != 0) {
			System.out.println("____________________");
			for (int i = 0; i < tileOnTable.size(); i++) {
				System.out.println(tileOnTable.get(i).toString());
			}
			System.out.println("____________________");
		} else {
			System.out.println("Not tiles on the board.\n");
		}
	}
	
	//verifica se alguem fica sem pe�as na m�o
	public boolean checkWin(Player[] players) {
		
		boolean win = false;
		
		for(int i = 0; i < numberPlayers; i++) {
			if (players[i].handPlayer.size() == 0) {
				win = true;
				break;
			}
		}
		//dizer quem ganhou
		
		return win;
	}

	public boolean checkDraw(Player[] players, Board table) {
		
		
		humanTie = true;
		
		for(int j = 0; j < numberPlayers; j++) {
			for(int i = 0; i < players[j].handPlayer.size(); i++) {
				humanTie = !table.checkBoardPlace(players[j].handPlayer.get(i)) && humanTie;
			}
		}
		return  (humanTie);
	}

	public void clearBoard() {
		tileOnTable.clear();
	}
}
