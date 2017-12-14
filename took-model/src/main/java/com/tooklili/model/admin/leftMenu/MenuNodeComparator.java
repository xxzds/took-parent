package com.tooklili.model.admin.leftMenu;

import java.util.Comparator;

/**
 * 菜单排序，同一层级下的节点，按升序排列
 * @author shuai.ding
 * @date 2017年8月27日下午3:33:44
 */
public class MenuNodeComparator implements Comparator<MenuNode>{

	@Override
	public int compare(MenuNode o1, MenuNode o2) {
		int sortnum1 = o1.getAttributes().getSortnum();
		int sortnum2 = o2.getAttributes().getSortnum();
		return sortnum1 > sortnum2 ? 1 :(sortnum1 == sortnum2 ? 0 :-1);
	}

}
