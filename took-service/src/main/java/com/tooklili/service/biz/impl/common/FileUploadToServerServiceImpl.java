package com.tooklili.service.biz.impl.common;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;

import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import com.tooklili.service.biz.impl.common.exception.FileNameLengthLimitExceededException;
import com.tooklili.service.biz.impl.common.exception.InvalidExtensionException;
import com.tooklili.service.biz.intf.common.FileUploadService;
import com.tooklili.service.util.MessageUtils;
import com.tooklili.util.PropertiesUtil;
import com.tooklili.util.result.PlainResult;
import com.tooklili.util.security.Md5Utils;
import com.tooklili.util.sftp.SFTPChannel;
import com.tooklili.util.sftp.SftpUtil;

/**
 * 上传到文件服务器
 * 
 * @author ding.shuai
 * @date 2016年8月17日上午9:09:53
 */
public class FileUploadToServerServiceImpl implements FileUploadService {
	private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadToServerServiceImpl.class);

	// 默认大小 50M
	private long DEFAULT_MAX_SIZE = 52428800;

	// 上传路径前缀
	private String defaultBaseDir = PropertiesUtil.getInstance("config.properties").getValue("sftp.directory");

	// 默认的文件名最大长度
	private int DEFAULT_FILE_NAME_LENGTH = 200;

	private String[] IMAGE_EXTENSION = { "bmp", "gif", "jpg", "jpeg", "png" };

	private String[] FLASH_EXTENSION = { "swf", "flv" };

	private String[] MEDIA_EXTENSION = { "swf", "flv", "mp3", "wav", "wma", "wmv", "mid", "avi", "mpg", "asf", "rm","rmvb" };

	private String[] DEFAULT_ALLOWED_EXTENSION = {
			// 图片
			"bmp", "gif", "jpg", "jpeg", "png",
			// word excel powerpoint
			"doc", "docx", "xls", "xlsx", "ppt", "pptx", "html", "htm", "txt",
			// 压缩文件
			"rar", "zip", "gz", "bz2",
			// pdf
			"pdf" };

	private int counter = 0;

	public String getDefaultBaseDir() {
		return defaultBaseDir;
	}

	public void setDefaultBaseDir(String defaultBaseDir) {
		this.defaultBaseDir = defaultBaseDir;
	}

	public long getDEFAULT_MAX_SIZE() {
		return DEFAULT_MAX_SIZE;
	}

	public void setDEFAULT_MAX_SIZE(long dEFAULT_MAX_SIZE) {
		DEFAULT_MAX_SIZE = dEFAULT_MAX_SIZE;
	}

	public int getDEFAULT_FILE_NAME_LENGTH() {
		return DEFAULT_FILE_NAME_LENGTH;
	}

	public void setDEFAULT_FILE_NAME_LENGTH(int dEFAULT_FILE_NAME_LENGTH) {
		DEFAULT_FILE_NAME_LENGTH = dEFAULT_FILE_NAME_LENGTH;
	}

	public String[] getIMAGE_EXTENSION() {
		return IMAGE_EXTENSION;
	}

	public void setIMAGE_EXTENSION(String[] iMAGE_EXTENSION) {
		IMAGE_EXTENSION = iMAGE_EXTENSION;
	}

	public String[] getFLASH_EXTENSION() {
		return FLASH_EXTENSION;
	}

	public void setFLASH_EXTENSION(String[] fLASH_EXTENSION) {
		FLASH_EXTENSION = fLASH_EXTENSION;
	}

	public String[] getMEDIA_EXTENSION() {
		return MEDIA_EXTENSION;
	}

	public void setMEDIA_EXTENSION(String[] mEDIA_EXTENSION) {
		MEDIA_EXTENSION = mEDIA_EXTENSION;
	}

	public String[] getDEFAULT_ALLOWED_EXTENSION() {
		return DEFAULT_ALLOWED_EXTENSION;
	}

	public void setDEFAULT_ALLOWED_EXTENSION(String[] dEFAULT_ALLOWED_EXTENSION) {
		DEFAULT_ALLOWED_EXTENSION = dEFAULT_ALLOWED_EXTENSION;
	}

	@Override
	public PlainResult<String> uploadFile(MultipartFile file) {
		return this.uploadFile(file, defaultBaseDir, DEFAULT_ALLOWED_EXTENSION, DEFAULT_MAX_SIZE, true);
	}

	@Override
	public PlainResult<String> uploadFile(MultipartFile file, String[] allowedExtension) {
		return this.uploadFile(file, defaultBaseDir, allowedExtension, DEFAULT_MAX_SIZE, true);
	}

	@Override
	public PlainResult<String> uploadFile(MultipartFile file, String[] allowedExtension, long maxSize) {
		return this.uploadFile(file, defaultBaseDir, allowedExtension, maxSize, true);
	}

	@Override
	public PlainResult<String> uploadFile(MultipartFile file, String[] allowedExtension, long maxSize,
			boolean needDatePathAndRandomName) {
		return this.uploadFile(file, defaultBaseDir, allowedExtension, maxSize, needDatePathAndRandomName);
	}

	@Override
	public PlainResult<String> uploadFile(MultipartFile file, String baseDir, String[] allowedExtension, long maxSize,
			boolean needDatePathAndRandomName) {
		PlainResult<String> result = new PlainResult<String>();
		String filePath = null;
		try {
			filePath = this.upload(baseDir, file, allowedExtension, maxSize, needDatePathAndRandomName);
		} catch (IOException e) {
			LOGGER.error("file upload error", e);
			result.setErrorMessage(MessageUtils.message("upload.server.error"));
		} catch (InvalidExtensionException.InvalidImageExtensionException e) {
			LOGGER.error("upload.not.allow.image.extension", e);
			result.setErrorMessage(MessageUtils.message("upload.not.allow.image.extension"));
		} catch (InvalidExtensionException.InvalidFlashExtensionException e) {
			LOGGER.error("upload.not.allow.flash.extension", e);
			result.setErrorMessage(MessageUtils.message("upload.not.allow.flash.extension"));
		} catch (InvalidExtensionException.InvalidMediaExtensionException e) {
			LOGGER.error("upload.not.allow.media.extension", e);
			result.setErrorMessage(MessageUtils.message("upload.not.allow.media.extension"));
		} catch (InvalidExtensionException e) {
			LOGGER.error("upload.not.allow.extension", e);
			result.setErrorMessage(MessageUtils.message("upload.not.allow.extension"));
		} catch (FileUploadBase.FileSizeLimitExceededException e) {
			LOGGER.error("upload.exceed.maxSize", e);
			result.setErrorMessage(MessageUtils.message("upload.exceed.maxSize"));
		} catch (FileNameLengthLimitExceededException e) {
			LOGGER.error("upload.filename.exceed.length", e);
			result.setErrorMessage(MessageUtils.message("upload.filename.exceed.length"));
		} catch (SftpException e) {
			LOGGER.error("file upload error", e);
			result.setErrorMessage(MessageUtils.message("upload.server.error"));
		}
		result.setData(filePath);
		return result;
	}

	@Override
	public void delete(String filename) throws IOException {

	}

	/**
	 * 文件上传(通过sftp服务器上传)
	 * 
	 * @param baseDir
	 *            相对应用的基目录(sftp 全路径)
	 * @param file
	 *            上传的文件
	 * @param allowedExtension
	 *            允许的文件类型 null 表示允许所有
	 * @param maxSize
	 *            最大上传的大小 -1 表示不限制
	 * @param needDatePathAndRandomName
	 *            是否需要日期目录和随机文件名前缀
	 * @return 返回上传成功的文件名
	 * @throws InvalidExtensionException
	 *             如果MIME类型不允许
	 * @throws FileSizeLimitExceededException
	 *             如果超出最大大小
	 * @throws IOException
	 *             读写文件出错时
	 * @throws FileNameLengthLimitExceededException
	 *             文件名太长
	 */
	private String upload(String baseDir, MultipartFile file, String[] allowedExtension, long maxSize,
			boolean needDatePathAndRandomName) throws InvalidExtensionException, FileSizeLimitExceededException,
			IOException, FileNameLengthLimitExceededException, SftpException {

		int fileNamelength = file.getOriginalFilename().length();
		if (fileNamelength > DEFAULT_FILE_NAME_LENGTH) {
			throw new FileNameLengthLimitExceededException(file.getOriginalFilename(), fileNamelength,
					DEFAULT_FILE_NAME_LENGTH);
		}

		this.assertAllowed(file, allowedExtension, maxSize);
		String filename = this.extractFilename(file, baseDir, needDatePathAndRandomName);

		// 上传到sftp服务器
		SFTPChannel sftpChannel = new SFTPChannel();
		PropertiesUtil propertiesUtil = PropertiesUtil.getInstance("config.properties");
		String host = propertiesUtil.getValue("sftp.host");
		String port = propertiesUtil.getValue("sftp.port");
		String username = propertiesUtil.getValue("sftp.username");
		String password = propertiesUtil.getValue("sftp.password");

		ChannelSftp channelSftp = sftpChannel.getChannel(host, Integer.valueOf(port), username, password);
		try {
			SftpUtil.upload(file.getInputStream(), filename, channelSftp);
		} catch (SftpException e) {
			throw e;
		} finally {
			sftpChannel.closeChannel();
		}
		return filename.substring(baseDir.length());
	}

	/**
	 * 是否允许文件上传
	 * 
	 * @param file
	 *            上传的文件
	 * @param allowedExtension
	 *            文件类型 null 表示允许所有
	 * @param maxSize
	 *            最大大小 字节为单位 -1表示不限制
	 * @throws InvalidExtensionException
	 *             MIME类型不允许
	 * @throws FileSizeLimitExceededException
	 *             超出最大大小
	 */
	private void assertAllowed(MultipartFile file, String[] allowedExtension, long maxSize)
			throws InvalidExtensionException, FileSizeLimitExceededException {

		String filename = file.getOriginalFilename();
		String extension = FilenameUtils.getExtension(filename);

		if (allowedExtension != null && !isAllowedExtension(extension, allowedExtension)) {
			if (Arrays.equals(allowedExtension, IMAGE_EXTENSION)) {
				throw new InvalidExtensionException.InvalidImageExtensionException(allowedExtension, extension,
						filename);
			} else if (Arrays.equals(allowedExtension, FLASH_EXTENSION)) {
				throw new InvalidExtensionException.InvalidFlashExtensionException(allowedExtension, extension,
						filename);
			} else if (Arrays.equals(allowedExtension, MEDIA_EXTENSION)) {
				throw new InvalidExtensionException.InvalidMediaExtensionException(allowedExtension, extension,
						filename);
			} else {
				throw new InvalidExtensionException(allowedExtension, extension, filename);
			}
		}

		long size = file.getSize();
		if (maxSize != -1 && size > maxSize) {
			throw new FileSizeLimitExceededException("not allowed upload", size, maxSize);
		}
	}

	/**
	 * 判断MIME类型是否是允许的MIME类型
	 *
	 * @param extension
	 * @param allowedExtension
	 * @return
	 */
	private boolean isAllowedExtension(String extension, String[] allowedExtension) {
		for (String str : allowedExtension) {
			if (str.equalsIgnoreCase(extension)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 提取上传的文件名
	 * 
	 * @param file
	 *            上传的文件
	 * @param baseDir
	 *            基路径
	 * @param needDatePathAndRandomName
	 *            是否需要加上时间路径
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String extractFilename(MultipartFile file, String baseDir, boolean needDatePathAndRandomName)
			throws UnsupportedEncodingException {
		String filename = file.getOriginalFilename();
		int slashIndex = filename.indexOf("/");
		if (slashIndex >= 0) {
			filename = filename.substring(slashIndex + 1);
		}
		if (needDatePathAndRandomName) {
			filename = baseDir + File.separator + datePath() + File.separator + encodingFilename(filename);
		} else {
			filename = baseDir + File.separator + filename;
		}
		return filename;
	}

	/**
	 * 日期路径 即年/月/日 如2013/01/03
	 *
	 * @return
	 */
	private String datePath() {
		Date now = new Date();
		return DateFormatUtils.format(now, "yyyy/MM/dd");
	}

	/**
	 * 编码文件名,默认格式为：hex(md5(filename + now nano time + counter++))
	 *
	 * @param filename
	 * @return
	 */
	private String encodingFilename(String filename) {
		filename = Md5Utils.hash(filename + System.nanoTime() + counter++)+"."+FilenameUtils.getExtension(filename);
		return filename;
	}
}
