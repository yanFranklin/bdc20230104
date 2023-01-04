package cn.gtmap.realestate.building.service.impl;

import cn.gtmap.realestate.building.service.lpb.BdcPrintDjdcbService;
import cn.gtmap.realestate.common.core.domain.BdcDysjPzDO;
import cn.gtmap.realestate.common.core.domain.BdcDysjZbPzDO;
import cn.gtmap.realestate.common.core.dto.register.BdcDysjDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.BdcDysjPzService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcDypzFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcPrintFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Multimap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: realestate
 * @description: 地籍调查表打印服务
 * @author: <a href="mailto:duwei@gtmap.cn">duwei</a>
 * @create: 2022-10-13
 **/

@Service
public class BdcPrintDjdcbServiceImpl implements BdcPrintDjdcbService {

    protected final Logger LOGGER = LoggerFactory.getLogger(BdcPrintDjdcbServiceImpl.class);
    @Autowired
    BdcDypzFeignService bdcDypzFeignService;
    @Autowired
    BdcPrintFeignService bdcPrintFeignService;
    @Autowired
    BdcDysjPzService bdcDysjPzService;
    @Value("${url.register-ui:}")
    private String registerUiUrl;

    /**
     * @param bdcdyh
     * @return xml
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description 地籍调查表打印服务
     */
    @Override
    public String printDjdcb(String dylx, String qjgldm, String bdcdyh) {
        if (StringUtils.isAnyBlank(dylx, bdcdyh)) {
            LOGGER.error("当前打印缺失参数dyxl={}，bcdyh={}", dylx, bdcdyh);
            return null;
        }
        Map paramMap = new HashMap();
        paramMap.put("qjgldm", qjgldm);
        paramMap.put("bdcdyh", bdcdyh);
        paramMap.put("zdtUrl", StringUtils.replace(registerUiUrl + CommonConstantUtils.ZDT_URL + "?qjgldm=" + qjgldm, "{bdcdyh}", bdcdyh));
        if (StringUtils.isNotBlank(qjgldm)) {
            paramMap.put("zdtUrl2", StringUtils.replace(registerUiUrl + CommonConstantUtils.ZDT_URL2, "{bdcdyh}", bdcdyh).replace("{qjgldm}", qjgldm));
        } else {
            paramMap.put("zdtUrl2", paramMap.get("zdtUrl"));
        }
        paramMap.put("hstUrl", StringUtils.replace(registerUiUrl + CommonConstantUtils.HST_URL + "?qjgldm=" + qjgldm, "{bdcdyh}", bdcdyh));
        if (StringUtils.isNotBlank(qjgldm)) {
            paramMap.put("hstUrl2", StringUtils.replace(registerUiUrl + CommonConstantUtils.HST_URL2, "{bdcdyh}", bdcdyh).replace("{qjgldm}", qjgldm));
        } else {
            paramMap.put("hstUrl2", paramMap.get("hstUrl"));
        }
        LOGGER.warn("当前单元号={}打印类型为{}打印入参有={}", bdcdyh, dylx, JSON.toJSONString(paramMap));
        //获取主表打印配置信息
        List<BdcDysjDTO> bdcDysjDTOList = new ArrayList<>();
        BdcDysjPzDO bdcDysjPzDO = new BdcDysjPzDO();
        bdcDysjPzDO.setDylx(dylx);
        List<BdcDysjPzDO> bdcDysjPzDOList = bdcDypzFeignService.listBdcDysjPz(bdcDysjPzDO);
        if (CollectionUtils.isEmpty(bdcDysjPzDOList)) {
            throw new AppException("主表打印配置信息为空");
        }

        //获取子表打印配置信息
        BdcDysjZbPzDO bdcDysjZbPzDO = new BdcDysjZbPzDO();
        bdcDysjZbPzDO.setDylx(dylx);
        List<BdcDysjZbPzDO> bdcDysjZbPzDOList = bdcDypzFeignService.listBdcDysjZbPz(bdcDysjZbPzDO);

        //获取主表查询数据
        Map datas = bdcDysjPzService.queryPrintDatasList(paramMap, "buildingConfigMapper", bdcDysjPzDOList);

        //获取子表查询数据
        Multimap detail = bdcDysjPzService.queryPrintDetailList(paramMap, "buildingConfigMapper", bdcDysjZbPzDOList);

        BdcDysjDTO bdcDysjDTO = new BdcDysjDTO();
        bdcDysjDTO.setDylx(dylx);
        bdcDysjDTO.setDyzd(bdcDysjPzDOList.get(0).getDyzd());
        bdcDysjDTO.setDysj(JSONObject.toJSONString(datas));
        bdcDysjDTO.setDyzbsj(JSONObject.toJSONString(detail));
        bdcDysjDTOList.add(bdcDysjDTO);
        String xml = bdcPrintFeignService.printDatas(bdcDysjDTOList);
        LOGGER.warn("当前不动产单元号{}打印类型{}生成的xml数据源{}", bdcdyh, dylx, xml);
        return xml;
    }
}
