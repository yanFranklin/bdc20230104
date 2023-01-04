package cn.gtmap.realestate.exchange.service.impl.national.data;

import cn.gtmap.realestate.common.core.domain.exchange.AccessData;
import cn.gtmap.realestate.common.core.domain.exchange.DataModel;
import cn.gtmap.realestate.common.core.domain.exchange.QjsjDataModel;
import cn.gtmap.realestate.common.core.domain.exchange.ZdKDO;
import cn.gtmap.realestate.common.core.dto.exchange.access.QjsjjcDTO;
import cn.gtmap.realestate.common.core.service.feign.building.AccessBuildingFeignService;
import cn.gtmap.realestate.exchange.core.dto.common.DataModelOld;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.ZdKOldDO;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcdjMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.ZdkSqlMapper;
import cn.gtmap.realestate.exchange.service.impl.inf.gx.GxDataDbByFkServiceImpl;
import cn.gtmap.realestate.exchange.service.national.NationalAccessDataService;
import cn.gtmap.realestate.exchange.service.national.NationalAccessQjsjService;
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
 * 国家接入-宗地空间属性
 *
 * @author lst
 * @version 1.0, 2015/11/20
 */
@Service
public class NationalAccessDataZdImpl extends GxDataDbByFkServiceImpl implements NationalAccessDataService, NationalAccessQjsjService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NationalAccessDataZdImpl.class);

    @Autowired
    private ZdkSqlMapper zdkSqlMapper;
    @Autowired
    private BdcdjMapper bdcdjMapper;
    @Autowired
    private AccessBuildingFeignService accessBuildingFeignService;

    @Override
    public DataModel getAccessDataModel(String ywh, DataModel dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            Map<String, String> map = new HashMap();
            map.put("ywh", ywh);
            String bdcdyh = "";
            List<ZdKDO> zdKList = new ArrayList<>();
            if (datasourceSwitch) {
                LOGGER.info("开始调用building工程queryZdKList:{}", ywh);
                Map<String, Object> bdcdyhAndQjgldm = bdcdjMapper.getBdcdyhByXmid(ywh);
                if (MapUtils.isNotEmpty(bdcdyhAndQjgldm) && bdcdyhAndQjgldm.containsKey("BDCDYH")) {
                    LOGGER.info("开始调用building工程queryZdKList,入参:{}", bdcdyhAndQjgldm);
                    bdcdyh = String.valueOf(bdcdyhAndQjgldm.get("BDCDYH"));
                    zdKList = accessBuildingFeignService.queryZdList((String) bdcdyhAndQjgldm.get("BDCDYH"), bdcdyhAndQjgldm.containsKey("QJGLDM") ? (String) bdcdyhAndQjgldm.get("QJGLDM") : null);

                }
            } else {
                zdKList = zdkSqlMapper.queryZdKList(map);
            }
            if (CollectionUtils.isEmpty(zdKList)) {
                ZdKDO zdKDO = new ZdKDO();
                zdKDO.setBdcdyh(bdcdyh);
                zdKList.add(zdKDO);
            }
            if (CollectionUtils.isNotEmpty(zdKList)) {
                if (newDefault) {

                    for (ZdKDO zdKDO : zdKList) {
                        ClassHandleUtil.setDefaultValueToNullField(zdKDO);
                    }
                }
                setData(dataModel, zdKList);
            }
        }
        return dataModel;
    }

    @Override
    public DataModelOld getAccessDataModelOld(String ywh, DataModelOld dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            Map<String, String> map = new HashMap();
            map.put("ywh", ywh);
            List<ZdKOldDO> zdKList = zdkSqlMapper.queryZdKListOld(map);
            if (CollectionUtils.isNotEmpty(zdKList)) {
                setDataOld(dataModel, zdKList);
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
        return "ZD_K_103";
    }

    /**
     * @param dataModel
     * @return java.util.List<java.lang.Object>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 从 dataModel 中获取单表数据
     */
    @Override
    public List getData(DataModel dataModel) {
        return dataModel.getZdKList();
    }

    @Override
    public List getDataOld(DataModelOld dataModel) {
        return dataModel.getZdKList();
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
            dataModel.setZdKList(dataList);
        }
    }

    @Override
    public void setDataOld(DataModelOld dataModel, List dataList) {
        if (dataModel != null) {
            dataModel.setZdKList(dataList);
        }
    }

    /**
     * @param qjsjjcDTO
     * @param dataModel
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 组织权籍数据
     * @date : 2022/11/22 9:13
     */
    @Override
    public QjsjDataModel getAccessQjsjModel(QjsjjcDTO qjsjjcDTO, QjsjDataModel dataModel) {
        if (Objects.isNull(qjsjjcDTO)) {
            return dataModel;
        }
        List<ZdKDO> zdKList = new ArrayList<>();
        zdKList = accessBuildingFeignService.queryZdList(qjsjjcDTO.getBdcdyh(), qjsjjcDTO.getQjgldm());
        if (CollectionUtils.isEmpty(zdKList)) {
            ZdKDO zdKDO = new ZdKDO();
            zdKDO.setBdcdyh(qjsjjcDTO.getBdcdyh());
            zdKList.add(zdKDO);
        }
        if (CollectionUtils.isNotEmpty(zdKList)) {
            if (newDefault) {

                for (ZdKDO zdKDO : zdKList) {
                    ClassHandleUtil.setDefaultValueToNullField(zdKDO);
                }
            }
            setQjData(dataModel, zdKList);
        }
        return dataModel;
    }

    /**
     * @param dataModel@author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取查询的结果数据
     * @date : 2022/11/22 15:28
     */
    @Override
    public List getQjData(QjsjDataModel dataModel) {
        return dataModel.getZdKList();
    }

    /**
     * @param dataModel
     * @param dataList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description setQjsj
     * @date : 2022/11/24 14:05
     */
    @Override
    public void setQjData(QjsjDataModel dataModel, List dataList) {
        if (dataModel != null) {
            dataModel.setZdKList(dataList);
        }
    }
}
