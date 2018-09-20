package ro.nttdata.tutorial.consumer.boundary;

import ro.nttdata.tutorial.consumer.producer.entity.BusinessObject;

import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

@MessageDriven(mappedName = "jms/queue/myqueue")
public class Consumer implements MessageListener {

    @Override
    public void onMessage(Message message) {
        try {
            // In JMS 1.1:
            //   ObjectMessage objectMsg = (ObjectMessage)message;
            //   BusinessObject payload = (BusinessObject)objectMsg.getObject();
            BusinessObject payload = message.getBody(BusinessObject.class);
            System.out.println("Message received: " + payload);
        } catch (JMSException e) {
            System.err.println("Error fetching msg payload: " + e.getMessage());
        }
    }
}