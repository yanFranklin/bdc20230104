package cn.gtmap.realestate.register.ui.web.rest;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.storage.domain.dto.BaseResultDto;
import cn.gtmap.gtc.workflow.domain.manage.ProcessInstanceData;
import cn.gtmap.gtc.workflow.domain.manage.UserTaskDto;
import cn.gtmap.realestate.common.core.annotations.RedissonLock;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.accept.*;
import cn.gtmap.realestate.common.core.domain.certificate.BdcGdxxFphhDO;
import cn.gtmap.realestate.common.core.domain.register.BdcQzxxDO;
import cn.gtmap.realestate.common.core.dto.BdcPrintDTO;
import cn.gtmap.realestate.common.core.dto.BdcZsPrintDTO;
import cn.gtmap.realestate.common.core.dto.OfficeExportDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlSjclDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlZbDataDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcDysjDTO;
import cn.gtmap.realestate.common.core.dto.register.DyaSpbDTO;
import cn.gtmap.realestate.common.core.enums.TsDyaSpbResponseEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSjclQO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzYzQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmLsgxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.qo.register.BdcGdxxFphhQO;
import cn.gtmap.realestate.common.core.service.feign.accept.*;
import cn.gtmap.realestate.common.core.service.feign.certificate.*;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.*;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcZszmCxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.*;
import cn.gtmap.realestate.common.core.service.rest.certificate.BdcZsRestService;
import cn.gtmap.realestate.common.core.service.rest.register.BdcShxxRestService;
import cn.gtmap.realestate.common.core.support.pdf.PdfController;
import cn.gtmap.realestate.common.matcher.ProcessInstanceClientMatcher;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.common.matcher.ZipkinAuditEventRepositoryMatcher;
import cn.gtmap.realestate.common.property.registerui.DajjDahPrefixJtConfig;
import cn.gtmap.realestate.common.property.registerui.DajjLyConfig;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.register.ui.config.DajjDahPrefix2Config;
import cn.gtmap.realestate.register.ui.config.DajjDahPrefixConfig;
import cn.gtmap.realestate.register.ui.util.Constants;
import cn.gtmap.realestate.register.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/3/26
 * @description register-ui，所有打印功能实现的服务
 */
@RestController
@RequestMapping("/rest/v1.0/print")
@Validated
public class BdcPrintController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(BdcPrintController.class);

    @Value("${print.path}")
    private String printPath;

    @Autowired
    BdcZsFeignService bdcZsFeignService;
    @Autowired
    BdcZsPirntFeignService bdcZsPirntFeignService;
    @Autowired
    BdcFzjlFeignService bdcFzjlFeignService;
    @Autowired
    BdcYjdFeignService bdcYjdFeignService;
    @Autowired
    BdcJjdFeignService bdcJjdFeignService;
    @Autowired
    BdcShxxRestService bdcShxxRestService;
    @Autowired
    BdcSlPrintFeignService bdcSlPrintFeignService;
    @Autowired
    BdcBdcdyFeignService bdcBdcdyFeignService;
    @Autowired
    private BdcPrintFeignService bdcPrintFeignService;
    @Autowired
    WorkFlowUtils workFlowUtils;
    @Autowired
    private ExchangeInterfaceFeignService exchangeInterfaceFeignService;
    @Autowired
    private PdfController pdfController;
    @Autowired
    private BdcSlSfxxFeignService bdcSlSfxxFeignService;
    @Autowired
    BdcQzxxFeginService qzxxFeginService;
    @Autowired
    private BdcPrintDataFeignService bdcPrintDataFeignService;
    @Autowired
    BdcShxxFeignService bdcShxxFeignService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;

    @Autowired
    StorageClientMatcher storageClient;
    @Autowired
    BdcQlrFeignService bdcQlrFeignService;
    @Autowired
    BdcZdFeignService bdcZdFeignService;
    @Autowired
    BdcDypzFeignService bdcDypzFeignService;
    @Autowired
    BdcSlSjclFeignService bdcSlSjclFeignService;
    @Autowired
    BdcSlZdFeignService bdcSlZdFeignService;

    @Autowired
    DajjDahPrefixConfig dajjDahPrefixConfig;
    @Autowired
    DajjDahPrefix2Config  dajjDahPrefix2Config;
    @Autowired
    DajjDahPrefixJtConfig dajjDahPrefixJtConfig;
    @Autowired
    DajjLyConfig dajjLyConfig;
    @Autowired
    BdcDjxlPzFeignService bdcDjxlPzFeignService;
    @Autowired
    private BdcBhFeignService bdcBhFeignService;
    @Autowired
    private BdcZsRestService bdcZsRestService;
    @Autowired
    private BdcZszmCxFeignService bdcZszmCxFeignService;
    @Autowired
    private BdcGdxxFphhFeignService bdcGdxxFphhFeignService;
    @Autowired
    private BdcLsgxFeignService bdcLsgxFeignService;
    @Autowired
    private ZipkinAuditEventRepositoryMatcher zipkinAuditEventRepository;
    @Autowired
    BdcSlXmFeignService bdcSlXmFeignService;
    @Autowired
    BdcSlXmLsgxFeignService bdcSlXmLsgxFeignService;
    @Autowired
    ProcessInstanceClientMatcher processInstanceClient;
    @Autowired
    BdcSlJbxxFeignService bdcSlJbxxFeignService;
    @Autowired
    BdcQllxFeignService bdcQllxFeignService;
    @Autowired
    private RedisUtils redisUtils;
    @Value("${print.mode:0}")
    private Integer printMode;

    /**
     * 档案号需要特殊处理的区划（多个英文逗号分隔），例如武进
     */
    @Value("${dajj.tsqh:}")
    private String dajjTsqh;

    /**
     * 档案交接金坛区划
     */
    @Value("${dajj.jtqh:}")
    private String dajjJtqh;

    /*
     * 档案目录最后一个文件夹名称配置
     * */
    @Value("#{'${daml.lastfolder:}'.split(',')}")
    private List<String> damlFolderList;

    /**
     * 生成证书类档案号权利
     */
    @Value("#{'${dajj.zslql:}'.split(',')}")
    private List<String> zslqlList;

    /**
     * 生成证明类档案号权利
     */
    @Value("#{'${dajj.zmlql:}'.split(',')}")
    private List<String> zmlqlList;

    /**
     * 非登记业务沿用上一个档案号的流程定义id
     */
    @Value("#{'${dajj.fdjyw.yysys:}'.split(',')}")
    private List<String> yysysGzldyidList;

    /**
     * 沿用证书类
     */
    @Value("${dajj.yyzsl:}")
    private String yyzsl;

    /**
     * 证明类档案号
     */
    @Value("${dajj.zmlprefix:}")
    private String zmlprefix;

    /**
     * 证书类档案号
     */
    @Value("${dajj.zslprefix:}")
    private String zslprefix;

    @Value("${daml.tableConfig:}")
    private String damlTableConfig;

    @Value("#{'${dajj.gydahgzldyids:}'.split(',')}")
    private List<String> gydahgzldyids;
    /**
     * @param bdcPrintDTO 打印对象
     * @return String 证书打印xml
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 证书的附属清单
     */
    @GetMapping("/zs/fsqd/{dylx}/{gzlslid}/xml")
    String printZsFsqd(BdcPrintDTO bdcPrintDTO) {
        // 封装打印参数
        bdcPrintDTO.setBdcUrlDTO(this.bdcUrl());
        return bdcZsPirntFeignService.zsFsqdPrintXml(bdcPrintDTO);
    }


    /**
     * @param zsid 证书ID
     * @param zslx 证书打印类型
     * @return String 证书打印xml
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 单个证书打印
     */
    @GetMapping("/zs/{zsid}/{zslx}/singleXml")
    String printZs(@PathVariable(name = "zsid") String zsid, @PathVariable(name = "zslx") String zslx,
                   @RequestParam(name = "userName", required = false) String userName) throws UnsupportedEncodingException {
        if (StringUtils.isBlank(zsid) || StringUtils.isBlank(zslx)) {
            throw new MissingArgumentException("缺失打印证书的ID或者证书打印类型");
        }
        // 封装打印参数
        BdcPrintDTO bdcPrintDTO = new BdcPrintDTO();
        bdcPrintDTO.setBdcUrlDTO(this.bdcUrl());
        bdcPrintDTO.setDylx(zslx);
        bdcPrintDTO.setZsid(zsid);
        if (StringUtils.isNotBlank(userName)) {
            bdcPrintDTO.setJbr(URLDecoder.decode(userName, "UTF-8"));
        }
        return bdcZsPirntFeignService.singleZsPrintXml(bdcPrintDTO);
    }

    /**
     * @param zsidStr 证书ID字符串
     * @param zslx    证书打印类型
     * @return String 证书打印xml
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取证书批量打印xml
     */

    @GetMapping("/batchZs/{zslx}")
    String printBatchZs(@RequestParam(name = "zsidList") String zsidStr, @PathVariable(value = "zslx") String zslx) {
        if (StringUtils.isBlank(zsidStr) || StringUtils.isBlank(zslx)) {
            throw new MissingArgumentException("缺失打印证书的ID或者证书打印类型");
        }
        List<Integer> qsztList = new ArrayList(1);
        qsztList.add(CommonConstantUtils.QSZT_VALID);
        List<String> listZsid = Arrays.asList(StringUtils.split(zsidStr, CommonConstantUtils.ZF_YW_DH));
        // 只获取权属状态为现势的证书打印
        BdcZsQO bdcZsQO = new BdcZsQO();
        bdcZsQO.setZsidList(listZsid);
        bdcZsQO.setQsztList(qsztList);
        if (StringUtils.equals(CommonConstantUtils.DYLX_ZM, zslx)) {
            bdcZsQO.setZslx(CommonConstantUtils.ZSLX_ZM);
        } else {
            // 不动产权证和首次登记证的证书类型均为1
            bdcZsQO.setZslx(CommonConstantUtils.ZSLX_ZS);
        }
        List<String> printZsidList = bdcZsFeignService.queryZsid(bdcZsQO);

        // 封装打印参数
        BdcPrintDTO bdcPrintDTO = new BdcPrintDTO();
        bdcPrintDTO.setBdcUrlDTO(this.bdcUrl());
        bdcPrintDTO.setDylx(zslx);
        bdcPrintDTO.setListZsid(printZsidList);
        return bdcZsPirntFeignService.batchZsPrintXml(bdcPrintDTO);
    }

    /**
     * @param zsidStr 证书ID字符串
     * @param zslx    证书打印类型
     * @return String 证书打印xml
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description (南通)获取证书批量打印xml
     */
    @GetMapping("/nantong/batchZs/{zslx}")
    String printNtBatchZs(@RequestParam(name = "zsidList") String zsidStr, @PathVariable(value = "zslx") String zslx,
                          @RequestParam(name = "userName", required = false) String userName) throws UnsupportedEncodingException {
        if (StringUtils.isBlank(zsidStr) || StringUtils.isBlank(zslx)) {
            throw new MissingArgumentException("缺失打印证书的ID或者证书打印类型");
        }
        List<Integer> qsztList = new ArrayList(1);
        qsztList.add(CommonConstantUtils.QSZT_VALID);
        List<String> listZsid = Arrays.asList(StringUtils.split(zsidStr, CommonConstantUtils.ZF_YW_DH));
        // 只获取权属状态为现势的证书打印
        BdcZsQO bdcZsQO = new BdcZsQO();
        bdcZsQO.setZsidList(listZsid);
        bdcZsQO.setQsztList(qsztList);

        //  南通证书类型不一样，需要单独判断 modified by zhuyong
        if (StringUtils.equals(CommonConstantUtils.DYLX_ZM, zslx)) {
            // 证明
            bdcZsQO.setZslx(CommonConstantUtils.ZSLX_ZM);
        } else if (StringUtils.equals(CommonConstantUtils.DYLX_SCZMD, zslx)) {
            // 证明单
            bdcZsQO.setZslx(CommonConstantUtils.ZSLX_ZMD);
        } else {
            // 证书
            bdcZsQO.setZslx(CommonConstantUtils.ZSLX_ZS);
        }
        List<String> printZsidList = bdcZsFeignService.queryZsid(bdcZsQO);

        // 封装打印参数
        BdcPrintDTO bdcPrintDTO = new BdcPrintDTO();
        bdcPrintDTO.setBdcUrlDTO(this.bdcUrl());
        bdcPrintDTO.setDylx(zslx);
        bdcPrintDTO.setListZsid(printZsidList);
        if (StringUtils.isNotBlank(userName)) {
            bdcPrintDTO.setJbr(URLDecoder.decode(userName, "UTF-8"));
        }
        return bdcZsPirntFeignService.batchZsPrintXml(bdcPrintDTO);
    }

    /**
     * @param zslx 证书类型
     * @return string
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 打印流程的所有证书
     */
    @GetMapping("/allZs/{zslx}")
    String printAllZs(@PathVariable(value = "zslx") String zslx, @RequestParam(name = "gzlslid") String gzlslid,@RequestParam(name = "zsmodel",required = false) String zsmodel) {
        if (StringUtils.isBlank(gzlslid) || StringUtils.isBlank(zslx)) {
            throw new MissingArgumentException("缺失gzlslid或者证书打印类型");
        }
        List<Integer> qsztList = new ArrayList(1);
        qsztList.add(CommonConstantUtils.QSZT_VALID);
        // 获取流程所有的权属状态为现势的证书ID
        BdcZsQO bdcZsQO = new BdcZsQO();
        bdcZsQO.setGzlslid(gzlslid);
        bdcZsQO.setQsztList(qsztList);
        if (StringUtils.equals(CommonConstantUtils.DYLX_ZM, zslx)) {
            bdcZsQO.setZslx(CommonConstantUtils.ZSLX_ZM);
        } else if (CommonConstantUtils.ZS_MODAL_SCZMD.equals(zsmodel) ||StringUtils.equals(CommonConstantUtils.DYLX_ZS, zslx)){
            // 不动产权证和首次登记证(zsmodel=sczmd)的证书类型均为1
            bdcZsQO.setZslx(CommonConstantUtils.ZSLX_ZS);
        }else if(StringUtils.equals(CommonConstantUtils.DYLX_SCZMD, zslx)){
            bdcZsQO.setZslx(CommonConstantUtils.ZSLX_ZMD);

        }
        List<String> listZsid = bdcZsFeignService.queryZsid(bdcZsQO);
        // 封装打印参数
        BdcPrintDTO bdcPrintDTO = new BdcPrintDTO();
        bdcPrintDTO.setBdcUrlDTO(this.bdcUrl());
        bdcPrintDTO.setDylx(zslx);
        bdcPrintDTO.setListZsid(listZsid);
        return bdcZsPirntFeignService.batchZsPrintXml(bdcPrintDTO);
    }

    /**
     * @param zslx    证书类型 (zs 证书  zm 证明 sczmd 首次证明单)
     * @param gzlslid 工作流实例ID
     * @return {String} 打印的XML数据
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description （南通）打印流程对应的所有证书、证明或者首次证明单
     */
    @GetMapping("/nantong/allZs/{zslx}")
    public String printNtAllZs(@PathVariable(value = "zslx") String zslx, @RequestParam(name = "gzlslid") String gzlslid) {
        if (StringUtils.isBlank(gzlslid) || StringUtils.isBlank(zslx)) {
            throw new MissingArgumentException("南通打印证书、证明或首次证明单错误：未指定证书类型或者未传递工作流实例ID参数！");
        }

        List<Integer> qsztList = new ArrayList(1);
        qsztList.add(CommonConstantUtils.QSZT_VALID);
        // 获取流程所有的权属状态为现势的证书ID
        BdcZsQO bdcZsQO = new BdcZsQO();
        bdcZsQO.setGzlslid(gzlslid);
        bdcZsQO.setQsztList(qsztList);

        //  南通证书类型不一样，需要单独判断 modified by zhuyong
        if (StringUtils.equals(CommonConstantUtils.DYLX_ZM, zslx)) {
            // 证明
            bdcZsQO.setZslx(CommonConstantUtils.ZSLX_ZM);
        } else if (StringUtils.equals(CommonConstantUtils.DYLX_SCZMD, zslx)) {
            // 证明单
            bdcZsQO.setZslx(CommonConstantUtils.ZSLX_ZMD);
        } else {
            // 证书
            bdcZsQO.setZslx(CommonConstantUtils.ZSLX_ZS);
        }
        List<String> zsidList = bdcZsFeignService.queryZsid(bdcZsQO);

        // 封装打印参数
        BdcPrintDTO bdcPrintDTO = new BdcPrintDTO();
        bdcPrintDTO.setBdcUrlDTO(this.bdcUrl());
        bdcPrintDTO.setDylx(zslx);
        bdcPrintDTO.setListZsid(zsidList);
        return bdcZsPirntFeignService.batchZsPrintXml(bdcPrintDTO);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @param xmid    项目ID
     * @param dylx    发证记录打印类型
     * @param zsid    证书ID
     * @return String 发证记录打印xml
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取发证记录的打印xml
     */
    @GetMapping(value = "/fzjl/xml")
    String fzjlPrintXml(@RequestParam(name = "gzlslid") String gzlslid, @RequestParam(name = "xmid", required = false) String xmid,
                        @RequestParam(name = "dylx") String dylx, @RequestParam(name = "zsid", required = false) String zsid) {
        BdcPrintDTO bdcPrintDTO = new BdcPrintDTO();
        bdcPrintDTO.setGzlslid(gzlslid);
        bdcPrintDTO.setXmid(xmid);
        bdcPrintDTO.setDylx(dylx);
        bdcPrintDTO.setZsid(zsid);
        bdcPrintDTO.setBdcUrlDTO(this.bdcUrl());
        return bdcFzjlFeignService.fzjlPrintXml(bdcPrintDTO);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @param xmid    项目ID
     * @param dylx    发证记录打印类型
     * @return String 发证记录打印xml
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 按流程生成发证记录获取发证记录的打印xml
     */
    @GetMapping(value = "/fzjl/one/xml")
    String fzjlOnePrintXml(@RequestParam(name = "gzlslid") String gzlslid, @RequestParam(name = "xmid", required = false) String xmid, @RequestParam(name = "dylx") String dylx) {
        BdcPrintDTO bdcPrintDTO = new BdcPrintDTO();
        bdcPrintDTO.setGzlslid(gzlslid);
        bdcPrintDTO.setXmid(xmid);
        bdcPrintDTO.setDylx(dylx);
        bdcPrintDTO.setBdcUrlDTO(this.bdcUrl());
        return bdcFzjlFeignService.fzjlOnePrintXml(bdcPrintDTO);
    }

    /**
     * @param yjdid 移交单ID
     * @return String 移交单打印xml
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取移交单打印xml
     */
    @GetMapping(value = "/yjd/{yjdid}/{dylx}/xml")
    String yjdPrintXml(@PathVariable(value = "yjdid") String yjdid, @PathVariable(value = "dylx") String dylx) {
        return bdcYjdFeignService.yjdPrintXml(yjdid, dylx, this.bdcUrl());
    }

    /**
     * 南通交接单打印xml
     *
     * @param jjdid 移交单ID
     * @return String 移交单打印xml
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping(value = "/jjd/{jjdid}/xml")
    String jjdPrintXml(@PathVariable(value = "jjdid") String jjdid) {
        return bdcJjdFeignService.jjdPrintXml(jjdid, this.bdcUrl());
    }

    /**
     * 生成南通交接单批量打印xml
     * @param key 打印参数缓存Redis Key
     * @return {String} 移交单打印xml
     */
    @GetMapping(value = "/jjd/batch/{key}/xml")
    String jjdBatchPrintXml(@PathVariable(value = "key") String key) {
        return bdcJjdFeignService.jjdBatchPrintXml(key);
    }

    /**
     * @param bdcPrintDTO 打印参数DTO
     * @return String 打印xml
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取审批表的打印xml
     */
    @GetMapping(value = "/spb/{gzlslid}/{dylx}/xml")
    String spbPrintXml(BdcPrintDTO bdcPrintDTO) {
        List jdmcList = new ArrayList();
        String gzlslid = bdcPrintDTO.getGzlslid();
        List<UserTaskDto> userTaskDataList = workFlowUtils.listShjdxx(gzlslid);
        userTaskDataList.forEach(taskData -> {
            jdmcList.add(taskData.getName());
        });
        bdcPrintDTO.setJdmcList(jdmcList);
        bdcPrintDTO.setBdcUrlDTO(this.bdcUrl());
        bdcPrintDTO.setPrintMode(printMode);
        if (ArrayUtils.contains(Constants.DYLX_ARR_NANTONG, bdcPrintDTO.getDylx()) && !Objects.equals(3, printMode) && !bdcPrintDTO.getLcpzdylx()) {
            return bdcShxxRestService.bdPrintXmlNantong(bdcPrintDTO);
        }
        return bdcShxxRestService.bdPrintXml(bdcPrintDTO);
    }

    /**
     * 获取审批表节点数目
     *
     * @param gzlslid gzlslid
     * @return {Integer} 节点数目
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping(value = "/spb/node/{gzlslid}")
    Integer querySpbNodes(@PathVariable String gzlslid) {
        List<UserTaskDto> userTaskDataList = workFlowUtils.listShjdxx(gzlslid);
        return userTaskDataList == null ? 0 : userTaskDataList.size();
    }

    /**
     * @param gzlslid 工作流实例ID
     * @param dylx    打印类型
     * @return String 打印xml
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取不动产单元的打印xml
     */
    @GetMapping(value = "/bdcdy/{gzlslid}/{dylx}/xml")
    String bdcdyPrintXml(@PathVariable(value = "gzlslid") String gzlslid
            , @PathVariable(value = "dylx") String dylx
            , @RequestParam(value = "zsid", required = false) String zsid
            , @RequestParam(value = "qlr", required = false) String qlr) {
        BdcPrintDTO bdcPrintDTO = new BdcPrintDTO();
        bdcPrintDTO.setZsid(zsid);
        bdcPrintDTO.setGzlslid(gzlslid);
        bdcPrintDTO.setDylx(dylx);
        bdcPrintDTO.setBdcUrlDTO(this.bdcUrl());
        // 抵押权人已在前台两次编码，这里自动解码使用
        bdcPrintDTO.setQlr(qlr);
        return bdcBdcdyFeignService.bdcdyPrintXml(bdcPrintDTO);
    }

    /**
     * @param ewmnr 二维码内容
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取受理编号的二维码信息
     */
    @GetMapping(value = "/ewm/{ewmnr}")
    void ewm(@PathVariable(name = "ewmnr") String ewmnr, HttpServletResponse response) {
        response.setContentType("image/jpg;charset=utf-8");
        response.addHeader("Content-Disposition", "attachment; filename=test.jpg");
        QRcodeUtils.encoderQRCode(ewmnr, null, response);
    }

    /**
     * @param sfxxid 收费信息id
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 获取收费的二维码信息
     */
    @GetMapping(value = "/ewm/sf/{sfxxid}")
    void ewmSf(@PathVariable(name = "sfxxid") String sfxxid, HttpServletResponse response) {
        response.setContentType("image/jpg;charset=utf-8");
        response.addHeader("Content-Disposition", "attachment; filename=test.jpg");
        BdcSlSfxxDO bdcSlSfxxDO = bdcSlSfxxFeignService.queryBdcSlSfxxBySfxxid(sfxxid);
        if (bdcSlSfxxDO == null) {
            throw new AppException("查询不到对应的收费信息");
        }
        if (StringUtils.isBlank(bdcSlSfxxDO.getJfsewmurl())) {
            throw new AppException("找不到对应的网址");
        }
        QRcodeUtils.encoderQRCode(bdcSlSfxxDO.getJfsewmurl(), null, response);
    }


    /**
     * @param gzlslid 工作流实例ID
     * @param zsid    证书ID
     * @return {String} XML数据/ewm/{ewmnr}
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取打印证书（证明）附表的XML数据
     */
    @GetMapping("/zszmfb/{gzlslid}/{zsid}/{zslx}/xml")
    public String getZsfbXml(@PathVariable("gzlslid") String gzlslid, @PathVariable("zsid") String zsid, @PathVariable("zslx") String zslx) {
        if (StringUtils.isBlank(gzlslid) || StringUtils.isBlank(zsid) || StringUtils.isBlank(zslx)) {
            throw new MissingArgumentException("打印证书附表缺失参数！");
        }
        // 批量打印的情况下是以英文逗号分隔的
        String[] zsidArr = zsid.split(CommonConstantUtils.ZF_YW_DH);

        // 封装参数
        Map<String, List<Map>> paramMap = new HashMap<>(1);
        List<Map> bdcdyhListMap = new ArrayList<>(1);

        for (String zsidTemp : zsidArr) {
            Map<String, Object> map = new HashMap<>(1);
            map.put("gzlslid", gzlslid);
            map.put("zsid", zsidTemp);
            bdcdyhListMap.add(map);
        }

        if (CommonConstantUtils.DYLX_ZS.equals(zslx)) {
            // 打印模板：zsfb.fr3  配置数据源类型 zsfb
            paramMap.put("zsfb", bdcdyhListMap);
        } else if (CommonConstantUtils.DYLX_ZM.equals(zslx)) {
            // 打印模板：zmfb.fr3  配置数据源类型 zmfb
            paramMap.put("zmfb", bdcdyhListMap);
        } else if ("dkfb".equals(zslx)) {
            //打印膜版：dkfb.fr3  配置数据源为dkfb
            paramMap.put("dkfb", bdcdyhListMap);
        }

        return bdcPrintFeignService.print(paramMap);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return {String} XML数据
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @description 获取打印证明单列表的XML数据
     */
    @GetMapping("/zmdlb/{gzlslid}/xml")
    public String getZmdlbXml(@PathVariable("gzlslid") String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("打印证书单列表缺失参数！");
        }
        // 封装参数
        Map<String, List<Map>> paramMap = new HashMap<>(1);
        List<Map> bdcdyhListMap = new ArrayList<>(1);
        Map<String, Object> map = new HashMap<>(1);
        map.put("gzlslid", gzlslid);
        bdcdyhListMap.add(map);
        paramMap.put("zmdlb", bdcdyhListMap);

        return bdcPrintFeignService.print(paramMap);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return {String} XML数据
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @description 获取打印所有证明单的XML数据
     */
    @GetMapping("/allZmdprint/{gzlslid}/xml")
    public String getAllZmdXml(@PathVariable("gzlslid") String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("打印证明单缺失参数！");
        }
        List<Integer> qsztList = new ArrayList(1);
        qsztList.add(CommonConstantUtils.QSZT_VALID);
        // 获取流程所有的权属状态为现势的证书ID
        BdcZsQO bdcZsQO = new BdcZsQO();
        bdcZsQO.setGzlslid(gzlslid);
        bdcZsQO.setQsztList(qsztList);
        List<String> zsidList = bdcZsFeignService.queryZsid(bdcZsQO);
        // 封装参数
        Map<String, List<Map>> paramMap = new HashMap<>(1);
        if(CollectionUtils.isNotEmpty(zsidList) && zsidList.size()>0){
            List<Map> zsListMap = new ArrayList<>(1);
            for(String zsid : zsidList){
                Map<String, Object> map = new HashMap<>(1);
                map.put("zsid", zsid);
                zsListMap.add(map);
            }
            paramMap.put("plzmddy", zsListMap);
        }
        return bdcPrintFeignService.print(paramMap);
    }

    /**
     * @param xzdm 乡镇代码
     * @param sxh    顺序号
     * @return {String} XML数据
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 获取打印盒内目录的XML数据
     */
    @GetMapping("/hnml/{xzdm}/{sxh}/xml")
    public String getHnmlXml(@PathVariable("xzdm") String xzdm, @PathVariable("sxh") String sxh){
        if (StringUtils.isBlank(xzdm) || StringUtils.isBlank(sxh)) {
            throw new MissingArgumentException("打印盒内目录缺失参数！");
        }
        // 封装参数
        Map<String, List<Map>> paramMap = new HashMap<>(1);
        List<Map> bdcdyhListMap = new ArrayList<>(1);
        Map<String, Object> map = new HashMap<>(1);
        map.put("xzdm", xzdm);
        map.put("sxh", sxh);
        bdcdyhListMap.add(map);
        paramMap.put("hnml", bdcdyhListMap);
        return bdcPrintFeignService.print(paramMap);
    }
    
    /**
     * @param gzlslid 工作流实例ID
     * @return {String} XML数据/ewm/{ewmnr}
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取打印证书（证明）附表的XML数据
     */
    @GetMapping("/dkfb/{gzlslid}/{zslx}/xml")
    public String getDkfbXml(@PathVariable("gzlslid") String gzlslid, @PathVariable("zslx") String zslx) {
        if (StringUtils.isBlank(gzlslid) || StringUtils.isBlank(zslx)) {
            throw new MissingArgumentException("打印地块附表缺失参数！");
        }
        // 封装参数
        Map<String, List<Map>> paramMap = new HashMap<>(1);
        List<Map> bdcdyhListMap = new ArrayList<>(1);

        Map<String, Object> map = new HashMap<>(1);
        map.put("gzlslid", gzlslid);
        bdcdyhListMap.add(map);


        if (CommonConstantUtils.DYLX_DKFB.equals(zslx)) {
            //打印膜版：dkfb.fr3  配置数据源为dkfb
            paramMap.put("dkfb", bdcdyhListMap);
        }

        return bdcPrintFeignService.print(paramMap);
    }

    /**
     * 根据gzlslid查询所有qllx=9的证书附表信息
     *
     * @param
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 17:47 2020/9/8
     */
    @GetMapping("/dkfbAll/{gzlslid}/{zslx}/xml")
    public String getDkfbXmlAll(@PathVariable("gzlslid") String gzlslid, @PathVariable("zslx") String zslx) {
        if (StringUtils.isBlank(gzlslid) || StringUtils.isBlank(zslx)) {
            throw new MissingArgumentException("打印地块附表缺失参数！");
        }
        // 获取流程所有的权属状态为现势的证书ID
        BdcZsQO bdcZsQO = new BdcZsQO();
        bdcZsQO.setGzlslid(gzlslid);
        bdcZsQO.setZslx(CommonConstantUtils.ZSLX_ZS);
        bdcZsQO.setQllx(CommonConstantUtils.QLLX_TDCBNYDSYQ);
        List<String> zsidList = bdcZsFeignService.queryZsid(bdcZsQO);
        if (CollectionUtils.isEmpty(zsidList)) {
            throw new MissingArgumentException("未查到满足条件的证书信息！");
        }

        // 封装参数
        Map<String, List<Map>> paramMap = new HashMap<>(1);
        List<Map> bdcdyhListMap = new ArrayList<>(1);
        for (String zsid : zsidList) {
            Map<String, Object> map = new HashMap<>(1);
            map.put("zsid", zsid);
            bdcdyhListMap.add(map);
        }


        if (CommonConstantUtils.DYLX_DKFB.equals(zslx)) {
            //打印膜版：dkfb.fr3  配置数据源为dkfb
            paramMap.put("dkfbByZsid", bdcdyhListMap);
        }

        return bdcPrintFeignService.print(paramMap);
    }

    /**
     * 根据证书id 打印地块附表的XML数据
     *
     * @param
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 14:07 2020/9/8
     */
    @GetMapping("/dkfbByZsid/{zsid}/{zslx}/xml")
    public String getDkfbXmlByZsid(@PathVariable("zsid") String zsid, @PathVariable("zslx") String zslx) {
        if (StringUtils.isBlank(zsid) || StringUtils.isBlank(zslx)) {
            throw new MissingArgumentException("打印地块附表缺失参数！");
        }
        // 批量打印的情况下是以英文逗号分隔的
        String[] zsidArr = zsid.split(CommonConstantUtils.ZF_YW_DH);
        // 封装参数
        Map<String, List<Map>> paramMap = new HashMap<>(1);
        List<Map> bdcdyhListMap = new ArrayList<>(1);

        for (String zsidTemp : zsidArr) {
            Map<String, Object> map = new HashMap<>(1);
            map.put("zsid", zsidTemp);
            bdcdyhListMap.add(map);
        }

        if (CommonConstantUtils.DYLX_DKFB.equals(zslx)) {
            //打印膜版：dkfb.fr3  配置数据源为dkfbByZsid
            paramMap.put("dkfbByZsid", bdcdyhListMap);
        }

        return bdcPrintFeignService.print(paramMap);
    }


    /**
     * @param gzlslid 工作流实例ID
     * @return {String} XML数据/ewm/{ewmnr}
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取打印证书（证明）附表的XML数据
     */
    @GetMapping("/zszmfb/{gzlslid}/{zslx}/xml")
    public String getAllZsfbXml(@PathVariable("gzlslid") String gzlslid, @PathVariable("zslx") String zslx) {
        if (StringUtils.isBlank(gzlslid) || StringUtils.isBlank(zslx)) {
            throw new MissingArgumentException("打印证书附表缺失参数gzlslid或zslx！");
        }

        // 获取流程所有的权属状态为现势的证书ID
        BdcZsQO bdcZsQO = new BdcZsQO();
        bdcZsQO.setGzlslid(gzlslid);

        //  南通证书类型不一样，需要单独判断 modified by zhuyong
        if (StringUtils.equals(CommonConstantUtils.DYLX_ZM, zslx)) {
            // 证明
            bdcZsQO.setZslx(CommonConstantUtils.ZSLX_ZM);
        } else if (StringUtils.equals(CommonConstantUtils.DYLX_SCZMD, zslx)) {
            // 证明单
            bdcZsQO.setZslx(CommonConstantUtils.ZSLX_ZMD);
        } else {
            // 证书
            bdcZsQO.setZslx(CommonConstantUtils.ZSLX_ZS);
        }
        List<String> zsidList = bdcZsFeignService.queryZsid(bdcZsQO);

        if (CollectionUtils.isEmpty(zsidList)) {
            throw new MissingArgumentException("未查到满足条件的证书信息！");
        }
        // 封装参数
        Map<String, List<Map>> paramMap = new HashMap<>(1);
        List<Map> bdcdyhListMap = new ArrayList<>(1);

        for (String zsid : zsidList) {
            Map<String, Object> map = new HashMap<>(1);
            map.put("gzlslid", gzlslid);
            map.put("zsid", zsid);
            bdcdyhListMap.add(map);
        }

        if (CommonConstantUtils.DYLX_ZS.equals(zslx)) {
            // 打印模板：zsfb.fr3  配置数据源类型 zsfb
            paramMap.put("zsfb", bdcdyhListMap);
        } else if (CommonConstantUtils.DYLX_ZM.equals(zslx)) {
            // 打印模板：zmfb.fr3  配置数据源类型 zmfb
            paramMap.put("zmfb", bdcdyhListMap);
        }

        return bdcPrintFeignService.print(paramMap);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @param key     预先保存到redis的key值
     * @param dylx    打印类型
     * @return
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description
     */
    @GetMapping("/{dylx}/xmids/xml")
    public String getXmlByRedisXmids(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "key") String key, @PathVariable(value = "dylx") String dylx) {
        // 从redis中获取xmids
        List<String> xmidList = bdcZsFeignService.getXmidsByKey(key);
        LOGGER.warn("xmid的值{}！", xmidList);

        // 封装参数
        Map<String, List<Map>> paramMap = new HashMap<>(1);
        List<Map> bdcdyhListMap = new ArrayList<>(1);
        // 打印服务不支持in操作，这里做变量替换的方式，打印sql写成in ${xmid}
        String inXmidStr = StringToolUtils.appendSqlInStr(xmidList);


        Map<String, Object> map = new HashMap<>(1);
        map.put("gzlslid", gzlslid);
        map.put("inXmidStr", inXmidStr);
        bdcdyhListMap.add(map);
        paramMap.put(dylx, bdcdyhListMap);
        return bdcPrintFeignService.print(paramMap);
    }


    /**
     * @param gzlslid 工作流实例ID
     * @param key     预先保存到redis的key值
     * @param hssj    是否是查户室数据{@true 查询户室数据 @false 查询多幢信息}
     * @return String 打印的xml信息
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 查询房屋户室打印信息
     */
    @GetMapping("/dyfwqd/{hssj}/xmids/xml")
    public String getHssjXmlByRedisXmids(@RequestParam(value = "gzlslid") String gzlslid,
                                         @RequestParam(value = "key", required = false) String key,
                                         @RequestParam(value = "zsid", required = false) String zsid,
                                         @PathVariable(value = "hssj") boolean hssj) throws Exception {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("打印房屋清单缺失参数！");
        }
        // 从redis中获取xmids
        List<String> xmidList = null;
        if (StringUtils.isNotBlank(key)) {
            xmidList = bdcZsFeignService.getXmidsByKey(key);
        }
        //空的话创建个新对象 防止feign报错
        if (CollectionUtils.isEmpty(xmidList)) {
            xmidList = new ArrayList<>();
        }
        LOGGER.warn("xmid的值{}！", xmidList);
        List<BdcDysjDTO> list = bdcPrintDataFeignService.queryFdcqDzFwqdDysj(xmidList, gzlslid, zsid, hssj);
        return bdcPrintFeignService.printDatas(list);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @param key     预存项目ID的redis的key值
     * @return {String} XML数据
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取打印证书（证明）附表预览时候的XML数据
     */
    @GetMapping("/zszmfbyl/xml")
    public String getZsfbYlXml(@RequestParam("gzlslid") String gzlslid, @RequestParam("key") String key, @RequestParam("zslx") String zslx) {
        // 从redis中获取xmids
        List<String> xmidList = bdcZsFeignService.getXmidsByKey(key);
        LOGGER.warn("xmid的值{}！", xmidList);
        // 封装参数
        Map<String, List<Map>> paramMap = new HashMap<>(1);
        List<Map> bdcdyhListMap = new ArrayList<>(1);
        // 打印服务不支持in操作，这里做变量替换的方式，打印sql写成in ${xmid}
        String inXmidStr = StringToolUtils.appendSqlInStr(xmidList);

        Map<String, Object> map = new HashMap<>(1);
        map.put("gzlslid", gzlslid);
        map.put("xmid", inXmidStr);
        bdcdyhListMap.add(map);
        if (CommonConstantUtils.DYLX_ZS.equals(zslx)) {
            // 打印模板：zsfbyl.fr3  配置数据源类型 zsfbyl
            paramMap.put("zsfbyl", bdcdyhListMap);
        } else if (CommonConstantUtils.DYLX_ZM.equals(zslx)) {
            // 打印模板：zmfbyl.fr3  配置数据源类型 zmfbyl
            paramMap.put("zmfbyl", bdcdyhListMap);
        }

        return bdcPrintFeignService.print(paramMap);
    }

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 打印档案目录信息图片
     */
    @ResponseBody
    @RequestMapping(value = "/hefei/image/{userName}/{archid}/{currentpage}")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("打印档案目录信息图片")
    @ApiImplicitParams({@ApiImplicitParam(name = "archid", value = "archid", required = true, dataType = "String", paramType = "path"), @ApiImplicitParam(name = "currentpage", value = "当前页", required = true, dataType = "String", paramType = "path")})
    public void queryImage(HttpServletResponse response, @PathVariable("userName") String userName, @PathVariable("archid") String archid, @PathVariable("currentpage") Integer currentpage) {
        BufferedImage image = null;
        LOGGER.info("archid：{}", archid);
        LOGGER.info("userName：{}", userName);
        if (StringUtils.isAnyBlank(archid, userName) || currentpage == null) {
            throw new MissingArgumentException("未获取到档案信息图片参数缺失：archid，currentpage,userName！");
        }
        Map param = new HashMap(3);
        param.put("username", userName);
        param.put("archid", archid);
        param.put("currentpage", currentpage);
        Object dafjxxImage = exchangeInterfaceFeignService.requestInterface("hfDaFjxx", param);
        if (dafjxxImage == null) {
            throw new AppException("未获取到档案信息图片");
        }
        String baseCode = "data:image/jpg;base64,";
        String imageJson = JSON.toJSONString(dafjxxImage);
//        LOGGER.info("imageJson：{}", imageJson);
        Map dafjxxImageMap = JSON.parseObject(imageJson, HashMap.class);
        MultipartFile file = Base64Utils.base64ToMultipart(baseCode + dafjxxImageMap.get("data"));
        try (ByteArrayInputStream bis = new ByteArrayInputStream(file.getBytes())) {
            image = ImageIO.read(bis);
            image.flush();
            ImageIO.write(image, "jpg", response.getOutputStream());
        } catch (IOException e) {
            LOGGER.info("审核登簿子系统：获取档案信息图片异常:{}", e);
        }
    }

    /**
     * @param archid currentpage
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取档案目录信息图片xml
     */
    @GetMapping(value = "/hefei/image/xml/{userName}/{archid}/{currentpage}")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取档案目录信息图片xml")
    @ApiImplicitParams({@ApiImplicitParam(name = "userName", value = "userName", required = true, dataType = "String", paramType = "path"), @ApiImplicitParam(name = "archid", value = "archid", required = true, dataType = "String", paramType = "path"), @ApiImplicitParam(name = "currentpage", value = "当前页", required = true, dataType = "Integer", paramType = "path")})
    public String queryDaxxImagePrintXml(@PathVariable("userName") String userName, @PathVariable("archid") String archid, @PathVariable("currentpage") Integer currentpage) {
        if (StringUtils.isAnyBlank(archid, userName) || currentpage == null) {
            throw new MissingArgumentException("查询参数缺失！");
        }
        String zdtXml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<fetchdatas>" +
                "    <page>" +
                "        <datas>" +
                "            <data name=\"daxx\" type=\"Image\">$DAXX</data>" +
                "        </datas>" +
                "    </page>" +
                "</fetchdatas>";
        List<BdcDysjDTO> bdcDysjDTOList = Lists.newArrayListWithCapacity(2);
        Map<String, String> param = Maps.newHashMap();
        String daxxPrintUrl = registerUiUrl + CommonConstantUtils.DAXX_IMAGE_PRINT_URL;
        daxxPrintUrl = StringUtils.replace(daxxPrintUrl, "{userName}", userName);
        daxxPrintUrl = StringUtils.replace(daxxPrintUrl, "{archid}", archid);
        daxxPrintUrl = StringUtils.replace(daxxPrintUrl, "{currentpage}", String.valueOf(currentpage));
        param.put("DAXX", daxxPrintUrl);
        BdcDysjDTO bdcDysjDTO = new BdcDysjDTO();
        bdcDysjDTO.setDysj(JSONObject.toJSONString(param));
        bdcDysjDTO.setDyzd(zdtXml);
        bdcDysjDTO.setDyzbsj(JSONObject.toJSONString(new HashMap<String, String>()));
        bdcDysjDTOList.add(bdcDysjDTO);
        return bdcPrintFeignService.printDatas(bdcDysjDTOList);
    }


    /**
     * @param listZsidsStr 选中的记录数据
     * @return {String} 保存的Redis key
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 把勾选的打印zsid，保存至Redis中
     */
    @PostMapping("/batchzsprint")
    public String saveListZsidsToRedis(@RequestParam String listZsidsStr) {
        return bdcZsPirntFeignService.saveListZsidsToRedis(listZsidsStr);
    }


    /**
     * @param bdcZsPrintDTO
     * @return {String} 保存的Redis key
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @description 把勾选的打印zsid，保存至Redis中
     */
    @PostMapping("/batchzsprintsavetoredis")
    public String saveZsidsToRedis(@RequestBody BdcZsPrintDTO bdcZsPrintDTO) {
        if(bdcZsPrintDTO == null || StringUtils.isBlank(bdcZsPrintDTO.getListZsidsStr())){
            throw new MissingArgumentException("缺失必要参数");
        }
        return bdcZsPirntFeignService.saveZsidsToRedis(bdcZsPrintDTO);
    }

    /**
     * @param key  证书ID字符串
     * @param zslx 证书打印类型
     * @return String 证书打印xml
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取证书批量打印xml
     */

    @GetMapping("/batchZs/{zslx}/{key}")
    String printBatchZsByRedisKey(@PathVariable(value = "zslx") String zslx, @PathVariable(value = "key") String key) {
        List<Integer> qsztList = new ArrayList(1);
        List<String> listZsid = bdcZsPirntFeignService.getZsidsByKey(key);

        qsztList.add(CommonConstantUtils.QSZT_VALID);
        //List<String> listZsid = Arrays.asList(StringUtils.split(zsids, CommonConstantUtils.ZF_YW_DH));
        // 只获取权属状态为现势的证书打印
        BdcZsQO bdcZsQO = new BdcZsQO();
        bdcZsQO.setZsidList(listZsid);
        bdcZsQO.setQsztList(qsztList);
        if (StringUtils.equals(CommonConstantUtils.DYLX_ZM, zslx)) {
            bdcZsQO.setZslx(CommonConstantUtils.ZSLX_ZM);
        } else {
            // 不动产权证和首次登记证的证书类型均为1
            bdcZsQO.setZslx(CommonConstantUtils.ZSLX_ZS);
        }
        List<String> printZsidList = bdcZsFeignService.queryZsid(bdcZsQO);

        // 封装打印参数
        BdcPrintDTO bdcPrintDTO = new BdcPrintDTO();
        bdcPrintDTO.setBdcUrlDTO(this.bdcUrl());
        bdcPrintDTO.setDylx(zslx);
        bdcPrintDTO.setListZsid(printZsidList);
        return bdcZsPirntFeignService.batchZsPrintXml(bdcPrintDTO);
    }

    /**
     * @param key  证书ID字符串
     * @param zslx 证书打印类型
     * @return String 证书打印xml
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取证书批量打印xml
     */

    @GetMapping("/nantong/batchZs/{zslx}/{key}")
    String printNtBatchZsByRedisKey(@PathVariable(value = "zslx") String zslx, @PathVariable(value = "key") String key) {
        List<Integer> qsztList = new ArrayList(1);
        List<String> listZsid = bdcZsPirntFeignService.getZsidsByKey(key);

        qsztList.add(CommonConstantUtils.QSZT_VALID);
        //List<String> listZsid = Arrays.asList(StringUtils.split(zsids, CommonConstantUtils.ZF_YW_DH));
        // 只获取权属状态为现势的证书打印
        BdcZsQO bdcZsQO = new BdcZsQO();
        bdcZsQO.setZsidList(listZsid);
        bdcZsQO.setQsztList(qsztList);

        //  南通证书类型不一样，需要单独判断 modified by zhuyong
        if (StringUtils.equals(CommonConstantUtils.DYLX_ZM, zslx)) {
            // 证明
            bdcZsQO.setZslx(CommonConstantUtils.ZSLX_ZM);
        } else if (StringUtils.equals(CommonConstantUtils.DYLX_SCZMD, zslx)) {
            // 证明单
            bdcZsQO.setZslx(CommonConstantUtils.ZSLX_ZMD);
        } else {
            // 证书
            bdcZsQO.setZslx(CommonConstantUtils.ZSLX_ZS);
        }

        List<String> printZsidList = bdcZsFeignService.queryZsid(bdcZsQO);

        // 封装打印参数
        BdcPrintDTO bdcPrintDTO = new BdcPrintDTO();
        bdcPrintDTO.setBdcUrlDTO(this.bdcUrl());
        bdcPrintDTO.setDylx(zslx);
        bdcPrintDTO.setListZsid(printZsidList);
        return bdcZsPirntFeignService.batchZsPrintXml(bdcPrintDTO);
    }
    /**
     * @param key  证书ID字符串

     * @return String 证书打印xml
     * @author <a href="mailto:wutao@gtmap.cn">zhangwentao</a>
     * @description 获取证书批量打印xml
     */

    @GetMapping("/batchZmdDy/{key}")
    String printBatchZmdByRedisKey(@PathVariable(value = "key") String key) {

        List<String> listZsid = bdcZsPirntFeignService.getZsidsByKey(key);
        List<Integer> qsztList = new ArrayList(1);
        qsztList.add(CommonConstantUtils.QSZT_VALID);
        // 只获取权属状态为现势的证书打印
        BdcZsQO bdcZsQO = new BdcZsQO();
        bdcZsQO.setZsidList(listZsid);
        bdcZsQO.setQsztList(qsztList);
        List<String> printZsidList = bdcZsFeignService.queryZsid(bdcZsQO);

        // 封装参数
        Map<String, List<Map>> paramMap = new HashMap<>(1);
        if(CollectionUtils.isNotEmpty(printZsidList) && printZsidList.size()>0){
            List<Map> zsListMap = new ArrayList<>(1);
            for(String zsid : printZsidList){
                Map<String, Object> map = new HashMap<>(1);
                map.put("zsid", zsid);
                zsListMap.add(map);
            }
            paramMap.put("plzmddy", zsListMap);
        }
        return bdcPrintFeignService.print(paramMap);
    }

    /**
     * @param bdcPrintDTO 打印对象DTO
     * @return String 文件路径信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取发证记录pdf生成的路径
     */
    @PostMapping("/fzjl/pdf/path")
    public String fzjlPdfFilePath(@RequestBody BdcPrintDTO bdcPrintDTO) {
        if (StringUtils.isBlank(bdcPrintDTO.getDylx())) {
            throw new MissingArgumentException("发证记录获取pdf路径异常，缺失dylx参数！");
        }
        String fzjlXml = this.fzjlPrintXml(bdcPrintDTO.getGzlslid(), bdcPrintDTO.getXmid(), bdcPrintDTO.getDylx(), bdcPrintDTO.getZsid());
        if (StringUtils.isBlank(fzjlXml)) {
            throw new MissingArgumentException("发证记录获取pdf路径异常，未获取到打印xml信息！");
        }
        OfficeExportDTO pdfExportDTO = new OfficeExportDTO();
        pdfExportDTO.setModelName(printPath + "fzjl.docx");
        pdfExportDTO.setFileName("fzjl" + bdcPrintDTO.getXmid());
        pdfExportDTO.setXmlData(fzjlXml);
        return pdfController.generatePdfFile(pdfExportDTO);
    }

    /**
     * @param bdcQzxxDO 签字信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取评价器的签名图片
     */
    @GetMapping(value = "/pjqqm")
    public void getPjqqm(BdcQzxxDO bdcQzxxDO, HttpServletResponse response) {
        LOGGER.debug("bdcQzxxDO：{}", bdcQzxxDO);
        String imageStr = "";
        List<BdcQzxxDO> bdcQzxxDOS = qzxxFeginService.queryBdcQzxx(bdcQzxxDO);
        if (CollectionUtils.isNotEmpty(bdcQzxxDOS)) {
            //从大云获取签字信息图片
            BaseResultDto baseResultDto = storageClient.downloadBase64(bdcQzxxDOS.get(0).getFid());
            if (Objects.nonNull(baseResultDto)) {
                LOGGER.info("xmid{}，表单类型{}，签字人类别{}，签名图片是否获取成功0代表成功{}", bdcQzxxDO.getXmid(),
                        bdcQzxxDO.getBdlx(), bdcQzxxDO.getQzrlb(), baseResultDto.getCode());
                Base64Utils.changeBase64ToImage(baseResultDto.getMsg(), "fzjlQm.jpg", "jpg", response);
            }
        }
    }

    /**
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 首次证明单批量导出一个PDF文件
     */
    @GetMapping("/sczmd/pdf")
    public void exportSczmdPdf(HttpServletResponse response, @RequestParam("gzlslid") String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("证明单导出pdf中止，原因：没有工作流实例ID参数！");
        }

        // 获取首次证明单的打印内容数据，数据来源直接根据fr3打印来
        String xmlData = this.printAllZs("sczmd", gzlslid,"sczmd");
        if (StringUtils.isBlank(xmlData)) {
            throw new MissingArgumentException("证明单导出pdf中止，原因：未获取到！");
        }

        OfficeExportDTO pdfExportDTO = new OfficeExportDTO();
        pdfExportDTO.setModelName(printPath + "sczmd.docx");
        pdfExportDTO.setFileName("首次证明单");
        pdfExportDTO.setXmlData(xmlData);
        pdfController.exportPdf(response, pdfExportDTO);
    }

    /**
     * @param zsid 证书ID
     * @param dylx 打印类型
     * @return 打印xml信息
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 打印证书上的图像信息（宗地图/户室图等）
     */
    @GetMapping(value = {"/zs/image/{dylx}/{zsid}/{bdcdyh}","/zs/image/{dylx}/{zsid}"})
    public String printZdtView(@PathVariable(name = "zsid" ,required = false ) String zsid, @PathVariable(name = "dylx") String dylx,@PathVariable(name = "bdcdyh" ,required = false ) String bdcdyh) {
        String param1 = "";
        if(StringUtils.isBlank(bdcdyh)) {
            BdcZsDO bdcZsDO = bdcZsFeignService.queryBdcZs(zsid);
            if (StringUtils.isBlank(bdcZsDO.getBdcdyh())) {
                throw new MissingArgumentException("zsid");
            }
            param1 = bdcZsDO.getBdcdyh();
        }else{
            param1 = bdcdyh;
        }
        Map<String, String> param = Maps.newHashMap();
        List<BdcDysjDTO> bdcDysjDTOList = Lists.newArrayListWithCapacity(2);
        BdcDysjDTO bdcDysjDTO = new BdcDysjDTO();

        if (StringUtils.equals(Constants.ZDT, dylx)) {
            param.put("ZDT", StringUtils.replace(registerUiUrl + CommonConstantUtils.ZDT_URL, "{bdcdyh}", param1));
            bdcDysjDTO.setDyzd(Constants.ZDT_DY_XML);
        } else if (StringUtils.equals(Constants.HST, dylx)) {
            param.put("HST", StringUtils.replace(registerUiUrl + CommonConstantUtils.HST_URL, "{bdcdyh}",param1));
            bdcDysjDTO.setDyzd(Constants.HST_DY_XML);
        }
        bdcDysjDTO.setDysj(JSONObject.toJSONString(param));
        bdcDysjDTO.setDyzbsj(JSONObject.toJSONString(new HashMap<String, String>()));
        bdcDysjDTOList.add(bdcDysjDTO);
        return bdcPrintFeignService.printDatas(bdcDysjDTOList);
    }

    /**
     * 同时打印宗地图户室图
     * @param bdcdyh 不动产单元号
     * @return {String} 打印XML数据
     * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @GetMapping(value = "/zs/image/zdthst/{bdcdyh}")
    public String printZdtView(@PathVariable(name = "bdcdyh") String bdcdyh) {
        if (StringUtils.isBlank(bdcdyh)) {
            throw new MissingArgumentException("打印宗地图户时图缺失参数bdcdyh");
        }
        Map<String, String> param = Maps.newHashMap();
        List<BdcDysjDTO> bdcDysjDTOList = Lists.newArrayListWithCapacity(2);
        BdcDysjDTO bdcDysjDTO = new BdcDysjDTO();

        param.put("ZDT", StringUtils.replace(registerUiUrl + CommonConstantUtils.ZDT_URL, "{bdcdyh}", bdcdyh));
        param.put("HST", StringUtils.replace(registerUiUrl + CommonConstantUtils.HST_URL, "{bdcdyh}", bdcdyh));
        bdcDysjDTO.setDyzd(Constants.ZDTHST_DY_XML);

        bdcDysjDTO.setDysj(JSONObject.toJSONString(param));
        bdcDysjDTO.setDyzbsj(JSONObject.toJSONString(new HashMap<String, String>()));
        bdcDysjDTOList.add(bdcDysjDTO);
        return bdcPrintFeignService.printDatas(bdcDysjDTOList);
    }

    /**
     * @param processInsId 工作流实例id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 推送抵押审批表到第三方系统
     * @date : 2020/7/30 15:26
     */
    @GetMapping(value = "/pdf/dyaspb/tsdsf/{processInsId}")
    public void tsDyaSpbToDsf(@PathVariable(name = "processInsId") String processInsId) {
        if (StringUtils.isBlank(processInsId)) {
            throw new AppException("推送抵押审批表缺失工作流实例id");
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(processInsId);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmDOList) && StringUtils.isNotBlank(bdcXmDOList.get(0).getSpxtywh())) {
            //生成审批表xml数据
            BdcPrintDTO bdcPrintDTO = new BdcPrintDTO();
            bdcPrintDTO.setDylx(CommonConstantUtils.DYLX_DYASPB);
            bdcPrintDTO.setGzlslid(processInsId);
            bdcPrintDTO.setZxlc(false);
            List<String> jdmcList = new ArrayList(4);
            List<UserTaskDto> userTaskDataList = workFlowUtils.listShjdxx(processInsId);
            userTaskDataList.forEach(taskData -> {
                jdmcList.add(taskData.getName());
            });
            bdcPrintDTO.setJdmcList(jdmcList);
            bdcPrintDTO.setDjxlListStr(StringUtils.join(bdcShxxFeignService.getShxxDylx(processInsId).get(CommonConstantUtils.DYLX_DYASPB), CommonConstantUtils.ZF_YW_DH));
            bdcPrintDTO.setBdcUrlDTO(this.bdcUrl());
            String xml = bdcShxxRestService.bdPrintXmlNantong(bdcPrintDTO);
            LOGGER.warn("生成的xml文件信息为{}", xml);
            // 生成pdf文件路径
            OfficeExportDTO pdfExportDTO = new OfficeExportDTO();
            pdfExportDTO.setModelName(printPath + CommonConstantUtils.DYLX_DYASPB + ".docx");
            pdfExportDTO.setFileName(CommonConstantUtils.WJMC_DYASPB);
            pdfExportDTO.setXmlData(xml);
            String pdfPath = pdfController.generatePdfFile(pdfExportDTO);
            LOGGER.info("需要推送的抵押审批表工作流实例id为{},---- 路径为{}", processInsId, pdfPath);
            if (StringUtils.isNotBlank(pdfPath)) {
                //组织base64 信息
                String pdfBase64 = Base64Utils.getPDFBinary(new File(pdfPath));
                if (StringUtils.isNotBlank(pdfBase64)) {
                    DyaSpbDTO dyaSpbDTO = new DyaSpbDTO();
                    //根据工作流实例id判断流程类型
                    int lclx = bdcXmFeignService.makeSureBdcXmLx(processInsId);
                    //普通或者单个组合流程需要传入单元号和单元唯一编号
                    if (Objects.equals(CommonConstantUtils.LCLX_PT, lclx) || Objects.equals(CommonConstantUtils.LCLX_ZH, lclx)) {
                        dyaSpbDTO.setBdcdyh(bdcXmDOList.get(0).getBdcdyh());
                        dyaSpbDTO.setBdcdywybh(bdcXmDOList.get(0).getBdcdywybh());
                    }
                    dyaSpbDTO.setSlbh(bdcXmDOList.get(0).getSlbh());
                    dyaSpbDTO.setFjmc(CommonConstantUtils.WJMC_DYASPB);
                    dyaSpbDTO.setFjnr(pdfBase64);
                    dyaSpbDTO.setGzlslid(processInsId);
                    dyaSpbDTO.setSpxtywh(bdcXmDOList.get(0).getSpxtywh());
                    Map<String, Object> param = new HashMap<>(1);
                    param.put("dyaSpbDTO", dyaSpbDTO);
                    Object response = exchangeInterfaceFeignService.requestInterface("noticeWwsqSpb", param);
                    if (Objects.nonNull(response)) {
                        LOGGER.info("推送抵押审批表返回信息{}", response.toString());
                        Map<String, String> resMap = (Map<String, String>) response;
                        LOGGER.info("推送抵押审批表返回状态信息{}", TsDyaSpbResponseEnum.getMessage(resMap.get("returncode")));
                    }
                }
            }
        }
    }


    /**
     * @param gzlslid 工作流实例id
     * @param zsid 证书id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2020/8/24 16:05
     */

    @GetMapping(value = "/jtcyfb/{gzlslid}/{zsid}/xml")
    public Object printJtcyFb(@PathVariable(value = "gzlslid") String gzlslid,@PathVariable(value = "zsid") String zsid) {
        if(StringUtils.isAnyBlank(gzlslid,zsid)) {
            throw new AppException("查询证书项目关系缺失参数");
        }
        List<BdcXmDO> bdcXmDOList = bdcZsFeignService.queryZsXmByZsid(zsid);
        Map<String, List<Map>> paramMap = new HashMap<>(1);
        List<Map> jtcyMapList = new ArrayList<>(1);
        if(CollectionUtils.isNotEmpty(bdcXmDOList)) {
            //根据xmid 找到权利人数量，即承包方数量
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
            bdcQlrQO.setXmid(bdcXmDOList.get(0).getXmid());
            List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
            if(CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                for(BdcQlrDO bdcQlrDO : bdcQlrDOList) {
                    Map jtcyMap = new HashMap(1);
                    jtcyMap.put("xmid",bdcXmDOList.get(0).getXmid());
                    jtcyMap.put("qlrid",bdcQlrDO.getQlrid());
                    jtcyMapList.add(jtcyMap);
                }
            }
        }
        paramMap.put("jtcyfb",jtcyMapList);
        return bdcPrintFeignService.print(paramMap);
    }

    /**
     * @param gzlslid
     * @param xmid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2020/8/25 11:20
     */

    @GetMapping(value = "/jtcyfb/{gzlslid}/xml/yl")
    public Object jtcyFbYl(@PathVariable(value = "gzlslid") String gzlslid, String xmid) {
        if(StringUtils.isBlank(gzlslid)) {
            throw new AppException("查询证书项目信息缺失工作流实例id");
        }
        if(StringUtils.isBlank(xmid)) {
            List<BdcXmDTO> bdcXmDOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
            if(CollectionUtils.isNotEmpty(bdcXmDOList)) {
                xmid = bdcXmDOList.get(0).getXmid();
            }
        }
        if(StringUtils.isNotBlank(xmid)) {
            Map<String, List<Map>> paramMap = new HashMap<>(1);
            List<Map> jtcyMapList = new ArrayList<>(1);
            //根据xmid 找到权利人数量，即承包方数量
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
            bdcQlrQO.setXmid(xmid);
            List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
            if(CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                for(BdcQlrDO bdcQlrDO : bdcQlrDOList) {
                    Map jtcyMap = new HashMap(1);
                    jtcyMap.put("xmid", xmid);
                    jtcyMap.put("qlrid", bdcQlrDO.getQlrid());
                    jtcyMapList.add(jtcyMap);
                }
            }
            paramMap.put("jtcyfb", jtcyMapList);
            return bdcPrintFeignService.print(paramMap);
        }
        return null;
    }

    @GetMapping(value = "/ewm")
    public void queryZsEwm(@RequestParam("ewmnr") String ewmnr, HttpServletResponse httpServletResponse) {
        this.ewm(ewmnr, httpServletResponse);
    }

    /**
     * @param gzlslids
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 档案目录打印
     * @date : 2021/6/16 17:14
     */
    @GetMapping("/dajj/{dylx}/{gzlslids}")
    public String printDaml(@PathVariable(value = "dylx") String dylx, @PathVariable(value = "gzlslids") String gzlslids) {
        logger.info("档案目录打印,dylx：{},gzlslids:{}", dylx,gzlslids);
        String[] gzlslidArray = gzlslids.split(CommonConstantUtils.ZF_YW_DH);
        List<BdcXmDO> printXmList = new ArrayList<>(10);
        // 根据工作流实例id 获取项目数据
        Map<String, List<BdcXmDO>> bdcxmListMap = this.getBdcXmxxMap(gzlslidArray);
        bdcxmListMap.values().forEach(printXmList::addAll);
        Map<String, String> gzlslidMap = new HashMap<>(bdcxmListMap.size());
        //把当前的gzlslid 和查询到的项目的gzlslid 做个关联关系
        for (String key : bdcxmListMap.keySet()) {
            List<BdcXmDO> bdcXmDOList = bdcxmListMap.get(key);
            gzlslidMap.put(bdcXmDOList.get(0).getGzlslid(), key);
        }
        if (CollectionUtils.isNotEmpty(printXmList)) {
            List<Map> qlxzMapList = bdcZdFeignService.queryBdcZd("qlxz");
            List<Map> sjlxMapList = bdcZdFeignService.queryBdcZd("sjlx");
            BdcDysjPzDO bdcDysjPzDO = new BdcDysjPzDO();
            bdcDysjPzDO.setDylx(dylx);
            List<BdcDysjPzDO> bdcDysjPzDOList = bdcDypzFeignService.listBdcDysjPz(bdcDysjPzDO);
            if (CollectionUtils.isEmpty(bdcDysjPzDOList)) {
                throw new AppException("未获取到相应的打印数据源配置");
            }
            List<BdcDysjDTO> bdcDysjDTOList = new ArrayList<>(1);
            List<BdcXmDO> printXmListDuplicate = printXmList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() ->
                    new TreeSet<>(Comparator.comparing(BdcXmDO::getGzlslid))), ArrayList::new));
            List<BdcXmDO> printList = listPrintXm(printXmListDuplicate, dylx);
            logger.info("档案目录打印,printList：{}", printList);
            for (BdcXmDO bdcXmDO : printList) {
                int lclx = bdcXmFeignService.makeSureBdcXmLx(bdcXmDO.getGzlslid());
                boolean sfpllc = false;
                if (Objects.equals(CommonConstantUtils.LCLX_PL, lclx)) {
                    sfpllc = true;
                }
                Pair<Map, Multimap> dypz = this.getDamlDysjMap(bdcXmDO, qlxzMapList, sjlxMapList, true, dylx, sfpllc, gzlslidMap);
                BdcDysjDTO bdcDysjDTO = new BdcDysjDTO();
                logger.info("当前档案目录打印类型{}", dylx);
                bdcDysjDTO.setDylx(dylx);
                bdcDysjDTO.setDyzd(bdcDysjPzDOList.get(0).getDyzd());
                bdcDysjDTO.setDysj(JSONObject.toJSONString(dypz.getLeft()));
                bdcDysjDTO.setDyzbsj(JSONObject.toJSONString(dypz.getRight()));
                bdcDysjDTOList.add(bdcDysjDTO);
            }
            String xml = bdcPrintFeignService.printDatas(bdcDysjDTOList);
            Map<String, Object> map = new HashMap<>();
            UserDto userDto = userManagerUtils.getCurrentUser();
            map.put(CommonConstantUtils.ALIAS, userDto != null ? userDto.getAlias() : userManagerUtils.getCurrentUserName());
            map.put(CommonConstantUtils.GZLSLID,gzlslids);
            map.put(Constants.DAJJ_DYLX,Constants.DAJJ_DYLX_ML);
            map.put("XML",xml);
            AuditEvent auditEvent = new AuditEvent(userManagerUtils.getCurrentUserName(), Constants.DAJJ_DY, map);
            zipkinAuditEventRepository.add(auditEvent);
            LOGGER.info("打印类型{}++++数据源xml////{}", dylx, xml);
            return xml;
        }
        return "";
    }

    /**
     * @param
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 档案目录封面打印
     * @date : 2021/11/29 17:14
     */
    @GetMapping("/dajj/dafm/{dylx}/{gzlslids}")
    public String printDafm(@PathVariable(value = "dylx") String dylx, @PathVariable(value = "gzlslids") String gzlslids) {
        if (StringUtils.isBlank(gzlslids)) {
            throw new AppException(ErrorCode.MISSING_ARG, "缺少工作流实例ID");
        }
        String[] gzlslidArray = gzlslids.split(CommonConstantUtils.ZF_YW_DH);
        List<BdcXmDO> printXmList = new ArrayList<>(10);
        Map<String, List<BdcXmDO>> bdcxmListMap = this.getBdcXmxxMap(gzlslidArray);
        bdcxmListMap.values().forEach(printXmList::addAll);
        Map<String, String> gzlslidMap = new HashMap<>(bdcxmListMap.size());
        //把当前的gzlslid 和查询到的项目的gzlslid 做个关联关系
        for (String key : bdcxmListMap.keySet()) {
            List<BdcXmDO> bdcXmDOList = bdcxmListMap.get(key);
            gzlslidMap.put(bdcXmDOList.get(0).getGzlslid(), key);
        }
        if (CollectionUtils.isNotEmpty(printXmList)) {
            List<BdcXmDO> printXmListDuplicate = printXmList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() ->
                    new TreeSet<>(Comparator.comparing(t -> t.getGzlslid()))), ArrayList::new));
            for (BdcXmDO bdcXmDO : printXmListDuplicate) {
                List<BdcZsDO> bdcZsDOList = bdcZsFeignService.queryBdcZsByXmid(bdcXmDO.getXmid());
                BdcZsDO bdcZsDO = new BdcZsDO();
                if (CollectionUtils.isEmpty(bdcZsDOList)) {
                    //如果当前流程证书数据为空，取上一手的证书信息
                    BdcZsQO bdcZsQO = new BdcZsQO();
                    bdcZsQO.setXmid(bdcXmDO.getXmid());
                    List<BdcZsDO> yxmZsList = bdcZsFeignService.queryYxmZs(bdcZsQO);
                    if (CollectionUtils.isNotEmpty(yxmZsList)) {
                        bdcZsDO = yxmZsList.get(0);
                    } else {
                        LOGGER.error("当前打印档案交接项目id{}未找到证书信息", bdcXmDO.getXmid());
                    }
                } else {
                    bdcZsDO = bdcZsDOList.get(0);
                }
                final String bdcqzhjc = bdcZsDO.getBdcqzhjc();
                BdcXmQO bdcXmQO = new BdcXmQO();
                bdcXmQO.setGzlslid(bdcXmDO.getGzlslid());
                List<BdcXmDO> bdcXmDOS = this.bdcXmFeignService.listBdcXm(bdcXmQO);
                Map<String, BdcXmDO> bdcXmDOMap = bdcXmDOS.stream().sorted(Comparator.comparing(BdcXmDO::getXmid))
                        .collect(Collectors.toMap(BdcXmDO::getDjxl, t-> t,(key1,key2)->key1));
                AtomicInteger i = new AtomicInteger(1);
                bdcXmDOMap.forEach((k, v) -> {
                    //档案号生成
                    getDah(v, v.getQllx(), bdcqzhjc, gzlslidMap);
                });
            }
        }
        // 封装参数
        Map<String, List<Map>> paramMap = new HashMap<>(1);
        List<Map> bdcdyhListMap = new ArrayList<>(1);
        Map<String, Object> map = new HashMap<>(1);
        for(String gzlslid : gzlslidArray){
            map.put("gzlslid", gzlslid);
            bdcdyhListMap.add(map);
        }
        paramMap.put(dylx, bdcdyhListMap);
        Map<String, Object> map1 = new HashMap<>();
        UserDto userDto = userManagerUtils.getCurrentUser();
        map.put(CommonConstantUtils.ALIAS, userDto != null ? userDto.getAlias() : userManagerUtils.getCurrentUserName());
        map.put(CommonConstantUtils.GZLSLID,gzlslids);
        map.put(Constants.DAJJ_DYLX, Constants.DAJJ_DYLX_FM);
        AuditEvent auditEvent = new AuditEvent(userManagerUtils.getCurrentUserName(), Constants.DAJJ_DY, map1);
        zipkinAuditEventRepository.add(auditEvent);
        return bdcPrintFeignService.print(paramMap);
    }

    /**
     * 获取档案目录打印数据
     * Pair.getLeft 返回主表打印数据
     * Pair.getRight 返回子表打印数据
     */
    private Pair<Map, Multimap> getDamlDysjMap(BdcXmDO bdcXmDO, List<Map> qlxzMapList, List<Map> sjlxMapList, boolean sfhb, String dylx, boolean sfpllc, Map<String, String> gzlslidMap) {
        String xmid = "";
        boolean sfpldy = pldy(bdcXmDO, dylx);
        if (sfpldy) {
            xmid = bdcXmDO.getXmid();
            //如果之前是批量流程且要批量打印，此处修改为false
            if (sfpllc) {
                sfpllc = false;
            }
        }
        // 打印主表数据
        Map<String, String> damlMainMap = new HashMap(2);
        String qlxz = StringToolUtils.convertBeanPropertyValueOfZdByString(bdcXmDO.getQlxz(), qlxzMapList);
        Map xmMap = Object2MapUtils.object2MapExceptNull(bdcXmDO);
        damlMainMap.putAll(xmMap);
        damlMainMap.put("qlxz", Optional.ofNullable(qlxz).orElse(""));
        damlMainMap.put("gzlslid", bdcXmDO.getGzlslid());
        //当前档案交接打印 获取的是生成档案交接流程的 证书数据
        List<BdcZsDO> bdcZsDOList = bdcZsFeignService.queryBdcZsByXmid(bdcXmDO.getXmid());
        BdcZsDO bdcZsDO = new BdcZsDO();
        if (CollectionUtils.isEmpty(bdcZsDOList)) {
            //如果当前流程证书数据为空，取上一手的证书信息
            BdcZsQO bdcZsQO = new BdcZsQO();
            bdcZsQO.setXmid(bdcXmDO.getXmid());
            List<BdcZsDO> yxmZsList = bdcZsFeignService.queryYxmZs(bdcZsQO);
            if (CollectionUtils.isNotEmpty(yxmZsList)) {
                bdcZsDO = yxmZsList.get(0);
            } else {
                LOGGER.error("当前打印档案交接项目id{}未找到证书信息", bdcXmDO.getXmid());
            }
        } else {
            bdcZsDO = bdcZsDOList.get(0);
        }
        Multimap<String, List> zbMap = ArrayListMultimap.create();
        final String bdcqzhjc = bdcZsDO.getBdcqzhjc();
        final Map<String, String> lcslidFinalMap = gzlslidMap;
        if (sfhb) {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setGzlslid(bdcXmDO.getGzlslid());
            if(CollectionUtils.isNotEmpty(gydahgzldyids) && !gydahgzldyids.contains(bdcXmDO.getGzldyid())){
                if (StringUtils.isNotBlank(xmid)) {
                    bdcXmQO.setXmid(xmid);
                }
            }
            List<BdcXmDO> bdcXmDOS = this.bdcXmFeignService.listBdcXm(bdcXmQO);
            Map<String, BdcXmDO> bdcXmDOMap = bdcXmDOS.stream().sorted(Comparator.comparing(BdcXmDO::getXmid))
                    .collect(Collectors.toMap(BdcXmDO::getDjxl, t -> t, (key1, key2) -> key1));
            AtomicInteger i = new AtomicInteger(1);
            boolean finalSfpllc = sfpllc;
            bdcXmDOMap.forEach((k, v) -> {
                //档案号生成
                damlMainMap.put("dah" + i, getDah(v, v.getQllx(), bdcqzhjc, lcslidFinalMap));
                damlMainMap.put("qlr" + i, v.getQlr());
                damlMainMap.put("zl" + i, finalSfpllc ? v.getZl() + "等" : v.getZl());
                damlMainMap.put("ywr" + i, v.getYwr());
                damlMainMap.put("qlxz" + i, Optional.ofNullable(StringToolUtils.convertBeanPropertyValueOfZdByString(v.getQlxz(), qlxzMapList)).orElse(""));
                zbMap.put("bdcsjcl" + i + damlTableConfig, querySlSjclxx(v.getGzlslid(), v.getDjxl(), sjlxMapList, dylx));
                if(sfWjPlxflc(bdcXmDO)){
                    BdcXmQO cfXmQO = new BdcXmQO();
                    cfXmQO.setGzlslid(bdcXmDO.getGzlslid());
                    List<BdcXmDO> cfXmList = this.bdcXmFeignService.listBdcXm(cfXmQO);
                    List<BdcQlrDO> qlrDOList = new ArrayList<>(2);
                    List<BdcQlrDO> ywrDOList = new ArrayList<>(2);
                    for (BdcXmDO cfBdcXmDO : cfXmList) {
                        BdcQlrQO bdcQlrQO = new BdcQlrQO();
                        bdcQlrQO.setXmid(cfBdcXmDO.getXmid());
                        bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
                        List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
                        if(CollectionUtils.isNotEmpty(bdcQlrDOList)){
                            qlrDOList.addAll(bdcQlrDOList);
                        }
                        bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_YWR);
                        List<BdcQlrDO> bdcYwrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
                        if(CollectionUtils.isNotEmpty(bdcYwrDOList)){
                            ywrDOList.addAll(bdcYwrDOList);
                        }
                    }
                    String qlrStr = qlrDOList.stream().filter(bdcQlrDO -> StringUtils.isNotBlank(bdcQlrDO.getQlrmc())).map(BdcQlrDO::getQlrmc).collect(Collectors.joining(","));
                    String ywrStr = ywrDOList.stream().filter(bdcQlrDO -> StringUtils.isNotBlank(bdcQlrDO.getQlrmc())).map(BdcQlrDO::getQlrmc).collect(Collectors.joining(","));
                    String zlStr = cfXmList.stream().filter(bdcXmDO1 -> StringUtils.isNotBlank(bdcXmDO1.getZl())).map(BdcXmDO::getZl).collect(Collectors.joining(","));
                    damlMainMap.put("qlr" + i, qlrStr);
                    damlMainMap.put("zl" + i,zlStr);
                    damlMainMap.put("ywr" + i, ywrStr);
                }
                i.getAndIncrement();
            });
        }else{
            // 生成档案号
            damlMainMap.put("dah", getDah(bdcXmDO, bdcXmDO.getQllx(), bdcqzhjc, gzlslidMap));
            zbMap.put("bdcsjcl" + damlTableConfig, querySlSjclxx(bdcXmDO.getGzlslid(), bdcXmDO.getDjxl(), sjlxMapList, dylx));
        }
        damlMainMap.put("qlr", bdcXmDO.getQlr());
        damlMainMap.put("zl", sfpllc ? bdcXmDO.getZl()+"等" : bdcXmDO.getZl());
        logger.info("打印数据源参数主表{}------子表{}", damlMainMap, zbMap);
        return Pair.of(damlMainMap, zbMap);
    }

    /**
     * @param
     * @return 档案号
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @date 2022/3/4 11:05
     * @description 获取档案号方法
     **/
    @RedissonLock(lockKey = Constants.DAH_REDIS_LOCK_NAME, description = "获取档案号", waitTime = 60L, leaseTime = 30L)
    public String getDah(BdcXmDO bdcXmDO, Integer qllx, String bdcqzhjc, Map<String, String> gzlslidMap) {
        String dah  = getGyDah(bdcXmDO);
        if(StringUtils.isNotBlank(dah)){
            return dah;
        }
        String dahPrefix = "";
        String qzh = dajjDahPrefix2Config.getQzh();
        String mlh = "";
        if (MapUtils.isNotEmpty(dajjDahPrefixConfig.getQllxMap())) {
            dahPrefix = dajjDahPrefixConfig.getQllxMap().get(qllx);
        }

        boolean fdjywyysysDah = checkYysysDahlc(MapUtils.getString(gzlslidMap, bdcXmDO.getGzlslid(), ""));
        if (StringUtils.contains(dajjTsqh, bdcXmDO.getQxdm()) && MapUtils.isNotEmpty(dajjDahPrefix2Config.getConfigMap())) {
            logger.info("受理编号{}区划{}采用特殊档案号处理,登记小类{}", bdcXmDO.getSlbh(), dajjTsqh, bdcXmDO.getDjxl());
            // 已存在档案号的不重新生成
            BdcGdxxFphhQO bdcGdxxFphhQO = new BdcGdxxFphhQO();
            bdcGdxxFphhQO.setXmid(bdcXmDO.getXmid());
            List<BdcGdxxFphhDO> bdcGdxxFphhDOList = bdcGdxxFphhFeignService.getBdcGdxxFphhDOList(bdcGdxxFphhQO);
            if(CollectionUtils.isNotEmpty(bdcGdxxFphhDOList)){
                dah = bdcGdxxFphhDOList.get(0).getAjh();
                return dah;
            }
            // 特殊档案号处理
            for(Map.Entry<Integer, String> config : dajjDahPrefix2Config.getConfigMap().entrySet()) {
                if(StringUtils.contains(config.getValue(), bdcXmDO.getDjxl())) {
                    logger.info("特殊档案号处理， config.getKey : {},  mlhConfig: {}", config.getKey(), JSON.toJSONString(dajjDahPrefix2Config.getMlhconfigMap()));
                    dah = getTsdah(bdcXmDO, config.getKey());
                    if(MapUtils.isNotEmpty(dajjDahPrefix2Config.getMlhconfigMap()) && Objects.nonNull(config.getKey())){
                        mlh = dajjDahPrefix2Config.getMlhconfigMap().get(config.getKey());
                    }
                    // 档案号为空不保存
                    if(StringUtils.isNotBlank(dah)){
                        this.saveDah(bdcXmDO, dah, "", new Date(),qzh,mlh,null);
                    }
                    return dah;
                }
            }
        }

        if(StringUtils.contains(dajjJtqh, bdcXmDO.getQxdm()) && (CollectionUtils.isNotEmpty(dajjDahPrefixJtConfig.getQzlql())||CollectionUtils.isNotEmpty(dajjDahPrefixJtConfig.getJtzmlql()))){
            logger.info("金坛受理编号{},档案号处理, dajjDahPrefixJtConfig: {},", bdcXmDO.getSlbh(), JSON.toJSONString(dajjDahPrefixJtConfig));
            dah = getJtDah(bdcXmDO,qllx);
            return dah;
        }

        if(StringUtils.contains(dajjLyConfig.getLyqh(), bdcXmDO.getQxdm()) && (CollectionUtils.isNotEmpty(dajjLyConfig.getLycqlql())||CollectionUtils.isNotEmpty(dajjLyConfig.getLyzmlql()))){
            logger.info("溧阳受理编号{},档案号特殊处理, dajjLyConfig: {}", bdcXmDO.getSlbh() , JSON.toJSONString(dajjLyConfig));
            int lclx = bdcXmFeignService.makeSureBdcXmLx(bdcXmDO.getGzlslid());
            logger.info("特殊处理的项目信息: {},流程类型：{}", JSON.toJSONString(bdcXmDO),lclx);
            if (Objects.equals(CommonConstantUtils.LCLX_ZH, lclx)) {
                dah = getLyZhlcDah(bdcXmDO, qllx);
            } else {
                dah = getLyDah(bdcXmDO, qllx);
            }
            return dah;
        }
        logger.info("受理编号{}区划{}采用普通规则生成档案号", bdcXmDO.getSlbh(), bdcXmDO.getQxdm());
        dah = dahPrefix + (StringUtils.isNotBlank(bdcqzhjc) ? bdcqzhjc : StringUtils.EMPTY);
        String dahlx = "";
        if (fdjywyysysDah) {
            //非登记业务沿用上一手档案号 此时项目信息已经是上一手的bdc_xm，不需要再次查询历史关系
            BdcGdxxFphhQO bdcGdxxFphhQO = new BdcGdxxFphhQO();
            bdcGdxxFphhQO.setXmid(bdcXmDO.getXmid());
            List<BdcGdxxFphhDO> bdcGdxxFphhDOList = bdcGdxxFphhFeignService.getBdcGdxxFphhDOList(bdcGdxxFphhQO);
            if (CollectionUtils.isNotEmpty(bdcGdxxFphhDOList)) {
                BdcGdxxFphhDO bdcGdxxFphhDO = bdcGdxxFphhDOList.get(0);
                dah = bdcGdxxFphhDO.getAjh();
            } else {
                dah = "";
            }
            logger.info("受理编号{}，项目ID{}，当前属于非登记业务沿用上一手的档案号{}", bdcXmDO.getSlbh(), bdcXmDO.getXmid(), dah);

            //档案号在Bdc_Gdxx_Fphh表里没有时才保存
            List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmFeignService.listBdcSlXmByGzlslid(MapUtils.getString(gzlslidMap, bdcXmDO.getGzlslid(), ""));
            if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                bdcGdxxFphhQO.setXmid(bdcSlXmDOList.get(0).getXmid());
                bdcGdxxFphhDOList = bdcGdxxFphhFeignService.getBdcGdxxFphhDOList(bdcGdxxFphhQO);
                if (CollectionUtils.isEmpty(bdcGdxxFphhDOList)) {
                    this.saveFdjywDah(bdcSlXmDOList.get(0), dah, "YYZSL");
                }
            }
        }
        //查封、接轨登记沿用选择产权的档案号
        if (StringUtils.contains(yyzsl, bdcXmDO.getDjxl())) {
            //如果权利类型是查封
            if(CommonConstantUtils.QLLX_CF.equals(bdcXmDO.getQllx())){
                List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listPrevCqXm(bdcXmDO.getXmid(), true);
                if(CollectionUtils.isNotEmpty(bdcXmDOS)){
                    BdcXmDO ycqBdcxm = bdcXmDOS.get(0);
                    BdcGdxxFphhQO bdcGdxxFphhQO = new BdcGdxxFphhQO();
                    bdcGdxxFphhQO.setXmid(ycqBdcxm.getXmid());
                    List<BdcGdxxFphhDO> bdcGdxxFphhDOList = bdcGdxxFphhFeignService.getBdcGdxxFphhDOList(bdcGdxxFphhQO);
                    if(CollectionUtils.isNotEmpty(bdcGdxxFphhDOList)){
                        BdcGdxxFphhDO bdcGdxxFphhDO = bdcGdxxFphhDOList.get(0);
                        dah = bdcGdxxFphhDO.getAjh();
                    }else{
                        dah = "";
                    }
                    logger.info("受理项目信息{}，受理编号{}，产权的项目ID{}，查封续封应沿用选择产权的档案号{}",bdcXmDO, bdcXmDO.getSlbh(),ycqBdcxm.getXmid(), dah);
                    //档案号在Bdc_Gdxx_Fphh表里没有时才保存
                    bdcGdxxFphhQO.setXmid(bdcXmDO.getXmid());
                    bdcGdxxFphhDOList = bdcGdxxFphhFeignService.getBdcGdxxFphhDOList(bdcGdxxFphhQO);
                    if(CollectionUtils.isEmpty(bdcGdxxFphhDOList)){
                        this.saveDah(bdcXmDO, dah, "YYZSL", new Date(),null,null,null);
                    }
                }
            }else{
                BdcXmLsgxQO bdcXmLsgxQO = new BdcXmLsgxQO();
                bdcXmLsgxQO.setXmid(bdcXmDO.getXmid());
                List<BdcXmLsgxDO> bdcXmLsgxDOS = bdcLsgxFeignService.listXmLsgxByXmid(bdcXmLsgxQO);
                if (CollectionUtils.isNotEmpty(bdcXmLsgxDOS)) {
                    String yxmid = bdcXmLsgxDOS.get(0).getYxmid();
                    BdcGdxxFphhQO bdcGdxxFphhQO = new BdcGdxxFphhQO();
                    bdcGdxxFphhQO.setXmid(yxmid);
                    List<BdcGdxxFphhDO> bdcGdxxFphhDOList = bdcGdxxFphhFeignService.getBdcGdxxFphhDOList(bdcGdxxFphhQO);
                    if(CollectionUtils.isNotEmpty(bdcGdxxFphhDOList)){
                        BdcGdxxFphhDO bdcGdxxFphhDO = bdcGdxxFphhDOList.get(0);
                        dah = bdcGdxxFphhDO.getAjh();
                    }else{
                        dah = "";
                    }
                    logger.info("受理项目信息{}，受理编号{}，原项目ID{}，应沿用选择产权的档案号{}",bdcXmDO, bdcXmDO.getSlbh(),yxmid, dah);

                    //档案号在Bdc_Gdxx_Fphh表里没有时才保存
                    bdcGdxxFphhQO.setXmid(bdcXmDO.getXmid());
                    bdcGdxxFphhDOList = bdcGdxxFphhFeignService.getBdcGdxxFphhDOList(bdcGdxxFphhQO);
                    if(CollectionUtils.isEmpty(bdcGdxxFphhDOList)){
                        this.saveDah(bdcXmDO, dah, "YYZSL", new Date(),null,null,null);
                    }
                }
            }
        }
        if(CollectionUtils.isNotEmpty(zslqlList) &&qllx != null &&zslqlList.contains(Integer.toString(qllx))){
            dahlx = Constants.BDC_DAHLX_ZS;
            dah = getDrlsh(dahlx,bdcXmDO);
        }
        if (CollectionUtils.isNotEmpty(zmlqlList) && qllx != null && zmlqlList.contains(Integer.toString(qllx))) {
            dahlx = Constants.BDC_DAHLX_ZM;
            dah = getDrlsh(dahlx, bdcXmDO);
        }
        logger.info("bdcXmDO:{}", bdcXmDO);
        logger.info("市本级档案号生成规则生成的档案号{},zmlprefix：{},zslprefix:{},getCurrDay:{},qllx:{}", dah, zmlprefix, zslprefix, DateUtils.getCurrDay(), qllx);
        // 保存档案号到数据库
        return dah;
    }

    private boolean checkYysysDahlc(String gzlslid) {
        //流程实例id查定义id
        if (StringUtils.isNotBlank(gzlslid)) {
            ProcessInstanceData processInstanceData = processInstanceClient.getProcessInstance(gzlslid);
            if (Objects.nonNull(processInstanceData)) {
                String gzldyid = processInstanceData.getProcessDefinitionKey();
                if (CollectionUtils.isNotEmpty(yysysGzldyidList) && yysysGzldyidList.contains(gzldyid)) {
                    LOGGER.warn("当前流程实例id{}查询到定义id{},配置了沿用上一手档案号", gzlslid, gzldyid);
                    return true;
                }

            }
        }
        return false;
    }

    private void saveDah(BdcXmDO bdcXmDO, String dah, String dahlx, Date dahscsj, String qzh, String mlh, Integer fmajh) {
        BdcGdxxFphhDO bdcGdxxFphhDO = new BdcGdxxFphhDO();
        bdcGdxxFphhDO.setXmid(bdcXmDO.getXmid());
        bdcGdxxFphhDO.setGzlslid(bdcXmDO.getGzlslid());
        bdcGdxxFphhDO.setSlbh(bdcXmDO.getSlbh());
        bdcGdxxFphhDO.setAjh(dah);
        bdcGdxxFphhDO.setQlr(bdcXmDO.getQlr());
        bdcGdxxFphhDO.setQzh(qzh);
        bdcGdxxFphhDO.setMlh(mlh);
        bdcGdxxFphhDO.setXzdm(bdcXmDO.getSsxz());
        List<Map> zdList = bdcSlZdFeignService.queryBdcSlzd("jddm");
        if(CollectionUtils.isNotEmpty(zdList)) {
            for(Map zd : zdList) {
                if(StringUtils.equals(bdcGdxxFphhDO.getXzdm(), (String) zd.get("DM"))) {
                    bdcGdxxFphhDO.setXzmc((String) zd.get("MC"));
                }
            }
        }
        bdcGdxxFphhDO.setNf(DateUtils.getCurrYear());
        bdcGdxxFphhDO.setDahscsj(dahscsj);
        bdcGdxxFphhDO.setDahlx(dahlx);
        if (fmajh != null) {
            bdcGdxxFphhDO.setFmajh(Integer.toString(fmajh));
        }
        logger.info("生成档案号信息：bdcGdxxFphhDO{}", bdcGdxxFphhDO);

        bdcGdxxFphhFeignService.saveDah(bdcGdxxFphhDO);
    }

    private void saveFdjywDah(BdcSlXmDO bdcSlXmDO, String dah, String dahlx) {
        BdcGdxxFphhDO bdcGdxxFphhDO = new BdcGdxxFphhDO();
        BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxFeignService.queryBdcSlJbxxByJbxxid(bdcSlXmDO.getJbxxid());
        if (Objects.nonNull(bdcSlJbxxDO)) {
            bdcGdxxFphhDO.setGzlslid(bdcSlJbxxDO.getGzlslid());
            bdcGdxxFphhDO.setSlbh(bdcSlJbxxDO.getSlbh());
        }
        bdcGdxxFphhDO.setXmid(bdcSlXmDO.getXmid());
        bdcGdxxFphhDO.setAjh(dah);
        bdcGdxxFphhDO.setQlr(bdcSlXmDO.getQlr());
        bdcGdxxFphhDO.setXzdm(bdcSlJbxxDO.getQxdm());
        List<Map> zdList = bdcSlZdFeignService.queryBdcSlzd("jddm");
        if (CollectionUtils.isNotEmpty(zdList)) {
            for (Map zd : zdList) {
                if (StringUtils.equals(bdcGdxxFphhDO.getXzdm(), (String) zd.get("DM"))) {
                    bdcGdxxFphhDO.setXzmc((String) zd.get("MC"));
                }
            }
        }
        bdcGdxxFphhDO.setNf(DateUtils.getCurrYear());
        bdcGdxxFphhDO.setDahscsj(new Date());
        bdcGdxxFphhDO.setDahlx(dahlx);
        logger.info("生成档案号信息：bdcGdxxFphhDO{}", bdcGdxxFphhDO);
        bdcGdxxFphhFeignService.saveDah(bdcGdxxFphhDO);
    }

    /**
     * @param bdcXmDO 项目信息
     * @param key     编号类型
     * @return 档案号
     * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取特殊档案号
     */
    private String getTsdah(BdcXmDO bdcXmDO, Integer key) {
        String dah = "";

        String ywlx = "";
        // 乡镇代码
        String xzdm = "";
        switch (key) {
            case 1:
                xzdm = bdcXmDO.getSsxz();
                if(StringUtils.isNotBlank(xzdm)){
                    ywlx = xzdm + Constants.BDC_CZWJDAH_1;
                    // 类型1：不动产登记、不动产注销目录号为16，档案号为乡镇代码+年份+顺序号（例1052017-1）
                    dah = xzdm + DateUtils.getCurrYear() + "-" + bdcBhFeignService.queryLsh(ywlx, "YEAR");
                }

                break;
            case 2:
                // 类型2：不动产抵押目录号为18，档案号为证号简称
                BdcZsQO bdcZsQO = new BdcZsQO();
                bdcZsQO.setXmid(bdcXmDO.getXmid());
                List<BdcZsDO> zsDOList = bdcZsRestService.listBdcZs(bdcZsQO);
                if(CollectionUtils.isNotEmpty(zsDOList) && null != zsDOList.get(0) && StringUtils.isNotBlank(zsDOList.get(0).getBdcqzhjc())) {
                    dah = zsDOList.get(0).getBdcqzhjc();
                }
                break;
            case 3:
                // 类型3：抵押注销目录号为22，档案号为受理编号
                dah = bdcXmDO.getSlbh();
                break;
            case 4:
                // 类型4：查解封登记目录号为19，档案号为受理编号
                dah = bdcXmDO.getSlbh();
                break;
            case 5:
                xzdm = bdcXmDO.getSsxz();
                if(StringUtils.isNotBlank(xzdm)){
                    // 类型5：集体土地所有权业务目录号为23，档案号为JA+乡镇代码+年份-顺序号（JA0022021-1）
                    ywlx = xzdm + Constants.BDC_CZWJDAH_2;
                    dah = "JA" + xzdm + DateUtils.getCurrYear() + "-" + bdcBhFeignService.queryLsh(ywlx, "YEAR");
                }
                break;
            case 6:
                // 类型6：居住权登记目录号为25档案号为JZ+居住权证明号简称
                bdcZsQO = new BdcZsQO();
                bdcZsQO.setXmid(bdcXmDO.getXmid());
                zsDOList = bdcZsRestService.listBdcZs(bdcZsQO);
                if(CollectionUtils.isNotEmpty(zsDOList) && null != zsDOList.get(0) && StringUtils.isNotBlank(zsDOList.get(0).getBdcqzhjc())) {
                    dah = "JZ" + zsDOList.get(0).getBdcqzhjc();
                }

                break;
            case 7:
                // 类型7：居住权注销目录号为26，档案号为受理编号
                dah = bdcXmDO.getSlbh();
                break;
            case 8:
                // 类型8：预告登记目录号为21，档案号为YG+预告登记证明号简称
                bdcZsQO = new BdcZsQO();
                bdcZsQO.setXmid(bdcXmDO.getXmid());
                zsDOList = bdcZsRestService.listBdcZs(bdcZsQO);
                if(CollectionUtils.isNotEmpty(zsDOList) && null != zsDOList.get(0) && StringUtils.isNotBlank(zsDOList.get(0).getBdcqzhjc())) {
                    dah = "YG" + zsDOList.get(0).getBdcqzhjc();
                }
            default:

        }
        logger.info("项目{}，档案号类型{}，乡镇代码{}，生成档案号{}", bdcXmDO.getSlbh(), key,bdcXmDO.getSsxz(), dah);
        return dah;
    }

    private List querySlSjclxx(String gzlslid, String djxl, List<Map> sjlxMapList, String dylx) {
        if (StringUtils.isNotBlank(dylx)) {
            BdcDysjZbPzDO bdcDysjZbPzDO = new BdcDysjZbPzDO();
            bdcDysjZbPzDO.setDylx(dylx);
            List<BdcDysjZbPzDO> bdcDysjZbPzDOList = bdcDypzFeignService.listBdcDysjZbPz(bdcDysjZbPzDO);
            //当前档案目录默认只有收件材料作为子表
            if (CollectionUtils.isNotEmpty(bdcDysjZbPzDOList)) {
                Map configParam = new HashMap(2);
                configParam.put("gzlslid", gzlslid);
                configParam.put("djxl", djxl);
                BdcSlZbDataDTO bdcSlZbDataDTO = new BdcSlZbDataDTO();
                bdcSlZbDataDTO.setConfigParam(configParam);
                bdcSlZbDataDTO.setBdcDysjZbPzDOList(bdcDysjZbPzDOList);
                List<Map> sjclList = bdcSlPrintFeignService.queryZbData(bdcSlZbDataDTO);
                if (CollectionUtils.isNotEmpty(sjclList)) {
                    List<BdcSlSjclDTO> bdcSlSjclDTOList = new ArrayList<>(CollectionUtils.size(sjclList));
                    List<BdcSlSjclDO> bdcSlSjclDOList = new ArrayList<>(CollectionUtils.size(sjclList));
                    for (Map sjclMap : sjclList) {
                        //map转为实体对象
                        BdcSlSjclDO bdcSlSjclDO = BeansResolveUtils.convertMap2Bean(sjclMap, BdcSlSjclDO.class);
                        bdcSlSjclDOList.add(bdcSlSjclDO);
                    }
                    if (CollectionUtils.isNotEmpty(bdcSlSjclDOList)) {
                        // 添加收件材料：登记审批表，永远在第二个位置
                        BdcSlSjclQO bdcSlSjclQO = new BdcSlSjclQO();
                        bdcSlSjclQO.setDjxl(djxl);
                        bdcSlSjclQO.setGzlslid(gzlslid);
                        bdcSlSjclQO.setClmc(CommonConstantUtils.DAML_SJCL_DJSHB);
                        List<BdcSlSjclDO> djshbList = bdcSlSjclFeignService.listBdcSlSjcl(bdcSlSjclQO);
                        if (CollectionUtils.isEmpty(djshbList)) {
                            BdcSlSjclDO spbxx = new BdcSlSjclDO();
                            spbxx.setClmc(CommonConstantUtils.DAML_SJCL_DJSHB);
                            spbxx.setFs(1);
                            spbxx.setXh(2);
                            spbxx.setSjlx(1);
                            spbxx.setSjclid(UUIDGenerator.generate16());
                            bdcSlSjclDOList.add(1, spbxx);
                        }
                        // 判断最后一个材料是否属于配置里面的
                        BdcSlSjclDO lastSjcl = bdcSlSjclDOList.get(CollectionUtils.size(bdcSlSjclDOList) - 1);
                        BdcSlSjclDO qtzmSjcl = new BdcSlSjclDO();
                        qtzmSjcl.setClmc(CommonConstantUtils.DAML_SJCL_QTZMCL);
                        qtzmSjcl.setFs(1);
                        qtzmSjcl.setSjlx(1);
                        qtzmSjcl.setSjclid(UUIDGenerator.generate16());
                        bdcSlSjclQO.setClmc(CommonConstantUtils.DAML_SJCL_QTZMCL);
                        List<BdcSlSjclDO> qtzmclList = bdcSlSjclFeignService.listBdcSlSjcl(bdcSlSjclQO);
                        //没有当前材料就插入
                        if (CollectionUtils.isEmpty(qtzmclList)) {
                            if (CollectionUtils.isNotEmpty(damlFolderList) && damlFolderList.contains(lastSjcl.getClmc())) {
                                //如果最后一个是属于配置的文件名称，需要在倒数第二个插入 其他证明材料，否则在最后一个后面插入证明材料
                                if (CollectionUtils.size(bdcSlSjclDOList) == 2) {
                                    qtzmSjcl.setXh(3);
                                    bdcSlSjclDOList.add(2, qtzmSjcl);
                                } else {
                                    qtzmSjcl.setXh(CollectionUtils.size(bdcSlSjclDOList) - 1);
                                    bdcSlSjclDOList.add(CollectionUtils.size(bdcSlSjclDOList) - 1, qtzmSjcl);
                                }
                            } else {
                                //添加到最后一个
                                bdcSlSjclDOList.add(qtzmSjcl);
                            }
                        }
                        int xh = 1;
                        for (BdcSlSjclDO bdcSlSjclDO : bdcSlSjclDOList) {
                            bdcSlSjclDO.setXh(xh);
                            BdcSlSjclDTO bdcSlSjclDTO = new BdcSlSjclDTO();
                            BeanUtils.copyProperties(bdcSlSjclDO, bdcSlSjclDTO);
                            bdcSlSjclDTO.setSjlxmc(StringToolUtils.convertBeanPropertyValueOfZd(bdcSlSjclDO.getSjlx(), sjlxMapList));
                            bdcSlSjclDTOList.add(bdcSlSjclDTO);
                            xh++;
                        }
                    }
                    LOGGER.info("当前档案目录材料数量{},开始补充文件夹", CollectionUtils.size(bdcSlSjclDTOList));
                    //判断收件材料个数是否超过20 ，不足20 补满，超过20补满四十
                    if (CollectionUtils.size(bdcSlSjclDTOList) < 20) {
                        List<BdcSlSjclDTO> addSjclList = new ArrayList<>(10);
                        for (int i = 0; i < (20 - CollectionUtils.size(bdcSlSjclDTOList)); i++) {
                            BdcSlSjclDTO bdcSlSjclDTO = new BdcSlSjclDTO();
                            bdcSlSjclDTO.setSjclid(UUIDGenerator.generate16());
                            addSjclList.add(bdcSlSjclDTO);
                        }
                        bdcSlSjclDTOList.addAll(addSjclList);
                    } else {
                        if (CollectionUtils.size(bdcSlSjclDTOList) > 20) {
                            List<BdcSlSjclDTO> addSjclList = new ArrayList<>(10);
                            for (int i = 0; i < (40 - CollectionUtils.size(bdcSlSjclDTOList)); i++) {
                                BdcSlSjclDTO bdcSlSjclDTO = new BdcSlSjclDTO();
                                bdcSlSjclDTO.setSjclid(UUIDGenerator.generate16());
                                addSjclList.add(bdcSlSjclDTO);
                            }
                            bdcSlSjclDTOList.addAll(addSjclList);
                        }
                    }
                    return bdcSlSjclDTOList;
                }
            }
        }
        //获取收件材料信息
        BdcSlSjclQO bdcSlSjclQO = new BdcSlSjclQO();
        bdcSlSjclQO.setGzlslid(gzlslid);
        bdcSlSjclQO.setDjxl(djxl);
        List<BdcSlSjclDO> bdcSlSjclDOList = bdcSlSjclFeignService.listBdcSlSjcl(bdcSlSjclQO);
        List<BdcSlSjclDTO> bdcSlSjclDTOS = new ArrayList<>(CollectionUtils.size(bdcSlSjclDOList));
        if (CollectionUtils.isNotEmpty(bdcSlSjclDOList)) {
            // 添加收件材料：登记审批表
            BdcSlSjclDO spbxx = new BdcSlSjclDO();
            spbxx.setClmc("登记审核表");
            spbxx.setFs(1);
            spbxx.setXh(2);
            spbxx.setSjlx(1);
            bdcSlSjclDOList.add(1, spbxx);

            int xh = 1;
            for (BdcSlSjclDO bdcSlSjclDO : bdcSlSjclDOList) {
                bdcSlSjclDO.setXh(xh);
                BdcSlSjclDTO bdcSlSjclDTO = new BdcSlSjclDTO();
                BeanUtils.copyProperties(bdcSlSjclDO, bdcSlSjclDTO);
                bdcSlSjclDTO.setSjlxmc(StringToolUtils.convertBeanPropertyValueOfZd(bdcSlSjclDO.getSjlx(), sjlxMapList));
                bdcSlSjclDTOS.add(bdcSlSjclDTO);
                xh++;
            }
        }
        Multimap<String, List> zbMap = ArrayListMultimap.create();
        zbMap.put("bdcsjcl", bdcSlSjclDTOS);
        return bdcSlSjclDTOS;
    }

    @GetMapping(value = "/spb/{gzlslids}/{dylx}/xml/pl")
    public String spbPrintXmlPl(@PathVariable(value = "gzlslids") String gzlslids, @PathVariable(value="dylx")String dylx) {
        BdcPrintDTO bdcPrintDTO = new BdcPrintDTO();
        bdcPrintDTO.setBdcUrlDTO(this.bdcUrl());
        bdcPrintDTO.setDylx(dylx);
        bdcPrintDTO.setGzlslids(gzlslids);
        bdcPrintDTO.setPrintMode(printMode);
        return bdcShxxFeignService.bdPrintXml(bdcPrintDTO);
    }

    /**
     * 不动产审批表和档案目录合并打印
     * @param gzlslids 工作流实例ID集合
     *  1、生成打印请求参数，调用打印配置sql生成主表数据与子表数据
     *  2、组织档案目录后台生成接口数据，添加到主表与子表中去
     */
        @GetMapping("/dajj/spbdaml/{dylx}/{gzlslids}")
    public String printSpbDaml(@PathVariable(value = "dylx") String dylx,
                               @PathVariable(value = "gzlslids") String gzlslids) {
        if(StringUtils.isBlank(gzlslids)){
            throw new AppException(ErrorCode.MISSING_ARG, "缺少工作流实例ID");
        }
        String[] gzlslidArray = gzlslids.split(CommonConstantUtils.ZF_YW_DH);
        List<Map> qlxzMapList = bdcZdFeignService.queryBdcZd("qlxz");
        List<Map> sjlxMapList = bdcZdFeignService.queryBdcZd("sjlx");
        BdcDysjPzDO bdcDysjPzDO = new BdcDysjPzDO();
        bdcDysjPzDO.setDylx(dylx);
        List<BdcDysjPzDO> bdcDysjPzDOList = bdcDypzFeignService.listBdcDysjPz(bdcDysjPzDO);
        List<BdcDysjDTO> bdcDysjDTOList = new ArrayList<>(gzlslidArray.length);
            for (String gzlslid : gzlslidArray) {
                Map<String, String> gzlslidMap = new HashMap<>(1);
                BdcXmQO bdcXmQO = new BdcXmQO();
                bdcXmQO.setGzlslid(gzlslid);
                List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
                if(CollectionUtils.isEmpty(bdcXmDOList)) {
                    //未查询到登记数据查询上一手项目信息
                    List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmFeignService.listBdcSlXmByGzlslid(gzlslid);
                    if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                        List<String> yxmidList = new ArrayList<>(CollectionUtils.size(bdcSlXmDOList));
                        for (BdcSlXmDO bdcSlXmDO : bdcSlXmDOList) {
                            List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = bdcSlXmLsgxFeignService.listBdcSlXmLsgx(bdcSlXmDO.getXmid(), "", CommonConstantUtils.SF_F_DM);
                            if (CollectionUtils.isNotEmpty(bdcSlXmLsgxDOList)) {
                                yxmidList.add(bdcSlXmLsgxDOList.get(0).getYxmid());
                            }
                        }
                        if (CollectionUtils.isNotEmpty(yxmidList)) {
                            bdcXmQO = new BdcXmQO();
                            bdcXmQO.setXmidList(yxmidList);
                            bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
                        }
                    }
                }
                BdcXmDO bdcXmDO = bdcXmDOList.get(0);
                gzlslidMap.put(bdcXmDO.getGzlslid(), gzlslid);
                // 1、组织sql执行后的打印参数
                BdcDysjDTO bdcDysjDTO = this.getSqlDysjMap(bdcXmDO.getXmid(), gzlslid, dylx);
                // 主表数据
                Map dataMap = Optional.ofNullable(JSON.parseObject(bdcDysjDTO.getDysj(), HashMap.class)).orElse(new HashMap());
                // 子表数据
                Map detailMap = Optional.ofNullable(JSON.parseObject(bdcDysjDTO.getDyzbsj(), HashMap.class)).orElse(new HashMap());

                // 判断流程是否组合流程，组合流程时，收件材料在一个模板上分登记小类进行打印
                int lclx = bdcXmFeignService.makeSureBdcXmLx(gzlslid);
                boolean sfhb = false;
                if (Objects.equals(CommonConstantUtils.LCLX_ZH, lclx) || Objects.equals(CommonConstantUtils.LCLX_PLZH, lclx)) {
                    sfhb = true;
                }
                boolean sfpllc = false;
                if (Objects.equals(CommonConstantUtils.LCLX_PL, lclx)) {
                    sfpllc = true;
                }
                // 2、组织档案目录打印参数，档案数据为后台组织数据
                Pair<Map, Multimap> damldypz = this.getDamlDysjMap(bdcXmDO, qlxzMapList, sjlxMapList, sfhb, dylx, sfpllc, gzlslidMap);
                dataMap.putAll(damldypz.getLeft());
                Multimap multimap = damldypz.getRight();
                if(!multimap.isEmpty()){
                    detailMap = JSON.parseObject(JSON.toJSONString(multimap), HashMap.class);
                }
                bdcDysjDTO.setDylx(dylx);
                bdcDysjDTO.setDyzd(bdcDysjPzDOList.get(0).getDyzd());
                bdcDysjDTO.setDysj(JSONObject.toJSONString(dataMap));
                bdcDysjDTO.setDyzbsj(JSONObject.toJSONString(detailMap));
                bdcDysjDTOList.add(bdcDysjDTO);
            }
        if(CollectionUtils.isNotEmpty(bdcDysjDTOList)){
            String xml = bdcPrintFeignService.printDatas(bdcDysjDTOList);
            Map<String, Object> eventMap = new HashMap<>();
            UserDto userDto = userManagerUtils.getCurrentUser();
            eventMap.put(CommonConstantUtils.ALIAS, userDto != null ? userDto.getAlias() : userManagerUtils.getCurrentUserName());
            eventMap.put(CommonConstantUtils.GZLSLID,gzlslids);
            eventMap.put(Constants.DAJJ_DYLX,Constants.DAJJ_DYLX_QB);
            eventMap.put("xml",xml);
            AuditEvent auditEvent = new AuditEvent(userManagerUtils.getCurrentUserName(), Constants.DAJJ_DY, eventMap);
            zipkinAuditEventRepository.add(auditEvent);
            LOGGER.info("{} 数据源xml{}",dylx ,xml);
            return xml;
        }
        return "";
    }

    /**
     * 根据工作流实例ID获取项目信息
     * @param gzlslidArray 工作流实例ID集合
     * @return Map<gzlslid, List<BdcXmDO>>
     */
    private Map<String, List<BdcXmDO>> getBdcXmxxMap(String[] gzlslidArray){
        Map<String, List<BdcXmDO>> resultMap = new HashMap<>(8);
        if(Objects.nonNull(gzlslidArray) && gzlslidArray.length > 0 ){
            for (String gzlslid : gzlslidArray) {
                //根据gzlslid找到对应的项目，单个，批量 流程打印一份，组合多份然后循环项目数据
                int lclx = bdcXmFeignService.makeSureBdcXmLx(gzlslid);
                BdcXmQO bdcXmQO = new BdcXmQO();
                bdcXmQO.setGzlslid(gzlslid);
                List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
                if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                    if (Objects.equals(CommonConstantUtils.LCLX_PT, lclx) || Objects.equals(CommonConstantUtils.LCLX_PL, lclx)) {
                        resultMap.put(gzlslid, Arrays.asList(bdcXmDOList.get(0)));
                    } else if (Objects.equals(CommonConstantUtils.LCLX_ZH, lclx) || Objects.equals(CommonConstantUtils.LCLX_PLZH, lclx)) {
                        resultMap.put(gzlslid, bdcXmDOList);
                    }
                } else {
                    LOGGER.warn("当前流程实例id{}没有生成bdc_xm 数据，查询受理库数据，获取上一手的项目信息", gzlslid);
                    //登记库无数据查询受理库，根据受理项目历史关系查上一手的数据
                    List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmFeignService.listBdcSlXmByGzlslid(gzlslid);
                    if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                        //查项目历史关系
                        List<String> xmidList = new ArrayList<>(CollectionUtils.size(bdcSlXmDOList));
                        for (BdcSlXmDO bdcSlXmDO : bdcSlXmDOList) {
                            List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = bdcSlXmLsgxFeignService.listBdcSlXmLsgx(bdcSlXmDO.getXmid(), "", CommonConstantUtils.SF_F_DM);
                            if (CollectionUtils.isNotEmpty(bdcSlXmLsgxDOList)) {
                                xmidList.add(bdcSlXmLsgxDOList.get(0).getYxmid());
                            }
                        }
                        if (CollectionUtils.isNotEmpty(xmidList)) {
                            bdcXmQO = new BdcXmQO();
                            bdcXmQO.setXmidList(xmidList);
                            bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
                        }
                        resultMap.put(gzlslid, bdcXmDOList);
                    } else {
                        LOGGER.error("当前流程实例id{}没有查询到受理项目数据，无法打印", gzlslid);
                    }
                }
            }
        }
        return resultMap;
    }

    /**
     * 获取spbdaml打印配置sql执行后的打印参数
     */
    private BdcDysjDTO getSqlDysjMap(String xmid, String gzlslid, String dylx){
        BdcPrintDTO bdcPrintDTO = new BdcPrintDTO();
        List jdmcList = new ArrayList();
        List<UserTaskDto> userTaskDataList = workFlowUtils.listShjdxx(gzlslid);
        userTaskDataList.forEach(taskData -> {
            jdmcList.add(taskData.getName());
        });
        bdcPrintDTO.setJdmcList(jdmcList);
        bdcPrintDTO.setBdcUrlDTO(this.bdcUrl());
        bdcPrintDTO.setGzlslid(gzlslid);
        bdcPrintDTO.setXmid(xmid);
        bdcPrintDTO.setDylx(dylx);
        return this.bdcShxxRestService.getPrintDataMap(bdcPrintDTO);
    }

    @GetMapping("/dypz/dylx")
    public String queryDylxFromDypz(@NotBlank(message = "获取打印类型工作流实例id不可为空") String gzlslid, String djxl) {
        String dylx = "bdcSqSpb";
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        if (StringUtils.isNotBlank(djxl)) {
            bdcXmQO.setDjxl(djxl);
        }
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            BdcDjxlPzDO bdcDjxlPzDO = bdcDjxlPzFeignService.queryBdcDjxlPzByGzldyidAndDjxl(bdcXmDOList.get(0).getGzldyid(), bdcXmDOList.get(0).getDjxl());
            if (Objects.nonNull(bdcDjxlPzDO) && StringUtils.isNotBlank(bdcDjxlPzDO.getSpbdylx())) {
                dylx = bdcDjxlPzDO.getSpbdylx();
            }
        }
        return dylx;
    }

    /**
     * @author: <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param: bdcGzYzQO 规则验证查询参数
     * @param: zhbs 组合标识
     * @return {List} 验证结果
     * @description 证书证明打印前规则验证
     */
    @PostMapping("/gzyz/{zhbs}")
    public List<Map<String, Object>> checkBeforePrint(@RequestBody BdcGzYzQO bdcGzYzQO, @PathVariable("zhbs") String zhbs) {
        if (StringUtils.isBlank(zhbs) || null == bdcGzYzQO || CollectionUtils.isEmpty(bdcGzYzQO.getBdcGzYzsjDTOList())) {
            throw new AppException("验证异常：未指定组合规则标识或验证参数");
        }
        bdcGzYzQO.setZhbs(zhbs);
        return bdcZszmCxFeignService.checkBdcdyhGz(bdcGzYzQO);
    }

    /**
     * 获取当日流水号
     * @return
     */
    public String getDrlsh(String dahlx, BdcXmDO bdcXmDO) {
        String drlsh = "";
        BdcGdxxFphhQO bdcGdxxFphhQO = new BdcGdxxFphhQO();
        bdcGdxxFphhQO.setXmid(bdcXmDO.getXmid());
        List<BdcGdxxFphhDO> bdcGdxxFphhDOList = bdcGdxxFphhFeignService.getBdcGdxxFphhDOList(bdcGdxxFphhQO);
        String ydahlx = "";
        if(CollectionUtils.isNotEmpty(bdcGdxxFphhDOList)){
            ydahlx = bdcGdxxFphhDOList.get(0).getDahlx();
            drlsh = bdcGdxxFphhDOList.get(0).getAjh();
        }else{
             if(Constants.BDC_DAHLX_ZS.equals(dahlx)){
                 Integer lsh = bdcBhFeignService.queryLsh(dahlx, "");
                 logger.info("证书类档案号获取的当日最大流水号：{}", lsh);
                 if(lsh!=null){
                     drlsh = zslprefix + DateUtils.getCurrDay() + String.format("%04d", lsh);
                 }
             }else{
                 Integer lsh = bdcBhFeignService.queryLsh(dahlx, "");
                 logger.info("证明类档案号获取的当日最大流水号：{}", lsh);
                 if(lsh!=null){
                     drlsh = zmlprefix + DateUtils.getCurrDay() + String.format("%04d", lsh);
                 }
             }
             this.saveDah(bdcXmDO, drlsh, dahlx, new Date(),null,null,null);
        }
        logger.info("档案号：{},档案号类型：{}", drlsh ,ydahlx);
        return drlsh;
    }

    /**
     * 获取金坛档案号
     * @return
     */
    public String getJtDah(BdcXmDO bdcXmDO , Integer qllx) {
        String dah = "";
        BdcGdxxFphhQO bdcGdxxFphhQO = new BdcGdxxFphhQO();
        bdcGdxxFphhQO.setXmid(bdcXmDO.getXmid());
        List<BdcGdxxFphhDO> bdcGdxxFphhDOList = bdcGdxxFphhFeignService.getBdcGdxxFphhDOList(bdcGdxxFphhQO);
        if(CollectionUtils.isNotEmpty(bdcGdxxFphhDOList)){
            dah = bdcGdxxFphhDOList.get(0).getAjh();
            return dah;
        }else{
            if(CommonConstantUtils.DJLX_ZXDJ_DM.equals(bdcXmDO.getDjlx())){
                if(CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcXmDO.getQllx())){
                    Integer lsh = bdcBhFeignService.queryLsh(Constants.BDC_JT_DAHLX_DYQZX, Constants.ZZSJFW_YEAR);
                    logger.info("金坛抵押权注销类档案号获取的当日最大流水号：{}", lsh);
                    if(lsh!=null){
                        dah = dajjDahPrefixJtConfig.getDyqzxprefix() + DateUtils.getCurrYear() + String.format("%06d", lsh);
                    }
                }else if(ArrayUtils.contains(CommonConstantUtils.QLLX_FDCQ, bdcXmDO.getQllx())){
                    Integer lsh = bdcBhFeignService.queryLsh(Constants.BDC_JT_DAHLX_CQZX, Constants.ZZSJFW_YEAR);
                    logger.info("金坛产权注销类档案号获取的当日最大流水号：{}", lsh);
                    if(lsh!=null){
                        dah = dajjDahPrefixJtConfig.getCqzxprefix() + DateUtils.getCurrYear() + String.format("%06d", lsh);
                    }
                }else{
                    Integer lsh = bdcBhFeignService.queryLsh(Constants.BDC_JT_DAHLX_QTZX, Constants.ZZSJFW_YEAR);
                    logger.info("金坛其他注销档案号获取的当日最大流水号：{}", lsh);
                    if(lsh!=null){
                        dah = dajjDahPrefixJtConfig.getQtzxprefix() + DateUtils.getCurrYear() + String.format("%06d", lsh);
                    }
                }
            }else if(CollectionUtils.isNotEmpty(dajjDahPrefixJtConfig.getQzlql()) &&qllx != null &&dajjDahPrefixJtConfig.getQzlql().contains(Integer.toString(qllx))){
                Integer lsh = bdcBhFeignService.queryLsh(Constants.BDC_JT_DAHLX_QZ, Constants.ZZSJFW_YEAR);
                logger.info("金坛权证类档案号获取的当日最大流水号：{}", lsh);
                if(lsh!=null){
                    dah = dajjDahPrefixJtConfig.getQzlprefix() + DateUtils.getCurrYear() + String.format("%06d", lsh);
                }
            }else if(CollectionUtils.isNotEmpty(dajjDahPrefixJtConfig.getJtzmlql()) &&qllx != null &&dajjDahPrefixJtConfig.getJtzmlql().contains(Integer.toString(qllx))){
                Integer lsh = bdcBhFeignService.queryLsh(Constants.BDC_JT_DAHLX_ZM, Constants.ZZSJFW_YEAR);
                logger.info("金坛权证类档案号获取的当日最大流水号：{}", lsh);
                if(lsh!=null){
                    dah = dajjDahPrefixJtConfig.getZmlprefix() + DateUtils.getCurrYear() + String.format("%06d", lsh);
                }
            }

        }
        logger.info("档案号：{}", dah);
        if(StringUtils.isBlank(dah)){
            throw new AppException("金坛档案号获取失败！");
        }else{
            this.saveDah(bdcXmDO, dah, null, new Date(),null,null,null);
        }
        return dah;
    }

    /**
     * 获取溧阳档案号
     * @return
     */
    public String getLyDah(BdcXmDO bdcXmDO , Integer qllx) {
        String dah = "";
        BdcGdxxFphhQO bdcGdxxFphhQO = new BdcGdxxFphhQO();
        bdcGdxxFphhQO.setXmid(bdcXmDO.getXmid());
        List<BdcGdxxFphhDO> bdcGdxxFphhDOList = bdcGdxxFphhFeignService.getBdcGdxxFphhDOList(bdcGdxxFphhQO);
        if(CollectionUtils.isNotEmpty(bdcGdxxFphhDOList)){
            dah = bdcGdxxFphhDOList.get(0).getAjh();
            return dah;
        }else{
            if(CollectionUtils.isNotEmpty(dajjLyConfig.getLyCfDjxl()) && dajjLyConfig.getLyCfDjxl().contains(bdcXmDO.getDjxl())){
                Integer lsh = bdcBhFeignService.queryLsh(Constants.BDC_LY_DAHLX_CF, "ALL");
                logger.info("溧阳受理编号：{} ,查封档案号获取的当日最大流水号：{}",bdcXmDO.getSlbh(), lsh);
                if(lsh!=null){
                    dah = lsh.toString() + DateUtils.getCurrYear() + String.format("%04d", lsh);;
                    this.saveDah(bdcXmDO, dah, Constants.BDC_LY_DAHLX_CF, new Date(),null,dajjLyConfig.getCfmlh(),null);
                }
            }else if(CollectionUtils.isNotEmpty(dajjLyConfig.getLycqlql()) &&qllx != null &&dajjLyConfig.getLycqlql().contains(Integer.toString(qllx))){
                Integer lsh = bdcBhFeignService.queryLsh(Constants.BDC_LY_DAHLX_CQ, "ALL");
                logger.info("溧阳受理编号：{} ,产权类档案号获取的当日最大流水号：{}", bdcXmDO.getSlbh(), lsh);
                if(lsh!=null){
                    dah = lsh.toString();
                    this.saveDah(bdcXmDO, dah, Constants.BDC_LY_DAHLX_CQ, new Date(),null,dajjLyConfig.getCqlqlmlh(),null);
                }
            }else if(CollectionUtils.isNotEmpty(dajjLyConfig.getLyzmlql()) &&qllx != null &&dajjLyConfig.getLyzmlql().contains(Integer.toString(qllx))){
                Integer lsh = bdcBhFeignService.queryLsh(Constants.BDC_LY_DAHLX_ZM, "ALL");
                logger.info("溧阳受理编号：{} ,证明类档案号获取的当日最大流水号：{}", bdcXmDO.getSlbh(),lsh);
                if(lsh!=null){
                    dah = lsh.toString();
                    this.saveDah(bdcXmDO, dah, Constants.BDC_LY_DAHLX_ZM, new Date(),null,dajjLyConfig.getZmlqlmlh(),null);
                }
            }

        }
        if(StringUtils.isBlank(dah)){
            throw new AppException("溧阳档案号获取失败！");
        }
        return dah;
    }

    /**
     * 获取溧阳组合流程档案号
     * @return
     */
    public String getLyZhlcDah(BdcXmDO bdcXmDO , Integer qllx) {
        String dah = "";
        BdcGdxxFphhQO bdcGdxxFphhQO = new BdcGdxxFphhQO();
        bdcGdxxFphhQO.setGzlslid(bdcXmDO.getGzlslid());
        List<BdcGdxxFphhDO> bdcGdxxFphhDOList = bdcGdxxFphhFeignService.getBdcGdxxFphhDOList(bdcGdxxFphhQO);
        if(CollectionUtils.isNotEmpty(bdcGdxxFphhDOList)){
            dah = bdcGdxxFphhDOList.get(0).getAjh();
            return dah;
        }else{
            //组合流程未生成档案号，一个证明一个产权
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setGzlslid(bdcXmDO.getGzlslid());
            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            List<String> qllxList = bdcXmDOList.stream().map(BdcXmDO::getQllx).map(qllxStr -> qllxStr +"").collect(Collectors.toList());
            logger.info("溧阳组合流程权利类型list：{},xmid:{}", qllxList,bdcXmDO.getXmid());
            if(dajjLyConfig.getLyzmlql().containsAll(qllxList)){
                Integer lsh = bdcBhFeignService.queryLsh(Constants.BDC_LY_DAHLX_ZM, "ALL");
                logger.info("溧阳证明类档案号获取的当日最大流水号：{},xmid:{}", lsh,bdcXmDO.getXmid());
                if(lsh!=null){
                    dah = lsh.toString();
                    this.saveDah(bdcXmDO, dah, Constants.BDC_LY_DAHLX_ZM, new Date(),null,dajjLyConfig.getZmlqlmlh(),null);
                }
            }else{
                if(CollectionUtils.isNotEmpty(dajjLyConfig.getLycqlql()) &&qllx != null &&dajjLyConfig.getLycqlql().contains(Integer.toString(qllx))){
                    Integer lsh = bdcBhFeignService.queryLsh(Constants.BDC_LY_DAHLX_CQ, "ALL");
                    logger.info("溧阳产权类档案号获取的当日最大流水号：{},xmid:{}", lsh,bdcXmDO.getXmid());
                    if(lsh!=null){
                        dah = lsh.toString();
                        this.saveDah(bdcXmDO, dah, Constants.BDC_LY_DAHLX_CQ, new Date(),null,dajjLyConfig.getCqlqlmlh(),null);
                    }
                }
            }
        }
        logger.info("档案号：{}", dah);
        return dah;
    }


    /**
     * 批量发证签收单打印
     */
    @GetMapping("/plfzqsd/{dylx}/{redisKey}")
    public String printPlfzqsd(@PathVariable("dylx")String dylx, @PathVariable(value="redisKey") String redisKey) {
        if(StringUtils.isBlank(redisKey)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到打印的redis标识符。");
        }
        String data = redisUtils.getStringValue(redisKey);
        if(StringUtils.isBlank(data)){
            throw new AppException("未获取到要打印的数据");
        }

        List<Map> paramList = JSON.parseArray(data, Map.class);
        // 1、查询打印配置
        BdcDysjPzDO bdcDysjPzDO = new BdcDysjPzDO();
        bdcDysjPzDO.setDylx(dylx);
        List<BdcDysjPzDO> bdcDysjPzDOList = bdcDypzFeignService.listBdcDysjPz(bdcDysjPzDO);
        if (CollectionUtils.isEmpty(bdcDysjPzDOList)){
            throw new AppException("未获取到打印配置,请检查配置");
        }
        // 2、主表数据
        Map<String, String> parentData = new HashMap<>(2);
        //通过打印数据源sql配置转换接口数据
        // 3、设置子表数据
        Multimap<String, List> childData = ArrayListMultimap.create();
        childData.put("ZT_plfzqsdmx", paramList);

        // 4、设置打印模板格式
        List<BdcDysjDTO> bdcDysjDTOList = new ArrayList<>(1);
        BdcDysjDTO bdcDysjDTO = new BdcDysjDTO();
        bdcDysjDTO.setDysj(JSONObject.toJSONString(parentData));
        bdcDysjDTO.setDyzbsj(JSONObject.toJSONString(childData));
        bdcDysjDTO.setDyzd(bdcDysjPzDOList.get(0).getDyzd());
        bdcDysjDTOList.add(bdcDysjDTO);
        return bdcPrintFeignService.printDatas(bdcDysjDTOList);
    }


    private List<BdcXmDO> listPrintXm(List<BdcXmDO> bdcXmDOList, String dylx) {
        List<BdcXmDO> printXmList = new ArrayList<>(CollectionUtils.size(bdcXmDOList));
        for (BdcXmDO bdcXmDO : bdcXmDOList) {
            boolean sfpldy = pldy(bdcXmDO, dylx);
            logger.info("是否批量打印：{},xmid:{}", sfpldy,bdcXmDO.getXmid());
            if (sfpldy) {
                //证明类注销生成一个目录
                if(CollectionUtils.isNotEmpty(zmlqlList) && bdcXmDO.getQllx() != null &&zmlqlList.contains(Integer.toString(bdcXmDO.getQllx()))){
                    printXmList.add(bdcXmDO);
                //查封类和产权注销类业务流程分别持证只生成一份目录
                } else if(CommonConstantUtils.SF_S_DM.equals(bdcXmDO.getSqfbcz())){
                    printXmList.add(bdcXmDO);
                }else if(sfWjPlxflc(bdcXmDO)){
                    printXmList.add(bdcXmDO);
                }else{
                    BdcXmQO bdcXmQO = new BdcXmQO();
                    bdcXmQO.setGzlslid(bdcXmDO.getGzlslid());
                    List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(bdcXmQO);
                    bdcXmDOS = bdcXmDOS.stream().filter(bdcxm -> StringUtils.isNotBlank(bdcxm.getYcqzh())).collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(BdcXmDO::getYcqzh))), ArrayList::new));
                    printXmList.addAll(bdcXmDOS);
                }
            } else {
                printXmList.add(bdcXmDO);
            }
        }
        return printXmList;
    }

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 判断档案目录打印是否能够批量打印
     * @date : 2022/2/24 16:15
     */
    private boolean pldy(BdcXmDO bdcXmDO, String dylx) {
        String daml = "daml";
        BdcQl bdcQl = bdcQllxFeignService.queryQlxx(bdcXmDO.getXmid());
        BdcDjxlPzDO bdcDjxlPzDO = bdcDjxlPzFeignService.queryBdcDjxlPzByGzldyidAndDjxl(bdcXmDO.getGzldyid(), bdcXmDO.getDjxl());
        if (Objects.nonNull(bdcDjxlPzDO) && StringUtils.isNotBlank(bdcDjxlPzDO.getDamldylx())) {
            daml = bdcDjxlPzDO.getDamldylx();
        }
        if(dajjTsqh.contains(bdcXmDO.getQxdm())){
            return false;
        }
        if ((CommonConstantUtils.QLLX_CF.equals(bdcXmDO.getQllx()) || CommonConstantUtils.DJLX_ZXDJ_DM.equals(bdcXmDO.getDjlx()) || Objects.isNull(bdcQl)) && StringUtils.equals(daml, dylx)) {
            return true;
        }
        return false;
    }

    /**
     * @param
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 判断是否是批量续封登记
     * @date : 2022/2/24 16:15
     */
    private boolean sfWjPlxflc(BdcXmDO bdcXmDO){
        if(CommonConstantUtils.QLLX_CF.equals(bdcXmDO.getQllx()) && StringUtils.contains(dajjTsqh, bdcXmDO.getQxdm())){
            int lclx = bdcXmFeignService.makeSureBdcXmLx(bdcXmDO.getGzlslid());
            if(Objects.equals(CommonConstantUtils.LCLX_PL, lclx)){
                BdcQl bdcQl = bdcQllxFeignService.queryQlxx(bdcXmDO.getXmid());
                if(bdcQl instanceof BdcCfDO){
                    BdcCfDO bdcCfDO = (BdcCfDO) bdcQl;
                    if(CommonConstantUtils.CFLX_XF.equals(bdcCfDO.getCflx())){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @param
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 判断是否是生成多个项目共用一个档案号的流程
     * @date : 2022/12/5 16:15
     */
    private String getGyDah(BdcXmDO bdcXmDO){
        if(CollectionUtils.isNotEmpty(gydahgzldyids) && gydahgzldyids.contains(bdcXmDO.getGzldyid())){
            BdcGdxxFphhQO bdcGdxxFphhQO = new BdcGdxxFphhQO();
            bdcGdxxFphhQO.setGzlslid(bdcXmDO.getGzlslid());
            List<BdcGdxxFphhDO> bdcGdxxFphhDOList = bdcGdxxFphhFeignService.getBdcGdxxFphhDOList(bdcGdxxFphhQO);
            if(CollectionUtils.isNotEmpty(bdcGdxxFphhDOList)){
                return bdcGdxxFphhDOList.get(0).getAjh();
            }
        }
        return null;
    }

}
