package cn.gtmap.realestate.exchange.service.impl.national.data;

import cn.gtmap.realestate.common.core.domain.exchange.AccessData;
import cn.gtmap.realestate.common.core.domain.exchange.DataModel;
import cn.gtmap.realestate.common.core.domain.exchange.KttZhjbxxDO;
import cn.gtmap.realestate.exchange.core.dto.common.DataModelOld;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.KttZhjbxxOldDO;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcdjMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.KttZhjbxxMapper;
import cn.gtmap.realestate.exchange.service.impl.inf.gx.GxDataDbByPkServiceImpl;
import cn.gtmap.realestate.exchange.service.national.NationalAccessDataService;
import cn.gtmap.realestate.exchange.util.ClassHandleUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * 国家接入-宗海基本信息
 *
 * @author lst
 * @version 1.0, 2015/11/20
 */
public class NationalAccessDataZhjbxxImpl extends GxDataDbByPkServiceImpl implements NationalAccessDataService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NationalAccessDataZhjbxxImpl.class);

    @Autowired
    private KttZhjbxxMapper zhjbxxMapper;

    @Autowired
    private BdcdjMapper bdcdjMapper;

    @Override
    public DataModel getAccessDataModel(String ywh, DataModel dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            HashMap<String, String> map = new HashMap();
            List<KttZhjbxxDO> tempList = null;
            map.put("ywh", ywh);

            List<KttZhjbxxDO> kttZhjbxxList = zhjbxxMapper.queryKttZhjbxxList(map);
            if (CollectionUtils.isNotEmpty(kttZhjbxxList)) {
                tempList = new ArrayList();
                for (KttZhjbxxDO kttZhjbxx : kttZhjbxxList) {
                    String bsm = bdcdjMapper.getBsm().toString();
                    if (bsm.length() > 10) {
                        //作为唯一标识码固定为10位
                        bsm = bsm.substring(bsm.length() - 10);
                    } else {
                        bsm = String.format("%010d", Integer.valueOf(bsm));
                    }
                    kttZhjbxx.setBsm(Integer.valueOf(bsm));
                    tempList.add(kttZhjbxx);
                }
            }
            if (CollectionUtils.isNotEmpty(tempList)) {
                if (newDefault) {

                    for (KttZhjbxxDO kttZhjbxxDO : tempList) {
                        if (qxdmConvert) {
                            //需要根据qxdm进行对照
                            kttZhjbxxDO.setQxdm(commonService.bdcDmToDsfZdNvl("ACCESS_QXDM", "ACCESS", kttZhjbxxDO.getQxdm()));
                        }
                        ClassHandleUtil.setDefaultValueToNullField(kttZhjbxxDO);
                    }
                }
                setData(dataModel, tempList);
            }
        }
        return dataModel;
    }

    @Override
    public DataModelOld getAccessDataModelOld(String ywh, DataModelOld dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            HashMap<String, String> map = new HashMap();
            List<KttZhjbxxOldDO> tempList = null;
            map.put("ywh", ywh);
            List<KttZhjbxxOldDO> kttZhjbxxList = zhjbxxMapper.queryKttZhjbxxListOld(map);
            if (CollectionUtils.isNotEmpty(kttZhjbxxList)) {
                tempList = new ArrayList();
                for (KttZhjbxxOldDO kttZhjbxx : kttZhjbxxList) {
                    String bsm = bdcdjMapper.getBsm().toString();
                    if (bsm.length() > 10) {
                        //作为唯一标识码固定为10位
                        bsm = bsm.substring(bsm.length() - 10);
                    } else {
                        bsm = String.format("%010d", Integer.valueOf(bsm));
                    }
                    kttZhjbxx.setBsm(Integer.valueOf(bsm));
                    tempList.add(kttZhjbxx);
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
        return "KTT_ZHJBXX";
    }

    /**
     * @param dataModel
     * @return java.util.List<java.lang.Object>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 从 dataModel 中获取单表数据
     */
    @Override
    public List getData(DataModel dataModel) {
        return dataModel.getKttZhjbxxList();
    }

    @Override
    public List getDataOld(DataModelOld dataModel) {
        return dataModel.getKttZhjbxxList();
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
            dataModel.setKttZhjbxxList(dataList);
        }
    }

    @Override
    public void setDataOld(DataModelOld dataModel, List dataList) {
        if (dataModel != null) {
            dataModel.setKttZhjbxxList(dataList);
        }
    }
}
