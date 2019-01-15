/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.web.filter;

import com.github.fartherp.framework.common.util.JndiUtil;
import com.github.fartherp.framework.core.bean.config.PlaceholderResolver;
import com.github.fartherp.framework.core.bean.config.PropertyPlaceholderConfigurerTool;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySourcesPropertyResolver;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceEditor;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.Assert;
import org.springframework.web.context.support.ServletContextPropertySource;
import org.springframework.web.context.support.ServletContextResourceLoader;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.util.NestedServletException;

import javax.naming.NamingException;
import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * A abstract Filter for jndi support. sub class extends from this class<br>
 * will has such features.
 * <pre>
 *  features usage:
 *  &lt;param-value&gt;loginPagePath&lt;/param-value&gt;
 *  &lt;param-value&gt;jndi:java:comp/env/uc/uc.loginPagePath&lt;/param-value&gt;
 *  param loginPagePath will load value from jndi service
 * </pre>
 * Auth: CK
 * Date: 2016/8/29
 */
public abstract class JndiSupportFilter implements Filter {
    /**
     * Logger for this class
     */
    private static final Log LOGGER = LogFactory.getLog(JndiSupportFilter.class);

    /**
     * JNDI property prefix string
     */
    public static final String JNDI_PROPERTY_PREFIX = "jndi:";

    public static final String NAME = JndiSupportFilter.class.getName();

    /**
     * place holder resovler
     */
    private static PlaceholderResolver resolver;

    /**
     * Set of required properties (Strings) that must be supplied as
     * config parameters to this filter.
     */
    private final Set requiredProperties = new HashSet();

    public final void init(FilterConfig filterConfig) throws ServletException {
        Assert.notNull(filterConfig, "FilterConfig must not be null");
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Initializing filter '" + filterConfig.getFilterName() + "'");
        }

        initPlaceHolderConfigure(filterConfig);

        // Set bean properties from init parameters.
        try {
            PropertyValues pvs = new JndiSupportFilterConfigPropertyValues(filterConfig, this.requiredProperties);
            BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(this);
            ResourceLoader resourceLoader = new ServletContextResourceLoader(filterConfig.getServletContext());
            ServletContextPropertySource servletContextPropertySource
                    = new ServletContextPropertySource(NAME + "_ServletContextPropertySource", filterConfig.getServletContext());
            MutablePropertySources propertySources = new MutablePropertySources();
            propertySources.addFirst(servletContextPropertySource);
            PropertySourcesPropertyResolver propertySourcesPropertyResolver = new PropertySourcesPropertyResolver(propertySources);
            bw.registerCustomEditor(Resource.class, new ResourceEditor(resourceLoader, propertySourcesPropertyResolver));
            bw.setPropertyValues(pvs, true);
        } catch (BeansException ex) {
            String msg = "Failed to set bean properties on filter '" +
                    filterConfig.getFilterName() + "': " + ex.getMessage();
            LOGGER.error(msg, ex);
            throw new NestedServletException(msg, ex);
        }

        doInit(filterConfig);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Filter '" + filterConfig.getFilterName() + "' configured successfully");
        }
    }

    /**
     * initialize place holder from {@link FilterConfig}
     *
     * @param filterConfig {@link FilterConfig} instance
     */
    private void initPlaceHolderConfigure(FilterConfig filterConfig) {
        ApplicationContext app = WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext());
        if (app == null) {
            return;
        }

        AbstractApplicationContext context = (AbstractApplicationContext) app;

        Properties propertyResource =
                PropertyPlaceholderConfigurerTool.getRegisteredPropertyResourceConfigurer(context.getBeanFactory());
        if (propertyResource != null && resolver == null) {
            resolver = PropertyPlaceholderConfigurerTool.createPlaceholderParser(propertyResource);
        }
    }

    /**
     * invoke after filter init method. same as init() method.
     *
     * @param filterConfig {@link FilterConfig} instance.
     * @throws ServletException in case of any {@link ServletException}
     */
    protected void doInit(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * Subclasses may this to perform custom filter shutdown.
     * <p>Note: This method will be called from standard filter destruction
     * as well as filter bean destruction in a Spring application context.
     * <p>This default implementation is empty.
     */
    public void destroy() {
    }

    /**
     * check property name is mark as JNDI
     *
     * @param property property name
     * @return true if mark JNDI
     */
    private static boolean isMarkJndiProperty(String property) {
        return !StringUtils.isBlank(property) && StringUtils.startsWith(property, JNDI_PROPERTY_PREFIX);
    }

    /**
     * get value from JNDI
     *
     * @param property property name
     * @return value
     */
    private static String getJndiProperty(String property) {
        String strRet = property;
        if (resolver != null) {
            strRet = resolver.doParse(strRet);
        }

        if (isMarkJndiProperty(strRet)) {
            strRet = StringUtils.removeStart(strRet, JNDI_PROPERTY_PREFIX);
            try {
                strRet = JndiUtil.getValue(strRet);
            } catch (NamingException e) {
                LOGGER.warn(e.getMessage(), e);
            }
        }
        return strRet;
    }

    /**
     * PropertyValues implementation created from FilterConfig init parameters.
     */
    private static class JndiSupportFilterConfigPropertyValues extends MutablePropertyValues {

        /**
         * Create new FilterConfigPropertyValues.
         *
         * @param config             FilterConfig we'll use to take PropertyValues from
         * @param requiredProperties set of property names we need, where
         *                           we can't accept default values
         * @throws ServletException if any required properties are missing
         */
        @SuppressWarnings("unchecked")
        public JndiSupportFilterConfigPropertyValues(FilterConfig config, Set requiredProperties)
                throws ServletException {

            Set missingProps = CollectionUtils.isNotEmpty(requiredProperties) ? new HashSet(requiredProperties) : null;

            Enumeration en = config.getInitParameterNames();
            while (en.hasMoreElements()) {
                String property = en.nextElement().toString();
                String value = config.getInitParameter(property);
                value = getJndiProperty(value);
                addPropertyValue(new PropertyValue(property, value));
                if (missingProps != null) {
                    missingProps.remove(property);
                }
            }

            // Fail if we are still missing properties.
            if (missingProps != null && missingProps.size() > 0) {
                throw new ServletException(
                        "Initialization from FilterConfig for filter '" + config.getFilterName() +
                                "' failed; the following required properties were missing: " +
                                org.springframework.util.StringUtils.collectionToDelimitedString(missingProps, ", "));
            }
        }
    }
}
