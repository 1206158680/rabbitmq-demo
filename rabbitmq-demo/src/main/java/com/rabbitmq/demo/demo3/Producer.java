package com.rabbitmq.demo.demo3;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.demo.util.ConnectUtil;

public class Producer {
	
	public static final String EXCHANGE_NAME = "exchange1";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		
		Connection connection = ConnectUtil.connect();
		
		Channel channel = connection.createChannel();
		
		/*
		 * type:
		 * direct,topic,fanout,header
		 * */
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
		
		for (int i = 0; i < 9; i++){
			String message = "Hello World" + i;
			channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
		}
		
		channel.close();
		connection.close();
	}
}
