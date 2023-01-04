package cn.gtmap.realestate.accept.core.thread;

import cn.gtmap.realestate.accept.service.BdcSfService;
import cn.gtmap.realestate.common.core.support.thread.CommonThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2021/12/3.
 * @description 不动产自动计算收费信息收费项目线程类
 */
public class BdcAutoCountSfxxSfxmThread extends CommonThread{

    /**
     * 日志组件
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcAutoCountSfxxSfxmThread.class);

    /**
     * 工作流实例ID
     */
    private String gzlslid;
    /**
     * 不动产收费信息Service
     */
    private BdcSfService bdcSfService;

    public BdcAutoCountSfxxSfxmThread(String gzlslid, BdcSfService bdcSfService){
        this.gzlslid = gzlslid;
        this.bdcSfService = bdcSfService;
    }

    /**
     * 默认执行方法
     */
    @Override
    public void execute() {
        try{
            bdcSfService.autoCountSfxxSfxmForZf(gzlslid);
        }catch(Exception e){
            LOGGER.error("自动计算收费信息收费项目线程异常：", e.getMessage(), e);
        }
    }

}
