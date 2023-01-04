package cn.gtmap.realestate.exchange.service.impl.national.data;

import cn.gtmap.realestate.common.core.domain.exchange.AccessData;
import cn.gtmap.realestate.common.core.domain.exchange.DataModel;
import cn.gtmap.realestate.common.core.domain.exchange.KttGyJzdDO;
import cn.gtmap.realestate.common.core.service.feign.building.AccessBuildingFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.core.dto.common.DataModelOld;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.KttGyJzdOldDO;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcdjMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.KttGyJzdMapper;
import cn.gtmap.realestate.exchange.service.impl.inf.gx.GxDataDbByFkServiceImpl;
import cn.gtmap.realestate.exchange.service.national.NationalAccessDataService;
import cn.gtmap.realestate.exchange.util.ClassHandleUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;

/**
 * 国家接入-界址点信息
 *
 * @author lst
 * @version 1.0, 2015/11/20
 */
@Service
public class NationalAccessDataJzdImpl extends GxDataDbByFkServiceImpl implements NationalAccessDataService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NationalAccessDataJzdImpl.class);

    @Autowired
    private KttGyJzdMapper kttGyJzdMapper;

    @Autowired
    private BdcdjMapper bdcdjMapper;

    @Autowired
    private AccessBuildingFeignService accessBuildingFeignService;

    @Override
    public DataModel getAccessDataModel(String ywh, DataModel dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            HashMap<String, String> map = new HashMap();
            List<KttGyJzdDO> tempList = null;
            map.put("ywh", ywh);
            List<KttGyJzdDO> kttGyJzdList = null;
            if (datasourceSwitch) {
                LOGGER.info("开始调用building工程queryKttGyJzdList:{}", ywh);
                Map<String, Object> bdcdyhAndQjgldm = bdcdjMapper.getBdcdyhByXmid(ywh);
                if (MapUtils.isNotEmpty(bdcdyhAndQjgldm) && bdcdyhAndQjgldm.containsKey("BDCDYH")) {
                    LOGGER.info("开始调用building工程queryKttGyJzdList,入参:{}", bdcdyhAndQjgldm);
                    kttGyJzdList = accessBuildingFeignService.queryKttGyJzdList((String) bdcdyhAndQjgldm.get("BDCDYH"), bdcdyhAndQjgldm.containsKey("QJGLDM") ? (String) bdcdyhAndQjgldm.get("QJGLDM") : null);
                }
            } else {
                kttGyJzdList = kttGyJzdMapper.queryKttGyJzdList(map);
            }
            if (CollectionUtils.isNotEmpty(kttGyJzdList)) {
                tempList = new ArrayList();
                for (KttGyJzdDO kttGyJzd : kttGyJzdList) {
                    String bsm = bdcdjMapper.getBsm().toString();
                    if (bsm.length() > 10) {
                        //作为唯一标识码固定为10位
                        bsm = bsm.substring(bsm.length() - 10);
                    } else {
                        bsm = String.format("%010d", Integer.valueOf(bsm));
                    }
                    kttGyJzd.setBsm(Integer.valueOf(bsm));
                    if (newDefault) {

                        ClassHandleUtil.setDefaultValueToNullField(kttGyJzd);
                    }
                    tempList.add(kttGyJzd);
                }
            }
            if (CollectionUtils.isNotEmpty(tempList)) {
                setData(dataModel, kttGyJzdList);
            }
        }
        return dataModel;
    }

    @Override
    public DataModelOld getAccessDataModelOld(String ywh, DataModelOld dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            HashMap<String, String> map = new HashMap();
            List<KttGyJzdOldDO> tempList = null;
            map.put("ywh", ywh);
            List<KttGyJzdOldDO> kttGyJzdList = kttGyJzdMapper.queryKttGyJzdListOld(map);

            if (CollectionUtils.isNotEmpty(kttGyJzdList)) {
                tempList = new ArrayList();
                for (KttGyJzdOldDO kttGyJzd : kttGyJzdList) {
                    String bsm = bdcdjMapper.getBsm().toString();
                    if (bsm.length() > 10) {
                        //作为唯一标识码固定为10位
                        bsm = bsm.substring(bsm.length() - 10);
                    } else {
                        bsm = String.format("%010d", Integer.valueOf(bsm));
                    }
                    kttGyJzd.setBsm(Integer.valueOf(bsm));
                    tempList.add(kttGyJzd);
                }
            }
            if (CollectionUtils.isNotEmpty(tempList)) {
                setDataOld(dataModel, kttGyJzdList);
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
        return "KTT_GY_JZD";
    }

    /**
     * @param dataModel
     * @return java.util.List<java.lang.Object>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 从 dataModel 中获取单表数据
     */
    @Override
    public List getData(DataModel dataModel) {
        return dataModel.getKttGyJzdList();
    }

    @Override
    public List getDataOld(DataModelOld dataModel) {
        return dataModel.getKttGyJzdList();
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
            dataModel.setKttGyJzdList(dataList);
        }
    }

    @Override
    public void setDataOld(DataModelOld dataModel, List dataList) {
        if (dataModel != null) {
            dataModel.setKttGyJzdList(dataList);
        }
    }
}
