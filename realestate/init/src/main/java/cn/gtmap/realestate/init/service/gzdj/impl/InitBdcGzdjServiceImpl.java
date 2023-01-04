package cn.gtmap.realestate.init.service.gzdj.impl;

import cn.gtmap.realestate.common.core.domain.BdcGzdjDO;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.gzdj.InitBdcGzdjAbstractService;
import cn.gtmap.realestate.init.util.DozerUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/9/13
 * @description
 * @modify wangyinghao 55979
 */
@Service
public class InitBdcGzdjServiceImpl extends InitBdcGzdjAbstractService {

    @Autowired
    protected DozerUtils dozerUtils;
    /**
     * 更正登记类型-添加的默认值的值
     */
    @Value("${init.bdcgzdj.gzdjlx:1}")
    private String gzdjlx;

    /**
     * 更正登记-添加默认值-是否要添加默认值
     */
    @Value("${init.bdcgzdj.tjmrz:true}")
    private Boolean tjmrz;

    @Override
    public String getVal() {
        return CommonConstantUtils.SF_S_DM.toString();
    }

    @Override
    public BdcGzdjDO initBdcGzdj(InitServiceQO initServiceQO){
        BdcGzdjDO bdcGzdjDO =new BdcGzdjDO();
        bdcGzdjDO.setGzid(UUIDGenerator.generate16());
        bdcGzdjDO.setXmid(initServiceQO.getXmid());
        if(tjmrz) {
            //登记类型（gzdjlx）、更正主体（gzGt）、更正内容（gznr）默认值设置
            bdcGzdjDO.setGzdjlx(gzdjlx);
            BdcXmDO bdcXm = initServiceQO.getBdcXm();
            List<BdcQlrDO> bdcQlrList = initServiceQO.getBdcQlrList();
            if (Objects.nonNull(bdcXm) && Objects.nonNull(bdcXm.getDjyy())) {
                bdcGzdjDO.setGznr(bdcXm.getDjyy());
            }
            if (CollectionUtils.isNotEmpty(bdcQlrList)) {
                StringBuffer qlrStr = new StringBuffer();
                for (BdcQlrDO bdcQlrDO : bdcQlrList) {
                    if (CommonConstantUtils.QLRLB_QLR.equals(bdcQlrDO.getQlrlb())) {
                        if (StringUtils.isBlank(qlrStr)) {
                            qlrStr.append(bdcQlrDO.getQlrmc());
                        } else {
                            qlrStr.append(",").append(bdcQlrDO.getQlrmc());
                        }
                    }
                }
                if (StringUtils.isNotBlank(qlrStr)) {
                    bdcGzdjDO.setGzgt(qlrStr.toString());
                }
            }
        }
        if(initServiceQO.getBdcGzdjDO() != null){
            dozerUtils.initBeanDateConvert(initServiceQO.getBdcGzdjDO(),bdcGzdjDO);

        }
        return bdcGzdjDO;

    }
}
