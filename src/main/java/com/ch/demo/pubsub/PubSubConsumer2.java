package com.ch.demo.pubsub;

import com.ch.config.RabbitConfig;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author CH
 * @date 2022-05-09
 */
public class PubSubConsumer2 {

    public static void main(String[] args) {
        try {
            Connection connection = RabbitConfig.getConnection();
            Channel channel = connection.createChannel();

            // 声明队列
            channel.queueDeclare(PubSubConstant.PUB_SUB_QUEUE2, true, false, false, null);
            // 队列绑定交换机
            channel.queueBind(PubSubConstant.PUB_SUB_QUEUE2, PubSubConstant.PUB_SUB_EXCHANGE, "");

            // 消费消息
            channel.basicConsume(PubSubConstant.PUB_SUB_QUEUE2, false, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                                           AMQP.BasicProperties properties, byte[] body) throws IOException {
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
