package com.jubotech.framework.netty.handler.websocket;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.protobuf.util.JsonFormat;
import com.jubotech.business.web.domain.AccountInfo;
import com.jubotech.business.web.domain.WeChatAccountInfo;
import com.jubotech.business.web.service.AccountService;
import com.jubotech.business.web.service.WeChatAccountService;
import com.jubotech.framework.netty.common.Constant;
import com.jubotech.framework.netty.utils.MessageUtil;

import Jubo.JuLiao.IM.Wx.Proto.GetWeChatsReq.GetWeChatsReqMessage;
import Jubo.JuLiao.IM.Wx.Proto.GetWeChatsRsp.GetWeChatsRspMessage;
import Jubo.JuLiao.IM.Wx.Proto.GetWeChatsRsp.WeChatRspMessage;
import Jubo.JuLiao.IM.Wx.Proto.TransportMessageOuterClass.EnumAccountType;
import Jubo.JuLiao.IM.Wx.Proto.TransportMessageOuterClass.EnumErrorCode;
import Jubo.JuLiao.IM.Wx.Proto.TransportMessageOuterClass.EnumMsgType;
import Jubo.JuLiao.IM.Wx.Proto.TransportMessageOuterClass.TransportMessage;
import io.netty.channel.ChannelHandlerContext;

@Service
public class GetWeChatsReqWebsocketHandler{
	Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private WeChatAccountService weChatAccountService;
	@Autowired
	private AccountService accountService;
	/** 
	 * 拉取当前微信个人号列表响应 
	 * @param ctx
	 * @param vo
	 */
    public  void handleMsg(ChannelHandlerContext ctx ,TransportMessage vo, String contentJsonStr) {
        try {
        	log.info(contentJsonStr);
        	GetWeChatsReqMessage.Builder bd = GetWeChatsReqMessage.newBuilder();
        	JsonFormat.parser().merge(contentJsonStr, bd);
    		GetWeChatsReqMessage req = bd.build();
    		//GetWeChatsReqMessage req = vo.getContent().unpack(GetWeChatsReqMessage.class);
        	//1、校验用户信息
        	log.info("账号："+req.getUnionId());
        	if(null != req){
        		Integer id = (int) req.getUnionId();
        		
        		AccountInfo  account = accountService.findAccountInfoByid(id);
        		if(null != account){
        			
        			List<WeChatAccountInfo> list =	weChatAccountService.findWeChatAccountInfo(account.getCid(), account.getId());
//        			if(null == list ||  list.isEmpty()){
//        				list =	weChatAccountService.findWeChatAccountInfo(account.getCid(), null);
//        			}
            		List<WeChatRspMessage> weChatrspList = getWechatList(list);
            		 
            		GetWeChatsRspMessage.Builder builder = GetWeChatsRspMessage.newBuilder();
            		builder.setUnionId(req.getUnionId());
            		builder.setAccountType(EnumAccountType.SubUser);
            		builder.setSupplierId(account.getCid());
            		if(null != weChatrspList && weChatrspList.size()>0){
            			builder.addAllWeChats(weChatrspList);
            		}
            		
            		GetWeChatsRspMessage resp = builder.build();
            		
            		//3、告诉PC客户端消息已收到
    	    		MessageUtil.sendJsonMsg(ctx, EnumMsgType.GetWeChatsRsp, vo.getAccessToken(), vo.getId(), resp);
        		}else{
        			MessageUtil.sendJsonErrMsg(ctx, EnumErrorCode.InvalidParam,Constant.ERROR_MSG_PARAMERROR);
        		}
        	} 
        } catch (Exception e) {
            e.printStackTrace();
            MessageUtil.sendJsonErrMsg(ctx, EnumErrorCode.InvalidParam, Constant.ERROR_MSG_DECODFAIL);
        }
    }
    
    /**
     * 封装返回对象
     * @param list
     * @return
     */
    private static List<WeChatRspMessage>  getWechatList(List<WeChatAccountInfo> list){
    	List<WeChatRspMessage> weChatrspList = null;
        if(null != list && list.size()>0){
        	weChatrspList = new ArrayList<>();
    		for(int i=0;i<list.size();i++){
    			WeChatAccountInfo info = list.get(i);
    			boolean online = false;
    			if(null != info.getIsonline() && info.getIsonline()==0){
    				online = true;
    			}
    			boolean logined = false;
    			if(null != info.getIslogined() && info.getIslogined()==0){
    				logined = true;
    			}
    			WeChatRspMessage.Builder builder = WeChatRspMessage.newBuilder();
    	        if(null != info.getWechatid()){
    	        	builder.setWeChatId(info.getWechatid());
    	        }
    	        if(null != info.getWechatno()){
    	        	builder.setWeChatNo(info.getWechatno());
    	        }
    	        if(null !=info.getWechatnick()){
    	        	builder.setWeChatNick(info.getWechatnick());
    	        }
    	        if(null != info.getAvatar()){
    	        	builder.setAvatar(info.getAvatar());
    	        }
    	        if(null != info.getCountry()){
    	        	builder.setCountry(info.getCountry());
    	        }
    	        if(null != info.getProvince()){
    	        	builder.setProvince(info.getProvince());
    	        }
    	        if(null != info.getCity()){
    	        	builder.setCity(info.getCity());
    	        }
    	        if(null != info.getGender()){
    	        	builder.setGenderValue(info.getGender());
    	        }
    	        if(null != info.getLogin_time()){
    	        	builder.setLoginTime(info.getLogin_time().getTime());
    	        }
    	        if(null != info.getModify_time()){
    	        	builder.setModifyTime(info.getModify_time().getTime());
    	        }
    	        if(null != info.getDeviceid()){
    	        	builder.setDeviceName(info.getDeviceid());
    	        }
    	        if(null != info.getAccountid()){
    	        	builder.setLoginUnionId(info.getAccountid());
    	        }
    	        builder.setIsOnline(online);
    	        builder.setIsLogined(logined);
    	        builder.setIsDelete(false);
    	        builder.setIsUpgrading(false);
    					  
    			weChatrspList.add(builder.build());
    		}
        }
		
		return weChatrspList;
    }
}