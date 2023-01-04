package cn.gtmap.realestate.accept.core.service;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlJbxxQO;

import java.util.List;
import java.util.Objects;


/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/11/5
 * @description 不动产受理基本信息数据服务
 */
public interface BdcSlJbxxService {
    /**
     * @param jbxxid 基本信息ID
     * @return 不动产受理基本信息
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据基本信息ID获取不动产受理基本信息
     */
    BdcSlJbxxDO queryBdcSlJbxxByJbxxid(String jbxxid);

    /**
     * @param gzlslid 工作流实例ID
     * @return 不动产受理基本信息
     * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description 根据工作流实例ID获取不动产受理基本信息
     */
    BdcSlJbxxDO queryBdcSlJbxxByGzlslid(String gzlslid);

    /**
     * @param gzlslid 工作流实例ID
     * @return 不动产受理基本信息
     * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description 根据工作流实例ID获取不动产受理基本信息
     */
    List<BdcSlJbxxDO> queryBdcSlJbxxByGzlslids(List<String> gzlslid);

    /**
     * @param slbh
     * @param type 1:一窗受理(无登记数据) 2 登记,其他值或空任取一条
     * @return 不动产受理基本信息
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 根据slbh获取不动产受理基本信息
     */
    BdcSlJbxxDO queryBdcSlJbxxBySlbh(String slbh,String type);

    /**
     * @param bdcSlJbxxDO 不动产受理基本信息
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 新增不动产受理基本信息
     */
    BdcSlJbxxDO insertBdcSlJbxx(BdcSlJbxxDO bdcSlJbxxDO);

    /**
     * @param bdcSlJbxxDO 不动产受理基本信息
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 更新不动产受理基本信息
     */
    Integer updateBdcSlJbxx(BdcSlJbxxDO bdcSlJbxxDO);

    /**
     * @param jbxxid 基本信息ID
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据基本信息ID删除不动产受理基本信息
     */
    Integer deleteBdcSlJbxxByJbxxid(String jbxxid);

    /**
     * @param bdcSlJbxxQO 受理基本信息
     * @return 受理基本信息
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @description  获取不动产受理基本信息
     */
    List<BdcSlJbxxDO> listBdcSlJbxxByJbxxQO(BdcSlJbxxQO bdcSlJbxxQO);
}
