package com.tooklili.dao.db.intf.tooklili;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tooklili.model.tooklili.Item;
import com.tooklili.model.tooklili.ItemModel;

/**
 * 商品持久层
 * @author ding.shuai
 * @date 2017年9月19日下午9:34:21
 */
public interface ItemDao {
	
	/**
	 * 通过分类id查询有效的商品集合
	 * @author shuai.ding
	 * @param cateId
	 * @return
	 */
	public List<Item> queryItemsByCateId(@Param("cateId") Integer cateId);
	
//	public PageList<Item> queryItems(@Param("item")Item item,@Param("pageBounds")PageBounds pageBounds);
	
	/**
	 * 通过id查询商品信息
	 * @author shuai.ding
	 * @param id
	 * @return
	 */
	public Item queryItemById(Long id);
	
	/**
	 * 插入商品
	 * @author shuai.ding
	 * @param itemModel
	 * @return  返回主键
	 */
	public Long insertItem(ItemModel itemModel);
	
	/**
	 * 通过商品id查询商品信息
	 * @author shuai.ding
	 * @param numId
	 * @return
	 */
	public Item queryItemBynumId(Long numId);
	
	/**
	 * 通过主键id更新商品信息
	 * @author shuai.ding
	 * @param itemModel
	 * @return
	 */
	public Long updateItemById(ItemModel itemModel);
	
	/**
	 * 更新商品的pic_url,使url带上前缀'http:'
	 * @author shuai.ding
	 * @return
	 */
	public Long updateItemPicUrl();
	
	/**
	 * 删除过期商品
	 * @author shuai.ding
	 * @return
	 */
	public long deleteExpiredItem();
}
