package cn.gtmap.realestate.exchange.service.impl.national.data;

import cn.gtmap.realestate.common.core.domain.exchange.AccessData;
import cn.gtmap.realestate.common.core.domain.exchange.DataModel;
import cn.gtmap.realestate.common.core.domain.exchange.KttZdjbxxDO;
import cn.gtmap.realestate.exchange.core.dto.common.DataModelOld;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.KttZdjbxxOldDO;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcdjMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.KttZdjbxxMapper;
import cn.gtmap.realestate.exchange.service.impl.inf.gx.GxDataDbByPkServiceImpl;
import cn.gtmap.realestate.exchange.service.national.NationalAccessDataService;
import cn.gtmap.realestate.exchange.util.ClassHandleUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * 国家接入-宗地基本信息
 *
 * @author <a href="mailto:shenjian@gtmap.cn">jane</a>
 * @version 1.0, 2015/11/16
 */
@Service
public class NationalAccessDataZdjbxxImpl extends GxDataDbByPkServiceImpl implements NationalAccessDataService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NationalAccessDataZdjbxxImpl.class);

    @Autowired
    private KttZdjbxxMapper kttZdjbxxMapper;

    @Autowired
    private BdcdjMapper bdcdjMapper;

    @Override
    public DataModel getAccessDataModel(String ywh, DataModel dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            HashMap<String, String> map = new HashMap();
//            List<KttZdjbxxDO> tempList = null;
            map.put("ywh", ywh);
            List<KttZdjbxxDO> kttZdjbxxList = kttZdjbxxMapper.queryKttZdjbxxList(map);
            LOGGER.info("上报查询宗地基本信息：{}", kttZdjbxxList.size());
            if (CollectionUtils.isNotEmpty(kttZdjbxxList)) {
//                tempList = new ArrayList();
                for (KttZdjbxxDO kttZdjbxx : kttZdjbxxList) {
                    String bsm = this.bdcdjMapper.getBsm().toString();
                    if (bsm.length() > 10) {
                        //作为唯一标识码固定为10位
                        bsm = bsm.substring(bsm.length() - 10);
                    } else {
                        bsm = String.format("%010d", Integer.valueOf(bsm));
                    }
                    kttZdjbxx.setBsm(Integer.valueOf(bsm));
//                    tempList.add(kttZdjbxx);
                }
                if (newDefault) {
                    for (KttZdjbxxDO kttZdjbxxDO : kttZdjbxxList) {
                        if (qxdmConvert) {
                            //需要根据qxdm进行对照
                            kttZdjbxxDO.setQxdm(commonService.bdcDmToDsfZdNvl("ACCESS_QXDM", "ACCESS", kttZdjbxxDO.getQxdm()));
                        }
                        ClassHandleUtil.setDefaultValueToNullField(kttZdjbxxDO);
                    }
                }
            }
            LOGGER.info("上报查询宗地基本信息：{}", kttZdjbxxList.toString());
            setData(dataModel, kttZdjbxxList);
        }
        return dataModel;
    }

    @Override
    public DataModelOld getAccessDataModelOld(String ywh, DataModelOld dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            HashMap<String, String> map = new HashMap();
            List<KttZdjbxxOldDO> tempList = null;
            map.put("ywh", ywh);
            List<KttZdjbxxOldDO> kttZdjbxxList = kttZdjbxxMapper.queryKttZdjbxxListOld(map);
            if (CollectionUtils.isNotEmpty(kttZdjbxxList)) {
                tempList = new ArrayList();
                for (KttZdjbxxOldDO kttZdjbxx : kttZdjbxxList) {
                    String bsm = this.bdcdjMapper.getBsm().toString();
                    if (bsm.length() > 10) {
                        //作为唯一标识码固定为10位
                        bsm = bsm.substring(bsm.length() - 10);
                    } else {
                        bsm = String.format("%010d", Integer.valueOf(bsm));
                    }
                    kttZdjbxx.setBsm(Integer.valueOf(bsm));
                    tempList.add(kttZdjbxx);
                }
            }
            if (CollectionUtils.isNotEmpty(tempList)) {
                setDataOld(dataModel, kttZdjbxxList);
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
        return "KTT_ZDJBXX";
    }

    /**
     * @param dataModel
     * @return java.util.List<java.lang.Object>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 从 dataModel 中获取单表数据
     */
    @Override
    public List getData(DataModel dataModel) {
        return dataModel.getKttZdjbxxList();
    }

    @Override
    public List getDataOld(DataModelOld dataModel) {
        return dataModel.getKttZdjbxxList();
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
            dataModel.setKttZdjbxxList(dataList);
        }
    }

    @Override
    public void setDataOld(DataModelOld dataModel, List dataList) {
        if (dataModel != null) {
            dataModel.setKttZdjbxxList(dataList);
        }
    }
}
