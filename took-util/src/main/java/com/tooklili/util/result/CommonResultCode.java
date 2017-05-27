package com.tooklili.util.result;

import java.io.Serializable;

/**
 * 状态码
 * @author shuai.ding
 *
 * @date 2017年2月3日下午4:57:14
 */
public enum CommonResultCode implements Serializable{
	
	//成功
	SUCCESS(0,""),
	
	//token
	TOKEN_DISABLED(10001,"token不可用，请重新登录"),	
	USERID_EMPTY(10002,"userId不能为空"),
	TOKEN_EMPTY(10003,"token不能为空"),
	
	//version
	VERSION_DIFFER(10004,"版本号不一致"),
	VERSION_EMPTY(10005,"版本号不能为空"),
	
	// 参数不合法
    ILEEGAL_ARGUMENT(10006, "参数不合法"),
    ILLEGAL_PARAM(10007, "%s"),
    
    //用户模块
    USER_NOT_EXITST(10010,"用户不存在"),
  	USER_ERROR_USERNAMEORPWD(10011,"用户名或密码错误,请重新输入"),
  	USER_NOTBELONG_MOBILEDOT(10012,"此用户不属于移动网点用户"),
  	USER_EQUIPMENT_MISMATCH(10013,"用户与设备不匹配"),
  	USER_NOT_DOT(10014,"用户非网点用户！"),
  	
  	//充值
  	RECHARGE_NO_APPLY(10020,"充值订单不存在"),
  	RECHARGE_CONFIRMING(10021,"充值确认中"),
  	RECHARGE_FAIL(10022,"充值失败"),
  	
  	//慧易通账户模块
  	YC_VALID_PROMPT(10030,"您的易借条账户%s已经欠款%s元，请先支付欠款，再进行慧易通账户预存。"),
  	YC_FAIL(10031,"预存失败"),
  	

   //有关操作账户时错误
  	ERROR_ON_KFWEBSERVICE(10040,"调用账务系统时发生错误"),
  	ILLEGAE_ACCOUNTNAME(10041,"账户名已存在"),
  	UNCOMPLETE_PARAM(10042,"参数不完整"),
	ACCOUNTLIST_NULL(10043,"账户列表为空"),
	ERROR_ON_UPLOAD(10044,"文件上传失败"),
	ILLEGAE_PIC_EXT(10045,"上传文件扩展名是不允许的扩展名,只允许gif,jpg,jpeg,png,bmp格式"),
	
	//卡片模块
  	NO_HYTACCOUNT(10050,"关联不到慧易通账户信息！"),
  	NO_NORMAL_HYTACCOUNT(10051,"所属慧易通账户处于非正常状态！"),
  	XHAPPLY_HYTACCOUNT(10052,"所属慧易通账户已提交销户申请！"),
  	CARD_ISSUED(10053,"该卡号已发行，不能重复发行！"),
  	CARD_NOT_EXIST(10054,"卡片库不存在该卡，不能发行！"),
  	CARD_NOT_IN_DOT(10055,"该卡尚未配发到网点，不能发行！"),
  	CARD_NOT_BELONG_DOT(10056,"卡片库该卡不属于‘%s’网点，不能发行！"),
  	Card_ISSUE_FAILURE(10057,"卡片%s发行失败,%s,请点击解卡按钮！"),
  	Card_ISSUE_FAILURE2(10058,"卡片%s发行失败,请点击解卡按钮！"),
  	HYTACCOUNT_LOCK_BALANCE(10059,"账户余额不足！"),
  	
  	//库存
  	REPERTORY_NOT_SAME(10060,"领用数发生变化,请刷新页面"),
  	
  	//申请易借条模块
  	APPLY_YJT_NOT_WTACCOUNT(10070,"该手机号未注册慧易通网厅用户，请先通过慧易通网厅/APP/微信进行注册，再申请开通ETC借条服务"),
  	APPLY_YJT_EXIST_YJTACCOUNT(10071,"有待审核/审核通过的账户,请您耐心等待！"),
  	APPLY_YJT_MOBILE_FAIL(10072,"判断手机号注册情况失败！"),
  	APPLY_YJT_RELEVANCE_HLQB_FAIL(10073,"通过手机号查询联钱包账户信息失败！"),
  	APPLY_YJT_FAIL(10074,"慧易贷申请失败！"),
  	APPLY_YJT_EXIST(10075,"已经存在慧易贷借条！"),
  	
  	//卡校验、加密
  	CARD_AVLID_FILE(10080,"获取加密后的数据失败,请重新获取"),
  	
	//异常
	EXCEPTION_UNKNOWN(-1,"未知异常"),
	EXCEPTION(500,"异常:%s");

	 private final Integer code;
	 private final String message;
	 
	 CommonResultCode(Integer code, String msg) {
	        this.code = code;
	        this.message = msg;
	 }

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
