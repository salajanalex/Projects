//package ro.nttdata.tutorial.JMS2;
//
//import javax.jms.JMSException;
//import javax.jms.MapMessage;
//import javax.jms.Message;
//import javax.jms.MessageListener;
//
//public class MapMessageListener implements MessageListener
//{
//    public void onMessage(Message message)
//    {
//        if (message instanceof MapMessage)
//        {
//            MapMessage mapMessage = (MapMessage)message;
//
//            try
//            {
//                String name = mapMessage.getString("Name");
//                System.out.println("Name : " + name);
//            }
//            catch (JMSException e)
//            {
//                throw new RuntimeException(e);
//            }
//        }
//        else
//        {
//            System.out.println("Invalid Message Received");
//        }
//    }
//}