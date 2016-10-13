import java.awt.Point;
import java.util.ArrayList;

public class GottaCatchThemAll extends Problem{
	ArrayList<MinimumSpanningTreeEdge> edges;
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
		for (int i = 0; i < this.visitedStates.length; i++) {
			for(int j = 0; j < this.visitedStates[i].length; j++) {
				for(int k = 0; k < this.visitedStates[i][j].length; k ++) 
					this.visitedStates[i][j][k] = -1; 
			}
		}
	}
	@Override
	public boolean passGoalTest(State currentState) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setFirstHeuristic(Node node) {
		// TODO Auto-generated method stub
		ArrayList<Point> pokemonLocations = maze.pokemonLocations;

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
		State state1 = new State(1, 1, null, 1, 1);
		Node node1 = new Node(state1, null, null, 0, 0, -1);
		Point point1 = new Point(3,5);
		Point point2 = new Point(-6,2);
		Point point3 = new Point(0,7);

		ArrayList<Point> pokemonLocations = new ArrayList<Point>();
		pokemonLocations.add(point1);
		pokemonLocations.add(point2);
		pokemonLocations.add(point3);
//		setFirstHeuristic(node1, pokemonLocations);
//		System.out.println(node1.heuristicCost);
	}

	@Override
	public void setSecondHeuristic(Node node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setThirdHeuristic(Node node) {
		// TODO Auto-generated method stub
		
	}

}
