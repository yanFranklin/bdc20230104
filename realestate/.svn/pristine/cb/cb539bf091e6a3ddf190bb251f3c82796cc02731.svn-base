package cn.gtmap.realestate.exchange.service.impl.national.data;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.exchange.DataModel;
import cn.gtmap.realestate.common.core.domain.exchange.ZttGyJtcyDO;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.exchange.core.dto.common.DataModelOld;
import cn.gtmap.realestate.exchange.core.mapper.server.ZttGyJtcyMapper;
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

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * 国家接入-家庭成员
 *
 * @author lst
 * @version 1.0, 2015/11/20
 */
@Service
public class NationalAccessDataJtcyImpl extends GxDataDbByFkServiceImpl implements NationalAccessDataService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NationalAccessDataJtcyImpl.class);

    @Autowired
    private ZttGyJtcyMapper zttGyJtcyMapper;

    @Autowired
    private BdcQlrFeignService qlrFeignService;

    @Resource(name = "exchangeDozerMapper")
    private DozerBeanMapper dozerBeanMapper;


    @Override
    public DataModel getAccessDataModel(String ywh, DataModel dataModel) {
        LOGGER.info("开始查询家庭成员信息！");
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            //xmid查权利人表，查到权利人之后，用qlrid查户口簿关系表，再用hkbbm查家庭成员
            List<ZttGyJtcyDO> zttGyJtcyList = new ArrayList<>();
            BdcQlrQO qlrQO = new BdcQlrQO();
            qlrQO.setXmid(ywh);
            qlrQO.setQlrlb("1");
            List<BdcQlrDO> qlrDOList = qlrFeignService.listBdcQlr(qlrQO);
            if (CollectionUtils.isNotEmpty(qlrDOList)) {
                LOGGER.info("查到权利人，开始循环查户口簿！");
                for (BdcQlrDO qlrDO : qlrDOList) {
                    HashMap<String, Object> map = new HashMap();
                    map.put("qlrid", qlrDO.getQlrid());
                    List<ZttGyJtcyDO> jtcyDOList = zttGyJtcyMapper.queryZttGyJtcyList(map);
                    zttGyJtcyList.addAll(jtcyDOList);
                }
            }

            if (CollectionUtils.isNotEmpty(zttGyJtcyList)) {
                LOGGER.info("查到了家庭成员信息！");
                for (int i = 0; i < zttGyJtcyList.size(); i++) {
                    zttGyJtcyList.get(i).setYwh(ywh);
                    if (newDefault) {
                        ClassHandleUtil.setDefaultValueToNullField(zttGyJtcyList.get(i));
                    }
                }
                LOGGER.info("查到了家庭成员信息！家庭成员数量为：{}", zttGyJtcyList.size());

                setData(dataModel, zttGyJtcyList);
            }
        }


        return dataModel;
    }

    @Override
    public DataModelOld getAccessDataModelOld(String ywh, DataModelOld dataModel) {
        return null;
    }

    @Override
    public <T> List<T> getAccessData(String ywh) {
        return Collections.emptyList();
    }

    @Override
    public String getAccessDataTagName() {
        return "ZTT_GY_JTCY";
    }

    /**
     * @param dataModel
     * @return java.util.List<java.lang.Object>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 从 dataModel 中获取单表数据
     */
    @Override
    public List getData(DataModel dataModel) {
        return dataModel.getZttGyJtcyDOList();
    }

    @Override
    public List getDataOld(DataModelOld dataModel) {
        return null;
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
            dataModel.setZttGyJtcyDOList(dataList);
        }
    }

    @Override
    public void setDataOld(DataModelOld dataModel, List dataList) {

    }
}
