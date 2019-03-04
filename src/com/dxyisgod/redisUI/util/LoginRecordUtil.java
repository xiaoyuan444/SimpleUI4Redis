package com.dxyisgod.redisUI.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Properties;

import com.dxyisgod.redisUI.compent.dialog.ErrorDialog;
import com.dxyisgod.redisUI.data.Paths;

/*
 * 登录记录 读取、存储、修改工具类
 */
public class LoginRecordUtil {

	//记录文件
	private static final String LOGIN_RECORD_FILE = Paths.confPath + "login.record";
	
	/*
	 * 读取操作
	 */
	public static Properties read() {
		Properties props = new Properties();
		try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(LOGIN_RECORD_FILE), "UTF-8"))) {
			props.load(br);
			return props;
		}catch (FileNotFoundException e) {
			new ErrorDialog("登录记录文件未找到！");
			e.printStackTrace();
		} catch (IOException e) {
			new ErrorDialog("登录记录文件读取失败！");
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * 存储操作
	 */
	public static void write(Properties props) {
		try(PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(LOGIN_RECORD_FILE), "UTF-8"))) {
			props.store(pw, null);
		} catch (IOException e) {
			new ErrorDialog("<html>保存失败：<br/>登录配置文件写入失败！</html>");
			e.printStackTrace();
		}
	}
	
}
