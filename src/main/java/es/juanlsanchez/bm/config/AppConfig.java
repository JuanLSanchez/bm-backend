package es.juanlsanchez.bm.config;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Configuration
@ConfigurationProperties(prefix = "app.properties", ignoreUnknownFields = false)
public class AppConfig {

  private final Security security = new Security();
  private final CorsConfiguration cors = new CorsConfiguration();


  // Internal class
  public static class Security {

    @Getter
    private final Authentication authentication = new Authentication();

    @Getter
    @Setter
    public static class Authentication {

      @NotEmpty
      private final Jwt jwt = new Jwt();

      @Getter
      @Setter
      public static class Jwt {

        private String secret;

        private long tokenValidityInSeconds = 1800;
        private long tokenValidityInSecondsForRememberMe = 2592000;

      }
    }
  }

}
