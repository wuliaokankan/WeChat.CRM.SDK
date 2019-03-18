package com.jubotech.framework.netty.client;

public class NettyClient {

    private static final int PORT = 11087;
    private static final String HOST = "127.0.0.1";
 
    public static void main(String[] args) throws Exception {
        NettyConnect.connect(HOST, PORT,new ProtoBufClientHandler1());
        //NettyConnect.connect(HOST, PORT,new ProtoBufClientHandler2());
        //NettyConnect.connect(HOST, PORT,new ProtoBufClientHandler3());
    }
}