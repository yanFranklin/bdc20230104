package cn.gtmap.realestate.service;

import cn.gtmap.realestate.certificate.CertificateApp;
import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzLogDO;
import cn.gtmap.realestate.certificate.util.DateUtil;
import cn.gtmap.realestate.certificate.util.PublicUtil;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.*;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.DeleteIndex;
import io.searchbox.indices.mapping.GetMapping;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/6/13
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CertificateApp.class)
public class JestTest {
    private static JestClient jestClient;
    private static String indexName = "logindex";
    private static String typeName = "log";
    private static String elasticIps="http://127.0.0.1:9200";

    static {
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(new HttpClientConfig.Builder(elasticIps).connTimeout(100000).readTimeout(100000).multiThreaded(true).build());
        jestClient =  factory.getObject();
    }

    /*private static  JestClient getJestClient() {
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(new HttpClientConfig.Builder(elasticIps).connTimeout(60000).readTimeout(60000).multiThreaded(true).build());
        return factory.getObject();
    }*/

    @Test
    public void test() throws Exception {
        //System.out.println("\n创建索引" + createIndex(jestClient, this.indexName));
        //eruptTest();
        //serach2();
    }

    @Test
    public void eruptTest(){
        //System.out.println("\n 并发测试开始："+ PublicUtil.convertDateToStr4(DateUtil.now()));
        int thread_num = 50;
        int client_num = 100;
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int index = 0; index < client_num; index++) {
            final int NO = index;
            Runnable run = new Runnable() {
                public void run() {
                    System.out.println("\n并发插入"+NO+"开始：" + PublicUtil.convertDateToStr4(DateUtil.now()));
                    Bulk.Builder bulk = new Bulk.Builder().defaultIndex(indexName).defaultType(typeName);
                    BdcDzzzLogDO bdcDzzzLogDO = new BdcDzzzLogDO();
                    bdcDzzzLogDO.setLogId(UUIDGenerator.generate());
                    bdcDzzzLogDO.setCzrq(DateUtil.now());
                    bdcDzzzLogDO.setController("http://127.0.0.1:8081/realestate-e-certificate/rest/v1.0/zzgl/zzpdf");
                    bdcDzzzLogDO.setParamJson("响应标识:1558346910383,响应结果:\"{\"head\":{\"message\":\"success\",\"status\":\"0\"},\"data\":{\"message\":\"生成不动产电子证照PDF成功，上传文件中心成功！\",\"result\":\"0\"}}\"");
                    bdcDzzzLogDO.setIp("192.168.0.50");
                    Index index = new Index.Builder(bdcDzzzLogDO).build();
                    bulk.addAction(index);
                    try {
                        BulkResult br = jestClient.execute(bulk.build());
                        System.out.println(""+NO+"并发新增:"+br.isSucceeded());
                        System.out.println("\n并发插入"+NO+"结束：" + br.isSucceeded() + " "+ PublicUtil.convertDateToStr4(DateUtil.now()));
                    } catch (IOException e) {}

                }
            };
            exec.execute(run);
        }
        try {
            Thread.sleep((long)(Math.random()*6000000));
        } catch (Exception e) {
            e.printStackTrace();
        }
        exec.shutdown();
        System.out.println("\n 并发测试结束："+ PublicUtil.convertDateToStr4(DateUtil.now()));
    }

    public static void insertBatch() {
        System.out.println("\n 批量插入开始："+ PublicUtil.convertDateToStr4(DateUtil.now()));
        List<Object> objs = new ArrayList<Object>();
        for (int i = 0; i < 100; i++) {
            BdcDzzzLogDO bdcDzzzLogDO = new BdcDzzzLogDO();
            bdcDzzzLogDO.setLogId(UUIDGenerator.generate());
            bdcDzzzLogDO.setCzrq(DateUtil.now());
            bdcDzzzLogDO.setController("http://127.0.0.1:8081/realestate-e-certificate/rest/v1.0/zzgl/zzpdf");
            bdcDzzzLogDO.setParamJson("响应标识:1558346910383,响应结果:\"{\"head\":{\"message\":\"success\",\"status\":\"0\"},\"data\":{\"message\":\"生成不动产电子证照PDF成功，上传文件中心成功！\",\"result\":\"0\"}}\"");
            bdcDzzzLogDO.setIp("192.168.0.50");
            objs.add(bdcDzzzLogDO);
        }
        boolean result = false;
        try {
            result = insertBatch(jestClient,indexName, typeName,objs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("\n 批量新增:"+result);
        System.out.println("\n 批量插入结束："+ PublicUtil.convertDateToStr4(DateUtil.now()));
    }


    /**
     * 全文搜索
     */
    public static void serach1() {
        String query ="工程师";
        try {
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.queryStringQuery(query));
            //分页设置
            searchSourceBuilder.from(0).size(2);
            System.out.println("全文搜索查询语句:"+searchSourceBuilder.toString());
            System.out.println("全文搜索返回结果:"+search(jestClient,indexName, typeName, searchSourceBuilder.toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 精确搜索
     */
    @Test
    public void serach2() {
        try {
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.termQuery("logId", "01a584ca6c4f4267b73cb5988d106ddf"));
            System.out.println("精确搜索查询语句:"+searchSourceBuilder.toString());
            System.out.println("精确搜索返回结果:"+search(jestClient,indexName, typeName, searchSourceBuilder.toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 区间搜索
     */
    public static void serach3() {
        String createtm="createtm";
        String from="2016-8-21 06:11:32";
        String to="2018-8-21 06:11:32";

        try {
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.rangeQuery(createtm).gte(from).lte(to));
            System.out.println("区间搜索语句:"+searchSourceBuilder.toString());
            System.out.println("区间搜索返回结果:"+search(jestClient,indexName, typeName, searchSourceBuilder.toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建索引
     * @param indexName
     * @return
     * @throws Exception
     */
    public boolean createIndex(JestClient jestClient,String indexName) throws IOException {
        JestResult jr = jestClient.execute(new CreateIndex.Builder(indexName).build());
        return jr.isSucceeded();
    }

    /**
     * 新增数据
     * @param
     * @param
     * @param
     * @return
     * @throws Exception
     */
    @Test
    public void insert() throws Exception {
        BdcDzzzLogDO bdcDzzzLogDO = new BdcDzzzLogDO();
        bdcDzzzLogDO.setLogId(UUIDGenerator.generate());
        bdcDzzzLogDO.setCzrq(DateUtil.now());
        bdcDzzzLogDO.setController("http://127.0.0.1:8081/realestate-e-certificate/rest/v1.0/zzgl/zzpdf");
        bdcDzzzLogDO.setParamJson("响应标识:1558346910383,响应结果:\"{\"head\":{\"message\":\"success\",\"status\":\"0\"},\"data\":{\"message\":\"生成不动产电子证照PDF成功，上传文件中心成功！\",\"result\":\"0\"}}\"");
        bdcDzzzLogDO.setIp("192.168.0.50");
        Index index = new Index.Builder(bdcDzzzLogDO).index(indexName).type(typeName).build();
        JestResult jr = jestClient.execute(index);
        System.out.println("\n插入数据" + jr.isSucceeded());
    }


    /**
     * 查询数据
     * @param indexName
     * @param typeName
     * @return
     * @throws Exception
     */
    public static String getIndexMapping(JestClient jestClient, String indexName, String typeName) throws Exception {
        GetMapping getMapping = new GetMapping.Builder().addIndex(indexName).addType(typeName).build();
        JestResult jr =jestClient.execute(getMapping);
        return jr.getJsonString();
    }



    /**
     * 批量新增数据
     * @param indexName
     * @param typeName
     * @param objs
     * @return
     * @throws Exception
     */
    public static boolean insertBatch(JestClient jestClient, String indexName, String typeName, List<Object> objs) throws Exception {
        Bulk.Builder bulk = new Bulk.Builder().defaultIndex(indexName).defaultType(typeName);
        for (Object obj : objs) {
            Index index = new Index.Builder(obj).build();
            bulk.addAction(index);
        }
        BulkResult br = jestClient.execute(bulk.build());
        return br.isSucceeded();
    }

    /**
     * 全文搜索
     * @param indexName
     * @param typeName
     * @param query
     * @return
     * @throws Exception
     */
    public static String search(JestClient jestClient, String indexName, String typeName, String query) throws Exception {
        Search search = new Search.Builder(query)
                .addIndex(indexName)
                .addType(typeName)
                .build();
        JestResult jr = jestClient.execute(search);
//          System.out.println("--"+jr.getJsonString());
//          System.out.println("--"+jr.getSourceAsObject(User.class));
        return jr.getSourceAsString();
    }





    /**
     * 删除索引
     * @param
     * @return
     * @throws Exception
     */
    @Test
    public void delete() throws Exception {
        JestResult jr = jestClient.execute(new DeleteIndex.Builder(indexName).build());
        System.out.println("\n删除索引" + jr.isSucceeded());
    }

    /**
     * 删除数据
     * @param indexName
     * @param typeName
     * @param id
     * @return
     * @throws Exception
     */
    public boolean delete(JestClient jestClient, String indexName, String typeName, String id) throws Exception {
        DocumentResult dr = jestClient.execute(new Delete.Builder(id).index(indexName).type(typeName).build());
        return dr.isSucceeded();
    }
}
