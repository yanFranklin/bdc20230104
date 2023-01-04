package cn.gtmap.realestate.init.service.jtcy;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.InitService;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/8/5.
 * @description 义务人家庭成员
 */

public abstract class InitBdcYwrJtcyAbstractService extends InitBdcJtcyBaseAbstractService{
    @Override
    public String getCode() {
        return "ywrsjly";
    }
    @Override
    public InitServiceDTO initJtcy(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) throws Exception {
        List<InitService> list = initBeanFactory.getTrafficMode(initServiceQO.getBdcCshFwkgSl(), InitBdcYwrJtcyAbstractService.class);
        if (CollectionUtils.isNotEmpty(list)) {
            for (InitService service : list) {
                service.init(initServiceQO, initServiceDTO);
            }
        }
        return initServiceDTO;
    }
    @Override
    public List<Object> delete(List<BdcXmDO> list, Boolean sfzqlpbsj, Boolean sfdzbflpbsj, Boolean plDel) throws Exception {
        return deleteJtcy(list,sfzqlpbsj,sfdzbflpbsj,plDel,CommonConstantUtils.QLRLB_YWR);
    }

    @Override
    public InitServiceDTO query(BdcXmDO bdcXmDO, InitServiceDTO initServiceDTO) throws Exception {
        return queryJtcy(bdcXmDO,initServiceDTO,CommonConstantUtils.QLRLB_YWR);
    }
}
