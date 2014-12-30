package com.mysoft.b2b.commons.exception;

import java.util.List;

/**
 * The checked exception definition for platform.
 * User: Think
 * Date: 13-12-6
 * Time: 下午8:54
 * To change this template use File | Settings | File Templates.
 */
@SuppressWarnings("serial")
public class PlatformCheckedException extends Exception {

    /**
     * The error code;
     */
    private String code;
    /**
     * The argument for the error. It is used when we needs to to tell the end user detail error cause;
     */
    private List<Object> args;

    public PlatformCheckedException(String code, String message) {
        super(message);
        this.code = code;
    }

    public PlatformCheckedException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }


    public PlatformCheckedException(String code, String message, List<Object> args) {
        super(message);
        this.code = code;
        this.args = args;
    }


    public PlatformCheckedException(String code, String message, List<Object> args, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.args = args;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(100);
        sb.append("Error raised. Error code(").append(code).append("), message is :").append(super.getLocalizedMessage());
        if(args !=null && args.size() > 0 ) {
            sb.append(" and arguments are :");
            sb.append(args.toString());
        }
        return sb.toString();
    }

    public String getCode() {
        return code;
    }

    public List<Object> getArgs() {
        return args;
    }
}
