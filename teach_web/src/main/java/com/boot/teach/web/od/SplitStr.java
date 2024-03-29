package com.boot.teach.web.od;

import java.util.Scanner;

/**
 * 描述
 * 将一个英文语句以单词为单位逆序排放。例如“I am a boy”，逆序排放后为“boy a am I”
 *
 * 所有单词之间用一个空格隔开，语句中除了英文字母外，不再包含其他字符
 *
 * 数据范围：输入的字符串长度满足
 * 1
 * ≤
 * �
 * ≤
 * 1000
 *
 * 1≤n≤1000
 *
 * 注意本题有多组输入
 * 输入描述：
 * 输入一个英文语句，每个单词用空格隔开。保证输入只包含空格和字母。
 *
 * 输出描述：
 * 得到逆序的句子
 *
 * 示例1
 * 输入：
 * I am a boy
 * 复制
 * 输出：
 * boy a am I
 * 复制
 * 示例2
 * 输入：
 * nowcoder
 * 复制
 * 输出：
 * nowcoder
 */
public class SplitStr {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        String[] split = line.split(" ");
        for (int i = split.length-1; i >=0 ; i--) {
            System.out.print(split[i]+" ");
        }
    }
}
