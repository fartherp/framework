/*
 * Copyright (c) 2018. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.validate;

import org.hibernate.validator.internal.engine.messageinterpolation.InterpolationTerm;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.hibernate.validator.internal.util.privilegedactions.GetClassLoader;
import org.hibernate.validator.internal.util.privilegedactions.SetContextClassLoader;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.spi.resourceloading.ResourceBundleLocator;

import javax.el.ExpressionFactory;
import javax.validation.MessageInterpolator;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2018/3/22
 */
public class ExpandResourceBundleMessageInterpolator extends ExpandAbstractMessageInterpolator {
    private static final Log LOG = LoggerFactory.make();

    private final ExpressionFactory expressionFactory;

    public ExpandResourceBundleMessageInterpolator(Locale locale) {
        super(locale);
        this.expressionFactory = buildExpressionFactory();
    }

    public ExpandResourceBundleMessageInterpolator(ResourceBundleLocator userResourceBundleLocator, Locale locale) {
        super( userResourceBundleLocator , locale);
        this.expressionFactory = buildExpressionFactory();
    }

    public ExpandResourceBundleMessageInterpolator(ResourceBundleLocator userResourceBundleLocator,
                                                   ResourceBundleLocator contributorResourceBundleLocator, Locale locale) {
        super( userResourceBundleLocator, contributorResourceBundleLocator , locale);
        this.expressionFactory = buildExpressionFactory();
    }

    public ExpandResourceBundleMessageInterpolator(ResourceBundleLocator userResourceBundleLocator,
                                                   ResourceBundleLocator contributorResourceBundleLocator,
                                                   boolean cachingEnabled, Locale locale) {
        super( userResourceBundleLocator, contributorResourceBundleLocator, cachingEnabled , locale);
        this.expressionFactory = buildExpressionFactory();
    }

    public ExpandResourceBundleMessageInterpolator(ResourceBundleLocator userResourceBundleLocator, boolean cachingEnabled, Locale locale) {
        super( userResourceBundleLocator, null, cachingEnabled , locale);
        this.expressionFactory = buildExpressionFactory();
    }

    public ExpandResourceBundleMessageInterpolator(ResourceBundleLocator userResourceBundleLocator, boolean cachingEnabled, ExpressionFactory expressionFactory, Locale locale) {
        super( userResourceBundleLocator, null, cachingEnabled , locale);
        this.expressionFactory = expressionFactory;
    }

    @Override
    public String interpolate(MessageInterpolator.Context context, Locale locale, String term) {
        InterpolationTerm expression = new InterpolationTerm( term, locale, expressionFactory );
        return expression.interpolate( context );
    }

    /**
     * The javax.el FactoryFinder uses the TCCL to load the {@link ExpressionFactory} implementation so we need to be
     * extra careful when initializing it.
     *
     * @return the {@link ExpressionFactory}
     */
    private static ExpressionFactory buildExpressionFactory() {
        // First, we try to load the instance from the original TCCL.
        try {
            return ExpressionFactory.newInstance();
        }
        catch (Throwable e) {
            // we ignore the error in this case as we will try the Hibernate Validator class loader.
        }

        // Then we try the Hibernate Validator class loader. In a fully-functional modular environment such as
        // WildFly or Jigsaw, it is the way to go.
        final ClassLoader originalContextClassLoader = run( GetClassLoader.fromContext() );

        try {
            run( SetContextClassLoader.action( ResourceBundleMessageInterpolator.class.getClassLoader() ) );
            return ExpressionFactory.newInstance();
        }
        catch (Throwable e) {
            // HV-793 - We fail eagerly in case we have no EL dependencies on the classpath
            throw LOG.getUnableToInitializeELExpressionFactoryException( e );
        }
        finally {
            run( SetContextClassLoader.action( originalContextClassLoader ) );
        }
    }

    /**
     * Runs the given privileged action, using a privileged block if required.
     * <p>
     * <b>NOTE:</b> This must never be changed into a publicly available method to avoid execution of arbitrary
     * privileged actions within HV's protection domain.
     */
    private static <T> T run(PrivilegedAction<T> action) {
        return System.getSecurityManager() != null ? AccessController.doPrivileged( action ) : action.run();
    }
}
