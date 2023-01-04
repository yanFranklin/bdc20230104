package cn.gtmap.realestate.exchange.service.impl.national.data;

import cn.gtmap.realestate.common.core.domain.exchange.AccessData;
import cn.gtmap.realestate.common.core.domain.exchange.DataModel;
import cn.gtmap.realestate.common.core.domain.exchange.KtfZdbhqkDO;
import cn.gtmap.realestate.common.core.service.feign.building.AccessBuildingFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.core.dto.common.DataModelOld;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.KtfZdbhqkOldDO;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcdjMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.KtfZdbhqkMapper;
import cn.gtmap.realestate.exchange.service.impl.inf.gx.GxDataDbByFkServiceImpl;
import cn.gtmap.realestate.exchange.service.national.NationalAccessDataService;
import cn.gtmap.realestate.exchange.util.ClassHandleUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 国家接入-宗地变化情况
 *
 * @author lst
 * @version 1.0, 2015/11/20
 */
public class NationalAccessDataZdbhqkImpl extends GxDataDbByFkServiceImpl implements NationalAccessDataService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NationalAccessDataZdbhqkImpl.class);

    @Autowired
    private KtfZdbhqkMapper ktfZdbhqkMapper;
    @Autowired
    private BdcdjMapper bdcdjMapper;
    @Autowired
    private AccessBuildingFeignService accessBuildingFeignService;

    @Override
    public DataModel getAccessDataModel(String ywh, DataModel dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            HashMap<String, String> map = new HashMap();
            map.put("ywh", ywh);
            List<KtfZdbhqkDO> ktfZdbhqkList = null;
            if (datasourceSwitch && !CommonConstantUtils.SYSTEM_VERSION_HF.equals(dataVersion)) {
                ktfZdbhqkList = ktfZdbhqkMapper.queryKtfZdbhqkList(map);
                if (CollectionUtils.isNotEmpty(ktfZdbhqkList)) {
                    LOGGER.info("开始调用building工程queryZdbgjlbList:{}", ywh);
                    Map<String, Object> bdcdyhAndQjgldm = bdcdjMapper.getBdcdyhByXmid(ywh);
                    for (KtfZdbhqkDO ktfZdbhqkDO : ktfZdbhqkList) {
                        if (qxdmConvert) {
                            //需要根据qxdm进行对照
                            ktfZdbhqkDO.setQxdm(commonService.bdcDmToDsfZdNvl("ACCESS_QXDM", "ACCESS", ktfZdbhqkDO.getQxdm()));
                        }
                        if (StringUtils.isNotBlank(ktfZdbhqkDO.getZddm())) {
                            List<Map> zdbhjlbList = accessBuildingFeignService.queryZdbgjlbList(ktfZdbhqkDO.getZddm(), bdcdyhAndQjgldm.containsKey("QJGLDM") ? (String) bdcdyhAndQjgldm.get("QJGLDM") : null);
                            if (CollectionUtils.isNotEmpty(zdbhjlbList)) {
                                ktfZdbhqkDO.setBhqzddm(null != zdbhjlbList.get(0).get("YBH") ? String.valueOf(zdbhjlbList.get(0).get("YBH")) : null);
                            }
                        }
                    }

                }
            } else {
                ktfZdbhqkList = ktfZdbhqkMapper.queryKtfZdbhqkList(map);

            }
            if (CollectionUtils.isNotEmpty(ktfZdbhqkList)) {
                if (newDefault) {

                    for (KtfZdbhqkDO ktfZdbhqkDO : ktfZdbhqkList) {
                        ClassHandleUtil.setDefaultValueToNullField(ktfZdbhqkDO);
                    }
                }
                setData(dataModel, ktfZdbhqkList);
            }
        }
        return dataModel;
    }

    @Override
    public DataModelOld getAccessDataModelOld(String ywh, DataModelOld dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            HashMap<String, String> map = new HashMap();
            map.put("ywh", ywh);
            List<KtfZdbhqkOldDO> ktfZdbhqkList = ktfZdbhqkMapper.queryKtfZdbhqkListOld(map);
            if (CollectionUtils.isNotEmpty(ktfZdbhqkList)) {
                setDataOld(dataModel, ktfZdbhqkList);
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
        return "KTF_ZDBHQK";
    }

    /**
     * @param dataModel
     * @return java.util.List<java.lang.Object>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 从 dataModel 中获取单表数据
     */
    @Override
    public List getData(DataModel dataModel) {
        return dataModel.getKtfZdbhqkList();
    }

    @Override
    public List getDataOld(DataModelOld dataModel) {
        return dataModel.getKtfZdbhqkList();
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
            dataModel.setKtfZdbhqkList(dataList);
        }
    }

    @Override
    public void setDataOld(DataModelOld dataModel, List dataList) {
        if (dataModel != null) {
            dataModel.setKtfZdbhqkList(dataList);
        }
    }
}
