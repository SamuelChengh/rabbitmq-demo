package com.ch.demo.helloworld;

import com.ch.config.RabbitConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author CH
 * @date 2022-05-05
 */
public class HelloWorldProducer {

    public static void main(String[] args) {
        // 获取连接
        Connection connection = RabbitConfig.getConnection();
        Channel channel = null;
        try {
            // 获取连接中的通道
            channel = connection.createChannel();

            /*
             * 声明消息队列
             *
             * 参数1：队列名称，不存在则自动创建
             * 参数2：队列是否需要持久化，true持久化 false不持久化
             * 参数3：是否独占队列，true独占队列 false共享队列
             * 参数4：是否在消费完成后删除队列 true自动删除 false不自动删除
             * 参数5：额外附加参数
             */
            channel.queueDeclare(HelloWorldConstant.HELLO_WORLD_QUEUE_NAME, true, false, false, null);

            /*
             * 发布消息
             *
             * 参数1：交换机名称
             * 参数2：队列名称
             * 参数3：消息的额外设置
             * 参数4：消息的具体内容
             */
            channel.basicPublish("", HelloWorldConstant.HELLO_WORLD_QUEUE_NAME, null, "hello world".getBytes());

            System.out.println("============ 发布消息成功 ============");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            RabbitConfig.closeConnection(channel, connection);
        }
    }

}
