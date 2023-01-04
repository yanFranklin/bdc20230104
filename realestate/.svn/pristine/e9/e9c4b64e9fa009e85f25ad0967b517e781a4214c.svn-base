package cn.gtmap.realestate.register.service.xxbl;


import cn.gtmap.realestate.common.core.domain.BdcZssdDO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcXzZsVO;

import java.util.List;

/**
 * 不动产信息补录证书接口
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version v1.0, 2020/01/07 18:17
 */
public interface BdcXxblZsService {

    /**
     * @param xmid   xmid
     * @param bdcqzh bdcqzh 多个证号，英文逗号隔开
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 信息补录更新不动产权证号 </br>
     * 1. 保存证书表中产权证号 </br>
     * 2. 更新相关表中冗余字段 </br>
     * 3. 更新权利其他状况和附记 </br>
     */
    void updateBlBdcqzh(String xmid, String bdcqzh);

    /**
     * @param zsid   证书 id
     * @param xmid   项目 id
     * @param qlqtzk 权利其他状况
     * @param bdcqzh 不动产权证号
     * @param fj     附记
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 更新部分证书信息，同时处理冗余字段 </br>
     * 权利其他状况、附记以及不动产权证号
     */
    void updateBfZsxx(String zsid, String xmid, String qlqtzk,
                      String bdcqzh, String fj);

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [zsid, xmid, bdcqzh]
     * @return void
     * @description 更新不动产权证号
     */
    void updateBdcqzh(String zsid, String xmid, String bdcqzh);

    /**
     * 更新证书锁定 <br>
     * <p>
     * 由于重新生成证书，所以先对于将原证书锁定信息关联到新生成的证书上 <br/>
     *
     * @param xmid       xmid
     * @param bdcZssdDOS 证书锁定
     * @return 更新数据条数
     * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
     */
    int updateZssd(String xmid, List<BdcZssdDO> bdcZssdDOS);

    /**
     * 查询证书锁定信息
     *
     * @param xmid xmid
     * @param sdzt 锁定状态
     * @return 证书锁定集合
     */
    List<BdcZssdDO> listZssdByXmid(String xmid, Integer sdzt);

    /**
     * @param bdcXzZsVO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 修正流程更新证书信息
     * @date : 2021/11/25 9:47
     */
    void updateXzBdcZs(BdcXzZsVO bdcXzZsVO);

    /**
     * 添加项目和已有证书的关联关系
     *
     * @param bdcZsQO
     * @author <a href="mailto:liaoxiang@gtmap.cn">wangyinghao</a>
     * @description 添加项目和已有证书的关联关系
     */
    public void addXmZsConnection(BdcZsQO bdcZsQO);
}
