package cn.gtmap.realestate.exchange.service.impl.national.data;

import cn.gtmap.realestate.exchange.core.domain.exchange.AccessData;
import cn.gtmap.realestate.exchange.core.domain.exchange.DataModel;
import cn.gtmap.realestate.exchange.core.domain.exchange.KtfZhYhydzbDO;
import cn.gtmap.realestate.exchange.core.mapper.server.KtfZhYhYdzbMapper;
import cn.gtmap.realestate.exchange.service.impl.inf.gx.GxDataDbByPkServiceImpl;
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
 * 国家接入-用海、用岛坐标
 *
 * @author lst
 * @version 1.0, 2015/11/20
 */
public class NationalAccessDataYhydzbImpl extends GxDataDbByPkServiceImpl implements NationalAccessDataService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NationalAccessDataYhydzbImpl.class);

    @Autowired
    private KtfZhYhYdzbMapper ktfZhYhYdzbMapper;

    @Override
    public DataModel getAccessDataModel(String ywh, DataModel dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            Map<String, String> map = Maps.newHashMap();
            map.put("ywh", ywh);
            List<KtfZhYhydzbDO> ktfZhYhydzbList = ktfZhYhYdzbMapper.queryKtfZhYhYdzbList(map);
            if (CollectionUtils.isNotEmpty(ktfZhYhydzbList)) {
                if (newDefault) {

                    for (KtfZhYhydzbDO ktfZhYhydzbDO : ktfZhYhydzbList) {
                        ClassHandleUtil.setDefaultValueToNullField(ktfZhYhydzbDO);
                    }
                }
                setData(dataModel, ktfZhYhydzbList);
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
        return "KTF_ZH_YHYDZB";
    }

    /**
     * @param dataModel
     * @return java.util.List<java.lang.Object>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 从 dataModel 中获取单表数据
     */
    @Override
    public List getData(DataModel dataModel) {
        return dataModel.getKtfZhYhydzbList();
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
            dataModel.setKtfZhYhydzbList(dataList);
        }
    }

}
