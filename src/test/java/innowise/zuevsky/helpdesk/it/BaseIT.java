package innowise.zuevsky.helpdesk.it;

import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@DirtiesContext
public class BaseIT {

    @ClassRule
    public PostgreSQLContainer<TestPostgresContainer> postgresDB = TestPostgresContainer.getInstance();

}