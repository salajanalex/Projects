//package ro.nttdata.tutorial.JMS2;
//
//import org.apache.activemq.ActiveMQConnectionFactory;
//
//import javax.jms.*;
//import javax.naming.Context;
//import javax.naming.InitialContext;
//
//public class MessageSender
//{
//    public static void main(String[] args)
//    {
//        Connection connection = null;
//        try
//        {
//            Context ctx = new InitialContext();
//
//            ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory("tcp://localhost:4848");
//
//            connection = cf.createConnection();
//
//            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//
//            Destination destination = session.createQueue("test.message.queue");
//
//            MessageProducer messageProducer = session.createProducer(destination);
//
//            MapMessage message = session.createMapMessage();
//
//            message.setString("Name", "Tim");
//            message.setString("Role", "Developer");
//            message.setDouble("Salary", 850000);
//
//            messageProducer.send(message);
//
//        }
//        catch (Exception e)
//        {
//            System.out.println(e);
//        }
//        finally
//        {
//            if (connection != null)
//            {
//                try
//                {
//                    connection.close();
//                }
//                catch (JMSException e)
//                {
//                    System.out.println(e);
//                }
//            }
//            System.exit(0);
//        }
//
//
//    }
//}