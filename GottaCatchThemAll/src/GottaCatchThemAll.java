import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;

import javax.lang.model.element.QualifiedNameable;
public class GottaCatchThemAll extends Problem{
	ArrayList<MinimumSpanningTreeEdge> edges;
	Maze maze;
	int[][][] visitedStates;
	int cleaned; 
	int depthSoFar; 
	ArrayList<Node> nodesSoFar; 
	ArrayList<State> statesSoFar;
	boolean clean = false; 
	int limiting = 0;
	public GottaCatchThemAll(Maze maze) {
		this.maze = maze; 
		this.initialState = new State(maze.intialSate.x, maze.intialSate.y, Direction.NORTH, maze.totalPoky, maze.xhatch, false,maze.pokemonLocations);
		this.visitedStates = new int[maze.Width] [maze.Length][4];
		cleaned = 0; 
		depthSoFar = 0;
		nodesSoFar = new ArrayList<Node>(); 
		statesSoFar = new ArrayList<State>(); 
		
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
	public boolean foundOnPath(Node node,Node parent) {
		if (parent == null) return false; 
		if (node.state.equals(parent.state)) return true; 
		return foundOnPath(node, parent.parent);
	}
	@Override
	public boolean passGoalTest(State currentState) {
//		System.out.println(currentState);
		if(maze.mazeGrid[currentState.x][currentState.y].isGoal && currentState.pokemonsSoFar <= 0 && currentState.xHatch <= 0 )
			return true;
		return false;
	}
	public void setVisitedState(Node node) {
		 if (node == null) return;
		 if (cleaned == 1) 
			visitedStates[node.state.x][node.state.y][getDirectionInt(node.state.direction)] = node.state.pokemonsSoFar;
		 else visitedStates[node.state.x][node.state.y][getDirectionInt(node.state.direction)] = node.state.xHatch;
		 setVisitedState(node.parent);
	}
	@Override
	public int pathCostFunction(Node node) {
		int pokemon =  node.parent.state.pokLocation.size() - node.state.pokLocation.size();
		int goal = 0 ;
		if(passGoalTest(node.state)) {
			goal = 1;
		}
		int xHatch =  Math.abs(node.state.xHatch - node.parent.state.xHatch);
		//xHatch is always adding as it the new one is always less than the old one
		//if(xHatch <0) xHatch = 0;
		node.pathCost = node.parent.pathCost - (20*pokemon + 500* goal + 10*xHatch);
		//System.out.println("A : "+ node.state.xHatch);
		//System.out.println("B : "+ node.parent.state.xHatch);
		//if (pokemon > 0)
		//System.out.println("C : "+ pokemon);
		//System.out.println("B : "+ (20*pokemon + 500* goal + 10*xHatch));
		// TODO Auto-generated method stub
		return node.pathCost;
	}
	public void emptyDown() {
		for(int i = 0; i < nodesSoFar.size(); i++) {
			if(nodesSoFar.get(i).depth > depthSoFar) { 
				visitedStates[nodesSoFar.get(i).state.x][nodesSoFar.get(i).state.y][getDirectionInt(nodesSoFar.get(i).state.direction)] = -1;
		}
		}
	}
	public boolean stateExist(State state) {
		for (int i = 0; i < statesSoFar.size(); i++) {
			if(state.equals(statesSoFar.get(i)))
				return true;
			
		}
		return false; 
			
	}
	@Override
	public ArrayList<Node> expand(Node node) {
		if(node.parent == null) 
			statesSoFar = new ArrayList<State>(); 	
		//}
		//if(node.state.xHatch < 0 && node.state.pokemonsSoFar <= 0 && !clean) {
		//	statesSoFar = new ArrayList<State>();	
		//	clean = true;
		//}
		//if(node.depth < depthSoFar) statesSoFar = new ArrayList<State>();
		//if (node.state.xHatch < 0 && node.state.pokemonsSoFar == 0) 
		//	statesSoFar = new ArrayList<State>();
		if(node.state.pokLocation.size() == 0 && node.state.xHatch <= 0) limiting ++; 
		if(stateExist(node.state)) {
			//if(node.state.pokLocation.size() == 0 && node.state.xHatch <= 0 && limiting > 100) {
			//	limiting = 0;
			//	return new ArrayList<Node>(); 
			//}
			//else 
			//System.out.println("la2etha abl keda");
			return new ArrayList<Node>(); 
		}
		else statesSoFar.add(node.state);
		if (cleaned == 1)
			visitedStates[node.state.x][node.state.y][getDirectionInt(node.state.direction)] = node.state.pokemonsSoFar;
		else visitedStates[node.state.x][node.state.y][getDirectionInt(node.state.direction)] = node.state.xHatch;
		if (node.depth < depthSoFar) {
			if(node.state.pokemonsSoFar > 0) cleaned = 1; 
			else cleaned = 2; 
			emptyDown();
		}
		nodesSoFar.add(node); 
		depthSoFar = node.depth;
		ArrayList<Node> possibleMovements = new ArrayList<Node>();
		int xnode = node.state.x; 
		int ynode = node.state.y;
		int xTranslate = xnode;
		int yTranslate = ynode; 
		if (cleaned == 1) {
//			System.out.println("XNODE: "+xnode + ", "+ynode +" "+ getDirectionInt(node.state.direction));
			visitedStates[xnode][ynode][getDirectionInt(node.state.direction)] = node.state.pokemonsSoFar; 
		}
		if (cleaned == 2)
			visitedStates[xnode][ynode][getDirectionInt(node.state.direction)] = node.state.xHatch; 
		if(node.state.pokemonsSoFar <= 0 && cleaned == 1) {
			System.out.println("lamet kol el pokemon");
			emptyVisitedStates();
		}
		// translation possible movements 
		if (node.state.direction == Direction.NORTH && ((ynode + 1) < maze.Length) && (!maze.mazeGrid[xnode][ynode].wallUp)) {
			yTranslate = yTranslate + 1;
			int pokemonNode = node.state.pokemonsSoFar;
//			System.out.println(maze.mazeGrid[xTranslate][yTranslate].ContainsPock);
			/*
			if (maze.mazeGrid[xTranslate][yTranslate].ContainsPock) { 
				pokemonNode = pokemonNode - 1;
				maze.mazeGrid[xTranslate][yTranslate].ContainsPock = false;
			} 
			if(node.state.pokLocation.contains(new Point(xTranslate, yTranslate))) {
				pokemonNode = pokemonNode - 1;
				//int index =node.state.pokLocation.indexOf(new Point(node.state.x, node.state.y));
				node.state.pokLocation.remove(new Point(xTranslate, yTranslate));
			}*/
			State translate = new State(xTranslate,yTranslate,Direction.NORTH,
					pokemonNode,node.state.xHatch -1, false,node.state.pokLocation);
			boolean isgoal = passGoalTest(translate) ; 
			translate.isgoal = isgoal; 
			Node newNode = new Node(translate,node,Operator.TRANSLATE,node.depth + 1,0, 0, isgoal);
			pathCostFunction(newNode);
			possibleMovements.add(newNode);
		}
		if (node.state.direction == Direction.SOUTH && ynode > 0 && (!maze.mazeGrid[xnode][ynode].wallDown)) {
			yTranslate = yTranslate - 1;
			int pokemonNode = node.state.pokemonsSoFar;
//			System.out.println(maze.mazeGrid[xTranslate][yTranslate].ContainsPock);
			/*
			if (maze.mazeGrid[xTranslate][yTranslate].ContainsPock) {
				pokemonNode = pokemonNode - 1;
				maze.mazeGrid[xTranslate][yTranslate].ContainsPock = false;
			}*/
			State translate = new State(xTranslate,yTranslate,Direction.SOUTH,
					pokemonNode,node.state.xHatch -1, false,node.state.pokLocation);
			boolean isgoal = passGoalTest(translate); 
			translate.isgoal = isgoal; 
			Node newNode = new Node(translate,node,Operator.TRANSLATE,node.depth + 1,0, 0, isgoal);
			int cost = pathCostFunction(newNode); 
			newNode.pathCost = cost;
			possibleMovements.add(newNode);
		}
		if (node.state.direction == Direction.EAST && ((xnode + 1) < maze.Width) && (!maze.mazeGrid[xnode][ynode].wallRight)) {
			xTranslate = xTranslate + 1;
			int pokemonNode = node.state.pokemonsSoFar;
//			System.out.println(maze.mazeGrid[xTranslate][yTranslate].ContainsPock);
			/*
			if (maze.mazeGrid[xTranslate][yTranslate].ContainsPock) {
				pokemonNode = pokemonNode - 1;
				maze.mazeGrid[xTranslate][yTranslate].ContainsPock = false;

			}*/
			State translate = new State(xTranslate,yTranslate,Direction.EAST,
					pokemonNode,node.state.xHatch -1, false,node.state.pokLocation);
			boolean isgoal = passGoalTest(translate); 
			translate.isgoal = isgoal; 
			Node newNode = new Node(translate,node,Operator.TRANSLATE,node.depth + 1,0, 0, isgoal);
			//pathCostFunction(newNode); 
			int cost = pathCostFunction(newNode); 
			newNode.pathCost = cost;
			possibleMovements.add(newNode);
			
		}
		if (node.state.direction == Direction.WEST && ((xnode - 1) >= 0) && (!maze.mazeGrid[xnode][ynode].wallLeft)) {
			xTranslate = xTranslate - 1;
			int pokemonNode = node.state.pokemonsSoFar;
//			System.out.println(maze.mazeGrid[xTranslate][yTranslate].ContainsPock);
			/*
			if (maze.mazeGrid[xTranslate][yTranslate].ContainsPock) {
				pokemonNode = pokemonNode - 1;
				maze.mazeGrid[xTranslate][yTranslate].ContainsPock = false;

			}*/
			State translate = new State(xTranslate,yTranslate,Direction.EAST,
					pokemonNode,node.state.xHatch -1, false,node.state.pokLocation);
			boolean isgoal = passGoalTest(translate); 
			translate.isgoal = isgoal; 
			Node newNode = new Node(translate,node,Operator.TRANSLATE,node.depth + 1,0, 0, isgoal);
			//pathCostFunction(newNode); 
			int cost = pathCostFunction(newNode); 
			newNode.pathCost = cost;
			possibleMovements.add(newNode);
		}
		// rotation possible movements 
		State rotateLeft,rotateRight; 
		Node newNodeRight, newNodeLeft; 
		if (node.state.direction == Direction.NORTH) {
			 rotateLeft = new State(xTranslate,yTranslate,Direction.WEST,
					node.state.pokemonsSoFar,node.state.xHatch, false,node.state.pokLocation);
			 newNodeLeft = new Node(rotateLeft,node,Operator.ROTATELEFT,node.depth + 1,0, 0, false);
			
			 rotateRight = new State(xTranslate,yTranslate,Direction.EAST,
					node.state.pokemonsSoFar,node.state.xHatch, false,node.state.pokLocation);
			 newNodeRight = new Node(rotateRight,node,Operator.ROTATERIGHT,node.depth + 1,0, 0, false);
		}
		else if (node.state.direction == Direction.EAST) {
			 rotateLeft = new State(xTranslate,yTranslate,Direction.NORTH,
						node.state.pokemonsSoFar,node.state.xHatch, false,node.state.pokLocation);
			 newNodeLeft = new Node(rotateLeft,node,Operator.ROTATELEFT,node.depth + 1,0, 0, false);
				
			 rotateRight = new State(xTranslate,yTranslate,Direction.SOUTH,
						node.state.pokemonsSoFar,node.state.xHatch, false,node.state.pokLocation);
			 newNodeRight = new Node(rotateRight,node,Operator.ROTATERIGHT,node.depth + 1,0, 0, false);
		}
		else if (node.state.direction == Direction.SOUTH) {
			 rotateLeft = new State(xTranslate,yTranslate,Direction.EAST,
						node.state.pokemonsSoFar,node.state.xHatch, false,node.state.pokLocation);
			 newNodeLeft = new Node(rotateLeft,node,Operator.ROTATELEFT,node.depth + 1,0, 0, false);
				
			 rotateRight = new State(xTranslate,yTranslate,Direction.WEST,
						node.state.pokemonsSoFar,node.state.xHatch, false,node.state.pokLocation);
			 newNodeRight = new Node(rotateRight,node,Operator.ROTATERIGHT,node.depth + 1,0, 0, false);
		}
		else {
			 rotateLeft = new State(xTranslate,yTranslate,Direction.SOUTH,
						node.state.pokemonsSoFar,node.state.xHatch, false,node.state.pokLocation);
			 newNodeLeft = new Node(rotateLeft,node,Operator.ROTATELEFT,node.depth + 1,0, 0, false);
				
			 rotateRight = new State(xTranslate,yTranslate,Direction.NORTH,
						node.state.pokemonsSoFar,node.state.xHatch, false,node.state.pokLocation);
			 newNodeRight = new Node(rotateRight,node,Operator.ROTATERIGHT,node.depth + 1,0, 0, false);
		}
		int costLeft = pathCostFunction(newNodeLeft); 
		newNodeLeft.pathCost = costLeft;
		//pathCostFunction(newNodeLeft); 
		possibleMovements.add(newNodeLeft);
		int costRight = pathCostFunction(newNodeRight); 
		newNodeRight.pathCost = costRight;

		//pathCostFunction(newNodeRight); 
		possibleMovements.add(newNodeRight);
		ArrayList<Node> result = new ArrayList<Node>(); 
		for (int i = 0; i < possibleMovements.size(); i ++) {
			//State temp = possibleMovements.get(i).state; 
			//if(cleaned == 1 && visitedStates[temp.x][temp.y][getDirectionInt(temp.direction)] != temp.pokemonsSoFar) 
			//	result.add(possibleMovements.get(i));
			//if(cleaned == 2 && visitedStates[temp.x][temp.y][getDirectionInt(temp.direction)] == -1) 
				
			result.add(possibleMovements.get(i));
		 }
		if( cleaned == 2 && result.size()>0) {
//			System.out.println("VISITED: "+visitedStates[result.get(0).state.x][result.get(0).state.y][getDirectionInt(result.get(0).state.direction)]);
//		System.out.println("POKEMON: "+result.get(0).state.pokemonsSoFar);
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
	
		ArrayList<Point> pokemonLocations =new ArrayList<Point>();
		for (int i = 0; i < node.state.pokLocation.size(); i ++) {
			pokemonLocations.add(node.state.pokLocation.get(i));
		}
		pokemonLocations.add(new Point(node.state.x, node.state.y));
		ArrayList<Vertex> vertices = Vertex.convertToVertex(pokemonLocations);
		ArrayList<MinimumSpanningTreeEdge> edges = MinimumSpanningTreeEdge.convertToEdges(vertices);
		edges.sort((o1, o2) -> o1.compareTo(o2));
		ArrayList<MinimumSpanningTreeEdge> minimumSpanningTree = new ArrayList<MinimumSpanningTreeEdge>();
		for(int i = 0; i<edges.size(); i++) {
			if(edges.get(i).start.setNo != 1 || edges.get(i).end.setNo != 1) {
				edges.get(i).start.setNo(1);
				edges.get(i).end.setNo(1);
				minimumSpanningTree.add(edges.get(i));
			}
		}
		int heuristic = 0;
		for(int i = 0; i<minimumSpanningTree.size(); i++) {
			heuristic += minimumSpanningTree.get(i).weight;
		}
		node.heuristicCost = heuristic;
	
	}
	public static void main(String[] args) {
//		State state1 = new State(1, 1, null, 1, 1);
//		Node node1 = new Node(state1, null, null, 0, 0, -1);
//		Point point1 = new Point(3,5);
//		Point point2 = new Point(-6,2);
//		Point point3 = new Point(0,7);
//
//		ArrayList<Point> pokemonLocations = new ArrayList<Point>();
//		pokemonLocations.add(point1);
//		pokemonLocations.add(point2);
//		pokemonLocations.add(point3);
//		setFirstHeuristic(node1, pokemonLocations);
//		System.out.println(node1.heuristicCost);
		Maze maze = new Maze();
		maze = maze.GeneMaze();
		Maze.PrintMaze(maze);
//		GottaCatchThemAll gottaCatchThemAll = new GottaCatchThemAll(maze);
	//System.out.println(maze.pokemonLocations.size());
//
//		gottaCatchThemAll.maze.pokemonLocations.remove(0);
//		System.out.println(gottaCatchThemAll.maze.pokemonLocations.size());
//		System.out.println(maze.pokemonLocations.size());

		SearchGeneral search = new SearchGeneral();
		search.Search(maze, QING_FUN.AStar2, true);
		//Node node = search.General_Search(gottaCatchThemAll, QING_FUN.AStar2);
		//System.out.println("NODE: "+ node + " Expanded nodes = " + search.expandedNode);
		//System.out.println(node.state.x +", "+ node.state.y+ " GOAL?: "+ gottaCatchThemAll.passGoalTest(node.state));
	}
}
