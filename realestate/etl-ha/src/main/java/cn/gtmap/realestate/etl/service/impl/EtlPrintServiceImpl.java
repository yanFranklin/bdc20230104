package cn.gtmap.realestate.etl.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcDysjPzDO;
import cn.gtmap.realestate.common.core.domain.BdcDysjZbPzDO;
import cn.gtmap.realestate.common.core.dto.register.BdcDysjDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.BdcDysjPzService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcDypzFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcPrintFeignService;
import cn.gtmap.realestate.etl.core.mapper.hlw.HlwConfigMapper;
import cn.gtmap.realestate.etl.service.EtlPrintService;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Multimap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/6/15
 * @description etl 打印服务
 */
@Service
public class EtlPrintServiceImpl implements EtlPrintService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EtlPrintServiceImpl.class);

    @Autowired
    BdcDypzFeignService bdcDypzFeignService;

    @Autowired
    BdcPrintFeignService bdcPrintFeignService;

    @Autowired
    BdcDysjPzService bdcDysjPzService;

    @Autowired
    HlwConfigMapper hlwConfigMapper;

    public String generateXmlData(Map configParam, String dylx,String configBeanName){
        if(StringUtils.isBlank(dylx) ||StringUtils.isBlank(configBeanName)){
            LOGGER.error("缺失打印参数,dylx:{},configBeanName:{}",dylx,configBeanName);
            throw new MissingArgumentException("缺失打印参数,打印类型"+dylx +"configBeanName"+configBeanName);
        }
        List<BdcDysjDTO> bdcDysjDTOList = new ArrayList<>();
        //主表
        BdcDysjPzDO bdcDysjPzDO = new BdcDysjPzDO();
        bdcDysjPzDO.setDylx(dylx);
        List<BdcDysjPzDO> bdcDysjPzDOList = bdcDypzFeignService.listBdcDysjPz(bdcDysjPzDO);
        Map datas = bdcDysjPzService.queryPrintDatasList(configParam, configBeanName, bdcDysjPzDOList);
        //子表
        BdcDysjZbPzDO bdcDysjZbPzDO = new BdcDysjZbPzDO();
        bdcDysjZbPzDO.setDylx(dylx);
        List<BdcDysjZbPzDO> bdcDysjZbPzDOList = bdcDypzFeignService.listBdcDysjZbPz(bdcDysjZbPzDO);
        Multimap detail = bdcDysjPzService.queryPrintDetailList(configParam, configBeanName, bdcDysjZbPzDOList);
        //组织打印数据
        BdcDysjDTO bdcDysjDTO = new BdcDysjDTO();
        bdcDysjDTO.setDylx(dylx);
        bdcDysjDTO.setDyzd(bdcDysjPzDOList.get(0).getDyzd());
        bdcDysjDTO.setDysj(JSONObject.toJSONString(datas));
        bdcDysjDTO.setDyzbsj(JSONObject.toJSONString(detail));
        bdcDysjDTOList.add(bdcDysjDTO);
        String xml = bdcPrintFeignService.printDatas(bdcDysjDTOList);
        LOGGER.info("打印类型{},生成的数据源xml{}", dylx, xml);
        return xml;

    }
}
