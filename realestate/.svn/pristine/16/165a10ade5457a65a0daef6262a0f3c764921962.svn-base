package cn.gtmap.realestate.common.core.service.rest.etl;

import cn.gtmap.realestate.common.core.domain.etl.HtbaQlrDO;
import cn.gtmap.realestate.common.core.dto.accept.FcjyBaxxDTO;
import cn.gtmap.realestate.common.core.vo.etl.HtbaMsrVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: realestate
 * @description: 合同备案权利人rest服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-12-21 18:02
 **/
public interface HtbaQlrRestService {

    /**
     * @param bdcdyh
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询买受人
     * @date : 2020/12/21 18:04
     */
    @GetMapping("/realestate-etl/rest/v1.0/htbaqlr/bdcdyh")
    List<HtbaQlrDO> listHtbaQlr(@RequestParam("bdcdyh") String bdcdyh);

    /**
     * @param bdcdyh
     * @return
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 商品房转移登记根据不动产单元号查询买受人（盐城）
     */
    @GetMapping("/realestate-etl/rest/v1.0/htbaqlr/{bdcdyh}")
    FcjyBaxxDTO spfzyMsr(@PathVariable(name = "bdcdyh") String bdcdyh);

    /**
     * @param bdcdyhList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 批量查询买受人信息
     * @date : 2021/3/3 17:11
     */
    @PostMapping("/realestate-etl/rest/v1.0/htbaqlr/bdcdyhs")
    List<HtbaMsrVO> listMsr(@RequestParam("bdcdyhList") List<String> bdcdyhList,@RequestParam(value = "bazt",required = false) String bazt);
}
