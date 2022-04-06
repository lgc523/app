package dev.spider.configs;

import dev.spider.util.AES;

import javax.annotation.Resource;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;

public abstract class AbstractSecretProcess implements SecretProcess {


    @Override
    public String decrypt(String data) {
        return AES.aesDecrypt(data, AES.aesKey);
    }


    @Override
    public String encrypt(String data) {
//        return AES.aesEncrypt(data, AES.aesKey);
        return data;
    }
}
