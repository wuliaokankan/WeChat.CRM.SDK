package com.jubotech.framework.netty.utils;

import com.alibaba.fastjson.JSONObject;
import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.util.JsonFormat;

import Jubo.JuLiao.IM.Wx.Proto.ErrorMessageOuterClass.ErrorMessage;
import Jubo.JuLiao.IM.Wx.Proto.TransportMessageOuterClass.EnumErrorCode;
import Jubo.JuLiao.IM.Wx.Proto.TransportMessageOuterClass.EnumMsgType;
import Jubo.JuLiao.IM.Wx.Proto.TransportMessageOuterClass.TransportMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class MessageUtil {

	/**
	 * 发送消息
	 * @param ctx
	 * @param msgType
	 * @param accessToken
	 * @param refMsgId
	 * @param resp
	 */
	public static void sendMsg(ChannelHandlerContext ctx, EnumMsgType msgType,String accessToken,Long refMsgId, Message resp) {
		Message msg = MessageUtil.getMessage(msgType,accessToken,refMsgId, resp);
		ctx.channel().writeAndFlush(msg);
	}
	
	/**
	 * 发送json字符串消息
	 * @param ctx
	 * @param msgType
	 * @param accessToken
	 * @param refMsgId
	 * @param resp
	 */
	public static void sendJsonMsg(ChannelHandlerContext ctx, EnumMsgType msgType,String accessToken,Long refMsgId,Message resp) {
		//Message msg = MessageUtil.getMessage(msgType,accessToken,refMsgId, resp);
		//String json = JsonFormat.printToString(msg);
		//ctx.channel().writeAndFlush(json);
		String json = null;
		if(null != resp){
			try {
				json = JsonFormat.printer().print(resp);
			} catch (InvalidProtocolBufferException e) {
				e.printStackTrace();
			}
		}
		JSONObject obj  = new JSONObject();
		obj.put("msgType", msgType.name());
		obj.put("message", json);
		ctx.channel().writeAndFlush(new TextWebSocketFrame(obj.toJSONString()));
	}
	
	
	
	/**
	 * 发送错误消息
	 * @param ctx
	 * @param errorCode
	 * @param errMsg
	 */
	public static void sendErrMsg(ChannelHandlerContext ctx,EnumErrorCode errorCode,String errMsg) {
	    ErrorMessage resp = MessageUtil.getErrMessage(errorCode,errMsg);
	    sendMsg(ctx,EnumMsgType.Error,null,null, resp);
	}
	
	/**
	 * 发送json错误消息
	 * @param ctx
	 * @param errorCode
	 * @param errMsg
	 */
	public static void sendJsonErrMsg(ChannelHandlerContext ctx,EnumErrorCode errorCode,String errMsg) {
	    ErrorMessage resp = MessageUtil.getErrMessage(errorCode,errMsg);
	    sendJsonMsg(ctx,EnumMsgType.Error,null,null, resp);
	}
	
	/**
	 * 封装错误消息消息体
	 */
	private static ErrorMessage getErrMessage(EnumErrorCode errorCode,String  errorMsg) {
		return	ErrorMessage.newBuilder()
				.setErrorCode(errorCode)
				.setErrorMsg(errorMsg)
				.build();
	}
	
	
	/**
	 * 封装base消息体
	 * @param type
	 * @param accessToken
	 * @param refMsgId
	 * @param resp
	 * @return
	 */
	private static Message getMessage(EnumMsgType type,String accessToken,Long refMsgId, Message resp) {
		TransportMessage.Builder builder = TransportMessage.newBuilder();
		
		builder.setId(MsgIdBuilder.getId());
		 
		if (null != accessToken) {
			builder.setAccessToken(accessToken);
		}
		if (null != type) {
			builder.setMsgType(type);
		}
		if (null != refMsgId) {
			builder.setRefMessageId(refMsgId);
		}
		if (null != resp) {
			builder.setContent(Any.pack(resp));
		}
		return builder.build();
	}
 
}
