package com.jubotech.business.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jubotech.business.web.dao.DeviceStateDao;
import com.jubotech.business.web.domain.DeviceStateInfo;
import com.jubotech.business.web.domain.req.DeviceStateInfoVo;
import com.jubotech.framework.domain.base.DBPage;
import com.jubotech.framework.domain.base.PageBean;

@Service
@Transactional // 支持事务
public class DeviceStateService {
  
	@Autowired
	private DeviceStateDao deviceStateDao;
	
	 
	public PageBean<DeviceStateInfo> queryDeviceStateInfo(DeviceStateInfoVo info) {
		 
		DBPage page = new DBPage(info.getPageSize(), (info.getPageNo() - 1) * info.getPageSize());
		
		List<DeviceStateInfo> resultList = deviceStateDao.queryDeviceStateInfo(page,info.getWechatid(),info.getDeviceid(),info.getIsonline(),info.getIslogined(),info.getCid());
		Integer totalResult = deviceStateDao.queryDeviceStateInfoCount(page,info.getWechatid(),info.getDeviceid(),info.getIsonline(),info.getIslogined(),info.getCid());
		int pageTotal = totalResult % info.getPageSize() == 0 ? totalResult / info.getPageSize(): totalResult / info.getPageSize() + 1;

		PageBean<DeviceStateInfo> pageBean = new PageBean<DeviceStateInfo>(info.getPageSize(), info.getPageNo(), pageTotal, resultList);

		return pageBean;
	}
 
}
