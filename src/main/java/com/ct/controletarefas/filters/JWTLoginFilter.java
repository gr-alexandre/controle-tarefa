package com.ct.controletarefas.filters;

import com.ct.controletarefas.services.TokenAuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

	public JWTLoginFilter(String url, AuthenticationManager authManager) {
		super(new AntPathRequestMatcher(url));
		setAuthenticationManager(authManager);
	}



	@Override
	protected void successfulAuthentication(
			HttpServletRequest req,
			HttpServletResponse res, FilterChain chain,
			Authentication auth
			) throws IOException, ServletException {
		TokenAuthenticationService.addAuthentication(res, auth.getName());
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException {
		UsuarioCredenciais creds = new ObjectMapper().readValue(request.getInputStream(), UsuarioCredenciais.class);

		return getAuthenticationManager().authenticate(
				new UsernamePasswordAuthenticationToken(
						creds.getEmail(),
						creds.getPassword(),
						Collections.<GrantedAuthority>emptyList()
						)
				);
	}

}
