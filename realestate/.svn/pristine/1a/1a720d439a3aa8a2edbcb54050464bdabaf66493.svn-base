package cn.gtmap.realestate.exchange.service.impl.national.data;

import cn.gtmap.realestate.common.core.domain.exchange.AccessData;
import cn.gtmap.realestate.common.core.domain.exchange.DataModel;
import cn.gtmap.realestate.common.core.domain.exchange.KtfZhYhzkDO;
import cn.gtmap.realestate.exchange.core.dto.common.DataModelOld;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.KtfZhYhzkOldDO;
import cn.gtmap.realestate.exchange.core.mapper.server.KtfZhYhzkMapper;
import cn.gtmap.realestate.exchange.service.impl.inf.gx.GxDataDbByFkServiceImpl;
import cn.gtmap.realestate.exchange.service.national.NationalAccessDataService;
import cn.gtmap.realestate.exchange.util.ClassHandleUtil;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 国家接入-用海状况
 *
 * @author lst
 * @version 1.0, 2015/11/20
 */
public class NationalAccessDataYhzkImpl extends GxDataDbByFkServiceImpl implements NationalAccessDataService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NationalAccessDataYhzkImpl.class);

    @Autowired
    private KtfZhYhzkMapper ktfZhYhzkMapper;

    @Override
    public DataModel getAccessDataModel(String ywh, DataModel dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            Map<String, String> map = Maps.newHashMap();
            map.put("ywh", ywh);
            List<KtfZhYhzkDO> ktfZhYhzkList = ktfZhYhzkMapper.queryKtfZhYhzkList(map);
            if (CollectionUtils.isNotEmpty(ktfZhYhzkList)) {
                if (newDefault) {

                    for (KtfZhYhzkDO ktfZhYhzkDO : ktfZhYhzkList) {
                        if (qxdmConvert) {
                            //需要根据qxdm进行对照
                            ktfZhYhzkDO.setQxdm(commonService.bdcDmToDsfZdNvl("ACCESS_QXDM", "ACCESS", ktfZhYhzkDO.getQxdm()));
                        }
                        ClassHandleUtil.setDefaultValueToNullField(ktfZhYhzkDO);
                    }
                }
                setData(dataModel, ktfZhYhzkList);
            }
        }
        return dataModel;
    }

    @Override
    public DataModelOld getAccessDataModelOld(String ywh, DataModelOld dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            Map<String, String> map = Maps.newHashMap();
            map.put("ywh", ywh);
            List<KtfZhYhzkOldDO> ktfZhYhzkList = ktfZhYhzkMapper.queryKtfZhYhzkListOld(map);
            if (CollectionUtils.isNotEmpty(ktfZhYhzkList)) {
                setDataOld(dataModel, ktfZhYhzkList);
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
        return "KTF_ZH_YHZK";
    }

    /**
     * @param dataModel
     * @return java.util.List<java.lang.Object>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 从 dataModel 中获取单表数据
     */
    @Override
    public List getData(DataModel dataModel) {
        return dataModel.getKtfZhYhzkList();
    }

    @Override
    public List getDataOld(DataModelOld dataModel) {
        return dataModel.getKtfZhYhzkList();
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
            dataModel.setKtfZhYhzkList(dataList);
        }
    }

    @Override
    public void setDataOld(DataModelOld dataModel, List dataList) {
        if (dataModel != null) {
            dataModel.setKtfZhYhzkList(dataList);
        }
    }
}
