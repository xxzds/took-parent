package com.tooklili.enums.common;

/**
 * 超级搜索渠道
 * @author ding.shuai
 * @date 2018年5月13日下午5:39:07
 */
public enum ChannelEnum {
	NINE("9k9","超值9"),
	TWENTY("20k","20元封顶"),
	TEHUI("tehui","特价好货"),
	GYHD("qqhd","高佣活动");
	
	
	private String channel;
	private String name;

	private ChannelEnum(String channel,String name) {
		this.channel = channel;
		this.name = name;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
