package com.monster.rocketmq.simpleMsg;


import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

import static com.monster.rocketmq.Config.NAME_SERVER_URL;

/**
 * 异步发送，不需要等待结果返回
 * 但需要监听一个结果通知
 */
public class AsyncProducer {

    private static CountDownLatch countDownLatch=new CountDownLatch(5);


    public static void main(String[] args) throws RemotingException, InterruptedException, MQClientException {
        //初始化默认producer
        DefaultMQProducer producer=new DefaultMQProducer("simple_msg");
        producer.setNamesrvAddr(NAME_SERVER_URL);
        //设个超时时间，太短了容易sendDefaultImpl call timeout
        producer.setSendMsgTimeout(5000);
        producer.start();

        //设置发送失败的重试次数
        producer.setRetryTimesWhenSendAsyncFailed(0);

        for(int i=1 ;i<=5;i++){
            Message msg=new Message("simple_async",("asyncMsg"+i).getBytes(StandardCharsets.UTF_8));

            //异步发送消息，发送结果通过callback返回给客户端
            producer.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    countDownLatch.countDown();
                    System.out.println("消息发送成功--"+sendResult.toString());
                }

                @Override
                public void onException(Throwable e) {
                    countDownLatch.countDown();
                    System.out.println("消息发送失败");
                    e.printStackTrace();
                }
            });
        }


        //关闭这里有个问题，如果是异步的话关的太早，容易把后面的连不上所以用个CountDownLatch
        countDownLatch.await();
        producer.shutdown();
    }
}
