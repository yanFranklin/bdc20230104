package cn.gtmap.realestate.inquiry.service.changzhou;

import java.util.List;

/**
 * 发票打印
 *
 * @author <a href="mailto:lixin1@lixin.com">lixin</a>
 * @version 5:16 下午 2020/11/30
 */
public interface BdcFpDyService {

    /**
     * 常州缴款书主表 数据源
     *
     * @param fplb   fplb
     * @param sfxxid sfxxid
     * @return 主表数据
     */
    Object getJksZbSjy(String sfxxid, String fplb);

    /**
     * 常州缴款书子表 数据源
     *
     * @param fplb   fplb
     * @param sfxxid sfxxid
     * @return 主表数据
     */
    Object getJksZb(String sfxxid, String fplb);

    /**
     * 查询收费信息「单个」<br>
     * 基于 sfxx 基础上额外查询 qlrmc，gzldymc 和 收费项目合计金额
     *
     * @param slbh   slbh
     * @param sfxmdm sfxmdm 「逗号隔开」
     * @return {List} 收费信息集合
     */
    Object queryFpcxSfxx(String slbh, String sfxmdm);

    /**
     * 批量查询收费信息<br>
     * 区分权利人，查询 jkfs 为 扫码支付并且 jspzfph 和 fssrfph 均为空的收费信息
     *
     * @param qlrlb qlrlb
     * @param fplb  fplb
     * @return {sfxx} 收费信息
     * @author <a href="mailto:lixin1@lixin.com">lixin</a>
     */
    Object listFpcxSfxx(String qlrlb, String fplb);

    Object listFpcxSfxx(List<String> sfxxidList, String fplb);
}
