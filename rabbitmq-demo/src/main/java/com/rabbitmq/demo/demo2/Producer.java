package com.rabbitmq.demo.demo2;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.demo.util.ConnectUtil;

public class Producer {
	
	public static final String QUEUE_NAME = "queue2";
	public static void main(String[] args) throws IOException, TimeoutException {
		
		Connection connection = ConnectUtil.connect();
		
		Channel channel = connection.createChannel();
		
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		channel.basicQos(1);  
		for (int i = 0; i < 9; i++){
			String message = "Hello World " + i;
			channel.basicPublish("", QUEUE_NAME, false, null, message.getBytes());
		}
		
		channel.close();
		connection.close();
	}
}
