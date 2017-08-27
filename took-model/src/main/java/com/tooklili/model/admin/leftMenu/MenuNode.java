package com.tooklili.model.admin.leftMenu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * easyui 树型菜单实体
 * @author shuai.ding
 * @date 2017年8月26日下午5:30:05
 */
public class MenuNode {
	 private static Long ROOT_ID    = 0L;
	/**
	 * 节点ID
	 */
	private Long id;	
	/**
	 * 显示节点文本
	 */
	private String text;
	
	/**
	 * 图标
	 */
	private String iconCls;
	
	/**
	 * 节点状态，'open' 或 'closed'，默认：'open'。如果为'closed'的时候，将不自动展开该节点。
	 */
	private String state;
	
	/**
	 * 表示该节点是否被选中
	 */
	private Boolean checked;
	
	/**
	 *  被添加到节点的自定义属性
	 */
	private  NodeAttribute attributes = new NodeAttribute();
	
	/**
	 * 子节点
	 */
	private List<MenuNode> children;
	
	
	/**
	 * 构建树型菜单
	 * @author shuai.ding
	 * @param list        普通的list集合
	 * @return
	 */
	public static MenuNode buildMenu(List<MenuNode> list){
		MenuNode root = new MenuNode();
        root.setId(ROOT_ID);
        return recursiveBuild(root, list);
	}
	
	
	/**
	 * 使用递归构建树
	 * @author shuai.ding
	 * @param root        树型对象
	 * @param list        普通的list集合
	 * @return
	 */
	private static MenuNode recursiveBuild(MenuNode root, List<MenuNode> list){
		List<MenuNode> children = new ArrayList<MenuNode>();
		
		for(MenuNode menuNode:list){
			if(menuNode.getAttributes().getParentid()==root.getId()){
				children.add(menuNode);
			}
		}
		//排序
		 Collections.sort(children, new MenuNodeComparator());
		 root.setChildren(children);
		 
		 //移除
		 list.removeAll(children);
		 
		 //是否是叶子节点
		 if(children.size()==0){
			 root.getAttributes().setIsLeaf(true);
		 }else{
			 root.getAttributes().setIsLeaf(false);
		 }
		 
		 for(MenuNode item:children){
			 recursiveBuild(item, list);
		 }
		
		return root;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public NodeAttribute getAttributes() {
		return attributes;
	}

	public void setAttributes(NodeAttribute attributes) {
		this.attributes = attributes;
	}

	public List<MenuNode> getChildren() {
		return children;
	}

	public void setChildren(List<MenuNode> children) {
		this.children = children;
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	
	/**
	 * 重写equals方法，当两个对象的id相等，就返回true
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof MenuNode){
			MenuNode menuNode = (MenuNode)obj;
			return menuNode.getId() == this.getId();
		}	
		return false;
	}
}
