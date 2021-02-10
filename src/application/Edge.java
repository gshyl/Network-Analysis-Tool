package application;

public class Edge {
	
	private Node node1 = null;         //An edge has two parameters: two single node object.
	private Node node2 = null;
	
	public Edge(Node NODE1, Node NODE2) {
		this.node1 = NODE1;
		this.node2 = NODE2;
	}
	
	public Node getNode1() {  //get the first node object of this edge.
		return this.node1;
	}
	
	public Node getNode2() {  //get the second node object of this edge.
		return this.node2;
	}
	
}
