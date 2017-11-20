package br.com.portalCliente.security.authentication;

import br.com.portalCliente.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class AuthService {

    @Autowired
    @Qualifier("authenticationManager")
    private AuthenticationManager authManager;

    private UserAuth userAuth;
    private String USER = "user";

    public User authentication(UserAuth userAuth, boolean product) throws AuthenticationException {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userAuth.getLogin(), userAuth.getPassword());

        Authentication authentication = authManager.authenticate(authenticationToken);
        userAuth = (UserAuth) authentication.getDetails();

        User user = User.find(userAuth.getId());
        user.setLastLogin(new Date());
        user.merge();

        return user;
    }

    public boolean validateUser() {

        User user = User.findByLogin(userAuth.getLogin());
        boolean validate = false;
        if (user != null) {
            String password = userAuth.getPassword();

            if (!user.getPassword().equals(password)) {
                throw new BadCredentialsException("Wrong password.");
            } else if (user.getPassword().equals(password)) {
                validate = true;
            }
        }
        return validate;

    }

    public UserAuth getUserAuth() {
        return this.userAuth;
    }
}
