package com.dxyisgod.redisUI.data;

import com.dxyisgod.redisUI.Application;

/*
 * 存储各资源文件夹的本地路径
 */
public class Paths {

	//图片文件夹
	public static String imgPath;
	//图标文件夹
	public static String iconPath;
	//配置文件夹
	public static String confPath;
	//sql文件夹
	public static String rsqlPath;
	
	public static void init(String[] args){
		//获取程序根路径(不同运行方式获取方法不同，笨方法，如有更好的方法，欢迎指出，感激不尽)
		String rootPath;
		if(args.length!=0 && "exe".equals(args[0])){
			//exe
			String root = Application.class.getClassLoader().getResource("com/dxyisgod/redisUI").getFile();
			rootPath = root.replace("/lib/redisUI.jar!/com/dxyisgod/redisUI", "").replace("file:/", "");
		}else if(args.length!=0 && "dev".equals(args[0])){
			//dev
			String root = Application.class.getResource("Application.class").getFile();
			rootPath = root.replace("/bin/com/dxyisgod/redisUI/Application.class", "");
		}else{
			//jar
			String root = System.getProperty("java.class.path").replace("lib\\redisUI.jar", "");
			rootPath = root.replace("lib\\redisUI.jar", "");
		}
		
		imgPath = rootPath + "/img/";
		iconPath = imgPath + "icon/";
		confPath = rootPath + "/conf/";
		rsqlPath = rootPath + "/rsql/";
	}
	
	
}
