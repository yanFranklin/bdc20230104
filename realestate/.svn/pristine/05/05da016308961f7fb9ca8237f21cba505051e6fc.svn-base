package cn.gtmap.realestate.exchange.service.impl.inf.wwsq;

import cn.gtmap.realestate.common.matcher.ProcessInstanceClientMatcher;
import cn.gtmap.gtc.workflow.domain.manage.ProcessInstanceData;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSfxmFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSfxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlZdFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.exchange.core.dto.wwsq.sfxx.response.WwsqSfxxResponseData;
import cn.gtmap.realestate.exchange.core.dto.wwsq.sfxx.response.WwsqSfxxResponseQlr;
import cn.gtmap.realestate.exchange.core.dto.wwsq.sfxx.response.WwsqSfxxResponseSfxm;
import cn.gtmap.realestate.exchange.core.dto.wwsq.sfxx.response.WwsqSfxxResponseSfxx;
import cn.gtmap.realestate.exchange.service.inf.CommonService;
import cn.gtmap.realestate.exchange.service.inf.wwsq.SfxxService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author <a herf="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/10/17
 * @description
 */
@Service
public class SfxxServiceImpl implements SfxxService {
    @Autowired
    private CommonService commonService;
    @Autowired
    private BdcSlSfxxFeignService bdcSlSfxxFeignService;
    @Autowired
    private BdcSlSfxmFeignService bdcSlSfxmFeignService;
    @Autowired
    private BdcZdFeignService bdcZdFeignService;
    @Autowired
    private BdcSlZdFeignService bdcSlZdFeignService;
    @Autowired
    private ProcessInstanceClientMatcher processInstanceClient;
    @Autowired
    private DozerBeanMapper exchangeDozerMapper;

    @Override
    public WwsqSfxxResponseData getSfxx(Map map) {
        WwsqSfxxResponseData wwsqSfxxResponseData = new WwsqSfxxResponseData();
        String spxtywh = MapUtils.getString(map, "spxtywh");
        if (StringUtils.isNotBlank(spxtywh)) {
            List<BdcXmDO> bdcXmDOList = commonService.listBdcXmBySpxtywh(spxtywh);
            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                BdcXmDO bdcXmDO = bdcXmDOList.get(0);
                exchangeDozerMapper.map(bdcXmDO, wwsqSfxxResponseData, "BdcxmSfxxData");

                ProcessInstanceData processInstanceData = processInstanceClient.getProcessInstance(bdcXmDO.getGzlslid());
                if (processInstanceData != null) {
                    wwsqSfxxResponseData.setLcmc(processInstanceData.getProcessDefinitionName());
                }

                // 权利人信息
                // 组合流程返回所有项目的权利人
                List<WwsqSfxxResponseQlr> wwsqSfxxResponseQlrList = new ArrayList<>();
                for (BdcXmDO xm : bdcXmDOList) {
                    List<BdcQlrDO> bdcQlrDOList = commonService.listBdcQlrByXmid(bdcXmDO.getXmid(), null);
                    if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                        for (BdcQlrDO bdcQlrDO : bdcQlrDOList) {
                            WwsqSfxxResponseQlr wwsqSfxxResponseQlr = new WwsqSfxxResponseQlr();
                            exchangeDozerMapper.map(bdcQlrDO, wwsqSfxxResponseQlr, "BdcqlrSfxxQlr");
                            wwsqSfxxResponseQlrList.add(wwsqSfxxResponseQlr);
                        }
                    }
                }
                wwsqSfxxResponseData.setQlrList(wwsqSfxxResponseQlrList);

                // 收费信息
                List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxFeignService.listBdcSlSfxxByGzlslid(bdcXmDO.getGzlslid());
                if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
                    List<WwsqSfxxResponseSfxx> wwsqSfxxResponseSfxxList = new ArrayList<>();
                    // 收费项目
                    for (BdcSlSfxxDO bdcSlSfxxDO : bdcSlSfxxDOList) {
                        WwsqSfxxResponseSfxx wwsqSfxxResponseSfxx = new WwsqSfxxResponseSfxx();
                        exchangeDozerMapper.map(bdcSlSfxxDO, wwsqSfxxResponseSfxx, "BdcsfxxSfxxSfxx");

                        List<BdcSlSfxmDO> bdcSlSfxmDOList = bdcSlSfxmFeignService.listBdcSlSfxmBySfxxid(bdcSlSfxxDO.getSfxxid());
                        if (CollectionUtils.isNotEmpty(bdcSlSfxmDOList)) {
                            List<WwsqSfxxResponseSfxm> wwsqSfxxResponseSfxmList = new ArrayList<>();
                            for (BdcSlSfxmDO bdcSlSfxmDO : bdcSlSfxmDOList) {
                                WwsqSfxxResponseSfxm wwsqSfxxResponseSfxm = new WwsqSfxxResponseSfxm();
                                exchangeDozerMapper.map(bdcSlSfxmDO, wwsqSfxxResponseSfxm, "BdcsfxmSfxxSfxm");
                                wwsqSfxxResponseSfxmList.add(wwsqSfxxResponseSfxm);
                            }
                            wwsqSfxxResponseSfxx.setSfxmList(wwsqSfxxResponseSfxmList);
                        }
                        wwsqSfxxResponseSfxxList.add(wwsqSfxxResponseSfxx);
                    }
                    wwsqSfxxResponseData.setSfxxList(wwsqSfxxResponseSfxxList);
                }
            }
        }
        return wwsqSfxxResponseData;
    }
}
