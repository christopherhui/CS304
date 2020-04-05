package ca.ubc.cs304.server;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.model.Applicant;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostQueries {
    private DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();

    @PostMapping("/applicant/create/{sin}/{year}/{major}/{fname}/{lname}/{address}")
    String newApplicant(@PathVariable int sin, @PathVariable int year, @PathVariable String major,
                        @PathVariable String fname, @PathVariable String lname, @PathVariable String address) {
        Applicant app = new Applicant(sin, year, major, fname, lname, address, -1);
        return databaseConnectionHandler.insertApplicant(app);
    }
}
