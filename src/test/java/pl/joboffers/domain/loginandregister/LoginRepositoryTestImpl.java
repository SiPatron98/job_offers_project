package pl.joboffers.domain.loginandregister;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class LoginRepositoryTestImpl implements LoginRepository {

    private final Map<String, User> userList = new ConcurrentHashMap<>();
    @Override
    public User register(User entity) {

        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .login(entity.login())
                .password(entity.password())
                .build();

        userList.put(user.login(), user);
        return user;
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return Optional.ofNullable(userList.get(login));
    }
}
