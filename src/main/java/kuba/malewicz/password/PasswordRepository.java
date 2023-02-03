package kuba.malewicz.password;


import kuba.malewicz.appuser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PasswordRepository extends JpaRepository<Password, Long> {

    List<Password> findAllByAppUser(AppUser appUser);
}
