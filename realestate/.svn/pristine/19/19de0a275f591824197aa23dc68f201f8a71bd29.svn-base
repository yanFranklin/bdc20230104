package cn.gtmap.realestate.exchange.service.impl.national.data;

import cn.gtmap.realestate.common.core.domain.exchange.AccessData;
import cn.gtmap.realestate.common.core.domain.exchange.DataModel;
import cn.gtmap.realestate.common.core.domain.exchange.QlfQlJsydsyqDO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.core.dto.common.DataModelOld;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.QlfQlJsydsyqOldDO;
import cn.gtmap.realestate.exchange.core.mapper.server.QlfQlJsydsyqMapper;
import cn.gtmap.realestate.exchange.service.impl.inf.gx.GxDataDbByPkServiceImpl;
import cn.gtmap.realestate.exchange.service.national.NationalAccessDataService;
import cn.gtmap.realestate.exchange.service.national.QlfQlQsztService;
import cn.gtmap.realestate.exchange.util.ClassHandleUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 国家接入-建设用地、宅基地使用权
 *
 * @author lst
 * @version 1.0, 2015/11/20
 */
public class NationalAccessDataJsydsyqImpl extends GxDataDbByPkServiceImpl implements NationalAccessDataService, QlfQlQsztService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NationalAccessDataJsydsyqImpl.class);

    @Autowired
    private QlfQlJsydsyqMapper jsydsyqMapperImpl;

    @Override
    public DataModel getAccessDataModel(String ywh, DataModel dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            HashMap<String, String> map = new HashMap();
            map.put("ywh", ywh);
            List<QlfQlJsydsyqDO> qlfQlJsydsyqList = jsydsyqMapperImpl.queryQlfQlJsydsyqList(map);
            if (CollectionUtils.isEmpty(qlfQlJsydsyqList)) {
                map.clear();
                map.put("yywh", ywh);
                qlfQlJsydsyqList = jsydsyqMapperImpl.queryQlfQlJsydsyqList(map);
            }
            if (CollectionUtils.isNotEmpty(qlfQlJsydsyqList)) {
                List<QlfQlJsydsyqDO> qlfQlJsydsyqDOList = Collections.singletonList(qlfQlJsydsyqList.get(0));
                if (CollectionUtils.size(qlfQlJsydsyqList) > 1) {
                    //查询到多条权利数据发出预警
                    sendMsg(ywh);
                }
                if (newDefault) {

                    for (QlfQlJsydsyqDO qlfQlJsydsyqDO : qlfQlJsydsyqDOList) {
                        if (qxdmConvert) {
                            //需要根据qxdm进行对照
                            qlfQlJsydsyqDO.setQxdm(commonService.bdcDmToDsfZdNvl("ACCESS_QXDM", "ACCESS", qlfQlJsydsyqDO.getQxdm()));
                        }
                        if (null != qlfQlJsydsyqDO.getQdjg()) {
                            BigDecimal b = new BigDecimal(qlfQlJsydsyqDO.getQdjg());
                            Double doubleValue = b.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
                            qlfQlJsydsyqDO.setQdjg(doubleValue);
                        }

                        if (turnOnReplacezf && StringUtils.isNotBlank(qlfQlJsydsyqDO.getBdcqzh())) {
                            qlfQlJsydsyqDO.setBdcqzh(qlfQlJsydsyqDO.getBdcqzh().replaceAll(CommonConstantUtils.ZF_YW_DH, replaceZf));
                        }
                        ClassHandleUtil.setDefaultValueToNullField(qlfQlJsydsyqDO);
                    }
                }
                setData(dataModel, qlfQlJsydsyqDOList);
            }
        }
        return dataModel;
    }

    @Override
    public DataModelOld getAccessDataModelOld(String ywh, DataModelOld dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            HashMap<String, String> map = new HashMap();
            map.put("ywh", ywh);
            List<QlfQlJsydsyqOldDO> qlfQlJsydsyqList = jsydsyqMapperImpl.queryQlfQlJsydsyqListOld(map);
            if (CollectionUtils.isEmpty(qlfQlJsydsyqList)) {
                map.clear();
                map.put("yywh", ywh);
                qlfQlJsydsyqList = jsydsyqMapperImpl.queryQlfQlJsydsyqListOld(map);
            }
            if (CollectionUtils.isNotEmpty(qlfQlJsydsyqList)) {
                setDataOld(dataModel, qlfQlJsydsyqList);
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
        return "QLF_QL_JSYDSYQ";
    }

    /**
     * @param dataModel
     * @return java.util.List<java.lang.Object>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 从 dataModel 中获取单表数据
     */
    @Override
    public List getData(DataModel dataModel) {
        return dataModel.getQlfQlJsydsyqList();
    }

    @Override
    public List getDataOld(DataModelOld dataModel) {
        return dataModel.getQlfQlJsydsyqList();
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
            dataModel.setQlfQlJsydsyqList(dataList);
        }
    }

    @Override
    public void setDataOld(DataModelOld dataModel, List dataList) {
        if (dataModel != null) {
            dataModel.setQlfQlJsydsyqList(dataList);
        }
    }

    @Override
    public void updateQsztByYwh(String ywh) {
        DataModel dataModel = new DataModel();
        dataModel = getAccessDataModel(ywh, dataModel);
        if (dataModel != null && CollectionUtils.isNotEmpty(dataModel.getQlfQlJsydsyqList())) {
            for (QlfQlJsydsyqDO qlfQlJsydsyqDO : dataModel.getQlfQlJsydsyqList()) {
                qlfQlJsydsyqDO.setUpdatetime(new Date());
            }
            saveData(dataModel);
        }
    }
}
