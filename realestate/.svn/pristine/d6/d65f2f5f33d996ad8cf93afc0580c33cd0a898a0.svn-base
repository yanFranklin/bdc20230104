package cn.gtmap.realestate.accept.core.service;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxmPzDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlSfxmDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
 * @version 1.0, 2018/11/5
 * @description 不动产受理收费项目数据服务
 */
public interface BdcSlSfxmPzService {
    /**
     * @param djxl 登记小类
     * @return 不动产受理收费项目配置
     * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description 根据登记小类获取不动产受理收费项目配置
     */
    List<BdcSlSfxmPzDO> listBdcSlSfxmPzByDjxl(String djxl);

    /**
     * @param djxl 登记小类
     * @return 不动产受理收费项目配置
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据登记小类和权利人类别获取不动产受理收费项目配置
     */
    List<BdcSlSfxmPzDO> listBdcSlSfxmPzByDjxlAndQlrlb(String djxl, String qlrlb);

    /**
     * @param bdcSlSfxmPzDO 不动产受理收费项目配置
     * @return 修改数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 保存不动产受理收费项目配置
     */
    int saveBdcSlSfxmPzDO(BdcSlSfxmPzDO bdcSlSfxmPzDO);

    /**
     * @param bdcSlSfxmPzDOList 收费项目配置集合
     * @return 删除数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 删除不动产受理收费项目配置
     */
    int deleteBdcSlSfxmPzDO(List<BdcSlSfxmPzDO> bdcSlSfxmPzDOList);

    /**
     * @param bdcSlSfxmPzJson 登记小类
     * @return 分页
     * @author <a href="mailto:gailining@gtmap.cn">gailining</a>
     * @description 分页查询收件材料配置
     */
    Page<BdcSlSfxmPzDO> listBdcSlSfxmPzByPage(Pageable pageable, String bdcSlSfxmPzJson);

    /**
     * @param bdcSlSfxmPzDO
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取受理收费项目配置 最大序号
     */
    Integer querySfxmPzMaxSxh(BdcSlSfxmPzDO bdcSlSfxmPzDO);

    /**
     * @return 收费项目配置
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取所有收费项目配置
     */
    List<BdcSlSfxmDTO> listAllBdcSlSfxmDTO();

    /**
     * 根据登记小类获取收费项目配置
     * @param djxl 登记小类
     * @return 收费项目配置
     * @author <a href="mailto:liaoxiang@gtmap.cn">yaoyi</a>
     */
    List<BdcSlSfxmDTO> listBdcSlSfxmDTOByDjxl(String djxl);
}
