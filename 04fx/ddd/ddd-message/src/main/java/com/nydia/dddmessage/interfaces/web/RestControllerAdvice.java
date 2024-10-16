package com.nydia.dddmessage.interfaces.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 统一的异常处理类， 通过不同的异常类别，包装不同的 {@link Response } 对象
 *
 * @author nydia
 * Created on 2021-7-22
 */
@org.springframework.web.bind.annotation.RestControllerAdvice
public class RestControllerAdvice {
    private final Logger logger = LoggerFactory.getLogger(RestControllerAdvice.class);

    /**
     * 错误处理
     *
     * @param throwable           异常
     * @param httpServletRequest  http 请求对象
     * @param httpServletResponse http 响应对象
     *                            return   {@link Response}
     */
    @ExceptionHandler(Throwable.class)
    public Response<Void> errorHandler(Throwable throwable, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        //可以根据异常类别，做判断，包装不同的响应
        Response<Void> response = Response.failed(throwable.getMessage());
        httpServletResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        StringBuilder builder = new StringBuilder();
        builder.append("Controller ErrorHandler")
                .append(" , ").append(httpServletRequest.getMethod()).append(" ").append(httpServletRequest.getRequestURI())
                .append(" , errorInfo ").append(throwable.getMessage())
                .append(" , responseHttpStatus [").append(httpServletResponse.getStatus()).append("]")
                .append(" , ").append(response.toString());
        logger.error(builder.toString(), throwable);
        return response;
    }
}
