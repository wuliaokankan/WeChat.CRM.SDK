package com.jubotech.business.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jubotech.business.web.dao.AccountDao;
import com.jubotech.business.web.dao.CustomerDao;
import com.jubotech.business.web.domain.AccountInfo;
import com.jubotech.business.web.domain.CustomerInfo;
import com.jubotech.business.web.domain.req.CustomerVo;
import com.jubotech.framework.domain.base.DBPage;
import com.jubotech.framework.domain.base.PageBean;

@Service
@Transactional // 支持事务
public class CustomerService {

	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private AccountDao accountDao;

	public PageBean<CustomerInfo> queryCustomerInfo(CustomerVo info) {
		 
		DBPage page = new DBPage(info.getPageSize(), (info.getPageNo() - 1) * info.getPageSize());
		
		List<CustomerInfo> resultList = customerDao.queryCustomerInfo(page,info.getSuppliername());
		Integer totalResult = customerDao.queryCustomerInfoCount(page,info.getSuppliername());
		int pageTotal = totalResult % info.getPageSize() == 0 ? totalResult / info.getPageSize(): totalResult / info.getPageSize() + 1;

		PageBean<CustomerInfo> pageBean = new PageBean<CustomerInfo>(info.getPageSize(), info.getPageNo(), pageTotal, resultList);

		return pageBean;
	}

 
	public CustomerInfo getCustomerInfoByid(Integer id){
		CustomerInfo user = customerDao.findCustomerInfoByid(id);
		return user;
	}
	
	public void insert(CustomerInfo user) {
		customerDao.insert(user);
		if(null != user.getId()){
			AccountInfo info = new AccountInfo(user.getAdmin(), "123456", user.getAdmin(), 0, user.getId(), 0);
			accountDao.insert(info);
		}
	}

	public void update(CustomerInfo user) {
		customerDao.update(user);
	}

	public void delete(Integer id) {
		CustomerInfo user = new CustomerInfo();
		user.setId(id);
		customerDao.delete(user);
	}
	
	public CustomerInfo getCustomerInfoByDeviceId(String deviceId){
		return customerDao.getCustomerInfoByDeviceId(deviceId);
	}
	
	
	
}
