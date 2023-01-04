package cn.gtmap.realestate.certificate.service.impl;

import cn.gtmap.realestate.certificate.core.bo.BdcBdcqzhBO;
import cn.gtmap.realestate.certificate.core.service.BdcZsService;
import cn.gtmap.realestate.certificate.service.BdcBdcqzhService;
import cn.gtmap.realestate.certificate.util.Constants;
import cn.gtmap.realestate.common.core.domain.BdcCshFwkgSlDO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcBdcqzhDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcRyzdFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/08/06
 * @description  不动产权证号相关逻辑处理接口定义
 */
@Service
public class BdcBdcqzhServiceImpl implements BdcBdcqzhService {
    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcBdcqzhServiceImpl.class);
    /**
     * 接口请求占位变量为空导致的默认值
     */
    private static final String DEFAULT_XMID = "{xmid}";

    /**
     * 证书逻辑
     */
    @Autowired
    BdcZsService bdcZsService;
    /**
     * 项目服务
     */
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    /**
     * 冗余字段
     */
    @Autowired
    BdcRyzdFeignService bdcRyzdFeignService;
    /**
     * 公共证号逻辑处理
     */
    @Autowired
    private BdcBdcqzhGgServiceImpl bdcBdcqzhGgService;
    /**
     * 证号：不换证处理
     */
    @Autowired
    private BdcBdcqzhBhzServiceImpl bdcqzhBhzService;
    /**
     * 证号：正常生成证号处理
     */
    @Autowired
    private BdcBdcqzhScServiceImpl bdcBdcqzhScService;

    /**
     * 生成证号是否采用锁方案，默认true采用
     */
    @Value("${bdcqzh.needlock:true}")
    private Boolean bdcqzhNeedLock;

    /**
     * 线程重试生成证号开始时间
     */
    private ConcurrentHashMap<String, Long> threadTryTime = new ConcurrentHashMap<>();


    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   processInsId 流程实例ID
     * @return  {List} 不动产权证号信息集合
     * @description  流程关联项目生成证书（明）号
     */
    @Override
    public void generateBdcqzhOfPro(String processInsId){
        try {
            bdcqzh(processInsId);

            this.removeThreadTime(Thread.currentThread());
        } catch (Exception e) {
            if(bdcqzhNeedLock) {
                // 采用加锁方案，不处理，直接继续抛出异常
                throw e;
            } else {
                Long startTime = threadTryTime.get(String.valueOf(Thread.currentThread().getId()));
                if(null == startTime) {
                    threadTryTime.put(String.valueOf(Thread.currentThread().getId()), System.currentTimeMillis());
                } else {
                    long time = System.currentTimeMillis() - startTime;
                    if((time / 1000) > 300) {
                        LOGGER.error("当前{}生成证号失败重试超过5分钟，不再重试，可能数据问题需要核查数据", processInsId);
                        this.removeThreadTime(Thread.currentThread());
                        return;
                    }
                }

                // 采用缓存取号+错误重试方案
                // 说明：此种方案不采用分布式锁，为了避免同步导致大批量操作导致证号严重耗时；
                //      从缓存取号，如果有人工手动处理数据库数据，可能导致缓存和数据库最大号不一致，那么程序保存时候会冲突失败，那么这里重新请求处理
                try {
                    // 暂停一段时间继续处理
                    Random random = new Random();
                    TimeUnit.MILLISECONDS.sleep(random.nextInt(5000));
                } catch (InterruptedException ex) {
                    LOGGER.error("当前{}生成证号失败，准备重试休眠异常", processInsId);
                }

                LOGGER.info("当前{}生成证号失败，采用重试方案，重新请求生成证号", processInsId);
                generateBdcqzhOfPro(processInsId);
            }
        }
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   processInsId 流程实例ID
     * @return  {List} 不动产权证号信息集合
     * @description  生成证书（明）号
     */
    private void bdcqzh(String processInsId) {
        if(StringUtils.isEmpty(processInsId)){
            throw new NullPointerException("证书子系统生成流程关联证号异常：参数为空！");
        }

        // 查询当前流程关联的项目信息
        List<BdcXmDTO> bdcXmDOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(processInsId);
        if(CollectionUtils.isEmpty(bdcXmDOList)){
            LOGGER.warn("证书子系统生成证号中止：未查询到工作流实例ID对应项目信息！");
            return;
        }

        // 保存证号信息集合（key XMID ; value 项目证号）
        Map<String, List<BdcBdcqzhDTO>> bdcqzhMap;
        // 根据不同项目类型生成证号
        int xmlx = bdcXmFeignService.makeSureBdcXmLx(processInsId);
        if(xmlx == Constants.PLLC){
            LOGGER.info("生成证号，当前场景：批量流程，processInsId {}", processInsId);
            bdcqzhMap = this.newBdcqzhPllc(processInsId, bdcXmDOList);
        } else {
            LOGGER.info("生成证号，当前场景：单个项目或者组合流程生成证号，processInsId {}", processInsId);
            bdcqzhMap = this.newBdcqzhDgZh(bdcXmDOList);
        }

        // 更新冗余字段
        if(MapUtils.isNotEmpty(bdcqzhMap)){
            LOGGER.info("processInsId {} 生成证号结束，更新证号冗余字段：{}", processInsId, JSON.toJSONString(bdcqzhMap));
            bdcRyzdFeignService.updateRyzdBdcqzh(bdcqzhMap);
        }
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   xmid 项目ID
     * @return  {List} 不动产权证号信息集合
     * @description  生成单个项目不动产项目证书（明）号
     */
    @Override
    public List<BdcBdcqzhDTO> generateBdcqzh(String xmid){
        if (StringUtils.isBlank(xmid) || DEFAULT_XMID.equals(xmid)) {
            throw new NullPointerException("生成证号入参xmid为空");
        }

        // 1、查询初始化实例表，判断是否生成证书
        BdcCshFwkgSlDO bdcCshFwkgSlDO = bdcXmFeignService.queryFwkgsl(xmid);
        if(null == bdcCshFwkgSlDO || !CommonConstantUtils.SF_S_DM.equals(bdcCshFwkgSlDO.getSfsczs())) {
            LOGGER.warn("生成证号时未查询到开关表信息或不需要生成证号，生成证号中止，对应项目id：{}", xmid);
            return Collections.emptyList();
        }

        // 2、根据不同场景生成证号
        if(CommonConstantUtils.SF_F_DM.equals(bdcCshFwkgSlDO.getSfhz())){
            // 不换证情况（即沿用之前证号，不生成新的）
            LOGGER.info("生成证号，当前证号不换证，沿用之前旧的证号 xmid {}", xmid);
            return bdcqzhBhzService.generateBdcqzh(xmid);
        } else {
            // 新生成证号
            LOGGER.info("生成证号，当前证号新生成证号 xmid {}", xmid);
            BdcBdcqzhBO bdcBdcqzhBO = bdcBdcqzhGgService.getBdcqzhBO(xmid);

            if(bdcqzhNeedLock) {
                // 采用加锁方案
                return bdcBdcqzhScService.generateBdcqzh(bdcBdcqzhBO);
            } else {
                // 不采用加锁方案
                return bdcBdcqzhScService.generateBdcqzhWithNoLock(bdcBdcqzhBO);
            }
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcXmDOList 流程项目集合
     * @description 批量流程生成证号
     */
    private Map newBdcqzhPllc(String processInsId, List<BdcXmDTO> bdcXmDOList){
        String type = this.getPlzhTypeByGzlslid(processInsId);
        Map<String, List<BdcBdcqzhDTO>> bdcqzhMap = new HashMap<>(bdcXmDOList.size());

        if(Constants.PLLC_BHZ.equals(type)){
            // 不换证场景
            LOGGER.info("生成证号，当前证号不换证，沿用之前旧的证号 processInsId {}", processInsId);
            for(BdcXmDTO bdcXmDTO : bdcXmDOList){
                List<BdcBdcqzhDTO> bdcBdcqzhDTOList = bdcqzhBhzService.generateBdcqzh(bdcXmDTO.getXmid());
                bdcqzhMap.put(bdcXmDTO.getXmid(), bdcBdcqzhDTOList);
            }
        }
        else if(Constants.PLLC_NEW.equals(type)){
            // 正常场景生成证号
            LOGGER.info("生成证号，正常场景生成新的证号 processInsId {}", processInsId);

            List<BdcBdcqzhDTO> bdcBdcqzhDTOList;
            if(bdcqzhNeedLock) {
                bdcBdcqzhDTOList = bdcBdcqzhScService.generateBdcqzhPllc(processInsId);
            } else {
                bdcBdcqzhDTOList = bdcBdcqzhScService.generateBdcqzhPllcWithNoLock(processInsId);
            }

            bdcqzhMap = bdcBdcqzhDTOList.stream().collect(Collectors.groupingBy(BdcBdcqzhDTO::getXmid));
        }
        else {
            LOGGER.warn("生成证号时未查询到开关表信息或不需要生成证号，生成证号中止，对应流程实例id：{}", processInsId);
        }

        return bdcqzhMap;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcXmDOList 流程项目集合
     * @description 单个项目或者组合流程生成证号
     */
    private Map newBdcqzhDgZh(List<BdcXmDTO> bdcXmDOList){
        Map<String, List<BdcBdcqzhDTO>> bdcqzhMap = new HashMap<>(bdcXmDOList.size());
        //排序
        bdcXmDOList.sort((o1, o2) -> o1.getXmid().compareTo(o2.getXmid()));
        //组合和单一流程处理
        for(BdcXmDTO bdcXmDTO: bdcXmDOList){
            List<BdcBdcqzhDTO> list = this.generateBdcqzh(bdcXmDTO.getXmid());
            if(CollectionUtils.isNotEmpty(list)){
                bdcqzhMap.put(bdcXmDTO.getXmid(), list);
            }
        }

        return bdcqzhMap;
    }

    /**
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @param gzlslid gzlslid
     * @return {String} 类型
     * @description 判断批量流程生成证号场景
     */
    private String getPlzhTypeByGzlslid(String gzlslid){
        if (StringUtils.isBlank(gzlslid)) {
            return Constants.PLLC_NULL;
        }

        // 查询初始化实例表，判断是否生成证书
        List<BdcCshFwkgSlDO> bdcCshFwkgSlDOList = bdcXmFeignService.queryFwkgslByGzlslid(gzlslid);
        if(CollectionUtils.isNotEmpty(bdcCshFwkgSlDOList)){
            for(BdcCshFwkgSlDO bdcCshFwkgSlDO : bdcCshFwkgSlDOList){
                // 任意有一个是 不换证的 则认定为此流程是不换证流程
                if(CommonConstantUtils.SF_F_DM.equals(bdcCshFwkgSlDO.getSfhz())){
                    return Constants.PLLC_BHZ;
                }
                // 任意有一个是 换证的 则认定为此流程是新生成证书的流程
                if(CommonConstantUtils.SF_S_DM.equals(bdcCshFwkgSlDO.getSfsczs()) ){
                    return Constants.PLLC_NEW;
                }
            }
        }
        // 当循环完实例表后，既没有换证也没有新生成，则认定为 不生成证书流程
        return Constants.PLLC_NULL;
    }

    /**
     * 清除生成证号重试开始时间信息
     * @param thread 运行线程
     */
    private void removeThreadTime(Thread thread) {
        String threadId = String.valueOf(thread.getId());

        if(threadTryTime.containsKey(threadId)) {
            threadTryTime.remove(threadId);
        }
    }
}
