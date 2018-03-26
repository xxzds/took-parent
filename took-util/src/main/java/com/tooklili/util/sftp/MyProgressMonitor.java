package com.tooklili.util.sftp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.SftpProgressMonitor;

/**
 * 进度监控器-JSch每次传输一个数据块，就会调用count方法来实现主动进度通知
 * @author shuai.ding
 * @date 2017年3月22日下午5:18:12
 */
public class MyProgressMonitor implements SftpProgressMonitor {
	
	private static final Logger LOGGER =LoggerFactory.getLogger(MyProgressMonitor.class);

	private long count = 0; // 当前接收的总字节数
	private long max = 0; // 最终文件大小
	private long percent = -1; // 进度

	/**
	 * 当每次传输了一个数据块后，调用count方法，count方法的参数为这一次传输的数据块大小
	 */
	@Override
	public boolean count(long count) {
		this.count += count;
		if (percent >= this.count * 100 / max) {
			return true;
		}
		percent = this.count * 100 / max;
		LOGGER.info("Completed " + this.count + "(" + percent + "%) out of " + max + ".");
		return true;
	}

	/**
	 * 当传输结束时，调用end方法
	 */
	@Override
	public void end() {
		LOGGER.info("Transferring done.");
	}

	/**
	 * 当文件开始传输时，调用init方法
	 */
	@Override
	public void init(int op, String src, String dest, long max) {
		if (op == SftpProgressMonitor.PUT) {
			LOGGER.info("Upload file begin.[desc:{}]",dest);
		} else {
			LOGGER.info("Download file begin.[src:{},desc:{}]",src,dest);
		}

		this.max = max;
		this.count = 0;
		this.percent = -1;
	}
}
