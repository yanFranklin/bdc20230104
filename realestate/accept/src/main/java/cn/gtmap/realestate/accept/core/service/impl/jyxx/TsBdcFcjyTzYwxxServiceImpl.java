package cn.gtmap.realestate.accept.core.service.impl.jyxx;

import cn.gtmap.realestate.accept.core.service.TsBdcFcjyYwxxService;
import cn.gtmap.realestate.common.core.domain.BdcCzrzDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmLsgxDO;
import cn.gtmap.realestate.common.core.dto.accept.TsFcjyYwxxDTO;
import cn.gtmap.realestate.common.core.enums.BdcCzrzLxEnum;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcCzrzFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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
 * @description: 推送通知业务信息
 * @author: jinfei
 * @email: <a href="mailto:liaoxiang@gtmap.cn">jinfei</a>
 * @date: 2022/9/15 11:09
 **/
@Service
public class TsBdcFcjyTzYwxxServiceImpl implements TsBdcFcjyYwxxService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TsBdcFcjyTzYwxxServiceImpl.class);

    public static final String BEANNAME = "fcjyTsYwxx";

    @Autowired
    BdcXmFeignService bdcXmFeignService;

    @Autowired
    BdcZdFeignService bdcZdFeignService;

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
        String xmids = tsFcjyYwxxDTO.getXmids();
        String ywlx = tsFcjyYwxxDTO.getYwlx();
        List<BdcXmDO> bdcXmDOList = null;
        // 获取项目信息
        if (StringUtils.isNotBlank(xmids) && StringUtils.isBlank(gzlslid)) {
            List<String> xmidList = Arrays.asList(xmids.split(","));
            bdcXmDOList = bdcXmFeignService.listBdcXmByXmids(xmidList);
        } else if (StringUtils.isBlank(xmids) && StringUtils.isNotBlank(gzlslid)) {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setGzlslid(gzlslid);
            bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        }
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            JSONObject requestJsonObj = new JSONObject();
            JSONArray dataJsonArray = new JSONArray();
            int ywcount = 0;
            Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
            ywcount = bdcXmDOList.size();
            for (BdcXmDO bdcXmDO : bdcXmDOList) {
                JSONObject paramJsonObj = new JSONObject();
                // 业务类型
                paramJsonObj.put("ywlx", ywlx);
                // 权利类型
                paramJsonObj.put("sqlx", bdcXmDO.getQllx());
                // 权利类型名称
                String sqlxname = StringToolUtils.convertBeanPropertyValueOfZd(bdcXmDO.getQllx(), zdMap.get("qllx"));
                paramJsonObj.put("sqlxname", sqlxname);
                // 行政区属代码
                paramJsonObj.put("xzqdm", bdcXmDO.getQxdm());
                // 登记小类
                paramJsonObj.put("djzx", bdcXmDO.getDjxl());
                // 登记小类名称
                String djzxname = StringToolUtils.convertBeanPropertyValueOfZdByString(bdcXmDO.getDjxl(), zdMap.get("djxl"));
                paramJsonObj.put("djzxname", djzxname);
                // 商品房首次登记，ywh传受理编号，其他流程ywh传xmid
                if("1000401".equals(bdcXmDO.getDjxl())) {
                    paramJsonObj.put("ywh", bdcXmDO.getSlbh());
                } else {
                    paramJsonObj.put("ywh", bdcXmDO.getXmid());
                }
                // 非首次登记yywh和ybdcqzh必填,原业务号，yxmid以,间隔拼接
                String yywh = "";
                // 获取原项目信息
                List<BdcXmDO> yxmList = bdcXmFeignService.listYxmByGzlslid(new BdcXmLsgxDO(), gzlslid, null);
                if (CollectionUtils.isNotEmpty(yxmList)) {
                    List<String> yxmidList = yxmList.stream().map(BdcXmDO::getXmid).collect(Collectors.toList());
                    yywh = StringUtils.join(yxmidList.toArray(), ",");
                }
                paramJsonObj.put("yywh", yywh);
                paramJsonObj.put("ybdcqzh", bdcXmDO.getYcqzh());
                paramJsonObj.put("ywcount", String.valueOf(ywcount));
                dataJsonArray.add(paramJsonObj);
            }
            requestJsonObj.put("tscount", String.valueOf(ywcount));
            requestJsonObj.put("data", dataJsonArray);

            paramMap.put("Jsonstr", requestJsonObj.toJSONString());
        }
        return paramMap;

    }

    @Override
    public boolean tsFcjyYwxx(Map<String, Object> paramMap) {
        Object response = null;
        JSONObject responseObj = null;
        LOGGER.info("推送淮安房产交易通知业务信息接口入参beanName：{},paramMap：{}", BEANNAME, paramMap);
        // 推送三次，如果都失败，记录失败日志
        for (int i = 0; i < 3; i++) {
            try {
                response = exchangeInterfaceFeignService.requestInterface(BEANNAME, paramMap);
                if (Objects.isNull(response)) {
                    LOGGER.error("淮安房产交易通知业务信息接口返回为空");
                    continue;
                }
                responseObj = JSONObject.parseObject(JSON.toJSONString(response), JSONObject.class);
                boolean flag = true;
                // 推送成功直接返回
                JSONArray resData = responseObj.getJSONArray("data");
                for (int j = 0; j < resData.size(); j++) {
                    String result = resData.getJSONObject(j).getString("result");
                    if("false".equals(result)) {
                        flag = false;
                        break;
                    }
                }
                LOGGER.info("淮安房产交易通知业务信息接口返回值：{}", responseObj);
                if (flag) {
                    return flag;
                }
            } catch (Exception e) {
                LOGGER.error("淮安房产交易通知业务信息接口推送异常", e);
            }
        }
        return false;
    }

    @Override
    public void addTssbLog(BdcCzrzDO bdcCzrzDO) {
        bdcCzrzDO.setCzmc(BEANNAME);
        bdcCzrzDO.setCzyy("淮安推送房产交易通知业务信息到住建局");
        bdcCzrzDO.setCzr(userManagerUtils.getCurrentUserName());
        bdcCzrzDO.setCzsj(new Date());
        bdcCzrzDO.setCzlx(BdcCzrzLxEnum.CZRZ_CZLX_TSYWXX.key());
        bdcCzrzFeignService.addBdcCzrz(bdcCzrzDO);
    }
}
