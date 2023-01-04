package cn.gtmap.realestate.register.core.service;

import cn.gtmap.realestate.common.core.domain.BdcDysdDO;
import cn.gtmap.realestate.common.core.domain.BdcZssdDO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcDysdQO;

import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/4/17
 * @description 不动产单元锁定service
 */
public interface BdcdySdService {
    /**
     * @param bdcdyh 不动产单元号
     * @param sdzt   锁定状态
     * @return BdcDysdDO 查询到的锁定结果集
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询不动产单元锁定结果
     */
    List<BdcDysdDO> queryBdcDysd(String bdcdyh, Integer sdzt, Integer bdclx);

    /**
     * @param bdcDysdQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 批量查询单元锁定信息
     * @date : 2021/8/19 15:31
     */
    List<BdcDysdDO> listBdcDySd(BdcDysdQO bdcDysdQO);

    /**
     * @param bdcdyh 不动产单元号
     * @param sdzt   锁定状态
     * @return BdcDysdDO 查询到的锁定结果集
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询不动产证书锁定结果
     */
    List<BdcZssdDO> queryBdZsSd(String bdcdyh, Integer sdzt, Integer bdclx);

    /**
     *
     * @param bdcdyh
     * @return
     */
    Integer queryBdcXssdcs(String bdcdyh);

    /**
     * 查询证书锁定下的所有的不动产单元号
     * @param bdcdyh
     * @param sdzt
     * @return
     */
    public List<String> queryBdcZssdBdcdyh(List<String> bdcdyh, Integer sdzt);
}
