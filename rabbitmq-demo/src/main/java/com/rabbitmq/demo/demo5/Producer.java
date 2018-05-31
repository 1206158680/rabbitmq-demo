package com.rabbitmq.demo.demo5;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.demo.util.ConnectUtil;

public class Producer {
	
	public static final String EXCHANGE_NAME = "exchange4";
	
	public static final String[] routingKeys = new String[]{"rabbit.key", "demo.rabbit.key", "rabbit.key.demo"};
	
	public static void main(String[] args) throws IOException, TimeoutException {
		
		Connection connection = ConnectUtil.connect();
		
		Channel channel = connection.createChannel();
		
		channel.exchangeDeclare(EXCHANGE_NAME, "topic");
		
		for (String routingKey : routingKeys){
			String message = "hello World " + routingKey;
			channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes());
		}
		
		channel.close();
		connection.close();
	}
}
