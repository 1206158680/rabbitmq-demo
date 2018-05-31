package com.rabbitmq.demo.demo1;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;
import com.rabbitmq.demo.util.ConnectUtil;

public class Customer {
	
	public static final String QUEUE_NAME = "queue1";
	
	public static void main(String[] args) throws IOException, TimeoutException, ShutdownSignalException, ConsumerCancelledException, InterruptedException {
		
		Connection connection = ConnectUtil.connect();
		
		Channel channel = connection.createChannel();
		
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		
		//创建队列消费者1
		QueueingConsumer customer = new QueueingConsumer(channel);
		 
		/* 指定消费队列
		 * autoAck 接收到消息后是否自动应
		 */
		channel.basicConsume(QUEUE_NAME, true, customer);
		while(true){
			QueueingConsumer.Delivery delivery = customer.nextDelivery();
			String message = new String(delivery.getBody());
			System.out.println("customer1 received:" + message);
		}
		
		
	}
}	
