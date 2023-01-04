package cn.gtmap.realestate.exchange.service.impl.national.data;

import cn.gtmap.realestate.common.core.domain.exchange.AccessData;
import cn.gtmap.realestate.common.core.domain.exchange.DataModel;
import cn.gtmap.realestate.common.core.domain.exchange.QjsjDataModel;
import cn.gtmap.realestate.common.core.domain.exchange.ZhKDO;
import cn.gtmap.realestate.common.core.dto.exchange.access.QjsjjcDTO;
import cn.gtmap.realestate.common.core.service.feign.building.AccessBuildingFeignService;
import cn.gtmap.realestate.exchange.core.dto.common.DataModelOld;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.ZhKOldDO;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcdjMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.ZhKMapper;
import cn.gtmap.realestate.exchange.service.impl.inf.gx.GxDataDbByFkServiceImpl;
import cn.gtmap.realestate.exchange.service.national.NationalAccessDataService;
import cn.gtmap.realestate.exchange.service.national.NationalAccessQjsjService;
import cn.gtmap.realestate.exchange.util.ClassHandleUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 国家接入-宗海空间属性
 *
 * @author lst
 * @version 1.0, 2015/11/20
 */
public class NationalAccessDataZhImpl extends GxDataDbByFkServiceImpl implements NationalAccessDataService, NationalAccessQjsjService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NationalAccessDataZhImpl.class);

    @Autowired
    private ZhKMapper zhKMapper;

    @Autowired
    private BdcdjMapper bdcdjMapper;

    @Autowired
    private AccessBuildingFeignService accessBuildingFeignService;

    @Override
    public DataModel getAccessDataModel(String ywh, DataModel dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            Map<String, String> map = Maps.newHashMap();
            map.put("ywh", ywh);
            List<ZhKDO> zhKList = null;
            if (datasourceSwitch) {
                LOGGER.info("开始调用building工程queryZhkList:{}", ywh);
                Map<String, Object> bdcdyhAndQjgldm = bdcdjMapper.getBdcdyhByXmid(ywh);
                if (MapUtils.isNotEmpty(bdcdyhAndQjgldm) && bdcdyhAndQjgldm.containsKey("BDCDYH")) {
                    LOGGER.info("开始调用building工程queryZhkList,入参:{}", bdcdyhAndQjgldm);
                    zhKList = accessBuildingFeignService.queryZhkList((String) bdcdyhAndQjgldm.get("BDCDYH"), bdcdyhAndQjgldm.containsKey("QJGLDM") ? (String) bdcdyhAndQjgldm.get("QJGLDM") : null);
                }
            } else {
                zhKList = zhKMapper.queryZhkList(map);
            }
            if (CollectionUtils.isNotEmpty(zhKList)) {
                if (newDefault) {

                    for (ZhKDO zhKDO : zhKList) {
                        ClassHandleUtil.setDefaultValueToNullField(zhKDO);
                    }
                }
                setData(dataModel, zhKList);
            }
        }
        return dataModel;
    }

    @Override
    public DataModelOld getAccessDataModelOld(String ywh, DataModelOld dataModel) {

        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            Map<String, String> map = Maps.newHashMap();
            map.put("ywh", ywh);
            List<ZhKOldDO> zhKList = null;
            zhKList = zhKMapper.queryZhkListOld(map);
            if (CollectionUtils.isNotEmpty(zhKList)) {
                if (newDefault) {

                    for (ZhKOldDO zhKDO : zhKList) {
                        ClassHandleUtil.setDefaultValueToNullField(zhKDO);
                    }
                }
                setDataOld(dataModel, zhKList);
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
        return "ZH_K_105";
    }

    /**
     * @param dataModel
     * @return java.util.List<java.lang.Object>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 从 dataModel 中获取单表数据
     * TODO 宗海属性 由于表结构 没有主键 和外键  所以暂时不保存共享库
     */
    @Override
    public List getData(DataModel dataModel) {
        return Collections.emptyList();
    }

    @Override
    public List getDataOld(DataModelOld dataModel) {
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
            dataModel.setZhkList(dataList);
        }
    }

    @Override
    public void setDataOld(DataModelOld dataModel, List dataList) {
        if (dataModel != null) {
            dataModel.setZhkList(dataList);
        }
    }

    /**
     * @param qjsjjcDTO
     * @param dataModel
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 组织权籍数据
     * @date : 2022/11/22 9:13
     */
    @Override
    public QjsjDataModel getAccessQjsjModel(QjsjjcDTO qjsjjcDTO, QjsjDataModel dataModel) {
        if (Objects.isNull(qjsjjcDTO)) {
            return dataModel;
        }
        List<ZhKDO> zhKList = null;
        LOGGER.info("开始调用building工程queryZhkList,入参:{}", JSON.toJSONString(qjsjjcDTO));
        zhKList = accessBuildingFeignService.queryZhkList(qjsjjcDTO.getBdcdyh(), qjsjjcDTO.getQjgldm());
        if (CollectionUtils.isNotEmpty(zhKList)) {
            if (newDefault) {

                for (ZhKDO zhKDO : zhKList) {
                    ClassHandleUtil.setDefaultValueToNullField(zhKDO);
                }
            }
            setQjData(dataModel, zhKList);
        }
        return dataModel;
    }

    /**
     * @param dataModel@author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取查询的结果数据
     * @date : 2022/11/22 15:28
     */
    @Override
    public List getQjData(QjsjDataModel dataModel) {
        return dataModel.getZdKList();
    }

    /**
     * @param dataModel
     * @param dataList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description setQjsj
     * @date : 2022/11/24 14:05
     */
    @Override
    public void setQjData(QjsjDataModel dataModel, List dataList) {
        if (dataModel != null) {
            dataModel.setZhkList(dataList);
        }
    }
}
