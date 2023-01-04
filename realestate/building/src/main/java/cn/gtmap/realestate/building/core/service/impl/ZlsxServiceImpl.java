package cn.gtmap.realestate.building.core.service.impl;

import cn.gtmap.realestate.building.core.bo.ZlsxConfigBO;
import cn.gtmap.realestate.building.core.mapper.FwKMapper;
import cn.gtmap.realestate.building.core.mapper.ZlsxMapper;
import cn.gtmap.realestate.building.core.service.FwHsService;
import cn.gtmap.realestate.building.core.service.FwLjzService;
import cn.gtmap.realestate.building.core.service.ZdService;
import cn.gtmap.realestate.building.core.service.ZlsxService;
import cn.gtmap.realestate.building.thread.ZlsxThread;
import cn.gtmap.realestate.building.util.ManageConfigUtil;
import cn.gtmap.realestate.common.core.cache.BdcZdCache;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.FwKDO;
import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;
import cn.gtmap.realestate.common.core.domain.building.SDmDwxxDO;
import cn.gtmap.realestate.common.core.dto.building.ZlsxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.support.thread.ThreadEngine;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/27
 * @description 坐落刷新相关实现
 */
@Service
public class ZlsxServiceImpl implements ZlsxService {
    @Autowired
    private FwLjzService fwLjzService;
    @Autowired
    private FwHsService fwHsService;
    @Autowired
    private ZlsxMapper zlsxMapper;
    @Autowired
    private FwKMapper fwKMapper;
    @Autowired
    private ZlsxService zlsxService;

    @Autowired
    private ThreadEngine threadEngine;

    @Autowired
    private ZdService zdService;

    @Value("${fwkTableName}")
    private String fwkTableName;

    @Autowired
    private BdcZdCache bdcZdCache;

    /**
     * @param zlsxDTO
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 房屋坐落刷新
     */
    @Override
    public void zlsxByRule(ZlsxDTO zlsxDTO) {
        if (zlsxDTO != null) {
            List<FwHsDO> fwHsDOList;
            if (StringUtils.isBlank(zlsxDTO.getFwDcbIndex())) {
                //刷新整个宗地下的房屋
                fwHsDOList = getFwHsListByZd(zlsxDTO);
            } else {
                //刷新整个逻辑幢下的房屋
                fwHsDOList = getFwHsListByLjz(zlsxDTO);
            }
            if (CollectionUtils.isNotEmpty(fwHsDOList)) {
                // 首先获取 户室以上级别的 拼接规则 对应的值
                String zlExceptHs = appendZlExceptHs(zlsxDTO);

                if (fwHsDOList.size() > 50) {
                    List<ZlsxThread> zlsxThreadList = new ArrayList<>();
                    for (FwHsDO fwHsDO : fwHsDOList) {
                        ZlsxThread zlsxThread = new ZlsxThread(zlsxService, zlsxDTO, fwHsDO, zlExceptHs);
                        zlsxThreadList.add(zlsxThread);
                    }
                    threadEngine.excuteThread(zlsxThreadList, false);
                } else {
                    for (FwHsDO fwHsDO : fwHsDOList) {
                        if (fwHsDO != null) {
                            fwhsZlsxByRule(fwHsDO, zlsxDTO, zlExceptHs);
                        }
                    }
                }

            }
        }
    }

    /**
     * @param zlsxDTO
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据配置做 做坐落刷新
     */
    @Override
    public void zlsxByConfig(ZlsxDTO zlsxDTO) {
        ZlsxConfigBO config = ManageConfigUtil.getZlsxConfig();
        if (config != null && StringUtils.isNotBlank(config.getSxgz())) {
            zlsxDTO.setSxgz(config.getSxgz());
            zlsxDTO.setNullOnly(config.isNullOnly());
            zlsxByRule(zlsxDTO);
        } else {
            throw new AppException("获取坐落刷新配置失败");
        }
    }

    /**
     * @param zlsxDTO
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据dto查询有效的不动产单元号
     */
    @Override
    public List<String> listValidBdcdyhByDTO(ZlsxDTO zlsxDTO) {
        List<String> bdcdyhList = new ArrayList<>();
        if(zlsxDTO!=null){
            if(StringUtils.isNotBlank(zlsxDTO.getFwDcbIndex())){
                bdcdyhList=fwLjzService.listValidBdcdyhByFwDcbIndex(zlsxDTO.getFwDcbIndex());
            }else if(StringUtils.isNotBlank(zlsxDTO.getDjh())){
                bdcdyhList=zdService.listValidBdcdyhByDjh(zlsxDTO.getDjh());
            }
        }
        return bdcdyhList;
    }


    /**
     * @param zlsxDTO
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 房屋坐落刷新（刷新整个宗地下的房屋）
     */
    private List<FwHsDO> getFwHsListByZd(ZlsxDTO zlsxDTO) {
        List<FwHsDO> fwHsDOList = new ArrayList<>();
        if (StringUtils.isNotBlank(zlsxDTO.getDjh())) {
            List<FwLjzDO> fwLjzDOList = fwLjzService.listLjzByDjhAndZrzh(zlsxDTO.getDjh(), "");
            if (CollectionUtils.isNotEmpty(fwLjzDOList)) {
                for (FwLjzDO fwLjzDO : fwLjzDOList) {
                    zlsxDTO.setFwDcbIndex(fwLjzDO.getFwDcbIndex());
                    List<FwHsDO> fwHsDOs = getFwHsListByLjz(zlsxDTO);
                    if (CollectionUtils.isNotEmpty(fwHsDOs)) {
                        fwHsDOList.addAll(fwHsDOs);
                    }
                }
            }
        }
        return fwHsDOList;
    }

    /**
     * @param zlsxDTO
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 房屋坐落刷新（刷新整个逻辑幢下的房屋）
     */
    private List<FwHsDO> getFwHsListByLjz(ZlsxDTO zlsxDTO) {
        List<FwHsDO> fwHsDOList = new ArrayList<>();
        if (StringUtils.isNotBlank(zlsxDTO.getFwDcbIndex())) {
            //如果没选择是否刷新全部房屋，则只刷新为空的房屋
            if (zlsxDTO.isNullOnly()) {
                fwHsDOList = fwHsService.listFwHsZlNullByFwDcbIndex(zlsxDTO.getFwDcbIndex());
            } else {
                fwHsDOList = fwHsService.listFwHsListByFwDcbIndex(zlsxDTO.getFwDcbIndex());
            }
        }
        return fwHsDOList;
    }

    /**
     * @param fwHsDO
     * @param zlsxDTO
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 刷新户室坐落
     */
    @Override
    public void fwhsZlsxByRule(FwHsDO fwHsDO, ZlsxDTO zlsxDTO, String zlExceptHs) {
        //获取拼接的字段
        String newDz = appendZl(zlsxDTO, fwHsDO.getFwHsIndex(), zlExceptHs);
        if (StringUtils.isNotBlank(newDz)) {
            fwHsDO.setZl(newDz);
            fwHsService.updateFwHs(fwHsDO,false);
        }
    }

    /**
     * @param zlsxDTO
     * @return java.lang.String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 拼接地址（只有户室）
     */
    private String appendZl(ZlsxDTO zlsxDTO, String fwHsIndex, String zlExceptHs) {
        StringBuffer sb = new StringBuffer();
        //户室以上级别的 拼接规则 对应的值  先拼接上
        if (StringUtils.isNotBlank(zlExceptHs)) {
            sb.append(zlExceptHs);
        }
        List<Map<String, String>> ruleList = disposeAllRule(zlsxDTO.getSxgz());
        if (CollectionUtils.isNotEmpty(ruleList)) {
            for (Map<String, String> ruleMap : ruleList) {
                String value = "";
                String table = MapUtils.getString(ruleMap, "table");
                String column = MapUtils.getString(ruleMap, "column");
                if (StringUtils.equals("FW_HS", table)) {
                    if (StringUtils.isNotBlank(zlsxDTO.getFwDcbIndex())) {
                        //根据规则查询数据库中字段信息
                        value = queryRule(table, column, "fw_hs_index", fwHsIndex);
                    }
                }
                if (StringUtils.isNotBlank(value)) {
                    sb.append(value);
                }
            }
        }
        return sb.toString();
    }

    /**
     * @param zlsxDTO
     * @return java.lang.String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 拼接地址（除了户室）
     */
    private String appendZlExceptHs(ZlsxDTO zlsxDTO) {
        StringBuffer sb = new StringBuffer();
        List<Map<String, String>> ruleList = disposeAllRule(zlsxDTO.getSxgz());
        if (CollectionUtils.isNotEmpty(ruleList)) {
            for (Map<String, String> ruleMap : ruleList) {
                String value = "";
                if (StringUtils.isNotBlank(MapUtils.getString(ruleMap, "constants"))) {
                    value = MapUtils.getString(ruleMap, "constants");
                } else {
                    String table = MapUtils.getString(ruleMap, "table");
                    String column = MapUtils.getString(ruleMap, "column");
                    if (StringUtils.equals("S_DM_DWXX", table)) {
                        String subLen = MapUtils.getString(ruleMap, "sublen");
                        // 行政区 查询单位名称
                        value = getXzqMC(zlsxDTO.getDjh(), Integer.parseInt(subLen));
                    } else if (StringUtils.equals("FW_LJZ", table)) {
                        if (StringUtils.isNotBlank(zlsxDTO.getFwDcbIndex())) {
                            //根据规则查询数据库中字段信息
                            value = queryRule(table, column, "fw_dcb_index", zlsxDTO.getFwDcbIndex());
                        }
                    } else if (StringUtils.equals("ZD_DJDCB", table)) {
                        if (StringUtils.isNotBlank(zlsxDTO.getDjh())) {
                            //根据规则查询数据库中字段信息
                            value = queryRule(table, column, "djh", zlsxDTO.getDjh());
                        }
                    } else if (StringUtils.equals("FW_K", table)) {
                        //根据lszd和zrzh查询逻辑幢
                        if (StringUtils.isNotBlank(zlsxDTO.getDjh()) && StringUtils.isNotBlank(zlsxDTO.getFwDcbIndex())) {
                            FwLjzDO fwLjzDO = fwLjzService.queryLjzByFwDcbIndex(zlsxDTO.getFwDcbIndex());
                            if (fwLjzDO != null && StringUtils.isNotBlank(fwLjzDO.getZrzh())) {
                                Map map = new HashMap();
                                map.put("lszd", zlsxDTO.getDjh());
                                map.put("zrzh", fwLjzDO.getZrzh());
                                List<FwKDO> fwKDOList = fwKMapper.queryFwKList(map);
                                if (CollectionUtils.isNotEmpty(fwKDOList)) {
                                    //根据规则查询数据库中字段信息
                                    value = queryRule(fwkTableName, column, "Objectid", fwKDOList.get(0).getObjectid());
                                }
                            }
                        }
                    }
                }
                if (StringUtils.isNotBlank(value)) {
                    sb.append(value);
                }
            }
        }
        return sb.toString();
    }

    private String getXzqMC(String djh, int subLen) {
        if (StringUtils.isNotBlank(djh) && djh.length() == 19) {
            String queryParam = djh.substring(0, subLen);
            return bdcZdCache.getFeildValue("SDmDwxxDO", queryParam, "DWMC", "DWDM", SDmDwxxDO.class).toString();
        }
        return "";
    }


    /**
     * @param table
     * @param column
     * @param whereName
     * @param whereValue
     * @return java.lang.String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据规则查询数据库中字段信息
     */
    private String queryRule(String table, String column, String whereName, String whereValue) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNotBlank(table)) {
            map.put("table", table);
        }
        if (StringUtils.isNotBlank(column)) {
            map.put("column", column);
        }
        if (StringUtils.isNotBlank(whereName)) {
            map.put("whereName", whereName);
        }
        if (StringUtils.isNotBlank(whereValue)) {
            map.put("whereValue", whereValue);
        }
        return zlsxMapper.queryRule(map);
    }

    /**
     * @param allRule
     * @return java.util.List<java.lang.String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 处理规则（将大规则处理成一个个小规则）
     */
    private List<Map<String, String>> disposeAllRule(String allRule) {
        if (StringUtils.isNotBlank(allRule)) {
            List<Map<String, String>> ruleList = new LinkedList<>();
            String[] rules = allRule.split(",");
            for (int i = 0; i < rules.length; i++) {
                String rule = rules[i];
                //获取规则中的table和column
                Map<String, String> ruleMap = null;
                if (rule.startsWith("XZQ") && rule.contains("_")) {
                    // 如果是行政区
                    String[] xzqRuleArr = rule.split("_");
                    if (xzqRuleArr.length == 2 && NumberUtils.isNumber(xzqRuleArr[1])) {
                        ruleMap = new HashMap<>();
                        ruleMap.put("table", "S_DM_DWXX");
                        ruleMap.put("sublen", xzqRuleArr[1]);
                    }
                } else if (rule.indexOf('.') != -1) {
                    // 如果是普通表字段
                    ruleMap = disposeRule(rule);
                } else {
                    // 其他判断为常量
                    ruleMap = new HashMap<>();
                    ruleMap.put("constants", rule);
                }

                if (ruleMap != null) {
                    ruleList.add(ruleMap);
                }
            }
            return ruleList;
        }
        return new ArrayList<>(0);
    }

    /**
     * @param rule
     * @return java.util.List<java.lang.String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 获取规则中的table和column
     */
    private Map<String, String> disposeRule(String rule) {
        if (StringUtils.isNotBlank(rule)) {
            Map<String, String> ruleMap = new HashMap<>();
            String table = rule.substring(0, rule.indexOf('.'));
            String column = rule.substring(rule.indexOf('.') + 1);
            ruleMap.put("table", table);
            ruleMap.put("column", column);
            return ruleMap;
        }
        return new HashMap<>(0);
    }
}