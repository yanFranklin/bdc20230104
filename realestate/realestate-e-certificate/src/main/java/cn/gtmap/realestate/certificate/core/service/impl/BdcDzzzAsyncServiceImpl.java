package cn.gtmap.realestate.certificate.core.service.impl;

import cn.gtmap.realestate.certificate.core.mapper.BdcDzzzZzxxMapper;
import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzZzxxDO;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzAsyncService;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzZzxxService;
import cn.gtmap.realestate.certificate.core.service.appear.BdcDzzzCityService;
import cn.gtmap.realestate.certificate.util.DateUtil;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @version 1.0 2019/1/22
 * @description 请求记录
 */
@Lazy
@Service
public class BdcDzzzAsyncServiceImpl implements BdcDzzzAsyncService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private BdcDzzzZzxxService bdcDzzzZzxxService;
    @Autowired
    private BdcDzzzCityService bdcDzzzCityService;
    @Resource
    private BdcDzzzZzxxMapper bdcDzzzZzxxMapper;

    @Override
    @Async
    public void zzxxIncrement(BdcDzzzZzxxDO bdcDzzzZzxxDO) {
        logger.info("定时器检测昨天未生成PDF数据, zzbs：{}，开始执行时间：{} ", bdcDzzzZzxxDO.getZzbs(), DateUtil.now());

        DzzzResponseModel resultModel = bdcDzzzZzxxService.createPdfBdcDzzzByZzid(bdcDzzzZzxxDO.getZzid());

        logger.info("定时器检测昨天未生成PDF数据, zzbs：{}，结束执行时间：{}，执行结果：{}", bdcDzzzZzxxDO.getZzbs(), DateUtil.now(), JSON.toJSONString(resultModel));
    }

    /**
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @param bdcDzzzZzxxDO
     * @description 根据zzbs更新证照信息表
     */
    @Override
    public void updateDzzzByZzbs(BdcDzzzZzxxDO bdcDzzzZzxxDO) {
        if(bdcDzzzZzxxDO !=null && StringUtils.isNotBlank(bdcDzzzZzxxDO.getZzbs())){
            try {
                // 更新属性信息
                bdcDzzzZzxxMapper.updateBdcDzzzByZzbs(bdcDzzzZzxxDO);
            } catch (Exception e) {
                logger.info("更新电子证照数据异常，zzbs：{}", bdcDzzzZzxxDO.getZzbs());
            }
        }
    }

    @Override
    public void syncDzzz(String zzbs) {
        try {
            bdcDzzzCityService.syncDzzz(zzbs);
        } catch (Exception e) {
            logger.info("定时市级同步省级电子证照数据异常，zzbs：{}", zzbs);
        }

    }

    @Override
    @Async("taskExecutor")
    public void syncDzzz( List<Map<String,Object>> list) {
        if (CollectionUtils.isNotEmpty(list)) {
            for (Map<String,Object> map : list) {
                try {
                    bdcDzzzCityService.syncDzzz((String)map.get("zzbs"));
                } catch (Exception e) {
                    logger.info("定时市级同步省级电子证照数据异常，zzbs：{}", map.get("zzbs"));
                }
            }
        }
    }

    @Override
    public void syncDzzzDownloadInfo(String zzbs) {
        try {
            bdcDzzzCityService.syncDzzzDownloadInfo(zzbs);
        } catch (Exception e) {
            logger.info("市级同步电子证照加注件信息数据异常，zzbs：{}", zzbs);
        }
    }
}
