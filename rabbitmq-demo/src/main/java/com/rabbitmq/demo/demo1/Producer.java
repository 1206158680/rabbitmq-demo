package com.rabbitmq.demo.demo1;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.demo.util.ConnectUtil;

public class Producer { 
	
	public static final String QUEUE_NAME = "queue1";
	public static void main(String[] args) throws IOException, TimeoutException {
		
		//创建一个连接
		Connection connection = ConnectUtil.connect();
		
		//创建一个通道
		Channel channel = connection.createChannel();
		
		/*
		 * 创建一个队列
		 * 参数：
		 * 1 queue:消息队列名称
		 * 2 durable:是否持久化，在服务器重启时，能否存活
		 * 3 exclusive:连接断开后，是否自动删除该队列
		 * 4 autoDelete:是否自动删除，当最后一个消费者断开连接后队列是否自动被删除
		 * 5 arguments:队列中的消息什么时候会自动被删除
		 * 
		 */
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		
		String message = "Hello World!";
		
		/*
		 * 发送消息 
		 * 参数：
		 * 1 exchange:交换机
		 * 2 routingKey:路由键 消息队列名称
		 * 3 mandatory:true如果exchange根据自身类型和消息routeKey无法找到一个符合条件的queue，那么会调用basic.return方法将消息返还给生产者。
		 * 4 immediate:如果exchange在将消息route到queue(s)时发现对应的queue上没有消费者，那么这条消息不会放入队列中。当与消息routeKey关联的所有queue(一个或多个)都没有消费者时，该消息会通过basic.return方法返还给生产者。
		 * 5 props:0:不持久化 1：持久化 这里指的是消息的持久化
		 */
		channel.basicPublish("", QUEUE_NAME, false, false, null, message.getBytes());
		
		//关闭连接
		channel.close();
		connection.close();
	}
}
