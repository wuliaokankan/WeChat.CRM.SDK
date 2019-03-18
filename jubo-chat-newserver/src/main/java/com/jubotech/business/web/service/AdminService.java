package com.jubotech.business.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jubotech.business.web.dao.AdminDao;
import com.jubotech.business.web.domain.AdminInfo;
import com.jubotech.framework.util.MD5Util;

@Service
@Transactional // 支持事务
public class AdminService {

	@Autowired
	private AdminDao adminDao;

	 

	public AdminInfo login(String pname,String password) {
		password = MD5Util.getMD5(pname+password);
		AdminInfo user = adminDao.findAdminInfo(pname, password);
		return user;
	}
	
	 
	
	public String pwdedit(String newpwd,String oldpwd,AdminInfo user){
		String res ="success";
		String password = MD5Util.getMD5(user.getLoginname()+oldpwd);
		if(user.getPassword().equals(password)){
			try {
				String newpassword = MD5Util.getMD5(user.getLoginname()+newpwd);
				adminDao.pwdedit(newpassword,user.getId());
			} catch (Exception e) {
				res ="over";
				e.printStackTrace();
			}
		}else{
			res ="fail";
		}
		return res;
	}
}
