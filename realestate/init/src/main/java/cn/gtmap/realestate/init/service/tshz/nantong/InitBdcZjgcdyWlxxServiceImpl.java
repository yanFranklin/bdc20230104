package cn.gtmap.realestate.init.service.tshz.nantong;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.dto.building.BdcdyResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.NydDjdcbResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.QszdDjdcbResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.ZdDjdcbResponseDTO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.other.InitServiceQoService;
import cn.gtmap.realestate.init.service.tshz.InitBdcTsHzService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 在建工程带抵押的流程读取外联证书信息
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/8/29.
 * @description
 */
@Service("bdcZjgcdyWlxxServiceImpl_nantong")
public class InitBdcZjgcdyWlxxServiceImpl implements InitBdcTsHzService {

    @Value("#{'${nttscl.zjgc.wlxx:}'.split(',')}")
    private List<String> zjgcWlxx;
    @Autowired
    private InitServiceQoService initServiceQoService;

    /**
     * 在建工程带抵押的流程读取外联证书信息
     * @param initServiceQO
     * @param initServiceDTO
     * @return InitServiceDTO
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public InitServiceDTO tshz(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) throws Exception {
        if(!initServiceQO.isSfzqlpbsj() && initServiceDTO!=null && initServiceDTO.getBdcXm()!=null){
            //是在建工程带抵押流程
            if(zjgcWlxx.contains(initServiceDTO.getBdcXm().getGzldyid())){
                String zl= StringUtils.EMPTY;
                String qlxz= StringUtils.EMPTY;
                String tdyt= StringUtils.EMPTY;
                String tdyt2= StringUtils.EMPTY;
                String tdyt3= StringUtils.EMPTY;
                //当前项目
                BdcXmDO bdcXmDO=initServiceDTO.getBdcXm();
                //权籍单元信息
                BdcdyResponseDTO bdcdyResponseDTO=initServiceQO.getBdcdyResponseDTO();
                if(bdcdyResponseDTO!=null){
                    zl=bdcdyResponseDTO.getZl();
                    qlxz=bdcdyResponseDTO.getTdsyqlx();
                    tdyt=bdcdyResponseDTO.getTdyt();
                    if(StringUtils.isBlank(tdyt)){
                        //取调查表土地用途
                        if(initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO() instanceof ZdDjdcbResponseDTO){
                            tdyt=((ZdDjdcbResponseDTO) initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO()).getTdyt();
                            tdyt2=((ZdDjdcbResponseDTO) initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO()).getTdyt2();
                            tdyt3=((ZdDjdcbResponseDTO) initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO()).getTdyt3();
                        }else if(initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO() instanceof QszdDjdcbResponseDTO ){
                            tdyt=((QszdDjdcbResponseDTO) initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO()).getTdyt();
                        }else if(initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO() instanceof NydDjdcbResponseDTO){
                            tdyt=((NydDjdcbResponseDTO) initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO()).getTdyt();
                            tdyt2=((NydDjdcbResponseDTO) initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO()).getTdyt2();
                            tdyt3=((NydDjdcbResponseDTO) initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO()).getTdyt3();
                        }
                        if(StringUtils.isNotBlank(tdyt)){bdcXmDO.setZdzhyt(tdyt);}
                        if(StringUtils.isNotBlank(tdyt2)){bdcXmDO.setZdzhyt2(tdyt2);}
                        if(StringUtils.isNotBlank(tdyt3)){bdcXmDO.setZdzhyt3(tdyt3);}
                    }else{
                        bdcXmDO.setZdzhyt(tdyt);
                        bdcXmDO.setZdzhyt2(tdyt2);
                        bdcXmDO.setZdzhyt3(tdyt3);
                    }
                    if(StringUtils.isBlank(qlxz)){
                        //取调查表权利性质
                        if(initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO() instanceof ZdDjdcbResponseDTO){
                            qlxz=((ZdDjdcbResponseDTO) initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO()).getSyqlx();
                        }else if(initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO() instanceof QszdDjdcbResponseDTO ){
                            qlxz=((QszdDjdcbResponseDTO) initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO()).getSyqlx();
                        }else if(initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO() instanceof NydDjdcbResponseDTO){
                            qlxz=((NydDjdcbResponseDTO) initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO()).getSyqlx();
                        }
                    }
                    //赋值不动产项目
                    if(StringUtils.isNotBlank(qlxz)){bdcXmDO.setQlxz(qlxz);}
                    if(StringUtils.isNotBlank(zl)){bdcXmDO.setZl(zl);}
                    //产权
                    if(initServiceDTO.getBdcQl() instanceof BdcFdcqDO){
                        BdcFdcqDO bdcFdcqDO= (BdcFdcqDO) initServiceDTO.getBdcQl();
                        if(StringUtils.isNotBlank(zl)){ bdcFdcqDO.setZl(zl);}
                    }else if(initServiceDTO.getBdcQl() instanceof BdcDyaqDO){
                        BdcDyaqDO bdcDyaqDO=(BdcDyaqDO) initServiceDTO.getBdcQl();
                        //坐落
                        if(StringUtils.isNotBlank(zl)){ bdcDyaqDO.setZl(zl);}
                        //房屋抵押面积
                        if(bdcdyResponseDTO.getScjzmj()!=null){  bdcDyaqDO.setFwdymj(bdcdyResponseDTO.getScjzmj());}
                        Double tddymj=0.0;
                        if(bdcdyResponseDTO.getDytdmj()!=null){
                            tddymj+=bdcdyResponseDTO.getDytdmj();
                        }
                        if(bdcdyResponseDTO.getFttdmj()!=null){
                            tddymj+=bdcdyResponseDTO.getFttdmj();
                        }
                        //土地抵押面积
                        if(tddymj>0){  bdcDyaqDO.setTddymj(tddymj);}
                        //获取项目关系  查询外联的抵押项目
                        if(CollectionUtils.isNotEmpty(initServiceQO.getBdcXmLsgxList())){
                            for(BdcXmLsgxDO bdcXmLsgxDO:initServiceQO.getBdcXmLsgxList()){
                                //外联项目
                                if(CommonConstantUtils.SF_S_DM.equals(bdcXmLsgxDO.getWlxm())){
                                    BdcQl bdcQl=initServiceQoService.queryYql(bdcXmLsgxDO.getYxmid(),initServiceQO);
                                    //抵押的外联项目
                                    if(bdcQl instanceof BdcDyaqDO){
                                        BdcDyaqDO ydyaq= (BdcDyaqDO) bdcQl;
                                        bdcDyaqDO.setDyfs(ydyaq.getDyfs());
                                        bdcDyaqDO.setBdbzzqse(ydyaq.getBdbzzqse());
                                        bdcDyaqDO.setZgzqqdse(ydyaq.getZgzqqdse());
                                        bdcDyaqDO.setZwlxqssj(ydyaq.getZwlxqssj());
                                        bdcDyaqDO.setZwlxjssj(ydyaq.getZwlxjssj());
                                        bdcDyaqDO.setDkfs(ydyaq.getDkfs());
                                        bdcDyaqDO.setFwpgjg(ydyaq.getFwpgjg());
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return initServiceDTO;
    }
}
