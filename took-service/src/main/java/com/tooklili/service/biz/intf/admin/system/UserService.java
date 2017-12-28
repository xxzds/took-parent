package com.tooklili.service.biz.intf.admin.system;

import com.tooklili.model.admin.SysUser;
import com.tooklili.util.result.BaseResult;
import com.tooklili.util.result.ListResult;
import com.tooklili.util.result.PageResult;
import com.tooklili.util.result.PlainResult;

/**
 * 用户服务
 * @author shuai.ding
 *
 * @date 2017年11月21日下午5:43:43
 */
public interface UserService {
	
	/**
	 * 通过用户名、密码查询用户
	 * @author shuai.ding
	 * @param userName    用户名
	 * @param password    未加密的密码
	 * @return
	 */
	public PlainResult<SysUser> findUserByUsernameAndPassword(String userName,String password);
	
	/**
	 * 生成记住我cookie值   [base64(用户名-md5(md5(用户名+加密后的密码+盐值)))]
	 * @author shuai.ding
	 * @param userName     用户名
	 * @param password     加密后的密码
	 * @param salt         盐值
	 * @return
	 */
	public PlainResult<String> generatorCookieValueAboutRememberMe(String userName,String password,String salt);	
	
	/**
	 * 校验存入客户端的cookie是否有效
	 * @author shuai.ding
	 * @param cookieValue
	 * @return
	 */
	public PlainResult<SysUser> validRememberMeCookieKey(String cookieValue);

	/**
	 * 分页查询用户列表
	 * @author shuai.ding
	 * @param user
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public PageResult<SysUser> findUsers(SysUser user,Integer currentPage,Integer pageSize);
	
	/**
	 * 查询用户列表
	 * @author shuai.ding
	 * @param user
	 * @return
	 */
	public ListResult<SysUser> findUser(SysUser user);
	
	/**
	 * 添加用户
	 * @author shuai.ding
	 * @param user
	 * @return  返回主键
	 */
	public PlainResult<Long> addUser(SysUser user);
	
	/**
	 * 添加用户和用户角色关联关系
	 * @author shuai.ding
	 * @param user
	 * @param roleId
	 * @return
	 */
	public BaseResult addUserAndRole(SysUser user,Long roleId);
	
	/**
	 * 修改用户信息
	 * @author shuai.ding
	 * @param user
	 * @return
	 */
	public BaseResult editUser(SysUser user);
	
	/**
	 * 修改用户信息和用户角色关联关系
	 * @author shuai.ding
	 * @param user
	 * @param roleId
	 * @return
	 */
	public BaseResult editUserAndRole(SysUser user,Long roleId);
	
	/**
	 * 逻辑删除用户
	 * @author shuai.ding
	 * @param id
	 * @return
	 */
	public BaseResult logicDelUser(Long id);
	
	/**
	 * 重置用户密码
	 * @author shuai.ding
	 * @param id    用户id
	 * @return
	 */
	public BaseResult defaultUserPwd(Long id);
	
	/**
	 * 修改密码
	 * @author shuai.ding
	 * @param userId       用户id
	 * @param oldPwd       原密码
	 * @param newPwd       新密码
	 * @param confirmPwd   确认密码
	 * @return
	 */
	public BaseResult modifyPassword(Long userId,String oldPwd,String newPwd,String confirmPwd);

}
