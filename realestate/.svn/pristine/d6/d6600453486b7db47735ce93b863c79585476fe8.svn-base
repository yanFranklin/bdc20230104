package cn.gtmap.realestate.exchange.service.impl.national.data;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.exchange.AccessData;
import cn.gtmap.realestate.common.core.domain.exchange.DataModel;
import cn.gtmap.realestate.common.core.domain.exchange.KttFwCDO;
import cn.gtmap.realestate.common.core.domain.exchange.QjsjDataModel;
import cn.gtmap.realestate.common.core.dto.exchange.access.QjsjjcDTO;
import cn.gtmap.realestate.common.core.service.feign.building.AccessBuildingFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.FwXmxxFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.core.dto.common.DataModelOld;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.KttFwCOldDO;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcdjMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.KttFwCMapper;
import cn.gtmap.realestate.exchange.service.impl.inf.gx.GxDataDbByPkServiceImpl;
import cn.gtmap.realestate.exchange.service.inf.CommonService;
import cn.gtmap.realestate.exchange.service.national.NationalAccessDataService;
import cn.gtmap.realestate.exchange.service.national.NationalAccessQjsjService;
import cn.gtmap.realestate.exchange.util.ClassHandleUtil;
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
public class NationalAccessDataCImpl extends GxDataDbByPkServiceImpl implements NationalAccessDataService, NationalAccessQjsjService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NationalAccessDataCImpl.class);

    @Autowired
    private KttFwCMapper kttFwCMapper;
    @Autowired
    private BdcdjMapper bdcdjMapper;
    @Autowired
    private AccessBuildingFeignService accessBuildingFeignService;
    @Autowired
    private CommonService commonService;
    @Autowired
    private FwXmxxFeignService fwXmxxFeignService;

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
            if (datasourceSwitch) {
                LOGGER.info("开始调用building工程queryKttFwCList:{}", ywh);
                Map<String, Object> bdcdyhAndQjgldm = bdcdjMapper.getBdcdyhByXmid(ywh);
                BdcXmDO bdcXmDO = commonService.getBdcXmByXmid(ywh);
                if (MapUtils.isNotEmpty(bdcdyhAndQjgldm) && bdcdyhAndQjgldm.containsKey("BDCDYH")) {
                    LOGGER.info("开始调用building工程queryKttFwCList,入参:{}", bdcdyhAndQjgldm);
                    //判断是否独幢
                    if (CommonConstantUtils.BDCDYFWLX_DUZH.equals(bdcXmDO.getBdcdyfwlx()) || CommonConstantUtils.FWLX_DUOZH.equals(bdcXmDO.getBdcdyfwlx())) {
                        kttFwCList = accessBuildingFeignService.queryKttFwCListDz((String) bdcdyhAndQjgldm.get("BDCDYH"), bdcdyhAndQjgldm.containsKey("QJGLDM") ? (String) bdcdyhAndQjgldm.get("QJGLDM") : null);
                    } else {
                        kttFwCList = accessBuildingFeignService.queryKttFwCList((String) bdcdyhAndQjgldm.get("BDCDYH"), bdcdyhAndQjgldm.containsKey("QJGLDM") ? (String) bdcdyhAndQjgldm.get("QJGLDM") : null);
                    }
                }
            } else {
                kttFwCList = kttFwCMapper.queryKttFwCList(map);
            }
            if (CollectionUtils.isNotEmpty(kttFwCList)) {
                if (newDefault) {
                    for (KttFwCDO kttFwCDO : kttFwCList) {
                        if (qxdmConvert) {
                            //需要根据qxdm进行对照
                            kttFwCDO.setQxdm(commonService.bdcDmToDsfZdNvl("ACCESS_QXDM", "ACCESS", kttFwCDO.getQxdm()));
                        }
                        ClassHandleUtil.setDefaultValueToNullField(kttFwCDO);
                    }
                }
                setData(dataModel, kttFwCList);
            }
        }
        return dataModel;
    }

    /**
     * @param qjsjjcDTO
     * @param dataModel
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取权籍数据并组织
     * @date : 2022/11/22 8:41
     */
    @Override
    public QjsjDataModel getAccessQjsjModel(QjsjjcDTO qjsjjcDTO, QjsjDataModel dataModel) {
        List<KttFwCDO> kttFwCList = null;
        if (CommonConstantUtils.BDCDYFWLX_DUZH.equals(qjsjjcDTO.getBdcdyfwlx()) || CommonConstantUtils.FWLX_DUOZH.equals(qjsjjcDTO.getBdcdyfwlx())) {
            kttFwCList = accessBuildingFeignService.queryKttFwCListDz(qjsjjcDTO.getBdcdyh(), qjsjjcDTO.getQjgldm());
        } else {
            kttFwCList = accessBuildingFeignService.queryKttFwCList(qjsjjcDTO.getBdcdyh(), qjsjjcDTO.getQjgldm());
        }
        if (CollectionUtils.isNotEmpty(kttFwCList)) {
            if (newDefault) {
                for (KttFwCDO kttFwCDO : kttFwCList) {
                    ClassHandleUtil.setDefaultValueToNullField(kttFwCDO);
                }
            }
            setQjData(dataModel, kttFwCList);
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
        return dataModel.getKttFwCList();
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
            dataModel.setKttFwCList(dataList);
        }
    }

    @Override
    public DataModelOld getAccessDataModelOld(String ywh, DataModelOld dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            Map<String, String> map = new HashMap();
            map.put("ywh", ywh);
            /**
             * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
             * @description 获取不动产单元房屋类型
             */
            List<KttFwCOldDO> kttFwCListOld = null;
            kttFwCListOld = kttFwCMapper.queryKttFwCListOld(map);
            if (CollectionUtils.isNotEmpty(kttFwCListOld)) {
                setDataOld(dataModel, kttFwCListOld);
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

    @Override
    public List getDataOld(DataModelOld dataModel) {
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

    @Override
    public void setDataOld(DataModelOld dataModel, List dataList) {
        if (dataModel != null) {
            dataModel.setKttFwCList(dataList);
        }
    }
}
