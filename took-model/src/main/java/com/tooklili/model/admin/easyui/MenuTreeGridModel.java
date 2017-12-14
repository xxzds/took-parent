package com.tooklili.model.admin.easyui;

import java.util.Date;

/**
 * 树型表格的模型数据
 * @author shuai.ding
 *
 * @date 2017年12月11日下午6:04:22
 */
public class MenuTreeGridModel {
	
	/**
	 * 主键
	 */
	private Long id;
	
	/**
	 * 图标(easyui 字段匹配)
	 */
	private String iconCls;
	
	/**
	 * 父节点id(easyui 字段匹配)
	 */
	private Long parentId;
	
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
	
	/**
	 * closed 代表有子节点  open代表没有子节点(easyui 字段匹配)
	 */
	private String state;
	
	/**
	 * 排序位置
	 */
	private Integer menuSort;
	
	/**
	 * 是否可见 0-不可见 1-可见
	 */
	private Integer menuVisible;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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
}
