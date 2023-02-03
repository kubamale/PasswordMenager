package kuba.malewicz.password;


import kuba.malewicz.appuser.AppUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Password {


    @Id
    @SequenceGenerator(
            name = "password_sequence",
            sequenceName = "password_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "password_sequence"
    )
    private Long id;
    @Column(nullable = false)
    private String page;
    @Column(nullable = false)
    private String login;
    @Column(nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "app_user_id"
    )
    private AppUser appUser;

    public Password(String page, String login, String password, AppUser appUser) {
        this.page = page;
        this.login = login;
        this.password = password;
        this.appUser = appUser;
        System.out.println("new password");

    }

    public String getPage() {
        return page;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return id +
                ". page:" + page +
                ", login=" + login +
                ", password=" + password +
                '}';
    }
}
