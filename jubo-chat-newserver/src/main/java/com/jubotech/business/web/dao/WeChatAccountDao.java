package com.jubotech.business.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.jubotech.business.web.domain.WeChatAccountInfo;

@Mapper
public interface WeChatAccountDao {
	WeChatAccountInfo findWeChatAccountInfoByid(@Param("id")Integer id);
	
	WeChatAccountInfo findWeChatAccountInfoByDeviceid(@Param("deviceid")String deviceid);
	
	WeChatAccountInfo findWeChatAccountInfoByWeChatId(@Param("wechatid")String wechatid);
	
	List<WeChatAccountInfo> findWeChatAccountInfo(@Param("cid")Integer cid ,@Param("accountid")Integer accountid);
	
	void insert(WeChatAccountInfo info);
	
	void update(WeChatAccountInfo info);
	
	void updateLogined(WeChatAccountInfo info);
	
	void updateOnline(WeChatAccountInfo info);
	
	void updateOnlineLogined(WeChatAccountInfo info);
	
	void updateAccountidOffline(@Param("id")Integer id ,@Param("accountid")Integer accountid);
	   
}
