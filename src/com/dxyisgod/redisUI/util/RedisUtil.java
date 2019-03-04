package com.dxyisgod.redisUI.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.exceptions.JedisException;

public class RedisUtil extends Jedis{
	
	private static RedisUtil redis;
	
	/*
	 * 私有化构造方法
	 */
	private RedisUtil() {
		super();
	}
	
	private RedisUtil(String ip, int port, int connectionTimeout, int soTimeout) {
		super(ip, port, connectionTimeout, soTimeout);
	}

	/*
	 * 初始化
	 */
	public static boolean init(String ip, int port, String pwd, int connectionTimeout, int soTimeout) {
		redis = new RedisUtil(ip, port, connectionTimeout, soTimeout);
		redis.getClient().setPassword(pwd);
		try {
			redis.connect();
		}catch (JedisException e) {
			e.printStackTrace();
			redis = null;
			return false;
		}
		return true;
	}
	
	/*
	 * 单例模式
	 */
	public static RedisUtil getInstance() {
		if(redis!=null) {
			return redis;
		}else {
			//如果没有init成功就执行操作的话会抛出NOAUTH异常
			redis = new RedisUtil();
			return redis;
		}
	}
	
	/*
	 * 通过反射Jedis的方法来执行命令
	 */
	private Object sendCommand(String command) throws Throwable{
		//空行直接返回空
		if (null == command || "".equals(command.trim())) {
			return "";
		}
		//分解指令，获得命令名称和参数
		String[] spilt = command.split(" ");
		String cmd = null;
		List<String> arglist = new ArrayList<>();
		int avilable = 0;
		for (String str : spilt) {
			if(!"".equals(str)) {
				if(avilable==0) {
					cmd = str;
				}else {
					arglist.add(str);
				}
				avilable++;
			}
		}
		Object[] args = new String[avilable-1];
		arglist.toArray(args);
		//通过Protocol.Command类来检验命令是否有效
		try {
			Protocol.Command.valueOf(cmd.toUpperCase());
		}catch(IllegalArgumentException iae) {
			throw new IllegalArgumentException("Error!No command found - "+cmd, iae) ;
		}
		
		//由反射获取方法
		Method m = null;
		Method[] ms = Jedis.class.getMethods();
		for (Method method : ms) {
			if(method.getName().equalsIgnoreCase(cmd) && method.getParameterTypes().length>0 && method.getParameterTypes()[0]==String.class) {
				m = method;
				break;
			}
		}
		try {
			return m.invoke(this, args);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e.getTargetException();
		}
	}
	
	/*
	 * 默认发送指令方法，出异常返回null
	 */
	public Object sendCommandDefaultNull(final String command) {
		try {
			return this.sendCommand(command);
		} catch (Throwable e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * 发送指令方法，出异常不做处理
	 */
	public Object sendCommandThrowable(final String command) throws Throwable {
		return this.sendCommand(command);
	}

	/*
	 * 发送指令方法，出异常则返回异常信息
	 */
	public Object sendCommandUseErrmsg(final String command) {
		try {
			return this.sendCommand(command);
		} catch (Throwable e) {
			e.printStackTrace();
			return "ERROR!"+e.getMessage();
		}
	}
	
	/*
	 * 在不知道或者无视key的类型时获取其值
	 */
	public Object getWithoutType(final String key) {
		try {
			// 获取key的类型
			String type = (String) this.type(key);
			// 根据类型发送对应的指令
			if ("none".equals(type)) {
				return "no this key --" + key;
			} else if ("string".equals(type)) {
				return this.get(key);
			} else if ("list".equals(type)) {
				return this.lrange(key, 0, Long.MAX_VALUE);
			} else if ("hash".equals(type)) {
				return this.hgetAll(key);
			} else if ("set".equals(type)) {
				return this.smembers(key);
			} else if ("zset".equals(type)) {
				return this.zrange(key, 0, Long.MAX_VALUE);
			}
		}catch (JedisException e) {
			e.printStackTrace();
			return "ERROR!"+e.getMessage();
		}
		return null;
	}

	/*
	 * 批量提交方法
	 */
	public List<Object> commit(final String text) {
		// 将指令以行分离，并无视空行及注释
		String[] commands = text.split("\n|\r");
		List<String> commandlist = new ArrayList<>();
		for (String str : commands) {
			if (!"".equals(str.trim()) && !str.trim().startsWith("#")) {
				commandlist.add(str);
			}
		}
		// 循环获取结果
		List<Object> resultlist = new ArrayList<>();
		commandlist.forEach(command -> resultlist.add(this.sendCommandUseErrmsg(command)));
		return resultlist;
	}
	
}
