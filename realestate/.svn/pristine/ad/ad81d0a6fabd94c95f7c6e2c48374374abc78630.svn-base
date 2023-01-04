package cn.gtmap.realestate.portal.ui.core.thread;

import cn.gtmap.realestate.common.core.service.feign.init.BdcPpFeignService;
import cn.gtmap.realestate.common.core.support.thread.CommonThread;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.portal.ui.service.BdcPpLzService;
import org.apache.commons.lang3.StringUtils;

/**
 * 判断是否可以匹配落宗
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version 1.0  2019/10/16.
 */
public class PpLzThread extends CommonThread {

    /**
     * 参数 bdcdyh
     */
    private String bdcdyh;

    /**
     * 参数 qjgldm
     */
    private String qjgldm;

    /**
     * 参数 取消匹配
     */
    private boolean qxpp;

    /**
     * 服务
     */
    private BdcPpLzService bdcPpLzService;

    /**
     * 服务
     */
    private BdcPpFeignService bdcPpFeignService;



    public PpLzThread(String bdcdyh, String qjgldm,BdcPpLzService bdcPpLzService,boolean qxpp,BdcPpFeignService bdcPpFeignService) {
        this.bdcdyh = bdcdyh;
        this.qjgldm =qjgldm;
        this.bdcPpLzService = bdcPpLzService;
        this.qxpp=qxpp;
        this.bdcPpFeignService=bdcPpFeignService;
    }

    @Override
    public void execute() throws Exception {
        // 匹配数据
        if(StringUtils.isNotBlank(bdcdyh)){
            if(qxpp){
                String[] param=bdcdyh.split(CommonConstantUtils.ZF_YW_FH);
                if(param.length==2 && StringUtils.isNotBlank(param[0]) && StringUtils.isNotBlank(param[1])){
                    bdcPpFeignService.qxlz(param[0],param[1],qjgldm);
                }
            }else{
                bdcPpLzService.matchData(bdcdyh);
            }
        }
    }
}
