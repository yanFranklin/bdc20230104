package cn.gtmap.realestate.init.core.thread;

import cn.gtmap.realestate.common.core.support.thread.CommonThread;
import cn.gtmap.realestate.init.core.service.BdcXtQlqtzkFjPzService;
import org.apache.commons.lang3.StringUtils;

/**
 *  更新证书的权利其他状况或者附记
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/3/11.
 * @description
 */
public class InitZsQlqtzkFjThread extends CommonThread{
    /**
     * 参数 项目ID
     */
    private String xmid;

    /**
     * 参数 更新模式
     */
    private String mode;

    /**
     * 服务
     */
    private BdcXtQlqtzkFjPzService bdcXtQlqtzkFjPzService;

    /**
     * 构造函数
     * @param bdcXtQlqtzkFjPzService
     * @param xmid
     */
    public InitZsQlqtzkFjThread(BdcXtQlqtzkFjPzService bdcXtQlqtzkFjPzService, String xmid,String mode){
        this.bdcXtQlqtzkFjPzService=bdcXtQlqtzkFjPzService;
        this.xmid=xmid;
        this.mode=mode;
    }

    /**
     * 默认执行方法
     */
    @Override
    public void execute() throws Exception {
        // 更新附记
        if(StringUtils.isNotBlank(xmid) && StringUtils.isNotBlank(mode) ){
            bdcXtQlqtzkFjPzService.updateQlqtzkFjByMode(xmid,mode);
        }
    }
}
