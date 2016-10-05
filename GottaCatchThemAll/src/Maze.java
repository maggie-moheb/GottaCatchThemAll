import java.util.Random;
import java.util.Stack;



public class Maze {
	int Length, Width;
	MazeCell[][] mazeGrid;
	/*
	 * Create an instance of Maze with random dimensions  
	 */
	public Maze(){
		Random R = new Random();
		int High = 40; // maximum l/w of any generated maze
		int Low = 4; //minimum l/w of any generated maze
		Length = R.nextInt(High - Low) + Low;
		Width = R.nextInt(High - Low) + Low;
		mazeGrid = new MazeCell[Width][Length]; 		
	}
	/*
	 * Randomly generate a maze configuration   
	 */
	public static Maze GeneMaze(){
		Maze maze = new Maze(); //create new instance of maze
		//Generate 4 random walls 
		Random R = new Random();
		int xStart = R.nextInt(maze.Width);
		int yStart = R.nextInt(maze.Length);
		Stack <MazeCell>nodes = new Stack<MazeCell>();
		MazeCell startCell = new MazeCell();
		startCell.x = xStart; //set indexes of the starting cell
		startCell.y = yStart;
		maze.mazeGrid[xStart][yStart] = startCell;
		nodes.add(startCell); // add start node to the nodes Stack
		//DFS
		System.out.println(" Limit: ("+maze.Width+", "+maze.Length);

		while(!nodes.isEmpty()){
			boolean poky = R.nextBoolean();
			int pokyLoc = R.nextInt(4);
			MazeCell Current = nodes.pop();
			if(Current.Wall || !Current.vistied){
				Current.vistied = true;
				int right = Current.x + 1;// move 1 steps to the right at a time
				int left = Current.x - 1; // 1 steps to the left
				int up = Current.y + 1; // 1 steps upwards
				int down  = Current.y - 1; //1 steps downwards

				// check steps against boundaries of the maze
				if (right >= maze.Width)
					right = maze.Width-1;
				if (left <= 0)
					left = 0;
				if( up >= maze.Length)
					up = maze.Length-1;
				if(down < 0)
					down = 0;

				// add children resulting from all possible moves
				// if not visited before
				MazeCell leftCell, rightCell, aboveCell, belowCell;

				if(maze.mazeGrid[right][Current.y] == null ) {
					// check that the parent node was not on the boundary
					rightCell = new MazeCell();
					rightCell.x = right;
					rightCell.y = Current.y;
					rightCell.ContainsPock = (pokyLoc==1)?poky:false;
					maze.mazeGrid[right][Current.y] = rightCell;
					nodes.add(maze.mazeGrid[right][Current.y]);
				}
				if(left > 0 && maze.mazeGrid[left][Current.y] == null){
					leftCell = new MazeCell();

					leftCell.x = right;
					leftCell.y = Current.y;
					leftCell.ContainsPock = (pokyLoc==2)?poky:false;
					maze.mazeGrid[left][Current.y] = leftCell;
					nodes.add(maze.mazeGrid[left][Current.y]);
				}
				if( up > 0 && maze.mazeGrid[Current.x][up] == null){
					aboveCell = new MazeCell();
					aboveCell.x = Current.x;
					aboveCell.y = up;
					aboveCell.ContainsPock = (pokyLoc==3)?poky:false;
					maze.mazeGrid[Current.x][up] = aboveCell;
					nodes.add(maze.mazeGrid[Current.x][up]);
				}
				if(down > 0 && maze.mazeGrid[Current.x][down] == null){
					belowCell = new MazeCell();
					belowCell.x = Current.x;
					belowCell.y = down;
					belowCell.ContainsPock = (pokyLoc==4)?poky:false;
					maze.mazeGrid[Current.x][down] = belowCell;
					nodes.add(maze.mazeGrid[Current.x][down]);
				}
			}

		}
		// seting the location of the Goal
		int xGoal = xStart;
		int yGoal = yStart;
		while(true){
			if(xGoal==xStart && yGoal == yStart){
				xGoal = R.nextInt(maze.Width);
				yGoal = R.nextInt(maze.Length);
			}
			else
				break;
		}
	maze = setwallGoal(maze, xGoal, yGoal);
		return maze;
	}
	/*
	 * A method to display the 
	 */
	public static Maze setwallGoal(Maze maze, int xGoal, int yGoal){
		MazeCell[][] Grid = maze.mazeGrid;
		// set Goal
		Grid[xGoal][yGoal] = (Grid[xGoal][yGoal]== null)? new MazeCell():Grid[xGoal][yGoal];
		Grid[xGoal][yGoal].isGoal = true;
		// set wall if not goal or null 
		for(int i = 0; i < maze.Width; i++){
			for(int j = 0; j < maze.Length; j++){
				if(Grid[i][j] == null)
				{
					Grid[i][j] = new MazeCell();
					Grid[i][j].Wall = true;
				}

			}
		}

		return maze;
	}
	public static void PrintMaze(Maze maze){
		MazeCell[][] Grid = maze.mazeGrid;
		for(int i = 0; i < maze.Width; i++){
			for(int j = 0; j < maze.Length; j++){
				if(Grid[i][j] == null)
					System.out.print(" | ");
			else if(Grid[i][j].Wall)
				System.out.print(" | ");
				else if(Grid[i][j].isGoal)
					System.out.print(" O ");
				else if(Grid[i][j].ContainsPock)
					System.out.print(" K ");
				else
					System.out.print(" - ");
			}
			System.out.println();
		}

	}
	public static void main(String[] args){
		Maze maze = GeneMaze();
		PrintMaze(maze);
	}
}
/*
 * Maze cells Class
 */
class MazeCell{
	boolean vistied, Wall, ContainsPock, isGoal;
	int x,y;
	public MazeCell() {
		vistied = false;
		isGoal = false;
		Wall = false;
		ContainsPock = false;
	}		
}