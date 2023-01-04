package cn.gtmap.realestate.exchange.service.impl.national.data;

import cn.gtmap.realestate.exchange.core.domain.exchange.AccessData;
import cn.gtmap.realestate.exchange.core.domain.exchange.DataModel;
import cn.gtmap.realestate.exchange.core.domain.exchange.QlfQlHysyqDO;
import cn.gtmap.realestate.exchange.core.mapper.server.QlfQlHysyqMapper;
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
 * 国家接入-海域(无居民海岛)使用权
 *
 * @author lst
 * @version 1.0, 2015/11/20
 */
public class NationalAccessDataHysyqImpl extends GxDataDbByPkServiceImpl implements NationalAccessDataService, QlfQlQsztService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NationalAccessDataHysyqImpl.class);

    @Autowired
    private QlfQlHysyqMapper qlfQlHysyqMapper;

    @Override
    public DataModel getAccessDataModel(String ywh, DataModel dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            Map<String, String> map = Maps.newHashMap();
            map.put("ywh", ywh);
            List<QlfQlHysyqDO> qlfQlHysyqList = qlfQlHysyqMapper.queryQlfQlHysyqList(map);
            if(CollectionUtils.isEmpty(qlfQlHysyqList)){
                map.clear();
                map.put("yywh",ywh);
                qlfQlHysyqList = qlfQlHysyqMapper.queryQlfQlHysyqList(map);
            }
            if (CollectionUtils.isNotEmpty(qlfQlHysyqList)) {
                if (newDefault) {

                    for (QlfQlHysyqDO qlfQlHysyqDO : qlfQlHysyqList) {
                        ClassHandleUtil.setDefaultValueToNullField(qlfQlHysyqDO);
                    }
                }
                setData(dataModel, qlfQlHysyqList);
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
        return "QLF_QL_HYSYQ";
    }

    /**
     * @param dataModel
     * @return java.util.List<java.lang.Object>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 从 dataModel 中获取单表数据
     */
    @Override
    public List getData(DataModel dataModel) {
        return dataModel.getQlfQlHysyqList();
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
            dataModel.setQlfQlHysyqList(dataList);
        }
    }

    @Override
    public void updateQsztByYwh(String ywh) {
        DataModel dataModel = new DataModel();
        dataModel = getAccessDataModel(ywh, dataModel);
        if (dataModel != null && CollectionUtils.isNotEmpty(dataModel.getQlfQlHysyqList())) {
            for (QlfQlHysyqDO qlfQlHysyqDO : dataModel.getQlfQlHysyqList()) {
                qlfQlHysyqDO.setUpdatetime(new Date());
            }
            saveData(dataModel);
        }
    }
}
