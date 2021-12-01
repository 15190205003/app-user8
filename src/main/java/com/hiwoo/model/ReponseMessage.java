package com.hiwoo.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY )
public class ReponseMessage {

	private String code;

	private String message;

	private  Object attachObject;

	private Integer total;
	
	public static String S_Success="0000";
	public static String S_Fail="0001";
	public static String S_AuthCodeFail="0002";
	public static String S_DataException="0003";

	public Object getAttachObject() {
		return attachObject;
	}

	public void setAttachObject(Object attachObject) {
		this.attachObject = attachObject;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public ReponseMessage()
	{
		
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public ReponseMessage(String code, String message)
	{
		this.code=code;
		this.message=message;
	}
	
	public static ReponseMessage Create(String code,String message)
	{
		ReponseMessage result=new ReponseMessage(code,message);
		return result;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
}
