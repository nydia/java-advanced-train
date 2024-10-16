package com.nydia.agent.core.utils;


import com.nydia.agent.core.enhance.Interceptor;

import java.util.List;

public class InterceptorUtils {
    public static String arrayStrOfInterceptors(List<? extends Interceptor> interceptors) {
        if (interceptors == null || interceptors.isEmpty()) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder("[");
        for (Interceptor itrcpt : interceptors) {
            sb.append(itrcpt.getClass().getName()).append(",");
        }
        int length = sb.length();
        if (length > 1) {
            sb.deleteCharAt(length - 1);
        }
        sb.append("]");
        return sb.toString();
    }
}
