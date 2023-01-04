package cn.gtmap.realestate.exchange.service.impl.national.data;

import cn.gtmap.realestate.exchange.core.domain.exchange.AccessData;
import cn.gtmap.realestate.exchange.core.domain.exchange.DataModel;
import cn.gtmap.realestate.exchange.core.domain.exchange.DjfDjDb;
import cn.gtmap.realestate.exchange.core.mapper.server.DjfDjDbxxMapper;
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
 * 国家接入-登记登簿信息
 *
 * @author hj
 * @version 1.0, 2015/11/20
 */
@Service
public class NationalAccessDataDbxxImpl extends GxDataDbByPkServiceImpl implements NationalAccessDataService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NationalAccessDataDbxxImpl.class);

    @Autowired
    private DjfDjDbxxMapper djfDjDbxxMapper;

    @Override
    public DataModel getAccessDataModel(String ywh, DataModel dataModel) {
        LOGGER.info("组织登簿数据！");
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            HashMap<String, String> map = new HashMap();
            map.put("ywh", ywh);
            LOGGER.info("开始查询登簿数据！");
            List<DjfDjDb> djfDjDbs = djfDjDbxxMapper.queryDjfDbxxList(map);
            LOGGER.info("登簿数据：{}", djfDjDbs.toString());
            if (CollectionUtils.isNotEmpty(djfDjDbs)) {
                if (newDefault) {
                    for (DjfDjDb djfDjDb : djfDjDbs) {
                        ClassHandleUtil.setDefaultValueToNullField(djfDjDb);
                    }
                }
                setData(dataModel, djfDjDbs);
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
        return "DJF_DJ_DB";
    }

    /**
     * @param dataModel
     * @return java.util.List<java.lang.Object>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 从 dataModel 中获取单表数据
     */
    @Override
    public List getData(DataModel dataModel) {
        return dataModel.getDjfDjDbList();
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
            dataModel.setDjfDjDbList(dataList);
        }
    }

}
