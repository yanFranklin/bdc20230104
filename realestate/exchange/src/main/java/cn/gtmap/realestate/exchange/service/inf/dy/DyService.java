package cn.gtmap.realestate.exchange.service.inf.dy;

import cn.gtmap.realestate.exchange.core.dto.wwsq.yhxt.YhxtResponseDTO;

/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 1.0  2020-08-19
 * @description 请求大云的一些操作
 */
public interface DyService {

    /**
     * @param slbh
     * @return
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 根据受理编号在佛那个退回
     */
    YhxtResponseDTO zdthBySlbh(String slbh);

}


