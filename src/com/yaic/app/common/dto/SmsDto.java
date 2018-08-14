package com.yaic.app.common.dto;

public class SmsDto {
	/**
	 * 命令字
	 */
	private String cmd;
	
	/**
	 * 手机号码
	 */
	private String mobiles;
	/**
	 * 状态id，对应短信或者彩信的消息ID。确定唯一性。
	 */
	private String smsid;
	
	/**
	 * 需要e-mas中哪一条通道发送的通道ID。
	 */
	private String channel_id;
	
	/**
	 * 商贸类型id
	 */
	private String business_id;
	
	/**
	 * 短信内容
	 * 
	 */
	private String smscontent;
	
	/**
	 * 密码
	 * @return
	 */
	private String userpassword;
	/**
	 * 系统用户名
	 * @return
	 */
	private String username;
	
	/**
	 * 时间戳
	 * @return
	 */
	private String timestamp;
	
	/**
	 * 解密公钥
	 */
	private String key;
	
	/**
	 * 编码方式
	 * @return
	 */
	private String srccharset;
	/**
	 * 发送时间
	 * @return
	 */
	private String sendtime;
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public String getMobiles() {
		return mobiles;
	}
	public void setMobiles(String mobiles) {
		this.mobiles = mobiles;
	}
	public String getSmsid() {
		return smsid;
	}
	public void setSmsid(String smsid) {
		this.smsid = smsid;
	}
	public String getChannel_id() {
		return channel_id;
	}
	public void setChannel_id(String channel_id) {
		this.channel_id = channel_id;
	}
	public String getBusiness_id() {
		return business_id;
	}
	public void setBusiness_id(String business_id) {
		this.business_id = business_id;
	}
	public String getSmscontent() {
		return smscontent;
	}
	public void setSmscontent(String smscontent) {
		this.smscontent = smscontent;
	}
	public String getUserpassword() {
		return userpassword;
	}
	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getSrccharset() {
		return srccharset;
	}
	public void setSrccharset(String srccharset) {
		this.srccharset = srccharset;
	}
	public String getSendtime() {
		return sendtime;
	}
	public void setSendtime(String sendtime) {
		this.sendtime = sendtime;
	}
	
}