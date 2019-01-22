package com.nortal.cdoc.client.config;

import com.nortal.cdoc.client.consumer.CdocServiceClient;
import com.nortal.cdoc.client.consumer.CdocServiceErrorHandler;
import java.util.Arrays;
import javax.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 */
@Configuration
@PropertySource(value = "classpath:cdoc-service-client.properties", ignoreResourceNotFound = true)
public class CdocServiceClientConfig {
  @Resource
  private Environment env;

  @Bean
  public RestTemplate cdocServiceRestTemplate() {
    RestTemplate rt =
        new RestTemplate(Arrays.asList(new FormHttpMessageConverter(),
                                       new ResourceHttpMessageConverter(),
                                       new ByteArrayHttpMessageConverter()));
    rt.setErrorHandler(new CdocServiceErrorHandler());

    SimpleClientHttpRequestFactory rf = new SimpleClientHttpRequestFactory();
    rf.setConnectTimeout(env.getProperty("cdoc.service.app.connect.timeout", Integer.class, 0));
    rf.setReadTimeout(env.getProperty("cdoc.service.app.read.timeout", Integer.class, 0));
    rt.setRequestFactory(rf);
    return rt;
  }

  @Bean
  public CdocServiceClient cdocServiceClient() {
    return new CdocServiceClient();
  }
}
