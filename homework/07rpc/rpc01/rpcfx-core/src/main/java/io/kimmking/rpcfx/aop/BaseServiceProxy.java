package io.kimmking.rpcfx.aop;

import com.alibaba.fastjson.JSON;
import io.kimmking.rpcfx.api.Filter;
import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResponse;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


/**
 * Copyright (C) 2021 ShangHai IPS Information Technology Co.,Ltd.
 * <p>
 * All right reserved.
 * <p>
 * This software is the confidential and proprietary information of IPS
 * Company of China. ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms
 * of the contract agreement you entered into with IPS inc.
 * <p>
 *
 * @Description: BaseServiceProxy
 * @ClassName: BaseServiceProxy
 * @Auther: nydia.lhq
 * @Date: 2021/7/12 17:51
 */
public class BaseServiceProxy<T> implements InvocationHandler{

    public static final MediaType JSONTYPE = MediaType.get("application/json; charset=utf-8");

    //service的class

    private Class<T> interfaceClass;

    private String url = null;
    private Filter[] filters = null;


    public Object bind(Class<T> clazz, String url, Filter[] filters){
        this.interfaceClass = clazz;
        this.url = url;
        this.filters = filters;
        return Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{interfaceClass}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 加filter地方之二
        // mock == true, new Student("hubao");

        RpcfxRequest request = new RpcfxRequest();
        request.setServiceClass(this.interfaceClass.getName());
        request.setMethod(method.getName());
        request.setParams(args);

        if (null!=filters) {
            for (Filter filter : filters) {
                if (!filter.filter(request)) {
                    return null;
                }
            }
        }

        RpcfxResponse response = post(request, url);

        // 加filter地方之三
        // Student.setTeacher("cuijing");

        // 这里判断response.status，处理异常
        // 考虑封装一个全局的RpcfxException

        return JSON.parse(response.getResult().toString());
    }

    @SuppressWarnings("Duplicates")
    private RpcfxResponse post(RpcfxRequest req, String url) throws IOException {
        String reqJson = JSON.toJSONString(req);
        System.out.println("req json: "+reqJson);

        // 1.可以复用client
        // 2.尝试使用httpclient或者netty client
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(JSONTYPE, reqJson))
                .build();
        String respJson = client.newCall(request).execute().body().string();

        //作业3的改造：netty client
        //HttpClient httpClient = new HttpClient();
        //String respJson = httpClient.doPostJson(url, reqJson);

        System.out.println("resp json: "+respJson);
        return JSON.parseObject(respJson, RpcfxResponse.class);
    }
}
