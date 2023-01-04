package cn.gtmap.realestate.building.ui.web;

import cn.gtmap.realestate.building.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.FwYchsDO;
import cn.gtmap.realestate.common.core.service.feign.building.*;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/12/30
 * @description
 */
@Controller
@Validated
@RequestMapping("yctosc")
public class YcToScController extends BaseController {
    @Autowired
    FwLjzFeginService fwLjzFeginService;

    @Autowired
    private FwHsFeignService fwHsFeignService;
    @Autowired
    private FwYcHsFeignService fwYcHsFeignService;

    /**
     * @param fwDcbIndex
     * @return java.util.Map
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 预测转实测
     */
    @ResponseBody
    @RequestMapping("/ychstoschs")
    public Map ychsToSchs(String fwDcbIndex,String qjgldm) {
        Map resultMap = new HashMap();
        if (StringUtils.isNotBlank(fwDcbIndex)) {
            List<FwYchsDO> fwYchsDOList = fwYcHsFeignService.listFwYchsByFwDcbIndex(fwDcbIndex,qjgldm);
            if (CollectionUtils.isNotEmpty(fwYchsDOList)) {
                List<FwHsDO> fwHsDOList = fwHsFeignService.listFwhsByFwDcbIndex(fwDcbIndex,qjgldm);
                if (CollectionUtils.isEmpty(fwHsDOList)) {
                    List<FwHsDO> fwHsList = ychsToSchs(fwYchsDOList,qjgldm);
                    if (CollectionUtils.isNotEmpty(fwHsList)) {
                        resultMap.putAll(returnHtmlResult(true, "成功"));
                    }

                } else {
                    resultMap.putAll(returnHtmlResult(false, "逻辑幢下已有实测户室"));
                }
            }else {
                resultMap.putAll(returnHtmlResult(false, "逻辑幢下无预测户室"));
            }
        } else {
            resultMap.putAll(returnHtmlResult(false, "必要数据为空"));
        }
        return resultMap;
    }

    /**
     * @param fwYchsDOList
     * @return java.util.Map
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 预测转实测
     */
    private List<FwHsDO> ychsToSchs(List<FwYchsDO> fwYchsDOList,String qjgldm) {
        List<FwHsDO> fwHsDOList = new ArrayList<>();
        for (FwYchsDO fwYchsDO : fwYchsDOList) {
            FwHsDO fwHsDO = new FwHsDO();
            BeanUtils.copyProperties(fwYchsDO, fwHsDO);
            fwHsDO.setFwHsIndex(UUIDGenerator.generate());
            fwHsDO.setScjzmj(fwYchsDO.getYcjzmj());
            fwHsDO.setSctnjzmj(fwYchsDO.getYctnjzmj());
            fwHsDO.setScftjzmj(fwYchsDO.getYcftjzmj());
            fwHsDO.setScdxbfjzmj(fwYchsDO.getYcdxbfjzmj());
            fwHsDO.setScqtjzmj(fwYchsDO.getYcqtjzmj());
            fwHsDO.setScftxs(fwYchsDO.getYcftxs());
            fwHsFeignService.insertFwHs(fwHsDO,qjgldm);
            fwHsDOList.add(fwHsDO);
        }
        return fwHsDOList;
    }
}