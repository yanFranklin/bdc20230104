package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.dto.building.FwHsBgHistoryNewDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmLsgxDTO;
import cn.gtmap.realestate.common.core.dto.init.LsgxModelDTO;
import cn.gtmap.realestate.common.core.dto.init.LsgxXzqlModelDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.LsgxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.building.FwHsHistroyFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcLsgxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcSdFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

/**
 * @author <a href="mailto:wangwei2@gtmap.cn">wangwei2</a>
 * @version 1.0, 2019/3/11
 * @description
 */
@RestController
@RequestMapping(value = "/history")
public class BdcLsgxController extends BaseController {
    @Autowired
    private BdcXmFeignService bdcXmFeignService;

    @Autowired
    private FwHsHistroyFeignService fwHsHistroyFeignService;

    @Autowired
    private BdcLsgxFeignService bdcLsgxFeignService;

    @Autowired
    private BdcSdFeignService bdcSdFeignService;

    @Autowired
    private BdcZsFeignService bdcZsFeignService;

    @Autowired
    private BdcZdFeignService bdcZdFeignService;


    /**
     * 历史关系权利信息展示详细内容
     */
    @Value("${yancheng.lsgx.xsxx:false}")
    private Boolean xsxx;

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(BdcLsgxController.class);

    /**
     * version 1.0
     *
     * @return
     * @description 房屋历史关系
     * @date 2019/3/11
     * @author <a href ="mailto:wangwei2@gtmap.cn">wangwei2</a>
     */
    @GetMapping("/fwhs")
    public List<FwHsBgHistoryNewDTO> listFwHsBgHistory(@RequestParam(value = "xmid", required = false) String xmid,
                                                       @RequestParam(value = "bdcdyh", required = false) String bdcdyh){
        if (StringUtils.isBlank(bdcdyh) && StringUtils.isBlank(xmid)){
            throw new AppException("项目ID或不动产单元号不能为空！");
        }else if (StringUtils.isBlank(bdcdyh)){
            bdcdyh = this.queryBdcdyhByXmid(xmid);
        }

        return fwHsHistroyFeignService.getHsBgHistoryNewByBdcdyh(bdcdyh,"");
    }

    /**
     * version 1.0
     *
     * @return
     * @description 产权信息
     * @date 2019/3/11
     * @author <a href ="mailto:wangwei2@gtmap.cn">wangwei2</a>
     */
    @GetMapping("/cqxx")
    public List<LsgxModelDTO> listLscqXx(@RequestParam(value = "xmid", required = false) String xmid,
                                         @RequestParam(value = "bdcdyh", required = false) String bdcdyh) throws ReflectiveOperationException{
        if (StringUtils.isBlank(bdcdyh) && StringUtils.isBlank(xmid)){
            throw new AppException("项目ID或不动产单元号不可为空！");

        }else if (StringUtils.isBlank(bdcdyh)){
            bdcdyh = this.queryBdcdyhByXmid(xmid);
        }

        List<LsgxModelDTO> lsgxModelDTOS = null;
        if (StringUtils.isNotBlank(bdcdyh)){
            // 根据bdcdyh查询主线所有产权(只查询产权信息)
            lsgxModelDTOS = bdcLsgxFeignService.getCqList(bdcdyh);
            LOGGER.info("allCqLsgxModel查询数据为:" + JSONObject.toJSONString(lsgxModelDTOS));
            if (CollectionUtils.isNotEmpty(lsgxModelDTOS)){
                Collections.sort(lsgxModelDTOS,new Comparator<LsgxModelDTO>(){
                    @Override
                    public int compare(LsgxModelDTO model1,LsgxModelDTO model2){
                        if ((null != model1.getDjsj() && null != model2.getDjsj()) && (model1.getDjsj().getTime() > model2.getDjsj().getTime())){
                            return -1;
                        }
                        return 1;
                    }
                });
            }
        }
        return lsgxModelDTOS;
    }


    /**
     * version 1.0
     *
     * @return
     * @description 根据xmid查询所有的限制权利信息
     * @date 2019/3/18
     * @author <a href ="mailto:wangwei2@gtmap.cn">wangwei2</a>
     */
    @GetMapping("/cqxx/xzxx")
    public LsgxXzqlModelDTO listLscqXzxx(@RequestParam(value = "xmid") String xmid){

        LsgxXzqlModelDTO lsgxXzqlModelDTO = bdcLsgxFeignService.getAllXzqlList(xmid,CommonConstantUtils.SF_F_DM);

        // 排序 历史的在下面 现势的在上面
        if (null != lsgxXzqlModelDTO){
            List<LsgxModelDTO> listDy = lsgxXzqlModelDTO.getDyLsgxModel();
            if (CollectionUtils.isNotEmpty(listDy)){
                listDy.sort(comparing(LsgxModelDTO::getQszt));
            }
            List<LsgxModelDTO> listCf = lsgxXzqlModelDTO.getCfLsgxModel();
            if (CollectionUtils.isNotEmpty(listCf)){
                listCf.sort(comparing(LsgxModelDTO::getQszt));
            }
            List<LsgxModelDTO> listDyi = lsgxXzqlModelDTO.getDyiLsgxModel();
            if (CollectionUtils.isNotEmpty(listDyi)){
                listDyi.sort(comparing(LsgxModelDTO::getQszt));
            }
            List<LsgxModelDTO> listYg = lsgxXzqlModelDTO.getYgLsgxModel();
            if (CollectionUtils.isNotEmpty(listYg)){
                //限制权利移除预告登记种类为预告的权利类型，只保留预告预抵押
                listYg.removeIf(lsgxModelDTO -> ArrayUtils.contains(CommonConstantUtils.YG_YGDJZL_ARR,((BdcYgDO)lsgxModelDTO.getBdcQl()).getYgdjzl()));
                listYg.sort(comparing(LsgxModelDTO::getQszt));
            }

            List<LsgxModelDTO> listYy = lsgxXzqlModelDTO.getYyLsgxModel();
            if (CollectionUtils.isNotEmpty(listYy)){
                listYy.sort(comparing(LsgxModelDTO::getQszt));
            }

            List<LsgxModelDTO> listJz = lsgxXzqlModelDTO.getJzLsgxModel();
            if (CollectionUtils.isNotEmpty(listJz)){
                listJz.sort(comparing(LsgxModelDTO::getQszt));
            }
            List<LsgxModelDTO> listSd = lsgxXzqlModelDTO.getSdLsgxModel();
            if (CollectionUtils.isNotEmpty(listSd)){
                Collections.sort(listSd,(sd1,sd2) -> {
                    if ((null != sd1.getDjsj() && null != sd2.getDjsj()) && (sd1.getDjsj().getTime() > sd2.getDjsj().getTime())){
                        return -1;
                    }
                    return 1;
                });
            }

        }

        return lsgxXzqlModelDTO;
    }

    /**
     * 获取锁定不动产单元
     *
     * @param bdcdyh 不动产单元号
     * @return List 锁定不动产单元集合
     */
    private List<BdcDysdDO> getDySdxxList(String bdcdyh){
        if (StringUtils.isBlank(bdcdyh)){
            return Collections.emptyList();
        }

        // 不动产单元锁定
        BdcDysdDO bdcDysdDO = new BdcDysdDO();
        bdcDysdDO.setBdcdyh(bdcdyh);
        return bdcSdFeignService.queryBdcdySd(bdcDysdDO);
    }

    /**
     * version 1.0
     *
     * @return
     * @description 查询不动产单元号
     * @date 2019/3/14
     * @author <a href ="mailto:wangwei2@gtmap.cn">wangwei2</a>
     */
    @GetMapping("/bdcdyh")
    public String queryBdcdyhByXmid(@RequestParam("xmid") String xmid){
        if (StringUtils.isBlank(xmid)){
            throw new NullPointerException("参数xmid为空！");
        }

        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setXmid(xmid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isEmpty(bdcXmDOList) || null == bdcXmDOList.get(0)){
            throw new AppException("未查询到项目信息！");
        }

        return bdcXmDOList.get(0).getBdcdyh();
    }

    /**
     * 根据不动产单元号或xmid查询所有产权信息和限制权力信息
     *
     * @param xmid xmid
     * @return List lsgxDto
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @GetMapping("/cqxxAll")
    public List<LsgxDTO> queryLsgx(@RequestParam(value = "xmid", required = false) String xmid,
                                   @RequestParam(value = "bdcdyh", required = false) String bdcdyh)
            throws ReflectiveOperationException{
        List<LsgxDTO> lsgxDTOS = new ArrayList<>();
        List<LsgxModelDTO> lsgxModelDTOS = listLscqXx(xmid,bdcdyh);
        List<BdcDysdDO> bdcDysdDOList = this.getDySdxxList(bdcdyh);
        Map<String,List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        if (CollectionUtils.isNotEmpty(lsgxModelDTOS)){
            LsgxXzqlModelDTO lsgxXzqlModelDTO = null;
            for (LsgxModelDTO lsgxModelDTO : lsgxModelDTOS){
                LsgxDTO lsgxDTO = new LsgxDTO();
                if (StringUtils.isNotBlank(lsgxModelDTO.getId())){
                    lsgxXzqlModelDTO = listLscqXzxx(lsgxModelDTO.getId());
                    List<BdcZssdDO> bdcZssdDOList = this.getZsSdxxList(lsgxModelDTO.getId());
                    if (xsxx){
                        // 处理锁定信息
                        if (null == lsgxXzqlModelDTO){
                            lsgxXzqlModelDTO = new LsgxXzqlModelDTO();
                            lsgxXzqlModelDTO.initModelMap();
                        }
                        lsgxXzqlModelDTO = lsgxXzqlModelDTO.resolveSdxx(bdcDysdDOList,bdcZssdDOList,"");
                        // 盐城历史关系展示需要按照类似 现势/历史_权利人_登记时间 格式展示，处理相关字段内容
                        lsgxXzqlModelDTO = lsgxXzqlModelDTO.resolveLsgxXzqlXsxx(zdMap);
                        lsgxModelDTO.resolveLsgxXsxx();
                    }
                }
                lsgxDTO.setLsgxModelDTO(lsgxModelDTO);
                lsgxDTO.setLsgxXzqlModelDTO(lsgxXzqlModelDTO);
                lsgxDTOS.add(lsgxDTO);
            }
        }
        return lsgxDTOS;
    }

    /**
     * 根据xmid查询项目信息  ccx 2019-10-03 用于提示信息查看项目使用
     *
     * @param xmid
     * @return
     */
    @GetMapping("/queryBdcXmDoByXmid")
    public List<BdcXmDO> queryBdcXmDoByXmid(@RequestParam(value = "xmid", required = true) String xmid){
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setXmid(xmid);
        return bdcXmFeignService.listBdcXm(bdcXmQO);
    }

    /**
     * 根据主键ID查询锁定信息
     *
     * @param sdxxid 主键ID
     * @return Object 单元或者证书锁定信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @GetMapping("/sdxx/{sdxxid}")
    public Object querySdxx(@PathVariable("sdxxid") String sdxxid){
        if (StringUtils.isBlank(sdxxid)){
            return null;
        }
        return bdcSdFeignService.queryBdcSdxxById(sdxxid);
    }

    /**
     * 根据不动产单元号或xmid查询所有产权信息和限制权力信息 盐城版本
     *
     * @param xmid bdcdyh
     * @return LsgxXzqlModelDTO
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    @GetMapping("/cqxxAll/yc")
    public LsgxXzqlModelDTO queryLsgxYc(@RequestParam(value = "xmid", required = false) String xmid,
                                        @RequestParam(value = "bdcdyh", required = false) String bdcdyh)
            throws ReflectiveOperationException{
        List<LsgxModelDTO> lsgxModelDTOS = listLscqXx(xmid,bdcdyh);
        LOGGER.info("权力历史查询:" + JSONObject.toJSONString(lsgxModelDTOS));
        LsgxXzqlModelDTO lsgxXzqlModelDTO = new LsgxXzqlModelDTO();

        lsgxXzqlModelDTO.setAllCqLsgxModel(lsgxModelDTOS);
        List<BdcDysdDO> bdcDysdDOList = this.getDySdxxList(bdcdyh);
        Map<String,List<Map>> zdMap = bdcZdFeignService.listBdcZd();

        if (CollectionUtils.isNotEmpty(lsgxModelDTOS)){

            for (LsgxModelDTO lsgxModelDTO : lsgxModelDTOS){
                if (StringUtils.isNotBlank(lsgxModelDTO.getId())){
                    lsgxModelDTO.setCqid(lsgxModelDTO.getId());
                    listLscqXzxxYc(lsgxModelDTO.getId(),lsgxXzqlModelDTO);
                    if (xsxx){
                        List<BdcZssdDO> bdcZssdDOList = this.getZsSdxxList(lsgxModelDTO.getId());
                        // 处理锁定信息
                        if (null == lsgxXzqlModelDTO){
                            lsgxXzqlModelDTO = new LsgxXzqlModelDTO();
                            lsgxXzqlModelDTO.initModelMap();
                        }
                        lsgxXzqlModelDTO = lsgxXzqlModelDTO.resolveSdxx(bdcDysdDOList,bdcZssdDOList,lsgxModelDTO.getId());
                        // 盐城历史关系展示需要按照类似 现势/历史_权利人_登记时间 格式展示，处理相关字段内容
                        lsgxXzqlModelDTO = lsgxXzqlModelDTO.resolveLsgxXzqlXsxx(zdMap);
                    }
                }
            }

            //对锁定进行去重
            if (CollectionUtils.isNotEmpty(lsgxXzqlModelDTO.getSdLsgxModel())) {
                lsgxXzqlModelDTO.setSdLsgxModel(lsgxXzqlModelDTO.getSdLsgxModel()
                        .stream().distinct().collect(Collectors.toList()));
            }
        }

        return lsgxXzqlModelDTO;
    }


    /**
     * 盐城的权利历史处理限制信息
     *
     * @param xmid
     */
    public void listLscqXzxxYc(String xmid,LsgxXzqlModelDTO lsgxXzqlModelDTO){

        LsgxXzqlModelDTO curLsgxXzqlModelDTO = bdcLsgxFeignService.getAllXzqlList(xmid,CommonConstantUtils.SF_F_DM);

        if (null != curLsgxXzqlModelDTO){

            // 作为限制信息展示的预告  只能ygdjzl为3和4的
            if (CollectionUtils.isNotEmpty(curLsgxXzqlModelDTO.getYgLsgxModel())){
                List<LsgxModelDTO> listCqXzxxYgNew = new ArrayList<>();
                // 非预告的产权下面含有预告限制信息
                LOGGER.info("含有预告限制信息:{}",JSONObject.toJSONString(curLsgxXzqlModelDTO.getYgLsgxModel()));
                for (LsgxModelDTO lsgxModelDTO : curLsgxXzqlModelDTO.getYgLsgxModel()){
                    if (null == lsgxModelDTO || null == lsgxModelDTO.getBdcQl()){
                        continue;
                    }
                    Integer ygdjzl = ((BdcYgDO) lsgxModelDTO.getBdcQl()).getYgdjzl();
                    if (ArrayUtils.contains(CommonConstantUtils.YG_YGDJZL_YDY_ARR,ygdjzl)){
                        lsgxModelDTO.setCqid(xmid);
                        listCqXzxxYgNew.add(lsgxModelDTO);
                    }
                }

                lsgxXzqlModelDTO.getYgLsgxModel().addAll(listCqXzxxYgNew);
                //去重

                List<LsgxModelDTO> ygLsgxModelDTO = lsgxXzqlModelDTO.getYgLsgxModel().stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getId()))),ArrayList::new));
                lsgxXzqlModelDTO.setYgLsgxModel(ygLsgxModelDTO);
            }

            // 查封的和预查封的要区分开来
            List<LsgxModelDTO> listCf = curLsgxXzqlModelDTO.getCfLsgxModel();
            if (CollectionUtils.isNotEmpty(listCf)){
                for (LsgxModelDTO lsgxModelDTO : listCf){
                    BdcCfDO bdcCfDO = (BdcCfDO) lsgxModelDTO.getBdcQl();
                    lsgxModelDTO.setCqid(xmid);
                    if (bdcCfDO.getCflx() != null && bdcCfDO.getCflx().equals(CommonConstantUtils.CFLX_YCF)){
                        lsgxXzqlModelDTO.getYcfLsgxModel().add(lsgxModelDTO);
                    }else{
                        lsgxXzqlModelDTO.getCfLsgxModel().add(lsgxModelDTO);
                    }
                }
                List<LsgxModelDTO> cfLsgxModelDTO = lsgxXzqlModelDTO.getCfLsgxModel().stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getId()))),ArrayList::new));
                lsgxXzqlModelDTO.setCfLsgxModel(cfLsgxModelDTO);
                List<LsgxModelDTO> ycfLsgxModelDTO = lsgxXzqlModelDTO.getYcfLsgxModel().stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getId()))),ArrayList::new));
                lsgxXzqlModelDTO.setYcfLsgxModel(ycfLsgxModelDTO);
            }

            // 抵押
            List<LsgxModelDTO> listDy = curLsgxXzqlModelDTO.getDyLsgxModel();
            if (CollectionUtils.isNotEmpty(listDy)){
                for (LsgxModelDTO lsgxModelDTO : listDy){
                    lsgxModelDTO.setCqid(xmid);
                }
                lsgxXzqlModelDTO.getDyLsgxModel().addAll(listDy);

                List<LsgxModelDTO> dyLsgxModelDTO = lsgxXzqlModelDTO.getDyLsgxModel().stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getId()))),ArrayList::new));
                lsgxXzqlModelDTO.setDyLsgxModel(dyLsgxModelDTO);

            }

            // 异议
            List<LsgxModelDTO> listYy = curLsgxXzqlModelDTO.getYyLsgxModel();
            if (CollectionUtils.isNotEmpty(listYy)){
                for (LsgxModelDTO lsgxModelDTO : listYy){
                    lsgxModelDTO.setCqid(xmid);
                }
                lsgxXzqlModelDTO.getYyLsgxModel().addAll(listYy);
                List<LsgxModelDTO> yyLsgxModelDTO = lsgxXzqlModelDTO.getYyLsgxModel().stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getId()))),ArrayList::new));
                lsgxXzqlModelDTO.setYyLsgxModel(yyLsgxModelDTO);
            }
        }
    }


    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 添加锁定信息到权利历史限制信息中
     */
    private List<BdcZssdDO> getZsSdxxList(String xmid){
        List<LsgxModelDTO> sdLsgxModel = new ArrayList<>();
        List<BdcZsDO> bdcZsDOList = bdcZsFeignService.queryBdcZsByXmid(xmid);
        List<BdcZssdDO> allBdcZssdDOList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(bdcZsDOList)){
            for (BdcZsDO zsDO : bdcZsDOList){
                if (StringUtils.isNotBlank(zsDO.getZsid()) && StringUtils.isNotBlank(zsDO.getBdcqzh())){
                    BdcZssdDO zssdQO = new BdcZssdDO();
                    zssdQO.setCqzh(zsDO.getBdcqzh());
                    zssdQO.setZsid(zsDO.getZsid());
                    List<BdcZssdDO> currentBdcZssdDOList = bdcSdFeignService.queryBdczsSd(zssdQO);
                    if (CollectionUtils.isNotEmpty(currentBdcZssdDOList)){
                        allBdcZssdDOList.addAll(currentBdcZssdDOList);
                    }
                }
            }
        }
        return allBdcZssdDOList;
    }


    /**
     * version 1.0
     *
     * @return
     * @description 项目历史关系
     * @date 2019/3/11
     * @author <a href ="mailto:wangwei2@gtmap.cn">wangwei2</a>
     */
    @GetMapping("/xmlsgx")
    public BdcXmLsgxDTO listXmHistory(@RequestParam(value = "xmid", required = false) String xmid){
        if (StringUtils.isBlank(xmid)){
            throw new AppException("项目ID或不动产单元号不能为空！");
        }
        return bdcXmFeignService.queryBdcxmLsgxByXmid(xmid);
    }

    /**
     * version 1.0
     *
     * @return
     * @description 项目历史关系
     * @date 2019/3/11
     * @author <a href ="mailto:wangwei2@gtmap.cn">wangwei2</a>
     */
    @GetMapping("/xm")
    public Object listXm(@LayuiPageable Pageable pageable, @RequestParam(value = "gzlslid") String gzlslid){
        if (StringUtils.isBlank(gzlslid)){
            throw new AppException("项目ID或不动产单元号不能为空！");
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(bdcXmQO);
        return addLayUiCode(new PageImpl(bdcXmDOS, pageable, bdcXmDOS.size()));
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 产权变更历史关系
     */
    @GetMapping("/cqbgls")
    public Object listBdcCqBgLs(@RequestParam(value = "bdcdyh", required = false) String bdcdyh){
        if (StringUtils.isBlank(bdcdyh)){
            throw new AppException("不动产单元号不能为空！");
        }
        return bdcLsgxFeignService.listBdcCqBgLs(bdcdyh);
    }

    /**
     * 生成项目历史关系
     * @param xmid  当前项目ID
     * @param yxmid 原项目ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping("/sc/lsgx")
    public void generateLsgx(@RequestParam(value = "xmid") String xmid,
                             @RequestParam(value = "yxmid") String yxmid){
        if(StringUtils.isAnyBlank(xmid, yxmid)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到项目ID或原项目ID信息。");
        }
        BdcXmLsgxDO bdcXmLsgxDO = new BdcXmLsgxDO();
        bdcXmLsgxDO.setXmid(xmid);
        bdcXmLsgxDO.setYxmid(yxmid);
        List<BdcXmLsgxDO> bdcXmLsgxDOList = new ArrayList<>();
        bdcXmLsgxDOList.add(bdcXmLsgxDO);
        this.bdcXmFeignService.batchInsertBdcXmLsgx(bdcXmLsgxDOList);
    }

}
