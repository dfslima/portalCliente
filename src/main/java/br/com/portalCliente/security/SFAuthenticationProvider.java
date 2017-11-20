package br.com.portalCliente.security;

import br.com.portalCliente.entity.user.User;
import br.com.portalCliente.security.authentication.SFAuthentication;
import br.com.portalCliente.security.authentication.UserAuth;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class SFAuthenticationProvider implements AuthenticationProvider {


	public Authentication authenticate(Authentication auth)	throws AuthenticationException {
		String login = (String) auth.getPrincipal();
		//String password = PasswordUtilities.password(auth.getCredentials().toString());
		String password = auth.getCredentials().toString();
		
		User user = User.findByLogin(login);
		if (user == null) {
			return null;
		}

		String passwordUser = Crypt.decrypt(user.getPassword());
		if(!password.equals(passwordUser)){
			passwordUser =  user.getPassword();
			if(!password.equals(passwordUser)){
				throw new BadCredentialsException("Wrong password.");
			}
			
		}

		UserAuth userSecurity = new UserAuth(user);
		SFAuthentication resultado = new SFAuthentication(userSecurity);
		resultado.setAuthenticated(userSecurity != null);
		return resultado;		
	}

	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}
}