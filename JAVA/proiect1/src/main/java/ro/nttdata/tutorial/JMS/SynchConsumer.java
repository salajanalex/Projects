//package ro.nttdata.tutorial.JMS;
//
//import javax.annotation.Resource;
//import javax.ejb.ActivationConfigProperty;
//import javax.ejb.MessageDriven;
//import javax.jms.*;
//
//@SuppressWarnings("Duplicates")
//@MessageDriven(mappedName="jms/Queue", activationConfig =  {
//        @ActivationConfigProperty(propertyName = "acknowledgeMode",
//                propertyValue = "Auto-acknowledge"),
//        @ActivationConfigProperty(propertyName = "destinationType",
//                propertyValue = "javax.jms.Queue")
//})
//public class SynchConsumer {
//
//    @Resource(lookup = "jms/ConnectionFactory")
//    private static ConnectionFactory connectionFactory;
//    @Resource(lookup = "jms/Queue")
//    private static Queue queue;
//    @Resource(lookup = "jms/Topic")
//    private static Topic topic;
//
//
//    public static void main(String[] args) {
//
//        final int NUM_MSGS;
//        String destType = args[0];
//
//        Destination dest = null;
//        try {
//            if (destType.equals("queue")) {
//                dest = (Destination) queue;
//            } else {
//                dest = (Destination) topic;
//            }
//        } catch (Exception e) {
//            System.err.println("Error setting destination: " + e.toString());
//            e.printStackTrace();
//            System.exit(1);
//        }
//
//        //added by me so finally works
//        Connection connection = null;
//        //try catch block added by me, dosen't work otherwise
//        try {
//            connection = connectionFactory.createConnection();
//            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//
//            //MessageConsumer added by me
//            MessageConsumer consumer = session.createConsumer(dest);
//            connection.start();
//
//            /**
//             * 2 options:
//             * 1: timed synchronus receive:
//             *      cunsumer.recieve(1) => 1 milisecond; or recieveNoWait() -> recieves message only if one is available.
//             * 2: the method blocks indefinitely until a message arrives:
//             *     consumer.receive();    or     consumer.receive(0);
//             */
//            while (true) {
//                Message m = consumer.receiveNoWait();
//                if (m != null) {
//                    if (m instanceof TextMessage) {
//                        TextMessage message = (TextMessage) m;
//                        System.out.println("Reading message: " + message.getText());
//                    } else {
//                        break;
//                    }
//                }
//            }
//
//        } catch (JMSException e) {
//            e.printStackTrace();
//        } finally {
//            if (connection != null) {
//                try {
//                    connection.close();
//                } catch (JMSException e) {
//                }
//            }
//        }
//    }
//}
