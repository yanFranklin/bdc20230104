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
     * ??????????????????????????????????????????
     */
    @Value("${yancheng.lsgx.xsxx:false}")
    private Boolean xsxx;

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(BdcLsgxController.class);

    /**
     * version 1.0
     *
     * @return
     * @description ??????????????????
     * @date 2019/3/11
     * @author <a href ="mailto:wangwei2@gtmap.cn">wangwei2</a>
     */
    @GetMapping("/fwhs")
    public List<FwHsBgHistoryNewDTO> listFwHsBgHistory(@RequestParam(value = "xmid", required = false) String xmid,
                                                       @RequestParam(value = "bdcdyh", required = false) String bdcdyh){
        if (StringUtils.isBlank(bdcdyh) && StringUtils.isBlank(xmid)){
            throw new AppException("??????ID????????????????????????????????????");
        }else if (StringUtils.isBlank(bdcdyh)){
            bdcdyh = this.queryBdcdyhByXmid(xmid);
        }

        return fwHsHistroyFeignService.getHsBgHistoryNewByBdcdyh(bdcdyh,"");
    }

    /**
     * version 1.0
     *
     * @return
     * @description ????????????
     * @date 2019/3/11
     * @author <a href ="mailto:wangwei2@gtmap.cn">wangwei2</a>
     */
    @GetMapping("/cqxx")
    public List<LsgxModelDTO> listLscqXx(@RequestParam(value = "xmid", required = false) String xmid,
                                         @RequestParam(value = "bdcdyh", required = false) String bdcdyh) throws ReflectiveOperationException{
        if (StringUtils.isBlank(bdcdyh) && StringUtils.isBlank(xmid)){
            throw new AppException("??????ID????????????????????????????????????");

        }else if (StringUtils.isBlank(bdcdyh)){
            bdcdyh = this.queryBdcdyhByXmid(xmid);
        }

        List<LsgxModelDTO> lsgxModelDTOS = null;
        if (StringUtils.isNotBlank(bdcdyh)){
            // ??????bdcdyh????????????????????????(?????????????????????)
            lsgxModelDTOS = bdcLsgxFeignService.getCqList(bdcdyh);
            LOGGER.info("allCqLsgxModel???????????????:" + JSONObject.toJSONString(lsgxModelDTOS));
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
     * @description ??????xmid?????????????????????????????????
     * @date 2019/3/18
     * @author <a href ="mailto:wangwei2@gtmap.cn">wangwei2</a>
     */
    @GetMapping("/cqxx/xzxx")
    public LsgxXzqlModelDTO listLscqXzxx(@RequestParam(value = "xmid") String xmid){

        LsgxXzqlModelDTO lsgxXzqlModelDTO = bdcLsgxFeignService.getAllXzqlList(xmid,CommonConstantUtils.SF_F_DM);

        // ?????? ?????????????????? ??????????????????
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
                //???????????????????????????????????????????????????????????????????????????????????????
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
     * ???????????????????????????
     *
     * @param bdcdyh ??????????????????
     * @return List ???????????????????????????
     */
    private List<BdcDysdDO> getDySdxxList(String bdcdyh){
        if (StringUtils.isBlank(bdcdyh)){
            return Collections.emptyList();
        }

        // ?????????????????????
        BdcDysdDO bdcDysdDO = new BdcDysdDO();
        bdcDysdDO.setBdcdyh(bdcdyh);
        return bdcSdFeignService.queryBdcdySd(bdcDysdDO);
    }

    /**
     * version 1.0
     *
     * @return
     * @description ????????????????????????
     * @date 2019/3/14
     * @author <a href ="mailto:wangwei2@gtmap.cn">wangwei2</a>
     */
    @GetMapping("/bdcdyh")
    public String queryBdcdyhByXmid(@RequestParam("xmid") String xmid){
        if (StringUtils.isBlank(xmid)){
            throw new NullPointerException("??????xmid?????????");
        }

        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setXmid(xmid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isEmpty(bdcXmDOList) || null == bdcXmDOList.get(0)){
            throw new AppException("???????????????????????????");
        }

        return bdcXmDOList.get(0).getBdcdyh();
    }

    /**
     * ???????????????????????????xmid?????????????????????????????????????????????
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
                        // ??????????????????
                        if (null == lsgxXzqlModelDTO){
                            lsgxXzqlModelDTO = new LsgxXzqlModelDTO();
                            lsgxXzqlModelDTO.initModelMap();
                        }
                        lsgxXzqlModelDTO = lsgxXzqlModelDTO.resolveSdxx(bdcDysdDOList,bdcZssdDOList,"");
                        // ?????????????????????????????????????????? ??????/??????_?????????_???????????? ???????????????????????????????????????
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
     * ??????xmid??????????????????  ccx 2019-10-03 ????????????????????????????????????
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
     * ????????????ID??????????????????
     *
     * @param sdxxid ??????ID
     * @return Object ??????????????????????????????
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
     * ???????????????????????????xmid????????????????????????????????????????????? ????????????
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
        LOGGER.info("??????????????????:" + JSONObject.toJSONString(lsgxModelDTOS));
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
                        // ??????????????????
                        if (null == lsgxXzqlModelDTO){
                            lsgxXzqlModelDTO = new LsgxXzqlModelDTO();
                            lsgxXzqlModelDTO.initModelMap();
                        }
                        lsgxXzqlModelDTO = lsgxXzqlModelDTO.resolveSdxx(bdcDysdDOList,bdcZssdDOList,lsgxModelDTO.getId());
                        // ?????????????????????????????????????????? ??????/??????_?????????_???????????? ???????????????????????????????????????
                        lsgxXzqlModelDTO = lsgxXzqlModelDTO.resolveLsgxXzqlXsxx(zdMap);
                    }
                }
            }

            //?????????????????????
            if (CollectionUtils.isNotEmpty(lsgxXzqlModelDTO.getSdLsgxModel())) {
                lsgxXzqlModelDTO.setSdLsgxModel(lsgxXzqlModelDTO.getSdLsgxModel()
                        .stream().distinct().collect(Collectors.toList()));
            }
        }

        return lsgxXzqlModelDTO;
    }


    /**
     * ???????????????????????????????????????
     *
     * @param xmid
     */
    public void listLscqXzxxYc(String xmid,LsgxXzqlModelDTO lsgxXzqlModelDTO){

        LsgxXzqlModelDTO curLsgxXzqlModelDTO = bdcLsgxFeignService.getAllXzqlList(xmid,CommonConstantUtils.SF_F_DM);

        if (null != curLsgxXzqlModelDTO){

            // ?????????????????????????????????  ??????ygdjzl???3???4???
            if (CollectionUtils.isNotEmpty(curLsgxXzqlModelDTO.getYgLsgxModel())){
                List<LsgxModelDTO> listCqXzxxYgNew = new ArrayList<>();
                // ????????????????????????????????????????????????
                LOGGER.info("????????????????????????:{}",JSONObject.toJSONString(curLsgxXzqlModelDTO.getYgLsgxModel()));
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
                //??????

                List<LsgxModelDTO> ygLsgxModelDTO = lsgxXzqlModelDTO.getYgLsgxModel().stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getId()))),ArrayList::new));
                lsgxXzqlModelDTO.setYgLsgxModel(ygLsgxModelDTO);
            }

            // ???????????????????????????????????????
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

            // ??????
            List<LsgxModelDTO> listDy = curLsgxXzqlModelDTO.getDyLsgxModel();
            if (CollectionUtils.isNotEmpty(listDy)){
                for (LsgxModelDTO lsgxModelDTO : listDy){
                    lsgxModelDTO.setCqid(xmid);
                }
                lsgxXzqlModelDTO.getDyLsgxModel().addAll(listDy);

                List<LsgxModelDTO> dyLsgxModelDTO = lsgxXzqlModelDTO.getDyLsgxModel().stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getId()))),ArrayList::new));
                lsgxXzqlModelDTO.setDyLsgxModel(dyLsgxModelDTO);

            }

            // ??????
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
     * @description ????????????????????????????????????????????????
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
     * @description ??????????????????
     * @date 2019/3/11
     * @author <a href ="mailto:wangwei2@gtmap.cn">wangwei2</a>
     */
    @GetMapping("/xmlsgx")
    public BdcXmLsgxDTO listXmHistory(@RequestParam(value = "xmid", required = false) String xmid){
        if (StringUtils.isBlank(xmid)){
            throw new AppException("??????ID????????????????????????????????????");
        }
        return bdcXmFeignService.queryBdcxmLsgxByXmid(xmid);
    }

    /**
     * version 1.0
     *
     * @return
     * @description ??????????????????
     * @date 2019/3/11
     * @author <a href ="mailto:wangwei2@gtmap.cn">wangwei2</a>
     */
    @GetMapping("/xm")
    public Object listXm(@LayuiPageable Pageable pageable, @RequestParam(value = "gzlslid") String gzlslid){
        if (StringUtils.isBlank(gzlslid)){
            throw new AppException("??????ID????????????????????????????????????");
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
     * @description ????????????????????????
     */
    @GetMapping("/cqbgls")
    public Object listBdcCqBgLs(@RequestParam(value = "bdcdyh", required = false) String bdcdyh){
        if (StringUtils.isBlank(bdcdyh)){
            throw new AppException("?????????????????????????????????");
        }
        return bdcLsgxFeignService.listBdcCqBgLs(bdcdyh);
    }

    /**
     * ????????????????????????
     * @param xmid  ????????????ID
     * @param yxmid ?????????ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping("/sc/lsgx")
    public void generateLsgx(@RequestParam(value = "xmid") String xmid,
                             @RequestParam(value = "yxmid") String yxmid){
        if(StringUtils.isAnyBlank(xmid, yxmid)){
            throw new AppException(ErrorCode.MISSING_ARG, "??????????????????ID????????????ID?????????");
        }
        BdcXmLsgxDO bdcXmLsgxDO = new BdcXmLsgxDO();
        bdcXmLsgxDO.setXmid(xmid);
        bdcXmLsgxDO.setYxmid(yxmid);
        List<BdcXmLsgxDO> bdcXmLsgxDOList = new ArrayList<>();
        bdcXmLsgxDOList.add(bdcXmLsgxDO);
        this.bdcXmFeignService.batchInsertBdcXmLsgx(bdcXmLsgxDOList);
    }

}
