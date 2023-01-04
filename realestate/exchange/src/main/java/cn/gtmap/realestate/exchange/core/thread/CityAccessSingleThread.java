package cn.gtmap.realestate.exchange.core.thread;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.support.thread.CommonThread;
import cn.gtmap.realestate.exchange.service.national.CityAccesssModelHandlerService;

import java.util.List;

/**
 * @Author  <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
 * @Description 市级根据项目id单独上报
 * @Date 2022/5/5
 * @Param
 * @return
 **/
public class CityAccessSingleThread extends CommonThread {

    /**
     * 服务
     */
    private CityAccesssModelHandlerService cityAccesssModelHandlerService;

    private BdcXmDO bdcXmDO;

    private List<BdcXmDO> bdcXmDOList;


    public CityAccessSingleThread(CityAccesssModelHandlerService cityAccesssModelHandlerService, BdcXmDO bdcXmDO, List<BdcXmDO> bdcXmDOList) {
        this.cityAccesssModelHandlerService = cityAccesssModelHandlerService;
        this.bdcXmDO = bdcXmDO;
        this.bdcXmDOList = bdcXmDOList;
        super.setSfbjs(true);
    }

    /**
     * 默认执行方法
     */
    @Override
    public void execute() throws Exception {
        cityAccesssModelHandlerService.singleAccess(bdcXmDO, bdcXmDOList);
    }
}
