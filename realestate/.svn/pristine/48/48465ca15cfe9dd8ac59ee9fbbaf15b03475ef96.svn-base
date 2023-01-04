package cn.gtmap.realestate.init.service.tshz.nantong;

import cn.gtmap.realestate.common.core.domain.BdcCshFwkgSlDO;
import cn.gtmap.realestate.common.core.domain.BdcFdcqDO;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.core.service.BdcXmService;
import cn.gtmap.realestate.init.service.tshz.InitBdcTsHzService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 车库车位的特殊处理
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/8/29.
 * @description
 */
@Service("bdcCkCwServiceImpl_nantong")
public class InitBdcCkCwServiceImpl implements InitBdcTsHzService {
    @Autowired
    private BdcXmService bdcXmService;
    @Value("#{'${nttscl.zdyt.ghyt:}'.split(',')}")
    private List<Integer> zdytGhyt;
    @Value("#{'${nttscl.tdsyjssj.ghyt:}'.split(',')}")
    private List<Integer> tdjssjGhyt;

    /**
     * 车库车位的特殊处理
     * @param initServiceQO
     * @param initServiceDTO
     * @return InitServiceDTO
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public InitServiceDTO tshz(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) throws Exception {
        if(!initServiceQO.isSfzqlpbsj() && initServiceDTO!=null && initServiceDTO.getBdcXm()!=null){
            //属于房地产权的做处理  土地时间为空并且土地分摊面积为空的处理
            if(initServiceDTO.getBdcQl() instanceof BdcFdcqDO &&
                    ((BdcFdcqDO) initServiceDTO.getBdcQl()).getTdsyjssj()==null
                    && ((BdcFdcqDO) initServiceDTO.getBdcQl()).getFttdmj()==null){
                //判定是出让的并且是车库的
                if(StringUtils.equals(initServiceDTO.getBdcXm().getQlxz(),"102") && ((BdcFdcqDO) initServiceDTO.getBdcQl()).getGhyt()!=null){
                    if(tdjssjGhyt.contains(((BdcFdcqDO) initServiceDTO.getBdcQl()).getGhyt())){
                        //判定是否单独发证
                        if(initServiceQO.getBdcCshFwkgSl()!=null){
                            boolean clsj=true;
                            if(initServiceQO.getBdcCshFwkgSl().getZsxh()!=null){
                                List<BdcCshFwkgSlDO> list = bdcXmService.listBdCshSl(initServiceDTO.getBdcXm().getGzlslid());
                                if(CollectionUtils.isNotEmpty(list)){
                                    for(BdcCshFwkgSlDO bdcCshFwkgSlDO:list){
                                        //非单独发证
                                        if(initServiceQO.getBdcCshFwkgSl().getZsxh().equals(bdcCshFwkgSlDO.getZsxh()) && !StringUtils.equals(bdcCshFwkgSlDO.getId(),initServiceQO.getBdcCshFwkgSl().getId())){
                                            clsj=false;
                                        }
                                    }
                                }
                            }
                            //处理时间为1900-01-01
                            if(clsj){
                                ((BdcFdcqDO) initServiceDTO.getBdcQl()).setTdsyjssj(DateUtils.formatDate("1900-01-01"));
                            }
                        }
                    }
                }
            }
            //为空时处理宗地用途
            if(initServiceDTO.getBdcXm().getZdzhyt()==null && initServiceDTO.getBdcQl() instanceof BdcFdcqDO
            && ((BdcFdcqDO) initServiceDTO.getBdcQl()).getGhyt()!=null && zdytGhyt.contains(((BdcFdcqDO) initServiceDTO.getBdcQl()).getGhyt())){
                //城镇住宅用地
                initServiceDTO.getBdcXm().setZdzhyt("071");
            }
        }
        return initServiceDTO;
    }
}
