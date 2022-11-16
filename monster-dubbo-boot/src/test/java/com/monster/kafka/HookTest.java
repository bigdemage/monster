package com.monster.kafka;

/**
 * @ClassName HookTest
 * @Deacription TODO
 * @Author wrx
 * @Date 2022/1/17/017 14:12
 * @Version 1.0
 **/
public class HookTest {


        public static void main(String[] args) {
            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("钩子函数执行");
                }
            }));
            //当主动关闭应用
            while (true);
        }
}
