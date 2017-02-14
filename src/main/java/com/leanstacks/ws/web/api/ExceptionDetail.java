package com.leanstacks.ws.web.api;

/**
 * The ExceptionDetail class models information about a web service request which results in an Exception. This
 * information may be returned to the client.
 * 
 * @author Matt Warman
 *
 */
public class ExceptionDetail {

    /**
     * A timestamp expressed in milliseconds.
     */
    private long timestamp;
    /**
     * The HTTP method (e.g. GET, POST, etc.)
     */
    private String method = "";
    /**
     * The web service servlet path.
     */
    private String path = "";
    /**
     * The HTTP status code of the response.
     */
    private int status;
    /**
     * The text description of the HTTP status code of the response.
     */
    private String statusText = "";
    /**
     * The fully qualified Class name of the Exception.
     */
    private String exceptionClass = "";
    /**
     * The value of the Exception message attribute.
     */
    private String exceptionMessage = "";

    /**
     * Construct an ExceptionDetail.
     */
    public ExceptionDetail() {
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * Returns the timestamp attribute value.
     * 
     * @return A long.
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp attribute value.
     * 
     * @param timestamp A long.
     */
    public void setTimestamp(final long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Returns the method attribute value.
     * 
     * @return A String.
     */
    public String getMethod() {
        return method;
    }

    /**
     * Sets the method attribute value.
     * 
     * @param method A String.
     */
    public void setMethod(final String method) {
        this.method = method;
    }

    /**
     * Returns the path attribute value.
     * 
     * @return A String.
     */
    public String getPath() {
        return path;
    }

    /**
     * Sets the path attribute value.
     * 
     * @param path A String.
     */
    public void setPath(final String path) {
        this.path = path;
    }

    /**
     * Returns the status attribute value.
     * 
     * @return An int.
     */
    public int getStatus() {
        return status;
    }

    /**
     * Sets the status attribute value.
     * 
     * @param status An int.
     */
    public void setStatus(final int status) {
        this.status = status;
    }

    /**
     * Returns the statusText attribute value.
     * 
     * @return A String.
     */
    public String getStatusText() {
        return statusText;
    }

    /**
     * Sets the statusText attribute value.
     * 
     * @param statusText A String.
     */
    public void setStatusText(final String statusText) {
        this.statusText = statusText;
    }

    /**
     * Returns the exceptionClass attribute value.
     * 
     * @return A String.
     */
    public String getExceptionClass() {
        return exceptionClass;
    }

    /**
     * Sets the exceptionClass attribute value.
     * 
     * @param exceptionClass A String.
     */
    public void setExceptionClass(final String exceptionClass) {
        this.exceptionClass = exceptionClass;
    }

    /**
     * Returns the exceptionMessage attribute value.
     * 
     * @return A String.
     */
    public String getExceptionMessage() {
        return exceptionMessage;
    }

    /**
     * Sets the exceptionMessage attribute value.
     * 
     * @param exceptionMessage A String.
     */
    public void setExceptionMessage(final String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

}
