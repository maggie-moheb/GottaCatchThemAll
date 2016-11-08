import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
import java.io.PrintWriter;
/**
 * Maze class
 * @author maggiemoheb, myriameayman, youmnasalah
 *
 */

public class Maze{
	int Length, Width, totalPoky;
	Point intialSate;
	int xhatch;
	MazeCell[][] mazeGrid;
	public ArrayList<Point> pokemonLocations;
	int xGoal; 
	int yGoal; 
	int [][] cellNumber; 
	/**
	 * Create an instance of Maze with random dimensions  
	 */
	public Maze(){
		xGoal = 0; 
		yGoal = 0; 
		Random R = new Random();
		pokemonLocations = new ArrayList<Point>();
		int High = 8; // maximum l/w of any generated maze
		int Low = 8; //minimum l/w of any generated maze
		Length = R.nextInt(High) + 2;
		Width = R.nextInt(Low) + 2;
		Length = 3;
		Width = 3;
		mazeGrid = intiGrid(new MazeCell[Width][Length]); 		
	}
	public MazeCell[][] intiGrid(MazeCell[][] grid){
		cellNumber = new int [Width][Length];
		int count = 0;
		for (int i = 0; i< Width; i++){
			for (int j = 0; j< Length;j++){
				grid[i][j] = new MazeCell();
				grid[i][j].x = i;
				grid[i][j].y = j;
				cellNumber[i][j] = count;
				count ++;
			}
		}
		return grid;
	}
	/**
	 * getters and setters
	 * @return
	 */
	public int getLength() {
		return Length;
	}

	public void setLength(int length) {
		Length = length;
	}

	public int getWidth() {
		return Width;
	}

	public void setWidth(int width) {
		Width = width;
	}
	public MazeCell[][] getMazeGrid() {
		return mazeGrid;
	}

	public void setMazeGrid(MazeCell[][] mazeGrid) {
		this.mazeGrid = mazeGrid;
	}


	/**
	  * Randomly generate a maze configuration   
	  */
	public Maze GeneMaze(){
		Maze maze = new Maze(); //create new instance of maze
		Random R = new Random();
		Random R2 = new Random();
		int xStart = R.nextInt(maze.Width-1);
		int yStart = R.nextInt(maze.Length-1); 
		System.out.println("Start x,y :"+xStart+" "+yStart);
		maze.intialSate = (new Point(xStart,yStart));
		maze.xhatch = (R.nextInt((maze.Width)*(maze.Length))) + 1; // setting x-hatch randomly at max equal to the number of cells in the maze
		Stack <MazeCell>nodes = new Stack<MazeCell>();
		nodes.push(maze.mazeGrid[xStart][yStart] ); // add start node to the nodes Stack
		//DFS
		int randomVisit;// Randomly select one of the neighboring cells 0--3 
		while(!nodes.isEmpty()){
			boolean poky = R.nextBoolean(); // randomly set a pokemon
			int pokyLoc = R.nextInt(4);
			ArrayList<Integer> randomCell = new ArrayList<Integer>();
			MazeCell Current = nodes.pop();
			boolean Lft = false, rgt = false, u = false, d = false; // lft, rgt, u, d is not visited before 
			if(Current.vistied)continue;
			Current.vistied = true;
			int right = Current.x + 1;// move 1 steps to the right at a time
			int left = Current.x - 1; // 1 steps to the left
			int up = Current.y + 1; // 1 steps upwards
			int down  = Current.y - 1; //1 steps downwards
			randomVisit = R.nextInt(4); 
			// check steps against boundaries of the maze
			if (right >= maze.Width)
				right = -100;
			if (left < 0)
				left = -100;
			if( up >= maze.Length)
				up = -100;
			if(down < 0)
				down = -100;
			// add children resulting from all possible moves
			// if not visited before
			MazeCell leftCell, rightCell, aboveCell, belowCell;
			// break the right wall if the right cell is not visited 
			if(right!= -100 && !maze.mazeGrid[right][Current.y].vistied){
				rightCell = maze.mazeGrid[right][Current.y];
				rgt = true;
				if(pokyLoc == 0){
					if(poky){
						maze.mazeGrid[right][Current.y].ContainsPock = true;
						maze.totalPoky = maze.totalPoky + 1;
						maze.pokemonLocations.add(new Point(right, Current.y));
					}
				}
			}
			// insert 
			// break the Left wall if the left cell is not visited 
			if( left!= -100 && !maze.mazeGrid[left][Current.y].vistied ){
				leftCell = maze.mazeGrid[left][Current.y];
				Lft = true;
				if(pokyLoc == 1){
					if(poky){
						maze.mazeGrid[left][Current.y].ContainsPock = true;
						maze.totalPoky = maze.totalPoky + 1;
						maze.pokemonLocations.add(new Point(left, Current.y));
					}
				}
			}
			// break the ceiling if node above not visited
			if( up!= -100 && !maze.mazeGrid[Current.x][up].vistied){
				aboveCell = maze.mazeGrid[Current.x][up];
				u = true;
				if(pokyLoc == 2){
					if(poky){
						maze.mazeGrid[Current.x][up].ContainsPock = true;
						maze.totalPoky = maze.totalPoky + 1;
						maze.pokemonLocations.add(new Point(Current.x,up));
					}
				}

			}

			// break the floor if the node below not visited 
			if( down!= -100 && !maze.mazeGrid[Current.x][down].vistied ){
				belowCell = maze.mazeGrid[Current.x][down];
				d = true;
				if(pokyLoc == 3){
					if(poky){
						maze.mazeGrid[Current.x][down].ContainsPock = true;
						maze.totalPoky = maze.totalPoky + 1;
						maze.pokemonLocations.add(new Point(Current.x, down));
					}
				}
			}
			randomCell.add(0);
			randomCell.add(1);
			randomCell.add(2);
			randomCell.add(3);
			// check if the randomCell is empty
			if(Current.parent != null){
				// current is the child break the wall btw the current and the parent
				if(Current.x == Current.parent.x +1){
					maze.mazeGrid[Current.x][Current.y].wallLeft = false;
					maze.mazeGrid[Current.parent.x][Current.parent.y].wallRight = false;
				}
				else if(Current.x == Current.parent.x - 1){
					maze.mazeGrid[Current.x][Current.y].wallRight = false;
					maze.mazeGrid[Current.parent.x][Current.parent.y].wallLeft = false;
				}
				else if(Current.y == Current.parent.y +1){
					maze.mazeGrid[Current.x][Current.y].wallDown = false;
					maze.mazeGrid[Current.parent.x][Current.parent.y].wallUp = false;
				}
				else if(Current.y == Current.parent.y -1){
					maze.mazeGrid[Current.x][Current.y].wallUp = false;
					maze.mazeGrid[Current.parent.x][Current.parent.y].wallDown = false;
				} else{
				}
			}
			while(!randomCell.isEmpty()){
				if(randomCell.get(randomVisit) == 0){
					if(rgt){
						maze.mazeGrid[right][Current.y].parent = Current;
						nodes.push(maze.mazeGrid[right][Current.y]);
					}
				}
				else if(randomCell.get(randomVisit)== 1){
					if(Lft){
						maze.mazeGrid[left][Current.y].parent = Current;
						nodes.push(maze.mazeGrid[left][Current.y]);	
					}
				}
				else if(randomCell.get(randomVisit)== 2){
					if(u){
						maze.mazeGrid[Current.x][up].parent = Current;
						nodes.push(maze.mazeGrid[Current.x][up]);
					}
				}
				else if(randomCell.get(randomVisit) == 3){
					if(d){
						maze.mazeGrid[Current.x][down].parent = Current;
						nodes.push(maze.mazeGrid[Current.x][down]);
					}
				}
				randomCell.remove(randomVisit);
				if(!randomCell.isEmpty())
					randomVisit = R2.nextInt(randomCell.size());
			}
		}
		// seting the location of the Goal
		int xGoal = xStart;
		int yGoal = yStart;
		while(true){
			if(xGoal== xStart && yGoal == yStart){
				xGoal = R.nextInt(maze.Width-1);
				yGoal = R.nextInt(maze.Length-1);
			}
			else{
				maze.mazeGrid[xGoal][yGoal].isGoal = true;
				break;
			}
		}
		this.xGoal = xGoal; 
		this.yGoal = yGoal;
		return maze;
	}
	/**
	 * method to print the maze in the console
	 * @param maze
	 */
	public static void PrintMaze(Maze maze){
		MazeCell[][] Grid = maze.mazeGrid;
		String wallsUp, wallsDown, current;	
		for(int j =  maze.Length -1; j >=0; j--){
			wallsUp = ""; wallsDown = "";current = "";
			for(int i = 0; i < maze.Width; i++){
				if (!Grid[i][j].vistied) System.out.println("rrrr");;
				if(Grid[i][j].wallUp)
					wallsUp += "  --  ";
				else
					wallsUp += "      ";
				if(Grid[i][j].wallLeft)
					current += " |";
				else
					current += "  ";
				if(Grid[i][j].ContainsPock && Grid[i][j].isGoal)
					current += "po";
				else if(i == maze.intialSate.x && j == maze.intialSate.y)current += " A";
				else if(Grid[i][j].isGoal)
					current += " O";
				else if(Grid[i][j].ContainsPock)
					current += " p";
				else 
					current+= " *";
				if(Grid[i][j].wallRight)
					current +="| ";
				else
					current +="  ";
				if(Grid[i][j].wallDown)
					wallsDown += "  --  ";
				else
					wallsDown += "      ";
			}
			System.out.println(wallsUp);
			System.out.println(current);
			System.out.println(wallsDown);
		}
	}
	public static void Print_Maze(Maze maze){
		MazeCell[][] Grid = maze.mazeGrid;
		String wallsUp, wallsDown, current;	
		for(int j =  maze.Length -1; j >=0; j--){
			wallsUp = ""; wallsDown = "";current = "";
			for(int i = 0; i < maze.Width; i++){
				if (!Grid[i][j].vistied) 
					System.out.println("rrrr");
				if(Grid[i][j].wallUp)
					wallsUp += "  --  ";
				else
					wallsUp += "      ";
				if(Grid[i][j].wallLeft)
					current += " |";
				else
					current += "  ";
				if(maze.pokemonLocations.contains(new Point(i, j))&& Grid[i][j].isGoal)
					current += "po";
				else 
					if(i == maze.intialSate.x && j == maze.intialSate.y)
						current += " A";
				else 
					if(Grid[i][j].isGoal)
					current += " O";
				else 
					if(maze.pokemonLocations.contains(new Point(i, j)))
					current += " p";
				else 
					current+= " *";
				if(Grid[i][j].wallRight)
					current +="| ";
				else
					current +="  ";
				if(Grid[i][j].wallDown)
					wallsDown += "  --  ";
				else
					wallsDown += "      ";
			}
			System.out.println(wallsUp);
			System.out.println(current);
			System.out.println(wallsDown);
		}
	}
	public static String generate_predicates(String name, ArrayList<String> inputs) {
		String result = name + "( "; 
		for(int i =0; i < inputs.size() - 1; i++) {
			result = result + inputs.get(i) + " , "; 
		}
		result = result + inputs.get(inputs.size()-1) + " ).";
		return result;
	}
	public static void GenMazeTextFile(Maze maze) {
		MazeCell[][] Grid = maze.mazeGrid;
		String dimension = generate_predicates("dimension",new ArrayList<String>() {{
			add(maze.Width + "");
		    add(maze.Length+ "");}}) + "\n";
		String goal = "";
		String cells =  ""; 
		String walls = "";
		String pok = "";
		String xhatch = generate_predicates("xHatch", new ArrayList<String>() {{
			add(maze.xhatch + "");}}) + "\n";
		ArrayList<String> agentCell = new ArrayList<String>();
		agentCell.add(maze.cellNumber[maze.intialSate.y][maze.intialSate.x] + "");
		agentCell.add(1 + "");
		agentCell.add(maze.xhatch + "");
		agentCell.add("s0");
		String agent = generate_predicates("at", agentCell) + "\n";
		
		for(int i = 0; i < maze.Length; i++) {
			for(int j = 0; j < maze.Width; j++) {
				ArrayList<String> cell = new ArrayList<String>();
				cell.add(maze.cellNumber[i][j] + "");
				cells = cells + generate_predicates("cell", cell);
				cells = cells + "\n";
				
				if (maze.mazeGrid[j][i].isGoal) {
					goal = generate_predicates("goal",cell) + "\n";
				}
				if(maze.mazeGrid[j][i].wallDown && maze.cellNumber[i][j] - maze.Width >= 0){
					ArrayList<String> wallIndex = new ArrayList<String>();
					wallIndex.add(maze.cellNumber[i][j] + "");
					wallIndex.add(maze.cellNumber[i][j] - maze.Width + "");
					walls =  generate_predicates("walldown", wallIndex) + "\n" + walls;
				}
				else if(maze.mazeGrid[j][i].wallLeft && maze.cellNumber[i][j] - 1 >= 0 && maze.cellNumber[i][j] % maze.Width > 0){
					ArrayList<String> wallIndex = new ArrayList<String>();
					wallIndex.add(maze.cellNumber[i][j] + "");
					wallIndex.add(maze.cellNumber[i][j] - 1 + "");
					walls = walls + generate_predicates("wallleft", wallIndex) + "\n";
				}
				if(maze.mazeGrid[j][i].ContainsPock) {
					ArrayList<String> pokemon = new ArrayList<String>();
					pokemon.add(maze.cellNumber[i][j] + "");
					pokemon.add("s0");
					pok = pok + generate_predicates("pokemon", pokemon) + "\n"; 
				}
			}
		}
		String initialProblem = goal + agent + dimension + walls + cells + pok + xhatch; 

		try{
		    PrintWriter writer = new PrintWriter("mazeGenerated.pl", "UTF-8");
		    writer.println(initialProblem);
		    System.out.println("GOWA");
		    writer.close();
		} catch (Exception e) {
		  System.out.println("Could not write to the text file");
		}		
	}
	public static void main(String[] args){
		Maze maze = new Maze();
		maze = maze.GeneMaze();
		System.out.println(maze.pokemonLocations.size());
		for(int i = 0; i< maze.pokemonLocations.size(); i++){
			System.out.println(maze.pokemonLocations.get(i).toString() );
			System.out.println(maze.mazeGrid[maze.pokemonLocations.get(i).x][maze.pokemonLocations.get(i).y].ContainsPock);
		}
		PrintMaze(maze);
		/*
		ArrayList<String> inputs = new ArrayList<String>();		
		String dimension = generate_predicates("dimension",new ArrayList<String>() {{
			add(6 + "");
		    add(6+ "");}});
		for(int i =0; i < maze.cellNumber.length; i++) {
			for(int j =0; j < maze.cellNumber[i].length; j++){ 
				System.out.print(j + "," + i + ",");
				System.out.println(maze.cellNumber[i][j]);
			}
			System.out.println("\n");
			}
		*/
		GenMazeTextFile(maze);
	}

		

}
/*
 * Maze cells Class
 */
class MazeCell{
	boolean vistied, wallUp, wallDown, wallRight, wallLeft, ContainsPock, isGoal;
	int x,y; MazeCell parent;
	public MazeCell() {
		isGoal = false;
		parent = null;
		vistied = false;
		wallUp = true;
		wallDown = true;
		wallRight = true;
		wallLeft = true;
		ContainsPock = false;
	}		
}