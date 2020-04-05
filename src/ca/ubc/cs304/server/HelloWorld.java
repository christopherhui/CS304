package ca.ubc.cs304.server;

import ca.ubc.cs304.model.Applicant;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

// The Java class will be hosted at the URI path "/helloworld"
@Path("/helloworld")
public class HelloWorld {
    // The Java method will process HTTP GET requests
    @GET
    // The Java method will produce content identified by the MIME Media type "text/plain"
    @Produces(MediaType.APPLICATION_JSON)
    public Applicant getClichedMessage() {
        // Return some cliched textual content
        return new Applicant(100, 3283, "att", "adf", "adsf", "dasf");
    }
}