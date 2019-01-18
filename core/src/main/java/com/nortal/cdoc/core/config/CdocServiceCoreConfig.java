package com.nortal.cdoc.core.config;

import com.nortal.cdoc.core.service.CdocService;
import com.nortal.cdoc.core.service.CdocServiceImpl;
import javax.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

/**
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 */
@Configuration
@PropertySource(value = "classpath:cdoc-service-core.properties", ignoreResourceNotFound = true)
public class CdocServiceCoreConfig {
  @Resource
  private Environment env;

  @Bean
  public LdapContextSource cdocLdapContextSource() {
    LdapContextSource lcs = new LdapContextSource();
    lcs.setUrl(env.getProperty("cdoc.service.sk.ldap.url"));
    lcs.setBase(env.getProperty("cdoc.service.sk.ldap.base"));
    lcs.setAnonymousReadOnly(true);
    return lcs;
  }

  @Bean
  public LdapTemplate cdocLdapTemplate() {
    return new LdapTemplate(cdocLdapContextSource());
  }

  @Bean
  public CdocService cdocService() {
    return new CdocServiceImpl();
  }
}
