package cn.gtmap.realestate.exchange.service.impl.national.data;

import cn.gtmap.realestate.common.core.domain.exchange.AccessData;
import cn.gtmap.realestate.common.core.domain.exchange.DataModel;
import cn.gtmap.realestate.common.core.domain.exchange.QlfFwFdcqQfsyqDO;
import cn.gtmap.realestate.exchange.core.dto.common.DataModelOld;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.QlfFwFdcqQfsyqOldDO;
import cn.gtmap.realestate.exchange.core.mapper.server.QlfFwFdcqQfsyqMapper;
import cn.gtmap.realestate.exchange.service.impl.inf.gx.GxDataDbByPkServiceImpl;
import cn.gtmap.realestate.exchange.service.national.NationalAccessDataService;
import cn.gtmap.realestate.exchange.service.national.QlfQlQsztService;
import cn.gtmap.realestate.exchange.util.ClassHandleUtil;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 国家接入-建筑物区分所有权业主共有部分信息
 *
 * @author lst
 * @version 1.0, 2015/11/20
 */
public class NationalAccessDataFdcqQfsyqImpl extends GxDataDbByPkServiceImpl implements NationalAccessDataService, QlfQlQsztService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NationalAccessDataFdcqQfsyqImpl.class);

    @Autowired
    private QlfFwFdcqQfsyqMapper qlfFwFdcqQfsyqMapper;

    @Override
    public DataModel getAccessDataModel(String ywh, DataModel dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            Map<String, String> map = Maps.newHashMap();
            map.put("ywh", ywh);
            List<QlfFwFdcqQfsyqDO> qlfFwFdcqQfsyqList = qlfFwFdcqQfsyqMapper.queryQlfFwFdcqQfsyqList(map);
            if (CollectionUtils.isEmpty(qlfFwFdcqQfsyqList)) {
                map.clear();
                map.put("yywh", ywh);
                qlfFwFdcqQfsyqList = qlfFwFdcqQfsyqMapper.queryQlfFwFdcqQfsyqList(map);
            }
            if (CollectionUtils.isNotEmpty(qlfFwFdcqQfsyqList)) {
                if (newDefault) {

                    for (QlfFwFdcqQfsyqDO qlfFwFdcqQfsyqDO : qlfFwFdcqQfsyqList) {
                        if (qxdmConvert) {
                            //需要根据qxdm进行对照
                            qlfFwFdcqQfsyqDO.setQxdm(commonService.bdcDmToDsfZdNvl("ACCESS_QXDM", "ACCESS", qlfFwFdcqQfsyqDO.getQxdm()));
                        }
                        ClassHandleUtil.setDefaultValueToNullField(qlfFwFdcqQfsyqDO);
                    }
                }
                setData(dataModel, qlfFwFdcqQfsyqList);
            }
        }
        return dataModel;
    }

    @Override
    public DataModelOld getAccessDataModelOld(String ywh, DataModelOld dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            Map<String, String> map = Maps.newHashMap();
            map.put("ywh", ywh);
            List<QlfFwFdcqQfsyqOldDO> qlfFwFdcqQfsyqList = qlfFwFdcqQfsyqMapper.queryQlfFwFdcqQfsyqListOld(map);
            if (CollectionUtils.isEmpty(qlfFwFdcqQfsyqList)) {
                map.clear();
                map.put("yywh", ywh);
                qlfFwFdcqQfsyqList = qlfFwFdcqQfsyqMapper.queryQlfFwFdcqQfsyqListOld(map);
            }
            if (CollectionUtils.isNotEmpty(qlfFwFdcqQfsyqList)) {

                setDataOld(dataModel, qlfFwFdcqQfsyqList);
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
        return "QLF_FW_FDCQ_QFSYQ";
    }

    /**
     * @param dataModel
     * @return java.util.List<java.lang.Object>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 从 dataModel 中获取单表数据
     */
    @Override
    public List getData(DataModel dataModel) {
        return dataModel.getQlfFwFdcqQfsyqList();
    }

    @Override
    public List getDataOld(DataModelOld dataModel) {
        return dataModel.getQlfFwFdcqQfsyqList();
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
            dataModel.setQlfFwFdcqQfsyqList(dataList);
        }
    }

    @Override
    public void setDataOld(DataModelOld dataModel, List dataList) {
        if (dataModel != null) {
            dataModel.setQlfFwFdcqQfsyqList(dataList);
        }
    }

    @Override
    public void updateQsztByYwh(String ywh) {
        DataModel dataModel = new DataModel();
        dataModel = getAccessDataModel(ywh, dataModel);
        if (dataModel != null && CollectionUtils.isNotEmpty(dataModel.getQlfFwFdcqQfsyqList())) {
            for (QlfFwFdcqQfsyqDO qlfFwFdcqQfsyqDO : dataModel.getQlfFwFdcqQfsyqList()) {
                qlfFwFdcqQfsyqDO.setUpdatetime(new Date());
            }
            saveData(dataModel);
        }
    }
}
