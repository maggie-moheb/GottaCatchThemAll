import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;




public class Maze{
	int Length, Width, totalPoky;
	Point intialSate;
	int xhatch;
	MazeCell[][] mazeGrid;
	public ArrayList<Point> pokemonLocations;
	int xGoal; 
	int yGoal; 
	/*
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
		for (int i = 0; i< Length; i++){
			for (int j = 0; j< Width;j++){
				grid[j][i] = new MazeCell();
				grid[j][i].x = j;
				grid[j][i].y = i;
			}
		}
		return grid;
	}
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


	/*
	 * Randomly generate a maze configuration   
	 */
	public Maze GeneMaze(){
		Maze maze = new Maze(); //create new instance of maze
		Random R = new Random();
		Random R2 = new Random();
		int xStart = R.nextInt(maze.Width-1);
		int yStart = R.nextInt(maze.Length-1); 
		//		int xStart = 0;
		//		int yStart = 0;
		System.out.println("Start x,y :"+xStart+" "+yStart);
		maze.intialSate = (new Point(xStart,yStart));
		maze.xhatch = (R.nextInt((maze.Width)*(maze.Length))); // setting x-hatch randomly at max equal to the number of cells in the maze
		Stack <MazeCell>nodes = new Stack<MazeCell>();
		Stack <MazeCell>backtrack = new Stack<MazeCell>();

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
			//			backtrack.push(Current);
			int right = Current.x + 1;// move 1 steps to the right at a time
			int left = Current.x - 1; // 1 steps to the left
			int up = Current.y + 1; // 1 steps upwards
			int down  = Current.y - 1; //1 steps downwards
			//			ArrayList<Point> neighbors = new ArrayList<Point>();
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
				//				neighbors.add(new Point(right, Current.y));
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
				//				maze.mazeGrid[left][Current.y] = leftCell;
				Lft = true;
				if(pokyLoc == 1){
					if(poky){
						maze.mazeGrid[left][Current.y].ContainsPock = true;
						maze.totalPoky = maze.totalPoky + 1;
						maze.pokemonLocations.add(new Point(left, Current.y));
					}
				}
				//				neighbors.add(new Point(left, Current.y));
				//				System.out.println("Left child x, y : "+ left+" "+Current.y);

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
				belowCell = maze.mazeGrid[Current.x][down];				//				maze.mazeGrid[Current.x][down] = belowCell;
				d = true;
				if(pokyLoc == 3){
					if(poky){
						maze.mazeGrid[Current.x][down].ContainsPock = true;
						maze.totalPoky = maze.totalPoky + 1;
						maze.pokemonLocations.add(new Point(Current.x, down));
					}
				}
				//				neighbors.add(new Point(Current.x, down));
				//				System.out.println("below child x, y : "+ Current.x+" "+down);

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
					//					System.out.println("Break right x,y:"+Current.x+" , "+Current.y);
				}
				else if(Current.x == Current.parent.x - 1){
					maze.mazeGrid[Current.x][Current.y].wallRight = false;
					maze.mazeGrid[Current.parent.x][Current.parent.y].wallLeft = false;
					//					System.out.println("Break left x,y:"+Current.x+" , "+Current.y);

				}
				else if(Current.y == Current.parent.y +1){
					maze.mazeGrid[Current.x][Current.y].wallDown = false;
					maze.mazeGrid[Current.parent.x][Current.parent.y].wallUp = false;
					//					System.out.println("Break up x,y:"+Current.x+" , "+Current.y);
				}
				else if(Current.y == Current.parent.y -1){
					maze.mazeGrid[Current.x][Current.y].wallUp = false;
					maze.mazeGrid[Current.parent.x][Current.parent.y].wallDown = false;
					//					System.out.println("Break down x,y:"+Current.x+" , "+Current.y);
				} else{

					//					System.out.println("Inner else break case Parent: x, y "+ Current.parent.x + " ," + Current.parent.y);
					//					System.out.println("Current "+ Current.x + " ," + Current.y);

				}
				//				continue;
			}
			else{
				//				System.out.println("Outer else break case");
			}
			while(!randomCell.isEmpty()){
				if(randomCell.get(randomVisit) == 0){
					if(rgt){
						maze.mazeGrid[right][Current.y].parent = Current;
						nodes.push(maze.mazeGrid[right][Current.y]);
						//						System.out.println("Push right");
						//						System.out.println("parent x, y :"+ Current.x +" " + Current.y);
					}
				}
				else if(randomCell.get(randomVisit)== 1){
					if(Lft){
						//						System.out.println("Push left");
						maze.mazeGrid[left][Current.y].parent = Current;
						nodes.push(maze.mazeGrid[left][Current.y]);	
						//						System.out.println("parent x, y :"+ Current.x +" " + Current.y);
					}
				}
				else if(randomCell.get(randomVisit)== 2){
					if(u){
						maze.mazeGrid[Current.x][up].parent = Current;
						//						System.out.println("Push up");
						nodes.push(maze.mazeGrid[Current.x][up]);
						//						System.out.println("parent x, y :"+ Current.x +" " + Current.y);

					}
				}
				else if(randomCell.get(randomVisit) == 3){
					if(d){
						maze.mazeGrid[Current.x][down].parent = Current;
						//						System.out.println("Push down");
						nodes.push(maze.mazeGrid[Current.x][down]);
						//						System.out.println("parent x, y :"+ Current.x +" " + Current.y);

					}
				}
				randomCell.remove(randomVisit);
				if(!randomCell.isEmpty())
					randomVisit = R2.nextInt(randomCell.size());
			}
			//			if(!nodes.isEmpty()){
			//				MazeCell peek = nodes.peek();
			//				int x = peek.x;
			//				int y = peek.y;
			//				if(maze.mazeGrid[x][y].vistied) continue;
			//				System.out.println("Peek x, y: "+x+" , "+ y);
			//				if(x - peek.parent.x > 0){
			//					maze.mazeGrid[x-1][y].wallRight = false;
			//					maze.mazeGrid[x][y].wallLeft = false;
			//					System.out.println("Break right x,y:"+x+" , "+y);
			//				}
			//				else if(x - peek.parent.x < 0){
			//					maze.mazeGrid[x + 1][y].wallLeft = false;
			//					maze.mazeGrid[x][y].wallRight = false;
			//					System.out.println("Break Left x,y:"+x+" , "+y);
			//
			//				}
			//				else if(y - peek.parent.y > 0){
			//					//					Current.wallUp = false;
			//					maze.mazeGrid[x][y-1].wallUp = false;
			//					maze.mazeGrid[x][y].wallDown = false;
			//					System.out.println("Break Up x,y:"+x+" , "+y);
			//
			//				}
			//				else if(y - peek.parent.y< 0){
			//					//					Current.wallDown = false;
			//					maze.mazeGrid[x][y+1].wallDown = false;
			//					maze.mazeGrid[x][y].wallUp = false;
			//					System.out.println("Break Down x,y:"+x+" , "+y);
			//
			//				}
			//			}
			//			if(rgt){
			//				if(randomVisit == 0){
			//					Current.wallRight = false;
			//					maze.mazeGrid[Current.x][Current.y].wallRight = false;
			//					maze.mazeGrid[right][Current.y].wallLeft = false;
			//					nodes.push(maze.mazeGrid[right][Current.y]);
			//					if(u) nodes.push(maze.mazeGrid[Current.x][up]);
			//					if(d) nodes.push(maze.mazeGrid[Current.x][down]);
			//					if(Lft) nodes.push(maze.mazeGrid[left][Current.y]);
			//				}
			//				else{
			//					nodes.push(maze.mazeGrid[right][Current.y]);
			//				}
			//				haveNN = true;
			//			}
			//			if(Lft){
			//				if(randomVisit == 1){
			//					Current.wallLeft = false;
			//					maze.mazeGrid[Current.x][Current.y].wallLeft = false;
			//					maze.mazeGrid[left][Current.y].wallRight = false;
			//					nodes.push(maze.mazeGrid[left][Current.y]);
			//					if(u) nodes.push(maze.mazeGrid[Current.x][up]);
			//					if(rgt) nodes.push(maze.mazeGrid[right][Current.y]);
			//					if(d) nodes.push(maze.mazeGrid[Current.x][down]);
			//				}
			//				else{
			//					nodes.push(maze.mazeGrid[left][Current.y]);
			//				}
			//				haveNN = true;
			//			}
			//			if(u){
			//				if(randomVisit == 2){
			//					Current.wallUp = false;
			//					maze.mazeGrid[Current.x][Current.y].wallUp = false;
			//					maze.mazeGrid[Current.x][up].wallDown = false;
			//					nodes.push(maze.mazeGrid[Current.x][up]);
			//					if(rgt) nodes.push(maze.mazeGrid[right][Current.y]);
			//					if(d) nodes.push(maze.mazeGrid[Current.x][down]);
			//					if(Lft) nodes.push(maze.mazeGrid[left][Current.y]);
			//				}
			//				else{
			//					nodes.push(maze.mazeGrid[Current.x][up]);
			//				}
			//				haveNN = true;
			//			}
			//			if(d){
			//				if(randomVisit == 3 ){
			//					Current.wallDown = false;
			//					maze.mazeGrid[Current.x][Current.y].wallDown = false;
			//					maze.mazeGrid[Current.x][down].wallUp = false;
			//					nodes.push(maze.mazeGrid[Current.x][down]);
			//					if(rgt) nodes.push(maze.mazeGrid[right][Current.y]);
			//					if(u) nodes.push(maze.mazeGrid[Current.x][up]);
			//					if(Lft) nodes.push(maze.mazeGrid[left][Current.y]);
			//				}
			//				else{
			//					nodes.push(maze.mazeGrid[Current.x][down]);
			//				}
			//				haveNN = true;
			//			}
			//reached a deadend
			//			if(!haveNN){
			//				MazeCell nn = nodes.peek();
			//				MazeCell cnn = null;
			//				Stack<MazeCell> neighbour = new Stack<MazeCell>();
			//				//				//relation between last cell with unvisited neighbors and nearest neighbor
			//				while(!backtrack.isEmpty()){
			//					cnn = backtrack.pop();
			//					if(VisitedChildren(maze, cnn, nn)!= null){
			//					maze = VisitedChildren(maze, cnn, nn);
			//					break;
			//					}
			//				}

			//			}
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
		//		System.out.println(xGoal+" , "+ yGoal +" "+maze.mazeGrid[xGoal][yGoal].isGoal);
		this.xGoal = xGoal; 
		this.yGoal = yGoal;
		return maze;
	}
	//	/*
	//	 * A method to display the 
	//	 */
	//	public static Maze VisitedChildren(Maze m, MazeCell cnn, MazeCell peek){
	//		int right = cnn.x + 1;
	//		int left = cnn.x - 1;
	//		int up = cnn.y + 1;
	//		int down = cnn.y -1;
	//		int curx = peek.x;
	//		int cury = peek.y;
	//		if (right >= m.Width)
	//			right = -100;
	//		if (left < 0)
	//			left = -100;
	//		if( up >= m.Length)
	//			up = -100;
	//		if(down < 0)
	//			down = -100;
	//		boolean allvisited = true;
	//		// right child not visited 
	//		if( right != -100 && !m.mazeGrid[right][cury].vistied&& right == curx && cnn.y == cury){
	//			m.mazeGrid[right][cury].wallLeft = false;
	//			m.mazeGrid[curx][cury].wallRight = false;
	//			System.out.println("IN1");
	//			allvisited = false;
	//			//			neighStack.push(m.mazeGrid[right][cury]);
	//		}
	//		// left child not visited 
	//		if(left!= -100 && !m.mazeGrid[left][cury].vistied && left == curx && cnn.y == cury ){
	//			m.mazeGrid[left][cury].wallRight = false;
	//			m.mazeGrid[curx][cury].wallLeft = false;
	//			System.out.println("IN2");
	//			allvisited = false;
	//			//			neighStack.push( m.mazeGrid[left][cury]);
	//
	//		}
	//		// above child not visited 
	//		if(up!= -100 && !m.mazeGrid[curx][up].vistied &&  cnn.x == curx && up == cury ){
	//			m.mazeGrid[curx][up].wallDown = false;
	//			m.mazeGrid[curx][cury].wallUp = false;
	//			System.out.println("IN3");
	//			allvisited = false;
	//			//			neighStack.push(m.mazeGrid[curx][up]);
	//
	//		}
	//		// below child not visited 
	//		if(down != -100 && !m.mazeGrid[curx][down].vistied && cnn.x == curx && down == cury){
	//			m.mazeGrid[curx][down].wallUp = false;
	//			m.mazeGrid[curx][cury].wallDown = false;
	//			System.out.println("IN4");
	//			allvisited = false;
	//
	//		}
	//		return !allvisited?m:null;
	//	}
	public static void PrintMaze(Maze maze){
		MazeCell[][] Grid = maze.mazeGrid;
		String [][] MazePrint = new String[maze.Width*2][maze.Length*2];
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
		//		for(int i = 0; i ❤ ; i++){
		//			for(int j = 0; j < maze.Width*2; j++){
		//				String current  = MazePrint[j][i]; 
		//				if(current != null)
		//				System.out.print(current);
		//			}
		//			System.out.println();
		//		}
	}
	public static void Print_Maze(Maze maze){
		MazeCell[][] Grid = maze.mazeGrid;
		String [][] MazePrint = new String[maze.Width*2][maze.Length*2];
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
				if(maze.pokemonLocations.contains(new Point(i, j))&& Grid[i][j].isGoal)
					current += "po";
				else if(i == maze.intialSate.x && j == maze.intialSate.y)current += " A";
				else if(Grid[i][j].isGoal)
					current += " O";
				else if(maze.pokemonLocations.contains(new Point(i, j)))
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
		//		for(int i = 0; i ❤ ; i++){
		//			for(int j = 0; j < maze.Width*2; j++){
		//				String current  = MazePrint[j][i]; 
		//				if(current != null)
		//				System.out.print(current);
		//			}
		//			System.out.println();
		//		}
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