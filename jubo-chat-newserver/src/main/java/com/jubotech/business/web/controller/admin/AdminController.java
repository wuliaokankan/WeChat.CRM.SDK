package com.jubotech.business.web.controller.admin;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jubotech.business.web.domain.AdminInfo;
import com.jubotech.business.web.service.AdminService;

@Controller
public class AdminController {

	@Autowired
	private AdminService service;

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public ModelAndView index() {
		return new ModelAndView("/admin/login");
	}

	/**
	 * 用户登录
	 * 
	 * @param pname
	 * @param password
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/admin/login", method = RequestMethod.POST)
	public ModelAndView login(@RequestParam("pname") String pname, @RequestParam("password") String password,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();

		AdminInfo user = service.login(pname, password);
		if (null == user) {
			map.put("error", "账号或密码错误");
			return new ModelAndView("/admin/login", map);
		}

		request.getSession().setAttribute("admin", user);

		return new ModelAndView("/admin/adminlist", map);
	}

	/**
	 * 后台管理 退出
	 */
	@RequestMapping(value = "/admin/exit", method = RequestMethod.GET)
	public ModelAndView exit(HttpServletRequest request) throws Exception {
		Enumeration<?> em = request.getSession().getAttributeNames();
		while (em.hasMoreElements()) {
			request.getSession().removeAttribute(em.nextElement().toString());
		}
		return new ModelAndView("admin/login");
	}
	
	 

	/**
	 * 到密码修改页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/admin/pwd", method = RequestMethod.GET)
	public ModelAndView pwd() {
		return new ModelAndView("/admin/pwd");
	}

	/**
	 * 到密码修改页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/admin/pwdedit", method = RequestMethod.POST)
	public String pwdedit(@RequestParam(value = "newpwd") String newpwd, @RequestParam(value = "oldpwd") String oldpwd,
			HttpServletRequest request) {
		AdminInfo ui = (AdminInfo) request.getSession(false).getAttribute("admin");
		return service.pwdedit(newpwd, oldpwd, ui);
	}
}
