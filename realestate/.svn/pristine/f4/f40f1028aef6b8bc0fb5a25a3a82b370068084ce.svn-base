package cn.gtmap.realestate.exchange.service.impl.national.data;

import cn.gtmap.realestate.common.core.domain.BdcFdcqDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.exchange.AccessData;
import cn.gtmap.realestate.common.core.domain.exchange.DataModel;
import cn.gtmap.realestate.common.core.domain.exchange.KttFwLjzDO;
import cn.gtmap.realestate.common.core.domain.exchange.QjsjDataModel;
import cn.gtmap.realestate.common.core.dto.exchange.access.QjsjjcDTO;
import cn.gtmap.realestate.common.core.service.feign.building.AccessBuildingFeignService;
import cn.gtmap.realestate.exchange.core.dto.common.DataModelOld;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.KttFwLjzOldDO;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcdjMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.KttFwLjzMapper;
import cn.gtmap.realestate.exchange.service.impl.inf.gx.GxDataDbByPkServiceImpl;
import cn.gtmap.realestate.exchange.service.inf.CommonService;
import cn.gtmap.realestate.exchange.service.national.NationalAccessDataService;
import cn.gtmap.realestate.exchange.service.national.NationalAccessQjsjService;
import cn.gtmap.realestate.exchange.util.ClassHandleUtil;
import com.alibaba.fastjson.JSON;
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
 * 国家接入-逻辑幢信息
 *
 * @author lst
 * @version 1.0, 2015/11/20
 */
@Service
public class NationalAccessDataLjzImpl extends GxDataDbByPkServiceImpl implements NationalAccessDataService, NationalAccessQjsjService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NationalAccessDataLjzImpl.class);

    @Autowired
    private KttFwLjzMapper kttFwLjzMapper;
    @Autowired
    private BdcdjMapper bdcdjMapper;
    @Autowired
    private AccessBuildingFeignService accessBuildingFeignService;

    @Autowired
    private CommonService commonService;

    @Override
    public DataModel getAccessDataModel(String ywh, DataModel dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            Map<String, String> map = new HashMap();
            map.put("ywh", ywh);
            // 先作为户室类型查询
            map.put("ishs", "true");
            List<KttFwLjzDO> kttFwLjzList = null;
            if (datasourceSwitch) {
                LOGGER.info("开始调用building工程queryKttFwLjzList:{}", ywh);
                Map<String, Object> bdcdyhAndQjgldm = bdcdjMapper.getBdcdyhByXmid(ywh);
                if (MapUtils.isNotEmpty(bdcdyhAndQjgldm) && bdcdyhAndQjgldm.containsKey("BDCDYH")) {
                    LOGGER.info("开始调用building工程queryKttFwLjzList,入参:{}", bdcdyhAndQjgldm);
                    kttFwLjzList = accessBuildingFeignService.queryKttFwLjzList((String) bdcdyhAndQjgldm.get("BDCDYH"), bdcdyhAndQjgldm.containsKey("QJGLDM") ? (String) bdcdyhAndQjgldm.get("QJGLDM") : null);
                }
            } else {
                //根据bdcdyfwlx判断是不是多幢   bdcdyfwlx=1 是多幢
                BdcXmDO bdcXmDO = bdcdjMapper.queryBdcXm(ywh);
                if (null != bdcXmDO.getBdcdyfwlx()) {
                    if (1 == bdcXmDO.getBdcdyfwlx()) {
                        LOGGER.info("多幢开始查询逻辑幢信息：xmid:{}", ywh);
                        kttFwLjzList = kttFwLjzMapper.queryKttFwLjzDzList(map);
                    } else {
                        LOGGER.info("非多幢开始查询逻辑幢信息：xmid:{}", ywh);
                        kttFwLjzList = kttFwLjzMapper.queryKttFwLjzList(map);
                    }
                } else {
                    //项目表的fwlx为空时，查fdcq表再尝试一下
                    BdcFdcqDO fdcqDO = commonService.getFdcqByXmid(ywh);
                    if (null != fdcqDO) {
                        if (1 == fdcqDO.getBdcdyfwlx()) {
                            LOGGER.info("fdcq多幢开始查询逻辑幢信息：xmid:{}", ywh);
                            kttFwLjzList = kttFwLjzMapper.queryKttFwLjzDzList(map);
                        } else {
                            LOGGER.info("fdcq非多幢开始查询逻辑幢信息：xmid:{}", ywh);
                            kttFwLjzList = kttFwLjzMapper.queryKttFwLjzList(map);
                        }
                    }
                }
                if (CollectionUtils.isEmpty(kttFwLjzList)) {
                    map.clear();
                    map.put("ywh", ywh);
                    // 在作为 独幢类型查询
                    map.put("isyz", "true");
                    kttFwLjzList = kttFwLjzMapper.queryKttFwLjzList(map);
                }

            }
            if (CollectionUtils.isNotEmpty(kttFwLjzList)) {
                if (newDefault) {

                    for (KttFwLjzDO kttFwLjzDO : kttFwLjzList) {
                        if (qxdmConvert) {
                            //需要根据qxdm进行对照
                            kttFwLjzDO.setQxdm(commonService.bdcDmToDsfZdNvl("ACCESS_QXDM", "ACCESS", kttFwLjzDO.getQxdm()));
                        }
                        ClassHandleUtil.setDefaultValueToNullField(kttFwLjzDO);
                    }
                }
                setData(dataModel, kttFwLjzList);
            }
        }
        return dataModel;
    }


    @Override
    public DataModelOld getAccessDataModelOld(String ywh, DataModelOld dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            Map<String, String> map = new HashMap();
            map.put("ywh", ywh);
            // 先作为户室类型查询
            map.put("ishs", "true");
            List<KttFwLjzOldDO> kttFwLjzList = kttFwLjzMapper.queryKttFwLjzListOld(map);
            if (CollectionUtils.isEmpty(kttFwLjzList)) {
                map.clear();
                map.put("ywh", ywh);
                // 在作为 独幢类型查询
                map.put("isyz", "true");
                kttFwLjzList = kttFwLjzMapper.queryKttFwLjzListOld(map);
            }
            if (CollectionUtils.isNotEmpty(kttFwLjzList)) {
                setDataOld(dataModel, kttFwLjzList);
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

    @Override
    public List getDataOld(DataModelOld dataModel) {
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

    @Override
    public void setDataOld(DataModelOld dataModel, List dataList) {
        if (dataModel != null) {
            dataModel.setKttFwLjzList(dataList);
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
        List<KttFwLjzDO> kttFwLjzList = null;
        if (Objects.isNull(qjsjjcDTO)) {
            return dataModel;
        }
        LOGGER.info("开始调用building工程queryKttFwLjzList,入参:{}", JSON.toJSONString(qjsjjcDTO));
        kttFwLjzList = accessBuildingFeignService.queryKttFwLjzList(qjsjjcDTO.getBdcdyh(), qjsjjcDTO.getQjgldm());
        if (CollectionUtils.isNotEmpty(kttFwLjzList)) {
            if (newDefault) {

                for (KttFwLjzDO kttFwLjzDO : kttFwLjzList) {
                    if (qxdmConvert) {
                        //需要根据qxdm进行对照
                        kttFwLjzDO.setQxdm(commonService.bdcDmToDsfZdNvl("ACCESS_QXDM", "ACCESS", kttFwLjzDO.getQxdm()));
                    }
                    ClassHandleUtil.setDefaultValueToNullField(kttFwLjzDO);
                }
            }
            setQjData(dataModel, kttFwLjzList);
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
        return dataModel.getKttFwLjzList();
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
            dataModel.setKttFwLjzList(dataList);
        }
    }
}
