package com.jubotech.business.web.domain;

import java.io.Serializable;
import java.util.Date;

public class CustomerInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;// SupplierId
	private String suppliername;
	private String shortname;
	private Integer level;
	private Integer account_num;
	private Integer device_num;
	private String validity;
	private Integer state;
	private String admin;
	private String contact;
	private String phone;
	private String description;
	private Date create_time;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSuppliername() {
		return suppliername;
	}

	public void setSuppliername(String suppliername) {
		this.suppliername = suppliername;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getAccount_num() {
		return account_num;
	}

	public void setAccount_num(Integer account_num) {
		this.account_num = account_num;
	}

	public Integer getDevice_num() {
		return device_num;
	}

	public void setDevice_num(Integer device_num) {
		this.device_num = device_num;
	}

 
	public String getValidity() {
		return validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

}
