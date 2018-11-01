//package ro.nttdata.tutorial.JMS2;
//
//import org.apache.activemq.ActiveMQConnectionFactory;
//
//import javax.jms.Connection;
//import javax.jms.Destination;
//import javax.jms.MessageConsumer;
//import javax.jms.Session;
//import javax.naming.InitialContext;
//
//public class MessageReceiver
//{
//    public static void main(String[] args)
//    {
//        try
//        {
//            InitialContext ctx = new InitialContext();
//
//            ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory("tcp://localhost:4848");
//
//            Connection connection = cf.createConnection();
//
//            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//
//            Destination destination = session.createQueue("test.prog.queue");
//
//            MessageConsumer consumer = session.createConsumer(destination);
//
//            consumer.setMessageListener(new MapMessageListener());
//            connection.start();
//        }
//        catch (Exception e)
//        {
//            System.out.println(e);
//        }
//
//    }
//}