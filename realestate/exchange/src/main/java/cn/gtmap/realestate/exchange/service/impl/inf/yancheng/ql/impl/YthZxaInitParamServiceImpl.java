package cn.gtmap.realestate.exchange.service.impl.inf.yancheng.ql.impl;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.exchange.QlfQlZxdjDO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.YthYwxxDTO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.QlfQlZxdjOldDO;
import cn.gtmap.realestate.exchange.core.mapper.server.QlfQlZxdjMapper;
import cn.gtmap.realestate.exchange.service.impl.inf.yancheng.ql.AbstractYthInitParamService;
import cn.gtmap.realestate.exchange.service.impl.inf.yancheng.ql.YthInitParamService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class YthZxaInitParamServiceImpl extends AbstractYthInitParamService implements YthInitParamService {

    public static final String ZX_YTH_QL_INIT_PARAM_SERVICE_KEY = "ythZxQlInitParamServiceImpl";

    @Autowired
    private QlfQlZxdjMapper qlfQlZxdjMapper;

    /**
     * 获取相应的实现类
     *
     * @return
     */
    @Override
    public String getHandleServiceName() {
        return ZX_YTH_QL_INIT_PARAM_SERVICE_KEY;
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
        List<QlfQlZxdjOldDO> qlfQlZxdjList = qlfQlZxdjMapper.queryQlfQlZxdjListOld(sqlParam);
        //@description 根据国家上报标准zxywh大于20截取前20位
        if (CollectionUtils.isNotEmpty(qlfQlZxdjList)) {
            for (QlfQlZxdjOldDO qlfQlZxdj : qlfQlZxdjList) {
                if (StringUtils.isNotBlank(qlfQlZxdj.getZxywh())) {
                    String zxywh = qlfQlZxdj.getZxywh();
                    if (zxywh.length() > 20) {
                        zxywh = zxywh.substring(zxywh.length() - 20);
                    }
                    qlfQlZxdj.setZxywh(zxywh);
                }
            }
            if (ythYwxxDTO.getQlfQlZxdjDO()!=null){
                ythYwxxDTO.getQlfQlZxdjDO().addAll(qlfQlZxdjList);
            }else {
                ythYwxxDTO.setQlfQlZxdjDO(qlfQlZxdjList);
            }
        }
    }
}
