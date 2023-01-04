package cn.gtmap.realestate.natural.core.service.pzxx;

import cn.gtmap.realestate.common.core.domain.natural.ZrzyXtLcpzDO;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/10/26
 * @description 流程配置服务
 */
public interface ZrzyXtLcpzService {

    /**
      * @param gzldyid 工作流定义ID
      * @return 流程基本配置
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 根据工作流定义ID查询流程配置
      */
    ZrzyXtLcpzDO queryZrzyXtLcpz(String gzldyid);

    public List<ZrzyXtLcpzDO> listZrzlcshPz(String gzlslid);
}
