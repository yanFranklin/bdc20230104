package cn.gtmap.realestate.register.service;

import cn.gtmap.realestate.common.core.domain.BdcFgfDO;

import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/12/17
 * @description 不动产房改房服务
 */
public interface BdcFgfService {
    /**
     * @param bdcFgfDOList 批量的房改房信息
     * @return int 执行的数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 保存房改房项目信息
     */
    int saveFgfxm(List<BdcFgfDO> bdcFgfDOList);

    /**
     * @param bdcFgfDOList 批量的房改房信息
     * @return int 执行的数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新房改房项目信息
     */
    int updateFgfxm(List<BdcFgfDO> bdcFgfDOList);

    /**
     * @param gzlslid 工作流实例ID
     * @return List<BdcFgfDO>  查询到的房改房信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取当前流程的房改房信息
     */
    List<BdcFgfDO> getLcFgfxm(String gzlslid);


    /**
     * @param processInsId
     * @return void
     * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 现省直房改房上市交易成功后，把不动产登记的数据再推送给房改办进行登记
     */
    void djSzfgb(String processInsId);
}
