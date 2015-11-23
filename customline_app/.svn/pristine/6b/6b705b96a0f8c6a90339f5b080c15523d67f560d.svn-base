package com.pig84.ab.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Event.
 * 
 * @author GuoLin
 *
 */
public enum Event {
	
	/** 注册. */
	REGISTER,
	
	/** 买票. */
	BUYTICKET,
	
	/** 第三方操作. */
	SINGLEHANDLE,
	
	/** 第三方定时. */
	TIMINGHANDLE;
	
	private static final Map<Event, Set<Listener>> LISTENERS = new HashMap<Event, Set<Listener>>();
	
	public void trigger(String key, Object value) {
		trigger(Collections.singletonMap(key, value));
	}
	
	public void trigger(Map<String, Object> data) {
		Set<Listener> listeners = LISTENERS.get(this);
		if (listeners == null) {
			return;
		}
		for (Listener listener : listeners) {
			listener.trigger(this, data);
		}
	}
	
	public void triggerAsynchronous(final String key,final Object value) {
		ThreadPoolUtil.submit(new Runnable() {
			@Override
			public void run() {
				trigger(key,value);
			}
		});
	}
	
	public void triggerAsynchronous(final Map<String, Object> data) {
		ThreadPoolUtil.submit(new Runnable() {
			@Override
			public void run() {
				trigger(data);
			}
		});
	}
	
	public static void registerListener(Event event, Listener listener) {
		Set<Listener> listeners = LISTENERS.get(event);
		if (listeners == null) {
			listeners = new HashSet<Listener>();
			LISTENERS.put(event, listeners);
		}
		listeners.add(listener);
	}
	
	public static void removeListener(Event event, Listener listener) {
		Set<Listener> listeners = LISTENERS.get(event);
		if (listeners == null) {
			return;
		}
		listeners.remove(listener);
	}
	
	public static void removeListener(Event event) {
		LISTENERS.remove(event);
	}
	
	/**
	 * Event Listener.
	 * 
	 * @author GuoLin
	 *
	 */
	public static interface Listener {

		public void trigger(Event event, Map<String, Object> data);
		
	}
	
}
