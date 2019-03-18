package com.jubotech.business.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.jubotech.business.web.domain.CustomerInfo;
import com.jubotech.framework.domain.base.DBPage;

@Mapper
public interface CustomerDao {
	
	List<CustomerInfo> queryCustomerInfo(@Param("page") DBPage page,@Param("suppliername")String suppliername);
	
	Integer queryCustomerInfoCount(@Param("page") DBPage page,@Param("suppliername")String suppliername);
	 
	CustomerInfo findCustomerInfoByid(@Param("id")Integer id);
	  
	Integer insert(CustomerInfo info);
	
	void update(CustomerInfo info);
	
	void delete(CustomerInfo info);
 
	Integer getCustomerPhoneCount(@Param("id")Integer id);
	
	
	CustomerInfo getCustomerInfoByDeviceId(@Param("deviceid")String deviceid);
}
