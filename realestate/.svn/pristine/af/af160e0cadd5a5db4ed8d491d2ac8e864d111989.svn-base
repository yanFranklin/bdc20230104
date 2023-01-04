package cn.gtmap.realestate.init.service.jtcy.impl;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.InitService;
import cn.gtmap.realestate.init.service.jtcy.InitBdcJtcyAbstractService;
import cn.gtmap.realestate.init.service.jtcy.InitBdcQlrJtcyAbstractService;
import cn.gtmap.realestate.init.service.jtcy.InitBdcYwrJtcyAbstractService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/8/5.
 * @description 家庭成员服务实现类
 */
@Service
public class InitBdcJtcyServiceImpl extends InitBdcJtcyAbstractService {
    @Override
    public String getVal() {
        return CommonConstantUtils.SF_S_DM.toString();
    }
    @Override
    public InitServiceDTO initJtcy(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) throws Exception {
        // 加入权利人和义务人生成家庭成员服务
        List<InitService> list = initBeanFactory.getTrafficMode(initServiceQO.getBdcCshFwkgSl(), InitBdcQlrJtcyAbstractService.class);
        list.addAll(initBeanFactory.getTrafficMode(initServiceQO.getBdcCshFwkgSl(), InitBdcYwrJtcyAbstractService.class));
        if (CollectionUtils.isNotEmpty(list)) {
            for (InitService service : list) {
                service.init(initServiceQO, initServiceDTO);
            }
        }
        return initServiceDTO;
    }

    @Override
    public List<Object> delete(List<BdcXmDO> list, Boolean sfzqlpbsj, Boolean sfdzbflpbsj, Boolean plDel) throws Exception {
        return null;
    }

    @Override
    public InitServiceDTO query(BdcXmDO bdcXmDO, InitServiceDTO initServiceDTO) throws Exception {
        return null;
    }
}
