package cn.gtmap.realestate.common.core.service.rest.engine;

import cn.gtmap.realestate.common.core.dto.engine.BdcGzZhgzDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 2019/3/7 19:41
 * @description 文件的导出
 */

public interface BdcGzFileRestService {

    /**
     * 导出文件
     *
     * @param response response
     * @param text     text
     * @param mc       mc
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @GetMapping("/realestate-engine/rest/v1.0/File/exportTxt")
    void exportTxt(HttpServletResponse response,
                   @RequestParam(name = "text", required = true) String text,
                   @RequestParam(name = "mc", required = true) String mc);

    /**
     * 根据组合规则id查找和组织数据
     *
     * @param zhid zhid
     * @param response  response
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @GetMapping("/realestate-engine/rest/v1.0/File/searchData")
    void searchData(HttpServletResponse response, @RequestParam(name = "zhid", required = true) String zhid);

    /**
     * 根据组合id查询dto
     *
     * @param zhid
     * @return BdcGzZhgzDTO
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @GetMapping("/realestate-engine/rest/v1.0/File/queryZhgzDTO")
    BdcGzZhgzDTO queryZhgzDTO(@RequestParam(name = "zhid", required = true) String zhid);

}
