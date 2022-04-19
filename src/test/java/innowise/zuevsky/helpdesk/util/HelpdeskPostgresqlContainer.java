package innowise.zuevsky.helpdesk.util;



import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class HelpdeskPostgresqlContainer extends PostgreSQLContainer {

    private static final String IMAGE_VERSION = "postgres:11.1";

    private static HelpdeskPostgresqlContainer container;

    private HelpdeskPostgresqlContainer() {
        super(IMAGE_VERSION);
    }

    public static HelpdeskPostgresqlContainer getInstance(){
        if(container == null){
            container = new HelpdeskPostgresqlContainer();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME",container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());
    }

    @Override
    public void stop() {

    }
}