package cn.gtmap.realestate.exchange.service.impl.national.data;

import cn.gtmap.realestate.exchange.core.domain.exchange.AccessData;
import cn.gtmap.realestate.exchange.core.domain.exchange.DataModel;
import cn.gtmap.realestate.exchange.core.domain.exchange.DjfDjSqrDO;
import cn.gtmap.realestate.exchange.core.mapper.server.DjfDjSqrMapper;
import cn.gtmap.realestate.exchange.service.impl.inf.gx.GxDataDbByFkServiceImpl;
import cn.gtmap.realestate.exchange.service.national.NationalAccessDataService;
import cn.gtmap.realestate.exchange.util.ClassHandleUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 国家接入-申请人属性信息
 *
 * @author lst
 * @version 1.0, 2015/11/20
 */
@Service
public class NationalAccessDataDjSqrImpl extends GxDataDbByFkServiceImpl implements NationalAccessDataService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NationalAccessDataDjSqrImpl.class);

    @Autowired
    private DjfDjSqrMapper djfDjSqrMapper;

    @Value("${access.newAccess:true}")
    private boolean newAccessModel;

    @Override
    public DataModel getAccessDataModel(String ywh, DataModel dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            HashMap<String, String> map = new HashMap();
            map.put("ywh", ywh);
            List<DjfDjSqrDO> djfDjSqrList = djfDjSqrMapper.queryDjfDjSqrList(map);
            if (CollectionUtils.isNotEmpty(djfDjSqrList)) {
                djfDjSqrList.forEach(djfDjSqrDO -> {
                    if (djfDjSqrDO.getCreatetime() == null) {
                        djfDjSqrDO.setCreatetime(new Date());
                    }
                    if (newDefault) {
                        ClassHandleUtil.setDefaultValueToNullField(djfDjSqrDO);
                    }
                });
                setData(dataModel, djfDjSqrList);
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
        if (newAccessModel) {
            return "DJF_DJ_SQ";

        } else {
            return "DJF_DJ_SQR";
        }
    }

    /**
     * @param dataModel
     * @return java.util.List<java.lang.Object>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 从 dataModel 中获取单表数据
     */
    @Override
    public List getData(DataModel dataModel) {
        return dataModel.getDjfDjSqrList();
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
            dataModel.setDjfDjSqrList(dataList);
        }
    }

}
