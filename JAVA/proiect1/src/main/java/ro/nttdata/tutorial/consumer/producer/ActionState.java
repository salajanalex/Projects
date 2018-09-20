package ro.nttdata.tutorial.consumer.producer;

import ro.nttdata.tutorial.consumer.producer.boundary.TimerProducer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

public enum ActionState {
    START,
    STOP;

    @Path("/actions/{action}")
    @GET
    public String isAction(@PathParam("action") TimerProducer.ActionState action) throws MyApplicationException{
        switch(this){
            case START:

            case STOP:

            default:
                throw new MyApplicationException("Bad Request: 400");
        }
    }
}
