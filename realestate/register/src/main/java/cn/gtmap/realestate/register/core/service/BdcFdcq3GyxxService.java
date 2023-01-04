package cn.gtmap.realestate.register.core.service;

import cn.gtmap.realestate.common.core.domain.BdcFdcq3GyxxDO;

import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/11/11
 * @description 建筑物区分所有权业主共有部分登记信息_共有部分
 */
public interface BdcFdcq3GyxxService {
    /**
     * @param xmid
     * @return List<BdcFdcq3GyxxDO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 建筑物区分所有权业主共有部分登记信息_共有部分
     */
    List<BdcFdcq3GyxxDO> queryListBdcQlByXmid(String xmid);

    /**
     * @param bdcdyh 不动产单元号（或地籍号）
     * @return List<BdcFdcq3GyxxDO> 查询现势的共有信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据单元号或者地籍号，查询现势的共有信息
     */
    List<BdcFdcq3GyxxDO> queryListFdcq3Gyxx(String bdcdyh);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return xmid 项目ID
     * @description 获取建筑物区分所有权业主共有部分登记权利人信息
     */
    String getFdcq3Qlr(String xmid);
}
