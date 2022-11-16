package com.monster.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName Xishuju
 * @Deacription TODO
 * @Author wrx
 * @Date 2021/11/22/022 16:12
 * @Version 1.0
 **/
public class Xishuju {

    public static void main(String[] args) {
        String str="江苏省东海县李埝林场驻地9号";


        Map<String,String> map =new HashMap<>();

        map.put("江苏","20");
        map.put("江苏省","20");

        Set set=map.keySet();



//        System.out.println(set);
        bianli(str,map);

    }

    private static void bianli(String str,Map testData){
        Set<Map.Entry<String, String>> entries = testData.entrySet();

        for (Map.Entry<String, String> entry : entries) {
            if(str.contains(entry.getKey())){
                System.out.println(entry.getValue());
            }

        }
    }
}
