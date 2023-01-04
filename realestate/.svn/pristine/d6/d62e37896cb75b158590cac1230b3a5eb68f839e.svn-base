package cn.gtmap.realestate.init.service.tshz.nantong;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.dto.building.NydDjdcbResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.QszdDjdcbResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.ZdDjdcbResponseDTO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.core.service.BdcFctdPpgxService;
import cn.gtmap.realestate.init.core.service.BdcQllxService;
import cn.gtmap.realestate.init.service.other.InitServiceQoService;
import cn.gtmap.realestate.init.service.tshz.InitBdcTsHzService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 关联土地证的读取土地证信息
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/8/29.
 * @description
 */
@Service("bdcTdzxxServiceImpl_nantong")
public class InitBdcTdzxxServiceImpl implements InitBdcTsHzService {

    @Autowired
    private BdcQllxService bdcQllxService;
    @Autowired
    private BdcFctdPpgxService bdcFctdPpgxService;
    @Autowired
    private InitServiceQoService initServiceQoService;

    @Value("#{'${nttscl.exclude.dqtdz:}'.split(',')}")
    private List<String> gzldyidList;

    /**
     * 关联土地证的读取土地证信息
     * @param initServiceQO
     * @param initServiceDTO
     * @return InitServiceDTO
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public InitServiceDTO tshz(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) throws Exception {
        // 个别流程不需要读取土地证
        if(CollectionUtils.isNotEmpty(gzldyidList) && initServiceQO.getBdcXm() != null
                && StringUtils.isNotBlank(initServiceQO.getBdcXm().getGzldyid())
                && gzldyidList.contains(initServiceQO.getBdcXm().getGzldyid())){
            return initServiceDTO;
        }
        //属于房地产权或者抵押的
        if(initServiceDTO!=null){
            //部分对照的重新取数据
            if(initServiceQO.isSfdzbflpbsj()){
                initServiceQO.setBdcdyResponseDTO(null);
                initServiceQO.setDjxxResponseDTO(null);
            }
            if(initServiceDTO.getBdcQl() instanceof BdcFdcqDO || initServiceDTO.getBdcQl() instanceof BdcDyaqDO){
                //存在历史关系数据 过滤外联数据 要存有土地证的才处理
                if(CollectionUtils.isNotEmpty(initServiceDTO.getBdcXmLsgxList())){
                    //产权的原项目
                    BdcXmDO yxmDO=null;
                    if(initServiceDTO.getBdcQl() instanceof BdcFdcqDO && StringUtils.isNotBlank(initServiceQO.getYxmid())){
                        BdcFdcqDO bdcFdcqDO=(BdcFdcqDO)initServiceDTO.getBdcQl();
                        yxmDO=initServiceQoService.queryYbdcxm(initServiceQO.getYxmid(),initServiceQO);
                        //匹配土地证
                        if(yxmDO!=null && CommonConstantUtils.SF_S_DM.equals(yxmDO.getClsjppzt())){
                            //如果有空的则获取匹配的值
                            if(bdcFdcqDO.getFttdmj()==null  || bdcFdcqDO.getDytdmj()==null || bdcFdcqDO.getTdsyjssj()==null || StringUtils.isBlank(initServiceDTO.getBdcXm().getQlxz())){
                                List<BdcFctdPpgxDO> bdcFctdPpgxList=bdcFctdPpgxService.queryBdcFctdPpgx(initServiceQO.getYxmid());
                                if(CollectionUtils.isNotEmpty(bdcFctdPpgxList) && bdcFctdPpgxList.get(0)!=null && StringUtils.isNotBlank(bdcFctdPpgxList.get(0).getTdcqxmid())){
                                    BdcQl ytdql = initServiceQoService.queryYql(bdcFctdPpgxList.get(0).getTdcqxmid(),initServiceQO);
                                    if(ytdql instanceof BdcJsydsyqDO){
                                        if(bdcFdcqDO.getFttdmj()==null){
                                            bdcFdcqDO.setFttdmj(((BdcJsydsyqDO)ytdql).getFttdmj());
                                        }
                                        if(bdcFdcqDO.getDytdmj()==null){
                                            bdcFdcqDO.setDytdmj(((BdcJsydsyqDO)ytdql).getDytdmj());
                                        }
                                        if(bdcFdcqDO.getTdsyjssj()==null){
                                            bdcFdcqDO.setTdsyjssj(((BdcJsydsyqDO)ytdql).getSyqjssj());
                                        }
                                        if(StringUtils.isBlank(initServiceDTO.getBdcXm().getQlxz()) && ((BdcJsydsyqDO)ytdql).getQlxz()!=null){
                                            initServiceDTO.getBdcXm().setQlxz(((BdcJsydsyqDO)ytdql).getQlxz().toString());
                                        }
                                    }
                                }
                            }
                        }
                    }
                    //为匹配或者无原项目的循环外联的
                    if(yxmDO==null || !CommonConstantUtils.SF_S_DM.equals(yxmDO.getClsjppzt())){
                        for(BdcXmLsgxDO bdcXmLsgxDO:initServiceDTO.getBdcXmLsgxList()){
                            //外联项目 判定是否是土地
                            if(CommonConstantUtils.SF_S_DM.equals(bdcXmLsgxDO.getWlxm())){
                                BdcXmDO ybdcXmDO=initServiceQoService.queryYbdcxm(bdcXmLsgxDO.getYxmid(),initServiceQO);
                                if(ybdcXmDO!=null && ybdcXmDO.getQllx()!=null){
                                    BdcQl bdcQl=bdcQllxService.makeSureQllx(ybdcXmDO.getQllx().toString());
                                    //土地证信息读取
                                    if(bdcQl instanceof BdcJsydsyqDO){
                                        bdcQl=initServiceQoService.queryYql(bdcXmLsgxDO.getYxmid(),initServiceQO);
                                        BdcJsydsyqDO bdcJsydsyqDO=(BdcJsydsyqDO)bdcQl;
                                        BdcXmDO bdcXmDO=initServiceDTO.getBdcXm();
                                        if(initServiceDTO.getBdcQl() instanceof BdcFdcqDO){
                                            BdcFdcqDO bdcFdcqDO=(BdcFdcqDO)initServiceDTO.getBdcQl();
                                            if(bdcJsydsyqDO.getSyqqssj()!=null){bdcFdcqDO.setTdsyqssj(bdcJsydsyqDO.getSyqqssj());}
                                            if(bdcJsydsyqDO.getSyqjssj()!=null){bdcFdcqDO.setTdsyjssj(bdcJsydsyqDO.getSyqjssj());}
                                            if(initServiceQO.getBdcdyResponseDTO()!=null){
                                                bdcFdcqDO.setFttdmj(initServiceQO.getBdcdyResponseDTO().getFttdmj());
                                                bdcFdcqDO.setDytdmj(initServiceQO.getBdcdyResponseDTO().getDytdmj());
                                            }
                                            if(bdcJsydsyqDO.getQlxz()!=null){bdcXmDO.setQlxz(bdcJsydsyqDO.getQlxz().toString());}
                                        }else{
                                            BdcDyaqDO bdcDyaqDO=(BdcDyaqDO)initServiceDTO.getBdcQl();
                                            if(bdcJsydsyqDO.getSyqmj()!=null){bdcDyaqDO.setTddymj(bdcJsydsyqDO.getSyqmj());}
                                        }
                                        if(StringUtils.isNotBlank(ybdcXmDO.getZdzhyt())){bdcXmDO.setZdzhyt(ybdcXmDO.getZdzhyt());}
                                        if(StringUtils.isNotBlank(ybdcXmDO.getZdzhyt2())){bdcXmDO.setZdzhyt2(ybdcXmDO.getZdzhyt2());}
                                        if(StringUtils.isNotBlank(ybdcXmDO.getZdzhyt3())){bdcXmDO.setZdzhyt3(ybdcXmDO.getZdzhyt3());}
                                        if(ybdcXmDO.getZdzhmj()!=null){ bdcXmDO.setZdzhmj(ybdcXmDO.getZdzhmj());}
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                if(initServiceDTO.getBdcQl() instanceof BdcFdcqDO){
                    //权利性质赋值
                    if(initServiceQO.getBdcdyResponseDTO()!=null && StringUtils.isNotBlank(initServiceQO.getBdcdyResponseDTO().getTdsyqlx())){
                        initServiceDTO.getBdcXm().setQlxz(initServiceQO.getBdcdyResponseDTO().getTdsyqlx());
                    }else if(initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO() instanceof ZdDjdcbResponseDTO &&
                            StringUtils.isNotBlank(((ZdDjdcbResponseDTO) initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO()).getSyqlx())){
                        initServiceDTO.getBdcXm().setQlxz(((ZdDjdcbResponseDTO) initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO()).getSyqlx());
                    }else if(initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO() instanceof QszdDjdcbResponseDTO &&
                            StringUtils.isNotBlank(((QszdDjdcbResponseDTO) initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO()).getSyqlx())){
                        initServiceDTO.getBdcXm().setQlxz(((QszdDjdcbResponseDTO) initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO()).getSyqlx());
                    }else if(initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO() instanceof NydDjdcbResponseDTO &&
                            StringUtils.isNotBlank(((NydDjdcbResponseDTO) initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO()).getSyqlx())){
                        initServiceDTO.getBdcXm().setQlxz(((NydDjdcbResponseDTO) initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO()).getSyqlx());
                    }else if(StringUtils.isNotBlank(initServiceQO.getYxmid())){
                        BdcXmDO yxmDO=initServiceQoService.queryYbdcxm(initServiceQO.getYxmid(),initServiceQO);
                        if(yxmDO!=null){
                            initServiceDTO.getBdcXm().setQlxz(yxmDO.getQlxz());
                        }
                    }
                }
            }else if(initServiceDTO.getBdcQl() instanceof BdcJsydsyqDO){
                //宗地的处理
                if(initServiceQO.getDjxxResponseDTO()!=null){
                    String qlxz="";
                    Date zzrq=null;
                    if(initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO() instanceof ZdDjdcbResponseDTO){
                        ZdDjdcbResponseDTO zdDjdcbResponseDTO= (ZdDjdcbResponseDTO) initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO();
                        qlxz=zdDjdcbResponseDTO.getSyqlx();
                        zzrq=zdDjdcbResponseDTO.getZzrq();
                    }else if(initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO() instanceof QszdDjdcbResponseDTO){
                        QszdDjdcbResponseDTO qszdDjdcbResponseDTO= (QszdDjdcbResponseDTO) initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO();
                        qlxz=qszdDjdcbResponseDTO.getSyqlx();
                        zzrq=qszdDjdcbResponseDTO.getZzrq();
                    }else if(initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO() instanceof NydDjdcbResponseDTO){
                        NydDjdcbResponseDTO nydDjdcbResponseDTO= (NydDjdcbResponseDTO) initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO();
                        qlxz=nydDjdcbResponseDTO.getSyqlx();
                        zzrq=nydDjdcbResponseDTO.getZzrq();
                    }
                    //权利性质赋值
                    if(StringUtils.isNotBlank(qlxz)){
                        if(NumberUtils.isNumber(qlxz)){
                            ((BdcJsydsyqDO) initServiceDTO.getBdcQl()).setQlxz(NumberUtils.toInt(qlxz));
                        }
                        initServiceDTO.getBdcXm().setQlxz(qlxz);
                    }
                    //终止日期赋值
                    if(zzrq!=null){
                        ((BdcJsydsyqDO) initServiceDTO.getBdcQl()).setSyqjssj(zzrq);
                    }
                }
            }
        }
        return initServiceDTO;
    }
}
