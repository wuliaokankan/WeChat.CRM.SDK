package com.jubotech.business.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.jubotech.business.web.domain.PhoneInfo;
import com.jubotech.framework.domain.base.DBPage;

@Mapper
public interface PhoneDao {
	
	List<PhoneInfo> queryPhoneInfo(@Param("page") DBPage page,@Param("cid")Integer cid,@Param("deviceid")String deviceid,@Param("accountid")Integer accountid);
	
	Integer queryPhoneInfoCount(@Param("page") DBPage page,@Param("cid")Integer cid,@Param("deviceid")String deviceid,@Param("accountid")Integer accountid);
	 
	PhoneInfo findPhoneByid(@Param("id")Integer id);
	
	PhoneInfo getPhoneInfoByDeviceid(@Param("deviceid")String deviceid);
	 
	void insert(PhoneInfo info);
	
	void update(PhoneInfo info);
	
	void delete(PhoneInfo info);
	
	void binding(@Param("id")Integer id,@Param("accountid")Integer accountid);
	 
	Integer getPhoneAllCountByCid(@Param("cid")Integer cid);
	
	void updateLoginTime(@Param("deviceid")String deviceid);
}
