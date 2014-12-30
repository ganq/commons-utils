package com.mysoft.b2b.common;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import javax.crypto.Cipher;

import java.io.*;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by Think on 2014/6/4.
 */
public class RsaTest {
    private static int BUFFER_SIZE = 1024;
    public static final String KEY_ALGORITHM = "RSA";

    public static void main(String[] args) throws Exception {


        InputStream inputStream = RsaTest.class.getResourceAsStream("/rsa_private_key.pem");
        String priKey = inputStreamTOString(inputStream);
        inputStream.close();

        InputStream inputStream2 = RsaTest.class.getResourceAsStream("/rsa_public_key.pem");
        String pubKey = inputStreamTOString(inputStream2);
        inputStream2.close();

        byte[] bytes = encryptByPublicKey("alex123456".getBytes("UTF-8"), pubKey);

        byte[] bytes1 = Base64.encodeBase64(bytes);

        String encodedStr = new String(bytes1, "UTF-8");

        System.out.println(encodedStr);

        encodedStr = "dN3BJj6PVv+8dvfM0PllY3DaAtgxVjDTxMDjGpD7YqV4yGM0VjCL4EtRdXLOTnm+YDb7F4CwKvLUpkDioXkylrDWoxJQEefTZk0BfAKOzJW/OFtIG5z0nK5lUM8Xc2v78RqQsyFBEPCOtIsO10CF/cvPk2dsIO15YR4yGhH4RBE=";

        byte[] base64Data = decryptBASE64(encodedStr);

        byte[] bytes2 = decryptByPrivateKey(base64Data, priKey);

        System.out.println(new String(bytes2, "UTF-8"));
        
        
    }


    /**
     * .     * 加密<br>
     * 用公钥加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String key)
            throws Exception {
        // 对公钥解密
        byte[] keyBytes = Base64.decodeBase64(key.getBytes());

        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);

        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }

    public static byte[] encryptByPrivateKey(byte[] data, String key)
            throws Exception {
        // 对密钥解密
        byte[] keyBytes = Base64.decodeBase64(key.getBytes("UTF-8"));
        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    public static byte[] decryptByPublicKey(byte[] data, String key)
            throws Exception {
        // 对密钥解密
        byte[] keyBytes = decryptBASE64(key);
        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);
        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }

    public static byte[] decryptByPrivateKey(byte[] data, String key)
            throws Exception {
        // 对密钥解密
        byte[] keyBytes = decryptBASE64(key);

        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        return cipher.doFinal(data);
    }

    @SuppressWarnings("unused")
    private static byte[] encryptBASE64(String key) throws UnsupportedEncodingException {
        return Base64.encodeBase64(key.getBytes("UTF-8"));
    }

    private static byte[] decryptBASE64(String key) throws UnsupportedEncodingException {
        return Base64.decodeBase64(key.getBytes("UTF-8"));
    }

    /**
     * rsa签名
     *
     * @param content
     *            待签名的字符串
     * @param privateKey
     *            rsa私钥字符串
     * @param charset
     *            字符编码
     * @return 签名结果
     * @throws Exception
     *             签名失败则抛出异常
     */
    public  static  String rsaSign(String content, String privateKey, String charset) throws SignatureException {
        try {
            PrivateKey priKey = getPrivateKeyFromPKCS8("RSA", new ByteArrayInputStream(privateKey.getBytes()));

            Signature signature = Signature.getInstance("SHA1WithRSA");
            signature.initSign(priKey);
            if (StringUtils.isEmpty(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }

            byte[] signed = signature.sign();
            return new String(Base64.encodeBase64(signed));
        } catch (Exception e) {
            throw new SignatureException("RSAcontent = " + content + "; charset = " + charset, e);
        }
    }

    public  static  PrivateKey getPrivateKeyFromPKCS8(String algorithm, ByteArrayInputStream ins) throws Exception {
        if (ins == null || StringUtils.isEmpty(algorithm)) {
            return null;
        }

        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        byte[] encodedKey = inputStreamTOString(ins).getBytes();
        encodedKey = Base64.decodeBase64(encodedKey);
        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
    }

    private static String inputStreamTOString(InputStream in) throws Exception {

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[BUFFER_SIZE];
        int count = -1;
        while ((count = in.read(data, 0, BUFFER_SIZE)) != -1) {
            outStream.write(data, 0, count);
        }

        return new String(outStream.toByteArray(), "ISO-8859-1");
    }

}
