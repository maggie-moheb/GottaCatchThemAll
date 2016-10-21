import java.awt.Point;
import java.util.ArrayList;
/**
 * State implementation
 * @author maggiemoheb, myriameayman, youmnasalah
 *
 */
public class State extends StateAbs {
	int x;
	int y;
	Direction direction;
	int pokemonsSoFar;
	int xHatch;
	boolean isgoal;
	ArrayList <Point> pokLocation; 
	/**
	 * state constructor contains the x and y position on grid
	 * direction of the agent 
	 * pokemon that the agent has not collected so far 
	 * xHatch is the steps remaining for the egg to hatch 
	 * @param x x location
	 * @param y y location
	 * @param d direction
	 * @param p pokemons
	 * @param xH hatch steps
	 * @param goal if the state is goal
	 * @param pok list of locations of pokemons left from this state
	 */
	public State(int x, int y, Direction d, int p, int xH,boolean goal,ArrayList<Point> pok) {
		this.x = x; 
		this.y = y; 
		this.direction = d; 
		this.pokemonsSoFar = p; 
		this.xHatch = xH;
		this.isgoal = goal;
		this.pokLocation = new ArrayList<Point>();
		for (int i = 0; i < pok.size(); i++)
			this.pokLocation.add(pok.get(i));
		if (pok.contains(new Point(this.x,this.y))) {
			this.pokLocation.remove(new Point(this.x,this.y));
			this.pokemonsSoFar = this.pokLocation.size();
		}
	}
	/**
	 * getters and setters
	 */
	public boolean isGoal() {
		return isgoal;
	}
	public void setGoal(boolean goal) {
		this.isgoal = goal;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public Direction getDirection() {
		return direction;
	}
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	public int getPokemonsSoFar() {
		return pokemonsSoFar;
	}
	public void setPokemonsSoFar(int pokemonsSoFar) {
		this.pokemonsSoFar = pokemonsSoFar;
	}
	public int getxHatch() {
		return xHatch;
	}
	public void setxHatch(int xHatch) {
		this.xHatch = xHatch;
	}
	/**
	 * compares if 2 states are equal
	 * @param state
	 * @return
	 */
	public boolean equals(State state) {
		if(this.x == state.x && this.y == state.y && this.direction == state.direction && this.pokemonsSoFar == state.pokemonsSoFar && this.xHatch == state.xHatch){
			if(this.pokemonsSoFar == 0 && this.xHatch >= 0 && this.xHatch != state.xHatch) return false;
			if(this.pokemonsSoFar > 0 && this.xHatch < 0) return false;
			return true;
		}
		return false; 
	}
	
}
