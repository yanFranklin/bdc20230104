package cn.gtmap.realestate.accept.core.service;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxmJmzcGxDO;

import java.util.List;

/**
 * @program: realestate
 * @description: 受理收费项目减免政策关系服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-09-09 17:22
 **/
public interface BdcSlSfxmJmzcGxService {

    /**
     * @param jmzt   减免状态
     * @param jmzcbz 减免政策标志
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2020/9/9 17:25
     */
    List<BdcSlSfxmJmzcGxDO> listSlSfxmJmzcGx(Integer jmzt, String jmzcbz);
}
