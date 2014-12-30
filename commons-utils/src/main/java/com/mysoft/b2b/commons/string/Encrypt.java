package com.mysoft.b2b.commons.string;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * chengp: Title: PBEWithMD5AndDES
 * @version   Revision History
 * <pre>
 * Author     Version       Date        Changes
 * chengp    1.0           2014年8月30日     Created
 *
 * </pre>
 * @since 8.
 */
public class Encrypt {

    private static byte[] kbytes = "qwertyuiopl".getBytes();

    private static String KEY = "Blowfish";

    public static String encrypt(String secret) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        SecretKeySpec key = new SecretKeySpec(kbytes, KEY);

        Cipher cipher = Cipher.getInstance(KEY);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encoding = cipher.doFinal(secret.getBytes());
        BigInteger n = new BigInteger(encoding);
        return n.toString(16);
    }

    public static String decrypt(String secret) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        SecretKeySpec key = new SecretKeySpec(kbytes, KEY);

        BigInteger n = new BigInteger(secret, 16);
        byte[] encoding = n.toByteArray();

        Cipher cipher = Cipher.getInstance(KEY);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decode = cipher.doFinal(encoding);
        return new String(decode);
    }

    public static void main(String[] args) {
        String passwd = "123456abcdef";
        try {
            String encryptpwd = encrypt(passwd);
            System.out.println(encryptpwd);

            String unencyppwd = decrypt(encryptpwd);
            System.out.println(unencyppwd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
