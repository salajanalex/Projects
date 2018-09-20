//import com.example.jersey.User;
//
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.cfg.Configuration;
//import org.junit.Test;
//
//
///**
// * Unit test for simple App.
// */
//public class AppTest {
//
//    @Test
//    public void testApp() {
//        SessionFactory sessionFactory = new Configuration().configure()
//                .buildSessionFactory();
//        Session session = sessionFactory.openSession();
//        session.beginTransaction();
//
//        User user1 = new User("Alex", "pswd23", "mail1@yahoo");
//        User user2 = new User("Salajan", "pswd23", "mail2@yahoo");
//        User user3 = new User("Andru", "pswd23", "mail3@yahoo");
//        User user4 = new User("Marian", "pswd23", "mail4@yahoo");
//
//        session.save(user1);
//        session.save(user2);
//        session.save(user3);
//        session.save(user4);
//
//
//        session.getTransaction().commit();
//        session.close();
//    }
//}