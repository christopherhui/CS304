package ca.ubc.cs304.server;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.model.Company;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProjectionCompany {
    private DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();

    @GetMapping("/company/{first}/{second}")
    public List<Company> company(@PathVariable String first, @PathVariable String second) {
        return databaseConnectionHandler.getCompanyHiringInfo(first, second);
    }
}