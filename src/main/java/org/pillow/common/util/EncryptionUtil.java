package org.pillow.common.util;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Random;

/**
 * 加密工具类
 */
public class EncryptionUtil {
    /**
     * 密码盐
     */
    private static final String PASSWORD_SALT = "uT3tAJAm*QJzaXG1";

    /**
     * 加密用的盐的基础项，防止对方破解随机数，16位
     */
    private final static String baseSalt = "er32QW!@<}9U*&+m";

    /**
     * 以sha1方式加密
     *
     * @param rawText
     * @return
     */
    public static String encrypt(String rawText) {
        return encrypt(rawText, "sha1");
    }

    /**
     * 以指定方式编码加密
     *
     * @param rawText   ，加密内容
     * @param algorithm ，加密（编码）方式，md5 or sha1
     * @return
     */
    public static String encrypt(String rawText, String algorithm) {
        if (rawText == null) {
            rawText = "";
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.update(rawText.getBytes("utf8"));
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 加密用的盐，固定
     *
     * @return
     */
    public static String finalSalt() {
        return baseSalt;
    }

    /**
     * 生成加密用的盐
     *
     * @return
     */
    public static String salt() {
        /*
         * 随机字符串
		 */
        Random rand = new Random();
        StringBuffer salt = new StringBuffer();
        for (int i = 0; i < 16; ++i) {
            // ascii id:33~126
            int ascii = rand.nextInt(93) + 33;
            salt.append((char) ascii);
        }
        return salt.toString() + baseSalt;
    }

    /**
     * 把二进制digest转成字符串
     */
    private static String getFormattedText(byte[] byteDigest) {
        int i;
        StringBuffer buf = new StringBuffer("");
        for (int offset = 0; offset < byteDigest.length; offset++) {
            i = byteDigest[offset];
            if (i < 0)
                i += 256;
            if (i < 16)
                buf.append("0");
            buf.append(Integer.toHexString(i));
        }
        return buf.toString();
    }

    /**
     * 以AES加密,base64编码
     *
     * @param rawText
     * @return
     * @throws Exception
     */
    public static String encryptAesBase64(String rawText, String key) throws Exception {
        return encodeBase64(aesEncodeECB(rawText, key));
    }

    /**
     * ECB加密,源数据和秘钥为字符串,不要IV
     *
     * @param key  密钥
     * @param data 明文
     * @return Base64编码的密文
     * @throws Exception
     */
    public static byte[] aesEncodeECB(String data, String key) throws Exception {
        return aesEncodeECB(data.getBytes("utf8"), key.getBytes("utf8"));
    }

    /**
     * ECB加密,不要IV
     *
     * @param key  密钥
     * @param data 明文
     * @return Base64编码的密文
     * @throws Exception
     */
    public static byte[] aesEncodeECB(byte[] data, byte[] key)
            throws Exception {
        //将key进行MD5散列
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] buff = md.digest(key);
        SecretKeySpec seckey = new SecretKeySpec(buff, "AES");

        Cipher cipher = Cipher.getInstance("aes" + "/ECB/PKCS5Padding");

        cipher.init(Cipher.ENCRYPT_MODE, seckey);
        byte[] bOut = cipher.doFinal(data);

        return bOut;
    }

    /**
     * 以AES解密base64编码的字符串
     *
     * @param rawText
     * @return
     * @throws Exception
     */
    public static String decryptAesBase64(String rawText, String key) throws Exception {
        return new String(aesDecodeECB(decodeBase64(rawText), key.getBytes("utf8")), "utf8");
    }

    /**
     * ECB解密,源数据和秘钥为字符串,不要IV
     */
    public static byte[] aesDecodeECB(String data, String key) throws Exception {
        return aesDecodeECB(data.getBytes("utf8"), key.getBytes("utf8"));
    }

    /**
     * ECB解密,不要IV
     */
    public static byte[] aesDecodeECB(byte[] data, byte[] key)
            throws Exception {
        //将key进行MD5散列
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] buff = md.digest(key);
        SecretKeySpec seckey = new SecretKeySpec(buff, "AES");

        Cipher cipher = Cipher.getInstance("aes" + "/ECB/PKCS5Padding");

        cipher.init(Cipher.DECRYPT_MODE, seckey);
        byte[] bOut = cipher.doFinal(data);

        return bOut;
    }

    /**
     * base64编码
     */
    public static String encodeBase64(byte[] data) throws Exception {
        return new String(Base64.encodeBase64(data), "utf8");
    }

    /**
     * base64解码
     */
    public static byte[] decodeBase64(String data) {
        return Base64.decodeBase64(data);
    }

    /**
     * 加密密码
     */
    public static String encryptPassword(String password) {
        return encrypt(PASSWORD_SALT + password, "md5");
    }

    /**
     * md5加密字符串
     */
    public static String getMd5(String rawText) {
        return encrypt(rawText, "md5");
    }

    /**
     * 用户名密码前端加密方式
     *
     * @param rawText
     * @return
     */
    public static String encryptFrontString(String rawText) {
        double len = rawText.length();
        return encrypt((int) (len * len) + rawText + (int) Math.ceil(len * len / 10));
    }

}
