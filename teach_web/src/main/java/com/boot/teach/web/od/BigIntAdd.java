package com.boot.teach.web.od;

import java.math.BigInteger;
import java.util.Scanner;

public class BigIntAdd {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String a = in.nextLine();
        String b = in.nextLine();
        //第一种，直接大数相加，但是数据太大会越界
//        BigInteger b1 = new BigInteger(a);
//        BigInteger b2 = new BigInteger(b);
//        System.out.print(b1.add(b2));
        //第二种，换算字符串
        StringBuilder stb1 = new StringBuilder(a);
        StringBuilder stb2 = new StringBuilder(b);
        StringBuilder str = new StringBuilder();
        //长度对齐操作
        int sub = Math.abs(stb1.length() - stb2.length());
        if (stb1.length()>stb2.length()){
            for (int t=0;t<sub;t++){
                stb2.insert(0,'0');
            }
        }else{
            for (int j=0;j<sub;j++){
                stb1.insert(0,'0');
            }
        }
        int proceed = 0;
        //拆解相加
        for (int i = stb1.length()-1; i >=0; i--) {
            int num1 = stb1.charAt(i)-'0';
            int num2 = stb2.charAt(i)-'0';
            int add = num1 + num2 + proceed;
            if (add > 9){
                add = add%10;
                proceed = 1;
            }else {
                proceed = 0;
            }
            str.insert(0,add);
        }
        //最大位相加进一判断
        if (proceed == 1){
            str.insert(0,"1");
        }
        System.out.println(str.toString());
    }
}
