package ca.ubc.cs304.server;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.model.Job;
import org.springframework.web.bind.annotation.*;

@RestController
public class SelectCompanyDesc {
    private DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();

    @GetMapping("/company-select/{filter}")
    public List<Job> companySelect(@PathVariable String filter) {
        return databaseConnectionHandler.getCompanyMatchingDescription(filter);
    }
}