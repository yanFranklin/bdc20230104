package cn.gtmap.realestate.init.service.zsxx.impl;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.core.service.BdcXmService;
import cn.gtmap.realestate.init.service.zsxx.InitBdcZsBaseAbstractService;
import cn.gtmap.realestate.init.service.zsxx.InitBdcZslxAbstractService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 初始化证书信息
 *
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/7.
 * @description
 */
@Service
public class InitBdcZsxxServiceImpl extends InitBdcZslxAbstractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(InitBdcZsxxServiceImpl.class);

    /**
     * 48716 【盐城】换证和遗失补证业务自动带入上一手附记内容
     */
    @Value("#{'${csh.fjyql.gzldyids:}'.split(',')}")
    private List<String> fjyqlgzl;

    /**
     * 配置csh.fjyql.gzldyids后，也能获取模板和权利附记数据
     */
    @Value("#{'${csh.mbfjqlfj.gzldyids:}'.split(',')}")
    private List<String> mbfjqlfj;

    /**
     * 批量流程一本证的工作流定义id
     */
    @Value("#{'${pllcybz.gzldyid:}'.split(',')}")
    private List<String> pllcybzGzldyidList;

    @Autowired
    private BdcXmService bdcXmService;

    @Override
    public String getVal() {
        return CommonConstantUtils.ZSLX_ZS.toString();
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
        // 获取权利
        BdcQl bdcQl = bdcQllxService.queryQllxDO(bdcXm.getXmid());
        // 生成不动产证书
        BdcZsDO bdcZsmb = bdcXtZsmbPzService.queryZsmbContent(bdcXm);

        //模板配置
        initServiceQO.setBdcZsMbDO(bdcZsmb);

        for (int i = 0;i<bdcQlrList.size();i++) {
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

            // 批量生成一本证，如果权利类型不同，将权利类型设置为详见附记
            if(CollectionUtils.isNotEmpty(pllcybzGzldyidList) && pllcybzGzldyidList.contains(bdcXm.getGzldyid())){
                if(StringUtils.isNotBlank(bdcXm.getGzlslid())){
                    List<BdcXmDTO> bdcXmDTOList = bdcXmService.listXmBfxx(bdcXm.getGzlslid(),null);
                    if(CollectionUtils.isNotEmpty(bdcXmDTOList)){
                        Set<Integer> qllxSet = new HashSet<>();
                        for(BdcXmDTO bdcxm : bdcXmDTOList) {
                            if(bdcxm.getQllx()!= null){
                                qllxSet.add(bdcxm.getQllx());
                            }
                        }
                        if(qllxSet.size() > 1){
                            bdcZs.setQllx(CommonConstantUtils.QLLX_XJFJ);
                        }
                    }
                }
            }

            // 证书证明类型
            bdcZs.setZslx(CommonConstantUtils.ZSLX_ZS);
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
            //是否直接取权利中的数据
            boolean sfyqlfj = CollectionUtils.isNotEmpty(fjyqlgzl) && fjyqlgzl.contains(bdcXm.getGzldyid());

            //获取权力和模板附记数据
            boolean mbqlfj = CollectionUtils.isNotEmpty(mbfjqlfj) && mbfjqlfj.contains(bdcXm.getGzldyid());

            String fj=setFj(bdcZs,bdcQl,bdcXm.getXmid(),bdcQlrList,initServiceQO.isZsyl(),initServiceQO.isSjbl(),sfhxqlfj,sfyqlfj,mbqlfj);
            // 获得权利其他状况
            setZsQlqtzk(bdcZs,bdcXm,initServiceQO,bdcQlrList);

            // 存储附记
            bdcZs.setFj(fj);
            bdcZsList.add(bdcZs);
        }
        return bdcZsList;
    }
}
