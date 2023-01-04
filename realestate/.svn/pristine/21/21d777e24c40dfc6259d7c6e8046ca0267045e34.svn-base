package cn.gtmap.realestate.init.service.qlxx.qlfl.impl;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.core.service.impl.BdcQllxServiceImpl;
import cn.gtmap.realestate.init.service.qlxx.qlfl.InitBdcJsydsyqAbstractService;
import cn.gtmap.realestate.init.util.Constants;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
 * @version 1.0, 2018/11/15
 * @description 从权籍加载数据到建设用地使用权
 */
@Service
public class InitWlzsToBdcJsydsyqServiceImpl extends InitBdcJsydsyqAbstractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(InitWlzsToBdcJsydsyqServiceImpl.class);

    @Autowired
    BdcQllxServiceImpl bdcQllxService;

    /**
     * @return 对照开关值
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description
     */
    @Override
    public String getVal() {
        return Constants.QLSJLY_WLZS_HFTDZ;
    }

    /**
     * 初始化权利信息接口
     *
     * @param initServiceQO 初始化所需数据结构
     * @return 返回权利信息
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description
     */
    @Override
    public BdcQl initQlxx(InitServiceQO initServiceQO) throws Exception {
        return initFromWlzsXm(initServiceQO, new BdcJsydsyqDO());
    }


    /**
     * 初始化权利数据从wlzs
     *
     * @param initServiceQO
     * @param obj
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public BdcJsydsyqDO initFromWlzsXm(InitServiceQO initServiceQO, BdcJsydsyqDO obj) throws IllegalAccessException, InstantiationException {
        // 从外联证书中获取一些信息
        LOGGER.info("海域换证从外联项目中获取数据：{}", JSON.toJSONString(initServiceQO.getBdcXmLsgxList()));
        List<BdcXmLsgxDO> bdcXmLsgxList = initServiceQO.getBdcXmLsgxList();
        if (CollectionUtils.isNotEmpty(bdcXmLsgxList)) {
            List<BdcXmLsgxDO> wlXmLsgxList = bdcXmLsgxList
                    .stream()
                    .filter(bdcXmLsgxDO -> 1 == bdcXmLsgxDO.getWlxm()).collect(Collectors.toList());
            if(CollectionUtils.isEmpty(wlXmLsgxList)){
                return obj;
            }
            //选择土地单元号理论上只会有一个外联
            BdcXmLsgxDO bdcXmLsgxDO = wlXmLsgxList.get(0);
            //优先查询海域使用权
            BdcQl bdcQl = bdcQllxService.queryQllxDO(new BdcHysyqDO(), bdcXmLsgxDO.getYxmid());
            LOGGER.info("海域换证从外联项目中获取数据,尝试获取海域使用权{}：{}",bdcXmLsgxDO.getYxmid(),
                    JSON.toJSONString(bdcQl));
            if (Objects.nonNull(bdcQl)) {
                obj.setSyqqssj(((BdcHysyqDO) bdcQl).getSyqqssj());
                obj.setSyqjssj(((BdcHysyqDO) bdcQl).getSyqjssj());
                return obj;
            }

            //如果没有海域使用权则尝试查找建设用地使用权
            bdcQl = bdcQllxService.queryQllxDO(new BdcJsydsyqDO(), bdcXmLsgxDO.getYxmid());
            LOGGER.info("海域换证从外联项目中获取数据,尝试获取建设用地使用权{}：{}",bdcXmLsgxDO.getYxmid(),
                    JSON.toJSONString(bdcQl));
            if (Objects.nonNull(bdcQl)) {
                obj.setSyqqssj(((BdcJsydsyqDO) bdcQl).getSyqqssj());
                obj.setSyqjssj(((BdcJsydsyqDO) bdcQl).getSyqjssj());
                obj.setSyqqssj2(((BdcJsydsyqDO) bdcQl).getSyqqssj2());
                obj.setSyqjssj2(((BdcJsydsyqDO) bdcQl).getSyqjssj2());
                obj.setSyqqssj3(((BdcJsydsyqDO) bdcQl).getSyqqssj3());
                obj.setSyqjssj3(((BdcJsydsyqDO) bdcQl).getSyqjssj3());
                return obj;
            }
        }
        return obj;
    }

}
