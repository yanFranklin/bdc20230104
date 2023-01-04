package cn.gtmap.realestate.etl.web.main;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskClient;
import cn.gtmap.gtc.workflow.domain.manage.CategoryProcessDto;
import cn.gtmap.gtc.workflow.domain.manage.ProcessDefData;
import cn.gtmap.gtc.workflow.domain.manage.TaskData;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.BdcDjxlDjyyGxDO;
import cn.gtmap.realestate.common.core.dto.ExcelExportDTO;
import cn.gtmap.realestate.common.core.dto.OfficeExportDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.etl.WwyyQO;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcCshXtPzFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.excel.ExcelController;
import cn.gtmap.realestate.common.core.support.pdf.PdfController;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.PageUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.common.util.WorkFlowUtils;
import cn.gtmap.realestate.common.util.configuration.BdcConfigUtils;
import cn.gtmap.realestate.etl.core.domian.wwsq.GxWwSqxxFjxxDo;
import cn.gtmap.realestate.etl.core.dto.WwsqExportDTO;
import cn.gtmap.realestate.etl.core.qo.WwsqHtmlQO;
import cn.gtmap.realestate.etl.core.qo.WwsqQO;
import cn.gtmap.realestate.etl.core.service.HlwYwxxDataService;
import cn.gtmap.realestate.etl.service.HlwYwxxService;
import cn.gtmap.realestate.etl.util.Constants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

import static cn.hutool.core.collection.CollUtil.newArrayList;


/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/6/9
 * @description 外网申请
 */
@Controller
@RequestMapping("/realestate-etl/wwsq")
public class WwsqController {

    private static final Logger logger = LoggerFactory.getLogger(WwsqController.class);

    @Autowired
    private HlwYwxxService hlwYwxxService;

    @Autowired
    private HlwYwxxDataService hlwYwxxDataService;

    @Autowired
    private WorkFlowUtils workFlowUtils;


    @Autowired
    private PdfController pdfController;

    @Autowired
    BdcZdFeignService bdcZdFeignService;

    @Autowired
    protected UserManagerUtils userManagerUtils;

    @Autowired
    private ProcessTaskClient processTaskClient;

    @Autowired
    BdcCshXtPzFeignService bdcCshXtPzFeignService;

    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;


    @Autowired
    private ExcelController excelController;


    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 打印模板地址
     */
    @Value("${print.path:/usr/local/bdc3/print/}")
    private String printPath;

    /**
     * @param wwsqQO 查询参数
     * @return 互联网业务信息分页
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取外网申请分页
     */
    @ResponseBody
    @GetMapping("/page")
    public Object listHlwYwxxByPage(@LayuiPageable Pageable pageable, WwsqQO wwsqQO) {
        return PageUtils.addLayUiCode(hlwYwxxService.listHlwYwxxByPage(pageable, wwsqQO));
    }

    /**
     * 根据用户角色查询互联网业务信息
     *
     * @param pageable 分页参数
     * @param wwsqQO   外网申请QO
     * @return 互联网业务信息分页
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @GetMapping("/page/role")
    public Object listHlwYwxxByPageWithRole(@LayuiPageable Pageable pageable, WwsqQO wwsqQO) {
        logger.info("互联网业务信息分页查询入参：{}", wwsqQO);
        wwsqQO.setQxdmList(Container.getBean(BdcConfigUtils.class).qxdmFilter("wwsq", "", wwsqQO.getModuleCode()));
        wwsqQO.setSortParameter("sfcj asc,shzt asc nulls first,wwslbh desc nulls first");
        String userName = this.userManagerUtils.getCurrentUserName();
        if (StringUtils.isBlank(userName)) {
            throw new AppException(ErrorCode.CUSTOM, "未获取到当前人员登录信息");
        }
        Set<String> processList = this.queryAvailableProcessByUser(userName);

        // 判断当前人员拥有的流程权限中是否包含查询的工作流定义id
        if (CollectionUtils.isNotEmpty(processList)) {
            // 1、查询条件中，没有工作流定义id，则默认查询当前人拥有的所有流程
            if (StringUtils.isBlank(wwsqQO.getGzldyid())) {
                wwsqQO.setGzldyids(new ArrayList<>(processList));
            } else {
                if (processList.contains(wwsqQO.getGzldyid())) {
                    // 2、查询条件中，有工作流定义ID,并且是当前人拥有的流程，则根据查询条件进行查询
                    wwsqQO.setGzldyids(Arrays.asList(wwsqQO.getGzldyid()));
                } else {
                    // 3、查询条件中，有工作流定义ID，但不在当前人拥有的流程内，则返回空数据
                    return PageUtils.addLayUiCode(null);
                }
            }
            return PageUtils.addLayUiCode(hlwYwxxService.listHlwYwxxByPage(pageable, wwsqQO));
        }
        return PageUtils.addLayUiCode(null);
    }

    /**
     * 获取当前人员可以查询的流程
     *
     * @param userName 用户名称
     * @return 工作流实例ID集合
     */
    private Set<String> queryAvailableProcessByUser(String userName) {
        List<CategoryProcessDto> list = processTaskClient.listCategoryProcess(userName);
        Set<String> processDefKeySet = new HashSet<>(10);
        if (CollectionUtils.isNotEmpty(list)) {
            handlerProcess(processDefKeySet, list);
        }
        return processDefKeySet;
    }

    /**
     * 递归方法，获取工作定义ID
     */
    private void handlerProcess(Set<String> processDefKeys, List<CategoryProcessDto> categoryProcessDtoList) {
        for (CategoryProcessDto categoryProcessDto : categoryProcessDtoList) {
            if (CollectionUtils.isNotEmpty(categoryProcessDto.getProcessList())) {
                processDefKeys.addAll(
                        categoryProcessDto.getProcessList().stream().map(ProcessDefData::getProcessDefKey).collect(Collectors.toList()));
            }
            if (CollectionUtils.isNotEmpty(categoryProcessDto.getChildren())) {
                handlerProcess(processDefKeys, categoryProcessDto.getChildren());
            }
        }
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取工作流定义名称
     */
    @GetMapping("/gzldymcs")
    @ResponseBody
    @ApiOperation(value = "获取工作流定义名称")
    public Object queryGzldymcs() {
        List<ProcessDefData> processDefDataList = workFlowUtils.getAllProcessDefData();
        processDefDataList = processDefDataList.stream().filter(processDefData ->
                processDefData.getSuspensionState() == 1
        ).collect(Collectors.toList());
        return processDefDataList;
    }

    /**
     * @param hlwxmid 互联网项目ID
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 外网创建登记业务
     */
    @PutMapping("/cjBdcXm")
    @ResponseBody
    @ApiOperation(value = "外网创建登记业务")
    public Object cjBdcXm(@RequestParam(name = "hlwxmid") String hlwxmid, @RequestParam(name = "wwslbh", required = false) String wwslbh) {
        //删除原有失败创建记录
        //hlwYwxxDataService.deleteSbCjjl(hlwxmid);
        String slr = userManagerUtils.getCurrentUserName();
        Map<String, Object> cjResponse = hlwYwxxService.cjBdcXm(hlwxmid, wwslbh, slr);
        //如果流程当前受理人与登录用户一致，打开任务待办，否则打开项目列表资源
        if (MapUtils.isNotEmpty(cjResponse) && cjResponse.get("gzlslid") != null && StringUtils.isNotBlank(cjResponse.get("gzlslid").toString())) {
            String gzlslid = cjResponse.get("gzlslid").toString();
            List<TaskData> listTask = processTaskClient.processRunningTasks(gzlslid);
            if (CollectionUtils.isNotEmpty(listTask) && StringUtils.equals(slr, listTask.get(0).getTaskAssigin())) {
                logger.info("wwslbh:{}当前办理人:{}登录人：{}", wwslbh, listTask.get(0).getTaskAssigin(), slr);
                cjResponse.put("xmtype", "todo");
                cjResponse.put("taskid", listTask.get(0).getTaskId());
                return cjResponse;
            }
        }
        cjResponse.put("xmtype", "lsgx");
        return cjResponse;

    }


    /**
     * 删除项目
     *
     * @param hlwxmid 互联网项目ID
     * @return 附件信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping("/delete")
    @ResponseBody
    public void delete(@RequestParam(name = "hlwxmid") String hlwxmid) {
        if (StringUtils.isBlank(hlwxmid)) {
            throw new MissingArgumentException("互联网项目 id 或外网受理编号不能为空！");
        }
        // 删除附件
        hlwYwxxDataService.deleteXmxx(hlwxmid);
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 导出word
     */
    @PostMapping("/export")
    public void exportWwsqxx(HttpServletResponse response, @ModelAttribute WwsqExportDTO wwsqExportDTO) {
        if (StringUtils.isAnyBlank(wwsqExportDTO.getWwxmid(), wwsqExportDTO.getDylx())) {
            logger.error("外网申请信息导出缺少参数,wwxmid:{},dylx:{}", wwsqExportDTO.getWwxmid(), wwsqExportDTO.getDylx());
            throw new MissingArgumentException("外网申请信息导出缺少参数");
        }
        // 获取打印数据
        String xmlData = hlwYwxxService.getHlwSqxxPrintXml(wwsqExportDTO);
        logger.info("外网申请信息导出，打印数据：{}", xmlData);
        // 导出word文件
        OfficeExportDTO pdfExportDTO = new OfficeExportDTO();
        pdfExportDTO.setModelName(printPath + wwsqExportDTO.getDylx() + CommonConstantUtils.WJHZ_DOCX);
        pdfExportDTO.setFileName("申请书");
        pdfExportDTO.setXmlData(xmlData);
        pdfController.exportWord(response, pdfExportDTO);
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description
     */
    @PostMapping("/export/fj")
    public void exportFjxx(HttpServletResponse response, @RequestParam(name = "hlwxmid") String hlwxmid, @RequestParam(name = "wwslbh") String wwslbh) throws Exception {
        if (StringUtils.isBlank(hlwxmid) || StringUtils.isBlank(wwslbh)) {
            throw new MissingArgumentException("互联网项目 id 或外网受理编号不能为空！");
        }
        //总目录
        String parentPath = printPath + "temp" + File.separator + wwslbh;
        //生成外网申请附件
        String zipPath = hlwYwxxService.generateWwsqFjxx(hlwxmid, wwslbh, parentPath);
        if (StringUtils.isBlank(zipPath)) {
            logger.error("系统导出附件失败，因为未成功生成ZIP文件，目标文件名：{}", wwslbh);
            throw new AppException("系统导出附件失败，因为未成功生成ZIP文件");
        }
        File zipFile = new File(zipPath);

        //浏览器下载
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            // 设置浏览器下载
            String file = URLEncoder.encode(wwslbh + ".zip", "utf-8");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + file);
            // 读取临时文件，浏览器下载
            FileUtils.copyFile(zipFile, outputStream);

        } catch (Exception e) {
            logger.error("系统导出附件报错：", e);
            throw new AppException("系统导出附件报错，处理终止");
        } finally {
            IOUtils.closeQuietly(outputStream);
            //删除临时文件
            File parentfile = new File(parentPath);
            FileUtils.deleteDirectory(parentfile);
            FileUtils.deleteQuietly(zipFile);
        }
    }


    /**
     * @param wwsqHtmlQO
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 外网申请信息
     */
    @ResponseBody
    @GetMapping("")
    public Object queryWwsqDTO(WwsqHtmlQO wwsqHtmlQO) {
        return hlwYwxxService.queryWwsqDTO(wwsqHtmlQO);
    }

    @ResponseBody
    @GetMapping("/qlr")
    public Object queryWwsqQlr(String qlrid) {
        if (StringUtils.isNotBlank(qlrid)) {
            return hlwYwxxDataService.queryGxWwSqxxQlrByQlrid(qlrid);

        }
        return null;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取字典项
     */
    @GetMapping("/zd")
    @ResponseBody
    public Map<String, List<Map>> listZd() {
        return bdcZdFeignService.listBdcZd();
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 退回
     */
    @PostMapping("/th")
    @ResponseBody
    public void sqxxTh(@RequestParam(name = "hlwxmid") String hlwxmid, @RequestParam(name = "thyy") String thyy) {
        //回写退回原因和状态
        JSONObject hxObject = new JSONObject();
        hxObject.put("xmid", hlwxmid);
        hxObject.put("byslyy", thyy);
        hxObject.put("shzt", Constants.SHZT_ABANDON);
        UserDto userDto = userManagerUtils.getCurrentUser();
        if (userDto != null) {
            hxObject.put("czrmc", userDto.getAlias());
        }
        hlwYwxxService.hxDjxx(hxObject);

    }

    /**
     * 获取所有附件内容信息
     *
     * @param hlwxmid 互联网项目ID
     * @param wwslbh  外网受理编号
     * @return 附件信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping("/files/all")
    @ResponseBody
    public Object queryFjxx(@RequestParam(name = "hlwxmid") String hlwxmid, @RequestParam(name = "wwslbh") String wwslbh) {
        if (StringUtils.isBlank(hlwxmid) || StringUtils.isBlank(wwslbh)) {
            throw new MissingArgumentException("互联网项目 id 或外网受理编号不能为空！");
        }
        // 获取所有附件内容信息
        return hlwYwxxDataService.listGxWwSqxxClxxByXmid(hlwxmid);

    }

    /**
     * 获取附件信息并返回文件流
     *
     * @param fjid 附件信息ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping("/fjxx/download")
    public void fjxxDownload(HttpServletResponse response, @RequestParam(name = "fjid") String fjid) throws Exception {
        if (StringUtils.isNotBlank(fjid)) {
            GxWwSqxxFjxxDo gxWwSqxxFjxxDo = this.hlwYwxxDataService.queryGxWwSqxxFjxxByFjid(fjid);
            if (Objects.nonNull(gxWwSqxxFjxxDo)) {
                try (InputStream inputStream = this.hlwYwxxService.getHlwFjxxStream(gxWwSqxxFjxxDo);
                     OutputStream outputStream = response.getOutputStream();) {
                    if (Objects.nonNull(inputStream)) {
                        // 设置浏览器下载
                        String contentType = new MimetypesFileTypeMap().getContentType(new File(gxWwSqxxFjxxDo.getFjmc()));
                        response.setContentType(contentType);
                        IOUtils.copy(inputStream, outputStream);
                    }
                } catch (Exception e) {
                    throw new AppException("获取附件信息异常," + e.getMessage());
                }
            }
        }
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据工作流定义ID获取登记原因
     */
    @GetMapping("/djyy")
    @ResponseBody
    @ApiOperation(value = "根据工作流定义ID获取登记原因")
    public Object queryDjyyList(BdcDjxlDjyyGxDO bdcDjxlDjyyQO) {
        return bdcCshXtPzFeignService.listBdcDjxlDjyyGx(bdcDjxlDjyyQO);

    }


    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 分页查询外网预约台账
     * @date : 2022/9/1 16:25
     */
    @ResponseBody
    @GetMapping("/yydj/page")
    public Object queryWwyydjPage(@LayuiPageable Pageable pageable, WwyyQO wwyyQO) {
        wwyyQO.setPage(String.valueOf(pageable.getPageNumber() + 1));
        wwyyQO.setSize(String.valueOf(pageable.getPageSize()));
        logger.warn("分页查询外网业务数据接口入参{}", JSON.toJSONString(wwyyQO));
        Page page = PageUtils.listToPageWithTotal(newArrayList(), pageable, 0);
        Object response = exchangeInterfaceFeignService.requestInterface("wwsq_queryYyByPage", wwyyQO);
        if (Objects.nonNull(response)) {
            Map resultMap = (Map) response;
            //判断接口是否成功
            Map headMap = MapUtils.getMap(resultMap, "head");
            Map dataMap = MapUtils.getMap(resultMap, "data");
            if (MapUtils.isNotEmpty(headMap) && StringUtils.equals("0000", MapUtils.getString(headMap, "code", ""))) {
                if (MapUtils.isNotEmpty(dataMap)) {
                    Integer totalNum = MapUtils.getInteger(dataMap, "", 0);
                    //转成数组
                    List result = JSON.parseArray(JSON.toJSONString(MapUtils.getObject(dataMap, "yyxxList")));
                    if (CollectionUtils.isNotEmpty(result)) {
                        page = PageUtils.listToPageWithTotal(result, pageable, totalNum);
                        return PageUtils.addLayUiCode(page);
                    }
                }
            }
        }
        return PageUtils.addLayUiCode(page);
    }

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 导出勾选数据
     * @date : 2022/9/1 16:34
     */
    @PostMapping("/yydj/export")
    public void exportWwyyExcel(HttpServletResponse response, @ModelAttribute ExcelExportDTO excelExportDTO) {
        excelController.exportExcel(response, excelExportDTO);
    }

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 导出全部数据
     * @date : 2022/9/1 16:34
     */
    @GetMapping("/yydj/export/all")
    @ResponseBody
    public Object exportWwyyExcelAll(WwyyQO wwyyQO) {
        //根据查询条件查询所有的
        wwyyQO.setSlbh("");
        wwyyQO.setPage(null);
        wwyyQO.setSize(null);
        logger.warn("查询全部数据接口入参{}", JSON.toJSONString(wwyyQO));
        Object response = exchangeInterfaceFeignService.requestInterface("wwsq_queryYyByPage", wwyyQO);
        if (Objects.nonNull(response)) {
            Map resultMap = (Map) response;
            //判断接口是否成功
            Map headMap = MapUtils.getMap(resultMap, "head");
            Map dataMap = MapUtils.getMap(resultMap, "data");
            if (MapUtils.isNotEmpty(headMap) && StringUtils.equals("0000", MapUtils.getString(headMap, "code", ""))) {
                if (MapUtils.isNotEmpty(dataMap)) {
                    //转成数组
                    List result = JSON.parseArray(JSON.toJSONString(MapUtils.getObject(dataMap, "yyxxList")));
                    if (CollectionUtils.isNotEmpty(result)) {
                        return result;
                    }
                }
            } else {
                throw new AppException("导出全部查询互联网接口异常" + MapUtils.getString(headMap, "msg"));
            }
        }
        return Collections.emptyList();

    }

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新预约状态
     * @date : 2022/9/1 16:34
     */
    @ResponseBody
    @PostMapping("/yydj/yyzt")
    public Object gxyyzt(@RequestBody List<String> yybhList) {
        List<String> successBhList = new ArrayList<>(10);
        List<String> faileBhList = new ArrayList<>(10);
        if (CollectionUtils.isNotEmpty(yybhList)) {
            for (String yybh : yybhList) {
                WwyyQO wwyyQO = new WwyyQO();
                wwyyQO.setYybh(yybh);
                wwyyQO.setSlbh("");
                logger.warn("预约受理接口入参{}", JSON.toJSONString(wwyyQO));
                Object response = exchangeInterfaceFeignService.requestInterface("wwsq_yydcqr", wwyyQO);
                if (Objects.nonNull(response)) {
                    Map resultMap = (Map) response;
                    //判断接口是否成功
                    Map headMap = MapUtils.getMap(resultMap, "head");
                    if (MapUtils.isNotEmpty(headMap) && StringUtils.equals("0000", MapUtils.getString(headMap, "code", ""))) {
                        successBhList.add(yybh);
                    } else {
                        faileBhList.add(yybh);
                    }
                }
            }
        }
        //没有失败的返回成功，有失败的返回失败的编号数据
        if (CollectionUtils.isNotEmpty(faileBhList)) {
            return "以下预约编号处理失败" + StringUtils.join(faileBhList, CommonConstantUtils.ZF_YW_DH);
        }
        return null;
    }
}
