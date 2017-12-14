package com.tooklili.model.admin;

import java.io.Serializable;
import java.util.Date;

import com.tooklili.enums.admin.UserStatusEnum;
import com.tooklili.util.DateUtil;

/**
 * 用户实体
 * @author shuai.ding
 * @date 2017年8月26日上午11:57:46
 */
public class SysUser implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private Long id; 
	
	/**
	 * 用户名
	 */
	private String userName;
	
	/**
	 * 密码
	 */
	private String userPassword;
	
	/**
	 * 盐
	 */
	private String userSalt;
	
	/**
	 * 手机号码
	 */
	private String userPhone;
	
	/**
	 * 电子邮箱
	 */
	private String userEmail;
	
	/**
	 * 创建时间
	 */
	private Date userCreateTime;
	
	/**
	 * 修改时间
	 */
	private Date userEditTime;
	
	/**
	 * 用户状态 normal-正常状态  blocked-封禁状态
	 */
	private UserStatusEnum userStatus;
	
	/**
	 * 用户是否删除 0-未删除   1-删除
	 */
	private Integer userDeleted;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserSalt() {
		return userSalt;
	}

	public void setUserSalt(String userSalt) {
		this.userSalt = userSalt;
	}

	public Date getUserCreateTime() {
		return userCreateTime;
	}
	
	/**
	 * 将日期对象转化成字符串
	 * @author shuai.ding
	 * @return
	 */
	public String getUserCreateTimeStr(){
		if(this.userCreateTime==null){
			return null;
		}
		return DateUtil.formatDate(this.userCreateTime);
	}

	public void setUserCreateTime(Date userCreateTime) {
		this.userCreateTime = userCreateTime;
	}

	public UserStatusEnum getUserStatus() {
		return userStatus;
	}
	
	/**
	 * 将状态变成对应的汉字
	 * @author shuai.ding
	 * @return
	 */
	public String getUserStatusStr(){
		if(this.userStatus==null){
			return null;
		}
		return this.userStatus.getInfo();
	}

	public void setUserStatus(UserStatusEnum userStatus) {
		this.userStatus = userStatus;
	}

	public Date getUserEditTime() {
		return userEditTime;
	}

	public void setUserEditTime(Date userEditTime) {
		this.userEditTime = userEditTime;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public Integer getUserDeleted() {
		return userDeleted;
	}

	public void setUserDeleted(Integer userDeleted) {
		this.userDeleted = userDeleted;
	}

	@Override
	public String toString() {
		return "SysUser [id=" + id + ", userName=" + userName + ", userPassword=" + userPassword + ", userSalt="
				+ userSalt + ", userPhone=" + userPhone + ", userEmail=" + userEmail + ", userCreateTime="
				+ userCreateTime + ", userEditTime=" + userEditTime + ", userStatus=" + userStatus + ", userDeleted="
				+ userDeleted + "]";
	}
}
