package cn.gtmap.realestate.accept.core.service.impl.jyxx;

import cn.gtmap.realestate.accept.core.service.BdcSlJyxxService;
import cn.gtmap.realestate.accept.core.service.TsBdcFcjyYwxxService;
import cn.gtmap.realestate.common.core.domain.BdcCzrzDO;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJyxxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcDjxxQlrDTO;
import cn.gtmap.realestate.common.core.dto.accept.TsFcjyYwxxDTO;
import cn.gtmap.realestate.common.core.enums.BdcCzrzLxEnum;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlJyxxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcCzrzFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @description: 推送抵押类登记登簿信息【蚌埠】
 * @author: jinfei
 * @email: <a href="mailto:jinfei@gtmap.cn">jinfei</a>
 * @date: 2022/12/26 11:09
 **/
@Service
public class TsBdcFcjyDyDjxxServiceImpl implements TsBdcFcjyYwxxService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TsBdcFcjyDyDjxxServiceImpl.class);

    public static final String BEANNAME = "fcjyZyDyDjxx";

    @Autowired
    BdcXmFeignService bdcXmFeignService;

    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;

    @Autowired
    UserManagerUtils userManagerUtils;

    @Autowired
    BdcCzrzFeignService bdcCzrzFeignService;

    @Autowired
    BdcSlJyxxService bdcSlJyxxService;

    @Autowired
    BdcQlrFeignService bdcQlrFeignService;

    @Override
    public Map<String, Object> getFcjyTsYwxxRequestParam(TsFcjyYwxxDTO tsFcjyYwxxDTO) {
        if (Objects.isNull(tsFcjyYwxxDTO)) {
            return null;
        }
        Map<String, Object> paramMap = new HashMap<>(16);
        String gzlslid = tsFcjyYwxxDTO.getGzlslid();
        String ywlx = tsFcjyYwxxDTO.getYwlx();
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            paramMap.put("bdcywh", bdcXmDOList.get(0).getSlbh());
            paramMap.put("ywsx", bdcXmDOList.get(0).getGzldymc());
            paramMap.put("type", ywlx);

            // 抵押类
            List<BdcXmDO> dyXmDOList = bdcXmDOList.stream().filter(bdcXmDO -> Arrays.asList(CommonConstantUtils.QLLX_DYAQ_DM).contains(bdcXmDO.getQllx())).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(dyXmDOList)) {
                BdcQlrQO bdcQlrQO = new BdcQlrQO();
                bdcQlrQO.setXmid(dyXmDOList.get(0).getXmid());
                bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_YWR);
                List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
                if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                    List<BdcDjxxQlrDTO> bdcDjxxQlrDTOList = new ArrayList<>(bdcQlrDOList.size());
                    for (BdcQlrDO bdcQlrDO : bdcQlrDOList) {
                        BdcDjxxQlrDTO bdcDjxxQlrDTO = new BdcDjxxQlrDTO();
                        bdcDjxxQlrDTO.setName(bdcQlrDO.getQlrmc());
                        bdcDjxxQlrDTO.setEntId(bdcQlrDO.getZjh());
                        bdcDjxxQlrDTOList.add(bdcDjxxQlrDTO);
                    }
                    paramMap.put("dyrs", bdcDjxxQlrDTOList);
                }
                BdcSlJyxxQO bdcSlJyxxQO = new BdcSlJyxxQO();
                bdcSlJyxxQO.setXmid(dyXmDOList.get(0).getXmid());
                List<BdcSlJyxxDO> bdcSlJyxxDOList = bdcSlJyxxService.listBdcSlJyxx(bdcSlJyxxQO);
                if (CollectionUtils.isNotEmpty(bdcSlJyxxDOList)) {
                    paramMap.put("htbh", bdcSlJyxxDOList.get(0).getHtbh());
                }
            }
        }
        return paramMap;
    }

    @Override
    public boolean tsFcjyYwxx(Map<String, Object> paramMap) {
        Object response = null;
        JSONObject responseObj = null;
        LOGGER.info("推送抵押类登记登簿信息接口入参beanName：{},paramMap：{}", BEANNAME, paramMap);
        // 推送三次如果都失败，打印错误信息或抛出异常
        try {
            response = exchangeInterfaceFeignService.requestInterface(BEANNAME, paramMap);
            if (Objects.isNull(response)) {
                LOGGER.error("推送抵押类登记登簿信息接口返回为空");
            }
            responseObj = JSONObject.parseObject(JSON.toJSONString(response), JSONObject.class);
            LOGGER.info("推送抵押类登记登簿信息接口返回值：{}", responseObj);
            // 推送成功直接返回
            if ("200".equals(responseObj.getString("status"))) {
                return true;
            }
        } catch (Exception e) {
            LOGGER.error("推送抵押类登记登簿信息接口推送异常", e);
        }
        return false;
    }

    @Override
    public void addTssbLog(BdcCzrzDO bdcCzrzDO) {
        bdcCzrzDO.setCzmc(BEANNAME);
        bdcCzrzDO.setCzyy("推送抵押类登记登簿信息到住建局");
        bdcCzrzDO.setCzr(userManagerUtils.getCurrentUserName());
        bdcCzrzDO.setCzsj(new Date());
        bdcCzrzDO.setCzlx(BdcCzrzLxEnum.CZRZ_CZLX_TSYWXX.key());
        bdcCzrzFeignService.addBdcCzrz(bdcCzrzDO);
    }
}
