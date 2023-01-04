package cn.gtmap.realestate.exchange.service.impl.national.data;

import cn.gtmap.realestate.exchange.core.domain.exchange.AccessData;
import cn.gtmap.realestate.exchange.core.domain.exchange.DataModel;
import cn.gtmap.realestate.exchange.core.domain.exchange.QltFwFdcqYzDO;
import cn.gtmap.realestate.exchange.core.mapper.server.QltFwFdcqYzMapper;
import cn.gtmap.realestate.exchange.service.impl.inf.gx.GxDataDbByPkServiceImpl;
import cn.gtmap.realestate.exchange.service.national.NationalAccessDataService;
import cn.gtmap.realestate.exchange.service.national.QlfQlQsztService;
import cn.gtmap.realestate.exchange.util.ClassHandleUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 国家接入-房地产权_独幢、层、套、间房屋信息
 *
 * @author lst
 * @version 1.0, 2015/11/20
 */
@Service
public class NationalAccessDataFdcqYzImpl extends GxDataDbByPkServiceImpl implements NationalAccessDataService, QlfQlQsztService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NationalAccessDataFdcqYzImpl.class);

    @Autowired
    private QltFwFdcqYzMapper qltFwFdcqYzMapper;

    @Override
    public DataModel getAccessDataModel(String ywh, DataModel dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            HashMap<String, String> map = new HashMap();
            map.put("ywh", ywh);
            List<QltFwFdcqYzDO> qltFwFdcqYzList = qltFwFdcqYzMapper.queryQltFwFdcqYzList(map);
            if (CollectionUtils.isEmpty(qltFwFdcqYzList)) {
                map.clear();
                map.put("yywh", ywh);
                qltFwFdcqYzList = qltFwFdcqYzMapper.queryQltFwFdcqYzList(map);
            }
            if (CollectionUtils.isNotEmpty(qltFwFdcqYzList)) {
                if (newDefault) {

                    for (QltFwFdcqYzDO qltFwFdcqYzDO : qltFwFdcqYzList) {
                        ClassHandleUtil.setDefaultValueToNullField(qltFwFdcqYzDO);
                    }
                }
                setData(dataModel, qltFwFdcqYzList);
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
        return "QLT_FW_FDCQ_YZ";
    }

    /**
     * @param dataModel
     * @return java.util.List<java.lang.Object>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 从 dataModel 中获取单表数据
     */
    @Override
    public List getData(DataModel dataModel) {
        return dataModel.getQltFwFdcqYzList();
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
            dataModel.setQltFwFdcqYzList(dataList);
        }
    }

    @Override
    public void updateQsztByYwh(String ywh) {
        DataModel dataModel = new DataModel();
        dataModel = getAccessDataModel(ywh, dataModel);
        if (dataModel != null && CollectionUtils.isNotEmpty(dataModel.getQltFwFdcqYzList())) {
            for (QltFwFdcqYzDO qltFwFdcqYzDO : dataModel.getQltFwFdcqYzList()) {
                qltFwFdcqYzDO.setUpdatetime(new Date());
            }
            saveData(dataModel);
        }
    }
}
