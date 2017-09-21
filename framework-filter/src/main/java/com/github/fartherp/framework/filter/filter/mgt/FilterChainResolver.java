/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.filter.filter.mgt;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public interface FilterChainResolver {

    FilterChain getChain(ServletRequest request, ServletResponse response, FilterChain originalChain);

}
