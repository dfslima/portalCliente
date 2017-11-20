package br.com.portalCliente.security.authentication;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import br.com.portalCliente.entity.user.User;
import br.com.portalCliente.security.Crypt;


public class SFAuthenticationProvider implements AuthenticationProvider {

    public Authentication authenticate(Authentication auth) throws AuthenticationException {

        String login = "";
        String password = "";

        try {

            login = (String) auth.getPrincipal();
            password = auth.getCredentials().toString();

        } catch (Exception e) {
            throw new BadCredentialsException("Wrong password.");
        }

        User user = User.findByLogin(login);
        if (user == null) {
            return null;
        }

        String passwordUser = Crypt.decrypt(user.getPassword());
        if (!password.equals(passwordUser)) {
            passwordUser = user.getPassword();
            if (!password.equals(passwordUser)) {
                throw new BadCredentialsException("Wrong password.");
            }
        }

        UserAuth userAuth = new UserAuth(user);
        SFAuthentication response = new SFAuthentication(userAuth);
        response.setAuthenticated(userAuth != null);
        return response;
    }

    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
