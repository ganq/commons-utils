/**
 * Copyright ecVision Limited (c) 2012. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of ecVision Limited.  Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from Uet or an authorized sublicensor.
 */
package com.mysoft.b2b.commons.string;

import java.io.UnsupportedEncodingException;

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

public class EncodingHelper {

    private EncodingHelper() {
        
    }

    /**
     * 字符串 ISO8859-1转GBK编码
     * @param text
     * @return
     */
    public static String isoToGbk(String text) {
        try {
            if (text == null) {
                //text = "";
                return null;
            }
            return new String(text.getBytes("iso-8859-1"), "GBK");
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 字符串 UTF-8转GBK编码
     * @param text
     * @return
     */
    public static String utfToGbk(String text) {
        try {
            if (text == null) {
                //text = "";
                return null;
            }
            return new String(text.getBytes("UTF-8"), "GBK");
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 字符串 GBK转UTF-8编码
     * @param text
     * @return
     */
    public static String gbkToUtf(String text) {
        try {
            if (text == null) {
                //text = "";
                return null;
            }
            return new String(text.getBytes("GBK"), "UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 字符串 ISO8859-1转UTF-8编码
     * @param text
     * @return
     */
    public static String isoToUtf(String text) {
        try {
            if (text == null) {
                //text = "";
                return null;
            }
            return new String(text.getBytes("iso-8859-1"), "UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 字符串 GBK转ISO8859-1编码
     * @param text
     * @return
     */
    public static String gbkToIso(String text) {
        try {
            if (text == null) {
                //text = "";
                return null;
            }
            return new String(text.getBytes("GBK"), "iso-8859-1");
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 字符串 UTF-8转ISO8859-1编码
     * @param text
     * @return
     */
    public static String utfToIso(String text) {
        try {
            if (text == null) {
                //text = "";
                return null;
            }
            return new String(text.getBytes("UTF-8"), "iso-8859-1");
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 字符串 GBK转Unicode编码
     * @param gbkStr
     * @return
     */
    public static String gbkToUnicode(String gbkStr) {
        if (gbkStr == null) {
            //gbkStr = "";
            return null;
        }
        char[] arChar = gbkStr.toCharArray();
        int iValue = 0;
        String uStr = "";
        for (int i = 0; i < arChar.length; i++) {
            iValue = gbkStr.charAt(i);
            if (iValue <= 256)
                uStr = new StringBuilder(uStr).append("\\u00").append(Integer.toHexString(iValue)).toString();
            else
                uStr = new StringBuilder(uStr).append("\\u").append(Integer.toHexString(iValue)).toString();
        }
        return uStr;
    }

    /**
     * 字符串 编码类型转换
     * @param text
     * @param fformat
     * @param tformat
     * @return
     */
    public static String to(String text, String fformat, String tformat) {
        try {
            if (text == null) {
                //text = "";
                return null;
            }
            return new String(text.getBytes(fformat), fformat);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        String strIn = "陈国平";
        System.out.println(EncodingHelper.gbkToUnicode(strIn));
    }
}
