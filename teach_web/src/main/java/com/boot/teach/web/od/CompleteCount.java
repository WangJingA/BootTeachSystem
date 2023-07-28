package com.boot.teach.web.od;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * 描述
 * 完全数（Perfect number），又称完美数或完备数，是一些特殊的自然数。
 *
 * 它所有的真因子（即除了自身以外的约数）的和（即因子函数），恰好等于它本身。
 *
 * 例如：28，它有约数1、2、4、7、14、28，除去它本身28外，其余5个数相加，1+2+4+7+14=28。
 *
 * 输入n，请输出n以内(含n)完全数的个数。
 *
 * 数据范围：
 * 1
 * ≤
 * �
 * ≤
 * 5
 * ×
 * 1
 * 0
 * 5
 *
 * 1≤n≤5×10
 * 5
 *
 * 输入描述：
 * 输入一个数字n
 *
 * 输出描述：
 * 输出不超过n的完全数的个数
 *
 * 示例1
 * 输入：
 * 1000
 * 复制
 * 输出：
 * 3
 */
public class CompleteCount {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int count = 0;
        // 注意 hasNext 和 hasNextLine 的区别
            int a = in.nextInt();
            for(int i=1;i<=a;i++){
                List<Integer> list = new ArrayList<>();
                for(int j=1;j<i;j++){
                    if(i%j==0){
                        list.add(j);
                    }
                }
                Iterator<Integer> iterator = list.iterator();
                int sum = 0;
                while (iterator.hasNext()){
                    sum += iterator.next();
                }
                if (sum == i){
                    count ++;
                }
            }
            System.out.println(count);
    }
}
