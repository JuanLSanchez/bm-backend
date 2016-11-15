package es.juanlsanchez.bm.service;

import es.juanlsanchez.bm.domain.User;

public interface UserService {

  public User getPrincipal();

  public User getUserWithAuthorities();

}
