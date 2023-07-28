package com.boot.teach.web.od;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * 描述
 * 输入一个 int 型整数，按照从右向左的阅读顺序，返回一个不含重复数字的新的整数。
 * 保证输入的整数最后一位不是 0 。
 *
 * 数据范围：
 * 1
 * ≤
 * �
 * ≤
 * 1
 * 0
 * 8
 *
 * 1≤n≤10
 * 8
 *
 * 输入描述：
 * 输入一个int型整数
 *
 * 输出描述：
 * 按照从右向左的阅读顺序，返回一个不含重复数字的新的整数
 *
 * 示例1
 * 输入：
 * 9876673
 * 复制
 * 输出：
 * 37689
 */
public class DistinctNum {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String nextInt = scanner.nextLine();
        StringBuffer sf = new StringBuffer();
        for (int i = nextInt.length()-1; i >=0 ; i--) {
            char dex = nextInt.charAt(i);
            if (sf.toString().indexOf(dex) <0){
                sf.append(dex);
            }
        }
        System.out.println(sf.toString());
    }
}
