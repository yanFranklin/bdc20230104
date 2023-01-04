package cn.gtmap.realestate.exchange.service.impl.inf.yancheng.ql.impl;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.exchange.QlfQlYydjDO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.YthYwxxDTO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.QlfQlYydjOldDO;
import cn.gtmap.realestate.exchange.core.mapper.server.QlfQlYydjMapper;
import cn.gtmap.realestate.exchange.service.impl.inf.yancheng.ql.AbstractYthInitParamService;
import cn.gtmap.realestate.exchange.service.impl.inf.yancheng.ql.YthInitParamService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class YthYyInitParamServiceImpl extends AbstractYthInitParamService implements YthInitParamService {

    public static final String YY_YTH_QL_INIT_PARAM_SERVICE_KEY = "ythYyQlInitParamServiceImpl";

    @Autowired
    private QlfQlYydjMapper qlfQlYydjMapper;

    /**
     * 获取相应的实现类
     *
     * @return
     */
    @Override
    public String getHandleServiceName() {
        return YY_YTH_QL_INIT_PARAM_SERVICE_KEY;
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
        //预告信息
        List<QlfQlYydjOldDO> qlfQlYydjDOS = qlfQlYydjMapper.queryQlfQlYydjListOld(sqlParam);
        if (CollectionUtils.isEmpty(qlfQlYydjDOS)) {
            sqlParam.clear();
            sqlParam.put("yywh", bdcXmDO.getXmid());
            qlfQlYydjDOS = qlfQlYydjMapper.queryQlfQlYydjListOld(sqlParam);
        }
        if (CollectionUtils.isNotEmpty(qlfQlYydjDOS)) {
            if (ythYwxxDTO.getQlfQlYydjDO()!=null){
                ythYwxxDTO.getQlfQlYydjDO().addAll(qlfQlYydjDOS);
            }else {
                ythYwxxDTO.setQlfQlYydjDO(qlfQlYydjDOS);
            }
        }
    }
}
