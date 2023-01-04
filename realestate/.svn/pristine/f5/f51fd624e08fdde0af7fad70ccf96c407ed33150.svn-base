package cn.gtmap.realestate.register.core.thread;

import cn.gtmap.realestate.common.core.dto.building.BdcSyncZtRequestDTO;
import cn.gtmap.realestate.common.core.support.thread.CommonThread;
import cn.gtmap.realestate.register.service.BdcdyZtService;

import java.util.List;

/**
 * 回写不动产单元状态多线程服务
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/7/21.
 * @description
 */
public class BdcdyZtThread extends CommonThread{

    /**
     * 单元号
     */
    private String bdcdyh;
    /**
     * 结果集合
     */
    private List<BdcSyncZtRequestDTO> bdcdyhZtList;
    /**
     * 状态服务
     */
    private BdcdyZtService bdcdyZtService;

    /**
     * 构造函数
     * @param bdcdyhZtList
     * @param bdcdyh
     * @param bdcdyZtService
     */
    public BdcdyZtThread(List<BdcSyncZtRequestDTO> bdcdyhZtList, String bdcdyh, BdcdyZtService bdcdyZtService) {
        this.bdcdyhZtList=bdcdyhZtList;
        this.bdcdyh = bdcdyh;
        this.bdcdyZtService=bdcdyZtService;
    }

    /**
     * 默认执行方法
     */
    @Override
    public void execute() throws Exception {
        BdcSyncZtRequestDTO bdcSyncZtRequestDTO = bdcdyZtService.queryBdcdyhSyncZt(bdcdyh);
        if (bdcSyncZtRequestDTO != null) {
            //放入集合
            bdcdyhZtList.add(bdcSyncZtRequestDTO);
        }
    }
}
