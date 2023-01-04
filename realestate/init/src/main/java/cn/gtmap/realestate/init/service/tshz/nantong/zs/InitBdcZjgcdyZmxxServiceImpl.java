package cn.gtmap.realestate.init.service.tshz.nantong.zs;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmZsGxDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.dto.InitResultDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.core.service.BdcBdcdyService;
import cn.gtmap.realestate.init.service.other.InitServiceQoService;
import cn.gtmap.realestate.init.service.tshz.InitBdcZsTsService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 在建工程抵押证明信息(坐落和单元)
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/7/30.
 * @description
 */
@Service("bdcZjgcdyZmxxServiceImpl_nantong")
public class InitBdcZjgcdyZmxxServiceImpl implements InitBdcZsTsService {
    @Value("#{'${nttscl.zjgc.zmxx:}'.split(',')}")
    private List<String> zjgcZmxx;
    @Autowired
    private InitServiceQoService initServiceQoService;
    @Autowired
    private BdcBdcdyService bdcBdcdyService;

    /**
     * 在建工程抵押证明信息(坐落和单元)
     * @param initZsDTO 证书集合
     * @param zsyl      是否预览
     * @return InitResultDTO
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public InitResultDTO tshz(InitResultDTO initZsDTO, boolean zsyl, InitServiceQO initServiceQO) throws Exception {
        //证书类型非证书做处理
        if(initZsDTO!=null && CollectionUtils.isNotEmpty(initZsDTO.getBdcXmZsGxList())
                && CollectionUtils.isNotEmpty(initZsDTO.getBdcZsList())
                && !CommonConstantUtils.ZSLX_ZS.equals(initZsDTO.getBdcZsList().get(0).getZslx())){
            //获取一个项目
            BdcXmDO bdcXmDO=initServiceQoService.queryYbdcxm(initZsDTO.getBdcXmZsGxList().get(0).getXmid(),initServiceQO);
            //在建工程的做处理
            if(bdcXmDO!=null && zjgcZmxx.contains(bdcXmDO.getGzldyid())){
                Map<String,BdcZsDO> zsMap=new HashMap<>();
                //循环证书 先做单元处理 去掉等
                for(BdcZsDO bdcZsDO:initZsDTO.getBdcZsList()){
                    if(StringUtils.isNotBlank(bdcZsDO.getBdcdyh())){
                        bdcZsDO.setBdcdyh(bdcZsDO.getBdcdyh().replace(CommonConstantUtils.SUFFIX_PL,StringUtils.EMPTY));
                    }
                    zsMap.put(bdcZsDO.getZsid(),bdcZsDO);
                }
                //处理坐落
                for(BdcXmZsGxDO bdcXmZsGxDO:initZsDTO.getBdcXmZsGxList()){
                    //获取项目
                    BdcXmDO xm=initServiceQoService.queryYbdcxm(bdcXmZsGxDO.getXmid(),initServiceQO);
                    //非房子的
                    if(!bdcBdcdyService.judgeIsFwByBdcdyh(xm.getBdcdyh()) && StringUtils.isNotBlank(xm.getZl())){
                        BdcZsDO bdcZsDO=zsMap.get(bdcXmZsGxDO.getZsid());
                        if(bdcZsDO!=null  && StringUtils.isNotBlank(bdcZsDO.getZl())){
                            List<String> resultList = new ArrayList<>();
                            Collections.addAll(resultList,bdcZsDO.getZl().split(CommonConstantUtils.ZF_YW_DH));
                            if(resultList.size()>1){
                                resultList.remove(xm.getZl());
                            }
                            bdcZsDO.setZl(StringUtils.join(resultList,CommonConstantUtils.ZF_YW_DH));
                        }
                    }
                }
            }
        }
        return initZsDTO;
    }
}
