package com.boot.teach.web.od;

import java.util.Scanner;

public class StringTONum {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        String lowerCase = "abcdefghijklmnopqrstuvwxyz";
        String uplowerCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuffer stringBuffer = new StringBuffer();
        for(int i=0;i<str.length();i++){
            char dex = str.charAt(i);
            int index = uplowerCase.indexOf(dex);
            if(index >= 0) {
                dex = lowerCase.charAt((index + 1) % 26);
            }else {
                if ("abc".indexOf(dex)>=0){
                    dex = '2';
                }
                if ("def".indexOf(dex)>=0){
                    dex = '3';
                }
                if ("ghi".indexOf(dex)>=0){
                    dex = '4';
                }
                if ("jkl".indexOf(dex)>=0){
                    dex = '5';
                }
                if ("mno".indexOf(dex)>=0){
                    dex = '6';
                }
                if ("pqrs".indexOf(dex)>=0){
                    dex = '7';
                }
                if ("tuv".indexOf(dex)>=0){
                    dex = '8';
                }
                if ("wxyz".indexOf(dex)>=0){
                    dex = '9';
                }
            }

            stringBuffer.append(dex);
        }
        System.out.println(stringBuffer.toString());
    }
}
