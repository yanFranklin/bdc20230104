package cn.gtmap.realestate.config.service;

import cn.gtmap.realestate.common.core.domain.BdcXtZsbhmbDO;
import cn.gtmap.realestate.common.core.qo.config.BdcXtZsbhmbQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/01/11
 * @description 业务配置系统：证书编号模板配置处理Service接口定义
 */
public interface BdcXtZsbhmbService {
    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param  pageable 分页对象
     * @param  zsbhmbQO 查询条件
     * @return {Page} 证书编号模板分页数据
     * @description  查询证书编号模板数据列表
     */
    Page<BdcXtZsbhmbDO> queryBdcXtZsbhmb(Pageable pageable, BdcXtZsbhmbQO zsbhmbQO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcXtZsbhmbDO 证书编号模板实体
     * @return {int} 操作数据记录数
     * @description 保存证书编号模板配置
     */
    int saveBdcXtZsbhmb(BdcXtZsbhmbDO bdcXtZsbhmbDO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcXtZsbhmbDOList 证书编号模板集合
     * @return {int} 操作数据记录数
     * @description 删除证书编号模板
     */
    int deleteBdcXtZsbhmb(List<BdcXtZsbhmbDO> bdcXtZsbhmbDOList);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return {String} 省区代码
     * @description 从证书模板配置中获取省区代码（正常来说所有模板配置的是一致的）
     */
    String querySqdm();

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @param zsbhmbid 证书编号模板ID
     * @return｛String｝复制后新的证书编号模板ID
     * @description 复制证号模板
     */
    String copyBdcXtZsbhmb(String zsbhmbid);

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @param bdcXtZsbhmbDO
     * @return 新增的证书编号模板ID
     */
    String insertBdcXtZsbhmb(BdcXtZsbhmbDO bdcXtZsbhmbDO);
}
