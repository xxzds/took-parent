package com.tooklili.service.biz.impl.common.exception;

import org.apache.commons.fileupload.FileUploadException;

/**
 * 文件名超长异常
 * @author ding.shuai
 * @date 2016年9月21日上午11:42:13
 */
public class FileNameLengthLimitExceededException extends FileUploadException {

	private static final long serialVersionUID = 1L;
	private int length;
    private int maxLength;
    private String filename;

    public FileNameLengthLimitExceededException(String filename, int length, int maxLength) {
        super("file name : [" + filename + "], length : [" + length + "], max length : [" + maxLength + "]");
        this.length = length;
        this.maxLength = maxLength;
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public int getLength() {
        return length;
    }


    public int getMaxLength() {
        return maxLength;
    }

}
