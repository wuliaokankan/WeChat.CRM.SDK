package com.jubotech.business.web.domain.req;

import com.jubotech.framework.domain.base.Page;

public class UserVo extends Page{
	 
	private String loginname;

	private String nickname;
	
	private String pname;

 
	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

}
