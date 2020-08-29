package engine.services;

import engine.entities.User;

public interface UserService {
    User getUserByEmail(final String email);
    User saveUser(final User user);
}
