package com.dxyisgod.redisUI;

import com.dxyisgod.redisUI.compent.MainUI;
import com.dxyisgod.redisUI.compent.dialog.LoginDialog;
import com.dxyisgod.redisUI.data.Paths;

/*
 * 程序入口
 */
public class Application {

	public static MainUI mainUI;
	
	public static void main(String[] args) {
		//初始化
		init(args);
		//打开一个主界面
		mainUI = new MainUI();
		//打开一个登陆框
		new LoginDialog();
	}
	
	static void init(String[] args) {
		//获取各文件夹的本地路径
		Paths.init(args);
	}

}
