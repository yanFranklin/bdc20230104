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
                // 存放权利人户口簿关系集合
                List<BdcQlrHkbGxDO> bdcQlrHkbGxDOList = new ArrayList<>();
                // 存放家庭成员集合
                List<BdcJtcyDO> allJtcyList = new ArrayList<>();
                //所有权利人在权籍的家庭成员数据
                List<FwJtcyDO> qjJtcyAll = initServiceQO.getBdcdyResponseDTO().getFwJtcyDOList();
                List<BdcQlrDO> qlrDOList = initServiceQO.getBdcQlrList();
                qlrDOList = qlrDOList.stream().filter(bdcQlrDO -> bdcQlrDO.getQlrlb()!=null && StringUtils.equals(CommonConstantUtils.QLRLB_QLR,bdcQlrDO.getQlrlb())).collect(Collectors.toList());
                for(BdcQlrDO qlr:qlrDOList){
                    //权利人在不动产的家庭成员数据
                    List<BdcJtcyDO> bdcJtcy  = bdcJtcyMapper.queryLastBbhJtcyByHzZJH(qlr.getZjh());
                    // 获取该权利人在权籍中的所有家庭成员
                    List<FwFcqlrDO> collect = fwFcqlrDOList.stream().filter(fwFcqlrDO -> fwFcqlrDO.getQlrzjh() != null && fwFcqlrDO.getQlrzjh().equals(qlr.getZjh())).collect(Collectors.toList());
                    if(CollectionUtils.isNotEmpty(collect)){
                        List<FwJtcyDO> qjJtcy = qjJtcyAll.stream().filter(fwJtcyDO -> fwJtcyDO.getQlrIndex() != null && fwJtcyDO.getQlrIndex().equals(collect.get(0).getFwFcqlrIndex())).collect(Collectors.toList());
                        // 户口簿编码
                        String bkbbm = null;
                        // 户口本版本号
                        Integer hkbbbh = null;
                        // 存放新生成的家庭成员
                        List<BdcJtcyDO> scJtcyList = new ArrayList<>();
                        if(CollectionUtils.isNotEmpty(bdcJtcy)){
                            // 对比权籍和不动产家庭成员是否一致
                            if(compareJtcy(qjJtcy,bdcJtcy)){
                                // 完全一致 取出户口本编码和户口本版本号 生成关系数据使用
                                bkbbm = bdcJtcy.get(0).getHkbbm();
                                hkbbbh = bdcJtcy.get(0).getHkbbbh();
                            }else{
                                //  不一致  新增一个户口本版本号
                                scJtcyList = creatJtcyByFwJtcy(qjJtcy,bdcJtcy.get(0).getHkbbm(),bdcJtcy.get(0).getHkbbbh()+1);
                            }
                        }else{
                            // 未找到家庭成员  生成家庭成员
                            scJtcyList = creatJtcyByFwJtcy(qjJtcy,"",null);
                        }
                        if(CollectionUtils.isNotEmpty(scJtcyList)){
                            bkbbm = scJtcyList.get(0).getHkbbm();
                            hkbbbh = scJtcyList.get(0).getHkbbbh();
                            // 放入家庭成员数据
                            allJtcyList.addAll(scJtcyList);
                        }
                        if(bkbbm!=null && hkbbbh!=null){
                            // 生成权利人与户口簿关系
                            BdcQlrHkbGxDO bdcQlrHkbGxDO = createBdcQlrHkbGx(qlr.getQlrid(),
                                    bkbbm,hkbbbh);
                            bdcQlrHkbGxDOList.add(bdcQlrHkbGxDO);
                        }
                    }
                }

                // 存放权利人户口簿关系数据
                if(CollectionUtils.isNotEmpty(initServiceDTO.getBdcQlrHkbGxDOList())){
                    initServiceDTO.getBdcQlrHkbGxDOList().addAll(bdcQlrHkbGxDOList);
                }else{
                    initServiceDTO.setBdcQlrHkbGxDOList(bdcQlrHkbGxDOList);
                }
                // 存放家庭成员数据
                if(CollectionUtils.isNotEmpty(initServiceDTO.getBdcJtcyDOList())){
                    initServiceDTO.getBdcJtcyDOList().addAll(allJtcyList);
                }else{
                    initServiceDTO.setBdcJtcyDOList(allJtcyList);
                }
            }
        }else{
            // 当前业务权利人集合
            List<BdcQlrDO> qlrDOList = initServiceQO.getBdcQlrList();
            if(CollectionUtils.isNotEmpty(qlrDOList)){
                if(initServiceQO.getDjxxResponseDTO() != null && initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO() instanceof NydDjdcbResponseDTO){
                    // 承包方集合
                    List<CbzdCbfDO> cbfDOList = ((NydDjdcbResponseDTO) initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO()).getCbzdCbfDOList();
                    // 承包方家庭成员集合
                    List<NydJtcyDO> nydJtcyDOList = ((NydDjdcbResponseDTO) initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO()).getCbfJtcyDOList();
                    // 存放权利人户口簿关系集合
                    List<BdcQlrHkbGxDO> bdcQlrHkbGxDOList = new ArrayList<>();
                    // 存放家庭成员集合
                    List<BdcJtcyDO> allJtcyList = new ArrayList<>();
                    if(CollectionUtils.isNotEmpty(cbfDOList)){
                        for(BdcQlrDO qlr:qlrDOList){
                            if(qlr!=null && StringUtils.equals(CommonConstantUtils.QLRLB_QLR,qlr.getQlrlb()) && StringUtils.isNotBlank(qlr.getZjh())){
                                // 存放新生成的家庭成员
                                List<BdcJtcyDO> scJtcyList = new ArrayList<>();
                                // 户口簿编码
                                String bkbbm = null;
                                // 户口本版本号
                                Integer hkbbbh = null;
                                // 找到当前权利人对应的承包方数据
                                List<CbzdCbfDO> qlrCbf = cbfDOList.stream().filter(cbf -> qlr.getZjh().equals(cbf.getCbfzjhm())).collect(Collectors.toList());
                                if(CollectionUtils.isNotEmpty(qlrCbf)){
                                    // 当前权利人在不动产的家庭成员数据
                                    List<BdcJtcyDO> bdcJtcyDOList = bdcJtcyMapper.queryLastBbhJtcyByHzZJH(qlrCbf.get(0).getCbfzjhm());
                                    // 当前权利人在权籍的家庭成员数据
                                    List<NydJtcyDO> qjJtcy =new ArrayList<>();
                                    if(CollectionUtils.isNotEmpty(nydJtcyDOList)) {
                                        qjJtcy = nydJtcyDOList.stream().filter(jtcyDO -> qlrCbf.get(0).getCbzdCbfIndex().equals(jtcyDO.getJtIndex())).collect(Collectors.toList());
                                    }
                                    if(CollectionUtils.isNotEmpty(bdcJtcyDOList)){
                                        // 对比权籍和不动产家庭成员是否一致
                                        if(bdcJtcyService.compareJtcy(qjJtcy,bdcJtcyDOList)){
                                            // 完全一致 取出户口本编码和户口本版本号 生成关系数据使用
                                            bkbbm = bdcJtcyDOList.get(0).getHkbbm();
                                            hkbbbh = bdcJtcyDOList.get(0).getHkbbbh();
                                        }else{
                                            //  不一致  新增一个户口本版本号
                                            scJtcyList = creatJtcyByNydJtcy(qjJtcy,bdcJtcyDOList.get(0).getHkbbm(),bdcJtcyDOList.get(0).getHkbbbh()+1);
                                        }
                                    }else{
                                        // 未找到家庭成员  生成家庭成员
                                        scJtcyList = creatJtcyByNydJtcy(qjJtcy,"",null);
                                    }
                                    if(CollectionUtils.isNotEmpty(scJtcyList)){
                                        bkbbm = scJtcyList.get(0).getHkbbm();
                                        hkbbbh = scJtcyList.get(0).getHkbbbh();
                                        // 放入家庭成员数据
                                        allJtcyList.addAll(scJtcyList);
                                    }
                                    if(bkbbm!=null && hkbbbh!=null){
                                        // 生成权利人与户口簿关系
                                        BdcQlrHkbGxDO bdcQlrHkbGxDO = createBdcQlrHkbGx(qlr.getQlrid(),
                                                bkbbm,hkbbbh);
                                        bdcQlrHkbGxDOList.add(bdcQlrHkbGxDO);
                                    }
                                }
                            }
                        }
                    }
                    // 存放权利人户口簿关系数据
                    if(CollectionUtils.isNotEmpty(initServiceDTO.getBdcQlrHkbGxDOList())){
                        initServiceDTO.getBdcQlrHkbGxDOList().addAll(bdcQlrHkbGxDOList);
                    }else{
                        initServiceDTO.setBdcQlrHkbGxDOList(bdcQlrHkbGxDOList);
                    }
                    // 存放家庭成员数据
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
     * 根据权籍家庭成员生成不动产家庭成员
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
                throw new AppException("生成户口簿编码失败");
            }
        }
        return bdcJtcyDOList;
    }

    /**
     * 生成权利人户口簿关系
     * @param qlrid
     * @param hkbbm
     * @param hkbbbh
     * @return
     */
    private BdcQlrHkbGxDO createBdcQlrHkbGx(String qlrid, String hkbbm, Integer hkbbbh) {
        if(StringUtils.isBlank(qlrid) ||StringUtils.isBlank(hkbbm) || hkbbbh == null){
            throw new AppException("参数缺失，生成权利人户口簿关系失败");
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
     * 根据权籍家庭成员生成不动产家庭成员
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
                throw new AppException("生成户口簿编码失败");
            }
        }
        return bdcJtcyDOList;
    }
}
