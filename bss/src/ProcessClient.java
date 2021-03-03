import daj.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class ProcessClient extends Application{
	// here we add the nodes assign them a position in the graph

	public ProcessClient() {
		 super("A BSS Program", 550, 350);
	}
	  public String getText () {
			return "BSS Algorithm for message ordering";
			}
	public static List<String> NodeList; // nodelist created corresponding to processes

        public static void main (String[] args) {
        	
            NodeList = new ArrayList<String>();

            test tst = new test();
            tst.man();
            // processes are added to the nodelist
           NodeList.add("p1");	
            NodeList.add("p2");	
            NodeList.add("p3");	
       
            try {
            	// node variables created to identify procces in node
           
                String Node0 = "p1";	
                // calling object of process class and adding nodes corresponding to process
                Process process1 = new Process(NodeList, Node0);
        
                	String Node1 = "p2";	
                	Process process2 = new Process(NodeList, Node1);
                	String Node2 = "p3";	
                	Process process3 = new Process(NodeList, Node2);
                
      
                boolean flag = true;
                while(flag){
                	/* processes broadcast the message by creating object of message class
                	 * and assigning the vector clock corresponding to the process sending the message
                	 * 
                	 */
                    process1.broadcast(new Message("-> MESSAGE:- Msg 1 from process 1!", process1.getVectorClock()));
                    
                    process1.broadcast(new Message("-> MESSAGE:- Msg 2 from process 1!", process1.getVectorClock()));
                    try{
                    TimeUnit.SECONDS.sleep(1);
                    } catch (Exception e){
                    	System.out.println("error during broadcast: " + e);
                    }
                    // updating the clocks of the sending / broadcasting processes
                    
                    process2.upd(process1.getVectorClock());
                    
                    process3.upd(process2.getVectorClock());
                    process3.broadcast(new Message("-> MESSAGE:- Msg 1 from process 3!", process2.getVectorClock()));
                  
                    process2.broadcast(new Message("-> MESSAGE:- Msg 1 from process 2!", process2.getVectorClock()));

                     flag = false;                      
                }
               

            }catch (Exception e) {
                System.out.println("Client Exception: " + e);
            }
        }
    	@Override
    	public void construct() {
    		//Node node0 = node(new Prog(0), "0", 100, 100);
    	}
		@Override
		public void resetStatistics() {
			
		}
}
