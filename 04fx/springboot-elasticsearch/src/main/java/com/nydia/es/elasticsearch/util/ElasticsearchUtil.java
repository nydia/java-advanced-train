package com.nydia.es.elasticsearch.util;

import cn.hutool.extra.spring.SpringUtil;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Query;

import java.util.List;
import java.util.Map;

/**
 * Elasticsearch工具类
 * @see <a href="http://gitlab.ciicsh.com/fanjun/vhr-demo/-/blob/main/src/main/java/com/ciicsh/vhr/demo/web/ProductController.java?ref_type=heads">使用示例</a>
 */
@Log4j2
public class ElasticsearchUtil {

    private static ElasticsearchTemplate elasticsearchTemplate;
    private static ElasticsearchClient client;

    // ==== 索引操作 ==== //
    public static <T> boolean createIndex(Class<T> clazz) {
        return getElasticsearchTemplate().indexOps(clazz).create();
    }

    public static <T> boolean deleteIndex(Class<T> clazz) {
        return getElasticsearchTemplate().indexOps(clazz).delete();
    }
    public static <T> boolean deleteIndex(String indexName) {
        return getElasticsearchTemplate().indexOps(IndexCoordinates.of(indexName)).delete();
    }

    public static <T> String getIndexName(Class<T> clazz) {
        return getElasticsearchTemplate().indexOps(clazz).getIndexCoordinates().getIndexName();
    }

    public static <T> boolean isIndexExist(Class<T> clazz) {
        return getElasticsearchTemplate().indexOps(clazz).exists();
    }
    public static boolean isIndexExist(String indexName) {
        return getElasticsearchTemplate().indexOps(IndexCoordinates.of(indexName)).exists();
    }

    // ==== 文档操作 ==== //
    public static <T> void save(T entity) {
        getElasticsearchTemplate().save(entity);
    }

    public static <T> T getById(String id, Class<T> clazz) {
        return getElasticsearchTemplate().get(id, clazz);
    }

    public static <T> void update(T entity) {
        getElasticsearchTemplate().update(entity);
    }

    public static <T> void delete(String id, Class<T> clazz) {
        getElasticsearchTemplate().delete(id, clazz);
    }

    public static <T> void delete(T entity) {
        getElasticsearchTemplate().delete(entity);
    }

    // ==== 查询操作 ==== //
    /**
     * 查询
     * @see org.springframework.data.elasticsearch.core.query.Criteria
     * @see org.springframework.data.elasticsearch.core.query.CriteriaQuery
     * @see org.springframework.data.elasticsearch.client.elc.NativeQuery
     * @see org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder
     * @param query
     * @param clazz
     * @return List
     * @param <T>
     */
    public static <T> List<T> search(Query query, Class<T> clazz) {
        return getElasticsearchTemplate().search(query, clazz).stream().map(s -> s.getContent()).toList();
    }

//    public static <T> PageResponseVo<T> searchPage(Query query, Class<T> clazz) {
//        SearchHits<T> searchHits = getElasticsearchTemplate().search(query, clazz);
//        PageResponseVo<T> pageResponseVo = new PageResponseVo<>(
//                searchHits.getTotalHits(),
//                searchHits.getSearchHits().stream().map(s -> s.getContent()).toList(),
//                query.getPageable().getPageNumber(),
//                query.getPageable().getPageSize(),
//                searchHits.getTotalHits() / query.getPageable().getPageSize()
//        );
//        return pageResponseVo;
//    }

    /**
     * 基于SQL查询,占位符：#{key}<br>注：ES SQL不支持分页，返回所有数据或scroll
     * @see <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/sql-spec.html">sql-spec</a>
     * @param sql
     * @return List
     */
    public static List<Map<String, Object>> search(String sql) {
        try {
            EsSqlHelper esSqlHelper = SpringUtil.getBean(EsSqlHelper.class);
            return esSqlHelper.executeQuery(sql, null);
        } catch (Exception e) {
            log.error("search() error", e);
            return null;
        }
    }

    // ==== 批量操作 ==== //
    public static <T> void batchSaveOrUpdate(List<T> entityList) {
        getElasticsearchTemplate().save(entityList);
    }

    public static <T> void batchDelete(Query query, Class<T> clazz) {
        getElasticsearchTemplate().delete(query, clazz);
    }
    public static <T> void batchDelete(List<String> idList, Class<T> clazz) {
        getElasticsearchTemplate().delete(getElasticsearchTemplate().idsQuery(idList), clazz);
    }

    // ==== Other ==== //
    public static ElasticsearchTemplate getElasticsearchTemplate() {
        if (elasticsearchTemplate == null) {
            elasticsearchTemplate = SpringUtil.getBean(ElasticsearchTemplate.class);
        }
        return elasticsearchTemplate;
    }

    public static ElasticsearchClient getClient() {
        if (client == null) {
            client = SpringUtil.getBean(ElasticsearchClient.class);
        }
        return client;
    }
}
