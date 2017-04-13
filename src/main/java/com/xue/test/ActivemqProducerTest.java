package com.xue.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xue.entity.Mail;
import com.xue.mq.MQProducer;

@ContextConfiguration(locations = {"classpath:spring-context-producer.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class ActivemqProducerTest {
	
	@Autowired
	private MQProducer mqProducer;
	
	@Test
	public void send(){
		Mail mail = new Mail();
		mail.setTo("xueshun1211@163.com");
		mail.setSubject("异步发送邮件");
		mail.setContent("Hi , This is a message");
		
		this.mqProducer.sendMessage(mail);
		System.out.println("发送成功");
	}
}
