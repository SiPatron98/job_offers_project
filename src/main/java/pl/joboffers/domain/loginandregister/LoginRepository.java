package pl.joboffers.domain.loginandregister;

import java.util.Optional;

public interface LoginRepository {

    User register(User user);

    Optional<User> findByLogin(String login);
}
