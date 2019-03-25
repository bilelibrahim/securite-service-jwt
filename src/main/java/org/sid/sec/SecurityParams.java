package org.sid.sec;

/**
 * Created by Bilel on 23/03/2019.
 */
public interface SecurityParams {
    public static final String JWT_HEADER_NAME = "Authorization";
    public static final String SECRET = "bilel.brahim@gmail.com";
    public static final long EXPIRATION = 10 * 24 * 3600;
    public static final String HEADER_PREFIX = "Bearer ";
}
