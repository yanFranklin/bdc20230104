package cn.gtmap.realestate.init.core.thread;

import cn.gtmap.realestate.common.core.dto.init.BdcCopyReplaceYwxxDTO;
import cn.gtmap.realestate.init.service.other.InitDataService;

import java.util.concurrent.Callable;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/11/29
 * @description 复制业务信息
 */
public class CopyYwxxThread implements Callable<Object> {

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  服务
     */
    private InitDataService initDataService;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  复制业务信息参数
     */
    private BdcCopyReplaceYwxxDTO bdcCopyReplaceYwxxDTO;

    public CopyYwxxThread(InitDataService initDataService,BdcCopyReplaceYwxxDTO bdcCopyReplaceYwxxDTO){
        this.initDataService =initDataService;
        this.bdcCopyReplaceYwxxDTO =bdcCopyReplaceYwxxDTO;
    }


    @Override
    public Object call() throws Exception {
        return initDataService.copyYwxx(bdcCopyReplaceYwxxDTO);

    }
}
