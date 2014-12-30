package com.mysoft.b2b.commons.crypto;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;


/**
 * DES安全编码组件
 *
 * <pre>
 * 支持 DES、DESede(TripleDES,就是3DES)、AES、Blowfish、RC2、RC4(ARCFOUR)
 * DES          		key size must be equal to 56
 * DESede(TripleDES) 	key size must be equal to 112 or 168
 * AES          		key size must be equal to 128, 192 or 256,but 192 and 256 bits may not be available
 * Blowfish     		key size must be multiple of 8, and can only range from 32 to 448 (inclusive)
 * RC2          		key size must be between 40 and 1024 bits
 * RC4(ARCFOUR) 		key size must be between 40 and 1024 bits
 * 具体内容 需要关注 JDK Document http://.../docs/technotes/guides/security/SunProviders.html
 * </pre>
 *
 * @author 梁栋
 * @version 1.0
 * @since 1.0
 */
public abstract class DESCoder extends Coder {
    /**
     * ALGORITHM 算法 <br>
     * 可替换为以下任意一种算法，同时key值的size相应改变。
     *
     * <pre>
     * DES          		key size must be equal to 56
     * DESede(TripleDES) 	key size must be equal to 112 or 168
     * AES          		key size must be equal to 128, 192 or 256,but 192 and 256 bits may not be available
     * Blowfish     		key size must be multiple of 8, and can only range from 32 to 448 (inclusive)
     * RC2          		key size must be between 40 and 1024 bits
     * RC4(ARCFOUR) 		key size must be between 40 and 1024 bits
     * </pre>
     *
     * 在Key toKey(byte[] key)方法中使用下述代码
     * <code>SecretKey secretKey = new SecretKeySpec(key, ALGORITHM);</code> 替换
     * <code>
     * DESKeySpec dks = new DESKeySpec(key);
     * SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
     * SecretKey secretKey = keyFactory.generateSecret(dks);
     * </code>
     */
    public static final String ALGORITHM = "AES";

    // 加密
    public static String encrypt(String sSrc, String sKey) throws Exception {
        if (sKey == null) {
            System.out.print("Key为空null");
            return null;
        }

        byte[] raw = sKey.getBytes("utf-8");
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] thedigest = md.digest(raw);
        SecretKeySpec skc = new SecretKeySpec(thedigest, "AES");

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
        cipher.init(Cipher.ENCRYPT_MODE, skc);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));

        return new String(Base64.encodeBase64(encrypted),"utf-8");//此处使用BASE64做转码功能，同时能起到2次加密的作用。
    }

    // 解密
    public static String decrypt(String sSrc, String sKey) throws Exception {
        try {
            // 判断Key是否正确
            if (sKey == null) {
                System.out.print("Key为空null");
                return null;
            }

            byte[] raw = sKey.getBytes("utf-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] thedigest = md.digest(raw);
            SecretKeySpec skc = new SecretKeySpec(thedigest, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skc);
            byte[] encrypted1 = Base64.decodeBase64(sSrc.getBytes("utf-8"));//先用base64解密
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original,"utf-8");
                return originalString;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

}
