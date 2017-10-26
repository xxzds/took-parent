package com.tooklili.enums.wechat;


/**
 * 微信的消息类型
 * @author ding.shuai
 * @date 2016年9月27日下午10:42:55
 */
public enum MsgType {
	text("文本"),
	image("图片"),
	voice("语音"),
	video("视频"),
	shortvideo("小视频"),
	location("地理位置"),
	link("链接"),
	event("事件"),
	music("音乐");
	
	private final String info;

	private MsgType(String info) {
		this.info = info;
	}

	public String getInfo() {
		return info;
	}
}
