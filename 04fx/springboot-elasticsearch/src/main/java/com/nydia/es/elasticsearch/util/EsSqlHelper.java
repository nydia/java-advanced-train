package com.nydia.es.elasticsearch.util;

import cn.hutool.json.JSONUtil;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class EsSqlHelper {

    @Autowired
    RestClient restClient;

    public List<Map<String, Object>> executeQuery(String sql, Map<String, Object> params) throws Exception {
        if (StringUtils.isBlank(sql)) {
            return new ArrayList<>();
        }

        List<Map<String, Object>> datas = new ArrayList<>();
        try {
            long start = System.currentTimeMillis();
            log.info("sql: {}, args: {}", sql, JSONUtil.toJsonStr(params));
            // 转换sql参数
            if (params != null && params.size() != 0) {
                params.keySet().forEach(key -> {
                    sql.replace("#{" + key + "}", params.toString());
                });
            }

            Map<String, Object> query = new HashMap<>();
            query.put("query", sql);
            query.put("request_timeout", "300s");
            query.put("page_timeout", "300s");
            query.put("fetch_size", 10000);

            Request request = new Request("POST", "/_sql?format=json");
            request.setJsonEntity(JSONUtil.toJsonStr(query));
            Response response = restClient.performRequest(request);
            String data = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
            EsModel esModel = JSONUtil.toBean(data, EsModel.class);
            datas = getDatas(esModel);

            if (datas.size() == 0) {
                log.warn("executeQuery record: 0, response: {}, sql: {}", response, sql);
            }
            log.info("executeQuery record: {}, duration: {}, sql: {}", datas.size(), (System.currentTimeMillis() - start), sql);
        } catch (Exception e) {
            log.error("executeQuery error", e);
            throw e;
        }
        return datas;
    }

    private List<Map<String, Object>> getDatas(EsModel model) {
        List<Map<String, Object>> datas = new ArrayList<>();
        if (model != null && !CollectionUtils.isEmpty(model.getColumns()) && !CollectionUtils.isEmpty(model.getRows())) {
            List<Columns> columns = model.getColumns();
            for (List row : model.getRows()) {
                Map<String, Object> rowMap = Maps.newHashMap();
                for (int i = 0; i < columns.size(); i++) {
                    rowMap.put(columns.get(i).name, row.get(i));
                }
                datas.add(rowMap);
            }
        }
        return datas;
    }

    /**
     * 针对ES返回数据定义的Dto
     */
    @Data
    static class EsModel {
        private List<Columns> columns;
        private List<List> rows;

        public EsModel() {
        }

        public EsModel(List<Columns> columns, List<List> rows) {
            this.columns = columns;
            this.rows = rows;
        }
    }

    /**
     * EsQuery中使用sql查询。他会返回的列
     */
    @Data
    static class Columns {
        /**
         * 列名
         */
        private String name;

        /**
         * 列的类型
         */
        private String type;
    }
}
