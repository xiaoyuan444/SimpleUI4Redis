package com.dxyisgod.redisUI.laji;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.dxyisgod.redisUI.exception.RedisException;

import redis.clients.jedis.Protocol;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.util.RedisInputStream;
import redis.clients.util.RedisOutputStream;
import redis.clients.util.SafeEncoder;

/*
 * Redis连接实例
 */
public class RedisClient {
	
	private static final String SPACE = " ";
	private static final String EMPTY = "";
	
	//输出流
	private RedisOutputStream ros;
	//输入流
	private RedisInputStream ris;

	/*
	 * 构造方法，只建立连接，不做密码校验
	 */
	@SuppressWarnings({"resource" })
	public RedisClient(String host, int port) throws RedisException {
		Socket socket = new Socket();
		try {
			socket.setReuseAddress(true);
	        socket.setKeepAlive(true);
	        socket.setTcpNoDelay(true);
	        socket.setSoLinger(true, 0);
			socket.connect(new InetSocketAddress(host, port), 30000);
			socket.setSoTimeout(30000);
			
			ros = new RedisOutputStream(socket.getOutputStream());
			ris = new RedisInputStream(socket.getInputStream());
		}catch (IOException e) {
			throw new RedisException("Error!Connect failed", e);
		}
	}
	
	/*
	 * 向服务端发送指令行并获取结果
	 *  特别注意：返回结果只有两种类型，一种是String，一种是List及嵌套，取决于Protocol.read(ris)方法
	 */
	public Object sendCommand(final String command) throws RedisException {
		//空行直接返回空
		if (null == command || EMPTY.equals(command.trim())) {
			return EMPTY;
		}
		//分解指令，获得命令名称和参数
		String[] spilt = command.split(SPACE);
		String cmd = null;
		List<String> arglist = new ArrayList<>();
		int avilable = 0;
		for (String str : spilt) {
			if(!EMPTY.equals(str)) {
				if(avilable==0) {
					cmd = str;
				}else {
					arglist.add(str);
				}
				avilable++;
			}
		}
		String[] args = new String[avilable-1];
		arglist.toArray(args);
		//通过Protocol.Command类来检验命令是否有效
		Protocol.Command method;
		try {
			method = Protocol.Command.valueOf(cmd.toUpperCase());
		}catch(IllegalArgumentException iae) {
			throw new RedisException("Error!No command found - "+cmd, iae) ;
		}
		//发送指令（该方法仅将指令做些处理并写到ros里，未flush）
		Protocol.sendCommand(ros, method, SafeEncoder.encodeMany(args));
		//冲刷输出流，正式发送指令
		try {
			ros.flush();
		} catch (IOException ioe) {
			throw new RedisException("Error!App throws an IOException", ioe);
		}
		//读取结果，会抛出redis自带的异常
		try {
			Object resp = Protocol.read(ris);
			return process(resp);
		}catch(JedisDataException jde) {
			throw new RedisException("Error!"+jde.getMessage(), jde);
		}
		
	}
	
	/*
	 * 对返回结果进行处理，这样做是因为Protocol.read(ris)方法返回的基本单元是byte[]，通过递归，将list等中的byte[]转为字符串
	 */
	@SuppressWarnings("unchecked")
	private Object process(final Object resp) {
		Object rtnObj = null;
		if(resp instanceof byte[]) {
			rtnObj = SafeEncoder.encode((byte[]) resp);
		}else if(resp instanceof List) {
			List<Object> convertlist = new ArrayList<>();
			((List<Object>) resp).forEach(a->convertlist.add(process(a)));
			rtnObj = convertlist;
		}
		return rtnObj;
	}
}
