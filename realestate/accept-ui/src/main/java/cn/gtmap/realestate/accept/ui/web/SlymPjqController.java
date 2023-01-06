package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.domain.BdcZdDsfzdgxDO;
import cn.gtmap.realestate.common.core.domain.accept.*;
import cn.gtmap.realestate.common.core.dto.accept.*;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.service.HttpClientService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.gtc.storage.domain.dto.BaseResultDto;
import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskClient;
import cn.gtmap.gtc.workflow.domain.manage.TaskData;
import cn.gtmap.realestate.accept.ui.config.PjqConfig;
import cn.gtmap.realestate.accept.ui.utils.Constants;
import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.register.BdcQzxxDO;
import cn.gtmap.realestate.common.core.dto.OfficeExportDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcQzxxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.*;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcQzxxFeginService;
import cn.gtmap.realestate.common.core.support.pdf.PdfController;
import cn.gtmap.realestate.common.util.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @program: realestate
 * @description: 评价器调用
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2019-08-06 14:57
 **/
@Controller
@RequestMapping("/pjq")
public class SlymPjqController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SlymPjqController.class);

    private static String zxlc = "false";

    private static String modelurl = "";
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcQlrFeignService bdcQlrFeignService;
    @Autowired
    BdcSlPjqFeignService bdcSlPjqFeignService;
    @Autowired
    ProcessTaskClient processTaskClient;
    @Autowired
    BdcSlJbxxFeignService bdcSlJbxxFeignService;
    @Autowired
    BdcSlSqrFeignService bdcSlSqrFeignService;
    @Autowired
    StorageClientMatcher storageClient;
    @Autowired
    BdcSlSjclFeignService bdcSlSjclFeignService;
    @Autowired
    BdcSlSfxxFeignService bdcSlSfxxFeignService;
    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;
    @Autowired
    BdcZdFeignService bdcZdFeignService;

    @Value("${print.path:}")
    private String printPath;
    @Value("${file.path:}")
    private String filePath;

    /**在没签字前是否上传文件中心：用于舒城评价器*/
    @Value("${slym.sfscwjzx:false}")
    private Boolean sfscwjzx;
    @Autowired
    private BdcSlPrintFeignService bdcSlPrintFeignService;
    @Autowired
    PdfController pdfController;
    @Autowired
    BdcQzxxFeginService qzxxFeginService;
    @Autowired
    HttpClientService httpClientService;

    /**
     * 附件上传通用服务
     */
    @Autowired
    private BdcUploadFileUtils bdcUploadFileUtils;

    @Autowired
    PjqConfig pjqConfig;

    /**
     * 图片地址
     */
    @Value("${url.sign.image:}")
    protected String signImageUrl;

    /**
     * 评价器使用mac地址作为ip地址
     */
    @Value("${pjq.ip.usemac:false}")
    protected String usemac;

    @GetMapping("/url")
    @ResponseBody
    public String getPjqUrl() {
        return getPjqUrlByQxdm();
    }

    @GetMapping("/userinfo")
    @ResponseBody
    public Object getCurrentUserInfo(){
        UserDto userDto = userManagerUtils.getCurrentUser();
        Map userMap = JSON.parseObject(JSON.toJSONString(userDto));
        String qxdm = userManagerUtils.getRegionCodeByUserName(userManagerUtils.getCurrentUserName());
        userMap.put("qxdm", qxdm);
        return userMap;
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return 页面地址
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 受理页面评价器
     */
    @ResponseBody
    @GetMapping("")
    public SlymPjqDTO evaluate(String gzlslid, Boolean groupByQxdm, @RequestParam(value = "taskid", required = false) String taskid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("工作流实例ID不能为空！");
        }
        SlymPjqDTO slymPjqDTO = new SlymPjqDTO();
        UserDto userDto = userManagerUtils.getCurrentUser();
        slymPjqDTO.setJobNumber(userDto.getJobNumber());
        slymPjqDTO.setUsername(userDto.getUsername());
        slymPjqDTO.setPictureId(signImageUrl + userDto.getPictureId());
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listAllBdcQlr(gzlslid, CommonConstantUtils.QLRLB_QLR, "");
        //同时根据名称和证件号去重
        List<BdcQlrDO> bdcQlrDOS = bdcQlrDOList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getQlrmc() + ";" + o.getZjh()))), ArrayList::new));
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            slymPjqDTO.setBdcXmDO(bdcXmDOList.get(0));
        }
        if (CollectionUtils.isNotEmpty(bdcQlrDOS)) {
            slymPjqDTO.setQlrmc(StringToolUtils.resolveBeanToAppendStr(bdcQlrDOS, "getQlrmc", ","));
            slymPjqDTO.setQlrlxfs(StringToolUtils.resolveBeanToAppendStr(bdcQlrDOS, "getDh", ","));
            slymPjqDTO.setDlrmc(StringToolUtils.resolveBeanToAppendStr(bdcQlrDOS, "getDlrmc", ","));
            slymPjqDTO.setDlrlxfs(StringToolUtils.resolveBeanToAppendStr(bdcQlrDOS, "getDlrdh", ","));
        }
        if (Boolean.TRUE.equals(groupByQxdm)) {
            slymPjqDTO.setUrl(this.getPjqUrlByQxdm());
//            slymPjqDTO.setUrl("exchange");
        } else {
            slymPjqDTO.setUrl(pjqConfig.getUrl());
        }
        if (StringUtils.isNotBlank(taskid)) {
            TaskData taskData = processTaskClient.getTaskById(taskid);
            if (Objects.nonNull(taskData)) {
                slymPjqDTO.setJdmc(taskData.getTaskName());
            }
        }
        return slymPjqDTO;
    }

    /**
     * 根据区县代码获取配置的评价器地址
     * 未获取到对应区县的评价器地址时，默认采用 pjq.url 地址
     */
    private String getPjqUrlByQxdm(){
        // 按照区县代码获取当前用户所在区县的评价器地址
        String qxdm = userManagerUtils.getRegionCodeByUserName(userManagerUtils.getCurrentUserName());
        String pjqUrl = pjqConfig.getQxdmUrlMap().get(qxdm);
        // 未获取到对应区县配置的评价器 URL 地址时，采用默认的 pjq.url 地址
        if(StringUtils.isBlank(pjqUrl)){
            pjqUrl = pjqConfig.getUrl();
        }
        return pjqUrl;
    }


    /**
     * @param gzlslid 工作流实例ID
     * @return 页面地址
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 受理页面评价器
     */
    @ResponseBody
    @GetMapping("/evaluateFdjlc")
    public SlymPjqDTO evaluateFdjlc(String gzlslid, String xmid, Boolean groupByQxdm) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("工作流实例ID不能为空！");
        }
        SlymPjqDTO slymPjqDTO = new SlymPjqDTO();
        BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxFeignService.queryBdcSlJbxxByGzlslid(gzlslid);
        if (bdcSlJbxxDO != null) {
            slymPjqDTO.setSlbh(bdcSlJbxxDO.getSlbh());
            slymPjqDTO.setSlr(bdcSlJbxxDO.getSlr());
        }

        List<BdcSlSqrDO> bdcSlSqrDOList = bdcSlSqrFeignService.listBdcSlSqrByXmid(xmid, CommonConstantUtils.QLRLB_QLR);
        if (CollectionUtils.isNotEmpty(bdcSlSqrDOList)) {
            slymPjqDTO.setQlrmc(StringToolUtils.resolveBeanToAppendStr(bdcSlSqrDOList, "getSqrmc", ","));
            slymPjqDTO.setQlrlxfs(StringToolUtils.resolveBeanToAppendStr(bdcSlSqrDOList, "getDh", ","));
            slymPjqDTO.setDlrmc(StringToolUtils.resolveBeanToAppendStr(bdcSlSqrDOList, "getDlrmc", ","));
            slymPjqDTO.setDlrlxfs(StringToolUtils.resolveBeanToAppendStr(bdcSlSqrDOList, "getDlrdh", ","));
        }
        if(Boolean.TRUE.equals(groupByQxdm)){
            slymPjqDTO.setUrl(this.getPjqUrlByQxdm());
        }else{
            slymPjqDTO.setUrl(pjqConfig.getUrl());
        }
        return slymPjqDTO;
    }

    @ResponseBody
    @PostMapping("evaluate")
    public Object insertPjjg(@RequestBody String json, @RequestParam(name = "taskId") String taskId) {
        JSONObject obj = JSONObject.parseObject(json);
        Integer count = 0;
        if (obj != null) {
            BdcSlPjqDO bdcSlPjqDO = JSONObject.parseObject(JSON.toJSONString(obj), BdcSlPjqDO.class);
            //根据taskId获取当前流程节点名称（受理还是发证）
            TaskData taskData = processTaskClient.getTaskById(taskId);
            if (bdcSlPjqDO != null) {
                bdcSlPjqDO.setJdmc(taskData.getTaskName());
                if (StringUtils.isNotBlank(bdcSlPjqDO.getYwbh()) && StringUtils.isNotBlank(bdcSlPjqDO.getJdmc())) {
                    BdcSlPjqDO bdcSlPjq = bdcSlPjqFeignService.queryBdcSlPjqByYwbh(bdcSlPjqDO.getYwbh(), bdcSlPjqDO.getJdmc());
                    if (bdcSlPjq != null) {
                        count = 1;
                    } else {
                        bdcSlPjqFeignService.insertBdcSlPjq(bdcSlPjqDO);
                        count = 2;
                    }
                }
            }
        }
        return count;
    }

    /**
     * 判断是否已经评价
     *
     * @param
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @ResponseBody
    @GetMapping("/isPj")
    public Object isPj(@RequestParam(name = "taskId") String taskId, @RequestParam(name = "ywbh") String ywbh) {
        Integer whetherPj = 2;
        if (StringUtils.isNotBlank(taskId) && StringUtils.isNotBlank(ywbh)) {
            TaskData taskData = processTaskClient.getTaskById(taskId);
            if (taskData != null) {
                BdcSlPjqDO bdcSlPjq = bdcSlPjqFeignService.queryBdcSlPjqByYwbh(ywbh, taskData.getTaskName());
                if (bdcSlPjq != null) {
                    whetherPj = 1;
                }
            }
        } else {
            throw new MissingArgumentException("缺失参数，请检查！");
        }

        return whetherPj;
    }


    /**
     * 推送评价结果至省厅
     * @param slymPjqDTO 评价器所需参数
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 评价结果
     */
    @ResponseBody
    @PostMapping("/saveAndTspjjg")
    public Object tsPjjg(@RequestBody SlymPjqDTO slymPjqDTO) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setSlbh(slymPjqDTO.getSlbh());
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if(CollectionUtils.isEmpty(bdcXmDOList)){
            throw new AppException("未获取到不动产项目信息");
        }
        slymPjqDTO.setBdcXmDO(bdcXmDOList.get(0));
        // 设置受理人
        UserDto userDto = userManagerUtils.getCurrentUser();
        // 设置区县代码
        slymPjqDTO.setSlr(userDto.getAlias());
        if(CollectionUtils.isNotEmpty(userDto.getOrgRecordList())){
            slymPjqDTO.setQxdm(userDto.getOrgRecordList().get(0).getRegionCode());
        }
        List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listAllBdcQlr(bdcXmDOList.get(0).getGzlslid(), CommonConstantUtils.QLRLB_QLR, "");
        //同时根据名称和证件号去重
        List<BdcQlrDO> bdcQlrDOS = bdcQlrDOList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getQlrmc() + ";" + o.getZjh()))), ArrayList::new));
        if(CollectionUtils.isNotEmpty(bdcQlrDOS)){
            slymPjqDTO.setQlrmc(StringToolUtils.resolveBeanToAppendStr(bdcQlrDOS, "getQlrmc", ","));
            slymPjqDTO.setQlrlxfs(StringToolUtils.resolveBeanToAppendStr(bdcQlrDOS, "getDh", ","));
        }
        if(StringUtils.isNotEmpty(slymPjqDTO.getTaskId())){
            TaskData taskData = processTaskClient.getTaskById(slymPjqDTO.getTaskId());
            if (Objects.nonNull(taskData)) {
                slymPjqDTO.setJdmc(taskData.getTaskName());
            }
        }
        slymPjqDTO.setPjsj(new Date());

        // 推送评价结果至省厅
        Object response = exchangeInterfaceFeignService.requestInterface("send_pjxx_to_sf", slymPjqDTO);
        // 保存评价结果信息
        this.bdcSlPjqFeignService.insertBdcSlPjq(convertPjqjg(slymPjqDTO, userDto));
        return response;
    }

    private BdcSlPjqDO convertPjqjg(SlymPjqDTO slymPjqDTO, UserDto userDto){
        BdcSlPjqDO bdcSlPjqDO = new BdcSlPjqDO();
        BeanUtils.copyProperties(slymPjqDTO, bdcSlPjqDO);
        bdcSlPjqDO.setYwbh(slymPjqDTO.getSlbh());
        bdcSlPjqDO.setBlry(userDto.getAlias());
        bdcSlPjqDO.setBlryid(userDto.getUsername());
        BdcXmDO bdcXmDO = slymPjqDTO.getBdcXmDO();
        bdcSlPjqDO.setYwmc(bdcXmDO.getGzldymc());
        bdcSlPjqDO.setYwblsj(bdcXmDO.getSlsj());
        bdcSlPjqDO.setDjbmdm(bdcXmDO.getDjbmdm());
        bdcSlPjqDO.setDjbmmc(bdcXmDO.getDjjg());
        bdcSlPjqDO.setSqrxm(slymPjqDTO.getQlrmc());
        bdcSlPjqDO.setSqrlxfs(slymPjqDTO.getQlrlxfs());
        return bdcSlPjqDO;
    }

    @ResponseBody
    @GetMapping("/pdfpath")
    public String getPdfPath(BdcSlPrintDTO bdcSlPrintDTO) throws IOException{
        zxlc = bdcSlPrintDTO.getZxlc();
        modelurl = bdcSlPrintDTO.getUrl();
        String path = "";
        //1.现获取准确的打印类型
        String dylx = "";
        String djxl = "";
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(bdcSlPrintDTO.getGzlslid());
        if (StringUtils.isNotBlank(bdcSlPrintDTO.getXmid())) {
            bdcXmQO.setXmid(bdcSlPrintDTO.getXmid());
        }
        if (StringUtils.isNotBlank(bdcSlPrintDTO.getDjxl())){
            djxl = bdcSlPrintDTO.getDjxl();
        }
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        dylx = getCommonDylx(bdcXmDOList, bdcSlPrintDTO);
        bdcSlPrintDTO.setDylx(StringUtils.isNotBlank(dylx) ? dylx : bdcSlPrintDTO.getDylx());
        bdcSlPrintDTO.setZxlc(StringUtils.isNotBlank(bdcSlPrintDTO.getZxlc()) ? bdcSlPrintDTO.getZxlc() : CommonConstantUtils.BOOL_FALSE);
        //模板路径
        String modelPath = printPath + bdcSlPrintDTO.getDylx() + CommonConstantUtils.WJHZ_DOCX;
        LOGGER.info("生成模板路径:{}", modelPath);
        //生成xml
        //sjd和sqs获取xml方法不同
        String xml = "";
        if (ArrayUtils.contains(CommonConstantUtils.Sl_DJ_DYLX, bdcSlPrintDTO.getDylx())) {
            xml = bdcSlPrintFeignService.packPrintXml(bdcSlPrintDTO.getGzlslid(), bdcSlPrintDTO.getDylx(), bdcSlPrintDTO.getZxlc(), StringUtils.join(bdcSlPrintDTO.getSjclids(), ","), userManagerUtils.getCurrentUserName());
        }
        //合肥存量房买卖合同只展示产权的传xmid生成xml
        if (StringUtils.equals(CommonConstantUtils.DYLX_CLFMMHT, bdcSlPrintDTO.getDylx())) {
            xml = bdcSlPrintFeignService.packPrintXmlToXmid(bdcSlPrintDTO.getGzlslid(), bdcSlPrintDTO.getDylx(), bdcSlPrintDTO.getZxlc(), StringUtils.join(bdcSlPrintDTO.getSjclids(), ","), userManagerUtils.getCurrentUserName(), bdcSlPrintDTO.getXmid());
        }
        if (ArrayUtils.contains(CommonConstantUtils.Sl_ALL_DYLX, bdcSlPrintDTO.getDylx())) {
            Integer lclx = bdcXmFeignService.makeSureBdcXmLx(bdcSlPrintDTO.getGzlslid());
            if (CommonConstantUtils.LCLX_PL.equals(lclx) || CommonConstantUtils.LCLX_PLZH.equals(lclx)) {
                bdcSlPrintDTO.setDylx(bdcSlPrintDTO.getDylx() + CommonConstantUtils.DYLX_HZPL);
            }
            xml = bdcSlPrintFeignService.packPrintDatas(bdcSlPrintDTO.getGzlslid(), bdcSlPrintDTO.getDylx(), bdcSlPrintDTO.getQlrlb(), bdcXmDOList.get(0).getXmid(), StringUtils.join(bdcSlPrintDTO.getSjclids(), ","));
        }
        if (ArrayUtils.contains(CommonConstantUtils.SL_ZD_DYLX, bdcSlPrintDTO.getDylx())) {
            xml = bdcSlPrintFeignService.packPrintDatasAndReplaceZd(bdcSlPrintDTO.getGzlslid(), bdcSlPrintDTO.getDylx(), bdcSlPrintDTO.getQlrlb(), bdcXmDOList.get(0).getXmid(), StringUtils.join(bdcSlPrintDTO.getSjclids(), ","));
        }
        if (ArrayUtils.contains(CommonConstantUtils.SL_JFXX_DYLX, bdcSlPrintDTO.getDylx())){
            xml = bdcSlPrintFeignService.packPrintDatasWithSfxxId(bdcSlPrintDTO.getGzlslid(), bdcSlPrintDTO.getDylx(),bdcSlPrintDTO.getSfxxid());
            LOGGER.info("生成xml及传入参数:{},{},{},{}", xml,bdcSlPrintDTO.getDylx(),bdcSlPrintDTO.getGzlslid(),bdcSlPrintDTO.getSfxxid());
        }
        OfficeExportDTO officeExportDTO = new OfficeExportDTO();
        officeExportDTO.setModelName(modelPath);
        officeExportDTO.setXmlData(xml);
        officeExportDTO.setFileName(bdcSlPrintDTO.getDylx() + bdcXmDOList.get(0).getXmid());
        path = pdfController.generatePdfFile(officeExportDTO);
        if (ArrayUtils.contains(CommonConstantUtils.SL_JFXX_DYLX, bdcSlPrintDTO.getDylx())){
            StorageDto storage;
            if (Objects.equals("ewmtspjq", bdcSlPrintDTO.getDylx())){
                storage = (StorageDto)uploadFile(bdcSlPrintDTO.getGzlslid(),path,"缴费二维码",djxl,bdcSlPrintDTO.getPdflx());
            }else {
                storage = (StorageDto)uploadFile(bdcSlPrintDTO.getGzlslid(),path,"合一支付二维码",djxl,bdcSlPrintDTO.getPdflx());
            }
            LOGGER.info("上传大云结果:{}", storage);
            return storage.getDownUrl();
        }
        LOGGER.info("生成的临时文件路径:{}",path);
        if (sfscwjzx){
            StorageDto storageDto;
            if (Objects.equals(1,bdcSlPrintDTO.getPdflx())){
                storageDto = (StorageDto)uploadFile(bdcSlPrintDTO.getGzlslid(),path,CommonConstantUtils.CLMC_SQS,djxl,bdcSlPrintDTO.getPdflx());
            }else if(Objects.equals(2,bdcSlPrintDTO.getPdflx())){
                storageDto = (StorageDto)uploadFile(bdcSlPrintDTO.getGzlslid(),path,CommonConstantUtils.CLMC_SJD,djxl,bdcSlPrintDTO.getPdflx());
            }else{
                storageDto = (StorageDto)uploadFile(bdcSlPrintDTO.getGzlslid(),path,CommonConstantUtils.CLMC_JYHT,djxl,bdcSlPrintDTO.getPdflx());
            }
            LOGGER.info("上传大云结果:{}", storageDto);
            return storageDto.getDownUrl();
        }else {
            return path;
        }
    }

    @ResponseBody
    @GetMapping("/bdcxm")
    public List<BdcXmDO> queryBdcXm(String gzlslid, String xmid, String djxl) {
        List<BdcXmDO> bdcXmDOList = new ArrayList<>();
        int xmlx = bdcXmFeignService.makeSureBdcXmLx(gzlslid);
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        //组合流程传xmid，每个项目单独保存各自的签名信息
        if (StringUtils.isNotBlank(xmid) && (CommonConstantUtils.LCLX_ZH.equals(xmlx))) {
            bdcXmQO.setXmid(xmid);
        }
        //批量组合根据gzlslid和djxl获取
        if (StringUtils.isNotBlank(djxl) && CommonConstantUtils.LCLX_PLZH.equals(xmlx)) {
            bdcXmQO.setDjxl(djxl);
        }
        List<BdcXmDO> bdcXmList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmList)) {
            //单个流程和批量放入的一个，批量和组合获取全部
            if (CommonConstantUtils.LCLX_PT.equals(xmlx)) {
                bdcXmDOList.addAll(bdcXmList);
            } else if (CommonConstantUtils.LCLX_PL.equals(xmlx)) {
                bdcXmDOList.add(bdcXmList.get(0));
            } else if (CommonConstantUtils.LCLX_ZH.equals(xmlx)) {
                bdcXmDOList.addAll(bdcXmList);
            } else if (CommonConstantUtils.LCLX_PLZH.equals(xmlx)) {
                bdcXmDOList.addAll(bdcXmList);
            }
        }
        return bdcXmDOList;
    }


    @ResponseBody
    @GetMapping("/getPdfBase64")
    public String getPdfBase64(@RequestParam(name = "pdf") String pdf) {
        if (StringUtils.isNotBlank(pdf)) {
            return Base64Utils.getPDFBinary(new File(pdf));
        }
        return null;
    }

    /**
     * @param bdcQzxxDTO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 保存签字信息
     * @date : 2020/1/10 10:09
     */
    @ResponseBody
    @PostMapping("/save")
    public Object savePjgQzxx(@RequestBody BdcQzxxDTO bdcQzxxDTO, String djxl) {
        Integer count = 0;
        if (null != bdcQzxxDTO) {
            if (CheckParameter.checkAnyParameter(bdcQzxxDTO, "xmid,gzlslid,slbh,bdlx,qzrlb")) {
                throw new MissingArgumentException("查询签字信息缺失参数");
            }
            //根据xmid获取到是否存在代理人，有代理人默认是代理人签字
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setXmid(bdcQzxxDTO.getXmid());
            bdcQlrQO.setQlrlb(bdcQzxxDTO.getQzrlb().toString());
            List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
            //确认书不需要判断是否为代理人签字
            BdcQzxxDO bdcQzxxDO = new BdcQzxxDO();
            BeanUtils.copyProperties(bdcQzxxDTO,bdcQzxxDO);
            if (CollectionUtils.isNotEmpty(bdcQlrDOList) && !Objects.equals(5,bdcQzxxDO.getBdlx())) {
                String dlr = bdcQlrDOList.get(0).getDlrmc();
                if (StringUtils.isNotBlank(dlr)) {
                    if (bdcQzxxDTO.getQzrlb() == 1) {
                        //权利人代理人
                        bdcQzxxDO.setQzrlb(2);
                    } else if (bdcQzxxDTO.getQzrlb() == 3) {
                        //义务人代理人
                        bdcQzxxDO.setQzrlb(4);
                    }
                }
            }
            List<BdcQzxxDO> bdcQzxxDOList = qzxxFeginService.queryBdcQzxx(bdcQzxxDO);
            if (CollectionUtils.isNotEmpty(bdcQzxxDOList)) {
                bdcQzxxDO.setId(bdcQzxxDOList.get(0).getId());
                if(StringUtils.isNotBlank(bdcQzxxDOList.get(0).getFid())) {
                    StorageDto storageDto = storageClient.findById(bdcQzxxDOList.get(0).getFid());
                    if(Objects.nonNull(storageDto)) {
                        boolean res = storageClient.deleteStorages(Stream.of(bdcQzxxDOList.get(0).getFid()).collect(Collectors.toList()));
                        LOGGER.warn("文件删除结果：{}。删除信息为：{}", res, JSONObject.toJSONString(storageDto));
                    }
                }
                uploadQzxx(bdcQzxxDTO,bdcQzxxDO);
                count = qzxxFeginService.updateBdcQzxx(bdcQzxxDO);
            } else {
                uploadQzxx(bdcQzxxDTO,bdcQzxxDO);
                qzxxFeginService.insertBdcQzxx(bdcQzxxDO);
                count = 1;
            }
        }
        return count;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  保存签字信息
     */
    @ResponseBody
    @PostMapping("/saveQzxx")
    public Object saveQzxx(@RequestBody BdcQzxxDTO bdcQzxxDTO) {
        Integer count = 0;
        if (null != bdcQzxxDTO) {
            if (CheckParameter.checkAnyParameter(bdcQzxxDTO, "xmid,gzlslid,slbh,bdlx,qzrlb")) {
                throw new MissingArgumentException("查询签字信息缺失参数");
            }
            BdcQzxxDO bdcQzxxDO = new BdcQzxxDO();
            BeanUtils.copyProperties(bdcQzxxDTO,bdcQzxxDO);
            List<BdcQzxxDO> bdcQzxxDOList = qzxxFeginService.queryBdcQzxx(bdcQzxxDO);
            if (CollectionUtils.isNotEmpty(bdcQzxxDOList)) {
                bdcQzxxDO.setId(bdcQzxxDOList.get(0).getId());
                if(StringUtils.isNotBlank(bdcQzxxDOList.get(0).getFid())) {
                    StorageDto storageDto = storageClient.findById(bdcQzxxDOList.get(0).getFid());
                    if(Objects.nonNull(storageDto)) {
                        boolean res = storageClient.deleteStorages(Stream.of(bdcQzxxDOList.get(0).getFid()).collect(Collectors.toList()));
                        LOGGER.warn("文件删除结果：{}。删除信息为：{}", res, JSONObject.toJSONString(storageDto));
                    }
                }
                uploadQzxx(bdcQzxxDTO,bdcQzxxDO);
                count = qzxxFeginService.updateBdcQzxx(bdcQzxxDO);
            } else {
                uploadQzxx(bdcQzxxDTO,bdcQzxxDO);
                qzxxFeginService.insertBdcQzxx(bdcQzxxDO);
                count = 1;
            }
        }
        return count;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  查询签字信息
     */
    @ResponseBody
    @GetMapping("/qznrxx")
    public Map<Integer,String> queryQznrxx(BdcQzxxDO bdcQzxxDO) {
        Map<Integer,String> qznrxx =new HashMap<>();
        if (!CheckParameter.checkAnyParameter(bdcQzxxDO,"xmid","gzlslid")) {
            throw new AppException("空参数异常");
        }
        List<BdcQzxxDO> qzxxList= qzxxFeginService.queryBdcQzxx(bdcQzxxDO);
        if(CollectionUtils.isNotEmpty(qzxxList)) {
            for(BdcQzxxDO qzxx:qzxxList){
                BaseResultDto baseResultDto = storageClient.downloadBase64(qzxx.getFid());
                if(baseResultDto != null && baseResultDto.getMsg() != null &&qzxx.getQzrlb() != null) {
                    qznrxx.put(qzxx.getQzrlb(),baseResultDto.getMsg());
                }

            }
        }

        return qznrxx;

    }




    /**
     * @param gzlslid 工作流实例id
     * @param djxl 登记小类
     * @param pdflx 打印的pdf类型
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2020/7/17 10:09
     */
    @ResponseBody
    @GetMapping("/upload")
    public void uploadMmht(String gzlslid, String djxl, Integer pdflx) throws IOException {
        //查询权利人义务人是否都已经签字
        BdcQzxxDO bdcQzxxDO = new BdcQzxxDO();
        bdcQzxxDO.setGzlslid(gzlslid);
        List<BdcQzxxDO> bdcQzxxDOList = qzxxFeginService.queryBdcQzxx(bdcQzxxDO);
        //权利人签字信息
        List<BdcQzxxDO> qlrQzxxList = bdcQzxxDOList.stream().filter(bdcQzxx -> (Objects.nonNull(bdcQzxx.getQzrlb())) && (bdcQzxx.getQzrlb() == 1 || bdcQzxx.getQzrlb() == 2)).collect(Collectors.toList());
        //义务人签字信息
        List<BdcQzxxDO> ywrQzxxList = bdcQzxxDOList.stream().filter(bdcQzxx -> (Objects.nonNull(bdcQzxx.getQzrlb())) && (bdcQzxx.getQzrlb() == 3 || bdcQzxx.getQzrlb() == 4)).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(qlrQzxxList) && CollectionUtils.isNotEmpty(ywrQzxxList)) {
            //都不为空重新获取都签完字后的合同路径
            BdcSlPrintDTO bdcSlPrintDTO = new BdcSlPrintDTO();
            String path = "";
            if(Objects.equals(5,pdflx)) {
                bdcSlPrintDTO.setDylx(CommonConstantUtils.DYLX_QRS);
                bdcSlPrintDTO.setGzlslid(gzlslid);
                bdcSlPrintDTO.setZxlc(zxlc);
                bdcSlPrintDTO.setUrl(modelurl);
                path = getPdfPath(bdcSlPrintDTO);
                LOGGER.info("生成文件地址为：{}", path);
                uploadFile(gzlslid, path, CommonConstantUtils.CLMC_QRS, djxl,pdflx);
            } else {
                bdcSlPrintDTO.setDylx(CommonConstantUtils.DYLX_CLFMMHT);
                bdcSlPrintDTO.setGzlslid(gzlslid);
                bdcSlPrintDTO.setZxlc(zxlc);
                bdcSlPrintDTO.setUrl(modelurl);
                path = getPdfPath(bdcSlPrintDTO);
                LOGGER.info("生成文件地址为：{}", path);
                uploadFile(gzlslid, path, CommonConstantUtils.CLMC_JYHT, djxl,pdflx);
            }
        }
    }

    /**
     * 舒城评价器文件上传
     * @throws IOException
     */
    @ResponseBody
    @PostMapping("/uploadJypjq")
    public void uploadJypjq(@RequestBody JypjqDTO dto) throws IOException {
        String pdfBase64 = dto.getPdfBase64();
        Integer pdflx = dto.getPdflx();
        String gzlslid = dto.getGzlslid();
        String djxl = dto.getDjxl();
        MultipartFile file = Base64Utils.base64ToMultipart(CommonConstantUtils.BASE64_QZ_PDF + pdfBase64);
        String clmc = pdflx==1?CommonConstantUtils.CLMC_SQS:pdflx==2?CommonConstantUtils.CLMC_SJD:CommonConstantUtils.CLMC_JYHT;
        this.uploadFileCommon(file,gzlslid,clmc,djxl,pdflx);
    }


    /**
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 评价签字完上传文件到文件中心
     * @date : 2020/7/6 9:38
     */
    public Object uploadFile(String gzlslid, String path, String clmc, String djxl, Integer pdflx) throws IOException {
        //都不为空重新获取都签完字后的合同路径
        String base64String = Base64Utils.getPDFBinary(new File(path));
        MultipartFile file = Base64Utils.base64ToMultipart(CommonConstantUtils.BASE64_QZ_PDF + base64String);
        return uploadFileCommon(file,gzlslid,clmc,djxl,pdflx);
    }

    /**上传文件公共方法*/
    private Object uploadFileCommon(MultipartFile file,String gzlslid, String clmc,String djxl,Integer pdflx) throws IOException {
        if (file != null) {
            //受理库同时新增文件
            List<BdcSlSjclDO> bdcSlSjclDOList = bdcSlSjclFeignService.listBdcSlSjclByGzlslid(gzlslid);
            //判断当前流程是否已存在相同名称的文件
            List<BdcSlSjclDO> sjclList = bdcSlSjclDOList.stream().filter(bdcSlSjclDO -> StringUtils.equals(clmc, bdcSlSjclDO.getClmc())).collect(Collectors.toList());
            //查询文件中心是否存在同名文件
            BdcSlSjclDO bdcSlSjclDO = new BdcSlSjclDO();
            if (CollectionUtils.isEmpty(sjclList)) {
                StorageDto storageDto = storageClient.createRootFolder(Constants.WJZX_CLIENTID, gzlslid, clmc, "");
                bdcSlSjclDO.setSjclid(UUIDGenerator.generate16());
                bdcSlSjclDO.setGzlslid(gzlslid);
                bdcSlSjclDO.setWjzxid(storageDto.getId());
                bdcSlSjclDO.setClmc(clmc);
                bdcSlSjclDO.setYs(1);
                bdcSlSjclDO.setFs(1);
                bdcSlSjclDO.setSjlx(1);
                bdcSlSjclDO.setXh(CollectionUtils.size(bdcSlSjclDOList) + 1);
                bdcSlSjclFeignService.insertBdcSlSjcl(bdcSlSjclDO);
            } else {
                bdcSlSjclDO = sjclList.get(0);
            }
            //上传到文件中心
            //批量流程根据项目数量上传
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setGzlslid(gzlslid);
            if (StringUtils.isNotBlank(djxl)) {
                bdcXmQO.setDjxl(djxl);
            }
            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                StorageDto storageDto = null;
                for (BdcXmDO bdcXmDO : bdcXmDOList) {
                   storageDto = uploadFileToFilecenter(bdcXmDO, bdcSlSjclDO, file,pdflx);
                }
                return storageDto;
            }
        }
        return null;
    }

    private StorageDto uploadFileToFilecenter(BdcXmDO bdcXmDO, BdcSlSjclDO bdcSlSjclDO, MultipartFile file, Integer pdflx) throws IOException {
        MultipartDto multipartDto = new MultipartDto();
        multipartDto.setClientId(Constants.WJZX_CLIENTID);
        multipartDto.setName(bdcSlSjclDO.getClmc());
        multipartDto.setNodeId(bdcSlSjclDO.getWjzxid());
        multipartDto.setData(file.getBytes());
        multipartDto.setContentType(file.getContentType());
        multipartDto.setSize(file.getSize());
        if(Objects.equals(5,pdflx)) {
            multipartDto.setOriginalFilename(CommonConstantUtils.CLMC_QRS+ bdcXmDO.getXmid() + CommonConstantUtils.WJHZ_PDF);
        } else if(Objects.equals(1,pdflx)){
            multipartDto.setOriginalFilename(CommonConstantUtils.CLMC_SQS+ bdcXmDO.getXmid() + CommonConstantUtils.WJHZ_PDF);
        } else if (Objects.equals(2,pdflx)){
            multipartDto.setOriginalFilename(CommonConstantUtils.CLMC_SJD+ bdcXmDO.getXmid() + CommonConstantUtils.WJHZ_PDF);
        }else{
            multipartDto.setOriginalFilename(CommonConstantUtils.WJMC_CLFMMHT + bdcXmDO.getXmid() + CommonConstantUtils.WJHZ_PDF);
        }
        //先查询大云是否存在相同的文件信息,存在替换,不存在上传
        List<StorageDto> storageDtoList = storageClient.listAllSubsetStorages(bdcSlSjclDO.getWjzxid(), multipartDto.getOriginalFilename(), null, null, null,null);
        StorageDto storageDto;
        if (CollectionUtils.isNotEmpty(storageDtoList)) {
            multipartDto.setRename(0);
//            storageDto = storageClient.replaceUpload(bdcSlSjclDO.getWjzxid(), multipartDto);
            storageDto = storageClient.multipartUpload(multipartDto);
        } else {
            storageDto = storageClient.multipartUpload(multipartDto);
        }
        LOGGER.info("是否上传大云文件中心:{}",sfscwjzx);
        if (sfscwjzx){
            return storageDto;
        }else {
            return null;
        }
    }



    /**
     * @param bdcQzxxDO 签字信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2020/7/28 14:07
     */
    public void uploadQzxx(BdcQzxxDTO bdcQzxxDTO,BdcQzxxDO bdcQzxxDO){
        StorageDto storageDto = storageClient.createRootFolder(CommonConstantUtils.WJZX_CLIENTID_QZXX,CommonConstantUtils.SPACEID_QZXX,CommonConstantUtils.WJMC_QZXX,null);
        MultipartFile multipartFile = Base64Utils.base64ToMultipart(CommonConstantUtils.BASE64_QZ_IMAGE + bdcQzxxDTO.getQznr());
        if(Objects.nonNull(storageDto)) {
            try {
                MultipartDto multipartDto = getUploadParamDto("",storageDto,multipartFile,CommonConstantUtils.WJZX_CLIENTID_QZXX);
                StorageDto storage = storageClient.multipartUpload(multipartDto);
                if(Objects.nonNull(storage)) {
                    bdcQzxxDO.setFid(storage.getId());
                    //字段暂时不去掉，但是不保存内容
                    bdcQzxxDO.setQznr("");
                }
            } catch (IOException e) {
                LOGGER.error("组织签名图片文件上传错误{}", e.getMessage());
            }
        }
    }


    /**
     * @param userName,storageDto,file,client
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 组织文件数据DTO
     * @date : 2020/7/28 11:06
     */
    public static MultipartDto getUploadParamDto(String userName, StorageDto storageDto, MultipartFile file,String client) throws IOException {
        MultipartDto multipartDto = new MultipartDto();
        multipartDto.setNodeId(storageDto.getId());
        multipartDto.setClientId(client);
        if (file != null) {
            multipartDto.setData(file.getBytes());
            multipartDto.setOwner(userName);
            multipartDto.setContentType(file.getContentType());
            multipartDto.setSize(file.getSize());
            multipartDto.setOriginalFilename(file.getOriginalFilename());
            multipartDto.setName(file.getName());
        }
        return multipartDto;
    }

    /**
     * @param bdcMkPjqRequestDTO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新版摩科评价器post 接口，调用exchange交互
     * @date : 2021/11/12 14:08
     */
    @GetMapping("/xmoke")
    @ResponseBody
    public Object newMkPjq(HttpServletRequest request, BdcMkPjqRequestDTO bdcMkPjqRequestDTO) {
        if (StringUtils.isBlank(bdcMkPjqRequestDTO.getYwbh())) {
            throw new AppException("调用评价器缺失业务编号");
        }
        bdcMkPjqRequestDTO.setSysIp(IPPortUtils.getClientIp(request));
        bdcMkPjqRequestDTO.setTimeOut("30");
        LOGGER.info("当前流程受理编号{},调用摩科评价器业务参数{}", bdcMkPjqRequestDTO.getYwbh(), JSON.toJSONString(bdcMkPjqRequestDTO));
        //评价结束后要记录到评价表
        //先查询是否有数据，有数据更新评价时间，无数据，新增
        BdcSlPjqDO bdcSlPjqDO = bdcSlPjqFeignService.queryBdcSlPjqByYwbh(bdcMkPjqRequestDTO.getYwbh(), CommonConstantUtils.JD_SL);
        if (Objects.nonNull(bdcSlPjqDO)) {
            bdcSlPjqDO.setPjsj(new Date());
            bdcSlPjqFeignService.updateBdcSlPjq(bdcSlPjqDO);
        } else {
            bdcSlPjqDO = new BdcSlPjqDO();
            bdcSlPjqDO.setPjsj(new Date());
            bdcSlPjqDO.setBlry(userManagerUtils.getCurrentUserName());
            bdcSlPjqDO.setYwbh(bdcMkPjqRequestDTO.getYwbh());
            bdcSlPjqDO.setSqrxm(bdcMkPjqRequestDTO.getSqrxm());
            bdcSlPjqDO.setSqrlxfs(bdcMkPjqRequestDTO.getSqrlxfs());
            bdcSlPjqDO.setJdmc(CommonConstantUtils.JD_SL);
            bdcSlPjqFeignService.insertBdcSlPjq(bdcSlPjqDO);
        }
        return exchangeInterfaceFeignService.requestInterface("mk_pj", bdcMkPjqRequestDTO);
    }

    /**
     * 存入评价器表中
     */
    @GetMapping("/insertPjq")
    @ResponseBody
    public void insertPjq(BdcMkPjqRequestDTO bdcMkPjqRequestDTO){
        String jdsl = CommonConstantUtils.JD_SL;
        try {
            jdsl = URLEncoder.encode(CommonConstantUtils.JD_SL, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            LOGGER.error("节点名称编解码失败！");
        }
        BdcSlPjqDO bdcSlPjqDO = bdcSlPjqFeignService.queryBdcSlPjqByYwbh(bdcMkPjqRequestDTO.getYwbh(), jdsl);
        if (Objects.nonNull(bdcSlPjqDO)){
            bdcSlPjqDO.setPjsj(new Date());
            bdcSlPjqFeignService.updateBdcSlPjq(bdcSlPjqDO);
        }else {
            bdcSlPjqDO = new BdcSlPjqDO();
            bdcSlPjqDO.setPjsj(new Date());
            bdcSlPjqDO.setBlry(userManagerUtils.getCurrentUserName());
            bdcSlPjqDO.setYwbh(bdcMkPjqRequestDTO.getYwbh());
            bdcSlPjqDO.setSqrxm(bdcMkPjqRequestDTO.getSqrxm());
            bdcSlPjqDO.setSqrlxfs(bdcMkPjqRequestDTO.getSqrlxfs());
            bdcSlPjqDO.setJdmc(CommonConstantUtils.JD_SL);
            bdcSlPjqDO.setMyd("5");
            bdcSlPjqDO.setYwmc(bdcMkPjqRequestDTO.getYwmc());
            bdcSlPjqFeignService.insertBdcSlPjq(bdcSlPjqDO);
        }
    }
    /**
     * @param qlrmc,qlrzjh,gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 摩科人证对比接口 ，调用exchange 交互
     * @date : 2021/11/12 15:25
     */
    @GetMapping("/mkrzdb")
    @ResponseBody
    public Object mkrzdb(HttpServletRequest request, String qlrmc, String qlrzjh, String gzlslid) throws IOException {
        String ip=getPjqIp(request);
        return bdcSlPjqFeignService.mkrzdb(qlrmc,qlrzjh,gzlslid,ip);

    }

    /**
     * @param gzlslid 工作流实例ID
     * @param taskid 任务ID
     * @return 评价器信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据配置调用不同评价器
     */
    @GetMapping("/commonpj")
    @ResponseBody
    public Object commonPj(String gzlslid,String taskid, HttpServletRequest httpServletRequest) {
        if(StringUtils.isBlank(gzlslid) ||StringUtils.isBlank(taskid)){
            throw new AppException("评价器评价缺失参数");
        }
        String ip = getPjqIp(httpServletRequest);

        SlymPjqDTO slymPjqDTO= bdcSlPjqFeignService.commonPj(gzlslid,taskid,ip);
        return slymPjqDTO;
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return 评价器信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据配置调用不同人证对比
     */
    @GetMapping("/commonrzdb")
    @ResponseBody
    public Object commonPjqRzdb(HttpServletRequest request, String qlrmc, String qlrzjh, String gzlslid) {
        if(StringUtils.isBlank(gzlslid)){
            throw new AppException("人证对比缺失参数");
        }
        String ip = getPjqIp(request);

        return bdcSlPjqFeignService.commonRzdb(qlrmc,qlrzjh,gzlslid,ip);
    }


    /**
     * @description: 获取评价器的ip地址
     * @author: <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @date: 2022/12/26 14:51
     * @param request
     * @return: java.lang.String
     **/
    private String getPjqIp(HttpServletRequest request) {
        String ip = "";
        LOGGER.info("评价器转换ip,当前用户{}", userManagerUtils.getCurrentUserName());
        if (StringUtils.isNotBlank(userManagerUtils.getCurrentUserName())) {
            //尝试从配置中获取当前用户的ip
            ip = converZd("BDC_PJQ_IP", "pjq", userManagerUtils.getCurrentUserName());
            LOGGER.info("评价器转换ip,当前用户{}，转换结果为{}", userManagerUtils.getCurrentUserName(), ip);
        }
        if (StringUtils.isBlank(ip)) {
            ip = IPPortUtils.getClientIp(request);
            LOGGER.info("客户端ip：{}", ip);
        }
        if ("true".equals(usemac)) {
            ip = IPUtils.getLocalMac();
            LOGGER.info("评价器mac地址：{}", ip);
        }
        return ip;
    }

    /**
     * 评价器后台接口调用
     * <p>淮安评价器前端请求存在跨域问题，采用后台请求方式</p>
     * @param url 请求地址
     * @return 返回值内容
     */
    @GetMapping("/getPjqResponse")
    @ResponseBody
    public String haPjqHttp(String url){
        if(StringUtils.isBlank(url)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到请求评价的服务地址");
        }
        url = url.replaceAll("127.0.0.1", IPUtils.getLocalRealIP());
        HttpGet httpGet = new HttpGet(url);
        try {
            String response = httpClientService.doGet(httpGet);
            return response;
        } catch (IOException e) {
            LOGGER.error("调用评价器异常，错误内容：{}", e.getMessage());
        }
        return "";
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return 评价器信息
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 安徽获取好差评系统评价器页面地址(合肥 ， 舒城 ， 蚌埠 ）
     */
    @GetMapping("/hcp/pjymurl")
    @ResponseBody
    public Object getHcpUrl(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "taskId") String taskId) {
        if (StringUtils.isBlank(gzlslid) || StringUtils.isBlank(taskId)) {
            throw new AppException("省好差评缺失参数gzlslid或taskId");
        }
        Map param = new HashMap();
        param.put("gzlslid", gzlslid);
        param.put("taskId", taskId);
        UserDto userDto = userManagerUtils.getCurrentUser();
        String userId = "";
        if (userDto != null) {
            userId = userDto.getId();
        }
        param.put("userId", userId);

        return exchangeInterfaceFeignService.requestInterface("hcp_pjym", param);
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 保存评价结果
     */
    @PostMapping("/pjjl")
    @ResponseBody
    public void savePjjl(@RequestBody SlymPjqDTO slymPjqDTO){
        bdcSlPjqFeignService.savePjjl(slymPjqDTO);
    }



    public String converZd(String zdTable, String xtbs, String dsfzdz) {
        try {
            BdcZdDsfzdgxDO bdcZdDsfzdgxDO = new BdcZdDsfzdgxDO();
            bdcZdDsfzdgxDO.setZdbs(zdTable);
            bdcZdDsfzdgxDO.setDsfzdz(dsfzdz);
            bdcZdDsfzdgxDO.setDsfxtbs(xtbs);
            BdcZdDsfzdgxDO result = bdcZdFeignService.dsfZdgx(bdcZdDsfzdgxDO);
            if (Objects.nonNull(result)) {
                return result.getBdczdz();
            }
            return null;
        } catch (Exception e){
            return null;
        }
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description 宣城附件推送新版摩科评价器签字，调用exchange交互
     */
    @ResponseBody
    @GetMapping("/mk/sign")
    public Object mkQm(HttpServletRequest request, BdcSlPrintDTO bdcSlPrintDTO) throws IOException {
        //生成对应附件材料，并获取pdf路径
        String path = "";
        String xml = "";
        String gzlslid = bdcSlPrintDTO.getGzlslid();
        String zxlc = bdcSlPrintDTO.getZxlc();
        String fjlx = bdcSlPrintDTO.getFjlx();
        String djxl = bdcSlPrintDTO.getDjxl();

        LOGGER.info("后台接收前端传参为:{}", bdcSlPrintDTO);
        //前端打印类型为空时,生成申请书，不为空则为其他附件材料
        if (StringUtils.isNotBlank(gzlslid) && StringUtils.isNotBlank(djxl)){
            xml = bdcSlPrintFeignService.generateSlMkXml(gzlslid, djxl, zxlc);
        }

        String modelPath = printPath + djxl + CommonConstantUtils.WJHZ_DOCX;

        OfficeExportDTO officeExportDTO = new OfficeExportDTO();
        officeExportDTO.setModelName(modelPath);
        officeExportDTO.setXmlData(xml);
        officeExportDTO.setFileName(gzlslid + djxl);
        path = pdfController.generatePdfFile(officeExportDTO);

        //组织新摩科必传参数
        String tablePdf = "";
        String slbh = "";
        if (StringUtils.isNotBlank(bdcSlPrintDTO.getGzlslid())) {
            BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxFeignService.queryBdcSlJbxxByGzlslid(bdcSlPrintDTO.getGzlslid());
            if (bdcSlJbxxDO != null && StringUtils.isNotBlank(bdcSlJbxxDO.getSlbh())) {
                slbh = bdcSlJbxxDO.getSlbh();
            }
        }

        String sysIp = getPjqIp(request);
        //获取附件材料pdf的base64编码
        if (StringUtils.isNotBlank(path)) {
            tablePdf = Base64Utils.getPDFBinary(new File(path));
        }

        String beanName = "mk_txQm";
        BdcMkqmDTO bdcMkqmDTO = new BdcMkqmDTO();
        bdcMkqmDTO.setTablePdf(tablePdf);
        bdcMkqmDTO.setSlid(slbh);
        if (StringUtils.isNotBlank(bdcSlPrintDTO.getFjlx())) {
            bdcMkqmDTO.setFjlx(bdcSlPrintDTO.getFjlx());
        }
        bdcMkqmDTO.setSysIp(sysIp);
        LOGGER.info("工作流实例id,组织传参系统ip,附件类型:{}", gzlslid, sysIp, fjlx);

        //exchange调用新摩科签字
        Object response = exchangeInterfaceFeignService.requestInterface(beanName, bdcMkqmDTO);
        return response;
    }

    /**
     * 调用摩科评价器接口
     * @param request  HttpServletRequest
     * @param beanName 请求接口名称
     * @param bdcMkPjqRequestDTO 不动产摩科评价器请求参数
     * @return 摩科评价器接口返回值
     */
    @PostMapping("/mk/{beanName}")
    @ResponseBody
    public Object mkpjq(HttpServletRequest request,@PathVariable(value ="beanName")String beanName, @RequestBody BdcMkPjqRequestDTO bdcMkPjqRequestDTO){
        if(StringUtils.isBlank(beanName)) {
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到评价器请求参数信息");
        }
        bdcMkPjqRequestDTO.setSysIp(IPPortUtils.getClientIp(request));
        return exchangeInterfaceFeignService.requestInterface(beanName, bdcMkPjqRequestDTO);
    }

}

