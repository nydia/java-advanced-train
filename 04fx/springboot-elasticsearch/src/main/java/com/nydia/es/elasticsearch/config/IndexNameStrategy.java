package com.nydia.es.elasticsearch.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component("IndexNameStrategy")
public class IndexNameStrategy {
    @Value("${spring.elasticsearch.dayly-date-format:yyyyMMdd}")
    private String daylyDateFormat;
    @Value("${spring.elasticsearch.monthly-date-format:yyyyMM}")
    private String monthlyDateFormat;
    @Value("${spring.elasticsearch.env:}")
    private String env = null;

    public String getDaylyIndexSuffix() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(daylyDateFormat);
        return formatter.format(date);
    }

    public String getMonthlyIndexSuffix() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(monthlyDateFormat);
        return formatter.format(date);
    }

    public String getEvnPrefix() {
        if(StringUtils.isNotBlank(env)) {
            return env + "_";
        } else {
            return "";
        }
    }
}
