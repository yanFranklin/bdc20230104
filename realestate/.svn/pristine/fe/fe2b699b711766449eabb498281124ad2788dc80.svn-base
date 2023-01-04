package cn.gtmap.realestate.natural.ui.web.rest;

import cn.gtmap.realestate.common.core.service.feign.natural.ZrzyDrExcelCxFeignService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:qianguoyi@gtmap.cn">qianguoyi</a>
 * @version 1.0
 * @date 2021/7/2 16:05
 * @description 导入excele查询
 */
@RestController
@Api(tags = "导入excele查询")
@RequestMapping(value = "/rest/v1.0/drcx")
public class ZrzyDrExcelCxController {

    @Autowired
    ZrzyDrExcelCxFeignService zrzyDrExcelCxFeignService;


    @PostMapping("/excelCx")
    public Map excelCx(@RequestBody Map<String, List<String>> stringListMap) {
        return zrzyDrExcelCxFeignService.excelCx(stringListMap);
    }
}
