package br.com.portalCliente.security.authentication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class SFAuthentication implements Authentication {

	private static final long serialVersionUID = 1L;
	
	private final UserAuth userAuth;
	private boolean autenticado;
	private List<GrantedAuthority> permission;
	
	
	public SFAuthentication(UserAuth userAuth) {
		this.userAuth = userAuth;
		permission = new ArrayList<GrantedAuthority>();
		permission.add(userAuth);
	}
	
	public String getName() {
		return userAuth != null ? userAuth.getName() : null;
	}
	
	public Collection<? extends GrantedAuthority> getAuthorities() {		
		return permission;
		
	}

	public Object getCredentials() {
		return userAuth != null ? userAuth.getPassword() : null;
	}

	public Object getDetails() {
		return userAuth;
	}

	public Object getPrincipal() {
		return userAuth != null ? userAuth.getLogin() : null;
	}

	public boolean isAuthenticated() {
		return this.autenticado;
	}

	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		this.autenticado = isAuthenticated;
		
	}

}
