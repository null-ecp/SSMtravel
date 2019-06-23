package com.weison.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * 发邮件工具类
 */
public final class MailUtils {

    private static final String USER = "553830180@qq.com"; // 发件人称号，同邮箱地址
    private static final String PASSWORD = "evvomnbhkhijbcid"; // 如果是qq邮箱可以使户端授权码，或者登录密码

    /**
     *
     * @param to 收件人邮箱
     * @param text 邮件正文
     * @param title 标题
     */
    /* 发送验证信息的邮件 */
    public static boolean sendMail(String to, String text, String title){
        try {
            final Properties config = new Properties();
            // 是否加密
            config.put("mail.smtp.auth", "true");
            // 邮箱服务器
            config.put("mail.smtp.host", "smtp.qq.com");

            // 发件人的账号
            config.put("mail.user", USER);
            //发件人的密码
            config.put("mail.password", PASSWORD);

            // 构建授权信息，用于进行SMTP进行身份验证
            // Authenticator 一个账户密码验证器抽象类
            Authenticator authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    // 用户名、密码
                    String userName = config.getProperty("mail.user");
                    String password = config.getProperty("mail.password");
                    // 返回一个验证器 需要两个参数 , 账号和密码
                    return new PasswordAuthentication(userName, password);
                }
            };
            // 使用环境属性和授权信息，创建邮件会话
            Session mailSession = Session.getInstance(config, authenticator);
            // 创建邮件消息 需要传入会话对象
            MimeMessage message = new MimeMessage(mailSession);
            // 设置发件人
            // InternetAddress 为网络地址对象 传参邮箱地址
            String username = config.getProperty("mail.user");
            InternetAddress form = new InternetAddress(username);
            message.setFrom(form);

            // 设置收件人
            InternetAddress toAddress = new InternetAddress(to);
            message.setRecipient(Message.RecipientType.TO, toAddress);

            // 设置邮件标题
            message.setSubject(title);

            // 设置邮件的内容体
            message.setContent(text, "text/html;charset=UTF-8");
            // 发送邮件 调用Transport工具类的send
            Transport.send(message);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) throws Exception { // 做测试用
        MailUtils.sendMail("ad725133@gmail.com","你好，这是一封测试邮件，无需回复。","测试邮件");
        System.out.println("发送成功");
    }



}
