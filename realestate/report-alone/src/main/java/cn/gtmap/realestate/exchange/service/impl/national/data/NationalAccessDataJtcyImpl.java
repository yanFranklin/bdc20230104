package cn.gtmap.realestate.exchange.service.impl.national.data;

import cn.gtmap.realestate.exchange.core.domain.BdcQlrDO;
import cn.gtmap.realestate.exchange.core.domain.exchange.DataModel;
import cn.gtmap.realestate.exchange.core.domain.exchange.ZttGyJtcyDO;
import cn.gtmap.realestate.exchange.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.ZttGyJtcyMapper;
import cn.gtmap.realestate.exchange.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.exchange.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.exchange.service.impl.inf.gx.GxDataDbByFkServiceImpl;
import cn.gtmap.realestate.exchange.service.national.NationalAccessDataService;
import cn.gtmap.realestate.exchange.util.ClassHandleUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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


    private static final Logger LOGGER = LoggerFactory.getLogger(NationalAccessDataJtcyImpl.class);
    @Autowired
    @Qualifier("serverEntityMapper")
    EntityMapper entityMapper;
    @Autowired
    private ZttGyJtcyMapper zttGyJtcyMapper;


    @Override
    public DataModel getAccessDataModel(String ywh, DataModel dataModel) {
        LOGGER.info("开始查询家庭成员信息！");
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            //xmid查权利人表，查到权利人之后，用qlrid查户口簿关系表，再用hkbbm查家庭成员
            List<ZttGyJtcyDO> zttGyJtcyList = new ArrayList<>();
            BdcQlrDO qlr = new BdcQlrDO();
            qlr.setXmid(ywh);
            qlr.setQlrlb("1");
            Example example = entityMapper.objToExample(qlr);
            List<BdcQlrDO> qlrDOList = entityMapper.selectByExampleNotNull(example);
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
    public <T> List<T> getAccessData(String ywh) {
        return Collections.emptyList();
    }

    @Override
    public String getAccessDataTagName() {
        return "ZTT_GY_JTCY";
    }


    @Override
    public List getData(DataModel dataModel) {
        return dataModel.getZttGyJtcyDOList();
    }

    @Override
    public void setData(DataModel dataModel, List dataList) {
        if (dataModel != null) {
            dataModel.setZttGyJtcyDOList(dataList);
        }
    }

}
