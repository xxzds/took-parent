package com.tooklili.service.biz.impl.admin.system;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.tooklili.convert.admin.SysMenuConverter;
import com.tooklili.dao.intf.admin.SysMenuDao;
import com.tooklili.dao.intf.admin.SysRoleMenuDao;
import com.tooklili.model.admin.SysMenu;
import com.tooklili.model.admin.SysRoleMenu;
import com.tooklili.model.admin.easyui.MenuTreeGridModel;
import com.tooklili.model.admin.leftMenu.MenuNode;
import com.tooklili.service.biz.intf.admin.system.MenuService;
import com.tooklili.service.exception.BusinessException;
import com.tooklili.util.result.BaseResult;
import com.tooklili.util.result.ListResult;

/**
 * 菜单服务
 * @author shuai.ding
 * @date 2017年8月27日下午3:41:06
 */
@Service
public class MenuServiceImpl implements MenuService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MenuServiceImpl.class);
	
	private static Long ROOT_ID    = 0L;
	
	@Resource
	private SysMenuDao sysMenuDao;
	
	@Resource
	private SysRoleMenuDao sysRoleMenuDao;

	@Override
	public List<MenuNode> getMenu(Long userId) {
		List<MenuNode> MenuNodeList =Lists.newArrayList();

		List<SysMenu> sysMenuList = Lists.newArrayList();
		if(userId==null){
			sysMenuList =  sysMenuDao.find(null);
		}else{
			List<SysMenu> childMenus = sysRoleMenuDao.queryMenuByUserId(userId);
			
			Map<Long,SysMenu> childMenuMaps = Maps.newHashMap();
			for(SysMenu menu:childMenus){
				childMenuMaps.put(menu.getId(), menu);
			}
			
			Set<SysMenu> parentMenu = Sets.newHashSet();
			this.getParentMenu(parentMenu, childMenuMaps);
			
			//父级菜单包含子菜单(此处可以清除重复的数据)
			parentMenu.addAll(childMenus);
			sysMenuList.addAll(parentMenu);
		}
		
		
		if(sysMenuList!=null){
			MenuNodeList = Lists.transform(sysMenuList, new Function<SysMenu, MenuNode>() {

				@Override
				public MenuNode apply(SysMenu input) {
					
					return SysMenuConverter.toMenuNode(input);
				}
			});
		}
		return MenuNode.buildMenu(MenuNodeList).getChildren();
	}
	
	
	/**
	 * 通过子节点，查询所有父节点集合
	 * @author shuai.ding
	 * @param menus        父节点集合
	 * @param childMenus   子节点集合
	 * @return
	 */
	private Set<SysMenu> getParentMenu(Set<SysMenu> menus,Map<Long,SysMenu> childMenus){
		
		Map<Long,SysMenu> parentMenus = Maps.newHashMap();
		
		for(SysMenu sysMenu:childMenus.values()){
			Long parentId = sysMenu.getMenuParentId();
			if(parentId != ROOT_ID && parentMenus.get(parentId)==null){				
				SysMenu menu = new SysMenu();
				menu.setId(parentId);
				List<SysMenu> sysMenus = sysMenuDao.find(menu);
				parentMenus.put(sysMenus.get(0).getId(), sysMenus.get(0));
			}			
		}		
		if(parentMenus.size()>0){
			menus.addAll(parentMenus.values());
			getParentMenu(menus, parentMenus);
		}
		return menus;
	}
	
	
	@Override
	public List<MenuNode> getMenuByRoleId(final Long roleId){
		if(roleId==null){
			return getMenu(null);
		}		
		
		List<MenuNode> MenuNodeList =Lists.newArrayList();
		
		List<SysMenu> sysMenuList =  sysMenuDao.find(null);
		
		if(sysMenuList!=null){
			MenuNodeList = Lists.transform(sysMenuList, new Function<SysMenu, MenuNode>() {

				@Override
				public MenuNode apply(SysMenu input) {			
					MenuNode menuNode =  SysMenuConverter.toMenuNode(input);
					
					//判断菜单是否被选中，如果选中，记录角色-菜单id
					SysRoleMenu sysRoleMenu = new SysRoleMenu();
					sysRoleMenu.setRoleId(roleId);
					sysRoleMenu.setMenuId(input.getId());
					List<SysRoleMenu> sysRoleMenus = sysRoleMenuDao.find(sysRoleMenu);
					if(sysRoleMenus != null && sysRoleMenus.size()>0){		
						menuNode.setChecked(true);
						menuNode.getAttributes().setRoleMenuId(sysRoleMenus.get(0).getId());
					}else{
						menuNode.setChecked(false); 
					}
					
				    return menuNode;
				}
			});
		}
		return MenuNode.buildMenu(MenuNodeList).getChildren();
	}

	@Override
	public ListResult<MenuTreeGridModel> getMenuTree(Long pid) {
		ListResult<MenuTreeGridModel> result = new ListResult<MenuTreeGridModel>();
		
		SysMenu sysMenu = new SysMenu();
		sysMenu.setMenuParentId(pid);
		List<SysMenu> sysMenus = sysMenuDao.findMenuAndOrder(sysMenu, Order.formString("menu_sort asc"));
		
		if(sysMenus!=null){
			List<MenuTreeGridModel> menuTreeGridModels = Lists.transform(sysMenus, new Function<SysMenu,MenuTreeGridModel>(){

				@Override
				public MenuTreeGridModel apply(SysMenu input) {
					MenuTreeGridModel menuTreeGridModel = SysMenuConverter.toMenuTreeGridModel(input);
					//设置是否是子节点
					if(input.getMenuParentId()==0L){
						menuTreeGridModel.setState("closed");
					}else{
						menuTreeGridModel.setState(hasChild(input.getId()) ? "closed" :"open");
					}
					
					return menuTreeGridModel;
				}				
			});
			result.setData(menuTreeGridModels);
		}	
		return result;
	}
	
	/**
	 * 判断是否有子节点
	 * @author shuai.ding
	 * @param pid
	 * @return
	 */
	private boolean hasChild(Long pid){
		int count = sysMenuDao.getCountByPid(pid);
		return count > 0 ?true:false;
	}

	@Override
	public BaseResult addMenu(SysMenu sysMenu) {
		if(sysMenu==null){
			throw new BusinessException("菜单数据不能为空");
		}
		BaseResult result = new BaseResult();
		
		//创建时间
		if(sysMenu.getCreateTime()==null){
			sysMenu.setCreateTime(new Date());
		}
				
		long count = sysMenuDao.insert(sysMenu);
		if(count<=0){
			throw new BusinessException("菜单插入数据失败");
		}
		return result;
	}

	@Override
	public BaseResult modifyMenu(SysMenu sysMenu) {
		BaseResult result = new BaseResult();
		
		if(sysMenu==null){
			throw new BusinessException("修改的菜单数据不能为空");
		}
		if(sysMenu.getId()==null){
			throw new BusinessException("修改菜单数据的主键不能为空");
		}
		
		//修改时间
		if(sysMenu.getModifyTime()==null){
			sysMenu.setModifyTime(new Date());
		}
		
		long count = sysMenuDao.updateByIdSelective(sysMenu);
		if(count<=0){
			LOGGER.warn("菜单数据{}更新失败",sysMenu.toString());
			throw new BusinessException("主键为"+sysMenu.getId()+"的数据更新失败");
		}
		
		return result;
	}

	@Override
	@Transactional
	public BaseResult delMenu(Long id) {
		BaseResult result = new BaseResult();
		
		if(id==null){
			return result.setErrorMessage("删除的菜单id不能为空");
		}
		
		//查询子节点
		SysMenu sysMenu = new SysMenu();
		sysMenu.setMenuParentId(id);
		List<SysMenu> menuSons =  sysMenuDao.find(sysMenu);
		
		if(menuSons!=null && menuSons.size()>0){
			List<Long> ids = Lists.transform(menuSons, new Function<SysMenu, Long>() {

				@Override
				public Long apply(SysMenu input) {
					return input.getId();
				}
			});
			
			//批量删除所有子节点
			sysMenuDao.batchDeleteByIds(ids.toArray(new Long[]{}));
		}
		
		//删除当前节点
		sysMenuDao.deleteById(id);		
		return result;
	}

}
