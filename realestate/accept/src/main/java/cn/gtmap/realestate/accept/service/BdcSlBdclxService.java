package cn.gtmap.realestate.accept.service;


/**
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @version 2019/3/4,1.0
 * @description
 */
public interface BdcSlBdclxService {

    /**
     * @param processDefId 不动产受理项目前台
     * @param bdcdyh       不动产单元号
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 初始化受理项目信息
     */
    Integer queryBdclx(String processDefId, String bdcdyh);

}
