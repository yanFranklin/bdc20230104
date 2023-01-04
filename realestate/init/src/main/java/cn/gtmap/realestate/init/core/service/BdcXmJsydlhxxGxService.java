package cn.gtmap.realestate.init.core.service;

import cn.gtmap.realestate.common.core.domain.BdcXmJsydlhxxGxDO;
import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/12/17
 * @description 项目建设用地量化信息关系服务
 */
public interface BdcXmJsydlhxxGxService {

    /**
     * @param gzlslid 工作流实例ID
     * @return 项目建设用地量化关系列表
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据xmid获取项目建设用地量化关系列表
     */
    List<BdcXmJsydlhxxGxDO> listBdcXmJsydlhxxGxByGzlslid(String gzlslid);

    /**
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据gzlslid删除项目建设用地量化关系
     */
    void deleteBdcXmJsydlhxxGx(String gzlslid);

}
