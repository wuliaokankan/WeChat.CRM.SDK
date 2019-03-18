package com.jubotech.business.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jubotech.business.web.dao.WeChatAccountDao;
import com.jubotech.business.web.domain.WeChatAccountInfo;

@Service
@Transactional // 支持事务
public class WeChatAccountService {

	@Autowired
	private WeChatAccountDao weChatAccountDao;


	public WeChatAccountInfo getWeChatAccountByid(Integer id) {
		WeChatAccountInfo user = weChatAccountDao.findWeChatAccountInfoByid(id);
		return user;
	}
	public WeChatAccountInfo findWeChatAccountInfoByDeviceid(String deviceid) {
		WeChatAccountInfo user = weChatAccountDao.findWeChatAccountInfoByDeviceid(deviceid);
		return user;
	}
	
	public WeChatAccountInfo findWeChatAccountInfoByWeChatId(String weChatId) {
		WeChatAccountInfo user = weChatAccountDao.findWeChatAccountInfoByWeChatId(weChatId);
		return user;
	}
 
	public List<WeChatAccountInfo> findWeChatAccountInfo(Integer cid ,Integer accountid) {
		return weChatAccountDao.findWeChatAccountInfo(cid,accountid);
	}
	
	public void insert(WeChatAccountInfo user) {
		weChatAccountDao.insert(user);
	}

	public void update(WeChatAccountInfo user) {
		weChatAccountDao.update(user);
	}

	public void updateLogined(WeChatAccountInfo user) {
		weChatAccountDao.updateLogined(user);
	}
	
	public void updateOnline(WeChatAccountInfo user) {
		weChatAccountDao.updateOnline(user);
	}
	
	public void updateOnlineLogined(WeChatAccountInfo user) {
		weChatAccountDao.updateOnlineLogined(user);
	}
 
	  
}
