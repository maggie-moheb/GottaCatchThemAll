import java.awt.Point;
import java.util.ArrayList;

// general search algorithm presented in lecture 
// takes problem and qing_fun as input which is the algorithm 
// returns the final node or null for failure  
public class SearchGeneral {
	Node root;
	int MAX_DEPTH = 0;
	int expandedNode = 0; 
	ArrayList<Node> path = new ArrayList<Node>();
	public Node Search (Maze maze, QING_FUN qing_fun, boolean visualize) {
		GottaCatchThemAll gottaCatchThemAll = new GottaCatchThemAll(maze);
		Node node = General_Search(gottaCatchThemAll,qing_fun);
		if (visualize) 
			get_path(node,maze);
		System.out.println("NODE: "+ node + " Expanded nodes = " + this.expandedNode);
		System.out.println(node.state.x +", "+ node.state.y+ " GOAL?: "+ gottaCatchThemAll.passGoalTest(node.state));
		return node; 
	}
	void get_path(Node node, Maze maze) {
		Node currentNode = node;
		while(currentNode != null) {
			path.add(currentNode);
			currentNode = currentNode.parent;
		}
		for(int i = path.size()-1; i>=0; i--) {
			maze.pokemonLocations = new ArrayList<Point>();
			for(int j = 0; j < path.get(i).state.pokLocation.size(); j++) {
				maze.pokemonLocations.add(path.get(i).state.pokLocation.get(j));
			}
			maze.intialSate.x = path.get(i).state.x; 
			maze.intialSate.y = path.get(i).state.y;
			Maze.Print_Maze(maze);
			System.out.println("+++++++++++++++++++++++++++++++++++++++++");
		}
	}
	void print_path(Node node, Maze maze) {
		if(node == null)  return; 
		print_path(node.parent,maze); 
		maze.pokemonLocations = new ArrayList<Point>();
		for(int i = 0; i < node.state.pokLocation.size(); i++) {
			maze.pokemonLocations.add(node.state.pokLocation.get(i));
		}
		maze.intialSate.x = node.state.x; 
		maze.intialSate.y = node.state.y;
		Maze.PrintMaze(maze);
		System.out.println("+++++++++++++++++++++++++++++++++++++++++");
	}
	public Node General_Search(Problem problem, QING_FUN qing_fun) {
		root = new Node(); 
		root = root.MakeNode(problem.initialState);
		ArrayList<Node> nodes = new ArrayList<Node>();
		nodes.add(root);
		System.out.println("INITIAL STATE: "+ root);

		while(true) {
			if (nodes.size() == 0) return null; 
			Node front = nodes.remove(0);

//			System.out.println("ANA BASEARCH: "+ front.depth);
//			System.out.println("POK MAZE: "+ ((GottaCatchThemAll) problem).maze.pokemonLocations.size());
//			System.out.println("pok: "+ front.state.pokLocation.size());
			if (problem.passGoalTest(front.state)) return front;
//			if(front.depth == 5) {
//				return null;
//			}
			ArrayList<Node> children = problem.expand(front); expandedNode ++;
			nodes = GeneralQingFunc(qing_fun, nodes, children, problem);
		}
	}
	// Qing function for different algorithms 
	public ArrayList<Node> GeneralQingFunc(QING_FUN algorithm, ArrayList<Node> nodesSoFar,ArrayList<Node> nodesExpanded, Problem problem) {
		if(algorithm == QING_FUN.UCS) return UniformCostSearchQing(nodesSoFar, nodesExpanded);	
		if(algorithm == QING_FUN.AStar1) return AStarQing(nodesSoFar, nodesExpanded, 1, problem);
		if(algorithm == QING_FUN.AStar2) return AStarQing(nodesSoFar, nodesExpanded, 2, problem);
		if(algorithm == QING_FUN.AStar3) return AStarQing(nodesSoFar, nodesExpanded, 3, problem);

		if(algorithm == QING_FUN.DFS) return DepthFirstSearchQing(nodesSoFar, nodesExpanded); 
		if(algorithm == QING_FUN.BFS) return BreadthFirstSearchQing(nodesSoFar, nodesExpanded);
		if(algorithm == QING_FUN.GR1) return GreedySearchQing(nodesSoFar, nodesExpanded, 1, problem);
		if(algorithm == QING_FUN.GR2) return GreedySearchQing(nodesSoFar, nodesExpanded, 2, problem);
		if(algorithm == QING_FUN.GR3) return GreedySearchQing(nodesSoFar, nodesExpanded, 3, problem);
		if(algorithm == QING_FUN.ID) return DepthLimitedSearchQing(nodesSoFar, nodesExpanded);
		
		return null; 
	}
	public ArrayList<Node> GreedySearchQing(ArrayList<Node> nodes, ArrayList<Node> children, int heuristic, Problem problem) {
//	System.out.println("CHILDREN SIZE: "+ children.size());
		for(int i = 0; i<children.size(); i++) {
			switch(heuristic) {
				case 1:problem.setFirstHeuristic(children.get(i));
//				System.out.println("heuristic: "+(children.get(i).heuristicCost));
				break;
				case 2:problem.setSecondHeuristic(children.get(i));break;
				case 3:problem.setThirdHeuristic(children.get(i));break;
			}
		}
//		System.out.println("SIZE: "+ nodes.size());

		nodes.addAll(children);
//	 	System.out.println("Adding children SIZE: "+ nodes.size());

		nodes.sort((o1, o2) -> o1.compareToGreedyHeuristic(o2));
//	 	System.out.println("SIZE: "+ nodes.size());

		return nodes;
	}
	
	public ArrayList<Node> DepthLimitedSearchQing(ArrayList<Node> nodes, ArrayList<Node> children) {
		/*
		if (children.size() == 0 && nodes.size() == 0) 
		{
			nodes.add(root); MAX_DEPTH ++; return nodes; 
		}
		if(children.size() == 0 ) return nodes;
		if(children.get(0).depth > MAX_DEPTH) {
			return nodes;
		} 
		else {
			for(int i = 0; i< nodes.size() ; i++) {
				children.add(nodes.get(i));
			}
		}
		return children;*/
		ArrayList <Node> result = new ArrayList<Node>(); 
		children.addAll(nodes);
		for (int i = 0; i < children.size(); i++) {
			if(children.get(i).depth <= MAX_DEPTH)
				result.add(children.get(i));
		}
		if (result.size() == 0) {
			result.add(root); MAX_DEPTH ++;
		}
		return result;
	}
	// Qing function for uniform cost search
	public ArrayList<Node> UniformCostSearchQing(ArrayList<Node> nodes, ArrayList<Node> newNodes) {
		nodes.addAll(newNodes);
		nodes.sort((o1, o2) -> o1.compareTo(o2));
		return nodes; 
	}
	// Qing function for A* 
	public ArrayList<Node> AStarQing(ArrayList<Node> nodes, ArrayList<Node> newNodes, int heuristic, Problem problem) {
		for(int i = 0; i<newNodes.size(); i++) {
			switch(heuristic) {
				case 1:problem.setFirstHeuristic(newNodes.get(i));break;
				case 2:problem.setSecondHeuristic(newNodes.get(i));break;
				case 3:problem.setThirdHeuristic(newNodes.get(i));break;
			}
		}
		nodes.addAll(newNodes);
		//nodes.sort((o1, o2) -> o1.compareToGreedyHeuristic(o2));		
		nodes.sort((o1, o2) -> o1.compareToHeuristic(o2));
		return nodes; 
	}
	public static void main(String[]args ) {
		
//		ArrayList<Node> nodes = new ArrayList<Node>(); 
//		ArrayList<Node> newNodes = new ArrayList<Node>(); 
//		for (int i = 3; i >= 0; i--) {
//			Node node = new Node(i);
//			Node node2 = new Node(i*2);
//			nodes.add(node);
//			newNodes.add(node2); 
//		}
//		ArrayList<Node> nodes = new ArrayList<Node>(); 
//		ArrayList<Node> newNodes = new ArrayList<Node>(); 
//		for (int i = 3; i >= 0; i--) {
//			Node node = new Node(null,null,null,0,0,i);
//		 	Node node2 = new Node(null,null,null,0,0,i*2);
//		 	nodes.add(node);
//		 	newNodes.add(node2); 
//		 }
//		 SearchGeneral s = new SearchGeneral(); 
//		 s.GreedySearchQing(nodes, newNodes, 1, null);
////		SearchGeneral s = new SearchGeneral(); 
////		nodes = s.DepthFirstSearchQing(nodes, newNodes);
//		for(int i = 0; i < nodes.size(); i++) {
//			System.out.println("HEURISTIC: "+ i+", "+nodes.get(i).heuristicCost);
//		}
	}
	// Qing function for Depth first search
	public ArrayList<Node> DepthFirstSearchQing(ArrayList<Node> nodesSoFar, ArrayList<Node> children){		
		for(int i = 0; i<nodesSoFar.size(); i++){
		  children.add(nodesSoFar.get(i));
		}					
		return children;
	}
	// Qing function for Breadth first search
	public ArrayList<Node> BreadthFirstSearchQing(ArrayList<Node> nodesSoFar, ArrayList<Node> children){
		
		for(int i = 0; i<children.size(); i++){
		  nodesSoFar.add(children.get(i));
			}					
		return nodesSoFar;
	}
	
}

