package cn.gtmap.realestate.exchange.service.impl.inf.yancheng.ql.impl;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.exchange.QlfQlYgdjDO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.YthYwxxDTO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.QlfQlYgdjOldDO;
import cn.gtmap.realestate.exchange.core.mapper.server.QlfQlYgdjMapper;
import cn.gtmap.realestate.exchange.service.impl.inf.yancheng.ql.AbstractYthInitParamService;
import cn.gtmap.realestate.exchange.service.impl.inf.yancheng.ql.YthInitParamService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class YthYgInitParamServiceImpl extends AbstractYthInitParamService implements YthInitParamService {

    public static final String YG_YTH_QL_INIT_PARAM_SERVICE_KEY = "ythYgQlInitParamServiceImpl";

    @Autowired
    private QlfQlYgdjMapper qlfQlYgdjMapper;

    /**
     * 获取相应的实现类
     *
     * @return
     */
    @Override
    public String getHandleServiceName() {
        return YG_YTH_QL_INIT_PARAM_SERVICE_KEY;
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
        List<QlfQlYgdjOldDO> qlfQlYgdjDOS = qlfQlYgdjMapper.queryQlfQlYgdjListOld(sqlParam);
        if (CollectionUtils.isEmpty(qlfQlYgdjDOS)) {
            sqlParam.clear();
            sqlParam.put("yywh", bdcXmDO.getXmid());
            qlfQlYgdjDOS = qlfQlYgdjMapper.queryQlfQlYgdjListOld(sqlParam);
        }
        if (CollectionUtils.isNotEmpty(qlfQlYgdjDOS)) {
            if (ythYwxxDTO.getQlfQlYgdjDO()!=null){
                ythYwxxDTO.getQlfQlYgdjDO().addAll(qlfQlYgdjDOS);
            }else {
                ythYwxxDTO.setQlfQlYgdjDO(qlfQlYgdjDOS);
            }
        }
    }
}
