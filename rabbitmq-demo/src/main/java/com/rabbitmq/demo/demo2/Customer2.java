package com.rabbitmq.demo.demo2;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.demo.util.ConnectUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Envelope;

public class Customer2 {
	public static final String QUEUE_NAME = "queue2";
	public static void main(String[] args) throws IOException, TimeoutException {
		
		Connection connection = ConnectUtil.connect();
		
		final Channel channel = connection.createChannel();
		
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		
		channel.basicQos(1);  
		Consumer consumer = new DefaultConsumer(channel){
			@Override  
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException { 
				String message = new String(body, "UTF-8"); 
				System.out.println("customer1:" + message);
				
				try {  
					 Thread.sleep(1000);  
                } catch (InterruptedException e) { 
                	
                } finally {  
                  //应答  添加此处  basicConsume自动应答设为false
                  channel.basicAck(envelope.getDeliveryTag(), false);    
                }  
			}
		};
		channel.basicConsume(QUEUE_NAME, false, consumer);  
	}
}
