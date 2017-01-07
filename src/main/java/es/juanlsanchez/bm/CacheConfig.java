package es.juanlsanchez.bm;

import java.util.concurrent.TimeUnit;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.cache.CacheBuilder;

@Configuration
@EnableCaching
public class CacheConfig {

  public final static String SHORT_CACHE = "shortCache";
  public final static String LONG_CACHE = "longCache";
  public final static String MEDIUM_CACHE = "mediumCache";

  @Bean
  public GuavaCache shortCache() {
    return new GuavaCache(SHORT_CACHE,
        CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.MINUTES).build());
  }

  @Bean
  public GuavaCache mediumCache() {
    return new GuavaCache(MEDIUM_CACHE,
        CacheBuilder.newBuilder().expireAfterWrite(30, TimeUnit.MINUTES).build());
  }

  @Bean
  public GuavaCache longCache() {
    return new GuavaCache(LONG_CACHE,
        CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.DAYS).build());
  }

}
