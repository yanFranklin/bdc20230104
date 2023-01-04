package cn.gtmap.realestate.exchange.service.impl.national.data;

import cn.gtmap.realestate.exchange.core.domain.exchange.AccessData;
import cn.gtmap.realestate.exchange.core.domain.exchange.DataModel;
import cn.gtmap.realestate.exchange.core.domain.exchange.KttFwCDO;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcdjMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.KttFwCMapper;
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
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 国家接入-层信息
 *
 * @author lst
 * @version 1.0, 2015/11/20
 */
public class NationalAccessDataCImpl extends GxDataDbByPkServiceImpl implements NationalAccessDataService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NationalAccessDataCImpl.class);

    @Autowired
    private KttFwCMapper kttFwCMapper;
    @Autowired
    private BdcdjMapper bdcdjMapper;
    @Autowired
    private BuildingService buildingService;

    @Override
    public DataModel getAccessDataModel(String ywh, DataModel dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            Map<String, String> map = new HashMap();
            map.put("ywh", ywh);
            /**
             * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
             * @description 获取不动产单元房屋类型
             */
            List<KttFwCDO> kttFwCList = null;
            if (datasourceSwitch && CommonConstantUtils.DZZZ_HEFEI.equals(dataVersion)) {
                LOGGER.info("开始调用building工程queryKttFwCList:{}", ywh);
                Map<String, Object> bdcdyhAndQjgldm = bdcdjMapper.getBdcdyhByXmid(ywh);
                if (MapUtils.isNotEmpty(bdcdyhAndQjgldm) && bdcdyhAndQjgldm.containsKey("BDCDYH")) {
                    LOGGER.info("开始调用building工程queryKttFwCList,入参:{}", bdcdyhAndQjgldm);
                    kttFwCList = buildingService.queryKttFwCList((String) bdcdyhAndQjgldm.get("BDCDYH"), bdcdyhAndQjgldm.containsKey("QJGLDM") ? (String) bdcdyhAndQjgldm.get("QJGLDM") : null);
                }
            } else {
                kttFwCList = kttFwCMapper.queryKttFwCList(map);
            }
            if (CollectionUtils.isNotEmpty(kttFwCList)) {
                if (newDefault) {
                    for (KttFwCDO kttFwCDO : kttFwCList) {
                        ClassHandleUtil.setDefaultValueToNullField(kttFwCDO);
                    }
                }
                setData(dataModel, kttFwCList);
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
        return "KTT_FW_C";
    }

    /**
     * @param dataModel
     * @return java.util.List<java.lang.Object>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 从 dataModel 中获取单表数据
     */
    @Override
    public List getData(DataModel dataModel) {
        return dataModel.getKttFwCList();
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
            dataModel.setKttFwCList(dataList);
        }
    }

}
