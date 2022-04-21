package innowise.zuevsky.helpdesk.util;

import innowise.zuevsky.helpdesk.domain.User;
import innowise.zuevsky.helpdesk.domain.enums.Role;
import innowise.zuevsky.helpdesk.domain.enums.Status;

public class UserTestUtil {

    private static final Long USER_ID = 666L;
    private static final String USER_FIRSTNAME = "User";
    private static final String USER_LASTNAME = "Userovich";
    private static final String USER_EMAIL = "test@mail.com";
    private static final String USER_PASSWORD = "pass";
    private static final Role USER_ROLE = Role.ROLE_EMPLOYEE;
    private static final Status USER_STATUS = Status.ACTIVE;

    public static User createTestUser() {
        return User.builder()
                .id(USER_ID)
                .firstName(USER_FIRSTNAME)
                .lastName(USER_LASTNAME)
                .email(USER_EMAIL)
                .password(USER_PASSWORD)
                .role(USER_ROLE)
                .status(USER_STATUS)
                .build();
    }
}
