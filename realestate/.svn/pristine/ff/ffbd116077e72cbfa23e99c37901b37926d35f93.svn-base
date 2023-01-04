package cn.gtmap.realestate.certificate.web.maintain;

import cn.gtmap.realestate.certificate.core.service.BdcDzzzUseConditionService;
import cn.gtmap.realestate.certificate.util.DateUtil;
import cn.gtmap.realestate.certificate.web.main.DzzzController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/2/18
 */
@Controller
@Api("电子证照统计分析")
@RequestMapping(value = "/Statistic")
public class BdcDzzzStatisticController extends DzzzController {

    @Autowired
    private BdcDzzzUseConditionService dzzzUseConditionService;

    @RequestMapping(value = "/toZzxxStatistic", method = RequestMethod.GET)
    @ApiOperation(value = "电子证照统计台账展示", notes = "电子证照统计台账展示")
    public String toZzxxCzList(HttpServletRequest request, Model model) {
        logger.info("电子证照统计台账展示：{}，请求时间：{}", request.getRequestURI(), DateUtil.now());
        return "statistic/statisticQfAndZx";
    }

    @ResponseBody
    @RequestMapping(value = "/dzzzZxAndQf", method = RequestMethod.POST)
    @ApiOperation(value = "电子证照各行政区签发、注销数量", notes = "电子证照各行政区签发、注销数量")
    public String dzzzZxAndQf(HttpServletRequest request, Model model) {
        logger.info("电子证照各行政区签发、注销数量：{}，请求时间：{}", request.getRequestURI(), DateUtil.now());
        Map paramMap = new HashMap<>(2);
        return bdcDzzzZzxxService.countZzxxZxAndQf(paramMap);
    }

    @ResponseBody
    @RequestMapping(value = "/dzzzPortPercent", method = RequestMethod.POST)
    @ApiOperation(value = "电子证照各接口请求占比", notes = "电子证照各接口请求占比")
    public String dzzzPortPercent(HttpServletRequest request, Model model) {
        logger.info("电子证照各接口请求占比：{}，请求时间：{}", request.getRequestURI(), DateUtil.now());
        Map paramMap = new HashMap<>(2);
        return bdcDzzzQqjlService.countPortPercent(paramMap);
    }

    @ResponseBody
    @RequestMapping(value = "/dzzzRegionUse", method = RequestMethod.POST)
    @ApiOperation(value = "电子证照各行政区接口请求情况", notes = "电子证照各行政区接口请求情况")
    public String dzzzRegionUse(HttpServletRequest request, Model model) {
        logger.info("电子证照各行政区接口请求情况：{}，请求时间：{}", request.getRequestURI(), DateUtil.now());
        Map paramMap = new HashMap<>(2);
        return bdcDzzzQqjlService.countRegionUse(paramMap);
    }

    @ResponseBody
    @RequestMapping(value = "/dzzzUseConditionYybm", method = RequestMethod.POST)
    @ApiOperation(value = "某本电子证照被使用情况", notes = "某本电子证照被使用情况")
    public String dzzzUseConditionYybm(HttpServletRequest request, Model model, String bdcqzh) {
        logger.info("某本电子证照被使用情况：{}，请求时间：{}", request.getRequestURI(), DateUtil.now());
        return dzzzUseConditionService.countUseConditionYybm(bdcqzh);
    }

}
