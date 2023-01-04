package cn.gtmap.realestate.register.ui.web.rest;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.dto.register.BdcZxDTO;
import cn.gtmap.realestate.common.core.service.feign.register.BdcBdcZxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.register.ui.util.ExportUtils;
import cn.gtmap.realestate.register.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import jxl.write.WriteException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static cn.gtmap.realestate.register.ui.util.Constants.*;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version
 * @description 注销
 */
@RestController
@RequestMapping("/rest/v1.0/zxqd")
public class BdcZxQdController extends BaseController {

    @Autowired
    private BdcBdcZxFeignService bdcBdcZxFeignService;

    @Autowired
    private BdcZdFeignService bdcZdFeignService;

    /**
     * @param gzlslid  工作流实例id
     * @param pageable 分页对象
     * @return Object 注销清单
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @description 查询注销清单
     */
    @PostMapping(value = "/listZxQdByPage/{gzlslid}")
    @ResponseStatus(HttpStatus.OK)
    public Object listZxQdByPage(@LayuiPageable Pageable pageable, @PathVariable(value = "gzlslid") String gzlslid) {
        Sort sort = "UNSORTED".equals(String.valueOf(pageable.getSort())) ? null : pageable.getSort();
        Page<BdcZxDTO> bdcZxDTOPage = bdcBdcZxFeignService.listZxQdByPage(pageable.getPageNumber(), pageable.getPageSize(), sort,gzlslid);
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        for (int i = 0; i < bdcZxDTOPage.getContent().size(); i++) {
            if("0.0".equals(String.valueOf(bdcZxDTOPage.getContent().get(i).getFwmj()))){
                bdcZxDTOPage.getContent().get(i).setFwmj(null);
            }
            if("0.0".equals(String.valueOf(bdcZxDTOPage.getContent().get(i).getTdmj()))){
                bdcZxDTOPage.getContent().get(i).setTdmj(null);
            }

            bdcZxDTOPage.getContent().get(i).setDzwytMc(StringToolUtils.convertBeanPropertyValueOfZd(bdcZxDTOPage.getContent().get(i).getDzwyt(), zdMap.get(FWYT)));
        }

        return addLayUiCode(bdcZxDTOPage);
    }

    /**
     * @param gzlslid  受理编号
     * @return Object 注销清单
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @description 查询注销清单
     */
    @PostMapping(value = "/listZxQdAll/{gzlslid}")
    @ResponseStatus(HttpStatus.OK)
    public Object listZxQdByPage(@PathVariable(value = "gzlslid") String gzlslid) {
        List<BdcZxDTO> bdcZxDTOList = bdcBdcZxFeignService.listZxQdAll(gzlslid);
        Map<String,Object> result = new ConcurrentHashMap<>(16);
        if(CollectionUtils.isNotEmpty(bdcZxDTOList)){
            DecimalFormat df = new DecimalFormat("0.00");
            Double totalFwMj = 0.00;
            Double totalTdMj = 0.00;
            for(BdcZxDTO bdcZxDTO : bdcZxDTOList){
                BigDecimal fwmj = BigDecimal.valueOf(totalFwMj);
                BigDecimal tdmj = BigDecimal.valueOf(totalTdMj);
                double fwmjItem = null == bdcZxDTO.getFwmj() ? 0 : bdcZxDTO.getFwmj();
                totalFwMj = fwmj.add(BigDecimal.valueOf(fwmjItem)).doubleValue();
                double tdmjItem = null == bdcZxDTO.getTdmj() ? 0 : bdcZxDTO.getTdmj();
                totalTdMj = tdmj.add(BigDecimal.valueOf(tdmjItem)).doubleValue();
            }
            String qlrmc = bdcZxDTOList.stream().map(t -> t.getQlr()).filter(t->StringUtils.isNotBlank(t)).distinct().collect(Collectors.joining(","));
            result.put("slbh", bdcZxDTOList.get(0).getSlbh());
            result.put("totalFwMj", df.format(totalFwMj));
            result.put("totalTdMj", df.format(totalTdMj));
            result.put("qlr", qlrmc);
            result.put("total", bdcZxDTOList.size());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("export")
    public String exportZxQd(HttpServletResponse response, String gzlslid, String exportCol) throws IOException, WriteException {
        if (StringUtils.isNoneBlank(exportCol)) {
            //处理表头数据
            exportCol = URLDecoder.decode(exportCol, "utf-8");
            LinkedHashMap exportColMap = JSONObject.parseObject(exportCol, LinkedHashMap.class);
            //创建导出文件名
            String fileName = "注销清单导出Excel表" + System.currentTimeMillis() + ".xls";

            List<BdcZxDTO> bdcZxDTOList = bdcBdcZxFeignService.listZxQdAll(gzlslid);
            Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();

            if (CollectionUtils.isNotEmpty(bdcZxDTOList)) {
                for(BdcZxDTO bdcZxDTO : bdcZxDTOList){
                    bdcZxDTO.setDzwytMc(StringToolUtils.convertBeanPropertyValueOfZd(bdcZxDTO.getDzwyt(), zdMap.get(FWYT)));
                }
                ExportUtils.exportExcel(fileName, exportColMap, JSON.parseArray(JSON.toJSONString(bdcZxDTOList), Map.class), response, zdMap);
            } else {
                return "无清单数据，导出失败";
            }
        }
        return "导出成功";
    }

}
