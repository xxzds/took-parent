package com.tooklili.model.admin;

import java.util.Date;

/**
 * 菜单
 * @author shuai.ding
 * @date 2017年8月26日下午3:51:11
 */
public class SysMenu {

	/**
	 * 主键
	 */
	private Long id;
	
	/**
	 * 菜单名称
	 */
	private String menuName;
	
	/**
	 * 菜单标识
	 */
	private String menuIdentify;
	
	/**
	 * 菜单url
	 */
	private String menuUrl;
	
	/**
	 * 父级菜单编号
	 */
	private Long menuParentId;
	
	/**
	 * 图标名称
	 */
	private String menuIcon;
	
	/**
	 * 排序位置
	 */
	private Integer menuSort;
	
	/**
	 * 是否可见 0-不可见 1-可见
	 */
	private Integer menuVisible;
	
	/**
	 * 备注
	 */
	private String menuRemark;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 修改时间
	 */
	private Date modifyTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuIdentify() {
		return menuIdentify;
	}

	public void setMenuIdentify(String menuIdentify) {
		this.menuIdentify = menuIdentify;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public Long getMenuParentId() {
		return menuParentId;
	}

	public void setMenuParentId(Long menuParentId) {
		this.menuParentId = menuParentId;
	}

	public String getMenuIcon() {
		return menuIcon;
	}

	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}

	public Integer getMenuSort() {
		return menuSort;
	}

	public void setMenuSort(Integer menuSort) {
		this.menuSort = menuSort;
	}

	public Integer getMenuVisible() {
		return menuVisible;
	}

	public void setMenuVisible(Integer menuVisible) {
		this.menuVisible = menuVisible;
	}

	public String getMenuRemark() {
		return menuRemark;
	}

	public void setMenuRemark(String menuRemark) {
		this.menuRemark = menuRemark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj==null || ! (obj instanceof SysMenu)){
			return false;
		}
		SysMenu sysMenu = (SysMenu)obj;
		if(this.id ==sysMenu.id){
			return true;
		}	
		return false;
	}
	
	@Override
	public int hashCode() {
		return this.getId()!=null ? this.getId().intValue():0;
	}

	@Override
	public String toString() {
		return "SysMenu [id=" + id + ", menuName=" + menuName + ", menuIdentify=" + menuIdentify + ", menuUrl="
				+ menuUrl + ", menuParentId=" + menuParentId + ", menuIcon=" + menuIcon + ", menuSort=" + menuSort
				+ ", menuVisible=" + menuVisible + ", menuRemark=" + menuRemark + ", createTime=" + createTime
				+ ", modifyTime=" + modifyTime + "]";
	}
}
