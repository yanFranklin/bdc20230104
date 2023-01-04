package cn.gtmap.realestate.exchange.service.impl.inf.yancheng.ql.impl;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.exchange.QlfQlCfdjDO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.YthYwxxDTO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.QlfQlCfdjOldDO;
import cn.gtmap.realestate.exchange.core.mapper.server.QlfQlCfdjMapper;
import cn.gtmap.realestate.exchange.service.impl.inf.yancheng.ql.AbstractYthInitParamService;
import cn.gtmap.realestate.exchange.service.impl.inf.yancheng.ql.YthInitParamService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class YthCfInitParamServiceImpl extends AbstractYthInitParamService implements YthInitParamService {

    public static final String CF_YTH_QL_INIT_PARAM_SERVICE_KEY = "ythCfQlInitParamServiceImpl";

    @Autowired
    private QlfQlCfdjMapper qlfQlCfdjMapper;

    /**
     * 获取相应的实现类
     *
     * @return
     */
    @Override
    public String getHandleServiceName() {
        return CF_YTH_QL_INIT_PARAM_SERVICE_KEY;
    }

    /**
     * 初始化盐城一体化-发证数据同步请求参数
     *
     *
     * @param ythYwxxDTO
     * @param bdcXmDO
     */
    @Override
    public void initYthQlRequestParam(YthYwxxDTO ythYwxxDTO, BdcXmDO bdcXmDO) {
        Map<String, Object> sqlParam = super.initSqlParam(bdcXmDO);
        //查封权利信息
        List<QlfQlCfdjOldDO> qlfQlCfdjDOS = qlfQlCfdjMapper.queryQlfQlCfdjListOld(sqlParam);
        if (CollectionUtils.isEmpty(qlfQlCfdjDOS)) {
            sqlParam.clear();
            sqlParam.put("yywh", bdcXmDO.getXmid());
            qlfQlCfdjDOS = qlfQlCfdjMapper.queryQlfQlCfdjListOld(sqlParam);
        }
        if (CollectionUtils.isNotEmpty(qlfQlCfdjDOS)){
            if (ythYwxxDTO.getQlfQlCfdjDO()!=null){
                ythYwxxDTO.getQlfQlCfdjDO().addAll(qlfQlCfdjDOS);
            }else {
                ythYwxxDTO.setQlfQlCfdjDO(qlfQlCfdjDOS);
            }
        }
    }
}
