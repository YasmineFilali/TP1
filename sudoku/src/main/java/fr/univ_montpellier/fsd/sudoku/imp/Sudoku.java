// Equipe : Filali Yasmine - Chemaou Doha
package fr.univ_montpellier.fsd.sudoku.imp;

import java.util.ArrayList;
import java.util.List;



public class Sudoku {

	int n;
	int s;
	int[][] grid;//= {{1,3,4,2}, {2,4,3,1},{3,1,2,4},{4,2,1,3}};

	/*
	 * Create an instance of the problem sudoku (nxn)
	 *
	 */

	public Sudoku(int n) {
		this.n = n;
		this.s = (int) Math.sqrt(n);
		this.grid = new int[n][n];


	}

	/*
	 * check if this.grid is a correct sudoku solution.
	 *
	 */

	private boolean solutionChecker() {
		System.out.println("\t vérification d'une solution :\n");

		//boolean checked = true;
		List<Integer> row_numbers = new ArrayList<>();
		List<Integer> column_numbers = new ArrayList<>();
		List<Integer> square_numbers = new ArrayList<>();
		String info = "";
		int number = 0;
		// parcours des lignes , si une lignes contient deux fois le meme nombre , on renvoie false
		for (int row = 0 ; row < n ; row++){
			row_numbers=new ArrayList<>();
			for (int column = 0 ; column < n ; column++){
				number = grid[row][column];
				if (!row_numbers.contains(number)){
					row_numbers.add(number);
				}
				else{
					info += "dans la ligne " + (row+1) + " on trouve le nombre " + number + " plus qu'une fois" ;
					System.out.println(info);
					return false;
				}
			}
		}
		// parcours des colonnes , si une colonne contient deux fois le meme nombre , on renvoie false
		for (int column=0; column<n; column++){
			column_numbers = new ArrayList<>();
			for(int row=0;row<n ; row++){
				number = grid[row][column];
				if (!column_numbers.contains(number)){
					column_numbers.add(number);
				}
				else{
					info += "dans la colonne " + (column+1) + " on trouve le nombre " + number + " plus qu'une fois" ;
					System.out.println(info);
					return false;
				}
			}
		}
		// parcours des carrés , si un carré contient deux fois le meme nombre , on renvoie false
		int racine = (int) Math.sqrt(n);
		int start_row = 0,start_column=0,end_row = racine, end_column = racine ;
		int row=0,column=0;
		int counter = 1;
		while (row<n || column<n){
			//System.out.println("passage ________________________");
			square_numbers = new ArrayList<>();
			for (row = start_row ; row < end_row ; row++ ){
				//System.out.print("\nrow "+row+" "+"\n");
				for (column = start_column; column < end_column ; column ++){
					number = grid[row][column];
					//System.out.print("column "+column+" number "+number + " ");
					if(!square_numbers.contains(number)){
						square_numbers.add(number);
					}
					else{
						info += "dans le carré " + counter + " on trouve le nombre " + number + " plus qu'une fois";
						System.out.println(info);
						System.out.println("number "+number) ;
						return false;
					}
				}
			}
			counter++;
			if(column!=n){
				start_column= end_column;
				end_column+=racine;
			}
			if(column==n){
				start_row=end_row;
				end_row+=racine;
				start_column=0;
				end_column=racine;

			}
			//System.out.println("\n" +end_column + " " + end_row);
			//System.out.println(end_column!=n && end_row!=n);

		}

		return true;
	}

	/*
	 * Generate a random grid solution
	 *
	 */

	public void generateSolution() {
		System.out.println("\t génération d'une solution :");
		int max = n;
		//System.out.println("max "+max);
		int min = 1;
		int range = max - min + 1;
		for (int i = 0 ; i < n ; i++){
			System.out.print("\t\t ");
			for (int j= 0 ; j < n ; j++){
				int rand = (int)(Math.random() * range) + min;
				grid[i][j]=rand;
				System.out.print(grid[i][j] + ((j+1 == n) ?"\n":" "));
			}
		}
		System.out.println("___________________________________________________");
	}


	/*
	 * Find a solution to the sudoku problem
	 *
	 */

	public List<Integer> returns_list(int index,int start,int end){//},String column_or_row){
		List<Integer> list = new ArrayList<>();
		//if(column_or_row.equals("column")){
		for (int i = start ; i < end ; i++){
			//Arrays.copyOfRange(grid[index], start, racine);
			list.add(grid[i][index]);
		}
		//System.out.println("==========================================");
		//System.out.println(start+" "+end);
		return list;
	}

	public void findSolution() {
		// List<String> list = ArrayToListConversion(array);
		int max = n;
		//System.out.println("max "+max);
		int min = 1;
		int range = max - min + 1;
		int rand;
		int racine = (int) Math.sqrt(n);
		List<Integer> lastly_added = new ArrayList<>();
		int number = 0;
		for (int i = 0 ; i < racine ; i++){
			lastly_added.add(number);
			number+=racine;
		}

		//List<List<Integer>> matrice = new ArrayList<>();
		//int[][] arr = new int[n][n];
		List<Integer> random_racine = new ArrayList<>();
		List<Integer> random_ = new ArrayList<>();
		// choisir "racine" nombres au hasard
		for (int f = 0 ; f < racine ; f++){
			random_racine = new ArrayList<>();
			while (random_racine.size() != racine){
				rand = (int)(Math.random() * range) + min;
				if(!random_racine.contains(rand) && !random_.contains(rand)){
					random_racine.add(rand);
					random_.add(rand);
				}
			}
			//System.out.println(random_racine);

			//int pointer = 0;
			for (int i = 0 ; i < racine ; i++){
				int counter = 0;
				int ind = lastly_added.get(i);
				for (int j = ind ; j < racine+ind ;j++){
					grid[i][j]=random_racine.get(counter);
					//System.out.print(grid[i][j] + ((j+1 == n) ?"\n":" "));
					//System.out.print(grid[i][j]);
					counter++;
				}
				int l = (ind+racine == n? 0:ind+racine);
				lastly_added.set(i,l);
				//System.out.println("________________________ "+i+" "+lastly_added);
				//ind +=racine;
				//int index = (ind==n? 0:ind);
				//lastly_added.put(i,index);
			}
		}
	
		// horizontal
		int start;
		int end;
		//int column_start = 0;
		//int column_end = racine;
		List<Integer> horizontal_list = new ArrayList<>();
		for (int m = 0 ; m < racine-1 ; m++){
			start = m*racine;
			end = start+racine;
			int browser=0;
			for (int i = 0 ; i < n ; i++){
				//System.out.println("index, start ,end "+i+" "+start+" "+end);
				horizontal_list = returns_list(i, start, end);
				//System.out.println(horizontal_list);
				// parcours de horizontal_list
				//ligne right
				int right = i+1;
				if(right%racine==0){
					right-=racine;
				}
				browser = 0;
				int column_start = (m+1)*racine;
				//int column_end = racine + racine;
				int column_end = column_start + racine;
				for (int j = column_start ; j < column_end; j++){
					//System.out.println("here "+right +" "+ j+" "+browser + " "+grid[j][right]);
					grid[j][right]=horizontal_list.get(browser);
					browser++;
				}

				//start = end;
				//end+=racine;
			}
		}
		/*for (int i = 0 ; i < n ; i++){
			//System.out.println("index, start ,end "+i+" "+start+" "+end);
			horizontal_list = returns_list(i, start, end);
			System.out.println(horizontal_list);
			// parcours de horizontal_list
			//ligne right
			int right = i+1;
			if(right%racine==0){
				right-=racine;
			}
			int browser = 0;
			int column_start = racine;
			int column_end = racine + racine;
			for (int j = column_start ; j < column_end; j++){
				System.out.println("here "+right +" "+ j+" "+browser + " "+grid[j][right]);
				grid[j][right]=horizontal_list.get(browser);
				browser++;
			}

			//start = end;
			//end+=racine;
		}*/

//------------------ partie affichage
		System.out.println("___________________________________________________");
		System.out.println("\t solution correcte :");

		for (int i = 0 ; i < n ; i++){
			System.out.print("\t\t");
			for (int j = 0 ; j < n ; j++){
				System.out.print(grid[i][j] + " ");
			}
			System.out.print("\n");
		}
		System.out.println("___________________________________________________");
		boolean checked = solutionChecker();
		if(checked){
			System.out.println("\tla solution donnée est correcte\n");
		}
		else {
			System.out.println("\tla solution donnée est incorrecte\n");
		}
	}

	public static void main(String args[]) {
		Sudoku sudoku = new Sudoku(4);
		//génération d'une solution
		sudoku.generateSolution();
		// vérification d'une solution
		boolean checked = sudoku.solutionChecker();
		if(checked){
			System.out.println("\tla solution donnée est correcte");
		}
		else {
			System.out.println("\tla solution donnée est incorrecte");
		}
		// solution correcte
		sudoku.grid = new int[sudoku.n][sudoku.n];
		sudoku.findSolution();

		List<Integer> matrice1 = new ArrayList<>();
		List<Integer> matrice2 = new ArrayList<>();
		//matrice1.add(1); matrice1.add(4); matrice1.add(3); matrice1.add(2);
		//matrice2.add(1); matrice2.add(4); matrice2.add(3); matrice2.add(2);
		//System.out.println(matrice1-matrice2);
		//matrice[0].add(23);
		//System.out.println(matrice);

	}
}

