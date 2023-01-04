package cn.gtmap.realestate.accept.core.service;

import cn.gtmap.realestate.common.core.dto.accept.BdcSfDTO;
import cn.gtmap.realestate.common.core.dto.accept.YcslFjxxDTO;
import cn.gtmap.realestate.common.core.dto.accept.YcslYmxxDTO;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/6/25
 * @description 一窗受理信息
 */
public interface BdcYcslService {

    /**
     * @param xmid 项目ID
     * @return 一窗受理信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询一窗受理信息
     */
    YcslYmxxDTO queryYcslYmxx(String xmid);

    /**
     * @param gzlslid 工作流受理ID
     * @return 一窗受理信息
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description 查询一窗受理核税附件信息
     */
    List<YcslFjxxDTO> queryYcslFjxx(String gzlslid);

    /**
     * @param xmid 项目ID
     * @return 权利人和义务人税务信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询税务信息
     */
    BdcSfDTO queryYcslSwxx(String xmid);
}
