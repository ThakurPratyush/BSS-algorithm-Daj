import daj.*;

public class test extends Application {
	@Override
	public void construct() {
		// creating nodes for visualization in daj
		Node node0 = node(new Prog(0), "0", 90, 90);
	    Node node1 = node(new Prog(1), "1", 180, 200);
	    Node node2 = node(new Prog(2), "2", 350, 70);
	    // linking the nodes to form a graph like structure
	    link(node0, node1);
	    link(node0, node1);
	    link(node1, node2);
	    link(node2, node0);
	    link(node0, node2);
	    link(node2, node1);
	    link(node1, node0);
	   
		
	}
	
//	public static void main(String[] args) {
		// to run the functionality of program using the run function from daj library
	//	new test().run();
		
	//}
	public void man() {
		new test().run();
	}
	public test()
	  {
	  super("A BSS Program", 550, 350);
	  }
	@Override
	  public void resetStatistics() {
	    // TODO Auto-generated method stub

	  }
	
	  public String getText () {

		return "BSS Algorithm for message ordering";

		}

	
}
