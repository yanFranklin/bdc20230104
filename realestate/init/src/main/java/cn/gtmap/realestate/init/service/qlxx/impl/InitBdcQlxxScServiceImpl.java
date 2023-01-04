package cn.gtmap.realestate.init.service.qlxx.impl;

import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.InitService;
import cn.gtmap.realestate.init.service.qlxx.InitBdcQlflAbstractService;
import cn.gtmap.realestate.init.service.qlxx.InitBdcQlxxAbstractService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 生成权利信息
 *
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/6.
 * @description
 */
@Service
public class InitBdcQlxxScServiceImpl extends InitBdcQlxxAbstractService {

    @Override
    public String getVal() {
        return CommonConstantUtils.SF_S_DM.toString();
    }


    /**
     * 初始化权利信息接口
     *
     * @param initServiceQO  初始化所需数据结构
     * @param initServiceDTO
     * @return 返回所有权利相关信息
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public InitServiceDTO initQlxx(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) throws Exception {
        //调用权利信息分类处理生成权利
        List<InitService> list = initBeanFactory.getTrafficMode(initServiceQO.getBdcCshFwkgSl(), InitBdcQlflAbstractService.class);
        if (CollectionUtils.isNotEmpty(list)) {
            for (InitService service : list) {
                service.init(initServiceQO, initServiceDTO);
            }
        }
        return initServiceDTO;
    }
}
