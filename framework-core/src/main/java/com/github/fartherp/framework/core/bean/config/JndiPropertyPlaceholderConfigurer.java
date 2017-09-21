/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.bean.config;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Properties;

/**
 * A property resource configurer that resolves placeholders in bean property
 * values of context definitions. It <i>pulls</i> values from a JNDI and
 * properties file into bean definitions.
 * Auth: CK
 * Date: 2016/8/29
 */
public class JndiPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
    /**
     * Logger for this class
     */
    private static final Log LOGGER = LogFactory.getLog(JndiPropertyPlaceholderConfigurer.class);

    /**
     * jndi name list
     */
    private List<String> jndiLocations;

    /**
     * default encode/decode code
     */
    private static final String DEFAULT_ENCODER = "utf-8";

    /**
     * this intercepter will call back on property resolved.
     */
    private PropertyResolvedInterceptor propertyResolvedInterceptor;

    /**
     * get propertyResolvedInterceptor
     *
     * @return propertyResolvedInterceptor
     */
    protected PropertyResolvedInterceptor getPropertyResolvedInterceptor() {
        return propertyResolvedInterceptor;
    }

    /**
     * set propertyResolvedInterceptor
     *
     * @param propertyResolvedInterceptor
     *            propertyResolvedInterceptor
     */
    public void setPropertyResolvedInterceptor(PropertyResolvedInterceptor propertyResolvedInterceptor) {
        this.propertyResolvedInterceptor = propertyResolvedInterceptor;
    }

    /**
     * default jndi prefix
     */
    private static final String JEE_ENV_PREFIX = "java:comp/env/";

    /**
     * if key duplicate by jndi and local if true to replace it by jndi
     */
    private boolean replaceDuplicateByJndi = true;
    /**
     * if true add "java:comp/env/" as the prefix
     */
    private boolean addEnvPrefix = true;

    /**
     * get environment prefix
     * @return the addEnvPrefix
     */
    public boolean isAddEnvPrefix() {
        return addEnvPrefix;
    }

    /**
     * set the environment prefix
     * @param addEnvPrefix
     *            the addEnvPrefix to set
     */
    public void setAddEnvPrefix(boolean addEnvPrefix) {
        this.addEnvPrefix = addEnvPrefix;
    }

    /**
     * set replace duplicate by jndi
     * @param replaceDuplicateByJndi
     *            the replaceDuplicateByJndi to set
     */
    public void setReplaceDuplicateByJndi(boolean replaceDuplicateByJndi) {
        this.replaceDuplicateByJndi = replaceDuplicateByJndi;
    }


    /**
     * Set locations of jndi names to be loaded.
     *
     * @param jndiLocations
     *            jndi name list
     */
    public void setJndiLocations(List<String> jndiLocations) {
        this.jndiLocations = jndiLocations;
    }

    /**
     * Resolve the given placeholder using the given properties, performing a
     * system properties check according to the given mode.
     * <p>
     * The default implementation delegates to {@code resolvePlaceholder
     * (placeholder, props)} before/after the system properties check.
     * <p>
     * Subclasses can this for custom resolution strategies, including
     * customized points for the system properties check.
     *
     * @param placeholder
     *            the placeholder to resolve
     * @param props
     *            the merged properties of this configurer
     * @param systemPropertiesMode
     *            the system properties mode, according to the constants in this
     *            class
     * @return the resolved value, of null if none
     * @see #setSystemPropertiesMode
     * @see System#getProperty
     * @see #resolvePlaceholder(String, Properties)
     */
    protected String resolvePlaceholder(String placeholder, Properties props, int systemPropertiesMode) {
        String value = super.resolvePlaceholder(placeholder, props, systemPropertiesMode);
        if (getPropertyResolvedInterceptor() != null) {
            getPropertyResolvedInterceptor().doPropertyResolved(placeholder, value);
        }
        return value;
    }

    /**
     * Load properties into the given instance.
     *
     * @param props
     *            the Properties instance to load into
     * @throws IOException
     *             in case of I/O errors
     * @see #setLocations
     * @see #setJndiLocations
     */
    protected void loadProperties(Properties props) throws IOException {
        super.loadProperties(props);
        try {
            loadJndi(props);
        } catch (NamingException e) {
            LOGGER.error("load jndi properties failed! ignore jndi properties loading");
        }

        if (getPropertyResolvedInterceptor() != null) {
            getPropertyResolvedInterceptor().onPropertiesLoad(props);
        }
    }

    /**
     * Do load jndi values by jndi name list and merge back to loaded
     * {@link Properties} instance.
     *
     * @param props
     *            target {@link Properties} instance to be merged
     * @throws NamingException
     *             exceptions on request to naming service
     */
    private void loadJndi(Properties props) throws NamingException {
        if (this.jndiLocations != null) {
            Context ctx = new InitialContext();
            for (String jndi : this.jndiLocations) {
                if (addEnvPrefix && !StringUtils.startsWith(jndi, JEE_ENV_PREFIX)) {
                    jndi = JEE_ENV_PREFIX + jndi;
                }
                LOGGER.info("load JNDI path : " + jndi);
                try {
                    Context envContext = (Context) ctx.lookup(jndi);
                    NamingEnumeration<NameClassPair> ne = envContext.list("");
                    while (ne.hasMore()) {
                        NameClassPair np = ne.next();
                        String path = np.getName();
                        if (!replaceDuplicateByJndi && props.containsKey(path)) {
                            LOGGER.info("ingore JNDI property key=" + path);
                            continue;
                        }
                        LOGGER.info("load JNDI property key=" + path);
                        props.put(path, decode(envContext.lookup(path)));
                    }
                } catch (Exception e) {
                    LOGGER.error("load jndi properties failed! ignore to load jndi:" + jndi);
                }
            }
        }
    }

    /**
     * Do url decode action
     *
     * @param str
     *            target string object
     * @return decoded string
     */
    private Object decode(Object str) {
        if (str instanceof String) {
            try {
                return URLDecoder.decode(str.toString(), DEFAULT_ENCODER);
            } catch (UnsupportedEncodingException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        return str;
    }
}
