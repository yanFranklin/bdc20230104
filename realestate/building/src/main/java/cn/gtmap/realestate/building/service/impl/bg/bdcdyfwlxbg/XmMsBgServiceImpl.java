package cn.gtmap.realestate.building.service.impl.bg.bdcdyfwlxbg;

import cn.gtmap.realestate.building.core.bo.FwBgBO;
import cn.gtmap.realestate.building.service.impl.bg.FwXmxxMsServiceImpl;
import cn.gtmap.realestate.common.core.domain.building.FwXmxxDO;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-02-26
 * @description 单独灭失变更项目信息逻辑
 * 不处理逻辑幢 和 不记录变更记录表
 */
@Service
public class XmMsBgServiceImpl extends FwXmxxMsServiceImpl{

    /**
     * @param bgBO
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 保存变更记录
     */
    @Override
    public void saveBgjl(FwBgBO<FwXmxxDO> bgBO) {

    }

    /**
     * @param bgBO
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 处理逻辑幢
     */
    @Override
    public void dealLjz(FwBgBO<FwXmxxDO> bgBO) {
    }
}
