package com.dxyisgod.redisUI.laji;
//package com.dxyisgod.redisUI.util;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.dxyisgod.redisUI.exception.RedisException;
//
///*
// * redis操作类(使用IO流)
// */
public class RedisUtil {
//
//	private static final String SPACE = " ";
//	private static final String EMPTY = "";
//
//	// redis实例
//	private static RedisClient redis;
//
//	/*
//	 * 初始化连接
//	 */
//	public static boolean init(String host, int port, String pwd) {
//		try {
//			redis = new RedisClient(host, port);
//			if (null != pwd && !EMPTY.equals(pwd)) {
//				// 密码输错或者无需密码都会抛异常
//				sendCommandThrowable("auth" + SPACE + pwd);
//			}
//		} catch (RedisException e) {
//			e.printStackTrace();
//			return false;
//		}
//
//		return true;
//	}
//
//	/*
//	 * 默认发送指令方法，出异常返回null
//	 */
//	public static Object sendCommand(final String command) {
//		try {
//			return redis.sendCommand(command);
//		} catch (RedisException e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
//
//	/*
//	 * 发送指令方法，出异常不做处理
//	 */
//	public static Object sendCommandThrowable(final String command) throws RedisException {
//		return redis.sendCommand(command);
//	}
//
//	/*
//	 * 发送指令方法，出异常则返回异常信息
//	 */
//	public static Object sendCommandUseErrmsg(final String command) {
//		try {
//			return redis.sendCommand(command);
//		} catch (RedisException e) {
//			e.printStackTrace();
//			return "ERROR!"+e.getMessage();
//		}
//	}
//
//	/*
//	 * 在不知道或者无视key的类型时获取其值
//	 */
//	public static Object getWithoutType(final String key) {
//		// 获取key的类型
//		String type = (String) sendCommandUseErrmsg("type" + SPACE + key);
//		// 根据类型发送对应的指令
//		if ("none".equals(type)) {
//			return "no this key --" + key;
//		} else if ("string".equals(type)) {
//			return sendCommandUseErrmsg("get" + SPACE + key);
//		} else if ("list".equals(type)) {
//			return sendCommandUseErrmsg("lrange" + SPACE + key + SPACE + 0 + SPACE + Long.MAX_VALUE);
//		} else if ("hash".equals(type)) {
//			return sendCommandUseErrmsg("hgetAll" + SPACE + key);
//		} else if ("set".equals(type)) {
//			return sendCommandUseErrmsg("smembers" + SPACE + key);
//		} else if ("zset".equals(type)) {
//			return sendCommandUseErrmsg("zrange" + SPACE + key + SPACE + 0 + SPACE + Long.MAX_VALUE);
//		} else {
//			// 该分支条件为获取类型时出错，此时type里存的是错误信息
//			return type;
//		}
//	}
//
//	/*
//	 * 批量提交方法
//	 */
//	public static List<Object> commit(final String text) {
//		// 将指令以行分离，并无视空行及注释
//		String[] commands = text.split("\n|\r");
//		List<String> commandlist = new ArrayList<>();
//		for (String str : commands) {
//			if (!EMPTY.equals(str.trim()) && !str.startsWith("#")) {
//				commandlist.add(str);
//			}
//		}
//		// 循环获取结果
//		List<Object> resultlist = new ArrayList<>();
//		commandlist.forEach(command -> resultlist.add(sendCommandUseErrmsg(command)));
//		return resultlist;
//	}
}
