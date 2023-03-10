package cn.gtmap.realestate.init.service.jtcy.impl;

import cn.gtmap.realestate.common.core.domain.BdcJtcyDO;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcQlrHkbGxDO;
import cn.gtmap.realestate.common.core.domain.building.CbzdCbfDO;
import cn.gtmap.realestate.common.core.domain.building.FwFcqlrDO;
import cn.gtmap.realestate.common.core.domain.building.FwJtcyDO;
import cn.gtmap.realestate.common.core.domain.building.NydJtcyDO;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.building.NydDjdcbResponseDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcBhFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.CbzdFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.FwJtcyFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.mapper.BdcJtcyMapper;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.jtcy.InitBdcQlrJtcyAbstractService;
import cn.gtmap.realestate.init.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/8/5.
 * @description
 */
@Service
public class InitLpbToBdcQlrJtcyServiceImpl extends InitBdcQlrJtcyAbstractService {
    @Autowired
    private DozerBeanMapper initDozerMapper;

    @Autowired
    private BdcJtcyMapper bdcJtcyMapper;
    @Autowired
    private BdcBhFeignService bdcBhFeignService;
    @Value("#{'${cbjyq.gzldyids:}'.split(',')}")
    private List<String> cbjyqgzldyids;
    @Value("#{'${ncqqdj.gzldyids:}'.split(',')}")
    private List<String> ncqqdjgzldyids;
    @Autowired
    private CbzdFeignService cbzdFeignService;
    @Autowired
    private FwJtcyFeignService fwJtcyFeignService;


    @Override
    public String getVal() {
        return Constants.QLRSJLY_LPB.toString();
    }
    @Override
    public InitServiceDTO init(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) throws Exception {
        String gzldyid = initServiceQO.getBdcXm().getGzldyid();
        if(CollectionUtils.isNotEmpty(ncqqdjgzldyids) && gzldyid != null &&ncqqdjgzldyids.contains(gzldyid)){
            if(CollectionUtils.isNotEmpty(initServiceQO.getBdcdyResponseDTO().getFwJtcyDOList())){
                List<FwFcqlrDO> fwFcqlrDOList = initServiceQO.getBdcdyResponseDTO().getQlrList();
                // ????????????????????????????????????
                List<BdcQlrHkbGxDO> bdcQlrHkbGxDOList = new ArrayList<>();
                // ????????????????????????
                List<BdcJtcyDO> allJtcyList = new ArrayList<>();
                //?????????????????????????????????????????????
                List<FwJtcyDO> qjJtcyAll = initServiceQO.getBdcdyResponseDTO().getFwJtcyDOList();
                List<BdcQlrDO> qlrDOList = initServiceQO.getBdcQlrList();
                qlrDOList = qlrDOList.stream().filter(bdcQlrDO -> bdcQlrDO.getQlrlb()!=null && StringUtils.equals(CommonConstantUtils.QLRLB_QLR,bdcQlrDO.getQlrlb())).collect(Collectors.toList());
                for(BdcQlrDO qlr:qlrDOList){
                    //??????????????????????????????????????????
                    List<BdcJtcyDO> bdcJtcy  = bdcJtcyMapper.queryLastBbhJtcyByHzZJH(qlr.getZjh());
                    // ???????????????????????????????????????????????????
                    List<FwFcqlrDO> collect = fwFcqlrDOList.stream().filter(fwFcqlrDO -> fwFcqlrDO.getQlrzjh() != null && fwFcqlrDO.getQlrzjh().equals(qlr.getZjh())).collect(Collectors.toList());
                    if(CollectionUtils.isNotEmpty(collect)){
                        List<FwJtcyDO> qjJtcy = qjJtcyAll.stream().filter(fwJtcyDO -> fwJtcyDO.getQlrIndex() != null && fwJtcyDO.getQlrIndex().equals(collect.get(0).getFwFcqlrIndex())).collect(Collectors.toList());
                        // ???????????????
                        String bkbbm = null;
                        // ??????????????????
                        Integer hkbbbh = null;
                        // ??????????????????????????????
                        List<BdcJtcyDO> scJtcyList = new ArrayList<>();
                        if(CollectionUtils.isNotEmpty(bdcJtcy)){
                            // ????????????????????????????????????????????????
                            if(compareJtcy(qjJtcy,bdcJtcy)){
                                // ???????????? ?????????????????????????????????????????? ????????????????????????
                                bkbbm = bdcJtcy.get(0).getHkbbm();
                                hkbbbh = bdcJtcy.get(0).getHkbbbh();
                            }else{
                                //  ?????????  ??????????????????????????????
                                scJtcyList = creatJtcyByFwJtcy(qjJtcy,bdcJtcy.get(0).getHkbbm(),bdcJtcy.get(0).getHkbbbh()+1);
                            }
                        }else{
                            // ?????????????????????  ??????????????????
                            scJtcyList = creatJtcyByFwJtcy(qjJtcy,"",null);
                        }
                        if(CollectionUtils.isNotEmpty(scJtcyList)){
                            bkbbm = scJtcyList.get(0).getHkbbm();
                            hkbbbh = scJtcyList.get(0).getHkbbbh();
                            // ????????????????????????
                            allJtcyList.addAll(scJtcyList);
                        }
                        if(bkbbm!=null && hkbbbh!=null){
                            // ?????????????????????????????????
                            BdcQlrHkbGxDO bdcQlrHkbGxDO = createBdcQlrHkbGx(qlr.getQlrid(),
                                    bkbbm,hkbbbh);
                            bdcQlrHkbGxDOList.add(bdcQlrHkbGxDO);
                        }
                    }
                }

                // ????????????????????????????????????
                if(CollectionUtils.isNotEmpty(initServiceDTO.getBdcQlrHkbGxDOList())){
                    initServiceDTO.getBdcQlrHkbGxDOList().addAll(bdcQlrHkbGxDOList);
                }else{
                    initServiceDTO.setBdcQlrHkbGxDOList(bdcQlrHkbGxDOList);
                }
                // ????????????????????????
                if(CollectionUtils.isNotEmpty(initServiceDTO.getBdcJtcyDOList())){
                    initServiceDTO.getBdcJtcyDOList().addAll(allJtcyList);
                }else{
                    initServiceDTO.setBdcJtcyDOList(allJtcyList);
                }
            }
        }else{
            // ???????????????????????????
            List<BdcQlrDO> qlrDOList = initServiceQO.getBdcQlrList();
            if(CollectionUtils.isNotEmpty(qlrDOList)){
                if(initServiceQO.getDjxxResponseDTO() != null && initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO() instanceof NydDjdcbResponseDTO){
                    // ???????????????
                    List<CbzdCbfDO> cbfDOList = ((NydDjdcbResponseDTO) initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO()).getCbzdCbfDOList();
                    // ???????????????????????????
                    List<NydJtcyDO> nydJtcyDOList = ((NydDjdcbResponseDTO) initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO()).getCbfJtcyDOList();
                    // ????????????????????????????????????
                    List<BdcQlrHkbGxDO> bdcQlrHkbGxDOList = new ArrayList<>();
                    // ????????????????????????
                    List<BdcJtcyDO> allJtcyList = new ArrayList<>();
                    if(CollectionUtils.isNotEmpty(cbfDOList)){
                        for(BdcQlrDO qlr:qlrDOList){
                            if(qlr!=null && StringUtils.equals(CommonConstantUtils.QLRLB_QLR,qlr.getQlrlb()) && StringUtils.isNotBlank(qlr.getZjh())){
                                // ??????????????????????????????
                                List<BdcJtcyDO> scJtcyList = new ArrayList<>();
                                // ???????????????
                                String bkbbm = null;
                                // ??????????????????
                                Integer hkbbbh = null;
                                // ?????????????????????????????????????????????
                                List<CbzdCbfDO> qlrCbf = cbfDOList.stream().filter(cbf -> qlr.getZjh().equals(cbf.getCbfzjhm())).collect(Collectors.toList());
                                if(CollectionUtils.isNotEmpty(qlrCbf)){
                                    // ????????????????????????????????????????????????
                                    List<BdcJtcyDO> bdcJtcyDOList = bdcJtcyMapper.queryLastBbhJtcyByHzZJH(qlrCbf.get(0).getCbfzjhm());
                                    // ?????????????????????????????????????????????
                                    List<NydJtcyDO> qjJtcy =new ArrayList<>();
                                    if(CollectionUtils.isNotEmpty(nydJtcyDOList)) {
                                        qjJtcy = nydJtcyDOList.stream().filter(jtcyDO -> qlrCbf.get(0).getCbzdCbfIndex().equals(jtcyDO.getJtIndex())).collect(Collectors.toList());
                                    }
                                    if(CollectionUtils.isNotEmpty(bdcJtcyDOList)){
                                        // ????????????????????????????????????????????????
                                        if(bdcJtcyService.compareJtcy(qjJtcy,bdcJtcyDOList)){
                                            // ???????????? ?????????????????????????????????????????? ????????????????????????
                                            bkbbm = bdcJtcyDOList.get(0).getHkbbm();
                                            hkbbbh = bdcJtcyDOList.get(0).getHkbbbh();
                                        }else{
                                            //  ?????????  ??????????????????????????????
                                            scJtcyList = creatJtcyByNydJtcy(qjJtcy,bdcJtcyDOList.get(0).getHkbbm(),bdcJtcyDOList.get(0).getHkbbbh()+1);
                                        }
                                    }else{
                                        // ?????????????????????  ??????????????????
                                        scJtcyList = creatJtcyByNydJtcy(qjJtcy,"",null);
                                    }
                                    if(CollectionUtils.isNotEmpty(scJtcyList)){
                                        bkbbm = scJtcyList.get(0).getHkbbm();
                                        hkbbbh = scJtcyList.get(0).getHkbbbh();
                                        // ????????????????????????
                                        allJtcyList.addAll(scJtcyList);
                                    }
                                    if(bkbbm!=null && hkbbbh!=null){
                                        // ?????????????????????????????????
                                        BdcQlrHkbGxDO bdcQlrHkbGxDO = createBdcQlrHkbGx(qlr.getQlrid(),
                                                bkbbm,hkbbbh);
                                        bdcQlrHkbGxDOList.add(bdcQlrHkbGxDO);
                                    }
                                }
                            }
                        }
                    }
                    // ????????????????????????????????????
                    if(CollectionUtils.isNotEmpty(initServiceDTO.getBdcQlrHkbGxDOList())){
                        initServiceDTO.getBdcQlrHkbGxDOList().addAll(bdcQlrHkbGxDOList);
                    }else{
                        initServiceDTO.setBdcQlrHkbGxDOList(bdcQlrHkbGxDOList);
                    }
                    // ????????????????????????
                    if(CollectionUtils.isNotEmpty(initServiceDTO.getBdcJtcyDOList())){
                        initServiceDTO.getBdcJtcyDOList().addAll(allJtcyList);
                    }else{
                        initServiceDTO.setBdcJtcyDOList(allJtcyList);
                    }
                }
            }
        }

        return initServiceDTO;
    }

    /**
     * ???????????????????????????????????????????????????
     * @param nydJtcyDOList
     * @return
     */
    private List<BdcJtcyDO> creatJtcyByNydJtcy(List<NydJtcyDO> nydJtcyDOList,String hkbbm,Integer hkbbbh) {
        List<BdcJtcyDO> bdcJtcyDOList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(nydJtcyDOList)){
            if(StringUtils.isBlank(hkbbm)) {
                hkbbm = bdcBhFeignService.queryBh(Constants.YWLX_HKB, CommonConstantUtils.ZZSJFW_YEAR);
            }
            if(hkbbbh ==null){
                hkbbbh =Constants.JTCY_HKB_BBH;
            }
            if(StringUtils.isNotBlank(hkbbm)){
                for(NydJtcyDO nydJtcyDO:nydJtcyDOList){
                    BdcJtcyDO jtcyDO = new BdcJtcyDO();
                    jtcyDO.setHkbbbh(hkbbbh);
                    jtcyDO.setHkbbm(hkbbm);
                    initDozerMapper.map(nydJtcyDO,jtcyDO);
                    jtcyDO.setJtcyid(UUIDGenerator.generate16());
                    bdcJtcyDOList.add(jtcyDO);
                }
            }else{
                throw new AppException("???????????????????????????");
            }
        }
        return bdcJtcyDOList;
    }

    /**
     * ??????????????????????????????
     * @param qlrid
     * @param hkbbm
     * @param hkbbbh
     * @return
     */
    private BdcQlrHkbGxDO createBdcQlrHkbGx(String qlrid, String hkbbm, Integer hkbbbh) {
        if(StringUtils.isBlank(qlrid) ||StringUtils.isBlank(hkbbm) || hkbbbh == null){
            throw new AppException("???????????????????????????????????????????????????");
        }
        BdcQlrHkbGxDO bdcQlrHkbGxDO = new BdcQlrHkbGxDO();
        bdcQlrHkbGxDO.setGxid(UUIDGenerator.generate16());
        bdcQlrHkbGxDO.setHkbbm(hkbbm);
        bdcQlrHkbGxDO.setHkbbbh(hkbbbh);
        bdcQlrHkbGxDO.setQlrid(qlrid);
        return bdcQlrHkbGxDO;
    }

    private boolean compareJtcy(List<FwJtcyDO> fwJtcyDOList, List<BdcJtcyDO> jtcyDOList){
        boolean isEqual = false;
        if(CollectionUtils.isNotEmpty(fwJtcyDOList) && CollectionUtils.isNotEmpty(jtcyDOList) && fwJtcyDOList.size()==jtcyDOList.size()){
            for(FwJtcyDO fwJtcyDO:fwJtcyDOList){
                List<BdcJtcyDO> list = jtcyDOList.stream().filter(jtcy -> StringUtils.isNoneBlank(jtcy.getJtcyzjh(),jtcy.getJtcymc(),jtcy.getYhzgx()) &&jtcy.getJtcyzjh().equals(fwJtcyDO.getZjh()) &&jtcy.getJtcymc().equals(fwJtcyDO.getXm()) &&jtcy.getYhzgx().equals(fwJtcyDO.getGx())).collect(Collectors.toList());
                if(CollectionUtils.isEmpty(list)){
                    return isEqual;
                }
            }
            isEqual = true;
        }
        return isEqual;
    }

    /**
     * ???????????????????????????????????????????????????
     * @param fwJtcyDOList
     * @return
     */
    private List<BdcJtcyDO> creatJtcyByFwJtcy(List<FwJtcyDO> fwJtcyDOList,String hkbbm,Integer hkbbbh) {
        List<BdcJtcyDO> bdcJtcyDOList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(fwJtcyDOList)){
            if(StringUtils.isBlank(hkbbm)) {
                hkbbm = bdcBhFeignService.queryBh(Constants.YWLX_HKB, CommonConstantUtils.ZZSJFW_YEAR);
            }
            if(hkbbbh ==null){
                hkbbbh =Constants.JTCY_HKB_BBH;
            }
            if(StringUtils.isNotBlank(hkbbm)){
                for(FwJtcyDO fwJtcyDO:fwJtcyDOList){
                    BdcJtcyDO jtcyDO = new BdcJtcyDO();
                    jtcyDO.setHkbbbh(hkbbbh);
                    jtcyDO.setHkbbm(hkbbm);
                    initDozerMapper.map(fwJtcyDO,jtcyDO);
                    jtcyDO.setJtcyid(UUIDGenerator.generate16());
                    bdcJtcyDOList.add(jtcyDO);
                }
            }else{
                throw new AppException("???????????????????????????");
            }
        }
        return bdcJtcyDOList;
    }
}
