package io.kimmking.rpcfx.aop;

import io.kimmking.rpcfx.api.Filter;
import org.springframework.beans.factory.FactoryBean;

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
 * @Date: 2021/7/12 17:43
 */
public class BaseServiceProxyFactory<T> implements FactoryBean<T> {

    private Class<T> interfaceClass;
    private String url = null;
    private Filter[] filters = null;

    public Class<T> getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(Class<T> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Filter[] getFilters() {
        return filters;
    }

    public void setFilters(Filter[] filters) {
        this.filters = filters;
    }

    @Override
    public T getObject(){
        return (T)new BaseServiceProxy<T>().bind(interfaceClass, url, filters);
    }

    @Override
    public Class<?> getObjectType() {
        return interfaceClass;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
