package com.paleo.blog.tools.uuid;

import java.util.UUID;

public class UUIDUtils{
	/**
	 * 获得UUID 
	 * @return
	 */	
	public static String getUUID(){ 
        //去掉“-”符号 8ab4298c-80d6-47cd-9e3d-89698605b916
        return UUID.randomUUID().toString().replaceAll("-", "");
    } 
}
