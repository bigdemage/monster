package com.monster.rocketmq.orderMsg;


import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.monster.rocketmq.Config.NAME_SERVER_URL;

/**
 * 局部顺序
 */
public class JubuOrderProducer {

    public static void main(String[] args) throws Exception{


        //初始化默认producer
        DefaultMQProducer producer=new DefaultMQProducer("simple_msg");
        producer.setNamesrvAddr(NAME_SERVER_URL);
        //设个超时时间，太短了容易sendDefaultImpl call timeout
        producer.setSendMsgTimeout(10000);
        producer.start();


        for(int i=1;i<10;i++){
            //类似订单id，一个订单发5次
            int orderId =i;
            for(int j=1;j<5;j++){
                Message msg=new Message("order_msg","order_"+orderId,"KEY"+orderId,("订单--"+orderId+"--物品--"+j).getBytes(StandardCharsets.UTF_8));
                SendResult result= producer.send(msg, new MessageQueueSelector() {
                    //选择器，选择某一个队列
                    //mqs:当前topic有多少个队列
                    //msg:消息体
                    //arg:就是传的orderId
                    @Override
                    public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                        Integer orderId= (Integer) arg;
                        int index=orderId%mqs.size();
                        System.out.println("订单id--"+orderId+"发送到对应队列--"+index);
                        return mqs.get(index);
                    }
                },orderId);
                System.out.println(result);
            }

        }

        producer.shutdown();



    }


}
