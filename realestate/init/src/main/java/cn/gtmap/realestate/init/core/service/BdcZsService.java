package cn.gtmap.realestate.init.core.service;

import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;


/**
 * 证书单体服务
 *
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0
 * @description
 */
public interface BdcZsService {

    /**
     * 通过证书id获取证书信息
     *
     * @param zsid 证书id
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    BdcZsDO queryBdcZsById(String zsid);

    /**
     * 更新证书信息
     *
     * @param bdcZs 不动产证书
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    void updateBdcZs(BdcZsDO bdcZs);

    /**
     * 通过项目id获取证书信息
     * @param xmid 项目id
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    List<BdcZsDO> queryBdcZsByXmid(String xmid);

    /**
     * 删除流程证书
     *
     * @param gzlslid 工作流实例ID
     * @return
     */
    void deleteZs(@NotBlank(message = "工作流实例ID不能为空") String gzlslid);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据xmid 删除证书
     * @date : 2022/12/27 10:48
     */
    void deleteZsByxmid(@NotBlank(message = "项目ID不能为空") String xmid);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据xmid 删除项目证书关系
     * @date : 2022/12/27 10:52
     */
    void deleteXmzsGx(@NotBlank(message = "项目ID不能为空") String xmid);

    /**
     * 删除流程证书关系
     *
     * @param gzlslid 工作流实例ID
     * @return
     */
    void deleteZsXmGx(@NotBlank(message = "工作流实例ID不能为空") String gzlslid);

}
