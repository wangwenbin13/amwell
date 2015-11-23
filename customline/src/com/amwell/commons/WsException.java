package com.amwell.commons;

/**
 * WebService异常
 * @author zhangqiang
 *
 */
public class WsException extends Exception {
	private static final long serialVersionUID = 1511592903851141246L;

	public WsException() {
	}

	public WsException(Exception e) {
		super(e);
	}
	
	public WsException(String message) {
		super(message);
	}

	public WsException(Throwable cause) {
		super(cause);
	}

	public WsException(String message, Throwable cause) {
		super(message, cause);
	}
}
