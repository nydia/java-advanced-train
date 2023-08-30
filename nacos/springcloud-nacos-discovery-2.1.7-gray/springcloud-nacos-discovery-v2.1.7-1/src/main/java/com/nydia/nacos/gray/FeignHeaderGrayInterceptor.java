package com.nydia.nacos.gray;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @Description 继承RequestInterceptor，把header设置到请求中,注意header的key若是大写时，请求中会被转为小写
 */
@Configuration
public class FeignHeaderGrayInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //当主线程的请求执行完毕后，Servlet容器会被销毁当前的Servlet，因此在这里需要做判空
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            Enumeration<String> headerNames = request.getHeaderNames();

            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                //不能把所有消息头都传递下去，否则会引起其他异常；header的name都是小写
                if (name.equals("gray")) {//灰度header
                    requestTemplate.header(name, request.getHeader(name));
                }
            }

        }

    }
}
