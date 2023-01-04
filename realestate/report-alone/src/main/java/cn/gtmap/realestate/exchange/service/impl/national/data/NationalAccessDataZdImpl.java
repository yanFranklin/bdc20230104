package cn.gtmap.realestate.exchange.service.impl.national.data;

import cn.gtmap.realestate.exchange.core.domain.exchange.AccessData;
import cn.gtmap.realestate.exchange.core.domain.exchange.DataModel;
import cn.gtmap.realestate.exchange.core.domain.exchange.ZdKDO;
import cn.gtmap.realestate.exchange.core.mapper.server.ZdkSqlMapper;
import cn.gtmap.realestate.exchange.service.impl.inf.gx.GxDataDbByFkServiceImpl;
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
import java.util.Map;

/**
 * 国家接入-宗地空间属性
 *
 * @author lst
 * @version 1.0, 2015/11/20
 */
@Service
public class NationalAccessDataZdImpl extends GxDataDbByFkServiceImpl implements NationalAccessDataService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NationalAccessDataZdImpl.class);

    @Autowired
    private ZdkSqlMapper zdkSqlMapper;

    @Override
    public DataModel getAccessDataModel(String ywh, DataModel dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            Map<String, String> map = new HashMap();
            map.put("ywh", ywh);
            List<ZdKDO> zdKList = zdkSqlMapper.queryZdKList(map);
            if (CollectionUtils.isNotEmpty(zdKList)) {
                if (newDefault) {

                    for (ZdKDO zdKDO : zdKList) {
                        ClassHandleUtil.setDefaultValueToNullField(zdKDO);
                    }
                }
                setData(dataModel, zdKList);
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
        return "ZD_K_103";
    }

    /**
     * @param dataModel
     * @return java.util.List<java.lang.Object>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 从 dataModel 中获取单表数据
     */
    @Override
    public List getData(DataModel dataModel) {
        return dataModel.getZdKList();
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
            dataModel.setZdKList(dataList);
        }
    }

}
