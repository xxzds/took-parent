package com.tooklili.model.wechat;

import com.tooklili.enums.wechat.MsgType;

/**
 * 接收到的微信xml对应的实体类
 * @author ding.shuai
 * @date 2016年9月27日下午10:16:41
 */
public class ReceiveXmlEntity {
	
	/************************公共************************/
	/**
	 * 开发者微信号
	 */
	private String ToUserName;
	
	/**
	 * 发送方帐号（一个OpenID）
	 */
	private String FromUserName;
	
	/**
	 * 消息创建时间 （整型）
	 */
	private Long CreateTime;
	
	/**
	 * 消息类型
	 */
	private MsgType MsgType;
	
	/**
	 * 消息id，64位整型
	 */
	private Long MsgId;
	
	/**
	 * 事件类型
	 */
	private String Event;
	
	/**
	 * 事件KEY值，与自定义菜单接口中KEY值对应
	 */
	private String EventKey;
	
	/**
	 * 消息媒体id，可以调用多媒体文件下载接口拉取数据。
	 */
	private String MediaId;
	
	
	private String MenuId;
	/************************文本消息************************/
	/**
	 * 文本消息内容
	 */
	private String Content;
	
	/************************图片消息************************/
	/**
	 * 图片链接（由系统生成）
	 */
	private String PicUrl;
	
	/************************语音消息************************/
	/**
	 * 语音格式，如amr，speex等
	 */
	private String Format;
	
	/**
	 * 语音识别结果，UTF8编码
	 */
	private String Recognition;
	
	
	/************************视频消息************************/
	/**
	 * 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。
	 */
	private String ThumbMediaId;
	
	/************************弹出地理位置选择器的事件推送************************/
	/**
	 * X坐标信息
	 */
	private double Location_X;
	/**
	 * Y坐标信息
	 */
	private double Location_Y;
		
	/**
	 * 地图缩放大小
	 */
	private double Scale;
	
	/**
	 * 地理位置的字符串信息
	 */
	private String Label;
	
	/**
	 * 朋友圈POI的名字，可能为空
	 */
	private String Poiname;
	
	/************************链接消息************************/
	/**
	 * 消息标题
	 */
	private String Title;
	
	/**
	 * 消息描述
	 */
	private String Description;
	
	/**
	 * 消息链接
	 */
	private String Url;
	
	private String Ticket;
	private String Latitude;
	private String Longitude;
	private String Precision;
	
	public String getToUserName() {
		return ToUserName;
	}
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	public String getFromUserName() {
		return FromUserName;
	}
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	public Long getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(Long createTime) {
		CreateTime = createTime;
	}
	public MsgType getMsgType() {
		return MsgType;
	}
	public void setMsgType(MsgType msgType) {
		MsgType = msgType;
	}
	public Long getMsgId() {
		return MsgId;
	}
	public void setMsgId(Long msgId) {
		MsgId = msgId;
	}
	public String getEvent() {
		return Event;
	}
	public void setEvent(String event) {
		Event = event;
	}
	public String getEventKey() {
		return EventKey;
	}
	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}
	public String getMediaId() {
		return MediaId;
	}
	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
	public String getPicUrl() {
		return PicUrl;
	}
	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}
	public String getFormat() {
		return Format;
	}
	public void setFormat(String format) {
		Format = format;
	}
	public String getRecognition() {
		return Recognition;
	}
	public void setRecognition(String recognition) {
		Recognition = recognition;
	}
	public String getThumbMediaId() {
		return ThumbMediaId;
	}
	public void setThumbMediaId(String thumbMediaId) {
		ThumbMediaId = thumbMediaId;
	}
	public double getLocation_X() {
		return Location_X;
	}
	public void setLocation_X(double location_X) {
		Location_X = location_X;
	}
	public double getLocation_Y() {
		return Location_Y;
	}
	public void setLocation_Y(double location_Y) {
		Location_Y = location_Y;
	}
	public double getScale() {
		return Scale;
	}
	public void setScale(double scale) {
		Scale = scale;
	}
	public String getLabel() {
		return Label;
	}
	public void setLabel(String label) {
		Label = label;
	}
	public String getPoiname() {
		return Poiname;
	}
	public void setPoiname(String poiname) {
		Poiname = poiname;
	}
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
	public String getUrl() {
		return Url;
	}
	public void setUrl(String url) {
		Url = url;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public String getTicket() {
		return Ticket;
	}
	public void setTicket(String ticket) {
		Ticket = ticket;
	}
	public String getLatitude() {
		return Latitude;
	}
	public void setLatitude(String latitude) {
		Latitude = latitude;
	}
	public String getLongitude() {
		return Longitude;
	}
	public void setLongitude(String longitude) {
		Longitude = longitude;
	}
	public String getPrecision() {
		return Precision;
	}
	public void setPrecision(String precision) {
		Precision = precision;
	}
	public String getMenuId() {
		return MenuId;
	}
	public void setMenuId(String menuId) {
		MenuId = menuId;
	}
}
