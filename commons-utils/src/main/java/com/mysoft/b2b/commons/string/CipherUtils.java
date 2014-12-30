/**
 * Copyright ecVision Limited (c) 2012. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of ecVision Limited.  Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from Uet or an authorized sublicensor.
 */
package com.mysoft.b2b.commons.string;

import java.security.MessageDigest;

import org.apache.log4j.Logger;

import com.mysoft.b2b.commons.exception.PlatformCheckedException;

/**
 * CGP: Change to the actual description of this class
 * @version   Revision History
 * <pre>
 * Author     Version       Date        Changes
 * CGP        1.0           2012-11-8     Created
 *
 * </pre>
 * @since 
 */

public class CipherUtils {
    
    //十六进制下数字到字符的映射数组  
    private final static String[] hexDigits = { "0", "a", "1", "b", "2", "c", "3", "d","4", "e", "5", "f", "6", "7", "8", "9" };
    private static Logger logger = Logger.getLogger(CipherUtils.class);
    private static MessageDigest md;
    public static final String DEFAULT_PWD="123123";
    public static final int MIN_LENGTH = 6;
    public static final int MAX_LENGTH = 18;
    public static final int MD5_LENGTH = 32;
    
    public static void init() throws PlatformCheckedException{
        try{
            //创建具有指定算法名称的信息摘要
            md =  MessageDigest.getInstance("MD5");
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            throw new PlatformCheckedException(e.getMessage(), null,e);
        }
    }
    
    /** 
     * 把inputString加密  
     * @throws CommonException 
     */
    public static String generatePassword(String inputString) throws PlatformCheckedException {
        return encodeByMD5(inputString);
    }

    /** 
      * 验证输入的密码是否正确 
      * @param password    加密后的密码 
      * @param inputString    输入的字符串 
      * @return 验证结果，TRUE:正确 FALSE:错误 
     * @throws CommonException 
      */
    public static boolean validatePassword(String password, String inputString) throws PlatformCheckedException {
        if (password.equals(encodeByMD5(inputString))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 对字符串进行MD5加密
     * @throws CommonException 
     */
    private static String encodeByMD5(String originString) throws PlatformCheckedException {
        if (originString != null) {
            //使用指定的字节数组对摘要进行最后更新，然后完成摘要计算 
            if(md == null){
                init();
            }
            byte[] results = md.digest(originString.getBytes());
            //将得到的字节数组变成字符串返回
            String resultString = byteArrayToHexString(results);
            return resultString.toUpperCase();
        }
        return null;
    }

    /**  
     * 转换字节数组为十六进制字符串 
     * @param     字节数组 
     * @return    十六进制字符串 
     */
    private static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    /** 
     * 将一个字节转化成十六进制形式的字符串
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n = 256 + n;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    public static void main(String[] args) {
        try{
            String pwd1 = "123123";
            String pwd2 = "";
            System.out.println("未加密的密码:" + pwd1);
            //将123加密  
            pwd2 = CipherUtils.generatePassword(pwd1);
            System.out.println("加密后的密码:" + pwd2);

            System.out.print("验证密码是否下确:");
            if (CipherUtils.validatePassword(pwd2, pwd1)) {
                System.out.println("正确");
            } else {
                System.out.println("错误");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
}
