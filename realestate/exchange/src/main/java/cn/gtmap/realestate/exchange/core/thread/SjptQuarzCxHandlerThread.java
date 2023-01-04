package cn.gtmap.realestate.exchange.core.thread;

import cn.gtmap.realestate.common.core.support.thread.CommonThread;
import cn.gtmap.realestate.exchange.core.domain.sjpt.GxPeCxqq;
import cn.gtmap.realestate.exchange.service.inf.sjpt.thread.GxPeCxqqServiceThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
 * @version 1.0  2021-02-02
 * @description 省级平台 查询申请线程处理类
 */
public class SjptQuarzCxHandlerThread extends CommonThread {

    private static final Logger LOGGER = LoggerFactory.getLogger(SjptQuarzCxHandlerThread.class);

    /**
     * 服务
     */
    private GxPeCxqqServiceThread gxPeCxqqServiceThread;

    private List<GxPeCxqq> gxPeCxqqList;

    private boolean sfQuartz;

    public SjptQuarzCxHandlerThread(GxPeCxqqServiceThread gxPeCxqqServiceThread, List<GxPeCxqq> gxPeCxqqList, boolean sfQuartz) {
        this.gxPeCxqqServiceThread = gxPeCxqqServiceThread;
        this.gxPeCxqqList = gxPeCxqqList;
        this.sfQuartz = sfQuartz;
    }

    public GxPeCxqqServiceThread getGxPeCxqqServiceThread() {
        return gxPeCxqqServiceThread;
    }

    public void setGxPeCxqqServiceThread(GxPeCxqqServiceThread gxPeCxqqServiceThread) {
        this.gxPeCxqqServiceThread = gxPeCxqqServiceThread;
    }

    public List<GxPeCxqq> getGxPeCxqqList() {
        return gxPeCxqqList;
    }

    public void setGxPeCxqqList(List<GxPeCxqq> gxPeCxqqList) {
        this.gxPeCxqqList = gxPeCxqqList;
    }

    public boolean isSfQuartz() {
        return sfQuartz;
    }

    public void setSfQuartz(boolean sfQuartz) {
        this.sfQuartz = sfQuartz;
    }

    /**
     * 默认执行方法
     */
    @Override
    public void execute() throws Exception {
//        LOGGER.info("线程开始处理：");
//        LOGGER.info("参数：{}", JSON.toJSONString(gxPeCxqqList));
//        LOGGER.info("参数：{}", sfQuartz);
        gxPeCxqqServiceThread.batchExexuteQueryCxqq(gxPeCxqqList,sfQuartz);
    }
}
