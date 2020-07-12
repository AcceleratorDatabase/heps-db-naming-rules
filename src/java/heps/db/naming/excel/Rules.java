/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package heps.db.naming.excel;

import java.util.regex.Pattern;

/**
 *
 * @author BaiYu
 */
public class Rules {
    //各列cell的正则表达式
    //系统代号是大写字母与数字的结合
    public static boolean isSerialNumber(String str) {
	String pattern = "(^[A-Z0-9]+$)";
	boolean isMatch =  Pattern.matches(pattern, str.trim());
	return isMatch;
    }
    //序号是非零正整数 
    public static boolean isNumber(String str) {
	String pattern = "(^\\+?[1-9][0-9]*$)";
	boolean isMatch =  Pattern.matches(pattern, str.trim());
	return isMatch;
    }
    //中文名称中存在部分数字及字母
    public static boolean isChineseWord(String str) {
	String pattern = "(^[A-Za-z0-9-_\\u4e00-\\u9fa5]+$)";
	boolean isMatch =  Pattern.matches(pattern, str.trim());
	return isMatch;
    }

    public static boolean isEnglishWord(String str) {
	String pattern = "(^[A-Za-z0-9-_\\s]+$)";
	boolean isMatch =  Pattern.matches(pattern, str.trim());
	return isMatch;
    }

    //代号只能为大写字母
    public static boolean isAZ(String str) {
	String pattern = "(^[A-Z]+$)";
	boolean isMatch =  Pattern.matches(pattern, str.trim());
	return isMatch;
    }
    //主体分区规则	
    public static boolean isMainPart(String str) {
	String pattern = "(^\\d{2}+[BIWU]+\\d+[A-Z])|(R|BS|LA|LB|BR|RB)|(^[\\u4e00-\\u9fa5]{0,}$)";
	boolean isMatch =  Pattern.matches(pattern, str.trim());
	return isMatch;
    }
    //位置规则
    public static boolean isLocation(String str) {
	String pattern = "([1-4]+[,]*)*|((([0]+[1-9])|([1-3]\\d)|([4]+[0-8]))+[,]*)*|([-])|([1-4]+[-]+[1-4])|(FE|BL|ES)|((([0]+[1-9])|([1-3]\\d)|([4]+[0-8]))+[-]+(([0]+[1-9])|([1-3]\\d)|([4]+[0-8])))|(^[\\u4e00-\\u9fa5]{0,}$)";
	boolean isMatch =  Pattern.matches(pattern, str.trim());
	return isMatch;
    }
    //最大台套数yes/no
    public static boolean isYes(String str) {
	String pattern = "(Yes|No)";
	boolean isMatch =  Pattern.matches(pattern, str.trim());
	return isMatch;
    }
    
}
