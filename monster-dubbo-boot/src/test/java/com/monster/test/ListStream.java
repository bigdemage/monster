package com.monster.test;

import org.apache.commons.lang3.RandomUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName ListStream
 * @Deacription TODO
 * @Author wrx
 * @Date 2021/11/17/017 13:45
 * @Version 1.0
 **/
public class ListStream {

    public static void main(String[] args) {
        List<Boy> list=new ArrayList<>();
        int i=10;
        while(i>0){
            Boy boy =new Boy("uncle"+RandomUtils.nextInt(10,100),i);
            list.add(boy);
            i--;
        }

        Map<String,Integer> map=list.stream().collect(Collectors.toMap(Boy::getName,Boy::getSex));
        System.out.println(map);
        Map<Integer,String> map2=list.stream().collect(Collectors.toMap(Boy::getSex,Boy::getName));
        System.out.println(map2);

    }

}


class Boy{
    String name;
    Integer sex;

    public Boy(){}

    public Boy(String name, Integer sex) {
        this.name = name;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public void setName(String name) {
        this.name = name;
    }
}
