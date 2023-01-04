package cn.gtmap.realestate.certificate.service;

import cn.gtmap.realestate.common.core.dto.certificate.eCertificate.DzqzCsDTO;
import cn.gtmap.realestate.common.core.dto.certificate.eCertificate.EZsDTO;
import cn.gtmap.realestate.common.core.dto.exchange.changzhou.dzqz.DzzzResponseData;

import java.util.List;

/**
 * 电子证照服务 2.0 版本
 *
 * @author <a href="mailto:lixin1@lixin.com">lixin</a>
 * @version 上午9:53 2021/1/8
 */
public interface ECertificate2Service {

    /**
     * 生成证照接口 2.0 「模版配置化」
     *
     * @param xmid xmid
     * @param bgyy 当前用户名
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    List<DzzzResponseData> cancelZzqz(String xmid, Integer bgyy);

    /**
     * 生成证照接口 2.0 「模版配置化」
     *
     * @param xmid xmid
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    List<DzzzResponseData> mbpzPdf(String xmid);

    /**
     * 常州 推送单个证书电子签章
     * @param zsid 证书id
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    void tsBdcZsDzqz(String zsid);

    /**
     * 电子证照文件下载
     *
     * @param gzlslid gzlslid
     * @param zsid    证书 id
     * @param symd    使用目的
     * @return path 大云平台下载地址
     * @author <a href="mailto:lixin1@lixin.com">lixin</a>
     */
    String zzxxxz(String gzlslid, String zsid, Integer symd);

    /**
     * 注销/作废 电子签章
     *
     * @param zsid 证书 id
     * @param bgyy 变更原因
     * @return void
     * @author <a href="mailto:lixin1@lixin.com">lixin</a>
     */
    DzzzResponseData zzzx(String zsid, Integer bgyy);

    /**
     * 常州证照签章服务
     *
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    void changzhouZzqz(String processInsId);

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 公告推送签章
     * @date : 2022/3/14 9:30
     */
    void changZhouGgtsqz(String gzlslid);


    /**
     * @param dzqzCsDTO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取生成电子签章需要的参数
     * @date : 2022/8/24 13:51
     */
    EZsDTO getDzqzCs(DzqzCsDTO dzqzCsDTO);

}
