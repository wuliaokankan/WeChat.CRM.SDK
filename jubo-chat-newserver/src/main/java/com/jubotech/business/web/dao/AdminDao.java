package com.jubotech.business.web.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.jubotech.business.web.domain.AdminInfo;

@Mapper
public interface AdminDao {
	 
	AdminInfo findAdminInfo(@Param("loginname")String pname,@Param("password")String password);
	
	void pwdedit(@Param("password")String password,@Param("id")Integer id);
	
}
