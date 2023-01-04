package cn.gtmap.realestate.exchange.service.impl.national.data;

import cn.gtmap.realestate.exchange.core.domain.exchange.AccessData;
import cn.gtmap.realestate.exchange.core.domain.exchange.DataModel;
import cn.gtmap.realestate.exchange.core.domain.exchange.QlfQlDyiqDO;
import cn.gtmap.realestate.exchange.core.mapper.server.QlfQlDyiqMapper;
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

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 国家接入-地役权
 *
 * @author lst
 * @version 1.0, 2015/11/20
 */
public class NationalAccessDataDyiqImpl extends GxDataDbByPkServiceImpl implements NationalAccessDataService, QlfQlQsztService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NationalAccessDataDyiqImpl.class);

    @Autowired
    private QlfQlDyiqMapper qlfQlDyiqMapper;

    @Override
    public DataModel getAccessDataModel(String ywh, DataModel dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            HashMap<String, String> map = new HashMap();
            map.put("ywh", ywh);
            List<QlfQlDyiqDO> qlfQlDyiqList = qlfQlDyiqMapper.queryQlfQlDyiqList(map);
            if (CollectionUtils.isEmpty(qlfQlDyiqList)) {
                map.clear();
                map.put("yywh", ywh);
                qlfQlDyiqList = qlfQlDyiqMapper.queryQlfQlDyiqList(map);
            }

            if (CollectionUtils.isNotEmpty(qlfQlDyiqList)) {
                if (newDefault) {
                    for (QlfQlDyiqDO qlfQlDyiqDO : qlfQlDyiqList) {
                        ClassHandleUtil.setDefaultValueToNullField(qlfQlDyiqDO);

                    }
                }
                setData(dataModel, qlfQlDyiqList);
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
        return "QLF_QL_DYIQ";
    }

    /**
     * @param dataModel
     * @return java.util.List<java.lang.Object>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 从 dataModel 中获取单表数据
     */
    @Override
    public List getData(DataModel dataModel) {
        return dataModel.getQlfQlDyiqList();
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
            dataModel.setQlfQlDyiqList(dataList);
        }
    }

    @Override
    public void updateQsztByYwh(String ywh) {
        DataModel dataModel = new DataModel();
        dataModel = getAccessDataModel(ywh, dataModel);
        if (dataModel != null && CollectionUtils.isNotEmpty(dataModel.getQlfQlDyiqList())) {
            for (QlfQlDyiqDO qlfQlDyiqDO : dataModel.getQlfQlDyiqList()) {
                qlfQlDyiqDO.setUpdatetime(new Date());
            }
            saveData(dataModel);
        }
    }
}
