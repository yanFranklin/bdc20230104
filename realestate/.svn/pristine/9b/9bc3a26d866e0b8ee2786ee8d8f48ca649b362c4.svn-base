package cn.gtmap.realestate.init.service.qlxx.qlfl.impl;

import cn.gtmap.realestate.common.core.domain.BdcCfDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.dto.building.DjxxQlrDTO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.core.service.BdcQlrService;
import cn.gtmap.realestate.init.service.qlxx.qlfl.InitBdcCfAbstractService;
import cn.gtmap.realestate.init.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0  2018/11/19
 * @description 从地籍数据 加载数据到查封
 */
@Service
public class InitLpbToBdcCfServiceImpl extends InitBdcCfAbstractService {
    private static Logger logger = LoggerFactory.getLogger(InitLpbToBdcCfServiceImpl.class);

    /**
     * 查封流程查封状态立即生效，配置false则到登簿环节再生效
     */
    @Value("${init.cfztsx:true}")
    private Boolean cfztsx;


    @Autowired
    BdcQlrService bdcQlrService;

    @Override
    public String getVal() {
        return Constants.QLSJLY_LPB;
    }

    @Override
    public BdcQl initQlxx(InitServiceQO initServiceQO) throws Exception {
        // 通过地籍数据对查封信息赋值
        BdcCfDO bdcCf = initFwFromLpb(initServiceQO, BdcCfDO.class);

        //获取权利人数据
        List<Object> list= bdcQlrService.queryDjQlr(initServiceQO,null);
        if(CollectionUtils.isNotEmpty(list)){
            String methodName;
            if(list.get(0) instanceof DjxxQlrDTO){
                methodName="getQlrmc";
            }else{
                methodName="getQlr";
            }
            bdcCf.setBzxr(StringToolUtils.resolveBeanToAppendStr(list, methodName, "、"));
        }
        //查封类型没值的话获取
        if(bdcCf.getCflx()==null){
            bdcCf.setCflx(bdcCfService.queryCflx(initServiceQO.getBdcdyh(),initServiceQO.getXmid(),initServiceQO.getYxmid()));
        }
        //处理查封顺序
        bdcCf=bdcCfService.dealCfsx(bdcCf);
        //赋值现势
        if(cfztsx) {
            bdcCf.setQszt(CommonConstantUtils.QSZT_VALID);
            if(initServiceQO.getBdcXm()!=null){
                initServiceQO.getBdcXm().setQszt(CommonConstantUtils.QSZT_VALID);
            }
        } else {
            logger.warn("当前系统设置查封流程创建不立即生效，当前件项目ID：{}", bdcCf.getXmid());
        }
        return dealCfjssj(bdcCf);
    }
}
