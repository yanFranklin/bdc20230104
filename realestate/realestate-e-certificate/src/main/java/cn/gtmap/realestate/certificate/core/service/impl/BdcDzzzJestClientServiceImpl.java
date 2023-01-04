package cn.gtmap.realestate.certificate.core.service.impl;

import cn.gtmap.realestate.certificate.core.service.BdcDzzzJestClientService;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Bulk;
import io.searchbox.core.BulkResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.DeleteIndex;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/6/13
 */
@Service
public class BdcDzzzJestClientServiceImpl implements BdcDzzzJestClientService {
    private final Logger logger = LoggerFactory.getLogger(BdcDzzzJestClientServiceImpl.class);
    @Autowired
    private JestClient jestClient;

    @Override
    public boolean createIndex(String indexName) {
        try {
            JestResult jr = jestClient.execute(new CreateIndex.Builder(indexName).build());
            return jr.isSucceeded();
        } catch (IOException e) {
            logger.error("ES createIndex --》 " + indexName, e);
        }
        return false;
    }

    @Override
    public boolean deleteIndex(String indexName) {
        try {
            JestResult jr = jestClient.execute(new DeleteIndex.Builder(indexName).build());
            return jr.isSucceeded();
        } catch (IOException e) {
            logger.error("ES deleteIndex --》 " + indexName, e);
        }
        return false;
    }

    @Override
    public boolean insertBatch(String indexName, String typeName, List<Object> objs) {
        Bulk.Builder bulk = new Bulk.Builder().defaultIndex(indexName).defaultType(typeName);
        for (Object obj : objs) {
            Index index = new Index.Builder(obj).build();
            bulk.addAction(index);
        }
        try {
            BulkResult br = jestClient.execute(bulk.build());
            return br.isSucceeded();
        } catch (IOException e) {
            logger.error("ES insertBatch --》 " + indexName, e);
        }
        return false;
    }

    @Override
    public boolean insert(String indexName, String typeName, Object obj) {
        try {
            JestResult jr = jestClient.execute(new Index.Builder(obj).index(indexName).type(typeName).build());
            return jr.isSucceeded();
        } catch (IOException e) {
            logger.error("ES insert --》 " + indexName, e);
        }
        return false;
    }

    @Override
    public String searchQuery(String indexName, String typeName,String key, String value) {
        try {
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.termQuery(key, value));
            return search(indexName, typeName, searchSourceBuilder.toString());
        } catch (Exception e) {
            logger.error("ES searchQuery --》 ", e);
        }
        return null;
    }

    @Override
    public String search(String indexName, String typeName, String query) {
        Search search = new Search.Builder(query)
                .addIndex(indexName)
                .addType(typeName)
                .build();
        try {
            JestResult jr = jestClient.execute(search);
            if (null != jr) {
                return jr.getSourceAsString();
            }
        } catch (IOException e) {
            logger.error("ES search --》 " + query, e);
        }
        return null;
    }
}
