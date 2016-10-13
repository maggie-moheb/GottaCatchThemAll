import java.util.ArrayList;
import java.util.Arrays;
public class GottaCatchThemAll extends Problem{
	Maze maze;
	int[][][] visitedStates;
	int cleaned; 
	public GottaCatchThemAll(Maze maze) {
		this.maze = maze; 
		this.visitedStates = new int [maze.Length][maze.Width][4];
		cleaned = 0; 
		emptyVisitedStates(); 
	}
	public void emptyVisitedStates(){
		this.cleaned = this.cleaned + 1;
		if (cleaned > 2) return; 
		for (int i = 0; i < this.visitedStates.length; i++) {
			for(int j = 0; j < this.visitedStates[i].length; j++) {
				for(int k = 0; k < this.visitedStates[i][j].length; k ++) 
					this.visitedStates[i][j][k] = -1; 
			}
		}
	}
	@Override
	public boolean passGoalTest(State currentState) {
		if(maze.mazeGrid[currentState.x][currentState.y].isGoal && currentState.pokemonsSoFar <= 0 && currentState.xHatch <= 0 )
			return true;
		return false;
	}

	@Override
	public int pathCostFunction(Node node) {
		int pokemon = node.state.pokemonsSoFar - node.parent.state.pokemonsSoFar;
		int goal = 0 ;
		if(passGoalTest(node.state)) {
			goal = 1;
		}
		int xHatch =  node.state.xHatch - node.parent.state.xHatch;
		node.pathCost = node.parent.pathCost - (20*pokemon + 50* goal + 10*xHatch);
		// TODO Auto-generated method stub
		return node.pathCost;
	}

	@Override
	public ArrayList<Node> expand(Node node) {
		ArrayList<Node> possibleMovements = new ArrayList<Node>();
		int xnode = node.state.x; 
		int ynode = node.state.y;
		int xTranslate = xnode;
		int yTranslate = ynode; 
		if (cleaned == 1)
			visitedStates[xnode][ynode][getDirectionInt(node.state.direction)] = node.state.pokemonsSoFar; 
		if (cleaned == 2)
			visitedStates[xnode][ynode][getDirectionInt(node.state.direction)] = node.state.xHatch; 

				
		// translation possible movements 
		if (node.state.direction == Direction.NORTH && ((ynode + 1) < maze.Length) && (!maze.mazeGrid[xnode][ynode].wallUp)) {
			yTranslate = yTranslate + 1;
			int pokemonNode = node.state.pokemonsSoFar;
			if (maze.mazeGrid[xTranslate][yTranslate].ContainsPock) 
				pokemonNode = pokemonNode - 1;
			State translate = new State(xTranslate,yTranslate,Direction.NORTH,
					pokemonNode,node.state.xHatch -1, false);
			boolean isgoal = passGoalTest(translate) ; 
			translate.isgoal = isgoal; 
			Node newNode = new Node(translate,node,Operator.TRANSLATE,node.depth + 1,0, 0, isgoal);
			pathCostFunction(newNode);
			possibleMovements.add(newNode);
		}
		if (node.state.direction == Direction.SOUTH && ynode > 0 && (!maze.mazeGrid[xnode][ynode].wallDown)) {
			yTranslate = yTranslate - 1;
			int pokemonNode = node.state.pokemonsSoFar;
			if (maze.mazeGrid[xTranslate][yTranslate].ContainsPock) 
				pokemonNode = pokemonNode - 1;
			State translate = new State(xTranslate,yTranslate,Direction.SOUTH,
					pokemonNode,node.state.xHatch -1, false);
			boolean isgoal = passGoalTest(translate); 
			translate.isgoal = isgoal; 
			Node newNode = new Node(translate,node,Operator.TRANSLATE,node.depth + 1,0, 0, isgoal);
			pathCostFunction(newNode); 
			possibleMovements.add(newNode);
		}
		if (node.state.direction == Direction.EAST && ((xnode + 1) < maze.Width) && (!maze.mazeGrid[xnode][ynode].wallRight)) {
			xTranslate = xTranslate + 1;
			int pokemonNode = node.state.pokemonsSoFar;
			if (maze.mazeGrid[xTranslate][yTranslate].ContainsPock) 
				pokemonNode = pokemonNode - 1;
			State translate = new State(xTranslate,yTranslate,Direction.EAST,
					pokemonNode,node.state.xHatch -1, false);
			boolean isgoal = passGoalTest(translate); 
			translate.isgoal = isgoal; 
			Node newNode = new Node(translate,node,Operator.TRANSLATE,node.depth + 1,0, 0, isgoal);
			pathCostFunction(newNode); 
			possibleMovements.add(newNode);
		}
		if (node.state.direction == Direction.WEST && ((xnode - 1) >= 0) && (!maze.mazeGrid[xnode][ynode].wallLeft)) {
			xTranslate = xTranslate - 1;
			int pokemonNode = node.state.pokemonsSoFar;
			if (maze.mazeGrid[xTranslate][yTranslate].ContainsPock) 
				pokemonNode = pokemonNode - 1;
			State translate = new State(xTranslate,yTranslate,Direction.EAST,
					pokemonNode,node.state.xHatch -1, false);
			boolean isgoal = passGoalTest(translate); 
			translate.isgoal = isgoal; 
			Node newNode = new Node(translate,node,Operator.TRANSLATE,node.depth + 1,0, 0, isgoal);
			pathCostFunction(newNode); 
			possibleMovements.add(newNode);
		}
		// rotation possible movements 
		State rotateLeft,rotateRight; 
		Node newNodeRight, newNodeLeft; 
		if (node.state.direction == Direction.NORTH) {
			 rotateLeft = new State(xTranslate,yTranslate,Direction.WEST,
					node.state.pokemonsSoFar,node.state.xHatch, false);
			 newNodeLeft = new Node(rotateLeft,node,Operator.ROTATELEFT,node.depth + 1,0, 0, false);
			
			 rotateRight = new State(xTranslate,yTranslate,Direction.EAST,
					node.state.pokemonsSoFar,node.state.xHatch, false);
			 newNodeRight = new Node(rotateRight,node,Operator.ROTATERIGHT,node.depth + 1,0, 0, false);
		}
		else if (node.state.direction == Direction.EAST) {
			 rotateLeft = new State(xTranslate,yTranslate,Direction.NORTH,
						node.state.pokemonsSoFar,node.state.xHatch, false);
			 newNodeLeft = new Node(rotateLeft,node,Operator.ROTATELEFT,node.depth + 1,0, 0, false);
				
			 rotateRight = new State(xTranslate,yTranslate,Direction.SOUTH,
						node.state.pokemonsSoFar,node.state.xHatch, false);
			 newNodeRight = new Node(rotateRight,node,Operator.ROTATERIGHT,node.depth + 1,0, 0, false);
		}
		else if (node.state.direction == Direction.SOUTH) {
			 rotateLeft = new State(xTranslate,yTranslate,Direction.EAST,
						node.state.pokemonsSoFar,node.state.xHatch, false);
			 newNodeLeft = new Node(rotateLeft,node,Operator.ROTATELEFT,node.depth + 1,0, 0, false);
				
			 rotateRight = new State(xTranslate,yTranslate,Direction.WEST,
						node.state.pokemonsSoFar,node.state.xHatch, false);
			 newNodeRight = new Node(rotateRight,node,Operator.ROTATERIGHT,node.depth + 1,0, 0, false);
		}
		else {
			 rotateLeft = new State(xTranslate,yTranslate,Direction.SOUTH,
						node.state.pokemonsSoFar,node.state.xHatch, false);
			 newNodeLeft = new Node(rotateLeft,node,Operator.ROTATELEFT,node.depth + 1,0, 0, false);
				
			 rotateRight = new State(xTranslate,yTranslate,Direction.NORTH,
						node.state.pokemonsSoFar,node.state.xHatch, false);
			 newNodeRight = new Node(rotateRight,node,Operator.ROTATERIGHT,node.depth + 1,0, 0, false);
		}
		pathCostFunction(newNodeLeft); 
		possibleMovements.add(newNodeLeft);
		pathCostFunction(newNodeRight); 
		possibleMovements.add(newNodeRight);
		ArrayList<Node> result = new ArrayList<Node>(); 
		for (int i = 0; i < possibleMovements.size(); i ++) {
			State temp = possibleMovements.get(i).state; 
			if(cleaned == 1 && visitedStates[temp.x][temp.y][getDirectionInt(temp.direction)] != temp.pokemonsSoFar) 
				result.add(possibleMovements.get(i));
			if(cleaned == 2 && visitedStates[temp.x][temp.y][getDirectionInt(temp.direction)] != temp.xHatch) 
				result.add(possibleMovements.get(i));
		}
		return result;
	}
	public int getDirectionInt(Direction d) {
		if(d == Direction.NORTH) return 0; 
		if(d == Direction.SOUTH) return 1;
		if(d == Direction.EAST) return 2; 
		return 3; 
	}
	@Override
	// city block distance 
	public void setFirstHeuristic(Node node) {
 		node.heuristicCost = (Math.abs(node.state.x - maze.xGoal) + Math.abs(node.state.y - maze.yGoal)); 		
	}

	@Override
	public void setSecondHeuristic(Node node) {
		node.heuristicCost = Math.max(node.state.pokemonsSoFar, node.state.xHatch);
	}

	@Override
	public void setThirdHeuristic(Node node) {
		// TODO Auto-generated method stub
		
	}

}
