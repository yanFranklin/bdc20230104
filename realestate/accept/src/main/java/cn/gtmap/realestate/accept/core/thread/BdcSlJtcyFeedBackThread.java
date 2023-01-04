package cn.gtmap.realestate.accept.core.thread;

import cn.gtmap.realestate.accept.core.service.BdcSlJtcyService;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJtcyDO;
import cn.gtmap.realestate.common.core.support.thread.CommonThread;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/1/13.
 * @description 南通获取不动产家庭成员反馈接口信息线程类
 */
public class BdcSlJtcyFeedBackThread extends CommonThread {
    /**
     * 日志组件
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcSlJtcyFeedBackThread.class);

    /**
     * 南通调用省级平台居民身份信息查询反馈信息接口BeanName
     */
    private static final String FEED_BACK_BEAN_NAME = "acceptJtcyCxfk";
    /**
     * 线程等待时间
     * 由于南通省级平台家庭成员接口，先调用申请接口后，需要1分钟在调用反馈接口才能获取到返回值。
     */
    private static final Integer WAIT_TIME = 1000 * 60;
    /**
     * 申请人ID
     */
    private String sqrid;
    /**
     * 证件号
     */
    private List<String> zjhList;
    /**
     * 家庭成员实现类
     */
    private BdcSlJtcyService bdcSlJtcyService;

    public BdcSlJtcyFeedBackThread(String sqrid, List<String> zjhList, BdcSlJtcyService bdcSlJtcyService){
        this.sqrid = sqrid;
        this.zjhList = zjhList;
        this.bdcSlJtcyService = bdcSlJtcyService;
    }

    /**
     * 默认执行方法
     */
    @Override
    public void execute() throws Exception {
        // 1、等待一分钟调用家庭成员反馈接口
        Thread.sleep(WAIT_TIME);
        List<BdcSlJtcyDO> bdcSlJtcyDOList = new ArrayList<>(10);
        for(String zjh: zjhList){
            // 南通家庭成员接口需等待60s后，在调用家庭成员反馈接口
            Object feedBackResp = bdcSlJtcyService.getGaxx(zjh, FEED_BACK_BEAN_NAME);
            LOGGER.info("证件号:{},调取南通家庭成员反馈接口成功,返回结果：{}", zjh, feedBackResp);
            bdcSlJtcyDOList.addAll(JSONObject.parseArray(JSONObject.toJSONString(feedBackResp), BdcSlJtcyDO.class));
        }
        // 2、家庭成员根据证件号去重处理
        if(CollectionUtils.isNotEmpty(bdcSlJtcyDOList)) {
            List<BdcSlJtcyDO> jtcyList = bdcSlJtcyDOList.stream().filter(jtcy -> StringUtils.isNotBlank(jtcy.getZjh())).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(jtcyList)) {
                List<BdcSlJtcyDO> bdcSlJtcyDOS = jtcyList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getZjh()))), ArrayList::new));
                for(BdcSlJtcyDO bdcSlJtcyDO:bdcSlJtcyDOS){
                    bdcSlJtcyDO.setJtcyid(UUIDGenerator.generate16());
                    bdcSlJtcyDO.setSqrid(sqrid);
                    bdcSlJtcyService.insertBdcSlJtcy(bdcSlJtcyDO);
                }
            }
        }
    }
}
