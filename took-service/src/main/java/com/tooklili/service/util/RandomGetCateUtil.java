package com.tooklili.service.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tooklili.enums.common.ChannelEnum;

/**
 * 获取阿里妈妈分类工具类
 * @author ding.shuai
 * @date 2018年5月19日下午4:45:43
 */
public class RandomGetCateUtil {
	//渠道集合
	private static List<ChannelEnum> channelEnums = new ArrayList<ChannelEnum>();
    //每个渠道对用的商品分类
	private static Map<String, List<String>> cates = new HashMap<String, List<String>>();

	// 初始化
	static {
		channelEnums.add(ChannelEnum.NINE);
		channelEnums.add(ChannelEnum.TWENTY);
		channelEnums.add(ChannelEnum.TEHUI);
		channelEnums.add(ChannelEnum.GYHD);

		List<String> nineList = new ItemCate().getLists();
		nineList.add("15_15_100_1");  //生活服务
		nineList.add("16_16_100_1");  //图书影像
		nineList.add("64_64_100_1");  //其它
		cates.put(ChannelEnum.NINE.getChannel(),nineList);
		
		List<String> twentyList = new ItemCate().getLists();
		twentyList.add("15_15_100_1");  //生活服务
		twentyList.add("16_16_100_1");  //图书影像
		twentyList.add("64_64_100_1");  //其它
		cates.put(ChannelEnum.TWENTY.getChannel(), twentyList);
		
		
		cates.put(ChannelEnum.TEHUI.getChannel(), new ItemCate().getLists());
		cates.put(ChannelEnum.GYHD.getChannel(), new ItemCate().getLists());
	}
	
	
	//获取分类对象
	public static Cate getCate() {
		int index=(int)(Math.random()*channelEnums.size());
		ChannelEnum channelEnum =  channelEnums.get(index);
		
		
		List<String> lists =  cates.get(channelEnum.getChannel());
		int index2=(int)(Math.random()*lists.size());
		String cateStr = lists.get(index2);
		
		Cate cate = stringParse(cateStr,lists,index2);
		cate.setChannel(channelEnum);		
		return cate;		
	}
	
	/**
	 * 将分类字符串转化成对象，同时将当前页加1
	 * @param cateStr   分类字符串
	 * @param lists     分类集合
	 * @param index     索引
	 * @return
	 */
	private static Cate stringParse(String cateStr,List<String> lists,int index){
		String[] strs = cateStr.split("_");
		
		//获取分类值
		String cateId = strs[0];
		String targetCateId = strs[1];
		Integer maxPage = Integer.parseInt(strs[2]);
		Integer currentPage = Integer.parseInt(strs[3]);
		
		//将集合中的分类 当前页+1
		Integer newCurrentPage = 1;
		if(currentPage < maxPage) {
			newCurrentPage = currentPage +1;
		}		
	    lists.set(index, cateId+"_"+targetCateId+"_"+maxPage+"_"+newCurrentPage);
		
		return new Cate(cateId, targetCateId, maxPage, currentPage);
		
	}

	
	/**
	 * 商品分类
	 * @author ding.shuai
	 * @date 2018年5月20日上午10:32:48
	 */
	private static class ItemCate {
		// 接口分类id_数据库存储id_最大页_当前页
		private List<String> lists = new ArrayList<String>();

		public ItemCate() {
			// 女装
			lists.add("1_1_100_1");
			// 男装
			lists.add("2_8_100_1");
			//鞋包
			lists.add("3_6_100_1");
			//珠宝配饰
			lists.add("4_6_30_1");
			//运动户外
			lists.add("5_10_100_1");
			//美妆
			lists.add("6_7_100_1");
			//母婴
			lists.add("7_4_100_1");
			//食品
			lists.add("8_5_100_1");
			//内衣
			lists.add("9_9_100_1");
			//数码
			lists.add("10_3_100_1");
			//家装
			lists.add("11_2_100_1");
			//家居用品
			lists.add("12_2_100_1");
			//家电
			lists.add("13_3_100_1");
			//汽车
			lists.add("14_11_100_1");
		}

		public List<String> getLists() {
			return lists;
		}
	}
	
	/**
	 * 分类  接口分类id_数据库存储id_最大页_当前页
	 * @author ding.shuai
	 * @date 2018年5月13日下午7:26:16
	 */
	public static class Cate{
		private String cateId;
		
		private String targetCateId;
		
		private Integer maxPage;
		
		private Integer currentPage;
		
		private ChannelEnum channel;

		public Cate(String cateId, String targetCateId, Integer maxPage, Integer currentPage) {
			super();
			this.cateId = cateId;
			this.targetCateId = targetCateId;
			this.maxPage = maxPage;
			this.currentPage = currentPage;
		}

		public String getCateId() {
			return cateId;
		}

		public void setCateId(String cateId) {
			this.cateId = cateId;
		}

		public String getTargetCateId() {
			return targetCateId;
		}

		public void setTargetCateId(String targetCateId) {
			this.targetCateId = targetCateId;
		}

		public Integer getMaxPage() {
			return maxPage;
		}

		public void setMaxPage(Integer maxPage) {
			this.maxPage = maxPage;
		}

		public Integer getCurrentPage() {
			return currentPage;
		}

		public void setCurrentPage(Integer currentPage) {
			this.currentPage = currentPage;
		}

		public ChannelEnum getChannel() {
			return channel;
		}

		public void setChannel(ChannelEnum channel) {
			this.channel = channel;
		}

		@Override
		public String toString() {
			return "Cate [cateId=" + cateId + ", targetCateId=" + targetCateId + ", maxPage=" + maxPage + ", currentPage="
					+ currentPage + ", channel=" + channel + "]";
		}
	}

	
	public static void main(String[] args) {
		for(int i=0;i<100;i++) {
			System.out.println(RandomGetCateUtil.getCate());
		}		
	}

}

