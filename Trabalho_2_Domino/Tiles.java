package trabalho2;

public class Tiles {
	
	int top, bot;
	int sum;
	
	//Construtor
	public Tiles(int top, int bot) {
		
		this.top = top;
		this.bot = bot;
	}
	
	public int getTopNumber() {
		return top;
	}
	
	public int getBotNumber() {
		return bot;
	}
	
	public void setTopNumber(int top) {
		this.top = top;
	}
	
	public void setBotNumber(int bot) {
		this.bot = bot;
	}
	
	public Tiles rotateTile() {
		return new Tiles(this.bot, this.top);
	}
	
	public int sumTile(Player player) {      //n�mero de pontos de um azulejo
		
		if(this.top == 0 && this.bot == 0) {		   // se |i|j| i = j = 0 ent�o a pe�a tem 10 pontos
			
			if (player.handPlayer.size() == 1) {
				
				sum = 10;
				
			} else {
				
				sum = 0;

			}
			

		} else {
		
			// em outros casos a pe�a tem i +j pontos
			sum = this.bot + this.top;
			
		}
		
		
		return sum;
	}
	
	public String toString()
	{
		if (this.top == this.bot)
		{
			return "\n" + "|" + this.top + "|" + this.top + "|" + "\n";
		}
		else
		{
			return "\n" + "|" + this.top + "|" + "\n" + "---" + "\n" + "|" + this.bot+ "|" + "\n";
		}
		
	}

	//m�todo para verificar se um n�mero existe numa pe�a
	public boolean checkIn(int number) {
		
		if (number == this.top) {
			return true;
		} else if ( number == this.bot) {
			return true;
		} else {
			return false;
		}
	}
}
