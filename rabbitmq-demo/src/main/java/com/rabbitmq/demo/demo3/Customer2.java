package com.rabbitmq.demo.demo3;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.demo.util.ConnectUtil;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP;

public class Customer2 {
	
	public static final String EXCHANGE_NAME = "exchange1";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		
		Connection connection = ConnectUtil.connect();
		
		Channel channel = connection.createChannel();
		
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
		
		String queueName = channel.queueDeclare().getQueue();
		
		channel.queueBind(queueName, EXCHANGE_NAME, "");
		
		Consumer consumer = new DefaultConsumer(channel){
			
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println("customer2:" + message);
			};
		};
		
		channel.basicConsume(queueName, true, consumer);
	}
}
