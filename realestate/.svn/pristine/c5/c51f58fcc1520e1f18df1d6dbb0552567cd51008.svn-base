package cn.gtmap.realestate.exchange.service.impl.inf.yancheng.ql.impl;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.exchange.QltFwFdcqYzDO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.YthYwxxDTO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.QltFwFdcqYzOldDO;
import cn.gtmap.realestate.exchange.core.mapper.server.QltFwFdcqYzMapper;
import cn.gtmap.realestate.exchange.service.impl.inf.yancheng.ql.AbstractYthInitParamService;
import cn.gtmap.realestate.exchange.service.impl.inf.yancheng.ql.YthInitParamService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class YthZyInitParamServiceImpl extends AbstractYthInitParamService implements YthInitParamService {

    public static final String ZY_YTH_QL_INIT_PARAM_SERVICE_KEY = "ythZyQlInitParamServiceImpl";

    @Autowired
    private QltFwFdcqYzMapper qltFwFdcqYzMapper;

    /**
     * 获取相应的实现类
     *
     * @return
     */
    @Override
    public String getHandleServiceName() {
        return ZY_YTH_QL_INIT_PARAM_SERVICE_KEY;
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
        List<QltFwFdcqYzOldDO> qltFwFdcqYzDOS = qltFwFdcqYzMapper.queryQltFwFdcqYzListOld(sqlParam);
        if(CollectionUtils.isEmpty(qltFwFdcqYzDOS)) {
            sqlParam.clear();
            sqlParam.put("yywh", bdcXmDO.getXmid());
            qltFwFdcqYzDOS = qltFwFdcqYzMapper.queryQltFwFdcqYzListOld(sqlParam);
        }
        if (CollectionUtils.isNotEmpty(qltFwFdcqYzDOS)) {
            if (ythYwxxDTO.getQltFwFdcqYzDO()!= null){
                ythYwxxDTO.getQltFwFdcqYzDO().addAll(qltFwFdcqYzDOS);
            }else {
                ythYwxxDTO.setQltFwFdcqYzDO(qltFwFdcqYzDOS);
            }
        }
    }
}
