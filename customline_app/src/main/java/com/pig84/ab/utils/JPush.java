package com.pig84.ab.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.APIConnectionException;
import cn.jpush.api.common.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

/**
 * JPush.
 * 
 * @author GuoLin
 *
 */
public class JPush {

    private static final Logger logger = LoggerFactory.getLogger(JPush.class);

	private static final String APPKEY ="207caa51def23a9ca6f0a9c3";

	private static final String MASTER_SECURE = "2545a2c77ee4aa18e1dbea14";
	
	private static final JPushClient client = new JPushClient(MASTER_SECURE, APPKEY, 3);
	
	public static boolean alert(String alert, String... mobileNumbers) {
		PushPayload payload = PushPayload.newBuilder()
			.setPlatform(Platform.all())
			.setNotification(Notification.alert(alert))
			.setAudience(Audience.alias(mobileNumbers))
			.build();

    	try {
			PushResult result = client.sendPush(payload);
			logger.debug("Push result: {}", result);
			return true;
		} catch (APIConnectionException e) {
			logger.warn("Send alert failed, see error below.");
		} catch (APIRequestException e) {
			logger.warn("Send alert failed, see error below.");
		}
    	return false;
	}
	
	public static boolean sendMessage(Object message, String... mobileNumbers) {
		return sendMessage(Json.toJson(message), mobileNumbers);
	}

    public static boolean sendMessage(String message, String... mobileNumbers) {
    	PushPayload payload = PushPayload.newBuilder()
			.setPlatform(Platform.all())
    		.setMessage(Message.content(message))
    		.setAudience(Audience.alias(mobileNumbers))
    		.build();
    	try {
			PushResult result = client.sendPush(payload);
			logger.debug("Push result: {}", result);
			return true;
		} catch (APIConnectionException e) {
			logger.warn("Send message failed, see error below.");
		} catch (APIRequestException e) {
			logger.warn("Send message failed, see error below.");
		}
    	return false;
    }
    
}
