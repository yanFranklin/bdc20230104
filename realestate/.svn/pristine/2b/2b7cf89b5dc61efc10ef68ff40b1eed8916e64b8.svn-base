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
 * @description 省级平台 提交查询申请线程处理类
 */
public class SjptQuarzCommitCxHandlerThread extends CommonThread {

    private static final Logger LOGGER = LoggerFactory.getLogger(SjptQuarzCommitCxHandlerThread.class);

    /**
     * 服务
     */
    private GxPeCxqqServiceThread gxPeCxqqServiceThread;

    private List<GxPeCxqq> gxPeCxqqList;

    public SjptQuarzCommitCxHandlerThread(GxPeCxqqServiceThread gxPeCxqqServiceThread, List<GxPeCxqq> gxPeCxqqList) {
        this.gxPeCxqqServiceThread = gxPeCxqqServiceThread;
        this.gxPeCxqqList = gxPeCxqqList;
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

    /**
     * 默认执行方法
     */
    @Override
    public void execute() throws Exception {
//        LOGGER.info("线程开始处理：");
//        LOGGER.info("参数：{}", JSON.toJSONString(gxPeCxqqList));
        gxPeCxqqServiceThread.batchCommitCxsq(gxPeCxqqList);
    }
}
