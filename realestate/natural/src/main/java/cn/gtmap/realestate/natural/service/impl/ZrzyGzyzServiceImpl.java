package cn.gtmap.realestate.natural.service.impl;

import cn.gtmap.gtc.feign.common.exception.GtFeignException;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzYzTsxxDTO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzYzsjDTO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzZgzTsxxDTO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzZhgzTsxxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzYzQO;
import cn.gtmap.realestate.common.core.service.feign.building.FwHsFeignService;
import cn.gtmap.realestate.common.core.service.feign.engine.BdcGzZhGzFeignService;
import cn.gtmap.realestate.natural.service.ZrzyGzyzService;
import cn.gtmap.realestate.natural.util.Constants;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/7/22
 * @description 规则验证
 */
@Service
public class ZrzyGzyzServiceImpl implements ZrzyGzyzService {

    @Autowired
    BdcGzZhGzFeignService bdcGzZhGzFeignService;
    @Autowired
    FwHsFeignService fwHsFeignService;


    private static final Logger LOGGER = LoggerFactory.getLogger(ZrzyGzyzServiceImpl.class);


    @Override
    public List<Map<String, Object>> yzBdcgz(BdcGzYzQO bdcGzYzQO) {
        return Collections.emptyList();
    }

    /**
     * @param bdcGzYzQO 规则验证查询实体(支持任意参数)
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 除流程验证外其他验证, 如匹配验证, 页面按钮操作验证等
     * @date : 2020/7/10 14:15
     */
    @Override
    public List<Map<String, Object>> qtyz(BdcGzYzQO bdcGzYzQO) {
        List<Map<String, Object>> resultList = Lists.newArrayList();
        BdcGzZhgzTsxxDTO bdcGzZhgzTsxxDTO = null;
        try {
            bdcGzZhgzTsxxDTO = bdcGzZhGzFeignService.getZhgzYzTsxx(bdcGzYzQO);
        } catch (Exception e) {
            handlException(e);
        }
        if (bdcGzZhgzTsxxDTO != null && CollectionUtils.isNotEmpty(bdcGzZhgzTsxxDTO.getZgzTsxxDTOList())) {
            resultList = handleTsxx(bdcGzZhgzTsxxDTO.getZgzTsxxDTOList(), null);
        }
        return resultList;
    }

    public List<Map<String, Object>> handelResult(List<Map<String, Object>> resultList) {
        List<Map<String, Object>> results = new ArrayList<>(resultList.size());
        if (CollectionUtils.isNotEmpty(resultList)) {
            //取出优先级为3 且存在ZDDYH 标志的数据
            List<Map<String, Object>> duplicateResult = new ArrayList<>(resultList.size());
            List<Map<String, Object>> normalResult = new ArrayList<>(resultList.size());
            Set<String> tsxx = new HashSet<>(resultList.size());
            for (Map<String, Object> result : resultList) {
                String yzlx = result.get("yzlx").toString();
                String msg = result.get("msg").toString();
                if (StringUtils.equals(Constants.ALERT, yzlx) && StringUtils.endsWith(msg, "ZDDYH")) {
                    if (!tsxx.contains(msg)) {
                        tsxx.add(msg);
                        result.put("msg", msg.replace("ZDDYH", ""));
                        duplicateResult.add(result);
                    }
                } else {
                    normalResult.add(result);
                }
            }
            results.addAll(normalResult);
            results.addAll(duplicateResult);
        }
        return results;
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

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 处理规则验证信息
     */
    private List<Map<String, Object>> handleTsxx(List<BdcGzZgzTsxxDTO> bdcGzZgzTsxxDTOList,
                                                 BdcGzYzTsxxDTO bdcGzYzTsxxDTO) {
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
}
