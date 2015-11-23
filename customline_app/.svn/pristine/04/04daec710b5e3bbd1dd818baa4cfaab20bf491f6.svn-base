package com.pig84.ab.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Short Message.
 * 
 * @author GuoLin
 *
 */
public class Message {
	
	private static final Logger logger = LoggerFactory.getLogger(Message.class);
	
	private static final String URL = PropertyManage.get("pns.sms.send");

	private final String content;
	
	public Message(String content, Object... args) {
		this.content = String.format(content, args);
	}
	
	public void sendTo(String... mobiles) {
		String mobileText = String.join(",", mobiles);
		if (logger.isInfoEnabled()) {
			logger.info("Sending message to " + mobileText + " : '" + content + "'");
		}
		Http.post(URL, "content", content, "mobile", mobileText);  // We don't care whether message is successful
	}
	
}
