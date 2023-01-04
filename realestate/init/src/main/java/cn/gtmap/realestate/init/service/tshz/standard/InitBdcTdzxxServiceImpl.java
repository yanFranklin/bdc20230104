package cn.gtmap.realestate.init.service.tshz.standard;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.core.service.BdcFctdPpgxService;
import cn.gtmap.realestate.init.service.other.InitServiceQoService;
import cn.gtmap.realestate.init.service.tshz.InitBdcTsHzService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/4/26.
 * @description 关联土地证的如果房屋信息没有数据则读取土地信息
 */
@Service("bdcTdzxxServiceImpl")
public class InitBdcTdzxxServiceImpl implements InitBdcTsHzService {
    @Autowired
    private InitServiceQoService initServiceQoService;
    @Autowired
    private BdcFctdPpgxService bdcFctdPpgxService;

    @Override
    public InitServiceDTO tshz(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) throws Exception {
        //属于房地产权
        if(initServiceDTO!=null){
            //部分对照的重新取数据
            if(initServiceQO.isSfdzbflpbsj()){
                initServiceQO.setBdcdyResponseDTO(null);
                initServiceQO.setDjxxResponseDTO(null);
            }
            if(initServiceDTO.getBdcQl() instanceof BdcFdcqDO){
                //存在历史关系数据 过滤外联数据 要存有土地证的才处理
                if(CollectionUtils.isNotEmpty(initServiceDTO.getBdcXmLsgxList())){
                    //产权的原项目
                    BdcXmDO yxmDO=null;
                    if(initServiceDTO.getBdcQl() instanceof BdcFdcqDO && StringUtils.isNotBlank(initServiceQO.getYxmid())){
                        BdcFdcqDO bdcFdcqDO=(BdcFdcqDO)initServiceDTO.getBdcQl();
                        yxmDO = initServiceQoService.queryYbdcxm(initServiceQO.getYxmid(),initServiceQO);
                        //匹配土地证
                        if(yxmDO!=null && CommonConstantUtils.SF_S_DM.equals(yxmDO.getClsjppzt())){
                            //如果有空的则获取匹配的值
                            if(bdcFdcqDO.getFttdmj()==null  || bdcFdcqDO.getDytdmj()==null || bdcFdcqDO.getTdsyjssj()==null || bdcFdcqDO.getTdsyqssj()==null){
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
                                        if(bdcFdcqDO.getTdsyqssj() ==null){
                                            bdcFdcqDO.setTdsyqssj(((BdcJsydsyqDO)ytdql).getSyqqssj());
                                        }
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
