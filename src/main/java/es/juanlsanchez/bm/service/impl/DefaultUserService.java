package es.juanlsanchez.bm.service.impl;

import javax.inject.Inject;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import es.juanlsanchez.bm.CacheConfig;
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
      result = getOneByLogin(login);
    }
    return result;
  }

  @Cacheable(CacheConfig.LONG_CACHE)
  private User getOneByLogin(String login) {
    return userRepository.findOneByLogin(login)
        .orElseThrow(() -> new IllegalArgumentException("Not found user"));
  }

  @Override
  public User getUserWithAuthorities() {
    User user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get();
    user.getAuthorities().size(); // eagerly load the association
    return user;
  }

}
