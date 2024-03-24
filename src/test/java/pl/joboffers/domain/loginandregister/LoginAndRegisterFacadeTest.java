package pl.joboffers.domain.loginandregister;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.joboffers.domain.loginandregister.dto.RegisterUserDto;
import pl.joboffers.domain.loginandregister.dto.RegistrationResultDto;
import pl.joboffers.domain.loginandregister.dto.UserDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LoginAndRegisterFacadeTest {

    LoginAndRegisterFacade loginAndRegisterFacade = new LoginAndRegisterFacade(
            new LoginRepositoryTestImpl()
    );

    @Test
    public void shouldThrowExceptionWhenUserNotFound() {
        // given
        String login = "login";

        // when, then
        Assertions.assertThrows(
                UserNotFoundException.class,
                () -> loginAndRegisterFacade.findByLogin(login),
                "Cannot find user with this login: [%s]".formatted(login)
        );
    }
    @Test
    public void shouldFindByLogin() {
        // given
        RegisterUserDto registerUserDto = new RegisterUserDto("login", "password");
        RegistrationResultDto register = loginAndRegisterFacade.register(registerUserDto);

        // when
        UserDto userDto = loginAndRegisterFacade.findByLogin(register.login());

        // then
        assertThat(userDto).isEqualTo(new UserDto(register.id(), "login", "password"));
    }
    @Test
    public void shouldRegisterUser() {
        // given
        RegisterUserDto registerUserDto = new RegisterUserDto("login", "password");

        // when
        RegistrationResultDto register = loginAndRegisterFacade.register(registerUserDto);

        // then
        assertAll(
                () -> assertThat(register.created()).isTrue(),
                () -> assertThat(register.login()).isEqualTo("login")
        );
    }

}