package com.jubotech.business.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class ExceptionInterceptor implements HandlerInterceptor {
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// System.out.println("----afterCompletion在页面渲染之后被调用--好像没啥鸟用--");
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2,ModelAndView modelAndView) throws Exception {
		// System.out.println("----postHandle在controller执行之后切页面渲染之前被调用----");
		if (response.getStatus() >= 500) {
			modelAndView.setViewName("/login");
		} else if (response.getStatus() == 404) {
			modelAndView.setViewName("/common/404");
		}

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		// System.out.println("-----preHandle在controller执行之前被调用------");
		HttpSession session = request.getSession(false);
		if(null == session || null == session.getAttribute("admin")){
			response.sendRedirect(request.getContextPath()+"/");
			return false;
		}
		return true;
	}
}
