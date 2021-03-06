package dev.spider.configs;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class SecretConfig { 
     
    @Bean
    @ConditionalOnMissingBean(SecretProcess.class)
    public SecretProcess secretProcess() { 
        return new AESAlgorithm() ; 
    } 
     
    @Component
    @ConfigurationProperties(prefix = "secret")
    public static class SecretProperties { 
         
        private Boolean enabled ; 
        private String key ; 
 
        public Boolean getEnabled() { 
            return enabled; 
        } 
 
        public void setEnabled(Boolean enabled) { 
            this.enabled = enabled; 
        } 
 
        public String getKey() { 
            return key; 
        } 
 
        public void setKey(String key) { 
            this.key = key; 
        } 
         
    } 
     
} 