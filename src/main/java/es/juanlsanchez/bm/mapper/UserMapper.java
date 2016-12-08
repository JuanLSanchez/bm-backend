package es.juanlsanchez.bm.mapper;

import org.mapstruct.Mapper;

import es.juanlsanchez.bm.domain.User;
import es.juanlsanchez.bm.web.dto.UserDTO;

@Mapper(componentModel = "spring")
public interface UserMapper {

  public UserDTO userToUserDTO(User principal);

}
