package com.utils.WeiXinUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * ID Generator.
 * 
 * @author GuoLin
 *
 */
public class IdGenerator {

    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyMMddHHmmssSS");

    private static int seq = 0;

    private static final int MAX = 999;

    /**
     * Generate a ID on milliseconds.
     * @return ID
     */
    public static synchronized String seq() {
    	if (seq == MAX) {
    		seq = 0;
    	} else {
    		seq++;
    	}
    	return DATE_FORMAT.format(new Date()) + seq;
    }

    /**
     * Generate a UUID.
     * @return UUID
     */
    public static String uuid(){ 
        return UUID.randomUUID().toString().replaceAll("-","");
    } 

}
