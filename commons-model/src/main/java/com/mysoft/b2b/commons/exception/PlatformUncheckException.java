package com.mysoft.b2b.commons.exception;

import java.util.List;

/**
 * The uncheck exception definition for platform.
 * User: Think
 * Date: 13-12-6
 * Time: 下午9:03
 * To change this template use File | Settings | File Templates.
 */
@SuppressWarnings("serial")
public class PlatformUncheckException extends RuntimeException {

    /**
     * The argument for the error. It is used when we needs to to tell the end user detail error cause;
     */
    private List<Object> args;

    public PlatformUncheckException(String message, List<Object> args) {
        super(message);
        this.args = args;
    }


    public PlatformUncheckException(String message, List<Object> args, Throwable cause) {
        super(message, cause);
        this.args = args;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(100);
        sb.append("Runtime Exception raised. The message is :").append(super.getLocalizedMessage());
        if(args !=null && args.size() > 0 ) {
            sb.append(" and arguments are :");
            sb.append(args.toString());
        }
        return sb.toString();
    }
}
