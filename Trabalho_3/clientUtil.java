package trabalho3.Trabalho_3;

import java.util.HashMap;
import java.util.Stack;

public class clientUtil {
	public static void main(String args[]) {


	}
	
	public static void welcome() {
		System.out.println("*".repeat(25));
		System.out.println("* Welcome to Hanoi Tower *");
		System.out.println("*".repeat(25));	
	}
	
	public static void errorDraw() {         // implementamos esta fun��o para avisas qnd um input do utilizador � invalido
		System.out.println("\n");
		System.out.println(" "+"*".repeat(35));
		System.out.println("****** Try again, not possible. ******");
		System.out.println("****** Please, insert a number. ******");
		System.out.println(" "+"*".repeat(35));
		System.out.println("\n");  
	}

	public static void playOptions() {

		System.out.println("1:A-->B");                        
		System.out.println("2:A-->C");
		System.out.println("3:B-->A");
		System.out.println("4:B-->C");
		System.out.println("5:C-->A");
		System.out.println("6:C-->B");
		System.out.println("Press Y to end game.");

		//return "1:A-->B\n2:A-->C\n3:B-->A\n4:B-->C\n5:C-->A\n6:C-->B\nPress Y to Exit.";

	}

	public static void diskXange (Stack<Integer> a ,Stack<Integer> b) {

		int var;
		var = a.pop(); 
		b.push(var);
			
	}
	
	public static void pinFiller(int disk,int initialpin,Stack<Integer> aux1,Stack<Integer> aux2,Stack<Integer> aux3) {
		//passar o void para Stack<Integer>
		//Stack<Integer> aux = aux1;  //neste momento todas as stacks s�o iguais 
		aux1.push(1000);
		aux2.push(1000);
		aux3.push(1000);
		for (int j = disk; j >= 1; j--) {
				
				switch (initialpin) {
			
				case 1 :
					
					aux1.push(j); 
					break;
			
				case 2:
					
					aux2.push(j); 
					break;
			
				case 3:
					
					aux3.push(j); 
					break;
				}
			
			}
		//return aux;		
		}

	public static void pinClear(Stack<Integer> aux1,Stack<Integer> aux2,Stack<Integer> aux3) {
		aux1.clear();
		aux2.clear();
		aux3.clear();
		
	}

	public static void draw(int disk, Stack<Integer> towerOne, Stack<Integer> towerTwo, Stack<Integer> towerThree) {


		/*   
		 * ------------------------- FORMULA��O MATEM�TICA --------------------------------
		 * 
		 *   Considere-se a seguinte pir�mide e a sua compara��o com uma stack:
		 *   
		 *   Na vertical, o index da Stack. Na horizontal, o index da coluna a
		 *   escrever. Um pixel � considerado um ponto desta matriz.
		 *   
		 *   index Stack
		 *     . . . | . . .  
		 *	 3 . . * * * . .     Stack:  [ 1 - diskNumber at index 3
		 *	 2 . * * * * * .               2 
		 *	 1 * * * * * * *               3
		 *	 0 0 1 2 3 4 5 6              100]
		 *	 
		 *
		 * 	 O n�mero m�ximo de pixeis por torre � definido pelo o numero de 
		 *	 pe�as escolhidas no inicio do jogo. Isto porque a maior pe�a  
		 *	 define este numero m�ximo de pixeis.
		 * 	 
		 *	 Logo, se L for o numero m�ximo de pixeis numa linha e uma torre, ent�o
		 *	 L � igual a 2*disk, sendo o disk o n�mero pe�as em jogo.
		 *	 
		 *	 � necess�rio definir o centro geom�trico das torres. 
		 *	 O centro geom�trico � sempre halfL = disk e metade do tamanho total, L.
		 *	 
		 *	 Por fim, cada disco � desenhado com simetria em rela��o ao eixo
		 *	 da base de sustento. Logo, o pixeis com (halfL, y) fazem sempre
		 * 	 parte do desenho de um disco. 
		 *   A partir deste p�xel central, pode se adicionar uma quantidade de 
		 *   asteriscos para a esquerda e para a direita do pixel referido. 
		 *   Seja W a quantidade de asteriscos a adicionar de cada lado. 
		 *   W � uma s�rie em fun��o do n�mero de disco.
		 *   (diskNumber - valor num determinado index da stack). 
		 *   
		 *   Resumindo:
		 *   L (n�mero de colunas) = 2*disk, disk - discos em jogo
		 *   halfL (centro geom�trico de uma torre) = disk
		 *   Intervalo de desenho de um disco - [halfL - diskNumber , halfL + diskNumber]
		 *	 
		 *-------------------------------------------------------------------------------*/

		String cursor = " "; //cursor de escrita de um pixel, inicializado com um espa�o

		int L = 2*disk; // n�mero m�ximo de pixeis horizontais por torre
		int halfL = disk; // centro geometrico de cada torre

		int towerSize = towerOne.size(); //quantidade de discos sobrepostos numa torre



		/*
		 * O pr�ximo loop funciona com se fosse um tubo de raios cat�dicos.
		 * Primeiro escolhe uma linha da matriz apresentada, (row). 
		 * De seguida. � chamada uma rotina de desenho, sendo que a rotina deseha prinheiro
		 * os espa�os vazios e os espa�os que correspondem a um disco em coluna.
		 * Ap�s acabar uma torre, o cursor de desenho mantem-se na mesma linha,
		 * mas muda de coluna e torre, fazendo isto sucessivamente at� todas as 
		 * torres estarem representadas. 
		 * No final, a linha de desenho � fechada e o cursor de desenho passa 
		 * a uma nova linha, (row).
		 * A quantidade de linhas � diretamente proporcional ao n�mero de pe�as
		 * em jogo.
		 *
		 */

		for (int row = disk + 1; row > 0; row--) {

			//� chamada a rotina de desenho para uma linha de cada torre
			drawRoutine(cursor, row, L, disk, towerOne, halfL, towerSize);
			drawRoutine(cursor, row, L, disk, towerTwo, halfL, towerSize);
			drawRoutine(cursor, row, L, disk, towerThree, halfL, towerSize);

			//A linha � fechada
			System.out.println(" ");

			//No final do loop, o cursor muda de linha

		}

		System.out.println("########".repeat(disk)); //tabuleiro que sustenta as tr�s torres
	}
	
	public static void drawRoutine (String cursor, int row, int L, int disk, Stack<Integer> tower, int halfL, int towerSize) {

		/* 
		 * Na rotina de desenho, verifica-se qual o tamanho de uma torre e
		 * qual o n�mero que identifica o tamanho de um disco.
		 * 
		 */
		int diskNumber; //o n�mero dos disco est� relacionado com o seu tamanho visual 


		/*
		 * Se linha a onde estamos a desenhar � menor que o n�mero de pe�as 
		 * numa torre, ent�o certamente que tem de ser desenhado um disco.
		 * O tamanho do disco e a sua posi��o � definido pelo m�todo towerDraw.
		 * Se o tamanho da torre � menor que a linha de desenho, ent�o estamos 
		 * perante uma torre vazia ou parcialmente vazia. Se o cursor estiver 
		 * no centro geom�trico da torre, ent�o este desenha um caracter "|". Caso
		 * contr�rio, desenha um " ".
		 */

		if (row < tower.size()) {

			diskNumber = tower.get(row); //n�mero no index de valor row da stack selecionada
			towerSize = tower.size(); //tamanho atual da torre, que muda a cada jogada

			/* M�todo towerDraw desenha um caracter na coluna escolhida, tomando em considera��o
			 * o tamanho do disco e a posi��o deste. Desenha tamb�m os pixeis "vazios"
			 * em torno dos disco.
			 */
			cursor = towerDraw(diskNumber, halfL, cursor, disk, towerSize, L);	

			//O m�todo towerDraw devolve a linha, j� com todas as colunas desenhadas de uma torre
			System.out.print(cursor);

		} else {

			cursor = " "; //reset no cursor. Acaba tamb�m uma barreira de sepra��o de torres

			/* Estamos perante o caso quando a linha � menor que o tamanho atual da
			 * torre. Logo, s� ser�o desenhados os caracteres "|" e " ".
			 * O for loop percorre todas as colunas de uma �nica torre.
			 */

			for (int col = 0; col < L + 1 ; col++) {

				if (col == halfL) {

					//caso a coluna esteja no centro geom�trico da torre, desenha-se "|"
					cursor = cursor +  "|";

				} else {

					//caso contr�rio, desenha-se um " ".
					cursor = cursor +  " ";

				}
			}

			/* Por fim, o cursor atual � printado na consola, mas sem ser mudada a linha.
			 * Isto porque ainda falta desenhar as outras torres, a n�o ser que esta seja
			 * a �ltima.
			 */
			System.out.print(cursor);

		}

	}


	public static String towerDraw (int diskNumber, int halfL, String cursor, int disk, int towerSize, int L) {



		/*
		 * A fun��o towerDraw desenha os pixeis coluna a coluna de uma �nica torre.
		 * Come�a no pixel mais � esquerda (0) e acaba no mais � direita (L);
		 * Neste m�todo, o n�mero que identifica o disk na stack � usado para definir
		 *  o intervalo de desenho do disco.
		 */


		int farRight_DiskBorder; //fronteira mais � direta do intervalo de desenho do disco
		int farLeft_DiskBorder; //fronteira mais � esquerda do intervalo de desenho do disco

		/* Como dito anteriormente: 
		 * Intervalo de desenho de um disco - [halfL - diskNumber , halfL + diskNumber]
		 * Exemplo:
		 *     . . . | . . .               
		 *	 3 . . * * * . .     Stack:  [ 1 - diskNumber at index 3
		 *	 2 . * * * * * .               2 
		 *	 1 * * * * * * *               3
		 *	 0 0 1 2 3 4 5 6              100]
		 *
		 * O menor disco disco tem um intervalo de desenho de [3 - 1, 3 + 1] = [ 2, 4].
		 * O segundo menor disco tem um intervalo de desenho de [3 - 2, 3 + 2] = [ 1, 5].
		 * cada disco tem 2*n+1 "*"
		 */

		//intervalos de desenho
		farRight_DiskBorder = halfL + diskNumber; 
		farLeft_DiskBorder = halfL - diskNumber;

		//Vari�veis que definem quais os valores que podem ser desenhados nos pixeis.
		String emptyPixel = " ";
		String diskPixel = "*";

		cursor = " "; //reset no cursor. Acaba tamb�m uma barreira de sepra��o de torres

		// O seguinte loop percorre todas as colunas de uma �nica torre.
		for (int col = 0; col < L + 1; col++ ) {

			// Se a coluna estiver dentro do intervalo de desenho do disco, ent�o � 
			// adiconado ao cursor um caracter "*".
			if (col >= farLeft_DiskBorder && col <= farRight_DiskBorder && diskNumber != 0) {

				cursor = cursor + diskPixel;	//o cursor vai adicionando os pixeis atuais aos antigos

			} else {
				//Caso se esteja fora do intervalo de desenho, � adicionado um " " ao cursor.
				cursor = cursor + emptyPixel;

			}

		}

		//A linha � de uma torre � terminada e enviada para ser adicionadas �s linhas de outras torres
		return cursor;

	}

	public static String displayStat(int nmbOfRods,int totalScore,int numOfTimes){



		double average = 1.0*totalScore/numOfTimes;
		return"For "+nmbOfRods+" rods you have in average "+average+" moves and you played "+numOfTimes+" times";

	}

	public static void displayMenu() { 
		System.out.println("---------Select and Option----------\n");
		System.out.println("1-Play");
		System.out.println("2-See stats");
		System.out.println("Q-Stop");
		
	}
	
	public static String showResults(HashMap<Integer, Integer[]> dataScores){

		String endstring="";	
		for (int i=3; i<11;i++){
			if (dataScores.get(i)[1]>=0){
				endstring += displayStat(dataScores.get(i)[0],dataScores.get(i)[1],i)+"\n"; 
				//{number of times played,total score,number of disks}  
			}	

		}
		return endstring;
	}

}



