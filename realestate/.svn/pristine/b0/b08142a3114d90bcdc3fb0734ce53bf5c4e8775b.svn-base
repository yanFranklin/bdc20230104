package cn.gtmap.realestate.accept.core.service.impl.jyxx;

import cn.gtmap.realestate.accept.core.service.TsBdcFcjyYwxxService;
import cn.gtmap.realestate.common.core.domain.BdcCzrzDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.accept.TsFcjyYwxxDTO;
import cn.gtmap.realestate.common.core.enums.BdcCzrzLxEnum;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcCzrzFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
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

/**
 * @description: 推送删除业务信息
 * @author: jinfei
 * @email: <a href="mailto:liaoxiang@gtmap.cn">jinfei</a>
 * @date: 2022/9/15 11:09
 **/
@Service
public class TsBdcFcjyDelYwxxServiceImpl implements TsBdcFcjyYwxxService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TsBdcFcjyDelYwxxServiceImpl.class);

    public static final String BEANNAME = "fcjyDelYwxx";

    @Autowired
    BdcXmFeignService bdcXmFeignService;

    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;

    @Autowired
    UserManagerUtils userManagerUtils;

    @Autowired
    BdcCzrzFeignService bdcCzrzFeignService;

    @Override
    public Map<String, Object> getFcjyTsYwxxRequestParam(TsFcjyYwxxDTO tsFcjyYwxxDTO) {
        if (Objects.isNull(tsFcjyYwxxDTO)) {
            return null;
        }
        Map<String, Object> paramMap = new HashMap<>(16);
        String gzlslid = tsFcjyYwxxDTO.getGzlslid();
        String reason = tsFcjyYwxxDTO.getReason();
        List<BdcXmDO> bdcXmDOList = null;
        // 获取项目信息
        if (StringUtils.isNotBlank(gzlslid)) {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setGzlslid(gzlslid);
            bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        }
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            // 单个项目业务号传xmid,多个项目业务号传slbh
            paramMap.put("ywh", bdcXmDOList.get(0).getXmid());
            if (bdcXmDOList.size() > 1) {
                paramMap.put("ywh", bdcXmDOList.get(0).getSlbh());
            }
            paramMap.put("xzqdm", bdcXmDOList.get(0).getQxdm());
            paramMap.put("delreason", reason);
            paramMap.put("deltime", DateUtils.formateYmdhms(new Date()));
            paramMap.put("fwbm", bdcXmDOList.get(0).getBdcdywybh());
        }
        return paramMap;
    }

    @Override
    public boolean tsFcjyYwxx(Map<String, Object> paramMap) {
        Object response = null;
        JSONObject responseObj = null;
        LOGGER.info("推送淮安房产交易还原业务信息接口入参beanName：{},paramMap：{}", BEANNAME, paramMap);
        // 推送三次如果都失败，打印错误信息或抛出异常
        for (int i = 0; i < 3; i++) {
            try {
                response = exchangeInterfaceFeignService.requestInterface(BEANNAME, paramMap);
                if (Objects.isNull(response)) {
                    LOGGER.error("淮安房产交易还原业务信息接口返回为空");
                    continue;
                }
                responseObj = JSONObject.parseObject(JSON.toJSONString(response), JSONObject.class);
                LOGGER.info("淮安房产交易还原业务信息接口返回值：{}", responseObj);
                // 推送成功直接返回
                if ("true".equals(responseObj.getString("result"))) {
                    return true;
                }
            } catch (Exception e) {
                LOGGER.error("淮安房产交易还原业务信息接口推送异常", e);
            }
        }
        return false;
    }

    @Override
    public void addTssbLog(BdcCzrzDO bdcCzrzDO) {
        bdcCzrzDO.setCzmc(BEANNAME);
        bdcCzrzDO.setCzyy("淮安推送房产交易删除业务信息到住建局");
        bdcCzrzDO.setCzr(userManagerUtils.getCurrentUserName());
        bdcCzrzDO.setCzsj(new Date());
        bdcCzrzDO.setCzlx(BdcCzrzLxEnum.CZRZ_CZLX_TSYWXX.key());
        bdcCzrzFeignService.addBdcCzrz(bdcCzrzDO);
    }
}
