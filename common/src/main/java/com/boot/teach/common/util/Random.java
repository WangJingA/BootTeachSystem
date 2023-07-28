package com.boot.teach.common.util;

/**
 * 随机密码
 */
public class Random {
    public static  String randomPassword(int len){
        String password = null;
        //数字，小写字母，大写字母，特殊符号
        String baseStr = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ~!@#$%^&*()_+{}|<>?";
        boolean flag = false;
        // 2、使用循环来判断是否是正确的密码
        while (!flag) {
            // 密码重置
            password = "";
            // 个数计数
            int a = 0,b = 0,c = 0,d = 0;
            for (int i = 0; i < len; i++) {
                int rand = (int) (Math.random() * baseStr.length());
                password+=baseStr.charAt(rand);
                if (0<=rand && rand<=9) {
                    a++;
                }
                if (10<=rand && rand<=35) {
                    b++;
                }
                if (36<=rand && rand<=61) {
                    c++;
                }
                if (62<=rand && rand<baseStr.length()) {
                    d++;
                }
            }
            // 3、判断是否是正确的密码（4类中仅一类为0，其他不为0）
            flag = (a*b*c!=0&&d==0)||(a*b*d!=0&&c==0)||(a*c*d!=0&&b==0)||(b*c*d!=0&&a==0);
        }
        return password;
    }
}
