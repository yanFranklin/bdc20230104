package cn.gtmap.realestate.register.core.thread;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.building.BdcdyxxRequestDTO;
import cn.gtmap.realestate.common.core.support.thread.CommonThread;
import cn.gtmap.realestate.register.service.BdcDbxxService;

import java.util.List;

/**
 * 回写不动产单元信息多线程服务
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/7/21.
 * @description
 */
public class BdcdyxxThread extends CommonThread{

    /**
     * 当前项目
     */
   private BdcXmDO bdcXmDO;
    /**
     * 结果集合
     */
    private List bdcdyxxRequestList;
    /**
     * 登薄服务
     */
    private BdcDbxxService bdcDbxxService;

    /**
     * 构造函数
     * @param bdcdyxxRequestList
     * @param bdcXmDO
     * @param bdcDbxxService
     */
    public BdcdyxxThread(List bdcdyxxRequestList, BdcXmDO bdcXmDO, BdcDbxxService bdcDbxxService){
        this.bdcdyxxRequestList=bdcdyxxRequestList;
        this.bdcXmDO=bdcXmDO;
        this.bdcDbxxService=bdcDbxxService;
    }

    /**
     * 默认执行方法
     */
    @Override
    public void execute() throws Exception {
        BdcdyxxRequestDTO bdcdyxxRequestDTO=bdcDbxxService.getBdcdyxxRequestDTO(bdcXmDO);
        if(bdcdyxxRequestDTO!=null){
            bdcdyxxRequestList.add(bdcdyxxRequestDTO);
        }
    }
}
