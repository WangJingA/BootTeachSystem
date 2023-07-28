package com.boot.teach.web.od;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String str = bufferedReader.readLine();
        int num = Integer.parseInt(str);
        int [] store = new int[11111111];
        for (int i=0;i<num;i++){
            String line = bufferedReader.readLine();
            int key = Integer.parseInt(line.substring(0,line.indexOf(" ")));
            int value = Integer.parseInt(line.substring(line.indexOf(" ")+1));
            store[key] += value;
        }
        for (int j = 0; j < store.length; j++) {
            if (store[j] != 0){
                System.out.println(j +" "+store[j]);
            }
        }
    }
}
