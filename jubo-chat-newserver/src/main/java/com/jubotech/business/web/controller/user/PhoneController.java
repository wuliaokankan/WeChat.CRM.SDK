package com.jubotech.business.web.controller.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.jubotech.business.web.domain.AccountInfo;
import com.jubotech.business.web.domain.PhoneInfo;
import com.jubotech.business.web.domain.req.PhoneVo;
import com.jubotech.business.web.service.AccountService;
import com.jubotech.business.web.service.PhoneService;
import com.jubotech.framework.domain.base.PageBean;
import com.jubotech.framework.util.SessionUtil;

@Controller 
@RequestMapping("/user/phone")
public class PhoneController {
	
	@Autowired
	private PhoneService phoneService;
	
	@Autowired
	private AccountService accountService;
 
    /**
     * 列表信息分页查询
     * @return
     */
    @RequestMapping(value = "/list",method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView list(HttpServletRequest request,PhoneVo vo){
    	Map<String, Object> map = new HashMap<String, Object>();
    	AccountInfo info =  SessionUtil.getAccountInfo(request);
    	Integer cid = info.getCid();
    	vo.setCid(cid);
    	PageBean<PhoneInfo> pageBean = phoneService.queryPhoneInfo(vo);
    	List<AccountInfo> userList = accountService.getAllAccountInfoByCid(cid);
    	
    	map.put("pageBean", pageBean);
    	map.put("userList", userList);
    	map.put("info", vo);
    	return new ModelAndView("/user/phonelist",map);
    }

 
    /**
     * 到添加页面
     * @return
     */
    @RequestMapping(value = "/toadd",method = RequestMethod.GET)
    public ModelAndView toadd(HttpServletRequest request){
    	Map<String, Object> map = new HashMap<String, Object>();
    	 
    	map.put("entity", new PhoneInfo());
    	return new ModelAndView("/user/phoneadd",map);
    }
    
    /**
     * 到修改页面
     * @param id
     * @return
     */
    @RequestMapping(value = "/toedit/{id}",method = RequestMethod.GET)
    public ModelAndView toedit(@PathVariable(value="id") Integer id,HttpServletRequest request){
    	Map<String, Object> map = new HashMap<String, Object>();
    	PhoneInfo info = phoneService.getPhoneInfoByid(id);
    	map.put("entity", info);
    	return new ModelAndView("/user/phoneadd",map);
    }
    
    /**
     * 信息修改
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/edit" ,method = RequestMethod.POST)
    public String edit(PhoneInfo info,HttpServletRequest request){
    	return phoneService.update(info);
    }
    
    /**
     * 信息添加
     * @param info
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/add" ,method = RequestMethod.POST)
    public String add(PhoneInfo info,HttpServletRequest request){
    	AccountInfo acc =  SessionUtil.getAccountInfo(request);
    	Integer cid = acc.getCid();
    	info.setCid(cid);
    	
    	return phoneService.insert(info);
    }
    
    /**
     * 信息删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}" ,method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable(value="id") Integer id,HttpServletRequest request){
    	phoneService.delete(id);
    	return list(request,new PhoneVo());
    }
    
    
    /**
     * 绑定账号
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/binding" ,method = RequestMethod.POST)
    public boolean binding(Integer accountid,String ids,HttpServletRequest request){
    	return phoneService.binding(accountid,ids);
    }
    
    
}
