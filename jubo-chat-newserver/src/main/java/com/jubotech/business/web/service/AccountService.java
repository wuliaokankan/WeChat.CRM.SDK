package com.jubotech.business.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jubotech.business.web.dao.AccountDao;
import com.jubotech.business.web.domain.AccountInfo;
import com.jubotech.business.web.domain.req.AccountVo;
import com.jubotech.framework.domain.base.DBPage;
import com.jubotech.framework.domain.base.PageBean;

@Service
@Transactional // 支持事务
public class AccountService {

	@Autowired
	private AccountDao accountDao;

	public PageBean<AccountInfo> queryAccountInfo(AccountVo info) {

		DBPage page = new DBPage(info.getPageSize(), (info.getPageNo() - 1) * info.getPageSize());

		List<AccountInfo> resultList = accountDao.queryAccountInfo(page, info.getAccount(),info.getType(),info.getCid());
		Integer totalResult = accountDao.queryAccountInfoCount(page, info.getAccount(),info.getType(),info.getCid());
		int pageTotal = totalResult % info.getPageSize() == 0 ? totalResult / info.getPageSize()
				: totalResult / info.getPageSize() + 1;

		PageBean<AccountInfo> pageBean = new PageBean<AccountInfo>(info.getPageSize(), info.getPageNo(), pageTotal,
				resultList);

		return pageBean;
	}

	public AccountInfo findAccountInfoByid(Integer id) {
		AccountInfo user = accountDao.findAccountInfoByid(id);
		return user;
	}
	
	public AccountInfo findAccountInfoByAccount(String account) {
		AccountInfo user = accountDao.findAccountInfoByAccount(account);
		return user;
	}
	 
	public List<AccountInfo> getAllAccountInfoByCid(Integer cid){
		return accountDao.getAllAccountInfoByCid(cid);
	}

	public void insert(AccountInfo user) {
		accountDao.insert(user);
	}

	public void update(AccountInfo user) {
		accountDao.update(user);
	}

	public void delete(Integer id) {
		AccountInfo user = new AccountInfo();
		user.setId(id);
		accountDao.delete(user);
	}
	
	public AccountInfo login(String pname,String password) {
		AccountInfo user = accountDao.findAccountInfo(pname, password,0);
		return user;
	}
	
	 
	
	public String pwdedit(String newpwd,String oldpwd,AccountInfo user){
		String res ="success";
		if(user.getPassword().equals(oldpwd)){
			try {
				accountDao.pwdedit(newpwd,user.getId());
			} catch (Exception e) {
				res ="over";
				e.printStackTrace();
			}
		}else{
			res ="fail";
		}
		return res;
	}
	
	
	public AccountInfo clientlogin(String loginname,String password) {
		return accountDao.findAccountInfo(loginname, password,1);
	}
	

}
