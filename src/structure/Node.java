package structure;

import java.io.Serializable;

public class Node implements Serializable {



		private int id;
	    public boolean leader = false;
	    private Integer neighbour; // vizinhos - portas
	    
	    public Node(int id, boolean leader, Integer vizinhos) {
	    	setId(id);
	    	setLeader(leader);
	    	setNeighbours(vizinhos);
	    }
	    
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public boolean isLeader() {
			return leader;
		}
		public void setLeader(boolean leader) {
			this.leader = leader;
		}
		
		public int getNeighbours() {
			return this.neighbour;
		}
		public void setNeighbours(int n) {
			this.neighbour = n;
		}
	    
}
