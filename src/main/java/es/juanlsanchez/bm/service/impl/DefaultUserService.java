package es.juanlsanchez.bm.service.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import es.juanlsanchez.bm.domain.User;
import es.juanlsanchez.bm.repository.UserRepository;
import es.juanlsanchez.bm.security.SecurityUtils;
import es.juanlsanchez.bm.service.UserService;

@Service
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;

    @Inject
    public DefaultUserService(final UserRepository userRepository) {
	this.userRepository = userRepository;
    }

    public User getPrincipal() {
	User result = null;
	String login = SecurityUtils.getCurrentUserLogin();
	if (!StringUtils.isEmpty(login)) {
	    result = userRepository.findOneByLogin(login)
		    .orElseThrow(() -> new IllegalArgumentException("Not found user"));
	}
	return result;
    }

}