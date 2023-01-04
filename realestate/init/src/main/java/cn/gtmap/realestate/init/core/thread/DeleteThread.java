package cn.gtmap.realestate.init.core.thread;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.support.thread.CommonThread;
import cn.gtmap.realestate.init.service.InitService;
import cn.gtmap.realestate.init.service.other.InitDataDealService;

import java.util.List;

/**
 * 初始化的删除以及证书删除多线程服务
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/3/11.
 * @description
 */
public class DeleteThread extends CommonThread{
    /**
     * 处理service
     */
    private InitDataDealService initDataDealService;
    /**
     * 要删除的项目
     */
    private List<BdcXmDO> listXm;
    /**
     * 查询出的要删除的数据
     */
    private List<Object> deleteList;
    /**
     * 单独删除service
     */
    private InitService initService;

    /**
     * 构造函数
     * @param initDataDealService
     * @param listXm
     * @param deleteList
     */
    public DeleteThread(InitDataDealService initDataDealService, List<BdcXmDO> listXm, List<Object> deleteList){
        this.initDataDealService=initDataDealService;
        this.listXm=listXm;
        this.deleteList=deleteList;
    }

    /**
     * 构造函数
     * @param initService
     * @param listXm
     * @param deleteList
     */
    public DeleteThread(InitService initService, List<BdcXmDO> listXm, List<Object> deleteList){
        this.initService=initService;
        this.listXm=listXm;
        this.deleteList=deleteList;
    }



    /**
     * 默认执行方法
     */
    @Override
    public void execute() throws Exception {
        if(initDataDealService!=null){
            deleteList.addAll(initDataDealService.queryDeleteData(listXm,false,false,false,false));
        }else if(initService!=null){
            deleteList.addAll(initService.delete(listXm,false,false,false));
        }
    }
}
