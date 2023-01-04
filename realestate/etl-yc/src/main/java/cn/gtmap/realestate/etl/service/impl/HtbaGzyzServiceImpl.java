package cn.gtmap.realestate.etl.service.impl;

import cn.gtmap.gtc.feign.common.exception.GtFeignException;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzYzTsxxDTO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzZgzTsxxDTO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzZhgzTsxxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzYzQO;
import cn.gtmap.realestate.common.core.service.feign.engine.BdcGzZhGzFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.etl.service.HtbaGzyzService;
import cn.gtmap.realestate.etl.util.Constants;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @program: realestate
 * @description: 合同备案规则验证实现类
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-12-17 11:22
 **/
@Service
public class HtbaGzyzServiceImpl implements HtbaGzyzService {
    private static final Logger LOGGER = LoggerFactory.getLogger(HtbaGzyzServiceImpl.class);

    @Autowired
    BdcGzZhGzFeignService bdcGzZhGzFeignService;

    /**
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 验证是否存在限制权利
     * @date : 2020/12/17 11:22
     */
    @Override
    public List<Map<String, Object>> queryGzyzResult(BdcGzYzQO bdcGzYzQO) {
        List<Map<String, Object>> resultList = Lists.newArrayList();
        List<BdcGzYzTsxxDTO> listBdcGzYzTsxx = new ArrayList<>();
        try {
            listBdcGzYzTsxx = bdcGzZhGzFeignService.listBdcGzYzTsxxOfAnyParam(bdcGzYzQO);
        } catch (Exception e) {
            handlException(e);
        }
        if (CollectionUtils.isNotEmpty(listBdcGzYzTsxx)) {
            for (BdcGzYzTsxxDTO bdcGzYzTsxxDTO : listBdcGzYzTsxx) {
                if (CollectionUtils.isNotEmpty(bdcGzYzTsxxDTO.getZgzTsxxDTOList())) {
                    resultList.addAll(handleTsxx(bdcGzYzTsxxDTO.getZgzTsxxDTOList(), bdcGzYzTsxxDTO));
                }
            }
        }
        return resultList;
    }

    public Object handlException(Exception e) {
        //获取规则的时候会抛出异常，当code为  时表示未配置验证项直接返回空集合
        GtFeignException gte = null;
        if (e.getCause() instanceof GtFeignException) {
            gte = (GtFeignException) e.getCause();
            if (gte != null) {
                String responseBody = gte.getMsgBody();
                Map bodyMap = JSONObject.parseObject(responseBody, Map.class);
                if (MapUtils.getInteger(bodyMap, "code") != null && StringUtils.isNotBlank(MapUtils.getString(bodyMap, "msg"))) {
                    Integer errorCode = MapUtils.getInteger(bodyMap, "code");
                    if (errorCode == 101) {
                        return Collections.emptyList();
                    } else {
                        LOGGER.error("规则验证异常{}", bodyMap.get("detail"));
                        throw new AppException("规则验证异常，请联系管理员" + bodyMap.get("detail"));
                    }
                }
            }
        } else {
            LOGGER.error("规则验证异常！", e);
            throw new AppException("规则验证异常，请联系管理员");
        }
        return null;
    }

    private List<Map<String, Object>> handleTsxx(List<BdcGzZgzTsxxDTO> bdcGzZgzTsxxDTOList, BdcGzYzTsxxDTO bdcGzYzTsxxDTO) {
        List<Map<String, Object>> resultList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(bdcGzZgzTsxxDTOList)) {
            for (BdcGzZgzTsxxDTO bdcGzZgzTsxxDTO : bdcGzZgzTsxxDTOList) {
                if (bdcGzZgzTsxxDTO != null && CollectionUtils.isNotEmpty(bdcGzZgzTsxxDTO.getTsxx())) {
                    Map<String, Object> returnMap = new HashMap<>();
                    List<String> tsxxList = bdcGzZgzTsxxDTO.getTsxx();
                    for (String tsxx : tsxxList) {
                        if (StringUtils.isNotBlank(tsxx)) {
                            returnMap.put("msg", tsxx);
                        }
                    }
                    if (bdcGzZgzTsxxDTO.getYxj() != null) {
                        if (bdcGzZgzTsxxDTO.getYxj() == 1) {
                            returnMap.put("yzlx", Constants.CONFIRM);
                        } else if (bdcGzZgzTsxxDTO.getYxj() == 2) {
                            returnMap.put("yzlx", Constants.CONFIRMANDCREATE);
                        } else if (bdcGzZgzTsxxDTO.getYxj() == 3) {
                            returnMap.put("yzlx", Constants.ALERT);
                        } else if (bdcGzZgzTsxxDTO.getYxj() == 4) {
                            returnMap.put("yzlx", Constants.ALERT_EXCLUDE);
                        }
                    } else {
                        returnMap.put("yzlx", Constants.ALERT);
                    }
                    if (bdcGzZgzTsxxDTO.getSjljg() != null) {
                        returnMap.put("xzxx", getXzxxBySjljg(bdcGzZgzTsxxDTO.getSjljg()));
                        returnMap.put("sjljg", bdcGzZgzTsxxDTO.getSjljg());
                        returnMap.put("xmid", bdcGzZgzTsxxDTO.getSjljg().get("xmid"));
                    }
                    if (bdcGzZgzTsxxDTO.getGzid() != null) {
                        returnMap.put("gzid", bdcGzZgzTsxxDTO.getGzid());
                    }
                    if (bdcGzZgzTsxxDTO.getGzmc() != null) {
                        returnMap.put("gzmc", bdcGzZgzTsxxDTO.getGzmc());
                    }
                    if (bdcGzYzTsxxDTO != null && MapUtils.isNotEmpty(bdcGzYzTsxxDTO.getParamMap())) {
                        returnMap.putAll(bdcGzYzTsxxDTO.getParamMap());
                    }
                    resultList.add(returnMap);
                }
            }
        }
        return resultList;
    }

    /**
     * @param sjljg 数据流
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据数据流执行结果获取限制信息
     */
    private <T> Object getXzxxBySjljg(Map<String, T> sjljg) {
        for (Map.Entry<String, T> entry : sjljg.entrySet()) {
            Object obj = entry.getValue();
            // 规则数据流返回值中存在验证的返回结果RULERESULT，需要排除掉
            if (obj != null && !StringUtils.equals(entry.getKey(), "RULERESULT")) {
                if (obj instanceof Collection && CollectionUtils.isEmpty((Collection) obj)) {
                    continue;
                }
                return obj;
            }
        }
        return null;
    }

    /**
     * 对数据流执行结果进行判断，获取对应的提示信息
     *
     * @param checkResult 数据流执行结果集合
     * @return { List } 子规则提示信息
     */
    public <T> List<String> getTsxxList(Map<String, T> checkResult) {
        if (null == checkResult) {
            return null;
        }
        List<String> tsxxList = new ArrayList();
        // ---------------------
        List<Map<String, Object>> resList = (List<Map<String, Object>>) checkResult.get("res");
        if (resList != null && Integer.parseInt(resList.get(0).get("NUM").toString()) != 0) {
            String bdcdyh = (String) ((List<Map<String, Object>>) checkResult.get("res")).get(0).get("BDCDYH");
            tsxxList.add("该不动产单元" + bdcdyh + "已备案");
        }
        // ---------------------
        return tsxxList;
    }
}
