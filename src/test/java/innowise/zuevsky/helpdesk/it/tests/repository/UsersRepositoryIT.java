package innowise.zuevsky.helpdesk.it.tests.repository;

import innowise.zuevsky.helpdesk.domain.User;
import innowise.zuevsky.helpdesk.domain.enums.Role;
import innowise.zuevsky.helpdesk.domain.enums.Status;
import innowise.zuevsky.helpdesk.it.BaseIT;
import innowise.zuevsky.helpdesk.repository.UsersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Sql({"/data/clear_users.sql", "/data/insert_users.sql"})
class UsersRepositoryIT extends BaseIT {

    @Autowired
    private UsersRepository usersRepository;

    private final User expectedUser = new User(2L, "user2", "user2", "user2_mogilev@yopmail.com", "$2a$12$UBXPkMHbwkC5ZFUWrInSKu65ba.joR3ZsVv2BoMhHH.GXtFsHR4p2", Role.ROLE_EMPLOYEE, Status.ACTIVE);

    @Test
    void findByEmail_shouldPass_WhenUserIsExists() {

        //when
        User currentUser = usersRepository.findByEmail("user2_mogilev@yopmail.com").orElseThrow();

        //then
        assertNotNull(currentUser);
        assertThat(currentUser.getEmail()).isEqualTo(expectedUser.getEmail());
        assertThat(currentUser.getPassword()).isEqualTo(expectedUser.getPassword());
    }

    @Test
    void save_ShouldPass_WhenUserWasSuccessfullySaved() {

        //when
        User currentUser = usersRepository.save(expectedUser);

        //then
        assertNotNull(currentUser);
        assertThat(currentUser.getEmail()).isEqualTo(expectedUser.getEmail());
        assertThat(currentUser.getPassword()).isEqualTo(expectedUser.getPassword());
    }
}