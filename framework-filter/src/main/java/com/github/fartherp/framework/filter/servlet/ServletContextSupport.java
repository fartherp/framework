/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.filter.servlet;

import javax.servlet.ServletContext;

public class ServletContextSupport {

    private ServletContext servletContext = null;

    public ServletContext getServletContext() {
        return servletContext;
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @SuppressWarnings({"UnusedDeclaration"})
    protected String getContextInitParam(String paramName) {
        return getServletContext().getInitParameter(paramName);
    }

    private ServletContext getRequiredServletContext() {
        ServletContext servletContext = getServletContext();
        if (servletContext == null) {
            String msg = "ServletContext property must be set via the setServletContext method.";
            throw new IllegalStateException(msg);
        }
        return servletContext;
    }

    @SuppressWarnings({"UnusedDeclaration"})
    protected void setContextAttribute(String key, Object value) {
        if (value == null) {
            removeContextAttribute(key);
        } else {
            getRequiredServletContext().setAttribute(key, value);
        }
    }

    @SuppressWarnings({"UnusedDeclaration"})
    protected Object getContextAttribute(String key) {
        return getRequiredServletContext().getAttribute(key);
    }

    protected void removeContextAttribute(String key) {
        getRequiredServletContext().removeAttribute(key);
    }

    /**
     * It is highly recommended not to override this method directly, and instead override the
     * {@link #toStringBuilder() toStringBuilder()} method, a better-performing alternative.
     *
     * @return the String representation of this instance.
     */
    @Override
    public String toString() {
        return toStringBuilder().toString();
    }

    /**
     * Same concept as {@link #toString() toString()}, but returns a {@link StringBuilder} instance instead.
     *
     * @return a StringBuilder instance to use for appending String data that will eventually be returned from a
     * {@code toString()} invocation.
     */
    protected StringBuilder toStringBuilder() {
        return new StringBuilder(super.toString());
    }
}
