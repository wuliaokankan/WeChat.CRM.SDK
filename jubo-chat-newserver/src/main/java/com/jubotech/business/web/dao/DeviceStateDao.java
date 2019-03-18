package com.jubotech.business.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.jubotech.business.web.domain.DeviceStateInfo;
import com.jubotech.framework.domain.base.DBPage;

@Mapper
public interface DeviceStateDao{
	
	List<DeviceStateInfo> queryDeviceStateInfo(@Param("page") DBPage page,@Param("wechatid")String wechatid,@Param("deviceid")String deviceid,@Param("isonline")Integer isonline,@Param("islogined")Integer islogined,@Param("cid")Integer cid);
	
	Integer queryDeviceStateInfoCount(@Param("page")DBPage page,@Param("wechatid")String wechatid,@Param("deviceid")String deviceid,@Param("isonline")Integer isonline,@Param("islogined")Integer islogined,@Param("cid")Integer cid);

}
