package cn.gtmap.realestate.supervise.web;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.BdcXtCxLogDO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZszmDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcZszmQO;
import cn.gtmap.realestate.common.util.office.ExcelUtil;
import cn.gtmap.realestate.supervise.core.dto.LfCxycDTO;
import cn.gtmap.realestate.supervise.core.dto.LfYzhtjDTO;
import cn.gtmap.realestate.supervise.core.qo.LfCxycQO;
import cn.gtmap.realestate.supervise.core.qo.LfLogQO;
import cn.gtmap.realestate.supervise.core.qo.LfYzhQO;
import cn.gtmap.realestate.supervise.service.LfCxycjgService;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 2021/08/25
 * @description 廉防3：查询异常监管服务类
 */
@RestController
@RequestMapping("/rest/v1.0/cxycjg")
public class LfCxycjgRestController extends BaseController {
    @Autowired
    private LfCxycjgService lfCxycjgService;


    /**
     * 统计岗位查询异常（分页台账）
     * @param pageable 分页参数
     * @param lfCxycQO 业务查询参数
     * @return 异常查询统计信息
     */
    @GetMapping("/gwcxyc/table")
    public Object listGwcxycTableData(@LayuiPageable Pageable pageable, LfCxycQO lfCxycQO) {
        Page<LfCxycDTO> cxycDTOPage = lfCxycjgService.listGwcxycTableData(pageable, lfCxycQO);
        return super.addLayUiCode(cxycDTOPage);
    }

    /**
     * 统计岗位查询异常（柱状图，封装展示数据）
     * @param lfCxycQO 业务查询参数
     * @return 异常查询统计信息
     */
    @PostMapping("/gwcxyc/chart")
    public List<Map> listGwcxycChartData(@RequestBody LfCxycQO lfCxycQO) {
        return lfCxycjgService.listGwcxycChartData(lfCxycQO);
    }

    /**
     * 统计岗位查询异常数据
     * @param lfCxycQO 业务查询参数
     * @return 异常查询统计信息
     */
    @PostMapping("/gwcxyc/data")
    public List<LfCxycDTO> listGwcxycData(@RequestBody LfCxycQO lfCxycQO) {
        return lfCxycjgService.listGwcxycData(lfCxycQO);
    }

    /**
     * 相同字段重复查询异常（分页台账）
     * @param pageable 分页参数
     * @param lfCxycQO 业务查询参数
     * @return 异常查询信息
     */
    @GetMapping("/xtzdcfcxyc/table")
    public Object listXtzdcfcxycTableData(@LayuiPageable Pageable pageable, LfCxycQO lfCxycQO) {
        Page<LfCxycDTO> cxycDTOPage = lfCxycjgService.listXtzdcfcxycTableData(pageable, lfCxycQO);
        return super.addLayUiCode(cxycDTOPage);
    }

    /**
     * 统计指定字段重复查询次数
     * @param lfCxycQO 业务查询参数
     * @return {List} 统计信息
     */
    @PostMapping("/xtzdcfcxyc/chart")
    public List<LfCxycDTO> listXtzdcfcxycChartData(@RequestBody LfCxycQO lfCxycQO) {
        return lfCxycjgService.listXtzdcfcxycChartData(lfCxycQO);
    }

    /**
     * 统计指定字段重复查询次数,被查询人统计图表
     * @param lfCxycQO 业务查询参数
     * @return {List} 统计信息
     */
    @PostMapping("/xtzdcfcxyc/bcxrchart")
    public List<LfCxycDTO> listXtzdcfcxycBcxrChartData(@RequestBody LfCxycQO lfCxycQO) {
        return lfCxycjgService.listXtzdcfcxycBcxrChartData(lfCxycQO);
    }

    /**
     * 查询指定用户查询操作日志记录
     * @param pageable 分页参数
     * @param lfLogQO  查询参数
     * @return 用户查询日志
     */
    @GetMapping("/query/log")
    public Object listUserQueryLog(@LayuiPageable Pageable pageable, LfLogQO lfLogQO) {
        Page<BdcXtCxLogDO> logPage = lfCxycjgService.listUserQueryLog(pageable, lfLogQO);
        return super.addLayUiCode(logPage);
    }

    /**
     * 导出excel
     * @param excelList 导出数据
     * @param response response
     * @param title 表格名称
     * @return
     */
    @PostMapping("/export")
    public void exportExcel(@RequestParam("excelList")String excelList,HttpServletResponse response,@RequestParam("title")String title) {
        if(StringUtils.isEmpty(excelList)){
            throw new AppException("导出数据为空!");
        }

        List<List<String>>  dataList = JSONObject.parseObject(excelList,new TypeReference<List<List<String>>>(){});
        try{
            ExcelUtil.simpleExport(response,dataList,title);
        }catch (Exception e){
            throw new AppException("导出数据错误!");
        }
    }

    /**
     * 获取综合查询实体字段和汉字名称对照关系
     * @return {Map} 查询实体信息
     */
    @GetMapping("/cxstxx")
    public Map<String, Map> cxstxx() {
        Map<String, Map> result = new HashMap<>();

        for(Class clazz : new Class[]{BdcZszmQO.class, BdcZszmDTO.class}) {
            Map<String, String> map = new HashMap<>();

            Field[] fields = clazz.getDeclaredFields();
            for(Field field : fields) {
                field.setAccessible(true);
                ApiModelProperty apiModelProperty = field.getAnnotation(ApiModelProperty.class);
                map.put(field.getName(), apiModelProperty.value());
            }

            result.put(clazz.getSimpleName(), map);
        }

        return result;
    }
}