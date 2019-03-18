package com.jubotech.framework.netty.utils;

import java.util.Random;

public class MsgIdBuilder {
    private static Long id = null;
    private MsgIdBuilder(){}
    public static Long getId(){
    	if(id == null){
    		id = Math.abs(new Random().nextLong());
    	}else{
    		id = id + 1;
    	}
    	
    	
    	return id;
    }
    
    
    public static void main(String[] args) {
    	for(int i =0;i<1000;i++){
    		System.out.println(MsgIdBuilder.getId());
    	}
	}
}
