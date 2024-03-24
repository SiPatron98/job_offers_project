package pl.joboffers.domain.loginandregister;

import lombok.AllArgsConstructor;
import pl.joboffers.domain.loginandregister.dto.RegisterUserDto;
import pl.joboffers.domain.loginandregister.dto.RegistrationResultDto;
import pl.joboffers.domain.loginandregister.dto.UserDto;

@AllArgsConstructor
public class LoginAndRegisterFacade {

    private static final String NOT_FOUND_MESSAGE = "Cannot find user with this login: [%s]";

    private final LoginRepository loginRepository;

    public UserDto findByLogin(String login) {
        User user = loginRepository.findByLogin(login).orElseThrow(() -> new UserNotFoundException(NOT_FOUND_MESSAGE.formatted(login)));
        return UserDto.builder()
                .id(user.id())
                .login(user.login())
                .password(user.password())
                .build();
    }

    public RegistrationResultDto register(RegisterUserDto registerUserDto) {
        final User user = User.builder()
                .login(registerUserDto.login())
                .password(registerUserDto.password())
                .build();
        User savedUser = loginRepository.register(user);
        return RegistrationResultDto.builder()
                .login(savedUser.login())
                .id(savedUser.id())
                .created(true)
                .build();
    }
}
