package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.gtc.clients.RegionManagerClient;
import cn.gtmap.gtc.sso.domain.dto.RegionDto;
import cn.gtmap.realestate.accept.ui.utils.ExportUtils;
import cn.gtmap.realestate.accept.ui.utils.StreamEx;
import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.accept.ui.web.utils.BdcZdController;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.accept.*;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;
import cn.gtmap.realestate.common.core.domain.building.FwYchsDO;
import cn.gtmap.realestate.common.core.domain.building.ZdDjdcbDO;
import cn.gtmap.realestate.common.core.dto.BdcXmCfDTO;
import cn.gtmap.realestate.common.core.dto.accept.*;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcSyqxPlDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcdySumDTO;
import cn.gtmap.realestate.common.core.dto.register.TdSyqJssjDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlQlxxymQO;
import cn.gtmap.realestate.common.core.qo.accept.InitBdcSlQlxxymQO;
import cn.gtmap.realestate.common.core.qo.init.*;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcCfxxQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcDysdQO;
import cn.gtmap.realestate.common.core.qo.register.BdcBdcdyQO;
import cn.gtmap.realestate.common.core.qo.register.BdcCfjgQO;
import cn.gtmap.realestate.common.core.qo.register.BdcCqQO;
import cn.gtmap.realestate.common.core.qo.register.BdcFdcq3QO;
import cn.gtmap.realestate.common.core.service.feign.accept.*;
import cn.gtmap.realestate.common.core.service.feign.building.*;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsxmFeignService;
import cn.gtmap.realestate.common.core.service.feign.engine.BdcGzlwFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.*;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcCfxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcSdqghFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.*;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcSlPlxgBatchXxVO;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcSlPlxgXxVO;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcBdcdySortVO;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcBdcdyVO;
import cn.gtmap.realestate.common.util.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import jxl.write.WriteException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Table;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 2019/1/17
 * @description 受理页面权利相关控制层
 */
@Controller
@RequestMapping("/slym/ql")
@Slf4j
public class SlymQlController extends BaseController {
    /**
     * 实体属性或对应方法名称常量定义
     */
    private static final String DJXL = "djxl";
    private static final String QLLX = "qllx";

    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    private BdcQllxFeignService bdcQllxFeignService;
    @Autowired
    private BdcZdFeignService bdcZdFeignService;
    @Autowired
    private BdcEntityFeignService bdcEntityFeignService;
    @Autowired
    private BdcRyzdFeignService bdcRyzdFeignService;
    @Autowired
    BdcLsgxFeignService bdcLsgxFeignService;
    @Autowired
    BdcBdcdyFeignService bdcBdcdyFeignService;
    @Autowired
    BdcQlrFeignService bdcQlrFeignService;
    @Autowired
    BdcZsInitFeignService bdcZsInitFeignService;
    @Autowired
    BdcSlJyxxFeignService bdcSlJyxxFeignService;
    @Autowired
    BdcZdController bdcZdController;
    @Autowired
    BdcXmfbFeignService bdcXmfbFeignService;
    @Autowired
    BdcDjbxxFeignService bdcDjbxxFeignService;
    @Autowired
    SlymQlrController slymQlrController;
    @Autowired
    BdcLzrFeignService bdcLzrFeignService;
    @Autowired
    BdcPpFeignService bdcPpFeignService;
    @Autowired
    BdcDjxlPzFeignService bdcDjxlPzFeignService;
    @Autowired
    BdcSdqghFeignService bdcSdqghFeignService;
    @Autowired
    BdcCfxxFeignService bdcCfxxFeignService;
    @Autowired
    cn.gtmap.realestate.common.core.service.feign.accept.BdcQllxFeignService acceprBdcQllxFeignService;
    @Autowired
    BdcGzdjFeignService bdcGzdjFeignService;
    @Autowired
    BdcSlXzxxFeignService bdcSlXzxxFeignService;
    @Autowired
    BdcZsxmFeignService bdcZsxmFeignService;
    @Autowired
    RegionManagerClient regionManagerClient;
    @Autowired
    FwHsFeignService fwHsFeignService;
    @Autowired
    FwYcHsFeignService fwYcHsFeignService;
    @Autowired
    ZdFeignService zdFeignService;
    @Autowired
    FwLjzFeginService fwLjzFeginService;
    @Autowired
    BdcGzlwFeignService engineBdcGzlwFeignService;
    @Autowired
    BdcSlWxzjFeignService bdcSlWxzjFeignService;

    @Autowired
    BdcdyFeignService bdcdyFeignService;

    @Autowired
    BdcSlXmFeignService bdcSlXmFeignService;

    @Autowired
    BdcZsFeignService bdcZsFeignService;

    @Autowired
    BdcSlFwxxFeignService bdcSlFwxxFeignService;

    /**
     * 预抵押登记小类
     */
    @Value("#{'${accept-ui.ydydjxl:}'.split(',')}")
    private List<String> ydydjxl;

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description tab页后面根据注销登记小类加（注销）两个字
     * @date : 2022/12/7 11:36
     */
    @Value("#{'${accept-ui.zxdjxl:}'.split(',')}")
    private List<String> zxdjxl;
    /**
     * 原产权证号 最多多少个不加"等"
     */
    @Value("#{'${slym.ycqzh.maxcount:1}'}")
    private Integer maxCount;

    /**
     * 需要显示用途统计信息的流程
     */
    @Value("#{'${slym.fwyttj:}'.split(',')}")
    protected List<String> fwyttjDyidList;

    @Value("#{'${tscl.gzldyid:}'.split(',')}")
    protected List<String> gzldyidList;

    @Value("${lzrxx.bzsdjxl:}")
    private String lzrBzsDjxl;

    @Value("${xstdqlmj.gzldyids:}")
    private String tdzQlmjGzldyids;

    /*
     * 需要查询水电气信息的登记小类
     * */
    @Value("#{'${slym.sdqgh.djxl:}'.split(',')}")
    private List<String> sdqghDjxlList;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 自动更新使用期限
     */
    @Value("${syqx.zdgx:false}")
    private Boolean updateSyqx;

    /**
     *
     * @description 是否按照天干进行排序，默认不按照
     */
    @Value("${dyxx.tgpx:false}")
    private Boolean tgpx;

    /**
     * 配置工作流id，通过此配置判断是否显示提示信息
     */
    @Value("${slxx.gzldyid:}")
    private String gzldyid;

    /**
     * 淮安地区外联证书展示内容,通过此配置判断是否显示证书权属状态
     */
    @Value("${wlzsnr.showqszt:true}")
    private String wlzsnr;

    /**
     *
     * @description 需要查询维修基金信息的gzldyid
     */
    @Value("#{'${slym.wxjj.gzldyid:}'.split(',')}")
    private List<String> wxjjGzldyid;

    /**
     *
     * @description 判断层高不足2.2米的不保存建筑面积配置的房屋类型（bdc_zd_fwlx）
     */
    @Value("#{'${qlxx.fwlx:}'.split(',')}")
    private List<String> fwlxList;

    /**
     *
     * @description 判断层高不足2.2米的不保存建筑面积配置的房屋用途（bdc_zd_fwyt）
     */
    @Value("#{'${qlxx.fwyt:}'.split(',')}")
    private List<String> fwytList;

    /**
     *
     * @description 判断不保存建筑面积配置的层高
     */
    @Value("${qlxx.cg:2.2}")
    private Double qlCg;

    /**
     * @param bdcSlQlxxymQO
     * @return 不动产受理权利页面
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据工作流实例ID获取权利信息(用于组合流程)
     */
    @ResponseBody
    @GetMapping("/zhlc")
    public Object listZhQlxx(BdcSlQlxxymQO bdcSlQlxxymQO) {
        Map<String, List<Map>> zdMap = new HashMap<>();
        List<BdcSlQlxxymDTO> bdcSlQlxxymDTOList = new ArrayList<>();
        try {
            zdMap = bdcZdFeignService.listBdcZd();
        } catch (Exception e) {
            LOGGER.error("字典项服务获取失败");
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(bdcSlQlxxymQO.getProcessInsId());
        //获取修正流程项目id
        if (bdcSlQlxxymQO.getXzlc()) {
            //修正流程不用gzlslid查询
            bdcXmQO.setGzlslid("");
            bdcXmQO.setXmid(bdcSlQlxxymQO.getXmid());
        }
        List<BdcXmDO> bdcXmList = bdcXmFeignService.listBdcXm(bdcXmQO);
        int xmlx = bdcXmFeignService.makeSureBdcXmLx(bdcSlQlxxymQO.getProcessInsId());
        if (bdcSlQlxxymQO.getXzlc()) {
            //修正流程打开的受理页面 xmlx 默认为单个的流程，组合流程也是根据xmid单独打开一个项目的信息
            xmlx = CommonConstantUtils.LCLX_PT;
        }
        if (CollectionUtils.isNotEmpty(bdcXmList)) {
            /* 根据xmid排序 */
            bdcXmList.sort(Comparator.comparing(BdcXmDO::getXmid));
            for (BdcXmDO bdcXmDO : bdcXmList) {

                BdcSlQlxxymDTO bdcSlQlxxymDTO = new BdcSlQlxxymDTO();
                bdcSlQlxxymDTO.setTableName(getTableName(bdcXmDO.getXmid(), Boolean.valueOf(bdcSlQlxxymQO.getZxlc())));

                BdcXmFbQO bdcXmFbQO = new BdcXmFbQO();
                bdcXmFbQO.setXmid(bdcXmDO.getXmid());
                List<BdcXmFbDO> bdcXmFbDOList = bdcXmfbFeignService.listBdcXmFb(bdcXmFbQO);

                this.setTsxx(bdcXmDO,bdcSlQlxxymDTO);
                BdcQl bdcQl = null;
                if (bdcSlQlxxymQO.getSfcxql()) {
                    bdcQl = bdcQllxFeignService.queryQlxx(bdcXmDO.getXmid());
                    BdcQl bdcYql = bdcQllxFeignService.queryBdcYqlxx(bdcXmDO.getXmid());
                    if (bdcQl == null) {
                        bdcQl = bdcYql;
                    }
                    if (bdcQl != null) {
                        String name = bdcQl.getClass().getAnnotation(Table.class).name();
                        bdcSlQlxxymDTO.setTableName(StringUtils.lowerCase(name));
                        bdcSlQlxxymDTO.setBdcQl(bdcQl);
                        //预抵押权利比较特殊需根据预告登记种类判断
                        if (bdcQl instanceof BdcYgDO) {
                            BdcYgDO bdcYgDO = (BdcYgDO) bdcQl;
                            if (bdcYgDO.getYgdjzl() != null && ArrayUtils.contains(CommonConstantUtils.YG_YGDJZL_YDY_ARR, bdcYgDO.getYgdjzl())) {
                                bdcSlQlxxymDTO.setDydj(true);
                            }
                        }
                        //特殊逻辑处理
                        InitBdcSlQlxxymQO initBdcSlQlxxymQO =new InitBdcSlQlxxymQO();
                        initBdcSlQlxxymQO.setBdcXmDOList(Collections.singletonList(bdcXmDO));
                        initBdcSlQlxxymQO.setBdcQl(bdcQl);
                        initBdcSlQlxxymQO.setBdcSlQlxxymQO(bdcSlQlxxymQO);
                        initBdcSlQlxxymQO.setZdMap(zdMap);

                        initBdcSlQlxxymDTOTscl(initBdcSlQlxxymQO,bdcSlQlxxymDTO);
                        //抵押权
                        if (bdcQl instanceof BdcDyaqDO) {


                            //根据单元号查询对应的fdcq 的数据
                            if (bdcSlQlxxymQO.getCxbdcdyxsql()) {
                                BdcDyaqDO bdcDyaqDO = (BdcDyaqDO) bdcQl;
                                //不是纯土地的查房地产权信息
                                if (!Objects.equals(1, bdcDyaqDO.getDybdclx())) {
                                    BdcCqQO bdcCqQO = new BdcCqQO();
                                    bdcCqQO.setQsztList(Arrays.asList(CommonConstantUtils.QSZT_VALID, CommonConstantUtils.QSZT_TEMPORY));
                                    bdcCqQO.setBdcdyhList(Collections.singletonList(bdcXmDO.getBdcdyh()));
                                    List<Map> fdcqXmxxList = bdcBdcdyFeignService.listBdcFdcqXmxx(bdcCqQO);
                                    if (CollectionUtils.isNotEmpty(fdcqXmxxList)) {
                                        bdcSlQlxxymDTO.setCqxxMap(fdcqXmxxList.get(0));
                                    }else{
                                        //在建工程抵押房地产信息为空，查询预测户室的产权信息
                                        if(Objects.equals(4, bdcDyaqDO.getDybdclx()) && bdcSlQlxxymQO.getCxYchsCqxx()){
                                            List<SlymDyaCqxxDTO> ychsCqxx = getYchsCqxx(Collections.singletonList(bdcXmDO));
                                            if(CollectionUtils.isNotEmpty(ychsCqxx)){
                                                SlymDyaCqxxDTO slymDyaCqxxDTO = ychsCqxx.get(0);
                                                Map map = JSONObject.parseObject(JSONObject.toJSONString(slymDyaCqxxDTO), Map.class);
                                                bdcSlQlxxymDTO.setCqxxMap(map);
                                            }
                                        }else{
                                            bdcSlQlxxymDTO.setCqxxMap(new HashMap());
                                        }
                                    }
                                } else {
                                    //查询建设用地使用权数据
                                    BdcCqQO bdcCqQO = new BdcCqQO();
                                    bdcCqQO.setBdcdyhList(Collections.singletonList(bdcXmDO.getBdcdyh()));
                                    bdcCqQO.setQsztList(Arrays.asList(CommonConstantUtils.QSZT_VALID, CommonConstantUtils.QSZT_TEMPORY));
                                    List<Map> bdcJsydsyqDOList = bdcBdcdyFeignService.listBdcJsydsyqXmxx(bdcCqQO);
                                    if (CollectionUtils.isNotEmpty(bdcJsydsyqDOList)) {
                                        bdcSlQlxxymDTO.setJsydsyqMap(bdcJsydsyqDOList.get(0));
                                    }else{
                                        bdcSlQlxxymDTO.setJsydsyqMap(new HashMap());
                                    }
                                }
                            }
                            //抵押注销增加证明类型，如果印制号为空为电子证照，有值为纸质证明
                            if(StringUtils.equals(CommonConstantUtils.BOOL_TRUE,bdcSlQlxxymQO.getZxlc())){
                                getZmlx(bdcXmDO,bdcSlQlxxymDTO);
                            }
                        }
                        // 房地产权无分摊与独用土地面积时，获取其匹配土地证的分摊与独用面积
                        if (bdcQl instanceof BdcFdcqDO) {
                            addPpTdzTdqlmj(bdcQl, bdcXmDO, Boolean.valueOf(bdcSlQlxxymQO.getZxlc()));
                        }
                        //查封信息
                        if (bdcSlQlxxymQO.getCxCfxx() && bdcQl instanceof BdcCfDO) {
                            Integer dyhqllx = acceprBdcQllxFeignService.getQllxByBdcdyh(bdcXmDO.getBdcdyh(), "1");
                            bdcSlQlxxymDTO.setDyhqllx(StringToolUtils.convertBeanPropertyValueOfZd(dyhqllx, zdMap.get("qllx")));
                            List<SlymCfxxDTO> slymCfxxDTOS = this.listBdcCfxx(bdcSlQlxxymQO.getProcessInsId());
                            bdcSlQlxxymDTO.setSlymCfxxDTOList(slymCfxxDTOS);
                            List<BdcXmDO> cqXmList = bdcXmFeignService.listXscqXm(Collections.singletonList(bdcXmDO));
                            List<BdcCfDO> yCfList = new ArrayList<>();
                            if (bdcYql instanceof BdcCfDO) {
                                yCfList.add((BdcCfDO) bdcYql);
                            }
                            bdcSlQlxxymDTO.setCqXmList(this.combineYcfbh(cqXmList, yCfList, bdcXmList,CollectionUtils.isNotEmpty(bdcXmFbDOList) ?bdcXmFbDOList.get(0).getQjgldm():""));
                        }
                    } else {
                        throw new AppException("项目ID:" + bdcXmDO.getXmid() + "未查询到权利信息！");
                    }
                    //组织抵押登记证明号
                    //抵押登记证明号取上一手项目的bdcqzh+yxtcqzh
                    bdcXmQO = new BdcXmQO();
                    bdcXmQO.setXmid(bdcQl.getXmid());
                    List<BdcXmDO> ybdcXmList = bdcXmFeignService.listBdcXm(bdcXmQO);
                    Set<String> ycqzhList = new HashSet<>();
                    Set<String> dydjzmhList = new HashSet<>();
                    if (CollectionUtils.isNotEmpty(ybdcXmList)) {
                        BdcXmDO ybdcXm = ybdcXmList.get(0);
                        String dydjzmh = "";
                        //取值逻辑，如果yxtcqzh为空或者 yxtcqzh和bdcqzh相等，取bdcqzh---否则取两个字段拼接
                        if (StringUtils.isBlank(ybdcXm.getYxtcqzh()) || StringUtils.equals(ybdcXm.getYxtcqzh(), ybdcXm.getBdcqzh())) {
                            dydjzmh = StringUtils.isNotBlank(ybdcXm.getBdcqzh()) ? ybdcXm.getBdcqzh() : "";
                            dydjzmhList.add(dydjzmh);
                        } else if (StringUtils.isNotBlank(ybdcXm.getYxtcqzh()) && !StringUtils.equals(ybdcXm.getYxtcqzh(), ybdcXm.getBdcqzh())) {
                            dydjzmh = (StringUtils.isNotBlank(ybdcXm.getBdcqzh()) ? ybdcXm.getBdcqzh() : "") + CommonConstantUtils.ZF_YW_XG + ybdcXm.getYxtcqzh();
                            dydjzmhList.add(dydjzmh);
                        }
                    }
                    if (StringUtils.isNotBlank(bdcXmDO.getYcqzh())) {
                        ycqzhList.add(bdcXmDO.getYcqzh());
                    }
                    //抵押登记证明号用逗号隔开
                    String resultZmh = String.join(CommonConstantUtils.ZF_YW_DH, dydjzmhList);
                    String resultYcqzh = String.join(CommonConstantUtils.ZF_YW_DH, ycqzhList);
                    bdcSlQlxxymDTO.setDydjzmh(resultZmh);
                    bdcSlQlxxymDTO.setYcqzh(resultYcqzh);


                    //查找登记小类，登记原因关系
                    BdcDjxlDjyyQO bdcDjxlDjyyQO = new BdcDjxlDjyyQO();
                    bdcDjxlDjyyQO.setDjxl(bdcXmDO.getDjxl());
                    bdcDjxlDjyyQO.setGzldyid(bdcXmDO.getGzldyid());
                    bdcSlQlxxymDTO.setBdcDjxlDjyyGxDOList(bdcXmFeignService.listBdcDjxlDjyyGxWithParam(bdcDjxlDjyyQO));
                    //设置外联证书字段
                    bdcSlQlxxymDTO.setWlzs(queryWlzs(bdcXmDO.getXmid()));
                    if (bdcSlQlxxymQO.getQtsj()) {
                        //查找领证人信息
                        //先判断是否展示
                        boolean sfzs = true;
                        if (StringUtils.isNotBlank(bdcXmDO.getXmid()) && StringUtils.isNotBlank(lzrBzsDjxl)) {
                            sfzs = !lzrBzsDjxl.contains(bdcXmDO.getDjxl());
                        }
                        //查询领证人数据
                        if (sfzs) {
                            BdcLzrQO bdcLzrQO = new BdcLzrQO();
                            bdcLzrQO.setXmid(bdcXmDO.getXmid());
                            List<BdcLzrDO> bdcLzrDOList = bdcLzrFeignService.listBdcLzr(bdcLzrQO);
                            if (CollectionUtils.isNotEmpty(bdcLzrDOList)) {
                                bdcSlQlxxymDTO.setBdcLzrDOList(bdcLzrDOList);
                            }
                        }
                        //查找第三权利人信息
                        if (CollectionUtils.isEmpty(bdcSlQlxxymDTO.getBdcDsQlrDOList())) {
                            List<BdcDsQlrDO> bdcDsQlrDOList = slymQlrController.listBdcDsQlr(bdcXmDO.getXmid());
                            if (CollectionUtils.isNotEmpty(bdcDsQlrDOList)) {
                                bdcSlQlxxymDTO.setBdcDsQlrDOList(bdcDsQlrDOList);
                            }
                        }
                    }
                    //展示权利原权利按钮
                    if (StringUtils.equals(CommonConstantUtils.BOOL_TRUE, bdcSlQlxxymQO.getZxlc())) {
                        bdcSlQlxxymDTO.setShowYqlBtn("showYqlBtn");
                    } else if (Objects.nonNull(bdcYql)) {
                        bdcSlQlxxymDTO.setShowYqlBtn("showAllBtn");
                    }
                    //查询水电气信息
                    if (Boolean.TRUE.equals(bdcSlQlxxymQO.getCxsdq()) && ArrayUtils.contains(CommonConstantUtils.QLLX_FDCQ, bdcXmDO.getQllx())) {
                        BdcSdqywQO bdcSdqghQO = new BdcSdqywQO();
                        bdcSdqghQO.setGzlslid(bdcSlQlxxymQO.getProcessInsId());
                        List<BdcSdqghDO> bdcSdqghDOList = bdcSdqghFeignService.querySdqyw(bdcSdqghQO);
                        if (CollectionUtils.isNotEmpty(bdcSdqghDOList)) {
                            bdcSlQlxxymDTO.setBdcSdqghDOList(bdcSdqghDOList);
                        }

                    } else if (CollectionUtils.isNotEmpty(sdqghDjxlList) && sdqghDjxlList.contains(bdcXmDO.getDjxl())) {
                        BdcSdqywQO bdcSdqywQO = new BdcSdqywQO();
                        bdcSdqywQO.setGzlslid(bdcSlQlxxymQO.getProcessInsId());
                        List<BdcSdqghDO> bdcSdqghDOList = bdcSdqghFeignService.querySdqyw(bdcSdqywQO);
                        if (CollectionUtils.isEmpty(bdcSdqghDOList)) {
                            bdcSdqghDOList = new ArrayList<BdcSdqghDO>(1);
                            bdcSdqghDOList.add(new BdcSdqghDO());
                        }
                        bdcSlQlxxymDTO.setBdcSdqghDOList(bdcSdqghDOList);
                    }
                    //更正信息
                    List<BdcGzdjDO> bdcGzdjDOList = bdcGzdjFeignService.listBdcGzdjByXmid(bdcXmDO.getXmid());
                    if (CollectionUtils.isNotEmpty(bdcGzdjDOList)) {
                        bdcSlQlxxymDTO.setBdcGzdj(bdcGzdjDOList.get(0));
                    } else {
                        bdcSlQlxxymDTO.setBdcGzdj(new BdcGzdjDO());
                    }

                    //修正流程查询证书信息
                    if (bdcSlQlxxymQO.getXzlc() && StringUtils.isNotBlank(bdcSlQlxxymQO.getXmid())) {
                        //根据xmid查询证书信息并返回
                        List<BdcZsDO> bdcZsDOList = bdcZsFeignService.queryBdcZsByXmid(bdcSlQlxxymQO.getXmid());
                        if (CollectionUtils.isNotEmpty(bdcZsDOList)) {
                            bdcSlQlxxymDTO.setZsbdcqzh(bdcZsDOList.get(0).getBdcqzh());
                            bdcSlQlxxymDTO.setZsid(bdcZsDOList.get(0).getZsid());
                            if (!Objects.equals(CommonConstantUtils.XMLY_BDC_DM, bdcXmDO.getXmly())) {
                                //存量数据允许修改证号
                                bdcSlQlxxymDTO.setXgzszh(true);
                            }
                        }
                    }
                }
                if (MapUtils.isNotEmpty(zdMap)) {
                    if (CommonConstantUtils.QLLX_YG_DM.equals(bdcXmDO.getQllx()) && CommonConstantUtils.DJLX_YGDJ_DM.equals(bdcXmDO.getDjlx())) {
                        String djxlMc = StringToolUtils.convertBeanPropertyValueOfZd(Integer.parseInt(bdcXmDO.getDjxl()), zdMap.get(DJXL));
                        if (StringUtils.isNotBlank(djxlMc)) {
                            bdcSlQlxxymDTO.setQlmc(djxlMc.substring(0, djxlMc.length() - 4));
                        }
                    } else {
                        bdcSlQlxxymDTO.setQlmc(StringToolUtils.convertBeanPropertyValueOfZd(bdcXmDO.getQllx(), zdMap.get(QLLX)));
                        if (CollectionUtils.isNotEmpty(zxdjxl) && zxdjxl.contains(bdcXmDO.getDjxl())) {
                            bdcSlQlxxymDTO.setQlmc(bdcSlQlxxymDTO.getQlmc() + "注销");
                        }
                    }
                } else {
                    bdcSlQlxxymDTO.setQlmc("权利信息");
                }
                List<SlymCqxxDTO> slymCqxxDTOList = new ArrayList<>(1);
                // 2021-10-27 居住权页面添加不动产基本信息模块内容展示
                if (bdcQl != null && bdcQl instanceof BdcJzqDO) {
                    slymCqxxDTOList = listSlymCqxx(bdcXmList, bdcSlQlxxymQO.getProcessInsId(), bdcQl);
                }
                bdcSlQlxxymDTO.setSlymCqxxDTOList(slymCqxxDTOList);

                bdcSlQlxxymDTO.setBdcXmFbDO(CollectionUtils.isNotEmpty(bdcXmFbDOList) ? bdcXmFbDOList.get(0) : new BdcXmFbDO());
                bdcSlQlxxymDTO.setBdcXm(bdcXmDO);
                bdcSlQlxxymDTO.setQllx(bdcXmDO.getQllx());
                bdcSlQlxxymDTO.setXmlx(xmlx);
                //判断是否抵押
                if (bdcSlQlxxymDTO.getDydj() == null) {
                    if ((CollectionUtils.isNotEmpty(ydydjxl) && ydydjxl.contains(bdcXmDO.getDjxl())) || CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcXmDO.getQllx())) {
                        bdcSlQlxxymDTO.setDydj(true);
                    } else {
                        bdcSlQlxxymDTO.setDydj(false);
                    }
                }
                //非限制权力查询区县代码展现且是修正流程
                if (bdcSlQlxxymQO.getXzlc() && !ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, bdcXmDO.getQllx())) {
                    List<RegionDto> regionDtoList = new ArrayList<>(20);
                    regionDtoList.addAll(regionManagerClient.findRegionsByLevel(2));
                    regionDtoList.addAll(regionManagerClient.findRegionsByLevel(3));
                    bdcSlQlxxymDTO.setRegionDtoList(regionDtoList);
                }
                //判断是否需要查询维修基金信息（盐城）
                if (CollectionUtils.isNotEmpty(wxjjGzldyid) && wxjjGzldyid.contains(bdcXmDO.getGzldyid())) {
                    bdcSlQlxxymDTO.setBdcSlWxjjxxDO(this.getWxjjxx(bdcXmDO));
                }
                bdcSlQlxxymDTOList.add(bdcSlQlxxymDTO);
            }
        } else {
            BdcSlQlxxymDTO bdcSlQlxxymDTO = new BdcSlQlxxymDTO();
            bdcSlQlxxymDTO.setSlymCqxxDTOList(Collections.singletonList(new SlymCqxxDTO()));
            bdcSlQlxxymDTO.setBdcXm(new BdcXmDO());
            bdcSlQlxxymDTO.setBdcXmFbDO(new BdcXmFbDO());
            bdcSlQlxxymDTOList.add(bdcSlQlxxymDTO);
        }
        return bdcSlQlxxymDTOList;
    }

    /**
     * 房地产权的分摊土地面积与独用土地面积为空时，获取匹配土地证的分摊与独用面积
     * 此面积只用于前台展示，不记录到房地产权的表中
     */
    private void addPpTdzTdqlmj(BdcQl bdcQl, BdcXmDO bdcXmDO, Boolean zxlc) {
        // 获取匹配的土地证的独用土地面积与分摊土地面积
        BdcJsydsyqDO bdcJsydsyqDO = this.queryPpTdzJsydsyq(bdcXmDO.getXmid(), bdcXmDO.getGzldyid(), zxlc);
        if (Objects.nonNull(bdcJsydsyqDO) && bdcQl instanceof BdcFdcqDO) {
            if (Objects.isNull(((BdcFdcqDO) bdcQl).getFttdmj())) {
                ((BdcFdcqDO) bdcQl).setFttdmj(bdcJsydsyqDO.getFttdmj());
            }
            if (Objects.isNull(((BdcFdcqDO) bdcQl).getDytdmj())) {
                ((BdcFdcqDO) bdcQl).setDytdmj(bdcJsydsyqDO.getDytdmj());
            }
        }
    }

    /**
     * @param bdcSlQlxxymQO 页面查询对象
     * @return 不动产受理权利页面
     * @author <a href="mailto:sunchao@gtmap.cn">liaoxiang</a>
     * @description 根据工作流实例ID获取权利信息(用于批量流程权利信息详细)
     */
    @ResponseBody
    @GetMapping("/pllc")
    public Object listPlQlxx(BdcSlQlxxymQO bdcSlQlxxymQO) {
        Map<String, List<Map>> zdMap = new HashMap<>();
        List<BdcSlQlxxymDTO> bdcSlQlxxymDTOList = new ArrayList<>();
        try {
            zdMap = bdcZdFeignService.listBdcZd();
        } catch (Exception e) {
            LOGGER.error("字典项服务获取失败");
        }
        String id = "";
        List<BdcCshFwkgSlDO> bdcCshFwkgSlDOList = bdcXmFeignService.querySfzfKgslByGzlslid(bdcSlQlxxymQO.getProcessInsId(), CommonConstantUtils.SF_S_DM);
        //找到主房数据，如果为空，根据xmid随意取一条
        if (CollectionUtils.isNotEmpty(bdcCshFwkgSlDOList)) {
            id = bdcCshFwkgSlDOList.get(0).getId();
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(bdcSlQlxxymQO.getProcessInsId());
        List<BdcXmDO> allXmList = bdcXmFeignService.listBdcXm(bdcXmQO);
        //如果存在主房数据，优先读取主房的信息展现，否则随意读取一条项目展现
        if (StringUtils.isNotBlank(id)) {
            bdcXmQO.setXmid(id);
        }
        List<BdcXmDO> bdcXmList = bdcXmFeignService.listBdcXm(bdcXmQO);
        allXmList.sort(Comparator.comparing(BdcXmDO::getXmid));
        if (CollectionUtils.isNotEmpty(allXmList)) {
            //只展现其中一条权利即可
            BdcXmDO bdcXmDO = bdcXmList.get(0);

            BdcXmFbQO bdcXmFbQO = new BdcXmFbQO();
            bdcXmFbQO.setXmid(bdcXmDO.getXmid());
            List<BdcXmFbDO> bdcXmFbDOList = bdcXmfbFeignService.listBdcXmFb(bdcXmFbQO);
            String qjgldm = "";
            if (CollectionUtils.isNotEmpty(bdcXmFbDOList)) {
                qjgldm = bdcXmFbDOList.get(0).getQjgldm();
            }
            //批量的抵押,义务人处理
            if (CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcXmDO.getQllx())) {
                BdcCshFwkgSlDO bdcCshFwkgSlDO = bdcXmFeignService.queryFwkgsl(bdcXmDO.getXmid());
                //批量发一本证的
                if (bdcCshFwkgSlDO != null && bdcCshFwkgSlDO.getZsxh() != null) {
                    bdcXmDO.setYwr(bdcQlrFeignService.queryQlrsYbzs(bdcSlQlxxymQO.getProcessInsId(), CommonConstantUtils.QLRLB_YWR, ""));
                }
            }
            BdcSlQlxxymDTO bdcSlQlxxymDTO = new BdcSlQlxxymDTO();
            this.setTsxx(bdcXmDO, bdcSlQlxxymDTO);
            if (bdcSlQlxxymQO.getSfcxql()) {
                Boolean sfyql = false;
                BdcQl bdcQl = bdcQllxFeignService.queryQlxx(bdcXmDO.getXmid());
                if (bdcQl == null) {
                    sfyql = true;
                    bdcQl = bdcQllxFeignService.queryBdcYqlxx(bdcXmDO.getXmid());
                }
                if (bdcQl != null) {
                    String name = bdcQl.getClass().getAnnotation(Table.class).name();
                    bdcSlQlxxymDTO.setTableName(StringUtils.lowerCase(name));
                    bdcSlQlxxymDTO.setClassName(bdcQl.getClass().getName());
                    bdcSlQlxxymDTO.setBdcQl(bdcQl);
                    //预抵押权利比较特殊需根据预告登记种类判断
                    if (bdcQl instanceof BdcYgDO) {
                        BdcYgDO bdcYgDO = (BdcYgDO) bdcQl;
                        if (bdcYgDO.getYgdjzl() != null && (bdcYgDO.getYgdjzl() == 3 || bdcYgDO.getYgdjzl() == 4)) {
                            bdcSlQlxxymDTO.setDydj(true);
                        }
                    }
                } else {
                    throw new AppException("项目ID:" + bdcXmDO.getXmid() + "未查询到权利信息！");
                }
                //批量流程，抵押房产面积,抵押土地面积读取计算总和
                if (bdcQl instanceof BdcDyaqDO) {
                    BdcdySumDTO bdcdySumDTO = bdcBdcdyFeignService.calculatedDyaqMj(bdcSlQlxxymQO.getProcessInsId(), sfyql, new ArrayList<>());
                    if (bdcdySumDTO != null) {
                        if (bdcdySumDTO.getFwdymj() != null) {
                            ((BdcDyaqDO) bdcQl).setFwdymjsum(NumberUtil.formatDigit(bdcdySumDTO.getFwdymj(), 2));
                        }
                        if (bdcdySumDTO.getTddymj() != null) {
                            ((BdcDyaqDO) bdcQl).setTddymjsum(bdcdySumDTO.getTddymj());
                        }
                    }
                    getZmlx(bdcXmDO,bdcSlQlxxymDTO);
                }
                //批量流程计算总面积
                if (bdcQl instanceof BdcFdcqDO) {
                    BdcdySumDTO bdcdySumDTO = bdcBdcdyFeignService.calculatedFdcqMj(bdcSlQlxxymQO.getProcessInsId(), sfyql, bdcXmDO.getDjxl());
                    if (Objects.nonNull(bdcdySumDTO)) {
                        if (Objects.nonNull(bdcdySumDTO.getJzmj())) {
                            bdcSlQlxxymDTO.setJzmjSum(bdcdySumDTO.getJzmj());
                        }
                    }
                }
                //组织外联证书字段
                String wlzs = queryWlzsPl(bdcSlQlxxymQO.getProcessInsId(), null);
                bdcSlQlxxymDTO.setWlzs(wlzs);
                //组织抵押登记证明号
                //查询原项目
                BdcYxmQO bdcYxmQO = new BdcYxmQO();
                bdcYxmQO.setGzlslid(bdcSlQlxxymQO.getProcessInsId());
                bdcYxmQO.setZxyql(CommonConstantUtils.SF_S_DM);
                List<BdcXmDO> yBdcXmList = bdcXmFeignService.listYxmByYxmQO(bdcYxmQO);
                String dydjzmh = "";
                Set<String> dydjzmhList = new HashSet<>();
                Set<String> ycqzhList = new HashSet<>();
                if (CollectionUtils.isNotEmpty(yBdcXmList)) {
                    for (BdcXmDO ybdcXm : yBdcXmList) {
                        if (StringUtils.isBlank(ybdcXm.getYxtcqzh()) || StringUtils.equals(ybdcXm.getYxtcqzh(), ybdcXm.getBdcqzh())) {
                            dydjzmh = StringUtils.isNotBlank(ybdcXm.getBdcqzh()) ? ybdcXm.getBdcqzh() : "";
                            dydjzmhList.add(dydjzmh);
                        } else if (StringUtils.isNotBlank(ybdcXm.getYxtcqzh()) && !StringUtils.equals(ybdcXm.getYxtcqzh(), ybdcXm.getBdcqzh())) {
                            dydjzmh = (StringUtils.isNotBlank(ybdcXm.getBdcqzh()) ? ybdcXm.getBdcqzh() : "") + CommonConstantUtils.ZF_YW_XG + ybdcXm.getYxtcqzh();
                            dydjzmhList.add(dydjzmh);
                        }
                    }
                }
                List<SlymCqxxDTO> slymCqxxDTOList = new ArrayList<>(1);
                InitBdcSlQlxxymQO initBdcSlQlxxymQO = new InitBdcSlQlxxymQO();
                initBdcSlQlxxymQO.setBdcXmDOList(allXmList);
                initBdcSlQlxxymQO.setBdcQl(bdcQl);
                initBdcSlQlxxymQO.setBdcSlQlxxymQO(bdcSlQlxxymQO);
                initBdcSlQlxxymQO.setZdMap(zdMap);
                initBdcSlQlxxymDTOTscl(initBdcSlQlxxymQO, bdcSlQlxxymDTO);
                for (BdcXmDO bdcXm : allXmList) {
                    if (StringUtils.isNotBlank(bdcXm.getYcqzh())) {
                        ycqzhList.add(bdcXm.getYcqzh());
                    }
                }
                if (bdcSlQlxxymQO.getZdytJssjQcpj() && bdcQl instanceof BdcFdcqDO) {
                    //处理使用权结束时间
                    TdSyqJssjDTO tdSyqJssjDTO = bdcBdcdyFeignService.listDistinctTdsyjssj(bdcSlQlxxymQO.getProcessInsId());
                    bdcSlQlxxymDTO.setSyqjssjpj(StringUtils.join(tdSyqJssjDTO.getTdsyqjssj1(), CommonConstantUtils.ZF_YW_DH));
                    bdcSlQlxxymDTO.setSyqjssj2pj(StringUtils.join(tdSyqJssjDTO.getTdsyqjssj2(), CommonConstantUtils.ZF_YW_DH));
                    bdcSlQlxxymDTO.setSyqjssj3pj(StringUtils.join(tdSyqJssjDTO.getTdsyqjssj3(), CommonConstantUtils.ZF_YW_DH));
                }
                if (Objects.nonNull(bdcSlQlxxymQO.getCxCqxx()) && bdcSlQlxxymQO.getCxCqxx() && bdcQl instanceof BdcDyaqDO) {
                    slymCqxxDTOList = listSlymCqxx(allXmList, bdcSlQlxxymQO.getProcessInsId(), bdcQl);

                }
                bdcSlQlxxymDTO.setSlymCqxxDTOList(slymCqxxDTOList);
                //抵押登记证明号用逗号隔开
                String resultZmh = String.join(CommonConstantUtils.ZF_YW_DH, dydjzmhList);
                String resultYcqzh = String.join(CommonConstantUtils.ZF_YW_DH, ycqzhList);
                bdcSlQlxxymDTO.setDydjzmh(resultZmh);
                bdcSlQlxxymDTO.setYcqzh(resultYcqzh);
                //处理字段加等
                //根据原产权证号去重，数量大于1加等字，=1不加等
                //查出所有原产权证号不为空的数据
                List<BdcXmDO> xmList = allXmList.stream().filter(xm -> StringUtils.isNotBlank(xm.getYcqzh())).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(xmList)) {
                    Set<BdcXmDO> newBdcXm = new TreeSet<>(Comparator.comparing(BdcXmDO::getYcqzh));
                    newBdcXm.addAll(xmList);
                    StringBuilder ycqzh = new StringBuilder();
                    if (newBdcXm.size() > maxCount) {
                        int i = 0;
                        for (BdcXmDO bdcXmSetData : newBdcXm) {
                            ycqzh.append(bdcXmSetData.getYcqzh()).append(CommonConstantUtils.ZF_YW_DH);
                            i++;
                            if (i >= maxCount) {
                                break;
                            }
                        }
                        bdcXmDO.setYcqzh(removeDuplicate(ycqzh.substring(0, ycqzh.length() - 1)) + CommonConstantUtils.SUFFIX_PL);
                    } else {
                        for (BdcXmDO bdcXmSetData : newBdcXm) {
                            ycqzh.append(bdcXmSetData.getYcqzh()).append(CommonConstantUtils.ZF_YW_DH);
                        }
                        bdcXmDO.setYcqzh(removeDuplicate(ycqzh.substring(0, ycqzh.length() - 1)));
                    }
                }
                //查询水电气信息
                if (Boolean.TRUE.equals(bdcSlQlxxymQO.getCxsdq()) && ArrayUtils.contains(CommonConstantUtils.QLLX_FDCQ, bdcXmDO.getQllx())) {
                    BdcSdqywQO bdcSdqghQO = new BdcSdqywQO();
                    bdcSdqghQO.setGzlslid(bdcSlQlxxymQO.getProcessInsId());
                    List<BdcSdqghDO> bdcSdqghDOList = bdcSdqghFeignService.querySdqyw(bdcSdqghQO);
                    if (CollectionUtils.isNotEmpty(bdcSdqghDOList)) {
                        bdcSlQlxxymDTO.setBdcSdqghDOList(bdcSdqghDOList);
                    }

                }else if (CollectionUtils.isNotEmpty(sdqghDjxlList) && sdqghDjxlList.contains(bdcXmDO.getDjxl())) {
                    BdcSdqywQO bdcSdqywQO = new BdcSdqywQO();
                    bdcSdqywQO.setGzlslid(bdcSlQlxxymQO.getProcessInsId());
                    List<BdcSdqghDO> bdcSdqghDOList = bdcSdqghFeignService.querySdqyw(bdcSdqywQO);
                    if (CollectionUtils.isEmpty(bdcSdqghDOList)) {
                        bdcSdqghDOList = new ArrayList<BdcSdqghDO>(1);
                        bdcSdqghDOList.add(new BdcSdqghDO());
                    }
                    bdcSlQlxxymDTO.setBdcSdqghDOList(bdcSdqghDOList);
                }

                //查封信息
                if (Objects.nonNull(bdcSlQlxxymQO.getCxCfxx()) && bdcSlQlxxymQO.getCxCfxx() && bdcQl instanceof BdcCfDO) {
                    Integer dyhqllx = acceprBdcQllxFeignService.getQllxByBdcdyh(bdcXmDO.getBdcdyh(), "1");
                    bdcSlQlxxymDTO.setDyhqllx(StringToolUtils.convertBeanPropertyValueOfZd(dyhqllx, zdMap.get("qllx")));
                    List<SlymCfxxDTO> slymCfxxDTOS = this.listBdcCfxx(bdcSlQlxxymQO.getProcessInsId());
                    bdcSlQlxxymDTO.setSlymCfxxDTOList(slymCfxxDTOS);
                    List<BdcXmDO> cqXmList = bdcXmFeignService.listXscqXm(allXmList);
                    List<BdcCfDO> yBdcCfList = bdcCfxxFeignService.listYcfxxByGzlslid(bdcSlQlxxymQO.getProcessInsId());
                    bdcSlQlxxymDTO.setCqXmList(this.combineYcfbh(cqXmList, yBdcCfList, allXmList,CollectionUtils.isNotEmpty(bdcXmFbDOList)?bdcXmFbDOList.get(0).getQjgldm():""));
                }
            }
            if (MapUtils.isNotEmpty(zdMap)) {
                if (CommonConstantUtils.QLLX_YG_DM.equals(bdcXmDO.getQllx()) && CommonConstantUtils.DJLX_YGDJ_DM.equals(bdcXmDO.getDjlx())) {
                    String djxlMc = StringToolUtils.convertBeanPropertyValueOfZd(Integer.parseInt(bdcXmDO.getDjxl()), zdMap.get(DJXL));
                    if (StringUtils.isNotBlank(djxlMc)) {
                        bdcSlQlxxymDTO.setQlmc(djxlMc.substring(0, djxlMc.length() - 4));
                    }

                } else {
                    /**
                     * 【28248】bugfix 蚌埠特殊处理：蚌埠的房屋首登是建筑物区分所有权和房屋所有权放在一起的
                     */
                    if (CollectionUtils.isNotEmpty(gzldyidList) && gzldyidList.contains(bdcXmDO.getGzldyid())) {
                        List<Integer> qllxList = allXmList.stream().map(BdcXmDO::getQllx).distinct().collect(Collectors.toList());
                        if (qllxList.size() <= 1) {
                            bdcSlQlxxymDTO.setQlmc(StringToolUtils.convertBeanPropertyValueOfZd(bdcXmDO.getQllx(), zdMap.get(QLLX)));
                        } else {
                            bdcSlQlxxymDTO.setQlmc(StringToolUtils.convertBeanPropertyValueOfZd(4, zdMap.get(QLLX)));
                        }
                    } else {
                        bdcSlQlxxymDTO.setQlmc(StringToolUtils.convertBeanPropertyValueOfZd(bdcXmDO.getQllx(), zdMap.get(QLLX)));
                    }
                }
            }
            bdcSlQlxxymDTO.setBdcXm(bdcXmDO);

            bdcSlQlxxymDTO.setBdcXmFbDO(CollectionUtils.isNotEmpty(bdcXmFbDOList) ? bdcXmFbDOList.get(0) : new BdcXmFbDO());
            bdcSlQlxxymDTO.setQllx(bdcXmDO.getQllx());

            //判断是否抵押
            if (bdcSlQlxxymDTO.getDydj() == null) {
                if ((CollectionUtils.isNotEmpty(ydydjxl) && ydydjxl.contains(bdcXmDO.getDjxl())) || CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcXmDO.getQllx())) {
                    bdcSlQlxxymDTO.setDydj(true);
                } else {
                    bdcSlQlxxymDTO.setDydj(false);
                }
            }
            bdcSlQlxxymDTO.setBdcXmDOList(allXmList);
            bdcSlQlxxymDTO.setBdcdyCount(allXmList.size());
            bdcSlQlxxymDTOList.add(bdcSlQlxxymDTO);


        }
        return bdcSlQlxxymDTOList;
    }

    /**
     * @param bdcSlQlxxymQO 页面查询对象
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 受理批量组合页面加载(页面打开时加载基本框架信息)
     */
    @ResponseBody
    @GetMapping("/plzh")
    public Object listPlzhxx(BdcSlQlxxymQO bdcSlQlxxymQO) {
        List<BdcSlQlxxymDTO> bdcSlQlxxymDTOList = new ArrayList<>();
        if (StringUtils.isNotBlank(bdcSlQlxxymQO.getProcessInsId())) {
            //对项目进行分组
            Map<String, List<BdcXmDO>> groupXmMap = scGroupXmMap(bdcSlQlxxymQO.getProcessInsId());

            List<String> sortXmidList = new ArrayList<>();
            String gzldyid = "";
            if (MapUtils.isNotEmpty(groupXmMap)) {
                //整理分组后的数据
                Map<String, List<String>> djxlXmidListMap = new HashMap<>();
                for (Map.Entry<String, List<BdcXmDO>> entry : groupXmMap.entrySet()) {
                    //取分组后各个list中第一条,用于后续排序
                    sortXmidList.add(entry.getKey());
                    String djxl = entry.getValue().get(0).getDjxl();
                    gzldyid = entry.getValue().get(0).getGzldyid();
                    if (djxlXmidListMap.containsKey(djxl)) {
                        djxlXmidListMap.get(djxl).add(entry.getKey());
                    } else {
                        List<String> xmidList = new ArrayList<>();
                        xmidList.add(entry.getKey());
                        djxlXmidListMap.put(djxl, xmidList);
                    }
                }
                //根据djxlpz表顺序号排序
                List<BdcDjxlPzDO> bdcDjxlPzDOList = null;
                if (StringUtils.isNotBlank(gzldyid)) {
                    bdcDjxlPzDOList = bdcDjxlPzFeignService.listBdcDjxlPzByGzldyid(gzldyid, null);
                }
                if (CollectionUtils.isNotEmpty(bdcDjxlPzDOList)) {
                    for (BdcDjxlPzDO bdcDjxlPzDO : bdcDjxlPzDOList) {
                        if (djxlXmidListMap.containsKey(bdcDjxlPzDO.getDjxl())) {
                            List<String> djxlsortXmidList = djxlXmidListMap.get(bdcDjxlPzDO.getDjxl());
                            djxlsortXmidList.sort((o1, o2) -> o1.compareTo(o2));
                            for (String xmid : djxlsortXmidList) {
                                BdcSlQlxxymDTO bdcSlQlxxymDTO = initBdcSlQlxxymDTOByKey(bdcSlQlxxymQO,xmid, groupXmMap);
                                bdcSlQlxxymDTOList.add(bdcSlQlxxymDTO);
                            }
                        }

                    }
                    if (groupXmMap.size() != bdcSlQlxxymDTOList.size()) {
                        //djxlpz 与分组无法完全匹配
                        bdcSlQlxxymDTOList = new ArrayList<>();
                    }
                }

                if (CollectionUtils.isEmpty(bdcSlQlxxymDTOList)) {
                    //根据xmid排序
                    sortXmidList.sort((o1, o2) -> o1.compareTo(o2));
                    if (CollectionUtils.isNotEmpty(sortXmidList)) {
                        for (String xmid : sortXmidList) {
                            BdcSlQlxxymDTO bdcSlQlxxymDTO = initBdcSlQlxxymDTOByKey(bdcSlQlxxymQO,xmid, groupXmMap);
                            bdcSlQlxxymDTOList.add(bdcSlQlxxymDTO);

                        }
                    }
                }
            }
        }
        return bdcSlQlxxymDTOList;
    }

    /**
     * @param xmid  项目ID
     * @param sfyql 是否查询原权利
     * @return 权利信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据项目ID查询权利信息
     */
    @ResponseBody
    @GetMapping("/queryQlxx")
    public Object queryQlxx(String xmid, Boolean sfyql) {
        if (StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException("缺少参数：xmid");
        }
        return sfyql != null && sfyql ? bdcQllxFeignService.queryBdcYqlxx(xmid) : bdcQllxFeignService.queryQlxx(xmid);
    }

    /**
     * @param json      前台传输权利JSON
     * @param className 类路径
     * @return 修改数量
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 修改权利信息（用于简单流程、组合流程）
     */
    @ResponseBody
    @PatchMapping("")
    public Integer updateQl(@RequestBody String json, @RequestParam("className") String className) {
        int count = bdcEntityFeignService.updateByJsonEntity(json, className);
        //更新使用期限
        if (Boolean.TRUE.equals(updateSyqx)) {
            JSONObject obj = JSONObject.parseObject(json);
            if (StringUtils.isNotBlank(obj.getString("xmid"))) {
                bdcRyzdFeignService.updateSyqx(obj.getString("xmid"));
            }
        }
        return count;
    }

    /**
     * @param json         集合
     * @param className    类名字符串
     * @param processInsId
     * @return 批量更新权利信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 更新权利
     */
    @ResponseBody
    @PatchMapping("/list")
    public Integer updateQl(@RequestBody String json, @RequestParam(name = "className", required = false) String className, @RequestParam("processInsId") String processInsId, @RequestParam(name = "xmids", required = false) String xmids, @RequestParam(name = "onexmid", required = false) String onexmid, @RequestParam(name = "zxlc", required = false) String zxlc) throws Exception {
        JSONObject obj = JSONObject.parseObject(json);
        BdcDjxxUpdateQO bdcDjxxUpdateQO = new BdcDjxxUpdateQO();
        bdcDjxxUpdateQO.setJsonStr(JSONObject.toJSONString(obj));
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNotBlank(xmids)) {
            map.put("xmids", xmids.split(CommonConstantUtils.ZF_YW_DH));
        } else {
            map.put("gzlslid", processInsId);
        }
        if (StringUtils.isBlank(className) && StringUtils.isNotBlank(onexmid)) {
            //注销流程查询原权利，非注销流程查询当前权利
            BdcQl bdcQl = (Boolean.parseBoolean(zxlc)) ? bdcQllxFeignService.queryBdcYqlxx(onexmid) : bdcQllxFeignService.queryQlxx(onexmid);
            if (bdcQl != null) {
                className = bdcQl.getClass().getName();
            } else {
                LOGGER.info("根据xmid未查询到权利信息，无法更新权利信息，xmid：{}", onexmid);
                return 0;
            }
        }
        //注销流程查询原权利，非注销流程查询当前权利
        if (Boolean.parseBoolean(zxlc)) {
            map.put("sfyql", Boolean.parseBoolean(zxlc));
        }
        bdcDjxxUpdateQO.setWhereMap(map);


        bdcDjxxUpdateQO.setClassName(className);
        int count= bdcQllxFeignService.updateBatchBdcQl(bdcDjxxUpdateQO);
        //更新使用期限
        if (Boolean.TRUE.equals(updateSyqx) &&!Boolean.parseBoolean(zxlc) &&StringUtils.isNotBlank(className)) {
            BdcSyqxPlDTO bdcSyqxPlDTO =new BdcSyqxPlDTO();
            bdcSyqxPlDTO.setGzlslid(processInsId);
            if(StringUtils.isNotBlank(xmids)) {
                bdcSyqxPlDTO.setXmidList(Arrays.asList(xmids.split(CommonConstantUtils.ZF_YW_DH)));
            }
            bdcSyqxPlDTO.setQlclassName(className.substring(className.lastIndexOf(CommonConstantUtils.ZF_YW_JH)+1));
            bdcSyqxPlDTO.setJsonObject(obj);
            bdcRyzdFeignService.updateSyqxPl(bdcSyqxPlDTO);
        }
        return count;

    }

    /**
     * @param bdcDjxxUpdateQO 登记信息更新查询参数
     * @return 更新数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量更新权利
     */
    @ResponseBody
    @PatchMapping("/updateBatchBdcQl")
    public Integer updateBatchBdcQl(@RequestBody BdcDjxxUpdateQO bdcDjxxUpdateQO) throws Exception {
        if (bdcDjxxUpdateQO == null || StringUtils.isAnyBlank(bdcDjxxUpdateQO.getJsonStr(), bdcDjxxUpdateQO.getClassName()) || MapUtils.isEmpty(bdcDjxxUpdateQO.getWhereMap())) {
            throw new NullPointerException("空参数异常！");
        }
        List<String> xmidList = new ArrayList<>();
        if (bdcDjxxUpdateQO.getWhereMap().containsKey("xmids")) {
            xmidList = (List<String>) bdcDjxxUpdateQO.getWhereMap().get("xmids");

        }

        int count = 0;
        BdcSyqxPlDTO bdcSyqxPlDTO =new BdcSyqxPlDTO();
        if (bdcDjxxUpdateQO.getWhereMap().containsKey("gzlslid")) {
            bdcSyqxPlDTO.setGzlslid(bdcDjxxUpdateQO.getWhereMap().get("gzlslid").toString());
        }
        bdcSyqxPlDTO.setQlclassName(bdcDjxxUpdateQO.getClassName().substring(bdcDjxxUpdateQO.getClassName().lastIndexOf(CommonConstantUtils.ZF_YW_JH)+1));
        bdcSyqxPlDTO.setJsonObject(JSONObject.parseObject(bdcDjxxUpdateQO.getJsonStr()));
        if (CollectionUtils.isNotEmpty(xmidList)) {
            List<List> lists = ListUtils.subList(xmidList, 1000);
            for (List partList : lists) {
                bdcDjxxUpdateQO.getWhereMap().put("xmids", partList);
                count += bdcQllxFeignService.updateBatchBdcQl(bdcDjxxUpdateQO);
                //更新使用期限
                if (Boolean.TRUE.equals(updateSyqx)) {
                    bdcSyqxPlDTO.setXmidList(partList);
                    bdcRyzdFeignService.updateSyqxPl(bdcSyqxPlDTO);
                }
            }
        } else {
            count = bdcQllxFeignService.updateBatchBdcQl(bdcDjxxUpdateQO);
            //更新使用期限
            if (Boolean.TRUE.equals(updateSyqx)) {
                bdcRyzdFeignService.updateSyqxPl(bdcSyqxPlDTO);
            }
        }
        return count;

    }


    /**
     * @param qlid 权利ID
     * @return 不动产房地产权（项目内多幢）项目信息
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据权利ID获取不动产房地产权（项目内多幢）项目信息 （用于简单流程、组合流程）
     */
    @ResponseBody
    @GetMapping("/fdcqxm/list")
    public Object listFdcqxm(String qlid) {
        return bdcQllxFeignService.listFdcqXm(qlid);
    }

    /**
     * @param qlid 权利ID
     * @return {List} 建筑物区分所有权业主共有部分登记信息_共有部分信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取建筑物区分所有权业主共有部分登记信息_共有部分息
     */
    @ResponseBody
    @GetMapping("/fdcq3/gyxx")
    public Object listFdcq3Gyxx(String qlid) {
        return bdcQllxFeignService.listFdcq3Gyxx(qlid);
    }

    /**
     * @param json 前台传输不动产房地产权（项目内多幢）项目信息JSON
     * @return 修改数量
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 修改不动产房地产权（项目内多幢）项目信息
     */
    @ResponseBody
    @PatchMapping(value = "/fdcqxm/list")
    public Integer updateFdcqXmxx(@RequestBody String json,boolean nosumfzmj) {
        Integer count = 0;
        double countJzmj = 0d;
        String qlid = "";
        for (Object obj : JSON.parseArray(json)) {
            try {
                String jzmjTemp = ((Map) obj).get("jzmj").toString();
                qlid = ((Map) obj).get("qlid").toString();
                if (StringUtils.isNotEmpty(jzmjTemp)) {
                    double zjmj = Double.parseDouble(jzmjTemp);
                    countJzmj += zjmj;
                }
            } catch (Exception e) {
                LOGGER.error("建筑面积转换失败", e);
            }
            count += bdcEntityFeignService.updateByJsonEntity(JSON.toJSONString(obj), BdcFdcqFdcqxmDO.class.getName());
        }
        //是否同步到建筑面积
        if (countJzmj > 0 && StringUtils.isNotBlank(qlid) &&Boolean.FALSE.equals(nosumfzmj)) {
            BdcFdcqDO bdcFdcqDO = new BdcFdcqDO();
            bdcFdcqDO.setQlid(qlid);
            bdcFdcqDO.setJzmj(countJzmj);
            bdcEntityFeignService.updateByJsonEntity(JSON.toJSONString(bdcFdcqDO), BdcFdcqDO.class.getName());

        }
        return count;
    }

    /**
     * @param json 建筑物区分所有权业主共有部分登记信息_共有部分信
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 更新建筑物区分所有权业主共有部分登记信息_共有部分信息（会有多条，提交的JSON数据为JSON数组）
     */
    @ResponseBody
    @PatchMapping(value = "/fdcq3/gyxx")
    public int updateFdcq3Gyxx(@RequestBody String json) {
        int count = 0;
        for (Object obj : JSON.parseArray(json)) {
            count += bdcEntityFeignService.updateByJsonEntity(JSON.toJSONString(obj), BdcFdcq3GyxxDO.class.getName());
        }

        return count;
    }

    /**
     * @param xmid 项目id
     * @return 表名
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取表名（用于批量修改表单）
     */
    @ResponseBody
    @GetMapping("/tableName")
    public String getTableName(String xmid, Boolean zxlc) {
        String tableName = "";
        if (StringUtils.isNotBlank(xmid)) {
            //批量修改目前只考虑同一种权利类型
            BdcQl bdcQl = zxlc != null && zxlc ? bdcQllxFeignService.queryBdcYqlxx(xmid) : bdcQllxFeignService.queryQlxx(xmid);
            if (bdcQl != null) {
                String name = bdcQl.getClass().getAnnotation(Table.class).name();
                tableName = StringUtils.lowerCase(name);
            }
        }
        return tableName;
    }

    /**
     * @param xmid 项目id
     * @return 表名
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取表名（用于批量修改表单）
     */
    @ResponseBody
    @GetMapping("/plxgXx")
    public BdcSlPlxgXxVO getPlxgXx(String xmid, Boolean zxlc) {
        BdcSlPlxgXxVO bdcSlPlxgXxVO = new BdcSlPlxgXxVO();
        if (StringUtils.isNotBlank(xmid)) {
            // 获取项目信息
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setXmid(xmid);
            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                bdcSlPlxgXxVO.setBdcXmDO(bdcXmDOList.get(0));
            }
            BdcXmFbQO bdcXmFbQO = new BdcXmFbQO();
            bdcXmFbQO.setXmid(xmid);
            List<BdcXmFbDO> bdcXmFbDOList = bdcXmfbFeignService.listBdcXmFb(bdcXmFbQO);
            if (CollectionUtils.isNotEmpty(bdcXmFbDOList)) {
                bdcSlPlxgXxVO.setBdcXmFbDO(bdcXmFbDOList.get(0));
            }

            //批量修改目前只考虑同一种权利类型
            BdcQl bdcQl = zxlc != null && zxlc ? bdcQllxFeignService.queryBdcYqlxx(xmid) : bdcQllxFeignService.queryQlxx(xmid);
            if (null == bdcQl) {
                return bdcSlPlxgXxVO;
            }
            // 获取对应的权利的表名
            bdcSlPlxgXxVO.setTableName(StringUtils.lowerCase(bdcQl.getClass().getAnnotation(Table.class).name()));
            bdcSlPlxgXxVO.setBdcQl(bdcQl);

            //获取交易信息
            List<BdcSlJyxxDO> bdcSlJyxxDOList = bdcSlJyxxFeignService.listBdcSlJyxxByXmid(xmid);
            if (CollectionUtils.isNotEmpty(bdcSlJyxxDOList)) {
                bdcSlPlxgXxVO.setBdcSlJyxxDO(bdcSlJyxxDOList.get(0));
            }
        }
        return bdcSlPlxgXxVO;
    }


    /**
     * @param xmids 项目id
     * @return 表名
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量获取项目信息
     */
    @ResponseBody
    @GetMapping("/batch/plxgXx")
    public BdcSlPlxgBatchXxVO getBatchPlxgXx(String xmids, Boolean zxlc,String processInsId) {
        BdcSlPlxgBatchXxVO bdcSlPlxgXxVO = new BdcSlPlxgBatchXxVO();
        List<String> xmidList = new ArrayList<>();
        if(StringUtils.isBlank(xmids) && StringUtils.isBlank(processInsId) ){
            return bdcSlPlxgXxVO;
        }
        if(StringUtils.isBlank(xmids)){
            List<BdcXmDTO> bdcXmDTOS = bdcXmFeignService.listBdcXmBfxxByGzlslid(processInsId);
            xmidList = bdcXmDTOS.stream().map(BdcXmDTO::getXmid).collect(Collectors.toList());
        } else {
            xmidList =  Arrays.asList(xmids.split(","));
        }
        if (CollectionUtils.isNotEmpty(xmidList)) {
            // 获取项目信息
            List<BdcXmDO> bdcXmDOList = new ArrayList<>(xmidList.size());
            if (CollectionUtils.size(xmidList) > 500) {
                List<List> xmidPartList = ListUtils.subList(xmidList, 500);
                for (List partXmids : xmidPartList) {
                    BdcXmQO bdcXmQO = new BdcXmQO();
                    bdcXmQO.setXmidList(partXmids);
                    List<BdcXmDO> partBdcXmList = bdcXmFeignService.listBdcXm(bdcXmQO);
                    if (CollectionUtils.isNotEmpty(partBdcXmList)) {
                        bdcXmDOList.addAll(partBdcXmList);
                    }
                }
            } else {
                BdcXmQO bdcXmQO = new BdcXmQO();
                bdcXmQO.setXmidList(xmidList);
                bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            }

            if (CollectionUtils.isEmpty(bdcXmDOList)) {
                return null;
            }
            bdcSlPlxgXxVO.setBdcXmDOs(bdcXmDOList);
            List<BdcXmFbDO> bdcXmFbDOList = new ArrayList<>(bdcXmDOList.size());
            if (CollectionUtils.size(xmidList) > 500) {
                List<List> xmidPartList = ListUtils.subList(xmidList, 500);
                for (List partXmids : xmidPartList) {
                    BdcXmFbQO bdcXmFbQO = new BdcXmFbQO();
                    bdcXmFbQO.setXmids(partXmids);
                    List<BdcXmFbDO> xmFbDOList = bdcXmfbFeignService.listBdcXmFb(bdcXmFbQO);
                    if (CollectionUtils.isNotEmpty(xmFbDOList)) {
                        bdcXmFbDOList.addAll(xmFbDOList);
                    }
                }
            } else {
                BdcXmFbQO bdcXmFbQO = new BdcXmFbQO();
                bdcXmFbQO.setXmids(xmidList);
                bdcXmFbDOList = bdcXmfbFeignService.listBdcXmFb(bdcXmFbQO);
            }
            if (CollectionUtils.isEmpty(bdcXmFbDOList)) {
                //return null;
            }
            bdcSlPlxgXxVO.setBdcXmFbDOs(bdcXmFbDOList);
            //批量修改目前只考虑同一种权利类型
            List<BdcQl> bdcQl = zxlc != null && zxlc ?
                    bdcQllxFeignService.queryBdcYqlxxs(StringUtils.join(xmidList, ",")) :
                    bdcQllxFeignService.listQlxxByXmids(xmidList);
            if (null == bdcQl) {
                return bdcSlPlxgXxVO;
            }
            bdcSlPlxgXxVO.setBdcQls(bdcQl);
            // 获取对应的权利的表名
            bdcSlPlxgXxVO.setTableName(StringUtils.lowerCase(bdcQl.get(0).getClass().getAnnotation(Table.class).name()));
        }
        return bdcSlPlxgXxVO;
    }

    /**
     * @param xmids 项目id
     * @return 表名
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 只登记不登簿 批量获取项目信息
     */
    @ResponseBody
    @GetMapping("/zdjbdb/batch/plxgXx")
    public BdcSlPlxgBatchXxVO getZdjbdbPlxgXx(String xmids, String processInsId) {
        BdcSlPlxgBatchXxVO bdcSlPlxgXxVO = new BdcSlPlxgBatchXxVO();
        List<String> xmidList = new ArrayList<>();
        if(StringUtils.isBlank(xmids) && StringUtils.isBlank(processInsId) ){
            return bdcSlPlxgXxVO;
        }
        if(StringUtils.isBlank(xmids)){
            List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmFeignService.listBdcSlXmByGzlslid(processInsId);
            xmidList = bdcSlXmDOList.stream().map(BdcSlXmDO::getXmid).collect(Collectors.toList());
        } else {
            xmidList =  Arrays.asList(xmids.split(","));
        }

        if (CollectionUtils.isNotEmpty(xmidList)) {
            // 获取受理项目信息
            List<BdcSlXmDO> bdcSlXmDOList = new ArrayList<>(xmidList.size());
            // 获取受理房屋信息
            List<BdcSlFwxxDO> bdcSlFwxxDOList = new ArrayList<>(xmidList.size());

            List<List> xmidPartList = ListUtils.subList(xmidList, 500);
            for (List partXmids : xmidPartList) {
                List<BdcSlXmDO> partBdcSlXmDOS = bdcSlXmFeignService.listBdcSlXmByXmids(partXmids);
                List<BdcSlFwxxDO> partBdcSlFwxxDOS = bdcSlFwxxFeignService.listBdcSlFwxxByXmids(partXmids);

                if (CollectionUtils.isNotEmpty(partBdcSlXmDOS)) {
                    bdcSlXmDOList.addAll(partBdcSlXmDOS);
                }
                if (CollectionUtils.isNotEmpty(partBdcSlFwxxDOS)) {
                    bdcSlFwxxDOList.addAll(partBdcSlFwxxDOS);
                }
            }

            if (CollectionUtils.isEmpty(bdcSlXmDOList)) {
                return null;
            }
            bdcSlPlxgXxVO.setTableName("bdc_sl_fwxx");
            bdcSlPlxgXxVO.setBdcSlXmDOList(bdcSlXmDOList);
            bdcSlPlxgXxVO.setBdcSlFwxxDO(bdcSlFwxxDOList);
        }
        return bdcSlPlxgXxVO;
    }

    /**
     * @param xmid 项目ID
     * @return 原项目ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询非外联证书的原项目id
     */
    @ResponseBody
    @GetMapping("/queryYxmid")
    public String queryYxmid(String xmid) {
        String yxmid = "";
        if (StringUtils.isNotBlank(xmid)) {
            BdcXmLsgxQO bdcXmLsgxQO = new BdcXmLsgxQO();
            bdcXmLsgxQO.setXmid(xmid);
            bdcXmLsgxQO.setWlxm(CommonConstantUtils.SF_F_DM);
            List<BdcXmLsgxDO> bdcXmLsgxDOList = bdcLsgxFeignService.listXmLsgxByXmid(bdcXmLsgxQO);
            if (CollectionUtils.isNotEmpty(bdcXmLsgxDOList)) {
                yxmid = bdcXmLsgxDOList.get(0).getYxmid();
            }
        }
        return yxmid;
    }


    /**
     * @param xmid
     * @return 原项目ID
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新查封机关或者解封机关
     */
    @ResponseBody
    @PostMapping("/updateCfjgOrJfjg")
    public Object updateCfjgOrJfjg(@RequestBody BdcCfjgQO bdcCfjgQO, String xmid) {
        if (StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException("缺失项目ID！");
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setXmid(xmid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            if (bdcCfjgQO.isIscf()) {
                bdcCfjgQO.setCfjg(bdcXmDOList.get(0).getQlr());
                bdcCfjgQO.setBzxr(bdcXmDOList.get(0).getYwr());
            } else if (bdcCfjgQO.isIsjf()) {
                bdcCfjgQO.setJfjg(bdcXmDOList.get(0).getQlr());
            }
            bdcRyzdFeignService.updateCfjgOrJfjg(bdcCfjgQO);
        }
        return bdcCfjgQO;
    }


    /**
     * @param bdcFdcq3QO 房地产权3信息
     * @return 原项目ID
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新房地产权3的权利人
     */
    @ResponseBody
    @PostMapping("/updateFdcq3Qlr")
    public void updateFdcq3Qlr(@RequestBody BdcFdcq3QO bdcFdcq3QO) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(bdcFdcq3QO.getGzlslid());
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            bdcFdcq3QO.setQlr(bdcXmDOList.get(0).getQlr());
            bdcRyzdFeignService.updateBdcFdcq3Qlr(bdcFdcq3QO);
        }
    }

    @ResponseBody
    @PostMapping("/updateCfjgOrJfjg/nt")
    public Object updateCfjgOrJfjgToNt(@RequestBody BdcCfjgQO bdcCfjgQO, String xmid) {
        if (StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException("缺失项目ID！");
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setXmid(xmid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        BdcQl bdcQl = bdcQllxFeignService.queryQlxx(xmid);
        BdcCfDO bdcCfDO = new BdcCfDO();
        if (bdcQl instanceof BdcCfDO) {
            bdcCfDO = (BdcCfDO) bdcQl;
        }
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            if (bdcCfjgQO.isIscf()) {
                bdcCfjgQO.setCfjg(bdcXmDOList.get(0).getQlr());
                //当被执行人为空的时候更新义务人进去，否则直接读取数据库的数据--南通
                if (StringUtils.isBlank(bdcCfDO.getBzxr())) {
                    bdcCfjgQO.setBzxr(bdcXmDOList.get(0).getYwr());
                } else {
                    bdcCfjgQO.setBzxr(bdcCfDO.getBzxr());
                }
            } else if (bdcCfjgQO.isIsjf()) {
                bdcCfjgQO.setJfjg(bdcXmDOList.get(0).getQlr());
            }
            bdcRyzdFeignService.updateCfjgOrJfjg(bdcCfjgQO);
        }
        return bdcCfjgQO;
    }

    /**
     * @param xmid
     * @return 原项目ID
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据xmid获取原权利信息
     */
    @ResponseBody
    @GetMapping("/yql")
    public Object getYqlxx(String xmid) {
        BdcSlQlxxymDTO bdcSlQlxxymDTO = new BdcSlQlxxymDTO();
        BdcQl bdcQl;
        if (StringUtils.isNotBlank(xmid)) {
            bdcQl = bdcQllxFeignService.queryBdcYqlxx(xmid);
            if (bdcQl != null) {
                bdcSlQlxxymDTO.setBdcQl(bdcQl);
                BdcXmQO bdcXmQO = new BdcXmQO();
                bdcXmQO.setXmid(bdcQl.getXmid());
                List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
                if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                    bdcSlQlxxymDTO.setBdcXm(bdcXmDOList.get(0));
                }
            }
        }
        return bdcSlQlxxymDTO;
    }

    /**
     * @param json
     * @return 权利信息分页
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询权利信息分页
     */
    @ResponseBody
    @PostMapping("/listQlByPageJson")
    public Object listQlByPageJson(@RequestBody String json) {
        Page<BdcBdcdyVO> bdcdyVOPage;
        List<BdcBdcdyVO> bdcBdcdyVOS = new ArrayList<>();
        BdcBdcdyQO bdcBdcdyQO = JSON.parseObject(json, BdcBdcdyQO.class);
        Pageable pageable = new PageRequest(bdcBdcdyQO.getPage() - 1, bdcBdcdyQO.getSize());
        Sort newsort = "UNSORTED".equals(String.valueOf(pageable.getSort())) ? null : pageable.getSort();
        //判断xmidList 的大小
        if (CollectionUtils.isNotEmpty(bdcBdcdyQO.getXmidList()) && CollectionUtils.size(bdcBdcdyQO.getXmidList()) > 900) {
            List<List> xmidPartList = ListUtils.subList(bdcBdcdyQO.getXmidList(), 900);
            bdcBdcdyQO.setXmidPartList(xmidPartList);
            bdcBdcdyQO.setXmidList(null);
        }
        //常州按照天干来进行单元排序,取出全部数据内存进行排序
        if (tgpx) {
            //如果一个项目选择了超过设置的个数据会出现分页问题，但是一般选择1-2千个创建时就会出问题了,而且排序也会很慢
            Pageable allAageable = new PageRequest(0, 1500);
            bdcdyVOPage = bdcBdcdyFeignService.listBdcdyByPage(allAageable.getPageNumber(), allAageable.getPageSize(), newsort, bdcBdcdyQO);
            if (bdcdyVOPage.getTotalElements() <= 1500) {
                try {
                    bdcBdcdyVOS = sortBdcdy(bdcdyVOPage.getContent());
                    List<List<BdcBdcdyVO>> partition = Lists.partition(bdcBdcdyVOS, pageable.getPageSize());
                    bdcBdcdyVOS = partition.get(pageable.getPageNumber());
                } catch (Exception e) {
                    log.info("查询权利信息(listQlByPageJson)分页错误{}", e.getMessage());
                    e.printStackTrace();
                    //走原逻辑
                    bdcdyVOPage = bdcBdcdyFeignService.listBdcdyByPage(pageable.getPageNumber(), pageable.getPageSize(), newsort, bdcBdcdyQO);
                    bdcBdcdyVOS = bdcdyVOPage.getContent();
                }
            } else {
                //数据量过大走原逻辑
                bdcdyVOPage = bdcBdcdyFeignService.listBdcdyByPage(pageable.getPageNumber(), pageable.getPageSize(), newsort, bdcBdcdyQO);
                bdcBdcdyVOS = bdcdyVOPage.getContent();
            }
        } else {
            bdcdyVOPage = bdcBdcdyFeignService.listBdcdyByPage(pageable.getPageNumber(), pageable.getPageSize(), newsort, bdcBdcdyQO);
            bdcBdcdyVOS = bdcdyVOPage.getContent();
        }
        List<Map> bdcQlList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(bdcBdcdyVOS)) {
            //循环获取权利信息
            for (BdcBdcdyVO data : bdcBdcdyVOS) {
                if (data != null) {
                    bdcQlList.add(setQlxx(data, bdcBdcdyQO.isSfyql()));
                }
            }
        }
        //封装返回分页信息
        return addLayUiCode(new PageImpl(bdcQlList, pageable, bdcdyVOPage.getTotalElements()));
    }

    /**
     * @param bdcBdcdyQO 项目信息
     * @return
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询当前流程的不动产单元/权利信息列表（listQlByPageJson 方法的非分页返回）
     */
    @PostMapping(value = "/listQl/all")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object queryBdcdyList(@RequestBody BdcBdcdyQO bdcBdcdyQO, Boolean sfyql) {
        List<Map> bdcQlList = new ArrayList<>();
        //先查询单元信息
        List<BdcBdcdyVO> bdcBdcdyVOList = bdcBdcdyFeignService.queryBdcdyList(bdcBdcdyQO);
        if (CollectionUtils.isNotEmpty(bdcBdcdyVOList)) {
            //循环获取权利信息
            for (BdcBdcdyVO data : bdcBdcdyVOList) {
                if (data != null) {
                    bdcQlList.add(setQlxx(data, sfyql));
                }
            }
        }
        return bdcQlList;
    }

    /**
     * @param xmid 项目ID
     * @return 是否抵押登记
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 判断是否为抵押类登记（包括预抵押）
     */
    @ResponseBody
    @GetMapping("/checkDydj")
    public Boolean checkDydj(String xmid) {
        Boolean sfdydj = false;
        if (StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException("缺失项目ID！");
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setXmid(xmid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            throw new AppException("未查询到对应的项目信息");
        }
        BdcXmDO bdcXmDO = bdcXmDOList.get(0);
        //判断是否抵押
        if ((CollectionUtils.isNotEmpty(ydydjxl) && ydydjxl.contains(bdcXmDO.getDjxl())) || CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcXmDO.getQllx())) {
            sfdydj = true;
        }
        return sfdydj;

    }

    /**
     * @param json 前台传输权利JSON
     * @param xmid 项目ID
     * @return 修改数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 修改权利信息冗余信息
     */
    @ResponseBody
    @PatchMapping("/updateQlRyzd")
    public void updateQlRyzd(@RequestBody String json, @RequestParam(name = "xmid", required = false) String xmid, @RequestParam(name = "processInsId", required = false) String processInsId) {
        if (StringUtils.isBlank(xmid) && StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException("缺少参数：xmid,processInsId");
        }
        //组合页面冗余字段更新
        if (StringUtils.isBlank(xmid)) {
            //批量更新
            List<BdcQl> bdcQlList = bdcQllxFeignService.listQlxxByProcessInsId(processInsId);
            for (BdcQl bdcQl : bdcQlList) {
                updateQl(bdcQl, json);
            }
        } else {
            //单个更新
            BdcQl bdcQl = bdcQllxFeignService.queryQlxx(xmid);
            if (bdcQl instanceof BdcFdcq3DO) {
                // 当权力为：建筑物区分所有权共有部分信息时，同步更新共有信息中的建筑物面积
                List<BdcFdcq3GyxxDO> gyxxList = bdcQllxFeignService.listFdcq3Gyxx(bdcQl.getQlid());
                JSONObject object = (JSONObject) JSON.parse(json);
                if (CollectionUtils.isNotEmpty(gyxxList) && object.containsKey("jzmj") && StringUtils.isNotBlank(object.getString("jzmj"))) {
                    for (BdcFdcq3GyxxDO gyxx : gyxxList) {
                        gyxx.setJgzwmj(Double.valueOf(object.getString("jzmj")));
                        bdcQllxFeignService.updateFdcq3Gyxx(gyxx);
                    }
                }
            } else {
                updateQl(bdcQl, json);
            }
        }
    }

    /**
     * @param gzlslid
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 计算权利面积字段之和(房地产权)
     */
    @ResponseBody
    @GetMapping("/calculatedQlMj")
    public Object calculatedQlMj(String gzlslid, String zxlc, String djxl) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException(ErrorCode.MISSING_ARG, "缺失参数工作流实例ID");
        }
        BdcdySumDTO bdcdyFdcqSumDTO = this.computeQlmj(gzlslid, zxlc, djxl);

        // 土地房屋一起注销时，添加建设用地使用权的土地面积
        BdcdySumDTO bdcdyJsydsyqSumDTO = bdcBdcdyFeignService.calculatedJsydsyqMj(gzlslid, Boolean.parseBoolean(zxlc), djxl);
        if (Objects.nonNull(bdcdyJsydsyqSumDTO) && Objects.nonNull(bdcdyJsydsyqSumDTO.getTdqlmj())) {
            Double fdcqTdqlmj = Objects.isNull(bdcdyFdcqSumDTO.getTdqlmj()) ? 0 : bdcdyFdcqSumDTO.getTdqlmj();
            Double tdqlmj = new BigDecimal(bdcdyJsydsyqSumDTO.getTdqlmj().toString())
                    .add(new BigDecimal(fdcqTdqlmj.toString())).doubleValue();
            bdcdyFdcqSumDTO.setTdqlmj(tdqlmj);
        }
        return bdcdyFdcqSumDTO;
    }

    /**
     * 计算权利面积
     */
    private BdcdySumDTO computeQlmj(String gzlslid, String zxlc, String djxl) {
        boolean sfyql = Boolean.parseBoolean(zxlc);
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setDjxl(djxl);
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = this.bdcXmFeignService.listBdcXm(bdcXmQO);

        BdcdySumDTO totalBdcdySumDTO = new BdcdySumDTO();
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            List<BdcdySumDTO> bdcdySumDTOList = new ArrayList<>(bdcXmDOList.size());
            for (BdcXmDO bdcXmDO : bdcXmDOList) {
                BdcQl bdcQl = sfyql ? bdcQllxFeignService.queryBdcYqlxx(bdcXmDO.getXmid()) : bdcQllxFeignService.queryQlxx(bdcXmDO.getXmid());
                if (Objects.nonNull(bdcQl) && bdcQl instanceof BdcFdcqDO) {
                    BdcdySumDTO bdcdySumDTO = new BdcdySumDTO(((BdcFdcqDO) bdcQl).getJzmj(), ((BdcFdcqDO) bdcQl).getTdsyqmj());
                    // 判断是否获取到房地产权的土地权利面积，没有则获取匹配的土地证的独用土地面积与分摊土地面积之和
                    if (Objects.isNull(bdcdySumDTO.getTdqlmj())) {
                        bdcdySumDTO.setTdqlmj(this.getPpTdzTdqlmj(bdcXmDO.getXmid(), bdcXmDO.getGzldyid(), sfyql));
                    }
                    bdcdySumDTOList.add(bdcdySumDTO);
                }
            }

            if (CollectionUtils.isNotEmpty(bdcdySumDTOList)) {
                Double totalJzmj = bdcdySumDTOList.stream().filter(t -> Objects.nonNull(t.getJzmj())).map(t -> new BigDecimal(String.valueOf(t.getJzmj())))
                        .reduce((m, n) -> m.add(n)).map(BigDecimal::doubleValue).orElse(0d);
                Double totalTdqlmj = bdcdySumDTOList.stream().filter(t -> Objects.nonNull(t.getTdqlmj())).map(t -> new BigDecimal(String.valueOf(t.getTdqlmj())))
                        .reduce((m, n) -> m.add(n)).map(BigDecimal::doubleValue).orElse(0d);
                totalBdcdySumDTO.setTdqlmj(totalTdqlmj);
                totalBdcdySumDTO.setJzmj(totalJzmj);
            }
        }
        return totalBdcdySumDTO;
    }

    /**
     * 获取匹配的土地证的独用土地面积与分摊土地面积之和
     */
    private Double getPpTdzTdqlmj(String xmid, String gzldyid, Boolean sfyql) {
        BdcJsydsyqDO bdcJsydsyqDO = this.queryPpTdzJsydsyq(xmid, gzldyid, sfyql);
        if (null != bdcJsydsyqDO) {
            return DoubleUtils.sum(bdcJsydsyqDO.getFttdmj(), bdcJsydsyqDO.getDytdmj());
        }
        return null;
    }

    /**
     * 查询房地产权匹配的土地证的独用土地面积与分摊土地面积
     * <p>只支持特定配置的流程，读取配置<code>xstdqlmj.gzldyids</code>中的工作流定义id</p>
     */
    private BdcJsydsyqDO queryPpTdzJsydsyq(String xmid, String gzldyid, Boolean sfyql) {
        if (StringUtils.isNotBlank(tdzQlmjGzldyids)
                && StringUtils.isNotBlank(gzldyid) && tdzQlmjGzldyids.indexOf(gzldyid) > -1) {
            List<BdcFctdPpgxDO> bdcFctdPpgxDOList = new ArrayList<>();
            if (sfyql) {
                BdcXmLsgxQO bdcXmLsgxQO = new BdcXmLsgxQO();
                bdcXmLsgxQO.setXmid(xmid);
                bdcXmLsgxQO.setWlxm(CommonConstantUtils.SF_F_DM);
                bdcXmLsgxQO.setZxyql(1);
                List<BdcXmLsgxDO> bdcXmLsgxDOList = bdcLsgxFeignService.listXmLsgxByXmid(bdcXmLsgxQO);
                if (CollectionUtils.isNotEmpty(bdcXmLsgxDOList)) {
                    String yxmid = bdcXmLsgxDOList.get(0).getYxmid();
                    bdcFctdPpgxDOList = this.bdcPpFeignService.queryBdcFctdPpgx(yxmid);
                }
            } else {
                bdcFctdPpgxDOList = this.bdcPpFeignService.queryBdcFctdPpgx(xmid);
            }
            if (CollectionUtils.isNotEmpty(bdcFctdPpgxDOList)) {
                String tdxmid = bdcFctdPpgxDOList.get(0).getTdcqxmid();
                BdcJsydsyqDO bdcJsydsyqDO = (BdcJsydsyqDO) this.bdcQllxFeignService.queryQlxx(tdxmid);
                if (null != bdcJsydsyqDO) {
                    return bdcJsydsyqDO;
                }
            }
        }
        return null;
    }

    /**
     * @param xmid 项目ID
     * @return 权利其他状况模板
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据项目ID获取权利其他状况或者附记模板模式(2 : 权利其他状况 3 : 附记)
     */
    @ResponseBody
    @GetMapping("/queryQlqtzkFjMb")
    public String queryQlqtzkFjMb(String xmid, String mode) {
        // 获取模板类型
        String mbnr = bdcZsInitFeignService.queryQlqtzkFj(xmid, mode);
        List<String> modeList = new ArrayList<>();
        modeList.add(mode);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("bfqlqtzk", mbnr);
        jsonObject.put("fj", mbnr);
        jsonObject.put("xmid", xmid);

        BdcQlqtzkFjQO bdcQlqtzkFjQO = new BdcQlqtzkFjQO();
        bdcQlqtzkFjQO.setModeList(modeList);
        bdcQlqtzkFjQO.setJsonStr(JSONObject.toJSONString(jsonObject));
        // 将模板内容直接更新到数据库
        bdcZsInitFeignService.updateQlqtzkAndFj(bdcQlqtzkFjQO);
        return mbnr;
    }

    /**
     * @param xmid  项目id
     * @param mode  2：权力其他状况 3： 附记
     * @param hqysj 是否获取原数据
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 南通要更新或者手动填写权力其他状况和附记
     * @date : 2020/8/27 9:57
     */
    @ResponseBody
    @GetMapping("/queryQlqtzkFjMb/nt")
    public String queryQlqtzkFjMb(String xmid, String mode, Boolean hqysj, Boolean sfgx) {
        if (StringUtils.isAnyBlank(xmid, mode)) {
            throw new AppException("获取权利其他状况附记模板失败");
        }
        // 获取模板类型
        String mbnr = bdcZsInitFeignService.queryQlqtzkFj(xmid, mode);
        List<String> modeList = new ArrayList<>();
        modeList.add(mode);
        if (Objects.nonNull(sfgx) && sfgx) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("bfqlqtzk", (StringUtils.isNotBlank(mbnr) ? mbnr : ""));
            jsonObject.put("fj", (StringUtils.isNotBlank(mbnr) ? mbnr : ""));
            jsonObject.put("xmid", xmid);
            BdcQlqtzkFjQO bdcQlqtzkFjQO = new BdcQlqtzkFjQO();
            bdcQlqtzkFjQO.setModeList(modeList);
            bdcQlqtzkFjQO.setJsonStr(JSONObject.toJSONString(jsonObject));
            // 将模板内容直接更新到数据库
            bdcQlqtzkFjQO.setPlgx(true);
            bdcZsInitFeignService.updateQlqtzkAndFj(bdcQlqtzkFjQO);
        }
        return mbnr;
    }

    /**
     * @param bdcQl 不动产权利
     * @param json  json集合
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 更新权利
     */
    private void updateQl(BdcQl bdcQl, String json) {
        if (bdcQl == null) {
            throw new MissingArgumentException("缺少参数：未找到对应的权利");
        }
        JSONObject obj = JSONObject.parseObject(json);
        obj.put("qlid", bdcQl.getQlid());
        json = JSONObject.toJSONString(obj);
        bdcEntityFeignService.updateByJsonEntity(json, bdcQl.getClass().getName());

    }

    /**
     * @param bdcBdcdyVO
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 处理获取权利信息
     */
    private Map setQlxx(BdcBdcdyVO bdcBdcdyVO, Boolean sfyql) {
        Map map = new HashMap();
        if (bdcBdcdyVO != null) {
            String xmid = bdcBdcdyVO.getXmid();
            BdcQl bdcQl = sfyql != null && sfyql ? bdcQllxFeignService.queryBdcYqlxx(xmid) : bdcQllxFeignService.queryQlxx(xmid);
            //qllx94 建筑物区分所有权及业主共有部分 获取bdc_fdcq3_gyxx数据
            if (bdcBdcdyVO.getQllx() != null && CommonConstantUtils.QLLX_JZWQFSYQYZGYBF_DM.equals(bdcBdcdyVO.getQllx())) {
                if (bdcQl != null) {
                    map.putAll(Object2MapUtils.object2MapExceptNull(bdcQl));
                    List<BdcFdcq3GyxxDO> bdcFdcq3GyxxDOList = bdcQllxFeignService.listFdcq3Gyxx(bdcQl.getQlid());
                    if (CollectionUtils.isNotEmpty(bdcFdcq3GyxxDOList)) {
                        map.putAll(Object2MapUtils.object2MapExceptNull(bdcFdcq3GyxxDOList.get(0)));
                        //登簿人登记时间取主表数据
                        map.put("dbr", bdcQl.getDbr());
                        map.put("djsj", bdcQl.getDjsj());
                    }
                    //不动产单元信息放到后面,防止权利信息为原权利,与项目表xmid冲突
                    map.putAll(Object2MapUtils.object2MapExceptNull(bdcBdcdyVO));
                    map.put("qlxmid", bdcQl.getXmid());
                }
            } else {
                if (bdcQl != null) {
                    map.putAll(Object2MapUtils.object2MapExceptNull(bdcQl));
                    map.put("qlxmid", bdcQl.getXmid());

                }
                //不动产单元信息放到后面,防止权利信息为原权利,与项目表xmid冲突
                map.putAll(Object2MapUtils.object2MapExceptNull(bdcBdcdyVO));
            }

            // 处理土地使用权面积
            if (bdcQl instanceof BdcJsydsyqDO) {
                BdcJsydsyqDO bdcJsydsyqDO = (BdcJsydsyqDO) bdcQl;
                map.put("tdsyqmj", bdcJsydsyqDO.getSyqmj());
            }
            //处理房地产权的土地使用期限
            if (bdcQl instanceof BdcFdcqDO) {
                BdcFdcqDO bdcFdcqDO = (BdcFdcqDO) bdcQl;
                String syqx = "";
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
                if (Objects.nonNull(bdcFdcqDO.getTdsyqssj())) {
                    syqx += sdf.format(bdcFdcqDO.getTdsyqssj()) + "起";
                }
                if (Objects.nonNull(bdcFdcqDO.getTdsyjssj())) {
                    syqx += sdf.format(bdcFdcqDO.getTdsyjssj()) + "止";
                }
                if (Objects.nonNull(bdcFdcqDO.getTdsyqssj2())) {
                    syqx += sdf.format(bdcFdcqDO.getTdsyqssj2()) + "起";
                }
                if (Objects.nonNull(bdcFdcqDO.getTdsyjssj2())) {
                    syqx += sdf.format(bdcFdcqDO.getTdsyjssj2()) + "止";
                }
                if (Objects.nonNull(bdcFdcqDO.getTdsyqssj3())) {
                    syqx += sdf.format(bdcFdcqDO.getTdsyqssj3()) + "起";
                }
                if (Objects.nonNull(bdcFdcqDO.getTdsyjssj3())) {
                    syqx += sdf.format(bdcFdcqDO.getTdsyjssj3()) + "止";
                }
                map.put("syqx", syqx);
            }

            // 土地权利面积为空时，读取土地证的数据
            dealTdsyqmj(map, bdcBdcdyVO, sfyql);
        }
        return map;
    }

    /**
     * @param bdcBdcdyList
     * @description 按照天干对单元号进行排序
     * 必须要有幢号或者房间（这样才能大致确认 幢号 房间号 单元号），否则将坐落中所有的数字拼接起来，用数字进行排序
     *
     */
    private List<BdcBdcdyVO> sortBdcdy(List<BdcBdcdyVO> bdcBdcdyList) {
        if(CollectionUtils.isEmpty(bdcBdcdyList) || (bdcBdcdyList.size() <= 1)){
            return bdcBdcdyList;
        }

        //是否都含有幢号或者房间号
        boolean allMatch = bdcBdcdyList
                .stream()
                .allMatch(bdcBdcdyVO ->
                        StringUtils.isNotBlank(bdcBdcdyVO.getFjh()) ||
                                StringUtils.isNotBlank(bdcBdcdyVO.getZh())
                );

        try {
            if(allMatch){
                //走幢号-单元号-房间号的排序
                return sortBdcdyNormal(bdcBdcdyList);
            } else {
                //走坐落排序
                return sortBdcdyZL(bdcBdcdyList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("按照天干对单元号进行排序出错{}",e.getMessage());
        }

        return bdcBdcdyList;
    }

    /**
     * 将坐落中所有的数字汇总进行排序
     * @param bdcBdcdyList
     * @return
     */
    private List<BdcBdcdyVO> sortBdcdyZL(List<BdcBdcdyVO> bdcBdcdyList) {
        List<BdcBdcdyVO> bdcBdcdyListSortAble = new ArrayList<>(bdcBdcdyList);
        //对幢号进行排序
        bdcBdcdyListSortAble.sort(Comparator.comparing(bdcBdcdyVO -> {
            String zl = bdcBdcdyVO.getZl();
            StringBuilder zlNumber = new StringBuilder();
            //从第一个是数字的位数到最后一个是数字的位数
            for (char i = 0; i < zl.length(); i++) {
                if(Character.isDigit(zl.charAt(i))){
                    zlNumber.append(zl.charAt(i));
                }
            }

            if (zlNumber.length() > 0) {
                return Long.parseLong(zlNumber.toString());
            }else {
                return 0L;
            }
        }));
        return bdcBdcdyListSortAble;
    }

    /**
     * 按照幢号-单元-房间号进行排序
     * @param bdcBdcdyList
     * @return
     */
    private List<BdcBdcdyVO> sortBdcdyNormal(List<BdcBdcdyVO> bdcBdcdyList) {
        log.info("按照天干对单元号进行排,按照单元号顺序排序{}",JSON.toJSONString(bdcBdcdyList));
        List<BdcBdcdyVO> bdcBdcdySortedList = new ArrayList<>();
        //生成排序的vo
        List<BdcBdcdySortVO> bdcBdcdySortVOS = genBdcdySortVo(bdcBdcdyList);
        log.info("按照天干对单元号进行排,按照单元号顺序排序{},数据格式化结果{}",JSON.toJSONString(bdcBdcdyList),
                JSON.toJSONString(bdcBdcdySortVOS));
        Map<String, List<BdcBdcdySortVO>> sameBuildingBdcdy = bdcBdcdySortVOS
                .stream()
                .collect(
                        Collectors.groupingBy(bdcBdcdySortVO -> {
                            return bdcBdcdySortVO.getZl().substring(0,
                                    Pattern.compile(("\\D" + bdcBdcdySortVO.getZh()+"\\D")).matcher(bdcBdcdySortVO.getZl()).find() ? bdcBdcdySortVO.getZl().indexOf("" + bdcBdcdySortVO.getZh()) : bdcBdcdySortVO.getZl().length()-1);
                        })
                );
        //排序
        Map<String, BdcBdcdyVO> bdcBdcdyMap = bdcBdcdyList
                .stream()
                .collect(Collectors.toMap(BdcBdcdyVO::getXmid, o -> o));
        for (Map.Entry<String, List<BdcBdcdySortVO>> stringListEntry : sameBuildingBdcdy.entrySet()) {
            stringListEntry.getValue().sort(Comparator
                    .comparing(BdcBdcdySortVO::getZh).reversed()
                    .thenComparing(BdcBdcdySortVO::getDyh)
                    .reversed()
                    .thenComparing(BdcBdcdySortVO::getFjh));
            for (BdcBdcdySortVO bdcBdcdySortVO : stringListEntry.getValue()) {
                bdcBdcdySortedList.add(bdcBdcdyMap.get(bdcBdcdySortVO.getXmid()));
            }
        }
        //合并排序后的数据
        return bdcBdcdySortedList;
    }

    /**
     *
     * @param bdcBdcdyList
     * @return
     */
    private List<BdcBdcdySortVO> genBdcdySortVo(List<BdcBdcdyVO> bdcBdcdyList) {
        List<BdcBdcdySortVO> bdcBdcdySortVOList = new ArrayList<>();
        for (BdcBdcdyVO bdcBdcdyVO : bdcBdcdyList) {
            BdcBdcdySortVO bdcBdcdySortVO = new BdcBdcdySortVO();
            bdcBdcdySortVO.setXmid(bdcBdcdyVO.getXmid());
            bdcBdcdySortVO.setZl(bdcBdcdyVO.getZl());
            //
            int zhwz = -1;
            int fjhwz = -1;
            String zl = bdcBdcdyVO.getZl();
            if(StringUtils.isNotBlank(bdcBdcdyVO.getFjh()) && StringUtils.isNotBlank(bdcBdcdyVO.getZh())){
                zhwz = zl.indexOf(bdcBdcdyVO.getZh());
                int zh = getNumberOfString(bdcBdcdyVO.getZh());
                bdcBdcdySortVO.setZh(zh);
                //取得房间号中的数字位置-房间号中存的数据和坐落中存的房间号有可能会不一致
                int fjh = getNumberOfString(bdcBdcdyVO.getFjh());
                fjhwz = zl.indexOf(String.valueOf(fjh),zhwz);
                bdcBdcdySortVO.setFjh(fjh);
            } else  if(StringUtils.isNotBlank(bdcBdcdyVO.getFjh())){
                //有房间号没有幢号
                int fjh = getNumberOfString(bdcBdcdyVO.getFjh());
                fjhwz = zl.indexOf(String.valueOf(fjh),zl.length()/2);
                bdcBdcdySortVO.setFjh(fjh);

                //找房间号之前的一个数字
                int zhRev = getNumberOfString(StringUtils.reverse(zl.substring(0, fjhwz)));
                int zh = Integer.parseInt(StringUtils.reverse(String.valueOf(zhRev)));
                bdcBdcdySortVO.setZh(zh);
                int zhRevIndex = StringUtils.reverse(zl.substring(0, fjhwz)).indexOf(String.valueOf(zhRev)) + String.valueOf(zh).length();
                zhwz = fjhwz - zhRevIndex;
            } else  if(StringUtils.isNotBlank(bdcBdcdyVO.getZh())){
                //有幢号没有房间号
                zhwz = zl.indexOf(bdcBdcdyVO.getZh());
                int zh = getNumberOfString(bdcBdcdyVO.getZh());
                bdcBdcdySortVO.setZh(zh);

                //找幢号之后的一个位置
                int fromIndex = zhwz + String.valueOf(zh).length() + 1;
                int fjh = getNumberOfString(zl.substring(fromIndex));
                fjhwz = zl.indexOf(String.valueOf(fjh), fromIndex);
                bdcBdcdySortVO.setFjh(fjh);
            }

            //在幢号和房间号质检查找单元信息
            for (int i = zhwz; i < fjhwz; i++) {
                // charAt(i)中i要>=0
                if (i<0 && fjhwz>0){
                    i = 0;
                } else {
                    break;
                }

                if (FullCalendarUtils.Gan.contains(Character.toString(zl.charAt(i)))) {
                    bdcBdcdySortVO.setDyh(FullCalendarUtils.Gan.size() - FullCalendarUtils.Gan.indexOf(Character.toString(zl.charAt(i))));
                    break;
                }
            }
            //如果没有找到,则到房间号中尝试查找
            if (Objects.isNull(bdcBdcdySortVO.getDyh()) || (bdcBdcdySortVO.getDyh() <= 0)) {
                String fjhString = "";
                if (StringUtils.isNotBlank(bdcBdcdyVO.getFjh())) {
                    fjhString = bdcBdcdyVO.getFjh().substring(0, 1);
                }

                if (FullCalendarUtils.Gan.contains(fjhString)) {
                    bdcBdcdySortVO.setDyh(FullCalendarUtils.Gan.size() - FullCalendarUtils.Gan.indexOf(fjhString));
                }
            }

            //0表示没有找到单元号的信息
            if (Objects.isNull(bdcBdcdySortVO.getDyh()) || (bdcBdcdySortVO.getDyh() <= 0)) {
                bdcBdcdySortVO.setDyh(0);
            }
            bdcBdcdySortVOList.add(bdcBdcdySortVO);
        }

        return bdcBdcdySortVOList;
    }

    /**
     * 从字符串中查找第一个数字
     * @param str
     * @return
     */
    private int getNumberOfString(String str){
        int zhNumber = 0;
        //从第一个是数字的位数到最后一个是数字的位数
        int start = -1;
        for (char i = 0; i < str.length(); i++) {
            if(Character.isDigit(str.charAt(i))){
                if(start < 0){
                    start = i;
                }
            } else {
                if(start >= 0) {
                    try {
                        zhNumber = Integer.parseInt(str.substring(start, i));
                    } catch (NumberFormatException e) {
                        zhNumber = 0;
                    }
                    break;
                }
            }
        }
        if(start >= 0 && zhNumber <=0){
            zhNumber = Integer.parseInt(str.substring(start));
        }

        return zhNumber;
    }

    // 如果房地产权的土地权利面积为空，则取匹配土地证的独用和分摊之和
    private void dealTdsyqmj(Map map, BdcBdcdyVO bdcBdcdyVO, Boolean sfyql) {
        // 判断是否有土地权利面积
        if (!map.containsKey("tdsyqmj") || StringUtils.isBlank(MapUtils.getString(map, "tdsyqmj"))) {
            // 根据配置判断当前流程是否需要读取匹配的土地证的独用土地面积与分摊土地面积之和
            Double tdsyqmj = this.getPpTdzTdqlmj(bdcBdcdyVO.getXmid(), bdcBdcdyVO.getGzldyid(), sfyql);
            if (Objects.nonNull(tdsyqmj)) {
                map.put("tdsyqmj", tdsyqmj);
            }
        }
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description xmid  分组key
     */
    //TODO 参数需要合并为实体
    private BdcSlQlxxymDTO initBdcSlQlxxymDTOByKey(BdcSlQlxxymQO bdcSlQlxxymQO,String xmid, Map<String, List<BdcXmDO>> groupXmMap) {
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        Boolean zxlc =Boolean.parseBoolean(bdcSlQlxxymQO.getZxlc());
        List<BdcXmDO> groupXmList = groupXmMap.get(xmid);
        BdcXmDO bdcXmDO = groupXmList.get(0);

        BdcSlQlxxymDTO bdcSlQlxxymDTO = initBdcSlQlxxymDTO(bdcXmDO, groupXmList, zxlc,zdMap);
        bdcSlQlxxymDTO.setBdcdyCount(groupXmList.size());
        List<String> xmidList = groupXmList.stream().map(BdcXmDO::getXmid).collect(Collectors.toList());
        List<String> bdcdyhList = groupXmList.stream().map(BdcXmDO::getBdcdyh).collect(Collectors.toList());
        bdcSlQlxxymDTO.setGroupXmidList(xmidList);
        bdcSlQlxxymDTO.setGroupBdcdyhList(bdcdyhList);

        // 处理外联证书
        bdcSlQlxxymDTO.setWlzs(queryWlzsPl(bdcSlQlxxymQO.getProcessInsId(), xmidList));
        //处理字段加等
        //根据原产权证号去重，数量大于1加等字，=1不加等
        //查出所有原产权证号不为空的数据
        List<BdcXmDO> xmList = groupXmList.stream().filter(xm -> StringUtils.isNotBlank(xm.getYcqzh())).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(xmList)) {
            Set<BdcXmDO> newBdcXm = new TreeSet<>(Comparator.comparing(BdcXmDO::getYcqzh));
            newBdcXm.addAll(xmList);
            StringBuilder ycqzh = new StringBuilder();
            if (newBdcXm.size() > maxCount) {
                int i = 0;
                for (BdcXmDO bdcXmSetData : newBdcXm) {
                    ycqzh.append(bdcXmSetData.getYcqzh()).append(CommonConstantUtils.ZF_YW_DH);
                    i++;
                    if (i >= maxCount) {
                        break;
                    }
                }
                bdcXmDO.setYcqzh(removeDuplicate(ycqzh.substring(0, ycqzh.length() - 1)) + CommonConstantUtils.SUFFIX_PL);
            } else {
                for (BdcXmDO bdcXmSetData : newBdcXm) {
                    ycqzh.append(bdcXmSetData.getYcqzh()).append(CommonConstantUtils.ZF_YW_DH);
                }
                bdcXmDO.setYcqzh(removeDuplicate(ycqzh.substring(0, ycqzh.length() - 1)));
            }
        }
        //批量的抵押,义务人处理
        if (CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcXmDO.getQllx())) {
            BdcCshFwkgSlDO bdcCshFwkgSlDO = bdcXmFeignService.queryFwkgsl(bdcXmDO.getXmid());
            //批量发一本证的
            if (bdcCshFwkgSlDO != null && bdcCshFwkgSlDO.getZsxh() != null) {
                bdcXmDO.setYwr(bdcQlrFeignService.queryQlrsYbzs(bdcSlQlxxymQO.getProcessInsId(), CommonConstantUtils.QLRLB_YWR, bdcXmDO.getDjxl()));
            }
        }
        BdcQl bdcQl = bdcSlQlxxymDTO.getBdcQl();
        InitBdcSlQlxxymQO initBdcSlQlxxymQO =new InitBdcSlQlxxymQO();
        initBdcSlQlxxymQO.setBdcXmDOList(groupXmList);
        initBdcSlQlxxymQO.setBdcQl(bdcQl);
        initBdcSlQlxxymQO.setBdcSlQlxxymQO(bdcSlQlxxymQO);
        initBdcSlQlxxymQO.setZdMap(zdMap);
        initBdcSlQlxxymDTOTscl(initBdcSlQlxxymQO,bdcSlQlxxymDTO);
        //批量流程，抵押房产面积,抵押土地面积读取计算总和
        if (bdcQl instanceof BdcDyaqDO) {
            BdcdySumDTO bdcdySumDTO = bdcBdcdyFeignService.calculatedDyaqMj(bdcSlQlxxymQO.getProcessInsId(), zxlc != null && zxlc, xmidList);
            if (bdcdySumDTO != null) {
                if (bdcdySumDTO.getFwdymj() != null) {
                    ((BdcDyaqDO) bdcQl).setFwdymj(NumberUtil.formatDigit(bdcdySumDTO.getFwdymj(), 2));
                }
                if (bdcdySumDTO.getTddymj() != null) {
                    ((BdcDyaqDO) bdcQl).setTddymj(bdcdySumDTO.getTddymj());
                }
            }


            //产权信息
            if (Objects.nonNull(bdcSlQlxxymQO.getCxCqxx()) && bdcSlQlxxymQO.getCxCqxx()) {
                List<SlymCqxxDTO> slymCqxxDTOS = listSlymCqxx(groupXmList, bdcSlQlxxymQO.getProcessInsId(), bdcQl);
                bdcSlQlxxymDTO.setSlymCqxxDTOList(slymCqxxDTOS);
                if(((BdcDyaqDO) bdcQl).getDybdclx() == 4 && bdcSlQlxxymQO.getCxYchsCqxx()){
                    for (SlymCqxxDTO slymCqxxDTO : slymCqxxDTOS) {
                        if(MapUtils.isEmpty(slymCqxxDTO.getCqxxMap())){
                            List<SlymDyaCqxxDTO> ychsCqxx = getYchsCqxx(Collections.singletonList(slymCqxxDTO.getBdcXmDO()));
                            if(CollectionUtils.isNotEmpty(ychsCqxx)){
                                Map map = JSONObject.parseObject(JSONObject.toJSONString(ychsCqxx.get(0)), Map.class);
                                slymCqxxDTO.setCqxxMap(map);
                            }
                        }
                    }
                }

            }
        }
        //批量流程计算总面积
        if (bdcQl instanceof BdcFdcqDO) {
            BdcdySumDTO bdcdySumDTO = bdcBdcdyFeignService.calculatedFdcqMj(bdcSlQlxxymQO.getProcessInsId(), zxlc != null ? zxlc : false, bdcXmDO.getDjxl());
            if (Objects.nonNull(bdcdySumDTO)) {
                if (Objects.nonNull(bdcdySumDTO.getJzmj())) {
                    bdcSlQlxxymDTO.setJzmjSum(bdcdySumDTO.getJzmj());
                }
            }
        }
        //查询水电气信息
        if (Boolean.TRUE.equals(bdcSlQlxxymQO.getCxsdq()) && ArrayUtils.contains(CommonConstantUtils.QLLX_FDCQ, bdcXmDO.getQllx())) {
            BdcSdqywQO bdcSdqghQO = new BdcSdqywQO();
            bdcSdqghQO.setGzlslid(bdcSlQlxxymQO.getProcessInsId());
            List<BdcSdqghDO> bdcSdqghDOList = bdcSdqghFeignService.querySdqyw(bdcSdqghQO);
            if (CollectionUtils.isNotEmpty(bdcSdqghDOList)) {
                bdcSlQlxxymDTO.setBdcSdqghDOList(bdcSdqghDOList);
            }

        }else if (CollectionUtils.isNotEmpty(sdqghDjxlList) && sdqghDjxlList.contains(bdcXmDO.getDjxl())) {
            BdcSdqywQO bdcSdqywQO = new BdcSdqywQO();
            bdcSdqywQO.setGzlslid(bdcSlQlxxymQO.getProcessInsId());
            List<BdcSdqghDO> bdcSdqghDOList = bdcSdqghFeignService.querySdqyw(bdcSdqywQO);
            if (CollectionUtils.isEmpty(bdcSdqghDOList)) {
                bdcSdqghDOList = new ArrayList<BdcSdqghDO>(1);
                bdcSdqghDOList.add(new BdcSdqghDO());
            }
            bdcSlQlxxymDTO.setBdcSdqghDOList(bdcSdqghDOList);
        }
        return bdcSlQlxxymDTO;


    }

    /**
     * @param bdcXmDO 项目对象
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 组织权利信息页面信息
     */
    private BdcSlQlxxymDTO initBdcSlQlxxymDTO(BdcXmDO bdcXmDO, List<BdcXmDO> groupXmList,Boolean zxlc,Map<String, List<Map>> zdMap) {

        BdcSlQlxxymDTO bdcSlQlxxymDTO = new BdcSlQlxxymDTO();
        //获取权利tab标题
        if (CommonConstantUtils.QLLX_YG_DM.equals(bdcXmDO.getQllx()) && CommonConstantUtils.DJLX_YGDJ_DM.equals(bdcXmDO.getDjlx())) {
            String djxlMc = StringToolUtils.convertBeanPropertyValueOfZd(Integer.parseInt(bdcXmDO.getDjxl()), zdMap.get(DJXL));
            if (StringUtils.isNotBlank(djxlMc)) {
                bdcSlQlxxymDTO.setQlmc(djxlMc.substring(0, djxlMc.length() - 4));
            }

        } else {
            bdcSlQlxxymDTO.setQlmc(StringToolUtils.convertBeanPropertyValueOfZd(bdcXmDO.getQllx(), zdMap.get(QLLX)));
        }
        bdcSlQlxxymDTO.setBdcXm(bdcXmDO);
        bdcSlQlxxymDTO.setBdcXmDOList(groupXmList);
        BdcXmFbQO bdcXmFbQO = new BdcXmFbQO();
        bdcXmFbQO.setXmid(bdcXmDO.getXmid());
        List<BdcXmFbDO> bdcXmFbDOList = bdcXmfbFeignService.listBdcXmFb(bdcXmFbQO);
        bdcSlQlxxymDTO.setBdcXmFbDO(CollectionUtils.isNotEmpty(bdcXmFbDOList) ? bdcXmFbDOList.get(0) : new BdcXmFbDO());
        bdcSlQlxxymDTO.setQllx(bdcXmDO.getQllx());

        //判断是否抵押
        if ((CollectionUtils.isNotEmpty(ydydjxl) && ydydjxl.contains(bdcXmDO.getDjxl())) || CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcXmDO.getQllx())) {
            bdcSlQlxxymDTO.setDydj(true);
        } else {
            bdcSlQlxxymDTO.setDydj(false);
        }
        //获取权利
        BdcQl bdcQl =null;
        if(zxlc != null &&zxlc){
            bdcQl =bdcQllxFeignService.queryBdcYqlxx(bdcXmDO.getXmid());
        }else {
            bdcQl =bdcQllxFeignService.queryQlxx(bdcXmDO.getXmid());
            if(bdcQl ==null){
                bdcQl =bdcQllxFeignService.queryBdcYqlxx(bdcXmDO.getXmid());
            }
        }
        bdcSlQlxxymDTO.setBdcQl(bdcQl);
        //获取权利模板ID
        if (bdcQl != null) {
            String name = bdcQl.getClass().getAnnotation(Table.class).name();
            bdcSlQlxxymDTO.setTableName(StringUtils.lowerCase(name));
            bdcSlQlxxymDTO.setClassName(bdcQl.getClass().getName());
            if (bdcQl instanceof BdcDyaqDO) {

            }
        }
        int lclx = bdcXmFeignService.makeSureBdcXmLx(bdcXmDO.getGzlslid());
        bdcSlQlxxymDTO.setXmlx(lclx);
        return bdcSlQlxxymDTO;
    }

    /**
     * @return 权利信息分页
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 查询抵押权利面积
     */
    @ResponseBody
    @GetMapping("/dymj")
    public Object dymj(String processInsId) {
        boolean sfscql = false;
        //获取当前流程生成的权利的权利类型
        List<String> qllxList = bdcQllxFeignService.listQllxByProcessInsId(processInsId);
        // 流程生成权利
        if (CollectionUtils.isNotEmpty(qllxList)) {
            sfscql = true;
        }
        return bdcBdcdyFeignService.calculatedDyaqMjGyBdclx(processInsId, sfscql);
    }

    @ResponseBody
    @GetMapping(value = "/listyqlxxbypage")
    public Object listyqlxxbypage(@LayuiPageable Pageable pageable, String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("查询原权利缺失参数gzlslid");
        }
        BdcQlQO bdcQlQO = new BdcQlQO();
        bdcQlQO.setGzlslid(gzlslid);
        String bdcQlQoStr = JSON.toJSONString(bdcQlQO);
        return addLayUiCode(bdcXmFeignService.listBdcYqlByPage(pageable, bdcQlQoStr));
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return 是否一本证
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据条件判断是否生成一本证
     */
    @ResponseBody
    @GetMapping("/checkPlZhOne")
    public Boolean checkPlZhOne(String gzlslid, String djxl) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("判断是否生成一本证缺失参数gzlslid");
        }
        //生成一本证
        Boolean plzhOne = false;
        List<BdcCshFwkgSlDO> fwkgSlDOList = new ArrayList<>();
        List<BdcCshFwkgSlDO> bdcCshFwkgSlDOList = bdcXmFeignService.queryFwkgslByGzlslid(gzlslid);
        if (CollectionUtils.isNotEmpty(bdcCshFwkgSlDOList)) {
            //获取所有符合条件的数据
            for (BdcCshFwkgSlDO bdcCshFwkgSlDO : bdcCshFwkgSlDOList) {
                if (StringUtils.isBlank(djxl) || bdcCshFwkgSlDO.getDjxl().equals(djxl)) {
                    fwkgSlDOList.add(bdcCshFwkgSlDO);
                }
            }
        }
        if (CollectionUtils.isNotEmpty(fwkgSlDOList)) {
            //查出所有zsxh为空的数据
            List<BdcCshFwkgSlDO> cshFwkgSlDOList = fwkgSlDOList.stream().filter(xm -> xm.getZsxh() == null).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(cshFwkgSlDOList)) {
                //不存在为空的数据,判断zsxh是否相等
                //根据证书序号分组
                Map<Integer, List<BdcCshFwkgSlDO>> zsxhMap = fwkgSlDOList.stream().collect(Collectors.groupingBy(BdcCshFwkgSlDO::getZsxh));
                if (MapUtils.isNotEmpty(zsxhMap) && zsxhMap.size() == 1) {
                    plzhOne = true;
                }
            }
        }
        return plzhOne;

    }


    private String removeDuplicate(String str) {
        String result = "";
        if (StringUtils.isBlank(str)) {
            return str;
        }
        //根据逗号间隔封装成list
        List<String> strList = Arrays.asList(StringUtils.split(str, CommonConstantUtils.ZF_YW_DH));
        //list去重
        List newList = strList.stream().distinct().collect(Collectors.toList());
        //去重后的list再次封装返回字符串
        result = StringUtils.join(newList, CommonConstantUtils.ZF_YW_DH);
        return result;
    }


    @ResponseBody
    @GetMapping("/qlmc")
    public Object queryBdcQlList(String gzlslid, Boolean zxlc) {
        List<BdcSlQlxxymDTO> bdcSlQlxxymDTOList = new ArrayList<>();
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmList)) {
            //根据登记小类进行分组
            Map<String, List<BdcXmDO>> djxlXmMap = bdcXmList.stream().collect(Collectors.groupingBy(BdcXmDO::getDjxl));
            List<BdcXmDO> sortbdcXmDOList = new ArrayList<>();
            if (MapUtils.isNotEmpty(djxlXmMap)) {
                //整理分组后的数据
                for (Map.Entry<String, List<BdcXmDO>> entry : djxlXmMap.entrySet()) {
                    List<BdcXmDO> djxlXmList = entry.getValue();
                    djxlXmList.sort(Comparator.comparing(BdcXmDO::getXmid));
                    //取分组后各个list中第一条,用于后续排序
                    sortbdcXmDOList.add(djxlXmList.get(0));
                }
                //根据xmid排序
                sortbdcXmDOList.sort(Comparator.comparing(BdcXmDO::getXmid));
                if (CollectionUtils.isNotEmpty(sortbdcXmDOList)) {
                    for (BdcXmDO bdcXmDO : sortbdcXmDOList) {
                        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
                        BdcSlQlxxymDTO bdcSlQlxxymDTO = initBdcSlQlxxymDTO(bdcXmDO,null, zxlc, zdMap);
                        bdcSlQlxxymDTOList.add(bdcSlQlxxymDTO);
                    }
                }
            }
        }
        return bdcSlQlxxymDTOList;
    }


    /**
     * 批量流程页面，不动产单元信息导出功能
     * 与listQlByPageJson获取的不动产单元与权利数据一致，该方法先获取当前流程所有不动产
     * 单元信息，在通过不动产单元获取权利信息。
     *
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: [response, gzlslid, exportCol]
     * @return: String  提示信息
     */
    @ResponseBody
    @RequestMapping("/exportBdcdyXxByGzlslid")
    public String exportGdxx(HttpServletResponse response, String gzlslid, String djxl, String exportCol) throws IOException, WriteException {
        if (StringUtils.isNoneBlank(gzlslid, exportCol)) {
            exportCol = URLDecoder.decode(exportCol, "utf-8");
            LinkedHashMap exportColMap = JSONObject.parseObject(exportCol, LinkedHashMap.class);
            String fileName = "不动产单元附页导出Excel表" + System.currentTimeMillis() + ".xls";
            BdcBdcdyQO bdcBdcdyQO = new BdcBdcdyQO();
            bdcBdcdyQO.setGzlslid(gzlslid);
            bdcBdcdyQO.setDjxl(djxl);
            List<BdcBdcdyVO> bdcBdcdyVOList = bdcBdcdyFeignService.queryBdcdyList(bdcBdcdyQO);
            List<Map> bdcQlList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(bdcBdcdyVOList)) {
                //  通过不动产单元获取权利信息
                for (BdcBdcdyVO data : bdcBdcdyVOList) {
                    if (data != null) {
                        bdcQlList.add(setQlxx(data, null));
                    }
                }
                // 根据不动产单元号对权利信息数据进行去重
                bdcQlList = bdcQlList.stream().filter(
                        StreamEx.distinctByKey(x -> x.get("bdcdyh"))
                ).collect(Collectors.toList());
            }
            Map<String, List<Map>> zdListMap = bdcZdController.initzd();
            ExportUtils.exportExcel(fileName, exportColMap, JSON.parseArray(JSON.toJSONString(bdcQlList), Map.class),
                    response, zdListMap);
        }
        return "导出成功";
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 处理外联证书字段
     */
    private String queryWlzsPl(String gzlslid, List<String> xmidList) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("工作流实例ID为空");
        }
        StringBuilder wlzs;
        String qszt = "";
        //查询外联原项目
        BdcYxmQO bdcYxmQO = new BdcYxmQO();
        bdcYxmQO.setGzlslid(gzlslid);
        bdcYxmQO.setWlxm(CommonConstantUtils.SF_S_DM);
        if (CollectionUtils.isNotEmpty(xmidList)) {
            bdcYxmQO.setXmidList(xmidList);
        }
        bdcYxmQO.setZxyql(CommonConstantUtils.SF_S_DM);
        List<BdcXmDO> yzxXmDOList = bdcXmFeignService.listYxmByYxmQO(bdcYxmQO);
        bdcYxmQO.setZxyql(CommonConstantUtils.SF_F_DM);
        List<BdcXmDO> ybzxXmDOList = bdcXmFeignService.listYxmByYxmQO(bdcYxmQO);
        // 当存在外联项目时
        Set<String> wlzsList = new HashSet<>();
        if (CollectionUtils.isNotEmpty(ybzxXmDOList) || CollectionUtils.isNotEmpty(yzxXmDOList)) {
            List<Map> qsztZdMap = bdcZdFeignService.queryBdcZd("qszt");
            if (CollectionUtils.isNotEmpty(yzxXmDOList)) {
                for (BdcXmDO yxmxx : yzxXmDOList) {
                    qszt = StringToolUtils.convertBeanPropertyValueOfZd(yxmxx.getQszt(), qsztZdMap);
                    // 组织外联证书字段
                    if (StringUtils.isNotBlank(yxmxx.getBdcqzh())) {
                        wlzs = new StringBuilder();
                        if (CommonConstantUtils.BOOL_FALSE.equals(wlzsnr)) {
                            wlzs.append(yxmxx.getBdcqzh()).append("/关联并注销  ");
                        } else {
                            wlzs.append(yxmxx.getBdcqzh()).append("/注销  ").append(qszt);
                        }
                        wlzsList.add(wlzs.toString());
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(ybzxXmDOList)) {
                for (BdcXmDO yxmxx : ybzxXmDOList) {
                    qszt = StringToolUtils.convertBeanPropertyValueOfZd(yxmxx.getQszt(), qsztZdMap);
                    // 组织外联证书字段
                    if (StringUtils.isNotBlank(yxmxx.getBdcqzh())) {
                        wlzs = new StringBuilder();
                        String status = "";
                        if (CommonConstantUtils.BOOL_FALSE.equals(wlzsnr)) {
                            status = "/只关联不注销  ";
                        } else {
                            status = "/不注销  ";
                        }
                        // 31461 合肥版本外联证书为历史状态的也展示为注销
                        if (CommonConstantUtils.SYSTEM_VERSION_HF.equals(systemVersion)
                                && CommonConstantUtils.QSZT_HISTORY.equals(yxmxx.getQszt())) {
                            status = "/注销 ";
                        }
                        if (CommonConstantUtils.BOOL_FALSE.equals(wlzsnr)) {
                            wlzs.append(yxmxx.getBdcqzh()).append(status);
                        } else {
                            wlzs.append(yxmxx.getBdcqzh()).append(status).append(qszt);
                        }
                        wlzsList.add(wlzs.toString());
                    }
                }
            }
        }
        if (CollectionUtils.isNotEmpty(wlzsList)) {
            return String.join(CommonConstantUtils.ZF_YW_DH, wlzsList);
        }
        return "";
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return Map<Object, List < BdcXmDO>>
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 生成分组项目信息
     */
    private Map<String, List<BdcXmDO>> scGroupXmMap(String gzlslid) {
        //key值为项目id,value为项目集合
        Map<String, List<BdcXmDO>> groupXmMap = new HashMap<>();
        //key为zsxh或登记小类，value初始化房屋开关
        Map<Object, List<BdcCshFwkgSlDO>> groupFwkgSlMap = new HashMap<>();
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmList = bdcXmFeignService.listBdcXm(bdcXmQO);
        List<BdcCshFwkgSlDO> cshFwkgSlDOList = bdcXmFeignService.queryFwkgslByGzlslid(gzlslid);
        if (CollectionUtils.isNotEmpty(cshFwkgSlDOList) && CollectionUtils.isNotEmpty(bdcXmList)) {
            //先查询出证书序号为空的或者不是抵押权的数据
            //抵押存在一押二押的情况，生成不同的证明，根据证书序号分组
            List<BdcCshFwkgSlDO> nullZsxhBdcCshFwkgSlDOS = cshFwkgSlDOList.stream().filter(xm -> xm.getZsxh() == null || !Objects.equals(CommonConstantUtils.QLLX_DYAQ_DM, xm.getQllx())).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(nullZsxhBdcCshFwkgSlDOS)) {
                //为空的按登记小类分组
                Map<String, List<BdcCshFwkgSlDO>> djxlXmMap = nullZsxhBdcCshFwkgSlDOS.stream().collect(Collectors.groupingBy(BdcCshFwkgSlDO::getDjxl));
                if (MapUtils.isNotEmpty(djxlXmMap)) {
                    groupFwkgSlMap.putAll(djxlXmMap);
                }
                cshFwkgSlDOList.removeAll(nullZsxhBdcCshFwkgSlDOS);

            }
            //不为空的按照证书序号分组 且是抵押权的
            if (CollectionUtils.isNotEmpty(cshFwkgSlDOList)) {
                Map<Integer, List<BdcCshFwkgSlDO>> zsxhXmMap = cshFwkgSlDOList.stream().collect(Collectors.groupingBy(BdcCshFwkgSlDO::getZsxh));
                if (MapUtils.isNotEmpty(zsxhXmMap)) {
                    groupFwkgSlMap.putAll(zsxhXmMap);

                }
            }
        }
        if (MapUtils.isNotEmpty(groupFwkgSlMap)) {
            for (Map.Entry<Object, List<BdcCshFwkgSlDO>> entry : groupFwkgSlMap.entrySet()) {
                List<BdcCshFwkgSlDO> groupCshFwkgSlDOList = entry.getValue();
                List<BdcCshFwkgSlDO> zfList = new ArrayList<>();
                List<BdcCshFwkgSlDO> fzfList = new ArrayList<>();
                List<BdcCshFwkgSlDO> sortKgslList = new ArrayList<>(groupCshFwkgSlDOList.size());
                //按照项目ID先排序
                groupCshFwkgSlDOList.sort(Comparator.comparing(BdcCshFwkgSlDO::getId));
                //获取主房的数据
                for (BdcCshFwkgSlDO bdcCshFwkgSlDO : groupCshFwkgSlDOList) {
                    if (Objects.nonNull(bdcCshFwkgSlDO.getSfzf()) && Objects.equals(CommonConstantUtils.SF_S_DM, bdcCshFwkgSlDO.getSfzf())) {
                        zfList.add(bdcCshFwkgSlDO);
                    } else {
                        fzfList.add(bdcCshFwkgSlDO);
                    }
                }
                sortKgslList.addAll(zfList);
                sortKgslList.addAll(fzfList);
                List<BdcXmDO> groupXmList = new ArrayList<>();
                for (BdcCshFwkgSlDO bdcCshFwkgSlDO : sortKgslList) {
                    //匹配对应的不动产项目
                    for (BdcXmDO bdcXmDO : bdcXmList) {
                        if (StringUtils.equals(bdcXmDO.getXmid(), bdcCshFwkgSlDO.getId())) {
                            groupXmList.add(bdcXmDO);
                            break;
                        }
                    }
                }
                groupXmMap.put(groupXmList.get(0).getXmid(), groupXmList);
            }


        }
        return groupXmMap;

    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 房屋用途面积统计功能
     */
    @ResponseBody
    @PostMapping("/calculateBdcdyMjByYt")
    public Object calculateBdcdyMjByYt(@RequestBody BdcBdcdyQO bdcBdcdyQO) {
        if (StringUtils.isBlank(bdcBdcdyQO.getGzlslid())) {
            throw new AppException("房屋用途面积统计功能缺失参数工作流实例ID");
        }
        List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(bdcBdcdyQO.getGzlslid());
        if (CollectionUtils.isNotEmpty(bdcXmDTOList) && CollectionUtils.isNotEmpty(fwyttjDyidList) && fwyttjDyidList.contains(bdcXmDTOList.get(0).getGzldyid())) {
            return bdcBdcdyFeignService.getSumMjByGhyt(bdcBdcdyQO);
        } else {
            return null;

        }


    }

    /**
     * @param xmidList 当前项目ID集合
     * @return 承包权利项目信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取原有的承包权利项目信息
     */
    @ResponseBody
    @PostMapping("/listYCbxxByJyqxm")
    public List<Map<String, Object>> listYCbxxByJyqxm(@RequestBody List<String> xmidList) {
        List<Map<String, Object>> bdcCbjbxxVOList = new ArrayList<>();
        //获取原有承包产权
        List<BdcXmDO> cqXmDOList = bdcXmFeignService.listPrevCqXmPl(xmidList, true);
        if (CollectionUtils.isNotEmpty(cqXmDOList)) {
            List<String> cqxmidList = new ArrayList<>();
            cqXmDOList.forEach(bdcXmDO -> {
                cqxmidList.add(bdcXmDO.getXmid());
            });
            //获取承包经营权
            List<BdcQl> bdcQlList = bdcQllxFeignService.listQlxxByXmids(cqxmidList);

            //获取宗地基本信息
            Map<String, BdcBdcdjbZdjbxxDO> zdjbxxDOMap = new HashMap<>(cqXmDOList.size());
            List<String> bdcdyhList = cqXmDOList.stream().map(BdcXmDO::getBdcdyh).distinct().collect(Collectors.toList());
            for (String bdcdyh : bdcdyhList) {
                BdcBdcdjbZdjbxxDO bdcdjbZdjbxxDO = bdcDjbxxFeignService.queryBdcBdcdjbZdjbxx(bdcdyh);
                if (bdcdjbZdjbxxDO != null) {
                    zdjbxxDOMap.put(bdcdyh, bdcdjbZdjbxxDO);
                }
            }

            for (BdcXmDO cbqXm : cqXmDOList) {
                Map<String, Object> bdcCbjbxxVO = new HashMap<>();
                bdcCbjbxxVO.putAll(Object2MapUtils.object2MapExceptNull(zdjbxxDOMap.get(cbqXm.getBdcdyh())));

                if (CollectionUtils.isNotEmpty(bdcQlList)) {
                    //匹配对应的不动产承包权
                    for (BdcQl bdcQl : bdcQlList) {
                        if (StringUtils.equals(cbqXm.getXmid(), bdcQl.getXmid())) {
                            if (bdcQl instanceof BdcTdcbnydsyqDO) {
                                bdcCbjbxxVO.putAll(Object2MapUtils.object2MapExceptNull(bdcQl));
                            }
                            break;
                        }
                    }
                }
                bdcCbjbxxVO.putAll(Object2MapUtils.object2MapExceptNull(cbqXm));
                bdcCbjbxxVOList.add(bdcCbjbxxVO);
            }
        }

        return bdcCbjbxxVOList;
    }


    /**
     * @param xmid xmid
     * @return ghyt
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 根据xmid获取规划用途
     */
    @ResponseBody
    @GetMapping("/getGhytByXmid/{xmid}")
    public String getGhytByXmid(@PathVariable("xmid") String xmid) {
        return bdcQllxFeignService.getGhytByXmid(xmid);
    }

    /**
     * @param xmid xmid
     * @return ghyt
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 根据xmid获取sfzf
     */
    @ResponseBody
    @GetMapping("/fwkg/sfzf/{xmid}")
    public Object getSfzfByXmid(@PathVariable("xmid") String xmid) {
        BdcCshFwkgSlDO bdcCshFwkgSlDO = bdcXmFeignService.queryFwkgsl(xmid);
        return bdcCshFwkgSlDO.getSfzf();
    }


    /**
     * @param bdcCshFwkgSlDO
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 保存是否主房
     */
    @PutMapping("/saveSfzfByXmid")
    public void updateSfzf(@RequestBody BdcCshFwkgSlDO bdcCshFwkgSlDO) {
        if (bdcCshFwkgSlDO != null && StringUtils.isNotBlank(bdcCshFwkgSlDO.getId())) {
            BdcCshFwkgSlDO bdcCshFwkgSlDOT = bdcXmFeignService.queryFwkgsl(bdcCshFwkgSlDO.getId());
            bdcCshFwkgSlDOT.setSfzf(bdcCshFwkgSlDO.getSfzf());
            bdcXmFeignService.updateCshFwkgSl(bdcCshFwkgSlDOT);
        }
    }


    /**
     * @param gzlslid xmids
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 保存后更新权力信息附记和项目表权力其他状况
     * @date : 2020/9/16 15:36
     */
    @ResponseBody
    @GetMapping("/qlqtzkfj/nthm")
    public void updateQlqtzkFj(String gzlslid, String xmids) {
        if (StringUtils.isBlank("gzlslid") && StringUtils.isBlank(xmids)) {
            throw new AppException("查询项目信息缺失必要参数");
        }
        List<String> xmidList = new ArrayList<>(xmids.length());
        if (StringUtils.isNotBlank(xmids)) {
            xmidList = Arrays.asList(xmids.split(CommonConstantUtils.ZF_YW_DH));
        } else {
            List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
                xmidList = bdcXmDTOList.stream().map(BdcXmDTO::getXmid).collect(Collectors.toList());
            }
        }
        if (CollectionUtils.isNotEmpty(xmidList)) {
            for (String xmid : xmidList) {
                queryQlqtzkFjMb(xmid, "2", true, true);
                queryQlqtzkFjMb(xmid, "3", true, true);
            }
        }
    }

    @ResponseBody
    @GetMapping("/zdjbxx")
    public Object zdJbxx(String bdcdyh) {
        if (StringUtils.isBlank(bdcdyh)) {
            throw new AppException("查询宗地基本信息bdcdyh为空");
        }
        return bdcDjbxxFeignService.queryBdcBdcdjbZdjbxx(bdcdyh);
    }

    @ResponseBody
    @PatchMapping("/zdjbxx")
    public Object updateZdJbxx(@RequestBody String json) {
        if (StringUtils.isBlank(json)) {
            throw new AppException("更新宗地基本信息数据为空");
        }
        JSONObject obj = JSONObject.parseObject(json);
        if (obj.containsKey("zddm")) {
            return bdcEntityFeignService.updateByJsonEntity(json, BdcBdcdjbZdjbxxDO.class.getName());
        }
        return null;

    }

    //页面展现的司法裁决的信息
    @ResponseBody
    @GetMapping("/sfcj")
    public Object querySfcjxx(BdcSlQlxxymQO bdcSlQlxxymQO) {
        Map<String, List<Map>> zdMap = new HashMap<>();
        List<BdcSlQlxxymDTO> bdcSlQlxxymDTOList = new ArrayList<>();
        try {
            zdMap = bdcZdFeignService.listBdcZd();
        } catch (Exception e) {
            LOGGER.error("字典项服务获取失败");
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(bdcSlQlxxymQO.getProcessInsId());
        List<BdcXmDO> bdcXmList = bdcXmFeignService.listBdcXm(bdcXmQO);
        BdcSlQlxxymDTO bdcSlQlxxymDTO = new BdcSlQlxxymDTO();
        String xmid = "";
        if (CollectionUtils.isNotEmpty(bdcXmList)) {
            BdcXmDO bdcXmDO = bdcXmList.get(0);
            xmid = bdcXmDO.getXmid();
            if (bdcSlQlxxymQO.getSfcxql()) {
                //项目信息
                BdcXmFbQO bdcXmFbQO = new BdcXmFbQO();
                bdcXmFbQO.setXmid(bdcXmDO.getXmid());
                List<BdcXmFbDO> bdcXmFbDOList = bdcXmfbFeignService.listBdcXmFb(bdcXmFbQO);
                BdcQl bdcQl = bdcQllxFeignService.queryQlxx(bdcXmDO.getXmid());
                BdcQl bdcYql = bdcQllxFeignService.queryBdcYqlxx(bdcXmDO.getXmid());
                if (bdcQl == null) {
                    bdcQl = bdcYql;
                }
                if (bdcQl != null) {
                    String name = bdcQl.getClass().getAnnotation(Table.class).name();
                    bdcSlQlxxymDTO.setTableName(StringUtils.lowerCase(name));
                    bdcSlQlxxymDTO.setBdcQl(bdcQl);
                } else {
                    throw new AppException("项目ID:" + bdcXmDO.getXmid() + "未查询到权利信息！");
                }
                if (bdcSlQlxxymQO.getQtsj()) {
                    //查找登记小类，登记原因关系
                    BdcDjxlDjyyQO bdcDjxlDjyyQO = new BdcDjxlDjyyQO();
                    bdcDjxlDjyyQO.setDjxl(bdcXmDO.getDjxl());
                    bdcDjxlDjyyQO.setGzldyid(bdcXmDO.getGzldyid());
                    bdcSlQlxxymDTO.setBdcDjxlDjyyGxDOList(bdcXmFeignService.listBdcDjxlDjyyGxWithParam(bdcDjxlDjyyQO));
                    //设置外联证书字段
                    bdcSlQlxxymDTO.setWlzs(queryWlzs(bdcXmDO.getXmid()));
                    //查找领证人信息
                    //先判断是否展示
                    boolean sfzs = true;
                    if (StringUtils.isNotBlank(bdcXmDO.getXmid()) && StringUtils.isNotBlank(lzrBzsDjxl)) {
                        sfzs = !lzrBzsDjxl.contains(bdcXmDO.getDjxl());
                    }
                    //查询领证人数据
                    if (sfzs) {
                        BdcLzrQO bdcLzrQO = new BdcLzrQO();
                        bdcLzrQO.setXmid(bdcXmDO.getXmid());
                        List<BdcLzrDO> bdcLzrDOList = bdcLzrFeignService.listBdcLzr(bdcLzrQO);
                        if (CollectionUtils.isNotEmpty(bdcLzrDOList)) {
                            bdcSlQlxxymDTO.setBdcLzrDOList(bdcLzrDOList);
                        }
                    }
                    //查找第三权利人信息
                    if (CollectionUtils.isEmpty(bdcSlQlxxymDTO.getBdcDsQlrDOList())) {
                        List<BdcDsQlrDO> bdcDsQlrDOList = slymQlrController.listBdcDsQlr(bdcXmDO.getXmid());
                        if (CollectionUtils.isNotEmpty(bdcDsQlrDOList)) {
                            bdcSlQlxxymDTO.setBdcDsQlrDOList(bdcDsQlrDOList);
                        }
                    }
                }
                //展示权利原权利按钮
                if (StringUtils.equals(CommonConstantUtils.BOOL_TRUE, bdcSlQlxxymQO.getZxlc())) {
                    bdcSlQlxxymDTO.setShowYqlBtn("showYqlBtn");
                } else if (Objects.nonNull(bdcYql)) {
                    bdcSlQlxxymDTO.setShowYqlBtn("showAllBtn");
                }
                //查封信息
                if (bdcSlQlxxymQO.getCxCfxx()) {
                    Integer dyhqllx = acceprBdcQllxFeignService.getQllxByBdcdyh(bdcXmDO.getBdcdyh(), "1");
                    bdcSlQlxxymDTO.setDyhqllx(StringToolUtils.convertBeanPropertyValueOfZd(dyhqllx, zdMap.get("qllx")));
                    bdcSlQlxxymDTO.setSlymCfxxDTOList(this.listBdcCfxx(bdcSlQlxxymQO.getProcessInsId()));
                    List<BdcXmDO> cqXmList = bdcXmFeignService.listXscqXm(bdcXmList);
                    List<BdcCfDO> yCfList = bdcCfxxFeignService.listYcfxxByGzlslid(bdcSlQlxxymQO.getProcessInsId());
                    bdcSlQlxxymDTO.setCqXmList(combineYcfbh(cqXmList, yCfList, bdcXmList,CollectionUtils.isNotEmpty(bdcXmFbDOList)?bdcXmFbDOList.get(0).getQjgldm():""));
                }

                bdcSlQlxxymDTO.setBdcXmFbDO(CollectionUtils.isNotEmpty(bdcXmFbDOList) ? bdcXmFbDOList.get(0) : new BdcXmFbDO());
                bdcSlQlxxymDTO.setBdcXm(bdcXmDO);
                bdcSlQlxxymDTO.setQllx(bdcXmDO.getQllx());
            }
        }

        //查询单元锁定信息
        BdcDysdQO bdcDysdQO = new BdcDysdQO();
        bdcDysdQO.setSdlx(CommonConstantUtils.SDLX_CJSD);
        bdcDysdQO.setGzlslid(bdcSlQlxxymQO.getProcessInsId());
        List<BdcDysdDO> bdcDysdDOList = bdcBdcdyFeignService.listBdcDysd(bdcDysdQO);
        if (CollectionUtils.isEmpty(bdcDysdDOList) && bdcSlQlxxymQO.getSfcxdj() && StringUtils.isNotBlank(xmid)) {
            BdcXmLsgxQO bdcXmLsgxQO = new BdcXmLsgxQO();
            bdcXmLsgxQO.setXmid(xmid);
            bdcXmLsgxQO.setWlxm(CommonConstantUtils.SF_S_DM);
            List<BdcXmLsgxDO> bdcXmLsgxDOList = bdcLsgxFeignService.listXmLsgxByXmid(bdcXmLsgxQO);
            if (CollectionUtils.isNotEmpty(bdcXmLsgxDOList)) {
                bdcDysdQO = new BdcDysdQO();
                bdcDysdQO.setSdlx(CommonConstantUtils.SDLX_CJSD);
                bdcDysdQO.setXmid(bdcXmLsgxDOList.get(0).getYxmid());
                bdcDysdDOList = bdcBdcdyFeignService.listBdcDysd(bdcDysdQO);
            }
        }
        if (CollectionUtils.isNotEmpty(bdcDysdDOList)) {
            bdcSlQlxxymDTO.setBdcDysdDO(bdcDysdDOList.get(0));
        } else {
            bdcSlQlxxymDTO.setBdcDysdDO(new BdcDysdDO());
        }
        bdcSlQlxxymDTOList.add(bdcSlQlxxymDTO);
        return bdcSlQlxxymDTOList;
    }


    //更新司法裁决锁定信息
    @ResponseBody
    @PatchMapping("/sfcj")
    public Object updateSfcjSd(@RequestBody String json, String gzlslid, Boolean cxdj) {
        //更新锁定表信息
        if (StringUtils.isNotBlank(json) && StringUtils.isNotBlank(gzlslid)) {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setGzlslid(gzlslid);
            List<BdcXmDO> bdcXmDOLsit = bdcXmFeignService.listBdcXm(bdcXmQO);
            //如果存在批量的要批量更新
            BdcDysdDO bdcDysdDO = JSON.parseObject(json, BdcDysdDO.class);
            if (CollectionUtils.isNotEmpty(bdcXmDOLsit)) {
                for (BdcXmDO bdcXmDO : bdcXmDOLsit) {
                    BdcDysdQO bdcDysdQO = new BdcDysdQO();
                    bdcDysdQO.setXmid(bdcXmDO.getXmid());
                    bdcDysdQO.setSdlx(9);
                    bdcDysdQO.setGzlslid(gzlslid);
                    List<BdcDysdDO> bdcDysdDOList = bdcBdcdyFeignService.listBdcDysd(bdcDysdQO);
                    if (CollectionUtils.isEmpty(bdcDysdDOList) && cxdj) {
                        //如果当前没有查询到单元锁定数据且是撤销登记，查询外联项目的单元锁定数据并进行更新操作
                        BdcXmLsgxQO bdcXmLsgxQO = new BdcXmLsgxQO();
                        bdcXmLsgxQO.setXmid(bdcXmDO.getXmid());
                        bdcXmLsgxQO.setWlxm(CommonConstantUtils.SF_S_DM);
                        List<BdcXmLsgxDO> bdcXmLsgxDOList = bdcLsgxFeignService.listXmLsgxByXmid(bdcXmLsgxQO);
                        if (CollectionUtils.isNotEmpty(bdcXmLsgxDOList)) {
                            bdcDysdQO = new BdcDysdQO();
                            bdcDysdQO.setXmid(bdcXmLsgxDOList.get(0).getYxmid());
                            bdcDysdQO.setSdlx(9);
                            bdcDysdDOList = bdcBdcdyFeignService.listBdcDysd(bdcDysdQO);
                        }
                    }
                    if (CollectionUtils.isNotEmpty(bdcDysdDOList)) {
                        BdcDysdDO bdcDysd = bdcDysdDOList.get(0);
                        if (StringUtils.equals(bdcDysd.getDysdid(), bdcDysdDO.getDysdid())) {
                            //如果是当前页面的数据，就直接更新当前页面的信息
                            bdcEntityFeignService.updateByJsonEntity(JSON.toJSONString(bdcDysdDO), BdcDysdDO.class.getName());
                        } else {
                            bdcDysd.setSdsqjg(bdcDysdDO.getSdsqjg());
                            bdcDysd.setSdsqwh(bdcDysdDO.getSdsqwh());
                            bdcEntityFeignService.updateByJsonEntity(JSON.toJSONString(bdcDysd), BdcDysdDO.class.getName());
                        }
                    }
                }
            }
            //找所有历史关系
            List<BdcXmLsgxDO> bdcXmLsgxDOList = bdcLsgxFeignService.listXmLsgxBySlid(gzlslid);
            List<String> yXmidList = bdcXmLsgxDOList.stream().map(BdcXmLsgxDO::getYxmid).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(yXmidList)) {
                List<BdcQl> bdcQlList = bdcQllxFeignService.listQlxxByXmids(yXmidList);
                List<String> cfXmidList = new ArrayList<>(CollectionUtils.size(bdcQlList));
                for (BdcQl bdcQl : bdcQlList) {
                    if (bdcQl instanceof BdcCfDO) {
                        cfXmidList.add(bdcQl.getXmid());
                    }
                }
                BdcDjxxUpdateQO bdcDjxxUpdateQO = new BdcDjxxUpdateQO();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("jfjg", bdcDysdDO.getSdsqjg());
                jsonObject.put("jfwh", bdcDysdDO.getSdsqwh());
                bdcDjxxUpdateQO.setClassName(BdcCfDO.class.getName());
                Map whereMap = new HashMap();
                whereMap.put("xmids", cfXmidList);
                bdcDjxxUpdateQO.setJsonStr(JSON.toJSONString(jsonObject));
                bdcDjxxUpdateQO.setWhereMap(whereMap);
                try {
                    bdcQllxFeignService.updateBatchBdcQl(bdcDjxxUpdateQO);
                } catch (Exception e) {
                    LOGGER.error("更新查封权利报错{}", e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取第三权利人信息
     */
    private void getDsQlrxx(BdcSlQlxxymDTO bdcSlQlxxymDTO, String xmid, Map<String, List<Map>> zdMap) {
        BdcDsQlrQO bdcDsQlrQO = new BdcDsQlrQO();
        bdcDsQlrQO.setXmid(xmid);
        List<BdcDsQlrDO> bdcDsQlrDOList = bdcQlrFeignService.listBdcDsQlr(bdcDsQlrQO);
        if (CollectionUtils.isNotEmpty(bdcDsQlrDOList)) {
            bdcSlQlxxymDTO.setBdcDsQlrDOList(bdcDsQlrDOList);
            //债务人
            List<BdcDsQlrDO> zwrList = bdcDsQlrDOList.stream().filter(dsqlr -> StringUtils.equals(CommonConstantUtils.DSQLR_QLRLB_ZWR, dsqlr.getQlrlb())).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(zwrList)) {
                bdcSlQlxxymDTO.setZwr(StringToolUtils.resolveBeanToAppendStr(zwrList, "getQlrmc", CommonConstantUtils.ZF_YW_DH));
                bdcSlQlxxymDTO.setZwrzjzl(StringToolUtils.convertBeanPropertiesValueOfZd(zwrList, "zjzl", zdMap.get("zjzl"),""));
                bdcSlQlxxymDTO.setZwrzjh(StringToolUtils.resolveBeanToAppendStr(zwrList, "getZjh", CommonConstantUtils.ZF_YW_DH));
            }

        }

    }

    public String queryWlzs(String xmid) {
        if (StringUtils.isBlank(xmid)) {
            throw new AppException("项目id为空");
        }
        StringBuilder wlzs;
        String qszt = "";
        // 查询外联项目
        BdcXmLsgxQO bdcXmLsgxQO = new BdcXmLsgxQO();
        bdcXmLsgxQO.setXmid(xmid);
        bdcXmLsgxQO.setWlxm(CommonConstantUtils.SF_S_DM);
        List<BdcXmLsgxDO> bdcXmLsgxDOList = bdcLsgxFeignService.listXmLsgxByXmid(bdcXmLsgxQO);
        // 当存在外联历史关系时 查询外联项目
        Set<String> wlzsList = new HashSet<>();
        if (CollectionUtils.isNotEmpty(bdcXmLsgxDOList)) {
            for (BdcXmLsgxDO bdcXmLsgxDO : bdcXmLsgxDOList) {
                wlzs = new StringBuilder();
                if (StringUtils.isNotBlank(bdcXmLsgxDO.getYxmid())) {
                    BdcXmQO bdcXmQO = new BdcXmQO();
                    bdcXmQO.setXmid(bdcXmLsgxDO.getYxmid());
                    List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
                    // 当存在外联项目时
                    if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                        List<Map> qsztZdMap = bdcZdFeignService.queryBdcZd("qszt");
                        qszt = StringToolUtils.convertBeanPropertyValueOfZd(bdcXmDOList.get(0).getQszt(), qsztZdMap);
                        // 组织外联证书字段
                        if (StringUtils.isNotBlank(bdcXmDOList.get(0).getBdcqzh())) {
                            wlzs.append(bdcXmDOList.get(0).getBdcqzh());
                            String status = "";
                            if (bdcXmLsgxDO.getZxyql() != null && CommonConstantUtils.SF_S_DM.equals(bdcXmLsgxDO.getZxyql())) {
                                if (CommonConstantUtils.BOOL_FALSE.equals(wlzsnr)) {
                                    status = "/关联并注销";
                                } else {
                                    status = "/注销";
                                }
                            } else {
                                if (CommonConstantUtils.BOOL_FALSE.equals(wlzsnr)) {
                                    status = "/只关联不注销";
                                } else {
                                    status = "/不注销";
                                }

                            }
                            // 31461 合肥版本外联证书为历史状态的也展示为注销
                            if (CommonConstantUtils.SYSTEM_VERSION_HF.equals(systemVersion)
                                    && CommonConstantUtils.QSZT_HISTORY.equals(bdcXmDOList.get(0).getQszt())) {
                                status = "/注销";
                            }
                            if (CommonConstantUtils.BOOL_FALSE.equals(wlzsnr)) {
                                wlzs.append(status).append(" ");
                            } else {
                                wlzs.append(status).append(" ").append(qszt);
                            }

                        }
                        wlzsList.add(wlzs.toString());
                    }
                }
            }
        }
        if (CollectionUtils.isNotEmpty(wlzsList)) {
            return String.join(CommonConstantUtils.ZF_YW_DH, wlzsList);
        }
        return "";
    }


    public List<SlymCfxxDTO> listBdcCfxx(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("查询查封信息缺失工作流实例id");
        }
        List<SlymCfxxDTO> slymCfxxDTOList = new ArrayList<>();
        List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        List<String> bdcdyhList = new ArrayList<>(CollectionUtils.size(bdcXmDTOList));
        if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
            for (BdcXmDTO bdcXmDTO : bdcXmDTOList) {
                bdcdyhList.add(bdcXmDTO.getBdcdyh());
            }
        }
        BdcCfxxQO bdcCfxxQO = new BdcCfxxQO();
        bdcCfxxQO.setBdcdyhList(bdcdyhList);
        List<BdcCfDTO> bdcCfxxDTOList = bdcCfxxFeignService.listBdcCfxxByCfsx(bdcCfxxQO);
        SlymCfxxDTO slymCfxxDTO = new SlymCfxxDTO();
        slymCfxxDTO.setBdcCfDOList(bdcCfxxDTOList);
        slymCfxxDTOList.add(slymCfxxDTO);
        return slymCfxxDTOList;
    }

    @ResponseBody
    @GetMapping("/jfxx")
    public Object queryJfxx(String xmid) {
        if (StringUtils.isNotBlank(xmid)) {
            //下一手是查封且没有生成权利的
            BdcXmLsgxQO bdcXmLsgxQO = new BdcXmLsgxQO();
            bdcXmLsgxQO.setYxmid(xmid);
            List<BdcXmLsgxDO> bdcXmLsgxDOList = bdcLsgxFeignService.listXmLsgxByXmid(bdcXmLsgxQO);
            if (CollectionUtils.isNotEmpty(bdcXmLsgxDOList)) {
                for (BdcXmLsgxDO bdcXmLsgxDO : bdcXmLsgxDOList) {
                    BdcXmQO bdcXmQO = new BdcXmQO(bdcXmLsgxDO.getXmid());
                    List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
                    if (CollectionUtils.isNotEmpty(bdcXmDOList) && CommonConstantUtils.QLLX_CF.equals(bdcXmDOList.get(0).getQllx())) {
                        BdcQl bdcQl = bdcQllxFeignService.queryQlxx(bdcXmDOList.get(0).getXmid());
                        if (Objects.isNull(bdcQl)) {
                            return bdcXmDOList.get(0);
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 抵押权页面查看产权信息
     */
    private List<SlymCqxxDTO> listSlymCqxx(List<BdcXmDO> allXmList, String processInsId, BdcQl bdcQl) {
        List<SlymCqxxDTO> slymCqxxDTOList = new ArrayList<>(allXmList.size());
        List<String> fwDyhList = new ArrayList<>();
        List<String> tdDyhList = new ArrayList<>();
        for (BdcXmDO bdcXm : allXmList) {
            SlymCqxxDTO slymCqxxDTO = new SlymCqxxDTO();
            slymCqxxDTO.setBdcXmDO(bdcXm);
            //不是纯土地的查房地产权信息
            // 2021-10-27 居住权需要展示不动产基本信息模块数据，添加了判断
            if (!Objects.equals(1, bdcXm.getBdclx())) {
                fwDyhList.add(bdcXm.getBdcdyh());
            } else {
                tdDyhList.add(bdcXm.getBdcdyh());
            }
//            //bdcxm转map
//            Map bdcXmMap = Object2MapUtils.object2MapExceptNull(bdcXm);
//            slymCqxxDTO.setBdcXmMap(bdcXmMap);
            slymCqxxDTOList.add(slymCqxxDTO);
        }
        //查询项目附表信息
        BdcXmFbQO bdcXmFbQO = new BdcXmFbQO();
        bdcXmFbQO.setGzlslid(processInsId);
        List<BdcXmFbDO> bdcXmFbDOList = bdcXmfbFeignService.listBdcXmFb(bdcXmFbQO);
        Map<String, BdcXmFbDO> xmidXmfbMap = new HashMap();
        if (CollectionUtils.isNotEmpty(bdcXmFbDOList)) {
            for (BdcXmFbDO xmFbDO : bdcXmFbDOList) {
                xmidXmfbMap.put(xmFbDO.getXmid(), xmFbDO);
            }
        }
        //查询房屋产权信息
        Map<String, Map> dyhCqxxMap = new HashMap();
        if (CollectionUtils.isNotEmpty(fwDyhList)) {
            BdcCqQO bdcCqQO = new BdcCqQO();
            bdcCqQO.setBdcdyhList(fwDyhList);
            bdcCqQO.setQsztList(Arrays.asList(CommonConstantUtils.QSZT_VALID, CommonConstantUtils.QSZT_TEMPORY));
            List<Map> fdcqXmxxList = bdcBdcdyFeignService.listBdcFdcqXmxx(bdcCqQO);
            if (CollectionUtils.isNotEmpty(fdcqXmxxList)) {
                for (Map map : fdcqXmxxList) {
                    if (map.get("BDCDYH") != null) {
                        dyhCqxxMap.put(map.get("BDCDYH").toString(), map);
                    }
                }
            }
        }
        //查询土地产权信息
        Map<String, Map> dyhJsydsyqxxMap = new HashMap();
        if (CollectionUtils.isNotEmpty(tdDyhList)) {
            BdcCqQO bdcCqQO = new BdcCqQO();
            bdcCqQO.setBdcdyhList(tdDyhList);
            bdcCqQO.setQsztList(Arrays.asList(CommonConstantUtils.QSZT_VALID, CommonConstantUtils.QSZT_TEMPORY));
            List<Map> jsydsyqXmxxList = bdcBdcdyFeignService.listBdcJsydsyqXmxx(bdcCqQO);
            if (CollectionUtils.isNotEmpty(jsydsyqXmxxList)) {
                for (Map map : jsydsyqXmxxList) {
                    if (map.get("BDCDYH") != null) {
                        dyhJsydsyqxxMap.put(map.get("BDCDYH").toString(), map);
                    }
                }
            }
        }
        //组织数据
        if (CollectionUtils.isNotEmpty(slymCqxxDTOList)) {

            for (SlymCqxxDTO slymCqxxDTO : slymCqxxDTOList) {
                if (StringUtils.isNotBlank(slymCqxxDTO.getBdcXmDO().getXmid()) && xmidXmfbMap.containsKey(slymCqxxDTO.getBdcXmDO().getXmid())) {
                    slymCqxxDTO.setBdcXmFbDO(xmidXmfbMap.get(slymCqxxDTO.getBdcXmDO().getXmid()));
//                    //xmfb转map
//                    slymCqxxDTO.setBdcXmFbMap(Object2MapUtils.object2MapExceptNull(xmidXmfbMap.get(slymCqxxDTO.getBdcXmDO().getXmid())));
                }
                if (StringUtils.isNotBlank(slymCqxxDTO.getBdcXmDO().getBdcdyh()) && dyhCqxxMap.containsKey(slymCqxxDTO.getBdcXmDO().getBdcdyh())) {
                    //房屋结构转换
                    Map cqxxMap = dyhCqxxMap.get(slymCqxxDTO.getBdcXmDO().getBdcdyh());
                    slymCqxxDTO.setCqxxMap(cqxxMap);

                } else {
                    slymCqxxDTO.setCqxxMap(new HashMap());
                }
                if (StringUtils.isNotBlank(slymCqxxDTO.getBdcXmDO().getBdcdyh()) && dyhJsydsyqxxMap.containsKey(slymCqxxDTO.getBdcXmDO().getBdcdyh())) {
                    slymCqxxDTO.setJsydsyqMap(dyhJsydsyqxxMap.get(slymCqxxDTO.getBdcXmDO().getBdcdyh()));

                } else {
                    slymCqxxDTO.setJsydsyqMap(new HashMap());
                }
//                //土地用途转换
//                Map bdcXmMap = slymCqxxDTO.getBdcXmMap();
//                bdcXmMap.put("tdytmc", StringToolUtils.convertBeanPropertyValueOfZdByString(MapUtils.getString(bdcXmMap, "zdzhyt", ""), tdytList));
////              //权利性质转换
//                bdcXmMap.put("qlxzmc", StringToolUtils.convertBeanPropertyValueOfZdByString(MapUtils.getString(bdcXmMap, "qlxz", ""), qlxzList));
//                slymCqxxDTO.setBdcXmMap(bdcXmMap);
            }
        }
        return slymCqxxDTOList;

    }

    /**
     * @param bdcXscqList 现势产权项目
     * @param bdcCfXmList 当前查封项目
     * @param qjgldm 权籍管理代码
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 拼接原查封编号到产权项目中
     */
    private List<BdcXmCfDTO> combineYcfbh(List<BdcXmDO> bdcXscqList, List<BdcCfDO> yCfList, List<BdcXmDO> bdcCfXmList,String qjgldm) {
        List<BdcXmCfDTO> bdcXmCfDTOS = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(bdcXscqList)) {
            bdcXscqList.sort(Comparator.comparing(BdcXmDO::getBdcdyh));
            for (BdcXmDO bdcXmDO : bdcXscqList) {
                BdcXmCfDTO bdcXmCfDTO = new BdcXmCfDTO();
                BeanUtils.copyProperties(bdcXmDO, bdcXmCfDTO);
                Set<String> cfbhSet = new LinkedHashSet<>();
                if (CollectionUtils.isNotEmpty(yCfList)) {
                    for (BdcCfDO bdcCfDO : yCfList) {
                        if (StringUtils.equals(bdcXmDO.getBdcdyh(), bdcCfDO.getBdcdyh()) && StringUtils.isNotBlank(bdcCfDO.getCfbh())) {
                            cfbhSet.add(bdcCfDO.getCfbh());
                        }
                    }
                }
                //组织当前查封的现势产权的产权证+ 匹配关系的土地证的证号
                getCfYcqzh(bdcXmDO, bdcXmCfDTO);
                bdcXmCfDTO.setCfbh(Joiner.on(",<br>").join(cfbhSet));
                //查询幢号房间号
                BdcQl bdcQl = bdcQllxFeignService.queryQlxx(bdcXmDO.getXmid());
                if (Objects.nonNull(bdcQl) && bdcQl instanceof BdcFdcqDO) {
                    BdcFdcqDO bdcFdcqDO = (BdcFdcqDO) bdcQl;
                    bdcXmCfDTO.setZh(bdcFdcqDO.getZh());
                    bdcXmCfDTO.setFjh(bdcFdcqDO.getFjh());
                }
                bdcXmCfDTOS.add(bdcXmCfDTO);
            }
            return bdcXmCfDTOS;
        } else {
            //如果现势产权查询无数据，先查实测户室数据，无数据再查预测户室数据(预查封)
            for (BdcXmDO bdcXmDO : bdcCfXmList) {
                BdcXmCfDTO bdcXmCfDTO = new BdcXmCfDTO();
                FwHsDO fwHsDO = fwHsFeignService.queryFwhsByBdcdyh(bdcXmDO.getBdcdyh(), qjgldm);
                if (Objects.isNull(fwHsDO)) {
                    FwYchsDO fwYchsDO = fwYcHsFeignService.queryFwYcHsByBdcdyh(bdcXmDO.getBdcdyh(), qjgldm);
                    if (Objects.nonNull(fwYchsDO)) {
                        LOGGER.warn("{}实测户室无数据，开始查询预测户室", bdcXmDO.getBdcdyh());
                        bdcXmCfDTO.setBdcdyh(fwYchsDO.getBdcdyh());
                        bdcXmCfDTO.setZl(fwYchsDO.getZl());
                        bdcXmCfDTO.setDzwmj(fwYchsDO.getYcjzmj());
                        if(fwYchsDO.getGhyt() !=null) {
                            bdcXmCfDTO.setDzwyt(Integer.valueOf(fwYchsDO.getGhyt()));
                        }
                        bdcXmCfDTO.setFjh(fwYchsDO.getFjh());
                        if(StringUtils.isNotBlank(fwYchsDO.getFwDcbIndex())) {
                            FwLjzDO fwLjzDO = fwLjzFeginService.queryLjzByFwDcbIndex(fwYchsDO.getFwDcbIndex(), qjgldm);
                            if (Objects.nonNull(fwLjzDO)) {
                                bdcXmCfDTO.setZh(fwLjzDO.getDh());
                            }
                        }
                    }
                } else {
                    bdcXmCfDTO.setBdcdyh(fwHsDO.getBdcdyh());
                    bdcXmCfDTO.setZl(fwHsDO.getZl());
                    bdcXmCfDTO.setDzwmj(fwHsDO.getScjzmj());
                    if(fwHsDO.getGhyt() != null) {
                        bdcXmCfDTO.setDzwyt(Integer.valueOf(fwHsDO.getGhyt()));
                    }
                    bdcXmCfDTO.setFjh(fwHsDO.getFjh());
                    if(StringUtils.isNotBlank(fwHsDO.getFwDcbIndex())) {
                        //查幢号
                        FwLjzDO fwLjzDO = fwLjzFeginService.queryLjzByFwDcbIndex(fwHsDO.getFwDcbIndex(), qjgldm);
                        if (Objects.nonNull(fwLjzDO)) {
                            bdcXmCfDTO.setZh(fwLjzDO.getDh());
                        }
                    }
                }
                //查询地籍调查表
                String zdBdcdyh = bdcXmDO.getBdcdyh().substring(0, 19) + "W00000000";
                LOGGER.warn("{}开始查询宗地地籍调查表信息", zdBdcdyh);
                ZdDjdcbDO zdDjdcbDO = zdFeignService.queryZdByBdcdyh(zdBdcdyh, qjgldm);
                if (Objects.nonNull(zdDjdcbDO)) {
                    bdcXmCfDTO.setQlxz(zdDjdcbDO.getSyqlx());
                    bdcXmCfDTO.setZdzhyt(zdDjdcbDO.getTdyt());
                }
                bdcXmCfDTOS.add(bdcXmCfDTO);
            }
        }
        return bdcXmCfDTOS;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 加载债务人信息
     */
    @ResponseBody
    @GetMapping("/zwr")
    public Object loadZwr(String xmid) {
        BdcSlQlxxymDTO bdcSlQlxxymDTO =new BdcSlQlxxymDTO();
        if(StringUtils.isNotBlank(xmid)){
            Map<String, List<Map>> zdMap =new HashMap<>();
            List<Map> zjzlZdMap = bdcZdFeignService.queryBdcZd("zjzl");
            zdMap.put("zjzl",zjzlZdMap);
            getDsQlrxx(bdcSlQlxxymDTO,xmid,zdMap);

        }
        return bdcSlQlxxymDTO;
    }

    /**
     * 获取抵押注销证明类型
     * @param bdcXmDO
     * @param bdcSlQlxxymDTO
     * @return
     */
    private BdcSlQlxxymDTO getZmlx(BdcXmDO bdcXmDO,BdcSlQlxxymDTO bdcSlQlxxymDTO){
        BdcXmLsgxQO bdcXmLsgxQO = new BdcXmLsgxQO();
        bdcXmLsgxQO.setXmid(bdcXmDO.getXmid());
        List<BdcXmLsgxDO> bdcXmLsgxDOList = bdcLsgxFeignService.listXmLsgxByXmid(bdcXmLsgxQO);
        if(CollectionUtils.isNotEmpty(bdcXmLsgxDOList)){
            List<BdcZsDO> bdcZsDOS = bdcZsInitFeignService.queryBdcqz(bdcXmLsgxDOList.get(0).getYxmid());
            if(CollectionUtils.isNotEmpty(bdcZsDOS)){
                if (StringUtils.isBlank(bdcZsDOS.get(0).getQzysxlh())) {
                    bdcSlQlxxymDTO.setZmlx(CommonConstantUtils.DYZX_ZMLX_DZZZ);
                } else {
                    bdcSlQlxxymDTO.setZmlx(CommonConstantUtils.DYZX_ZMLX_ZZZM);
                }
            }
        }
        return bdcSlQlxxymDTO;
    }

    /**
     * @return
     * @author <a href = "mailto:fanghao@gtmap.cn">fanghao</a>
     * @description 存入提示信息（通过配置工作流定义id来判断是否需要显示信息）
     */
    private BdcSlQlxxymDTO setTsxx(BdcXmDO bdcXmDO,BdcSlQlxxymDTO bdcSlQlxxymDTO){
        if (StringUtils.isNotBlank(gzldyid) && StringToolUtils.existItemEquals(bdcXmDO.getGzldyid(),gzldyid.split(","))){
            BdcXmLsgxQO bdcXmLsgxQO = new BdcXmLsgxQO();
            bdcXmLsgxQO.setXmid(bdcXmDO.getXmid());
            bdcXmLsgxQO.setWlxm(0);
            List<BdcXmLsgxDO> bdcXmLsgxDOList = bdcLsgxFeignService.listXmLsgxByXmid(bdcXmLsgxQO);
            if(CollectionUtils.isNotEmpty(bdcXmLsgxDOList)){
                List<BdcZsDO> bdcZsDOS = bdcZsInitFeignService.queryBdcqz(bdcXmLsgxDOList.get(0).getYxmid());
                if (CollectionUtils.isNotEmpty(bdcZsDOS)){
                    if (StringUtils.isNotBlank(bdcZsDOS.get(0).getQzysxlh())){
                        bdcSlQlxxymDTO.setTsxx(CommonConstantUtils.TSXX_YC);
                    }
                }
            }
        }
        return bdcSlQlxxymDTO;
    }

    private void getCfYcqzh(BdcXmDO xscqXm, BdcXmCfDTO bdcXmCfDTO) {
        //查当前查封的现势产权的匹配关系
        List<BdcFctdPpgxDO> bdcFctdPpgxDOList = bdcPpFeignService.queryBdcFctdPpgx(xscqXm.getXmid());
        if (CollectionUtils.isNotEmpty(bdcFctdPpgxDOList)) {
            String tdXmid = bdcFctdPpgxDOList.get(0).getTdcqxmid();
            BdcXmQO bdcXmQO = new BdcXmQO(tdXmid);
            List<BdcXmDO> tdXmList = bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isNotEmpty(tdXmList)) {
                String wlcqzh = StringToolUtils.resolveBeanToAppendStr(tdXmList, "getBdcqzh", CommonConstantUtils.ZF_YW_DH);
                if (StringUtils.isNotBlank(wlcqzh)) {
                    bdcXmCfDTO.setBdcqzh(bdcXmCfDTO.getBdcqzh() + CommonConstantUtils.ZF_YW_DH + wlcqzh);
                }
            }
        }
    }

    /**
     * @param initBdcSlQlxxymQO 查询对象
     * @param bdcSlQlxxymDTO 组织结果
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 组织权利信息页面信息
     */
    private void initBdcSlQlxxymDTOTscl(InitBdcSlQlxxymQO initBdcSlQlxxymQO, BdcSlQlxxymDTO bdcSlQlxxymDTO){
        BdcXmDO bdcXmDO =initBdcSlQlxxymQO.getBdcXmDOList().get(0);
        BdcSlQlxxymQO bdcSlQlxxymQO =initBdcSlQlxxymQO.getBdcSlQlxxymQO();
        if(initBdcSlQlxxymQO.getBdcQl() instanceof BdcDyaqDO) {
            //查询单元号是否存在抵押
            if(Boolean.TRUE.equals(bdcSlQlxxymQO.getCxexistDyaq())) {
                List<String> dyaDyhList = new ArrayList<>(initBdcSlQlxxymQO.getBdcXmDOList().size());
                for (BdcXmDO bdcXm : initBdcSlQlxxymQO.getBdcXmDOList()) {
                    BdcXmQO xmQO = new BdcXmQO();
                    xmQO.setXmid(bdcXm.getXmid());
                    xmQO.setBdcdyh(bdcXm.getBdcdyh());
                    List<BdcDyaqDO> bdcDyaqDOList = bdcBdcdyFeignService.listExistBdcDyaq(xmQO);
                    if (CollectionUtils.isNotEmpty(bdcDyaqDOList)) {
                        dyaDyhList.add(bdcXm.getBdcdyh());
                    }
                }
                //抵押单元号
                bdcSlQlxxymDTO.setDyaBdcdyh(StringUtils.join(dyaDyhList, CommonConstantUtils.ZF_YW_DH));
            }
            //第三权利人（债务人信息）
            if (bdcSlQlxxymQO.getDsqlr() != null && bdcSlQlxxymQO.getDsqlr()) {
                getDsQlrxx(bdcSlQlxxymDTO, bdcXmDO.getXmid(), initBdcSlQlxxymQO.getZdMap());
            }
        }


    }


    /**
     * @param gzlslid,zxlc
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 组织打印需要的参数信息
     * @date : 2022/5/30 11:43
     */
    @ResponseBody
    @GetMapping("/print")
    public Object queryQlxxForPrintBtn(String gzlslid, Boolean zxlc) {
        if (StringUtils.isNotBlank(gzlslid)) {
            return queryBdcQlList(gzlslid, zxlc);
        }
        return null;
    }

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据xmid 判断是否是产权
     * @date : 2022/12/22 9:56
     */
    @ResponseBody
    @PostMapping("/cqxmid")
    public Object listCqxmid(@RequestBody List<String> xmidList) {
        if (CollectionUtils.isNotEmpty(xmidList)) {
            List<String> cqXmidList = new ArrayList<>(xmidList.size());
            for (String xmid : xmidList) {
                BdcXmDO bdcXmDO = bdcXmFeignService.queryBdcXmByXmid(xmid);
                if (Objects.nonNull(bdcXmDO.getQllx()) && !ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, bdcXmDO.getQllx())) {
                    cqXmidList.add(xmid);
                }
            }
            return cqXmidList;
        }
        return null;
    }


    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 仅限批量流程使用，查询批量抵押的产权相关信息
     * @date : 2022/7/29 14:55
     */
    @ResponseBody
    @GetMapping("/pllc/dyacqxx")
    public Object listDyaCqxx(@LayuiPageable Pageable pageable, String gzlslid) {
        pageable = new PageRequest(0, 2000);
        if (StringUtils.isNotBlank(gzlslid)) {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setGzlslid(gzlslid);
            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                BdcQl bdcQl = bdcQllxFeignService.queryQlxx(bdcXmDOList.get(0).getXmid());
                if(Objects.isNull(bdcQl)){
                    bdcQl = bdcQllxFeignService.queryBdcYqlxx(bdcXmDOList.get(0).getXmid());
                }
                if (bdcQl instanceof BdcDyaqDO) {
                    List<SlymCqxxDTO> slymCqxxDTOList = listSlymCqxx(bdcXmDOList, gzlslid, bdcQl);
                    if (CollectionUtils.isNotEmpty(slymCqxxDTOList)) {
                        List<SlymDyaCqxxDTO> slymDyaCqxxDTOList = new ArrayList<>(CollectionUtils.size(slymCqxxDTOList));
                        AtomicInteger atomicInteger = new AtomicInteger(1);
                        List<Map> fwjgList = bdcZdFeignService.queryBdcZd("fwjg");
                        List<Map> fwytList = bdcZdFeignService.queryBdcZd("fwyt");
                        List<Map> tdytList = bdcZdFeignService.queryBdcZd("tdyt");
                        List<Map> fwxzList = bdcZdFeignService.queryBdcZd("fwxz");
                        List<Map> qlxzList = bdcZdFeignService.queryBdcZd("qlxz");
                        for (SlymCqxxDTO slymCqxxDTO : slymCqxxDTOList) {
                            String bdcdyh = slymCqxxDTO.getBdcXmDO().getBdcdyh();
                            String qlxzmc = StringToolUtils.convertBeanPropertyValueOfZdByString(slymCqxxDTO.getBdcXmDO().getQlxz(), qlxzList);
                            String tdytmc = StringToolUtils.convertBeanPropertyValueOfZdByString(slymCqxxDTO.getBdcXmDO().getZdzhyt(), tdytList);
                            if (MapUtils.isNotEmpty(slymCqxxDTO.getCqxxMap())) {
                                SlymDyaCqxxDTO slymDyaCqxxDTO = new SlymDyaCqxxDTO();
                                slymDyaCqxxDTO.setBdcdyfwlx(MapUtils.getInteger(slymCqxxDTO.getCqxxMap(), "BDCDYFWLX", null));
                                slymDyaCqxxDTO.setBdcdyh(bdcdyh);
                                slymDyaCqxxDTO.setBdcqzh(MapUtils.getString(slymCqxxDTO.getCqxxMap(), "BDCQZH", ""));
                                slymDyaCqxxDTO.setClsjsyqx(MapUtils.getString(slymCqxxDTO.getCqxxMap(), "CLSJSYQX", ""));
                                slymDyaCqxxDTO.setDytdmj(MapUtils.getString(slymCqxxDTO.getCqxxMap(), "DYTDMJ", ""));
                                slymDyaCqxxDTO.setFjh(MapUtils.getString(slymCqxxDTO.getCqxxMap(), "FJH", ""));
                                slymDyaCqxxDTO.setFttdmj(MapUtils.getString(slymCqxxDTO.getCqxxMap(), "FTTDMJ", ""));
                                slymDyaCqxxDTO.setFwjgmc(StringToolUtils.convertBeanPropertyValueOfZdByString(MapUtils.getString(slymCqxxDTO.getCqxxMap(), "FWJG", ""), fwjgList));
                                slymDyaCqxxDTO.setFwxzmc(StringToolUtils.convertBeanPropertyValueOfZdByString(MapUtils.getString(slymCqxxDTO.getCqxxMap(), "FWXZ", ""), fwxzList));
                                slymDyaCqxxDTO.setFwytmc(StringToolUtils.convertBeanPropertyValueOfZdByString(MapUtils.getString(slymCqxxDTO.getCqxxMap(), "DZWYT", ""), fwytList));
                                slymDyaCqxxDTO.setJzmj(MapUtils.getString(slymCqxxDTO.getCqxxMap(), "JZMJ", ""));
                                slymDyaCqxxDTO.setQlid(MapUtils.getString(slymCqxxDTO.getCqxxMap(), "QLID", ""));
                                slymDyaCqxxDTO.setDytdmj(MapUtils.getString(slymCqxxDTO.getCqxxMap(), "DYTDMJ", ""));
                                slymDyaCqxxDTO.setQlxzmc(qlxzmc);
                                slymDyaCqxxDTO.setSzmyc(MapUtils.getString(slymCqxxDTO.getCqxxMap(), "SZMYC", ""));
                                slymDyaCqxxDTO.setTdsyjssj(MapUtils.getString(slymCqxxDTO.getCqxxMap(), "TDSYJSSJ", ""));
                                slymDyaCqxxDTO.setTdsyjssj2(MapUtils.getString(slymCqxxDTO.getCqxxMap(), "TDSYJSSJ2", ""));
                                slymDyaCqxxDTO.setTdsyjssj3(MapUtils.getString(slymCqxxDTO.getCqxxMap(), "TDSYJSSJ3", ""));
                                slymDyaCqxxDTO.setTdsyqssj(MapUtils.getString(slymCqxxDTO.getCqxxMap(), "TDSYQSSJ", ""));
                                slymDyaCqxxDTO.setTdsyqssj2(MapUtils.getString(slymCqxxDTO.getCqxxMap(), "TDSYQSSJ2", ""));
                                slymDyaCqxxDTO.setTdsyqssj3(MapUtils.getString(slymCqxxDTO.getCqxxMap(), "TDSYQSSJ3", ""));
                                slymDyaCqxxDTO.setTdytmc(tdytmc);
                                slymDyaCqxxDTO.setXh(atomicInteger + "");
                                slymDyaCqxxDTO.setZcs(MapUtils.getString(slymCqxxDTO.getCqxxMap(), "ZCS", ""));
                                slymDyaCqxxDTO.setZdzhmj(Objects.nonNull(slymCqxxDTO.getBdcXmDO().getZdzhmj()) ? slymCqxxDTO.getBdcXmDO().getZdzhmj().toString() : "");
                                slymDyaCqxxDTO.setZh(MapUtils.getString(slymCqxxDTO.getCqxxMap(), "ZH", ""));
                                slymDyaCqxxDTO.setZl(MapUtils.getString(slymCqxxDTO.getCqxxMap(), "ZL", ""));
                                slymDyaCqxxDTO.setQllx(MapUtils.getString(slymCqxxDTO.getCqxxMap(), "QLLX", ""));
                                slymDyaCqxxDTO.setXmid(MapUtils.getString(slymCqxxDTO.getCqxxMap(), "XMID", ""));
                                atomicInteger.getAndIncrement();
                                slymDyaCqxxDTOList.add(slymDyaCqxxDTO);
                            }
                            if (MapUtils.isNotEmpty(slymCqxxDTO.getJsydsyqMap())) {
                                SlymDyaCqxxDTO slymDyaCqxxDTO = new SlymDyaCqxxDTO();
                                slymDyaCqxxDTO.setBdcdyh(bdcdyh);
                                slymDyaCqxxDTO.setQllx(MapUtils.getString(slymCqxxDTO.getJsydsyqMap(), "QLLX", ""));
                                slymDyaCqxxDTO.setBdcdyfwlx(MapUtils.getInteger(slymCqxxDTO.getJsydsyqMap(), "BDCDYFWLX", null));
                                slymDyaCqxxDTO.setQlid(MapUtils.getString(slymCqxxDTO.getJsydsyqMap(), "QLID", ""));
                                slymDyaCqxxDTO.setXmid(MapUtils.getString(slymCqxxDTO.getJsydsyqMap(), "XMID", ""));
                                slymDyaCqxxDTO.setBdcqzh(MapUtils.getString(slymCqxxDTO.getJsydsyqMap(), "BDCQZH", ""));
                                slymDyaCqxxDTO.setZl(MapUtils.getString(slymCqxxDTO.getJsydsyqMap(), "ZL", ""));
                                slymDyaCqxxDTO.setZdzhmj(MapUtils.getString(slymCqxxDTO.getJsydsyqMap(), "ZDZHMJ", ""));
                                slymDyaCqxxDTO.setFttdmj(MapUtils.getString(slymCqxxDTO.getJsydsyqMap(), "FTTDMJ", ""));
                                slymDyaCqxxDTO.setDytdmj(MapUtils.getString(slymCqxxDTO.getJsydsyqMap(), "DYTDMJ", ""));
                                slymDyaCqxxDTO.setQlxzmc(qlxzmc);
                                slymDyaCqxxDTO.setTdytmc(tdytmc);
                                slymDyaCqxxDTO.setClsjsyqx(MapUtils.getString(slymCqxxDTO.getJsydsyqMap(), "CLSJSYQX", ""));
                                slymDyaCqxxDTO.setTdsyjssj(MapUtils.getString(slymCqxxDTO.getJsydsyqMap(), "SYQJSSJ", ""));
                                slymDyaCqxxDTO.setTdsyjssj2(MapUtils.getString(slymCqxxDTO.getJsydsyqMap(), "SYQJSSJ2", ""));
                                slymDyaCqxxDTO.setTdsyjssj3(MapUtils.getString(slymCqxxDTO.getJsydsyqMap(), "SYQJSSJ3", ""));
                                slymDyaCqxxDTO.setTdsyqssj(MapUtils.getString(slymCqxxDTO.getJsydsyqMap(), "SYQQSSJ", ""));
                                slymDyaCqxxDTO.setTdsyqssj2(MapUtils.getString(slymCqxxDTO.getJsydsyqMap(), "SYQQSSJ2", ""));
                                slymDyaCqxxDTO.setTdsyqssj3(MapUtils.getString(slymCqxxDTO.getJsydsyqMap(), "SYQQSSJ3", ""));
                                atomicInteger.getAndIncrement();
                                slymDyaCqxxDTOList.add(slymDyaCqxxDTO);
                            }
                        }
                        if(((BdcDyaqDO) bdcQl).getDybdclx() == 4 && CollectionUtils.isEmpty(slymDyaCqxxDTOList)){
                            List<SlymDyaCqxxDTO> ychsCqxx = getYchsCqxx(bdcXmDOList);
                            slymDyaCqxxDTOList.addAll(ychsCqxx);
                        }
                        return addLayUiCode(new PageImpl(slymDyaCqxxDTOList, pageable, slymDyaCqxxDTOList.size()));
                    }
                }
            }
        }
        return addLayUiCode(new PageImpl(Collections.emptyList(), pageable, 0));
    }

    /**
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @param bdcXmDOS
     * @return
     * @description 查询预测户室的产权信息
     **/
    private List<SlymDyaCqxxDTO> getYchsCqxx(List<BdcXmDO> bdcXmDOS){
        List<SlymDyaCqxxDTO> ychsCqxx = new ArrayList();
        for (BdcXmDO bdcXmDO : bdcXmDOS) {
            FwHsDO fwHsDO = fwHsFeignService.queryFwhsByBdcdyh(bdcXmDOS.get(0).getBdcdyh(), "");
            if (Objects.isNull(fwHsDO)) {
                LOGGER.warn("{}实测户室无数据，开始查询预测户室", bdcXmDO.getBdcdyh());
                FwYchsDO fwYchsDO = fwYcHsFeignService.queryFwYcHsByBdcdyh(bdcXmDO.getBdcdyh(), "");
                if (Objects.nonNull(fwYchsDO)) {
                    SlymDyaCqxxDTO slymDyaCqxxDTO = new SlymDyaCqxxDTO();
                    slymDyaCqxxDTO.setXmid(bdcXmDO.getXmid());
                    slymDyaCqxxDTO.setBdcdyh(bdcXmDO.getBdcdyh());
                    slymDyaCqxxDTO.setQllx(Objects.toString(bdcXmDO.getQllx(),null));
                    slymDyaCqxxDTO.setBdcdyfwlx(bdcXmDO.getBdcdyfwlx());
                    slymDyaCqxxDTO.setZl(fwYchsDO.getZl());
                    slymDyaCqxxDTO.setFjh(fwYchsDO.getFjh());
                    slymDyaCqxxDTO.setSzmyc(Objects.toString(fwYchsDO.getYcjzmj(),null));
                    slymDyaCqxxDTO.setJzmj(Objects.toString(fwYchsDO.getYcjzmj(),null));
                    slymDyaCqxxDTO.setDytdmj(Objects.toString(fwYchsDO.getDytdmj(),null));
                    slymDyaCqxxDTO.setFttdmj(Objects.toString(fwYchsDO.getFttdmj(),null));
                    slymDyaCqxxDTO.setFwytmc(fwYchsDO.getYtmc());
                    slymDyaCqxxDTO.setTdytmc(fwYchsDO.getTdyt());
                    slymDyaCqxxDTO.setFwxzmc(fwYchsDO.getFwxzmc());
                    slymDyaCqxxDTO.setQlxzmc(fwYchsDO.getTdsyqlx());
                    FwLjzDO fwLjzDO = fwLjzFeginService.queryLjzByFwDcbIndex(fwYchsDO.getFwDcbIndex(), "");
                    if (Objects.nonNull(fwLjzDO)) {
                        slymDyaCqxxDTO.setZh(fwLjzDO.getDh());
                        slymDyaCqxxDTO.setFwjgmc(fwLjzDO.getFwjg());
                        slymDyaCqxxDTO.setZcs(fwLjzDO.getFwcs() == null?StringUtils.EMPTY:fwLjzDO.getFwcs().toString());
                    }
                    //查询地籍调查表
                    String zdBdcdyh = bdcXmDO.getBdcdyh().substring(0, 19) + "W00000000";
                    LOGGER.warn("{}开始查询宗地地籍调查表信息", zdBdcdyh);
                    ZdDjdcbDO zdDjdcbDO = zdFeignService.queryZdByBdcdyh(zdBdcdyh, "");
                    if (Objects.nonNull(zdDjdcbDO)) {
                        slymDyaCqxxDTO.setQlxzmc(zdDjdcbDO.getSyqlx());
                    }
                    ychsCqxx.add(slymDyaCqxxDTO);
                }
            } else {
                SlymDyaCqxxDTO slymDyaCqxxDTO = new SlymDyaCqxxDTO();
                slymDyaCqxxDTO.setXmid(bdcXmDO.getXmid());
                slymDyaCqxxDTO.setBdcdyh(bdcXmDO.getBdcdyh());
                slymDyaCqxxDTO.setQllx(Objects.toString(bdcXmDO.getQllx(),null));
                slymDyaCqxxDTO.setBdcdyfwlx(bdcXmDO.getBdcdyfwlx());
                slymDyaCqxxDTO.setZl(fwHsDO.getZl());
                slymDyaCqxxDTO.setFjh(fwHsDO.getFjh());
                slymDyaCqxxDTO.setSzmyc(fwHsDO.getCh());

                slymDyaCqxxDTO.setJzmj(Objects.toString(fwHsDO.getYcjzmj(),null));
                slymDyaCqxxDTO.setDytdmj(Objects.toString(fwHsDO.getDytdmj(),null));
                slymDyaCqxxDTO.setFttdmj(Objects.toString(fwHsDO.getFttdmj(),null));
                slymDyaCqxxDTO.setFwytmc(fwHsDO.getYtmc());
                slymDyaCqxxDTO.setTdytmc(fwHsDO.getTdyt());
                slymDyaCqxxDTO.setFwxzmc(fwHsDO.getFwxzmc());
                slymDyaCqxxDTO.setQlxzmc(fwHsDO.getTdsyqlx());
                FwLjzDO fwLjzDO = fwLjzFeginService.queryLjzByFwDcbIndex(fwHsDO.getFwDcbIndex(), "");
                if (Objects.nonNull(fwLjzDO)) {
                    slymDyaCqxxDTO.setZh(fwLjzDO.getDh());
                    slymDyaCqxxDTO.setFwjgmc(fwLjzDO.getFwjg());
                    slymDyaCqxxDTO.setZcs(fwLjzDO.getFwcs() == null?StringUtils.EMPTY:fwLjzDO.getFwcs().toString());
                }
                //查询地籍调查表
                String zdBdcdyh = bdcXmDO.getBdcdyh().substring(0, 19) + "W00000000";
                LOGGER.warn("{}开始查询宗地地籍调查表信息", zdBdcdyh);
                ZdDjdcbDO zdDjdcbDO = zdFeignService.queryZdByBdcdyh(zdBdcdyh, "");
                if (Objects.nonNull(zdDjdcbDO)) {
                    slymDyaCqxxDTO.setQlxzmc(zdDjdcbDO.getSyqlx());
                }
                ychsCqxx.add(slymDyaCqxxDTO);
            }

        }

        return null;
    }

    /**
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param bdcXmDO
     * @return
     * @description 根据不动产单元号 获取维修基金信息
     **/
    private BdcSlWxjjxxDO getWxjjxx(BdcXmDO bdcXmDO) {
        BdcSlWxjjxxDO bdcSlWxjjxxDO = new BdcSlWxjjxxDO();
        bdcSlWxjjxxDO.setBdcdyh(bdcXmDO.getBdcdyh());
        List<BdcSlWxjjxxDO> bdcSlWxjjxxDOList = bdcSlWxzjFeignService.queryBdcSlWxjjxx(bdcSlWxjjxxDO);
        if (CollectionUtils.isNotEmpty(bdcSlWxjjxxDOList)) {
            return bdcSlWxjjxxDOList.get(0);
        }
        return null;
    }

    /**
     * @return 是否保存建筑面积
     * @author <a href="mailto:hejian@gtmap.cn">hejian</a>
     * @description 配置的房屋类型（bdc_zd_fwlx）和房屋用途（bdc_zd_fwyt）判断层高不足2.2米的不保存建筑面积
     */
    @ResponseBody
    @PostMapping ("/changeJzmjByCg")
    public boolean queryBdcSlSfxmBz(@RequestParam(value = "cg") String cg, @RequestParam(value = "fwlx") String fwlx, @RequestParam(value = "fwyt") String fwyt) {
        if (StringUtils.isAnyBlank(cg, fwlx, fwyt)) {
            return false;
        }

        fwlx = fwlx.trim();
        fwyt = fwyt.trim();
        if (fwlxList.contains(fwlx) && fwytList.contains(fwyt) && Double.parseDouble(cg) < qlCg) {
            return true;
        }
        return false;
    }
}
