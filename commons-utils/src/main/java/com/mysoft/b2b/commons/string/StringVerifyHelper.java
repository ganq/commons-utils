/**
 * Copyright ecVision Limited (c) 2012. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of ecVision Limited.  Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from Uet or an authorized sublicensor.
 */
package com.mysoft.b2b.commons.string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * CGP: Change to the actual description of this class
 * @version   Revision History
 * <pre>
 * Author     Version       Date        Changes
 * CGP        1.0           2012-11-8     Created
 *
 * </pre>
 * @since b2b
 */

public abstract class StringVerifyHelper {

    /**
     * 匹配纯汉字
     * 格式  :
     * 匹配  :
     * 不匹配:
     */
    public static final String chinese_regexp = "^[\u0391-\uFFE5]+$";

    /**
     * 匹配图象
     * 格式: /相对路径/文件名.后缀 (后缀为gif,dmp,png)
     * 匹配 : /forum/head_icon/admini2005111_ff.gif 或 admini2005111.dmp
     * 不匹配: c:/admins4512.gif
     */
    public static final String icon_regexp = "^(/{0,1}\\w){1,}\\.(gif|dmp|png|jpg)$|^\\w{1,}\\.(gif|dmp|png|jpg)$";

    /**
     * 匹配email地址(^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$)
     * 格式: XXX@XXX.XXX.XX
     * 匹配 : foo@bar.com 或 foobar@foobar.com.au
     * 不匹配: foo@bar 或 $$$@bar.com
     */
    public static final String email_regexp = "(?:\\w[-._\\w]*\\w@\\w[-._\\w]*\\w\\.\\w{2,3}$)";

    /**
     * 匹配日期
     * 格式(首位不为0): XXXX-XX-XX 或 XXXX XX XX 或 XXXX-X-X
     * 范围:1900--2099
     * 匹配 : 2005-04-04
     * 不匹配: 01-01-01
     */
    public static final String date_regexp = "^((((19){1}|(20){1})\\d{2})|\\d{2})[-\\s]{1}((0{1}\\d{1})||(1{1}[0-2]{1}))[-\\s]{1}[0-3]{1}\\d{1}$";

    /**
     * 匹配电话
     * 格式为: 0XXX-XXXXXX(10-13位首位必须为0) 或0XXX XXXXXXX(10-13位首位必须为0) 或
     * (0XXX)XXXXXXXX(11-14位首位必须为0) 或 XXXXXXXX(6-8位首位不为0) 或 XXXXXXXXXXX(11位首位不为0)
     * 匹配  : 0371-123456 或 (0371)1234567 或 (0371)12345678 或 010-123456 或 010-12345678 或 12345678912
     * 不匹配: 1111-134355 或 0123456789
     */
    public static final String phone_regexp = "^(?:0[0-9]{2,3}[-\\s]{1}|\\(0[0-9]{2,4}\\))[0-9]{6,8}$|^[1-9]{1}[0-9]{5,7}$|^[1-9]{1}[0-9]{10}$";

    /**
     * 匹配手机
     * 格式  :
     * 匹配  :
     * 不匹配:
     */
    public static final String handset_regexp = "^1[\\d]{10}$";

    /**
     * 匹配身份证
     * 格式为: XXXXXXXXXX(10位) 或 XXXXXXXXXXXXX(13位) 或 XXXXXXXXXXXXXXX(15位) 或 XXXXXXXXXXXXXXXXXX(18位)
     * 匹配 : 0123456789123
     * 不匹配: 0123456
     */
    public static final String ID_card_regexp = "^\\d{10}|\\d{13}|\\d{15}|\\d{18}$";

    /**
     * 匹配邮编代码
     * 格式为: XXXXXX(6位)
     * 匹配 : 012345
     * 不匹配: 0123456
     */
    public static final String ZIP_regexp = "^[0-9]{6}$";

    /**
     * 不包括特殊字符的匹配 (字符串中不包括符号 数学次方号^ 单引号' 双引号" 分号; 逗号, 帽号: 数学减号- 右尖括号> 左尖括号< 反斜杠\
     * 即空格,制表符,回车符等 )
     * 格式为: x 或 一个一上的字符
     * 匹配 : 
     * 不匹配: 
     */
    public static final String non_special_char_regexp = "^[^'\"\\;,:-<>\\s].+$";

    /**
     * 判断是不是合法字符 c 要判断的字符
     * 格式  : 
     * 匹配  : 
     * 不匹配: 
     */
    public static final String fit_letter_regexp = "[\\w\\.-_]*";

    /**
     * 匹配非负整数（正整数 + 0)
     */
    public static final String non_negative_integers_regexp = "^\\d+$";

    /**
     * 匹配不包括零的非负整数（正整数 > 0)
     */
    public static final String non_zero_negative_integers_regexp = "^[1-9]+\\d*$";

    /**
     * 匹配正整数
     */
    public static final String positive_integer_regexp = "^[0-9]*[1-9][0-9]*$";

    /**
     * 匹配非正整数（负整数 + 0）
     */
    public static final String non_positive_integers_regexp = "^((-\\d+)|(0+))$";

    /**
     * 匹配负整数
     */
    public static final String negative_integers_regexp = "^-[0-9]*[1-9][0-9]*$";

    /**
     * 匹配整数（^[-\\+]?[\\d]+$）
     */
    public static final String integer_regexp = "^-?\\d+$";

    /**
     * 匹配非负浮点数（正浮点数 + 0）
     */
    public static final String non_negative_rational_numbers_regexp = "^\\d+(\\.\\d+)?$";

    /**
     * 匹配正浮点数
     */
    public static final String positive_rational_numbers_regexp = "^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$";

    /**
     * 匹配非正浮点数（负浮点数 + 0）
     */
    public static final String non_positive_rational_numbers_regexp = "^((-\\d+(\\.\\d+)?)|(0+(\\.0+)?))$";

    /**
     * 匹配负浮点数
     */
    public static final String negative_rational_numbers_regexp = "^(-(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*)))$";

    /**
     * 匹配浮点数（^[-\\+]?\\d+\\.\\d+$）
     */
    public static final String rational_numbers_regexp = "^(-?\\d+)(\\.\\d+)?$";

    /**
     * 匹配由26个英文字母组成的字符串
     */
    public static final String letter_regexp = "^[A-Za-z]+$";

    /**
     * 匹配由26个英文字母的大写组成的字符串
     */
    public static final String upward_letter_regexp = "^[A-Z]+$";

    /**
     * 匹配由26个英文字母的小写组成的字符串
     */
    public static final String lower_letter_regexp = "^[a-z]+$";

    /**
     * 匹配由数字和26个英文字母组成的字符串
     */
    public static final String letter_number_regexp = "^[A-Za-z0-9]+$";

    /**
     * 匹配由数字、26个英文字母或者下划线组成的字符串
     */
    public static final String letter_number_underline_regexp = "^\\w+$";

    /**
     * 匹配匹配并提取url
     * 格式: XXXX://XXX.XXX.XXX.XX/XXX.XXX?XXX=XXX
     * 匹配 : http://www.suncer.com 或news://www
     * 提取(MatchResult matchResult=matcher.getMatch()): 
     * matchResult.group(0)=http://www.suncer.com:8080/index.html?login=true 
     * matchResult.group(1)=http 
     * matchResult.group(2)=www.suncer.com 
     * matchResult.group(3)=:8080
     * matchResult.group(4)=/index.html?login=true
     * 不匹配: c:\window
     */
    public static final String url_regexp = "(\\w+)://([^/:]+)(:\\d*)?([^#\\s]*)";

    /**
     * 匹配并提取http
     * 格式: http://XXX.XXX.XXX.XX/XXX.XXX?XXX=XXX 或 ftp://XXX.XXX.XXX 或 https://XXX
     * 匹配 : http://www.suncer.com:8080/index.html?login=true
     * 提取(MatchResult matchResult=matcher.getMatch()): 
     * matchResult.group(0)=http://www.suncer.com:8080/index.html?login=true 
     * matchResult.group(1)=http 
     * matchResult.group(2)=www.suncer.com 
     * matchResult.group(3)=:8080
     * matchResult.group(4)=/index.html?login=true
     * 不匹配: news://www
     */
    public static final String http_regexp = "(http|https|ftp)://([^/:]+)(:\\d*)?([^#\\s]*)";

    /**
     * 功能描述：是否为空白,包括null和""
     * @param str
     * @return
     */
    public static boolean isBlank(String strIn) {
        return strIn == null || strIn.trim().length() == 0;
    }

    /**
     * 功能描述：判断是否为质数
     * @param x
     * @return
     */
    public static boolean isPrime(int x) {
        if (x <= 7) {
            if (x == 2 || x == 3 || x == 5 || x == 7)
                return true;
        }
        int c = 7;
        if (x % 2 == 0)
            return false;
        if (x % 3 == 0)
            return false;
        if (x % 5 == 0)
            return false;
        int end = (int) Math.sqrt(x);
        while (c <= end) {
            if (x % c == 0) {
                return false;
            }
            c += 4;
            if (x % c == 0) {
                return false;
            }
            c += 2;
            if (x % c == 0) {
                return false;
            }
            c += 4;
            if (x % c == 0) {
                return false;
            }
            c += 2;
            if (x % c == 0) {
                return false;
            }
            c += 4;
            if (x % c == 0) {
                return false;
            }
            c += 6;
            if (x % c == 0) {
                return false;
            }
            c += 2;
            if (x % c == 0) {
                return false;
            }
            c += 6;
        }
        return true;
    }

    /**
    * 验证字符串是否匹配相应格式
    * 
    * @param  String strIn 需要验证的字符串
    * @param  String patter验证格式
    * @return 是否合法
    * @throws Exception 
    */
    public static boolean testString(String strIn, String patter) {
        try {
            if (isBlank(strIn)) {
                return false;
            }
            Pattern pattern = Pattern.compile(patter);
            Matcher mat = pattern.matcher(strIn);
            if (!mat.find()) {
                return false;
            }
            return true;
        } catch (Exception ee) {
            return false;
        }
    }

    /**
     * 验证字符串是否匹配相应格式
     * 
     * @param  String strIn 需要验证的字符串
     * @param  String patter验证格式
     * @return 是否合法
     * @throws Exception 
     */
    public static boolean regexString(String strIn, String patter) {
        try {
            if (isBlank(strIn)) {
                return false;
            }
            Pattern pattern = Pattern.compile(patter);
            Matcher matcher = pattern.matcher(strIn);
            return matcher.matches();
        } catch (Exception ee) {
            return false;
        }
    }

}
