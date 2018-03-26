package com.tooklili.util.sftp;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * 连接、断开sftp服务器
 * @author shuai.ding
 * @date 2017年3月4日上午11:14:19
 */
public class SFTPChannel {
    Session session = null;
    Channel channel = null;

    private static final Logger logger =LoggerFactory.getLogger(SFTPChannel.class);

    /**
     * 连接sftp服务器
     * @param host      主机名
     * @param port      端口
     * @param username  用户名
     * @param password  密码
     * @return
     */
    public ChannelSftp getChannel(String host, int port, String username,String password){
    	try{
    		JSch jsch = new JSch(); // 创建JSch对象
            session = jsch.getSession(username, host, port); // 根据用户名，主机ip，端口获取一个Session对象
            logger.debug("Session created.");
            if (password != null) {
                session.setPassword(password); // 设置密码
            }
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config); // 为Session对象设置properties
//            session.setTimeout(timeout); // 设置timeout时间
            session.connect(); // 通过Session建立链接
            logger.debug("Session connected.");

            logger.debug("Opening Channel.");
            channel = session.openChannel("sftp"); // 打开SFTP通道
            channel.connect(); // 建立SFTP通道的连接
            logger.debug("Connected successfully to ftpHost = " + host + ",as ftpUserName = " + username
                    + ", returning: " + channel);
    	}catch(JSchException e){
    		logger.error("Connected failed to ftpHost = " + host + ",as ftpUserName = " + username, e);
    	}      
        return (ChannelSftp) channel;
    }

   
    /**
     * 关闭连接
     */
    public void closeChannel(){
        if (channel != null) {
            channel.disconnect();
        }
        if (session != null) {
            session.disconnect();
        }
    }
}