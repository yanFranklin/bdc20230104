package cn.gtmap.realestate.common.util.configuration.service;

import cn.gtmap.realestate.common.core.domain.building.*;
import cn.gtmap.realestate.common.core.dto.building.GzwDcbResponseDTO;
import cn.gtmap.realestate.common.core.qo.init.BdcQjgldmQO;
import cn.gtmap.realestate.common.core.service.feign.building.*;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.util.configuration.IBdcQjgldmQuery;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2021/08/19
 * @description 权籍管理代码权籍库查询类
 */
@Order(2)
@Service
public class BdcQjgldmQjQuerier implements IBdcQjgldmQuery {
    private static Logger logger = LoggerFactory.getLogger(BdcQjgldmQjQuerier.class);

    @Autowired
    private BdcXmFeignService xmFeignService;
    @Autowired
    private ZdFeignService zdFeignService;
    @Autowired
    private ZhFeignService zhFeignService;
    @Autowired
    private FwHsFeignService fwHsFeignService;
    @Autowired
    private FwYcHsFeignService fwYcHsFeignService;
    @Autowired
    private FwLjzFeginService fwLjzFeginService;
    @Autowired
    private CbzdFeignService cbzdFeignService;
    @Autowired
    private GzwFeignService gzwFeignService;
    @Autowired
    private LqFeignService lqFeignService;
    @Autowired
    private NydFeignService nydFeignService;


    /**
     * 从权籍库查询权籍管理代码
     * @param configName 当前配置项内容
     * @param bdcQjgldmQO 业务数据
     * @return {String} 权籍管理代码
     */
    @Override
    public String queryBdcQjgldm(String configName, BdcQjgldmQO bdcQjgldmQO) {
        if(StringUtils.isBlank(bdcQjgldmQO.getBdcdyh())) {
            return null;
        }

        // 如果不是登记存量数据，登记库无法查询到权籍管理代码，则遍历查询权籍库判断
        String qjgldm = null;
        List<String> qjgldmList = xmFeignService.listQjgldm();
        if(CollectionUtils.isNotEmpty(qjgldmList)) {
            for(String dm : qjgldmList) {
                ZdDjdcbDO zdDjdcbDO = zdFeignService.queryZdByBdcdyh(bdcQjgldmQO.getBdcdyh(), dm);
                if(null != zdDjdcbDO && StringUtils.isNotBlank(zdDjdcbDO.getBdcdyh())) {
                    qjgldm = dm; break;
                }

                ZhDjdcbDO zhDjdcbDO = zhFeignService.queryZhByBdcdyh(bdcQjgldmQO.getBdcdyh(), dm);
                if(null != zhDjdcbDO && StringUtils.isNotBlank(zhDjdcbDO.getBdcdyh())) {
                    qjgldm = dm; break;
                }

                FwHsDO fwHsDO = fwHsFeignService.queryFwhsByBdcdyh(bdcQjgldmQO.getBdcdyh(), dm);
                if(null != fwHsDO && StringUtils.isNotBlank(fwHsDO.getBdcdyh())) {
                    qjgldm = dm; break;
                }

                FwYchsDO ychsDO = fwYcHsFeignService.queryFwYcHsByBdcdyh(bdcQjgldmQO.getBdcdyh(), dm);
                if(null != ychsDO && StringUtils.isNotBlank(ychsDO.getBdcdyh())) {
                    qjgldm = dm; break;
                }

                FwLjzDO fwLjzDO = fwLjzFeginService.queryLjzByBdcdyh(bdcQjgldmQO.getBdcdyh(), dm);
                if(null != fwLjzDO && StringUtils.isNotBlank(fwLjzDO.getBdcdyh())) {
                    qjgldm = dm; break;
                }

                CbzdDcbDO cbzdDcbDO = cbzdFeignService.queryCbzdByBdcdyh(bdcQjgldmQO.getBdcdyh(), dm);
                if(null != cbzdDcbDO && StringUtils.isNotBlank(cbzdDcbDO.getBdcdyh())) {
                    qjgldm = dm; break;
                }

                GzwDcbResponseDTO gzw = gzwFeignService.queryGzwxxByBdcdyh(bdcQjgldmQO.getBdcdyh(), dm);
                if(null != gzw && null != gzw.getGzwDcbDO() && StringUtils.isNotBlank(gzw.getGzwDcbDO().getBdcdyh())) {
                    qjgldm = dm; break;
                }

                LqDcbDO lqDcbDO = lqFeignService.queryLqByBdcdyh(bdcQjgldmQO.getBdcdyh(), dm);
                if(null != lqDcbDO && StringUtils.isNotBlank(lqDcbDO.getBdcdyh())) {
                    qjgldm = dm; break;
                }

                NydDjdcbDO nydDjdcbDO = nydFeignService.queryNydByBdcdyh(bdcQjgldmQO.getBdcdyh(), dm);
                if(null != nydDjdcbDO && StringUtils.isNotBlank(nydDjdcbDO.getBdcdyh())) {
                    qjgldm = dm; break;
                }
            }
        }

        logger.info("获取指定区划配置项{}值，从权籍库查询数据[{}]对应权籍管理代码{}", configName, bdcQjgldmQO, qjgldm);
        return qjgldm;
    }
}