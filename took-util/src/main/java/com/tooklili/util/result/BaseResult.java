package com.tooklili.util.result;

/**
 * 返回的基础结果
 * @author shuai.ding
 *
 * @date 2017年2月3日下午5:08:18
 */
public class BaseResult {

	/**
	 * 状态码
	 */
	private Integer resultCode;
	
	/**
	 * 错误信息
	 */
	private String resultMsg;
	
	/**
	 * 分页信息
	 */
	private PageInfoModel pageInfo;
	
	
	public BaseResult() {
		this.resultCode=CommonResultCode.SUCCESS.getCode();
		this.resultMsg=CommonResultCode.SUCCESS.getMessage();
		this.pageInfo=null;
	}
	
	/**
	 * 设置错误信息
	 * @param resultCode
	 * @param resultMsg
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <R extends BaseResult> R setErrorMessage(int resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
        return (R) this;
    }
	
	@SuppressWarnings("unchecked")
	public <R extends BaseResult> R setErrorMessage(CommonResultCode commonResultCode) {
        this.resultCode = commonResultCode.getCode();
        this.resultMsg = commonResultCode.getMessage();
        return (R) this;
    } 
	
	 @SuppressWarnings("unchecked")
	public <R extends BaseResult> R setErrorMessage(CommonResultCode commonResultCode, Object... args) {
        this.resultCode = commonResultCode.getCode();
        this.resultMsg = String.format(commonResultCode.getMessage(), args);
        return (R) this;
    }


	public Integer getResultCode() {
		return resultCode;
	}


	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}


	public String getResultMsg() {
		return resultMsg;
	}


	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}


	public PageInfoModel getPageInfo() {
		return pageInfo;
	}


	public void setPageInfo(PageInfoModel pageInfo) {
		this.pageInfo = pageInfo;
	}
}
