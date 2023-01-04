package cn.gtmap.realestate.building.service.impl.bg.bdcdyfwlxbg;

import cn.gtmap.realestate.building.core.bo.FwBgBO;
import cn.gtmap.realestate.building.service.bg.FwBgService;
import cn.gtmap.realestate.building.service.impl.bg.FwLjzJbxxBgServiceImpl;
import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-02-26
 * @description
 */
@Service
public class LjzJbxxBgServiceImpl extends FwLjzJbxxBgServiceImpl{

    @Resource(name = "hsJbxxBgServiceImpl")
    private FwBgService hsJbxxBgServiceImpl;

    @Override
    public void dealBgjl(FwBgBO<FwLjzDO> bgBO) {
    }

    @Override
    public void dealFwHs(FwBgBO<FwLjzDO> bgBO) {
        super.dealFwHs(hsJbxxBgServiceImpl,bgBO);
    }
}
