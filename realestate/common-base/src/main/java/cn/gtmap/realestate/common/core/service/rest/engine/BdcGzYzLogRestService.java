package cn.gtmap.realestate.common.core.service.rest.engine;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzYzLogDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 2022/5/13
 * @description 规则验证日志RestService
 */
public interface BdcGzYzLogRestService {

    /**
     * 规则验证日志分组分页查询
     * <p>规则日志按照 验证标识 分组查询</p>
     * @param pageable  分页参数
     * @param bdcGzYzLogQOJson  规则验证日志QO json字符串
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 规则日志分组分页数据
     */
    @GetMapping("/realestate-engine/rest/v1.0/gzyzlog/group/page")
    Page<BdcGzYzLogDTO> listBdcGzYzLogGroupPage(@LayuiPageable Pageable pageable,
                                                @RequestParam(name = "bdcGzYzLogQOJson",required = false) String bdcGzYzLogQOJson);

    /**
     * 规则验证日志分页查询
     * @param pageable  分页参数
     * @param bdcGzYzLogQOJson  规则验证日志QO json字符串
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 规则日志分页数据
     */
    @GetMapping("/realestate-engine/rest/v1.0/gzyzlog/page")
    Page<BdcGzYzLogDTO> listBdcGzYzLogPage(@LayuiPageable Pageable pageable,
                                                @RequestParam(name = "bdcGzYzLogQOJson",required = false) String bdcGzYzLogQOJson);

}
