package com.monster.kafka.vo;

/**
 * 消息报文格式
 * @author Victor
 * @date 2017年5月4日
 */
public class Message {
	private String header;
	private String body;

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	private String topic;



	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
}
