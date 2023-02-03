package kuba.malewicz.appuser;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
public class AppUserRepositoryTest {
    @Autowired
    private AppUserRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void checkIfStudentExistsWithThatEmail(){
        String email = "jamila@gmail.com";
        AppUser user = new AppUser("kuba", "MAlewicz", email, "password", AppUserRole.USER );
        underTest.save(user);

        Optional<AppUser> optionalAppUser = underTest.findByEmail(email);


        assertThat(optionalAppUser.get()).isEqualTo(user);
    }
}
