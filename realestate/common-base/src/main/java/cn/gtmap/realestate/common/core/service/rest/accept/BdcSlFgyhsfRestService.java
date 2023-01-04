package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlFgyhsfDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlFghysfDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2021/10/22/10:01
 * @Description: 不动产受理房改优惠售房rest服务
 */
public interface BdcSlFgyhsfRestService {

    /**
     * @param bdcSlFgyhsfDO 不动产受理房改优惠售房信息
     * @author <a href="mailto:sunyuzhaung@gtmap.cn">sunyuzhaung</a>
     * @description 新增不动产受理房屋信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/fgyhsf/saveOrUpdate")
    Integer saveOrUpdateBdcSlFgyhsf(@RequestBody BdcSlFgyhsfDO bdcSlFgyhsfDO);

    /**
     * @param gzlslid 工作流实例id
     * @author <a href="mailto:sunyuzhaung@gtmap.cn">sunyuzhaung</a>
     * @description 根据工作流实例id获取不动产受理房屋信息
     */
    @GetMapping("/realestate-accept/rest/v1.0/fgyhsf/getBdcSlFgyhsf")
    BdcSlFgyhsfDO getBdcSlFgyhsf(@RequestParam(name="gzlslid") String gzlslid);

    @GetMapping("/realestate-accept/rest/v1.0/fgyhsf/getBdcSlFghysfDTO")
    BdcSlFghysfDTO getBdcSlFghysfDTO(@RequestParam(name="gzlslid") String gzlslid);


}
