package innowise.zuevsky.helpdesk.it;

import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

@TestPropertySource("classpath:application-test.yml")
public class HelpdeskPostgresContainer extends PostgreSQLContainer<HelpdeskPostgresContainer> {

    private static final String IMAGE_VERSION = "postgres:alpine";

    private static HelpdeskPostgresContainer container;

    private HelpdeskPostgresContainer() {
        super(IMAGE_VERSION);
    }

    public static synchronized HelpdeskPostgresContainer getInstance() {
        if (container == null) {
            container = new HelpdeskPostgresContainer();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("TEST_DB_URL", container.getJdbcUrl());
        System.setProperty("TEST_DB_USERNAME", container.getUsername());
        System.setProperty("TEST_DB_PASSWORD", container.getPassword());
    }

    @Override
    public void stop() {

    }

}