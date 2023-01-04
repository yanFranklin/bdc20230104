package cn.gtmap.realestate.register.ui.web.rest.xxbl;

import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskClient;
import cn.gtmap.gtc.workflow.domain.manage.CategoryProcessDto;
import cn.gtmap.gtc.workflow.domain.manage.ProcessDefData;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmLsgxDO;
import cn.gtmap.realestate.common.core.domain.BdcYgDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXztzPzDO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzYzsjDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.EntityNotFoundException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlBdcdyhQO;
import cn.gtmap.realestate.common.core.qo.init.BdcCfQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.qo.register.BdcHfQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlXztzPzFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.AcceptBdcdyFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcLsgxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcDbxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcXxblFeignService;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.matcher.FlowableNodeClientMatcher;
import cn.gtmap.realestate.common.util.BdcdyhToolUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.RedisUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.common.util.configuration.BdcConfigUtils;
import cn.gtmap.realestate.register.ui.core.vo.BdcBlXnbdcdyhVO;
import cn.gtmap.realestate.register.ui.core.vo.BdcBllcVO;
import cn.gtmap.realestate.register.ui.util.TzmUtils;
import cn.gtmap.realestate.register.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 * 不动产信息补录选择不动产信息服务接口
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version v1.0, 2019/12/19 16:00
 */
@RestController
@RequestMapping("/rest/v1.0/blxx/blxz")
public class BdcBlxzController extends BaseController {
    private final static String CLASS_NAME = BdcBlxzController.class.getName();

    /** 信息补录流程信息补录页面展示流程缓存Redis */
    private final static String REDIS_KEY_BLZSLC = "REDIS_KEY_BLZSLC";

    @Autowired
    private BdcSlXztzPzFeignService bdcSlXztzPzFeignService;
    @Autowired
    private AcceptBdcdyFeignService acceptBdcdyFeignService;
    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    private BdcLsgxFeignService bdcLsgxFeignService;
    @Autowired
    private ProcessTaskClient processTaskClient;
    @Autowired
    private BdcQllxFeignService bdcQllxFeignService;
    @Autowired
    private FlowableNodeClientMatcher flowableNodeClient;
    @Autowired
    private RedisUtils redisUtils;
    /**
     * 补录数据项目服务
     */
    @Autowired
    private BdcXxblFeignService bdcXxblFeignService;
    @Autowired
    private BdcDbxxFeignService bdcDbxxFeignService;
    /**
     * 补录是否保留生成虚拟号功能（true：开启配置，false：关闭配置，默认关闭）
     */
    @Value("${xxbl.scxnbdcdyh:false}")
    private boolean scxnbdcdyh;

    @Value("#{'${xxbl.xnbdcdyhtslckeys: }'.split(',')}")
    private List<String> tslckeys;

    /**
     * 补录买卖预告登记的登记小类
     */
    @Value("${xxblMmygdj.djxl:7000403,7000803}")
    private String xxblMmygdj;


    /**
     * 补录修改流程地址
     */
    @Value("${xxbl.modifyKey: }")
    private String modifyKey;

    @Value("#{'${xxbl.modify.gzldyid:}'.split(',')}")
    private List<String> xxblXgGzldyidList;


    /**
     * 不显示产权证号的流程
     */
    @Value("#{'${xxbl.showcqzh: }'.split(',')}")
    private List<String> gzldyids;
    @Value("${xxblMmygdj.xgnrglxs:}")
    private String xgnrglxs;
    /*
     * 信息补录选择界面产权证号是否必填
     * */
    @Value("#{'${xxbl.cqzhfbt.gzldyid:}'.split(',')}")
    private List<String> cqzhFbtLcdyidList;
    @Value("#{'${xxbl.zxqlGzldyid: }'.split(',')}")
    private List<String> zxqlGzldyidList;

    /**
     * 补录流程页面中的参数赋值
     * type，gzlslid 和 xmid
     *
     * @param gzlslid 工作流 ID
     * @return
     */
    @GetMapping("/param")
    public BdcBllcVO queryLcParam(@RequestParam("gzlslid") String gzlslid, @RequestParam("taskid") String taskid) {
        if (StringUtils.isAnyBlank(gzlslid)) {
            throw new MissingArgumentException("未传入有效参数，gzlslid 和 taskid 不能为空");
        }
        List<BdcXmDTO> bdcXmDTOS = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        if (CollectionUtils.isEmpty(bdcXmDTOS)) {
            throw new EntityNotFoundException("未查询到项目信息");
        }
        BdcBllcVO bdcBllcVO = new BdcBllcVO();
        int lclx = bdcXmFeignService.makeSureBdcXmLx(gzlslid);
        if(CommonConstantUtils.LCLX_PL.equals(lclx)){
            bdcBllcVO.setLclx(CommonConstantUtils.LCLX_PL_STR);
        }
        bdcBllcVO.setXgnrglxs(xgnrglxs);
        // 只有首节点才有修改编辑权限
        if (StringUtils.isNotBlank(taskid)) {
            boolean startUserTask = flowableNodeClient.isStartUserTask(taskid);
            if (startUserTask) {
                bdcBllcVO.setType("update");
            } else {
                bdcBllcVO.setType("check");
            }
        } else {
            bdcBllcVO.setType("check");
        }
        if (StringUtils.equals(bdcXmDTOS.get(0).getGzldyid(), modifyKey) || (CollectionUtils.isNotEmpty(xxblXgGzldyidList) && xxblXgGzldyidList.contains(bdcXmDTOS.get(0).getGzldyid()))) {
            List<BdcXmLsgxDO> bdcXmLsgxDOS = bdcLsgxFeignService.listXmLsgxBySlid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcXmLsgxDOS)) {
                BdcXmQO bdcXmQO = new BdcXmQO();
                bdcXmQO.setXmid(bdcXmLsgxDOS.get(0).getYxmid());
                List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(bdcXmQO);
                bdcBllcVO.generateXmxx(bdcXmDOS.get(0));
                return bdcBllcVO;
            }
        } else {
            bdcBllcVO.setProcessInsId(bdcXmDTOS.get(0).getGzlslid());
            bdcBllcVO.setXmid(bdcXmDTOS.get(0).getXmid());
            return bdcBllcVO;
        }
        return bdcBllcVO;
    }

    /**
     * @param gzlslid gzlslid
     * @return {BdcXmDTO} 项目对象
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 判断项目是否生成权利 <br>
     * 生成返回当前项目的 gzlslid 和 xmid <br>
     * 不生成返回上一手项目的 gzlslid 和 xmid <br>
     */
    @GetMapping("/sfscql")
    public BdcXmDO querySfscQl(@RequestParam("gzlslid") String gzlslid, @RequestParam(value = "xmid", required = false) String xmid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("gzlslid");
        }
        if (StringUtils.isBlank(xmid)) {
            List<BdcXmDTO> bdcXmDTOS = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
            if (CollectionUtils.isEmpty(bdcXmDTOS)) {
                throw new AppException("未查询到对应的项目信息: gzlslid 为" + gzlslid);
            }
            xmid = bdcXmDTOS.get(0).getXmid();
        }
        return bdcXxblFeignService.querySfscQl(xmid);
    }

    /**
     * 获取选择台账配置信息
     *
     * @param gzldyid 工作流定义 ID
     * @return {BdcSlXztzPzDO} 选择台账配置 DO 对象
     */
    @GetMapping("/xztzpz")
    public Object queryBdcSlXztzPzDOByGzldyid(@RequestParam("gzldyid") String gzldyid) {
        if (StringUtils.isBlank(gzldyid)) {
            throw new MissingArgumentException("未传入有效参数，gzldyid 不能为空");
        }
        return bdcSlXztzPzFeignService.queryBdcSlXztzPzDTOByGzldyid(gzldyid);
    }

    /**
     * 选择不动产单元号查询台账分页接口
     *
     * @param pageable      分页参数
     * @param bdcSlBdcdyhQO 受理单元号查询对象
     * @return {List<Map>}
     */
    @GetMapping("/bdcdyh")
    public Object listBdcdyhByPageJson(@LayuiPageable Pageable pageable, BdcSlBdcdyhQO bdcSlBdcdyhQO) {
        if (StringUtils.isNotBlank(bdcSlBdcdyhQO.getBdclx())) {
            TzmUtils.getCxByBdclx(bdcSlBdcdyhQO);
        }
        if (bdcSlBdcdyhQO.getDyhcxlx() != null) {
            if (StringUtils.isNotBlank(bdcSlBdcdyhQO.getZdtzm())) {
                TzmUtils.getQlxzAndZdtzm(bdcSlBdcdyhQO);
            }
        }
        return acceptBdcdyFeignService.listBdcdyhByPageOrList(pageable,JSON.toJSONString(bdcSlBdcdyhQO),true);
    }

    /**
     * 选择 查询不动产项目(证书) 分页接口
     *
     * @param pageable 分页参数
     * @param bdcQlQO  权利查询对象
     * @return {List<Map>}
     */
    @GetMapping("/bdcqz")
    public Object listXmByPageJson(@LayuiPageable Pageable pageable, BdcQlQO bdcQlQO) {
        bdcQlQO.setQxdmList(Container.getBean(BdcConfigUtils.class).qxdmFilter("xxbl","",""));
        bdcQlQO.setQszt(CommonConstantUtils.QSZT_VALID);
        // 处理英文括号
        bdcQlQO.setBdcqzh(StringToolUtils.replaceBracket(bdcQlQO.getBdcqzh()));
        String bdcQlQoStr = JSON.toJSONString(bdcQlQO);
        return addLayUiCode(bdcXmFeignService.bdcQlPageByPageJson(pageable, bdcQlQoStr));
    }

    /**
     * 根据业务分类查询流程列表
     *
     * @param apptype 应用场景类型
     * @return {List} 返回 CategoryProcessDto 类型的集合
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/process")
    public Object listCategoryProcess(@RequestParam(value = "apptype", required = false)String apptype) {
        List<CategoryProcessDto> processDtoList = processTaskClient.listAllCategoryProcess();

        if("process".equals(apptype) && CollectionUtils.isNotEmpty(processDtoList)) {
            // 如果是信息补录流程，支持办理流程可配置，例如 需求41442
            List<String> processIds = this.getShowProcess();
            if(CollectionUtils.isEmpty(processIds)) {
                // 如果没配置可展示流程，默认展示所有
            } else {
                // 如果配置了可展示流程，则过滤出指定流程
                List<CategoryProcessDto> result = new ArrayList<>();

                Map<String, String> showProcessIdMap = new HashMap<>();
                for(String processId : processIds) {
                    showProcessIdMap.put(processId, processId);
                }

                // 匹配出每个分类下可展示流程
                for(CategoryProcessDto category : processDtoList) {
                    List<ProcessDefData> processes = category.getProcessList();
                    if(CollectionUtils.isNotEmpty(processes)) {
                        List<ProcessDefData> processDefData = new ArrayList<>();
                        for(ProcessDefData pro : processes) {
                            if(showProcessIdMap.containsKey(pro.getId())) {
                                processDefData.add(pro);
                            }
                        }
                        // 复制分类信息
                        if(CollectionUtils.isNotEmpty(processDefData)) {
                            CategoryProcessDto newCategory = new CategoryProcessDto();
                            newCategory.setId(category.getId());
                            newCategory.setName(category.getName());
                            newCategory.setProcessList(processDefData);
                            result.add(newCategory);
                        }
                    }
                }
                return result;
            }
        }
        return processDtoList;
    }

    /**
     * 保存信息补录流程页面可展示的流程ID
     * @param processIds 流程ID
     */
    @PostMapping("/showprocess")
    public void saveShowProcess(@RequestBody List<String> processIds) {
        if(CollectionUtils.isEmpty(processIds)) {
            throw new MissingArgumentException("未定义要保存的流程id");
        }
        redisUtils.addStringValue(REDIS_KEY_BLZSLC, JSON.toJSONString(processIds));
    }

    /**
     * 获取信息补录流程页面可展示的流程ID
     * @return {List} 流程ID
     */
    @GetMapping("/showprocess")
    public List<String> getShowProcess() {
        String data = redisUtils.getStringValue(REDIS_KEY_BLZSLC);
        if(StringUtils.isNotBlank(data)) {
            return JSON.parseArray(data, String.class);
        }
        return Collections.emptyList();
    }

    /**
     * 判断是否显示生成虚拟不动产单元号按钮
     *
     * @return boolean 是否
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/xnbdcdyh")
    public BdcBlXnbdcdyhVO queryScXnbdcdyh() {
        BdcBlXnbdcdyhVO bdcBlXnbdcdyhVO = new BdcBlXnbdcdyhVO();
        bdcBlXnbdcdyhVO.setScxnbdcdyh(scxnbdcdyh);
        bdcBlXnbdcdyhVO.setTslckeys(tslckeys);
        return bdcBlXnbdcdyhVO;
    }

    /**
     * 根据 gzldymc 获取 gzldyid
     *
     * @param gzldymc 工作流名称
     * @return {String} gzldyid，获取不到对应的 gzldyid 则返回 ''
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/gzldyid")
    public String queryGzldyidBygzldymc(@RequestParam("gzldymc") String gzldymc) throws UnsupportedEncodingException {
        if (StringUtils.isBlank(gzldymc)) {
            throw new MissingArgumentException("缺少流程名称");
        }
        List<CategoryProcessDto> categoryProcessDtos = processTaskClient.listAllCategoryProcess();
        return queryProcessDefKey(URLDecoder.decode(gzldymc, "UTF-8"), categoryProcessDtos);
    }

    /**
     * 根据 工作流定义名称获取 工作流定义key
     *
     * @param gzldymc             工作流定义名称
     * @param categoryProcessDtos 返回
     * @return {String} 工作流定义key
     */
    private String queryProcessDefKey(String gzldymc, List<CategoryProcessDto> categoryProcessDtos) {
        for (CategoryProcessDto categoryProcessDto : categoryProcessDtos) {
            List<ProcessDefData> processList = categoryProcessDto.getProcessList();
            if (CollectionUtils.isEmpty(processList) && CollectionUtils.isNotEmpty(categoryProcessDto.getChildren())) {
                String processDefKey = queryProcessDefKey(gzldymc, categoryProcessDto.getChildren());
                if (StringUtils.isNotBlank(processDefKey)) {
                    return processDefKey;
                }
            } else {
                for (ProcessDefData processDefData : processList) {
                    if (StringUtils.equals(processDefData.getName(), gzldymc)) {
                        return processDefData.getProcessDefKey();
                    }
                }
            }
        }
        return StringUtils.EMPTY;
    }

    /**
     * @param pageable 分页对象
     * @param bdcCfQO  查封查询对象
     * @return 分页结果
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 查询不动产查封
     */
    @GetMapping("/cf")
    public Object listCfByPageJson(@LayuiPageable Pageable pageable, BdcCfQO bdcCfQO) {
        bdcCfQO.setQxdmList(Container.getBean(BdcConfigUtils.class).qxdmFilter("xxbl","",""));
        String bdcCfQoStr = JSON.toJSONString(bdcCfQO);
        return addLayUiCode(bdcXmFeignService.bdcCfxxPageByPageJson(pageable, bdcCfQoStr));
    }

    /**
     * @param gzldyid gzldyid
     * @return {boolean} true:不显示，false：显示
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 根据配置判断在补录新增页面是否显示不动产权输入框 <br>
     */
    @GetMapping("/showcqzh")
    public boolean queryIsShowcqzh(@RequestParam(value = "gzldyid") String gzldyid) {
        if (StringUtils.isBlank(gzldyid)) {
            LOGGER.warn("{}: 查询是否在补录新增页面是否显示不动产权输入框中止，原因未传入 gzldyid", CLASS_NAME);
            return false;
        }
        if (CollectionUtils.isEmpty(gzldyids)) {
            return false;
        }
        return gzldyids.contains(gzldyid);
    }

    /**
     * @param xmid xmid
     * @return {boolean} true:可以，false：不可以
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 判断是否可以关联数据 <br>
     * 首次产权登记流程 和 预购商品房预告登记流程 不能关联数据
     */
    @GetMapping("/isgl")
    public boolean isGl(@RequestParam(value = "xmid") String xmid) {
        if (StringUtils.isBlank(xmid)) {
            LOGGER.warn("{}: 判断是否可以关联数据中止，原因未传入 xmid", CLASS_NAME);
            return false;
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setXmid(xmid);
        List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isEmpty(bdcXmDOS) && bdcXmDOS.get(0) != null) {
            throw new AppException("未查询到对应的项目信息！");
        }
        BdcXmDO bdcXmDO = bdcXmDOS.get(0);
        // 首次产权登记流程
        if (CommonConstantUtils.DJLX_SCDJ_DM.equals(bdcXmDO.getDjlx())) {
            //非限制类权利
            if (bdcXmDO.getQllx() != null && !ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, bdcXmDO.getQllx())) {
                return false;
            }
        }

        // 预购商品房预告登记流程
        if (bdcXmDO.getQllx() != null && bdcXmDO.getQllx().equals(96) && xxblMmygdj.indexOf(bdcXmDO.getDjxl()) == -1) {
            BdcQl bdcQl = bdcQllxFeignService.queryQlxx(xmid);
            if (bdcQl instanceof BdcYgDO) {
                Integer ygdjzl = ((BdcYgDO) bdcQl).getYgdjzl();
                return ygdjzl == null || (ygdjzl != 1 && ygdjzl != 2);
            }
        }

        // 不动产买卖预告登记应该能关联产权
        if (xxblMmygdj.indexOf(bdcXmDO.getDjxl()) != -1) {
            return true;
        }

        return true;
    }

    /**
     * 注销验证<br>
     * 根据 xztz_pz 表中的配置判断当前流程是否可以办理此注销流程
     *
     * @param gzldyid 工作流定义 ID
     * @return {BdcSlXztzPzDO} 选择台账配置 DO 对象
     */
    @PostMapping("/zx")
    public Object zxyz(@RequestParam("gzldyid") String gzldyid, @RequestBody List<BdcGzYzsjDTO> bdcGzYzsjDTOS) {
        if (StringUtils.isBlank(gzldyid) && CollectionUtils.isNotEmpty(bdcGzYzsjDTOS)) {
            throw new MissingArgumentException("注销验证未传入有效参数");
        }
        BdcSlXztzPzDO bdcSlXztzPzDO = bdcSlXztzPzFeignService.queryBdcSlXztzPzDOByGzldyid(gzldyid);
        if (bdcSlXztzPzDO == null) {
            throw new AppException("此流程未配置选择台账");
        }
        LOGGER.info("判断当前流程是否可以注销 gzldyid:{}, 对应数据：{}", gzldyid, bdcSlXztzPzDO);
        List<String> bdclxs = Arrays.asList(bdcSlXztzPzDO.getBdclx().split(","));
        List<String> dzwtzms = Arrays.asList(bdcSlXztzPzDO.getDzwtzm().split(","));
        List<String> qllxs = Arrays.asList(bdcSlXztzPzDO.getQllx().split(","));

        for (BdcGzYzsjDTO bdcGzYzsjDTO : bdcGzYzsjDTOS) {
            String dzwtzm = BdcdyhToolUtils.getDzwTzm(bdcGzYzsjDTO.getBdcdyh());
            BdcXmQO xmQO = new BdcXmQO();
            xmQO.setXmid(bdcGzYzsjDTO.getXmid());
            List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(xmQO);
            if (CollectionUtils.isEmpty(bdcXmDOS)) {
                throw new AppException("未查询到对应项目信息");
            }

            BdcXmDO bdcXmDO = bdcXmDOS.get(0);
            boolean dzw = CollectionUtils.isEmpty(dzwtzms) || dzwtzms.contains(dzwtzm);
            boolean bdclx = CollectionUtils.isEmpty(bdclxs) || bdclxs.contains(bdcXmDO.getBdclx() == null ? "" : bdcXmDO.getBdclx().toString());
            boolean qllx = CollectionUtils.isEmpty(qllxs) || qllxs.contains(bdcXmDO.getQllx() == null ? "" : bdcXmDO.getQllx().toString());
            LOGGER.info("当前流程 dzwtzm :{}, bdclx: {}, qllx:{} ", dzwtzm, bdcXmDO.getBdclx(), bdcXmDO.getQllx());
            if (!(dzw && bdclx && qllx)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 权属状态恢复验证<br>
     * 验证通过直接恢复
     */
    @GetMapping("/qszt/hfyz")
    public String hfyz(@RequestParam("xmid") String xmid) {
        if (StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException("xmid");
        }

        BdcXmQO xmQO = new BdcXmQO();
        xmQO.setXmid(xmid);
        List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(xmQO);
        if (CollectionUtils.isEmpty(bdcXmDOS)) {
            throw new AppException("未查询到项目数据！");
        }
        BdcXmDO bdcXmDO = bdcXmDOS.get(0);
        return yzHfQlxx(bdcXmDO);
    }

    /**
     * 权属状态恢复验证<br>
     * 验证通过直接恢复
     */
    @PostMapping("/qszt/hfql")
    public String hfyz(@RequestBody BdcHfQO bdcHfQO) {
        if (StringUtils.isBlank(bdcHfQO.getXmid())) {
            throw new MissingArgumentException("xmid");
        }

        BdcXmQO xmQO = new BdcXmQO();
        xmQO.setXmid(bdcHfQO.getXmid());
        List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(xmQO);
        if (CollectionUtils.isEmpty(bdcXmDOS)) {
            throw new AppException("未查询到项目数据！");
        }
        BdcXmDO bdcXmDO = bdcXmDOS.get(0);

        String yzxx = yzHfQlxx(bdcXmDO);
        if (!StringUtils.equals(yzxx, "success")) {
            return yzxx;
        }
        bdcHfQO.setQszt(CommonConstantUtils.QSZT_VALID);
        try {
            bdcDbxxFeignService.hfQl(bdcHfQO);
        } catch (Exception e) {
            LOGGER.error("恢复权利信息异常：{}", e.getMessage());
            return "恢复权利信息异常";
        }

        return "success";
    }

    /**
    * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
    * @param gzlslid 工作流实例ID
    * @return
    * @description 获取原权利项目
    **/
    @GetMapping("/getYqlxm")
    public Object getYqlxm(String gzlslid){
        if(StringUtils.isBlank(gzlslid)){
            throw new MissingArgumentException("gzlslid");
        }
        List<BdcXmLsgxDO> bdcXmLsgxDOS = bdcLsgxFeignService.listXmLsgxBySlid(gzlslid);
        if(CollectionUtils.isNotEmpty(bdcXmLsgxDOS)){
            BdcXmLsgxDO bdcXmLsgxDO = bdcXmLsgxDOS.get(0);
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setXmid(bdcXmLsgxDO.getYxmid());
            List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isNotEmpty(bdcXmDOS)) {
                return bdcXmDOS.get(0);
            }
        }
        return null;
    }

    /*
     * 获取信息补录的一些配置
     * */
    @GetMapping("/config")
    public Object queryXxblConfig(String gzldyid) {
        Map configMap = new HashMap(3);
        configMap.put("cqzhbt", true);
        if (CollectionUtils.isNotEmpty(cqzhFbtLcdyidList) && cqzhFbtLcdyidList.contains(gzldyid)) {
            configMap.put("cqzhbt", false);
        }
        return configMap;
    }

    @GetMapping("/getZxlc")
    public Object getZxlc(@RequestParam(value = "apptype", required = false)String apptype) {
        List<CategoryProcessDto> processDtoList = processTaskClient.listAllCategoryProcess();
        if(CollectionUtils.isNotEmpty(processDtoList)) {
            List<ProcessDefData> processDefDatas = new ArrayList<>();
            // 匹配出每个分类下可展示流程
            for(CategoryProcessDto category : processDtoList) {
                List<ProcessDefData> processes = category.getProcessList();
                if(CollectionUtils.isNotEmpty(processes)) {
                    for(ProcessDefData pro : processes) {
                        if(CollectionUtils.isNotEmpty(zxqlGzldyidList) && zxqlGzldyidList.contains(pro.getProcessDefKey())) {
                            processDefDatas.add(pro);
                        }
                    }
                }
            }
            return processDefDatas;
        }
        return null;
    }

    private String yzHfQlxx(BdcXmDO bdcXmDO) {
        if (!(CommonConstantUtils.XMLY_CLGD_DM.equals(bdcXmDO.getXmly()) || CommonConstantUtils.XMLY_CLBDC_DM.equals(bdcXmDO.getXmly()))) {
            return "不支持恢复非存量数据！";
        }
        if (!CommonConstantUtils.QSZT_HISTORY.equals(bdcXmDO.getQszt())) {
            return "仅支持恢复权属状态为历史的数据！";
        }
        if (bdcXmDO.getQllx() != null && !ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, bdcXmDO.getQllx())) {
            if (StringUtils.isBlank(bdcXmDO.getBdcqzh())) {
                return "项目不动产权证号数据为空！";
            }
            // 不动产数据(非存量过渡数据) 产权证号标识
            if (bdcXmDO.getBdcqzh().contains("不动产")) {
                return "仅支持房产证和土地证的恢复，当前产权证号包含「不动产」！";
            }
            // 根据 bdcdyh 查询是否已经有现实产权
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setQszt(CommonConstantUtils.QSZT_VALID);
            if (CommonConstantUtils.BDCLX_TD_DM.equals(bdcXmDO.getBdclx().toString())) {
                bdcXmQO.setQllxs(Arrays.asList(1, 2, 3, 5, 7));
            } else {
                bdcXmQO.setQllxs(Arrays.asList(4, 6, 8));
            }
            bdcXmQO.setBdcdyh(bdcXmDO.getBdcdyh());
            List<BdcXmDO> results = bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isNotEmpty(results)) {
                return "不动产单元号：" + bdcXmDO.getBdcdyh() + "已经存在现势产权";
            }
            return "success";
        } else {
            return "仅支持产权的恢复";
        }
    }


}
