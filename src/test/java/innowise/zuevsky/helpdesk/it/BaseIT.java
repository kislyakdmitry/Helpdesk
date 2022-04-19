package innowise.zuevsky.helpdesk.it;

import org.junit.ClassRule;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest

@DirtiesContext
public class BaseIT {

    @ClassRule
    public PostgreSQLContainer<HelpdeskPostgresContainer> postgresDB = HelpdeskPostgresContainer.getInstance();

}