package com.rabbitmq.demo.demo4;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.demo.util.ConnectUtil;

public class Customer2 {
	
	public static final String EXCHANGE_NAME = "exchange_rou";
	
	public static final String[] routingKeys = new String[]{"level1", "level2"};
	
	public static void main(String[] args) throws IOException, TimeoutException {
		Connection connection = ConnectUtil.connect();
		
		Channel channel = connection.createChannel();
		
		channel.exchangeDeclare(EXCHANGE_NAME, "direct");
		
		String queueName = channel.queueDeclare().getQueue();
		
		for (String routingKey : routingKeys){
			channel.queueBind(queueName, EXCHANGE_NAME, routingKey);
		}
		
		Consumer consumer = new DefaultConsumer(channel){
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println("customer2:" + message);
			}
		};
		
		channel.basicConsume(queueName, true, consumer);
	}
}
