package ro.nttdata.tutorial.JMS4;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@MessageDriven(mappedName = "jms/Topic", activationConfig = {
        @ActivationConfigProperty(propertyName = "acknowledgeMode",
                propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "destinationType",
                propertyValue = "javax.jms.Topic")
})
public class StringTopicConsumer2 implements MessageListener {

    @Resource
    private MessageDrivenContext mdc;
    @Override
    public void onMessage(Message message) {

        TextMessage msg = null;
        try {
            if (message instanceof TextMessage) {
                msg = (TextMessage) message;
                System.out.println("MESSAGE BEAN: CONSUMER 2: Message received from Topic: " +
                        msg.getText());
            } else {

                System.out.println("Message of wrong Type: " +
                        message.getClass().getName());
            }
        } catch (JMSException e) {
            e.printStackTrace();

            mdc.setRollbackOnly();
        } catch (Throwable te) {
            te.printStackTrace();
        }
    }


}
