package cn.gtmap.realestate.exchange.service.impl.national.data;

import cn.gtmap.realestate.exchange.core.domain.exchange.AccessData;
import cn.gtmap.realestate.exchange.core.domain.exchange.DataModel;
import cn.gtmap.realestate.exchange.core.domain.exchange.KttFwLjzDO;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcdjMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.KttFwLjzMapper;
import cn.gtmap.realestate.exchange.service.impl.inf.gx.GxDataDbByPkServiceImpl;
import cn.gtmap.realestate.exchange.service.inf.qj.BuildingService;
import cn.gtmap.realestate.exchange.service.national.NationalAccessDataService;
import cn.gtmap.realestate.exchange.util.ClassHandleUtil;
import cn.gtmap.realestate.exchange.util.CommonConstantUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 国家接入-逻辑幢信息
 *
 * @author lst
 * @version 1.0, 2015/11/20
 */
@Service
public class NationalAccessDataLjzImpl extends GxDataDbByPkServiceImpl implements NationalAccessDataService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NationalAccessDataLjzImpl.class);

    @Autowired
    private KttFwLjzMapper kttFwLjzMapper;
    @Autowired
    private BdcdjMapper bdcdjMapper;
    @Autowired
    private BuildingService buildingService;

    @Override
    public DataModel getAccessDataModel(String ywh, DataModel dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            Map<String, String> map = new HashMap();
            map.put("ywh", ywh);
            // 先作为户室类型查询
            map.put("ishs", "true");
            List<KttFwLjzDO> kttFwLjzList = null;
            if (datasourceSwitch && CommonConstantUtils.DZZZ_HEFEI.equals(dataVersion)) {
                LOGGER.info("开始调用building工程queryKttFwLjzList:{}", ywh);
                Map<String, Object> bdcdyhAndQjgldm = bdcdjMapper.getBdcdyhByXmid(ywh);
                if (MapUtils.isNotEmpty(bdcdyhAndQjgldm) && bdcdyhAndQjgldm.containsKey("BDCDYH")) {
                    LOGGER.info("开始调用building工程queryKttFwLjzList,入参:{}", bdcdyhAndQjgldm);
                    kttFwLjzList = buildingService.queryKttFwLjzList((String) bdcdyhAndQjgldm.get("BDCDYH"), bdcdyhAndQjgldm.containsKey("QJGLDM") ? (String) bdcdyhAndQjgldm.get("QJGLDM") : null);
                }
            } else {
                kttFwLjzList = kttFwLjzMapper.queryKttFwLjzList(map);
            }
            if (CollectionUtils.isEmpty(kttFwLjzList)) {
                map.clear();
                map.put("ywh", ywh);
                // 在作为 独幢类型查询
                map.put("isyz", "true");
                kttFwLjzList = kttFwLjzMapper.queryKttFwLjzList(map);
            }
            if (CollectionUtils.isNotEmpty(kttFwLjzList)) {
                if (newDefault) {

                    for (KttFwLjzDO kttFwLjzDO : kttFwLjzList) {
                        ClassHandleUtil.setDefaultValueToNullField(kttFwLjzDO);
                    }
                }
                setData(dataModel, kttFwLjzList);
            }
        }
        return dataModel;
    }

    @Override
    public List<AccessData> getAccessData(String ywh) {
        return Collections.emptyList();
    }

    @Override
    public String getAccessDataTagName() {
        return "KTT_FW_LJZ";
    }

    /**
     * @param dataModel
     * @return java.util.List<java.lang.Object>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 从 dataModel 中获取单表数据
     */
    @Override
    public List getData(DataModel dataModel) {
        return dataModel.getKttFwLjzList();
    }

    /**
     * @param dataModel
     * @param dataList
     * @return java.util.List
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description
     */
    @Override
    public void setData(DataModel dataModel, List dataList) {
        if (dataModel != null) {
            dataModel.setKttFwLjzList(dataList);
        }
    }

}
