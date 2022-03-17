
package dev.spider.configs;

import java.security.Key;

public class AESAlgorithm extends AbstractSecretProcess {
 
    @Override 
    public String getAlgorithm() { 
        return "AES/ECB/PKCS5Padding"; 
    } 
     
    @Override 
    public Key keySpec() {
        return this.getKeySpec("AES") ; 
    } 
 
} 