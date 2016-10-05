import java.util.ArrayList;

// general search algorithm presented in lecture 
// takes problem and qing_fun as input which is the algorithm 
// returns the final node or null for failure  
public class SearchGeneral {
	public Node General_Search(Problem problem, QING_FUN qing_fun) {
		Node root = new Node(); 
		root.MakeNode(problem.initialState);
		ArrayList<Node> nodes = new ArrayList<Node>();
		nodes.add(root);
		while(true) {
			if (nodes.size() == 0) return null; 
			Node front = nodes.remove(0);
			if (problem.passGoalTest(front.state)) return front;
			ArrayList<Node> children = problem.expand(front);
			nodes = GeneralQingFunc(qing_fun, nodes, children);
		}
	}
	// Qing function for different algorithms 
	public ArrayList<Node> GeneralQingFunc(QING_FUN algorithm, ArrayList<Node> nodesSoFar,ArrayList<Node> nodesExpanded) {
		if(algorithm == QING_FUN.UCS) return UniformCostSearchQing(nodesSoFar, nodesExpanded);
		if(algorithm == QING_FUN.DFS) return DepthFirstSearchQing(nodesSoFar, nodesExpanded);
		return null; 


	}
	// Qing function for uniform cost search
	public ArrayList<Node> UniformCostSearchQing(ArrayList<Node> nodes, ArrayList<Node> newNode) {
		return null; 
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
		  nodesSoFar.add(nodesSoFar.get(i));
			}					
		return nodesSoFar;
	}
	
}

