
// process class is implementing this interface
public interface ProcessInterface {
   
	  public void broadcast(Message m);

	    public void receive(Message m, int processID) ;

	    public void deliver(Message m) ;

	    public int[] getVectorClock() ;
}