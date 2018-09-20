package ro.nttdata.tutorial.consumer.producer.boundary;


import ro.nttdata.tutorial.consumer.producer.entity.BusinessObject;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;


@Path("/producer")
@Stateless
public class TimerProducer {

    @Resource(lookup = "jms/queue/myqueue")
    private static Queue queue;

    @Inject
    private static JMSContext jmsContext;

    private static int counter = 1;
    private static boolean started = false;

    public enum ActionState{


    }

    @Path("/actions/{action}")
    @GET
    public String produce(@PathParam("action")String action){
        if (action.equals("start")){
            if (started){
                return "Already started";
            }
            else{
                started = true;
                loop();
            }
        }
        else if (action.equals("stop")){
            if(!started){
                return "Not started";
            }
            else {
                started = false;
                return "Action stopped";
            }
        }

        return "Action not supported";
    }

    private static void loop(){
        while (started){
            BusinessObject payload = new BusinessObject("This is message nr. " + counter);
            counter++;
            jmsContext.createProducer().send(queue, payload);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}