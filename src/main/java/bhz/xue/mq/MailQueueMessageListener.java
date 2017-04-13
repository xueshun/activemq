package bhz.xue.mq;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.xue.entity.Mail;

import bhz.xue.service.MailService;

@Component
public class MailQueueMessageListener implements SessionAwareMessageListener<Message>{

	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Autowired
	private Destination mailTestQueue;
	
	@Autowired
	private MailService mailService;
	
	/*@Override
	public void onMessage(Message message, Session session) throws JMSException {
		// TODO Auto-generated method stub
		
	}*/
	
	public synchronized void onMessage(Message message, Session session){
		try {
			TextMessage msg = (TextMessage) message;
			final String ms = msg.getText();
			System.out.println("收到信息：" + ms);
			
			//转化成相应的对象
			Mail mail = JSONObject.parseObject(ms,Mail.class);
			if(mail == null){
				return;
			}
			try{
				//执行发送业务
				mailService.mailSend(mail);
			}catch(Exception e){
				
				//发送异常，重新放回队列
			jmsTemplate.send(mailTestQueue,new MessageCreator() {
				
				@Override
				public Message createMessage(Session session) throws JMSException {
					
					return session.createTextMessage(ms);
				}
			});
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
