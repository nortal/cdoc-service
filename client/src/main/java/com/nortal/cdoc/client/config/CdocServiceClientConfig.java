package com.nortal.cdoc.client.config;

import com.nortal.cdoc.client.consumer.CdocServiceClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

/**
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 */
@Configuration
@PropertySource(value = "classpath:cdoc-service-client.properties", ignoreResourceNotFound = true)
public class CdocServiceClientConfig {
  @Bean
  public RestTemplate cdocServiceRestTemplate() {
    return new RestTemplate();
  }

  @Bean
  public CdocServiceClient cdocServiceClient() {
    return new CdocServiceClient();
  }
}
