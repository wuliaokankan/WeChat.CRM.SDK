package com.jubotech.framework.netty.handler.socket;

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
import com.jubotech.framework.netty.utils.NettyConnectionUtil;

import Jubo.JuLiao.IM.Wx.Proto.TransportMessageOuterClass.EnumErrorCode;
import Jubo.JuLiao.IM.Wx.Proto.TransportMessageOuterClass.EnumMsgType;
import Jubo.JuLiao.IM.Wx.Proto.TransportMessageOuterClass.TransportMessage;
import Jubo.JuLiao.IM.Wx.Proto.WeChatOfflineNotice.WeChatOfflineNoticeMessage;
import io.netty.channel.ChannelHandlerContext;

@Service
public class WeChatOfflineNoticeHandler{
	private  final Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private WeChatAccountService weChatAccountService;
	@Autowired
	private AccountService accountService;
	/**
	 * 手机客户端微信下线通知
	 * @param ctx
	 * @param vo
	 */
    public  void handleMsg(ChannelHandlerContext ctx, TransportMessage vo) {
        try {
        	WeChatOfflineNoticeMessage  req = vo.getContent().unpack(WeChatOfflineNoticeMessage.class);
        	log.info(JsonFormat.printer().print(req)); 
        	WeChatAccountInfo  accountInfo =  weChatAccountService.findWeChatAccountInfoByDeviceid(req.getIMEI());
    		if(null != req && null != accountInfo){
				// 把消息转发给pc端
				// a、根据wechatId找到accountid
				// b、通过accountid找到account
				// c、通过account账号找到通道
				WeChatAccountInfo account = weChatAccountService.findWeChatAccountInfoByWeChatId(req.getWeChatId());
				if (null != account && null != account.getAccountid() && 1 != account.getIslogined()) {
						AccountInfo accInfo = accountService.findAccountInfoByid(account.getAccountid());
						if (null != accInfo) {
							// 转发给pc端
							ChannelHandlerContext chx = NettyConnectionUtil.getClientChannelHandlerContextByUserId(accInfo.getAccount());
							if (null != chx) {
								MessageUtil.sendJsonMsg(chx, EnumMsgType.WeChatOfflineNotice, NettyConnectionUtil.getNettyId(chx),null, req);
							}
						}
						 
				}
				
				accountInfo.setIsonline(1);//下线
				weChatAccountService.updateOnline(accountInfo);
				
    			//3、告诉客户端消息已收到
	    		MessageUtil.sendMsg(ctx, EnumMsgType.MsgReceivedAck, vo.getAccessToken(), vo.getId(), null);
    		}else{
    			MessageUtil.sendErrMsg(ctx, EnumErrorCode.InvalidParam, Constant.ERROR_MSG_ILLEGALDEVICE);
    		}
    		 
        } catch (Exception e) {
            e.printStackTrace();
            MessageUtil.sendErrMsg(ctx, EnumErrorCode.InvalidParam, Constant.ERROR_MSG_DECODFAIL);
        }
    }
   
}