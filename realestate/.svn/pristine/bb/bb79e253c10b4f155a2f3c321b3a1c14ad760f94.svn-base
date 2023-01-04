package cn.gtmap.realestate.accept.core.thread;

import cn.gtmap.realestate.accept.service.BdcYcslManageService;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlxxInitDTO;
import cn.gtmap.realestate.common.core.support.thread.CommonThread;
import org.apache.commons.lang3.StringUtils;

/**
 *  同步生成一窗受理信息
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/3/11.
 * @description
 */
public class SyncYcslxxThread extends CommonThread{
    /**
     * 参数 项目ID
     */
    private String xmid;

    /**
     * 参数 基本信息ID
     */
    private String jbxxid;

    /**
     * 结果
     */
    private BdcSlxxInitDTO bdcSlxxInitDTO;


    /**
     * 服务
     */
    private BdcYcslManageService bdcYcslManageService;

    /**
     * 构造函数
     * @param bdcYcslManageService
     * @param xmid
     * @param jbxxid
     */
    public SyncYcslxxThread(BdcYcslManageService bdcYcslManageService, String xmid,String jbxxid){
        this.bdcYcslManageService=bdcYcslManageService;
        this.xmid=xmid;
        this.jbxxid=jbxxid;
    }

    /**
     * 默认执行方法
     */
    @Override
    public void execute() throws Exception {
        // 同步一窗信息
        if(StringUtils.isNotBlank(xmid) &&StringUtils.isNotBlank(jbxxid)){
            bdcSlxxInitDTO =bdcYcslManageService.syncYcslxx(xmid,jbxxid);
        }
    }

    public BdcSlxxInitDTO getBdcSlxxInitDTO() {
        return bdcSlxxInitDTO;
    }

}
