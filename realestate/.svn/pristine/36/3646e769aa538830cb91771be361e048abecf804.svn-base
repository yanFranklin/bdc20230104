package cn.gtmap.realestate.exchange.service.impl.national.data;

import cn.gtmap.realestate.common.core.domain.exchange.AccessData;
import cn.gtmap.realestate.common.core.domain.exchange.DataModel;
import cn.gtmap.realestate.common.core.domain.exchange.KttGyJzxDO;
import cn.gtmap.realestate.common.core.service.feign.building.AccessBuildingFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.core.dto.common.DataModelOld;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.KttGyJzxOldDO;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcdjMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.KttGyJzxMapper;
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
 * 国家接入-界址线信息
 *
 * @author lst
 * @version 1.0, 2015/11/20
 */
@Service
public class NationalAccessDataJzxImpl extends GxDataDbByFkServiceImpl implements NationalAccessDataService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NationalAccessDataJzxImpl.class);

    @Autowired
    private KttGyJzxMapper kttGyJzxMapper;

    @Autowired
    private BdcdjMapper bdcdjMapper;

    @Autowired
    private AccessBuildingFeignService accessBuildingFeignService;

    @Override
    public DataModel getAccessDataModel(String ywh, DataModel dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            HashMap<String, String> map = new HashMap();
            List<KttGyJzxDO> tempList = null;
            map.put("ywh", ywh);
            List<KttGyJzxDO> kttGyJzxList = null;
            if (datasourceSwitch) {
                LOGGER.info("开始调用building工程queryKttGyJzxList:{}", ywh);
                Map<String, Object> bdcdyhAndQjgldm = bdcdjMapper.getBdcdyhByXmid(ywh);
                if (MapUtils.isNotEmpty(bdcdyhAndQjgldm) && bdcdyhAndQjgldm.containsKey("BDCDYH")) {
                    LOGGER.info("开始调用building工程queryKttGyJzxList,入参:{}", bdcdyhAndQjgldm);
                    kttGyJzxList = accessBuildingFeignService.queryKttGyJzxList((String) bdcdyhAndQjgldm.get("BDCDYH"), bdcdyhAndQjgldm.containsKey("QJGLDM") ? (String) bdcdyhAndQjgldm.get("QJGLDM") : null);
                }
            } else {
                kttGyJzxList = kttGyJzxMapper.queryKttGyJzxList(map);
            }
            if (CollectionUtils.isNotEmpty(kttGyJzxList)) {
                tempList = new ArrayList();
                for (KttGyJzxDO kttGyJzx : kttGyJzxList) {
                    String bsm = bdcdjMapper.getBsm().toString();
                    if (bsm.length() > 10) {
                        //作为唯一标识码固定为10位
                        bsm = bsm.substring(bsm.length() - 10);
                    } else {
                        bsm = String.format("%010d", Integer.valueOf(bsm));
                    }
                    kttGyJzx.setBsm(Integer.valueOf(bsm));
                    if (newDefault) {

                        ClassHandleUtil.setDefaultValueToNullField(kttGyJzx);
                    }
                    tempList.add(kttGyJzx);
                }
            }
            if (CollectionUtils.isNotEmpty(tempList)) {
                setData(dataModel, tempList);
            }
        }
        return dataModel;
    }

    @Override
    public DataModelOld getAccessDataModelOld(String ywh, DataModelOld dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            HashMap<String, String> map = new HashMap();
            List<KttGyJzxOldDO> tempList = null;
            map.put("ywh", ywh);
            List<KttGyJzxOldDO> kttGyJzxList = kttGyJzxMapper.queryKttGyJzxListOld(map);
            if (CollectionUtils.isNotEmpty(kttGyJzxList)) {
                tempList = new ArrayList();
                for (KttGyJzxOldDO kttGyJzx : kttGyJzxList) {
                    String bsm = bdcdjMapper.getBsm().toString();
                    if (bsm.length() > 10) {
                        //作为唯一标识码固定为10位
                        bsm = bsm.substring(bsm.length() - 10);
                    } else {
                        bsm = String.format("%010d", Integer.valueOf(bsm));
                    }
                    kttGyJzx.setBsm(Integer.valueOf(bsm));
                    tempList.add(kttGyJzx);
                }
            }
            if (CollectionUtils.isNotEmpty(tempList)) {
                setDataOld(dataModel, tempList);
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
        return "KTT_GY_JZX";
    }

    /**
     * @param dataModel
     * @return java.util.List<java.lang.Object>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 从 dataModel 中获取单表数据
     */
    @Override
    public List getData(DataModel dataModel) {
        return dataModel.getKttGyJzxList();
    }

    @Override
    public List getDataOld(DataModelOld dataModel) {
        return dataModel.getKttGyJzxList();
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
            dataModel.setKttGyJzxList(dataList);
        }
    }

    @Override
    public void setDataOld(DataModelOld dataModel, List dataList) {
        if (dataModel != null) {
            dataModel.setKttGyJzxList(dataList);
        }
    }
}
