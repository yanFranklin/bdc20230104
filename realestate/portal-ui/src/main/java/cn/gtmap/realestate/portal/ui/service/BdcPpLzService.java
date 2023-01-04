package cn.gtmap.realestate.portal.ui.service;

/**
 * 不动产签名验证业务类
 *
 * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
 * @version 1.0, 2019/10/16
 */
public interface BdcPpLzService {
    /**
     * 匹配数据
     * 执行落宗操作，如果有数据可以落宗就会执行并返回 true，否则返回 false
     *
     * @param bdcdyh 不动产单元号
     * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
     */
    void matchData(String bdcdyh) ;
}
