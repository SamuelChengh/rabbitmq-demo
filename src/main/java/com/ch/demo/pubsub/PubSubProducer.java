package com.ch.demo.pubsub;

import com.ch.config.RabbitConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author CH
 * @date 2022-05-09
 */
public class PubSubProducer {

    public static void main(String[] args) {
        Connection connection = RabbitConfig.getConnection();
        Channel channel = null;
        try {
            channel = connection.createChannel();

            // 声明交换机
            channel.exchangeDeclare(PubSubConstant.PUB_SUB_EXCHANGE, "fanout", true);

            // 发布消息
            channel.basicPublish(PubSubConstant.PUB_SUB_EXCHANGE, "", null, "Publish/Subscribe".getBytes());

            System.out.println("============ 发布消息成功 ============");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            RabbitConfig.closeConnection(channel, connection);
        }
    }

}
