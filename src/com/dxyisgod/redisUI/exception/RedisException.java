package com.dxyisgod.redisUI.exception;

/*
 * redis异常
 */
public class RedisException extends Exception {

	private static final long serialVersionUID = 7091999729647438443L;

	public RedisException() {
		// TODO Auto-generated constructor stub
	}

	public RedisException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public RedisException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public RedisException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public RedisException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
