package cn.gtmap.realestate.init.service.zsxx.impl;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.zsxx.InitBdcZslxAbstractService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 初始化证明信息
 *
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/7.
 * @description
 */
@Service
public class InitBdcZmxxServiceImpl extends InitBdcZslxAbstractService {

    @Override
    public String getVal() {
        return CommonConstantUtils.ZSLX_ZM.toString();
    }


    /**
     * 生成不动产证书
     *
     * @param bdcXm
     * @param bdcQlrList
     * @return
     */
    @Override
    public List<BdcZsDO> setBdcZs(BdcXmDO bdcXm, List<BdcQlrDO> bdcQlrList,InitServiceQO initServiceQO,boolean dqLzr) {
        List<BdcZsDO> bdcZsList = new ArrayList<>();
        // 获取义务人
        List<BdcQlrDO> bdcYwrList = bdcQlrService.listBdcQlrOrderBySxh(bdcXm.getXmid(), CommonConstantUtils.QLRLB_YWR);
        // 获取权利
        BdcQl bdcQl = bdcQllxService.queryQllxDO(bdcXm.getXmid());
        // 生成不动产证书
        BdcZsDO bdcZsmb = bdcXtZsmbPzService.queryZsmbContent(bdcXm);
        //模板配置
        initServiceQO.setBdcZsMbDO(bdcZsmb);

        for (int i=0; i<bdcQlrList.size() ;i++) {
            BdcQlrDO bdcQlr = bdcQlrList.get(i);
            // 生成证明
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
            bdcZs.setZslx(CommonConstantUtils.ZSLX_ZM);
            String qllxMc="";
            //预告的话需查看是否是预告抵押
            if(bdcQl instanceof  BdcYgDO && Arrays.asList(CommonConstantUtils.YGDJZL_QTDYYG,CommonConstantUtils.YGDJZL_YSSPFDYYG).contains(((BdcYgDO)bdcQl).getYgdjzl())){
                qllxMc="预抵押";
            }
            if(StringUtils.isBlank(qllxMc)){
                //获取权利类型名称
                qllxMc=bdcZdCache.getFeildValue(BdcZdQllxDO.class.getSimpleName(),bdcXm.getQllx(),BdcZdQllxDO.class);
            }
            // 证明权利或事项
            bdcZs.setZmqlsx(qllxMc);
            // 设定义务人
            if (CollectionUtils.isNotEmpty(bdcYwrList)) {
                bdcZs.setYwr(StringToolUtils.resolveBeanToAppendStr(bdcYwrList, "getQlrmc", " "));
            }

            // 获得权利其他状况
            setZsQlqtzk(bdcZs,bdcXm,initServiceQO,bdcQlrList);

            //处理附记
            //分别持证的最后才回写权利附记
            boolean sfhxqlfj = false;
            if(i == bdcQlrList.size()-1){
                sfhxqlfj = true;
            }
            setFj(bdcZs,bdcQl,bdcXm.getXmid(),bdcQlrList,initServiceQO.isZsyl(),initServiceQO.isSjbl(),sfhxqlfj,false,false);
            bdcZsList.add(bdcZs);
        }
        return bdcZsList;
    }
}
