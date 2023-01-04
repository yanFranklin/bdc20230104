package cn.gtmap.realestate.building.ui.web;

import cn.gtmap.realestate.building.ui.config.LpbExportColumConfig;
import cn.gtmap.realestate.building.ui.util.ExportLpb;
import cn.gtmap.realestate.building.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.service.feign.building.LpbFeignService;
import jxl.write.WriteException;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2019/1/23
 * @description
 */
@Controller
@RequestMapping("export")
public class ExportController extends BaseController {
    @Autowired
    private LpbFeignService lpbFeignService;
    //导出楼盘表自定义配置字段和取值
    @Autowired
    LpbExportColumConfig lpbExportColumConfig;

    /**
     * 导入楼盘表页面展现
     *
     * @param fwDcbIndex
     * @return
     */
    @RequestMapping(value = "/lpb")
    public Map lpb(@NotBlank(message = "逻辑幢主键不能为空") String fwDcbIndex,String qjgldm, HttpServletResponse response) {
        List<Map<String, Object>> exportLpb = lpbFeignService.exportLpb(fwDcbIndex,qjgldm);
        String fileName = "户室信息表" + System.currentTimeMillis() + ".xls";
        try {
            ExportLpb.exportExcel(fileName, exportLpb, response, lpbExportColumConfig.getExportColumMap(), lpbExportColumConfig.getDefault0Column());
            return returnHtmlResult(true, "导出成功");
        } catch (WriteException e) {
            return returnHtmlResult(false, "导出失败");
        } catch (IOException e) {
            return returnHtmlResult(false, "导出失败");
        }
    }
}