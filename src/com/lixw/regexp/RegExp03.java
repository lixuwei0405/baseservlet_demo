package com.lixw.regexp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 2022/2/23 23:22
 *
 * @author lixw
 * @version 1.0
 */
public class RegExp03 {
    public static void main(String[] args) {
        String content = "the morning";
        String regStr = "^the"; //匹配a-z之间任意一个字符
        //创建一个正则表达式对象（模式对象）
        Pattern pattern = Pattern.compile(regStr);
        //创建一个匹配器对象
        Matcher matcher = pattern.matcher(content);
        System.out.println(matcher.matches());
        while(matcher.find()){
            System.out.println("找到"+matcher.group(0));
        }
    }
}
