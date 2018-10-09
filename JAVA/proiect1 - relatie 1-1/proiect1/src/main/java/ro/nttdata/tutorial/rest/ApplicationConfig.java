package ro.nttdata.tutorial.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import ro.nttdata.tutorial.admin.boundary.AddressResource;
import ro.nttdata.tutorial.admin.boundary.CompanyResource;
import ro.nttdata.tutorial.admin.boundary.PersonResource;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/")
public class ApplicationConfig extends ResourceConfig {

    public ApplicationConfig(){
        super();
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JaxbAnnotationModule());
        register(new JacksonJaxbJsonProvider(mapper, JacksonJaxbJsonProvider.DEFAULT_ANNOTATIONS));
        property(ServerProperties.MOXY_JSON_FEATURE_DISABLE, true);

        register(AddressResource.class);
        register(CompanyResource.class);
        register(PersonResource.class);

    }


}
