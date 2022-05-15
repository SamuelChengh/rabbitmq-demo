package com.ch.demo.workqueues;

import com.ch.config.RabbitConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author CH
 * @date 2022-05-08
 */
public class WorkQueuesProducer {

    public static void main(String[] args) {
        // 获取连接
        Connection connection = RabbitConfig.getConnection();
        Channel channel = null;
        try {
            // 获取连接中的通道
            channel = connection.createChannel();
            // 声明消息队列
            channel.queueDeclare(WorkQueuesConstant.WORK_QUEUES_NAME, true, false, false, null);
            // 发布消息
            for (int i = 0; i < 10; i++) {
                channel.basicPublish("", WorkQueuesConstant.WORK_QUEUES_NAME, null, ("work " + i).getBytes());
            }
            System.out.println("============ 发布消息成功 ============");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            RabbitConfig.closeConnection(channel, connection);
        }
    }

}
