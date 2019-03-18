package com.jubotech.business.web.domain;

import java.io.Serializable;

public class PhoneBindingInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String imei;

	private String account;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

}
