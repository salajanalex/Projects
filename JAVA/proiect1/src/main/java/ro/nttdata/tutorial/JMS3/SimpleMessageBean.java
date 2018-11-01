//package ro.nttdata.tutorial.JMS3;
//
//import javax.annotation.Resource;
//import javax.ejb.ActivationConfigProperty;
//import javax.ejb.MessageDriven;
//import javax.ejb.MessageDrivenContext;
//import javax.jms.JMSException;
//import javax.jms.Message;
//import javax.jms.MessageListener;
//import javax.jms.TextMessage;
//
//import static org.glassfish.admin.rest.RestService.logger;
//
//@MessageDriven(mappedName = "jms/Queue", activationConfig = {
//        @ActivationConfigProperty(propertyName = "acknowledgeMode",
//                propertyValue = "Auto-acknowledge"),
//        @ActivationConfigProperty(propertyName = "destinationType",
//                propertyValue = "javax.jms.Queue")
//})
//public class SimpleMessageBean implements MessageListener {
//    @Resource
//    private MessageDrivenContext mdc;
//
//    @Override
//    public void onMessage(Message message) {
//        TextMessage msg = null;
//
//        try {
//            if (message instanceof TextMessage) {
//                msg = (TextMessage) message;
//                logger.info("MESSAGE BEAN: Message received: " +
//                        msg.getText());
//            } else {
//                logger.warning("Message of wrong type: " +
//                        message.getClass().getName());
//            }
//        } catch (JMSException e) {
//            e.printStackTrace();
//            mdc.setRollbackOnly();
//        } catch (Throwable te) {
//            te.printStackTrace();
//        }
//    }
//}
