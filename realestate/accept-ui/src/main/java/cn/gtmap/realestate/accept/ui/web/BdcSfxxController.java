package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskClient;
import cn.gtmap.gtc.workflow.domain.manage.*;
import cn.gtmap.realestate.accept.ui.utils.Constants;
import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.config.accept.BdcSlSfxmConfig;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.accept.*;
import cn.gtmap.realestate.common.core.dto.accept.*;
import cn.gtmap.realestate.common.core.dto.exchange.hefei.fp.dydzpj.request.DydzpjRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.ntfssr.request.HandleRedemptionTicketInfoRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.ntfssr.request.InvoiceRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.ntfssr.request.ObtainRedemptionTicketInfoRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.ntfssr.request.VoidPaymentFormRequestDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcQlPageResponseDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmAndFbDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.enums.BdcSfztEnum;
import cn.gtmap.realestate.common.core.enums.BdcSlXwlxEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.*;
import cn.gtmap.realestate.common.core.qo.init.BdcQlQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.*;
import cn.gtmap.realestate.common.core.service.feign.config.BdcXtFphFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.NantongFsxtzfFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.YanchengYthFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.*;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcdjjfglVO;
import cn.gtmap.realestate.common.matcher.FlowableNodeClientMatcher;
import cn.gtmap.realestate.common.matcher.ProcessInstanceClientMatcher;
import cn.gtmap.realestate.common.matcher.TaskHandleClientMatcher;
import cn.gtmap.realestate.common.matcher.ZipkinAuditEventRepositoryMatcher;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.common.util.configuration.BdcConfigUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.base.Preconditions;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 2019/1/3,1.0
 * @description 收费信息
 */
@Controller
@RequestMapping("/sf")
@Validated
public class BdcSfxxController extends BaseController {

    @Autowired
    private BdcSlSfxmFeignService bdcSlSfxmFeignService;
    @Autowired
    private BdcSlSfxxFeignService bdcSlSfxxFeignService;
    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    private BdcQlrFeignService bdcQlrFeignService;
    @Autowired
    BdcSlSfxmPzFeignService bdcSlSfxmPzFeignService;
    @Autowired
    BdcZsInitFeignService bdcZsInitFeignService;
    @Autowired
    BdcSlXmFeignService bdcSlXmFeignService;
    @Autowired
    BdcSlJbxxFeignService bdcSlJbxxFeignService;
    @Autowired
    BdcQllxFeignService bdcQllxFeignService;
    @Autowired
    private ProcessInstanceClientMatcher processInstanceClient;
    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;
    @Autowired
    NantongFsxtzfFeignService nantongFsxtzfFeignService;
    @Autowired
    BdcSlSfxmJmzcGxFeignService bdcSlSfxmJmzcGxFeignService;
    @Autowired
    YanchengYthFeignService yanchengYthFeignService;
    @Autowired
    BdcYcslFeignService bdcYcslFeignService;

    @Autowired
    ProcessTaskClient processTaskClient;

    @Autowired
    BdcUploadFileUtils bdcUploadFileUtils;

    @Autowired
    BdcXtFphFeignService bdcXtFphFeignService;

    @Autowired
    BdcLcTsjfGxFeignService bdcLcTsjfGxFeignService;
    @Autowired
    BdcZdFeignService bdcZdFeignService;

    @Autowired
    BdcXtJgFeignService bdcXtJgFeignService;
    @Autowired
    private FlowableNodeClientMatcher flowableNodeClient;
    @Autowired
    TaskHandleClientMatcher taskHandleClient;
    @Autowired
    BdcSfxxFeignService bdcSfxxFeignService;
    @Autowired
    BdcSlSqrFeignService bdcSlSqrFeignService;

    @Autowired
    ZipkinAuditEventRepositoryMatcher zipkinAuditEventRepository;


    @Value("#{'${hzlc.gzldyid:}'.split(',')}")
    protected List<String> hzlcdyidList;
    @Value("#{'${ysbz.gzldyid:}'.split(',')}")
    protected List<String> ysbzdyidList;
    @Value("#{'${clfmmzy.djxl:}'.split(',')}")
    protected List<String> clfmmzydjxlList;

    /**
     * 连云港土地交易服务费计算逻辑
     */
    @Value("${lyg.tdjyfwf:}")
    protected String tdjyfwf;
    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 收费标准：不收登记费,工本费第一本不免费
     */
    @Value("#{'${clfmmzy.tssfdjyy:}'.split(',')}")
    protected List<String> tssfdjyyList;
    /**
     * 宅基地相关流程工作流定义id
     */
    @Value("#{'${zjdlc.gzldyid:}'.split(',')}")
    protected List<String> zjdlcdyidList;
    /*
     * 不需要收费项目的数据 key为fwxz，value为工作流定义id集合
     * */
    @Autowired
    BdcSlSfxmConfig bdcSlSfxmConfig;

    @Value("${html.version:}")
    private String version;

    @Value("${fskp.printDzfp.url:}")
    private String fskpPrintUrl;

    @Value("${sfxx.updateSfzt:false}")
    private boolean updateSfzt;

    /*是否删除金额为0 的收费项目*/
    @Value("${sfxx.sfxm.delete0je: false}")
    private boolean delete0Jexm;
    /*缴费书二维码url 是否转译*/
    @Value("${sfxx.jfsurl.sfzy:false}")
    private boolean jfsUrlSfzy;
    /*是否调用作废接口，true:调用作废接口，更新收费信息sfzf状态, false:不调用作废接口，更新收费信息sfzf状态*/
    @Value("${sfxx.zf.sfdyjk:true}")
    private boolean zfSfdyjk;

    /*
     * 是否过滤土地收益金
     * */
    @Value("${sfxx.sfxm.sfgltdsyj:false}")
    private boolean sfgltdsyj;

    @Value("${sfxx.sfxm.tdsyqsfxmdm:}")
    private String tdsyqsfxmdm;
    /**
     * 宅基地相关流程工作流定义id
     */
    @Value("${sfxx.zdfkbcxjs:false}")
    private boolean zdfkbcxjs;
    //缴款情况_涉税未缴费状态
    private final static int JKQK_SS_WJK = 2;

    /*
     * 经济适用房收费页面提示信息
     * */
    private static final String JJSYF_TSXX = "房屋性质为经济适用房，无需收费";

    @ResponseBody
    @GetMapping("/xx/new")
    public Object queryBdcSlSfxxNew(String processInsId, String xmid) {
        BdcSlSfxxDTO bdcSlSfxxDTO = bdcSlSfxxFeignService.generateSfxx(processInsId, xmid);
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(processInsId);
        if (StringUtils.isNotBlank(xmid)) {
            bdcXmQO.setXmid(xmid);
        }
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            if (MapUtils.isNotEmpty(bdcSlSfxmConfig.getNosfxm())) {
                BdcQl bdcQl = bdcQllxFeignService.queryQlxx(bdcXmDOList.get(0).getXmid());
                if (Objects.nonNull(bdcQl) && bdcQl instanceof BdcFdcqDO) {
                    BdcFdcqDO bdcFdcqDO = (BdcFdcqDO) bdcQl;
                    if (MapUtils.getString(bdcSlSfxmConfig.getNosfxm(), String.valueOf(bdcFdcqDO.getFwxz()), StringUtils.EMPTY).contains(bdcXmDOList.get(0).getGzldyid())) {
                        bdcSlSfxxDTO.setTsxx(JJSYF_TSXX);
                        bdcSlSfxxDTO.setSfsf(false);
                    }
                }
            }
            // 收费信息页面增加用途信息
            bdcSlSfxxDTO.setGhyt(bdcXmDOList.get(0).getDzwyt());
            // 收费信息页面增加区县代码
            bdcSlSfxxDTO.setQxdm(bdcXmDOList.get(0).getQxdm());
        }
        //处理是否展示必须现场收费
        if (sfgltdsyj) {
            //权利人收费信息
            BdcSlSqrSfxxDTO qlrSfxxDTO = bdcSlSfxxDTO.getBdcSlQlrSfxxDTO();
            BdcSlSqrSfxxDTO ywrSfxxDTO = bdcSlSfxxDTO.getBdcSlYwrSfxxDTO();
            //判断现场收费
            judgeXcsf(qlrSfxxDTO);
            judgeXcsf(ywrSfxxDTO);
        }
        return bdcSlSfxxDTO;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 加载一窗受理流程收费信息
     */
    @ResponseBody
    @GetMapping("/xx/ycsl")
    public Object generateYcslSfxx(String processInsId, String xmid) {
        return bdcSlSfxxFeignService.generateYcslSfxx(processInsId, xmid);
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取收费信息(南通)
     */
    @ResponseBody
    @GetMapping("/xx/nantong")
    public Object queryBdcSlSfxxNt(String processInsId, String xmid) {
        return bdcSlSfxxFeignService.generateSfxxNt(processInsId, xmid);
    }

    /**
     * @return java.lang.Object
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [processInsId, xmid]
     * @description 查询收费信息（包含作废数据）
     */
    @ResponseBody
    @GetMapping("/xx/nantong/bhzf")
    public Object queryBdcSlSfxxbhzf(String processInsId, String xmid) {
        return bdcSlSfxxFeignService.queryBdcSlSfxxBhzf(processInsId, xmid);
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 获取收费配置
     */
    @ResponseBody
    @GetMapping("/xx/nantong/pz")
    public Object queryBdcSlSfxxNtPz(String processInsId, String xmid) {
        BdcSlSfxxDO bdcSlSfxxDO = new BdcSlSfxxDO();

        String qxdm = userManagerUtils.getRegionCodeByUserName(userManagerUtils.getCurrentUserName());
        // 当配置中存在对应区县代码的默认收费信息时 读取配置
        Map mrsfxxMap = bdcSlSfxxFeignService.queryMrsfxxPz(qxdm);

        if (mrsfxxMap != null) {
            bdcSlSfxxDO.setSfdwdm(StringUtils.isBlank(bdcSlSfxxDO.getSfdwdm()) ? MapUtils.getString(mrsfxxMap, "sfdwdm") : bdcSlSfxxDO.getSfdwdm());
            bdcSlSfxxDO.setSfrxm(StringUtils.isBlank(bdcSlSfxxDO.getSfrxm()) ? MapUtils.getString(mrsfxxMap, "sfrxm") : bdcSlSfxxDO.getSfrxm());
            bdcSlSfxxDO.setSfdwmc(StringUtils.isBlank(bdcSlSfxxDO.getSfdwmc()) ? MapUtils.getString(mrsfxxMap, "sfdwmc") : bdcSlSfxxDO.getSfdwmc());
            bdcSlSfxxDO.setSfrzh(StringUtils.isBlank(bdcSlSfxxDO.getSfrzh()) ? MapUtils.getString(mrsfxxMap, "sfrzh") : bdcSlSfxxDO.getSfrzh());
            bdcSlSfxxDO.setSfrkhyhwddm(StringUtils.isBlank(bdcSlSfxxDO.getSfrkhyhwddm()) ? MapUtils.getString(mrsfxxMap, "sfrkhyhwddm") : bdcSlSfxxDO.getSfrkhyhwddm());
            bdcSlSfxxDO.setSfrkhyh(StringUtils.isBlank(bdcSlSfxxDO.getSfrkhyh()) ? MapUtils.getString(mrsfxxMap, "sfrkhyh") : bdcSlSfxxDO.getSfrkhyh());
        } else {
            bdcSlSfxxDO.setSfdwdm(StringUtils.isBlank(bdcSlSfxxDO.getSfdwdm()) ? "077022" : bdcSlSfxxDO.getSfdwdm());
            bdcSlSfxxDO.setSfrxm(StringUtils.isBlank(bdcSlSfxxDO.getSfrxm()) ? "南通市财政局" : bdcSlSfxxDO.getSfrxm());
            bdcSlSfxxDO.setSfdwmc(StringUtils.isBlank(bdcSlSfxxDO.getSfdwmc()) ? "南通市不动产登记中心" : bdcSlSfxxDO.getSfdwmc());
            bdcSlSfxxDO.setSfrzh(StringUtils.isBlank(bdcSlSfxxDO.getSfrzh()) ? "10707001040001772" : bdcSlSfxxDO.getSfrzh());
            bdcSlSfxxDO.setSfrkhyhwddm(StringUtils.isBlank(bdcSlSfxxDO.getSfrkhyhwddm()) ? "102" : bdcSlSfxxDO.getSfrkhyhwddm());
            bdcSlSfxxDO.setSfrkhyh(StringUtils.isBlank(bdcSlSfxxDO.getSfrkhyh()) ? "农业银行" : bdcSlSfxxDO.getSfrkhyh());
        }

        return bdcSlSfxxDO;
    }

    @ResponseBody
    @PostMapping("/xx/ts")
    public Object tsBdcSlSfxxNew(String sfxxid, String qlrlb, String gzlslid) {
        return bdcSlSfxxFeignService.rgtsdjfxx(sfxxid, qlrlb, "",  gzlslid);
    }

    /**
     * 作废待缴费信息，南通大市使用qxdm配置不同beanName调用接口模式
     *
     * @param bdcSlSfxxDO 不动产受理收费信息DO
     * @return
     */
    @ResponseBody
    @PostMapping("/xx/zfdjfxx")
    public Object zfsfxx(@RequestBody BdcSlSfxxDO bdcSlSfxxDO) {
        if (StringUtils.isAnyBlank(bdcSlSfxxDO.getGzlslid(), bdcSlSfxxDO.getTfyy(), bdcSlSfxxDO.getQlrlb())) {
            throw new AppException(ErrorCode.MISSING_ARG, "缺少参数工作流实例ID或作废原因或权利人类别信息。");
        }
        // 查询参数有 xmid，gzlslid,qlrlb
        List<BdcSlSfxxDO> bdcSlSfxxDOList = this.bdcSlSfxxFeignService.queryBdcSlSfxx(bdcSlSfxxDO);
        if (CollectionUtils.isEmpty(bdcSlSfxxDOList)) {
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到收费信息");
        }
        if (BdcSfztEnum.YJF.key().equals(bdcSlSfxxDOList.get(0).getSfzt())) {
            throw new AppException(ErrorCode.CUSTOM, "当前收费信息已缴费无法作废。");
        }
        bdcSlSfxxDO.setSfxxid(bdcSlSfxxDOList.get(0).getSfxxid());
        CommonResponse commonResponse = this.bdcSlSfxxFeignService.zfdjfxx(bdcSlSfxxDO);
        return commonResponse;
    }

    /**
     * @param sfxxid 收费信息ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 推送待缴信息
     */
    @ResponseBody
    @PostMapping("/xx/tsdjfxx")
    public BdcTsdjfxxResponseDTO tsdjfxx(String sfxxid) {
        return bdcSlSfxxFeignService.tsdjfxx(sfxxid);
    }

    /**
     * @param sfxxid 收费信息id
     * @return 收费项目
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 通过收费信息id获取收费项目(合肥版本, 在后台对费用数量进行处理, 后续调整到前台)
     */
    @ResponseBody
    @GetMapping("/xm")
    public Object queryBdcSlSfxm(String sfxxid, String gzlslid, String xmid, String qlrlb, Boolean sfcxjs) throws Exception {
        //单个流程he批量流程收费单是一个，根据gzlslid查当前生成多少本证，组合流程收费单多个用xmid单独查当前项目生成的证书本数
        if (StringUtils.isBlank(sfxxid) || StringUtils.isBlank(gzlslid) && StringUtils.isBlank(xmid)) {
            return Collections.emptyList();
        } else {
            //TODO 已移植到accept包中,后期需要调整将UI包方法删除！！！
            return countSfxm(sfxxid, gzlslid, xmid, sfcxjs != null ? sfcxjs : false);
        }
    }

    /**
     * @param sfxxid 收费信息id
     * @return 土地收费项目
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 通过收费信息id获取收费项目
     */
    @ResponseBody
    @GetMapping("/tdsfxm")
    public Object queryBdcSlTdSfxm(String sfxxid, String gzlslid, String xmid, Boolean sfcxjs) throws Exception {
        //TODO 已移植到accept包中,后期需要调整将UI包方法删除！！！
        //单个流程he批量流程收费单是一个，根据gzlslid查当前生成多少本证，组合流程收费单多个用xmid单独查当前项目生成的证书本数
        if (StringUtils.isBlank(sfxxid) || StringUtils.isBlank(gzlslid) && StringUtils.isBlank(xmid)) {
            return Collections.emptyList();
        } else {
            //只查询土地交易的费用
            List<BdcSlSfxmDO> bdcSlSfxmDOList = bdcSlSfxmFeignService.listBdcSlSfxmBySfxxid(sfxxid);
            if (StringUtils.isNotBlank(tdsyqsfxmdm)) {
                bdcSlSfxmDOList = bdcSlSfxmDOList.stream().filter(bdcSlSfxmDO -> bdcSlSfxmDO.getSfxmdm().equals(tdsyqsfxmdm)).collect(Collectors.toList());
            } else {
                return Collections.emptyList();
            }
            if (CollectionUtils.isEmpty(bdcSlSfxmDOList)) {
                return Collections.emptyList();
            }
            return bdcSlSfxxFeignService.countTdsyqJyfwf(bdcSlSfxmDOList, gzlslid, sfcxjs);
        }
    }

    /**
     * @param sfxxid 收费信息ID
     * @return 收费项目
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 通过收费信息id获取收费项目
     */
    @ResponseBody
    @GetMapping("/xm/new")
    public Object listBdcSlSfxmBySfxxid(String sfxxid) {
        if (StringUtils.isBlank(sfxxid)) {
            return Collections.emptyList();
        } else {
            return bdcSlSfxmFeignService.listBdcSlSfxmBySfxxid(sfxxid);
        }
    }

    /**
     * @param processInsId 实例id
     * @return 收费项目配置信息
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 获取收费项目配置(当前流程配置收费项目)
     */
    @ResponseBody
    @GetMapping("/xm/pz")
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
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 存在减免政策的
     * @date : 2020/9/10 11:11
     */
    @ResponseBody
    @GetMapping("/xm/pz/jmzc")
    public Object queryJmzcSfxmPz(String gzlslid, String xmid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException(messageProvider.getMessage("message.noparameter"));
        }
        HashMap<String, List<BdcSlSfxmDTO>> resultMap = new HashMap<>(4);
        BdcXmQO bdcXmQO = new BdcXmQO();
        if (StringUtils.isNotBlank(xmid)) {
            bdcXmQO.setXmid(xmid);
        } else {
            bdcXmQO.setGzlslid(gzlslid);
        }
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            List<BdcSlSfxmDTO> ySfxmPzList = listBdcSlSfxmDTOByDjxl(bdcXmDOList.get(0).getDjxl());
            resultMap.put("ysfxmpz", ySfxmPzList);
//            if(CollectionUtils.isNotEmpty(ySfxmPzList)) {
//                ArrayList<BdcSlSfxmDTO> jmSfxmPzList = (ArrayList<BdcSlSfxmDTO>) depCopy(ySfxmPzList);
//                List<BdcSlSfxmDTO> bdcSlSfxmDTOList = changeSfbzByJmzc(gzlslid, jmSfxmPzList);
//                resultMap.put("jmsfxmpz", bdcSlSfxmDTOList);
//            }else{
//                resultMap.put("jmsfxmpz", new ArrayList<>());
//            }
            resultMap.put("jmsfxmpz", new ArrayList<>());
            LOGGER.info("标准收费配置{}", resultMap.get("ysfxmpz"));
            LOGGER.info("减免收费配置{}", resultMap.get("jmsfxmpz"));
            return resultMap;
        }
        return null;
    }

    /**
     * @return 收费项目配置信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取所有收费项目配置(所有配置收费项目)
     */
    @ResponseBody
    @GetMapping("/xm/pz/all")
    public List<BdcSlSfxmDTO> queryBdcSlSfxmPzAll() {
        return bdcSlSfxmPzFeignService.listAllBdcSlSfxmDTO();
    }


    /**
     * @param processInsId 实例id
     * @return 收费项目配置信息
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 获取收费项目配置
     */
    @ResponseBody
    @GetMapping("/xm/pz/ycsl")
    public Object queryBdcSlSfxmPzForYcsl(String processInsId, String xmid) {
        if (StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException(messageProvider.getMessage("message.noparameter"));
        }
        BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxFeignService.queryBdcSlJbxxByGzlslid(processInsId);
        if (bdcSlJbxxDO == null) {
            return null;
        }
        BdcSlXmQO bdcSlXmQO = new BdcSlXmQO();
        if (StringUtils.isNotBlank(xmid)) {
            bdcSlXmQO.setXmid(xmid);
        } else {
            bdcSlXmQO.setJbxxid(bdcSlJbxxDO.getJbxxid());
        }
        List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmFeignService.listBdcSlXm(bdcSlXmQO);
        if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
            return listBdcSlSfxmDTOByDjxl(bdcSlXmDOList.get(0).getDjxl());
        }
        return null;
    }

    /**
     * 根据登记小类获取收费项目配置
     *
     * @return 收费项目配置信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @GetMapping("/xm/pz/{djxl}")
    public List<BdcSlSfxmDTO> queryBdcSlSfxmPzByDjxl(@PathVariable(value = "djxl") String djxl) {
        if (StringUtils.isBlank(djxl)) {
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到登记小类信息");
        }
        return bdcSlSfxmPzFeignService.listBdcSfxmDTOByDjxl(djxl);
    }

    /**
     * @param json 集合
     * @return 更新数量
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 更新收费信息
     */
    @ResponseBody
    @PatchMapping("/xx")
    public Integer saveOrUpdateBdcSlSfxx(@RequestBody String json) {
        BdcSlSfxxDO bdcSlSfxxDO = JSONObject.parseObject(json, BdcSlSfxxDO.class);
        if (bdcSlSfxxDO == null || StringUtils.isBlank(bdcSlSfxxDO.getSfxxid())) {
            throw new MissingArgumentException("保存收费信息缺失参数");
        }
        if (updateSfzt) {
            bdcSlSfxxDO.setSfzt(CommonConstantUtils.SFZT_YJF);
        }
        LOGGER.warn("收费页面点击保存按钮保存收费信息{}", JSON.toJSONString(bdcSlSfxxDO));
        return bdcSlSfxxFeignService.saveOrUpdateBdcSlSfxx(bdcSlSfxxDO);
    }

    /**
     * 保存收费信息
     *
     * @param bdcSlSfxxDO
     * @return 更新结果
     */
    @ResponseBody
    @PostMapping("/xx")
    public int saveBdcSlSfxx(@RequestBody BdcSlSfxxDO bdcSlSfxxDO) {
        if (bdcSlSfxxDO == null || StringUtils.isBlank(bdcSlSfxxDO.getSfxxid())) {
            throw new MissingArgumentException("保存收费信息缺失参数");
        }

        int count = 0;
        count = bdcSlSfxxFeignService.saveOrUpdateBdcSlSfxx(bdcSlSfxxDO);

        return count;
    }

    /**
     * 批量修改收费信息
     *
     * @param bdcSlSfxxDOList
     * @return 更新结果
     */
    @ResponseBody
    @PutMapping("/pl/xx")
    public void plSaveBdcSlSfxx(@RequestBody List<BdcSlSfxxDO> bdcSlSfxxDOList) {
        if (CollectionUtils.isEmpty(bdcSlSfxxDOList)) {
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到不动产受理收费信息");
        }
        this.bdcSlSfxxFeignService.updateBdcSlSfxxList(bdcSlSfxxDOList);
    }

    /**
     * @param json 集合
     * @return 保存数量
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 保存更新收费项目
     */
    @ResponseBody
    @PatchMapping("/xm")
    public Integer saveOrUpdateSfxm(@RequestBody String json) {
        Integer count = 0;
        for (Object obj : JSON.parseArray(json)) {
            BdcSlSfxmDO bdcSlSfxmDO = JSONObject.parseObject(obj.toString(), BdcSlSfxmDO.class);
            if (bdcSlSfxmDO != null) {
                JSONObject object = JSONObject.parseObject(obj.toString());
                LOGGER.warn("收费页面点击保存按钮保存收费项目信息{}", JSON.toJSONString(bdcSlSfxmDO));
                if (object.get("cz") != null) {
                    if (StringUtils.equals(object.get("cz").toString(), Constants.FUN_UPDATE)) {
                        count += bdcSlSfxmFeignService.updateBdcSlSfxm(bdcSlSfxmDO);
                    }
                    if (StringUtils.equals(object.get("cz").toString(), Constants.FUN_INSERT)) {
                        count += bdcSlSfxmFeignService.saveOrUpdateBdcSlSfxm(bdcSlSfxmDO);
                    }
                }
            }
        }
        return count;
    }

    /**
     * @param sfxmid 收费项目id
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 通过收费项目id删除收费项目
     */
    @ResponseBody
    @DeleteMapping("/xm")
    public void deleteBdcsfxm(@RequestParam("sfxmid") String sfxmid) {
        BdcSlSfxmDO bdcSlSfxmDO = bdcSlSfxmFeignService.queryBdcSlSfxmBySfxmid(sfxmid);
        bdcSlSfxmFeignService.deleteBdcSlSfxmBySfxmid(sfxmid);
        if (bdcSlSfxmDO != null) {
            LOGGER.warn("当前收费信息id={}收费项目被删除，详细信息{}", bdcSlSfxmDO.getSfxxid(), JSON.toJSONString(bdcSlSfxmDO));
            List<BdcSlSfxmDO> bdcSlSfxmDOList = bdcSlSfxmFeignService.listBdcSlSfxmBySfxxid(bdcSlSfxmDO.getSfxxid());
            for (BdcSlSfxmDO bdcSlSfxm : bdcSlSfxmDOList) {
                if (bdcSlSfxm.getXh() != null && bdcSlSfxmDO.getXh() != null && bdcSlSfxm.getXh() > bdcSlSfxmDO.getXh()) {
                    bdcSlSfxm.setXh(bdcSlSfxm.getXh() - 1);
                    bdcSlSfxmFeignService.updateBdcSlSfxm(bdcSlSfxm);

                }
            }
        }
    }

    /**
     * @param sfxxid 收费信息ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 通过收费信息ID删除收费项目
     */
    @ResponseBody
    @DeleteMapping("/sfxx")
    public void deleteBdcSlSfxx(@RequestParam("sfxxid") String sfxxid) {
        if (StringUtils.isBlank(sfxxid)) {
            throw new MissingArgumentException("未获取到收费信息ID");
        }
        this.bdcSlSfxxFeignService.deleteBdcSlSfxxBySfxxid(sfxxid);
    }

    /**
     * @param sfxmid 收费项目id
     * @param czlx   操作类型
     * @return 更新收费项目数量
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 修改收费项目顺序号
     */
    @ResponseBody
    @GetMapping(value = "/sxh")
    public Integer changeSfxmSxh(String sfxmid, String czlx) {
        Integer count = 0;
        List<BdcSlSfxmDO> bdcSlSfxmDOS = new ArrayList<>();
        BdcSlSfxmDO bdcSlSfxmDO = bdcSlSfxmFeignService.queryBdcSlSfxmBySfxmid(sfxmid);
        if (bdcSlSfxmDO != null) {
            List<BdcSlSfxmDO> bdcSlSfxmList = null;
            if (StringUtils.isNotBlank(bdcSlSfxmDO.getSfxxid())) {
                bdcSlSfxmList = bdcSlSfxmFeignService.listBdcSlSfxmBySfxxid(bdcSlSfxmDO.getSfxxid());
            }
            if (CollectionUtils.isNotEmpty(bdcSlSfxmList) && bdcSlSfxmList.size() > 1) {
                for (int i = 0; i < bdcSlSfxmList.size(); i++) {
                    BdcSlSfxmDO bdcSlSfxm = bdcSlSfxmList.get(i);
                    if (StringUtils.equals(bdcSlSfxm.getSfxmid(), sfxmid)) {
                        BdcSlSfxmDO changeBdcSlSfxm = null;
                        if (StringUtils.equals(czlx, Constants.SXH_UP) && i != 0) {
                            changeBdcSlSfxm = bdcSlSfxmList.get(i - 1);
                        }
                        if (StringUtils.equals(czlx, Constants.SXH_DOWN) && i != (bdcSlSfxmList.size() - 1)) {
                            changeBdcSlSfxm = bdcSlSfxmList.get(i + 1);
                        }
                        if (changeBdcSlSfxm != null) {
                            int sxh1 = bdcSlSfxmDO.getXh();
                            int sxh2 = changeBdcSlSfxm.getXh();
                            bdcSlSfxmDO.setXh(sxh2);
                            changeBdcSlSfxm.setXh(sxh1);
                            bdcSlSfxmDOS.add(bdcSlSfxmDO);
                            bdcSlSfxmDOS.add(changeBdcSlSfxm);
                        }
                    }
                }
            }
        }
        if (CollectionUtils.isNotEmpty(bdcSlSfxmDOS)) {
            for (BdcSlSfxmDO bdcSlSfxm : bdcSlSfxmDOS) {
                count += bdcSlSfxmFeignService.updateBdcSlSfxm(bdcSlSfxm);
            }
        }
        return count;
    }

    /**
     * @param djxl 登记小类
     * @return 不动产受理收费项目类
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
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


    /*
     * 处理收费单页面收费项目自动计算信息
     * */
    private List<BdcSlSfxmDO> countSfxm(String sfxxid, String gzlslid, String xmid, Boolean sfcxjs) throws Exception {
        List<BdcSlSfxmDO> bdcSlSfxmDOList;
        List<BdcSlSfxmDO> bdcSlSfxmList = new ArrayList<>();
        List<BdcXmDO> bdcXmList;
        if (StringUtils.isNotBlank(xmid)) {
            //组合流程用xmid查询
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setXmid(xmid);
            bdcXmList = bdcXmFeignService.listBdcXm(bdcXmQO);
            //判断是否批量组合
            int lclx = bdcXmFeignService.makeSureBdcXmLx(gzlslid);
            if (lclx == 4) {
                //批量组合流程,工本费需乘以当前项目对应登记小类的项目数量
                bdcXmQO = new BdcXmQO();
                bdcXmQO.setGzlslid(gzlslid);
                bdcXmQO.setDjxl(bdcXmList.get(0).getDjxl());
                bdcXmList = bdcXmFeignService.listBdcXm(bdcXmQO);
            }
        } else {
            //单个批量流程用gzlslid
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setGzlslid(gzlslid);
            bdcXmList = bdcXmFeignService.listBdcXm(bdcXmQO);
        }
        BdcSlSfxxDO bdcSlSfxxDO = bdcSlSfxxFeignService.queryBdcSlSfxxBySfxxid(sfxxid);
        if (CollectionUtils.isNotEmpty(bdcXmList)) {
            //---盐城房屋性质为经济适用房，删除所有收费项目
            bdcSlSfxmDOList = bdcSlSfxmFeignService.listBdcSlSfxmBySfxxid(sfxxid);
            BdcQl bdcQl = bdcQllxFeignService.queryQlxx(bdcXmList.get(0).getXmid());
            if (MapUtils.isNotEmpty(bdcSlSfxmConfig.getNosfxm()) && CollectionUtils.isNotEmpty(bdcSlSfxmDOList)) {
                if (Objects.nonNull(bdcQl) && bdcQl instanceof BdcFdcqDO) {
                    BdcFdcqDO bdcFdcqDO = (BdcFdcqDO) bdcQl;
                    if (MapUtils.getString(bdcSlSfxmConfig.getNosfxm(), String.valueOf(bdcFdcqDO.getFwxz()), StringUtils.EMPTY).contains(bdcXmList.get(0).getGzldyid())) {
                        bdcSlSfxmFeignService.deleteBdcSlSfxmBySfxxid(sfxxid);
                        return Collections.emptyList();
                    }
                }
            }
        }
        bdcSlSfxmDOList = bdcSlSfxmFeignService.listBdcSlSfxmBySfxxid(sfxxid);
        if (sfgltdsyj) {
            //过滤掉土地收益金的信息
            bdcSlSfxmDOList = bdcSlSfxmDOList.stream().filter(bdcSlSfxmDO -> !StringUtils.equals(bdcSlSfxmDO.getSfxmdm(), tdsyqsfxmdm)).collect(Collectors.toList());
        }
        if (CollectionUtils.isNotEmpty(bdcSlSfxmDOList) && CollectionUtils.isNotEmpty(bdcXmList)) {
            for (BdcSlSfxmDO bdcSlSfxmDO : bdcSlSfxmDOList) {

                //土地交易服务费计算
                if (StringUtils.isNotBlank(tdjyfwf) && StringUtils.equals(bdcSlSfxmDO.getSfxmdm(), tdjyfwf)){
                    bdcSlSfxmFeignService.countTdjyfwf(bdcSlSfxmDO.getSfxmid(), gzlslid, xmid);
                }

                //收取工本费时根据证书数量动态改变数量
                //工本费第一次加载收费单页面的时候读取接口获取工本数量自动计算，加载完后保存到数据库，后续加载直接去读数据库
                if (StringUtils.equals(bdcSlSfxmDO.getSfxmbz(), CommonConstantUtils.SFXM_BZ_GBF) && (bdcSlSfxmDO.getSl() == null || sfcxjs)) {
                    /*
                     * 计算工本费，一般是第一本默认不收费，证书数量加一本收一本的费用
                     * */
                    queryGbfSl(bdcXmList, bdcSlSfxmDO, xmid, gzlslid, sfcxjs);
                }
                //计算登记住宅费
                //sf_xm_pz配置表需要两种登记费都配置，如果有一个收费项目的应收金额为空说明没有计算该登记费，删掉
                //sfxmdm是登记费的或者标准包含 登记费三个字的
                if ((StringUtils.equals(bdcSlSfxmDO.getSfxmdm(), CommonConstantUtils.SFXM_BZ_DJF_DM) || StringUtils.contains(bdcSlSfxmDO.getSfxmbz(), "登记费")) && (bdcSlSfxmDO.getYsje() == null || sfcxjs)) {
                    //存量房买卖转移登记登记原因为"权利份额转移"不收登记费，原来项目中的全部删除
                    if (CollectionUtils.isNotEmpty(bdcXmList) && clfmmzydjxlList.contains(bdcXmList.get(0).getDjxl()) && CollectionUtils.isNotEmpty(tssfdjyyList) && tssfdjyyList.contains(bdcXmList.get(0).getDjyy())) {
                        bdcSlSfxmFeignService.deleteBdcSlSfxmBySfxmid(bdcSlSfxmDO.getSfxmid());
                    } else {
                        BdcSlSfxmsDTO bdcSlSfxmsDTO = new BdcSlSfxmsDTO();
                        bdcSlSfxmsDTO.setBdcSlSfxmDO(bdcSlSfxmDO);
                        bdcSlSfxmsDTO.setBdcXmDOList(bdcXmList);
                        //组合流程用xmid关联
                        if (StringUtils.isNotBlank(xmid) && bdcXmList.size() <= 1) {
                            //单个组合
                            bdcSlSfxmsDTO.setSfzhlc(true);
                        } else {
                            //单个和批量流程,批量组合
                            bdcSlSfxmsDTO.setSfzhlc(false);
                        }
                        bdcSlSfxmsDTO.setSfcxjs(sfcxjs);
                        bdcSlSfxmsDTO.setVersion(systemVersion);
                        bdcSlSfxmDO = bdcSlSfxmFeignService.countDjf(bdcSlSfxmsDTO);
                        if (bdcSlSfxmDO.getYsje() == null) {
                            bdcSlSfxmFeignService.deleteBdcSlSfxmBySfxmid(bdcSlSfxmDO.getSfxmid());
                        }
                    }
                }

                // 优惠信息为全免时，不删除收费金额为0 的收费项目
                if (delete0Jexm && Objects.nonNull(bdcSlSfxmDO.getYsje()) && bdcSlSfxmDO.getYsje() == 0
                        && !Objects.equals(bdcSlSfxmDO.getYh(), 3)) {
                    bdcSlSfxmFeignService.deleteBdcSlSfxmBySfxmid(bdcSlSfxmDO.getSfxmid());
                } else {
                    if (bdcSlSfxmDO.getYsje() != null) {
                        bdcSlSfxmList.add(bdcSlSfxmDO);
                    }
                }
            }
        }
        if (CollectionUtils.isNotEmpty(bdcSlSfxmList) && bdcSlSfxmDOList.size() > 1) {
            //根据收费项目标准分组处理，分组后的收费项目如果数量大于1，只保留一条数据，其余删除
            bdcSlSfxmList = bdcSlSfxmList.stream().filter(bdcSlSfxmDO -> StringUtils.isNotBlank(bdcSlSfxmDO.getSfxmbz())).collect(Collectors.toList());
            Map<String, List<BdcSlSfxmDO>> sfxmMap = bdcSlSfxmList.stream().collect(Collectors.groupingBy(BdcSlSfxmDO::getSfxmbz));
            for (Map.Entry<String, List<BdcSlSfxmDO>> entry : sfxmMap.entrySet()) {
                if (entry.getValue().size() > 1) {
                    for (int i = 0; i < entry.getValue().size(); i++) {
                        if (i > 0) {
                            bdcSlSfxmFeignService.deleteBdcSlSfxmBySfxmid(entry.getValue().get(i).getSfxmid());
                        }
                    }
                }
            }
        }
        bdcSlSfxmDOList = bdcSlSfxmFeignService.listBdcSlSfxmBySfxxid(sfxxid);
        Double hj = bdcSlSfxmDOList.stream().filter(sfxm -> null != sfxm.getSsje()).map(t -> new BigDecimal(String.valueOf(t.getSsje())))
                .reduce((m, n) -> m.add(n)).map(BigDecimal::doubleValue).orElse(0d);
        if (Objects.nonNull(bdcSlSfxxDO)) {
            bdcSlSfxxDO.setHj(hj);
            //如果合计为0，认定为不收费，改变sfzt
            if (Objects.equals(0d, hj) && !CommonConstantUtils.SYSTEM_VERSION_YC.equals(version)) {
                bdcSlSfxxDO.setSfzt(BdcSfztEnum.BSF.key());
            }
            if (sfgltdsyj) {
                bdcSlSfxmDOList = bdcSlSfxmDOList.stream().filter(bdcSlSfxmDO -> !StringUtils.equals(bdcSlSfxmDO.getSfxmdm(), tdsyqsfxmdm)).collect(Collectors.toList());
            }
            //处理缴款人
            if (CollectionUtils.isNotEmpty(bdcXmList)) {
                //判断缴款人是否发生改变
                BdcQlrQO bdcQlrQO = new BdcQlrQO();
                bdcQlrQO.setXmid(bdcXmList.get(0).getXmid());
                String yjfrxm = bdcSlSfxxDO.getJfrxm();
                if (StringUtils.equals(CommonConstantUtils.QLRLB_QLR, bdcSlSfxxDO.getQlrlb())) {
                    bdcSlSfxxDO.setJfrxm(bdcXmList.get(0).getQlr());
                    if (StringUtils.isBlank(bdcSlSfxxDO.getJfrdh())) {
                        bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
                        List<BdcQlrDO> bdcQlrDoList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
                        bdcSlSfxxDO.setJfrdh(getQlrDh(bdcQlrDoList).getDh());
                    }
                } else if (StringUtils.equals(CommonConstantUtils.QLRLB_YWR, bdcSlSfxxDO.getQlrlb())) {
                    bdcSlSfxxDO.setJfrxm(bdcQlrFeignService.queryQlrsYbzs(gzlslid, CommonConstantUtils.QLRLB_YWR, bdcXmList.get(0).getDjxl()));
                    if (StringUtils.isBlank(bdcSlSfxxDO.getJfrdh())) {
                        bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_YWR);
                        List<BdcQlrDO> bdcQlrDoList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
                        bdcSlSfxxDO.setJfrdh(getQlrDh(bdcQlrDoList).getDh());
                    }
                }
                // 设置是否月结
                if ((null == bdcSlSfxxDO.getSfyj() || (StringUtils.isNotBlank(yjfrxm) && !StringUtils.equals(yjfrxm, bdcSlSfxxDO.getJfrxm())))) {
                    bdcSlSfxxDO.setSfyj(bdcSlSfxxFeignService.querySfyjByJfrxm(bdcSlSfxxDO.getJfrxm(), bdcXmList.get(0).getGzldyid()));
                }
            }
            if (Objects.isNull(bdcSlSfxxDO.getSfsj())) {
                bdcSlSfxxDO.setSfsj(new Date());
            }
            bdcSlSfxxFeignService.updateBdcSlSfxx(bdcSlSfxxDO);
        }
        return bdcSlSfxmDOList;
    }

    /**
     * 获取权利人电话
     *
     * @param bdcQlrDOList
     * @return
     */
    private BdcQlrDO getQlrDh(List<BdcQlrDO> bdcQlrDOList) {
        if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
            BdcQlrDO bdcQlrDO = bdcQlrDOList.get(0);
            if (StringUtils.isBlank(bdcQlrDO.getDh())) {
                List<String> dhList = bdcQlrDOList.stream().filter(t -> StringUtils.isNotBlank(t.getDh())).map(BdcQlrDO::getDh)
                        .collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(dhList)) {
                    bdcQlrDO.setDh(dhList.get(0));
                } else {
                    dhList = bdcQlrDOList.stream().filter(t -> StringUtils.isNotBlank(t.getDlrdh())).map(BdcQlrDO::getDlrdh)
                            .collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(dhList)) {
                        bdcQlrDO.setDh(dhList.get(0));
                    }
                }
            }
            return bdcQlrDO;
        }
        return new BdcQlrDO();
    }


    @ResponseBody
    @GetMapping(value = "/listsfxxbypage")
    public Object listSfxxByPage(@LayuiPageable Pageable pageable, String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("gzlslid");
        }
        List<BdcSlSfxxDO> bdcSlSfxxDOList = null;
        if (StringUtils.isNotBlank(version) && StringUtils.equals(version, CommonConstantUtils.SYSTEM_VERSION_NT)) {
            bdcSlSfxxDOList = bdcSlSfxxFeignService.listBdcSlSfxxByGzlslidBhzf(gzlslid);
        } else {
            bdcSlSfxxDOList = bdcSlSfxxFeignService.listBdcSlSfxxByGzlslid(gzlslid);
        }
        List<String> xmid = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
            for (BdcSlSfxxDO bdcSlSfxxDO : bdcSlSfxxDOList) {
                if (StringUtils.isNotBlank(bdcSlSfxxDO.getXmid())) {
                    xmid.add(bdcSlSfxxDO.getXmid());
                }
            }
        }
        if (CollectionUtils.isEmpty(xmid)) {
            return addLayUiCode(new PageImpl(new ArrayList(), pageable, 0));
        }
        BdcQlQO bdcQlQO = new BdcQlQO();
        bdcQlQO.setXmid(xmid);
        String bdcQlQoStr = JSON.toJSONString(bdcQlQO);
        Page<BdcQlPageResponseDTO> bdcQlPageResponseDTOPage = bdcXmFeignService.bdcQlPageByPageJson(pageable, bdcQlQoStr);
        //处理加等
        Integer lclx = bdcXmFeignService.makeSureBdcXmLx(gzlslid);
        if (CommonConstantUtils.LCLX_PLZH.equals(lclx) && CollectionUtils.isNotEmpty(bdcQlPageResponseDTOPage.getContent())) {
            for (BdcQlPageResponseDTO bdcQlPageResponseDTO : bdcQlPageResponseDTOPage.getContent()) {
                bdcQlPageResponseDTO.setBdcdyh(bdcQlPageResponseDTO.getBdcdyh() + CommonConstantUtils.SUFFIX_PL);
                bdcQlPageResponseDTO.setZl(bdcQlPageResponseDTO.getZl() + CommonConstantUtils.SUFFIX_PL);
            }
        }
        return addLayUiCode(bdcQlPageResponseDTOPage);
    }

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 税费统缴查询受理项目数据
     * @date : 2022/9/27 15:12
     */
    @ResponseBody
    @GetMapping("/listsftjbypage")
    public Object listSftjByPage(@LayuiPageable Pageable pageable, String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("gzlslid");
        }
        BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxFeignService.queryBdcSlJbxxByGzlslid(gzlslid);
        if (Objects.isNull(bdcSlJbxxDO)) {
            throw new AppException("未获取到受理基本信息数据");
        }
        BdcSlXmQO bdcSlXmQO = new BdcSlXmQO();
        bdcSlXmQO.setJbxxid(bdcSlJbxxDO.getJbxxid());
        Page<BdcSlYwxxDTO> bdcSlYwxxDTOList = bdcSlXmFeignService.listBdcSlXmByPageJson(pageable, JSON.toJSONString(bdcSlXmQO));
        return addLayUiCode(bdcSlYwxxDTOList);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @param djxl    登记小类
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取收费项目数量(南通)
     */
    @ResponseBody
    @GetMapping("/sfxm/sl/nantong")
    public BdcSlSfxmSlDTO queryBdcSfxmSl(String gzlslid, String djxl) {
        if (StringUtils.isBlank(gzlslid)) {
            return new BdcSlSfxmSlDTO();
        } else {
            return bdcSlSfxxFeignService.querySfxmSl(gzlslid, djxl);
        }
    }

    @ResponseBody
    @GetMapping("/sfxm/save/nantong")
    public void saveSfxx(String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            bdcSlSfxxFeignService.autoCountSfxxNt(gzlslid);
        }
    }


    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取土地使用权面积, 房地产权取tdsyqmj, 土地取syqmj
     */
    private Double getTdsyqMj(List<String> xmidList) {
        Double syqmj = 0.00;
        if (CollectionUtils.isNotEmpty(xmidList)) {
            List<BdcQl> bdcQlList = bdcQllxFeignService.listQlxxByXmids(xmidList);
            if (CollectionUtils.isNotEmpty(bdcQlList)) {
                for (BdcQl bdcQl : bdcQlList) {
                    if (bdcQl instanceof BdcFdcqDO) {
                        BdcFdcqDO bdcFdcqDO = (BdcFdcqDO) bdcQl;
                        if (bdcFdcqDO.getTdsyqmj() != null) {
                            syqmj += bdcFdcqDO.getTdsyqmj();
                        }
                    } else if (bdcQl instanceof BdcJsydsyqDO) {
                        BdcJsydsyqDO bdcJsydsyqDO = (BdcJsydsyqDO) bdcQl;
                        if (bdcJsydsyqDO.getSyqmj() != null) {
                            syqmj += bdcJsydsyqDO.getSyqmj();
                        }
                    } else if (bdcQl instanceof BdcDyaqDO) {
                        BdcDyaqDO bdcDyaqDO = (BdcDyaqDO) bdcQl;
                        if (bdcDyaqDO.getTddymj() != null) {
                            syqmj += bdcDyaqDO.getTddymj();
                        }
                    }
                }
            }
        }
        return syqmj;
    }


    /**
     * @param xmids
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除项目时检查收费信息表存的xmid
     * @date : 2020/1/6 17:39
     */
    @ResponseBody
    @GetMapping("/checksfxx")
    public void checkSfxx(String[] xmids, String gzlslid, String djxl) {
        if (ArrayUtils.isNotEmpty(xmids)) {
            //查出删除后剩余的xm
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setDjxl(djxl);
            bdcXmQO.setGzlslid(gzlslid);
            List<BdcXmDO> bdcXmList = bdcXmFeignService.listBdcXm(bdcXmQO);
            for (String xmid : xmids) {
                //删除时根据xmid查询收费信息
                List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxFeignService.listBdcSlSfxxByXmid(xmid);
                if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList) && CollectionUtils.isNotEmpty(bdcXmList)) {
                    for (BdcSlSfxxDO bdcSlSfxx : bdcSlSfxxDOList) {
                        bdcSlSfxx.setXmid(bdcXmList.get(0).getXmid());
                        bdcSlSfxxFeignService.updateBdcSlSfxx(bdcSlSfxx);
                    }
                    //根据xmid获取到数据后就更新，不再寻找
                    break;
                }
            }
        }
    }


    /**
     * 校验缴库状态
     * 通过工作流实例ID校验缴库状态： true(已缴库) false(未缴库)
     *
     * @param gzlslid 工作流实例
     * @return boolean ｛@code true｝已缴库
     * ｛@code false｝未缴库
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @GetMapping("/checkJkzt")
    public boolean queryBdcSlSfxxNt(String gzlslid) {
        Preconditions.checkArgument(StringUtils.isNotBlank(gzlslid), "缺失工作流实例ID!");
        final Integer jfqk = bdcSlSfxxFeignService.checkJfqk(gzlslid);
        // 返回三种状态 0:未缴库, 2:涉税未缴库；可以推送缴库。
        //           -1:非线上缴费,1:已缴库；不可以推送缴库
        if (CommonConstantUtils.SF_F_DM.equals(jfqk) || JKQK_SS_WJK == jfqk) {
            return false;
        }
        return true;
    }

    /**
     * 调用exchange tsjkrk接口，推送缴库
     *
     * @param gzlslid 工作流实例ID
     * @return String  缴费结果{@code 0} 成功，其余非0表示失败
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @GetMapping("/tsjk")
    public Object tsjk(@RequestParam(value = "gzlslid", required = false) String gzlslid,
                       @RequestParam(value = "yjdh", required = false) String yjdh,
                       @RequestParam(value = "sfyj", required = false) String sfyj) {
        if (StringUtils.isBlank(gzlslid) && StringUtils.isBlank(yjdh)) {
            throw new AppException("缺失参数：工作流实例ID或月结单号。");
        }
        Map<String, String> paramMap = new HashMap<>(4);
        if (StringUtils.isNotBlank(gzlslid)) {
            Pair<String, String> pair = this.getLeftXmidAndRightSlbh(gzlslid);
            paramMap.put("xmid", pair.getLeft());
            paramMap.put("slbh", pair.getRight());
        }
        if (StringUtils.isNotBlank(yjdh)) {
            paramMap.put("xmid", "");
            paramMap.put("slbh", yjdh);
        }
        paramMap.put("sfyj", sfyj);
        return exchangeInterfaceFeignService.requestInterface("getSysAndJkrk", paramMap);
    }

    /**
     * 通过工作流实例id获取受理编号和第一条项目ID, 返回Pair键值对 Left:xmid; Right: slbh;
     *
     * @param gzlslid 工作流实例ID
     * @return Pair<xmid, slbh>
     */
    private Pair<String, String> getLeftXmidAndRightSlbh(String gzlslid) {
        List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        Preconditions.checkArgument(CollectionUtils.isNotEmpty(bdcXmDTOList), "未获取到不动产项目信息！");
        return Pair.of(bdcXmDTOList.get(0).getXmid(), bdcXmDTOList.get(0).getSlbh());
    }

    /**
     * @param gzlslid 工作流实例ID
     * @param xmid    项目ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description POS缴费(根据受理编号和项目ID推送待缴费信息外网生成支付订单)
     */
    @ResponseBody
    @PostMapping("/tsdjfxxByGzlslid")
    public BdcTsdjfxxResponseDTO tsdjfxxByGzlslid(String gzlslid, String xmid) {
        Preconditions.checkArgument(StringUtils.isNotBlank(gzlslid), "未获取到工作流实例ID信息");
        List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        Preconditions.checkArgument(CollectionUtils.isNotEmpty(bdcXmDTOList), "未获取到项目信息");
        if (StringUtils.isBlank(xmid)) {
            xmid = bdcXmDTOList.get(0).getXmid();
        }
        return bdcSlSfxxFeignService.tsdjfxxBySlbh(bdcXmDTOList.get(0).getSlbh(), xmid);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return 返回结果
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 收费信息退款申请
     */
    @ResponseBody
    @GetMapping("/tksq")
    public Object tksq(String gzlslid) {
        return bdcSlSfxxFeignService.sfxxTksq(gzlslid);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return 返回结果
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 收费信息退款申请
     */
    @ResponseBody
    @GetMapping("/querySfxxTkqk")
    public Object querySfxxTkqk(String gzlslid) {
        return bdcSlSfxxFeignService.querySfxxTkqk(gzlslid);
    }

    /**
     * 修改收费状态为已核验状态
     *
     * @param gzlslid 工作流实例ID
     */
    @ResponseBody
    @GetMapping("/modifySfzt")
    public void modifySfzt(String gzlslid) {
        List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxFeignService.listBdcSlSfxxByGzlslid(gzlslid);
        for (BdcSlSfxxDO bdcSlSfxxDO : bdcSlSfxxDOList) {
            //只有收费状态为空才进行更新
            if (bdcSlSfxxDO.getSfzt() != null) {
                continue;
            }
            bdcSlSfxxDO.setSfzt(BdcSfztEnum.YHY.key());
            bdcSlSfxxFeignService.updateBdcSlSfxx(bdcSlSfxxDO);
        }
    }

    /**
     * 查询收费状态
     *
     * @param gzlslid 工作流实例ID
     * @return boolean true(已缴费) false(未收费)
     */
    @ResponseBody
    @GetMapping("/checkSfzt")
    public boolean checkPosFeedback(String gzlslid) {
        Preconditions.checkArgument(StringUtils.isNotBlank(gzlslid), "缺少参数：工作流实例ID。");
        List<BdcSlSfxxDO> list = this.bdcSlSfxxFeignService.listBdcSlSfxxByGzlslid(gzlslid);
        boolean sfzt = false;
        for (BdcSlSfxxDO bdcSlSfxxDO : list) {
            if (BdcSfztEnum.YJF.key().equals(bdcSlSfxxDO.getSfzt())) {
                sfzt = true;
                break;
            }
        }
        return sfzt;
    }

    /**
     * 根据收费信息ID 修改收费状态
     *
     * @param sfxxids 收费信息ID集合
     * @param jkfs    缴款方式
     * @return 修改的个数
     */
    @ResponseBody
    @PostMapping("/updateJkfsBySfxxid/{jkfs}")
    public Integer updateJkfsBySfxxid(@RequestBody List<String> sfxxids, @PathVariable(value = "jkfs") String jkfs) {
        if (CollectionUtils.isEmpty(sfxxids)) {
            throw new AppException("缺失收费信息id");
        }
        Integer count = 0;
        for (String sfxxid : sfxxids) {
            BdcSfxxCzQO bdcSfxxCzQO = new BdcSfxxCzQO();
            bdcSfxxCzQO.setSfxxid(sfxxid);
            bdcSfxxCzQO.setJkfs(jkfs);
            bdcSfxxCzQO.setSftdsyj(false);
            this.bdcSfxxFeignService.changeJkfsModifySfzt(bdcSfxxCzQO);
        }
        return count;
    }

    /**
     * 更新收费状态
     *
     * @param sfxxids 收费信息ID集合
     * @param gzlslid 工作流实例ID
     * @param sfzt    缴费状态
     */
    @ResponseBody
    @PostMapping("/updateSfztBySfxxid/{gzlslid}/{sfzt}")
    public void updateSfztBySfxxid(@RequestBody List<String> sfxxids,
                                   @PathVariable(value = "gzlslid") String gzlslid, @PathVariable(value = "sfzt") Integer sfzt) {
        if (StringUtils.isBlank(gzlslid) || Objects.isNull(sfzt)) {
            throw new AppException("缺失工作流实例ID或收费状态");
        }

        if (CollectionUtils.isEmpty(sfxxids)) {
            BdcSfxxCzQO bdcSfxxCzQO = new BdcSfxxCzQO();
            bdcSfxxCzQO.setGzlslid(gzlslid);
            bdcSfxxCzQO.setSfzt(sfzt);
            this.bdcSfxxFeignService.plModifySfzt(bdcSfxxCzQO);
        } else {
            for (String sfxxid : sfxxids) {
                BdcSfxxCzQO bdcSfxxCzQO = new BdcSfxxCzQO();
                bdcSfxxCzQO.setSfxxid(sfxxid);
                bdcSfxxCzQO.setSfzt(sfzt);
                this.bdcSfxxFeignService.modifySfzt(bdcSfxxCzQO);
            }
        }
    }

    /**
     * 调用POS指令支付成功后，调用政融平台接口通知支付成功
     *
     * @param posJfDTO POS缴费实体类
     * @return Object   状态码和错误信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @GetMapping("/noticeZrpt")
    public Object noticeZrpt(PosJfDTO posJfDTO) {
        Preconditions.checkArgument(StringUtils.isNotBlank(posJfDTO.getGzlslid()), "未获取到工作流实ID。");
        return bdcSlSfxxFeignService.tzzrpt(posJfDTO);
    }

    /**
     * @param gzlslid 工作流实例id
     * @param xmid    项目ID
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 重新计算收费项目
     * 计算逻辑为 根据传入参数，先删除当前流程的所有收费项目，根据收费项目配置表数据，重新生成，然后处理工本费登记费数据
     * @date : 2020/5/9 9:44
     */
    @ResponseBody
    @GetMapping("/recount")
    public void reCountSfxm(String gzlslid, String xmid) throws Exception {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("重新计算缺失工作流实例id");
        }

        // 1.判断是否可以重新计算，收费信息为已收费或存在缴款码时，不能重新计算； 收费项目为已收费或存在缴款码时不能重新计算。
        if (!canRecount(gzlslid, xmid, false)) {
            throw new AppException(ErrorCode.YW_JYSB, "已收费或存在缴款码，不可重新计算");
        }
        //是否登记收费、减免原因任意字段非空，不可重新计算
        if(!zdfkcanRecount(gzlslid, xmid)){
            throw new AppException(ErrorCode.YW_JYSB, "是否登记收费、减免原因任意一个有值，不可重新计算");
        }
        // 当配置了土地使用权收费代码时，收费单重新计算，不重新计算土地收费项目信息，不影响土地收费单的收费信息
        Map<String, List<BdcSlSfxmDO>> tdsyqSfxmMap = null;
        if(StringUtils.isNotBlank(tdsyqsfxmdm)){
            tdsyqSfxmMap = this.queryTdSfxm(gzlslid, xmid);
        }

        // 2. 删除当前查询到的所有的收费项目, 如果传入了xmid那么应该是组合流程
        BdcSlSfxxDO deleteParam = new BdcSlSfxxDO();
        deleteParam.setGzlslid(gzlslid);
        deleteParam.setXmid(xmid);
        bdcSlSfxxFeignService.deleteSfxmAndSfxx(deleteParam);

        // 3. 读取配置重新生成
        bdcSlSfxxFeignService.cshSfxx(gzlslid, xmid);

        // 存在土地的收费项目，则将原有收费项目信息 替换新生成的土地收费项目，保持收费单重新计算时，不影响土地收费单的数据
        if(Objects.nonNull(tdsyqSfxmMap) && StringUtils.isNotBlank(tdsyqsfxmdm)){
            this.handlerTdsfxm(gzlslid, xmid, tdsyqSfxmMap);
        }

        // 4. 重新生成后查询收费信息
        BdcSlSfxxDO param = new BdcSlSfxxDO();
        param.setGzlslid(gzlslid);
        if (StringUtils.isNotBlank(xmid)) {
            param.setXmid(xmid);
        }
        List<BdcSlSfxxDO> bdcSlSfxxDOList = this.bdcSlSfxxFeignService.queryBdcSlSfxx(param);
        int xmlx = bdcXmFeignService.makeSureBdcXmLx(gzlslid);
        Boolean zhlc = false;
        if (CommonConstantUtils.LCLX_PLZH.equals(xmlx) || CommonConstantUtils.LCLX_ZH.equals(xmlx)) {
            zhlc = true;
        }
        if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
            for (BdcSlSfxxDO bdcSlSfxxDO : bdcSlSfxxDOList) {
                queryBdcSlSfxm(bdcSlSfxxDO.getSfxxid(), gzlslid, zhlc ? bdcSlSfxxDO.getXmid() : "", "", true);
            }
        }

        //计算土地收费信息
        //recountTdSfxm(gzlslid, xmid);
    }
    /**
    * 字段有值不允许重新计算
    **/
    private boolean zdfkcanRecount(String gzlslid, String xmid) {
        boolean canRecount = true;
        BdcSlSfxxDO param = new BdcSlSfxxDO();
        param.setGzlslid(gzlslid);
        param.setXmid(xmid);
        List<BdcSlSfxxDO> ysfxxList = this.bdcSlSfxxFeignService.queryBdcSlSfxx(param);
        if (CollectionUtils.isNotEmpty(ysfxxList)) {
            for (BdcSlSfxxDO bdcSlSfxxDO : ysfxxList) {
                if (zdfkbcxjs && (bdcSlSfxxDO.getSfsdjf() != null || StringUtils.isNotBlank(bdcSlSfxxDO.getJmyy()))) {
                    canRecount = false;
                    break;
                }
            }
        }
        return canRecount;
    }

    //  查询土地收费项目数据，根据 xmid+qlrlb 进行分组
    private Map<String, List<BdcSlSfxmDO>> queryTdSfxm(String gzlslid, String xmid){
        if(StringUtils.isNotBlank(tdsyqsfxmdm)){
            Map<String, List<BdcSlSfxmDO>> resultMap = new HashMap<>(10);
            BdcSlSfxxQO bdcSlSfxxQO = new BdcSlSfxxQO();
            bdcSlSfxxQO.setGzlslid(gzlslid);
            bdcSlSfxxQO.setXmid(xmid);
            bdcSlSfxxQO.setSfxmdm(tdsyqsfxmdm);
            List<BdcSfxxDTO> bdcSfxxDTOList = this.bdcSlSfxxFeignService.queryBdcSlSfxxAndSfxm(bdcSlSfxxQO);
            if(CollectionUtils.isNotEmpty(bdcSfxxDTOList)){
                // 判断土地收费项目是否已缴费，已缴费则土地收费项目不在重新计算
                for(BdcSfxxDTO bdcSfxxDTO : bdcSfxxDTOList){
                    if(CollectionUtils.isNotEmpty(bdcSfxxDTO.getBdcSlSfxmDOS()) ){
                        String key = bdcSfxxDTO.getBdcSlSfxxDO().getXmid()+ bdcSfxxDTO.getBdcSlSfxxDO().getQlrlb();
                        resultMap.put(key, bdcSfxxDTO.getBdcSlSfxmDOS());
                    }
                }
                return resultMap;
            }
        }
        return null;
    }

    // 收费信息重新计算后，将新生成的土地收费项目删除，用老的土地收费项目替换
    private void handlerTdsfxm(String gzlslid, String xmid, Map<String, List<BdcSlSfxmDO>> oldTdsyqSfxmMap){
        Map<String, List<BdcSlSfxmDO>> newTdsyqSfxmMap = this.queryTdSfxm(gzlslid, xmid);
        if(MapUtils.isNotEmpty(newTdsyqSfxmMap)){
            // 将新生成土地收费项目的收费信息id, 关联到原有的收费项目上去
            for(String key : newTdsyqSfxmMap.keySet()){
                if(CollectionUtils.isNotEmpty(oldTdsyqSfxmMap.get(key))){
                    List<BdcSlSfxmDO> newTdSfxmList = newTdsyqSfxmMap.get(key);
                    List<BdcSlSfxmDO> oldTdSfxmList = oldTdsyqSfxmMap.get(key);
                    String newSfxxid = newTdSfxmList.get(0).getSfxxid();
                    for(BdcSlSfxmDO bdcSlSfxmDO: oldTdSfxmList){
                        bdcSlSfxmDO.setSfxxid(newSfxxid);
                    }
                    // 删除新增的土地收费项目
                    this.plDeleteBdcSlSfxm(newTdSfxmList);
                    // 添加原有土地收费项目
                    this.bdcSlSfxmFeignService.batchUpdateBdcSlSfxm(oldTdSfxmList);
                }
            }
        }else{
            BdcSlSfxxDO param = new BdcSlSfxxDO();
            param.setXmid(xmid);
            param.setGzlslid(gzlslid);
            List<BdcSlSfxxDO> bdcSlSfxxDOList = this.bdcSlSfxxFeignService.queryBdcSlSfxx(param);
            if(CollectionUtils.isNotEmpty(bdcSlSfxxDOList)){
                for(BdcSlSfxxDO bdcSlSfxxDO : bdcSlSfxxDOList ){
                    String key = bdcSlSfxxDO.getXmid() + bdcSlSfxxDO.getQlrlb();
                    if(oldTdsyqSfxmMap.containsKey(key)){
                        List<BdcSlSfxmDO> oldTdSfxmList = oldTdsyqSfxmMap.get(key);
                        for(BdcSlSfxmDO bdcSlSfxmDO: oldTdSfxmList){
                            bdcSlSfxmDO.setSfxxid(bdcSlSfxxDO.getSfxxid());
                        }
                        // 添加原有土地收费项目
                        this.bdcSlSfxmFeignService.batchUpdateBdcSlSfxm(oldTdSfxmList);
                    }
                }
            }
        }

    }

    // 批量删除收费项目
    private void plDeleteBdcSlSfxm(List<BdcSlSfxmDO> bdcSlSfxmDOList){
        if(CollectionUtils.isNotEmpty(bdcSlSfxmDOList)){
            for(BdcSlSfxmDO sfxm: bdcSlSfxmDOList){
                this.bdcSlSfxmFeignService.deleteBdcSlSfxmBySfxmid(sfxm.getSfxmid());
            }
        }
    }

    /**
     *  是否可以重新计算
     * @param gzlslid 工作流实例ID
     * @param xmid    项目ID
     * @param sftdsyj 是否土地使用金收费 true：是，false：否
     */
    private boolean canRecount(String gzlslid, String xmid, boolean sftdsyj) {
        boolean canRecount = true;

        BdcSlSfxxDO param = new BdcSlSfxxDO();
        param.setGzlslid(gzlslid);
        param.setXmid(xmid);
        List<BdcSlSfxxDO> ysfxxList = this.bdcSlSfxxFeignService.queryBdcSlSfxx(param);
        if (CollectionUtils.isNotEmpty(ysfxxList)) {
            for (BdcSlSfxxDO bdcSlSfxxDO : ysfxxList) {
                if (CommonConstantUtils.SFZT_YJF.equals(bdcSlSfxxDO.getSfzt()) || StringUtils.isNotBlank(bdcSlSfxxDO.getJfsewmurl())) {
                    canRecount = false;
                    break;
                }
                List<BdcSlSfxmDO> bdcSlSfxmDOList = this.bdcSlSfxmFeignService.listBdcSlSfxmBySfxxid(bdcSlSfxxDO.getSfxxid());
                // 区分土地使用金收费 和 正常收费， 用于收费单与土地收费单重新计算时，互不影响
                if(sftdsyj && StringUtils.isNotBlank(tdsyqsfxmdm)){
                    bdcSlSfxmDOList = bdcSlSfxmDOList.stream().filter(bdcSlSfxmDO -> StringUtils.equals(bdcSlSfxmDO.getSfxmdm(), tdsyqsfxmdm)).collect(Collectors.toList());
                }else{
                    bdcSlSfxmDOList = bdcSlSfxmDOList.stream().filter(bdcSlSfxmDO -> !StringUtils.equals(bdcSlSfxmDO.getSfxmdm(), tdsyqsfxmdm)).collect(Collectors.toList());
                }
                if (CollectionUtils.isNotEmpty(bdcSlSfxmDOList)) {
                    for (BdcSlSfxmDO bdcSlSfxmDO : bdcSlSfxmDOList) {
                        if (CommonConstantUtils.SFZT_YJF.equals(bdcSlSfxmDO.getSfzt()) || StringUtils.isNotBlank(bdcSlSfxmDO.getJfsewmurl())) {
                            canRecount = false;
                            break;
                        }
                    }
                }
            }
        }
        return canRecount;
    }

    /*
     * 重新计算土地收费项目
     * */
    @ResponseBody
    @GetMapping("/recount/tdsfxm")
    public void recountTdSfxm(String gzlslid, String xmid) throws Exception {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("重新计算缺失工作流实例id");
        }
        // 判断是否可以重新计算，收费信息为已收费或存在缴款码时，不能重新计算； 收费项目为已收费或存在缴款码时不能重新计算。
        if (!canRecount(gzlslid, xmid, true)) {
            throw new AppException(ErrorCode.YW_JYSB, "已收费或存在缴款码，不可重新计算");
        }
        //是否登记收费、减免原因任意字段非空，不可重新计算
        if(!zdfkcanRecount(gzlslid, xmid)){
            throw new AppException(ErrorCode.YW_JYSB, "是否登记收费、减免原因任意一个有值，不可重新计算");
        }
        BdcSlSfxxDO param = new BdcSlSfxxDO();
        param.setGzlslid(gzlslid);
        if (StringUtils.isNotBlank(xmid)) {
            param.setXmid(xmid);
        }
        List<BdcSlSfxxDO> bdcSlSfxxDOList = this.bdcSlSfxxFeignService.queryBdcSlSfxx(param);
        if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
            for (BdcSlSfxxDO bdcSlSfxxDO : bdcSlSfxxDOList) {
                queryBdcSlTdSfxm(bdcSlSfxxDO.getSfxxid(), gzlslid, bdcSlSfxxDO.getXmid(), true);
            }
        }
        countHj(gzlslid);
    }

    /**
     * @param gzlslid 工作流实例id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 海门点击打印按钮更新收费状态
     * @date : 2020/6/3 15:11
     */
    @ResponseBody
    @GetMapping("/xx/sfzt")
    public Object updateSfzt(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("获取收费信息缺失工作流实例id");
        }
        Integer count = 0;
        List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxFeignService.listBdcSlSfxxByGzlslid(gzlslid);
        if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
            for (BdcSlSfxxDO bdcSlSfxxDO : bdcSlSfxxDOList) {
                bdcSlSfxxDO.setSfzt(BdcSfztEnum.YJF.key());
                count += bdcSlSfxxFeignService.updateBdcSlSfxx(bdcSlSfxxDO);
            }
        }
        return count;
    }

    /**
     * 调用exchange 互联网+缴费状态接口，获取缴费状态
     *
     * @param gzlslid 工作流实例ID
     * @return Object   缴费状态
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @GetMapping("/queryJfzt")
    public Object queryJfzt(@NotBlank(message = "缺失工作流实例ID!") String gzlslid) {
        List<BdcSlSfxxDO> bdcSlSfxxDOList = this.bdcSlSfxxFeignService.listBdcSlSfxxByGzlslid(gzlslid);
        Preconditions.checkArgument(CollectionUtils.isNotEmpty(bdcSlSfxxDOList), "未获取到当前流程的收费信息。");
        List<String> jfsbmList = bdcSlSfxxDOList.stream().map(x -> x.getJfsbm()).collect(Collectors.toList())
                .stream().filter(StringUtils::isNotBlank).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(jfsbmList)) {
            Preconditions.checkArgument(CollectionUtils.isNotEmpty(jfsbmList), "未获取到订单号信息。");
        }
        Pair<String, String> pair = this.getLeftXmidAndRightSlbh(gzlslid);
        String ddbh = jfsbmList.get(0);
        Map<String, String> paramMap = new HashMap<>(4);
        paramMap.put("slbh", pair.getRight());
        paramMap.put("xmid", pair.getLeft());
        paramMap.put("ddbh", ddbh);
        Object response = exchangeInterfaceFeignService.requestInterface("wwsq_ykqjfztcx", paramMap);
        if (Objects.nonNull(response)) {
            Map<String, String> map = (Map<String, String>) response;
            if (BdcSfztEnum.YJF.value().equals(map.get("jfzt"))) {
                // 获取缴费状态为已缴费时，更新受理缴费状态信息为已缴费，缴费方式为线上缴费。
                this.modifySfztAndjkfs(gzlslid, ddbh, null, null);
            }
        }
        return response;
    }

    // 更新收费信息收费状态
    private void modifySfztAndjkfs(String gzlslid, String ddbh, String shdm, String jypzh) {
        // 根据缴费书编码（即订单号）更新受理收费信息状态
        BdcSlSfxxDO bdcSlSfxxDO = new BdcSlSfxxDO();
        bdcSlSfxxDO.setJfsbm(ddbh);
        bdcSlSfxxDO.setSfztczsj(new Date());
        if (StringUtils.isNotBlank(shdm)) {
            bdcSlSfxxDO.setSfdwdm(shdm);
        }
        if (StringUtils.isNotBlank(jypzh)) {
            bdcSlSfxxDO.setJypzh(jypzh);
        }
        bdcSlSfxxDO.setSfzt(BdcSfztEnum.YJF.key());
        bdcSlSfxxDO.setJkfs(CommonConstantUtils.JKFS_XSJF);
        this.bdcSlSfxxFeignService.gxSfxxSfzt(bdcSlSfxxDO);
        LOGGER.info("已更新收费状态。工作流实例ID:{}", gzlslid);
    }

    /**
     * 查询不动产受理收费信息
     *
     * @param gzlslid 工作流实例ID
     * @return 不动产受理收费信息DO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @GetMapping("/queryBdcSfxx")
    public Object queryBdclSfxx(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("未获取到工作流实例ID");
        }
        List<BdcSlSfxxDO> bdcSlSfxxDOList = this.bdcSlSfxxFeignService.listBdcSlSfxxByGzlslid(gzlslid);
        if (CollectionUtils.isEmpty(bdcSlSfxxDOList)) {
            throw new MissingArgumentException("未获取到收费信息");
        }
        // 去除没有缴费书编码的数据与作废的收费信息数据
        bdcSlSfxxDOList = bdcSlSfxxDOList.stream().filter(t -> StringUtils.isNotBlank(t.getJfsbm())).collect(Collectors.toList());

        Map<String, Object> result = new HashMap<>(8);
        // 标记收费信息是否可以打印。判断收费信息中，存在缴费书二维码，但没有交费的情况，不允许打印
        boolean print = true;
        if (CollectionUtils.isEmpty(bdcSlSfxxDOList)) {
            print = false;
            result.put("errorMsg", "未获取到已缴费信息");
        } else {
            List<Map> jfxxList = new ArrayList<>(bdcSlSfxxDOList.size());
            for (BdcSlSfxxDO bdcSlSfxxDO : bdcSlSfxxDOList) {
                if (null == bdcSlSfxxDO.getSfzt() || !(bdcSlSfxxDO.getSfzt().equals(CommonConstantUtils.SFZT_YJF))) {
                    print = false;
                    result.put("errorMsg", "缴费书编码为：" + bdcSlSfxxDO.getJfsbm() + "，未完成缴费。");
                    break;
                } else {
                    Map<String, String> jfxx = new HashMap<>(3);
                    jfxx.put("jfsbm", bdcSlSfxxDO.getJfsbm());
                    jfxx.put("dh", getBdcQlrDh(bdcSlSfxxDO.getXmid(), bdcSlSfxxDO.getQlrlb()));
                    jfxx.put("sfkp", String.valueOf(bdcSlSfxxDO.getSfkp()));
                    jfxx.put("sfxxid", bdcSlSfxxDO.getSfxxid());
                    jfxxList.add(jfxx);
                }
            }
            // 获取当前登陆用户信息
            UserDto userDto = userManagerUtils.getUserDto();
            result.put("jfxx", jfxxList);
            result.put("userName", userDto.getAlias());
        }
        result.put("result", print);
        return result;
    }

    private String getBdcQlrDh(String xmid, String qlrlb) {
        String dh = "";
        if (StringUtils.isNotBlank(xmid)) {
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setXmid(xmid);
            if (StringUtils.isNotBlank(qlrlb)) {
                bdcQlrQO.setQlrlb(qlrlb);
            }
            List<BdcQlrDO> bdcQlrDOList = this.bdcQlrFeignService.listBdcQlr(bdcQlrQO);
            if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                dh = bdcQlrDOList.get(0).getDh();
            }
        }
        return dh;
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

    /**
     * 调用外部接口打印电子发票信息
     *
     * @param sfxxid 收费信息ID
     * @return 电子发票内容
     * @author <a href="mailto:yaoyi@gtmap.cn">caolu</a>
     */
    @ResponseBody
    @PostMapping("/printDzfpxx")
    public Object getDzfpxx(@RequestBody String sfxxid) throws IOException {
        if (StringUtils.isBlank(sfxxid)) {
            throw new MissingArgumentException("未获取到收费信息ID。");
        }
        return bdcSlSfxxFeignService.getDzfpxx(sfxxid);
    }

    /**
     * 生成电子发票信息
     *
     * @param bdcSlSfxxDO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @PostMapping("/xx/hkdzfp")
    public Object hkdzfp(@RequestBody BdcSlSfxxDO bdcSlSfxxDO) {
        if (StringUtils.isAnyBlank(bdcSlSfxxDO.getGzlslid(), bdcSlSfxxDO.getQlrlb())) {
            throw new AppException(ErrorCode.MISSING_ARG, "缺少参数工作流实例ID或权利人类别信息。");
        }
        // 查询参数有 gzlslid,qlrlb
        List<BdcSlSfxxDO> bdcSlSfxxDOList = this.bdcSlSfxxFeignService.queryBdcSlSfxx(bdcSlSfxxDO);
        if (CollectionUtils.isEmpty(bdcSlSfxxDOList)) {
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到收费信息");
        }
        if (!BdcSfztEnum.YJF.key().equals(bdcSlSfxxDOList.get(0).getSfzt())) {
            throw new AppException(ErrorCode.CUSTOM, "当前收费信息未进行缴费无法生成电子发票。");
        }
        return bdcSlSfxxFeignService.hkdzfpxx(bdcSlSfxxDOList.get(0).getSfxxid());
    }

    // 处理南通财政返回值
    private Map handleResult(Object response) {
        LOGGER.info("接口返回结果：{}", StringUtils.left(JSON.toJSONString(response), 1000));
        if (Objects.isNull(response)) {
            throw new AppException("未获取到电子票据信息");
        }
        Map map = (Map) response;
        if (map.get("errcode").equals(0)) {
            String result = JSON.toJSONString(map.get("result"));
            return JSONObject.parseObject(result, new TypeReference<Map>() {
            });
        } else {
            throw new AppException(String.valueOf(response));
        }
    }

    /**
     * 根据缴费编码更新开票状态为已开票
     *
     * @param jfsbm         缴费书编码
     * @param kprxm         开票人姓名
     * @param invoiceNumber 发票号
     */
    private void modifyKpzt(String jfsbm, String kprxm, String invoiceNumber) {
        if (StringUtils.isNotBlank(jfsbm)) {
            BdcSlSfxxDO queryParam = new BdcSlSfxxDO();
            queryParam.setJfsbm(jfsbm);
            List<BdcSlSfxxDO> bdcSlSfxxDOList = this.bdcSlSfxxFeignService.queryBdcSlSfxx(queryParam);
            if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
                for (BdcSlSfxxDO bdcSlSfxxDO : bdcSlSfxxDOList) {
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
     * 作废或冲红收费项目内容
     *
     * @param bdcSlSfxxDO 收费信息
     * @param sfch        是否冲红
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @PostMapping("/xx/zf")
    public Object zfsfxx(@RequestBody BdcSlSfxxDO bdcSlSfxxDO, @RequestParam(name = "sfch") boolean sfch) {
        if (StringUtils.isAnyBlank(bdcSlSfxxDO.getGzlslid(), bdcSlSfxxDO.getQlrlb())) {
            throw new MissingArgumentException("缺少参数，工作流实例ID和权利人类别");
        }
        List<BdcSlSfxxDO> bdcSlSfxxDOList = this.bdcSlSfxxFeignService.queryBdcSlSfxx(bdcSlSfxxDO);
        if (CollectionUtils.isEmpty(bdcSlSfxxDOList)) {
            throw new MissingArgumentException("未获取到收费信息。");
        }
        BdcSlSfxxDO sfxx = bdcSlSfxxDOList.get(0);
        if (StringUtils.isBlank(sfxx.getJfsbm())) {
            throw new MissingArgumentException("未获取当前收费信息的缴费书编码信息。");
        }
        if (sfch) {
            boolean canFlush = CommonConstantUtils.SFZT_YJF.equals(sfxx.getSfzt())
                    && StringUtils.isNotBlank(sfxx.getJfsbm()) && CommonConstantUtils.SF_S_DM.equals(sfxx.getSfkp());
            if (!canFlush) {
                throw new AppException("收费信息需已推送、已缴费、已开票才能进行冲红操作。");
            }
        } else {
            if (CommonConstantUtils.SFZT_YJF.equals(sfxx.getSfzt())) {
                throw new AppException(ErrorCode.CUSTOM, "已缴费成功，不允许作废。");
            }
        }

        // 根据配置判断是否执行 作废接口，在更新收费信息收费状态
        if (zfSfdyjk) {
            // 作废收费信息，作废接口beanname=fs_jfpt_zfdjfxx
            bdcSlSfxxDO.setSfxxid(sfxx.getSfxxid());
            CommonResponse commonResponse = this.bdcSlSfxxFeignService.zfdjfxx(bdcSlSfxxDO);
            LOGGER.info("作废接口返回结果：{}", JSON.toJSONString(commonResponse));
            if (Objects.nonNull(commonResponse) && commonResponse.isSuccess()) {
                return sfxx;
            }
        } else {
            this.modifySfxxZfzt(sfxx, bdcSlSfxxDO.getTfyy());
            return sfxx;
        }
        return null;
    }

    /**
     * 修改收费信息作废状态
     */
    private void modifySfxxZfzt(BdcSlSfxxDO sfxx, String tfyy) {
        sfxx.setTfyy(tfyy);
        sfxx.setSfzf(CommonConstantUtils.SF_S_DM);
        UserDto userDto = this.userManagerUtils.getCurrentUser();
        sfxx.setTfrxm(userDto.getAlias());
        this.bdcSlSfxxFeignService.updateBdcSlSfxx(sfxx);
    }

    /**
     * 获取冲红票据信息接口
     *
     * @param ortirDTO 获取冲红票据接口参数
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @PostMapping("/getBeforeRevoke")
    public Object getBeforeRevoke(@RequestBody ObtainRedemptionTicketInfoRequestDTO ortirDTO) {
        if (StringUtils.isAnyBlank(ortirDTO.getBillno(), ortirDTO.getSerialNumber())) {
            throw new MissingArgumentException("未获取到缴费书编码和CA序列号信息。");
        }
        if (StringUtils.isBlank(ortirDTO.getDrawer())) {
            UserDto userDto = userManagerUtils.getCurrentUser();
            ortirDTO.setDrawer(userDto.getAlias());
        }
        Object response = this.nantongFsxtzfFeignService.obtainRedemptionTicketInfo(ortirDTO);
        LOGGER.info("获取冲红票据信息接口返回结果：{}", JSON.toJSONString(response));
        if (Objects.nonNull(response)) {
            return response;
        }
        return null;
    }

    /**
     * 提交冲红票据接口
     *
     * @param hrtirDTO 提交冲红票据接口参数
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @PostMapping("/getHandleRevoke")
    public Object getHandleRevoke(@RequestBody HandleRedemptionTicketInfoRequestDTO hrtirDTO) {
        if (StringUtils.isBlank(hrtirDTO.getDrawer())) {
            UserDto userDto = userManagerUtils.getCurrentUser();
            hrtirDTO.setDrawer(userDto.getAlias());
        }
        Object response = this.nantongFsxtzfFeignService.handleRedemptionTicketInfo(hrtirDTO);
        LOGGER.info("提交冲红票据接口返回结果：{}", JSON.toJSONString(response));
        if (Objects.nonNull(response)) {
            return response;
        }
        return null;
    }


    @ResponseBody
    @GetMapping("/jmzc")
    public Object queryJmzc(String jmzcbz, Integer jmzt, String gzlslid, String qlrlb, String xmid) {
        if (Objects.isNull(jmzt)) {
            //如果为空，赋值为9查询
            jmzt = 9;
        }
        List<String> jmbzList = Arrays.asList(Constants.JMBZARRAY);
        List<BdcSlSfxmJmzcGxDO> bdcSlSfxmJmzcGxDOList = new ArrayList<>(5);
        BdcSlSfxxDO bdcSlSfxxDO = new BdcSlSfxxDO();
        if (StringUtils.isNotBlank(xmid)) {
            bdcSlSfxxDO.setXmid(xmid);
        } else {
            bdcSlSfxxDO.setGzlslid(gzlslid);
        }
        bdcSlSfxxDO.setQlrlb(qlrlb);
        List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxFeignService.queryBdcSlSfxx(bdcSlSfxxDO);
        if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
            for (BdcSlSfxxDO bdcSlSfxx : bdcSlSfxxDOList) {
                for (String jmbz : jmbzList) {
                    BdcSlSfxmJmzcGxDO bdcSlSfxmJmzcGxDO = new BdcSlSfxmJmzcGxDO();
                    if (StringUtils.equals(jmbz, jmzcbz)) {
                        bdcSlSfxmJmzcGxDO.setJmzt(jmzt);
                        bdcSlSfxmJmzcGxDO.setJmzcbz(jmzcbz);
                    } else {
                        Object res = getClassValue(bdcSlSfxx, jmbz);
                        bdcSlSfxmJmzcGxDO.setJmzt(Objects.nonNull(res) ? (Integer) res : 9);
                        bdcSlSfxmJmzcGxDO.setJmzcbz(jmbz);
                    }
                    List<BdcSlSfxmJmzcGxDO> bdcSlSfxmJmzcGxDOS = bdcSlSfxmJmzcGxFeignService.listSfxmJmzcGx(bdcSlSfxmJmzcGxDO);
                    if (CollectionUtils.isNotEmpty(bdcSlSfxmJmzcGxDOS)) {
                        //1个减免标志和减免状态只能获取一个减免标准
                        bdcSlSfxmJmzcGxDOList.add(bdcSlSfxmJmzcGxDOS.get(0));
                    }
                }
            }
        }
        return bdcSlSfxmJmzcGxDOList;
    }

    public List<BdcSlSfxmDTO> changeSfbzByJmzc(String gzlslid, List<BdcSlSfxmDTO> bdcSlSfxmDTOList) {
        //查询出所有的收费信息
        List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxFeignService.listBdcSlSfxxByGzlslid(gzlslid);
        if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
            List<String> jmbzList = Arrays.asList(Constants.JMBZARRAY);
            for (BdcSlSfxxDO bdcSlSfxxDO : bdcSlSfxxDOList) {
                //循环收费信息，改变对应的收费标准
                for (BdcSlSfxmDTO bdcSlSfxmDTO : bdcSlSfxmDTOList) {
                    if (StringUtils.equals(bdcSlSfxxDO.getQlrlb(), bdcSlSfxmDTO.getQlrlb())) {
                        for (String jmzcbz : jmbzList) {
                            Object res = getClassValue(bdcSlSfxxDO, jmzcbz);
                            BdcSlSfxmJmzcGxDO bdcSlSfxmJmzcGxDO = new BdcSlSfxmJmzcGxDO();
                            bdcSlSfxmJmzcGxDO.setJmzcbz(jmzcbz);
                            //结果只能是0或者1 (即是否)，如果为空给一个默认值9
                            bdcSlSfxmJmzcGxDO.setJmzt(Objects.nonNull(res) ? (Integer) res : 9);
                            List<BdcSlSfxmJmzcGxDO> bdcSlSfxmJmzcGxDOList = bdcSlSfxmJmzcGxFeignService.listSfxmJmzcGx(bdcSlSfxmJmzcGxDO);
                            if (CollectionUtils.isNotEmpty(bdcSlSfxmJmzcGxDOList)) {
                                BdcSlSfxmJmzcGxDO bdcSlSfxmJmzc = bdcSlSfxmJmzcGxDOList.get(0);
                                if (StringUtils.equals(bdcSlSfxmJmzc.getSfxmdm(), bdcSlSfxmDTO.getSfxmdm())) {
                                    for (BdcSlSfxmSfbzDO bdcSlSfxmSfbzDO : bdcSlSfxmDTO.getBdcSlSfxmSfbzList()) {
                                        //低保或者下岗再就业只针对收取住宅登记费，非住宅登记费不变
                                        if (StringUtils.equals(jmzcbz, "sfdb") || StringUtils.equals(jmzcbz, "sfxgzjy")) {
                                            if (!(bdcSlSfxmSfbzDO.getSfxmbz().contains("非住宅"))) {
                                                bdcSlSfxmSfbzDO.setSfxmdj(bdcSlSfxmJmzc.getSfxmdj().toString());
                                            }
                                        } else {
                                            bdcSlSfxmSfbzDO.setSfxmdj(bdcSlSfxmJmzc.getSfxmdj().toString());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return bdcSlSfxmDTOList;
    }

    /**
     * @param ewmnr
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 页面生成二维码
     * @date : 2020/9/14 15:05
     */
    @ResponseBody
    @GetMapping("/ewm")
    public void createEwm(String ewmnr, HttpServletResponse response) {
        response.setContentType("image/jpg;charset=utf-8");
        response.addHeader("Content-Disposition", "attachment; filename=test.jpg");
        QRcodeUtils.encoderQRCode(ewmnr, null, response);
    }

    /**
     * @param sfxxid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 常州退款申请
     * @date : 2020/9/14 15:05
     */
    @ResponseBody
    @GetMapping("/cz/tksq")
    public Map tksq(String sfxxid, String gzlslid, String xmid) {
        List<BdcSlSfxxDO> bdcSlSfxxDOList = new ArrayList<>(2);
        if (StringUtils.isNotBlank(sfxxid)) {
            BdcSlSfxxDO bdcSlSfxxDO = bdcSlSfxxFeignService.queryBdcSlSfxxBySfxxid(sfxxid);
            if (Objects.nonNull(bdcSlSfxxDO)) {
                bdcSlSfxxDOList.add(bdcSlSfxxDO);
            }
            if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
                //调用接口返回二维码信息存储到收费信息
                String beanName = "cz_ddtk";
                for (BdcSlSfxxDO bdcSlSfxx : bdcSlSfxxDOList) {
                    Map paramMap = new HashMap();
                    paramMap.put("sfxxid", bdcSlSfxx.getSfxxid());
                    paramMap.put("je", bdcSlSfxx.getHj());
                    paramMap.put("jkfs", bdcSlSfxx.getJkfs());
                    paramMap.put("xmid", bdcSlSfxx.getXmid());
                    Object response = exchangeInterfaceFeignService.requestInterface(beanName, paramMap);
                    LOGGER.info("退款申请结果返回信息:{}", response);
                    if (Objects.nonNull(response)) {
                        Map<String, String> map = (Map<String, String>) response;
                        if (StringUtils.equals("1", map.get("orderStatus"))) {
                            //1代表成功，保存平台退款订单号，收费状态改为退款
                            bdcSlSfxx.setTkdh(map.get("refundNo"));
                            bdcSlSfxx.setSfzt(BdcSfztEnum.TKCG.key());
                            bdcSlSfxxFeignService.updateBdcSlSfxx(bdcSlSfxxDO);
                        }
                        return map;
                    }
                }
            }
        }
        return null;
    }

    @ResponseBody
    @GetMapping("/xwlx")
    public Object queryXwlx(String sfxxid, String gzlslid, String qlrlb, String xmid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("查询小微类型缺失工作流实例id");
        }
        String xwlx = "";
        String id = "";
        String djxl = "";
        Set<String> xwlxSet = new TreeSet<>();
        if (StringUtils.isNotBlank(xmid)) {
            id = xmid;
        } else {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setGzlslid(gzlslid);
            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                id = bdcXmDOList.get(0).getXmid();
                djxl = bdcXmDOList.get(0).getDjxl();
            }
        }
        List<BdcQlrDO> bdcQlrDOList = new ArrayList<>(4);
        if (StringUtils.equals(CommonConstantUtils.QLRLB_QLR, qlrlb)) {
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setXmid(id);
            bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
            bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
        }
        if (StringUtils.equals(CommonConstantUtils.QLRLB_YWR, qlrlb)) {
            bdcQlrDOList = bdcQlrFeignService.listAllBdcQlr(gzlslid, CommonConstantUtils.QLRLB_YWR, djxl);
        }
        if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
            for (BdcQlrDO bdcQlrDO : bdcQlrDOList) {
                Map paramMap = new HashMap();
                paramMap.put("corporation", bdcQlrDO.getQlrmc());
                paramMap.put("tyshxydm", StringUtils.isNotBlank(bdcQlrDO.getZjh()) ? bdcQlrDO.getZjh() : StringUtils.EMPTY);
                LOGGER.info("查询小微类型入参:{}", paramMap);
                Object response = exchangeInterfaceFeignService.requestInterface("hf_xwqyxxcx", paramMap);
                LOGGER.info("查询小微类型结果返回值:{}{}", JSONObject.toJSONString(response), response);
                if (Objects.nonNull(response)) {
                    JSONObject object = JSONObject.parseObject(JSONObject.toJSONString(response));
                    JSONArray jsonArray = object.getJSONArray("qyxx");
                    if (CollectionUtils.isNotEmpty(jsonArray)) {
                        Object jsonObject = jsonArray.get(0);
                        xwlx = String.valueOf(((Map) jsonObject).get("type"));
                        //转化为字符串
                        String xwlxmc = BdcSlXwlxEnum.queryXwlx(xwlx);
                        xwlxSet.add(xwlxmc);
                    }
                }
            }
        }
        if (StringUtils.isNotBlank(StringUtils.join(xwlxSet, CommonConstantUtils.ZF_YW_DH)) && StringUtils.isNotBlank(sfxxid)) {
            BdcSlSfxxDO bdcSlSfxxDO = bdcSlSfxxFeignService.queryBdcSlSfxxBySfxxid(sfxxid);
            if (Objects.nonNull(bdcSlSfxxDO)) {
                bdcSlSfxxDO.setXwlx(StringUtils.join(xwlxSet, CommonConstantUtils.ZF_YW_DH));
                bdcSlSfxxFeignService.updateBdcSlSfxx(bdcSlSfxxDO);
            }
        }
        return StringUtils.join(xwlxSet, CommonConstantUtils.ZF_YW_DH);
    }

    /**
     * 登记缴费信息管理台账
     *
     * @param pageable    分页
     * @param bdcSlSfxxQO 查询qo
     * @return
     */
    @ResponseBody
    @GetMapping("/xm/djjfgl")
    public Object djjfgl(@LayuiPageable Pageable pageable, BdcSlSfxxQO bdcSlSfxxQO) {
        Page<BdcdjjfglVO> page = bdcSlSfxxFeignService.djjfgl(pageable, JSON.toJSONString(bdcSlSfxxQO));
        return super.addLayUiCode(page);
    }

    /**
     * 通过工作流实例id查询taskid 打开流程
     *
     * @param gzlslid
     * @return
     */
    @ResponseBody
    @GetMapping("/xm/xxck/{gzlslid}")
    public Object xxck(@PathVariable(value = "gzlslid") String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            List<TaskData> listTask = processTaskClient.processLastTasks(gzlslid);
            if (CollectionUtils.isNotEmpty(listTask)) {
                return listTask.get(0).getTaskId();
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    /**
     * 缴费 更新收费状态 保存收费操作日志
     *
     * @param jfList
     */
    @ResponseBody
    @PostMapping("/jfcz")
    public void jfcz(@RequestBody List<BdcdjjfglVO> jfList, @RequestParam("type") String type) {
        bdcSlSfxxFeignService.jfcz(jfList, type);
    }

    /**
     * 推送一体化受理信息与收费信息
     *
     * @param gzlslid 工作流实例ID
     * @return 推送结果
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @GetMapping("/tsythslsfxx")
    public Object tsythslsfxx(@RequestParam(value = "gzlslid") String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("缺少参数工作流实例ID");
        }
        return yanchengYthFeignService.ythTsSlxx(gzlslid);
    }


    /**
     * 非税开票按钮事件
     *
     * @param jfsewmurl
     * @param processInsId
     * @return
     */
    @ResponseBody
    @GetMapping("/fskp")
    public Object fskp(@RequestParam(value = "jfsewmurl") String jfsewmurl,
                       @RequestParam(value = "processInsId") String processInsId) {
        if (StringUtils.isBlank(jfsewmurl)) {
            throw new MissingArgumentException("缺少参数缴款识别码");
        }
        DydzpjRequestDTO bdcFskpDTO = new DydzpjRequestDTO();
        bdcFskpDTO.setJfsbm(jfsewmurl);
        LOGGER.info("调用dydzpj方法入参：{}", jfsewmurl);

        Object obj = exchangeInterfaceFeignService.requestInterface("dydzpj", bdcFskpDTO);
        if (null != obj) {
            LOGGER.info("调用dydzpj方法返回值：{}", JSONObject.toJSONString(obj));
            Map resultMap = (Map) obj;
            if (resultMap.containsKey("code") && null != resultMap.get("code")
                    && StringUtils.equals("00", resultMap.get("code").toString())) {
                if (resultMap.containsKey("Img_Conts") && null != resultMap.get("Img_Conts")) {
                    Map imgMap = new HashMap();
                    if (resultMap.get("Img_Conts") instanceof Map || resultMap.get("Img_Conts") instanceof HashMap) {
                        imgMap = (Map) resultMap.get("Img_Conts");
                        if (imgMap.containsKey("Img_Cont") && null != imgMap.get("Img_Cont")) {
                            String base64 = imgMap.get("Img_Cont").toString();
                            String id = null;
                            try {
                                id = saveFpxx(base64,jfsewmurl ,processInsId);
                            } catch (IOException e) {
                                LOGGER.error("上传文件异常", e);
                            }
                            resultMap.put("id", id);
                            resultMap.put("storageUrl", storageUrl);
                            return resultMap;
                        }
                    }
                } else {
                    throw new AppException("没有返回发票base64信息");
                }
            } else {
                throw new AppException("获取发票信息失败");
            }
        }
        return null;
    }

    /**
     * 非税卡票列表
     *
     * @param pageable
     * @param gzlslid
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/listFskpBypage")
    public Object listFskpByPage(@LayuiPageable Pageable pageable, String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("gzlslid");
        }
        Page<BdcSlSfxxDO> page = bdcSlSfxxFeignService.fskp(pageable, gzlslid);
        return addLayUiCode(page);
    }

    /**
     * 非税开票的打印xml
     *
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/fskp/print")
    public Object fskpPrint(@RequestParam(name = "id") String id, HttpServletRequest request) {
        if (StringUtils.isBlank(fskpPrintUrl)) {
            throw new AppException("未配置非税开票的打印url");
        }
        String url = fskpPrintUrl + id;
        String fskpXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<fetchdatas>" +
                "    <datas>" +
                "        <data name=\"url\" type=\"image\">" + url + "</data>\n" +
                "    </datas>" +
                "</fetchdatas>";
        LOGGER.info("非税开票打印xml:{}", fskpXml);
        return fskpXml;
    }


    /**
     * 保存图片到大云
     *
     * @param base64
     * @param processInsId
     */
    public String saveFpxx(String base64,String jfsewmurl, String processInsId) throws IOException {
        LOGGER.info("开始保存非税开票发票信息，{}", processInsId);
        String gzlslid = "";
        if (StringUtils.isNotBlank(processInsId)) {
            gzlslid = processInsId;
        } else {
            BdcSlSfxxDO bdcSlSfxxDO = new BdcSlSfxxDO();
            bdcSlSfxxDO.setJfsewmurl(jfsewmurl);
            List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxFeignService.queryBdcSlSfxx(bdcSlSfxxDO);
            if (CollectionUtils.isEmpty(bdcSlSfxxDOList)) {
                throw new MissingArgumentException("通过jfsewmurl为："+jfsewmurl+"的缴款识别码无法查询到不动产受理收费信息");
            }
            if (StringUtils.isBlank(bdcSlSfxxDOList.get(0).getGzlslid())) {
                throw new MissingArgumentException("通过jfsewmurl为："+jfsewmurl+"的缴款识别码查询到的不动产受理收费信息无gzlslid");
            }
            gzlslid = bdcSlSfxxDOList.get(0).getGzlslid();
        }
        return bdcUploadFileUtils.uploadBase64File(CommonConstantUtils.BASE64_QZ_PDF + base64, gzlslid, "发票信息", "", ".pdf");
    }

    /**
     * @return 收费项目配置信息
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 获取收费项目配置(当前流程配置收费项目)
     */
    @ResponseBody
    @GetMapping("/xm/bz")
    public Object queryBdcSlSfxmBz() {
        return bdcSlSfxmPzFeignService.listBdcSlSfxmSfbz();
    }


    /*
     * 保存并自动计算收费项目和收费信息合计(已移植到accept包中,后期需要调整将UI包方法删除！！！)
     * */
    @ResponseBody
    @GetMapping("/xm/all")
    public void saveAllSfxx(String gzlslid) throws Exception {
        if (StringUtils.isNotBlank(gzlslid)) {
            Integer lclx = bdcXmFeignService.makeSureBdcXmLx(gzlslid);
            List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxFeignService.listBdcSlSfxxByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
                for (BdcSlSfxxDO bdcSlSfxxDO : bdcSlSfxxDOList) {
                    if (Objects.equals(CommonConstantUtils.LCLX_ZH, lclx)) {
                        queryBdcSlSfxm(bdcSlSfxxDO.getSfxxid(), gzlslid, bdcSlSfxxDO.getXmid(), "", false);
                    } else {
                        queryBdcSlSfxm(bdcSlSfxxDO.getSfxxid(), gzlslid, "", "", false);
                    }
                }
            }
        }
    }

    /**
     * 查询收费缴款情况,并更新收费缴款信息
     *
     * @param bdcSlSfxxQO 不动产收费信息QO
     * @return {BdcSlSfxxDO} 不动产收费信息缴款情况
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @PostMapping("/xx/jkqk")
    public BdcSlSfxxDO queryJkqk(@RequestBody BdcSlSfxxQO bdcSlSfxxQO) {
        if (StringUtils.isBlank(bdcSlSfxxQO.getSfxxid())) {
            throw new AppException("未获取到收费信息ID");
        }
        return this.bdcSlSfxxFeignService.queryJkqkAndUpdate(bdcSlSfxxQO);
    }

    /**
     * 非税接口推送收费信息，生成缴费书编码信息
     *
     * @param sfxxid  收费信息ID
     * @param qlrlb   权利人类别
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @PostMapping("/xx/yancheng/ts")
    public void tsBdcSfxx(String sfxxid, String qlrlb, String gzlslid) {
        if (StringUtils.isBlank(sfxxid)) {
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到收费信息ID");
        }
        BdcSlSfxxDO bdcSlSfxxDO = this.bdcSlSfxxFeignService.queryBdcSlSfxxBySfxxid(sfxxid);
        if (Objects.isNull(bdcSlSfxxDO)) {
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到收费信息");
        }
        if (StringUtils.isNotBlank(bdcSlSfxxDO.getJfsbm())) {
            throw new AppException(ErrorCode.CUSTOM, "已推送缴费信息");
        }
        bdcSlSfxxFeignService.createTaxNotice(sfxxid, qlrlb, gzlslid);
    }

    /**
     * @param sfxxid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 盐城推送缴款码
     * @date : 2021/7/12 10:20
     */
    @GetMapping("/tsjkm")
    @ResponseBody
    public Object tsjkm(@NotBlank(message = "推送缴费码收费信息id不可为空") String sfxxid, HttpServletRequest httpServletRequest) {
        HashMap<String, String> paramMap = new HashMap<>(2);
        BdcSlSfxxDO bdcSlSfxxDO = bdcSlSfxxFeignService.queryBdcSlSfxxBySfxxid(sfxxid);
        if (Objects.isNull(bdcSlSfxxDO) || StringUtils.isBlank(bdcSlSfxxDO.getJfsewmurl())) {
            throw new AppException("没有获取到收费信息或者缴费码url为空");
        }
        paramMap.put("sysIp", IPPortUtils.getClientIp(httpServletRequest));
        if (jfsUrlSfzy) {
            try {
                paramMap.put("qrCode", URLEncoder.encode(bdcSlSfxxDO.getJfsewmurl(), "utf-8"));
            } catch (UnsupportedEncodingException e) {
                LOGGER.error("url编码错误{}", bdcSlSfxxDO.getJfsewmurl(), e);
            }
        } else {
            paramMap.put("qrCode", bdcSlSfxxDO.getJfsewmurl());
        }

        Object response = exchangeInterfaceFeignService.requestInterface("tsjfdz_mk", paramMap);
        LOGGER.info("推送缴款码返回结果{}", JSON.toJSONString(response));
        return response;
    }

    /**
     * 批量修改收费项目内容
     * <p>批量修改多个流程的收费项目信息</p>
     *
     * @param bdcSlSfxmPlxgDTO 不动产收费项目批量修改信息
     */
    @ResponseBody
    @PatchMapping("/plxg/sfxm")
    public void plxgSfxm(@RequestBody BdcSlSfxmPlxgDTO bdcSlSfxmPlxgDTO) {
        if (Objects.isNull(bdcSlSfxmPlxgDTO)) {
            throw new AppException(ErrorCode.MISSING_ARG, "缺失收费项目批量修改信息");
        }
        this.bdcSlSfxmFeignService.plxgSfxm(bdcSlSfxmPlxgDTO);
    }

    //推送产权收费信息
    @ResponseBody
    @GetMapping("/tscq")
    public CommonResponse cxtsCqjf(String gzlslid, boolean ignoreError) {
        if (StringUtils.isNotBlank(gzlslid)) {
            List<BdcLcTsjfGxDO> bdcLcTsjfGxDOList = bdcLcTsjfGxFeignService.listLcTsjfGx(gzlslid);

            List<BdcLcTsjfGxDO> errorList = this.checkJfsbm(bdcLcTsjfGxDOList);

            List<String> errorSlbhList = errorList.stream().map(t -> t.getSlbh()).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(errorList) && !ignoreError) {
                return CommonResponse.fail("1", StringUtils.join(errorSlbhList, CommonConstantUtils.ZF_YW_DH) + "存在缴款码，需先作废后在推送", errorList);
            }

            LOGGER.info("推送的产权收费信息{}", JSON.toJSONString(bdcLcTsjfGxDOList));

            List<String> tsSfxxList = new ArrayList<>();
            for (BdcLcTsjfGxDO bdcLcTsjfGxDO : bdcLcTsjfGxDOList) {
                if (CollectionUtils.isNotEmpty(errorSlbhList) && errorSlbhList.contains(bdcLcTsjfGxDO.getSlbh())) {
                    continue;
                }
                BdcTsdjfxxResponseDTO bdcTsdjfxxResponseDTO = bdcSlSfxxFeignService.cxtsdjfxx(bdcLcTsjfGxDO.getSfxxid(), "2", CommonConstantUtils.SF_PJDM, false);
                //如果是推送失败 ，前台进行提示
                if (!StringUtils.equals("00000", bdcTsdjfxxResponseDTO.getStatusCode())) {
                    tsSfxxList.add(bdcTsdjfxxResponseDTO.getSlbh() + bdcTsdjfxxResponseDTO.getMsg());
                }
            }
            if (CollectionUtils.isNotEmpty(tsSfxxList)) {
                return CommonResponse.fail("999", StringUtils.join(tsSfxxList, CommonConstantUtils.ZF_YW_DH));
            }
        }
        return CommonResponse.ok();
    }

    /**
     * 校验收费项目中是否存在缴费书编码
     */
    public List<BdcLcTsjfGxDO> checkJfsbm(List<BdcLcTsjfGxDO> bdcLcTsjfGxDOList) {
        List<String> sfxxidList = bdcLcTsjfGxDOList.stream().map(BdcLcTsjfGxDO::getSfxxid).collect(Collectors.toList());
        Map<String, String> sfxxidWithSlbhMap = bdcLcTsjfGxDOList.stream().collect(Collectors.toMap(BdcLcTsjfGxDO::getSfxxid, BdcLcTsjfGxDO::getSlbh));
        List<BdcLcTsjfGxDO> errorList = new ArrayList<>(10);
        if (CollectionUtils.isNotEmpty(sfxxidList)) {
            BdcSlSfxmQO bdcSlSfxmQO = new BdcSlSfxmQO();
            bdcSlSfxmQO.setSfxxidList(sfxxidList);
            List<BdcSlSfxmDO> bdcSlSfxmDOList = this.bdcSlSfxmFeignService.listFpcxSfxm(bdcSlSfxmQO);
            Map<String, List<BdcSlSfxmDO>> groupMap = bdcSlSfxmDOList.stream().filter(t -> StringUtils.isNotBlank(t.getJfsbm()))
                    .collect(Collectors.groupingBy(BdcSlSfxmDO::getSfxxid));

            if (MapUtils.isNotEmpty(groupMap)) {
                groupMap.forEach((key, value) -> {
                    if (sfxxidWithSlbhMap.containsKey(key)) {
                        BdcLcTsjfGxDO bdcLcTsjfGxDO = new BdcLcTsjfGxDO();
                        bdcLcTsjfGxDO.setSfxxid(key);
                        bdcLcTsjfGxDO.setSlbh(sfxxidWithSlbhMap.get(key));
                        errorList.add(bdcLcTsjfGxDO);
                    }
                });
            }
        }
        return errorList;
    }

    //推送抵押收费信息-- 多条数据相关数据累加
    @ResponseBody
    @GetMapping("/tsdyaq")
    public CommonResponse cxtsDyaq(String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            List<BdcLcTsjfGxDO> bdcLcTsjfGxDOList = bdcLcTsjfGxFeignService.listLcTsjfGx(gzlslid);

            List<BdcLcTsjfGxDO> errorList = this.checkJfsbm(bdcLcTsjfGxDOList);
            List<String> errorSlbhList = errorList.stream().map(t -> t.getSlbh()).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(errorList)) {
                return CommonResponse.fail("1", StringUtils.join(errorSlbhList, CommonConstantUtils.ZF_YW_DH) + "存在缴款码，需先作废后在推送", errorList);
            }

            BdcTsdjfxxResponseDTO bdcTsdjfxxResponseDTO = bdcSlSfxxFeignService.cxtsDyaqSfxx(gzlslid);
            if (!"00000".equals(bdcTsdjfxxResponseDTO.getStatusCode())) {
                return CommonResponse.fail("999", "受理编号：" + bdcTsdjfxxResponseDTO.getSlbh() + bdcTsdjfxxResponseDTO.getMsg());
            }
        }
        return CommonResponse.ok();
    }

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 推送缴费台账获取发票号
     * @date : 2021/9/16 16:15
     */
    @ResponseBody
    @GetMapping("/fph")
    public void getFph(String gzlslid, String ymlx) {
        List<BdcLcTsjfGxDO> bdcLcTsjfGxDOList = bdcLcTsjfGxFeignService.listLcTsjfGx(gzlslid);
        //获取发票号
        if (CollectionUtils.isNotEmpty(bdcLcTsjfGxDOList)) {
            if (StringUtils.isNotBlank(ymlx) && StringUtils.equals("dyaq", ymlx)) {
                // 抵押只获取一个发票号
                List<BdcFphDO> bdcFphDOList = this.bdcXtFphFeignService.getBdcFphxx(1);
                if (CollectionUtils.isEmpty(bdcFphDOList)) {
                    throw new AppException(ErrorCode.MISSING_ARG, "未获取到发票号");
                }
                BdcFphDO bdcFphDO = bdcFphDOList.get(0);
                String fph = bdcFphDO.getFph();
                for (BdcLcTsjfGxDO bdcLcTsjfGxDO : bdcLcTsjfGxDOList) {
                    List<BdcSlSfxmDO> bdcSlSfxmDOList = this.bdcSlSfxmFeignService.listBdcSlSfxmBySfxxid(bdcLcTsjfGxDO.getSfxxid());
                    for (BdcSlSfxmDO bdcSlSfxmDO : bdcSlSfxmDOList) {
                        bdcSlSfxmDO.setFph(fph);
                        bdcSlSfxmDO.setHqfphsj(new Date());
                        bdcFphDO.setSfxmid(bdcSlSfxmDO.getSfxmid());
                    }
                    this.bdcSlSfxmFeignService.batchUpdateBdcSlSfxm(bdcSlSfxmDOList);
                }
                this.bdcXtFphFeignService.updateBdcFphxx(bdcFphDO);
            } else {
                for (BdcLcTsjfGxDO bdcLcTsjfGxDO : bdcLcTsjfGxDOList) {
                    BdcSfxxCzQO bdcSfxxCzQO = new BdcSfxxCzQO();
                    bdcSfxxCzQO.setSfxxid(bdcLcTsjfGxDO.getSfxxid());
                    bdcSfxxCzQO.setSftdsyj(false);
                    this.bdcSfxxFeignService.lqfph(bdcSfxxCzQO);
                }
            }
        }
    }

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 推送缴费台账废除发票号
     * @date : 2021/9/16 16:16
     */
    @ResponseBody
    @GetMapping("/fph/zf")
    public void zfFph(String gzlslid) {
        List<BdcLcTsjfGxDO> bdcLcTsjfGxDOList = bdcLcTsjfGxFeignService.listLcTsjfGx(gzlslid);
        StringBuilder msg = new StringBuilder();
        if (CollectionUtils.isNotEmpty(bdcLcTsjfGxDOList)) {
            for (BdcLcTsjfGxDO bdcLcTsjfGxDO : bdcLcTsjfGxDOList) {
                BdcSfxxCzQO bdcSfxxCzQO = new BdcSfxxCzQO();
                bdcSfxxCzQO.setSfxxid(bdcLcTsjfGxDO.getSfxxid());
                bdcSfxxCzQO.setSftdsyj(false);
                this.bdcSfxxFeignService.zffph(bdcSfxxCzQO);
            }
        }
    }

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 缴费管理台账查询收费状态
     * @date : 2021/9/16 16:36
     */
    @ResponseBody
    @GetMapping("/tsjf/jfcx")
    public void jfztcx(String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            List<BdcLcTsjfGxDO> bdcLcTsjfGxDOList = bdcLcTsjfGxFeignService.listLcTsjfGx(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcLcTsjfGxDOList)) {
                if ("抵押".equals(bdcLcTsjfGxDOList.get(0).getTslx())) {
                    BdcSfxxCzQO bdcSfxxCzQO = new BdcSfxxCzQO();
                    bdcSfxxCzQO.setGzlslid(gzlslid);
                    bdcSfxxCzQO.setTsid(bdcLcTsjfGxDOList.get(0).getTsid());
                    this.bdcSfxxFeignService.dyaplQuerySfztByTsid(bdcSfxxCzQO);
                } else {
                    for (BdcLcTsjfGxDO bdcLcTsjfGxDO : bdcLcTsjfGxDOList) {
                        BdcSfxxCzQO bdcSfxxCzQO = new BdcSfxxCzQO();
                        bdcSfxxCzQO.setSfxxid(bdcLcTsjfGxDO.getSfxxid());
                        bdcSfxxCzQO.setSftdsyj(false);
                        this.bdcSfxxFeignService.querySfzt(bdcSfxxCzQO);
                    }
                }
            }
        }
    }

    /**
     * 作废发票号
     *
     * @param sfxxid 收费信息ID
     * @param fph    发票号
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping(value = "/zffph")
    @ResponseBody
    public void zfBdcFph(@RequestParam(value = "sfxxid") String sfxxid, @RequestParam(value = "fph") String fph) {
        if (StringUtils.isAnyBlank(sfxxid, fph)) {
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到收费信息ID或发票号");
        }
        // 作废发票号
        this.bdcXtFphFeignService.zfBdcFph(fph);
        // 置空收费信息发票号
        BdcSlSfxxDO bdcSlSfxxDO = new BdcSlSfxxDO();
        bdcSlSfxxDO.setSfxxid(sfxxid);
        bdcSlSfxxDO.setFph("");
        this.bdcSlSfxxFeignService.updateBdcSlSfxx(bdcSlSfxxDO);
    }

    /**
     * 获取发票号信息
     *
     * @param sfxxid       收费信息ID
     * @param processInsId 工作流实例ID
     * @return fph 发票号
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping(value = "/lqfph")
    @ResponseBody
    public String lqfph(@RequestParam(value = "sfxxid") String sfxxid, @RequestParam(value = "processInsId") String processInsId) {
        if (StringUtils.isAnyBlank(sfxxid, processInsId)) {
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到收费信息ID或工作流实例ID");
        }
        BdcSlSfxxDO bdcSlSfxxDO = new BdcSlSfxxDO();
        bdcSlSfxxDO.setSfxxid(sfxxid);
        List<BdcSlSfxxDO> bdcSlSfxxDOList = this.bdcSlSfxxFeignService.queryBdcSlSfxx(bdcSlSfxxDO);
        if (CollectionUtils.isEmpty(bdcSlSfxxDOList)) {
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到收费信息");
        }
        List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(processInsId);
        if (CollectionUtils.isEmpty(bdcXmDTOList)) {
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到不动产项目信息");
        }
        List<BdcSlSfxxDO> sfxxWithFphxxList = this.bdcXtFphFeignService.getBdcFph(bdcSlSfxxDOList, bdcXmDTOList.get(0).getSlbh());
        if (CollectionUtils.isEmpty(sfxxWithFphxxList)) {
            throw new AppException(ErrorCode.CUSTOM, "未获取到发票号信息或收费信息为按月结算");
        }
        return sfxxWithFphxxList.get(0).getFph();
    }

    /*
     * 获取工本费数量
     * */
    private void queryGbfSl(List<BdcXmDO> bdcXmList, BdcSlSfxmDO bdcSlSfxmDO, String xmid, String gzlslid, boolean sfcxjs) throws Exception {
        //TODO 已移植到accept包中,后期需要调整将UI包方法删除！！！
        //证书生成数量
        Integer count;
        //工本费数量
        Integer gbfsl = 0;
        BdcSlSfxmSlDTO bdcSlSfxmSlDTO = bdcSlSfxmFeignService.queryYcDjfSl(bdcXmList);
        LOGGER.info("bdcSlSfxmSlDTO:{},xmid:{}", bdcSlSfxmSlDTO, xmid);
        if (StringUtils.equals(systemVersion, CommonConstantUtils.SYSTEM_VERSION_YC) && bdcSlSfxmSlDTO.getSftssfxm()) {
            // 特殊收费根据登记原因获取sfxm
            List<BdcSlSfxmDO> bdcSlSfxmlist = bdcSlSfxmFeignService.listBdcSlSfxmBySfxxidAndDjyy(bdcSlSfxmDO.getSfxxid(), bdcXmList.get(0).getDjyy());
            if (CollectionUtils.isNotEmpty(bdcSlSfxmlist)) {
                bdcSlSfxmDO.setSfxmdm(bdcSlSfxmlist.get(0).getSfxmdm());
                bdcSlSfxmDO.setSfxmbz(bdcSlSfxmlist.get(0).getSfxmbz());
            }
        }
        if (Objects.nonNull(bdcSlSfxmSlDTO) && bdcSlSfxmSlDTO.getSfyydj() && (Objects.isNull(bdcSlSfxmSlDTO.getQtdjfsl()) || Objects.equals(0, bdcSlSfxmSlDTO.getQtdjfsl()))) {
            //如果异议登记,且只有住宅或者非住宅，不再收取工本费，直接删除
            bdcSlSfxmFeignService.deleteBdcSlSfxmBySfxmid(bdcSlSfxmDO.getSfxmid());
        } else {
            //组合流程两个单独的收费根据xmid查对应的收费信息,分别计算数量
            if (StringUtils.isNotBlank(xmid)) {
                count = bdcZsInitFeignService.initXmBdcqzSl(xmid);
                gbfsl = count - 1;
                if (CollectionUtils.isNotEmpty(bdcXmList) && clfmmzydjxlList.contains(bdcXmList.get(0).getDjxl()) && CollectionUtils.isNotEmpty(tssfdjyyList) && tssfdjyyList.contains(bdcXmList.get(0).getDjyy())) {
                    //存量房买卖转移登记，登记原因为权利份额转移的，收费不收登记费，只收工本费，第一本不免费
                    gbfsl = count;
                }
                //考虑批量组合
                gbfsl = gbfsl * bdcXmList.size();
            } else {
                String gzldyid = bdcXmList.get(0).getGzldyid();
                if (StringUtils.isBlank(gzldyid)) {
                    ProcessInstanceData processInstanceData = processInstanceClient.getProcessInstance(gzlslid);
                    if (processInstanceData != null) {
                        gzldyid = processInstanceData.getProcessDefinitionKey();
                    }
                }
                if (StringUtils.isNotBlank(gzldyid)) {
                    //获取当前权利人的信息
                    BdcQlrQO bdcQlrQO = new BdcQlrQO();
                    bdcQlrQO.setXmid(bdcXmList.get(0).getXmid());
                    bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
                    List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
                    Integer sffbcz = 0;
                    String djxl = "";
                    String djyy = "";
                    if (CollectionUtils.isNotEmpty(bdcXmList)) {
                        sffbcz = bdcXmList.get(0).getSqfbcz();
                        djxl = bdcXmList.get(0).getDjxl();
                        djyy = bdcXmList.get(0).getDjyy();
                    }
                    if (hzlcdyidList.contains(gzldyid) || ysbzdyidList.contains(gzldyid)) {
                        /**
                         * @author <a href = "mailto:gaolining@gtmap.cn" > gaolining </a >
                         *@description 内容变更，遗失补证换证流程都是从第一本就开始收费
                         *@date :2019 / 12 / 5 10:53
                         */
                        count = bdcZsInitFeignService.initLcBdcqzSl(gzlslid);
                        gbfsl = count;
                    } else if (CollectionUtils.isNotEmpty(bdcXmList) && clfmmzydjxlList.contains(djxl) && CollectionUtils.isNotEmpty(tssfdjyyList) && tssfdjyyList.contains(djyy)) {
                        //存量房买卖转移登记，登记原因为权利份额转移的，收费不收登记费，只收工本费，第一本不免费
                        //计算工本费
                        if (CommonConstantUtils.SF_F_DM.equals(sffbcz)) {
                            //没有分别持证都是只出一本证,收一本费用
                            gbfsl = 1;
                        }
                        if (CommonConstantUtils.SF_S_DM.equals(sffbcz)) {
                            //单个和批量流程统一计算公式为(权利人数量)*项目数量，不区分收一本证还是收多本证，第一本不免费
                            gbfsl = bdcQlrDOList.size() * bdcXmList.size();
                        }
                    } else if (zjdlcdyidList.contains(gzldyid)) {
                        //宅基地相关流程
                        if (CommonConstantUtils.SF_F_DM.equals(sffbcz)) {
                            gbfsl = 1;
                        }
                        if (CommonConstantUtils.SF_S_DM.equals(sffbcz)) {
                            //分别持证计算公式为((权利人数量)-1)*项目数量，不区分收一本证还是收多本证，第一本不免费
                            gbfsl = (bdcQlrDOList.size() - 1) * bdcXmList.size();
                        }
                    } else {
                        //计算工本费
                        if (CommonConstantUtils.SF_F_DM.equals(sffbcz)) {
                            //没有分别持证都是只出一本证，不收费
                            gbfsl = 0;
                        }
                        if (CommonConstantUtils.SF_S_DM.equals(sffbcz)) {
                            //获取受理项目表的zsxh字段值,为空说明是多本证,不为空说明是一本证,计算方法是(权利人数量-1)* 生成几本证---逻辑已调整
                            //单个和批量流程统一计算公式为(权利人数量-1)*项目数量，不区分收一本证还是收多本证
                            gbfsl = (bdcQlrDOList.size() - 1) * bdcXmList.size();
                        }
                        if (CommonConstantUtils.SF_S_DM.equals(sffbcz) && StringUtils.equals(systemVersion, CommonConstantUtils.SYSTEM_VERSION_YC) && bdcSlSfxmSlDTO.getSftssfxm()) {
                            // 盐城分割析产 工本费数量等于权利人数量
                            gbfsl = bdcQlrDOList.size();
                        }
                        if (CommonConstantUtils.SF_S_DM.equals(sffbcz) && StringUtils.equals(systemVersion, CommonConstantUtils.SYSTEM_VERSION_CZ) && bdcSlSfxmSlDTO.getSftssfxm()) {
                            gbfsl = (bdcQlrDOList.size() - 1) * bdcXmList.size();
                        }
                    }
                }

            }
            // 盐城常州特殊收费，工本费数量默认为1
            if ((StringUtils.equals(systemVersion, CommonConstantUtils.SYSTEM_VERSION_YC) || StringUtils.equals(systemVersion, CommonConstantUtils.SYSTEM_VERSION_CZ)) && bdcSlSfxmSlDTO.getSftssfxm() && gbfsl == 0) {
                gbfsl = 1;
                bdcSlSfxmDO.setSl(1.00);
                bdcSlSfxmDO.setYsje(bdcSlSfxmDO.getSl() * 10);
                bdcSlSfxmDO.setSsje(bdcSlSfxmDO.getYsje());
                bdcSlSfxmFeignService.updateBdcSlSfxm(bdcSlSfxmDO);
            }
            //数据库为空，读取gbfsl，否则读取数据库值，最小为0
            if (bdcSlSfxmDO.getSl() == null || sfcxjs) {
                bdcSlSfxmDO.setSl((double) (gbfsl >= 0 ? gbfsl : 0));
                bdcSlSfxmDO.setYsje(bdcSlSfxmDO.getSl() * 10);
                bdcSlSfxmDO.setSsje(bdcSlSfxmDO.getYsje());
                bdcSlSfxmFeignService.updateBdcSlSfxm(bdcSlSfxmDO);
            }
        }
    }

    /*
     * 计算常州土地使用权服务费
     * */

    private List<BdcSlSfxmDO> countTdsyqFwf(List<BdcSlSfxmDO> bdcSlSfxmDOList, String gzlslid, List<BdcXmDO> bdcXmList, boolean sfcxjs) {
        //TODO 已移植到accept包中,后期需要调整将UI包方法删除！！！
        //常州计算土地使用权服务费
        List<BdcSlSfxmDO> newSlSfxmList = new ArrayList<>();
        BdcSlSfxmDO bdcSlSfxmDO = bdcSlSfxmDOList.get(0);
        //重新计算时删除前面的土地交易收费项目数据
        if (sfcxjs) {
            for (BdcSlSfxmDO bdcSlSfxm : bdcSlSfxmDOList) {
                bdcSlSfxmFeignService.deleteBdcSlSfxmBySfxmid(bdcSlSfxm.getSfxmid());
            }
        }
        if (Objects.isNull(bdcSlSfxmDO.getYsje()) || sfcxjs) {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setGzlslid(gzlslid);
            List<BdcXmAndFbDTO> bdcXmAndFbDTOList = bdcXmFeignService.listBdcXmAndFb(bdcXmQO);
            //根据宗地数量去重
            bdcXmAndFbDTOList = bdcXmAndFbDTOList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getBdcdyh().substring(0, 19)))), ArrayList::new));

            if (CollectionUtils.isNotEmpty(bdcXmAndFbDTOList)) {
                BdcXmAndFbDTO xmxx = bdcXmAndFbDTOList.get(0);
                // 判断是否产权
                if (!ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, xmxx.getQllx())) {
                    //计算逻辑
                    for (int i = 0; i < bdcXmAndFbDTOList.size(); i++) {
                        double tdsyqjyf = 0.00;
                        BdcXmAndFbDTO bdcXmAndFbDTO = bdcXmAndFbDTOList.get(i);
                        if (Objects.nonNull(bdcXmAndFbDTO.getDdjb()) && ArrayUtils.contains(Constants.DDJB1_3, bdcXmAndFbDTO.getDdjb()) && Objects.nonNull(bdcXmAndFbDTO.getZdzhmj())) {
                            bdcSlSfxmDO.setSfxmbz("3元/平");
                            tdsyqjyf = (bdcXmAndFbDTO.getZdzhmj() * 3) / 2;
                        }
                        if (Objects.nonNull(bdcXmAndFbDTO.getDdjb()) && ArrayUtils.contains(Constants.DDJB4_6, bdcXmAndFbDTO.getDdjb()) && Objects.nonNull(bdcXmAndFbDTO.getZdzhmj())) {
                            tdsyqjyf = (bdcXmAndFbDTO.getZdzhmj() * 2.6) / 2;
                            bdcSlSfxmDO.setSfxmbz("2.6元/平");
                        }
                        bdcSlSfxmDO.setYsje(tdsyqjyf);
                        bdcSlSfxmDO.setSsje(tdsyqjyf);
                        bdcSlSfxmDO.setSl(bdcXmAndFbDTO.getZdzhmj());
                        if (i == 0 && !sfcxjs) {
                            //第一条数据直接更新收费项目，如果有其他不同地段级别的，新增收费项目服务费
                            bdcSlSfxmFeignService.updateBdcSlSfxm(bdcSlSfxmDO);
                            newSlSfxmList.add(bdcSlSfxmDO);
                        } else {
                            BdcSlSfxmDO bdcSlSfxm = new BdcSlSfxmDO();
                            BeanUtils.copyProperties(bdcSlSfxmDO, bdcSlSfxm);
                            bdcSlSfxm.setSfxmid(UUIDGenerator.generate16());
                            bdcSlSfxmFeignService.insertBdcSlSfxm(bdcSlSfxm);
                            newSlSfxmList.add(bdcSlSfxm);
                        }
                    }
                }

                //土地使用权抵押权收费
                if (CommonConstantUtils.QLLX_DYAQ_DM.equals(xmxx.getQllx())) {
                    //获取宗地数量，根据前19位地籍号去重
                    for (int i = 0; i < bdcXmAndFbDTOList.size(); i++) {
                        BdcXmAndFbDTO bdcXmAndFbDTO = bdcXmAndFbDTOList.get(i);
                        //根据项目id获取权利
                        BdcQl bdcQl = bdcQllxFeignService.queryQlxx(bdcXmAndFbDTO.getXmid());
                        if (bdcQl instanceof BdcDyaqDO) {
                            BdcDyaqDO bdcDyaqDO = (BdcDyaqDO) bdcQl;
                            double dyajg = Objects.nonNull(bdcDyaqDO.getBdbzzqse()) ? bdcDyaqDO.getBdbzzqse() : (Objects.nonNull(bdcDyaqDO.getZgzqe()) ? bdcDyaqDO.getZgzqe() : 0);
                            if (Objects.nonNull(dyajg)) {
                                double tdsyqdyaf = 0.00;
                                if (dyajg <= 200) {
                                    tdsyqdyaf = 500.00;
                                } else if (200 < dyajg && dyajg <= 500) {
                                    tdsyqdyaf = 1000.00;
                                } else if (dyajg > 500 && dyajg <= 1000) {
                                    tdsyqdyaf = 1500.00;
                                } else if (dyajg > 1000 && dyajg <= 3000) {
                                    tdsyqdyaf = 2000.00;
                                } else if (dyajg > 3000) {
                                    tdsyqdyaf = 5000.00;
                                }
                                bdcSlSfxmDO.setYsje(tdsyqdyaf);
                                bdcSlSfxmDO.setSsje(tdsyqdyaf);
                            }
                        }
                        bdcSlSfxmDO.setSl(1.00);
                        if (i == 0 && !sfcxjs) {
                            bdcSlSfxmFeignService.updateBdcSlSfxm(bdcSlSfxmDO);
                            newSlSfxmList.add(bdcSlSfxmDO);
                        } else {
                            BdcSlSfxmDO bdcSlSfxm = new BdcSlSfxmDO();
                            BeanUtils.copyProperties(bdcSlSfxmDO, bdcSlSfxm);
                            bdcSlSfxm.setSfxmid(UUIDGenerator.generate16());
                            bdcSlSfxmFeignService.insertBdcSlSfxm(bdcSlSfxm);
                            newSlSfxmList.add(bdcSlSfxm);
                        }
                    }
                }
            }
            countHj(gzlslid);
        } else {
            newSlSfxmList.addAll(bdcSlSfxmDOList);
        }
        return newSlSfxmList;
    }


    /**
     * @param json
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除推送缴费
     * @date : 2021/10/26 16:30
     */
    @DeleteMapping(value = "/sctsjfgx/{gzlslid}")
    @ResponseBody
    public Integer deleteTsjf(@RequestBody String json, @PathVariable(value = "gzlslid") String gzlslid) {
        if (StringUtils.isNotBlank(json)) {
            List<String> sfxxidList = JSONArray.parseArray(json, String.class);
            LOGGER.warn("开始删除流程推送缴费关系数据{}", json);
            return bdcLcTsjfGxFeignService.deleteLcTsJfxxBySfxxid(sfxxidList, gzlslid);
        }
        return null;
    }


    @ResponseBody
    @GetMapping("/hj")
    public void countHj(String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxFeignService.listBdcSlSfxxByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
                for (BdcSlSfxxDO bdcSlSfxxDO : bdcSlSfxxDOList) {
                    List<BdcSlSfxmDO> bdcSlSfxmDOList = bdcSlSfxmFeignService.listBdcSlSfxmBySfxxid(bdcSlSfxxDO.getSfxxid());
                    double hj = bdcSlSfxmDOList.stream().filter(sfxm -> null != sfxm.getSsje()).map(t -> new BigDecimal(String.valueOf(t.getSsje())))
                            .reduce((m, n) -> m.add(n)).map(BigDecimal::doubleValue).orElse(0d);
                    bdcSlSfxxDO.setHj(hj);
//                    LOGGER.info("合计{}", hj);
                }
                bdcSlSfxxFeignService.updateBdcSlSfxxList(bdcSlSfxxDOList);
            }
        }
    }

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 保存抵押推送时联系人的信息
     * @date : 2021/11/3 14:27
     */
    @ResponseBody
    @GetMapping("/dyats/lxrxx")
    public void saveDyaTsLxrxx(String gzlslid, String lxrmc, String lxdh) {
        if (StringUtils.isNotBlank(gzlslid) && StringUtils.isNotBlank(lxrmc) && StringUtils.isNotBlank(lxdh)) {
            List<BdcLcTsjfGxDO> bdcLcTsjfGxDOList = bdcLcTsjfGxFeignService.listLcTsjfGx(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcLcTsjfGxDOList)) {
                for (BdcLcTsjfGxDO bdcLcTsjfGxDO : bdcLcTsjfGxDOList) {
                    bdcLcTsjfGxDO.setLxrmc(lxrmc);
                    bdcLcTsjfGxDO.setLxdh(lxdh);
                }
                bdcLcTsjfGxFeignService.batchUpdateLcTsjfGx(bdcLcTsjfGxDOList);
            }
        }
    }

    @ResponseBody
    @GetMapping("/lctsjfgx")
    public Object queryLcTsjfGx(String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            List<BdcLcTsjfGxDO> bdcLcTsjfGxDOList = bdcLcTsjfGxFeignService.listLcTsjfGx(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcLcTsjfGxDOList)) {
                return bdcLcTsjfGxDOList.get(0);
            }
        }
        return null;
    }

    /**
     * @param bdcXmDOList 项目信息集合
     * @param gzlslid     工作流实例ID
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2021/11/3 9:38
     */
    public String checkSameProcess(List<BdcXmDO> bdcXmDOList, String gzlslid) {
        Set<String> slbhSet = new HashSet<>(1);
        if (CollectionUtils.isNotEmpty(bdcXmDOList) && StringUtils.isNotBlank(gzlslid)) {
            List<BdcLcTsjfGxDO> bdcLcTsjfGxDOList = bdcLcTsjfGxFeignService.listLcTsjfGx(gzlslid);
            String gzldyid = "";
            if (CollectionUtils.isNotEmpty(bdcLcTsjfGxDOList)) {
                //如果存在数据，随机取一条数据查工作流定义id
                List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxBySlbh(bdcLcTsjfGxDOList.get(0).getSlbh());
                if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
                    gzldyid = bdcXmDTOList.get(0).getGzldyid();
                }
            } else {
                gzldyid = bdcXmDOList.get(0).getGzldyid();
            }
            for (BdcXmDO bdcXmDO : bdcXmDOList) {
                if (!StringUtils.equals(gzldyid, bdcXmDO.getGzldyid())) {
                    //只要有一条数据不属于相同流程，抛出异常
                    slbhSet.add(bdcXmDO.getSlbh());
                }
            }
            if (CollectionUtils.isNotEmpty(slbhSet)) {
                return "以下受理编号不属于同一流程：" + StringUtils.join(slbhSet, CommonConstantUtils.ZF_YW_DH);
            }
        }
        return null;
    }


    /**
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 原收费流程自动转发
     * @date : 2021/11/3 20:49
     */
    @ResponseBody
    @GetMapping("/ylc/plzf")
    public Object ysflcPlzf(String gzlslid) throws Exception {
        HashMap<String, String> resultMap = new HashMap(1);
        //转发成功的受理编号
        List<String> successSlbhList = new ArrayList<>(1);
        //转发失败的受理编号
        List<String> failSlbhList = new ArrayList<>(1);
        //不符合转发条件的受理编号
        List<String> notSfjdList = new ArrayList<>(1);
        if (StringUtils.isNotBlank(gzlslid)) {
            List<BdcLcTsjfGxDO> bdcLcTsjfGxDOList = bdcLcTsjfGxFeignService.listLcTsjfGx(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcLcTsjfGxDOList)) {
                //根据受理编号找到项目信息，然后找到当前的工作流节点信息
                for (BdcLcTsjfGxDO bdcLcTsjfGxDO : bdcLcTsjfGxDOList) {
                    List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxBySlbh(bdcLcTsjfGxDO.getSlbh());
                    if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
                        String slid = bdcXmDTOList.get(0).getGzlslid();
                        List<TaskData> listTask = processTaskClient.processLastTasks(slid);
//
                        if (CollectionUtils.isNotEmpty(listTask) && StringUtils.equals("收费", listTask.get(0).getTaskName())) {
                            LOGGER.info("当前任务节点信息{}", JSON.toJSONString(listTask.get(0)));
                            //转发下一个节点
                            List<ForwardTaskDto> forwardTaskDtos = flowableNodeClient.getForwardUserTasksWithVariable(listTask.get(0).getTaskId(), "organization", false, getNodeVariableDTOS(listTask.get(0).getTaskId()));
                            LOGGER.error("当前受理编号{}的下一个节点转发信息:{}", bdcXmDTOList.get(0).getSlbh(), JSON.toJSONString(forwardTaskDtos));
                            if (CollectionUtils.isNotEmpty(forwardTaskDtos)) {
                                ForwardTaskDto forwardTaskDto = forwardTaskDtos.get(0);
                                LOGGER.info("默认角色id{}", forwardTaskDto.getDefaultRoleId());
                                LOGGER.info("默认用户{}", forwardTaskDto.getDefaultUserName());
                                forwardTaskDto.setSelectRoleIds(forwardTaskDto.getDefaultRoleId());
                                forwardTaskDto.setSelectUserNames(forwardTaskDto.getDefaultUserName());
                                forwardTaskDto.setTaskId(listTask.get(0).getTaskId());
                                boolean zfResult = taskHandleClient.complete(forwardTaskDtos);
                                if (zfResult) {
                                    successSlbhList.add(bdcXmDTOList.get(0).getSlbh());
                                } else {
                                    failSlbhList.add(bdcXmDTOList.get(0).getSlbh());
                                }
                            }
                        } else {
                            notSfjdList.add(bdcXmDTOList.get(0).getSlbh());

                        }
                    }
                }
                resultMap.put("success", StringUtils.join(successSlbhList, CommonConstantUtils.ZF_YW_DH));
                resultMap.put("fail", StringUtils.join(failSlbhList, CommonConstantUtils.ZF_YW_DH));
                resultMap.put("warn", StringUtils.join(notSfjdList, CommonConstantUtils.ZF_YW_DH));
            }
        }
        return resultMap;
    }

    /**
     * 组织网关配置所需参数
     * 只针对常州收费的，配置条件固定
     *
     * @param taskId taskId
     * @return {List} 网关配置参数
     * @author <a href ="mailto:lixin1@gtmap.cn">gaolining</a>
     */
    private List<NodeVariableDTO> getNodeVariableDTOS(String taskId) {
        List<NodeVariableDTO> params = Lists.newArrayList();
        NodeVariableDTO nextNodeDTO = new NodeVariableDTO();
        nextNodeDTO.setKey("g");
        nextNodeDTO.setValue("1");
        params.add(nextNodeDTO);
        return params;
    }

    /**
     * 已缴费查询台账
     */
    @ResponseBody
    @GetMapping("/wjf/page")
    public Object listSlWjfByPage(@LayuiPageable Pageable pageable, BdcSlSfxxQO bdcSlSfxxQO) {
        if (StringUtils.isNotBlank(bdcSlSfxxQO.getJkfs()) || Objects.nonNull(bdcSlSfxxQO.getCxqssj()) || Objects.nonNull(bdcSlSfxxQO.getCxjssj())) {
            bdcSlSfxxQO.setSfzt(BdcSfztEnum.YJF.key());
            bdcSlSfxxQO.setQxdmList(this.getCurrentUserPljfQxdm());
            Page<BdcSlWjfDTO> bdcSlWjfDTOPage = bdcSlSfxxFeignService.listSlWjfByPage(pageable, JSON.toJSONString(bdcSlSfxxQO));
            // 数据分组处理， 将相同收费信息ID 都是登记费的数据合并
            List<BdcSlWjfDTO> bdcSlWjfDTOList = bdcSlWjfDTOPage.getContent();
            Map<String, List<BdcSlWjfDTO>> groupMap = bdcSlWjfDTOList.stream().collect(Collectors.groupingBy(t -> t.getSfxxid() + "_" + t.getSfxmdm()));
            List<BdcSlWjfDTO> filterContent = new ArrayList<>(bdcSlWjfDTOList.size());
            groupMap.forEach((key, value) -> {
                if (value.size() > 1) {
                    Double djf = value.stream().filter(t -> StringUtils.isNotBlank(t.getDjf()))
                            .mapToDouble(t -> Double.parseDouble(t.getDjf())).sum();
                    value.get(0).setDjf(DoubleUtils.getString(djf));
                    filterContent.add(value.get(0));
                } else {
                    filterContent.addAll(value);
                }
            });
            return addLayUiCode(new PageImpl<>(
                    filterContent.stream().sorted(
                            Comparator.comparing(t -> {
                                if (StringUtils.isNotBlank(t.getJfsbm())) {
                                    String jfsbm = t.getJfsbm();
                                    return Float.parseFloat(jfsbm.substring(jfsbm.length() - 12, jfsbm.length()));
                                } else {
                                    return null;
                                }
                            }, Comparator.nullsLast(Float::compareTo))
                    ).collect(Collectors.toList()),
                    pageable, bdcSlWjfDTOPage.getTotalElements()));
        }
        return addLayUiCode(new PageImpl<>(Collections.emptyList(), pageable, 0));
    }

    /**
     * 获取当前人的区县代码，根据配置获取能够查询出来的qxdm数据
     * 例：当前人所在 320400， 批量缴费数据能够查询到 320401，320402的数据，不配置默认为320400
     * <p>sfxx.pljf.320400 = 320401,320402</p>
     *
     * @return 区县代码
     */
    private List<String> getCurrentUserPljfQxdm() {
        List<String> qxdmList = new ArrayList<>(10);
        // 获取当前人的区县代码
        String qxdm = userManagerUtils.getRegionCodeByUserName(userManagerUtils.getCurrentUserName());
        if (StringUtils.isNotBlank(qxdm)) {
            String qxdms = (String) Container.getBean(BdcConfigUtils.class).getConfigValueByQxdm("sfxx.pljf", qxdm, qxdm);
            if (StringUtils.isNotBlank(qxdms)) {
                qxdmList.addAll(Arrays.asList(qxdms.split(",")));
            }
        }
        return qxdmList;
    }

    @ResponseBody
    @GetMapping("/wjf")
    public Object listSlWjf(BdcSlSfxxQO bdcSlSfxxQO) {
        if (StringUtils.isNotBlank(bdcSlSfxxQO.getJkfs()) || Objects.nonNull(bdcSlSfxxQO.getCxqssj()) || Objects.nonNull(bdcSlSfxxQO.getCxjssj())) {
            bdcSlSfxxQO.setSfzt(BdcSfztEnum.YJF.key());
            bdcSlSfxxQO.setQxdmList(this.getCurrentUserPljfQxdm());
            List<BdcSlWjfDTO> bdcSlWjfDTOList = bdcSlSfxxFeignService.listSlWjfxx(JSON.toJSONString(bdcSlSfxxQO));
            // 数据分组处理， 将相同收费信息ID 都是登记费的数据合并
            Map<String, List<BdcSlWjfDTO>> groupMap = bdcSlWjfDTOList.stream().collect(Collectors.groupingBy(t -> t.getSfxxid() + "_" + t.getSfxmdm()));
            List<BdcSlWjfDTO> filterContent = new ArrayList<>(bdcSlWjfDTOList.size());
            groupMap.forEach((key, value) -> {
                if (value.size() > 1) {
                    Double djf = value.stream().filter(t -> StringUtils.isNotBlank(t.getDjf()))
                            .mapToDouble(t -> Double.parseDouble(t.getDjf())).sum();
                    value.get(0).setDjf(DoubleUtils.getString(djf));
                    filterContent.add(value.get(0));
                } else {
                    filterContent.addAll(value);
                }
            });

            // 截取缴费书编码后7位进行排序
            return filterContent.stream().sorted(
                    Comparator.comparing(t -> {
                        if (StringUtils.isNotBlank(t.getJfsbm())) {
                            String jfsbm = t.getJfsbm();
                            return Float.parseFloat(jfsbm.substring(jfsbm.length() - 12, jfsbm.length()));
                        } else {
                            return null;
                        }
                    }, Comparator.nullsLast(Float::compareTo))
            ).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @ResponseBody
    @GetMapping("/wjfhj")
    public Object countWjfHj(BdcSlSfxxQO bdcSlSfxxQO) {
        if (StringUtils.isNotBlank(bdcSlSfxxQO.getJkfs()) || Objects.nonNull(bdcSlSfxxQO.getCxqssj()) || Objects.nonNull(bdcSlSfxxQO.getCxjssj())) {
            bdcSlSfxxQO.setSfzt(BdcSfztEnum.YJF.key());
            return bdcSlSfxxFeignService.countWjfJe(JSON.toJSONString(bdcSlSfxxQO));
        }
        return null;
    }

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新未缴费状态
     * @date : 2021/11/5 10:19
     */
    @ResponseBody
    @GetMapping("/wjf/gxsfzt")
    public void updateSfzt(BdcSlSfxxQO bdcSlSfxxQO) {
        if (StringUtils.isNotBlank(bdcSlSfxxQO.getGxqssj()) && StringUtils.isNotBlank(bdcSlSfxxQO.getGxjssj())) {
            //查询当前时间段内未缴费的数据且缴费书编码不为空的信息
            bdcSlSfxxQO.setSfzt(BdcSfztEnum.WJF.key());
            List<BdcSlWjfDTO> bdcSlWjfDTOList = bdcSlSfxxFeignService.listSlWjfxx(JSON.toJSONString(bdcSlSfxxQO));
            LOGGER.info("当前时间段内收费状态为未缴费的数据{}", JSON.toJSONString(bdcSlWjfDTOList));
            if (CollectionUtils.isNotEmpty(bdcSlWjfDTOList)) {
                for (BdcSlWjfDTO bdcSlWjfDTO : bdcSlWjfDTOList) {
                    BdcSfxxCzQO bdcSfxxCzQO = new BdcSfxxCzQO();
                    bdcSfxxCzQO.setSfxxid(bdcSlWjfDTO.getSfxxid());
                    bdcSfxxCzQO.setTsly("1");
                    if (StringUtils.equals(bdcSlWjfDTO.getSftdsyj(), "1")) {
                        bdcSfxxCzQO.setSftdsyj(true);
                    } else {
                        bdcSfxxCzQO.setSftdsyj(false);
                    }
                    try {
                        bdcSfxxFeignService.querySfzt(bdcSfxxCzQO);
                    } catch (Exception e) {
                        LOGGER.error("当前收费信息：{},查询收费状态失败，失败原因：{}", bdcSfxxCzQO.getSfxxid(), e.getMessage());
                    }
                }
            }
        }
    }

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 原流程批量退回
     * @date : 2021/11/17 10:58
     */
    @ResponseBody
    @GetMapping("/ylc/plth")
    public Object plthYlc(String gzlslid) {
        HashMap<String, String> resultMap = new HashMap(1);
        //退回成功的受理编号
        List<String> successSlbhList = new ArrayList<>(1);
        //退回失败的受理编号
        List<String> failSlbhList = new ArrayList<>(1);
        //不符合退回条件的受理编号
        List<String> notSfjdList = new ArrayList<>(1);
        if (StringUtils.isNotBlank(gzlslid)) {
            List<BdcLcTsjfGxDO> bdcLcTsjfGxDOList = bdcLcTsjfGxFeignService.listLcTsjfGx(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcLcTsjfGxDOList)) {
                //根据gzlslid 去重
                bdcLcTsjfGxDOList = bdcLcTsjfGxDOList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(BdcLcTsjfGxDO::getSlbh))), ArrayList::new));
                //根据受理编号找到项目信息，然后找到当前的工作流节点信息
                for (BdcLcTsjfGxDO bdcLcTsjfGxDO : bdcLcTsjfGxDOList) {
                    List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxBySlbh(bdcLcTsjfGxDO.getSlbh());
                    if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
                        String slid = bdcXmDTOList.get(0).getGzlslid();
                        //根据实例id获取当前的任务信息
                        List<TaskData> listTask = processTaskClient.processLastTasks(slid);
                        if (CollectionUtils.isNotEmpty(listTask) && StringUtils.equals("收费", listTask.get(0).getTaskName())) {
                            LOGGER.info("当前受理编号{}，当前任务节点信息{}", bdcLcTsjfGxDO.getSlbh(), JSON.toJSONString(listTask.get(0)));
                            //获取退回的节点和用户信息
                            List<BackTaskDto> backTaskDtoList = flowableNodeClient.getBackUserTasks(listTask.get(0).getTaskId());
                            if (CollectionUtils.isNotEmpty(backTaskDtoList)) {
                                LOGGER.info("当前受理编号{}，获取到退回的节点和用户信息{}", bdcLcTsjfGxDO.getSlbh(), JSON.toJSONString(backTaskDtoList, SerializerFeature.WriteNullStringAsEmpty));
                                for (BackTaskDto backTaskDto : backTaskDtoList) {
                                    backTaskDto.setTaskId(listTask.get(0).getTaskId());
                                    backTaskDto.setOpinion("收费节点退回受理");
                                }
                                boolean backResult = taskHandleClient.back(backTaskDtoList);
                                if (backResult) {
                                    successSlbhList.add(bdcLcTsjfGxDO.getSlbh());
                                } else {
                                    failSlbhList.add(bdcLcTsjfGxDO.getSlbh());
                                }
                            } else {
                                failSlbhList.add(bdcLcTsjfGxDO.getSlbh());
                                LOGGER.error("受理编号{}未获取到退回的节点和用户信息", bdcLcTsjfGxDO.getSlbh());
                            }
                        } else {
                            notSfjdList.add(bdcXmDTOList.get(0).getSlbh());
                        }
                    }
                }
                resultMap.put("success", StringUtils.join(successSlbhList, CommonConstantUtils.ZF_YW_DH));
                resultMap.put("fail", StringUtils.join(failSlbhList, CommonConstantUtils.ZF_YW_DH));
                resultMap.put("warn", StringUtils.join(notSfjdList, CommonConstantUtils.ZF_YW_DH));
            }
        }
        return resultMap;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 收费模式是否区县模式
     */
    @ResponseBody
    @GetMapping("/nantong/sfmode")
    public boolean sfmode(String gzlslid, String configName) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("查询收费模式工作流实例ID为空");
        }
        return StringUtils.isNotBlank(bdcSlSfxxFeignService.nantongSfMode(gzlslid, configName)) ;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 收费模式区县beanName名称
     */
    @ResponseBody
    @GetMapping("/nantong/sfmode/name")
    public String sfmodeName(String gzlslid, String configName) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("查询收费模式工作流实例ID为空");
        }
        return bdcSlSfxxFeignService.nantongSfMode(gzlslid, configName);
    }

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新缴费人信息
     * @date : 2021/12/23 10:39
     */
    @ResponseBody
    @PutMapping("/jfrxm")
    public void upodateJfrxm(@RequestParam(name = "gzlslid") String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxFeignService.listBdcSlSfxxByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
                for (BdcSlSfxxDO bdcSlSfxxDO : bdcSlSfxxDOList) {
                    if (StringUtils.isNotBlank(bdcSlSfxxDO.getXmid())) {
                        BdcXmQO bdcXmQO = new BdcXmQO();
                        bdcXmQO.setXmid(bdcSlSfxxDO.getXmid());
                        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
                        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                            //缴费人姓名
                            BdcQlrDO bdcYwr = bdcSlSqrFeignService.generateYwr(gzlslid, CommonConstantUtils.QLRLB_YWR, bdcXmDOList.get(0).getDjxl());
                            if (StringUtils.equals(CommonConstantUtils.QLRLB_QLR, bdcSlSfxxDO.getQlrlb())) {
                                bdcSlSfxxDO.setJfrxm(bdcXmDOList.get(0).getQlr());
                                bdcSlSfxxDO.setYwr(StringUtils.isNotBlank(bdcYwr.getQlrmc()) ? bdcYwr.getQlrmc() : "");
                            } else if (StringUtils.equals(CommonConstantUtils.QLRLB_YWR, bdcSlSfxxDO.getQlrlb())) {
                                String str = bdcQlrFeignService.queryQlrsYbzs(gzlslid, CommonConstantUtils.QLRLB_YWR, bdcXmDOList.get(0).getDjxl());
                                if (StringUtils.isNotBlank(str)) {
                                    bdcSlSfxxDO.setJfrxm(StringUtils.join(str.split(" "), ","));
                                }
                                bdcSlSfxxDO.setYwr(StringUtils.isNotBlank(bdcYwr.getQlrmc()) ? bdcYwr.getQlrmc() : "");
                            }
                        }
                        bdcSlSfxxFeignService.updateBdcSlSfxx(bdcSlSfxxDO);
                    }
                }
            }
        }
    }

    /**
     * @param sfxxid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 舒城缴费查询
     * @date : 2022/1/24 11:23
     */
    @GetMapping("/jfcx/sc")
    @ResponseBody
    public void scJfcx(String sfxxid) {
        if (StringUtils.isNotBlank(sfxxid)) {
            BdcSlSfxxDO bdcSlSfxxDO = bdcSlSfxxFeignService.queryBdcSlSfxxBySfxxid(sfxxid);
            if (Objects.nonNull(bdcSlSfxxDO)) {
                if (StringUtils.isBlank(bdcSlSfxxDO.getJfsbm())) {
                    throw new AppException("当前收费还未获取到缴费编码billNo");
                }
                Map<String, Object> paramMap = new HashMap<>(2);
                paramMap.put("billNo", bdcSlSfxxDO.getJfsbm());
                Object response = exchangeInterfaceFeignService.requestInterface("jfpt_jfztcx", paramMap);
                if (response != null) {
                    LOGGER.info("收费信息ID:{},缴费书编码{},获取财政收费状态接口调用成功,返回结果：{}", sfxxid, bdcSlSfxxDO.getJfsbm(), response);
                    BdcQuerySfztDTO bdcQuerySfztDTO = JSON.parseObject(JSON.toJSONString(response), BdcQuerySfztDTO.class);
                    if (bdcQuerySfztDTO != null && bdcQuerySfztDTO.getSfzt() != null) {
                        bdcSlSfxxDO.setSfzt(bdcQuerySfztDTO.getSfzt());
                        bdcSlSfxxFeignService.updateBdcSlSfxx(bdcSlSfxxDO);
                    }
                }
            }
        }
    }

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询当前流程合一支付的二维码
     * @date : 2022/5/17 16:47
     */
    @ResponseBody
    @GetMapping("/hyzf/ewm")
    public Object queryHyzfEwm(String gzlslid, String xmid) {
        if (StringUtils.isBlank(gzlslid)) {
            return null;
        }
        return bdcSfxxFeignService.queryHyzfEwm(gzlslid, xmid);
    }


    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询合一支付的缴费状态
     * @date : 2022/5/17 16:55
     */
    @ResponseBody
    @GetMapping("/hyzf/zfzt")
    public void queryHyzfJfzt(String gzlslid, String xmid) {
        bdcSfxxFeignService.queryHyzfJfzt(gzlslid, xmid);
    }

    /**
     * @param bdcSlJfblQO
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @description 缴费办理
     * @date : 2022/5/25 9:43
     */
    @ResponseBody
    @PostMapping("/jfbl")
    public BdcSfjfblResponseDTO jfbl(@RequestBody BdcSlJfblQO bdcSlJfblQO) {
        if (Objects.isNull(bdcSlJfblQO) || StringUtils.isBlank(bdcSlJfblQO.getSfxxid())) {
            throw new MissingArgumentException("请求参数为空或收费信息id为空");
        }
        return bdcSfxxFeignService.jfbl(bdcSlJfblQO);

    }

    /**
     * @param bdcSlJfblQO
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @description 查询缴费情况
     * @date : 2022/5/26 18:43
     */
    @ResponseBody
    @PostMapping("/cxjfqk")
    public void cxjfqk(@RequestBody BdcSlJfblQO bdcSlJfblQO) {
        if (Objects.isNull(bdcSlJfblQO) || StringUtils.isBlank(bdcSlJfblQO.getSfxxid())) {
            throw new MissingArgumentException("请求参数为空或收费信息id为空");
        }
        bdcSfxxFeignService.cxjfqk(bdcSlJfblQO, false);
    }

    /**
     * @param bdcSlJfblQO
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @description 非税开票
     * @date : 2022/5/27 9:43
     */
    @ResponseBody
    @PostMapping("/fskp")
    Map<String, String> hafskp(@RequestBody BdcSlJfblQO bdcSlJfblQO) {
        if (Objects.isNull(bdcSlJfblQO) || StringUtils.isBlank(bdcSlJfblQO.getGzlslid())) {
            throw new MissingArgumentException("请求参数为空或工作流实例id为空");
        }
        return bdcSfxxFeignService.fskp(bdcSlJfblQO);
    }

    /**
     * @param bdcSlJfblQO
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @description 作废缴款通知书
     * @date : 2022/5/26 18:43
     */
    @ResponseBody
    @PostMapping("/zfjktzs")
    Map<String, String> zfjktzs(@RequestBody BdcSlJfblQO bdcSlJfblQO) {
        if (Objects.isNull(bdcSlJfblQO) || StringUtils.isBlank(bdcSlJfblQO.getSfxxid())) {
            throw new MissingArgumentException("请求参数为空或收费信息id为空");
        }
        return bdcSfxxFeignService.zfjktzs(bdcSlJfblQO);
    }

    /**
     * @param slbh
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 挂接收费信息
     * @date : 2022/8/1 13:37
     */
    @ResponseBody
    @GetMapping("/gjsfxx")
    public void gjSfxxBySlbh(String slbh, String qlrlb, String sfxxid) {
        if (StringUtils.isNotBlank(slbh) && StringUtils.isNotBlank(sfxxid)) {
            bdcSfxxFeignService.gjSfxx(slbh, sfxxid, qlrlb);
        }
    }

    /**
     * @param
     * @param htbh
     * @param gzlslid
     * @return java.util.List<cn.gtmap.realestate.common.core.dto.accept.BdcYhkkDTO>
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @date 2022/8/20 15:51
     * @description 获取银行扣款信息
     **/
    @ResponseBody
    @PostMapping("/getYhkk")
    public List<BdcYhkkDTO> getYhkk(@RequestParam(value = "htbh") String htbh,
                                    @RequestParam(value = "gzlslid") String gzlslid) {
        if (StringUtils.isAnyBlank(htbh, gzlslid)) {
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到合同编号或工作流实例ID");
        }
        return bdcSfxxFeignService.getYhkkxx(htbh, gzlslid);
    }

    /**
     * @param
     * @param gzlslid
     * @param qlrlb
     * @return java.lang.Object
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @date 2022/8/22 8:55
     * @description 获取税票
     **/
    @ResponseBody
    @PostMapping("/hqsp")
    public CommonResponse hqsp(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "qlrlb") String qlrlb) {
        if (StringUtils.isAnyBlank(gzlslid, qlrlb)) {
            throw new AppException(ErrorCode.MISSING_ARG, "缺少参数工作流实例ID或权利人类别");
        }
        return bdcSfxxFeignService.hqsp(gzlslid, qlrlb);
    }

    /**
     * @param bdcSlSqrSfxxDTO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 判断是否必须现场收费
     * @date : 2022/4/18 16:13
     */
    public void judgeXcsf(BdcSlSqrSfxxDTO bdcSlSqrSfxxDTO) {
        if (Objects.nonNull(bdcSlSqrSfxxDTO) && StringUtils.isNotBlank(bdcSlSqrSfxxDTO.getSfxxid())) {
            List<BdcSlSfxmDO> bdcSlSfxmDOList = bdcSlSfxmFeignService.listBdcSlSfxmBySfxxid(bdcSlSqrSfxxDTO.getSfxxid());
            if (CollectionUtils.isNotEmpty(bdcSlSfxmDOList)) {
                //判断是否有土地收费项目
                bdcSlSfxmDOList = bdcSlSfxmDOList.stream().filter(bdcSlSfxmDO -> StringUtils.equals(bdcSlSfxmDO.getSfxmdm(), tdsyqsfxmdm)).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(bdcSlSfxmDOList)) {
                    bdcSlSqrSfxxDTO.setXcsf(true);
                }
            }
        }
    }

    /**
     * 调用互联网缴费状态查询信息，并更新缴费状态、完税状态
     *
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @GetMapping("/hlw/jfzt/{gzlslid}")
    public void hlwJfztCx(@PathVariable(value = "gzlslid") String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException(ErrorCode.MISSING_ARG, "缺失参数工作流实例ID。");
        }
        this.bdcSlSfxxFeignService.queryHlwJfzt(gzlslid);
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取税费信息(南通)
     */
    @ResponseBody
    @GetMapping("/sfxx/nantong")
    public Object queryBdcSlSwxxNt(String gzlslid, String xmid) {

        if (StringUtils.isBlank(xmid)) {
            BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxFeignService.queryBdcSlJbxxByGzlslid(gzlslid);
            if (bdcSlJbxxDO != null) {
                List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmFeignService.listBdcSlXmByJbxxid(bdcSlJbxxDO.getJbxxid());
                if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                    xmid = bdcSlXmDOList.get(0).getXmid();
                }
            }
        }
        if (StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException("xmid");
        }
        BdcSfDTO bdcSfDTO = bdcYcslFeignService.queryYcslSwxx(xmid);
        return bdcSfDTO;
    }
}
