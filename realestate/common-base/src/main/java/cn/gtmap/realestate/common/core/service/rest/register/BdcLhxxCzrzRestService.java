package cn.gtmap.realestate.common.core.service.rest.register;

import cn.gtmap.realestate.common.core.domain.BdcLhxxCzrzDO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2022/1/10
 * @description 不动产量化信息操作日志服务
 */
public interface BdcLhxxCzrzRestService {

    /**
     * 查询量化信息操作日志信息
     * @param bdcLhxxCzrzDO 不动产量化信息操作日志DO
     */
    @PostMapping(value = "/realestate-register/rest/v1.0/lhxxczrz/list")
    List<BdcLhxxCzrzDO> queryBdcLhxxCrzz(@RequestBody BdcLhxxCzrzDO bdcLhxxCzrzDO);
}
