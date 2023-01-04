package cn.gtmap.realestate.exchange.core.thread;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.support.thread.CommonThread;
import cn.gtmap.realestate.exchange.service.national.AccessModelServiceThread;
import cn.gtmap.realestate.exchange.service.national.AccesssModelHandlerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @Author  <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
 * @Description 根据项目id单独上报
 * @Date 2022/5/5
 * @Param
 * @return
 **/
public class AccessSingleThread extends CommonThread {

    /**
     * 服务
     */
    private AccesssModelHandlerService accesssModelHandlerService;

    private BdcXmDO bdcXmDO;

    private List<BdcXmDO> bdcXmDOList;


    public AccessSingleThread(AccesssModelHandlerService accesssModelHandlerService, BdcXmDO bdcXmDO, List<BdcXmDO> bdcXmDOList) {
        this.accesssModelHandlerService = accesssModelHandlerService;
        this.bdcXmDO = bdcXmDO;
        this.bdcXmDOList = bdcXmDOList;
        super.setSfbjs(true);
    }

    /**
     * 默认执行方法
     */
    @Override
    public void execute() throws Exception {
        accesssModelHandlerService.singleAccess(bdcXmDO, bdcXmDOList);
    }
}
