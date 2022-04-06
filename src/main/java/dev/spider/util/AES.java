
package dev.spider.util;

import dev.spider.App;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class AES {
    private static final String AES_ALG = "AES";
    private static final String AES_CBC_PCK_ALG = "AES/CBC/PKCS5Padding";
    private static final byte[] AES_IV = initIv("AES/CBC/PKCS5Padding");
    private static final String CHARSET_UTF8 = "UTF-8";
    public static final String aesKey = "G242nUlMmiT1tcToX29xkg==";

    public AES() {
    }

    public static String aesEncrypt(String content, String aesKey) throws RuntimeException {
        return aesEncrypt(content, aesKey, "UTF-8");
    }

    public static String aesEncrypt(String content, String aesKey, String charset) throws RuntimeException {
        try {
            return new String(aesEncrypt(content.getBytes(charset), aesKey));
        } catch (Exception var4) {
            throw new RuntimeException("AES加密失败：Aescontent = " + content + "; charset = " + charset, var4);
        }
    }

    public static byte[] aesEncrypt(byte[] content, String aesKey) throws RuntimeException {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(AES_IV);
            cipher.init(1, new SecretKeySpec(Base64.decodeBase64(aesKey), "AES"), iv);
            byte[] encryptBytes = cipher.doFinal(content);
            return Base64.encodeBase64(encryptBytes);
        } catch (Exception var5) {
            throw new RuntimeException("AES加密失败：Aescontent = " + content, var5);
        }
    }

    public static String aesDecrypt(String content, String aesKey) throws RuntimeException {
        return aesDecrypt(content, aesKey, "UTF-8");
    }

    public static byte[] aesDecrypt(byte[] content, String aesKey) throws RuntimeException {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(AES_IV);
            cipher.init(2, new SecretKeySpec(Base64.decodeBase64(aesKey), "AES"), iv);
            return cipher.doFinal(Base64.decodeBase64(content));
        } catch (Exception var4) {
            throw new RuntimeException("AES解密失败：Aescontent = " + content, var4);
        }
    }

    public static String aesDecrypt(String content, String aesKey, String charset) throws RuntimeException {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(AES_IV);
            cipher.init(2, new SecretKeySpec(Base64.decodeBase64(aesKey), "AES"), iv);
            byte[] cleanBytes = cipher.doFinal(Base64.decodeBase64(content));
            return new String(cleanBytes, charset);
        } catch (Exception var6) {
            throw new RuntimeException("AES解密失败：Aescontent = " + content + "; charset = " + charset, var6);
        }
    }

    private static byte[] initIv(String fullAlg) {
        byte[] iv;
        int i;
        try {
            Cipher cipher = Cipher.getInstance(fullAlg);
            int blockSize = cipher.getBlockSize();
            iv = new byte[blockSize];

            for (i = 0; i < blockSize; ++i) {
                iv[i] = 0;
            }

            return iv;
        } catch (Exception var5) {
            int blockSize = 16;
            iv = new byte[blockSize];

            for (i = 0; i < blockSize; ++i) {
                iv[i] = 0;
            }

            return iv;
        }
    }

    public static void main(String[] args) {
        String aesKey = "G242nUlMmiT1tcToX29xkg==";
        App.Foo fuck = new App.Foo("fuck");
        String s = JsonUtil.toJsonString(fuck);
        byte[] bytes = aesEncrypt(s.getBytes(StandardCharsets.UTF_8), aesKey);
        byte[] bytes1 = aesDecrypt(bytes, aesKey);
        System.out.println(new String(bytes1));
    }
}
