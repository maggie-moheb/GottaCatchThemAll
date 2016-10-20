import java.awt.Point;
import java.util.ArrayList;
class Vertex{
	int x;
	int y;
	int setNo;
	Vertex(Point point) {
		this.x = point.x;
		this.y = point.y;
		this.setNo = -5;
	}
	void setNo(int setNo) {
		this.setNo = setNo;
	}
	static ArrayList<Vertex> convertToVertex(ArrayList<Point> pokemonLocations) {
		ArrayList<Vertex> vertices = new ArrayList<Vertex>();
		for(int i = 0; i< pokemonLocations.size(); i++) {
			Vertex vertex = new Vertex(pokemonLocations.get(i));
			vertices.add(vertex);
		}
		return vertices;
	}
}
public class MinimumSpanningTreeEdge implements Comparable{
	int weight;
	Vertex start;
	Vertex end;
	
	public MinimumSpanningTreeEdge(Vertex start, Vertex end) {
		this.start = start;
		this.end = end;
		this.weight = manhattan(start, end);
	}
	
	public static int manhattan(Vertex start, Vertex end) {
		return Math.abs(start.x - end.x) + Math.abs(start.y - end.y);
	}
	
	@Override
	public int compareTo(Object o) {
		MinimumSpanningTreeEdge edge = (MinimumSpanningTreeEdge)o;
		if(this.weight > edge.weight) {
			return 1;
		}
		if(edge.weight > this.weight) {
			return -1;
		}
		return 0;
	}
	
	public static ArrayList<MinimumSpanningTreeEdge> convertToEdges(ArrayList<Vertex> pokemonLocations) {
		ArrayList<MinimumSpanningTreeEdge> pokemonEdges = new ArrayList<MinimumSpanningTreeEdge>();
		//System.out.println(pokemonLocations.size());
		for(int i = 0; i<pokemonLocations.size(); i++) {
			for(int j = i+1; j<pokemonLocations.size(); j++) {
				MinimumSpanningTreeEdge edge = new MinimumSpanningTreeEdge(pokemonLocations.get(i), pokemonLocations.get(j));
				pokemonEdges.add(edge);
			}
		}
		return pokemonEdges;
	}
}
