package cn.gtmap.realestate.building.ui.web;

import cn.gtmap.realestate.building.ui.core.vo.FwFcqlrListVo;
import cn.gtmap.realestate.building.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.building.*;
import cn.gtmap.realestate.common.core.service.feign.building.BuildingZdConvertFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.FwFcqlrFeignService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a herf="mailto:shaoliyao@gtmap.cn">sly</a>
 * @version 1.0, 2018/12/11
 * @description
 */
@Controller
@RequestMapping("fwqlr")
public class FwQlrController extends BaseController {
    @Autowired
    FwFcqlrFeignService fwFcqlrFeignService;

    @Autowired
    BuildingZdConvertFeignService buildingZdConvertFeignService;

    /**
     * 根据房屋index获取所有权利人
     *
     * @param fwIndex
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listqlr")
    public Map listQlr(@NotBlank(message = "外键不能为空") String fwIndex) {
        Map resultMap = new HashMap();
        List<FwFcqlrDO> fcqlrDOList = fwFcqlrFeignService.listQlr(fwIndex,"");
        if (CollectionUtils.isNotEmpty(fcqlrDOList)) {
            resultMap.put("data", fcqlrDOList);
            resultMap.putAll(returnHtmlResult(true, "成功"));
        }
        return resultMap;

    }

    /**
     * 获得所有权利人页面需要的字典项
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/mapszd")
    public Map mapsQlrZd() {
        return getZdList(new Class[]{SZdZjllxDO.class, SZdGyfsDO.class, SZdQlrxzDO.class, SZdQlrxbDO.class});
    }

    /**
     * 新增或者修改多个权利人
     *
     * @param qlrList
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/insertorupdate")
    public Map insertOrUpdateQlr(FwFcqlrListVo qlrList) {
        if (qlrList != null && StringUtils.isNotBlank(qlrList.getFwIndex()) && CollectionUtils.isNotEmpty(qlrList.getQlrList())) {
            List<FwFcqlrDO> insertFwFcqlrDOList = new ArrayList<>(20);
            List<FwFcqlrDO> updateFwFcqlrDOList = new ArrayList<>(20);
            int i = 1;
            for (FwFcqlrDO fwFcqlrDO : qlrList.getQlrList()) {
                if (fwFcqlrDO != null) {
                    fwFcqlrDO.setQlrbh(String.valueOf(i));
                    fwFcqlrDO.setFwIndex(qlrList.getFwIndex());
                    if (StringUtils.isNotBlank(fwFcqlrDO.getFwFcqlrIndex())) {
                        updateFwFcqlrDOList.add(fwFcqlrDO);
                    } else {
                        insertFwFcqlrDOList.add(fwFcqlrDO);
                    }
                    i++;
                }
            }
            if (CollectionUtils.isNotEmpty(insertFwFcqlrDOList)) {
                fwFcqlrFeignService.batchInsert(insertFwFcqlrDOList);
            }
            if (CollectionUtils.isNotEmpty(updateFwFcqlrDOList)) {
                fwFcqlrFeignService.batchUpdateFwFcQlr(updateFwFcqlrDOList, false);
            }
            return returnHtmlResult(true, "成功");
        }
        return returnHtmlResult(false, "必要数据为空");
    }

    /**
     * 根据权利人主键删除权利人
     *
     * @param fwFcqlrIndex
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteqlr")
    public Map deleteQlr(@NotBlank(message = "主键不能为空") String fwFcqlrIndex) {
        fwFcqlrFeignService.deleteFcqlrByFwFcqlrIndex(fwFcqlrIndex,"");
        return returnHtmlResult(true, "成功");
    }


}
