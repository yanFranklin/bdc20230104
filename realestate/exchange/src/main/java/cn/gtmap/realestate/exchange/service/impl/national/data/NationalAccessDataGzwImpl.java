package cn.gtmap.realestate.exchange.service.impl.national.data;

import cn.gtmap.realestate.common.core.domain.exchange.AccessData;
import cn.gtmap.realestate.common.core.domain.exchange.DataModel;
import cn.gtmap.realestate.common.core.domain.exchange.KttGzwDO;
import cn.gtmap.realestate.exchange.core.dto.common.DataModelOld;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.KttGzwOldDO;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcdjMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.KttGzwMapper;
import cn.gtmap.realestate.exchange.service.impl.inf.gx.GxDataDbByPkServiceImpl;
import cn.gtmap.realestate.exchange.service.national.NationalAccessDataService;
import cn.gtmap.realestate.exchange.util.ClassHandleUtil;
import org.apache.commons.collections.CollectionUtils;
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
 * 国家接入-构筑物
 *
 * @author lst
 * @version 1.0, 2015/11/20
 */
public class NationalAccessDataGzwImpl extends GxDataDbByPkServiceImpl implements NationalAccessDataService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NationalAccessDataGzwImpl.class);

    @Autowired
    private KttGzwMapper kttGzwMapper;

    @Autowired
    private BdcdjMapper bdcdjMapper;

    @Override
    public DataModel getAccessDataModel(String ywh, DataModel dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            Map<String, String> map = new HashMap();
            map.put("ywh", ywh);

            List<KttGzwDO> kttGzwList = kttGzwMapper.queryKttGzwList(map);
            if (CollectionUtils.isNotEmpty(kttGzwList)) {
                for (KttGzwDO kttGzw : kttGzwList) {
                    String bsm = bdcdjMapper.getBsm().toString();
                    if (bsm.length() > 10) {
                        //作为唯一标识码固定为10位
                        bsm = bsm.substring(bsm.length() - 10);
                    } else {
                        bsm = String.format("%010d", Integer.valueOf(bsm));
                    }
                    kttGzw.setBsm(Integer.valueOf(bsm));
                    if (newDefault) {
                        ClassHandleUtil.setDefaultValueToNullField(kttGzw);
                    }
                }
                setData(dataModel, kttGzwList);
            }
        }
        return dataModel;
    }

    @Override
    public DataModelOld getAccessDataModelOld(String ywh, DataModelOld dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            Map<String, String> map = new HashMap();
            map.put("ywh", ywh);

            List<KttGzwOldDO> kttGzwList = kttGzwMapper.queryKttGzwListOld(map);
            if (CollectionUtils.isNotEmpty(kttGzwList)) {
                for (KttGzwOldDO kttGzw : kttGzwList) {
                    String bsm = bdcdjMapper.getBsm().toString();
                    if (bsm.length() > 10) {
                        //作为唯一标识码固定为10位
                        bsm = bsm.substring(bsm.length() - 10);
                    } else {
                        bsm = String.format("%010d", Integer.valueOf(bsm));
                    }
                    kttGzw.setBsm(Integer.valueOf(bsm));
                }
                setDataOld(dataModel, kttGzwList);
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
        return "KTT_GZW";
    }

    /**
     * @param dataModel
     * @return java.util.List<java.lang.Object>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 从 dataModel 中获取单表数据
     */
    @Override
    public List getData(DataModel dataModel) {
        return dataModel.getKttGzwList();
    }

    @Override
    public List getDataOld(DataModelOld dataModel) {
        return dataModel.getKttGzwList();
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
            dataModel.setKttGzwList(dataList);
        }
    }

    @Override
    public void setDataOld(DataModelOld dataModel, List dataList) {
        if (dataModel != null) {
            dataModel.setKttGzwList(dataList);
        }
    }
}
