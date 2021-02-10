package application;
import java.io.*;
import java.util.ArrayList;
import java.util.Optional;

public class Network {	
	
	//A network has two lists to store all nodes and edges respectively.
	public ArrayList<Node> NodeList = new ArrayList<Node>(); 
	public ArrayList<Edge> EdgeList = new ArrayList<Edge>();
	  
	public Network() {
		
	}
	
	public String readTxt(String filePath) {
		try {
			File file = new File(filePath);
			if(file.isFile() && file.exists()) { //check the existence of file.
				InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "utf-8");
				BufferedReader br = new BufferedReader(isr);
				String lineTxt = null;
				ArrayList<String> nodeTemp = new ArrayList<String>();   //store the name of all the nodes after deduplication.
				ArrayList<String> edgeTemp = new ArrayList<String>();   //store the name of all the edges after deduplication.
				int lineindex = 0;
				while((lineTxt = br.readLine()) != null) {  //read file line by line.
					lineindex++;
					String UniprotRegex1 = "[OPQ]\\d[A-Z0-9]{3}\\d";   //regular expression of Uniprot Identifier
					String UniprotRegex2 = "[A-NR-Z][0-9]([A-Z][A-Z0-9]{2}[0-9]){1,2}";   //regular expression of Uniprot Identifier
					if(lineTxt.matches("\\s*" + UniprotRegex1 + "\\t" + UniprotRegex1 + "\\s*")   //Check if the protein name from the file matches uniprot identifier.
							|| lineTxt.matches("\\s*" + UniprotRegex1 + "\\t" + UniprotRegex2 + "\\s*")
							|| lineTxt.matches("\\s*" + UniprotRegex2 + "\\t" + UniprotRegex1 + "\\s*")
							|| lineTxt.matches("\\s*" + UniprotRegex2 + "\\t" + UniprotRegex2 + "\\s*")) {
					String[] temp = lineTxt.split("\\t");	//split two protein names.						
					if ((!edgeTemp.contains(temp[0]+ "\t" + temp[1])) && (!edgeTemp.contains(temp[1]+ "\t" + temp[0]))) edgeTemp.add(temp[0]+ "\t" + temp[1]); //deduplication of edges.
					if (!nodeTemp.contains(temp[0])) nodeTemp.add(temp[0]); //deduplication of nodes.
					if (!nodeTemp.contains(temp[1])) nodeTemp.add(temp[1]); //deduplication of nodes.
					}else {
						br.close();
						return "Wrong format of input file on line " + lineindex + ", please check."; // if protein name not matches uniprot identifier, show the error message.
					}
				}
				for (String x : nodeTemp) this.NodeList.add(new Node(x)); //create the final list of nodes after deduplication.
				for (String y : edgeTemp) {  //create the final list of edges after deduplication.
					Node node1 = this.NodeList.stream().filter(item -> item.getName().equals(y.split("\\t")[0])).findAny().get();
					Node node2 = this.NodeList.stream().filter(item -> item.getName().equals(y.split("\\t")[1])).findAny().get();
					this.EdgeList.add(new Edge(node1,node2));
				}
				br.close();
				return ("Number of nodes in current network:\t" + String.valueOf(this.NodeList.size()) + "\nNumber of edges in current network:\t" + String.valueOf(this.EdgeList.size())); //show the result of the input file.
			} else {
				return "Non-existed File!";
			}
		} catch (Exception e) {
			return "Error£º" + String.valueOf(e); // if other crash happens, show the error message.
		}
	}
	
	public int CalcDegree(String node) { //Calculate degree of a single node.
		int count = 0;
		for (Edge y : this.EdgeList) { //Traverse all the edge.
			if ((y.getNode1().getName().equals(node)) && (y.getNode2().getName().equals(node))) { //if given node has interaction with itself, degree +2.
				 count = count + 2;
				 continue;
			}
			if ((y.getNode1().getName().equals(node)) || (y.getNode2().getName().equals(node))) count ++; //if given node has interaction with itself, degree +1.
		}
		return count;
	}
	
	public double CalcAverDegree() { //Calculate average degree of network.
		return (double) Math.round((2*(double)this.EdgeList.size())/(double)this.NodeList.size()*1000)/1000; //Three decimal places.
	}
		
	public ArrayList<String> FindHub() { // find hubs of network
		ArrayList<Integer> degree = new ArrayList<Integer>();
		ArrayList<String> nodeName = new ArrayList<String>();
		ArrayList<Integer> maxIndex = new ArrayList<Integer>();
		ArrayList<String> result = new ArrayList<String>();
		for (Node x : this.NodeList) { // Traverse all nodes
			int count = 0;
			for (Edge y : this.EdgeList) {
				if ((y.getNode1().getName().equals(x.getName())) && (y.getNode2().getName().equals(x.getName()))) { //self interaction, degree +2
					count = count + 2;
					continue;
				}
				if (y.getNode1().getName().equals(x.getName()) || (y.getNode2().getName().equals(x.getName()))) count ++; //interaction with other nodes, degree +1
			}
			nodeName.add(x.getName());
			degree.add(count);
		}				
		int max = 0;
		for (int i = 0; i < degree.size(); i++) { //compare the degree of all nodes and find the maximum.
			if (degree.get(i) > max) {
				max = degree.get(i);
				maxIndex.clear();
				maxIndex.add(i);
				continue;
			}
			if (degree.get(i) < max ) {
				continue;
			}
			if (degree.get(i) == max) {
				maxIndex.add(i);
				continue;
			}
		}
		result.add(String.valueOf(max));
		for(Integer index : maxIndex) {
			result.add(nodeName.get(index));
		}
		return result;
	}
			
	public String DegDistr() { // calculate full degree distribution of network
		ArrayList<Integer> degree = new ArrayList<Integer>();
		ArrayList<Integer> degreeList = new ArrayList<Integer>();
		ArrayList<Integer> degreeCount = new ArrayList<Integer>();
		for (Node x : this.NodeList) {
			int count = 0;
				for (Edge y : this.EdgeList) {
					if ((y.getNode1().getName().equals(x.getName())) && (y.getNode2().getName().equals(x.getName()))) { //self interaction, degree +2
						count = count + 2;
						continue;
					}
					if (y.getNode1().getName().equals(x.getName()) || (y.getNode2().getName().equals(x.getName()))) count ++; //interaction with other nodes, degree +1
				}
				degree.add(count);
		}	
		for (Integer x : degree) { // count the number of nodes of each degree
			if (!degreeList.contains(x)) {
				degreeList.add(x);
				degreeCount.add(1);
				continue;
			}
			if (degreeList.contains(x)) {
				degreeCount.set(degreeList.indexOf(x), degreeCount.get(degreeList.indexOf(x))+1);
			}
		}
		for (int i = degreeList.size()-1; i>0; i--) { // sort the result of distribution
			int degreeTemp = 0;
			int countTemp = 0;
			for (int j = 0; j < i; j++) {
				if(degreeList.get(j) > degreeList.get(j+1)) {
					degreeTemp = degreeList.get(j);
					degreeList.set(j, degreeList.get(j+1));
					degreeList.set(j+1, degreeTemp);
					countTemp = degreeCount.get(j);
					degreeCount.set(j, degreeCount.get(j+1));
					degreeCount.set(j+1, countTemp);
				}
			}
		}
		String content = new String();
		for (int i = 0; i < degreeList.size(); i++) { // format output table
			content = content + degreeList.get(i) + "\t\t" + degreeCount.get(i) + "\n";
			}
		return content;
	}

	public Boolean addInter(String node1, String node2) { // function of adding interactions.
		for (Edge x : this.EdgeList) { // if the input interaction already exist, return false.
			if(x.getNode1().getName().equals(node1) & x.getNode2().getName().equals(node2)) return false;
			if(x.getNode2().getName().equals(node1) & x.getNode1().getName().equals(node2)) return false;
		}
		Optional<Node> Node1Optional = this.NodeList.stream().filter(item -> item.getName().equals(node1)).findAny(); //if node not in network, create new one
		if (!Node1Optional.isPresent()) this.NodeList.add(new Node(node1));
		Optional<Node> Node2Optional = this.NodeList.stream().filter(item -> item.getName().equals(node2)).findAny();
		if (!Node2Optional.isPresent()) this.NodeList.add(new Node(node2));
		this.EdgeList.add(new Edge(this.NodeList.stream().filter(item -> item.getName().equals(node1)).findAny().get(),this.NodeList.stream().filter(item -> item.getName().equals(node2)).findAny().get())); // add new interaction
		return true;
	}
}