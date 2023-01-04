package cn.gtmap.realestate.inquiry.service.impl;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcCqtjDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcCqTjQO;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.inquiry.service.BdcCqtjService;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Search;
import io.searchbox.core.SearchScroll;
import io.searchbox.params.Parameters;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2022/07/02/19:01
 * @Description:
 */
@Service
public class BdcCqtjServiceImpl implements BdcCqtjService {
    private final Logger logger = LoggerFactory.getLogger(BdcCqtjServiceImpl.class);

    @Autowired
    private JestClient jestClient;
    /**
    * 超期统计图表统计类型-行政区划
    **/
    private static final String CQTJTB_TJLX_XZQH ="xzqh";
    /**
     * 超期统计图表统计类型-工作流定义ID
     **/
    private static final String CQTJTB_TJLX_GZLDYID ="gzldyid";

    @Override
    public List<BdcCqtjDTO> bdcCqtjMx(BdcCqTjQO bdcCqTjQO) {
        //组织查询参数
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.existsQuery("startRegionCode"));
        if(StringUtils.isNotBlank(bdcCqTjQO.getProcessDefKey())){
            boolQueryBuilder.must(QueryBuilders.matchQuery("processKey",bdcCqTjQO.getProcessDefKey()));
        }
        RangeQueryBuilder procStartTimeInLong = QueryBuilders.rangeQuery("procStartTimeInLong");
        if(StringUtils.isNotBlank(bdcCqTjQO.getKssj())){
            long kssj = DateUtils.getDayTimeOfZeroHMS(DateUtils.formatDate(bdcCqTjQO.getKssj()));
            procStartTimeInLong.gte(kssj);
        }
        if(StringUtils.isNotBlank(bdcCqTjQO.getJzsj())){
            long jzsj  = DateUtils.getDayTimeOfLastHMS(DateUtils.formatDate(bdcCqTjQO.getJzsj()));
            procStartTimeInLong.lte(jzsj);
        }
        boolQueryBuilder.must(procStartTimeInLong);
        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.size(10000);
        List result = searchAll("statistics-proc-*", "doc", searchSourceBuilder.toString(),BdcCqtjDTO.class);
        return result;
    }

    @Override
    public Object bdcCqtjMxTb(BdcCqTjQO bdcCqTjQO) {
        //从ES获取超期数据
        List esResult = bdcCqtjMx(bdcCqTjQO);
        List<Map> result = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(esResult)){
            Object collect = null;
            if(CQTJTB_TJLX_XZQH.equals(bdcCqTjQO.getTjlx())){
                collect = esResult.stream().collect(Collectors.groupingBy(BdcCqtjDTO::getStartRegionCode, Collectors.groupingBy(BdcCqtjDTO::getProcTimeoutStatus, Collectors.counting())));
            }
            if(CQTJTB_TJLX_GZLDYID.equals(bdcCqTjQO.getTjlx())){
                collect = esResult.stream().collect(Collectors.groupingBy(BdcCqtjDTO::getProcDefName, Collectors.groupingBy(BdcCqtjDTO::getProcTimeoutStatus, Collectors.counting())));
            }
            if(collect!=null){
                Map<String,Map>  map= (Map<String,Map>)collect;
                Set<String> keySet = map.keySet();
                for (String key : keySet) {
                    Map sourceMap = map.get(key);
                    //未超期数量
                    if(sourceMap.containsKey(0)){
                        Map resultMap = new HashMap();
                        resultMap.put("SLR",key);
                        resultMap.put("AJZT","0");
                        resultMap.put("REALCOUNT",sourceMap.get(0));
                        result.add(resultMap);
                    }
                    //已超期超期数量
                    if(sourceMap.containsKey(1)){
                        Map resultMap = new HashMap();
                        resultMap.put("SLR",key);
                        resultMap.put("AJZT","1");
                        resultMap.put("REALCOUNT",sourceMap.get(1));
                        result.add(resultMap);
                    }

                }
            }
        }
        return result;
    }

    @Override
    public List<BdcCqtjDTO> xntjmx(BdcCqTjQO bdcCqTjQO) {
        //组织ES查询参数
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.existsQuery("startRegionCode"));
        if(StringUtils.isNotBlank(bdcCqTjQO.getProcessDefKey())){
            boolQueryBuilder.must(QueryBuilders.matchQuery("processKey",bdcCqTjQO.getProcessDefKey()));
        }
        RangeQueryBuilder procStartTimeInLong = QueryBuilders.rangeQuery("procStartTimeInLong");
        if(StringUtils.isNotBlank(bdcCqTjQO.getKssj())){
            long kssj = DateUtils.getDayTimeOfZeroHMS(DateUtils.formatDate(bdcCqTjQO.getKssj()));

            procStartTimeInLong.gte(kssj);
        }
        if(StringUtils.isNotBlank(bdcCqTjQO.getJzsj())){
            long jzsj  = DateUtils.getDayTimeOfLastHMS(DateUtils.formatDate(bdcCqTjQO.getJzsj()));
            procStartTimeInLong.lte(jzsj);
        }
        boolQueryBuilder.must(procStartTimeInLong);
        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.size(10000);
        List result = searchAll("custom-stats-proc-*", "doc", searchSourceBuilder.toString(),BdcCqtjDTO.class);
        return result;
    }

    @Override
    public Object xntjCount(BdcCqTjQO bdcCqTjQO) {
        List<Map> result = new ArrayList();
        List<BdcCqtjDTO> xntjmx = xntjmx(bdcCqTjQO);
        if(CollectionUtils.isNotEmpty(xntjmx)){
            Map<String, List<BdcCqtjDTO>> map = xntjmx.stream().filter(bdcCqtjDTO -> StringUtils.isNotBlank(bdcCqtjDTO.getProcDefKey())).collect(
                    Collectors.groupingBy(
                            bdcCqtjDTO -> bdcCqtjDTO.getProcDefKey()
                    ));
            if(MapUtils.isNotEmpty(map)){
                Set<String> keySet = map.keySet();
                for (String key : keySet) {
                    List<BdcCqtjDTO> bdcCqtjDTOS = map.get(key);
                    if(CollectionUtils.isNotEmpty(bdcCqtjDTOS)){
                        //计算每个流程用时
                        BigDecimal customStatisticTime = bdcCqtjDTOS.stream().map(x -> new BigDecimal(x.getCustomStatisticTime())).reduce(BigDecimal.ZERO, BigDecimal::add).divide(new BigDecimal(60),2,BigDecimal.ROUND_HALF_UP);
                        HashMap hashmap = new HashMap();
                        hashmap.put("SLR",bdcCqtjDTOS.get(0).getProcDefName());
                        hashmap.put("REALCOUNT",customStatisticTime);
                        result.add(hashmap);
                    }
                }
            }
        }
        return result;
    }

    public List searchAll(String indexName, String typeName, String query,Class object) {
        String rollId = null;
        boolean all = true;
        List result = new ArrayList<>();
        while (all){
            List sourceList = new ArrayList<>();
            if (rollId == null) {
                try {
                    Search search = new Search.Builder(query)
                            .addIndex(indexName)
                            .addType(typeName)
                            .setParameter(Parameters.SCROLL, "5m")
                            .build();
                    JestResult jestResult = jestClient.execute(search);
                    if (jestResult.isSucceeded()) {
                        rollId =  jestResult.getJsonObject().get("_scroll_id").getAsString();
                        sourceList = jestResult.getSourceAsObjectList(object);
                    }
                } catch (IOException e) {
                    logger.error("异常信息:", e);
                    return null;
                }
            } else {
                try {
                    JestResult jestResult = jestClient.execute(new SearchScroll.Builder(rollId, "5m").build());
                    if (jestResult.isSucceeded()) {
                        rollId =  jestResult.getJsonObject().get("_scroll_id").getAsString();
                        sourceList = jestResult.getSourceAsObjectList(object);
                    }
                } catch (IOException e) {

                    return null;
                }
            }
            if(CollectionUtils.isEmpty(sourceList)) {
                break;
            }
            result.addAll(sourceList);
        }
        return result;
    }
}
