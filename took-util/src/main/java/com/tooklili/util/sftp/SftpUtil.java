package com.tooklili.util.sftp;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.SftpProgressMonitor;

/**
 * sftp 工具类
 * @author shuai.ding
 * @date 2017年3月4日上午10:54:13
 */
public class SftpUtil {

	public static final Logger LOGGER = LoggerFactory.getLogger(SftpUtil.class);
	
	/**
	 * 判断目录是否存在
	 * @param sftp       
	 * @param directory   目录路径 如:/uer/local/frame
	 * @return
	 */
	public static boolean isDirExist(ChannelSftp sftp, String directory) {
		boolean isDirExistFlag = false;
		try {
			SftpATTRS sftpATTRS = sftp.lstat(directory);
			isDirExistFlag = sftpATTRS.isDir();
		} catch (Exception e) {
			if (e.getMessage().toLowerCase().equals("no such file")) {
				isDirExistFlag = false;
			}
		}
		return isDirExistFlag;
	}
	 
	/**
	 * 创建目录
	 * @param createpath   目录路径 如:/uer/local/frame
	 * @param sftp
	 */
	public static void createDir(String createpath, ChannelSftp sftp){
		try {
			if (isDirExist(sftp, createpath)) {
				sftp.cd(createpath);
				return;
			}
			
			String pathArry[] = createpath.split("/");
			StringBuffer filePath = new StringBuffer("/");
			for (String path : pathArry) {
				if (path.equals("")) {
					continue;
				}
				filePath.append(path + "/");
				if (isDirExist(sftp, filePath.toString())) {
					sftp.cd(filePath.toString());
				} else {
					// 建立目录
					sftp.mkdir(filePath.toString());
					// 进入并设置为当前目录
					sftp.cd(filePath.toString());
				}
			}
			sftp.cd(createpath);
		} catch (SftpException e) {
			LOGGER.error("sftp create diractory["+createpath+"] excetpin",e);
		}
	}
	
	/**
	 * 上传文件
	 * 直接将本地文件名为src的文件上传到目标服务器，目标文件名为dst。
     * 注：使用这个方法时，dst可以是目录，当dst是目录时，上传后的目标文件名将与src文件名相同
	 * @param src   本地文件路径
	 * @param dst   目标路径（可以是文件，也可以是目录）
	 * @param sftp
	 */
	public static void upload(String src, String dst, ChannelSftp sftp) throws SftpException{
		//判断服务端是否有此目录，如果没有，创建
		int index = dst.lastIndexOf("/");
		String directory = dst.substring(0, index+1);		
		createDir(directory,sftp);
		
		sftp.put(src, dst);
	}
	
	/**
	 * 带有进度的上传
	 */
	public static void upload(String src, String dst, ChannelSftp sftp,SftpProgressMonitor sftpProgressMonitor) throws SftpException{
		//判断服务端是否有此目录，如果没有，创建
		int index = dst.lastIndexOf("/");
		String directory = dst.substring(0, index+1);		
		createDir(directory,sftp);		
		sftp.put(src, dst,sftpProgressMonitor);
	} 
	
	/**
	 * 上传文件(目标文件路径不存在，会创建)
	 * 将本地文件名为src的文件输入流上传到目标服务器，目标文件名为dst。
	 * @param src     本地文件输入流
	 * @param dst     目标文件名 如：/usr/local/nginx-1.7.8/html/frame/20170304/a.txt
	 * @param sftp
	 */
	public static void upload(InputStream src,String dst, ChannelSftp sftp) throws SftpException{
		//判断服务端是否有此目录，如果没有，创建
		int index = dst.lastIndexOf("/");
		String directory = dst.substring(0, index+1);		
		createDir(directory,sftp);		
		sftp.put(src, dst);
	}
	
	/**
	 * 采用向put方法返回的输出流中写入数据的方式来传输文件。
	 * 需要由程序来决定写入什么样的数据，这里是将本地文件的输入流写入输出流。采用这种方式的好处是， 可以自行设定每次写入输出流的数据块大小.
	 * @param src          源文件路径
	 * @param dst          目标文件路径
	 * @param sftp
	 * @throws Exception
	 */
	public static void uploadByStream(String src, String dst, ChannelSftp sftp) throws Exception {
		//判断服务端是否有此目录，如果没有，创建
		int index = dst.lastIndexOf("/");
		String directory = dst.substring(0, index+1);		
		createDir(directory,sftp);		
				
		OutputStream out = sftp.put(dst, ChannelSftp.OVERWRITE); // 使用OVERWRITE模式
		byte[] buff = new byte[1024 * 256]; // 设定每次传输的数据块大小为256KB
		int read;
		if (out != null) {
			InputStream is = new FileInputStream(src);
			do {
				read = is.read(buff, 0, buff.length);
				if (read > 0) {
					out.write(buff, 0, read);
				}
				out.flush();
			} while (read >= 0);

			// 关闭输入流
			if (is != null) {
				is.close();
			}
		}
	}
	
}
