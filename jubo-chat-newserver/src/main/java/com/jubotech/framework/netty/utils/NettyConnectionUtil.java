package com.jubotech.framework.netty.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jubotech.business.web.domain.AccountInfo;
import com.jubotech.business.web.domain.WeChatAccountInfo;
import com.jubotech.business.web.service.AccountService;
import com.jubotech.business.web.service.WeChatAccountService;
import com.jubotech.framework.proxy.ProxyUtil;

import io.netty.channel.ChannelHandlerContext;

public class NettyConnectionUtil {
	/**
	 * key为nettyid 
	 * value 为deviceid
	 * 用于存贮deviceid
	 */
	public static Map<String, String> nettyid_deviceid = new HashMap<String, String>();
	/**
	 * key 为deviceid 
	 * value 为channel
	 * 用于存贮通道
	 */
	public static Map<String, ChannelHandlerContext> deviceid_nettyChannel = new HashMap<String, ChannelHandlerContext>();
  
	
	/**
	 *key 为nettyid 
	 *value 为userid
	 *用于存贮 userid
	 */
	public static Map<String, String> nettyId_userId = new HashMap<String, String>();
	
	/**
	 * key  userId  微信唯一id 或者 pc端账号名
	 * value  ChannelHandlerContext
	 */
	public static Map<String, ChannelHandlerContext> userId_nettyChannel = new HashMap<String, ChannelHandlerContext>();
	
	
	 
	public static void saveDeviceChannel(ChannelHandlerContext cx, String deviceid) {
		String nettyid = getNettyId(cx);
		String dvid = getDeviceidByNettyid(nettyid);
		if(null != dvid){
			nettyid_deviceid.remove(nettyid);
		}
		ChannelHandlerContext cc = getNettyChannelByDeviceid(deviceid);
		if(null != cc){
			deviceid_nettyChannel.remove(deviceid);
		}
		
		nettyid_deviceid.put(nettyid, deviceid);
		deviceid_nettyChannel.put(deviceid, cx);
	}

 
	/**
	 * 通过nettyid获取deviceid
	 * @param nettyid
	 * @return
	 */
	public static String getDeviceidByNettyid(String nettyId) {
		return nettyid_deviceid.get(nettyId);
	}

	/**
	 * 通过deviceid获取通道
	 * @return
	 */
	public static ChannelHandlerContext getNettyChannelByDeviceid(String deviceid) {
		ChannelHandlerContext ctx = deviceid_nettyChannel.get(deviceid);
		if(null != ctx){
			return ctx;
		}else{
			return null;
		}
	}
	 
	/**
	 * 删除通道
	 */
	public static void removeChannel(ChannelHandlerContext cx) {
		//清除设备通道
		String nettyid = getNettyId(cx);
		String deviceid = getDeviceidByNettyid(nettyid);
		nettyid_deviceid.remove(nettyid);
		WeChatAccountService wechatservice = ProxyUtil.getBean("weChatAccountService", WeChatAccountService.class);
		if(null !=deviceid && !"".equals(deviceid)){
			deviceid_nettyChannel.remove(deviceid);
			if(null != wechatservice){
				//手机端下线
				WeChatAccountInfo wechat = wechatservice.findWeChatAccountInfoByDeviceid(deviceid);
				if(null != wechat){//手机设备断线，微信也下线
					wechat.setIsonline(1);
					wechat.setIslogined(1);
					//wechat.setAccountid(null);
					wechatservice.updateOnlineLogined(wechat);
				}
			}
		}
		
		//清除微信通道
		String userid =  nettyId_userId.get(nettyid);
		if(null != userid){
			userId_nettyChannel.remove(userid);
			nettyId_userId.remove(nettyid);
			
			//pc端断线，logined与acccountid状态修改 
			AccountService accountService = ProxyUtil.getBean("accountService", AccountService.class);
			if(null != accountService){
				AccountInfo  acc = accountService.findAccountInfoByAccount(userid);
				if(null != acc){
					List<WeChatAccountInfo>  list = wechatservice.findWeChatAccountInfo(null,acc.getId());
					if(null != list && list.size()>0){
						for(int i=0;i<list.size();i++){
							WeChatAccountInfo wai = list.get(i);
							wai.setIslogined(1);
							//wai.setAccountid(null);
							wechatservice.updateLogined(wai);
						}
					}
				}
			}
		}
		
	}
	
	/**
	 * 客户端退出时调用，清理数据
	 * 
	 * @param ctx
	 */
	public static void exit(ChannelHandlerContext ctx) {
		removeChannel(ctx);
	}

	/**
	 * 注册真实用户，客户端以imei为准、pc端以账号用户名为准
	 * @param userId
	 * @param cx
	 */
	public static void registerUserid(String userId, ChannelHandlerContext cx) {
		 
		ChannelHandlerContext chc = userId_nettyChannel.get(userId);
		if(null != chc){
			userId_nettyChannel.remove(userId);
		}
		String nettyid = getNettyId(cx);
		String uid = nettyId_userId.get(nettyid);
		if(null != uid){
			nettyId_userId.remove(nettyid);
		}
		userId_nettyChannel.put(userId, cx);
		nettyId_userId.put(nettyid, userId);
		
	}
 
 
	public static ChannelHandlerContext getClientChannelHandlerContextByUserId(String userId) {
		ChannelHandlerContext chc = userId_nettyChannel.get(userId);
		if(null != chc){
			return chc;
		}else{
			return null;
		}
	}
	
	public static String getNettyId(ChannelHandlerContext cx) {
		return cx.channel().id().asShortText();
	}
	
}