package cn.gtmap.realestate.init.service.zsxx.impl;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.zsxx.InitBdcZslxAbstractService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 初始化证明单信息
 *
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/7.
 * @description
 */
@Service
public class InitBdcZmdxxServiceImpl extends InitBdcZslxAbstractService {

    @Override
    public String getVal() {
        return CommonConstantUtils.ZSLX_ZMD.toString();
    }


    /**
     * 生成不动产权证明单
     *
     * @param bdcXm
     * @param bdcQlrList
     * @return
     */
    @Override
    public List<BdcZsDO> setBdcZs(BdcXmDO bdcXm, List<BdcQlrDO> bdcQlrList,InitServiceQO initServiceQO,boolean dqLzr) {
        List<BdcZsDO> bdcZsList = new ArrayList<>();
        // 获取权利
        BdcQl bdcQl = bdcQllxService.queryQllxDO(bdcXm.getXmid());
        // 生成不动产证书
        BdcZsDO bdcZsmb = bdcXtZsmbPzService.queryZsmbContent(bdcXm);
        //模板配置
        initServiceQO.setBdcZsMbDO(bdcZsmb);
        for ( int i = 0; i< bdcQlrList.size();i++) {
            BdcQlrDO bdcQlr = bdcQlrList.get(i);
            BdcZsDO bdcZs = new BdcZsDO();
            //获取领证人
            if(dqLzr){
                BdcLzrDO bdcLzrDO=queryLzr(bdcQlr,bdcXm.getXmid());
                if(bdcLzrDO!=null){
                    dozerUtils.initBeanDateConvert(bdcLzrDO,bdcZs);
                }
            }
            dozerUtils.initBeanDateConvert(bdcQlr,bdcZs);
            dozerUtils.initBeanDateConvert(bdcXm,bdcZs);
            // 证书证明类型
            bdcZs.setZslx(CommonConstantUtils.ZSLX_ZMD);
            //获取权利类型名称
            String qllxMc=bdcZdCache.getFeildValue(BdcZdQllxDO.class.getSimpleName(),bdcXm.getQllx(),BdcZdQllxDO.class);
            // 证明权利或事项
            bdcZs.setZmqlsx(qllxMc);
            //处理附记
            //分别持证的最后才回写权利附记
            boolean sfhxqlfj = false;
            if(i == bdcQlrList.size()-1){
                sfhxqlfj = true;
            }
            String fj=setFj(bdcZs,bdcQl,bdcXm.getXmid(),bdcQlrList,initServiceQO.isZsyl(),initServiceQO.isSjbl(),sfhxqlfj,false,false);

            // 获得权利其他状况
            setZsQlqtzk(bdcZs,bdcXm,initServiceQO,bdcQlrList);
            // 存储附记
            bdcZs.setFj(fj);
            bdcZsList.add(bdcZs);
        }
        return bdcZsList;
    }
}
