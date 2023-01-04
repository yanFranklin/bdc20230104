package cn.gtmap.realestate.register.core.thread;

import cn.gtmap.realestate.common.core.service.feign.certificate.ECertificateFeignService;
import cn.gtmap.realestate.common.core.support.thread.CommonThread;

/**
 * 注销电子证照线程服务
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0  2020/06/11
 * @description
 */
public class BdcZxDzzzThread extends CommonThread{

    private ECertificateFeignService eCertificateFeignService;
    private String gzlslid;

    /**
     * 构造函数
     * @param eCertificateFeignService
     */
    public BdcZxDzzzThread(ECertificateFeignService eCertificateFeignService,String gzlslid){
        this.eCertificateFeignService = eCertificateFeignService;
        this.gzlslid = gzlslid;

    }

    /**
     * 默认执行方法
     */
    @Override
    public void execute() throws Exception {
        eCertificateFeignService.zxHefeiDzzz(gzlslid,"");
    }
}
