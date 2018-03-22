/*
 * Copyright (c) 2018. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.validate;

import javax.validation.Configuration;
import javax.validation.ValidationException;
import javax.validation.ValidationProviderResolver;
import javax.validation.ValidatorFactory;
import javax.validation.bootstrap.GenericBootstrap;
import javax.validation.bootstrap.ProviderSpecificBootstrap;
import javax.validation.spi.BootstrapState;
import javax.validation.spi.ValidationProvider;
import java.lang.ref.SoftReference;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.WeakHashMap;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2018/3/22
 */
public class ExpandValidation {
    /**
     * Builds and returns a {@link ValidatorFactory} instance based on the
     * default Bean Validation provider and following the XML configuration.
     * <p/>
     * The provider list is resolved using the default validation provider resolver
     * logic.
     * <p/>
     * The code is semantically equivalent to
     * {@code Validation.byDefaultProvider().configure().buildValidatorFactory()}.
     *
     * @return {@code ValidatorFactory} instance
     *
     * @throws ValidationException if the {@code ValidatorFactory} cannot be built
     */
    public static ValidatorFactory buildDefaultValidatorFactory(Locale locale) {
        return byDefaultProvider(locale).configure().buildValidatorFactory();
    }

    /**
     * Builds a {@link Configuration}. The provider list is resolved
     * using the strategy provided to the bootstrap state.
     * <pre>
     * Configuration&lt?&gt; configuration = Validation
     *    .byDefaultProvider()
     *    .providerResolver( new MyResolverStrategy() )
     *    .configure();
     * ValidatorFactory factory = configuration.buildValidatorFactory();
     * </pre>
     * The provider can be specified in the XML configuration. If the XML
     * configuration does not exist or if no provider is specified,
     * the first available provider will be returned.
     *
     * @return instance building a generic {@code Configuration}
     *         compliant with the bootstrap state provided
     */
    public static GenericBootstrap byDefaultProvider(Locale locale) {
        return new ExpandValidation.GenericBootstrapImpl(locale);
    }

    /**
     * Builds a {@link Configuration} for a particular provider implementation.
     * <p/>
     * Optionally overrides the provider resolution strategy used to determine the provider.
     * <p/>
     * Used by applications targeting a specific provider programmatically.
     * <p/>
     * <pre>
     * ACMEConfiguration configuration =
     *     Validation.byProvider(ACMEProvider.class)
     *             .providerResolver( new MyResolverStrategy() )
     *             .configure();
     * </pre>,
     * where {@code ACMEConfiguration} is the
     * {@code Configuration} sub interface uniquely identifying the
     * ACME Bean Validation provider. and {@code ACMEProvider} is the
     * {@link ValidationProvider} implementation of the ACME provider.
     *
     * @param providerType the {@code ValidationProvider} implementation type
     *
     * @return instance building a provider specific {@code Configuration}
     *         sub interface implementation
     */
    public static <T extends Configuration<T>, U extends ExpandValidationProvider<T>> ExpandProviderSpecificBootstrap<T> byProvider(Class<U> providerType) {
        return new ExpandValidation.ProviderSpecificBootstrapImpl<T, U>( providerType );
    }

    //private class, not exposed
    private static class ProviderSpecificBootstrapImpl<T extends Configuration<T>, U extends ExpandValidationProvider<T>>
            implements ExpandProviderSpecificBootstrap<T> {

        private final Class<U> validationProviderClass;
        private ValidationProviderResolver resolver;

        public ProviderSpecificBootstrapImpl(Class<U> validationProviderClass) {
            this.validationProviderClass = validationProviderClass;
        }

        /**
         * Optionally defines the provider resolver implementation used.
         * If not defined, use the default ValidationProviderResolver.
         *
         * @param resolver {@link ValidationProviderResolver} implementation used
         *
         * @return self
         */
        public ProviderSpecificBootstrap<T> providerResolver(ValidationProviderResolver resolver) {
            this.resolver = resolver;
            return this;
        }

        public T configure() {
            return null;
        }

        /**
         * Determines the provider implementation suitable for {@link #byProvider(Class)}
         * and delegates the creation of this specific {@link Configuration} subclass to the
         * provider.
         *
         * @return a {@code Configuration} sub interface implementation
         */
        public T configure(Locale locale) {
            if ( validationProviderClass == null ) {
                throw new ValidationException(
                        "builder is mandatory. Use Validation.byDefaultProvider() to use the generic provider discovery mechanism"
                );
            }
            //used mostly as a BootstrapState
            ExpandValidation.GenericBootstrapImpl state = new ExpandValidation.GenericBootstrapImpl(locale);
            if ( resolver == null ) {
                resolver = state.getDefaultValidationProviderResolver();
            }
            else {
                //stay null if no resolver is defined
                state.providerResolver( resolver );
            }

            List<ValidationProvider<?>> resolvers;
            try {
                resolvers = resolver.getValidationProviders();
            }
            catch ( RuntimeException re ) {
                throw new ValidationException( "Unable to get available provider resolvers.", re );
            }

            for ( ValidationProvider provider : resolvers ) {
                if ( validationProviderClass.isAssignableFrom( provider.getClass() ) ) {
                    ExpandValidationProvider<T> specificProvider = validationProviderClass.cast( provider );
                    return specificProvider.createSpecializedConfiguration( state , locale);
                }
            }
            throw new ValidationException( "Unable to find provider: " + validationProviderClass );
        }
    }

    //private class, not exposed
    private static class GenericBootstrapImpl implements GenericBootstrap, BootstrapState {

        private ValidationProviderResolver resolver;
        private ValidationProviderResolver defaultResolver;

        private Locale locale;

        public GenericBootstrapImpl(Locale locale) {
            this.locale = locale;
        }

        public GenericBootstrap providerResolver(ValidationProviderResolver resolver) {
            this.resolver = resolver;
            return this;
        }

        public ValidationProviderResolver getValidationProviderResolver() {
            return resolver;
        }

        public ValidationProviderResolver getDefaultValidationProviderResolver() {
            if ( defaultResolver == null ) {
                defaultResolver = new ExpandValidation.DefaultValidationProviderResolver();
            }
            return defaultResolver;
        }

        public Configuration<?> configure() {
            ValidationProviderResolver resolver = this.resolver == null ?
                    getDefaultValidationProviderResolver() :
                    this.resolver;

            List<ValidationProvider<?>> validationProviders;
            try {
                validationProviders = resolver.getValidationProviders();
            }
            // don't wrap existing ValidationExceptions in another ValidationException
            catch ( ValidationException e ) {
                throw e;
            }
            // if any other exception occurs wrap it in a ValidationException
            catch ( RuntimeException re ) {
                throw new ValidationException( "Unable to get available provider resolvers.", re );
            }

            if ( validationProviders.size() == 0 ) {
                String msg = "Unable to create a Configuration, because no Bean Validation provider could be found." +
                        " Add a provider like Hibernate Validator (RI) to your classpath.";
                throw new ValidationException( msg );
            }

            Configuration<?> config;
            try {
                config = ((ExpandValidationProvider)resolver.getValidationProviders().get( 0 )).createGenericConfiguration( this , locale);
            }
            catch ( RuntimeException re ) {
                throw new ValidationException( "Unable to instantiate Configuration.", re );
            }

            return config;
        }
    }

    /**
     * Finds {@link ValidationProvider} according to the default {@link ValidationProviderResolver} defined in the
     * Bean Validation specification. This implementation first uses thread's context classloader to locate providers.
     * If no suitable provider is found using the aforementioned class loader, it uses current class loader.
     * If it still does not find any suitable provider, it tries to locate the built-in provider using the current
     * class loader.
     *
     * @author Emmanuel Bernard
     * @author Hardy Ferentschik
     */
    private static class DefaultValidationProviderResolver implements ValidationProviderResolver {
        public List<ValidationProvider<?>> getValidationProviders() {
            // class loading and ServiceLoader methods should happen in a PrivilegedAction
            return ExpandValidation.GetValidationProviderListAction.getValidationProviderList();
        }
    }

    private static class GetValidationProviderListAction implements PrivilegedAction<List<ValidationProvider<?>>> {

        //cache per classloader for an appropriate discovery
        //keep them in a weak hash map to avoid memory leaks and allow proper hot redeployment
        private static final WeakHashMap<ClassLoader, SoftReference<List<ValidationProvider<?>>>> providersPerClassloader =
                new WeakHashMap<ClassLoader, SoftReference<List<ValidationProvider<?>>>>();

        public static List<ValidationProvider<?>> getValidationProviderList() {
            final ExpandValidation.GetValidationProviderListAction action = new ExpandValidation.GetValidationProviderListAction();
            if ( System.getSecurityManager() != null ) {
                return AccessController.doPrivileged( action );
            }
            else {
                return action.run();
            }
        }

        public List<ValidationProvider<?>> run() {
            // Option #1: try first context class loader
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            List<ValidationProvider<?>> cachedContextClassLoaderProviderList = getCachedValidationProviders( classloader );
            if ( cachedContextClassLoaderProviderList != null ) {
                // if already processed return the cached provider list
                return cachedContextClassLoaderProviderList;
            }

            List<ValidationProvider<?>> validationProviderList = loadProviders( classloader );

            // Option #2: if we cannot find any service files with the context class loader use the current class loader
            if ( validationProviderList.isEmpty() ) {
                classloader = ExpandValidation.DefaultValidationProviderResolver.class.getClassLoader();
                List<ValidationProvider<?>> cachedCurrentClassLoaderProviderList = getCachedValidationProviders(
                        classloader
                );
                if ( cachedCurrentClassLoaderProviderList != null ) {
                    // if already processed return the cached provider list
                    return cachedCurrentClassLoaderProviderList;
                }
                validationProviderList = loadProviders( classloader );
            }

            // cache the detected providers against the classloader in which they were found
            cacheValidationProviders( classloader, validationProviderList );

            return validationProviderList;
        }

        private List<ValidationProvider<?>> loadProviders(ClassLoader classloader) {
            ServiceLoader<ValidationProvider> loader = ServiceLoader.load( ValidationProvider.class, classloader );
            Iterator<ValidationProvider> providerIterator = loader.iterator();
            List<ValidationProvider<?>> validationProviderList = new ArrayList<ValidationProvider<?>>();
            while ( providerIterator.hasNext() ) {
                try {
                    validationProviderList.add( providerIterator.next() );
                }
                catch ( ServiceConfigurationError e ) {
                    // ignore, because it can happen when multiple
                    // providers are present and some of them are not class loader
                    // compatible with our API.
                }
            }
            return validationProviderList;
        }

        private synchronized List<ValidationProvider<?>> getCachedValidationProviders(ClassLoader classLoader) {
            SoftReference<List<ValidationProvider<?>>> ref = providersPerClassloader.get( classLoader );
            return ref != null ? ref.get() : null;
        }

        private synchronized void cacheValidationProviders(ClassLoader classLoader, List<ValidationProvider<?>> providers) {
            providersPerClassloader.put( classLoader, new SoftReference<List<ValidationProvider<?>>>( providers ) );
        }
    }
}
