package ro.nttdata.tutorial.JMS4;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import ro.nttdata.tutorial.admin.controller.AddressController;
import ro.nttdata.tutorial.admin.controller.CompanyController;
import ro.nttdata.tutorial.admin.controller.PersonController;
import ro.nttdata.tutorial.admin.entity.Address;
import ro.nttdata.tutorial.admin.entity.Company;
import ro.nttdata.tutorial.admin.entity.Person;
import ro.nttdata.tutorial.admin.entityEnum.Entities;
import ro.nttdata.tutorial.admin.entityEnum.Enum;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.*;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/jms")
public class StringProducer {

    public StringProducer() throws JMSException {
    }

    @Resource(mappedName = "jms/ConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(mappedName = "jms/Queue")
    private Queue queue;

    @Resource(mappedName = "jms/Topic")
    private Topic topic;

    @Inject
    private AddressController addressController;
    @Inject
    private PersonController personController;
    @Inject
    private CompanyController companyController;

    /**
     * adds message to queue and topic.
     * @param string
     * @throws JMSException
     * @throws InterruptedException
     */
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Path("/queue/{string}")
    public void produceString(@PathParam("string") String string) throws JMSException, InterruptedException {
        Connection connection = connectionFactory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        MessageProducer messageProducer = session.createProducer(queue);
        TextMessage message = session.createTextMessage();
        message.setText("This is a queue Message: " + string);
        System.out.println("Sending message to queue: " + message.getText());
        messageProducer.send(message);

        MessageProducer producer = session.createProducer(topic);
        TextMessage msg = session.createTextMessage("This is a topic message: " + string);
        System.out.println("Sending message to topic: '" + string + "'");
        producer.send(msg);

    }

    /**
     * Adds to Messaging Queue: most populated address / largest company / oldest Person.
     * @param string of values "address" / "company" / "person"
     * @throws JMSException
     * @throws JsonProcessingException
     */
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Path("/entity/{string}")
    public void objectToQueue(@PathParam("string") @Pattern(message = "Invalid Name",
            regexp = "\\b(company|address|person|COMPANY|ADDRESS|PERSON)\\b")
                                          String string) throws JMSException, JsonProcessingException {
        Connection connection = connectionFactory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageProducer producer = session.createProducer(queue);
        TextMessage message = session.createTextMessage();
        boolean invalidInput = false;

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = null;
        
        Entities entity = Entities.valueOf(string.toUpperCase());
        switch (entity) {
            case ADDRESS:
                json = ow.writeValueAsString(getLargestCompany());
                break;
            case COMPANY:
                json = ow.writeValueAsString(getMostPopulatedAddress());
                break;
            case PERSON:
                json = ow.writeValueAsString(getOldestPerson());
                break;
            default:
                invalidInput = true;
        }
        if (invalidInput) {
            message = session.createTextMessage("production of Message failed, invalid input given: " + string);
            producer.send(message);
        } else {
            message.setText(json);
            producer.send(message);
        }

    }


    public Company getLargestCompany() {
        List<Company> companyList = companyController.getAllCompanies();
        Company latgestCompany = companyList.get(0);
        for (Company company : companyList) {
            if (company.getPersonList().size() > latgestCompany.getPersonList().size()) {
                latgestCompany = company;
            }
        }
        return  latgestCompany;
    }

    public Address getMostPopulatedAddress() {
        List<Address> addressList = addressController.getAllAddresses();
        Address mostPopulatedAddress = addressList.get(0);
        for (Address address : addressList) {
            if (address.getPersonList().size() > mostPopulatedAddress.getPersonList().size()) {
                mostPopulatedAddress = address;
            }
        }
        return mostPopulatedAddress;
    }

    public Person getOldestPerson() {
        List<Person> personList = personController.getAllPersons();
        Person oldestPerson = personList.get(0);
        for (Person person : personList) {
            if (person.getAge() > oldestPerson.getAge()){
                oldestPerson = person;
            }
        }
        return oldestPerson;
    }

}
