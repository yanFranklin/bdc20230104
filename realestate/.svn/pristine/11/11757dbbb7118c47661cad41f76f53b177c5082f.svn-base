package cn.gtmap.realestate.accept.core.thread;

import cn.gtmap.realestate.accept.core.service.BdcSlFpxxService;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;
import cn.gtmap.realestate.common.core.enums.BdcSfztEnum;
import cn.gtmap.realestate.common.core.support.thread.CommonThread;
import cn.gtmap.realestate.common.util.CommonConstantUtils;

import java.util.Objects;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2021/8/20.
 * @description 盐城非税自动执行获取发票信息线程类
 */
public class FsAutoExecFpxxThread extends CommonThread {

    /**
     * 受理发票信息实现类
     */
    private BdcSlFpxxService bdcSlFpxxService;
    /**
     * 工作流实例ID
     */
    private String gzlslid;
    /**
     * 收费信息
     */
    private BdcSlSfxxDO bdcSlSfxxDO;


    public FsAutoExecFpxxThread (String gzlslid, BdcSlSfxxDO bdcSlSfxxDO, BdcSlFpxxService bdcSlFpxxService){
        this.gzlslid = gzlslid;
        this.bdcSlSfxxDO = bdcSlSfxxDO;
        this.bdcSlFpxxService = bdcSlFpxxService;
    }


    @Override
    public void execute() throws Exception {
        // 获取缴款情况，缴款状态为已交款之后，自动执行获取发票号、开具发票的操作
        if(Objects.nonNull(bdcSlSfxxDO) && Objects.equals(BdcSfztEnum.YJF.key(), bdcSlSfxxDO.getSfzt())){
            // 1、 获取电子票号
            bdcSlFpxxService.queryDzfpxx(bdcSlSfxxDO, "");

            // 2、开具电子发票
            bdcSlFpxxService.inovice(bdcSlSfxxDO.getSfxxid(), gzlslid, CommonConstantUtils.QLRLB_QLR);

            // 等待 5s， 开具发票后，需要等待5s在进行获取电子发票
            Thread.sleep(5000);

            // 3、获取电子发票信息
            bdcSlFpxxService.getFpxxAndUploadFpxx(bdcSlSfxxDO.getSfxxid(), gzlslid);
        }
    }
}
