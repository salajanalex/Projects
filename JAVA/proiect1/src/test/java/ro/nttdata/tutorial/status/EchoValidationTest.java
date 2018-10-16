package ro.nttdata.tutorial.status;

import org.junit.*;
import ro.nttdata.tutorial.status.boundery.StatusResource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.executable.ExecutableValidator;
import java.lang.reflect.Method;
import java.util.Set;

@Ignore
public class EchoValidationTest {
    private static ValidatorFactory validatorFactory;
    private static ExecutableValidator validator;
    private final String GOODECHO = "123";
    private final String LONGECHO = "123456765757456";
    private final  String BADECHO = "abc";
    private final String OOBMESSAGE = "Echo Length should be between 1 and 10!!";
    private final String ICMESSAGE = "Invalid Characters!!";

    @BeforeClass
    public static void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator().forExecutables();
    }

    @AfterClass
    public static void close() {
        validatorFactory.close();
    }

   @Test
    public void noViolationsTest() throws Throwable{
       StatusResource object = new StatusResource();
       Method method = StatusResource.class
               .getMethod("ping", String.class);
       Object[] parameterValues = {GOODECHO};
       Set<ConstraintViolation<StatusResource>> violations
               = validator.validateParameters(object, method, parameterValues);
      Assert.assertTrue(violations.isEmpty());
   }

    @Test
    public void outOfBoundsEchoTest() throws Throwable{
        StatusResource object = new StatusResource();
        Method method = StatusResource.class
                .getMethod("ping", String.class);
        Object[] parameterValues = {LONGECHO};
        Set<ConstraintViolation<StatusResource>> violations
                = validator.validateParameters(object, method, parameterValues);
        violations.stream().forEach(i->Assert.assertEquals(OOBMESSAGE,i.getMessage()));
        Assert.assertEquals(1,violations.size());
    }

    @Test
    public void invalidCharactersTest() throws Throwable{
        StatusResource object = new StatusResource();
        Method method = StatusResource.class
                .getMethod("ping", String.class);
        Object[] parameterValues = {BADECHO};
        Set<ConstraintViolation<StatusResource>> violations
                = validator.validateParameters(object, method, parameterValues);
        violations.stream().forEach(i->Assert.assertEquals(ICMESSAGE,i.getMessage()));
        Assert.assertEquals(1,violations.size());
    }


}
