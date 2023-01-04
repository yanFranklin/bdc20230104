package cn.gtmap.realestate.building.ui.web;

import cn.gtmap.realestate.building.ui.config.LpbCololrConfig;
import cn.gtmap.realestate.building.ui.config.LpbViewSortConfig;
import cn.gtmap.realestate.building.ui.core.vo.LpbChVO;
import cn.gtmap.realestate.building.ui.core.vo.LpbCkVo;
import cn.gtmap.realestate.building.ui.core.vo.LpbDyVO;
import cn.gtmap.realestate.building.ui.core.vo.LpbViewVO;
import cn.gtmap.realestate.building.ui.util.Constants;
import cn.gtmap.realestate.building.ui.util.FwhsResComparator;
import cn.gtmap.realestate.building.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;
import cn.gtmap.realestate.common.core.domain.building.FwYchsDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcCshSlxmDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlCshFwkgDataDTO;
import cn.gtmap.realestate.common.core.dto.building.HfJyztResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.ResourceDTO;
import cn.gtmap.realestate.common.core.dto.building.ZdTreeResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.ZdTreeZrzResponseDTO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzZhgzTsxxDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmAndFbDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzYzQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQjgldmQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcGzyzFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.*;
import cn.gtmap.realestate.common.core.service.feign.engine.BdcGzZhGzFeignService;
import cn.gtmap.realestate.common.core.service.feign.etl.HtbaQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmfbFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcRedisFeignService;
import cn.gtmap.realestate.common.core.vo.etl.HtbaMsrVO;
import cn.gtmap.realestate.common.util.*;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Preconditions;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-12-26
 * @description 楼盘表展现页面
 */
@Controller
@RequestMapping("/lpb")
public class LpbViewController extends BaseController {

    public static final int PAGESIZE = 100;
    @Autowired
    private LpbFeignService lpbFeignService;

    @Autowired
    private ZdFeignService zdFeignService;

    @Autowired
    private FwLjzFeginService fwLjzFeginService;
    @Autowired
    private FwHsFeignService fwHsFeginService;
    @Autowired
    private FwYcHsFeignService fwYcHsFeginService;

    @Autowired
    private BdcdyFeignService bdcdyFeignService;

    @Autowired
    private BdcRedisFeignService bdcRedisFeignService;

    @Autowired
    private BdcXmFeignService bdcXmFeignService;

    @Autowired
    private BdcGzyzFeignService bdcGzyzFeignService;
    @Autowired
    private HfJyztController hfJyztController;

    @Autowired
    private BdcSlXmFeignService bdcSlXmFeignService;
    @Autowired
    HtbaQlrFeignService htbaQlrFeignService;
    @Autowired
    LpbViewSortConfig lpbViewSortConfig;

    @Autowired
    LpbCololrConfig lpbCololrConfig;
    @Autowired
    ZdJsydLhxxFeignService zdJsydLhxxFeignService;
    @Autowired
    BdcGzZhGzFeignService bdcGzZhGzFeignService;
    @Autowired
    UserManagerUtils userManagerUtils;
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    BdcXmfbFeignService bdcXmfbFeignService;

    @Value("${lpb.view.version:}")
    private String lpbViewVersion;
    @Value("${lpb.view.width:110}")
    private Integer lpbviewWidth;
    @Value("${lpb.view.cxqlr:}")
    private Integer cxqlr;
    @Value("${lpb.view.showtitle:}")
    private Integer showTitle;
    @Value("${lpb.view.cxmsr:}")
    private Integer cxmsr;
    @Value("${lpb.view.cxmsr.bazt:}")
    private String cxmsrbazt;
    @Value("${lpb.view.showljzxx:}")
    private Integer showljzxx;
    @Value("${lpb.view.ljzxxdefaultshow:}")
    private Integer defaultShowLjzxx;
    @Value("#{'${showAddButton.gzldyid:}'.split(',')}")
    private List<String> showAddBtnDyidList;
    @Value("${lpb.view.showjyzt:1}")
    private Integer showjyzt;
    @Value("${lpb.view.chscount:8}")
    private Integer chsCount;
    @Value("${lpb.btn:}")
    private String btn;

    /*
     * 规则验证是否忽略相同子规则
     * */
    @Value("${gzyz.hlxtzgz:false}")
    private boolean hlxtzgz;
    //颜色是作为色块还是底色 1是底色
    @Value("${lpb.view.style:0}")
    private Integer style;
    @Value("${lpb.view.ftlPath:newlpb/lpbView}")
    private String ftlPath;
    @Value("${lpb.view.colorConfigPath:lpb/lpbColorConfig}")
    private String colorConfigPath;
    //三维户室图url
    @Value("${swhst.url:}")
    private String swhstUrl;
    @Value("#{'${swhst.html.code:}'.split(',')}")
    private List<String> swhstHtmlCode;

    /**
     * 车库最大长度
     */
    private static final int CK_MAX_NUM = 10;

    private static final String LPB_CODE_ACCEPT = "accept";

    private static final String QLZT_CONSTANT = "权利状态";

    private static final String JYZT_CONSTANT = "交易状态";

    private static final String FZZT_CONSTANT = "发证状态";

    private static final String BAZT_CONSTANT = "bazt";
    private static final String MORE_CONSTANT = "更多";

    // 省略坐落
    private static final String ELLIPSIS_ZL = "zl";

    // 保留长度
    private static final int ELLIPSIS_ZL_LEAVELEN = 7;

    private static final int P_ZT_HEIGHT = 21;

    private static final int P_DESC_HEIGHT = 24;

    private static final int P_FJH_HEIGHT = 24;

    /**
     * code  与 换行配置键值对(主要用于车库类型)
     */
    private static Map<String, Map<String, String>> CODE_TURNLINE_PARAM = new HashMap<>();


    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    /**
     * @param model
     * @param code
     * @param fwDcbIndex
     * @param paramJson  外围参数 JSON结构字符串 需要 encode
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 初始化页面
     */
    @RequestMapping("/view")
    public String init(Model model, String code, String djh,
                       String fwDcbIndex, String paramJson, String bdcdyh, String gzlslid, String processInsId, String qjgldm, String zlcsh) {
        if (StringUtils.isBlank(code)) {
            code = "default";
        }
        if (StringUtils.isBlank(gzlslid) && StringUtils.isNotBlank(processInsId)) {
            gzlslid = processInsId;
        }
        FwLjzDO fwLjzDO;
        // 有gzlslid没有别的的话 处理下
        if (StringUtils.isNotBlank(gzlslid)) {
            HashSet<String> bdcdyhHashSet = listBdcdyhByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcdyhHashSet)) {
                bdcdyh = bdcdyhHashSet.iterator().next();
            }
            //查询权籍管理代码
            if(StringUtils.isBlank(qjgldm)){
                BdcQjgldmQO bdcQjgldmQO =new BdcQjgldmQO();
                bdcQjgldmQO.setGzlslid(gzlslid);
                qjgldm =bdcXmfbFeignService.queryQjgldm(bdcQjgldmQO);
            }
        }
        // 支持 传递 FWHS 的 BDCDYH 查询
        if (StringUtils.isBlank(fwDcbIndex) && StringUtils.isNotBlank(bdcdyh)) {
            FwHsDO fwHsDO = bdcdyFeignService.queryHsByBdcdyh(bdcdyh, "",qjgldm);
            if (fwHsDO != null) {
                fwDcbIndex = fwHsDO.getFwDcbIndex();
            }
        } else if (StringUtils.isNotBlank(djh) && StringUtils.isBlank(fwDcbIndex)) {
            // 只传递 DJH 场景 查询
            fwLjzDO = getFirstFwDcbIndex(djh,qjgldm);
            if (fwLjzDO != null) {
                fwDcbIndex = fwLjzDO.getFwDcbIndex();
                model.addAttribute("bdcdyfwlx", fwLjzDO.getBdcdyfwlx());
            }
        } else {
            fwLjzDO = fwLjzFeginService.queryLjzByFwDcbIndex(fwDcbIndex,qjgldm);
            if (fwLjzDO != null) {
                model.addAttribute("bdcdyfwlx", fwLjzDO.getBdcdyfwlx());
            }
        }

        model.addAttribute("bdcdyh", bdcdyh);
        model.addAttribute("gzlslid", gzlslid);
        if (StringUtils.isNotBlank(fwDcbIndex)) {
            if (StringUtils.isNotBlank(paramJson)) {
                paramJson = paramJson.replace("\"", "'");
            }
            model.addAttribute("param", paramJson);
            model.addAttribute("code", code);
            model.addAttribute("fwDcbIndex", fwDcbIndex);
        } else {
            model.addAttribute("param", "");
            model.addAttribute("code", "");
            model.addAttribute("fwDcbIndex", "");
            //throw new EntityNotFoundException("找不到幢信息");
        }
        model.addAttribute("version", lpbViewVersion);
        //页面需要展现添加按钮的定义id集合
        model.addAttribute("showBtnDyidList", StringUtils.join(showAddBtnDyidList, CommonConstantUtils.ZF_YW_DH));
        model.addAttribute("lpbtdwidth", lpbviewWidth);
        model.addAttribute("showljzxx", showljzxx);
        model.addAttribute("defaultShow", defaultShowLjzxx);
        model.addAttribute("showjyzt", showjyzt);
        model.addAttribute("swhsturl", swhstUrl);
        String hssl = redisUtils.getStringValue(userManagerUtils.getCurrentUserName() + "_hssl_redis");
        if (StringUtils.isNotBlank(hssl) && NumberUtils.isNumber(hssl)) {
            chsCount = Integer.decode(hssl);
        }
        model.addAttribute("chscount", chsCount);
        model.addAttribute("hlxtzgz", hlxtzgz);
        model.addAttribute("qjgldm", qjgldm);
        model.addAttribute("zlcsh", StringUtils.isNotBlank(zlcsh) ? zlcsh : "");
        return ftlPath;
    }

    /**
     * @param djh
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据DJH查询 第一个逻辑幢主键
     */
    private FwLjzDO getFirstFwDcbIndex(String djh,String qjgldm) {
        ZdTreeResponseDTO tree = zdFeignService.initZdTreeByDjhAndAllZrzh(djh,qjgldm);
        if (tree != null && CollectionUtils.isNotEmpty(tree.getZrzList())) {
            for (ZdTreeZrzResponseDTO zrzTree : tree.getZrzList()) {
                if (CollectionUtils.isNotEmpty(zrzTree.getFwLjzDOList())) {
                    return zrzTree.getFwLjzDOList().get(0);
                }
            }
        }
        return null;
    }

    /**
     * @param fwDcbIndex
     * @return cn.gtmap.realestate.common.core.dto.building.ZdTreeResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 初始化宗地目录树
     */
    @RequestMapping("/initzdtree")
    @ResponseBody
    public ZdTreeResponseDTO initZdTreeByFwDcbIndex(@NotBlank(message = "逻辑幢主键不能为空") String fwDcbIndex) {
        return zdFeignService.initZdTreeByFwDcbIndex(fwDcbIndex,"");
    }

    /**
     * @param fwDcbIndex
     * @return cn.gtmap.realestate.common.core.dto.building.ZdTreeResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 初始化宗地目录树
     */
    @RequestMapping("/initzdtree/allzrz")
    @ResponseBody
    public ZdTreeResponseDTO initZdTreeByFwDcbIndexAndAllZrzh(@NotBlank(message = "逻辑幢主键不能为空") String fwDcbIndex) {
        return zdFeignService.initZdTreeByFwDcbIndexAndAllZrzh(fwDcbIndex,"");
    }

    /**
     * @param djh
     * @param zrzh
     * @return cn.gtmap.realestate.common.core.dto.building.ZdTreeResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 列出宗地和自然幢下的逻辑幢
     */
    @RequestMapping("/listljz")
    @ResponseBody
    public List<FwLjzDO> listLjzByDjhAndZrzh(@NotBlank(message = "地籍号不能为空") String djh,
                                             @NotBlank(message = "自然幢号不能为空") String zrzh,String qjgldm) {
        return fwLjzFeginService.listLjzByDjhAndZrzh(djh, zrzh,qjgldm);
    }

    /**
     * @param
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 初始化宗地TAB页面
     */
    @RequestMapping("/initzdtab")
    public String zdTab() {
        return "lpb/lpbZdTab";
    }

    /**
     * @param
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 初始化自然幢TAB页面
     */
    @RequestMapping("/initzrztab")
    public String zrzTab() {
        return "lpb/lpbZrzTab";
    }

    /**
     * @param
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 初始化逻辑幢TAB页面
     */
    @RequestMapping("/initljztab")
    public String ljzTab() {
        return "lpb/lpbLjzTab";
    }

    /**
     * @param
     * @return java.lang.String
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 楼盘表状态颜色配置界面
     */
    @RequestMapping("/colorPz")
    public String colorPz() {
        return colorConfigPath;
    }


    /**
     * @return Map<String, Object>
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @RequestMapping("/list/colorPz")
    @ResponseBody
    public Map<String, Object> getLpbColorPz() {
        return lpbFeignService.getLpbColorPz();
    }

    /**
     * @param code
     * @param fwDcbIndex
     * @return cn.gtmap.realestate.common.core.dto.building.ResourceDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description
     */
    @RequestMapping("/lpbres")
    @ResponseBody
    public ResourceDTO getLpbResource(@NotBlank(message = "配置标识不能为空") String code,
                                      @NotBlank(message = "逻辑幢主键不能为空") String fwDcbIndex, String gzlslid,String qjgldm) {
        ResourceDTO resourceDTO = lpbFeignService.getLpbConfigInfoResource(fwDcbIndex, code,qjgldm);
        //如果展示里面有发证面积，那么就统一查询一下发证书面积，发证明面积，只登簿不发证面积，这里的发证面积字段
        Map<String, Double> bdcdyhMjMap = new HashMap<>();
        if (StringUtils.isNotBlank(gzlslid) && MapUtils.isNotEmpty(MapUtils.getMap(resourceDTO.getInfo(), "FZMJ"))) {
            bdcdyhMjMap = getScBdcdhMjMap(fwDcbIndex,qjgldm);
        } else if (StringUtils.isNotBlank(gzlslid) && MapUtils.isNotEmpty(MapUtils.getMap(resourceDTO.getInfo(), "YCFZMJ"))) {
            bdcdyhMjMap = getYcBdcdhMjMap(fwDcbIndex,qjgldm);
        }
        if (MapUtils.isNotEmpty(bdcdyhMjMap)) {
            fillFzmj(resourceDTO, bdcdyhMjMap, gzlslid, "");
        }
        // 添加逻辑幢状态展示
        this.addLjzzt(fwDcbIndex, resourceDTO);

        if (CollectionUtils.isNotEmpty(lpbCololrConfig.getColorConfigStatus())) {
            resourceDTO.getInfo().put("colorConfig", lpbCololrConfig.getColorConfigStatus());
        }
        if (MapUtils.isNotEmpty(lpbCololrConfig.getHtmlState())) {
            resourceDTO.getInfo().put("htmlState", lpbCololrConfig.getHtmlState());
        }
        if (StringUtils.isNotBlank(btn)){
            HashMap map = JSONObject.parseObject(btn,HashMap.class);
            resourceDTO.getInfo().put("btnConfig", map);
        }
        return resourceDTO;
    }
    /**
     * 添加逻辑幢状态展示： 1、量化抵押状态 2、新项目状态
     */
    private void addLjzzt(String fwDcbIndex, ResourceDTO resourceDTO){
        if (CommonConstantUtils.SYSTEM_VERSION_YC.equals(lpbViewVersion)) {
            // 添加逻辑幢量化抵押状态
            this.addLhDyqlzt(fwDcbIndex, resourceDTO);
            // 添加新项目状态
            this.addNewXmzt(resourceDTO);
        }
    }
    /**
     * 查询逻辑幢是否新项目状态
     */
    private void addNewXmzt(ResourceDTO resourceDTO){
        Map lsztMap = MapUtils.getMap(resourceDTO.getInfo(), "lszd");
        if(Objects.nonNull(lsztMap) && Objects.nonNull(MapUtils.getString(lsztMap, "value"))){
            String lszd = MapUtils.getString(lsztMap, "value");
            int sfxxmzt = 0;
            try{
                BdcGzYzQO bdcGzYzQO = new BdcGzYzQO();
                bdcGzYzQO.setZhbs("YC_LPB_XXM");
                Map<String, Object> paramMap = new HashMap<>(5);
                paramMap.put("djh", lszd);
                bdcGzYzQO.setParamMap(paramMap);
                BdcGzZhgzTsxxDTO bdcGzZhgzTsxxDTO = bdcGzZhGzFeignService.getZhgzYzTsxx(bdcGzYzQO);
                if(Objects.nonNull(bdcGzZhgzTsxxDTO)){
                    sfxxmzt = 1;
                }
            }catch(Exception e){
                LOGGER.error("调用规则验证是否新项目失败。", e);
            }
            LinkedHashMap sfxxmztMap = new LinkedHashMap();
            sfxxmztMap.put("value", sfxxmzt);
            sfxxmztMap.put("desc", "是否新项目状态");
            resourceDTO.getInfo().put("sfxxmzt", sfxxmztMap);
        }
    }
    /**
     * 查询逻辑幢量化抵押权利状态
     */
    private void addLhDyqlzt(String fwDcbIndex, ResourceDTO resourceDTO){
        Map lsztMap = MapUtils.getMap(resourceDTO.getInfo(), "lszd");
        if(Objects.nonNull(lsztMap) && Objects.nonNull(MapUtils.getString(lsztMap, "value"))){
            String lszd = MapUtils.getString(lsztMap, "value");
            Integer lhdyqlzt = zdJsydLhxxFeignService.queryLhdyqlZtByFwDcbIndexAndDjh(fwDcbIndex, lszd);
            LinkedHashMap lhdyqlztMap = new LinkedHashMap();
            lhdyqlztMap.put("value", lhdyqlzt);
            lhdyqlztMap.put("desc", "量化抵押权利状态");
            resourceDTO.getInfo().put("lhdyqlzt", lhdyqlztMap);
        }

    }

    private void fillFzmj(ResourceDTO resourceDTO, Map<String, Double> bdcdyhMjMap, String gzlslid, String paramJson) {
        List<BdcSlCshFwkgDataDTO> bdcSlCshFwkgDataDTOList = getFzztDataList(gzlslid, paramJson);
        if (CollectionUtils.isNotEmpty(bdcSlCshFwkgDataDTOList)) {
            Double fzmj = new Double(0);
            Double bfzmj = new Double(0);
            for (BdcSlCshFwkgDataDTO bdcSlCshFwkgDataDTO : bdcSlCshFwkgDataDTOList) {
                if (CommonConstantUtils.SF_F_DM.equals(bdcSlCshFwkgDataDTO.getSfsczs())) {
                    bfzmj += bdcdyhMjMap.getOrDefault(bdcSlCshFwkgDataDTO.getBdcdyh(), new Double(0));
                } else {
                    fzmj += bdcdyhMjMap.getOrDefault(bdcSlCshFwkgDataDTO.getBdcdyh(), new Double(0));
                }
            }
            putMjIntoInfo(resourceDTO, NumberUtil.formatDigit(fzmj, 2), "发证面积", "fzmj");
            putMjIntoInfo(resourceDTO, NumberUtil.formatDigit(bfzmj, 2), "只登簿不发证面积", "bfzmj");
            resourceDTO.getInfo().remove("FZMJ");
            resourceDTO.getInfo().remove("YCFZMJ");
        }
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return 发证状态信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取发证状态
     */
    private List<BdcSlCshFwkgDataDTO> getFzztDataList(String gzlslid, String paramJson) {
        List<BdcSlCshFwkgDataDTO> bdcSlCshFwkgDataDTOList = new ArrayList<>();
        if (StringUtils.isNotBlank(gzlslid)) {
            bdcSlCshFwkgDataDTOList = bdcXmFeignService.queryFwkgslByGzlslid(gzlslid, "");
        } else if (StringUtils.isNotBlank(paramJson)) {
            BdcCshSlxmDTO dto;
            try {
                dto = JSONObject.parseObject(paramJson, BdcCshSlxmDTO.class);
                if (dto != null && StringUtils.isNotBlank(dto.getJbxxid())) {
                    bdcSlCshFwkgDataDTOList = bdcSlXmFeignService.querySlFzztByJbxxid(dto.getJbxxid());


                }
            } catch (Exception e) {
                LOGGER.error("购物车传递参数异常", e);
            }
        }
        return bdcSlCshFwkgDataDTOList;

    }


    private Map<String, Double> getYcBdcdhMjMap(@NotBlank(message = "逻辑幢主键不能为空") String fwDcbIndex,String qjgldm) {
        Map<String, Double> bdcdyhMjMap = new HashMap<>();
        List<FwYchsDO> fwHsDOList = fwYcHsFeginService.listFwYchsByFwDcbIndex(fwDcbIndex,qjgldm);
        if (CollectionUtils.isNotEmpty(fwHsDOList)) {
            for (FwYchsDO fwHsDO : fwHsDOList) {
                if (fwHsDO.getScjzmj() != null) {
                    bdcdyhMjMap.put(fwHsDO.getBdcdyh(), fwHsDO.getScjzmj());
                } else if (fwHsDO.getYcjzmj() != null) {
                    bdcdyhMjMap.put(fwHsDO.getBdcdyh(), fwHsDO.getYcjzmj());
                } else {
                    bdcdyhMjMap.put(fwHsDO.getBdcdyh(), new Double(0));
                }
            }
        }
        return bdcdyhMjMap;
    }

    private Map<String, Double> getScBdcdhMjMap(@NotBlank(message = "逻辑幢主键不能为空") String fwDcbIndex,String qjgldm) {
        Map<String, Double> bdcdyhMjMap = new HashMap<>();
        List<FwHsDO> fwHsDOList = fwHsFeginService.listFwhsByFwDcbIndex(fwDcbIndex,qjgldm);
        if (CollectionUtils.isNotEmpty(fwHsDOList)) {
            for (FwHsDO fwHsDO : fwHsDOList) {
                if (fwHsDO.getScjzmj() != null) {
                    bdcdyhMjMap.put(fwHsDO.getBdcdyh(), fwHsDO.getScjzmj());
                } else if (fwHsDO.getYcjzmj() != null) {
                    bdcdyhMjMap.put(fwHsDO.getBdcdyh(), fwHsDO.getYcjzmj());
                } else {
                    bdcdyhMjMap.put(fwHsDO.getBdcdyh(), new Double(0));
                }
            }
        }
        return bdcdyhMjMap;
    }

    private void putMjIntoInfo(ResourceDTO resourceDTO, Double mj, String desc, String key) {
        LinkedHashMap fzsmjMap = new LinkedHashMap();
        fzsmjMap.put("desc", desc);
        fzsmjMap.put("tabType", "hs,ychs");
        fzsmjMap.put("type", "JOINCOLUMN");
        fzsmjMap.put("value", mj);
        resourceDTO.getInfo().put(key, fzsmjMap);
    }

    /**
     * @param code
     * @param fwDcbIndex
     * @return cn.gtmap.realestate.building.ui.core.vo.LpbViewVO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 查询预测户室数据列表
     */
    @RequestMapping("/ychslist")
    @ResponseBody
    public LpbViewVO initYchsList(@NotBlank(message = "配置标识不能为空") String code,
                                  @NotBlank(message = "逻辑幢主键不能为空") String fwDcbIndex, String gzlslid, String paramJson,String qjgldm) {
        List<ResourceDTO> ychsList;
        ychsList = lpbFeignService.getFwYchsResList(fwDcbIndex, code,qjgldm);
        if (CollectionUtils.isNotEmpty(ychsList)) {
            LpbViewVO lpbViewVO = new LpbViewVO();
            ResourceDTO resourceDTO = lpbFeignService.getLpbConfigInfoResource(fwDcbIndex, code,qjgldm);
            convertFwhsData(gzlslid, code, lpbViewVO, ychsList, resourceDTO, "ychs", paramJson,qjgldm);
            Map infoMap = resourceDTO.getInfo();
            Map<String, Object> dxcsMap = MapUtils.getMap(infoMap, "dxcs");
            String dxcs = MapUtils.getString(dxcsMap, "value");
            String hssl = redisUtils.getStringValue(userManagerUtils.getCurrentUserName() + "_hssl_redis");
            if (StringUtils.isNotBlank(hssl) && NumberUtils.isNumber(hssl)) {
                chsCount = Integer.decode(hssl);
            }
            sortData(lpbViewVO, dxcs);
            return lpbViewVO;
        }
        return null;
    }

    /**
     * @param code
     * @param fwDcbIndex
     * @return cn.gtmap.realestate.building.ui.core.vo.LpbViewVO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 查询户室数据列表
     */
    @RequestMapping("/fwhslist")
    @ResponseBody
    public LpbViewVO initFwhsList(@NotBlank(message = "配置标识不能为空") String code,
                                  @NotBlank(message = "逻辑幢主键不能为空") String fwDcbIndex,
                                  String gzlslid, String paramJson,String qjgldm) throws InterruptedException {
        List<ResourceDTO> fwhsList;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS");
        LOGGER.info("开始查询户室信息，当前时间{}", simpleDateFormat.format(new Date()));
        fwhsList = lpbFeignService.getFwHsResList(fwDcbIndex, code,qjgldm);
        LOGGER.info("查询户室信息结束，时间为{}", simpleDateFormat.format(new Date()));
        if (CollectionUtils.isNotEmpty(fwhsList)) {
            LpbViewVO lpbViewVO = new LpbViewVO();
            ResourceDTO resourceDTO = lpbFeignService.getLpbConfigInfoResource(fwDcbIndex, code,qjgldm);
            LOGGER.info("开始封装户室信息，当前时间{}", simpleDateFormat.format(new Date()));
            convertFwhsData(gzlslid, code, lpbViewVO, fwhsList, resourceDTO, "hs", paramJson,qjgldm);
            LOGGER.info("封装户室信息结束，开始排序，当前时间{}", simpleDateFormat.format(new Date()));
            Map infoMap = resourceDTO.getInfo();
            Map<String, Object> dxcsMap = MapUtils.getMap(infoMap, "dxcs");
            String dxcs = MapUtils.getString(dxcsMap, "value");
            String hssl = redisUtils.getStringValue(userManagerUtils.getCurrentUserName() + "_hssl_redis");
            if (StringUtils.isNotBlank(hssl) && NumberUtils.isNumber(hssl)) {
                chsCount = Integer.decode(hssl);
            }
            sortData(lpbViewVO, dxcs);
            LOGGER.info("排序结束{}", simpleDateFormat.format(new Date()));
            return lpbViewVO;
        }
        return null;
    }

    /**
     * @param colorPzList
     * @description 存储楼盘表状态的颜色配置
     */
    @PostMapping("/update/status/color")
    @ResponseBody
    public void importColorPz(@RequestBody List<Map<String, Object>> colorPzList) {
        bdcRedisFeignService.addStringValue("lpbColor", JSONObject.toJSONString(colorPzList));
        //重置缓存
        lpbFeignService.refreshLpbConfig(null);
    }

    /**
     * @param lpbViewVO
     * @param dxcs      是否存在地下层数，用于扩展没有单元 的层数据
     * @return cn.gtmap.realestate.building.ui.core.vo.LpbViewVO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 排序
     */
    private LpbViewVO sortData(LpbViewVO lpbViewVO, String dxcs) {
        if (CollectionUtils.isNotEmpty(lpbViewVO.getcFwList())) {
            lpbViewVO.setChsCount(chsCount);
            for (LpbChVO lpbChVO : lpbViewVO.getcFwList()) {
                if (CollectionUtils.isNotEmpty(lpbChVO.getDyFwList())) {
                    // 对单元排序
                    Collections.sort(lpbChVO.getDyFwList());
                    for (LpbDyVO dyVO : lpbChVO.getDyFwList()) {
                        if (CollectionUtils.isNotEmpty(dyVO.getFwhsResourceDTOList())) {
                            // 对户室排序
                            dyVO.getFwhsResourceDTOList().sort(new FwhsResComparator(lpbViewSortConfig.getSortmap()));
                        }
                        //户室排序后根据配置的每行户室个数分组
                        if (Objects.nonNull(chsCount) && chsCount > 0) {
                            List<List> res = ListUtils.subList(dyVO.getFwhsResourceDTOList(), chsCount);
                            dyVO.setGroupfwhsResourceDTOList(res);
                        }
                    }
                }
            }
            // 单元排序
            sortDyh(lpbViewVO);
            // 层排序
            Collections.sort(lpbViewVO.getcFwList());

            // 填充 空层数据
            if (CollectionUtils.isNotEmpty(lpbViewVO.getcFwList())) {
                String maxWlcs = lpbViewVO.getcFwList().get(0).getWlcs();
                if (lpbViewVO.getFwcs() != null) {
                    maxWlcs = lpbViewVO.getFwcs().toString();
                }
                if (maxWlcs != null) {
                    // 所有没有数据的物理层
                    String lastDycs = "";
                    wlcsnum:
                    for (int i = 1; i <= Integer.parseInt(maxWlcs); i++) {
                        // 循环每层
                        boolean hasData = false;
                        clist:
                        for (LpbChVO lpbChVO : lpbViewVO.getcFwList()) {
                            // 如果有相等的层 则 循环下一物理层号
                            if (StringUtils.equals(lpbChVO.getWlcs(), i + "")) {
                                hasData = true;
                                lastDycs = lpbChVO.getDycs();
                                break clist;
                            }
                        }
                        if (!hasData) {
                            LpbChVO lpbChVO;
                            Integer cs = 0;
                            if (NumberUtils.isNumber(dxcs)) {
                                cs = NumberUtils.toInt(dxcs);
                            }
                            //处理逻辑：lpbViewVO.getcFwList已经对层数据进行过排序处理，如果存在地下层数，地上物理层数就不会从1开始递增
                            //i从1 开始，如果不存在数据，则说明是地下层的数据为空
                            if (i <= cs) {
                                lpbChVO = getEmptyCFw(i, lastDycs, lpbViewVO, cs);
                            } else {
                                lpbChVO = getEmptyCFw(i, lastDycs, lpbViewVO, null);
                            }
                            lastDycs = lpbChVO.getDycs();
                            lpbViewVO.getcFwList().add(lpbChVO);
                        }
                    }
                }
            }
            // 层重新排序
            Collections.sort(lpbViewVO.getcFwList());
        }
        return lpbViewVO;
    }

    private LpbChVO getEmptyCFw(Integer wlcs, String dycs, LpbViewVO lpbViewVO, Integer cs) {
        LpbChVO lpbChVO = new LpbChVO();
        lpbChVO.setWlcs(wlcs + "");
        // 定义层数用下层的定义层数 做递增
        if (Objects.nonNull(cs)) {
            lpbChVO.setDycs(getEmptyDycs(wlcs, dycs, cs));
        } else {
            lpbChVO.setDycs(getEmptyDycs(wlcs, dycs, null));
        }
        List<LpbDyVO> lpbDyVOS = new ArrayList<>();
        List<Map<String, Object>> allDyinfo = lpbViewVO.getDyList();
        for (Map<String, Object> dyMap : allDyinfo) {
            if (MapUtils.getInteger(dyMap, "colspan") != null) {
                LpbDyVO lpbDyVO = new LpbDyVO();
                lpbDyVO.setDyh(MapUtils.getString(dyMap, "dyh"));
                lpbDyVO.setMaxHs(MapUtils.getInteger(dyMap, "colspan"));
                List<ResourceDTO> resourceDTOS = new ArrayList<>();
                resourceDTOS.add(new ResourceDTO());
                lpbDyVO.setFwhsResourceDTOList(resourceDTOS);
                lpbDyVOS.add(lpbDyVO);
            }
        }
        lpbChVO.setDyFwList(lpbDyVOS);
        return lpbChVO;
    }

    /**
     * @param
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取空层的 定义层数
     */
    private static String getEmptyDycs(Integer wlcs, String lastDycs, Integer dxcs) {
        if (StringUtils.isNotBlank(lastDycs) && NumberUtils.isNumber(lastDycs)) {
            if (Objects.nonNull(dxcs)) {
                int lastDycsInt = NumberUtils.toInt(lastDycs);
                int thisDycs = lastDycsInt + 1;
                if (thisDycs == 0) {
                    thisDycs = -1;
                }
                return thisDycs + "";
            } else {
                int lastDycsInt = NumberUtils.toInt(lastDycs);
                int thisDycs = lastDycsInt + 1;
                if (thisDycs == 0) {
                    thisDycs = 1;
                }
                return thisDycs + "";
            }
        }
        if (Objects.nonNull(dxcs)) {
            return -dxcs + "";
        }
        return wlcs + "";
    }

    private static void sortDyh(LpbViewVO lpbViewVO) {
        Collections.sort(lpbViewVO.getDyList(), new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                String o1Dyh = MapUtils.getString(o1, "dyh");
                String o2Dyh = MapUtils.getString(o2, "dyh");
                if (NumberUtils.isNumber(o1Dyh) && NumberUtils.isNumber(o2Dyh)) {
                    if (Integer.parseInt(o1Dyh) > Integer.parseInt(o2Dyh)) {
                        return 1;
                    } else if (Integer.parseInt(o1Dyh) < Integer.parseInt(o2Dyh)) {
                        return -1;
                    } else {
                        return 0;
                    }
                } else {
                    o1Dyh = StringUtils.isNotBlank(o1Dyh) ? o1Dyh : "";
                    o2Dyh = StringUtils.isNotBlank(o2Dyh) ? o2Dyh : "";
                    return o1Dyh.compareTo(o2Dyh);
                }
            }
        });
    }

    /**
     * @param gzlslid
     * @return java.util.HashSet<java.lang.String>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据工作流实例ID获取当前项目上所有的BDCDYH
     */
    private HashSet<String> listBdcdyhByGzlslid(String gzlslid) {
        HashSet<String> bdcdyhSet = new HashSet<>();
        if (StringUtils.isBlank(gzlslid)) {
            return bdcdyhSet;
        }
        List<String> bdcdyhList = new ArrayList<>();
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> list = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(list)) {
            for (BdcXmDO bdcXmDO : list) {
                if (bdcXmDO != null && StringUtils.isNotBlank(bdcXmDO.getBdcdyh())) {
                    bdcdyhList.add(bdcXmDO.getBdcdyh());
                }
            }
        }
        if (CollectionUtils.isNotEmpty(bdcdyhList)) {
            bdcdyhSet.addAll(bdcdyhList);
        }
        return bdcdyhSet;
    }

    /**
     * @param resourceDTO
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 向户室保存选中标志
     */
    private void setBdcdyhCheckedFlag(ResourceDTO resourceDTO) {
        HashMap checkedFlagMap = new HashMap();
        checkedFlagMap.put("value", "1");
        Map infoMap = resourceDTO.getInfo();
        infoMap.put("checkedflag", checkedFlagMap);
    }


    /**
     * @param lpbConfig
     * @param tabType
     * @return java.util.List<java.lang.String>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取默认显示的属性
     */
    private List<String> getDefaultShowColumn(LpbViewVO lpbViewVO, ResourceDTO lpbConfig, String tabType) {
        LinkedHashMap<String, Object> info = lpbConfig.getInfo();
        List<String> defaultShows = new ArrayList<>();
        Iterator<Map.Entry<String, Object>> iterator = info.entrySet().iterator();
        int cellHeight = P_FJH_HEIGHT;
        lpbViewVO.setCellHeight(P_FJH_HEIGHT);
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            Map tempMap = (Map) entry.getValue();
            if (BooleanUtils.toBoolean(MapUtils.getBooleanValue(tempMap, "defaultShow"))
                    && Arrays.asList(MapUtils.getString(tempMap, "tabType", ",").split(",")).contains(tabType)) {
                // 状态类型特殊处理
                if (StringUtils.equals(MapUtils.getString(tempMap, "desc"), QLZT_CONSTANT)
                        || StringUtils.equals(MapUtils.getString(tempMap, "desc"), JYZT_CONSTANT)
                        || StringUtils.equals(MapUtils.getString(tempMap, "desc"), FZZT_CONSTANT)) {
                    cellHeight += P_ZT_HEIGHT;
                    defaultShows.add(MapUtils.getString(tempMap, "desc"));
                } else {
                    cellHeight += P_DESC_HEIGHT;
                    defaultShows.add(MapUtils.getString(tempMap, "refInfo"));
                }
            }
        }
        lpbViewVO.setCellHeight(cellHeight + 9);
        lpbViewVO.setDefaultShows(defaultShows);
        return defaultShows;
    }


    /**
     * @param lpbConfig
     * @param tabType
     * @return java.util.List<java.lang.String>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取配置显示的全部色块
     */
    private List<String> getAllZtState(ResourceDTO lpbConfig, String tabType) {
        LinkedHashMap<String, Object> info = lpbConfig.getInfo();
        List<String> ztList = new ArrayList<>();
        Map jyztInfoMap = (Map) info.get("jyztState");
        if (Arrays.asList(MapUtils.getString(jyztInfoMap, "tabType", "").split(",")).contains(tabType)) {
            String[] jyztArr = MapUtils.getString(jyztInfoMap, "constant", "").split(",");
            ztList.addAll(Arrays.asList(jyztArr));
        }

        Map qlztInfoMap = (Map) info.get("qlztState");
        if (Arrays.asList(MapUtils.getString(qlztInfoMap, "tabType", "").split(",")).contains(tabType)) {
            String[] qlztArr = MapUtils.getString(qlztInfoMap, "constant", "").split(",");
            ztList.addAll(Arrays.asList(qlztArr));
        }
        Map fzztInfoMap = (Map) info.get("fzztState");
        if (Arrays.asList(MapUtils.getString(fzztInfoMap, "tabType", "").split(",")).contains(tabType)) {
            String[] fzztArr = MapUtils.getString(fzztInfoMap, "constant", "").split(",");
            ztList.addAll(Arrays.asList(fzztArr));
        }
        Map bzztInfoMap = (Map) info.get("baztState");
        if (Arrays.asList(MapUtils.getString(bzztInfoMap, "tabType", "").split(",")).contains(tabType)) {
            String[] baztArr = MapUtils.getString(bzztInfoMap, "constant", "").split(",");
            ztList.addAll(Arrays.asList(baztArr));
        }
        return ztList;
    }

    /**
     * @param lpbViewVO
     * @param fwhsList
     * @param lpbConfig
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description
     */
    private void convertFwhsData(String gzlslid, String code, LpbViewVO lpbViewVO, List<ResourceDTO> fwhsList, ResourceDTO lpbConfig, String tabType, String paramJson,String qjgldm) {
        // 处理换行配置
        setTurnLineParam(code, lpbConfig);
        HashSet<String> bdcdyhHashSet = listBdcdyhByGzlslid(gzlslid);
        if (CollectionUtils.isNotEmpty(fwhsList)) {
            Set<String> ytSet = new HashSet<>();
            Set<String> jyztSet = new HashSet<>();
            lpbViewVO.setUpDownMergeFwList(new ArrayList<>());
            Map<String, List<ResourceDTO>> listByCsMap = new LinkedHashMap<>();
            List<ResourceDTO> ckList = new ArrayList<>();
            //获取颜色配置
            Map<String, String> mapColor = getStateColorMap(lpbConfig);

            // 获取房屋层数 用于显示单元行
            getLjzFwcs(lpbViewVO, lpbConfig);

            // 获取默认显示列
            List<String> defaultShows = getDefaultShowColumn(lpbViewVO, lpbConfig, tabType);

            // 获取所有的 色块（用于处理部分色块隐藏）
            List<String> allZtState = getAllZtState(lpbConfig, tabType);
            //发证状态要调用受理查询，页面要展示发证状态的时候才调用方法
            List<BdcSlCshFwkgDataDTO> bdcSlCshFwkgDataDTOList = new ArrayList<>();
            if (allZtState.contains(StringUtils.upperCase(CommonConstantUtils.DYLX_ZS))) {
                bdcSlCshFwkgDataDTOList = getFzztDataList(gzlslid, paramJson);
            }
            //合肥的交易状态与其他地区不一样，单独请求下获取到所有的交易状态
            Map<String, HfJyztResponseDTO> hfJyztMap = getHfJyztMap(fwhsList,qjgldm);
            List<String> bdcdyhList = new ArrayList<>(fwhsList.size());
            for (ResourceDTO resourceDTO : fwhsList) {
                Map infoMap = resourceDTO.getInfo();
                if (StringUtils.isNotBlank(MapUtils.getString(MapUtils.getMap(infoMap, "bdcdyh"), "value"))) {
                    bdcdyhList.add(MapUtils.getString(MapUtils.getMap(infoMap, "bdcdyh"), "value"));
                }
                Map<String, Object> wlcsMap = MapUtils.getMap(infoMap, "wlcs");
                String wlcs = MapUtils.getString(wlcsMap, "value");

                if (StringUtils.isNotBlank(wlcs) && NumberUtils.isNumber(wlcs)) {
                    // 判断物理层数 是否 超出层数范围
                    if (lpbViewVO.getFwcs() != null) {
                        Integer maxCs = lpbViewVO.getFwcs();
                        if (maxCs < Integer.parseInt(wlcs)) {
                            Map<String, Object> fjhMap = MapUtils.getMap(infoMap, "fjh");
                            String fjh = MapUtils.getString(fjhMap, "value");
                            throw new AppException("房间号：" + fjh + "物理层数为：" + wlcs + ",不在逻辑幢地上地下层数范围内，请检查");
                        }
                    }
                }
                String hbfx = MapUtils.getString(MapUtils.getMap(infoMap, "hbfx"), "value");
                String bdcdyh = MapUtils.getString(MapUtils.getMap(infoMap, "bdcdyh"), "value");
                String ghyt = MapUtils.getString(MapUtils.getMap(infoMap, "ghyt"), "value");
                if (StringUtils.isNotBlank(ghyt)) {
                    ytSet.add(ghyt);
                }
                if (bdcdyhHashSet.contains(bdcdyh)) {
                    // 如果当前项目中包含BDCDYH 则给他添加选中标志
                    setBdcdyhCheckedFlag(resourceDTO);
                }

                //获取房间号的页面元素,返回字体颜色
                String font = getFjhSpan(mapColor, resourceDTO, code);
                //获取其他属性信息
                getDescSpan(resourceDTO, tabType, defaultShows, font);
                //获取权利的页面元素
                getQlztSpan(mapColor, resourceDTO, defaultShows, allZtState, font, style, code, lpbCololrConfig);
                //获取交易状态的页面元素
                getJyztSpan(mapColor, resourceDTO, defaultShows, allZtState, jyztSet);
                //获取合肥交易状态的页面元素
                getHfJyztSpan(resourceDTO, defaultShows, hfJyztMap, jyztSet);
                //盐城展示备案状态和买受人
                getBaztSpan(mapColor, resourceDTO, defaultShows, allZtState, code, font);
                //更多title悬浮
                getTitleSpan(resourceDTO, code);
                //获取发证状态的页面元素
                getFzztSpan(mapColor, resourceDTO, defaultShows, allZtState, bdcSlCshFwkgDataDTOList, font);
                // 如果是需要换行的类型
                if (checkNeedTurnline(code, infoMap)) {
                    ckList.add(resourceDTO);
                } else {
                    if (MapUtils.getObject(listByCsMap, wlcs) == null) {
                        List<ResourceDTO> list = new ArrayList<>();
                        list.add(resourceDTO);
                        listByCsMap.put(wlcs, list);
                    } else {
                        listByCsMap.get(wlcs).add(resourceDTO);
                    }
                }
                // 合并方向如果是向上或者向下的 先预存一份
                if (StringUtils.equals(hbfx, Constants.FWHS_HBFX_DOWN)
                        || StringUtils.equals(hbfx, Constants.FWHS_HBFX_UP)) {
                    lpbViewVO.getUpDownMergeFwList().add(resourceDTO);
                }
            }

            //获取所有的单元号的现势产权数据
            List<HtbaMsrVO> htbaMsrVOList = new ArrayList<>(bdcdyhList.size());
            List<BdcXmDO> bdcXmDOList = new ArrayList<>(bdcdyhList.size());
            if (Objects.equals(CommonConstantUtils.SF_S_DM, cxqlr)) {
                LOGGER.info("开始查询权利人");
                bdcXmDOList = bdcXmFeignService.listBdcXmXscq(bdcdyhList);
                LOGGER.info("权利人查询结束");
            }
            if (Objects.equals(CommonConstantUtils.SF_S_DM, cxmsr)) {
                LOGGER.info("开始查询买受人");
                htbaMsrVOList = htbaQlrFeignService.listMsr(bdcdyhList,cxmsrbazt);
                LOGGER.info("买受人查询结束");
            }
            if (Objects.equals(CommonConstantUtils.SF_S_DM, cxqlr) || Objects.equals(CommonConstantUtils.SF_S_DM, cxmsr)) {
                getQlr(fwhsList, bdcXmDOList, htbaMsrVOList, defaultShows, mapColor);
            }


            // 向VO 写 过滤键值列表
            Map<String, Set<String>> filterMap = new HashMap<>();
            filterMap.put("ghyt", ytSet);
            filterMap.put("jyzt", jyztSet);
            lpbViewVO.setFilterList(filterMap);

            // 循环 每一层 数据
            if (MapUtils.isNotEmpty(listByCsMap)) {
                Iterator<Map.Entry<String, List<ResourceDTO>>> iterator = listByCsMap.entrySet().iterator();
                Map<String, Map<Integer, Integer>> dyWlcsMaxhsMap = new LinkedHashMap<>();
                while (iterator.hasNext()) {
                    Map.Entry<String, List<ResourceDTO>> entry = iterator.next();
                    LpbChVO lpbChVO = new LpbChVO();
                    // 保存物理层
                    lpbChVO.setWlcs(entry.getKey());
                    List<ResourceDTO> cHsList = entry.getValue();
                    // 将户室List 处理成 单元结构存进层实体
                    setCDyList(lpbChVO, cHsList, dyWlcsMaxhsMap);
                    // 默认定义层显示物理层
                    if (StringUtils.isBlank(lpbChVO.getDycs())) {
                        lpbChVO.setDycs(lpbChVO.getWlcs());
                    }
                    // 向页面层实体的层List中 增加 当前层
                    setCvo(lpbViewVO, lpbChVO);
                }
                Map<String, Integer> dyMaxhsMap = new LinkedHashMap<>();
                for (Map.Entry<String, Map<Integer, Integer>> dyWlcsMaxhs : dyWlcsMaxhsMap.entrySet()) {
                    List<Integer> list = new ArrayList<>();
                    list.addAll(dyWlcsMaxhs.getValue().values());
                    Collections.sort(list, Collections.reverseOrder());
                    dyMaxhsMap.put(dyWlcsMaxhs.getKey(), list.get(0));
                }
                // 将 dyMaxhsMap 转成 List
                if (MapUtils.isNotEmpty(dyMaxhsMap)) {
                    List<Map<String, Object>> dyhList = new ArrayList<>();
                    //获取最大列数,将各个单元最大列数相加
                    int maxHs = 0;
                    for (String key : dyMaxhsMap.keySet()) {
                        Map<String, Object> dyhMap = new HashMap<>();
                        dyhMap.put("dyh", key);
                        dyhMap.put("colspan", dyMaxhsMap.get(key));
                        maxHs += dyMaxhsMap.get(key);
                        dyhList.add(dyhMap);
                    }

                    if (lpbViewVO.getMaxHs() < maxHs) {
                        lpbViewVO.setMaxHs(maxHs);
                    }
                    lpbViewVO.setDyList(dyhList);

                    // 循环放进每一个单元数据
                    for (LpbChVO lpbChVO : lpbViewVO.getcFwList()) {
                        for (LpbDyVO lpbDyVO : lpbChVO.getDyFwList()) {
                            lpbDyVO.setMaxHs(dyMaxhsMap.get(lpbDyVO.getDyh()));
                        }
                    }
                }

            }

            // 如果车库不为空 则处理车库数据
            if (CollectionUtils.isNotEmpty(ckList)) {
                dealCk(lpbViewVO, ckList);
            }

            // 如果单元不存在 补充单元
            if (CollectionUtils.isEmpty(lpbViewVO.getDyList())) {
                List<Map<String, Object>> dyhList = new ArrayList<>();
                Map<String, Object> dyhMap = new HashMap<>();
                dyhMap.put("dyh", "");
                if (lpbViewVO.getMaxHs() > 0) {
                    dyhMap.put("colspan", lpbViewVO.getMaxHs());
                } else if (lpbViewVO.getLpbCkVo() != null && lpbViewVO.getLpbCkVo().getMaxnum() > 0) {
                    dyhMap.put("colspan", lpbViewVO.getLpbCkVo().getMaxnum());
                }
                dyhList.add(dyhMap);
                lpbViewVO.setDyList(dyhList);
            }
        }
    }

    private Map<String, HfJyztResponseDTO> getHfJyztMap(List<ResourceDTO> fwhsList,String qjgldm) {
        Map<String, HfJyztResponseDTO> hfJyztMap = new HashMap<>();
        List<String> ysfwbmList = new ArrayList<>();
        for (ResourceDTO resourceDTO : fwhsList) {
            Map<String, Object> ysfwbmMap = MapUtils.getMap(resourceDTO.getInfo(), "ysfwbm");
            if (!StringUtils.equals(MapUtils.getString(ysfwbmMap, "desc"), "getHfJyzt")) {
                break;
            }
            if (StringUtils.isNotBlank(MapUtils.getString(ysfwbmMap, "value"))) {
                ysfwbmList.add(MapUtils.getString(ysfwbmMap, "value"));
            }
        }
        if (CollectionUtils.isNotEmpty(ysfwbmList)) {
            //总记录数
            Integer totalCount = ysfwbmList.size();
            //分多少次处理
            Integer requestCount = totalCount / PAGESIZE;
            for (int i = 0; i <= requestCount; i++) {
                Integer fromIndex = i * PAGESIZE;
                //如果总数少于PAGE_SIZE,为了防止数组越界,toIndex直接使用totalCount即可
                int toIndex = Math.min(totalCount, (i + 1) * PAGESIZE);
                List<String> subList = ysfwbmList.subList(fromIndex, toIndex);
                List<HfJyztResponseDTO> hfJyztResponseDTOList = hfJyztController.queryJyztByYsfwbm(subList,qjgldm);
                if (CollectionUtils.isNotEmpty(hfJyztResponseDTOList)) {
                    for (HfJyztResponseDTO hfJyztResponseDTO : hfJyztResponseDTOList) {
                        hfJyztMap.put(hfJyztResponseDTO.getYsfwbm(), hfJyztResponseDTO);
                    }
                }
                //总数不到一页或者刚好等于一页的时候,只需要处理一次就可以退出for循环了
                if (toIndex == totalCount) {
                    break;
                }
            }

        }
        return hfJyztMap;
    }

    private static String getHfJyztSpan(ResourceDTO resourceDTO, List<String> defaultShows, Map<String, HfJyztResponseDTO> hfJyztMap, Set<String> jyztSet) {
        Map<String, Object> ysfwbmMap = MapUtils.getMap(resourceDTO.getInfo(), "ysfwbm");
        String ysfwbm = MapUtils.getString(ysfwbmMap, "value");
        if (!StringUtils.equals(MapUtils.getString(ysfwbmMap, "desc"), "getHfJyzt")) {
            return "";
        }
        boolean jyztShow = defaultShows.contains(JYZT_CONSTANT);
        if (MapUtils.isEmpty(resourceDTO.getInfo()) || MapUtils.isEmpty(hfJyztMap) || hfJyztMap.get(ysfwbm) == null) {
            StringBuilder emptyBui = new StringBuilder("<p class=\"bdc-table-state bdc-jy-state ");
            if (!jyztShow) {
                emptyBui.append("layui-hide");
            }
            emptyBui.append("\">无交易状态</p>");
            resourceDTO.getInfo().put("jyztHtml", emptyBui.toString());
            return emptyBui.toString();
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<p class=\"bdc-table-state bdc-jy-state ");
        if (!jyztShow) {
            stringBuffer.append("layui-hide");
        }
        stringBuffer.append("\" title=\"").append(hfJyztMap.get(ysfwbm).getJyzt()).append("\">");
        if (StringUtils.isNotBlank(hfJyztMap.get(ysfwbm).getColor())) {
            stringBuffer.append("<span style=\"color:#").append(hfJyztMap.get(ysfwbm).getColor()).append("\">").append(hfJyztMap.get(ysfwbm).getJyzt()).append("</span>");
        } else {
            stringBuffer.append(hfJyztMap.get(ysfwbm).getJyzt());
        }
        jyztSet.add(hfJyztMap.get(ysfwbm).getJyzt());
        stringBuffer.append("</p>");
        resourceDTO.getInfo().put("jyztHtml", stringBuffer.toString());
        return stringBuffer.toString();
    }

    /**
     * @param lpbViewVO
     * @param lpbChVO
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取每层数据
     */
    private static void setCvo(LpbViewVO lpbViewVO, LpbChVO lpbChVO) {
        int cHs = 0;
        for (LpbDyVO lpbDyVO : lpbChVO.getDyFwList()) {
            cHs += lpbDyVO.getFwhsResourceDTOList().size();
        }
        if (lpbViewVO.getMaxHs() < cHs) {
            lpbViewVO.setMaxHs(cHs);
        }
        lpbViewVO.getcFwList().add(lpbChVO);
    }


    /**
     * @param lpbChVO
     * @param cHsList
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 将每一层的户室List 处理成 单元结构存进层实体
     */
    private static void setCDyList(LpbChVO lpbChVO, List<ResourceDTO> cHsList, Map<String, Map<Integer, Integer>> dyWlcsMaxhsMap) {
        Map<String, LpbDyVO> dyMap = new LinkedHashMap<>();
        // 循环户室
        Integer wlcs = null;
        for (ResourceDTO fwhs : cHsList) {
            Map<String, Object> infoMap = fwhs.getInfo();
            Map<String, Object> dycsMap = MapUtils.getMap(infoMap, "dycs");
            Map<String, Object> wlcsMap = MapUtils.getMap(infoMap, "wlcs");
            Map<String, Object> dyhMap = MapUtils.getMap(infoMap, "dyh");
            if (StringUtils.isBlank(lpbChVO.getDycs())
                    && StringUtils.isNotBlank(MapUtils.getString(dycsMap, "value"))) {
                // 保存定义层
                lpbChVO.setDycs(MapUtils.getString(dycsMap, "value"));
            }
            if (wlcs == null && MapUtils.getInteger(wlcsMap, "value") != null) {
                wlcs = MapUtils.getInteger(wlcsMap, "value");
            }

            // 获取单元号
            String dyh = StringUtils.isNotBlank(MapUtils.getString(dyhMap, "value"))
                    ? MapUtils.getString(dyhMap, "value") : "";
            if (MapUtils.getObject(dyMap, dyh) == null) {
                LpbDyVO lpbDyVO = new LpbDyVO();
                lpbDyVO.setDyh(dyh);
                List<ResourceDTO> dyHsList = new ArrayList<>();
                dyHsList.add(fwhs);
                lpbDyVO.setFwhsResourceDTOList(dyHsList);
                dyMap.put(dyh, lpbDyVO);
            } else {
                LpbDyVO lpbDyVO = dyMap.get(dyh);
                lpbDyVO.getFwhsResourceDTOList().add(fwhs);
            }
        }
        if (wlcs == null) {
            wlcs = new Integer("0");
        }

        // 将单元Map 转成单元List
        if (MapUtils.isNotEmpty(dyMap)) {
            List<LpbDyVO> dyVOList = new LinkedList<>();
            for (String key : dyMap.keySet()) {
                LpbDyVO lpbDyVO = dyMap.get(key);
                dyVOList.add(lpbDyVO);
                // 处理 每单元最大户数
                int thisMaxHs = getDyMaxHs(lpbDyVO);
                if (MapUtils.isEmpty(dyWlcsMaxhsMap.get(key))) {
                    dyWlcsMaxhsMap.put(key, new LinkedHashMap<>());
                    dyWlcsMaxhsMap.get(key).put(wlcs, thisMaxHs);
                } else {
                    Integer maxHs = MapUtils.getIntValue(dyWlcsMaxhsMap.get(key), wlcs, 0);
                    dyWlcsMaxhsMap.get(key).put(wlcs, maxHs + thisMaxHs);
                }
                addDyMaxHs(lpbDyVO, dyWlcsMaxhsMap);
            }

            lpbChVO.setDyFwList(dyVOList);
        }
    }

    /**
     * @param lpbDyVO
     * @param lpbDyVO
     * @return int
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 有上下合并的也应该算在最大户室里，因为有可能 1层有1个，2层有一个，但是都合并，这样最大户室是2
     */
    private static void addDyMaxHs(LpbDyVO lpbDyVO, Map<String, Map<Integer, Integer>> dyWlcsMaxhsMap) {
        if (lpbDyVO != null && CollectionUtils.isNotEmpty(lpbDyVO.getFwhsResourceDTOList())) {
            for (ResourceDTO fwhs : lpbDyVO.getFwhsResourceDTOList()) {
                Map<String, Object> infoMap = fwhs.getInfo();
                Map<String, Object> hbfxMap = MapUtils.getMap(infoMap, "hbfx");
                Map<String, Object> hbhsMap = MapUtils.getMap(infoMap, "hbhs");
                // 获取单元号
                Map<String, Object> dyhMap = MapUtils.getMap(infoMap, "dyh");
                String dyh = StringUtils.isNotBlank(MapUtils.getString(dyhMap, "value"))
                        ? MapUtils.getString(dyhMap, "value") : "";
                Integer wlcs = MapUtils.getInteger(MapUtils.getMap(infoMap, "wlcs"), "value");
                if (wlcs == null) {
                    wlcs = 0;
                }
                if (MapUtils.isEmpty(dyWlcsMaxhsMap.get(dyh))) {
                    dyWlcsMaxhsMap.put(dyh, new LinkedHashMap<>());
                }
                dyWlcsMaxhsMap.get(dyh).putIfAbsent(wlcs, 0);
                if (infoMap != null
                        && StringUtils.isNotBlank(MapUtils.getString(hbfxMap, "value"))
                        && NumberUtils.isNumber(MapUtils.getString(hbhsMap, "value"))) {
                    String hbfx = MapUtils.getString(hbfxMap, "value");
                    int hbhs = MapUtils.getIntValue(hbhsMap, "value");
                    if (StringUtils.equals(Constants.FWHS_HBFX_UP, hbfx)) {
                        for (int cs = 1; cs <= hbhs; cs++) {
                            dyWlcsMaxhsMap.get(dyh).put(wlcs + cs, MapUtils.getIntValue(dyWlcsMaxhsMap.get(dyh), Integer.valueOf(wlcs + cs), 0) + 1);
                        }
                    }
                    if (StringUtils.equals(Constants.FWHS_HBFX_DOWN, hbfx)) {
                        for (int cs = 1; cs <= hbhs; cs++) {
                            dyWlcsMaxhsMap.get(dyh).put(wlcs - cs, MapUtils.getIntValue(dyWlcsMaxhsMap.get(dyh), Integer.valueOf(wlcs - cs), 0) + 1);
                        }
                    }
                }
            }
        }
    }

    /**
     * @param lpbDyVO
     * @return int
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 获取每单元最大户数 加上合并户数
     */
    private static int getDyMaxHs(LpbDyVO lpbDyVO) {
        int maxHs = 0;
        if (lpbDyVO != null
                && CollectionUtils.isNotEmpty(lpbDyVO.getFwhsResourceDTOList())) {
            for (ResourceDTO fwhs : lpbDyVO.getFwhsResourceDTOList()) {
                Map<String, Object> infoMap = fwhs.getInfo();
                Map<String, Object> hbfxMap = MapUtils.getMap(infoMap, "hbfx");
                Map<String, Object> hbhsMap = MapUtils.getMap(infoMap, "hbhs");
                if (infoMap != null
                        && StringUtils.isNotBlank(MapUtils.getString(hbfxMap, "value"))
                        && NumberUtils.isNumber(MapUtils.getString(hbhsMap, "value"))) {
                    String hbfx = MapUtils.getString(hbfxMap, "value");
                    int hbhs = MapUtils.getIntValue(hbhsMap, "value");
                    if (StringUtils.equals(Constants.FWHS_HBFX_LEFT, hbfx)
                            || StringUtils.equals(Constants.FWHS_HBFX_RIGHT, hbfx)) {
                        maxHs += hbhs;
                    }
                }
                maxHs++;
            }
        }
        return maxHs;
    }

    /**
     * @param lpbViewVO
     * @param ckList
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description
     */
    private void dealCk(LpbViewVO lpbViewVO, List<ResourceDTO> ckList) {
        Collections.sort(ckList, new FwhsResComparator(lpbViewSortConfig.getSortmap()));
        // 有且只有一行车库的情况下  判断最长户数
        int maxNum = getCkMaxNum(lpbViewVO);
        int rowspan = getRowSpan(ckList, maxNum);
        // 取第一个
        Map infoMap = ckList.get(0).getInfo();
        Map<String, Object> wlcsMap = MapUtils.getMap(infoMap, "wlcs");
        Map<String, Object> dycsMap = MapUtils.getMap(infoMap, "dycs");
        // 构造车库 VO
        LpbCkVo lpbCkVo = new LpbCkVo();
        lpbCkVo.setWlcs(MapUtils.getString(wlcsMap, "value"));
        lpbCkVo.setDycs(MapUtils.getString(dycsMap, "value"));
        lpbCkVo.setCkList(ckList);
        lpbCkVo.setRowspan(rowspan);
        lpbCkVo.setMaxnum(maxNum);
        lpbViewVO.setLpbCkVo(lpbCkVo);
    }

    /**
     * @param lpbViewVO
     * @return int
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取车库每行最大数
     */
    private static int getCkMaxNum(LpbViewVO lpbViewVO) {
        int max = 0;
        if (CollectionUtils.isNotEmpty(lpbViewVO.getDyList())) {
            // 获取每行最长单元格数
            for (Map<String, Object> dyMap : lpbViewVO.getDyList()) {
                max += MapUtils.getInteger(dyMap, "colspan");
            }
        }
        // 如果没有其他层房屋 则取最大设置单元格数
        if (max == 0) {
            max = CK_MAX_NUM;
        }
        return max;
    }


    /**
     * @param ckList
     * @param max
     * @return int
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取行数
     */
    private static int getRowSpan(List<ResourceDTO> ckList, int max) {
        int count = ckList.size();
        int mol = count % max;
        int rowspan = count / max;
        if (mol > 0) {
            rowspan += 1;
        }
        return rowspan;
    }


    /**
     * @param resourceDTO
     * @return java.util.Map<java.lang.String, java.lang.String>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取 地上层数和地下层数
     */
    private static void getLjzFwcs(LpbViewVO lpbViewVO, ResourceDTO resourceDTO) {
        if (resourceDTO != null && resourceDTO.getInfo() != null) {
            LinkedHashMap<String, Object> info = resourceDTO.getInfo();
            if (info.containsKey("fwcs")) {
                Map temMap = MapUtils.getMap(info, "fwcs");
                String fwcs = MapUtils.getString(temMap, "value");
                if (StringUtils.isNotBlank(fwcs) && NumberUtils.isNumber(fwcs)) {
                    lpbViewVO.setFwcs(Integer.parseInt(fwcs));
                } else {
                    throw new AppException("幢房屋层数值存在问题，请检查");
                }
            }
        }
    }

    /**
     * @param resourceDTO
     * @return map
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 获取颜色配置
     */
    private static Map<String, String> getStateColorMap(ResourceDTO resourceDTO) {
        Map<String, String> map = new HashMap<>();
        if (resourceDTO != null && resourceDTO.getInfo() != null) {
            LinkedHashMap<String, Object> info = resourceDTO.getInfo();
            String[] keys = new String[]{"djztState", "qlztState", "jyztState", "fzztState", "baztState"};
            for (String key : keys) {
                Map<String, Object> data = MapUtils.getMap(info, key);
                String constant = MapUtils.getString(data, "constant");
                if (StringUtils.isNotBlank(constant)) {
                    String[] pzs = constant.split(",");
                    for (String str : pzs) {
                        Map<String, Object> colorMap = MapUtils.getMap(info, str + "_color");
                        String color = MapUtils.getString(colorMap, "constant");
                        String font = MapUtils.getString(colorMap, "font");
                        if (StringUtils.isNotBlank(color)) {
                            map.put(str, color);
                        }
                        if (StringUtils.isNotBlank(font)) {
                            map.put(str + "_font", font);
                        }
                    }
                }
            }
        }
        return map;
    }


    /**
     * @param code, lpbConfigResource
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 保存 换行配置信息
     */
    private static void setTurnLineParam(String code, ResourceDTO lpbConfigResource) {
        Map<String, String> tempMap = CODE_TURNLINE_PARAM.get(code);
        if (MapUtils.isEmpty(tempMap)) {
            LinkedHashMap<String, Object> info = lpbConfigResource.getInfo();
            Map<String, Object> data = MapUtils.getMap(info, "turnline");
            if (data != null) {
                Map<String, String> turnlineConfigMap = new HashMap<>();
                turnlineConfigMap.put("column", MapUtils.getString(data, "refInfo"));
                turnlineConfigMap.put("columnval", MapUtils.getString(data, "constant"));
                CODE_TURNLINE_PARAM.put(code, turnlineConfigMap);
            }
        }
    }

    /**
     * @param code
     * @param infoMap
     * @return boolean
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 判断是否需要 换行
     */
    private static boolean checkNeedTurnline(String code, Map infoMap) {
        Map<String, String> thisCodeConfig = CODE_TURNLINE_PARAM.get(code);
        if (MapUtils.isNotEmpty(thisCodeConfig)) {
            String columnName = thisCodeConfig.get("column");
            String turnLineValue = thisCodeConfig.get("columnval");
            Map<String, Object> columnMap = MapUtils.getMap(infoMap, columnName);
            String columnValue = MapUtils.getString(columnMap, "value");
            if (StringUtils.isNotBlank(columnValue)) {
                String[] arrs = turnLineValue.split(",");
                List<String> tempList = Arrays.asList(arrs);
                if (tempList.contains(columnValue)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param resourceDTO
     * @param map
     * @return 颜色span
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 获取fjh的颜色配置
     */
    private String getFjhSpan(Map<String, String> map, ResourceDTO resourceDTO, String code) {
        String font = "";
        if (MapUtils.isEmpty(resourceDTO.getInfo())) {
            return StringUtils.EMPTY;
        }
        LinkedHashMap<String, Object> infoMap = resourceDTO.getInfo();
        Map wlcsMap = MapUtils.getMap(infoMap, "wlcs");
        Map dyhMap = MapUtils.getMap(infoMap, "dyh");
        Map fjhMap = MapUtils.getMap(infoMap, "fjh");
        Map fwHsIndexMap = MapUtils.getMap(infoMap, "fwHsIndex");
        Map bdcdyhMap = MapUtils.getMap(infoMap, "bdcdyh");
        Map prinBtnMap = MapUtils.getMap(infoMap, "printBtn");
        String wlcs = MapUtils.getString(wlcsMap, "value", StringUtils.EMPTY);
        String dyh = MapUtils.getString(dyhMap, "value", StringUtils.EMPTY);
        String fjh = MapUtils.getString(fjhMap, "value", StringUtils.EMPTY);
        String bdcdyh = MapUtils.getString(bdcdyhMap, "value", StringUtils.EMPTY);
        String fwHsIndex = MapUtils.getString(fwHsIndexMap, "value", StringUtils.EMPTY);
        boolean printBtn = MapUtils.getBooleanValue(prinBtnMap, "constant", false);
        StringBuffer stringBuffer = new StringBuffer();
        if (StringUtils.isNotBlank(fjh)) {
            stringBuffer.append("<p class=\"bdc-fh\"")
                    .append(" hsindex=\"" + fwHsIndex + "\"").append(" wlcs=\"" + wlcs + "\"").append(" dyh=\"" + dyh + "\"")
                    .append(">");
            if (StringUtils.isNotBlank(MapUtils.getString(fjhMap, "desc", StringUtils.EMPTY))) {
                stringBuffer.append(MapUtils.getString(fjhMap, "desc", StringUtils.EMPTY)).append(": ");
            }
            // 拼装房间号数据，如果有配置项，优先读取配置项展示
            List<LpbCololrConfig.ColorCofig> colorCofigList = lpbCololrConfig.getColorConfigStatus();
            Map<String, Object> htmlState = lpbCololrConfig.getHtmlState();
            String className = "bdc-wdj-word";
            String type = "WDJ";
            if (CollectionUtils.isNotEmpty(colorCofigList) && MapUtils.isNotEmpty(htmlState)) {
                Object state = htmlState.get(code);
                String title = String.valueOf(state).split(CommonConstantUtils.ZF_YW_DH)[0];
                for (LpbCololrConfig.ColorCofig colorCofig : colorCofigList) {
                    if (StringUtils.isNotBlank(title) && StringUtils.equals(title, colorCofig.getTitle())) {
                        colorCofig.order(colorCofig);
                        Map<String, Object> statusMap = colorCofig.getStatus();
                        if (colorCofig.isBackground()) {
                            style = 1;
                        }
                        for (String key : statusMap.keySet()) {
                            String value = String.valueOf(statusMap.get(key));
                            String zt = value.split(CommonConstantUtils.ZF_YW_HG)[1];
                            if (!CommonConstantUtils.SF_F_DM.equals(MapUtils.getInteger(resourceDTO.getStatus(), zt, CommonConstantUtils.SF_F_DM))) {
                                className = "bdc-" + zt + "-word";
                                type = zt.toUpperCase();
                                break;
                            }
                        }
                    }
                }
            } else {
                //房间号不展示预告的状态作为底色
                if (!CommonConstantUtils.SF_F_DM.equals(MapUtils.getInteger(resourceDTO.getStatus(), "yg", CommonConstantUtils.SF_F_DM)) && CommonConstantUtils.SF_F_DM.equals(style)) { // 预告
                    className = "bdc-ywg-word";
                    type = "YG";
                } else if (!CommonConstantUtils.SF_F_DM.equals(MapUtils.getInteger(resourceDTO.getStatus(), "zx", CommonConstantUtils.SF_F_DM))) { // 注销
                    className = "bdc-yzx-word";
                    type = "ZX";
                } else if (!CommonConstantUtils.SF_F_DM.equals(MapUtils.getInteger(resourceDTO.getStatus(), "dj", CommonConstantUtils.SF_F_DM))) { // 登记
                    className = "bdc-ydj-word";
                    type = "DJ";
                } else if (CommonConstantUtils.SF_S_DM.equals(MapUtils.getInteger(resourceDTO.getStatus(), "ba", CommonConstantUtils.SF_F_DM)) && StringUtils.equals(CommonConstantUtils.SYSTEM_VERSION_YC, lpbViewVersion)) {
                    type = "BA";
                }
            }
            if (Objects.nonNull(style) && CommonConstantUtils.SF_S_DM.equals(style)) {
                font = MapUtils.getString(map, type + "_font", StringUtils.EMPTY);
                stringBuffer.append("<span class=\"" + "\" title=\"")
                        .append(fjh).append("\" style=\"color:")
                        .append(font)
                        .append("\" data-wlcs=\"").append(wlcs)
                        .append("\" data-dyh=\"").append(dyh)
                        .append("\">").append(fjh).append("</span>");
            } else {
                stringBuffer.append("<span class=\"" + className + "\" title=\"")
                        .append(fjh).append("\" style=\"color:")
                        .append(MapUtils.getString(map, type, StringUtils.EMPTY))
                        .append("\" data-wlcs=\"").append(wlcs)
                        .append("\" data-dyh=\"").append(dyh)
                        .append("\">").append(fjh).append("</span>");
            }

            // 如果是受理  则 增加购物车图标
            if (LPB_CODE_ACCEPT.equals(code)) {
                stringBuffer.append("<a href='javascript:void(0);' cart='1' class='one-no-select one-icon'");
                stringBuffer.append(" hsindex='" + fwHsIndex + "'");
                stringBuffer.append(" wlcs='" + wlcs + "'");
                stringBuffer.append(" dyh='" + dyh + "'");
                stringBuffer.append("></a>");
            }
            //            //盐城加上打印图标
            if (printBtn) {
                stringBuffer.append("<a href='javascript:void(0);' class='btn-print'");
                stringBuffer.append(" hsindex='" + fwHsIndex + "'");
                stringBuffer.append(" bdcdyh='" + bdcdyh + "'");
                stringBuffer.append("></a>");
            }
            if (CollectionUtils.isNotEmpty(swhstHtmlCode) && swhstHtmlCode.contains(code)) {
                if (LPB_CODE_ACCEPT.equals(code)) {
                    stringBuffer.append("<a href='javascript:void(0);' title='三维户室图' class='btn-swhst-accept'");
                } else {
                    stringBuffer.append("<a href='javascript:void(0);' title='三维户室图' class='btn-swhst'");
                }
                stringBuffer.append(" bdcdyh='" + bdcdyh + "'");
                stringBuffer.append("></a>");
            }
            stringBuffer.append("</p>");
            // 处理详细信息的链接
            List<Link> list = resourceDTO.getLinks();
            String infoUrl = StringUtils.EMPTY;
            if (CollectionUtils.isNotEmpty(list)) {
                for (Link link : list) {
                    if (link != null && StringUtils.equals(link.getRel(), "fwhsinfo")) {
                        infoUrl = link.getHref();
                        break;
                    }
                }
            }
            if (StringUtils.isNotBlank(infoUrl)) {
                stringBuffer.insert(0, "<a href=\"" + infoUrl + "\" target=\"_blank\">");
            } else {
                stringBuffer.insert(0, "<a>");
            }
            stringBuffer.append("</a>");
            String dyfh = "";
            if (StringUtils.isNotBlank(dyh)) {
                dyfh = dyh + "-" + fjh;
            } else {
                dyfh = fjh;
            }
            HashMap<String, String> dyfhMap = new HashMap<>(1);
            dyfhMap.put("value", dyfh);
            infoMap.put("dyfh", dyfhMap);
            fjhMap.put("html", stringBuffer.toString());
            fjhMap.put("class", className);
            if (Objects.equals(CommonConstantUtils.SF_S_DM, style)) {
                fjhMap.put("color", type);
            }
        }
        return font;
    }


    /**
     * @param resourceDTO
     * @param map
     * @return 颜色span
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 获取权利状态的颜色配置
     */
    private static String getQlztSpan(Map<String, String> map, ResourceDTO resourceDTO, List<String> defaultShows, List<String> ztList, String font, Integer style, String code, LpbCololrConfig lpbCololrConfig) {
        // qlzt 是否默认显示
        boolean qlztShow = defaultShows.contains(QLZT_CONSTANT);
        if (MapUtils.isEmpty(resourceDTO.getInfo()) || MapUtils.isEmpty(resourceDTO.getStatus())) {
            StringBuilder emptyBui = new StringBuilder("<p class=\"bdc-table-state bdc-ql-state ");
            if (!qlztShow) {
                emptyBui.append("layui-hide");
            }
//            if (StringUtils.isNotBlank(font)) {
//                emptyBui.append("\" style=\"color:").append(font);
//            }
            emptyBui.append("\">无权利状态</p>");
            String qlztHtml = emptyBui.toString();
            resourceDTO.getInfo().put("qlztHtml", qlztHtml);
            return qlztHtml;
        }
        Map<String, Object> mapStatus = resourceDTO.getStatus();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<p class=\"bdc-table-state bdc-ql-state ");
        if (!qlztShow) {
            stringBuffer.append(" layui-hide");
        }
//        if (StringUtils.isNotBlank(font)) {
//            stringBuffer.append("\" style=\"color:").append(font);
//        }
        stringBuffer.append(" \">");
        boolean hasQlzt = false;
        List<LpbCololrConfig.ColorCofig> colorCofigList = lpbCololrConfig.getColorConfigStatus();
        Map<String, Object> htmlState = lpbCololrConfig.getHtmlState();
        if (CollectionUtils.isNotEmpty(colorCofigList) && MapUtils.isNotEmpty(htmlState)) {
            Object state = htmlState.get(code);
            String title = "权利状态";
            for (LpbCololrConfig.ColorCofig colorCofig : colorCofigList) {
                if (StringUtils.isNotBlank(title) && StringUtils.equals(title, colorCofig.getTitle())) {
                    Map<String, Object> statusMap = colorCofig.getStatus();
                    List<Object> statusList = statusMap.values().stream().collect(Collectors.toList());
                    for (String key : statusMap.keySet()) {
                        String value = String.valueOf(statusMap.get(key));
                        String zt = value.split(CommonConstantUtils.ZF_YW_HG)[1];
                        if (!CommonConstantUtils.SF_F_DM.equals(MapUtils.getInteger(mapStatus, zt, CommonConstantUtils.SF_F_DM))) {
                            stringBuffer.append("<span mark=\"")
                                    .append(zt.toUpperCase())
                                    .append("\" style=\"background-color:")
                                    .append(MapUtils.getString(map, zt.toUpperCase(), StringUtils.EMPTY))
                                    .append("\" class=\"").append(value);
                            if (!statusList.contains(value)) {
                                stringBuffer.append(" layui-hide");
                            } else {
                                hasQlzt = true;
                            }
                            stringBuffer.append(" \" title=\" ").append(key).append("\"></span>");
                        }
                    }
                }
            }
        } else {
            if (!CommonConstantUtils.SF_F_DM.equals(MapUtils.getInteger(mapStatus, "zjgcdy", CommonConstantUtils.SF_F_DM))) {
                stringBuffer.append("<span mark=\"")
                        .append("ZJGCDY")
                        .append("\" style=\"background-color:")
                        .append(MapUtils.getString(map, "ZJGCDY", StringUtils.EMPTY))
                        .append("\" class=\"bdc-zj");
                if (!ztList.contains("ZJGCDY")) {
                    stringBuffer.append(" layui-hide");
                } else {
                    hasQlzt = true;
                }
                stringBuffer.append(" \" title=\"在建工程抵押\"></span>");
            }
            if (!CommonConstantUtils.SF_F_DM.equals(MapUtils.getInteger(mapStatus, "ydya", CommonConstantUtils.SF_F_DM))) {
                stringBuffer.append("<span mark=\"")
                        .append("YDYA")
                        .append("\" style=\"background-color:")
                        .append(MapUtils.getString(map, "YDYA", StringUtils.EMPTY))
                        .append("\" class=\"bdc-ydy");
                if (!ztList.contains("YDYA")) {
                    stringBuffer.append(" layui-hide");
                } else {
                    hasQlzt = true;
                }
                stringBuffer.append(" \" title=\"预抵押\"></span>");
            }
            if (!CommonConstantUtils.SF_F_DM.equals(MapUtils.getInteger(mapStatus, "dya", CommonConstantUtils.SF_F_DM))) {
                stringBuffer.append("<span mark=\"")
                        .append("DYA")
                        .append("\" style=\"background-color:")
                        .append(MapUtils.getString(map, "DYA", StringUtils.EMPTY))
                        .append("\" class=\"bdc-dya");
                if (!ztList.contains("YDYA")) {
                    stringBuffer.append(" layui-hide");
                } else {
                    hasQlzt = true;
                }
                stringBuffer.append(" \" title=\"抵押\"></span>");
            }
            if (!CommonConstantUtils.SF_F_DM.equals(MapUtils.getInteger(mapStatus, "ycf", CommonConstantUtils.SF_F_DM))) {
                stringBuffer.append("<span mark=\"")
                        .append("YCF")
                        .append("\" style=\"background-color:")
                        .append(MapUtils.getString(map, "YCF", StringUtils.EMPTY))
                        .append("\" class=\"bdc-ycf");
                if (!ztList.contains("YCF")) {
                    stringBuffer.append(" layui-hide");
                } else {
                    hasQlzt = true;
                }
                stringBuffer.append(" \" title=\"预查封\"></span>");
            }
            if (!CommonConstantUtils.SF_F_DM.equals(MapUtils.getInteger(mapStatus, "cf", CommonConstantUtils.SF_F_DM))) {
                stringBuffer.append("<span mark=\"")
                        .append("CF")
                        .append("\" style=\"background-color:")
                        .append(MapUtils.getString(map, "CF", StringUtils.EMPTY))
                        .append("\" class=\"bdc-cf");
                if (!ztList.contains("CF")) {
                    stringBuffer.append(" layui-hide");
                } else {
                    hasQlzt = true;
                }
                stringBuffer.append(" \" title=\"查封\"></span>");
            }
            if (!CommonConstantUtils.SF_F_DM.equals(MapUtils.getInteger(mapStatus, "sd", CommonConstantUtils.SF_F_DM))) {
                stringBuffer.append("<span mark=\"")
                        .append("SD")
                        .append("\" style=\"background-color:")
                        .append(MapUtils.getString(map, "SD", StringUtils.EMPTY))
                        .append("\" class=\"bdc-dj");
                if (!ztList.contains("SD")) {
                    stringBuffer.append(" layui-hide");
                } else {
                    hasQlzt = true;
                }
                stringBuffer.append(" \" title=\"锁定\"></span>");
            }
            if (!CommonConstantUtils.SF_F_DM.equals(MapUtils.getInteger(mapStatus, "yy", CommonConstantUtils.SF_F_DM))) {
                stringBuffer.append("<span mark=\"")
                        .append("YY")
                        .append("\" style=\"background-color:")
                        .append(MapUtils.getString(map, "YY", StringUtils.EMPTY))
                        .append("\" class=\"bdc-yy");
                if (!ztList.contains("YY")) {
                    stringBuffer.append(" layui-hide");
                } else {
                    hasQlzt = true;
                }
                stringBuffer.append(" \" title=\"异议\"></span>");
            }
            if (!CommonConstantUtils.SF_F_DM.equals(MapUtils.getInteger(mapStatus, "dyi", CommonConstantUtils.SF_F_DM))) {
                stringBuffer.append("<span mark=\"")
                        .append("DYI")
                        .append("\" style=\"background-color:")
                        .append(MapUtils.getString(map, "DYI", StringUtils.EMPTY))
                        .append("\" class=\"bdc-dy");
                if (!ztList.contains("DYI")) {
                    stringBuffer.append(" layui-hide");
                } else {
                    hasQlzt = true;
                }
                stringBuffer.append(" \" title=\"地役权\"></span>");
            }
            if (!CommonConstantUtils.SF_F_DM.equals(MapUtils.getInteger(mapStatus, "jzq", CommonConstantUtils.SF_F_DM))) {
                stringBuffer.append("<span mark=\"")
                        .append("JZQ")
                        .append("\" style=\"background-color:")
                        .append(MapUtils.getString(map, "JZQ", StringUtils.EMPTY))
                        .append("\" class=\"bdc-jzq");
                if (!ztList.contains("JZQ")) {
                    stringBuffer.append(" layui-hide");
                } else {
                    hasQlzt = true;
                }
                stringBuffer.append(" \" title=\"居住权\"></span>");
            }
            //如果是底色展示，读取预告信息放在权利部分展示
            if (!CommonConstantUtils.SF_F_DM.equals(MapUtils.getInteger(resourceDTO.getStatus(), "yg", CommonConstantUtils.SF_F_DM)) && CommonConstantUtils.SF_S_DM.equals(style)) { // 预告
                stringBuffer.append("<span mark=\"")
                        .append("YG")
                        .append("\" style=\"background-color:")
                        .append(MapUtils.getString(map, "YG", StringUtils.EMPTY))
                        .append("\" class=\"bdc-ywg");
                if (!ztList.contains("YG")) {
                    stringBuffer.append(" layui-hide");
                } else {
                    hasQlzt = true;
                }
                stringBuffer.append(" \" title=\"预告\"></span>");
            }
        }
        if (!hasQlzt) {
            stringBuffer.append("无权利状态");
        }
        stringBuffer.append("</p>");
        resourceDTO.getInfo().put("qlztHtml", stringBuffer.toString());
        return stringBuffer.toString();
    }

    /**
     * @param map
     * @param resourceDTO
     * @param jyztSet
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 获取交易状态的信息
     */
    private static String getJyztSpan(Map<String, String> map, ResourceDTO resourceDTO, List<String> defaultShows, List<String> ztList, Set<String> jyztSet) {
        boolean jyztShow = defaultShows.contains(JYZT_CONSTANT);
        if (MapUtils.isEmpty(resourceDTO.getInfo()) || MapUtils.isEmpty(resourceDTO.getStatus())) {
            StringBuilder emptyBui = new StringBuilder("<p class=\"bdc-table-state bdc-jy-state ");
            if (!jyztShow) {
                emptyBui.append("layui-hide");
            }
            emptyBui.append("\">无交易状态</p>");
            String jyztHtml = emptyBui.toString();
            resourceDTO.getInfo().put("jyztHtml", jyztHtml);
            return jyztHtml;
        }
        Map<String, Object> mapStatus = resourceDTO.getStatus();
        StringBuffer stringBuffer = new StringBuffer();
        boolean hasJyzt = false;
        String title = "";
        if (!CommonConstantUtils.SF_F_DM.equals(MapUtils.getInteger(mapStatus, "ks", CommonConstantUtils.SF_F_DM))) {
            stringBuffer.append("<span mark=\"")
                    .append("KS")
                    .append("\" style=\"background-color:")
                    .append(MapUtils.getString(map, "KS", StringUtils.EMPTY))
                    .append("\" class=\"bdc-ks");
            if (!ztList.contains("KS")) {
                stringBuffer.append(" layui-hide");
            } else {
                hasJyzt = true;
            }
            title = "可售";
            stringBuffer.append(" \" title=\"可售\"></span>");
        }
        if (!CommonConstantUtils.SF_F_DM.equals(MapUtils.getInteger(mapStatus, "ys", CommonConstantUtils.SF_F_DM))) {
            stringBuffer.append("<span mark=\"")
                    .append("YS")
                    .append("\" style=\"background-color:")
                    .append(MapUtils.getString(map, "YS", StringUtils.EMPTY))
                    .append("\" class=\"bdc-ys");
            if (!ztList.contains("YS")) {
                stringBuffer.append(" layui-hide");
            } else {
                hasJyzt = true;
            }
            title = "已售";
            stringBuffer.append(" \" title=\"已售\"></span>");
        }
        if (!CommonConstantUtils.SF_F_DM.equals(MapUtils.getInteger(mapStatus, "cq", CommonConstantUtils.SF_F_DM))) {
            stringBuffer.append("<span mark=\"")
                    .append("CQ")
                    .append("\" style=\"background-color:")
                    .append(MapUtils.getString(map, "CQ", StringUtils.EMPTY))
                    .append("\" class=\"bdc-cq");
            if (!ztList.contains("CQ")) {
                stringBuffer.append(" layui-hide");
            } else {
                hasJyzt = true;
            }
            title = "草签";
            stringBuffer.append(" \" title=\"草签\"></span>");
        }
        if (!CommonConstantUtils.SF_F_DM.equals(MapUtils.getInteger(mapStatus, "wg", CommonConstantUtils.SF_F_DM))) {
            stringBuffer.append("<span mark=\"")
                    .append("WG")
                    .append("\" style=\"background-color:")
                    .append(MapUtils.getString(map, "WG", StringUtils.EMPTY))
                    .append("\" class=\"bdc-wg");
            if (!ztList.contains("WG")) {
                stringBuffer.append(" layui-hide");
            } else {
                hasJyzt = true;
            }
            title = "物管用房";
            stringBuffer.append(" \" title=\"物管用房\"></span>");
        }
        if (!CommonConstantUtils.SF_F_DM.equals(MapUtils.getInteger(mapStatus, "az", CommonConstantUtils.SF_F_DM))) {
            stringBuffer.append("<span mark=\"")
                    .append("AZ")
                    .append("\" style=\"background-color:")
                    .append(MapUtils.getString(map, "AZ", StringUtils.EMPTY))
                    .append("\" class=\"bdc-az");
            if (!ztList.contains("AZ")) {
                stringBuffer.append(" layui-hide");
            } else {
                hasJyzt = true;
            }
            title = "安置房";
            stringBuffer.append(" \" title=\"安置房\"></span>");
        }
        if (!CommonConstantUtils.SF_F_DM.equals(MapUtils.getInteger(mapStatus, "bl", CommonConstantUtils.SF_F_DM))) {
            stringBuffer.append("<span mark=\"")
                    .append("BL")
                    .append("\" style=\"background-color:")
                    .append(MapUtils.getString(map, "BL", StringUtils.EMPTY))
                    .append("\" class=\"bdc-bl layui-hide\" title=\"保留房\"></span>");
            if (!ztList.contains("BL")) {
                stringBuffer.append(" layui-hide");
            } else {
                hasJyzt = true;
            }
            title = "保留房";
            stringBuffer.append(" \" title=\"保留房\"></span>");
        }
        if (!CommonConstantUtils.SF_F_DM.equals(MapUtils.getInteger(mapStatus, "fpt", CommonConstantUtils.SF_F_DM))) {
            stringBuffer.append("<span mark=\"")
                    .append("FPT")
                    .append("\" style=\"background-color:")
                    .append(MapUtils.getString(map, "FPT", StringUtils.EMPTY))
                    .append("\" class=\"bdc-fpt layui-hide\" title=\"非普通住宅\"></span>");
            if (!ztList.contains("FPT")) {
                stringBuffer.append(" layui-hide");
            } else {
                hasJyzt = true;
            }
            title = "非普通住宅";
            stringBuffer.append(" \" title=\"非普通住宅\"></span>");
        }
        if (!hasJyzt) {
            stringBuffer.append("无交易状态");
        }
        StringBuffer head = new StringBuffer();
        head.append("<p class=\"bdc-table-state bdc-jy-state ");
        if (!jyztShow) {
            head.append("layui-hide");
        }
        head.append(" \" title=\"").append(title).append("\">");
        stringBuffer.insert(0, head);
        if (StringUtils.isNotBlank(title)) {
            jyztSet.add(title);
        }
        stringBuffer.append("</p>");
        resourceDTO.getInfo().put("jyztHtml", stringBuffer.toString());
        return stringBuffer.toString();
    }

    /**
     * @param resourceDTO
     * @param map
     * @return
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 获取交易状态的信息
     */
    private String getFzztSpan(Map<String, String> map, ResourceDTO resourceDTO, List<String> defaultShows, List<String> ztList, List<BdcSlCshFwkgDataDTO> bdcSlCshFwkgDataDTOList, String font) {
        boolean fzztShow = defaultShows.contains(FZZT_CONSTANT);
        if (MapUtils.isEmpty(resourceDTO.getInfo()) || MapUtils.isEmpty(resourceDTO.getStatus())) {
            StringBuilder emptyBui = new StringBuilder("<p class=\"bdc-table-state bdc-fz-state ");
            if (!fzztShow) {
                emptyBui.append("layui-hide");
            }
//            if (StringUtils.isNotBlank(font)) {
//                emptyBui.append("\" style=\"color:").append(font);
//            }
            emptyBui.append("\">无发证状态</p>");
            String fzztHtml = emptyBui.toString();
            resourceDTO.getInfo().put("fzztHtml", fzztHtml);
            return fzztHtml;
        }
        Map<String, Object> mapStatus = resourceDTO.getStatus();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<p class=\"bdc-table-state bdc-fz-state ");
        if (!fzztShow) {
            stringBuffer.append("layui-hide");
        }
        if (StringUtils.isNotBlank(font)) {
            stringBuffer.append("\" style=\"color:").append(font);
        }
        stringBuffer.append(" \">");
        boolean hasFzzt = false;
        String bdcdyh = MapUtils.getString(MapUtils.getMap(resourceDTO.getInfo(), "bdcdyh"), "value");
        BdcSlCshFwkgDataDTO bdcSlCshFwkgDataDTO = null;
        for (BdcSlCshFwkgDataDTO bdcSlCshFwkgDataTemp : bdcSlCshFwkgDataDTOList) {
            if (StringUtils.equals(bdcSlCshFwkgDataTemp.getBdcdyh(), bdcdyh)) {
                bdcSlCshFwkgDataDTO = bdcSlCshFwkgDataTemp;
            }
        }
        if (bdcSlCshFwkgDataDTO != null && CommonConstantUtils.SF_S_DM.equals(bdcSlCshFwkgDataDTO.getSfsczs())
                && CommonConstantUtils.ZSLX_ZS.equals(bdcSlCshFwkgDataDTO.getZszl())) {
            stringBuffer.append("<span mark=\"")
                    .append("ZS")
                    .append("\" style=\"background-color:")
                    .append(MapUtils.getString(map, "ZS", StringUtils.EMPTY))
                    .append("\" class=\"bdc-zs");
            if (!ztList.contains("ZS")) {
                stringBuffer.append(" layui-hide");
            } else {
                hasFzzt = true;
            }
            stringBuffer.append(" \" title=\"生成证书\"></span>");
        }
        if (bdcSlCshFwkgDataDTO != null && CommonConstantUtils.SF_S_DM.equals(bdcSlCshFwkgDataDTO.getSfsczs())
                && CommonConstantUtils.ZSLX_ZMD.equals(bdcSlCshFwkgDataDTO.getZszl())) {
            stringBuffer.append("<span mark=\"")
                    .append("ZM")
                    .append("\" style=\"background-color:")
                    .append(MapUtils.getString(map, "ZM", StringUtils.EMPTY))
                    .append("\" class=\"bdc-zm");
            if (!ztList.contains("ZM")) {
                stringBuffer.append(" layui-hide");
            } else {
                hasFzzt = true;
            }
            stringBuffer.append(" \" title=\"生成证明书\"></span>");
        }
        if (bdcSlCshFwkgDataDTO != null && CommonConstantUtils.SF_F_DM.equals(bdcSlCshFwkgDataDTO.getSfsczs())) {
            stringBuffer.append("<span mark=\"")
                    .append("BFZ")
                    .append("\" style=\"background-color:")
                    .append(MapUtils.getString(map, "BFZ", StringUtils.EMPTY))
                    .append("\" class=\"bdc-bfz");
            if (!ztList.contains("BFZ")) {
                stringBuffer.append(" layui-hide");
            } else {
                hasFzzt = true;
            }
            stringBuffer.append(" \" title=\"只登簿不发证\"></span>");
        }
        if (!hasFzzt) {
            stringBuffer.append("无发证状态");
        }
        stringBuffer.append("</p>");
        resourceDTO.getInfo().put("fzztHtml", stringBuffer.toString());
        return stringBuffer.toString();
    }

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取备案状态
     * @date : 2020/12/21 14:48
     */
    private String getBaztSpan(Map<String, String> map, ResourceDTO resourceDTO, List<String> defaultShows, List<String> ztList, String code, String font) {
        boolean baztShow = defaultShows.contains(BAZT_CONSTANT);
        if (MapUtils.isEmpty(resourceDTO.getInfo()) || MapUtils.isEmpty(resourceDTO.getStatus())) {
            StringBuilder emptyBui = new StringBuilder("<p class=\"bdc-table-state bdc-ba-state ");
            if (!baztShow) {
                emptyBui.append("layui-hide");
            }
//            if (StringUtils.isNotBlank(font)) {
//                emptyBui.append("\" style=\"color:").append(font);
//            }
            HashMap<String, String> baztMap = new HashMap<>(1);
            baztMap.put("class", "");
            baztMap.put("color", StringUtils.EMPTY);
            resourceDTO.getInfo().put("baztMap", baztMap);
            emptyBui.append("\">无备案状态</p>");
            String baztHtml = emptyBui.toString();
            resourceDTO.getInfo().put("baztHtml", baztHtml);
            return baztHtml;
        }

        Map<String, Object> mapStatus = resourceDTO.getStatus();
        Map<String, Object> fwhsInfo = resourceDTO.getInfo();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<p class=\"bdc-table-state bdc-ba-state");
        if (!baztShow) {
            stringBuffer.append(" layui-hide");
        }
        if (StringUtils.isNotBlank(font)) {
            stringBuffer.append("\" style=\"color:").append(font);
        }
        stringBuffer.append(" \">");
        boolean hasBazt = false;
        String title = "";
        HashMap<String, String> baztMap = new HashMap<>(1);
        if (CommonConstantUtils.SF_S_DM.equals(MapUtils.getInteger(mapStatus, "ba", CommonConstantUtils.SF_F_DM))) {
            stringBuffer.append("<span mark=\"")
                    .append("BA")
                    .append("\" style=\"background-color:")
                    .append(MapUtils.getString(map, "BA", StringUtils.EMPTY))
                    .append("\" class=\"bdc-ba");
            if (!ztList.contains("BA")) {
                stringBuffer.append(" layui-hide");
            } else {
                hasBazt = true;
            }
            stringBuffer.append(" \" title=\"已备案").append(title).append("\"></span>");
            baztMap.put("class", "bdc-ba");
            if (Objects.equals(CommonConstantUtils.SF_S_DM, style)) {
                baztMap.put("color", MapUtils.getString(map, "BA", StringUtils.EMPTY));
            }
        } else {
            baztMap.put("class", "");
            baztMap.put("color", "");
        }
        if (!hasBazt) {
            stringBuffer.append("无备案状态");
        }
        stringBuffer.append("</p>");
        fwhsInfo.put("baztMap", baztMap);
        resourceDTO.getInfo().put("baztHtml", stringBuffer.toString());
        return stringBuffer.toString();
    }

    /*
     * 更多内容展示图标
     * */
    private String getTitleSpan(ResourceDTO resourceDTO, String code) {
        LinkedHashMap<String, Object> infoMap = resourceDTO.getInfo();
        Map titleMap = MapUtils.getMap(infoMap, "title");
        boolean title = MapUtils.getBooleanValue(titleMap, "constant", false);
        if (!title) {
            return StringUtils.EMPTY;
        }
        boolean moreShow = Objects.equals(CommonConstantUtils.SF_S_DM, showTitle);
        StringBuilder emptyBui = new StringBuilder("<p class=\"show-more ");
        String moreHtml = "";
        if (MapUtils.isEmpty(resourceDTO.getInfo())) {
            resourceDTO.getInfo().put("moreHtml", moreHtml);
            return moreHtml;
        } else {
            if (!moreShow) {
                resourceDTO.getInfo().put("moreHtml", moreHtml);
                return moreHtml;
            }
            emptyBui.append("\"></p>");
        }
        moreHtml = emptyBui.toString();
        resourceDTO.getInfo().put("moreHtml", moreHtml);
        return moreHtml;
    }


    /**
     * @param resourceDTO
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 获取其他属性的信息
     */
    private static String getDescSpan(ResourceDTO resourceDTO, String tab, List<String> defaultShows, String font) {
        StringBuffer stringBuffer = new StringBuffer();
        LinkedHashMap<String, Object> mapInfo = resourceDTO.getInfo();
        if (MapUtils.isNotEmpty(mapInfo)) {
            for (String key : mapInfo.keySet()) {
                Map mapKey = MapUtils.getMap(mapInfo, key);
                String tabType = MapUtils.getString(mapKey, "tabType", StringUtils.EMPTY);
                if (StringUtils.isBlank(tabType) || Arrays.asList(tabType.split(",")).contains(tab)) {
                    String desc = MapUtils.getString(mapKey, "desc");
                    // desc 存在null 和 空字符串两种情况， null时不添加至单元格内
                    if (desc != null) {
                        String descInfo = StringUtils.isBlank(desc) ? StringUtils.EMPTY : desc + ":";
                        String descValue = MapUtils.getString(mapKey, "value", StringUtils.EMPTY);
                        String descTitle = MapUtils.getString(mapKey, "value", StringUtils.EMPTY);
                        if (ELLIPSIS_ZL.equals(key) && descValue.length() > ELLIPSIS_ZL_LEAVELEN) {
                            descValue = "..." + descValue.substring(descValue.length() - ELLIPSIS_ZL_LEAVELEN, descValue.length());
                        }
                        stringBuffer.append("<p class=\"bdc-describe");
                        // 判断是否隐藏
                        if (!defaultShows.contains(key)) {
                            stringBuffer.append(" layui-hide");
                        }
                        stringBuffer.append("\" mark=\"")
                                .append(key).append("\" title=\"")
                                .append(descTitle).append("\" style=\"color:")
                                .append("\">").append(descInfo).append(descValue)
                                .append("</p>");
                    }
                }
            }
        }
        resourceDTO.getInfo().put("descHtml", stringBuffer.toString());
        return stringBuffer.toString();
    }

    /*
     * 获取显示产权的权利人数据
     * */
    private static void getQlr(List<ResourceDTO> resourceDTOList, List<BdcXmDO> bdcXmDOList, List<HtbaMsrVO> htbaMsrVOList, List<String> defaultShows, Map<String, String> map) {
        for (ResourceDTO resourceDTO : resourceDTOList) {
            LinkedHashMap<String, Object> fwhsInfo = resourceDTO.getInfo();
            String bdcdyh = MapUtils.getString(MapUtils.getMap(fwhsInfo, "bdcdyh"), "value");
            Map qlrMap = MapUtils.getMap(fwhsInfo, "qlr");
            Map<String, String> bdcxmMap = new HashMap<>(bdcXmDOList.size());
            Map<String, String> htbaMap = new HashMap<>(htbaMsrVOList.size());
            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                bdcxmMap = bdcXmDOList.stream().filter(bdcXmDO -> StringUtils.isNotBlank(bdcXmDO.getQlr())).collect(Collectors.toMap(BdcXmDO::getBdcdyh, BdcXmDO::getQlr, (key1, key2) -> key1));
            }
            if (StringUtils.isNotBlank(bdcdyh) && MapUtils.isNotEmpty(bdcxmMap) && MapUtils.isNotEmpty(qlrMap)) {
                String font = "";
                String type = "WDJ";
                if (!CommonConstantUtils.SF_F_DM.equals(MapUtils.getInteger(resourceDTO.getStatus(), "zx", CommonConstantUtils.SF_F_DM))) { // 注销
                    type = "ZX";
                } else if (!CommonConstantUtils.SF_F_DM.equals(MapUtils.getInteger(resourceDTO.getStatus(), "dj", CommonConstantUtils.SF_F_DM))) { // 登记
                    type = "DJ";
                }
                if (CommonConstantUtils.SF_S_DM.equals(MapUtils.getInteger(resourceDTO.getStatus(), "ba", CommonConstantUtils.SF_F_DM))) {
                    type = "BA";
                }
                font = MapUtils.getString(map, type + "_font", StringUtils.EMPTY);
                boolean qlrShow = defaultShows.contains("qlr");
                StringBuilder qlrStrb = new StringBuilder("<p class=\"bdc-describe ");
                // 判断是否隐藏
                if (!qlrShow) {
                    qlrStrb.append(" layui-hide");
                }
                qlrStrb.append("\" mark=\"")
                        .append("qlr").append("\" title=\"")
                        .append(StringUtils.isNotBlank(bdcxmMap.get(bdcdyh)) ? bdcxmMap.get(bdcdyh) : StringUtils.EMPTY).append("\" style=\"color:")
                        .append(font).append("\">").append(StringUtils.isNotBlank(bdcxmMap.get(bdcdyh)) ? bdcxmMap.get(bdcdyh) : StringUtils.EMPTY)
                        .append("</p>");
                fwhsInfo.put("descHtml", qlrStrb.toString() + fwhsInfo.get("descHtml"));
                qlrMap.put("value", bdcxmMap.get(bdcdyh));
            }
            if (CollectionUtils.isNotEmpty(htbaMsrVOList)) {
                htbaMap = htbaMsrVOList.stream().filter(htbaMsrVO -> StringUtils.isNotBlank(htbaMsrVO.getQlrmc())).collect(Collectors.toMap(HtbaMsrVO::getBdcdyh, HtbaMsrVO::getQlrmc, (key1, key2) -> key1 + "/" + key2));
            }
            Map<String, String> msrMap = new HashMap<>(1);
            if (StringUtils.isNotBlank(bdcdyh) && MapUtils.isNotEmpty(htbaMap)) {
                msrMap.put("value", htbaMap.get(bdcdyh));
            }
            fwhsInfo.put("msr", msrMap);
        }
    }

    /**
     * @param bdcdyhlist
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcXmDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 查询BDCDYH 现势产权
     */
    @PostMapping("/xmxxbybdcdyh")
    @ResponseBody
    public List<BdcXmDO> listBdcXmWithBdcdyh(@RequestParam(name = "bdcdyhlist") List<String> bdcdyhlist) {
        return bdcXmFeignService.listBdcXmXscq(bdcdyhlist);
    }

    /**
     * 用于校验外联土地证是否存在限制状态
     *
     * @param xmid          项目ID
     * @param processDefKey 工作流定义ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @GetMapping("/queryxzQl")
    public boolean queryxzQl(@RequestParam(value = "xmid") String xmid, @RequestParam(value = "processDefKey") String processDefKey) {
        Preconditions.checkArgument(StringUtils.isNotBlank(processDefKey), "未获取到流程定义ID信息。");
        return this.bdcGzyzFeignService.checkWltdzXzQl(xmid, processDefKey);
    }

    /**
     * 用于校验外联土地证是否存在限制状态
     *
     * @param xmids         项目ID集合
     * @param processDefKey 工作流定义ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @PostMapping("/plQueryXzQl")
    public boolean plQueryXzQl(@RequestBody List<String> xmids, @RequestParam(value = "processDefKey") String processDefKey) {
        Preconditions.checkArgument(CollectionUtils.isNotEmpty(xmids), "未获取到项目ID信息");
        Preconditions.checkArgument(StringUtils.isNotBlank(processDefKey), "未获取到流程定义ID信息。");
        for (String xmid : xmids) {
            if (this.bdcGzyzFeignService.checkWltdzXzQl(xmid, processDefKey)) {
                return true;
            }
        }
        return false;
    }

    @ResponseBody
    @GetMapping("/yspz")
    public Object queryLpbYspz() {
        return lpbCololrConfig.getColorConfigStatus();
    }


    @ResponseBody
    @GetMapping("/hssl")
    public void changeHssl(Integer hssl) {
        if (Objects.nonNull(hssl)) {
            //存到redis中以用户名为key
            String userName = userManagerUtils.getCurrentUserName();
            redisUtils.deleteKey(userName + "_hssl_redis");
            redisUtils.addStringValue(userName + "_hssl_redis", hssl.toString());
        }
    }
}

