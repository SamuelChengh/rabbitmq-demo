package com.ch.demo.helloworld;

import com.ch.config.RabbitConfig;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author CH
 * @date 2022-05-05
 */
public class HelloWorldConsumer {

    public static void main(String[] args) {
        try {
            // 获取连接
            Connection connection = RabbitConfig.getConnection();
            // 获取连接中的通道
            Channel channel = connection.createChannel();

            // 通道绑定消息队列
            channel.queueDeclare(HelloWorldConstant.HELLO_WORLD_QUEUE_NAME, true, false, false, null);

            /*
             * 消费消息
             *
             * 参数1：要消费的队列名称
             * 参数2：消息确认机制 是否自动确认 true自动 false手动
             * 参数3：消费消息的回调接口
             */
            channel.basicConsume(HelloWorldConstant.HELLO_WORLD_QUEUE_NAME, true, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                                           AMQP.BasicProperties properties, byte[] body) throws IOException {
                    // 手动ack
                    channel.basicAck(envelope.getDeliveryTag(), false);
                    System.out.println("============ 消费消息成功 ============：" + new String(body));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
