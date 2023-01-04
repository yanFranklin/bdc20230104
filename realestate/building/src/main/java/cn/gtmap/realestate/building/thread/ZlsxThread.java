package cn.gtmap.realestate.building.thread;

import cn.gtmap.realestate.common.core.dto.building.ZlsxDTO;
import cn.gtmap.realestate.building.core.service.ZlsxService;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.support.thread.CommonThread;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/29
 * @description 坐落刷新
 */
public class ZlsxThread extends CommonThread {
    /**
     * 坐落刷新service
     */
    private ZlsxService zlsxService;
    /**
     * 坐落刷新QO
     */
    private ZlsxDTO zlsxDTO;
    /**
     * 房屋户室DO
     */
    private FwHsDO fwHsDO;
    /**
     * 房屋户室DO
     */
    private String zlExceptHs;

    public ZlsxThread(ZlsxService service, ZlsxDTO zlsx, FwHsDO fwHs,String zl) {
        zlsxService = service;
        zlsxDTO = zlsx;
        fwHsDO=fwHs;
        zlExceptHs=zl;
    }

    @Override
    public void execute() throws Exception {
        zlsxService.fwhsZlsxByRule(fwHsDO, zlsxDTO,zlExceptHs);
    }
}