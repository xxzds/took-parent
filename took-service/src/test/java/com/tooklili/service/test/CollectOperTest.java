package com.tooklili.service.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

/**
 * 集合，交集、并集、差集操作
 * @author shuai.ding
 *
 * @date 2017年12月18日下午1:52:14
 */
public class CollectOperTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(CollectOperTest.class);

	@Test
	public void oper(){
		List<String> list1 =new ArrayList<String>();
		list1.add("A");
		list1.add("B");

		List<String> list2 =new ArrayList<String>();
		list2.add("B");
		list2.add("C");
		
		//并集
//		list1.addAll(list2);
//		LOGGER.info(list1.toString());
//		LOGGER.info(list2.toString());
		
		//无重复的并集
//		list2.removeAll(list1);
//		LOGGER.info(list2.toString());
//		
//		list1.addAll(list2);
//		LOGGER.info(list1.toString());
		
		//交集
//		list1.retainAll(list2);
//		LOGGER.info(list1.toString());
		
		//差集
//		list1.removeAll(list2);
//		LOGGER.info(list1.toString());
		
		
		List<String> temp = Lists.newArrayList();
		temp.addAll(list1);
		temp.removeAll(list2);
		LOGGER.info(list1.toString());
		LOGGER.info(temp.toString());
		
	}
}
