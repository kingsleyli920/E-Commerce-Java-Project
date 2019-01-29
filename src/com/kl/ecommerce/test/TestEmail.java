package com.kl.ecommerce.test;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class TestEmail {

	public static void main(String[] args) throws Exception {
		// 0.1 服务器的设置
		Properties props = new Properties();
		props.setProperty("mail.host", "smtp.qq.com");
		props.setProperty("mail.smtp.auth", "true");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.starttls.enable", "true");

		// 0.2 账号和密码
		Authenticator authenticator = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				// 设置发送者邮箱账户
				return new PasswordAuthentication("852444214@qq.com", "vzjotitdnlsfbgae");
			}
		};

		System.out.println("账户设置完成，准备发送");

		// 1 与163服务器建立连接：Session
		Session session = Session.getDefaultInstance(props, authenticator);
		System.out.println("与服务器建立连接完成");
		// 2 编写邮件：Message
		Message message = new MimeMessage(session);
		// 2.1 发件人
		message.setFrom(new InternetAddress("852444214@qq.com"));
		// 2.2 收件人 , to:收件人 ， cc ：抄送，bcc：暗送（密送）。（模拟账号）
		message.setRecipient(RecipientType.TO, new InternetAddress("kingsleyli920@hotmail.com"));
		// 2.3 主题
		message.setSubject("这是我们得第一份邮件");
		// 2.4 内容
		message.setContent("哈哈，您到我的商城注册了。", "text/html;charset=UTF-8");
		System.out.println("设置邮件内容完成");
		// 3 将消息进行发送：Transport
		Transport.send(message);
		System.out.println("发送完成");

	}

}
