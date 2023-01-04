package cn.gtmap.realestate.exchange.service.inf.changzhou;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.exchange.core.dto.changzhou.yjfw.YsqTsDTO;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
 * @params
 * @return
 * @description 常州 易家服务相关接口
 */
public interface ChangZhouYjfwService {

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [processInsId]
     * @return void
     * @description 预申请推送接口
     */
    void ysqTs(String processInsId);

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [processInsId, bdcXmDTO, ysqTsDTO]
     * @return void
     * @description 收件材料推送
     */
    void sjclTs(String processInsId, BdcXmDO bdcXmDO, YsqTsDTO ysqTsDTO);

}
