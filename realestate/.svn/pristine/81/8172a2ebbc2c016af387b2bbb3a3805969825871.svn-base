package cn.gtmap.realestate.common.core.service.rest.config;

import cn.gtmap.realestate.common.core.domain.BdcDysjPzDO;
import cn.gtmap.realestate.common.core.domain.BdcDysjZbPzDO;
import cn.gtmap.realestate.common.core.dto.config.BdcDysjPzDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author <a href ="mailto:wutao2@gtmap.cn">wutao</a>
 * @version 1.0, 2022/8/9
 * @description 异地受理相关接口
 */
public interface BdcYdslPzRestService {
    /**
     * @param
     * @return
     * @author <a href ="mailto:wutao2@gtmap.cn">wutao</a>
     * @description 查询异地受理角色台账页面需要展示哪些查询条件
     */
    @GetMapping("/realestate-config/rest/v1.0/ydsl/listcxtj")
    String listcxtj(@RequestParam(name = "cxym") String cxym);

}
