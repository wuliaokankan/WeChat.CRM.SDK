package com.jubotech.business.web.controller;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.google.gson.Gson;
import com.jubotech.framework.domain.base.BaseResp;
import com.jubotech.framework.util.ActionResult;
import com.jubotech.framework.util.FileUtil;
 

/**
 * @author admin 公共上传方法
 */

@Controller
public class UploadController {

	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

	/**
	 * 后台管理 上传文件通用接口
	 */
	@ResponseBody
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
	public void fileUpload(@RequestParam("myfile") MultipartFile file, HttpServletResponse response) throws Exception {
		BaseResp resp = new BaseResp();
		try {
			Map<String, Object> data = FileUtil.saveFile(file);
			resp.setData(data);
		} catch (Exception e) {
			logger.error("上传文件出错：{}", ExceptionUtils.getStackTrace(e));
			resp.setMsg("文件上传失败");
			resp.setBizCode(ActionResult.BIZCODE_ERROR);
		}
		
		String json = new Gson().toJson(resp);
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();
	}


	 

}
