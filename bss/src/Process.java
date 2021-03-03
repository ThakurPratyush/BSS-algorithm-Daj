import java.util.*;
import java.util.concurrent.TimeUnit;

public class Process implements ProcessInterface {
     //vectorClock variable corresponding to each process
	private int[] vectorClock; 
	//List of nodes/process participating in program
	private List<String> NodeList = new ArrayList<String>(); 
	// buffer to add messages that do not satisfy the delivery condition
    Set<Message> buffer = new HashSet<Message>(); 
    // nonce is the variable which we add to the clock at the sending process 
    // here the increment is done by 1 so nonce is set to 1
    int nonce = 1;	
    // current clock that is used to increment a particular index of vector clock corresponding to the selected process
	int indexOfLocalClock;

	public void upd( int[] vClock) {
		// function to update the clock
		vectorClock = vClock;
	}
	public Process(List<String> NodeList, String currentNode){
		//initialize the nodes and create corresponding vector clock
        System.out.println("Process constructor");
		this.NodeList = NodeList; // assigning node corresponding to the proccess initiating the constructor
		int numberOfProcesses = NodeList.size(); // contains number of nodes
		vectorClock = new int[numberOfProcesses]; //initializing the vector clock of zeros of corresponding process
		
		// assigning the corresponding local clock index to the current node
		for(int i = 0; i < numberOfProcesses; i++){
			/* iterate through all the processes and if we find the current node then we assign it to the indexOfLocalClock
             *which is then set into the vectorClock   			
 			*/
		    if(NodeList.get(i).equals(currentNode)){
		        indexOfLocalClock = i;
            }
		}
	}


	public int getLocalClock() {
		return vectorClock[indexOfLocalClock];
	}

	
	public void updLocalClock() {
		vectorClock[indexOfLocalClock] += nonce;
	}
	

	public void receive(Message msgIn, int processID)  {
		  
		System.out.println("Start receiving");

        // we add delays to check the correct ordering of messages
        if(processID == 1){
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        if (chkVectorClock(msgIn, processID )) {
        	/* we deliver the passed message by calling the deliver function and
			* then we will look the buffer if the clock corresponding to the message in 
			* the buffer is satisfying the delivery condition then we will call the 
			* delivery function corresponding to the message extracted from the buffer
			*/
			deliver(msgIn);
			while (!buffer.isEmpty()) {
				for (Message elem : buffer) {
					receive(elem, processID); 
					}
				}
			}
			// if the message passed does not satify condition the it is added to he message buffer
		} else {
			buffer.add(msgIn);
		}
	}
	


	public void deliver(Message msgIn) {
		// function called when the message is to be delivered to a process  
		System.out.println("Start delivering");
    // updating the vector clock corresponding to the message passed in argument
		updateVectorClock(msgIn);
         // print message and corresponding vector clock
		System.out.println("Received "+msgIn.getMessage() + Arrays.toString(msgIn.getVectorClock()));
		// remove from the buffer
		if(buffer.contains(msgIn)) {
			buffer.remove(msgIn);
		}
    }

	@Override
	public void broadcast(Message msgIn) {
		  // send message to all the other process
		System.out.println("Broadcast");
		System.out.println("Clock Before receiving msg "+Arrays.toString(msgIn.getVectorClock()));  
		// update clock before sending the message
		updLocalClock();
          
	//	ProcessInterface otherProcess;
		for (int i = 0; i< NodeList.size(); i++ ){
			if(i != indexOfLocalClock){
				try{
					// message object created and sent to all the processes added to the nodelist
					Message msgOut = new Message(msgIn.getMessage(),msgIn.getVectorClock());
					 System.out.print(msgIn.getMessage()+"[");
					 //System.out.println(String.valueOf(vectorClock[0])+","+String.valueOf(vectorClock[1])+","+String.valueOf(vectorClock[2])+"]");
					 receive(msgOut, indexOfLocalClock);
					//otherProcess.receive(msgOut, indexOfLocalClock);
				}catch (Exception e) {
					System.out.println("Broadcast Exception: " + e);
				}
			}
		}

	}


	private boolean chkVectorClock(Message m, int index) {
		// return true/false according to the message can be delivered or not according to the bss algorithm
        System.out.println("checkVectorClock");
        boolean deliverable = false;
        int ones=0;
        for(int i=0;i<NodeList.size();i++)
        {
        	// check that the clocks should differ only by 1 or 0 for a particular index i in the vectorClock array
        	if ((vectorClock[i] + 1== m.getVectorClock()[i]))
        	{
        		ones++;
        	}
        }
		// if clock vary by at most 1 then message is deliverable
        if (ones<=1)
        {
        	deliverable = true;
        }
        System.out.println("Deliverable is: "+deliverable);
		return deliverable;
	}

	
	private void updateVectorClock(Message m) {
		//updates vector clock
		// element wise maximum performed 
        System.out.println("Update vectorClock");
        int length = m.getVectorClock().length;
		for (int i = 0; i < length; i++) {
			if ((vectorClock[i]) < m.getVectorClock()[i]) {
				vectorClock[i] = m.getVectorClock()[i];
			}
		}
	}


	@Override
	public int[] getVectorClock(){
		return vectorClock;
	}
}
