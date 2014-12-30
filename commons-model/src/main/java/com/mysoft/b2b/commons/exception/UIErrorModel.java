package com.mysoft.b2b.commons.exception;

import java.io.Serializable;

/**
 * The ui error model. The class use for present an use resolve error to such as user name registered by other people..
 * User: Think
 * Date: 13-12-6
 * Time: 下午9:13
 * To change this template use File | Settings | File Templates.
 */
@SuppressWarnings("serial")
public class UIErrorModel implements Serializable {
    /**
     * The error message to present to end user.
     */
    private String message;
    /**
     * The error source element. It guides the ui to display the error at right of the error source control. So it always is an error to describe the error.
     * For complicated error. Sets the  srcElement to be null.
     */
    private String srcElement;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSrcElement() {
        return srcElement;
    }

    public void setSrcElement(String srcElement) {
        this.srcElement = srcElement;
    }
}
