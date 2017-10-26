package com.tooklili.model.wechat;

/**
 * 回复音乐消息的音乐实体
 * @author ding.shuai
 * @date 2016年9月28日上午8:20:09
 */
public class Music {

	/**
	 * 音乐标题
	 */
	private String Title="";
	
	/**
	 * 音乐描述
	 */
	private String Description="";
	
	/**
	 * 音乐链接
	 */
	private String MusicURL="";
	
	/**
	 * 高质量音乐链接，WIFI环境优先使用该链接播放音乐
	 */
	private  String HQMusicUrl="";
	
	/**
	 * 缩略图的媒体id，通过素材管理中的接口上传多媒体文件，得到的id
	 */
	private String ThumbMediaId="";

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getMusicURL() {
		return MusicURL;
	}

	public void setMusicURL(String musicURL) {
		MusicURL = musicURL;
	}

	public String getHQMusicUrl() {
		return HQMusicUrl;
	}

	public void setHQMusicUrl(String hQMusicUrl) {
		HQMusicUrl = hQMusicUrl;
	}

	public String getThumbMediaId() {
		return ThumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		ThumbMediaId = thumbMediaId;
	}
}
