package cn.gtmap.realestate.accept.core.service;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlFwtcxxDO;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/12/4
 * @description 房屋套次信息服务
 */
public interface BdcSlFwtcxxService {

    /**
     * @param xmid 项目ID
     * @param sqrlb 申请人类别
     * @return 房屋套次信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据xmid和申请人类别获取房屋套次信息
     */
    List<BdcSlFwtcxxDO> listBdcSlFwtcxxByXmid(String xmid,String sqrlb);

    /**
     * @param xmid 项目ID
     * @param sqrlb 申请人类别
     * @return 删除数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据xmid和申请人类别删除房屋套次信息
     */
    int deleteBdcSlFwtcxxByXmid(String xmid,String sqrlb,String sqrid);

    /**
     * @param bdcSlFwtcxxDOList 待新增的房屋套次集合
     * @param sqrlb 申请人类别
     * @param xmid 项目ID
     * @return 新增数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 新增受理房屋套次信息
     */
    int addBdcSlFwtcxx(List<BdcSlFwtcxxDO> bdcSlFwtcxxDOList,String xmid,String sqrlb);
}
