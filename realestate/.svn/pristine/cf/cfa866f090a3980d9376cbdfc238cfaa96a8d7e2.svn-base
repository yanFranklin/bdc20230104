package cn.gtmap.realestate.certificate.core.service;

import io.searchbox.client.JestClient;

import java.io.IOException;
import java.util.List;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/6/13
 */
public interface BdcDzzzJestClientService {

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param indexName
     * @return
     * @description 创建索引
     */
    boolean createIndex(String indexName);

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param indexName
     * @return
     * @description 删除索引
     */
    boolean deleteIndex(String indexName);

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param indexName
     * @param typeName
     * @param objs
     * @return
     * @description 批量插入
     */
    boolean insertBatch(String indexName, String typeName, List<Object> objs);

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param indexName
     * @param typeName
     * @param obj
     * @return
     * @description 单个插入
     */
    boolean insert(String indexName, String typeName, Object obj);

    /**
     *
     * @param key
     * @description 精确查询
     */
    String searchQuery(String indexName, String typeName,String key, String value);

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param indexName
     * @param typeName
     * @param query
     * @return 查询
     */
    String search(String indexName, String typeName, String query);
}
