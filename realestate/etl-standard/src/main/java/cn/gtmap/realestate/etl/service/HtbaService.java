package cn.gtmap.realestate.etl.service;

import cn.gtmap.realestate.common.core.dto.etl.HtbaxxDTO;

/**
 * @program: realestate
 * @description: 合同备案服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-12-16 09:26
 **/
public interface HtbaService {
    /**
     * @param bdcdyh 不动产单元号
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 从楼盘表界面打开合同备案信息的处理
     * @date : 2020/12/16 9:28
     */

    String forwardHtbaHtml(String bdcdyh);

    /**
     * @param htbaxxDTO 备案数据
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 外网推送备案数据
     * @date : 2021/4/13 16:04
     */
    Object wwtsHtbaxx(HtbaxxDTO htbaxxDTO);

    /**
     * @param htbaxxDTO 撤销数据
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 外网撤销备案数据
     * @date : 2021/4/13 16:04
     */
    Object wwcxHtbaxx(HtbaxxDTO htbaxxDTO);
}
