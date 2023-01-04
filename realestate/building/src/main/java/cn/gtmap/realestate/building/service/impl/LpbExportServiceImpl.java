package cn.gtmap.realestate.building.service.impl;

import cn.gtmap.realestate.building.core.service.FwFcqlrService;
import cn.gtmap.realestate.building.core.service.FwHsService;
import cn.gtmap.realestate.building.core.service.FwLjzService;
import cn.gtmap.realestate.building.core.service.FwYcHsService;
import cn.gtmap.realestate.building.service.LpbExportService;
import cn.gtmap.realestate.common.core.domain.building.FwFcqlrDO;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;
import cn.gtmap.realestate.common.core.domain.building.FwYchsDO;
import cn.gtmap.realestate.common.util.EntityZdConvertUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/12/26
 * @description 导出楼盘表相关信息
 */
@Service
public class LpbExportServiceImpl implements LpbExportService {
    @Autowired
    private FwHsService fwHsService;
    @Autowired
    private FwFcqlrService fwFcqlrService;
    @Autowired
    private EntityZdConvertUtils entityZdConvertUtils;
    @Autowired
    private FwLjzService fwLjzService;
    @Autowired
    FwYcHsService fwYcHsService;


    /**
     * @param fwDcbIndex
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 导出楼盘表数据
     */
    @Override
    public List<Map<String, Object>> exportLpb(String fwDcbIndex) {
        List<Map<String, Object>> exportLpbList = new ArrayList<>();
        FwLjzDO fwLjzDO = fwLjzService.queryLjzByFwDcbIndex(fwDcbIndex);
        List<FwHsDO> fwHsDOList = fwHsService.listFwHsListByFwDcbIndex(fwDcbIndex);
        List<FwYchsDO> fwYchsDOList = new ArrayList<>(1);
        if (CollectionUtils.isEmpty(fwHsDOList)) {
            //如果实测为空，查询预测户室数据
            fwYchsDOList = fwYcHsService.queryYchsByFwDcbIndex(fwDcbIndex);
        }
        entityZdConvertUtils.convertEntityToMc(fwLjzDO);
        if (CollectionUtils.isNotEmpty(fwHsDOList)) {
            for (FwHsDO fwHsDO : fwHsDOList) {
                entityZdConvertUtils.convertEntityToMc(fwHsDO);
                Map<String, Object> exportLpb = JSONObject.parseObject(JSONObject.toJSONString(fwHsDO), Map.class);
                exportLpb.put("zh", fwLjzDO.getLjzh());
                exportLpb.put("bdcdyfwlx", fwLjzDO.getBdcdyfwlx());
                exportLpb.put("jgrq", fwLjzDO.getJgrq());
                exportLpb.put("fwjg", fwLjzDO.getFwjg());
                getQlrxx(exportLpb, fwHsDO.getFwHsIndex());
                exportLpbList.add(exportLpb);
            }
        } else if (CollectionUtils.isNotEmpty(fwYchsDOList)) {
            for (FwYchsDO fwYchsDO : fwYchsDOList) {
                entityZdConvertUtils.convertEntityToMc(fwYchsDO);
                Map<String, Object> exportLpb = JSONObject.parseObject(JSONObject.toJSONString(fwYchsDO), Map.class);
                exportLpb.put("zh", fwLjzDO.getLjzh());
                exportLpb.put("bdcdyfwlx", fwLjzDO.getBdcdyfwlx());
                exportLpb.put("jgrq", fwLjzDO.getJgrq());
                exportLpb.put("fwjg", fwLjzDO.getFwjg());
                getQlrxx(exportLpb, fwYchsDO.getFwHsIndex());
                exportLpbList.add(exportLpb);
            }
        }
        return exportLpbList;
    }


    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param map
     * @param fwHsIndex
     * @return void
     * @description 权利人信息
     */
    private void getQlrxx(Map<String, Object> map, String fwHsIndex) {
        List<FwFcqlrDO> fwFcqlrDOS = fwFcqlrService.listFwFcQlrByFwIndex(fwHsIndex);
        if (CollectionUtils.isNotEmpty(fwFcqlrDOS)) {
            for (int i = 1; i <= fwFcqlrDOS.size(); i++) {
                FwFcqlrDO fwFcqlrDO = fwFcqlrDOS.get(i-1);
                entityZdConvertUtils.convertEntityToMc(fwFcqlrDO);
                map.put("qlr" + (i), fwFcqlrDO.getQlr());
                map.put("qlrbm" + (i), fwFcqlrDO.getQlrbh());
                map.put("qlrzjlx" + (i), fwFcqlrDO.getQlrzjlx());
                map.put("qlrzjh" + (i), fwFcqlrDO.getQlrzjh());
                map.put("qlrxz" + (i), fwFcqlrDO.getQlrxz());
            }
        }
    }
}