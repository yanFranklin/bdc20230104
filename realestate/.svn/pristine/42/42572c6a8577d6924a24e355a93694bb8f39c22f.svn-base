package cn.gtmap.realestate.exchange.service.impl.national.data;

import cn.gtmap.realestate.common.core.domain.exchange.AccessData;
import cn.gtmap.realestate.common.core.domain.exchange.DataModel;
import cn.gtmap.realestate.common.core.domain.exchange.QlfFwFdcqDzXmDO;
import cn.gtmap.realestate.exchange.core.dto.common.DataModelOld;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.QlfFwFdcqDzXmOldDO;
import cn.gtmap.realestate.exchange.core.mapper.server.QltFwFdcqDzMapper;
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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * 国家接入-房地产权_项目内多撞房屋 项目属性
 *
 * @author lst
 * @version 1.0, 2015/11/20
 */
@Service
public class NationalAccessDataFdcqDzXmImpl extends GxDataDbByPkServiceImpl implements NationalAccessDataService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NationalAccessDataFdcqDzXmImpl.class);

    @Autowired
    private QltFwFdcqDzMapper qltFwFdcqDzMapper;

    @Override
    public DataModel getAccessDataModel(String ywh, DataModel dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            HashMap<String, String> map = new HashMap();
            map.put("ywh", ywh);
            List<QlfFwFdcqDzXmDO> qlfFwFdcqDzXmList = qltFwFdcqDzMapper.queryQlfFwFdcqDzXmList(map);
            if (CollectionUtils.isEmpty(qlfFwFdcqDzXmList)) {
                map.clear();
                map.put("yywh", ywh);
                qlfFwFdcqDzXmList = qltFwFdcqDzMapper.queryQlfFwFdcqDzXmList(map);
            }
            if (CollectionUtils.isNotEmpty(qlfFwFdcqDzXmList)) {
                if (newDefault) {

                    for (QlfFwFdcqDzXmDO qlfFwFdcqDzXmDO : qlfFwFdcqDzXmList) {
                        if (qxdmConvert) {
                            //需要根据qxdm进行对照
                            qlfFwFdcqDzXmDO.setQxdm(commonService.bdcDmToDsfZdNvl("ACCESS_QXDM", "ACCESS", qlfFwFdcqDzXmDO.getQxdm()));
                        }
                        ClassHandleUtil.setDefaultValueToNullField(qlfFwFdcqDzXmDO);
                    }
                }
                setData(dataModel, qlfFwFdcqDzXmList);
            }
        }
        return dataModel;
    }

    @Override
    public DataModelOld getAccessDataModelOld(String ywh, DataModelOld dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            HashMap<String, String> map = new HashMap();
            map.put("ywh", ywh);
            List<QlfFwFdcqDzXmOldDO> qlfFwFdcqDzXmList = qltFwFdcqDzMapper.queryQlfFwFdcqDzXmListOld(map);
            if (CollectionUtils.isEmpty(qlfFwFdcqDzXmList)) {
                map.clear();
                map.put("yywh", ywh);
                qlfFwFdcqDzXmList = qltFwFdcqDzMapper.queryQlfFwFdcqDzXmListOld(map);
            }
            if (CollectionUtils.isNotEmpty(qlfFwFdcqDzXmList)) {
                setDataOld(dataModel, qlfFwFdcqDzXmList);
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
        return "QLF_FW_FDCQ_DZ_XM";
    }

    /**
     * @param dataModel
     * @return java.util.List<java.lang.Object>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 从 dataModel 中获取单表数据
     */
    @Override
    public List getData(DataModel dataModel) {
        return dataModel.getQlfFwFdcqDzXmList();
    }

    @Override
    public List getDataOld(DataModelOld dataModel) {
        return dataModel.getQlfFwFdcqDzXmList();
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
            dataModel.setQlfFwFdcqDzXmList(dataList);
        }
    }

    @Override
    public void setDataOld(DataModelOld dataModel, List dataList) {
        if (dataModel != null) {
            dataModel.setQlfFwFdcqDzXmList(dataList);
        }
    }
}
