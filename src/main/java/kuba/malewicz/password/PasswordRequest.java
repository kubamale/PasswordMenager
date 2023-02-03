package kuba.malewicz.password;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PasswordRequest {

    private final String page;
    private final String login;
    private final String password;
}
