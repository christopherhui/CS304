package ca.ubc.cs304.server;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.model.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GetQueries {
    private DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();

    @GetMapping("/company/{first}/{second}")
    public List<Company> company(@PathVariable String first, @PathVariable String second) {
        return databaseConnectionHandler.getCompanyHiringInfo(first, second);
    }

    @GetMapping("/company/description/{filter}")
    public List<Job> companySelect(@PathVariable String filter) {
        return databaseConnectionHandler.getCompanyMatchingDescription(filter);
    }

    @GetMapping("/applicant/{cname}")
    public List<Pair<Applicant, ApplicationThroughFor>> applicantSelect(@PathVariable String cname) {
        List<Pair<Applicant, ApplicationThroughFor>> res = databaseConnectionHandler.findAllApplicants(cname);
//        List<Applicant> apps = new ArrayList<>();
//        for (Pair<Applicant, ApplicationThroughFor> p : res) {
//            apps.add(p.getFirst());
//        }
        return res;
    }

    @GetMapping("/applications/count/{sin}")
    public int noApps(@PathVariable Integer sin) {
        return databaseConnectionHandler.findNoApps(sin);
    }

    @GetMapping("/company/advanced")
    public List<Company> companyHireInternGreater() {
        return databaseConnectionHandler.findHireAvgTermsWorked();
    }

    @GetMapping("/applicant/all-submit")
    public List<Applicant> appliedToAll() {
        return databaseConnectionHandler.appliedToAll();
    }
}
