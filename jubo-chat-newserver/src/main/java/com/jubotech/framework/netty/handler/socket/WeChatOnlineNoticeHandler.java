package com.jubotech.framework.netty.handler.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.protobuf.util.JsonFormat;
import com.jubotech.business.web.domain.PhoneInfo;
import com.jubotech.business.web.domain.WeChatAccountInfo;
import com.jubotech.business.web.service.PhoneService;
import com.jubotech.business.web.service.WeChatAccountService;
import com.jubotech.framework.netty.common.Constant;
import com.jubotech.framework.netty.utils.MessageUtil;
import com.jubotech.framework.netty.utils.NettyConnectionUtil;

import Jubo.JuLiao.IM.Wx.Proto.TransportMessageOuterClass.EnumErrorCode;
import Jubo.JuLiao.IM.Wx.Proto.TransportMessageOuterClass.EnumMsgType;
import Jubo.JuLiao.IM.Wx.Proto.TransportMessageOuterClass.TransportMessage;
import Jubo.JuLiao.IM.Wx.Proto.WeChatOnlineNotice.WeChatOnlineNoticeMessage;
import io.netty.channel.ChannelHandlerContext;

@Service
public class WeChatOnlineNoticeHandler{
	private  final Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private WeChatAccountService weChatAccountService;
	
	@Autowired
	private PhoneService phoneService;
	 
	/** 
	 * 微信上线通知 
	 * @param ctx
	 * @param vo
	 */
    public  void handleMsg(ChannelHandlerContext ctx, TransportMessage vo) {
        try {
        	WeChatOnlineNoticeMessage req = vo.getContent().unpack(WeChatOnlineNoticeMessage.class);
        	log.info(JsonFormat.printer().print(req));
        	//1、校验用户信息
        	if(null != req){
	        	//2、存储微信全局id 与通道
	        	NettyConnectionUtil.registerUserid(req.getWeChatId(),ctx);
	        	 
	    		WeChatAccountInfo  accountInfo =  weChatAccountService.findWeChatAccountInfoByDeviceid(req.getIMEI());
	    		PhoneInfo  pi = phoneService.getPhoneInfoByDeviceid(req.getIMEI());
        		if(null != accountInfo && null != accountInfo.getWechatid()){
        			//是否还是之前的那个微信号
        			if(!accountInfo.getWechatid().equals(req.getWeChatId())){
        				updateAccountInfo(req, accountInfo);
        			}
        			if(null != pi && null != pi.getAccountid()){
        				accountInfo.setAccountid(pi.getAccountid());
        			}
        			//改为上线状态
        			accountInfo.setIsonline(0);//上线
    				weChatAccountService.updateOnline(accountInfo);
        			
        			//3、告诉客户端消息已收到
    	    		MessageUtil.sendMsg(ctx, EnumMsgType.MsgReceivedAck, vo.getAccessToken(), vo.getId(), null);
        		}else{
        			if(null != pi){
        				accountInfo = new WeChatAccountInfo(req.getWeChatId(), req.getWeChatNo(), req.getWeChatNick(), req.getGenderValue(),
            					req.getAvatar(), req.getCountry(), req.getProvince(), req.getCity(), 0, req.getIMEI(), pi.getCid(), pi.getAccountid());
        				weChatAccountService.insert(accountInfo);
        				//3、告诉客户端消息已收到
        	    		MessageUtil.sendMsg(ctx, EnumMsgType.MsgReceivedAck, vo.getAccessToken(), vo.getId(), null);
        			}else{
        			    MessageUtil.sendErrMsg(ctx, EnumErrorCode.InvalidParam, Constant.ERROR_MSG_ILLEGALDEVICE);
        			}
        		}
        		 
        	} 
         
        } catch (Exception e) {
            e.printStackTrace();
            MessageUtil.sendErrMsg(ctx, EnumErrorCode.InvalidParam, Constant.ERROR_MSG_DECODFAIL);
        }
    }
	private void updateAccountInfo(WeChatOnlineNoticeMessage req, WeChatAccountInfo accountInfo) {
		accountInfo.setWechatid(req.getWeChatId());
		accountInfo.setWechatno(req.getWeChatNo());
		accountInfo.setWechatnick(req.getWeChatNick());
		accountInfo.setGender(req.getGenderValue());
		accountInfo.setCountry(req.getCountry());
		accountInfo.setCity(req.getCity());
		accountInfo.setAvatar(req.getAvatar());
		accountInfo.setProvince(req.getProvince());
		weChatAccountService.update(accountInfo);
	}
}