package com.boot.teach.web.od;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 描述
 * 给定 n 个字符串，请对 n 个字符串按照字典序排列。
 *
 * 数据范围：
 * 1
 * ≤
 * �
 * ≤
 * 1000
 *
 * 1≤n≤1000  ，字符串长度满足
 * 1
 * ≤
 * �
 * �
 * �
 * ≤
 * 100
 *
 * 1≤len≤100
 * 输入描述：
 * 输入第一行为一个正整数n(1≤n≤1000),下面n行为n个字符串(字符串长度≤100),字符串中只含有大小写字母。
 * 输出描述：
 * 数据输出n行，输出结果为按照字典序排列的字符串。
 * 示例1
 * 输入：
 * 9
 * cap
 * to
 * cat
 * card
 * two
 * too
 * up
 * boat
 * boot
 * 复制
 * 输出：
 * boat
 * boot
 * cap
 * card
 * cat
 * to
 * too
 * two
 * up
 */
public class DictionaryWord {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int anInt = Integer.parseInt(scanner.nextLine());
        String [] strA = new String[anInt];
        for (int i = 0; i < anInt; i++) {
            strA[i] = scanner.nextLine();
        }
        Arrays.sort(strA);
        for (int j =0;j<anInt;j++){
            System.out.println(strA[j]);
        }
    }
}
