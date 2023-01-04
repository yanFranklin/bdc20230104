package cn.gtmap.realestate.inquiry.ui.web.rest.yancheng;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.inquiry.BdcXtLscsDO;
import cn.gtmap.realestate.common.core.domain.inquiry.BdcZqDjDO;
import cn.gtmap.realestate.common.core.domain.inquiry.BdcZqThxxDO;
import cn.gtmap.realestate.common.core.domain.inquiry.BdcZqZxsqDO;
import cn.gtmap.realestate.common.core.dto.ExcelExportDTO;
import cn.gtmap.realestate.common.core.dto.ExcelExportSheetsDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.yancheng.BdcZqDjDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.yancheng.BdcZqXxhzDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.yancheng.BdcZqZxsqDTO;
import cn.gtmap.realestate.common.core.dto.pub.ResponseHeadEntityDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzYzQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcGzyzFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcXtLscsFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.yancheng.BdcZqFeignService;
import cn.gtmap.realestate.common.core.support.excel.ExcelController;
import cn.gtmap.realestate.common.core.support.excel.ExcelExpandService;
import cn.gtmap.realestate.common.matcher.ZipkinAuditEventRepositoryMatcher;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2020/12/16
 * @description 盐城征迁系统服务前端接口
 */
@RestController
@RequestMapping("/rest/v1.0/zq")
public class BdcZqController extends BaseController {
    @Autowired
    private BdcZqFeignService bdcZqFeignService;
    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    private BdcQlrFeignService bdcQlrFeignService;
    @Autowired
    private UserManagerUtils userManagerUtils;
    @Autowired
    private  ZipkinAuditEventRepositoryMatcher zipkin;
    @Autowired
    private BdcGzyzFeignService bdcGzyzFeignService;
    @Autowired
    private BdcXtLscsFeignService bdcXtLscsFeignService;
    @Autowired
    private ExcelController excelController;

    /**
     * 征迁注销申请审核通过后在登记发起的注销流程工作流定义ID
     */
    @Value("${yancheng.zq.djzxlc:}")
    private String djzxlc;

    /**
     * 征迁注销流程允许被领取的角色编码，英文逗号间隔
     */
    @Value("${yancheng.zq.role:}")
    private String roleCodes;


    /**
     * 获取项目信息
     * @param xmid 项目ID
     * @return BdcXmDO
     */
    @GetMapping("/xmxx")
    public BdcXmDO getBdcXmDO(@RequestParam("xmid")String xmid) {
        if(StringUtils.isBlank(xmid)) {
            throw new AppException("未定义查询参数xmid");
        }

        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setXmid(xmid);
        List<BdcXmDO> xmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if(CollectionUtils.isEmpty(xmDOList)) {
            return null;
        }
        return xmDOList.get(0);
    }

    /**
     *
     * 获取项目信息
     * @param xmid 项目ID
     * @return BdcXmDO
     */
    @GetMapping("/qlr")
    public List<BdcQlrDO> getBdcQlrByXmid(@RequestParam("xmid")String xmid) {
        if(StringUtils.isBlank(xmid)) {
            throw new AppException("未定义查询参数xmid");
        }

        BdcQlrQO bdcQlrQO = new BdcQlrQO();
        bdcQlrQO.setXmid(xmid);
        return bdcQlrFeignService.listBdcQlr(bdcQlrQO);
    }

    /**
     * 保存注销申请信息
     * @param bdcZqZxsqDO 注销申请信息
     * @return String 注销申请信息ID
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @PostMapping("/zxsq")
    public String saveZxsq(@RequestBody BdcZqZxsqDO bdcZqZxsqDO) {
        if(null == bdcZqZxsqDO) {
            throw new AppException("未定义注销申请信息");
        }
        return bdcZqFeignService.saveZxsq(bdcZqZxsqDO);
    }

    /**
     * 获取注销申请信息
     * @param sqxxid 注销申请信息ID
     * @return BdcZqZxsqDO 注销申请信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @GetMapping("/zxsq")
    public BdcZqZxsqDO getZxsq(@RequestParam("sqxxid")String sqxxid) {
        if(StringUtils.isBlank(sqxxid)) {
            throw new AppException("未定义注销申请查询参数");
        }
        return bdcZqFeignService.getZxsq(sqxxid);
    }


    /**
     * 删除注销申请
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param bdcZqZxsqDTO
     */
    @PostMapping("/zxsq/delete")
    public void deleteZxsq(@RequestBody BdcZqZxsqDTO bdcZqZxsqDTO) {
        if(StringUtils.isEmpty(bdcZqZxsqDTO.getSqxxid())){
            throw new AppException("sqxxid不能为空!");
        }
        UserDto userDto = userManagerUtils.getCurrentUser();
        bdcZqZxsqDTO.setScr(userDto.getAlias());
        bdcZqZxsqDTO.setScrid(userDto.getId());
        bdcZqZxsqDTO.setScsj(new Date());
        bdcZqZxsqDTO.setShzt(4);
        bdcZqFeignService.updateZxsq(bdcZqZxsqDTO);
    }

    /**
     * 提交注销申请 更新审核状态为已提交
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param bdcZqZxsqDTO
     */
    @PostMapping("/zxsq/shzt/update")
    public void updateZxsqShzt(@RequestBody BdcZqZxsqDTO bdcZqZxsqDTO) {
        if(StringUtils.isEmpty(bdcZqZxsqDTO.getSqxxid())){
            throw new AppException("sqxxid不能为空!");
        }
        if(Objects.isNull(bdcZqZxsqDTO.getShzt())){
            throw new AppException("shzt不能为空!");
        }
        bdcZqFeignService.updateZxsq(bdcZqZxsqDTO);
    }

    /**
     * 冻结信息 解冻
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param bdcZqDjDOS
     */
    @PostMapping("/dj/djzt/batch/update")
    public void updateDjDjzt(@RequestBody List<BdcZqDjDO> bdcZqDjDOS) {
        if(CollectionUtils.isEmpty(bdcZqDjDOS)) {
            throw new AppException("未定义需要冻结的数据");
        }

        UserDto userDto = userManagerUtils.getCurrentUser();
        String jdr = null == userDto ? "" : userDto.getAlias();
        String jdrid = null == userDto ? "" : userDto.getId();
        bdcZqDjDOS.forEach(bdcZqDjDO -> {
            bdcZqDjDO.setJdr(jdr);
            bdcZqDjDO.setJdrid(jdrid);
            bdcZqDjDO.setJdsj(new Date());
            bdcZqDjDO.setDjzt(2);
        });
        bdcZqFeignService.batchUpdateDj(bdcZqDjDOS);
    }

    /**
     * 新建附件文件夹
     * @param sqxxid 注销申请信息ID
     */
    @GetMapping("/zxsq/fjcl")
    public String createZxsqFjcl(@RequestParam("sqxxid") String sqxxid) {
        if(StringUtils.isBlank(sqxxid)) {
            return "";
        }
        return bdcZqFeignService.createZxsqFjcl(sqxxid);
    }

    /**
     * 锁定不动产单元和证书
     * @param bdcZqZxsqDO 注销申请信息
     */
    @PostMapping("/zxsq/sd")
    public void sdBdcdyZs(@RequestBody BdcZqZxsqDO bdcZqZxsqDO) {
        if(null == bdcZqZxsqDO || StringToolUtils.isAllNullOrEmpty(bdcZqZxsqDO.getBdcdyh(), bdcZqZxsqDO.getBdcqzh())) {
            throw new AppException("未定义锁定单元或证号数据！");
        }
        bdcZqFeignService.sdBdcdyZs4Zx(bdcZqZxsqDO);
    }

    /**
     * 创建注销项目前规则验证
     * @param bdcZqZxsqDO 注销申请信息
     * @return List 验证结果
     */
    @PostMapping("/zxsq/gzyz")
    public List<Map<String, Object>> gzyz(@RequestBody BdcZqZxsqDO bdcZqZxsqDO) {
        if(null == bdcZqZxsqDO || StringToolUtils.isAllNullOrEmpty(bdcZqZxsqDO.getXmid(), bdcZqZxsqDO.getBdcdyh(), bdcZqZxsqDO.getBdcqzh())) {
            throw new AppException("未获取到需要验证的项目参数！");
        }

        List<Map<String, Object>> paramList = new ArrayList<>();
        Map<String, Object> paramMap = new HashedMap();
        paramMap.put("yxmid", bdcZqZxsqDO.getXmid());
        paramMap.put("bdcdyh", bdcZqZxsqDO.getBdcdyh());
        paramMap.put("bdcqzh", bdcZqZxsqDO.getBdcqzh());
        paramList.add(paramMap);

        BdcGzYzQO bdcGzYzQO = new BdcGzYzQO();
        bdcGzYzQO.setZhbs(djzxlc + CommonConstantUtils.LC_ZHGZBS_XZBDCDY);
        bdcGzYzQO.setParamList(paramList);
        return bdcGzyzFeignService.yzBdcgz(bdcGzYzQO);
    }

    /**
     * 征迁审批完，在登记查看注销申请台账 新建注销项目
     * @param bdcZqZxsqDTO 注销申请信息
     * @return BdcXmDO 项目信息
     */
    @PostMapping("/zxsq/process")
    public BdcXmDO createProcess(@RequestBody BdcZqZxsqDTO bdcZqZxsqDTO) {
        if(null == bdcZqZxsqDTO || StringUtils.isAnyBlank(bdcZqZxsqDTO.getXmid(), djzxlc)) {
            throw new AppException("未定义新建注销项目所需参数信息");
        }

        bdcZqZxsqDTO.setLcbs(djzxlc);
        bdcZqZxsqDTO.setRoleCodes(roleCodes);
        return bdcZqFeignService.createProcess(bdcZqZxsqDTO);
    }

    /**
     * 忽略规则验证提示记录日志
     * @param data 规则提示信息
     */
    @PostMapping("/gzyz/addIgnoreLog")
    public void addIgnoreLog(@RequestBody Map<String, Object> data) {
        UserDto userDto = userManagerUtils.getCurrentUser();
        Map<String, Object> map = new HashMap<>();
        map.put(CommonConstantUtils.VIEW_TYPE, CommonConstantUtils.INQUIRY_DM);
        map.put(CommonConstantUtils.VIEW_TYPE_NAME, CommonConstantUtils.HLLX);
        map.put(CommonConstantUtils.ALIAS, userDto != null ? userDto.getAlias() : userManagerUtils.getCurrentUserName());
        map.put(CommonConstantUtils.HLNR, data);
        AuditEvent auditEvent = new AuditEvent(userManagerUtils.getCurrentUserName(), CommonConstantUtils.GZYZ_HL, map);
        zipkin.add(auditEvent);
    }

    @PostMapping("/txfx")
    public void txfx(@RequestParam(value = "data", required = false) String data,
                     @RequestParam(value = "general", required = false) String general) {
        if (StringUtils.isNotBlank(data)) {
            LOGGER.info("图形分析返回接口数据data值{}", JSONObject.toJSONString(data));
        } else {
            LOGGER.info("图形分析返回接口数据data值为空");
        }
        if (StringUtils.isNotBlank(general)) {
            LOGGER.info("图形分析返回接口数据,general值{}", JSONObject.toJSONString(general));
        } else {
            LOGGER.info("图形分析返回接口数据general值为空");
        }
    }

    /**
     * 征迁在审核通过台账退回功能请求
     * @param bdcZqThxxDO 退回信息
     * @return 退回信息ID
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @PostMapping("/zxsq/th")
    public String zxsqTh(@RequestBody BdcZqThxxDO bdcZqThxxDO) {
        if(null == bdcZqThxxDO || StringUtils.isEmpty(bdcZqThxxDO.getSqxxid())){
            throw new AppException("参数信息为空!");
        }
        return bdcZqFeignService.zxsqTh(bdcZqThxxDO);
    }

    /**
     * 单元冻结
     * @param zqDjDOList 冻结信息
     * @return 返回状态
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @PostMapping("/dydj")
    public ResponseHeadEntityDTO dydj(@RequestBody List<BdcZqDjDO> zqDjDOList) {
        if(CollectionUtils.isEmpty(zqDjDOList)) {
            throw new AppException("未定义冻结参数");
        }
        return bdcZqFeignService.djBdcdy(zqDjDOList);
    }

    /**
     * 单元冻结（全部冻结）
     * @param bdcZqDjDTO 参数信息
     * @return 返回状态
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @PostMapping("/dydj/qbdj")
    public ResponseHeadEntityDTO dydjQbdj(@RequestBody BdcZqDjDTO bdcZqDjDTO) {
        return bdcZqFeignService.dydjQbdj(bdcZqDjDTO);
    }

    /**
     * 生成冻结文号
     * @return 冻结文号
     */
    @GetMapping("/djwh")
    public String getDjwh() {
        return bdcZqFeignService.getDjwh();
    }

    /**
     * 移除不需要冻结的记录
     * @param bdcXtLscsDO 单元信息
     * @return 记录数
     */
    @PostMapping("/dydj/ycjl")
    public int ycjl(@RequestBody BdcXtLscsDO bdcXtLscsDO) {
        if(null == bdcXtLscsDO || StringToolUtils.isAllNullOrEmpty(bdcXtLscsDO.getCsmc(), bdcXtLscsDO.getCsz())) {
            throw new AppException("参数存在空");
        }
        return bdcXtLscsFeignService.deleteRecords(Arrays.asList(bdcXtLscsDO));
    }

    /**
     * 导出查询指定区域不动产信息汇总统计Excel
     * @param response
     */
    @PostMapping(value = "/cqcx/xxhz")
    public void xxhzExcel(HttpServletResponse response, @ModelAttribute BdcZqXxhzDTO bdcZqXxhzDTO) {
        if(null == bdcZqXxhzDTO || StringUtils.isBlank(bdcZqXxhzDTO.getParam())) {
            throw new MissingArgumentException("导出汇总表异常：未定义参数");
        }

        ExcelExportSheetsDTO sheetsDTO = new ExcelExportSheetsDTO();
        sheetsDTO.setFileName("指定区域不动产信息汇总");
        sheetsDTO.setSheets(new ArrayList<>());

        // 第一个工作表：指定区域不动产信息汇总
        ExcelExportDTO excelExportDTO = bdcZqFeignService.xxhzExcel(bdcZqXxhzDTO);
        this.setXxhzExcelExpandService(excelExportDTO);
        sheetsDTO.getSheets().add(excelExportDTO);

        // 第二个工作表：指定区域内的现势建设用地使用权信息
        ExcelExportDTO excelExportDTO2 = bdcZqFeignService.xxhzJsydsyqExcel(bdcZqXxhzDTO);
        this.setXxhzJsydsyqExcelExpandService(excelExportDTO2);
        sheetsDTO.getSheets().add(excelExportDTO2);

        excelController.exportExcelWithSheets(response, sheetsDTO);
    }

    /**
     * 批量新增注销申请信息
     * @param bdcZqZxsqDOS 注销申请信息
     * @return String 注销申请编号
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     */
    @PostMapping("/batch/zxsq")
    public String batchInsertZxsq(@RequestBody List<BdcZqZxsqDO> bdcZqZxsqDOS) {
        if(CollectionUtils.isEmpty(bdcZqZxsqDOS)) {
            throw new AppException("未定义注销申请信息");
        }
        return bdcZqFeignService.batchInsertZxsq(bdcZqZxsqDOS);
    }

    /**
     * 盐城征迁信息汇总导出Excel“指定区域不动产信息汇总”工作表扩展逻辑
     * @param excelExportDTO Excel导出信息实体
     */
    private void setXxhzExcelExpandService(ExcelExportDTO excelExportDTO) {
        excelExportDTO.setExpandServices(Arrays.asList(new ExcelExpandService() {
            @Override
            public void expand(HSSFWorkbook workbook, HSSFSheet sheet, ExcelExportDTO excelExportDTO) {
                List<HashMap> excelData = JSON.parseArray(excelExportDTO.getData(), HashMap.class);
                List<Integer> rowsIndexs = new ArrayList<>();
                for(int i = 0; i < excelData.size(); i++) {
                    // 面积汇总行的名称“合计建筑面积”在房屋用途这一列
                    if(StringUtils.equals("合计建筑面积", StringToolUtils.valueOf(excelData.get(i).get("FWYT"), ""))) {
                        rowsIndexs.add(i + 2);
                    }
                }

                // 开头部分行标题横向合并处理
                // 2,2,0,13 表示第3行到第3行中第1到第14列合并
                List<String> mergedIndexs = Arrays.asList("2,2,0,12", "3,3,2,5", "3,3,6,11", "3,3,12,13", "3,4,1,1");
                mergeRegion(sheet, mergedIndexs);

                // 删除多余标题
                sheet.removeRow(sheet.getRow(1));

                // 汇总行空值列横向合并
                for(Integer rowIndex : rowsIndexs) {
                    mergedIndexs = Arrays.asList(rowIndex + "," + rowIndex + ",0,8", rowIndex + "," + rowIndex + ",11,13");
                    mergeRegion(sheet, mergedIndexs);
                }
            }
        }));
    }

    /**
     * 盐城征迁信息汇总导出Excel“指定区域不动产信息汇总”工作表扩展逻辑
     * @param excelExportDTO Excel导出信息实体
     */
    private void setXxhzJsydsyqExcelExpandService(ExcelExportDTO excelExportDTO) {
        excelExportDTO.setExpandServices(Arrays.asList(new ExcelExpandService() {
            @Override
            public void expand(HSSFWorkbook workbook, HSSFSheet sheet, ExcelExportDTO excelExportDTO) {
                // 开头部分行标题横向合并处理
                // 2,2,0,13 表示第3行到第3行中第1到第14列合并
                List<String> mergedIndexs = Arrays.asList("2,2,0,11", "3,3,1,4", "3,3,5,10", "3,3,11,12");
                mergeRegion(sheet, mergedIndexs);

                // 删除多余标题
                sheet.removeRow(sheet.getRow(1));
            }
        }));
    }

    /**
     * 征迁汇总Excel横向合并操作
     * @param sheet 工作表
     * @param indexList 合并列序号
     */
    private void mergeRegion(HSSFSheet sheet, List<String> indexList) {
        for(String index : indexList) {
            String[] array = index.split(",");
            sheet.addMergedRegion(new CellRangeAddress(Integer.parseInt(array[0]), Integer.parseInt(array[1]), Integer.parseInt(array[2]), Integer.parseInt(array[3])));
        }
    }
}
