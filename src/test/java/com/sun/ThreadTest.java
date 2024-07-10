package com.sun;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

public class ThreadTest {


    public static void main(String[] args) {


        Map<String,String> ma = new HashMap<>();
        ma.compute("a",(k,v)->{
            if (k.equals("f")){
                return "456";
            }
           return v;
        });
        System.out.println(ma);

    }

}
