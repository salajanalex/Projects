//package ro.nttdata.tutorial.JMS3;
//
//import javax.annotation.Resource;
//import javax.jms.*;
//
//public class SimpleMessageClient {
//    private static final int NUM_MSGS = 3;
//    @Resource(mappedName = "jms/ConnectionFactory")
//    private static ConnectionFactory connectionFactory;
//
//    @Resource(mappedName = "jms/Queue")
//    private static Queue queue;
//
//    public static void main(String[] args) throws JMSException {
//        Connection connection = connectionFactory.createConnection();
//        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//        MessageProducer messageProducer = session.createProducer(queue);
//
//        TextMessage message = session.createTextMessage();
//
//        for (int i = 0; i < NUM_MSGS; i++) {
//            message.setText("This is message " + (i + 1));
//            System.out.println("Sending message: " + message.getText());
//            messageProducer.send(message);
//        }
//    }
//
//}
