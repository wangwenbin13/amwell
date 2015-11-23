package com.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;


/**
 * 线程池工具类
 * @author Nevyn
 *
 */
public final class ThreadPoolUtil {
	private static Logger logger = Logger.getLogger(ThreadPoolUtil.class);
    public static ExecutorService service;

    private ThreadPoolUtil() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
        		if(service!=null){
        			//关闭线程池
        			service.shutdown();
        		}
            }
        });
    }
    
    public static ExecutorService createExecutor(int poolSize) {
    	if (service != null) {
    		return service;
    	}
    	//创建固定大小的线程池，poolSize为同时运行的线程个数
    	service = Executors.newFixedThreadPool(poolSize);
    	if (service instanceof ThreadPoolExecutor) {
    		ThreadPoolExecutor exc = (ThreadPoolExecutor) service;
    		exc.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    	}
    	return service;
    }

    public static void submit(Runnable task) {
    	int poolSize = StringUtils.isBlank(PropertyManage.get("executorPoolSize"))?10:Integer.parseInt(PropertyManage.get("executorPoolSize"));
    	ExecutorService service =  createExecutor(poolSize);
    	//将线程放入池中进行执行
    	service.submit(task); 
    }
}

