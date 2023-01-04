package cn.gtmap.realestate.natural.core.service.impl;

import cn.gtmap.realestate.common.core.domain.natural.ZrzyXmDO;
import cn.gtmap.realestate.common.core.dto.natural.ZrzyCqzhDTO;
import cn.gtmap.realestate.common.core.service.feign.register.BdcRyzdFeignService;
import cn.gtmap.realestate.natural.core.bo.ZrzyZscCqzhBO;
import cn.gtmap.realestate.natural.core.service.ZrzyDjcqzhService;
import cn.gtmap.realestate.natural.core.service.ZrzyXmService;
import cn.gtmap.realestate.natural.service.ZrzyZsService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author wyh
 * @version 1.0
 * @date 2021/11/16 9:49
 */
@Service
public class ZrzyDjcqzhServiceImpl implements ZrzyDjcqzhService {
    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ZrzyDjcqzhServiceImpl.class);
    /**
     * 接口请求占位变量为空导致的默认值
     */
    private static final String DEFAULT_XMID = "{xmid}";

    /**
     * 证书逻辑
     */
    @Autowired
    ZrzyZsService zrzyZsService;
    /**
     * 项目服务
     */
    @Autowired
    ZrzyXmService zrzyXmService;
    /**
     * 冗余字段
     */
    @Autowired
    BdcRyzdFeignService bdcRyzdFeignService;
    /**
     * 公共证号逻辑处理
     */
    @Autowired
    private ZrzyDjcqzhGgServiceImpl zrzyDjcqzhGgService;

    /**
     * 证号：正常生成证号处理
     */
    @Autowired
    private ZrzyDjcqzhScServiceImpl zrzyDjcqzhScService;

    /**
     * 生成证号是否采用锁方案，默认true采用
     */
    @Value("${cqzh.needlock:true}")
    private Boolean zrzycqzhNeedLock;

    /**
     * 线程重试生成证号开始时间
     */
    static ThreadLocal<Long> threadTryTime = new ThreadLocal<>();

    // 暂停一段时间继续处理
    static Random random = new Random();

    /**
     * @param processInsId 流程实例ID
     * @return {List} 不动产权证号信息集合
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 流程关联项目生成证书（明）号
     */
    @Override
    public void generateCqzhOfPro(String processInsId) {
        try {
            zrzyCqzh(processInsId);
            threadTryTime.remove();
        } catch (Exception e) {
            if (zrzycqzhNeedLock) {
                // 采用加锁方案，不处理，直接继续抛出异常
                throw e;
            } else {
                Long startTime = threadTryTime.get();
                if (null == startTime) {
                    threadTryTime.set(System.currentTimeMillis());
                } else {
                    long time = System.currentTimeMillis() - startTime;
                    if ((time / 1000) > 300) {
                        LOGGER.error("当前{}生成证号失败重试超过5分钟，不再重试，可能数据问题需要核查数据", processInsId);
                        threadTryTime.remove();
                        return;
                    }
                }

                // 采用缓存取号+错误重试方案
                // 说明：此种方案不采用分布式锁，为了避免同步导致大批量操作导致证号严重耗时；
                //      从缓存取号，如果有人工手动处理数据库数据，可能导致缓存和数据库最大号不一致，那么程序保存时候会冲突失败，那么这里重新请求处理
                try {
                    TimeUnit.MILLISECONDS.sleep(random.nextInt(5000));
                } catch (InterruptedException ex) {
                    LOGGER.error("当前{}生成证号失败，准备重试休眠异常", processInsId);
                }

                LOGGER.info("当前{}生成证号失败，采用重试方案，重新请求生成证号", processInsId);
                generateCqzhOfPro(processInsId);
            }
        }
    }

    /**
     * @param processInsId 流程实例ID
     * @return {List} 不动产权证号信息集合
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 生成证书（明）号
     */
    private void zrzyCqzh(String processInsId) {
        if (StringUtils.isEmpty(processInsId)) {
            throw new NullPointerException("证书子系统生成流程关联证号异常：参数为空！");
        }

        // 查询当前流程关联的项目信息
        List<ZrzyXmDO> zrzyXmDOList = zrzyXmService.queryAllZrzyXmByGzlslid(processInsId);
        if (CollectionUtils.isEmpty(zrzyXmDOList)) {
            LOGGER.warn("证书子系统生成证号中止：未查询到工作流实例ID对应项目信息！");
            return;
        }

        // 保存证号信息集合（key XMID ; value 项目证号）
        Map<String, List<ZrzyCqzhDTO>> bdcqzhMap;
        LOGGER.info("生成证号，当前场景：单个项目或者组合流程生成证号，processInsId {}", processInsId);
        bdcqzhMap = newBdcqzhDgZh(zrzyXmDOList);

        // 更新冗余字段
        if (MapUtils.isNotEmpty(bdcqzhMap)) {
            LOGGER.info("processInsId {} 生成证号结束，更新证号冗余字段：{}", processInsId, JSON.toJSONString(bdcqzhMap));
            //bdcRyzdFeignService.updateRyzdBdcqzh(bdcqzhMap);
        }
    }

    /**
     * @param zrzyXmDOList 流程项目集合
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 单个项目或者组合流程生成证号
     */
    private Map<String, List<ZrzyCqzhDTO>> newBdcqzhDgZh(List<ZrzyXmDO> zrzyXmDOList) {
        Map<String, List<ZrzyCqzhDTO>> bdcqzhMap = new HashMap<>(zrzyXmDOList.size());
        //排序
        zrzyXmDOList.sort(Comparator.comparing(ZrzyXmDO::getXmid));
        //组合和单一流程处理
        for (ZrzyXmDO zrzyXmDO : zrzyXmDOList) {
            List<ZrzyCqzhDTO> list = generateBdcqzh(zrzyXmDO.getXmid());
            if (CollectionUtils.isNotEmpty(list)) {
                bdcqzhMap.put(zrzyXmDO.getXmid(), list);
            }
        }

        return bdcqzhMap;
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   xmid 项目ID
     * @return  {List} 不动产权证号信息集合
     * @description  生成单个项目不动产项目证书（明）号
     */
    public List<ZrzyCqzhDTO> generateBdcqzh(String xmid){
        if (StringUtils.isBlank(xmid) || DEFAULT_XMID.equals(xmid)) {
            throw new NullPointerException("生成证号入参xmid为空");
        }

        // 新生成证号
        LOGGER.info("生成证号，当前证号新生成证号 xmid {}", xmid);
        ZrzyZscCqzhBO zrzyZscCqzhBO = zrzyDjcqzhGgService.getBdcqzhBO(xmid);

        if(zrzycqzhNeedLock) {
            // 采用加锁方案
            return zrzyDjcqzhScService.generateZrzycqzh(zrzyZscCqzhBO);
        } else {
            // 不采用加锁方案
            return zrzyDjcqzhScService.generateZrzycqzhWithNoLock(zrzyZscCqzhBO);
        }
    }
}
