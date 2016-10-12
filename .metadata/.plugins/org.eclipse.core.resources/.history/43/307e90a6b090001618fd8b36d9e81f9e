import java.util.ArrayList;

public abstract class Problem {
	Operator[] operators;
	State initialState;
	State[] stateSpace;
	
	public abstract boolean passGoalTest(State currentState);
	
	public Operator[] getOperators() {
		return operators;
	}

	public void setOperators(Operator[] operators) {
		this.operators = operators;
	}

	public State getInitialState() {
		return initialState;
	}

	public void setInitialState(State initialState) {
		this.initialState = initialState;
	}

	public State[] getStateSpace() {
		return stateSpace;
	}

	public void setStateSpace(State[] stateSpace) {
		this.stateSpace = stateSpace;
	}

	//path cost 
	public abstract int goalFunction();
	
	// expand a specific node based on the operators of the problem
	public abstract ArrayList<Node> expand(Node node);
	
	public abstract void setFirstHeuristic(Node node);
	public abstract void setSecondHeuristic(Node node);
	public abstract void setThirdHeuristic(Node node);

}
