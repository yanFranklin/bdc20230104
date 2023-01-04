package cn.gtmap.realestate.engine.core.service;

import cn.gtmap.realestate.common.core.dto.engine.BdcGzYzLogDTO;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzYzLogQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2022/5/13
 * @description 规则系统子规则服务接口
 */
public interface BdcGzYzLogService {

    /**
     * 规则验证日志分组分页查询
     * <p>规则日志按照 验证标识 分组查询</p>
     * @param pageable  分页参数
     * @param bdcGzYzLogQO  规则验证日志QO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 规则日志分组分页数据
     */
    Page<BdcGzYzLogDTO> listBdcGzYzLogGroupPage(Pageable pageable, BdcGzYzLogQO bdcGzYzLogQO);


    /**
     * 规则验证日志分页查询
     * @param pageable  分页参数
     * @param bdcGzYzLogQO  规则验证日志QO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 规则日志分页数据
     */
    Page<BdcGzYzLogDTO> listBdcGzYzLogPage(Pageable pageable, BdcGzYzLogQO bdcGzYzLogQO);

}
