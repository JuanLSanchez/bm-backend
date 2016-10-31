package es.juanlsanchez.bm.web.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import es.juanlsanchez.bm.config.Constant;
import lombok.Data;

/**
 * A DTO representing a user's credentials
 */
@Data
public class LoginDTO {

    @Pattern(regexp = Constant.LOGIN_REGEX)
    @NotNull
    @Size(min = 1, max = 50)
    private String username;

    @NotNull
    @Size(min = Constant.PASSWORD_MIN_LENGTH, max = Constant.PASSWORD_MAX_LENGTH)
    private String password;

    private Boolean rememberMe;

}
