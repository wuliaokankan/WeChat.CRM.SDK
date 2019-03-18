package com.jubotech.business.web.controller.admin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.jubotech.business.web.domain.CustomerInfo;
import com.jubotech.business.web.domain.req.CustomerVo;
import com.jubotech.business.web.service.CustomerService;
import com.jubotech.framework.domain.base.PageBean;

@Controller
@RequestMapping("/admin/customer")
public class CustomerController {
	
	@Autowired
	private CustomerService service;
    
    /**
     * 进入列表
     * @return
     */
    @RequestMapping(value = "/list",method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView list(HttpServletRequest request ,CustomerVo vo){
    	Map<String, Object> map = new HashMap<String, Object>();
    	 
    	PageBean<CustomerInfo> pageBean = service.queryCustomerInfo(vo);
    	map.put("pageBean", pageBean);
    	map.put("info", vo);
    	return new ModelAndView("/admin/customerlist",map);
    }
    

    
    /**
     * 到添加页面
     * @return
     */
    @RequestMapping(value = "/toadd",method = RequestMethod.GET)
    public ModelAndView toadd(){
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("entity", new CustomerInfo());
    	return new ModelAndView("/admin/customeradd",map);
    }
    
    /**
     * 到修改页面
     * @param id
     * @return
     */
    @RequestMapping(value = "/toedit/{id}",method = RequestMethod.GET)
    public ModelAndView toedit(@PathVariable(value="id") Integer id){
    	Map<String, Object> map = new HashMap<String, Object>();
    	CustomerInfo user = service.getCustomerInfoByid(id);
    	map.put("entity", user);
    	return new ModelAndView("/admin/customeradd",map);
    }
   
    /**
     * 信息修改
     * @param user
     * @return
     */
    @RequestMapping(value = "/edit" ,method = RequestMethod.POST)
    public ModelAndView edit(CustomerInfo user,HttpServletRequest request){
    	service.update(user);
    	return list(request,new CustomerVo());
    }
    
    /**
     * 信息添加
     * @return
     */
    @RequestMapping(value = "/add" ,method = RequestMethod.POST)
    public ModelAndView add(CustomerInfo user,HttpServletRequest request){
    	service.insert(user);
    	return list(request,new CustomerVo());
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
    	return list(request,new CustomerVo());
    }
     
    
}
