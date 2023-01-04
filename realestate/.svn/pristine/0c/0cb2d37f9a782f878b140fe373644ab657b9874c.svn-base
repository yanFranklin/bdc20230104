package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskClient;
import cn.gtmap.gtc.workflow.domain.manage.TaskData;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXtJgDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxmPzDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxmSfbzDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;
import cn.gtmap.realestate.common.core.dto.ExcelExportDTO;
import cn.gtmap.realestate.common.core.dto.XmlExportDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlSfxmDTO;
import cn.gtmap.realestate.common.core.dto.exchange.ntfssr.request.InvoiceRequestDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSfxxQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSfxxWithDepartmentQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSfxmFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSfxmPzFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSfxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.config.BdcXtFphFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.NantongFsxtzfFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXtJgFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcRedisFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcPrintFeignService;
import cn.gtmap.realestate.common.core.support.excel.ExcelController;
import cn.gtmap.realestate.common.core.support.spring.EnvironmentConfig;
import cn.gtmap.realestate.common.core.vo.accept.ui.*;
import cn.gtmap.realestate.common.matcher.OrganizationManagerClientMatcher;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.common.util.groovy.XmlUtils;
import cn.gtmap.realestate.inquiry.ui.config.FinanceConfig;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import groovy.util.Node;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019-09-09
 * @description
 */
@RestController
@RequestMapping(value = "/rest/v1.0/fph")
public class BdcFphCxController extends BaseController {
    @Autowired
    BdcXtFphFeignService bdcXtFphFeignService;
    @Autowired
    BdcSlSfxxFeignService bdcSlSfxxFeignService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    ProcessTaskClient processTaskClient;
    @Autowired
    BdcPrintFeignService bdcPrintFeignService;
    @Autowired
    BdcRedisFeignService bdcRedisFeignService;
    @Autowired
    FinanceConfig financeConfig;
    @Autowired
    UserManagerUtils userManagerUtils;
    @Autowired
    private BdcSlSfxmFeignService bdcSlSfxmFeignService;
    @Autowired
    private BdcSlSfxmPzFeignService bdcSlSfxmPzFeignService;
    @Autowired
    private ExcelController excelController;
    @Autowired
    NantongFsxtzfFeignService nantongFsxtzfFeignService;
    @Autowired
    private BdcQlrFeignService bdcQlrFeignService;
    @Autowired
    OrganizationManagerClientMatcher organizationManagerClient;
    @Autowired
    BdcXtJgFeignService bdcXtJgFeignService;
    @Value("${nantong.fpcx.dbsjzwsfrq:false}")
    private Boolean dbsjzwsfrq;

    /**
     * 一窗受理的工作流定义ID集合，多个用英文逗号","分隔
     */
    @Value("#{'${ycsl.gzldyid:}'.split(',')}")
    private List<String> ycslGzldyidList;

    /**
     * @param
     * @return true:办结 false：未办结
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 判断 流程是否已经办结
     */
    @GetMapping("/check")
    public Object checkState(@RequestParam String slbh) {
        if (StringUtils.isBlank(slbh)) {
            throw new MissingArgumentException("slbh");
        }
        String gzlslid = bdcXmFeignService.queryGzlslid(slbh);
        List<TaskData> taskDataList = processTaskClient.processRunningTasks(gzlslid);
        Map<String, Object> result = Maps.newHashMap();
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        boolean state = CommonConstantUtils.AJZT_YB_DM.equals(bdcXmDOList.get(0).getAjzt());
        boolean isSz = false;
        if (CollectionUtils.isNotEmpty(taskDataList) && StringUtils.equals(taskDataList.get(0).getTaskName(), CommonConstantUtils.JD_SZ)) {
            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                isSz = DateUtils.isSameDay(bdcXmDOList.get(0).getDjsj(), new Date());
            }
        }
        List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxFeignService.listBdcSlSfxxByGzlslidHjfk(gzlslid);
        if (CollectionUtils.isEmpty(bdcSlSfxxDOList)) {
            throw new AppException("收费信息为空！");
        }
        boolean sfzt = bdcSlSfxxDOList.get(0).getSfzt() != null
                && bdcSlSfxxDOList.get(0).getSfzt() == 2;

        result.put("sfzt", sfzt);
        result.put("state", state);
        result.put("isSz", isSz);
        return result;
    }

    /**
     * @param slbh
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 生成收费信息：获取发票号
     */
    @GetMapping
    public Object queryFph(@RequestParam String slbh) {
        if (StringUtils.isBlank(slbh)) {
            throw new MissingArgumentException("slbh");
        }
        String gzlslid = bdcXmFeignService.queryGzlslid(slbh);
        List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxFeignService.listBdcSlSfxxByGzlslidHjfk(gzlslid);

        bdcSlSfxxDOList = bdcXtFphFeignService.getBdcFph(bdcSlSfxxDOList, slbh);
        return bdcSlSfxxDOList;
    }

    /**
     * @param slbh
     * @return
     * @author <a href ="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 查询发票信息
     */
    @GetMapping("/query")
    public Object listFph(@RequestParam String slbh) {
        if (StringUtils.isBlank(slbh)) {
            throw new MissingArgumentException("slbh");
        }
        List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxBySlbh(slbh);
        String gzlslid = "";
        if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
            // 过滤一窗受理的项目
            if (CollectionUtils.isNotEmpty(ycslGzldyidList)) {
                bdcXmDTOList = bdcXmDTOList.stream().filter(bdcXmDTO -> {
                    if (ycslGzldyidList.contains(bdcXmDTO.getGzldyid())) {
                        return false;
                    }
                    return true;
                }).collect(Collectors.toList());
            }
            if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
                gzlslid = bdcXmDTOList.get(0).getGzlslid();
            }
        }

        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("工作流实例id为空！");
        }
        List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxFeignService.listBdcSlSfxxByGzlslid(gzlslid);
        if(CollectionUtils.isEmpty(bdcSlSfxxDOList)){
            bdcSlSfxxDOList = new ArrayList<>();
        }
        // 过滤掉线上收费的
        bdcSlSfxxDOList = bdcSlSfxxDOList.stream().filter(bdcSlSfxxDO -> (bdcSlSfxxDO.getSfxsjf() == null ? 0 :bdcSlSfxxDO.getSfxsjf()) != 1).collect(Collectors.toList());
        return bdcSlSfxxDOList;
    }

    /**
     * @param slbh
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 收费
     */
    @GetMapping("/sf")
    public void sf(@RequestParam String slbh, @RequestParam String gxbz) {
        if (StringUtils.isBlank(slbh)) {
            throw new MissingArgumentException("slbh");
        }
        String gzlslid = bdcXmFeignService.queryGzlslid(slbh);
        List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxFeignService.listBdcSlSfxxByGzlslid(gzlslid);
        if (CollectionUtils.isEmpty(bdcSlSfxxDOList)) {
            throw new AppException("收费信息为空！");
        }
        int xmlx = bdcXmFeignService.makeSureBdcXmLx(gzlslid);
        UserDto userDto = userManagerUtils.getCurrentUser();
        bdcSlSfxxDOList.forEach(bdcSlSfxxDO -> {
            BdcXmDO bdcXm = bdcXmFeignService.queryBdcXmByXmid(bdcSlSfxxDO.getXmid());
            if((xmlx == 2 || xmlx == 4)) {
                //验证是否启用电子证照银行
                boolean dzzzyh = this.sfdzzzyh(bdcSlSfxxDO);
                if ( CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcXm.getQllx()) && dzzzyh){
                    bdcSlSfxxDO.setSfztczsj(bdcXm.getDjsj());
                } else {
                    bdcSlSfxxDO.setSfztczsj(new Date());
                }
            } else {
                bdcSlSfxxDO.setSfztczsj(new Date());
            }
            bdcSlSfxxDO.setSfzt(2);
            bdcSlSfxxDO.setSfztczrxm(userDto.getAlias());
            // 当更新标志为1 或 收费时间为空时
            if (StringUtils.equals(gxbz, "1") || bdcSlSfxxDO.getSfsj() == null) {
                bdcSlSfxxDO.setSfsj(new Date());
            }
        });
        LOGGER.info("当前受理编号{},在发票号管理台账收费，更新收费状态2和收费时间为当前时间", slbh);
        bdcSlSfxxFeignService.updateBdcSlSfxxList(bdcSlSfxxDOList);
    }

    /**
     * @param slbh
     * @return
     * @author <a href ="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 取消收费
     */
    @GetMapping("/qxsf")
    public void qxsf(@RequestParam String slbh) {
        if (StringUtils.isBlank(slbh)) {
            throw new MissingArgumentException("slbh");
        }
        String gzlslid = bdcXmFeignService.queryGzlslid(slbh);
        List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxFeignService.listBdcSlSfxxByGzlslid(gzlslid);
        if (CollectionUtils.isEmpty(bdcSlSfxxDOList)) {
            throw new AppException("收费信息为空！");
        }
        bdcSlSfxxDOList.forEach(bdcSlSfxxDO -> {
            bdcSlSfxxDO.setSfzt(1);
        });
        bdcSlSfxxFeignService.updateBdcSlSfxxList(bdcSlSfxxDOList);
    }

    /**
     * @param sfxxid slbh
     * @return bdcSlSfxxDOList
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 重新获取发票号
     */
    @GetMapping("/cxhqfph")
    public Object cxhqfph(@RequestParam String sfxxid, @RequestParam String slbh) {
        if (StringUtils.isAnyBlank(sfxxid, slbh)) {
            throw new MissingArgumentException("sfxxid,slbh");
        }
        BdcSlSfxxDO bdcSlSfxxDO = bdcSlSfxxFeignService.queryBdcSlSfxxBySfxxid(sfxxid);
        return bdcXtFphFeignService.getBdcFph(Lists.newArrayList(bdcSlSfxxDO), slbh);
    }

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description
     */
    @GetMapping("/dy/xml")
    public Object dy(@RequestParam(name = "sfxxidList") List<String> sfxxidList) {
        if (CollectionUtils.isEmpty(sfxxidList)) {
            throw new AppException("要打印的收费信息不能为空！");
        }
        Map<String, List<Map>> printParam = Maps.newHashMap();
        List<Map> list = Lists.newArrayList();
        sfxxidList.forEach(sfxxid -> {
            // 更新收费时间
            BdcSlSfxxDO bdcSlSfxxDO = bdcSlSfxxFeignService.queryBdcSlSfxxBySfxxid(sfxxid);
            // 当收费时间是空时 更新收费时间
            if (bdcSlSfxxDO.getSfsj() == null) {
                bdcSlSfxxDO.setSfsj(new Date());
                bdcSlSfxxFeignService.updateBdcSlSfxx(bdcSlSfxxDO);
            }

            // 组织打印参数
            Map<String, String> param = Maps.newHashMap();
            param.put("sfxxid", sfxxid);
            list.add(param);
        });
        printParam.put("fphdy", list);
        return bdcPrintFeignService.print(printParam);
    }

    @GetMapping("/dy/xml/{sfxxid}")
    public Object dyq(@PathVariable(name = "sfxxid") String sfxxid) {
        Map<String, List<Map>> printParam = Maps.newHashMap();
        List<Map> list = Lists.newArrayList();
        Map<String, String> param = Maps.newHashMap();
        param.put("sfxxid", sfxxid);
        param.put("dylx", "fphdy");
        list.add(param);
        printParam.put("fph", list);
        return bdcPrintFeignService.print(printParam);
    }

    /**
     * @param bdcSlSfxxWithDepartmentQO
     * @return Page<BdcSlSfxxVO>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 分页查询获取 收费信息
     */
    @PostMapping("/page")
    public Object listBdcSfxxByPage(@RequestBody BdcSlSfxxWithDepartmentQO bdcSlSfxxWithDepartmentQO) {
        String bdcSlSfxxQOJSON = JSONObject.toJSONString(bdcSlSfxxWithDepartmentQO);
        Pageable pageable = new PageRequest(bdcSlSfxxWithDepartmentQO.getPage() - 1, bdcSlSfxxWithDepartmentQO.getSize());
        Page<BdcSlSfxxVO> page = bdcSlSfxxFeignService.listBdcSlSfxxByPage(pageable, bdcSlSfxxQOJSON);
        //未缴费的月结银行收费日期为空，则登簿时间作为收费日期
        if(dbsjzwsfrq){
            if(CollectionUtils.isNotEmpty(page.getContent())){
                wjfAyjsyhscsfsj(page.getContent());
            }
        }
        return super.addLayUiCode(page);
    }

    /**
     * @param bdcSlSfxxQO
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取excel 导出数据，用户 未选择导出数据时，按照查询条件 导出 所有数据
     */
    @PostMapping("/excel/data")
    public Object queryExcelData(@RequestBody BdcSlSfxxQO bdcSlSfxxQO) {
        if (bdcSlSfxxQO == null) {
            throw new AppException("查询参数不能为空！");
        }
        List<BdcSlSfxxVO> bdcSlSfxxVOList = bdcSlSfxxFeignService.listBdcSlSfxx(bdcSlSfxxQO);

        return checkSfxm(bdcSlSfxxVOList);
    }

    /**
     * @param bdcSlSfxxQO
     * @return
     * @author <a href ="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 获取excel 导出数据 用于生成导入模板
     */
    @PostMapping("/excel/data/drmb")
    public Object queryExcelDrmb(@RequestBody BdcSlSfxxQO bdcSlSfxxQO) {
        if (bdcSlSfxxQO == null) {
            throw new AppException("查询参数不能为空！");
        }
        if(StringUtils.equals("currentUser",bdcSlSfxxQO.getExportType())){
            UserDto userDto = userManagerUtils.getCurrentUser();
            bdcSlSfxxQO.setKprxm(userDto.getAlias());
        }
        List<BdcSlSfxxVO> bdcSlSfxxVOList = bdcSlSfxxFeignService.listBdcSlSfxx(bdcSlSfxxQO);

        return checkSfxm(bdcSlSfxxVOList);
    }

    /**
     * @param bdcSlSfxxQO
     * @return
     * @author <a href ="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 获取excel 导出数据，用户 未选择导出数据时，按照查询条件 导出 所有数据
     */
    @PostMapping("/excel/data/yh")
    public Object queryExcelDataYh(@RequestBody BdcSlSfxxQO bdcSlSfxxQO) {
        if (bdcSlSfxxQO == null) {
            throw new AppException("查询参数不能为空！");
        }
        List<BdcSlSfxxVO> bdcSlSfxxVOList = bdcSlSfxxFeignService.listBdcSlSfxxYh(bdcSlSfxxQO);
        if(dbsjzwsfrq){
            if(CollectionUtils.isNotEmpty(bdcSlSfxxVOList)){
                wjfAyjsyhscsfsj(bdcSlSfxxVOList);
            }
        }
        return checkSfxm(bdcSlSfxxVOList);
    }

    /**
     * 去掉收费项目不存在 或者合计金额对不上的收费信息
     * @param bdcSlSfxxVOList
     * @return
     */
    private List<BdcSlSfxxVO> checkSfxm(List<BdcSlSfxxVO> bdcSlSfxxVOList){
        List<BdcSlSfxxVO> bdcSlSfxxVOS = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(bdcSlSfxxVOList)){
            bdcSlSfxxVOList.forEach(bdcSlSfxxVO -> {
                if (CollectionUtils.isEmpty(bdcSlSfxxVO.getBdcSlSfxmVOList())) {
                    LOGGER.info("收费信息没有收费项目，收费信息为：" + bdcSlSfxxVO.toString());
                    return;
                }
                BigDecimal hj = BigDecimal.ZERO;
                for (BdcSlSfxmVO bdcSlSfxmVO : bdcSlSfxxVO.getBdcSlSfxmVOList()) {
                    if (bdcSlSfxmVO.getSsje() != null) {
                        hj = hj.add(new BigDecimal(Double.toString(bdcSlSfxmVO.getSsje())));
                    }
                }
                if (!bdcSlSfxxVO.getHj().equals(hj.doubleValue())) {
                    LOGGER.info("收费信息金额与收费项目金额不一致，收费信息为：" + bdcSlSfxxVO.toString());
                    LOGGER.info("收费信息合计为：" + bdcSlSfxxVO.getHj() + "，收费信息合计：" + hj);
                    return;
                }
                bdcSlSfxxVOS.add(bdcSlSfxxVO);
            });
        }

        return bdcSlSfxxVOS;
    }

    /**
     * @return
     * @author <a href ="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 获取所有配置中的银行
     */
    @GetMapping("/yhlb/data")
    public Object queryYhlbData() {
        return financeConfig.getYhmcList();
    }

    /**
     * @return
     * @author <a href ="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 获取所有收费项目
     */
    @GetMapping("/sfxm/data")
    public Object querySfxmData() {
        return bdcSlSfxxFeignService.getSfxmmc();
    }

    /**
     * @return
     * @author <a href ="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 验证可用发票号
     */
    @GetMapping("/edit/check")
    public Object checkFph(String fph) {
        if (StringUtils.isBlank(fph)) {
            throw new AppException("发票号不能为空！");
        }
        Map result = new HashMap();
        result.put("isKy", bdcXtFphFeignService.checkYyFph(fph));
        result.put("syzt", bdcXtFphFeignService.getSyqk(fph));

        return result;
    }

    /**
     * 保存发票号 和 收费信息
     * @author <a href ="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping("/edit/save")
    public void saveSfxx(BdcSlSfxxQO bdcSlSfxxQO) {
        if (StringUtils.isAnyBlank(bdcSlSfxxQO.getSfxxid())) {
            throw new AppException("未获取到收费信息ID");
        }
        // 保存收费发票信息
        if(StringUtils.isNotBlank(bdcSlSfxxQO.getFph())){
            BdcSlSfxxDO sfxx = bdcSlSfxxFeignService.queryBdcSlSfxxBySfxxid(bdcSlSfxxQO.getSfxxid());
            String slbh = bdcSlSfxxQO.getSlbh();
            if (StringUtils.isBlank(slbh) && StringUtils.isNotBlank(sfxx.getXmid())) {
                BdcXmQO bdcXmQO = new BdcXmQO();
                bdcXmQO.setXmid(sfxx.getXmid());
                List<BdcXmDO> bdcXmDOList = this.bdcXmFeignService.listBdcXm(bdcXmQO);
                if(CollectionUtils.isNotEmpty(bdcXmDOList)){
                    slbh = bdcXmDOList.get(0).getSlbh();
                }
            }
            bdcXtFphFeignService.saveSfxxFph(sfxx, slbh, bdcSlSfxxQO.getFph());
        }

        // 保存收费信息
        BdcSlSfxxDO sfxxDO =  new BdcSlSfxxDO();
        BeanUtils.copyProperties(bdcSlSfxxQO, sfxxDO);
        this.bdcSlSfxxFeignService.saveOrUpdateBdcSlSfxx(sfxxDO);
    }

    /**
     * @param response data queryParam
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 导出 xml 数据
     */
    @PostMapping("/export/xml")
    public void exportXml(HttpServletResponse response, @ModelAttribute XmlExportDTO xmlExportDTO) {
        List<BdcSlSfxxVO> bdcSlSfxxVOList = null;
        String kssj = "";
        if (StringUtils.isBlank(xmlExportDTO.getData())) {
            BdcSlSfxxQO bdcSlSfxxQO = JSONObject.parseObject(xmlExportDTO.getQueryParam(), BdcSlSfxxQO.class);
            UserDto userDto = userManagerUtils.getCurrentUser();
            bdcSlSfxxQO.setKprxm(userDto.getAlias());
            kssj = bdcSlSfxxQO.getKssj();
            bdcSlSfxxVOList = bdcSlSfxxFeignService.listBdcSlSfxx(bdcSlSfxxQO);
        }
        if (CollectionUtils.isEmpty(bdcSlSfxxVOList)) {
            bdcSlSfxxVOList = JSONObject.parseArray(xmlExportDTO.getData(), BdcSlSfxxVO.class);
        }
        if (CollectionUtils.isEmpty(bdcSlSfxxVOList)) {
            throw new AppException("导出的收费信息不能为空！");
        }
        String sfxxXml = bdcRedisFeignService.getStringValue(CommonConstantUtils.REDIS_INQUIRY_BDC_SFXX_XML);
        /**
         * redis没有的话，重新从文件读取并且存储到redis
         */
        if (StringUtils.isBlank(sfxxXml)) {
            sfxxXml = readBdcSfxxXmlFile();
            bdcRedisFeignService.addStringValue(CommonConstantUtils.REDIS_INQUIRY_BDC_SFXX_XML, sfxxXml, String.valueOf(DateUtils.MILLIS_PER_DAY));
        }
        Node xmlNode = XmlUtils.getXmlNodeByString(sfxxXml);
        String main = XmlUtils.getBdcSfxxMain(xmlNode).replace(" ", "").replace("\n", "");
        String detail = XmlUtils.getBdcSfxxDetails(xmlNode).replace(" ", "").replace("\n", "");
        if (StringUtils.isBlank(main)) {
            throw new AppException("xml中的main节点不能为空！");
        }
        Map<String, Map<String, String>> fphMap = financeConfig.getDjfMap();
        String username = userManagerUtils.getCurrentUserName();
        if (StringUtils.isBlank(username)) {
            throw new AppException("无法获取当前用户名！");
        }
        Map<String, String> fph = fphMap.get(username);
        if (fph == null || fph.size() == 0) {
            throw new AppException("财务登记费用户配置错误！");
        }

        StringBuilder records = new StringBuilder("<?xml version=\"1.0\" encoding=\"GB2312\"?><ROOT><FLAG/><MESSAGE/><DATA>");
        bdcSlSfxxVOList.forEach(bdcSlSfxxVO -> {
            if (CollectionUtils.isEmpty(bdcSlSfxxVO.getBdcSlSfxmVOList())) {
                LOGGER.info("收费信息没有收费项目，收费信息为：" + bdcSlSfxxVO.toString());
                return;
            }
            BigDecimal hj = BigDecimal.ZERO;
            for (BdcSlSfxmVO bdcSlSfxmVO : bdcSlSfxxVO.getBdcSlSfxmVOList()) {
                if (bdcSlSfxmVO.getSsje() != null) {
                    hj = hj.add(new BigDecimal(Double.toString(bdcSlSfxmVO.getSsje())));
                }
            }
            if (!bdcSlSfxxVO.getHj().equals(hj.doubleValue())) {
                LOGGER.info("收费信息金额与收费项目金额不一致，收费信息为：" + bdcSlSfxxVO.toString());
                LOGGER.info("收费信息合计为：" + bdcSlSfxxVO.getHj() + "，收费信息合计：" + hj);
                return;
            }
            StringBuilder builder = new StringBuilder("<RECORD>");
            StringBuilder detailBulider = new StringBuilder();
            // 根据发票号查询当前发票状态
            Integer syqk = bdcXtFphFeignService.getSyqk(bdcSlSfxxVO.getFph());
            if (syqk == null) {
                fph.put("zfbz", "");
            } else if (syqk == CommonConstantUtils.SYQK_ZF
                    || syqk == CommonConstantUtils.SYQK_XH) {
                fph.put("zfbz", "9");
            } else {
                fph.put("zfbz", "8");
            }
            if (bdcSlSfxxVO.getSfztczsj() != null) {
                fph.put("sfsj", DateFormatUtils.format(bdcSlSfxxVO.getSfztczsj(), "yyyy-MM-dd"));
            }
            // 南通打印发票号为'6'+8位发票号
            bdcSlSfxxVO.setFph("6" + bdcSlSfxxVO.getFph());
            List<Field> fieldArrayList = Lists.newArrayList(bdcSlSfxxVO.getClass().getDeclaredFields());
            fieldArrayList.addAll(Lists.newArrayList(bdcSlSfxxVO.getClass().getSuperclass().getDeclaredFields()));
            String mainXml = main;
            mainXml = recoverXml(fph, mainXml);
            mainXml = processXml(fieldArrayList, mainXml, bdcSlSfxxVO);
            List<BdcSlSfxmVO> bdcSlSfxmDOList = bdcSlSfxxVO.getBdcSlSfxmVOList();
            if (CollectionUtils.isNotEmpty(bdcSlSfxmDOList) && StringUtils.isNotBlank(detail)) {
                detailBulider.append("<DETAILS>");
                for (BdcSlSfxmVO bdcSlSfxmVO : bdcSlSfxmDOList) {
                    List<Field> sfxmFileds = Lists.newArrayList(bdcSlSfxmVO.getClass().getDeclaredFields());
                    sfxmFileds.addAll(Lists.newArrayList(bdcSlSfxmVO.getClass().getSuperclass().getDeclaredFields()));
                    String detailXml = detail;
                    detailBulider.append(processXml(sfxmFileds, detailXml, bdcSlSfxmVO));
                }
                detailBulider.append("</DETAILS>");
            }
            builder.append(mainXml).append(detailBulider).append("</RECORD>");
            records.append(builder);
        });
        records.append("</DATA></ROOT>");
        //浏览器下载
        String fileName = "收费信息" + kssj;
        try (OutputStream outputStream = response.getOutputStream()) {
            fileName = URLEncoder.encode(fileName + ".xml", "utf-8");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            outputStream.flush();
            outputStream.write(records.toString().getBytes("GBK"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * @param sfxxid 收费信息ID
     * @return 收费项目
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 通过收费信息id获取收费项目
     */
    @ResponseBody
    @GetMapping("/sf/xm/new")
    public Object listBdcSlSfxmBySfxxid(String sfxxid) {
        if (StringUtils.isBlank(sfxxid)) {
            return Collections.emptyList();
        } else {
            return bdcSlSfxmFeignService.listBdcSlSfxmBySfxxid(sfxxid);
        }
    }

    /**
     * @param json 集合
     * @return 更新数量
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 更新收费信息
     */
    @ResponseBody
    @PatchMapping("/sf/xx")
    public Integer updateBdcSlSfxx(@RequestBody String json) {
        return bdcSlSfxxFeignService.updateBdcSlSfxx(JSONObject.parseObject(json, BdcSlSfxxDO.class));
    }

    /**
     * @param json 集合
     * @return 保存数量
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 保存更新收费项目
     */
    @ResponseBody
    @PatchMapping("/sf/xm")
    public Integer saveOrUpdateSfxm(@RequestBody String json) {
        Integer count = 0;
        for (Object obj : JSON.parseArray(json)) {
            BdcSlSfxmDO bdcSlSfxmDO = JSONObject.parseObject(obj.toString(), BdcSlSfxmDO.class);
            bdcSlSfxmDO.setXh(count + 1);
            if (bdcSlSfxmDO != null) {
                JSONObject object = JSONObject.parseObject(obj.toString());
                if (object.get("cz") != null) {
                    if (StringUtils.equals(object.get("cz").toString(), "update")) {
                        count += bdcSlSfxmFeignService.updateBdcSlSfxm(bdcSlSfxmDO);
                    }
                    if (StringUtils.equals(object.get("cz").toString(), "insert")) {
                        BdcSlSfxmDO bdcSlSfxm = bdcSlSfxmFeignService.insertBdcSlSfxm(bdcSlSfxmDO);
                        if (bdcSlSfxm != null) {
                            count++;
                        }
                    }
                }
            }
        }
        return count;
    }

    /**
     * @param sfxmid 收费项目id
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 通过收费项目id删除收费项目
     */
    @ResponseBody
    @DeleteMapping("/sf/xm")
    public void deleteBdcsfxm(@RequestParam("sfxmid") String sfxmid) {
        BdcSlSfxmDO bdcSlSfxmDO = bdcSlSfxmFeignService.queryBdcSlSfxmBySfxmid(sfxmid);
        bdcSlSfxmFeignService.deleteBdcSlSfxmBySfxmid(sfxmid);
        if (bdcSlSfxmDO != null) {
            List<BdcSlSfxmDO> bdcSlSfxmDOList = bdcSlSfxmFeignService.listBdcSlSfxmBySfxxid(bdcSlSfxmDO.getSfxxid());
            for (BdcSlSfxmDO bdcSlSfxm : bdcSlSfxmDOList) {
                if (bdcSlSfxm.getXh() != null && bdcSlSfxmDO.getXh() != null) {
                    if (bdcSlSfxm.getXh() > bdcSlSfxmDO.getXh()) {
                        bdcSlSfxm.setXh(bdcSlSfxm.getXh() - 1);
                        bdcSlSfxmFeignService.updateBdcSlSfxm(bdcSlSfxm);
                    }
                }
            }
        }
    }

    /**
     * @return uuid
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 生成uuid
     */
    @ResponseBody
    @GetMapping("/uuid")
    public String getUUid(String sfxxid) {
        return UUIDGenerator.generate16();
    }

    /**
     * @param processInsId 实例id
     * @return 收费项目配置信息
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 获取收费项目配置
     */
    @ResponseBody
    @GetMapping("/sf/xm/pz")
    public Object queryBdcSlSfxmPz(String processInsId, String xmid) {
        if (StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException(messageProvider.getMessage("message.noparameter"));
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        if (StringUtils.isNotBlank(xmid)) {
            bdcXmQO.setXmid(xmid);
        } else {
            bdcXmQO.setGzlslid(processInsId);
        }
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            return listBdcSlSfxmDTOByDjxl(bdcXmDOList.get(0).getDjxl());
        }
        return null;
    }

    /**
     * @return 收费项目配置信息
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 获取所有收费项目配置(所有配置收费项目)
     */
    @ResponseBody
    @GetMapping("/sf/xm/pz/all")
    public List<BdcSlSfxmDTO> queryBdcSlSfxmPzAll() {
        return bdcSlSfxmPzFeignService.listAllBdcSlSfxmDTO();
    }

    /**
     * 更新收费信息开票状态
     * @param sfxxidList 收费信息ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @PostMapping("/sf/kpzt")
    public void modifyKpzt(@RequestBody List<String> sfxxidList) {
        if(CollectionUtils.isNotEmpty(sfxxidList)){
            UserDto userDto = userManagerUtils.getCurrentUser();
            for(String sfxxid:sfxxidList){
                BdcSlSfxxDO bdcSlSfxxDO = new BdcSlSfxxDO();
                bdcSlSfxxDO.setSfxxid(sfxxid);
                bdcSlSfxxDO.setSfkp(CommonConstantUtils.SF_S_DM);
                bdcSlSfxxDO.setKprxm(userDto.getAlias());
                this.bdcSlSfxxFeignService.updateBdcSlSfxx(bdcSlSfxxDO);
            }
        }
    }

    /**
     * @param fields xml object
     * @return xml
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 处理xml 替换 key value
     */
    private String processXml(List<Field> fields, String xml, Object object) {
        if (CollectionUtils.isEmpty(fields) || StringUtils.isBlank(xml)) {
            throw new AppException("替换的xml或者字段不能为空");
        }
        String replaceXml = xml;
        for (Field f : fields) {
            f.setAccessible(true);
            try {
                Object value = f.get(object);
                replaceXml = StringToolUtils.replaceXml(f.getName(), value == null ? "" : String.valueOf(value), replaceXml);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return replaceXml;
    }

    /**
     * @param fields xml object
     * @return xml
     * @author <a href ="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 处理xml 替换 key value
     */
    private String recoverXml(Map<String, String> fields, String xml) {
        if (fields == null || fields.size() == 0 || StringUtils.isBlank(xml)) {
            throw new AppException("替换的xml或者字段不能为空");
        }
        String replaceXml = xml;
        for (Map.Entry<String, String> f : fields.entrySet()) {
            Object value = f.getValue();
            replaceXml = StringToolUtils.replaceXml(f.getKey(), value == null ? "" : String.valueOf(value), replaceXml);
        }
        return replaceXml;
    }

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description
     */
    private String readBdcSfxxXmlFile() {
        String configPath = EnvironmentConfig.getEnvironment().getProperty("spring.config.location");
        String xml = null;
        try (BufferedReader br = new BufferedReader(new FileReader(configPath + CommonConstantUtils.XML_PATH_SFXX))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            xml = sb.toString();
            LOGGER.debug("收费信息配置xml ：提示信息 {}", xml);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return xml;
    }

    /**
     * @param djxl 登记小类
     * @return 不动产受理收费项目类
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 通过登记小类获取不动产收费项目收费标准
     */
    private List<BdcSlSfxmDTO> listBdcSlSfxmDTOByDjxl(String djxl) {
        if (StringUtils.isBlank(djxl)) {
            throw new MissingArgumentException(messageProvider.getMessage("message.noparameter"));
        }
        List<BdcSlSfxmDTO> bdcSlSfxmDTOList = new ArrayList<>();
        List<BdcSlSfxmPzDO> bdcSlSfxmPzDOList = bdcSlSfxmPzFeignService.listBdcSlSfxmPzByDjxl(djxl);
        if (CollectionUtils.isNotEmpty(bdcSlSfxmPzDOList)) {
            for (int i = 0; i < bdcSlSfxmPzDOList.size(); i++) {
                BdcSlSfxmPzDO bdcSlSfxmPzDO = bdcSlSfxmPzDOList.get(i);
                BdcSlSfxmDTO bdcSlSfxmDTO = new BdcSlSfxmDTO(bdcSlSfxmPzDO.getSfxmdm(), bdcSlSfxmPzDO.getSfxmmc(), bdcSlSfxmPzDO.getSl(), bdcSlSfxmPzDO.getJedw(), null, bdcSlSfxmPzDO.getQlrlb());
                List<BdcSlSfxmSfbzDO> bdcSlSfxmSfbzDOList = bdcSlSfxmPzFeignService.listBdcSlSfxmSfbzDO(bdcSlSfxmPzDO.getSfxmdm());
                if (CollectionUtils.isNotEmpty(bdcSlSfxmSfbzDOList)) {
                    bdcSlSfxmDTO.setBdcSlSfxmSfbzList(bdcSlSfxmSfbzDOList);
                }
                bdcSlSfxmDTOList.add(bdcSlSfxmDTO);
            }
        }
        if (CollectionUtils.isNotEmpty(bdcSlSfxmDTOList)) {
            List<BdcSlSfxmDTO> qlrBdcSfxmDTOList = new ArrayList<>();
            List<BdcSlSfxmDTO> ywrBdcSfxmDTOList = new ArrayList<>();
            for (BdcSlSfxmDTO bdcSlSfxmDTO : bdcSlSfxmDTOList) {
                if (StringUtils.equals(bdcSlSfxmDTO.getQlrlb(), CommonConstantUtils.QLRLB_QLR)) {
                    qlrBdcSfxmDTOList.add(bdcSlSfxmDTO);
                }
                if (StringUtils.equals(bdcSlSfxmDTO.getQlrlb(), CommonConstantUtils.QLRLB_YWR)) {
                    ywrBdcSfxmDTOList.add(bdcSlSfxmDTO);
                }
            }
            List<BdcSlSfxmDTO> bdcSlSfxmDTOS = new ArrayList<>();
            Set<BdcSlSfxmDTO> bdcQlrSlSfxmList = new TreeSet<>(Comparator.comparing(BdcSlSfxmDTO::getSfxmdm));
            Set<BdcSlSfxmDTO> bdcYwrSlSfxmList = new TreeSet<>(Comparator.comparing(BdcSlSfxmDTO::getSfxmdm));
            bdcQlrSlSfxmList.addAll(qlrBdcSfxmDTOList);
            bdcYwrSlSfxmList.addAll(ywrBdcSfxmDTOList);
            if (CollectionUtils.isNotEmpty(bdcQlrSlSfxmList)) {
                bdcSlSfxmDTOS.addAll(bdcQlrSlSfxmList);
            }
            if (CollectionUtils.isNotEmpty(bdcYwrSlSfxmList)) {
                bdcSlSfxmDTOS.addAll(bdcYwrSlSfxmList);
            }
            return bdcSlSfxmDTOS;
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * 汇总缴费数据，导出Excel
     * @param bdcSlSfxxQO 缴费数据查询条件
     */
    @PostMapping("/hzjfsj")
    public void zjfsj(HttpServletResponse response, @ModelAttribute BdcSlSfxxQO bdcSlSfxxQO) {
        if(null == bdcSlSfxxQO || StringUtils.isAnyBlank(bdcSlSfxxQO.getKssj(), bdcSlSfxxQO.getJzsj())) {
            throw new MissingArgumentException("汇总缴费数据异常：未选择开始、结束时间");
        }

        BdcSfxxhzVO bdcSfxxhzVO = bdcSlSfxxFeignService.hzjfsj(bdcSlSfxxQO);
        if(null == bdcSfxxhzVO || CollectionUtils.isEmpty(bdcSfxxhzVO.getData())) {
            throw new AppException("未查询到缴费数据，无法导出");
        }

        if(CollectionUtils.isEmpty(bdcSfxxhzVO.getJfsj()) || CollectionUtils.isEmpty(bdcSfxxhzVO.getOrgName())) {
            throw new AppException("未查询到缴费数据，无法导出");
        }

        // 组织Excel导出数据：标题行、数据行
        List<Map<String, Object>> data = new ArrayList<>();
        data.addAll(newTitleData(bdcSfxxhzVO));
        data.addAll(bdcSfxxhzVO.getData());

        List<String> titleKeyList = this.getTitleKeyList(bdcSfxxhzVO);

        List<String> cellWidthList = new ArrayList<>(titleKeyList.size());
        cellWidthList.addAll(Arrays.asList("15,15".split(",")));
        for(int i = 0 ; i < titleKeyList.size() - 2; i++) {
            cellWidthList.add("20");
        }

        // 组织导出Excel数据参数DTO
        ExcelExportDTO excelExportDTO = new ExcelExportDTO();
        excelExportDTO.setData(JSON.toJSONString(data));
        excelExportDTO.setFileName("不动产登记业务系统缴费数据");
        excelExportDTO.setSheetName("不动产登记业务系统缴费数据");
        excelExportDTO.setCellTitle(StringUtils.join(titleKeyList, ","));
        excelExportDTO.setCellKey(StringUtils.join(titleKeyList, ","));
        excelExportDTO.setCellWidth(StringUtils.join(cellWidthList, ","));
        excelExportDTO.setMergeSameCell(true);
        excelExportDTO.setMergeCellKey("xh,sfsj");
        excelExportDTO.setAddBorder(true);

        excelExportDTO.setExpandServices(Arrays.asList((workbook, sheet, excelExportDTO1) -> {
            // 横向合并标题列
            for(int i = 2 ; i < titleKeyList.size(); i += 2) {
                sheet.addMergedRegion(new CellRangeAddress(2, 2, i, i + 1));
            }

            // 将标题key行隐藏
            HSSFRow row = sheet.getRow(1);
            if(null != row) {
                row.setZeroHeight(true);
            }
        }));
        excelController.exportExcel(response, excelExportDTO);
    }

    /**
     * 查询明细缴费数据数量
     * @param bdcSlSfxxQO 缴费数据查询条件
     */
    @PostMapping("/mxjfsjsl")
    public int mxjfsj(@RequestBody BdcSlSfxxQO bdcSlSfxxQO) {
        BdcSfxxmxVO bdcSfxxmxVO = bdcSlSfxxFeignService.mxjfsj(bdcSlSfxxQO);
        return bdcSfxxmxVO.getCount();
    }

    /**
     * 查询明细缴费数据，导出Excel
     * @param bdcSlSfxxQO 缴费数据查询条件
     */
    @PostMapping("/mxjfsj")
    public void mxjfsj(HttpServletResponse response, @ModelAttribute BdcSlSfxxQO bdcSlSfxxQO) {
        BdcSfxxmxVO bdcSfxxmxVO = bdcSlSfxxFeignService.mxjfsj(bdcSlSfxxQO);
        if (null == bdcSfxxmxVO || CollectionUtils.isEmpty(bdcSfxxmxVO.getSfxxmxList())) {
            throw new AppException("未查询到缴费数据，无法导出");
        }

        List<BdcSfxxmxDTO> data = new ArrayList<>(bdcSfxxmxVO.getSfxxmxList().size() + 2);
        data.addAll(this.newTitleData());
        data.addAll(bdcSfxxmxVO.getSfxxmxList());

        ExcelExportDTO excelExportDTO = new ExcelExportDTO();
        excelExportDTO.setData(JSON.toJSONString(data));
        excelExportDTO.setFileName("不动产单元一般缴款书明细表");
        excelExportDTO.setSheetName("不动产单元一般缴款书明细表");
        excelExportDTO.setCellTitle("序号,部门,收件编码,缴款书编码,缴款人全称,发票号,手机号,不动产登记费,土地使用权交易服务费,实际收费总金额,交款日期,状态");
        excelExportDTO.setCellKey("xh,sfdwmc,sjbm,jfsbm,jfrxm,fph,sjh,bdcdjf,tdsyqjyfwf,ssje,sfsj,sfztmc");
        excelExportDTO.setCellWidth("20,20,20,20,20,20,20,20,20,20,20,20");

        excelExportDTO.setExpandServices(Arrays.asList((workbook, sheet, excelExportDTO1) -> {
            // 横向合并标题列
            sheet.addMergedRegion(new CellRangeAddress(2, 2, 2, 10));

            // 将标题key行隐藏
            HSSFRow row = sheet.getRow(1);
            if (null != row) {
                row.setZeroHeight(true);
            }
        }));
        excelController.exportExcel(response, excelExportDTO);
    }

    /**
     * 获取汇总缴费数据导出Excel列 key
     * @param bdcSfxxhzVO 查询到的缴费数据
     * @return 列key集合
     */
    private List<String> getTitleKeyList(BdcSfxxhzVO bdcSfxxhzVO) {
        List<String> titleKeyList = new ArrayList<>();
        titleKeyList.add("xh");
        titleKeyList.add("sfsj");
        for(String orgCode : bdcSfxxhzVO.getOrgCode()) {
            for(String sfbzCode : CommonConstantUtils.JFHZFYDM.split(",")) {
                titleKeyList.add(orgCode + "_" + sfbzCode);
            }
        }
        return titleKeyList;
    }

    /**
     * 新建汇总缴费数据导出Excel标题列
     * @param bdcSfxxhzVO 查询到的缴费数据
     * @return {List} 标题列数据
     */
    private List<Map<String, Object>> newTitleData(BdcSfxxhzVO bdcSfxxhzVO) {
        List<Map<String, Object>> data = new ArrayList<>();

        Map<String, Object> dataItem = new LinkedHashMap<>();
        dataItem.put("xh", "序号");
        dataItem.put("sfsj", "缴款日期");
        for(int i = 0; i < bdcSfxxhzVO.getOrgCode().size(); i++) {
            for(String sfbzCode : "A,B".split(",")) {
                dataItem.put(bdcSfxxhzVO.getOrgCode().get(i) + "_" + sfbzCode, bdcSfxxhzVO.getOrgName().get(i));
            }
        }
        data.add(dataItem);

        Map<String, Object> dataItem2 = new LinkedHashMap<>();
        dataItem2.put("xh", "序号");
        dataItem2.put("sfsj", "缴款日期");
        for(int i = 0; i < bdcSfxxhzVO.getOrgCode().size(); i++) {
            for(String sfbzCode : CommonConstantUtils.JFHZFYDM.split(",")) {
                String sfbz = CommonConstantUtils.JFHZFYDM_A.equals(sfbzCode) ? CommonConstantUtils.JFHZFYMC_A : CommonConstantUtils.JFHZFYMC_B;
                dataItem2.put(bdcSfxxhzVO.getOrgCode().get(i) + "_" + sfbzCode, sfbz);
            }
        }
        data.add(dataItem2);

        return data;
    }

    /**
     * 新建明细缴费数据导出Excel标题列
     * @return {List} 标题列数据
     */
    private List<BdcSfxxmxDTO> newTitleData() {
        List<BdcSfxxmxDTO> data = new ArrayList<>();

        String date = DateFormatUtils.format(new Date(), "yyyy年MM月dd日");
        String dateStr = "{\"xh\": \"打印日期：\",\"sfdwmc\": \"" + date + "\",\"sjbm\": \" \",\"jfsbm\": \" \",\"jfrxm\": \" \",\"fph\": \" \",\"sjh\": \" \",\"bdcdjf\": \" \",\"tdsyqjyfwf\": \" \",\"ssje\": \" \",\"sfsj\": \" \",\"sfztmc\": \"单位：元\"}";
        BdcSfxxmxDTO bdcSfxxmxDTO = JSON.parseObject(dateStr, BdcSfxxmxDTO.class);
        data.add(bdcSfxxmxDTO);

        String titleStr = "{\"xh\": \"序号\",\"sfdwmc\": \"部门\",\"sjbm\": \"收件编码\",\"jfsbm\": \"缴款书编码\",\"jfrxm\": \"缴款人全称\",\"fph\": \"发票号\",\"sjh\": \"手机号\",\"bdcdjf\": \"不动产登记费\",\"tdsyqjyfwf\": \"土地使用权交易服务费\",\"ssje\": \"实际收费总金额\",\"sfsj\": \"交款日期\",\"sfztmc\": \"状态\"}";
        BdcSfxxmxDTO bdcSfxxmxDTO2 = JSON.parseObject(titleStr, BdcSfxxmxDTO.class);
        data.add(bdcSfxxmxDTO2);

        return data;
    }

    /**
     * 调用外部接口获取电子发票信息
     *
     * @param beanName          接口标识
     * @param invoiceRequestDTO 电子票据信息接口请求参数
     * @return 电子发票内容
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @PostMapping("/getDzfpxx")
    public Object getDzfpxx(@RequestParam(name = "beanName") String beanName,
                            @RequestBody InvoiceRequestDTO invoiceRequestDTO) {
        if (StringUtils.isBlank(beanName)) {
            throw new MissingArgumentException("未获取到接口标识名称。");
        }
        // 接口增加开票人信息
        UserDto userDto = userManagerUtils.getCurrentUser();
        invoiceRequestDTO.setDrawer(userDto.getAlias());
        Map result = null;
        LOGGER.info("调用电子发票接口beanName:{}, 请求参数：{}", beanName, invoiceRequestDTO);
        switch (beanName) {
            case "invoiceBeforIssue":
                result = this.handleResult(nantongFsxtzfFeignService.invoiceBeforIssue(invoiceRequestDTO));
                break;
            case "invoiceHandleIssue":
                result = handleResult(nantongFsxtzfFeignService.invoiceHandleIssue(invoiceRequestDTO));
                modifyKpzt(invoiceRequestDTO.getBillno(), userDto.getAlias(), (String) result.get("invoiceNumber"));
                break;
            case "invoiceDownload":
                result = handleResult(nantongFsxtzfFeignService.invoiceDownload(invoiceRequestDTO));
                break;
            default:
        }
        return result;
    }

    // 处理南通财政返回值
    private Map handleResult(Object response){
        LOGGER.info("接口返回结果：{}", JSON.toJSONString(response));
        if(Objects.isNull(response)){
            throw new AppException("未获取到电子票据信息");
        }
        Map map = (Map) response;
        if(map.get("errcode").equals(0)){
            String result = JSON.toJSONString(map.get("result"));
            return JSONObject.parseObject(result, new TypeReference<Map>(){});
        }else{
            throw new AppException(String.valueOf(response));
        }
    }

    /**
     * 根据缴费编码更新开票状态为已开票
     * @param jfsbm  缴费书编码
     * @param kprxm  开票人姓名
     * @param invoiceNumber  发票号
     */
    private void modifyKpzt(String jfsbm, String kprxm, String invoiceNumber){
        if(StringUtils.isNotBlank(jfsbm)){
            BdcSlSfxxDO queryParam = new BdcSlSfxxDO();
            queryParam.setJfsbm(jfsbm);
            List<BdcSlSfxxDO> bdcSlSfxxDOList = this.bdcSlSfxxFeignService.queryBdcSlSfxx(queryParam);
            if(CollectionUtils.isNotEmpty(bdcSlSfxxDOList)){
                for(BdcSlSfxxDO bdcSlSfxxDO:bdcSlSfxxDOList){
                    bdcSlSfxxDO.setSfkp(CommonConstantUtils.SF_S_DM);
                    bdcSlSfxxDO.setKprxm(kprxm);
                    bdcSlSfxxDO.setFph(invoiceNumber);
                    bdcSlSfxxDO.setKpfs(CommonConstantUtils.KPFS_DZFP);
                    this.bdcSlSfxxFeignService.updateBdcSlSfxx(bdcSlSfxxDO);
                }
            }
        }
    }

    /**
     * 获取当前用户信息与权利人联系电话
     * @param xmid  项目ID
     * @param qlrlb 权利人类别
     * @return 当前用户名与权利人联系电话
     */
    @ResponseBody
    @GetMapping("/queryUserAndDh")
    public Map<String, Object> queryUserAndDh(String xmid, String qlrlb) {
        Map<String, Object> result = new HashMap<>(8);
        // 获取当前登陆用户信息
        UserDto userDto = userManagerUtils.getUserDto();
        result.put("userName", userDto.getAlias());
        String dh = "";
        if(StringUtils.isNotBlank(xmid)){
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setXmid(xmid);
            if(StringUtils.isNotBlank(qlrlb)){
                bdcQlrQO.setQlrlb(qlrlb);
            }
            List<BdcQlrDO> bdcQlrDOList = this.bdcQlrFeignService.listBdcQlr(bdcQlrQO);
            if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                dh = bdcQlrDOList.get(0).getDh();
            }
        }
        result.put("dh", dh);
        return result;
    }

    /**
     * @author  <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @param bdcSlSfxxVOS
     * @return List<BdcSlSfxxVO>
     * @description   未缴费的月结银行且收费日期为空,讲登簿时间作为收费日期
     */
    private List<BdcSlSfxxVO> wjfAyjsyhscsfsj(List<BdcSlSfxxVO> bdcSlSfxxVOS){
        List<BdcXtJgDO> bdcXtJgDOS = bdcXtJgFeignService.listAyjsBdcXtJgYhmcLike("");
        List<String> ayjsyhList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(bdcXtJgDOS)){
            bdcXtJgDOS.forEach(bdcXtJgDO -> {
                ayjsyhList.add(bdcXtJgDO.getJgmc());
            });
        }
        for (BdcSlSfxxVO bdcSlSfxxVO : bdcSlSfxxVOS) {
            //判断是否为未缴费的月结银行且收费日期为空
            Boolean flag = bdcSlSfxxVO.getSfzt() != null && bdcSlSfxxVO.getSfzt() == 1 && bdcSlSfxxVO.getSfsj() == null &&
                    StringUtils.isNotBlank(bdcSlSfxxVO.getJfrxm()) && ayjsyhList.contains(bdcSlSfxxVO.getJfrxm());
            if(flag){
                LOGGER.warn("当前流程{}是未缴费的月结银行且收费日期为空,登簿时间作为收费日期", bdcSlSfxxVO.getGzlslid());
                bdcSlSfxxVO.setSfztczsj(bdcSlSfxxVO.getDbsj());
            }
        }
        return bdcSlSfxxVOS;
    }

    /**
     * 判断是否启用电子证照银行
     * @return
     */
    private boolean sfdzzzyh(BdcSlSfxxDO bdcSlSfxxDO) {
        BdcQlrQO bdcQlrQO = new BdcQlrQO();
        bdcQlrQO.setXmid(bdcSlSfxxDO.getXmid());
        bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
        List<BdcQlrDO> qlrDOS = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
        if (CollectionUtils.isNotEmpty(qlrDOS)){
            for (BdcQlrDO bdcQlrDO : qlrDOS) {
                BdcXtJgDO bdcXtJgDO = bdcXtJgFeignService.queryJgByJgmc(bdcQlrDO.getQlrmc(),null);
                if (bdcXtJgDO != null && StringUtils.equals(bdcXtJgDO.getSfjrhlw(), "1")){
                    return true;
                }
            }
        }
        return false;
    }


}
