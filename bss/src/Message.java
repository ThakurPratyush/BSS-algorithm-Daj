import java.io.Serializable;
import java.util.List;
/*
 * Message class takes a message and assigns a clock corresponding to it
 * We can use the getMessage() and getVectorClock methods to get the string message and vector clock array
 * 
 */

public class Message implements Serializable{
    String m;
    int[] clock;


    Message(String m, int[] clock){
   // set the assigned message
    this.m = m;
    //associate message with clock passed in argument
    this.clock = clock;
    }

   
    public String getMessage(){
        return m;
    }

  
    public int[] getVectorClock(){
        return clock;
    }
}
