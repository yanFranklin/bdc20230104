package cn.gtmap.realestate.exchange.service.impl.national.data;

import cn.gtmap.realestate.common.core.domain.exchange.DataModel;
import cn.gtmap.realestate.common.core.domain.exchange.ZttGyQlrDO;
import cn.gtmap.realestate.exchange.core.dto.common.DataModelOld;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.ZttGyQlrOldDO;
import cn.gtmap.realestate.exchange.core.mapper.server.ZttGyQlrMapper;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 国家接入-权利人
 *
 * @author lst
 * @version 1.0, 2015/11/20
 */
@Service
public class NationalAccessDataQlrImpl extends GxDataDbByFkServiceImpl implements NationalAccessDataService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NationalAccessDataQlrImpl.class);

    @Autowired
    private ZttGyQlrMapper zttGyQlrMapper;

    @Override
    public DataModel getAccessDataModel(String ywh, DataModel dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            HashMap<String, String> map = new HashMap();
            map.put("ywh", ywh);
            List<ZttGyQlrDO> zttGyQlrList = zttGyQlrMapper.queryZttGyQlrList(map);
            if (CollectionUtils.isNotEmpty(zttGyQlrList)) {
                //共享库默认生成时间不生效
                zttGyQlrList.forEach(zttGyQlrDO -> {
                    if (qxdmConvert) {
                        //需要根据qxdm进行对照
                        zttGyQlrDO.setQxdm(commonService.bdcDmToDsfZdNvl("ACCESS_QXDM", "ACCESS", zttGyQlrDO.getQxdm()));
                    }
                    if (zttGyQlrDO.getCreatetime() == null) {
                        zttGyQlrDO.setCreatetime(new Date());
                        if (newDefault) {

                            ClassHandleUtil.setDefaultValueToNullField(zttGyQlrDO);
                        }
                    }
                });
                dataModel.setZttGyQlrList(zttGyQlrList);
                setData(dataModel, zttGyQlrList);
            }
        }
        return dataModel;
    }

    @Override
    public DataModelOld getAccessDataModelOld(String ywh, DataModelOld dataModel) {
        LOGGER.info("老版上报获取权利人！");
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            HashMap<String, String> map = new HashMap();
            map.put("ywh", ywh);
            List<ZttGyQlrOldDO> zttGyQlrList = zttGyQlrMapper.queryZttGyQlrListOld(map);
            if (CollectionUtils.isNotEmpty(zttGyQlrList)) {
                //共享库默认生成时间不生效
                zttGyQlrList.forEach(zttGyQlrDO -> {
                    if (zttGyQlrDO.getCreatetime() == null) {
                        zttGyQlrDO.setCreatetime(new Date());
                    }
                });
                dataModel.setZttGyQlrList(zttGyQlrList);
                setDataOld(dataModel, zttGyQlrList);
            }
        }
        return dataModel;
    }

    @Override
    public <T> List<T> getAccessData(String ywh) {
        return Collections.emptyList();
    }

    @Override
    public String getAccessDataTagName() {
        return "ZTT_GY_QLR";
    }

    /**
     * @param dataModel
     * @return java.util.List<java.lang.Object>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 从 dataModel 中获取单表数据
     */
    @Override
    public List getData(DataModel dataModel) {
        return dataModel.getZttGyQlrList();
    }

    @Override
    public List getDataOld(DataModelOld dataModel) {
        return dataModel.getZttGyQlrList();
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
            dataModel.setZttGyQlrList(dataList);
        }
    }

    @Override
    public void setDataOld(DataModelOld dataModel, List dataList) {
        if (dataModel != null) {
            dataModel.setZttGyQlrList(dataList);
        }
    }
}
