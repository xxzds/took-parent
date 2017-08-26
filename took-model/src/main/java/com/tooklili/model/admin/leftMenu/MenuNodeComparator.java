package com.tooklili.model.admin.leftMenu;

import java.util.Comparator;

public class MenuNodeComparator implements Comparator<MenuNode>{

	@Override
	public int compare(MenuNode o1, MenuNode o2) {
		int sortnum1 = o1.getAttributes().getSortnum();
		int sortnum2 = o2.getAttributes().getSortnum();
		return sortnum1 < sortnum2 ? 1 :(sortnum1 == sortnum2 ? 0 :-1);
	}

}
