package com.jubotech.framework.netty.handler.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.protobuf.util.JsonFormat;
import com.jubotech.framework.netty.common.Constant;
import com.jubotech.framework.netty.utils.MessageUtil;
import com.jubotech.framework.netty.utils.NettyConnectionUtil;

import Jubo.JuLiao.IM.Wx.Proto.DeleteSNSNewsTask.DeleteSNSNewsTaskMessage;
import Jubo.JuLiao.IM.Wx.Proto.TransportMessageOuterClass.EnumErrorCode;
import Jubo.JuLiao.IM.Wx.Proto.TransportMessageOuterClass.EnumMsgType;
import Jubo.JuLiao.IM.Wx.Proto.TransportMessageOuterClass.TransportMessage;
import io.netty.channel.ChannelHandlerContext;

@Service
public class DeleteSNSNewsTaskWebsocketHandler{
	private  final Logger log = LoggerFactory.getLogger(getClass());
	/**
	 * 删除朋友圈
	 * @param ctx
	 * @param vo
	 */
    public  void handleMsg(ChannelHandlerContext ctx,TransportMessage vo, String contentJsonStr) {
        try {
        	log.info(contentJsonStr);
        	DeleteSNSNewsTaskMessage.Builder bd = DeleteSNSNewsTaskMessage.newBuilder();
        	JsonFormat.parser().merge(contentJsonStr, bd);
    		DeleteSNSNewsTaskMessage req = bd.build();
        	//DeleteSNSNewsTaskMessage req = vo.getContent().unpack(DeleteSNSNewsTaskMessage.class);
        	ChannelHandlerContext  chx = NettyConnectionUtil.getClientChannelHandlerContextByUserId(req.getWeChatId());
        	if(null != chx){
        	    //转发给手机端
        		MessageUtil.sendMsg(chx, EnumMsgType.DeleteSNSNewsTask, vo.getAccessToken(), null, req);
        		//告诉发送方 消息已经收到
        		MessageUtil.sendJsonMsg(ctx, EnumMsgType.MsgReceivedAck, vo.getAccessToken(), vo.getId(), null);
        	}else{
        		//对方不在线
        		MessageUtil.sendJsonErrMsg(ctx, EnumErrorCode.TargetNotOnline, Constant.ERROR_MSG_NOTONLINE);
        	}
        	 
        } catch (Exception e) {
            e.printStackTrace();
            MessageUtil.sendJsonErrMsg(ctx, EnumErrorCode.InvalidParam, Constant.ERROR_MSG_DECODFAIL);
        }
    }
}