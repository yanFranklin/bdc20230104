package cn.gtmap.realestate.exchange.service.impl.national.data;

import cn.gtmap.realestate.common.core.domain.BdcFdcqDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.exchange.AccessData;
import cn.gtmap.realestate.common.core.domain.exchange.DataModel;
import cn.gtmap.realestate.common.core.domain.exchange.KttFwHDO;
import cn.gtmap.realestate.common.core.domain.exchange.QjsjDataModel;
import cn.gtmap.realestate.common.core.dto.exchange.access.QjsjjcDTO;
import cn.gtmap.realestate.common.core.service.feign.building.AccessBuildingFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.core.dto.common.DataModelOld;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.KttFwHOldDO;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcdjMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.KttFwHMapper;
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
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;

/**
 * 国家接入-户信息
 *
 * @author lst
 * @version 1.0, 2015/11/20
 */
@Service
public class NationalAccessDataHImpl extends GxDataDbByPkServiceImpl implements NationalAccessDataService, NationalAccessQjsjService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NationalAccessDataHImpl.class);

    @Autowired
    private KttFwHMapper kttFwHMapper;

    @Autowired
    private CommonService commonService;

    @Autowired
    private BdcdjMapper bdcdjMapper;

    @Autowired
    private AccessBuildingFeignService accessBuildingFeignService;

    @Override
    public DataModel getAccessDataModel(String ywh, DataModel dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            Map<String, String> map = new HashMap();
            map.put("ywh", ywh);
            BdcXmDO bdcXmDO = commonService.getBdcXmByXmid(ywh);
            if (bdcXmDO != null && (CommonConstantUtils.BDCDYFWLX_DUZH.equals(bdcXmDO.getBdcdyfwlx()) || CommonConstantUtils.FWLX_DUOZH.equals(bdcXmDO.getBdcdyfwlx()))) {
                map.put("sfdz", "true");
            }
            List<KttFwHDO> kttFwHList = null;
            if (datasourceSwitch) {
                LOGGER.info("开始调用building工程queryKttFwHList:{}", ywh);
                Map<String, Object> bdcdyhAndQjgldm = bdcdjMapper.getBdcdyhByXmid(ywh);
                if (MapUtils.isNotEmpty(bdcdyhAndQjgldm) && bdcdyhAndQjgldm.containsKey("BDCDYH")) {
                    LOGGER.info("开始调用building工程queryKttFwHList,入参:{}", bdcdyhAndQjgldm);
                    kttFwHList = accessBuildingFeignService.queryKttFwHList((String) bdcdyhAndQjgldm.get("BDCDYH"), map.containsKey("sfdz"), bdcdyhAndQjgldm.containsKey("QJGLDM") ? (String) bdcdyhAndQjgldm.get("QJGLDM") : null);
                    for (KttFwHDO kttFwHDO : kttFwHList) {
                        if (qxdmConvert) {
                            //需要根据qxdm进行对照
                            kttFwHDO.setQxdm(commonService.bdcDmToDsfZdNvl("ACCESS_QXDM", "ACCESS", kttFwHDO.getQxdm()));
                        }
                        if (StringUtils.isBlank(kttFwHDO.getZl())) {
                            kttFwHDO.setZl(bdcXmDO.getZl());
                        }
                        if (StringUtils.isBlank(kttFwHDO.getFwyt1())) {
                            kttFwHDO.setFwyt1(null != bdcXmDO.getDzwyt() ? bdcXmDO.getDzwyt().toString() : null);
                        }
                    }
                }
            } else {
                if (null != bdcXmDO.getBdcdyfwlx()) {
                    if (1 == bdcXmDO.getBdcdyfwlx()) {
                        LOGGER.info("多幢H开始查询逻辑幢信息：xmid:{}", ywh);
                        kttFwHList = this.kttFwHMapper.queryKttFwHDzList(map);
                    } else {
                        LOGGER.info("非多幢H开始查询逻辑幢信息：xmid:{}", ywh);
                        kttFwHList = this.kttFwHMapper.queryKttFwHList(map);
                    }
                } else {
                    BdcFdcqDO fdcqDO = this.commonService.getFdcqByXmid(ywh);
                    if (null != fdcqDO) {
                        if (1 == fdcqDO.getBdcdyfwlx()) {
                            LOGGER.info("fdcq多幢H开始查询逻辑幢信息：xmid:{}", ywh);
                            kttFwHList = this.kttFwHMapper.queryKttFwHDzList(map);
                        } else {
                            LOGGER.info("fdcq非多幢H开始查询逻辑幢信息：xmid:{}", ywh);
                            kttFwHList = this.kttFwHMapper.queryKttFwHList(map);
                        }
                    }
                }

                if (CollectionUtils.isEmpty(kttFwHList)) {
                    map.clear();
                    map.put("ywh", ywh);
                    kttFwHList = this.kttFwHMapper.queryKttFwHList(map);
                }
            }
            if (CollectionUtils.isNotEmpty(kttFwHList)) {
                if (newDefault) {

                    for (KttFwHDO kttFwHDO : kttFwHList) {
                        ClassHandleUtil.setDefaultValueToNullField(kttFwHDO);
                    }
                }
                setData(dataModel, kttFwHList);
            }
        }
        return dataModel;
    }

    @Override
    public DataModelOld getAccessDataModelOld(String ywh, DataModelOld dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            Map<String, String> map = new HashMap();
            map.put("ywh", ywh);
            BdcXmDO bdcXmDO = commonService.getBdcXmByXmid(ywh);
            if (bdcXmDO != null && CommonConstantUtils.BDCDYFWLX_DUZH.equals(bdcXmDO.getBdcdyfwlx())) {
                map.put("sfdz", "true");
            }
            List<KttFwHOldDO> kttFwHList = kttFwHMapper.queryKttFwHListOld(map);

            if (CollectionUtils.isNotEmpty(kttFwHList)) {

                setDataOld(dataModel, kttFwHList);
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
        return "KTT_FW_H";
    }

    /**
     * @param dataModel
     * @return java.util.List<java.lang.Object>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 从 dataModel 中获取单表数据
     */
    @Override
    public List getData(DataModel dataModel) {
        return dataModel.getKttFwHList();
    }

    @Override
    public List getDataOld(DataModelOld dataModel) {
        return dataModel.getKttFwHList();
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
            dataModel.setKttFwHList(dataList);
        }
    }

    @Override
    public void setDataOld(DataModelOld dataModel, List dataList) {
        if (dataModel != null) {
            dataModel.setKttFwHList(dataList);
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
        List<KttFwHDO> kttFwHList = null;
        boolean sfdz = false;
        if ((CommonConstantUtils.BDCDYFWLX_DUZH.equals(qjsjjcDTO.getBdcdyfwlx()) || CommonConstantUtils.FWLX_DUOZH.equals(qjsjjcDTO.getBdcdyfwlx()))) {
            sfdz = true;
        }
        kttFwHList = accessBuildingFeignService.queryKttFwHList(qjsjjcDTO.getBdcdyh(), sfdz, qjsjjcDTO.getQjgldm());
        for (KttFwHDO kttFwHDO : kttFwHList) {
            if (qxdmConvert) {
                //需要根据qxdm进行对照
                kttFwHDO.setQxdm(commonService.bdcDmToDsfZdNvl("ACCESS_QXDM", "ACCESS", kttFwHDO.getQxdm()));
            }
            if (StringUtils.isBlank(kttFwHDO.getZl())) {
                kttFwHDO.setZl(qjsjjcDTO.getZl());
            }
            if (StringUtils.isBlank(kttFwHDO.getFwyt1())) {
                kttFwHDO.setFwyt1(qjsjjcDTO.getDzwyt());
            }
        }
        if (CollectionUtils.isNotEmpty(kttFwHList)) {
            if (newDefault) {

                for (KttFwHDO kttFwHDO : kttFwHList) {
                    ClassHandleUtil.setDefaultValueToNullField(kttFwHDO);
                }
            }
            setQjData(dataModel, kttFwHList);
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
        return dataModel.getKttFwHList();
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
            dataModel.setKttFwHList(dataList);
        }
    }
}
