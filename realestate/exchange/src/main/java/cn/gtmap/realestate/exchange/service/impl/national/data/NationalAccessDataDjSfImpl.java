package cn.gtmap.realestate.exchange.service.impl.national.data;

import cn.gtmap.realestate.common.core.domain.exchange.AccessData;
import cn.gtmap.realestate.common.core.domain.exchange.DataModel;
import cn.gtmap.realestate.common.core.domain.exchange.DjfDjSfDO;
import cn.gtmap.realestate.exchange.core.dto.common.DataModelOld;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.DjfDjSfOldDO;
import cn.gtmap.realestate.exchange.core.mapper.server.DjfDjSfMapper;
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

/**
 * 国家接入-登记收费信息
 *
 * @author lst
 * @version 1.0, 2015/11/20
 */
@Service
public class NationalAccessDataDjSfImpl extends GxDataDbByFkServiceImpl implements NationalAccessDataService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NationalAccessDataDjSfImpl.class);

    @Autowired
    private DjfDjSfMapper djfDjSfMapper;

    @Override
    public DataModel getAccessDataModel(String ywh, DataModel dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            HashMap<String, String> map = new HashMap();
            map.put("ywh", ywh);
            /**
             * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
             * @description 暂时不需要用bdcdyh做查询条件 后续需要可再改
             */
            List<DjfDjSfDO> djfDjSfList = djfDjSfMapper.queryDjfDjSfList(map);
            if (CollectionUtils.isNotEmpty(djfDjSfList)) {
                if (newDefault) {
                    for (DjfDjSfDO djfDjSfDO : djfDjSfList) {
                        if (qxdmConvert) {
                            //需要根据qxdm进行对照
                            djfDjSfDO.setQxdm(commonService.bdcDmToDsfZdNvl("ACCESS_QXDM", "ACCESS", djfDjSfDO.getQxdm()));
                        }
                        ClassHandleUtil.setDefaultValueToNullField(djfDjSfDO);
                    }
                }
                setData(dataModel, djfDjSfList);
            }
        }
        return dataModel;
    }

    @Override
    public DataModelOld getAccessDataModelOld(String ywh, DataModelOld dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            HashMap<String, String> map = new HashMap();
            map.put("ywh", ywh);
            /**
             * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
             * @description 暂时不需要用bdcdyh做查询条件 后续需要可再改
             */
            List<DjfDjSfOldDO> djfDjSfList = djfDjSfMapper.queryDjfDjSfListOld(map);
            if (CollectionUtils.isNotEmpty(djfDjSfList)) {
                setDataOld(dataModel, djfDjSfList);
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
        return "DJF_DJ_SF";
    }

    /**
     * @param dataModel
     * @return java.util.List<java.lang.Object>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 从 dataModel 中获取单表数据
     */
    @Override
    public List getData(DataModel dataModel) {
        return dataModel.getDjfDjSfList();
    }

    @Override
    public List getDataOld(DataModelOld dataModel) {
        return dataModel.getDjfDjSfList();
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
            dataModel.setDjfDjSfList(dataList);
        }
    }

    @Override
    public void setDataOld(DataModelOld dataModel, List dataList) {
        if (dataModel != null) {
            dataModel.setDjfDjSfList(dataList);
        }
    }
}
