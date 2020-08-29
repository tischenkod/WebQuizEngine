package engine.controllers;

import engine.entities.User;
import engine.services.UserService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/register")
public class RegisterController {
    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping(path = "", consumes = "application/json")
    public User createUser(@Valid @RequestBody User user, HttpServletResponse res) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        try {
            return userService.saveUser(user);
        }catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
    }
}
