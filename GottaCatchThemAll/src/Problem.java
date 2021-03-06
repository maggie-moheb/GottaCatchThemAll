import java.util.ArrayList;
/**
 * abstract problem class
 * @author maggiemoheb, myriameayman, youmnasalah
 *
 */
public abstract class Problem {
	Operator[] operators;
	StateAbs initialState;
	StateAbs[] stateSpace;

	public abstract boolean passGoalTest(StateAbs currentState);
	
	public Operator[] getOperators() {
		return operators;
	}

	public void setOperators(Operator[] operators) {
		this.operators = operators;
	}

	public StateAbs getInitialState() {
		return initialState;
	}

	public void setInitialState(StateAbs initialState) {
		this.initialState = initialState;
	}

	public StateAbs[] getStateSpace() {
		return stateSpace;
	}

	public void setStateSpace(StateAbs[] stateSpace) {
		this.stateSpace = stateSpace;
	}

	//path cost 
	public abstract int pathCostFunction(Node node);

	// expand a specific node based on the operators of the problem
	public abstract ArrayList<Node> expand(Node node);

	public void setFirstHeuristic(Node node) {
		// TODO Auto-generated method stub

	}

	public abstract void setSecondHeuristic(Node node);

	public abstract void setThirdHeuristic(Node node);
}


