package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.accept.*;
import cn.gtmap.realestate.common.core.dto.BdcPdfDTO;
import cn.gtmap.realestate.common.core.dto.OfficeExportDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcNewPrintDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlDysjDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlPrintDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlXmZsDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcFczmDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcFwqlDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZfxxDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcDysjDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmLsgxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcFwqlQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcFwqlQlrQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcZfxxQO;
import cn.gtmap.realestate.common.core.service.feign.accept.*;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.config.BdcDysjPzFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcLsgxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcNtFczmFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcZfxxCxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcPrintFeignService;
import cn.gtmap.realestate.common.core.support.pdf.PdfController;
import cn.gtmap.realestate.common.util.BdcUploadFileUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.RedisUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ArrayListMultimap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @version 2019/1/17,1.0
 * @description 受理页面打印功能
 */
@Controller
@RequestMapping("/slPrint")
public class BdcSlPrintController extends BaseController {
    @Autowired
    private BdcSlPrintFeignService bdcSlPrintFeignService;
    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    private BdcQllxFeignService bdcQllxFeignService;
    @Autowired
    BdcLsgxFeignService bdcLsgxFeignService;

    @Autowired
    BdcSlJbxxFeignService bdcSlJbxxFeignService;
    @Autowired
    BdcSlXmFeignService bdcSlXmFeignService;
    //pdf处理工具方法
    @Autowired
    private PdfController pdfController;
    @Autowired
    BdcNtFczmFeignService bdcNtFczmFeignService;
    @Autowired
    BdcZfxxCxFeignService bdcZfxxCxFeignService;
    @Autowired
    BdcSlSjclFeignService bdcSlSjclFeignService;
    @Autowired
    BdcUploadFileUtils bdcUploadFileUtils;
    @Autowired
    BdcDjxlPzFeignService bdcDjxlPzFeignService;
    @Autowired
    BdcSlXmLsgxFeignService bdcSlXmLsgxFeignService;
    @Autowired
    BdcSlSqrFeignService bdcSlSqrFeignService;
    @Autowired
    BdcSlCdBlxxFeignService bdcSlCdBlxxFeignService;
    @Autowired
    BdcZsFeignService bdcZsFeignService;
    @Autowired
    BdcDysjPzFeignService bdcDysjPzFeignService;
    @Autowired
    BdcPrintFeignService bdcPrintFeignService;
    @Autowired
    private RedisUtils redisUtils;

    @Value("#{'${sjddylx.czsjd:}'.split(',')}")
    private List<String> czsjdList;
    @Value("#{'${sjddylx.sjd:}'.split(',')}")
    private List<String> sjdList;
    @Value("#{'${sjddylx.sjdpl:}'.split(',')}")
    private List<String> sjdplList;
    @Value("#{'${sjddylx.czsjdpl:}'.split(',')}")
    private List<String> czsjdplList;
    @Value("#{'${sjddylx.nosjd:}'.split(',')}")
    private List<String> nosjdList;
    @Value("#{'${sjddylx.cdgh:}'.split(',')}")
    private List<String> cdghsjdList;
    @Value("#{'${slym.print.pldylx:}'.split(',')}")
    private List<String> pldyList;
    // 默认打印地址
    @Value("${print.path:/usr/local/bdc3/print/}")
    private String printPath;
    //  常州三调流程
    @Value("${czsd.gzldyid:}")
    private String czsdGzldyid;
    @Value("${slym.print.mode:1}")
    private Integer printMode;
    @Value("${sjddylx.zdylcdylx:}")
    private String zdyLcDylx;
    @Value("${slym.print.xzqhdylx:}")
    private String xzqhDylx;

    @Value("#{'${slym.sqs.lcpzdylx:}'.split(',')}")
    private List<String> sqsLcpzDylx;

    /**
     * @param bdcSlPrintDTO 打印数据传参
     * @return 本地打印地址
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 本地打印----南通版本使用
     */
    @ResponseBody
    @GetMapping("")
    public String print(BdcSlPrintDTO bdcSlPrintDTO) {
        String printUrl = "";
        String dylx = "";
        String url = "";

        List<BdcXmDO> bdcXmDOList = this.getBdcXm(bdcSlPrintDTO);

        // 处理收件单打印类型
        if(CollectionUtils.isNotEmpty(bdcXmDOList)){
            dylx = getNtSjdDylx(bdcXmDOList, bdcSlPrintDTO);
        }else{
            dylx = getNtYcslSjdDylx(this.getBdcSlXm(bdcSlPrintDTO.getGzlslid()), bdcSlPrintDTO);
        }

        List<BdcSlXmDO> bdcSlXmDOList = new ArrayList<>();
        //权籍调查流程不生成登记项目，只生成受理数据
        if (StringUtils.equals(bdcSlPrintDTO.getDylx(), CommonConstantUtils.DYLX_QJDCSJD)) {
            bdcSlXmDOList = this.getBdcSlXm(bdcSlPrintDTO.getGzlslid());
        }

        bdcSlPrintDTO.setDylx(StringUtils.isNotBlank(dylx) ? dylx : bdcSlPrintDTO.getDylx());
        url = bdcSlPrintDTO.getUrl() + CommonConstantUtils.ModelPath + bdcSlPrintDTO.getDylx() + CommonConstantUtils.WJHZ_FR3;
        if (StringUtils.isNotBlank(bdcSlPrintDTO.getDylx()) && !StringUtils.equals(bdcSlPrintDTO.getDylx(), CommonConstantUtils.DYLX_NOSJD)) {
            bdcSlPrintDTO.setUrl(url);
            bdcSlPrintDTO.setXmid(StringUtils.isNotBlank(bdcSlPrintDTO.getXmid()) ? bdcSlPrintDTO.getXmid() : (CollectionUtils.isNotEmpty(bdcXmDOList) ? bdcXmDOList.get(0).getXmid() : (CollectionUtils.isNotEmpty(bdcSlXmDOList) ? bdcSlXmDOList.get(0).getXmid() : CommonConstantUtils.NOXMID)));
            bdcSlPrintDTO.setZxlc(StringUtils.isNotBlank(bdcSlPrintDTO.getZxlc()) ? bdcSlPrintDTO.getZxlc() : CommonConstantUtils.BOOL_FALSE);
            bdcSlPrintDTO.setQlrlb(bdcSlPrintDTO.getQlrlb());
            printUrl = bdcSlPrintFeignService.print(bdcSlPrintDTO);
        } else {
            throw new AppException("该流程无需打印！");
        }
        return printUrl;
    }

    /**
     * 获取登记项目信息
     */
    private List<BdcXmDO> getBdcXm(BdcSlPrintDTO bdcSlPrintDTO){
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(bdcSlPrintDTO.getGzlslid());
        if (StringUtils.isNotBlank(bdcSlPrintDTO.getXmid())) {
            bdcXmQO.setXmid(bdcSlPrintDTO.getXmid());
        }
        return bdcXmFeignService.listBdcXm(bdcXmQO);
    }

    /**
     * 获取受理项目信息
     */
    private List<BdcSlXmDO> getBdcSlXm(String gzlslid){
        List<BdcSlXmDO> bdcSlXmDOList = new ArrayList<>();
        BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxFeignService.queryBdcSlJbxxByGzlslid(gzlslid);
        if (bdcSlJbxxDO != null) {
            bdcSlXmDOList = bdcSlXmFeignService.listBdcSlXmByJbxxid(bdcSlJbxxDO.getJbxxid());
        }
        return bdcSlXmDOList;
    }

    /**
     * @param bdcSlPrintDTO 打印数据传参
     * @return 本地打印地址
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 本地打印(合肥打印获取打印类型)
     */
    @ResponseBody
    @GetMapping("/hf")
    public String printHfData(BdcSlPrintDTO bdcSlPrintDTO) {
        String printUrl = "";
        String dylx = "";
        String url = "";
        List<BdcXmDO> bdcXmDOList = this.getBdcXm(bdcSlPrintDTO);
        if(CollectionUtils.isEmpty(bdcXmDOList)){
            throw new AppException("未获取到项目");
        }
        dylx = getCommonDylx(bdcXmDOList, bdcSlPrintDTO);
        bdcSlPrintDTO.setDylx(StringUtils.isNotBlank(dylx) ? dylx : bdcSlPrintDTO.getDylx());
        url = bdcSlPrintDTO.getUrl() + CommonConstantUtils.ModelPath + bdcSlPrintDTO.getDylx() + CommonConstantUtils.WJHZ_FR3;
        if (StringUtils.isNotBlank(bdcSlPrintDTO.getDylx()) && !StringUtils.equals(bdcSlPrintDTO.getDylx(), CommonConstantUtils.DYLX_NOSJD)) {
            bdcSlPrintDTO.setUrl(url);
            bdcSlPrintDTO.setXmid(StringUtils.isNotBlank(bdcSlPrintDTO.getXmid()) ? bdcSlPrintDTO.getXmid() : bdcXmDOList.get(0).getXmid());
            bdcSlPrintDTO.setZxlc(StringUtils.isNotBlank(bdcSlPrintDTO.getZxlc()) ? bdcSlPrintDTO.getZxlc() :CommonConstantUtils.BOOL_FALSE);
            bdcSlPrintDTO.setQlrlb(bdcSlPrintDTO.getQlrlb());
            printUrl = bdcSlPrintFeignService.printByLclx(bdcSlPrintDTO);
        } else {
            throw new AppException("该流程无需打印！");
        }
        return printUrl;
    }
    @ResponseBody
    @GetMapping("/pdf")
    public void printPdf(HttpServletResponse response, BdcSlPrintDTO bdcSlPrintDTO) {
        String userName = userManagerUtils.getCurrentUserName();
        LOGGER.info("获取当前用户:{}",userName);
        getDycs(bdcSlPrintDTO,false);
        String xml = "";
        //南通合肥获取打印xml地址不一样
        if (StringUtils.isNotBlank(systemVersion) && StringUtils.equals(systemVersion, CommonConstantUtils.SYSTEM_VERSION_NT) || Objects.equals(2, printMode)) {
            if (ArrayUtils.contains(CommonConstantUtils.SL_SQS_DYLX, bdcSlPrintDTO.getDylx()) || ArrayUtils.contains(CommonConstantUtils.SL_XWBL_DYLX, bdcSlPrintDTO.getDylx()) || ArrayUtils.contains(CommonConstantUtils.SL_JDS_DYLX, bdcSlPrintDTO.getDylx())
                    || ArrayUtils.contains(CommonConstantUtils.DJ_OTHER_DYLX, bdcSlPrintDTO.getDylx())) {
                xml = bdcSlPrintFeignService.generateSlXmlToNt(bdcSlPrintDTO.getGzlslid(), bdcSlPrintDTO.getDylx(), bdcSlPrintDTO.getZxlc());
            }
            if (ArrayUtils.contains(CommonConstantUtils.Sl_ALL_DYLX, bdcSlPrintDTO.getDylx()) || ArrayUtils.contains(CommonConstantUtils.DYLX_JK, bdcSlPrintDTO.getDylx())) {
                xml = bdcSlPrintFeignService.packPrintDatasToNt(bdcSlPrintDTO.getGzlslid(), bdcSlPrintDTO.getDylx(), bdcSlPrintDTO.getQlrlb(), bdcSlPrintDTO.getXmid(), userName);
            }
        } else {
            if (ArrayUtils.contains(CommonConstantUtils.Sl_DJ_DYLX, bdcSlPrintDTO.getDylx())) {
                xml = bdcSlPrintFeignService.packPrintXml(bdcSlPrintDTO.getGzlslid(), bdcSlPrintDTO.getDylx(), bdcSlPrintDTO.getZxlc(), StringUtils.join(bdcSlPrintDTO.getSjclids(), ","), userName);
            }
            if (ArrayUtils.contains(CommonConstantUtils.Sl_ALL_DYLX, bdcSlPrintDTO.getDylx())) {
                Integer lclx = bdcXmFeignService.makeSureBdcXmLx(bdcSlPrintDTO.getGzlslid());
                if (CommonConstantUtils.LCLX_PL.equals(lclx) || CommonConstantUtils.LCLX_PLZH.equals(lclx)) {
                    bdcSlPrintDTO.setDylx(bdcSlPrintDTO.getDylx() + CommonConstantUtils.DYLX_HZPL);
                }
                xml = bdcSlPrintFeignService.packPrintDatas(bdcSlPrintDTO.getGzlslid(),bdcSlPrintDTO.getDylx(),bdcSlPrintDTO.getQlrlb(),bdcSlPrintDTO.getXmid(),StringUtils.join(bdcSlPrintDTO.getSjclids(), ","));
            }
            if (ArrayUtils.contains(CommonConstantUtils.SL_ZD_DYLX, bdcSlPrintDTO.getDylx())) {
                xml = bdcSlPrintFeignService.packPrintDatasAndReplaceZd(bdcSlPrintDTO.getGzlslid(),bdcSlPrintDTO.getDylx(),bdcSlPrintDTO.getQlrlb(),bdcSlPrintDTO.getXmid(),StringUtils.join(bdcSlPrintDTO.getSjclids(), ","));
            }
        }
        //打印pdf
        LOGGER.info("pdf打印类型:{}",bdcSlPrintDTO.getDylx());
        LOGGER.info("pdf打印xml数据源:{}",xml);
        OfficeExportDTO pdfExportDTO = new OfficeExportDTO();
        pdfExportDTO.setModelName(printPath + bdcSlPrintDTO.getDylx() + CommonConstantUtils.WJHZ_DOCX);
        pdfExportDTO.setFileName(bdcSlPrintDTO.getDylx() + bdcSlPrintDTO.getGzlslid());
        pdfExportDTO.setXmlData(xml);
        pdfController.exportPdf(response, pdfExportDTO);
    }

    /**
     * @param bdcSlPrintDTO  打印数据
     * @param hqtsdylx 是否获取特殊打印类型
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 组织打印需要的一些必要参数
     * @date : 2020/3/3 14:07
     */
    private BdcSlPrintDTO getDycs(BdcSlPrintDTO bdcSlPrintDTO,Boolean hqtsdylx) {
        String dylx = "";
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(bdcSlPrintDTO.getGzlslid());
        if (StringUtils.isNotBlank(bdcSlPrintDTO.getXmid())) {
            bdcXmQO.setXmid(bdcSlPrintDTO.getXmid());
        }
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        List<BdcSlXmDO> bdcSlXmDOList = new ArrayList<>();
        //权籍调查流程不生成登记项目，只生成受理数据
        BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxFeignService.queryBdcSlJbxxByGzlslid(bdcSlPrintDTO.getGzlslid());
        if (bdcSlJbxxDO != null) {
            bdcSlXmDOList = bdcSlXmFeignService.listBdcSlXmByJbxxid(bdcSlJbxxDO.getJbxxid());
        }
        //南通合肥获取打印参数和打印类型方式不一样
        if (StringUtils.isNotBlank(systemVersion) && (ArrayUtils.contains(CommonConstantUtils.SYSTEM_VERSION_NTDS, systemVersion)) || Objects.equals(2, printMode)) {
            // 收件单打印类型获取
            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                dylx = getNtSjdDylx(bdcXmDOList, bdcSlPrintDTO);
            } else {
                dylx = getNtYcslSjdDylx(this.getBdcSlXm(bdcSlPrintDTO.getGzlslid()), bdcSlPrintDTO);
            }
            //南通申请书打印特殊配置获取打印类型
            if ((ArrayUtils.contains(CommonConstantUtils.SL_SQS_DYLX, bdcSlPrintDTO.getDylx()) || (CollectionUtils.isNotEmpty(sqsLcpzDylx) && sqsLcpzDylx.contains(bdcSlPrintDTO.getDylx()))) && hqtsdylx) {
                BdcSlDysjDTO bdcSlDysjDTO = bdcSlPrintFeignService.getTsDycs(bdcSlPrintDTO);
                dylx = bdcSlDysjDTO.getDylx();
            }
            if (StringUtils.equals(bdcSlPrintDTO.getDylx(), CommonConstantUtils.DYLX_HMSFLXD)) {
                //根据不动产单元号去重，如果去重后数量大于1则为批量流程
                int lclx = bdcXmFeignService.makeSureBdcXmLx(bdcSlPrintDTO.getGzlslid());
                //批量流程
                if (lclx == 3 || lclx == 4) {
                    dylx = CommonConstantUtils.DYLX_HMSFLXDPL;
                } else {
                    dylx = CommonConstantUtils.DYLX_HMSFLXD;
                }
            }
        } else if (Objects.equals(3, bdcSlPrintDTO.getPrintModel())) {
            dylx = getDylxFromDypz(bdcXmDOList, bdcSlPrintDTO);
        } else {
            dylx = getCommonDylx(bdcXmDOList, bdcSlPrintDTO);
        }
        //printmodel=3常州查档，三调查询打印类型
        if (Objects.equals(3, bdcSlPrintDTO.getPrintModel())) {
            dylx = getCzQtDylx(bdcSlXmDOList, bdcSlPrintDTO, dylx);
        }
        bdcSlPrintDTO.setDylx(StringUtils.isNotBlank(dylx) ? dylx : bdcSlPrintDTO.getDylx());
        bdcSlPrintDTO.setXmid(StringUtils.isNotBlank(bdcSlPrintDTO.getXmid()) ? bdcSlPrintDTO.getXmid() : (CollectionUtils.isNotEmpty(bdcXmDOList) ? bdcXmDOList.get(0).getXmid() : (CollectionUtils.isNotEmpty(bdcSlXmDOList) ? bdcSlXmDOList.get(0).getXmid() : CommonConstantUtils.NOXMID)));
        bdcSlPrintDTO.setZxlc(StringUtils.isNotBlank(bdcSlPrintDTO.getZxlc()) ? bdcSlPrintDTO.getZxlc() : CommonConstantUtils.BOOL_FALSE);
        bdcSlPrintDTO.setQlrlb(bdcSlPrintDTO.getQlrlb());
        return bdcSlPrintDTO;
    }

    private void getSjdSqsDylx(BdcSlPrintDTO bdcSlPrintDTO) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(bdcSlPrintDTO.getGzlslid());
        if (StringUtils.isNotBlank(bdcSlPrintDTO.getXmid())) {
            bdcXmQO.setXmid(bdcSlPrintDTO.getXmid());
        }
        if (StringUtils.isNotBlank(bdcSlPrintDTO.getDjxl())) {
            bdcXmQO.setDjxl(bdcSlPrintDTO.getDjxl());
        }
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        List<BdcSlXmDO> bdcSlXmDOList = new ArrayList<>();
        //权籍调查流程不生成登记项目，只生成受理数据
        BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxFeignService.queryBdcSlJbxxByGzlslid(bdcSlPrintDTO.getGzlslid());
        if (bdcSlJbxxDO != null) {
            bdcSlXmDOList = bdcSlXmFeignService.listBdcSlXmByJbxxid(bdcSlJbxxDO.getJbxxid());
        }
        // 打印类型为sqs,sqspl
        String dylx = "";
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            BdcDjxlPzDO bdcDjxlPzDO = bdcDjxlPzFeignService.queryBdcDjxlPzByGzldyidAndDjxl(bdcXmDOList.get(0).getGzldyid(), bdcXmDOList.get(0).getDjxl());
            if (StringUtils.equals(bdcSlPrintDTO.getDylx(), CommonConstantUtils.DYLX_SQS) || StringUtils.equals(bdcSlPrintDTO.getDylx(), CommonConstantUtils.DYLX_SQSPL)) {
                if (StringUtils.isNotBlank(bdcDjxlPzDO.getSqsdylx())) {
                    dylx = bdcDjxlPzDO.getSqsdylx();
                }
            }
            if (StringUtils.equals(bdcSlPrintDTO.getDylx(), CommonConstantUtils.DYLX_SJD) || StringUtils.equals(bdcSlPrintDTO.getDylx(), CommonConstantUtils.DYLX_SJDPL)) {
                dylx = bdcDjxlPzDO.getSjddylx();
            }
        }
        bdcSlPrintDTO.setDylx(StringUtils.isNotBlank(dylx) ? dylx : bdcSlPrintDTO.getDylx());
        bdcSlPrintDTO.setZxlc(StringUtils.isNotBlank(bdcSlPrintDTO.getZxlc()) ? bdcSlPrintDTO.getZxlc() : CommonConstantUtils.BOOL_FALSE);
        bdcSlPrintDTO.setXmid(StringUtils.isNotBlank(bdcSlPrintDTO.getXmid()) ? bdcSlPrintDTO.getXmid() : (CollectionUtils.isNotEmpty(bdcXmDOList) ? bdcXmDOList.get(0).getXmid() : (CollectionUtils.isNotEmpty(bdcSlXmDOList) ? bdcSlXmDOList.get(0).getXmid() : CommonConstantUtils.NOXMID)));
    }

    /**
     * @param bdcSlPrintDTO 打印传参
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新模式打印传参获取打印数据  南通，合肥，标准版
     * @date : 2020/4/16 16:35
     */
    @ResponseBody
    @GetMapping("/newmode")
    public BdcNewPrintDTO generateNewPrintmode(BdcSlPrintDTO bdcSlPrintDTO) {
        bdcSlPrintDTO.setSystemVersion(systemVersion);
        bdcSlPrintDTO.setPrintModel(printMode);
        //1. 先根据版本不同和页面传递的打印类型，区分好获取xml的地址
        //第一次获取主要为了获取xmid、组织好正确的打印类型；
        getDycs(bdcSlPrintDTO, false);
        String modelurl = bdcSlPrintDTO.getUrl() + CommonConstantUtils.ModelPath + bdcSlPrintDTO.getDylx() + CommonConstantUtils.WJHZ_FR3;
        BdcNewPrintDTO bdcNewPrintDTO = bdcSlPrintFeignService.generatePrintDTO(bdcSlPrintDTO);
        bdcNewPrintDTO.setModelurl(modelurl);
        //2. 特殊处理打印类型
        getDycs(bdcSlPrintDTO, true);

        //配置
        if (StringUtils.isNotBlank(bdcSlPrintDTO.getGzlslid()) && CollectionUtils.isNotEmpty(pldyList) && pldyList.contains(bdcSlPrintDTO.getDylx())) {
            LOGGER.info("当前打印类型为{}，系统中配置的需要改变打印类型为批量打印的打印类型{}", bdcSlPrintDTO.getDylx(), pldyList);
            int lclx = bdcXmFeignService.makeSureBdcXmLx(bdcSlPrintDTO.getGzlslid());
            if (Objects.equals(CommonConstantUtils.LCLX_PL, lclx)) {
                bdcNewPrintDTO.setDylx(bdcSlPrintDTO.getDylx() + "pl");
            } else {
                bdcNewPrintDTO.setDylx(bdcSlPrintDTO.getDylx());
            }
        } else {
            bdcNewPrintDTO.setDylx(bdcSlPrintDTO.getDylx());
        }
        LOGGER.info("工作流实例id{},打印类型{},数据源xml{}", bdcSlPrintDTO.getGzlslid(), bdcNewPrintDTO.getDylx(), bdcNewPrintDTO.getDataurl());
        return bdcNewPrintDTO;
    }

    //南通收件单打印类型获取
    public String getNtSjdDylx(List<BdcXmDO> bdcXmDOList,BdcSlPrintDTO bdcSlPrintDTO) {
        String dylx = "";
        if (CollectionUtils.isNotEmpty(bdcXmDOList) && !StringUtils.equals(bdcSlPrintDTO.getDylx(), CommonConstantUtils.DYLX_SQS) &&
                !StringUtils.equals(bdcSlPrintDTO.getDylx(), CommonConstantUtils.DYLX_SQSPL) && StringUtils.indexOf(bdcSlPrintDTO.getDylx(), "sqs") < 0) {
            String djxl = bdcXmDOList.get(0).getDjxl();
            if (StringUtils.isNotBlank(djxl)) {
                if (StringUtils.equals(bdcSlPrintDTO.getDylx(), CommonConstantUtils.DYLX_SJD)) {
                    if (czsjdList.indexOf(djxl) > -1) {
                        dylx = CommonConstantUtils.DYLX_CZSJD;
                    } else if (sjdList.indexOf(djxl) > -1) {
                        dylx = CommonConstantUtils.DYLX_SJD;
                    } else if (nosjdList.indexOf(djxl) > -1) {
                        dylx = CommonConstantUtils.DYLX_NOSJD;
                    } else {
                        dylx = CommonConstantUtils.DYLX_CZSJD;
                    }
                }
                if (StringUtils.equals(bdcSlPrintDTO.getDylx(), CommonConstantUtils.DYLX_SJDPL)) {
                    if (sjdplList.indexOf(djxl) > -1) {
                        dylx = CommonConstantUtils.DYLX_SJDPL;
                    } else if (czsjdplList.indexOf(djxl) > -1) {
                        dylx = CommonConstantUtils.DYLX_CZSJDPL;
                    } else if (nosjdList.indexOf(djxl) > -1) {
                        dylx = CommonConstantUtils.DYLX_NOSJD;
                    } else {
                        dylx = CommonConstantUtils.DYLX_CZSJDPL;
                    }
                }
                if (StringUtils.equals(bdcSlPrintDTO.getDylx(), CommonConstantUtils.DYLX_SFD)) {
                    //根据不动产单元号去重，如果去重后数量大于1则为批量流程
                    Set<BdcXmDO> bdcxm = new TreeSet<>(Comparator.comparing(BdcXmDO::getBdcdyh));
                    bdcxm.addAll(bdcXmDOList);
                    if (CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcXmDOList.get(0).getQllx())) {
                        if (bdcxm.size() > 1) {
                            dylx = CommonConstantUtils.DYLX_DYASFD;
                        } else {
                            dylx = CommonConstantUtils.DYLX_DYASFDPL;
                        }
                    } else if (bdcxm.size() > 1) {
                        dylx = CommonConstantUtils.DYLX_SFDPL;
                    } else {
                        dylx = CommonConstantUtils.DYLX_SFD;
                    }
                }
                if (StringUtils.equals(bdcSlPrintDTO.getDylx(), CommonConstantUtils.DYLX_JDS)) {
                    BdcQl bdcQl = bdcQllxFeignService.queryQlxx(bdcXmDOList.get(0).getXmid());
                    if (bdcQl == null) {
                        bdcQl = bdcQllxFeignService.queryBdcYqlxx(bdcXmDOList.get(0).getXmid());
                    }
                    if (bdcQl instanceof BdcCfDO) {
                        BdcCfDO bdcCfDO = (BdcCfDO) bdcQl;
                        //获取权利信息，查封信息根据查封类型判断打印类型
                        switch (bdcCfDO.getCflx()) {
                            case 2:
                                dylx = CommonConstantUtils.DYLX_LHCFJDS;
                                break;
                            case 3:
                                dylx = CommonConstantUtils.DYLX_YCFJDS;
                                break;
                            case 5:
                                dylx = CommonConstantUtils.DYLX_XFJDS;
                                break;
                            case 1:
                            default:
                                dylx = CommonConstantUtils.DYLX_CFJDS;
                        }
                    }
                    //裁定过户流程
                    if (cdghsjdList.indexOf(bdcXmDOList.get(0).getDjxl()) > -1) {
                        dylx = CommonConstantUtils.DYLX_CDGHJDS;
                    }
                    //解封登记
                    if (StringUtils.equals(bdcSlPrintDTO.getZxlc(), CommonConstantUtils.BOOL_TRUE)) {
                        dylx = CommonConstantUtils.DYLX_JFJDS;
                    }
                }
            }
            if (StringUtils.isNotBlank(zdyLcDylx)) {
                Map<String, String> zdylcDylxMap = JSON.parseObject(zdyLcDylx, Map.class);
                String gzldyid = bdcXmDOList.get(0).getGzldyid();
                if (StringUtils.isNotBlank(gzldyid) && StringUtils.isNotBlank(MapUtils.getString(zdylcDylxMap, gzldyid, "")) && MapUtils.isNotEmpty(zdylcDylxMap)) {
                    dylx = zdylcDylxMap.get(gzldyid);
                }
            }
        }
        return dylx;
    }

    // 南通一窗收件单打印类型获取
    private String getNtYcslSjdDylx(List<BdcSlXmDO> bdcSlXmDOList, BdcSlPrintDTO bdcSlPrintDTO){
        String dylx = "";
        if (CollectionUtils.isNotEmpty(bdcSlXmDOList)
                && !StringUtils.equals(bdcSlPrintDTO.getDylx(), CommonConstantUtils.DYLX_SQS)
                && !StringUtils.equals(bdcSlPrintDTO.getDylx(), CommonConstantUtils.DYLX_SQSPL)) {
            String djxl = bdcSlXmDOList.get(0).getDjxl();
            if (StringUtils.isNotBlank(djxl)) {
                if (StringUtils.equals(bdcSlPrintDTO.getDylx(), CommonConstantUtils.DYLX_YCSJD)) {
                    if (czsjdList.indexOf(djxl) > -1) {
                        dylx = CommonConstantUtils.DYLX_YCCZSJD;
                    } else if (sjdList.indexOf(djxl) > -1) {
                        dylx = CommonConstantUtils.DYLX_YCSJD;
                    } else if (nosjdList.indexOf(djxl) > -1) {
                        dylx = CommonConstantUtils.DYLX_YCNOSJD;
                    } else {
                        dylx = CommonConstantUtils.DYLX_YCCZSJD;
                    }
                }
                if (StringUtils.equals(bdcSlPrintDTO.getDylx(), CommonConstantUtils.DYLX_YCSJDPL)) {
                    if (sjdplList.indexOf(djxl) > -1) {
                        dylx = CommonConstantUtils.DYLX_YCSJDPL;
                    } else if (czsjdplList.indexOf(djxl) > -1) {
                        dylx = CommonConstantUtils.DYLX_YCCZSJDPL;
                    } else if (nosjdList.indexOf(djxl) > -1) {
                        dylx = CommonConstantUtils.DYLX_YCNOSJD;
                    } else {
                        dylx = CommonConstantUtils.DYLX_YCCZSJDPL;
                    }
                }
            }
        }
        return dylx;
    }


    public String getCzQtDylx(List<BdcSlXmDO> bdcSlXmDOList, BdcSlPrintDTO bdcSlPrintDTO, String dylx) {
        //查档信息
        if (StringUtils.equals(CommonConstantUtils.DYLX_CDXX, bdcSlPrintDTO.getDylx())) {
            //打印类型为查档信息时，判断项目表产权证号是否为不动产数据
            if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxFeignService.queryBdcSlJbxxByJbxxid(bdcSlXmDOList.get(0).getJbxxid());
                BdcXmLsgxQO bdcXmLsgxQO = new BdcXmLsgxQO();
                bdcXmLsgxQO.setWlxm(0);
                bdcXmLsgxQO.setXmid(bdcSlXmDOList.get(0).getXmid());
                List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = bdcSlXmLsgxFeignService.listBdcSlXmLsgx(bdcSlXmDOList.get(0).getXmid(), "", CommonConstantUtils.SF_F_DM);
                if (CollectionUtils.isNotEmpty(bdcSlXmLsgxDOList)) {
                    BdcXmQO bdcXmQO = new BdcXmQO(bdcSlXmLsgxDOList.get(0).getYxmid());
                    List<BdcXmDO> yBdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
                    if (CollectionUtils.isNotEmpty(yBdcXmDOList)) {
                        String bdcqzh = yBdcXmDOList.get(0).getBdcqzh();
                        String gzldyid = bdcSlJbxxDO.getGzldyid();
                        if (StringUtils.isNotBlank(bdcqzh) && StringUtils.indexOf(bdcqzh, CommonConstantUtils.BDC_BDCQZH_BS) > -1) {
                            dylx = CommonConstantUtils.DYLX_CDXX_BDCQZ;
                        } else {
                            dylx = CommonConstantUtils.DYLX_CDXX_FCTDZ;
                        }
                        if (StringUtils.isNotBlank(czsdGzldyid) && StringUtils.equals(czsdGzldyid, gzldyid)) {
                            dylx = CommonConstantUtils.DYLX_SDDY;
                        }
                    }
                }
            }
        }
        //首套房证明无房特殊处理
        if (StringUtils.equals(CommonConstantUtils.DYLX_STFZM, bdcSlPrintDTO.getDylx())) {
            //根据查询参数判断是否有房，如果结果为空则调用无房模板
            // 根据xmid未获取到权利人信息时，获取 bdc_sl_sqr 信息
            List<BdcSlSqrDO> bdcSlSqrDOList = bdcSlSqrFeignService.listBdcSlSqrByXmid(bdcSlXmDOList.get(0).getXmid(), "");
            List<BdcQlrDO> bdcQlrDOList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(bdcSlSqrDOList)) {
                bdcQlrDOList = new ArrayList<>(bdcSlSqrDOList.size());
                for (BdcSlSqrDO bdcSlSqrDO : bdcSlSqrDOList) {
                    bdcQlrDOList.add(BdcQlrDO.BdcQlrDOBuilder.aBdcQlrDO().withQlrmc(bdcSlSqrDO.getSqrmc()).withZjh(bdcSlSqrDO.getZjh()).build());
                }
            }
            if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                if (StringUtils.isNotBlank(dylx)) {
                    //查询权利人是否存在现势房屋权利信息
                    BdcFwqlQO bdcFwqlQO = new BdcFwqlQO();
                    List<BdcFwqlQlrQO> bdcQlrQOList = new ArrayList<>(bdcQlrDOList.size());
                    for (BdcQlrDO bdcQlrDO : bdcQlrDOList) {
                        BdcFwqlQlrQO fwqlQlrQO = new BdcFwqlQlrQO();
                        fwqlQlrQO.setQlrmc(bdcQlrDO.getQlrmc());
                        fwqlQlrQO.setZjh(bdcQlrDO.getZjh());
                        bdcQlrQOList.add(fwqlQlrQO);
                    }
                    bdcFwqlQO.setQlrxx(bdcQlrQOList);
                    List<BdcFwqlDTO> bdcFwqlList = bdcZfxxCxFeignService.listBdcFwqlDTO(bdcFwqlQO);
                    BdcCdBlxxDO bdcCdBlxxDO = new BdcCdBlxxDO();
                    bdcCdBlxxDO.setXmid(bdcSlXmDOList.get(0).getXmid());
                    BdcCdBlxxDO bdcCdBlxx = bdcSlCdBlxxFeignService.queryBdcBlxx(bdcCdBlxxDO);
                    //房屋查询为空并且受理补录信息的数据也为空
                    if (CollectionUtils.isEmpty(bdcFwqlList) && (Objects.isNull(bdcCdBlxx) || StringUtils.isBlank(bdcCdBlxx.getBlxxid()))) {
                        LOGGER.warn("当前权利人{}房屋权利查询结果为空，更改首套房证明打印类型stfzm为 stfzm_0", bdcQlrDOList);
                        dylx = CommonConstantUtils.DYLX_STFZM_0;
                    }
                }
            }
        }
        if (StringUtils.isNotBlank(xzqhDylx)) {
            LOGGER.warn("获取到的配置行政区划的打印类型信息{}", xzqhDylx);
            Map<String, String> xzqhDylxMap = JSON.parseObject(xzqhDylx, Map.class);
            if (CollectionUtils.isNotEmpty(bdcSlXmDOList) && MapUtils.isNotEmpty(xzqhDylxMap)) {
                String xmid = bdcSlXmDOList.get(0).getXmid();
                List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = bdcSlXmLsgxFeignService.listBdcSlXmLsgx(xmid, "", CommonConstantUtils.SF_F_DM);
                if (CollectionUtils.isNotEmpty(bdcSlXmLsgxDOList)) {
                    String yxmid = bdcSlXmLsgxDOList.get(0).getYxmid();
                    BdcXmQO bdcXmQO = new BdcXmQO(yxmid);
                    List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
                    if (CollectionUtils.isNotEmpty(bdcXmDOList) && StringUtils.isNotBlank(bdcXmDOList.get(0).getQxdm()) && StringUtils.isNotBlank(xzqhDylxMap.get(bdcXmDOList.get(0).getQxdm()))) {
                        List<String> xzqhDylxList = Arrays.asList(xzqhDylxMap.get(bdcXmDOList.get(0).getQxdm()).split(CommonConstantUtils.ZF_YW_DH));
                        if (CollectionUtils.isNotEmpty(xzqhDylxList) && xzqhDylxList.contains(dylx)) {
                            dylx = dylx + bdcXmDOList.get(0).getQxdm();
                        }
                    }
                } else {
                    //没有项目历史关系，无数据创建，根据当前的受理人员查找组织机构代码打印
                    BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxFeignService.queryBdcSlJbxxByJbxxid(bdcSlXmDOList.get(0).getJbxxid());
                    if (Objects.nonNull(bdcSlJbxxDO)) {
                        LOGGER.warn("受理编号{}未查询到项目历史关系，根据基本信息表的区县代码判断{}", bdcSlJbxxDO.getSlbh(), bdcSlJbxxDO.getQxdm());
                        String qxdm = StringUtils.defaultString(bdcSlJbxxDO.getQxdm());
                        if (StringUtils.isNotBlank(qxdm) && StringUtils.isNotBlank(xzqhDylxMap.get(qxdm))) {
                            List<String> xzqhDylxList = Arrays.asList(xzqhDylxMap.get(qxdm).split(CommonConstantUtils.ZF_YW_DH));
                            if (CollectionUtils.isNotEmpty(xzqhDylxList) && xzqhDylxList.contains(dylx)) {
                                dylx = dylx + qxdm;
                            }
                        }
                    }
                }
            }
        }
        return dylx;
    }

    public String getDylxFromDypz(List<BdcXmDO> bdcXmDOList, BdcSlPrintDTO bdcSlPrintDTO) {
        String dylx = bdcSlPrintDTO.getDylx();
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            BdcDjxlPzDO bdcDjxlPzDO = bdcDjxlPzFeignService.queryBdcDjxlPzByGzldyidAndDjxl(bdcXmDOList.get(0).getGzldyid(), bdcXmDOList.get(0).getDjxl());
            if (StringUtils.equals(CommonConstantUtils.DYLX_SJD, bdcSlPrintDTO.getDylx()) && StringUtils.isNotBlank(bdcDjxlPzDO.getSjddylx())) {
                dylx = bdcDjxlPzDO.getSjddylx();
            }
            if (StringUtils.equals(CommonConstantUtils.DYLX_SQS, bdcSlPrintDTO.getDylx()) && StringUtils.isNotBlank(bdcDjxlPzDO.getSqsdylx())) {
                dylx = bdcDjxlPzDO.getSqsdylx();
            }
        }
        return dylx;
    }


    /**
     * 生成人证对比PDF文件并上传大云附件中心
     *
     * @param slbh    受理编号
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @GetMapping("/rzdb")
    public void generateRzdbPdf (String slbh, String gzlslid) {
        if(StringUtils.isAnyBlank(slbh, gzlslid)){
            throw new MissingArgumentException("缺失参数受理编号、工作流实例ID");
        }
        this.bdcSlPrintFeignService.scrzdb(slbh, gzlslid);
    }


    /**
     * 生成有房无房证明PDF文件，并上传至大云文件中心
     * @param qlrmc  权利人名称
     * @param qlrzjh  权利人证件号
     * @param gzlslid  工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @GetMapping("/nantong/yfwfzm")
    public void generateYfwfzm(@RequestParam(name="qlrmc") String qlrmc, @RequestParam(name="qlrzjh") String qlrzjh,
                               @RequestParam(name="gzlslid") String gzlslid){
        // 生成有房无房证明xml数据
        BdcFczmDTO bdcFczmDTO = new BdcFczmDTO();
        bdcFczmDTO.setCxsqr(qlrmc);
        bdcFczmDTO.setSfzh(qlrzjh);
        String xmlData = this.bdcNtFczmFeignService.generateYfwfzmXml(bdcFczmDTO);
        // 判断生成有房证明还是无房证明
        String dylx = this.getYfwfzmType(qlrmc, qlrzjh);

        // 生成PDF文件
        OfficeExportDTO pdfExportDTO = new OfficeExportDTO();
        pdfExportDTO.setModelName(printPath + dylx + CommonConstantUtils.WJHZ_DOCX);
        pdfExportDTO.setFileName(qlrmc + "有房无房证明");
        pdfExportDTO.setXmlData(xmlData);
        String pdfFilePath = pdfController.generatePdfFile(pdfExportDTO);

        // 5、上传pdf文件至大云
        try{
            BdcPdfDTO bdcPdfDTO = new BdcPdfDTO();
            bdcPdfDTO.setPdffjmc(pdfExportDTO.getFileName());
            bdcPdfDTO.setFoldName("不动产权属登记信息查询结果证明");
            bdcPdfDTO.setGzlslid(gzlslid);
            bdcPdfDTO.setPdfFilePath(pdfFilePath);
            bdcPdfDTO.setFileSuffix(CommonConstantUtils.WJHZ_PDF);
            this.bdcUploadFileUtils.uploadPdfByFilePath(bdcPdfDTO);
        }catch (IOException e){
            throw new AppException("上传PDF文件至大云中心出错，错误信息为：" + e.getMessage());
        }
    }

    /**
     * 判断打印的有房无房证明类型
     */
    private String getYfwfzmType(String qlrmc, String qlrzjh){
        List<BdcQlrQO> qlrxxList = new ArrayList<>(2);
        BdcQlrQO qlrxx = new BdcQlrQO();
        qlrxx.setQlrmc(qlrmc);
        qlrxx.setZjh(qlrzjh);
        qlrxxList.add(qlrxx);
        BdcZfxxQO bdcZfxxQO = new BdcZfxxQO();
        bdcZfxxQO.setQlrxx(qlrxxList);
        bdcZfxxQO.setCxly("1");
        List<BdcZfxxDTO> zfxxDTOList = bdcZfxxCxFeignService.listBdcNtZfxxDTO(bdcZfxxQO);
        if (CollectionUtils.isNotEmpty(zfxxDTOList)) {
            return "yfzm";
        }
        return "wfzm";
    }

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 常州查封登记上传权属证明到文件中心
     * @date : 2021/9/7 11:04
     */
    @ResponseBody
    @GetMapping("/fwqszm")
    public void uploadFwqszm(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("生成权属证明缺失工作流实例id");
        }
        //批量的上传多分
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            List<BdcSlXmZsDTO> bdcSlXmZsDTOList = new ArrayList<>(CollectionUtils.size(bdcXmDOList));
            for (BdcXmDO bdcXmDO : bdcXmDOList) {
                List<BdcXmDO> cqXmList = bdcXmFeignService.listPrevCqXm(bdcXmDO.getXmid(), true);
                BdcSlXmZsDTO bdcSlXmZsDTO = new BdcSlXmZsDTO();
                bdcSlXmZsDTO.setBdcXmDO(bdcXmDO);
                if (CollectionUtils.isNotEmpty(cqXmList)) {
                    List<BdcZsDO> bdcZsDOList = bdcZsFeignService.queryBdcZsByXmid(cqXmList.get(0).getXmid());
                    if (CollectionUtils.isNotEmpty(bdcZsDOList)) {
                        bdcSlXmZsDTO.setZsid(bdcZsDOList.get(0).getZsid());
                    }
                }
                bdcSlXmZsDTOList.add(bdcSlXmZsDTO);
            }
            //根据证书id不为空的去重，为空的直接添加
            List<BdcSlXmZsDTO> nonNullZsidList = bdcSlXmZsDTOList.stream().filter(bdcSlXmZsDTO -> StringUtils.isNotBlank(bdcSlXmZsDTO.getZsid())).collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(BdcSlXmZsDTO::getZsid))), ArrayList::new));
            List<BdcSlXmZsDTO> nullZsidList = bdcSlXmZsDTOList.stream().filter(bdcSlXmZsDTO -> StringUtils.isBlank(bdcSlXmZsDTO.getZsid())).collect(Collectors.toList());
            List<BdcSlXmZsDTO> resultList = new ArrayList<>(CollectionUtils.size(bdcXmDOList));
            resultList.addAll(nonNullZsidList);
            resultList.addAll(nullZsidList);
            for (BdcSlXmZsDTO bdcSlXmZsDTO : resultList) {
                BdcXmDO bdcXmDO = bdcSlXmZsDTO.getBdcXmDO();
                String dylx = "";
                if (StringUtils.isNotBlank(bdcXmDO.getYcqzh()) && StringUtils.indexOf(bdcXmDO.getYcqzh(), CommonConstantUtils.BDC_BDCQZH_BS) > -1) {
                    dylx = CommonConstantUtils.DYLX_QSZM_BDCQZ;
                } else {
                    dylx = CommonConstantUtils.DYLX_QSZM_FCTDZ;
                }
                //获取数据源xml
                String xml = bdcSlPrintFeignService.packPrintXmlToXmid(bdcXmDO.getGzlslid(), dylx, "false", "", "", bdcXmDO.getXmid());
                // 生成PDF文件
                OfficeExportDTO pdfExportDTO = new OfficeExportDTO();
                pdfExportDTO.setModelName(printPath + dylx + CommonConstantUtils.WJHZ_DOCX);
                pdfExportDTO.setFileName(bdcXmDO.getYwr() + bdcXmDO.getXmid() + "权属证明");
                pdfExportDTO.setXmlData(xml);
                String pdfFilePath = pdfController.generatePdfFile(pdfExportDTO);

                //上传到文件中心
                try {
                    BdcPdfDTO bdcPdfDTO = new BdcPdfDTO();
                    bdcPdfDTO.setPdffjmc(pdfExportDTO.getFileName());
                    bdcPdfDTO.setFoldName("权属证明");
                    bdcPdfDTO.setGzlslid(gzlslid);
                    bdcPdfDTO.setPdfFilePath(pdfFilePath);
                    bdcPdfDTO.setFileSuffix(CommonConstantUtils.WJHZ_PDF);
                    this.bdcUploadFileUtils.uploadPdfByFilePath(bdcPdfDTO);
                } catch (IOException e) {
                    throw new AppException("上传PDF文件至大云中心出错，错误信息为：" + e.getMessage());
                }
            }
        }
    }

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 档案交接 打印档案目录获取打印类型
     * @date : 2021/11/2 16:43
     */
    @ResponseBody
    @GetMapping("/damldylx")
    public Object queryDamlDylx(String gzlslids) {
        if (StringUtils.isNotBlank(gzlslids)) {
            String[] gzlslidArray = gzlslids.split(CommonConstantUtils.ZF_YW_DH);
            //根据gzlslid查询上一手项目信息
            List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslidArray[0]);
            if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
                BdcDjxlPzDO bdcDjxlPzDO = bdcDjxlPzFeignService.queryBdcDjxlPzByGzldyidAndDjxl(bdcXmDTOList.get(0).getGzldyid(), bdcXmDTOList.get(0).getDjxl());
                if (Objects.nonNull(bdcDjxlPzDO)) {
                    if(StringUtils.isBlank(bdcDjxlPzDO.getDamldylx())){
                        bdcDjxlPzDO.setDamldylx("daml");
                    }
                    if(StringUtils.isBlank(bdcDjxlPzDO.getSpbdylx())){
                        bdcDjxlPzDO.setSpbdylx("bdcSqSpb");
                    }
                    return  bdcDjxlPzDO;
                }
            }
        }
        return null;
    }

    /**
     * 权限申请表数据存储redis中
     * <p>请求链接无需权限，链接在ignores中过滤了</p>
     * @param json 表单参数值
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @PostMapping("/qxsqb/redisKey")
    public String qxsqbRediskey(@RequestBody String json){
        String redisKey = redisUtils.addStringValue(UUIDGenerator.generate16(), json, 600);
        return redisKey;
    }

    /**
     * 权限申请表生成pdf
     * * <p>请求链接无需权限，链接在ignores中过滤了</p>
     * @param response
     * @param key redisKey
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @GetMapping("/qxsqb/print/{key}")
    public void getQxsqbXml(HttpServletResponse response, @PathVariable("key") String key) {
        String paramJson = redisUtils.getStringValue(key);

        // 查询打印配置
        Map dylxMap = bdcDysjPzFeignService.queryBdcDysjPzByDylx(Arrays.asList("qxsqb"));
        if(MapUtils.isEmpty(dylxMap) || !dylxMap.containsKey("qxsqb")){
            throw new AppException(ErrorCode.CUSTOM, "未获取到权限申请表的打印配置。");
        }
        BdcDysjPzDO bdcDysjPzDO = JSON.parseObject(JSON.toJSONString(dylxMap.get("qxsqb")), BdcDysjPzDO.class);

        // 组装打印数据
        BdcDysjDTO bdcDysjDTO = new BdcDysjDTO();
        JSONObject jsonObject = JSON.parseObject(paramJson);
        bdcDysjDTO.setDysj(jsonObject.toJSONString());
        bdcDysjDTO.setDyzbsj(JSONObject.toJSONString(ArrayListMultimap.create()));
        bdcDysjDTO.setDylx("qxsqb");
        bdcDysjDTO.setDyzd(bdcDysjPzDO.getDyzd());

        // 获取打印XML数据
        List<BdcDysjDTO> bdcDysjDTOList = new ArrayList<>(1);
        bdcDysjDTOList.add(bdcDysjDTO);
        String xmlData = bdcPrintFeignService.printDatas(bdcDysjDTOList);

        if (StringUtils.isBlank(xmlData)) {
            throw new MissingArgumentException("导出pdf中止，原因：未获取到数据信息！");
        }

        // 调用PDF打印服务
        OfficeExportDTO pdfExportDTO = new OfficeExportDTO();
        pdfExportDTO.setModelName(bdcDysjPzDO.getPdfpath());
        /// 文件名称临时用打印类型代替下
        pdfExportDTO.setFileName(bdcDysjPzDO.getDylx());
        pdfExportDTO.setXmlData(xmlData);
        pdfExportDTO.setLocalFile(null);
        pdfController.exportPdf(response, pdfExportDTO);
    }


    /**
     * @param bdcSlPrintDTO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 标准版打印参数组织
     * 1. 页面按钮支持自定义设置，配置值为slym.printBtn = '{"dylx": "按钮名称", "dylx1": "按钮名称1"}'
     * 2. 收件单和申请书各个流程变化较大，从bdc_djxl_pz 的表读取配置值
     * @date : 2022/5/27 11:14
     */
    @ResponseBody
    @GetMapping("/standard")
    public BdcNewPrintDTO generateStandardPrint(BdcSlPrintDTO bdcSlPrintDTO) {
        bdcSlPrintDTO.setSystemVersion(systemVersion);
        bdcSlPrintDTO.setPrintModel(printMode);
        getSjdSqsDylx(bdcSlPrintDTO);
        String modelurl = bdcSlPrintDTO.getUrl() + CommonConstantUtils.ModelPath + bdcSlPrintDTO.getDylx() + CommonConstantUtils.WJHZ_FR3;
        BdcNewPrintDTO bdcNewPrintDTO = bdcSlPrintFeignService.generatePrintDTO(bdcSlPrintDTO);
        bdcNewPrintDTO.setModelurl(modelurl);
        if (StringUtils.isNotBlank(bdcSlPrintDTO.getGzlslid()) && CollectionUtils.isNotEmpty(pldyList) && pldyList.contains(bdcSlPrintDTO.getDylx())) {
            LOGGER.info("当前打印类型为{}，系统中配置的需要改变打印类型为批量打印的打印类型{}", bdcSlPrintDTO.getDylx(), pldyList);
            int lclx = bdcXmFeignService.makeSureBdcXmLx(bdcSlPrintDTO.getGzlslid());
            if (Objects.equals(CommonConstantUtils.LCLX_PL, lclx)) {
                bdcNewPrintDTO.setDylx(bdcSlPrintDTO.getDylx() + "pl");
            } else {
                bdcNewPrintDTO.setDylx(bdcSlPrintDTO.getDylx());
            }
        } else {
            bdcNewPrintDTO.setDylx(bdcSlPrintDTO.getDylx());
        }
        LOGGER.info("工作流实例id{},打印类型{},数据源dataUrl==={}", bdcSlPrintDTO.getGzlslid(), bdcNewPrintDTO.getDylx(), bdcNewPrintDTO.getDataurl());
        return bdcNewPrintDTO;
    }


}
