/*  Primeiro trabalho de SI - 15/03/2021
 * 	Torre de Hanoi
 * 
 *  Francisco Relv�o | 2018285965 |MIEF
 *  Gon�alo Gouveia  | 2018277419 |MIEF
 * 
 * */

package resolproj;

import java.util.*; 


public class Playground {



	public static void main(String args[])
	{
		// cria��o do objeto da classe Scanner, que l� o teclado
		Scanner sc = new Scanner(System.in);
		
		//Recep��o ao jogador
		System.out.println("*".repeat(25));
		System.out.println("* Welcome to Hanoi Tower *");
		System.out.println("*".repeat(25));
		
		/*
		* Stacks vazios s�o arrays que permitem altera��es aos seus elementos.
		* Decidimos realizar este exercicio com Stacks, porque para algoritmos 
		* recursivos, stacks s�o uma otima ferramenta.
		*/
		
		// cria��o de uma stack vazia para cada torre
		Stack<Integer> aux1 = new Stack<Integer>();       //torre direita		
		Stack<Integer> aux2 = new Stack<Integer>();       //stack intermedia
		Stack<Integer> aux3 = new Stack<Integer>();       //stack esquerda

		
		int disk = 0;    //n�mero de discos usados durante o jogo 
		int diskMin = 3; //n�mero minimo de discos permitidos
		int diskMax = 10;//n�mero m�ximo de discos permitidos
		int counter = 0; // regista o numero de passos at� resolver o puzzle
		int solve;       //regista o numero de tentaivas minimo para resolver o jogo

		int size1 = 0;   //inicializa o tamanho de cada um dos stacks
		int size2 = 0;
		int size3 = 0;
		

		String option; // Input de jogadas ao longo do programa

			
		    	//� pedido um input v�lido para o numero de discos entre 3 e 10
				//qualquer outro input fora do limite ou letra ser� lan�ada uma mensagem de erro 
				// e ser� pedido ao utilizador um novo numero de discos at� ser valido
		
			do {
				
			System.out.print("* Insert number of disks between 3 and 10 to continue: ");
			
			
			if(sc.hasNextInt()) {      //seleciona apenas inputs inteiros
				
			    disk = sc.nextInt(); 
			    
			} else {
				
				disk = 0;   //valor para dar erro e voltar a pedir um input
			}
			//pede-se ao usu�rio o numero de discos
			
			
			sc.nextLine();   // tratar do caso especial do /n quando se insere o inteiro
			//O .nextInt separa o inteiro e o /n. Se o barra /n n�o for limpo da linha,
			//ent�o este loop nunca ir� parar.

			// caso o numero de discos selecionado n�o estiver dentro dos valores aceitaveis
			// � lan�ada uma mensagem de erro e � pedido um novo input v�lido.
			
			if (disk < diskMin || disk > diskMax) {
				errorDraw(); //rotina de erro
			}

		}  while (disk < diskMin || disk > diskMax) ;
			
			
			
		solve = (int)Math.pow(2,disk)-1;   //n�mero de tentativas minimas para resolver o jogo
			
		//Base de cada torre. Com este, desenhamos um base da stack pois nunca pode haver troca 
		//de valores da base entre as bases. Com este metodo conseguimos com que as Stacks nunca fiquem vazias

		aux1.push(1000);
		aux2.push(1000);
		aux3.push(1000);

		/*
		 * Loop de introdu��o do n�mero de discos definidos pelo o usuario 
		 * Os discos s�o introduzidos por ordem descrescente no stack
		 */
		
		for (int j = disk; j >= 1; j--){
			aux1.push(j); 

		}
		
		//Loop que mantem o jogo ativo, enquanto o puzzle n�o for resolvido
		
		while (true) {	
			
			
			
			System.out.print("* Please enter moves A->B A->C B->C, exit press y/Y:\n");
			System.out.print("* From tower A, B, C: \n"); //Primeira torre selecionada. Origem do disco
			
			//routina de desenho das 3 torres do jogo
			draw(disk, aux1, aux2, aux3);
			
			
			//-----------------------------------------------------------------------------------------------
			
			// Bloco de decis�o para fechar o jogo, caso o jogador pressione a tecla Y
			
			System.out.println("Close game? ([Y] for yes, any key to continue.)");
			
			/*usamos .toUpperCase(); para que o jogo possa
            parar se o jogador seleccioanr y/Y */
			
			option = sc.nextLine().toUpperCase(); //a input � convertida para letra mai�scula
			
			if (option.equals("Y")) {  //excerto de codigo que indica que o jogo acaba quando y/Y
				
				System.out.println("Problem not solved, you pressed exit."); 
				break; // o jogo � terminado, o while loop � quebrado
			}
			
			//-----------------------------------------------------------------------------------------------
			
			//Output das op��es de jogadas
			System.out.println("1:A-->B");                        
			System.out.println("2:A-->C");
			System.out.println("3:B-->A");
			System.out.println("4:B-->C");
			System.out.println("5:C-->A");
			System.out.println("6:C-->B");
			
			
			option = sc.nextLine().toUpperCase();           
			
			
		
			
			switch (option) {       //implementamos este switch para avaliar cada um dos inputs do ultilizador
			//al�m disso, um movimento so � contado, se existir movimento de discos entre pir�mides
				case "1":
					counter = diskXange(aux1,aux2, counter);    //1:A-->B
					break;
	
				case "2":
					counter = diskXange(aux1,aux3, counter);    //2:A-->C
					break;
	
				case "3":
					counter = diskXange(aux2,aux1, counter);    //3:B-->A
					break;
	
				case "4":
					counter = diskXange(aux2,aux3, counter);    //4:B-->C
					break;
	
				case "5":
					counter = diskXange(aux3,aux1, counter);    //5:C-->A
					break;
	
				case "6":
					counter = diskXange(aux3,aux2, counter);	 //6:C-->B
					break;
					
				case "":
					// movimento vazio � ignorado
					break;
					
				default:                      //qualquer outro input d� erro
					errorDraw();   			  //mensagem de erro
					break;

			}
			
			//-----------------------------------------------------------------------------------------------

            
			Object[] auxx1 = aux1.toArray(); 
			size1 = auxx1.length;
				
			Object[] auxx2 = aux2.toArray(); 
			size2 = auxx2.length;
			
			
			Object[] auxx3 = aux3.toArray(); 
			size3 = auxx3.length;
			
			
			System.out.println("Current play: " + counter + "\n");
			
			if (counter>0) {
				//qando apenas a primeira e a segunda torre tiverem os valores da base o jogo fica completo
				// ou seja, as duas primeiras torres tiverem apenas um elemento, isto implica
				//que a terceira torre tem todos os elementos e de forma ordenada.
				
				if (size1==1 && size2 ==1)   
				{ System.out.println("* Game solved with sucess *\n");
				System.out.println("Problem solved in "+ counter + " attemps");
				System.out.println("Could be solved in " + solve +" attemps");
				
				break;}  
				
			}
			
			

		}
		// cr�ditos
		System.out.println("* Thank you for Playing \n");

		System.out.println("\n");
		System.out.println(" " +"*".repeat(23));
		System.out.println("*         Credits       *");
		System.out.println(" " +"*".repeat(23)+"\n");
		System.out.println(" Francisco Relv�o | 2018285965 |MIEF\n Gon�alo Gouveia  | 2018277419 |MIEF");


		sc.close();
	}
	
	
	
	
	public static int diskXange (Stack<Integer> a ,Stack<Integer> b, int movement) {
		//rotina para mudar os discos
		
		int var;
		// se o ultimo disco da torre para que vai o disco seclecionado for maior ent�o n�o � permitido
		if (a.lastElement() >= b.lastElement() )   
		{
			errorDraw();   //chama a fun��o a aviar que este pa�o n�o � possivel
			
		}

		else {  //se possivel executar passar os discos da torre, o ultimo disco � extraido da stack1 e colocado na stack 2
			var = a.pop(); b.push(var);
			movement ++; //o counter de jogada s� � incrementado, se existir movimento de discos
			}
		return movement; // o counter de jogada � igualado ao movement 
	}
	
	
	public static void errorDraw() {         // implementamos esta fun��o para avisar quando um input do utilizador � invalido
		System.out.println("\n");
		System.out.println(" "+"*".repeat(35));
		System.out.println("****** Try again, not possible. ******");
		System.out.println("****** Please, insert a number. ******");
		System.out.println(" "+"*".repeat(35));
		System.out.println("\n");
		}
	  
	
	public static void draw(int disk, Stack<Integer> towerOne, Stack<Integer> towerTwo, Stack<Integer> towerThree) {
		
		
		/*   
		 * ------------------------- FORMULA��O MATEM�TICA --------------------------------
		 * 
		 *   Considere-se a seguinte pir�mide e a sua compara��o com uma stack:
		 *   
		 *   Na vertical, tem -se o index da Stack. 
		 *   Na horizontal, o index da coluna a escrever. Um pixel � considerado 
		 *   um ponto desta matriz.
		 *   
		 *   index Stack
		 *     . . . | . . .  
		 *	 3 . . * * * . .     Stack:  [ 1 - diskNumber at index 3
		 *	 2 . * * * * * .               2 
		 *	 1 * * * * * * *               3
		 *	 0 0 1 2 3 4 5 6              100]
		 *	 		(L = 6, neste caso.), (halfL = 3);
		 *
		 * 	 O n�mero m�ximo de pixeis por matriz de torre � definido pelo o numero de 
		 *	 pe�as escolhidas no inicio do jogo. Isto, porque a maior pe�a  
		 *	 define este numero m�ximo de pixeis.
		 * 	 
		 *	 Logo, se L for o numero m�ximo de pixeis numa linha e em uma torre, ent�o
		 *	 L � igual a 2*disk, sendo o disk o n�mero pe�as em jogo.
		 *	 
		 *	 � necess�rio definir o centro geom�trico das torres. 
		 *	 O centro geom�trico � sempre halfL = disk e � metade do tamanho total, L.
		 *	 
		 *	 Por fim, cada disco � desenhado com simetria em rela��o ao eixo
		 *	 da base de sustento. Logo, o pixeis com (halfL, y) fazem sempre
		 * 	 parte do desenho de um disco. 
		 *   A partir deste p�xel central, pode-se adicionar uma quantidade de 
		 *   asteriscos para a esquerda e para a direita do pixel referido. 
		 *   Seja W a quantidade de asteriscos a adicionar de cada lado. 
		 *   W � uma s�rie em fun��o do n�mero que indentifica o disco na stack.
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
		 * Primeiro, o loop escolhe uma linha da matriz apresentada, (row). 
		 * De seguida. � chamada uma rotina de desenho, sendo que esta deseha prinheiro
		 * os espa�os vazios e os espa�os que correspondem a um disco em coluna.
		 * Ap�s acabar uma torre, o cursor de desenho mantem-se na mesma linha,
		 * mas muda de coluna e torre, fazendo isto sucessivamente at� todas as 
		 * torres estarem desenhadas, numa dada linha. 
		 * No final, a linha de desenho � fechada e o cursor de desenho passa 
		 * a uma nova linha, (row).
		 * A quantidade de linhas � diretamente proporcional ao n�mero de pe�as
		 * em jogo.
		 *
		 */
		
		for (int row = disk + 1; row > 0; row--) {
			
			//� chamada a rotina de desenho para uma linha de cada torre
			drawRoutine(cursor, row, L, disk, towerOne, halfL, towerSize);
			//torre interm�dia
			drawRoutine(cursor, row, L, disk, towerTwo, halfL, towerSize);
			//torre mais � direita
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
		int diskNumber; //o n�mero do disco na stack est� relacionado com o seu tamanho visual 
		
		
		/*
		 * Se linha (row) a onde estamos a desenhar � menor que o n�mero de pe�as 
		 * numa torre (tower.size()), ent�o certamente que tem de ser desenhado um disco.
		 * O tamanho do disco e a sua posi��o � definido pelo m�todo towerDraw.
		 * Se o tamanho da torre � menor que a linha atual de desenho, ent�o estamos 
		 * perante uma torre vazia ou parcialmente vazia. Se o cursor estiver 
		 * no centro geom�trico da torre, ent�o o cursor desenha um caracter "|". Caso
		 * contr�rio, desenha um " "(empty).
		 */
		
		if (row < tower.size()) {
			
			diskNumber = tower.get(row); //n�mero de disco no index de valor row da stack selecionada
			towerSize = tower.size(); //tamanho atual da torre, que muda a cada jogada
			
			/* M�todo towerDraw desenha um caracter na coluna escolhida, tomando em considera��o
			 * o tamanho do disco e a posi��o deste. Desenha tamb�m os pixeis "vazios"
			 * em torno dos disco.
			 */
			cursor = towerDraw(diskNumber, halfL, cursor, disk, towerSize, L);	
			
			//O m�todo towerDraw devolve a linha, j� com todas as colunas desenhadas de uma torre
			System.out.print(cursor);
			
		} else {
			
			cursor = " "; //reset no cursor. Acaba tamb�m por ser uma barreira de separa��o de torres
			
			/* Estamos perante o caso em que a linha � menor que o tamanho atual da
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
		 * Cada disco tem 2*n+1 "*" (asteriscos)
		 */
		
		//intervalos de desenho
		farRight_DiskBorder = halfL + diskNumber; 
		farLeft_DiskBorder = halfL - diskNumber;
		
		//Vari�veis que definem quais os valores que podem ser desenhados nos pixeis, neste caso
		String emptyPixel = " ";
		String diskPixel = "*";
		
		cursor = " "; //reset no cursor. Acaba tamb�m por ser uma barreira de separa��o de torres
			
		// O seguinte loop percorre todas as colunas de uma �nica torre.
		for (int col = 0; col < L + 1; col++ ) {
			
			// Se a coluna estiver dentro do intervalo de desenho do disco, ent�o � 
			// adiconado ao cursor um caracter "*".
			if (col >= farLeft_DiskBorder && col <= farRight_DiskBorder && diskNumber != 0) {
				
				cursor = cursor + diskPixel;	//o cursor vai adicionando os pixeis atuais aos antigos
				
			} else {
				//Caso o cursor esteja fora do intervalo de desenho, � adicionado um " " ao cursor.
				cursor = cursor + emptyPixel;
				
			}
				 
		}
		 
		//A linha de uma torre � terminada e � enviada para ser adicionada � linha de outras torres
		return cursor;
	}
	
}