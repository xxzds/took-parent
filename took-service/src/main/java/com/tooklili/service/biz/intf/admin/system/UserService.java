package com.tooklili.service.biz.intf.admin.system;

import com.tooklili.model.admin.SysUser;
import com.tooklili.util.result.BaseResult;
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
	 * @param password    密码
	 * @return
	 */
	public PlainResult<SysUser> findUserByUsernameAndPassword(String userName,String password);
	

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
	 * 添加用户
	 * @author shuai.ding
	 * @param user
	 * @return  返回主键
	 */
	public PlainResult<Long> addUser(SysUser user);
	
	/**
	 * 修改用户信息
	 * @author shuai.ding
	 * @param user
	 * @return
	 */
	public BaseResult editUser(SysUser user);
	
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

}
