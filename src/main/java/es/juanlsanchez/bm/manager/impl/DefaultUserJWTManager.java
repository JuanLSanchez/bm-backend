package es.juanlsanchez.bm.manager.impl;

import javax.inject.Inject;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import es.juanlsanchez.bm.manager.UserJWTManager;
import es.juanlsanchez.bm.security.jwt.TokenProvider;
import es.juanlsanchez.bm.web.dto.JWTTokenDTO;
import es.juanlsanchez.bm.web.dto.LoginDTO;

@Component
public class DefaultUserJWTManager implements UserJWTManager {

  private final TokenProvider tokenProvider;
  private final AuthenticationManager authenticationManager;

  @Inject
  public DefaultUserJWTManager(final TokenProvider tokenProvider,
      final AuthenticationManager authenticationManager) {
    this.tokenProvider = tokenProvider;
    this.authenticationManager = authenticationManager;
  }

  public JWTTokenDTO authorize(LoginDTO loginDTO) throws AuthenticationException {

    JWTTokenDTO result;
    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
    Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    boolean rememberMe = (loginDTO.getRememberMe() == null) ? false : loginDTO.getRememberMe();
    String jwt = tokenProvider.createToken(authentication, rememberMe);
    result = new JWTTokenDTO(jwt);
    return result;
  }

}
