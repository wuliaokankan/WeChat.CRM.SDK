package com.jubotech.framework.util;

import javax.servlet.http.HttpServletRequest;

import com.jubotech.business.web.domain.AccountInfo;
import com.jubotech.business.web.domain.AdminInfo;

public class SessionUtil {
    public static AdminInfo getUserInfo(HttpServletRequest request){
    	return (AdminInfo) request.getSession(false).getAttribute("admin");
    }
    
    public static AccountInfo getAccountInfo(HttpServletRequest request){
    	return (AccountInfo) request.getSession(false).getAttribute("admin");
    }
     
}
