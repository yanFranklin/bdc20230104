package cn.gtmap.realestate.exchange.service.impl.inf.yancheng.ql.impl;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.YthYwxxDTO;
import cn.gtmap.realestate.exchange.service.impl.inf.yancheng.ql.AbstractYthInitParamService;
import cn.gtmap.realestate.exchange.service.impl.inf.yancheng.ql.YthInitParamService;
import org.springframework.stereotype.Service;

@Service
public class YthInitParamServiceImpl extends AbstractYthInitParamService implements YthInitParamService {

    public static final String YTH_INIT_PARAM_SERVICE_KEY = "ythInitParamServiceImpl";

    /**
     * 获取相应的实现类
     *
     * @return
     */
    @Override
    public String getHandleServiceName() {
        return YTH_INIT_PARAM_SERVICE_KEY;
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
        //无权利信息
    }
}
