package dev.spider.app.secret;

import dev.spider.App;
import dev.spider.configs.AbstractSecretProcess;
import dev.spider.configs.SecretProcess;
import dev.spider.util.JsonUtil;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;


public class TestAES {
    @Test
    public void testAesEnDeCrept() throws Exception {
        App.Foo foo = new App.Foo("2022-03-18 12:31:00");
        String input = JsonUtil.toJsonString(foo);
        String body = Base64.getEncoder().encodeToString(input.getBytes(StandardCharsets.UTF_8));
        System.out.println(body);
        SecretKey key = generateKey(128);
        IvParameterSpec ivParameterSpec = generateIv();
        String algorithm = "AES/CBC/PKCS5Padding";
        String cipherText = encrypt(algorithm, body, key, ivParameterSpec);
        System.out.println(cipherText);
        String plainText = decrypt(algorithm, cipherText, key, ivParameterSpec);
        System.out.println(plainText);
        String s = new String(Base64.getDecoder().decode(plainText.getBytes(StandardCharsets.UTF_8)));
        Assertions.assertEquals(input, s);


    }

    public static SecretKey generateKey(int n) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(n);
        SecretKey key = keyGenerator.generateKey();
        return key;
    }

    public static IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    public static String encrypt(String algorithm, String input, SecretKey key,
                                 IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] cipherText = cipher.doFinal(input.getBytes());
        return Base64.getEncoder()
                .encodeToString(cipherText);
    }

    public static String decrypt(String algorithm, String cipherText, SecretKey key,
                                 IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] plainText = cipher.doFinal(Base64.getDecoder()
                .decode(cipherText));
        return new String(plainText);
    }

    @Test
    public void testAES() {
        App.Foo foo = new App.Foo("2022-03-18 12:31:00");
        String input = JsonUtil.toJsonString(foo);
        String encrypt = encrypt(input);
        System.out.println(encrypt);
        String decrypt = decrypt(encrypt);
        System.out.println(decrypt);
    }

    public String decrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance(getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, keySpec());
            byte[] decryptBytes = cipher.doFinal(SecretProcess.Hex.decode(data));
            return new String(decryptBytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String encrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance(getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, keySpec());
            return SecretProcess.Hex.encode(cipher.doFinal(data.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getAlgorithm() {
        return "AES/ECB/PKCS5Padding";
    }

    public Key keySpec() {
        return this.getKeySpec("AES");
    }

    public Key getKeySpec(String algorithm) {
        if (algorithm == null || algorithm.trim().length() == 0) {
            return null;
        }
        String secretKey = "GKH/YF7zxakdOQpzLTvCNbXDwQxySj0O";
        switch (algorithm.toUpperCase()) {
            case "AES":
                return new SecretKeySpec(secretKey.getBytes(), "AES");
            case "DES":
                Key key;
                try {
                    DESKeySpec desKeySpec = new DESKeySpec(secretKey.getBytes());
                    SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
                    key = secretKeyFactory.generateSecret(desKeySpec);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                return key;
            default:
                return null;
        }
    }
}
