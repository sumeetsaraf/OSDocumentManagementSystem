package com.oneshield.dms.common;

import java.sql.Timestamp;

public class DMSHelper {

    public static final long GIGABYTE = 1024L*1024L*1024L; 
    private static final long MAX_SIZE_GB = 1L;
    
    public static String getEscapaedTimeForCurrentTimestamp() {
	Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	return Long.toString(timestamp.toInstant().toEpochMilli());
    }
    
    public static boolean getSizeOfByteArray(byte[] byteArray) {
	long byteArraySizeInGb = byteArray.length/GIGABYTE;
	return byteArraySizeInGb<=MAX_SIZE_GB;
    }
    
}
