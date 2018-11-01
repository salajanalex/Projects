package ro.nttdata.tutorial.admin.entity;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class ValidationTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeClass
    public static void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterClass
    public static void close() {
        validatorFactory.close();
    }

    @Test
    public void testAddressViolations() {
        Address address = new Address("a", -2, "b", "iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
        Set<ConstraintViolation<Address>> violationSet = validator.validate(address);
        Assert.assertEquals(4, violationSet.size());
    }

    @Test
    public void testCompanyViolations() {
        Company company = new Company("","a");
        Set<ConstraintViolation<Company>> violationSet = validator.validate(company);
        Assert.assertEquals(3, violationSet.size());
    }

    @Test
    public void testPersonViolations() {
        Person person = new Person("", "b", 222);
        Set<ConstraintViolation<Person>> violationSet = validator.validate(person);
        Assert.assertEquals(4, violationSet.size());
    }

}
