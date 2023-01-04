package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.dto.accept.BdcCshSlxmDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlYwxxDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2019/2/28
 * @description 虚拟单元号服务
 */
public interface BdcXndyhRestService {
    /**
     * @param bdcCshSlxmDTO 不动产初始化受理项目
     * @return 不动产受理项目前台
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 判断虚拟单元号
     */
    @PostMapping("/realestate-accept/rest/v1.0/xndyh/yzxndyh")
    List<BdcSlYwxxDTO> listYzXndyh(@RequestBody BdcCshSlxmDTO bdcCshSlxmDTO);
}
