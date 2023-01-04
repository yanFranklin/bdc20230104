package cn.gtmap.realestate.exchange.service.impl.national.data;

import cn.gtmap.realestate.exchange.core.domain.exchange.AccessData;
import cn.gtmap.realestate.exchange.core.domain.exchange.DataModel;
import cn.gtmap.realestate.exchange.core.domain.exchange.QlfQlQtxgqlDO;
import cn.gtmap.realestate.exchange.core.mapper.server.QlfQlQtxgqlMapper;
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
 * 国家接入-其他相关权利
 *
 * @author lst
 * @version 1.0, 2015/11/20
 */
public class NationalAccessDataQtxgqlImpl extends GxDataDbByPkServiceImpl implements NationalAccessDataService, QlfQlQsztService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NationalAccessDataQtxgqlImpl.class);

    @Autowired
    private QlfQlQtxgqlMapper qlfQlQtxgqlMapper;

    @Override
    public DataModel getAccessDataModel(String ywh, DataModel dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            Map<String, String> map = Maps.newHashMap();
            map.put("ywh", ywh);
            List<QlfQlQtxgqlDO> qlfQlQtxgqlList = qlfQlQtxgqlMapper.queryQlfQlQtxgqlList(map);
            if(CollectionUtils.isEmpty(qlfQlQtxgqlList)){
                map.clear();
                map.put("yywh",ywh);
                qlfQlQtxgqlList = qlfQlQtxgqlMapper.queryQlfQlQtxgqlList(map);
            }
            if (CollectionUtils.isNotEmpty(qlfQlQtxgqlList)) {
                if (newDefault) {

                    for (QlfQlQtxgqlDO qlfQlQtxgqlDO : qlfQlQtxgqlList) {
                        ClassHandleUtil.setDefaultValueToNullField(qlfQlQtxgqlDO);
                    }
                }
                setData(dataModel, qlfQlQtxgqlList);
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
        return "QLF_QL_QTXGQL";
    }

    /**
     * @param dataModel
     * @return java.util.List<java.lang.Object>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 从 dataModel 中获取单表数据
     */
    @Override
    public List getData(DataModel dataModel) {
        return dataModel.getQlfQlQtxgqlList();
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
            dataModel.setQlfQlQtxgqlList(dataList);
        }
    }

    @Override
    public void updateQsztByYwh(String ywh) {
        DataModel dataModel = new DataModel();
        dataModel = getAccessDataModel(ywh, dataModel);
        if (dataModel != null && CollectionUtils.isNotEmpty(dataModel.getQlfQlQtxgqlList())) {
            for (QlfQlQtxgqlDO qlfQlQtxgqlDO : dataModel.getQlfQlQtxgqlList()) {
                qlfQlQtxgqlDO.setUpdatetime(new Date());
            }
            saveData(dataModel);
        }
    }
}
