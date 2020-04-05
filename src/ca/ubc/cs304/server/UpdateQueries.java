package ca.ubc.cs304.server;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdateQueries {
    private DatabaseConnectionHandler databaseConnectionHandler = DCHSingleton.getDatabaseConnectionHandler();

    @PutMapping("/applicant/update/{sin}/{major}/{year}")
    public String updateMajor(@PathVariable int sin, @PathVariable String major, @PathVariable int year) {
        return databaseConnectionHandler.updateApplicantMajor(sin, major, year);
    }
}
