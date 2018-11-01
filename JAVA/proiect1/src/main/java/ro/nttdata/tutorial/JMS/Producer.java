//package ro.nttdata.tutorial.JMS;
//
//import javax.annotation.Resource;
//import javax.jms.*;
//
//@SuppressWarnings("Duplicates")
//
//public class Producer {
//
//    @Resource(lookup = "jms/ConnectionFactory")
//    private static ConnectionFactory connectionFactory;
//    @Resource(lookup = "jms/Queue")
//    private static Queue queue;
//    @Resource(lookup = "jms/Topic")
//    private static Topic topic;
//
//    public static void main(String[] args) {
//
//        final int NUM_MSGS;
//        String destType = args[0];
//
//        System.out.println("Destination type is " + destType);
//        if (!(destType.equals("queue") || destType.equals("topic"))) {
//            System.err.println("Argument must be queue or " + "topic.");
//            System.exit(1);
//        }
//        if (args.length == 2) {
//            NUM_MSGS = (new Integer(args[1])).intValue();
//        } else {
//            NUM_MSGS = 1;
//        }
//
//        Destination dest = null;
//        try {
//            if (destType.equals("queue")) {
//                dest = (Destination) queue;
//            } else {
//                dest = (Destination) topic;
//            }
//        }
//        catch (Exception e) {
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
//            MessageProducer producer = session.createProducer(dest);
//            TextMessage message = session.createTextMessage();
//
//            for (int i = 0; i < NUM_MSGS; i++) {
//                message.setText("This is message " + (i + 1) + " from producer");
//                System.out.println("Sending message: " + message.getText());
//                producer.send(message);
//                producer.send(session.createMessage());
//            }
//
//
//        } catch (JMSException e) {
//            e.printStackTrace();
//        }  finally {
//            if (connection != null) {
//                try { connection.close(); }
//                catch (JMSException e) { }
//            }
//        }
//
//
//    }
//}
