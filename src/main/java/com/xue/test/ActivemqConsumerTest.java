package com.xue.test;

import org.springframework.beans.BeansException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ActivemqConsumerTest {
	
	public static void main(String[] args) {
		try {
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{
					"spring-context-consumer.xml"
			});
			context.start();
		} catch (BeansException e) {
			e.printStackTrace();
		}
		
	}
}
