/**
 * Copyright ecVision Limited (c) 2012. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of ecVision Limited.  Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from Uet or an authorized sublicensor.
 */
package com.mysoft.b2b.commons.string;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * CGP: Change to the actual description of this class
 * @version   Revision History
 * <pre>
 * Author     Version       Date        Changes
 * CGP        1.0           2012-11-8   Created
 *
 * </pre>
 * @since 
 */

public class StringHelper {

    public static final String EMPTY_STRING = "";

    public static final char QUOTE_CHAR = 39;

    public static final String DOUBLE_QUOTE_STRING = "''";

    public static boolean isCorrect(String p_Parameter) {
        boolean bTmpCheckResult = false;
        if ((p_Parameter != null) && (!(p_Parameter.equals("")))) {
            bTmpCheckResult = true;
        }

        return bTmpCheckResult;
    }

    public static boolean isCorrect(Object p_Parameter) {
        if (p_Parameter instanceof String) {
            return isCorrect((String) p_Parameter);
        }

        return (p_Parameter != null);
    }

    public static void notEmpty(Object object, String message) {
        if (!(isCorrect(object)))
            throw new IllegalArgumentException(message);
    }

    public static void notNull(Object object, String message) {
        if (object == null)
            throw new IllegalArgumentException(message);
    }

    public static void notEmpty(Object[] array, String message) {
        if (isEmpty(array))
            throw new IllegalArgumentException(message);
    }

    public static boolean isEmpty(Object[] array) {
        return ((array == null) || (array.length == 0));
    }

    public static Integer string2Integer(String numberSring) {
        if (isDigit(numberSring)) {
            return Integer.valueOf(numberSring);
        }
        return null;
    }

    public static Long string2Long(String numberSring) {
        if (isDigit(numberSring)) {
            return Long.valueOf(numberSring);
        }
        return null;
    }

    public static boolean String2Boolean(String booleanString) {
        if (!(isCorrect(booleanString)))
            return false;
        if (booleanString.equals("1")) {
            return true;
        }
        return (booleanString.equals("true"));
    }

    public static String boolean2String(boolean b) {
        return ((b) ? "1" : "0");
    }

    public static boolean isDigit(String numberString) {
        if (isCorrect(numberString)) {
            int i = 0;
            while (true) {
                if (!(Character.isDigit(numberString.charAt(i))))
                    throw new IllegalArgumentException("字符串" + numberString + "不是数字");
                ++i;
                if (i >= numberString.length()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 
     * @param userSeq
     * @param length
     * @return
     */
    public static String fillZero(String userSeq, int length) {
        return fillZero(userSeq, length, true);
    }

    public static String fillZero(String userSeq, int length, boolean isBefore) {
        StringBuffer sb = new StringBuffer();
        while (length > 0) {
            sb.append("0");
            --length;
        }
        if (isBefore) {
            return sb.toString() + userSeq;
        }
        return userSeq + sb.toString();
    }

    public static boolean isEqual(Object ob1, Object ob2) {
        boolean result = true;

        if (ob1 == null)
            result = (result) && (ob2 == null);
        else {
            result = (result) && (ob1.equals(ob2));
        }

        return result;
    }

    public static String notEmpty(String p_string) {
        if ("".equals(p_string)) {
            return null;
        }
        return p_string;
    }

    @SuppressWarnings({ "rawtypes" })
    public static String getCommaSeparated(Collection p_collection) {
        StringBuffer sb = new StringBuffer();
        Iterator iter = p_collection.iterator();

        if (iter.hasNext()) {
            sb.append(iter.next().toString());

            while (iter.hasNext()) {
                sb.append(", ");
                sb.append(iter.next().toString());
            }
        }
        return sb.toString();
    }

    public static String replacePattern(String p_stringToParse, String p_oldText, String p_newText) {
        int lenght = p_oldText.length();
        int index;
        while ((index = p_stringToParse.lastIndexOf(p_oldText)) != -1) {
            p_stringToParse = p_stringToParse.substring(0, index) + p_newText + p_stringToParse.substring(index + lenght);
        }
        return p_stringToParse;
    }

    /**
     * 功能描述：分割字符串
     * 
     * @param str
     *            String 原始字符串
     * @param splitsign
     *            String 分隔符（正则表达式）
     * @return String[] 分割后的字符串数组
     */
    public static String[] split(String str, String splitsign) {
        if (str != null && splitsign != null) {
            return str.split(splitsign);
        } else {
            return null;
        }
    }

    /**
     * 功能描述：替换字符串
     * 
     * @param source
     *            String 母字符串
     * @param from
     *            String 原始字符串
     * @param to
     *            String 目标字符串
     * @return String 替换后的字符串
     */
    public static String replace(String source, String from, String to) {
        if (source == null || from == null || to == null)
            return null;
        return source.replaceAll(from, to);
    }

    /**
     * 替换字符串，能能够在HTML页面上直接显示(替换双引号和小于号)
     * 
     * @param str
     *            String 原始字符串
     * @return String 替换后的字符串
     */
    public static String htmlencode(String str) {
        if (str == null) {
            return null;
        }
        return replace("\"", "&quot;", replace("<", "&lt;", str));
    }

    /**
     * 替换字符串，将被编码的转换成原始码（替换成双引号和小于号）
     * 
     * @param str
     *            String
     * @return String
     */
    public static String htmldecode(String str) {
        if (str == null) {
            return null;
        }

        return replace("&quot;", "\"", replace("&lt;", "<", str));
    }

    private static final String _BR = "<br/>";

    /**
     * 功能描述：在页面上直接显示文本内容，替换小于号，空格，回车，TAB
     * 
     * @param str
     *            String 原始字符串
     * @return String 替换后的字符串
     */
    public static String htmlshow(String str) {
        if (str == null) {
            return null;
        }

        str = replace("<", "&lt;", str);
        str = replace(" ", "&nbsp;", str);
        str = replace("\r\n", _BR, str);
        str = replace("\n", _BR, str);
        str = replace("\t", "&nbsp;&nbsp;&nbsp;&nbsp;", str);
        return str;
    }

    /**
     * 功能描述：返回指定字节长度的字符串
     * 
     * @param str
     *            String 字符串
     * @param length
     *            int 指定长度
     * @return String 返回的字符串
     */
    public static String toLength(String str, int length) {
        if (str == null) {
            return null;
        }
        if (length <= 0) {
            return "";
        }
        try {
            if (str.getBytes("GBK").length <= length) {
                return str;
            }
        } catch (Exception e) {
        }
        StringBuffer buff = new StringBuffer();

        int index = 0;
        char c;
        length -= 3;
        while (length > 0) {
            c = str.charAt(index);
            if (c < 128) {
                length--;
            } else {
                length--;
                length--;
            }
            buff.append(c);
            index++;
        }
        buff.append("...");
        return buff.toString();
    }

    /**
     * 功能描述：人民币转成大写
     * 
     * @param str
     *            数字字符串
     * @return String 人民币转换成大写后的字符串
     */
    public static String hangeToBig(String str) {
        double value;
        try {
            value = Double.parseDouble(str.trim());
        } catch (Exception e) {
            return null;
        }
        char[] hunit = { '拾', '佰', '仟' }; // 段内位置表示
        char[] vunit = { '万', '亿' }; // 段名表示
        char[] digit = { '零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖' }; // 数字表示
        long midVal = (long) (value * 100); // 转化成整形
        String valStr = String.valueOf(midVal); // 转化成字符串

        String head = valStr.substring(0, valStr.length() - 2); // 取整数部分
        String rail = valStr.substring(valStr.length() - 2); // 取小数部分

        String prefix = ""; // 整数部分转化的结果
        String suffix = ""; // 小数部分转化的结果
        // 处理小数点后面的数
        if (rail.equals("00")) { // 如果小数部分为0
            suffix = "整";
        } else {
            suffix = digit[rail.charAt(0) - '0'] + "角" + digit[rail.charAt(1) - '0'] + "分"; // 否则把角分转化出来
        }
        // 处理小数点前面的数
        char[] chDig = head.toCharArray(); // 把整数部分转化成字符数组
        char zero = '0'; // 标志'0'表示出现过0
        byte zeroSerNum = 0; // 连续出现0的次数
        for (int i = 0; i < chDig.length; i++) { // 循环处理每个数字
            int idx = (chDig.length - i - 1) % 4; // 取段内位置
            int vidx = (chDig.length - i - 1) / 4; // 取段位置
            if (chDig[i] == '0') { // 如果当前字符是0
                zeroSerNum++; // 连续0次数递增
                if (zero == '0') { // 标志
                    zero = digit[0];
                } else if (idx == 0 && vidx > 0 && zeroSerNum < 4) {
                    prefix += vunit[vidx - 1];
                    zero = '0';
                }
                continue;
            }
            zeroSerNum = 0; // 连续0次数清零
            if (zero != '0') { // 如果标志不为0,则加上,例如万,亿什么的
                prefix += zero;
                zero = '0';
            }
            prefix += digit[chDig[i] - '0']; // 转化该数字表示
            if (idx > 0)
                prefix += hunit[idx - 1];
            if (idx == 0 && vidx > 0) {
                prefix += vunit[vidx - 1]; // 段结束位置应该加上段名如万,亿
            }
        }

        if (prefix.length() > 0)
            prefix += '圆'; // 如果整数部分存在,则有圆的字样
        return prefix + suffix; // 返回正确表示
    }

    /**
     * 功能描述：去掉字符串中重复的子字符串
     * 
     * @param str
     *            原字符串，如果有子字符串则用空格隔开以表示子字符串
     * @return String 返回去掉重复子字符串后的字符串
     */
    public static String removeSameString(String str) {
        Set<String> mLinkedSet = new LinkedHashSet<String>();// set集合的特征：其子集不可以重复
        String[] strArray = str.split(" ");// 根据空格(正则表达式)分割字符串
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < strArray.length; i++) {
            if (!mLinkedSet.contains(strArray[i])) {
                mLinkedSet.add(strArray[i]);
                sb.append(strArray[i] + " ");
            }
        }
        return sb.toString();
    }

    /**
     * 功能描述：过滤特殊字符
     * 
     * @param src
     * @return
     */
    public static String encoding(String src) {
        if (src == null)
            return "";
        StringBuilder result = new StringBuilder();
        if (src != null) {
            src = src.trim();
            for (int pos = 0; pos < src.length(); pos++) {
                switch (src.charAt(pos)) {
                case '\"':
                    result.append("&quot;");
                    break;
                case '<':
                    result.append("&lt;");
                    break;
                case '>':
                    result.append("&gt;");
                    break;
                case '\'':
                    result.append("&apos;");
                    break;
                case '&':
                    result.append("&amp;");
                    break;
                case '%':
                    result.append("&pc;");
                    break;
                case '_':
                    result.append("&ul;");
                    break;
                case '#':
                    result.append("&shap;");
                    break;
                case '?':
                    result.append("&ques;");
                    break;
                default:
                    result.append(src.charAt(pos));
                    break;
                }
            }
        }
        return result.toString();
    }

    /**
     * 功能描述：反过滤特殊字符
     * 
     * @param src
     * @return
     */
    public static String decoding(String src) {
        if (src == null)
            return "";
        String result = src;
        result = result.replace("&quot;", "\"").replace("&apos;", "\'");
        result = result.replace("&lt;", "<").replace("&gt;", ">");
        result = result.replace("&amp;", "&");
        result = result.replace("&pc;", "%").replace("&ul", "_");
        result = result.replace("&shap;", "#").replace("&ques", "?");
        return result;
    }

    /**
     * 从指定的字符串中提取Email content 指定的字符串
     * 
     * @param content
     * @return
     */
    public static String getEmailString(String content) {
        String email = null;
        if (content == null || content.length() < 1) {
            return email;
        }
        int beginPos;
        int i;
        String token = "@";
        String preHalf = "";
        String sufHalf = "";
        beginPos = content.indexOf(token);// 找出含有@
        if (beginPos > -1) {
            // 前项扫描
            String s = null;
            i = beginPos;
            while (i > 0) {
                s = content.substring(i - 1, i);
                if (StringVerifyHelper.testString(s, StringVerifyHelper.letter_regexp))
                    preHalf = s + preHalf;
                else
                    break;
                i--;
            }
            // 后项扫描
            i = beginPos + 1;
            while (i < content.length()) {
                s = content.substring(i, i + 1);
                if (StringVerifyHelper.testString(s, StringVerifyHelper.letter_regexp))
                    sufHalf = sufHalf + s;
                else
                    break;
                i++;
            }
            // 判断合法性
            email = preHalf + "@" + sufHalf;
            if (StringVerifyHelper.testString(email, StringVerifyHelper.email_regexp)) {
                return email;
            }
        }
        return null;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

    }
}
