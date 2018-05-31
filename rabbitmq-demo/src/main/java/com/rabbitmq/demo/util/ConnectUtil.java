package com.rabbitmq.demo.util;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConnectUtil {
	
	private final static String HOST = "localhost";
	
	private final static String USER_NAME = "guest";
	
	private final static String PASSWORD = "guest";
	
	private final static int PORT = 15672;
	
	public static Connection connect() throws IOException, TimeoutException{
		
		//创建连接工厂
		ConnectionFactory factory = new ConnectionFactory();
		
		//配置rabbitMQ相关信息
		factory.setHost(HOST);
		//factory.setUsername(USER_NAME);
		//factory.setPassword(PASSWORD);
		//factory.setPort(PORT);
		
		Connection connection = factory.newConnection();
		return connection;
	}
}
