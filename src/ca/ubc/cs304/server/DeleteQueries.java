package ca.ubc.cs304.server;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeleteQueries {
    private DatabaseConnectionHandler databaseConnectionHandler = DCHSingleton.getDatabaseConnectionHandler();

    @DeleteMapping("/applicant/delete-applicant/{sin}")
    public String deleteApplicant(@PathVariable int sin) {
        return databaseConnectionHandler.deleteApplicant(sin);
    }
}
