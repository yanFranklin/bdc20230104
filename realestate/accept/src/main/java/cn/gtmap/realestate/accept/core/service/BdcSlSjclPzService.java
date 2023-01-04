package cn.gtmap.realestate.accept.core.service;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlSjclPzDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
 * @version 1.0, 2018/12/28
 * @description 不动产受理收件材料配置数据服务
 */
public interface BdcSlSjclPzService {

    /**
     * @param djxl 登记小类
     * @return 不动产受理收件材料配置信息
     * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description 根据登记小类获取不动产受理收件材料配置信息
     */
    List<BdcSlSjclPzDO> listBdcSlSjclPzByDjxl(String djxl);

    /**
     * @param bdcSlSjclPzDO 不动产受理收件材料配置信息
     * @return 修改数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 保存不动产受理收件材料配置信息
     */
    int saveBdcSlSjclPzDO(BdcSlSjclPzDO bdcSlSjclPzDO);

    /**
     * @param bdcSlSjclPzDOList 不动产受理收件材料配置
     * @return 删除数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 删除不动产受理收件材料配置信息
     */
    int deleteBdcSlSjclPzDO(List<BdcSlSjclPzDO> bdcSlSjclPzDOList);

    /**
     * @param bdcSlSjclPzJson 收件材料配置
     * @return 分页
     * @author <a href="mailto:gailining@gtmap.cn">gailining</a>
     * @description 分页查询收件材料配置
     */
    Page<BdcSlSjclPzDO> listBdcSlSjclPzByPage(Pageable pageable, String bdcSlSjclPzJson);

    /**
     * @param bdcSlSjclPzDO 受理收件材料配置
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取收件材料 配置 最大序号
     */
    Integer querySjclPzMaxSxh(BdcSlSjclPzDO bdcSlSjclPzDO);
}
