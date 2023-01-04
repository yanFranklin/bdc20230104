package cn.gtmap.realestate.accept.core.thread;

import cn.gtmap.realestate.accept.core.service.BdcSlJbxxService;
import cn.gtmap.realestate.accept.core.service.BdcSlxxHxService;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.support.thread.CommonThread;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 20922/2/23.
 * @description 常州批量缴费台账，回写大云权利人名称和坐落信息
 */
public class HxPljfxxThread  extends CommonThread {

    private static final Logger LOGGER = LoggerFactory.getLogger(HxPljfxxThread.class);

    /**
     * 不动产受理基本信息
     */
    private BdcSlJbxxDO bdcSlJbxxDO;
    /**
     * 权利人名称
     */
    private String qlrmc;
    /**
     * 坐落
     */
    private String zl;
    /**
     * 受理回写大云数据
     */
    private BdcSlxxHxService bdcSlxxHxService;

    private BdcSlJbxxService bdcSlJbxxService;

    public HxPljfxxThread(String qlrmc, String zl, BdcSlJbxxDO bdcSlJbxxDO, BdcSlxxHxService bdcSlxxHxService, BdcSlJbxxService bdcSlJbxxService){
        this.qlrmc = qlrmc;
        this.zl = zl;
        this.bdcSlJbxxDO = bdcSlJbxxDO;
        this.bdcSlxxHxService = bdcSlxxHxService;
        this.bdcSlJbxxService = bdcSlJbxxService;
    }

    @Override
    public void execute() throws Exception {
        if(StringUtils.isNotBlank(qlrmc)){
            bdcSlJbxxDO.setSqrxm(qlrmc);
        }
        if(StringUtils.isNotBlank(zl)){
            bdcSlJbxxDO.setZl(zl);
        }
        if(StringUtils.isNotBlank(qlrmc) || StringUtils.isNotBlank(zl)){
            this.bdcSlJbxxService.updateBdcSlJbxx(bdcSlJbxxDO);
        }

        try{
            bdcSlxxHxService.hxBdcSlxx(bdcSlJbxxDO);
        }catch(Exception e){
            LOGGER.error("批量缴费台账回写大云权利人名称、坐落信息失败。", e);
        }
    }
}
