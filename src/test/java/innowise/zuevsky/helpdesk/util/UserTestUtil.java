package innowise.zuevsky.helpdesk.util;

import innowise.zuevsky.helpdesk.domain.enums.Role;
import innowise.zuevsky.helpdesk.dto.CurrentUser;

public class UserTestUtil {

    private static final CurrentUser employee = CurrentUser.builder().userName("user1_mogilev").role(Role.ROLE_EMPLOYEE).build();
    private static final CurrentUser manager = CurrentUser.builder().userName("manager1_mogilev").role(Role.ROLE_MANAGER).build();
    private static final CurrentUser engineer = CurrentUser.builder().userName("engineer1_mogilev").role(Role.ROLE_ENGINEER).build();

}
