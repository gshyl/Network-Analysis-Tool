package application;

public class Node {
	
	private String name;  // A node has a name parameter.
	
	public Node() {
		this.name = null;
	}
	
	public Node(String NAME) {
		this.name = NAME;
	}
	
	public String getName() {    //get the name of this node.
		return this.name;
	}
	
}
