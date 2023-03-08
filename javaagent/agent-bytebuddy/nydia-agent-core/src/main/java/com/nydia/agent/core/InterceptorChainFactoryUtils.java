package com.nydia.agent.core;

import com.nydia.agent.core.enhance.AbstractInterceptorChainFactory;
import com.nydia.agent.core.enhance.ConstructorInterceptor;
import com.nydia.agent.core.enhance.ConstructorInterceptorChainFactory;
import com.nydia.agent.core.enhance.Interceptor;
import com.nydia.agent.core.enhance.MethodInterceptor;
import com.nydia.agent.core.enhance.MethodInterceptorChainFactory;

import java.util.List;

/**
 * @Author: foolwc
 * @Date: 2019/8/7 14:18
 */
public class InterceptorChainFactoryUtils {

    public static List<Interceptor> INTERCEPTORS;

    public static final AbstractInterceptorChainFactory<MethodInterceptor> METHOD
            = new MethodInterceptorChainFactory();

    public static final AbstractInterceptorChainFactory<ConstructorInterceptor> CONSTRUCTOR
            = new ConstructorInterceptorChainFactory();

}

