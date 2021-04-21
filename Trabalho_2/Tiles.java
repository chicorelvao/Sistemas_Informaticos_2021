package trabalho23;

public class Tiles {
	
	private int top, bot;
	private int sum;
	
	//Construtor
	public Tiles(int top, int bot)
	{
		
		this.top = top;
		this.bot = bot;
	}
	
	
	//metodo que permite obter o valor superior da pe�a de domino
	public int getTopNumber() 
	{
		return top;
	}
	
	//metodo que permite obter o valor inferior da pe�a de domino
	public int getBotNumber() 
	{
		return bot;
	}
	
	//metodo que permite atribuir um valor superior � pe�a de domino
	//tem com entrada o valor que se vai atribuir
	public void setTopNumber(int top) 
	{
			this.top = top;
	}
	
	//metodo que permite atribuir um valor inferior � pe�a de domino
	//tem com entrada o valor que se vai atribuir
	public void setBotNumber(int bot) 
	{
		this.bot = bot;
	}
	
	//metodo que rodar uma pe�a de domino, 
	//util quando se quer jogar uma pe�a em cima da mesa e � necessario a rodar 
	public Tiles rotateTile() 
	{
		return new Tiles(this.bot, this.top);
	}
	
	
	
	//metodo que soma os valores de cada pe�a 
	//tem com entrada os valores superior e inferior de cada pe�a e o jogador que tem a pe�a 
	// se |i|j| i = j = 0 ent�o a pe�a tem 10 pontos se for a unica na m�o do jogador
	// em outros casos a pe�a tem i +j pontos
	
	
	public int sumTile(Player player) 
	{      //n�mero de pontos de um azulejo

		if(this.top == 0 && this.bot == 0)
		{		   										// se |i|j| i = j = 0 ent�o a pe�a tem 10 pontos
														//apenas se for a unica na m�o do jogador
			if (player.handPlayer.size() == 1) 
			{

				sum = 10;
			} 
			else 
			{
				sum = 0;		// se |i|j| i = j = 0 e n�o for a unica pe�a do tabuleiro ent�o a soma � 0 pontos				
											
			}

		} 
		else
		{
			// em outros casos a pe�a tem soma = i +j pontos
			sum = this.bot + this.top;
			
		}
		return sum;
	}
	
	//met�do que desenha a pe�a no tabuleiro
	//
	// (caso 1)  i!=j          (caso 2) i = j 
	//
	//     |j|                    |i|j|
	//     ---
	//	   |i|
	//
	
	
	public String toString()
	{
		if (this.top == this.bot)         //caso 1 
		{
			return "\n" + "|" + this.top + "|" + this.top + "|" + "\n";        
		}
		else							  //caso 2
		{
			return "\n" + "|" + this.top + "|" + "\n" + "---" + "\n" + "|" + this.bot+ "|" + "\n";
		}
		
	}

	//m�todo para verificar se um n�mero existe numa pe�a
	//tem como valor de entrada o valor que queremos verificar s eexiste na pe�a de domino
	
	public boolean checkIn(int number) 
	{
		
		if (number == this.top) 
		{    								 // verifica se o numero que queremos verificar existe na posi��o superior da pe�a 
			return true;					 // se for verdade retorna true
		} 
		else if ( number == this.bot)
		{   								 // verifica se o numero que queremos verificar existe na posi��o inferior da pe�a 
			return true;					 // se for verdade retorna true
		} 
		else 								// caso o numero que procuramos n�o esteja em nenhum posi��o do azulejo 
		{									//� retornado um false
			return false;
		}
	}
}

