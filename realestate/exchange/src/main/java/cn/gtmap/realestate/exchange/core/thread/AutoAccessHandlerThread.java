package cn.gtmap.realestate.exchange.core.thread;

import cn.gtmap.realestate.common.core.support.thread.CommonThread;
import cn.gtmap.realestate.exchange.service.national.AccessModelServiceThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
 * @version 1.0  2021-02-02
 * @description 根据xmidList上报线程处理类
 */
public class AutoAccessHandlerThread extends CommonThread {

    private static final Logger LOGGER = LoggerFactory.getLogger(AutoAccessHandlerThread.class);

    /**
     * 服务
     */
    private AccessModelServiceThread accessModelServiceThread;

    private List<String> xmidList;

    public AutoAccessHandlerThread(AccessModelServiceThread accessModelServiceThread, List<String> xmidList) {
        this.accessModelServiceThread = accessModelServiceThread;
        this.xmidList = xmidList;
    }

    public AccessModelServiceThread getAccessModelServiceThread() {
        return accessModelServiceThread;
    }

    public void setAccessModelServiceThread(AccessModelServiceThread accessModelServiceThread) {
        this.accessModelServiceThread = accessModelServiceThread;
    }

    public List<String> getXmidList() {
        return xmidList;
    }

    public void setXmidList(List<String> xmidList) {
        this.xmidList = xmidList;
    }

    /**
     * 默认执行方法
     */
    @Override
    public void execute() throws Exception {
//        LOGGER.info("线程开始处理：");
//        LOGGER.info("参数：{}", JSON.toJSONString(xmidList));
        accessModelServiceThread.autoAccessByXmidList(xmidList);
    }
}
