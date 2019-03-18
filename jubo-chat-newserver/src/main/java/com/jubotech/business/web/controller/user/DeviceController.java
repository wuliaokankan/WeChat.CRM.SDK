package com.jubotech.business.web.controller.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.jubotech.business.web.domain.AccountInfo;
import com.jubotech.business.web.domain.DeviceStateInfo;
import com.jubotech.business.web.domain.req.DeviceStateInfoVo;
import com.jubotech.business.web.service.DeviceStateService;
import com.jubotech.framework.domain.base.PageBean;

@Controller
@RequestMapping("/user/device")
public class DeviceController {
	
	@Autowired
	private DeviceStateService service;
    
    /**
     * 进入列表
     * @return
     */
    @RequestMapping(value = "/list",method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView list(HttpServletRequest request ,DeviceStateInfoVo vo){
    	Map<String, Object> map = new HashMap<String, Object>();
    	AccountInfo info = (AccountInfo) request.getSession().getAttribute("admin");
    	vo.setCid(info.getCid());
    	PageBean<DeviceStateInfo> pageBean = service.queryDeviceStateInfo(vo);
    	map.put("pageBean", pageBean);
    	map.put("info", vo);
    	return new ModelAndView("/user/devicestatelist",map);
    }
    
 
    
}
