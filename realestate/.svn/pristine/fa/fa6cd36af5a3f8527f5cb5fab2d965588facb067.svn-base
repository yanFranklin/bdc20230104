package cn.gtmap.realestate.init.service.tshz.standard;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.core.service.BdcQlrService;
import cn.gtmap.realestate.init.core.service.BdcXmService;
import cn.gtmap.realestate.init.service.tshz.InitBdcTsHzService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 增量添加不动产单元时做权利人读取同流程的数据
 *
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version v1.0, 2019/10/10 14:58
 */
@Service("bdcZlxmQlrServiceImpl")
public class InitBdcZlxmQlrServiceImpl implements InitBdcTsHzService {

    @Autowired
    private BdcXmService bdcXmService;
    @Autowired
    private BdcQlrService bdcQlrService;

    /**
     * 特殊后置处理
     *增量添加不动产单元时做权利人读取同流程的数据
     * @param initServiceQO
     * @param initServiceDTO *
     * @return InitServiceDTO
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public InitServiceDTO tshz(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) throws Exception {
        //增量初始化的做处理
        if (!initServiceQO.isSfzqlpbsj() && initServiceDTO != null && initServiceQO.getBdcCshFwkgSl()!=null && CommonConstantUtils.SF_S_DM.equals(initServiceQO.getBdcCshFwkgSl().getSfzlcsh())) {
            if(initServiceQO.getBdcXm()!=null && StringUtils.isNotBlank(initServiceQO.getBdcXm().getDjxl()) && StringUtils.isNotBlank(initServiceQO.getBdcXm().getGzlslid())){
                List<BdcXmDTO> list=bdcXmService.listXmBfxx(initServiceQO.getBdcXm().getGzlslid(),null);
                if(CollectionUtils.isNotEmpty(list)){
                    for(BdcXmDTO bdcXmDTO:list){
                        if(StringUtils.equals(bdcXmDTO.getDjxl(),initServiceQO.getBdcXm().getDjxl()) &&
                                !StringUtils.equals(bdcXmDTO.getXmid(),initServiceQO.getXmid())){
                            //查询项目的权利人数据
                            List<BdcQlrDO> listQlr=bdcQlrService.listBdcQlrOrderBySxh(bdcXmDTO.getXmid(),CommonConstantUtils.QLRLB_QLR);
                            if(CollectionUtils.isNotEmpty(listQlr)){
                                 for(BdcQlrDO bdcQlrDO:listQlr){
                                     bdcQlrDO.setQlrid(UUIDGenerator.generate());
                                     bdcQlrDO.setXmid(initServiceQO.getXmid());
                                 }
                                 //不为空的话把权利人数据删除掉
                                 if(CollectionUtils.isNotEmpty(initServiceDTO.getBdcQlrList())){
                                     for(BdcQlrDO bdcQlrDO:initServiceDTO.getBdcQlrList()){
                                         //义务人增加
                                         if(StringUtils.equals(CommonConstantUtils.QLRLB_YWR,bdcQlrDO.getQlrlb())){
                                             listQlr.add(bdcQlrDO);
                                         }
                                     }
                                }
                                initServiceDTO.setBdcQlrList(listQlr);
                            }
                            break;
                        }
                    }
                }
            }
        }
        return initServiceDTO;
    }
}
