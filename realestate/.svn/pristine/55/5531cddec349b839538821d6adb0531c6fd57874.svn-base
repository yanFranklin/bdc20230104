package cn.gtmap.realestate.accept.core.service;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlSjclDO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSjclQO;

import java.util.List;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/11/5
 * @description 不动产受理收件材料数据服务
 */
public interface BdcSlSjclService {
    /**
     * @param sjclid 收件材料ID
     * @return 不动产受理收件材料
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据收件材料ID获取不动产受理收件材料
     */
    BdcSlSjclDO queryBdcSlSjclBySjclid(String sjclid);

    /**
     * @param wjzxid 文件中心ID
     * @return 不动产受理收件材料
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据文件中心ID获取不动产受理收件材料
     */
    BdcSlSjclDO queryBdcSlSjclByWjzxid(String wjzxid);


    /**
     * @param gzlslid 工作流实例ID
     * @return 不动产受理收件材料
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 工作流实例ID获取不动产受理收件材料
     */
    List<BdcSlSjclDO> listBdcSlSjclByGzlslid(String gzlslid);


    /**
     * @param gzlslid 工作流实例ID
     * @param clmc 材料名称
     * @return 不动产受理收件材料
     * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description 根据项目ID、工作流实例ID或者材料名称获取不动产受理收件材料
     */
    List<BdcSlSjclDO> listBdcSlSjcl(String gzlslid, String clmc);

    /**
     * @param bdcSlSjclDO 不动产受理收件材料
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 新增不动产受理收件材料
     */
    BdcSlSjclDO insertBdcSlSjcl(BdcSlSjclDO bdcSlSjclDO);

    /**
     * @param bdcSlSjclDO 不动产受理收件材料
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 更新不动产受理收件材料
     */
    Integer updateBdcSlSjcl(BdcSlSjclDO bdcSlSjclDO);

    /**
     * @param sjclid 收件材料ID
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据收件材料ID删除不动产受理收件材料
     */
    Integer deleteBdcSlSjclBySjclid(String sjclid);

    /**
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据工作流实例ID删除不动产受理收件材料
     */
    Integer deleteBdcSlSjclByGzlslid(String gzlslid);

    /**
     * @param bdcSlSjclDOList
     * @return 新增数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量新增收件材料
     */
    int insertBatchSjclList(List<BdcSlSjclDO> bdcSlSjclDOList);


    /**
     * @param bdcSlSjclQO 查询条件
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询收件材料新接口
     * @date : 2021/3/25 14:16
     */
    List<BdcSlSjclDO> listBdcSlSjcl(BdcSlSjclQO bdcSlSjclQO);
}
