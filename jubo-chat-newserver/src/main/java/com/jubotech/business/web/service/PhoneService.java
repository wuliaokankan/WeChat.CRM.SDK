package com.jubotech.business.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jubotech.business.web.dao.CustomerDao;
import com.jubotech.business.web.dao.PhoneDao;
import com.jubotech.business.web.dao.WeChatAccountDao;
import com.jubotech.business.web.domain.PhoneInfo;
import com.jubotech.business.web.domain.WeChatAccountInfo;
import com.jubotech.business.web.domain.req.PhoneVo;
import com.jubotech.framework.domain.base.DBPage;
import com.jubotech.framework.domain.base.PageBean;

@Service
@Transactional // 支持事务
public class PhoneService {

	@Autowired
	private PhoneDao phoneDao;
	
	@Autowired
	private CustomerDao customerDao;
	
	@Autowired
	private WeChatAccountDao weChatAccountDao;
	
	 
	public PageBean<PhoneInfo> queryPhoneInfo(PhoneVo info) {
		 
		DBPage page = new DBPage(info.getPageSize(), (info.getPageNo() - 1) * info.getPageSize());
		
		List<PhoneInfo> resultList = phoneDao.queryPhoneInfo(page,info.getCid(),info.getDeviceid(),info.getAccountid());
		Integer totalResult = phoneDao.queryPhoneInfoCount(page,info.getCid(),info.getDeviceid(),info.getAccountid());
		int pageTotal = totalResult % info.getPageSize() == 0 ? totalResult / info.getPageSize(): totalResult / info.getPageSize() + 1;

		PageBean<PhoneInfo> pageBean = new PageBean<PhoneInfo>(info.getPageSize(), info.getPageNo(), pageTotal, resultList);

		return pageBean;
	}

	
	public PhoneInfo getPhoneInfoByid(Integer id){
		return phoneDao.findPhoneByid(id);
	}
	 
	public String insert(PhoneInfo info) {
		String res = "success";
		Integer count = customerDao.getCustomerPhoneCount(info.getCid());//该客户端的的设备数
		Integer pcount =  phoneDao.getPhoneAllCountByCid(info.getCid());//已经绑定的设备数
		if(pcount >= count){
			res = "over";
		}else{
			try {
				phoneDao.insert(info);
			} catch (Exception e) {
				res = "fail";
				e.printStackTrace();
			}
		}
		
		return res;
	}

	public String update(PhoneInfo info) {
		String res = "success";
		try {
			phoneDao.update(info);
		} catch (Exception e) {
			res = "fail";
			e.printStackTrace();
		}
		return res;
	}

	public void delete(Integer id) {
		PhoneInfo user = new PhoneInfo();
		user.setId(id);
		phoneDao.delete(user);
	}
	
	
	public boolean binding(Integer accountid,String ids){
		try {
			if(!StringUtils.isEmpty(accountid) && !StringUtils.isEmpty(ids)){
				String []  s= ids.split(",");
				if(null != s && s.length>0){
					for(int i= 0;i<s.length;i++){
						Integer id = Integer.valueOf(s[i]);
						bangding(id, accountid);
					}
				}else{
					Integer id = Integer.valueOf(ids);
					bangding(id, accountid);
				}
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	private void  bangding(Integer id,Integer accountid){
		phoneDao.binding(id,accountid);
		PhoneInfo  phone = phoneDao.findPhoneByid(id);
		if( null != phone && null != phone.getDeviceid()){
			WeChatAccountInfo  account = weChatAccountDao.findWeChatAccountInfoByDeviceid(phone.getDeviceid());
			if(null != account && null != account.getId() && null != accountid){
				weChatAccountDao.updateAccountidOffline(account.getId(), accountid);
			}
		}
	}
	
	public PhoneInfo getPhoneInfoByDeviceid(String deviceid){
		return phoneDao.getPhoneInfoByDeviceid(deviceid);
	}
	
  
	
	public void updateLoginTime(String deviceid){
		phoneDao.updateLoginTime(deviceid);
	}
}
