package cn.gtmap.realestate.exchange.service.impl.inf.wwsq;

import cn.gtmap.realestate.common.core.domain.BdcFctdPpgxDO;
import cn.gtmap.realestate.common.core.domain.BdcFdcqDO;
import cn.gtmap.realestate.common.core.domain.BdcJsydsyqDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.service.feign.building.FwHsFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcPpFeignService;
import cn.gtmap.realestate.exchange.core.dto.wwsq.grdacx.request.GrdacxRequestBody;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcdjMapper;
import cn.gtmap.realestate.exchange.service.inf.CommonService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2021/4/14
 * @description 特殊字段相关逻辑处理服务
 */

@Service
@Validated
public class SpecialServiceImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpecialServiceImpl.class);

    @Autowired
    private CommonService commonService;

    @Autowired
    private BdcPpFeignService ppFeignService;

    @Autowired
    private BdcdjMapper bdcdjMapper;

    @Autowired
    private FwHsFeignService fwHsFeignService;

    /**
     * 字段特殊逻辑处理
     *
     * @param xmid
     * @return map
     * @Date 2021/4/14
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public Map<String, Object> getTszd(String xmid) {
        LOGGER.info(xmid);
        Double tdsyqmj = new Double(0);
        if (StringUtils.isNotBlank(xmid)) {
            BdcXmDO xmDO = commonService.getBdcXmByXmid(xmid);
            if (null != xmDO) {
                //1、首先先去bdc_fdcq表找tdsyqmj字段，如果该字段有值，返回该字段的值；
                //2、上述没有值的话，则去找是否有匹配关系，若有匹配关系，则取bdc_jsydsyq表中的syqmj字段；
                //3、若无匹配关系，则再去权籍库中取f值，取fw_hs表的fttdmj字段
                BdcFdcqDO fdcqDO = commonService.getFdcqByXmid(xmid);
                if (null != fdcqDO && null != fdcqDO.getTdsyqmj()) {
                    tdsyqmj = fdcqDO.getTdsyqmj();
                } else {
                    List<BdcFctdPpgxDO> ppgxDOList = ppFeignService.queryBdcFctdPpgx(xmid);
                    if (CollectionUtils.isNotEmpty(ppgxDOList)) {
                        GrdacxRequestBody grdacxRequestBody = new GrdacxRequestBody();
                        grdacxRequestBody.setLikeBdcdyh(xmDO.getBdcdyh());
                        List<BdcJsydsyqDO> jsydsyqDOList = bdcdjMapper.queryBdcJsydsyq(grdacxRequestBody);
                        if (CollectionUtils.isNotEmpty(jsydsyqDOList) && null != jsydsyqDOList.get(0).getSyqmj()) {
                            tdsyqmj = jsydsyqDOList.get(0).getSyqmj();
                        } else {
                            FwHsDO fwHsDO = fwHsFeignService.queryFwhsByBdcdyh(xmDO.getBdcdyh(),"");
                            if (null != fwHsDO) {
                                tdsyqmj = fwHsDO.getFttdmj();
                            }
                        }
                    } else {
                        FwHsDO fwHsDO = fwHsFeignService.queryFwhsByBdcdyh(xmDO.getBdcdyh(),"");
                        if (null != fwHsDO) {
                            tdsyqmj = fwHsDO.getFttdmj();
                        }
                    }
                }

            }
        }
        HashMap resultMap = new HashMap(1);
        resultMap.put("tdsyqmj", tdsyqmj);
        return resultMap;
    }

}
