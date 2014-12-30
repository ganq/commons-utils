package com.mysoft.b2b.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;

import com.mysoft.b2b.commons.crypto.RSACoder;

/**
 *
 * @author 梁栋
 * @version 1.0
 * @since 1.0
 */
public class RSACoderTest {
    private static int BUFFER_SIZE = 1024;

    private String publicKey;

    private String privateKey;

    @Before
    public void setUp() throws Exception {
        InputStream inputStream = this.getClass().getResourceAsStream("/rsa_private_key.pem");
        privateKey = inputStreamTOString(inputStream);
        inputStream.close();

        InputStream inputStream2 = this.getClass().getResourceAsStream("/rsa_public_key.pem");
        ;
        publicKey = inputStreamTOString(inputStream2);
        inputStream2.close();

        //publicKey = RSACoder.getPublicKey(keyMap);
        //privateKey = RSACoder.getPrivateKey(keyMap);
        System.err.println("公钥: \n\r" + publicKey);
        System.err.println("私钥： \n\r" + privateKey);
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

    @Test
    public void test() throws Exception {
        System.err.println("公钥加密——私钥解密");
        String inputStr = "abc";
        byte[] data = inputStr.getBytes();

        byte[] encodedData = RSACoder.encryptByPublicKey(data, publicKey);

        String s = RSACoder.encryptBASE64(encodedData);

        System.out.println(s);

        byte[] decodedData = RSACoder.decryptByPrivateKey(encodedData, privateKey);

        String outputStr = new String(decodedData);
        System.err.println("加密前: " + inputStr + "\n\r" + "解密后: " + outputStr);
        assertEquals(inputStr, outputStr);

    }

    @Test
    public void testSign() throws Exception {
        System.err.println("私钥加密——公钥解密");
        String inputStr = "sign";
        byte[] data = inputStr.getBytes();

        byte[] encodedData = RSACoder.encryptByPrivateKey(data, privateKey);

        byte[] decodedData = RSACoder.decryptByPublicKey(encodedData, publicKey);

        String outputStr = new String(decodedData);
        System.err.println("加密前: " + inputStr + "\n\r" + "解密后: " + outputStr);
        assertEquals(inputStr, outputStr);

        System.err.println("私钥签名——公钥验证签名");
        // 产生签名
        String sign = RSACoder.sign(encodedData, privateKey);
        System.err.println("签名:\r" + sign);

        // 验证签名
        boolean status = RSACoder.verify(encodedData, publicKey, sign);
        System.err.println("状态:\r" + status);
        assertTrue(status);

    }

}
