package com.monster.kafka.vo;

/**
 * 消息头格式
 * @author Victor
 * @date 2017年5月4日
 */
public class Header {
	//来源
	private String source;

	//主题
	private String topic;

	//模块
	private String module;
	//功能
	private String function;

	// 操作
	private String action;
	// 类型
	private String type;
	
	//消息编码
	private String code;
	
	//打包的时间
	private String packtime;
	
	// 消息序列号
	private String seqno;
	
	// 消息关键字
	private String keyword;
	
	// 消息数量
	private String total;

	// 随机数
	private String nonce;

	// 参考字段1
	private String ref1;
	// 参考字段2
	private String ref2;
	// 参考字段3
	private String ref3;


	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
 
 

 
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getPacktime() {
		return packtime;
	}
	public void setPacktime(String packtime) {
		this.packtime = packtime;
	}
	public String getSeqno() {
		return seqno;
	}
	public void setSeqno(String seqno) {
		this.seqno = seqno;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getRef1() {
		return ref1;
	}
	public void setRef1(String ref1) {
		this.ref1 = ref1;
	}
	public String getRef2() {
		return ref2;
	}
	public void setRef2(String ref2) {
		this.ref2 = ref2;
	}
	public String getRef3() {
		return ref3;
	}
	public void setRef3(String ref3) {
		this.ref3 = ref3;
	}

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	@Override
	public String toString() {
		return "Header{" +
				"source='" + source + '\'' +
				", topic='" + topic + '\'' +
				", module='" + module + '\'' +
				", function='" + function + '\'' +
				", action='" + action + '\'' +
				", type='" + type + '\'' +
				", code='" + code + '\'' +
				", packtime='" + packtime + '\'' +
				", seqno='" + seqno + '\'' +
				", keyword='" + keyword + '\'' +
				", total='" + total + '\'' +
				", nonce='" + nonce + '\'' +
				", ref1='" + ref1 + '\'' +
				", ref2='" + ref2 + '\'' +
				", ref3='" + ref3 + '\'' +
				'}';
	}
}
