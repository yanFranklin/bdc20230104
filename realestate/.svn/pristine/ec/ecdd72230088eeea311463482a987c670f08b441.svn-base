package cn.gtmap.realestate.building.ui.web;

import cn.gtmap.realestate.building.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.building.PrintDjdcbFeginService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcBzbZmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcBdcdyFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcPrintFeignService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @program: realestate
 * @description: 楼盘表子系统打印服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-12-07 15:24
 **/
@RestController
@RequestMapping("/print")
public class PrintController extends BaseController {
    @Autowired
    BdcPrintFeignService bdcPrintFeignService;
    @Autowired
    BdcBzbZmFeignService bdcBzbZmFeignService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcBdcdyFeignService bdcBdcdyFeignService;


    @Autowired
    private PrintDjdcbFeginService printDjdcbFeginService;

    @GetMapping("/djdcb")
    String printDjdcb(String dylx, String qjgldm, String bdcdyh) {
        if (StringUtils.isBlank(dylx) && StringUtils.isBlank(bdcdyh)) {
            throw new AppException("缺失配置打印参数");
        }
        return printDjdcbFeginService.printDjdcb(dylx, qjgldm, bdcdyh);
    }

    /**
     * @param dylx   打印类型
     * @param bdcdyh 不动产单元号
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 楼盘表页面打印登记簿查询
     * @date : 2020/12/7 15:27
     */
    @GetMapping("/lpb/djb/{dylx}/{bdcdyh}/xml")
    String printDjbcx(@PathVariable(name = "dylx") String dylx, @PathVariable(name = "bdcdyh") String bdcdyh) {
        if(StringUtils.isAnyBlank(dylx,bdcdyh)) {
            throw new AppException("楼盘表打印登记簿查询结果缺失打印类型和单元号");
        }
        Map<String, List<Map>> params = new HashMap<>(1);
        List<Map> mapList = new ArrayList<>(1);
        Map<String,String> printMap = new HashMap(1);
        printMap.put("bdcdyh",bdcdyh);

        // 证明查询编号
        String cxh = bdcBzbZmFeignService.getQszmCxh();
        if ("qjqszm".equals(dylx) || "fwqszm".equals(dylx)) {
            printMap.put("cxh", StringUtils.isBlank(cxh) ? "/" : cxh);
            printMap.put("bdclx", "2");
        }

        mapList.add(printMap);
        params.put(dylx, mapList);
        return bdcPrintFeignService.print(params);
    }

    @GetMapping("/lpb/xscq/{bdcdyh}")
    Object queryXscq(@PathVariable(name = "bdcdyh") String bdcdyh) {
        if (StringUtils.isBlank(bdcdyh)) {
            return false;
        }
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXmXscq(Collections.singletonList(bdcdyh));
        List bdcYgList = new ArrayList();
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            //查询预告的数据
            bdcYgList = bdcBdcdyFeignService.listYgByBdcdyh(bdcdyh);
        }
        return CollectionUtils.isNotEmpty(bdcXmDOList) || CollectionUtils.isNotEmpty(bdcYgList);
    }
}
