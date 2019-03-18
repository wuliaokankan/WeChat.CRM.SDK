package com.jubotech.framework.netty.handler.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.protobuf.util.JsonFormat;
import com.jubotech.business.web.domain.CustomerInfo;
import com.jubotech.business.web.service.CustomerService;
import com.jubotech.business.web.service.PhoneService;
import com.jubotech.framework.netty.common.Constant;
import com.jubotech.framework.netty.utils.MessageUtil;
import com.jubotech.framework.netty.utils.NettyConnectionUtil;

import Jubo.JuLiao.IM.Wx.Proto.DeviceAuthReq.DeviceAuthReqMessage;
import Jubo.JuLiao.IM.Wx.Proto.DeviceAuthReq.DeviceAuthReqMessage.EnumAuthType;
import Jubo.JuLiao.IM.Wx.Proto.DeviceAuthRsp.DeviceAuthRspMessage;
import Jubo.JuLiao.IM.Wx.Proto.DeviceAuthRsp.DeviceAuthRspMessage.ExtraMessage;
import Jubo.JuLiao.IM.Wx.Proto.TransportMessageOuterClass.EnumAccountType;
import Jubo.JuLiao.IM.Wx.Proto.TransportMessageOuterClass.EnumErrorCode;
import Jubo.JuLiao.IM.Wx.Proto.TransportMessageOuterClass.EnumMsgType;
import Jubo.JuLiao.IM.Wx.Proto.TransportMessageOuterClass.TransportMessage;
import io.netty.channel.ChannelHandlerContext;

@Service
public class DeviceAuthReqHandler{
	private  final Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private CustomerService customerService;
	@Autowired
	private PhoneService phoneService; 
	
	/**
	 * 设备校验
	 * 如果校验通过存储token、imei建立连接
	 * @param ctx
	 * @param vo
	 */
    public  void handleMsg(ChannelHandlerContext ctx, TransportMessage vo) {
        try {
        	DeviceAuthReqMessage req = vo.getContent().unpack(DeviceAuthReqMessage.class);
        	log.info(JsonFormat.printer().print(req));
        	if(null != req){
        		//1、校验用户信息
            	if(req.getAuthType().equals(EnumAuthType.DeviceCode)){//设备码（用于手机客户端，此方式Credential应传入手机IMEI）
            		log.debug("手机端登陆imei："+req.getCredential());
            		CustomerInfo  customerInfo = customerService.getCustomerInfoByDeviceId(req.getCredential());
                	if(null != customerInfo){
                		//3、生成用户token信息
                    	String token = NettyConnectionUtil.getNettyId(ctx);
                    	log.info("token : "+token);
                    	
                    	//存储 设备id 和 通道信息
                    	NettyConnectionUtil.saveDeviceChannel(ctx, req.getCredential());
                    	 
                    	Integer supplierid = customerInfo.getId();
                    	String suppliername = customerInfo.getSuppliername();//商家名称
                    	 
                    	//回复消息  
                    	sendMsg(supplierid, suppliername,null,null, token, ctx, vo);
                    	 
                    	//修改登录时间
                    	phoneService.updateLoginTime(req.getCredential());
                    	 
                	}else{
                		sendErrMsg(ctx);
                	}
            	}else{
            		 MessageUtil.sendErrMsg(ctx, EnumErrorCode.NoRight, Constant.ERROR_MSG_VERIFYWAY);
            	}
        	}else{
        		sendErrMsg(ctx);
        	}
        } catch (Exception e) {
            e.printStackTrace();
            MessageUtil.sendErrMsg(ctx, EnumErrorCode.InvalidParam, Constant.ERROR_MSG_DECODFAIL);
        }
        
    }
    
    private void sendErrMsg(ChannelHandlerContext ctx){
    	MessageUtil.sendErrMsg(ctx, EnumErrorCode.NoRight, Constant.ERROR_MSG_DECODFAIL);
    }
    
    private void sendMsg(Integer  supplierid,String suppliername,Long unionid,String nickname,String token,ChannelHandlerContext ctx, TransportMessage vo){
     
    	ExtraMessage.Builder buider = ExtraMessage.newBuilder();
    	buider.setSupplierId(supplierid);
    	buider.setSupplierName(suppliername);
    	buider.setAccountType(EnumAccountType.SubUser);//账号类型  子账号 
    	
    	if(null != unionid && !StringUtils.isEmpty(nickname)){
    		buider.setUnionId(unionid);
    		buider.setNickName(nickname);
    	}
    	
    	ExtraMessage  ext = buider.build();
		DeviceAuthRspMessage resp = DeviceAuthRspMessage.newBuilder()
				.setAccessToken(token)
				.setExtra(ext)
				.build();
		
		
		MessageUtil.sendMsg(ctx, EnumMsgType.DeviceAuthRsp, null, vo.getId(), resp);
    	
    }
  
 
}