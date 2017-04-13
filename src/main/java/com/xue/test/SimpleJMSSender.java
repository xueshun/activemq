package com.xue.test;

import java.io.Serializable;
import java.util.Date;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class SimpleJMSSender {
	
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-send.xml");
		
		JmsTemplate jmsTemplate = (JmsTemplate) ctx.getBean("jmsTemplate");
		for (int i = 0; i < 10; i++) {
			jmsTemplate.send(new MessageCreator() {
				
				@Override
				public Message createMessage(Session session) throws JMSException {
					ObjectMessage msg = session.createObjectMessage();
					msg.setStringProperty("phrCode", "C001");
					msg.setObject(new Body());
					/*TextMessage textMessage = session.createTextMessage();
					textMessage.setText("我是消息内容，id为：" );*/
					return  msg;
				}
			});
		}
	}
}

class Body implements Serializable{
	private static final long serialVersionUID = 1L;
	String hand = "aa";
	int siz = 176;
	Date date = new Date();
}
