package com.tooklili.service.admin.took;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tooklili.model.tooklili.Item;
import com.tooklili.service.biz.intf.admin.took.ItemNineService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-service-admin.xml"})
@ActiveProfiles("development")
public class ItemNineServiceTest {

	@Autowired
	private ItemNineService itemNineService;
	
	@Test
	public void insertItemNineTest() {
		Item item = new Item();		
		itemNineService.insertItemNine(item);
	}
}
