package com.rabbitmq.demo.demo4;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.demo.util.ConnectUtil;

public class Producer {
	
	public static final String EXCHANGE_NAME = "exchange_rou";
	
	public static final String[] routingKeys = new String[]{"level1", "level2", "level3"};
	
	public static void main(String[] args) throws IOException, TimeoutException {
		
		Connection connection = ConnectUtil.connect();
		Channel channel = connection.createChannel();
		
		channel.exchangeDeclare(EXCHANGE_NAME, "direct");
		
		for (String routingKey : routingKeys){
			String message = "hello world " + routingKey; 
			channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes());
		}
		
		channel.close();
		connection.close();
	}
}
