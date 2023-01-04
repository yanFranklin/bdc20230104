package cn.gtmap.realestate.init.service.qlxx.qlfl.impl;

import cn.gtmap.realestate.common.core.domain.BdcCfDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.qlxx.qlfl.InitBdcCfAbstractService;
import cn.gtmap.realestate.init.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0  2018/11/16
 * @description 从原项目加载数据到查封
 */
@Service
public class InitYxmToBdcCfServiceImpl extends InitBdcCfAbstractService {
    private static Logger logger = LoggerFactory.getLogger(InitYxmToBdcCfServiceImpl.class);

    /**
     * 查封流程查封状态立即生效，配置false则到登簿环节再生效
     */
    @Value("${init.cfztsx:true}")
    private Boolean cfztsx;


    @Override
    public String getVal() {
        return Constants.QLSJLY_YXM;
    }

    @Override
    public BdcQl initQlxx(InitServiceQO initServiceQO) throws Exception {
        BdcCfDO bdcCf = new BdcCfDO();
        initDozerMapper.map(initServiceQO.getBdcXm(), bdcCf);
        if(StringUtils.isNotBlank(initServiceQO.getYxmid())){
            BdcQl yBdcQl;
            //获取初始化后的数据
            InitServiceDTO initServiceDTO = initServiceQO.getServiceDataMap().get(initServiceQO.getYxmid());
            if (initServiceDTO != null) {
                yBdcQl = initServiceDTO.getBdcQl();
            } else {
                yBdcQl = bdcQllxService.queryQllxDO(initServiceQO.getYxmid());
            }
            // 若原权利存在且为查封时
            if (yBdcQl instanceof BdcCfDO) {
                // 通过原权利对查封赋值
                initDozerMapper.map(yBdcQl, bdcCf);
                //原权利是查封的给予续封类型
                bdcCf.setCflx(CommonConstantUtils.CFLX_XF);
            } else {
                String bzxr = getBzxr(initServiceQO);
                bdcCf.setBzxr(bzxr);
            }
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

    /**
     * 通过传入结构获取被执行人名称
     *
     * @param initServiceQO 初始化传入结构
     * @return
     */
    private String getBzxr(InitServiceQO initServiceQO) {
        String result = "";

        //获取初始化后的数据
        InitServiceDTO initServiceDTO = initServiceQO.getServiceDataMap().get(initServiceQO.getYxmid());
        List<BdcQlrDO> qlrList;
        if (initServiceDTO != null) {
            qlrList = initServiceDTO.getBdcQlrList();
            if (CollectionUtils.isNotEmpty(qlrList)) {
                //过滤掉义务人数据
                List<BdcQlrDO> list = new ArrayList<>();
                for (BdcQlrDO bdcQlrDO : qlrList) {
                    if (StringUtils.equals(CommonConstantUtils.QLRLB_QLR, bdcQlrDO.getQlrlb())) {
                        list.add(bdcQlrDO);
                    }
                }
                qlrList = list;
            }
        } else {
            // 从原项目中获取权利人
            Example exampleQlr = new Example(BdcQlrDO.class);
            Example.Criteria criteria = exampleQlr.createCriteria().andEqualTo("xmid", initServiceQO.getYxmid());
            criteria.andEqualTo("qlrlb", CommonConstantUtils.QLRLB_QLR);
            qlrList = entityMapper.selectByExample(exampleQlr);
        }
        // 拼接被执行人
        if (CollectionUtils.isNotEmpty(qlrList)) {
            result = StringToolUtils.resolveBeanToAppendStr(qlrList,"getQlrmc","、");
        }

        return result;
    }
}
