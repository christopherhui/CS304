package ca.ubc.cs304.server;

import java.util.concurrent.atomic.AtomicLong;

import ca.ubc.cs304.model.Applicant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/greeting")
    public Applicant greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Applicant(324, 3243, "", "", "", "", 1);
    }
}