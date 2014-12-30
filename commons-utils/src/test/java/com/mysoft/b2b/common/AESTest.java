package com.mysoft.b2b.common;

import com.mysoft.b2b.commons.crypto.DESCoder;
import org.junit.Test;

import static org.junit.Assert.*;


import java.net.URLEncoder;
import java.util.Date;

/**
 * Created by Think on 2014/6/4.
 */
public class AESTest {

    @Test
    public void test() throws Exception {
        String inputStr = "539011d8e4b0bf4a8fdb616c|" + new Date().getTime();
        String key = "mysoft.cloud";
        System.err.println("原文:\t" + inputStr);

        System.err.println("密钥:\t" + key);

        String newStr = DESCoder.encrypt(inputStr, key);

        System.err.println("加密后:\t" + newStr);

        String outputStr = DESCoder.decrypt(newStr, key);

        System.err.println("解密后:\t" + outputStr);

        assertEquals(inputStr, outputStr);

        String encode = URLEncoder.encode(outputStr, "utf-8");
        System.out.println(encode);
    }


}
