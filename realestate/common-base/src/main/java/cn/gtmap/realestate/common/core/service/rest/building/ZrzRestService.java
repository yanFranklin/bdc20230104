package cn.gtmap.realestate.common.core.service.rest.building;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-01-11
 * @description 自然幢服务
 */
public interface ZrzRestService {

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param pageable
     * @param paramJson
     * @return org.springframework.data.domain.Page<java.util.Map>
     * @description 自然幢分页查询
     */
    @PostMapping("/building/rest/v1.0/zrz/page")
    Page<Map> listZrzByPage(Pageable pageable,
                            @RequestParam(name = "paramJson",required = false) String paramJson);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param djh
     * @return int
     * @description 根据LSZD查询宗地下最大自然幢号（查询FW_LJZ FW_XMXX表）
     */
    @GetMapping("/building/rest/v1.0/zrz/maxzrzh/{djh}")
    int getMaxZrzhByDjh(@PathVariable("djh") String djh,@RequestParam(name = "qjgldm", required = false) String qjgldm);
}
