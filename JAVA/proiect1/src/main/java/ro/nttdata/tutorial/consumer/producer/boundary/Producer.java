package ro.nttdata.tutorial.consumer.producer.boundary;


import ro.nttdata.tutorial.consumer.producer.entity.BusinessObject;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/ro/nttdata/tutorial/consumer/producer")
@Stateless
public class Producer {

    @Resource(lookup = "jms/queue/myqueue")
    private Queue queue;

    @Inject
    private JMSContext jmsContext;

    private static int counter = 1;

    @Path("/jms20/")
    @GET
    public String produce() {
        BusinessObject payload = new BusinessObject("This is message nr. " + counter);
        counter++;
        jmsContext.createProducer().send(queue, payload);

        return "Hai ca o mers";
    }
}