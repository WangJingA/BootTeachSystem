package com.boot.teach.web.od;

import java.util.Scanner;

public class BucketSoller {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a = 1;
        while(a!= 0){
            a = Integer.parseInt(scanner.nextLine());
            System.out.println(new BucketSoller().count(a));
        }
    }
    int count(int num){
        int sum = 0;
        if (num+1 < 3){
            return sum;
        }else {
            sum = num/3+count(num%3+num/3);
            num = num %3;
        }
        return  sum;
    }
}
