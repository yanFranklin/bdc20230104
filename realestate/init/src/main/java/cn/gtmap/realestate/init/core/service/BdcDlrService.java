package cn.gtmap.realestate.init.core.service;

import cn.gtmap.realestate.common.core.domain.BdcDlrDO;
import cn.gtmap.realestate.common.core.qo.init.BdcDlrQO;

import java.util.List;

/**
 * @program: realestate
 * @description: 代理人Service服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-03-21 16:20
 **/
public interface BdcDlrService {

    /**
     * @param json
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 批量新增代理人
     * @date : 2022/3/21 16:33
     */
    List<BdcDlrDO> insertBatchDlr(String json, String gzlslid, String djxl);

    /**
     * @param json
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 批量更新代理人
     * @date : 2022/3/21 16:34
     */
    List<BdcDlrDO> updateBatchDlr(String json, String gzlslid, String djxl);

    /**
     * @param bdcDlrQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询不动产代理人信息
     * @date : 2022/3/21 17:11
     */
    List<BdcDlrDO> listBdcDlr(BdcDlrQO bdcDlrQO);

    /**
     * @param dlrid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据代理人id删除代理人
     * @date : 2022/3/22 9:01
     */
    int deleteBdcDlrByDlrid(String dlrid);

    /**
     * @param null
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2022/3/22 14:13
     */
    int deleteBatchDlr(String dlrmc, String dlrzjh, String gzlslid, String djxl, List<String> qlridList);

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据流程删除代理人
     * @date : 2022/3/23 14:59
     */
    int deleteLcDlr(String gzlslid);
}
