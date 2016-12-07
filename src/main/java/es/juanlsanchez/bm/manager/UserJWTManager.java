package es.juanlsanchez.bm.manager;

import org.springframework.security.core.AuthenticationException;

import es.juanlsanchez.bm.web.dto.JWTTokenDTO;
import es.juanlsanchez.bm.web.dto.LoginDTO;

public interface UserJWTManager {

    public JWTTokenDTO authorize(LoginDTO loginDTO) throws AuthenticationException;

}
