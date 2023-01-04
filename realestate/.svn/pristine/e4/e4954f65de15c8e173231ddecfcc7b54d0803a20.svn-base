package cn.gtmap.realestate.init.core.service;

import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/8/25
 * @description 撤销业务信息服务
 */
public interface BdcCxYwxxService {

    /**
     * @param initServiceQO
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 初始化撤销登记业务信息
     */
    List<InitServiceDTO> initCxYwxx(InitServiceQO initServiceQO) throws Exception;

    /**
     * @param zsDOList 删除的证书列表
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 还原撤销数据证书号
     */
    void revertZhzt(List<BdcZsDO> zsDOList);
}
