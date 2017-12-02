package com.tooklili.service.biz.impl.admin.system;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tooklili.dao.intf.admin.SysUserDao;
import com.tooklili.enums.admin.UserDelEnum;
import com.tooklili.enums.admin.UserStatusEnum;
import com.tooklili.model.admin.SysUser;
import com.tooklili.service.biz.intf.admin.system.UserService;
import com.tooklili.service.exception.BusinessException;
import com.tooklili.service.util.MessageUtils;
import com.tooklili.util.UUIDUtils;
import com.tooklili.util.result.BaseResult;
import com.tooklili.util.result.ListResult;
import com.tooklili.util.result.PageResult;
import com.tooklili.util.result.PlainResult;
import com.tooklili.util.security.Md5Utils;

/**
 * 用户服务
 * @author shuai.ding
 * @date 2017年11月21日下午5:44:10
 */
@Service
public class UserServiceImpl implements UserService{
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Resource
	private SysUserDao sysUserDao;

	@Override
	public PlainResult<SysUser> findUserByUsernameAndPassword(String userName, String password) {
		PlainResult<SysUser> result = new PlainResult<SysUser>();
		
		if(StringUtils.isEmpty(userName)){
			return result.setErrorMessage(MessageUtils.message("login.no.username"));
		}
		
		if(StringUtils.isEmpty(password)){
			return result.setErrorMessage(MessageUtils.message("login.no.password"));
		}
		
		SysUser sysUser = new SysUser();
		sysUser.setUserName(userName);
		List<SysUser> sysUsers = sysUserDao.find(sysUser);
		
		if(sysUser==null || sysUsers.size()==0){
			return result.setErrorMessage(MessageUtils.message("login.error"));
		}
		
		sysUser = sysUsers.get(0);
		
		//判断用户是否逻辑删除
		if(sysUser.getUserDeleted()==UserDelEnum.USER_DEL.getCode()){
			return result.setErrorMessage(MessageUtils.message("login.logic.del"));
		}
		
		//判断用户是否禁用
		if(sysUser.getUserStatus()==UserStatusEnum.blocked){
			return result.setErrorMessage(MessageUtils.message("login.forbade"));
		}
		
		//判断密码是否正确
		if(!matches(sysUser, password)){
			return result.setErrorMessage(MessageUtils.message("login.error"));			
		}
		
		result.setData(sysUser);
		return result;
	}
	
	/**
	 * 密码是否匹配
	 * @author shuai.ding
	 * @param sysUser       用户
	 * @param newPassword   用户的明文密码
	 * @return
	 */
	private boolean matches(SysUser sysUser,String newPassword){		
		return sysUser.getUserPassword().equals(encryptPassword(sysUser.getUserName(), newPassword, sysUser.getUserSalt()));
	}
	
	/**
	 * 密码加密
	 * @author shuai.ding
	 * @param username    用户名
	 * @param password    明文密码
	 * @param salt        盐值
	 * @return
	 */
	private String encryptPassword(String username, String password, String salt){
		return Md5Utils.hash(username+password+salt);
		
	}

	@Override
	public PageResult<SysUser> findUsers(SysUser user,Integer currentPage,Integer pageSize) {		
		if(currentPage==null){
			currentPage=1;
		}
		if(pageSize==null){
			pageSize=20;
		}
		PageResult<SysUser> result = new PageResult<SysUser>(currentPage,pageSize);
		PageBounds pageBounds = new PageBounds(currentPage,pageSize);
		PageList<SysUser> pageList = sysUserDao.queryUsersByPage(user, pageBounds);
		
		result.setData(pageList);
		result.setTotalCount(pageList.getPaginator().getTotalCount());
		
		return result;
	}
	
	@Override
	public ListResult<SysUser> findUser(SysUser user) {
		ListResult<SysUser> result = new ListResult<SysUser>();
		List<SysUser> users = sysUserDao.find(user);
		result.setData(users);
		return result;
	}

	@Override
	public PlainResult<Long> addUser(SysUser user) {
		PlainResult<Long> result = new PlainResult<Long>();
		if(user==null){
			throw new BusinessException("添加的用户信息不能为空");
		}
		//创建时间
		if(user.getUserCreateTime()==null){
			user.setUserCreateTime(new Date());
		}
		
		//如果没有设置密码，给定默认密码123
		if(StringUtils.isEmpty(user.getUserPassword())){
			user.setUserPassword("123");
		}
		
		String salt = UUIDUtils.generateUuid32();
		user.setUserSalt(salt);
		user.setUserPassword(encryptPassword(user.getUserName(), user.getUserPassword(), salt));
		
		//默认正常状态
		if(user.getUserStatus()==null){
			user.setUserStatus(UserStatusEnum.normal);
		}
		
		long count = sysUserDao.insertSelective(user);
		if(count<=0){
			LOGGER.info("用户信息{},添加失败",user);
			throw new BusinessException("添加用户失败");
		}
		result.setData(user.getId());
		return result;
	}

	@Override
	public BaseResult editUser(SysUser user) {
		BaseResult result = new BaseResult();
		
		if(user.getId()==null || user.getId()==0){
			return result.setErrorMessage("修改用户信息的主键不能为空");
		}
		
		//修改时间
		if(user.getUserEditTime()==null){
			user.setUserEditTime(new Date());
		}
		
		long count = sysUserDao.updateByIdSelective(user);
		if(count<=0){
			LOGGER.info("用户信息{},修改失败",user);
			throw new BusinessException("修改用户失败");
		}
		return result;
	}

	@Override
	public BaseResult logicDelUser(Long id) {
		BaseResult result = new BaseResult();
		
		if(id==null){
			return result.setErrorMessage("用户主键不能为空");
		}
		
		SysUser sysUser = new SysUser();
		sysUser.setId(id);
		//1-逻辑删除
		sysUser.setUserDeleted(UserDelEnum.USER_DEL.getCode());
		long count = sysUserDao.updateByIdSelective(sysUser);
		if(count<=0){
			LOGGER.info("主键[{}]的用户，逻辑删除失败",id);
			throw new BusinessException("逻辑删除用户失败");
		}	
		return result;
	}

	@Override
	public BaseResult defaultUserPwd(Long id) {
		BaseResult result = new BaseResult();
		if(id==null){
			return result.setErrorMessage("用户主键不能为空");
		}
		
		SysUser user = sysUserDao.findById(id);
		if(user==null){
			LOGGER.info("通过主键[{}],没有查询到用户",id);
			throw new BusinessException("没有查询到用户信息");
		}
		
		SysUser sysUser = new SysUser();
		sysUser.setId(id);
		String salt = UUIDUtils.generateUuid32();
		sysUser.setUserSalt(salt);
		sysUser.setUserPassword(encryptPassword(user.getUserName(), "123", salt));
		
		long count = sysUserDao.updateByIdSelective(sysUser);
		if(count<=0){
			LOGGER.info("主键[{}]的用户，重置密码失败",id);
			throw new BusinessException("重置密码失败");
		}		
		return result;
	}

	@Override
	public BaseResult modifyPassword(Long userId,String oldPwd, String newPwd, String confirmPwd) {
		BaseResult result = new BaseResult();
		if(StringUtils.isEmpty(oldPwd)){
			return result.setErrorMessage(MessageUtils.message("modify.no.old.password"));
		}
		if(StringUtils.isEmpty(newPwd)){
			return result.setErrorMessage(MessageUtils.message("modify.no.new.password"));
		}
		if(StringUtils.isEmpty(confirmPwd)){
			return result.setErrorMessage(MessageUtils.message("modify.no.confirm.password"));
		}
		if(!newPwd.equals(confirmPwd)){
			return result.setErrorMessage(MessageUtils.message("newpwd.and.confirmpwd.no.same"));
		}
		
		SysUser user = sysUserDao.findById(userId);
		if(user == null){
			LOGGER.info("通过用户id[{}]没有查询到此用户",userId);
			return result.setErrorMessage("没有查询到用户");
		}
		
		if(!matches(user,oldPwd)){
			return result.setErrorMessage(MessageUtils.message("modify.old.password.error"));
		}
		
		SysUser sysUser = new SysUser();
		sysUser.setId(userId);
		String salt = UUIDUtils.generateUuid32();
		sysUser.setUserSalt(salt);
		sysUser.setUserPassword(encryptPassword(user.getUserName(), newPwd, salt));
		long count = sysUserDao.updateByIdSelective(sysUser);
		if(count<=0){
			return result.setErrorMessage("修改密码失败");
		}
		return result;
	}
}
