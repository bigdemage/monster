package com.monster.kafka;

/**
 * @ClassName CpuInfo
 * @Deacription TODO
 * @Author wrx
 * @Date 2021/12/17/017 15:57
 * @Version 1.0
 **/
public class CpuInfo {

    public static void main(String[] args) {
        //获取机器cpu个数
        System.out.println(Runtime.getRuntime().availableProcessors());
    }
}
