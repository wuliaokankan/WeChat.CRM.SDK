package com.jubotech.business.web.controller.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.jubotech.business.web.domain.AccountInfo;
import com.jubotech.business.web.domain.req.AccountVo;
import com.jubotech.business.web.service.AccountService;
import com.jubotech.framework.domain.base.PageBean;

@Controller
@RequestMapping("/user/account")
public class AccountController {
	
	@Autowired
	private AccountService service;
    
    /**
     * 进入列表
     * @return
     */
    @RequestMapping(value = "/list",method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView list(HttpServletRequest request ,AccountVo vo){
    	Map<String, Object> map = new HashMap<String, Object>();
    	 
    	AccountInfo info = (AccountInfo) request.getSession().getAttribute("admin");
    	vo.setCid(info.getCid());
    	vo.setType(1);
    	PageBean<AccountInfo> pageBean = service.queryAccountInfo(vo);
    	map.put("pageBean", pageBean);
    	map.put("info", vo);
    	return new ModelAndView("/user/accountlist",map);
    }
    

    
    /**
     * 到添加页面
     * @return
     */
    @RequestMapping(value = "/toadd",method = RequestMethod.GET)
    public ModelAndView toadd(){
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("entity", new AccountInfo());
    	return new ModelAndView("/user/accountadd",map);
    }
    
    /**
     * 到修改页面
     * @param id
     * @return
     */
    @RequestMapping(value = "/toedit/{id}",method = RequestMethod.GET)
    public ModelAndView toedit(@PathVariable(value="id") Integer id){
    	Map<String, Object> map = new HashMap<String, Object>();
    	AccountInfo user = service.findAccountInfoByid(id);
    	map.put("entity", user);
    	return new ModelAndView("/user/accountadd",map);
    }
   
    /**
     * 信息修改
     * @param user
     * @return
     */
    @RequestMapping(value = "/edit" ,method = RequestMethod.POST)
    public ModelAndView edit(AccountInfo user,HttpServletRequest request){
    	service.update(user);
    	return list(request,new AccountVo());
    }
    
    /**
     * 信息添加
     * @return
     */
    @RequestMapping(value = "/add" ,method = RequestMethod.POST)
    public ModelAndView add(AccountInfo user,HttpServletRequest request){
    	AccountInfo info = (AccountInfo) request.getSession().getAttribute("admin");
    	user.setCid(info.getCid());
    	user.setType(1);
    	service.insert(user);
    	return list(request,new AccountVo());
    }
    
    /**
     * 信息删除
     * @param user
     * @param request
     * @return
     */
    @RequestMapping(value = "/delete/{id}" ,method = RequestMethod.GET)
    public ModelAndView userdel(@PathVariable(value="id") Integer id,HttpServletRequest request){
    	//service.delete(id);
    	return list(request,new AccountVo());
    }
     
    
}
