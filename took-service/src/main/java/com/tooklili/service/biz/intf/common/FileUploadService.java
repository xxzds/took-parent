package com.tooklili.service.biz.intf.common;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.tooklili.util.result.PlainResult;

/**
 * 文件上传服务
 * 
 * @author ding.shuai
 * @date 2016年8月17日上午9:08:37
 */
public interface FileUploadService {

	/**
	 * 上传文件
	 * 
	 * @param file 上传的文件
	 * @return
	 */
	public PlainResult<String> uploadFile(MultipartFile file);	
	
	/**
	 * 上传文件
	 * @param file 上传的文件
	 * @param allowedExtension 允许的文件类型 null 表示允许所有
	 * @return
	 */
	public PlainResult<String> uploadFile(MultipartFile file, String[] allowedExtension);
	
	/**
	 * 上传文件
	 * @param file 上传的文件
	 * @param allowedExtension  允许的文件类型 null 表示允许所有
	 * @param maxSize  最大上传的大小 -1 表示不限制
	 * @return
	 */
	public PlainResult<String> uploadFile(MultipartFile file, String[] allowedExtension, long maxSize);

	/**
	 * 上传文件
	 * @param file  上传的文件
	 * @param allowedExtension  允许的文件类型 null 表示允许所有
	 * @param maxSize  最大上传的大小 -1 表示不限制
	 * @param needDatePathAndRandomName  是否需要日期目录和随机文件名前缀
	 * @return
	 */
	public PlainResult<String> uploadFile(MultipartFile file, String[] allowedExtension, long maxSize,
			boolean needDatePathAndRandomName);
	/**
	 * 上传文件
	 * @param file  上传的文件
	 * @param baseDir  相对应用的基目录
	 * @param allowedExtension  允许的文件类型 null 表示允许所有
	 * @param maxSize   最大上传的大小 -1 表示不限制
	 * @param needDatePathAndRandomName  是否需要日期目录和随机文件名前缀
	 * @return
	 */
	public PlainResult<String> uploadFile(MultipartFile file, String baseDir, String[] allowedExtension, long maxSize,
			boolean needDatePathAndRandomName);
	
	/**
	 * 删除文件
	 * @param filename   文件路径
	 * @throws IOException
	 */
	public void delete(String filename) throws IOException;

}
