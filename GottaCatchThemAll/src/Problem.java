import java.util.ArrayList;

public abstract class Problem {
	Operator[] operators;
	State initialState;
	State[] stateSpace;
	
	public abstract boolean passGoalTest(State currentState);
	
	public abstract int goalFunction();
	
	// expand a specific node based on the operators of the problem
	public abstract ArrayList<Node> expand(Node node);
}
