package innowise.zuevsky.helpdesk.it;

import org.junit.ClassRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@DirtiesContext
public class DatabaseIT {

    @Autowired
    protected TestRestTemplate testRestTemplate ;

    @ClassRule
    public PostgreSQLContainer<TestPostgresContainer> postgresDB = TestPostgresContainer.getInstance();


}