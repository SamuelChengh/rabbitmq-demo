package com.ch.demo.workqueues;

import com.ch.config.RabbitConfig;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author CH
 * @date 2022-05-08
 */
public class WorkQueuesConsumer2 {

    public static void main(String[] args) {
        try {
            // 获取连接
            Connection connection = RabbitConfig.getConnection();
            // 获取连接中的通道
            Channel channel = connection.createChannel();
            // 声明消息队列
            channel.queueDeclare(WorkQueuesConstant.WORK_QUEUES_NAME, true, false, false, null);
            /*
             * 消费端限流，实现能者多劳
             *
             * 参数1：一次给 2 条消息进行消费
             * 注：一定要手动ack
             */
            channel.basicQos(2);
            // 消费消息
            channel.basicConsume(WorkQueuesConstant.WORK_QUEUES_NAME, false, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                                           AMQP.BasicProperties properties, byte[] body) throws IOException {
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // 手动ack
                    channel.basicAck(envelope.getDeliveryTag(), false);
                    System.out.println("============ 消费者2 消费消息成功 ============：" + new String(body));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
